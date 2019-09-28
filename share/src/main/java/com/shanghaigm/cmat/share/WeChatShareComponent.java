package com.shanghaigm.cmat.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.lang.reflect.Field;

/**
 * 封装分享相关功能便于使用
 * Created by Jim on 2018/07/10.
 */

public class WeChatShareComponent {
    public static final String APP_ID = getAppId();

    private static final String TAG = "WeChatShareComponent";
    // 微信分享封装类实例(单例模式)
    private static WeChatShareComponent mInstance;
    // 微信IWXAPI对象
    private static IWXAPI wxApi;

    /**
     * 私有的构造方法，单例模式
     *
     * @param mContext 用于构造和注册IWAPI对象
     */
    private WeChatShareComponent(Context mContext) {
        wxApi = WXAPIFactory.createWXAPI(mContext, getAppId(), true);
        wxApi.registerApp(getAppId());
    }

    /**
     * 私有的WeChatShareComponent 构造方法，单例模式
     * 虽然项目中应该不会涉及该类多线程的操作，但还是加一下校验
     *
     * @param mContext 用于构造WeChatShareComponent 对象
     */
    private static WeChatShareComponent createWeChatShareInstance(Context mContext) {
        if (mInstance == null) {
            synchronized (WeChatShareComponent.class) {
                if (mInstance == null) {
                    mInstance = new WeChatShareComponent(mContext);
                }
            }
        }
        Log.w(TAG, "mInstance = " + mInstance);
        return mInstance;
    }

    private static String getAppId() {
        if (TextUtils.isEmpty(APP_ID)) {
            try {
                Class config = Class.forName("com.shanghaigm.cmat.model.Config");
                Field appId = config.getField("APP_ID");
                Log.w(TAG, "获取到的APP_ID为:" + appId.get(null).toString());
                return appId.get(null).toString();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                Log.e(TAG, "获取APP_ID时获取Config类失败");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                Log.e(TAG, "未找到Config类中的APP_ID字段");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                Log.e(TAG, "访问APP_ID字段失败");
            }
            return "";
        } else {
            return APP_ID;
        }
    }

    /**
     * 构造器模式，WeChatShareComponent.Builder对象
     */
    public static class Builder {
        private Context mContext;
        // 微信网页对象
        private WXWebpageObject mWxWebPageObject;
        // 微信分享信息对象
        private WXMediaMessage mWxMediaMessage;
        // 微信图片对象
        private WXImageObject mWxImageObject;
        // 分享到微信对象
        private SendMessageToWX.Req mSReq;

        /**
         * WeChatShareComponent.Builder的构造方法
         *
         * @param mContext 上下文对象
         */
        public Builder(Context mContext) {
            this.mContext = mContext;
            initWXMediaMessage();
        }

        /**
         * 设置分享内容的url
         *
         * @param url 分享内容的url
         */
        public Builder setShareContentUrl(String url) {
            initWXWebPageObject();
            mWxWebPageObject.webpageUrl = url;
            return this;
        }

        /**
         * 设置分享内容的标题
         *
         * @param title 分享内容的标题
         */
        public Builder setShareContentTitle(String title) {
            mWxMediaMessage.title = title;
            return this;
        }

        /**
         * 设置分享内容的摘要
         *
         * @param summary 分享内容的摘要
         */
        public Builder setShareContentSummary(String summary) {
            mWxMediaMessage.description = summary;
            return this;
        }

        /**
         * 设置分享内容的缩略图
         *
         * @param thumb 分享内容的缩略图bitmap对象
         *              要求图片不能大于32kb
         *              否则会throw checkArgs fail, thumbData is invalid 错误
         */
        public Builder setShareContentImage(Bitmap thumb) {
            try {
                mWxMediaMessage.thumbData = BitmapUtils.compressImageStream(thumb, 32);// BitmapUtil.compressAndGenImage(thumb, 32);
                if (!thumb.isRecycled()) {
                    thumb.recycle();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this;
        }

        public Builder setShareImage(Bitmap image) {
            try {
                initWXImageObject();
                mWxImageObject.imageData = BitmapUtils.compressImageStream(image, 5096);
                mWxMediaMessage.mediaObject = mWxImageObject;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this;
        }

        /**
         * 设置分享内容的缩略图SendMessageToWX
         *
         * @param scene 分享场景，0-对话，1-朋友圈，2-收藏，详见{@link com.tencent.mm.opensdk.modelmsg.SendMessageToWX.Req}
         * @param type  分享内容的缩略图bitmap对象
         */
        public Builder setShareTransaction(int scene, String type) {
            initSendMessageToWXRequest();
            mSReq.transaction = buildTransaction(type);
            mSReq.message = mWxMediaMessage;
            mSReq.scene = scene;
            return this;
        }

        /**
         * 创建WeChatShareComponent对象，调用
         *
         * @return WeChatShareComponent对象
         * @see #createWeChatShareInstance(Context)
         */
        public WeChatShareComponent build() {
            return WeChatShareComponent.createWeChatShareInstance(mContext);
        }

        /**
         * 初始化WXWebPageObject对象
         */
        private void initWXWebPageObject() {
            if (mWxWebPageObject == null) {
                mWxWebPageObject = new WXWebpageObject();
            }
            Log.w(TAG, "mWxWebPageObject=" + mWxWebPageObject);
        }


        /**
         * 初始化WXMediaMessage对象
         */
        private void initWXMediaMessage() {
            if (mWxMediaMessage == null) {
                initWXWebPageObject();
                mWxMediaMessage = new WXMediaMessage(mWxWebPageObject);
            }
            Log.w(TAG, "mWxMediaMessage=" + mWxMediaMessage);
        }

        /**
         * 初始化WXImageObject
         */
        private void initWXImageObject() {
            if (mWxImageObject == null) {
                mWxImageObject = new WXImageObject();
            }
        }

        /**
         * 使用当前时间创建发送分享消息的 transaction
         *
         * @param type 分享内容的类型
         *             主要有文字text、图片picture、网页webpage等
         */
        private String buildTransaction(final String type) {
            return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
        }

        /**
         * 初始化SendMessageToWX.Req对象
         */
        private void initSendMessageToWXRequest() {
            if (mSReq == null) {
                mSReq = new SendMessageToWX.Req();
            }
            Log.w(TAG, "mSReq=" + mSReq);
        }

        /**
         * 发起发送到微信请求，实现分享功能。
         */
        public void share() {
            build();
            Log.w(TAG, "wxApi=" + wxApi);
            wxApi.sendReq(mSReq);
        }
    }
}