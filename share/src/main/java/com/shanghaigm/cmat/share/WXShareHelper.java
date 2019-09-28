package com.shanghaigm.cmat.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import com.shanghaigm.cmat.http.CmatModel;
import com.shanghaigm.cmat.http.RequestServer;
import com.tencent.mm.opensdk.modelbase.BaseResp;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信分享帮助类，接入时只需要关心以下几个方法/类
 * 分享, share()
 * 接收分享结果信息, 参照示例Activity中的内容{@link com.shanghaigm.cmat.share.ExampleActivity}
 * 涉及分享结果解析的方法有两个：
 * 1.判断是否完成完整分享流程, isShareSuccessful()
 * 2.未完成完整分享流程时，对错误信息的解析, getErrorInfo()
 *
 * Created by Jim. on 2018/7/14.
 */

public class WXShareHelper {
    private static final String TAG = "WXShareHelper";
    private static String URL = getReportUrl();

    /**
     * 分享网页
     *
     * @param sysName 系统名称
     * @param scene   分享场景，0-对话，1-朋友圈，2-收藏
     * @param title   标题
     * @param summary 摘要
     * @param url     网页url
     */
    public static void share(Context mContext, String sysName, int scene, String title, String summary, String url) {
//        mContext.startActivity(new Intent(mContext, ExampleActivity.class));

        WeChatShareComponent.Builder builder = new WeChatShareComponent.Builder(mContext);
        builder.setShareTransaction(scene, "web")
                .setShareContentTitle(title)
                .setShareContentSummary(summary)
                .setShareContentUrl(url)
                .setShareContentImage(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.white));
        builder.share();

        try {
            uploadShareInfo(mContext, sysName, url, new String[]{String.valueOf(scene), title, summary});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分享图片
     *
     * @param scene  分享场景，0-对话，1-朋友圈，2-收藏
     * @param imgUrl 图片URL
     */
    public static void share(Context mContext, String sysName, int scene, String imgUrl) throws Exception {
//        mContext.startActivity(new Intent(mContext, ExampleActivity.class));

        Bitmap bm = BitmapUtils.url2bitmap(imgUrl);
        if (bm != null) {
            Log.w(TAG, "bitmap is not null");
            WeChatShareComponent.Builder builder = new WeChatShareComponent.Builder(mContext);
            builder.setShareTransaction(scene, "img")
                    .setShareImage(bm)
                    .share();
        }

        try {
            uploadShareInfo(mContext, sysName, imgUrl, new String[]{String.valueOf(scene)});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否完成分析
     *
     * @param baseResp 分享结束后从微信返回app时的结果，由EventBus发出
     * @return true 完成分享
     */
    public static boolean isShareSuccessful(BaseResp baseResp) {
        return baseResp.errCode == BaseResp.ErrCode.ERR_OK;
    }

    /**
     * 分享执行结果，成功时不需要调用该方法
     *
     * @param baseResp 分享结束后从微信返回app时的结果，由EventBus发出
     * @return 状态码对应的提示信息
     */
    public static String getErrorInfo(Context mContext, BaseResp baseResp) {
        int result;
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = R.string.errcode_success;
                break;

            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.errcode_cancel;
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = R.string.errcode_deny;
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                result = R.string.errcode_unsupported;
                break;
            case BaseResp.ErrCode.ERR_BAN:
                result = R.string.errcode_ban;
                break;
            default:
                result = R.string.errcode_unknown;
                break;
        }
        return mContext.getString(result);
    }

    /**
     * 上报分享信息
     */
    private static void uploadShareInfo(Context mContext, String sysName, String url, String[] extraInfo) throws Exception {
        String reportUrl = getReportUrl();
        if (TextUtils.isEmpty(reportUrl)) {
            Log.w(TAG, "report url is empty, report end.");
        }
        CmatModel cmatModel = new CmatModel(mContext);
        Map<String, String> header = new HashMap<>();
        header.put("access_token", cmatModel.getAccessToken());
        header.put("buId", cmatModel.getUserName());

        header.put("userCode", cmatModel.getAccountCode());
        header.put("positionCode", cmatModel.getPositionCode());
        header.put("storeCode", cmatModel.getStoreCode());
        header.put("sign", cmatModel.getSignCode());

        Log.w(TAG, "token = " + header.get("access_token"));
        StringBuilder sysNameBuilder = new StringBuilder(sysName);
        sysNameBuilder.append(" - ").append(url);
        for (String item : extraInfo) {
            sysNameBuilder.append(" - ").append(item);
        }
        sysName = sysNameBuilder.toString();
        Map<String, Object> params = new HashMap<>();
        params.put("param", sysName);
        RequestServer server = new RequestServer(mContext, reportUrl, "POST", header, params, true);
        server.setOnServerRequestCompleteListener(new RequestServer.OnServerRequestComplete() {
            @Override
            public void onSuccess(String response) {
                Log.w(TAG, "success, " + response);
            }

            @Override
            public void onFail(int statusCode, String message, String url) {
                Log.w(TAG, "failed, " + message + ", url = " + url);
            }
        });
        server.requestService();
    }

    private static String getReportUrl() {
        if (TextUtils.isEmpty(URL)) {
            try {
                Class config = Class.forName("com.shanghaigm.cmat.model.Config");
                Field urlBase = config.getField("URL_BASE");
                Field urlMainPath = config.getField("URL_MAIN_PATH");
                Log.w(TAG, "获取到的URL_BASE为:" + urlBase.get(null).toString());
                Log.w(TAG, "URL_MAIN_PATH:" + urlMainPath.get(null).toString());
                String url = urlBase.get(null).toString()
                        + urlMainPath.get(null).toString()
                        + "/app/logger/wechat/share";
                Log.w(TAG, "url = " + url);
                return url;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                Log.e(TAG, "获取URL相关字段时，获取Config类失败");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                Log.e(TAG, "未找到Config类中的URL相关字段");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                Log.e(TAG, "访问URL相关字段失败");
            }
            return "";
        } else {
            return URL;
        }
    }
}
