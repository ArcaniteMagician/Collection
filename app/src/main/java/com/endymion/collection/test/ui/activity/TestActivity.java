package com.endymion.collection.test.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.endymion.collection.CollectionApplication;
import com.endymion.collection.R;
import com.endymion.collection.apply.presenter.MultiPresenter;
import com.endymion.collection.apply.ui.view.MultiViewBridge;
import com.endymion.common.ui.activity.BasePresenterActivity;
import com.endymion.common.util.CommonUtils;
import com.endymion.common.util.TimeMillisUtils;

import java.util.TimeZone;

public class TestActivity extends BasePresenterActivity<MultiPresenter> implements MultiViewBridge {
    private static final String TAG = "TestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String date = TimeMillisUtils.dateLong2StrGMT8(TestActivity.this, System.currentTimeMillis(), TimeMillisUtils.TIME_FORMAT);
                String date1 = TimeMillisUtils.dateLong2StrGMT8(TestActivity.this, TimeMillisUtils.getInstance().getServerTimeMillis(), TimeMillisUtils.TIME_FORMAT);
                String date2 = TimeMillisUtils.dateLong2StrDefault(TestActivity.this, TimeMillisUtils.getInstance().getServerTimeMillis(), TimeMillisUtils.TIME_FORMAT);
                Log.w(TAG, "System GMT+08:00 is " + date);
                Log.w(TAG, "Server GMT+08:00 is " + date1);
                Log.w(TAG, "Server " + TimeZone.getDefault() + " is " + date2);
            }
        }, 3000);

        mPresenter.getTestPresenter().test();
        mPresenter.getTestPresenter().allMethod();
        mPresenter.getSecondPresenter().allMethod();

        ExpandableTextView txt = findViewById(R.id.id_text);

        try {
            txt.initWidth(CommonUtils.getScreenSize(this)[CommonUtils.INDEX_WIDTH] - CommonUtils.dip2px(this, 30));
        } catch (Exception e) {
            e.printStackTrace();
        }
        txt.setCloseText("茫茫的长白大山，浩瀚的原始森林，大山脚下，原始森林环抱中散落着几十户人家的" +
                "一个小山村，茅草房，对面炕，烟筒立在屋后边。在村东头有一个独立的房子，那就是青年点，" +
                "窗前有一道小溪流过。学子在这里吃饭，由这里出发每天随社员去地里干活。干的活要么上山伐" +
                "树，抬树，要么砍柳树毛子开荒种地。在山里，可听那吆呵声：“顺山倒了！”放树谨防回头棒！" +
                "树上的枯枝打到别的树上再蹦回来，这回头棒打人最厉害。");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.test_activity_main;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new MultiPresenter();
    }

    @Override
    public void extraMethod() {
        Log.w(TAG, "extraMethod()");
    }

    @Override
    public void secondViewTest() {
        Log.w(TAG, "secondTest()");
    }

    public void exit(View view) {
        CollectionApplication.getInstance().exit();
    }
}
