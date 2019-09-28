package com.shanghaigm.cmat.share;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelbase.BaseResp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ExampleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);// 用于微信分享结果通知
    }

    /**
     * 接收到微信分享返回结果，会在这个方法内接收
     *
     * @param baseResp 微信的返回信息
     */
    @Subscribe
    public void onEventMainThread(BaseResp baseResp) {
        Log.w("WXShareHelper", baseResp.errCode + baseResp.errStr);
        if (WXShareHelper.isShareSuccessful(baseResp)) {
            Log.w("WXShareHelper", " share module success");
        } else {
            Toast.makeText(this, WXShareHelper.getErrorInfo(this, baseResp), Toast.LENGTH_SHORT).show();
            Log.w("WXShareHelper", " share module failed");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeStickyEvent(this);
    }
}
