package com.endymion.common.ui.widget.refresh;

import android.content.Context;
import android.support.annotation.IntDef;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.endymion.common.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2018/1/24.
 */

public class RotateHeaderView implements HeaderView {
    public static final int WHITE = 0;
    public static final int BLUE = 1;
    public static final int DURATION = 2000;
    private Context mContext;
    private View mParent;
    private LoadingView mLoadingView;
    private TextView mTextView;
    private ImageView mFinishView;
    private RotateAnimatorToY mYAnimation;
    private boolean isSuccess;

    public RotateHeaderView(Context context) {
        mContext = context;
        mYAnimation = new RotateAnimatorToY();
        mYAnimation.setDuration(DURATION);
    }

    @Override
    public void begin() {
        reset();
    }

    @Override
    public void progress(float progress) {
        if (progress >= 1f) {
            mTextView.setText(R.string.loosen_to_refresh);
        } else {
            mTextView.setText(R.string.pull_to_refresh);
        }
    }

    @Override
    public void loading() {
        mLoadingView.setVisibility(View.VISIBLE);
        mFinishView.setVisibility(View.INVISIBLE);
        mTextView.setText(R.string.refreshing);
    }

    @Override
    public void reset() {
        mFinishView.clearAnimation();
        mFinishView.setVisibility(View.INVISIBLE);
        mLoadingView.setVisibility(View.INVISIBLE);
        mTextView.setText(R.string.pull_to_refresh);
    }

    @Override
    public View getView() {
        if (mParent == null) {
            mParent = LayoutInflater.from(mContext).inflate(R.layout.refresh_head_rotate, null, false);
            mLoadingView = (LoadingView) mParent.findViewById(R.id.loading);
            mTextView = (TextView) mParent.findViewById(R.id.txt_tip);
            mFinishView = (ImageView) mParent.findViewById(R.id.finish);
            mFinishView.setAnimation(mYAnimation);
        }
        return mParent;
    }

    public void setStyle(@LoadingColor int style) {
        getView();
        if (style == WHITE) {
            mFinishView.setImageResource(R.mipmap.refresh_connected);
            mLoadingView.setLoadingBitmap(R.mipmap.refresh_loading_white);
            mTextView.setTextColor(mContext.getResources().getColor(android.R.color.white));
        } else {
            mFinishView.setImageResource(R.mipmap.refresh_connected_blue);
            mLoadingView.setLoadingBitmap(R.mipmap.refresh_loading_blue);
            mTextView.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        }
    }

    @Override
    public void showPause(boolean success) {
        mLoadingView.setVisibility(View.INVISIBLE);
        if (success) {
            isSuccess = true;
            mFinishView.setVisibility(View.VISIBLE);
            mFinishView.startAnimation(mYAnimation);
            mTextView.setText(R.string.refreshing_success);
        } else {
            isSuccess = false;
            mTextView.setText(R.string.refreshing_failure);
        }
    }

    @Override
    public boolean isPauseTime() {
        return mFinishView.getVisibility() == View.VISIBLE;
    }

    @Override
    public long getPauseMillTime() {
        if (isSuccess) {
            return mYAnimation.getDuration();
        } else {
            return 0;
        }
    }

    @IntDef({WHITE, BLUE})
    @Retention(RetentionPolicy.SOURCE)
    @interface LoadingColor {
    }
}
