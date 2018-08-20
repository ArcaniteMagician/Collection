package com.endymion.common.ui.widget.refresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.endymion.common.R;

/**
 * Created by Administrator on 2018/1/19.
 */

public class ClassicsHeaderView implements HeaderView {
    private Context mContext;
    private View mParent;

    private TextView mTextView;
    private ImageView mFinishView;

    private int tipIdPullToRefresh;
    private int tipIdLoosenToRefresh;
    private int tipIdRefreshing;
    private int tipIdRefreshingSuccess;
    private int tipIdRefreshingFailure;

    public ClassicsHeaderView(Context context, @Nullable AttributeSet attrs) {
        mContext = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RefreshLayout);
        tipIdPullToRefresh = array.getResourceId(R.styleable.RefreshLayout_pull_to_refresh, R.string.pull_to_refresh);
        tipIdLoosenToRefresh = array.getResourceId(R.styleable.RefreshLayout_loosen_to_refresh, R.string.loosen_to_refresh);
        tipIdRefreshing = array.getResourceId(R.styleable.RefreshLayout_refreshing, R.string.refreshing);
        tipIdRefreshingSuccess = array.getResourceId(R.styleable.RefreshLayout_refreshing_success, R.string.refreshing_success);
        tipIdRefreshingFailure = array.getResourceId(R.styleable.RefreshLayout_refreshing_failure, R.string.refreshing_failure);
        array.recycle();
    }

    @Override
    public void begin() {
        reset();
    }

    @Override
    public void progress(float progress) {
        if (progress >= 1f) {
            mTextView.setText(tipIdLoosenToRefresh);
        } else {
            mTextView.setText(tipIdPullToRefresh);
        }
    }

    @Override
    public void loading() {
        mTextView.setText(tipIdRefreshing);
    }

    @Override
    public void reset() {
        mFinishView.setVisibility(View.INVISIBLE);
        mTextView.setText(tipIdPullToRefresh);
    }

    @Override
    public View getView() {
        if (mParent == null) {
            mParent = LayoutInflater.from(mContext).inflate(R.layout.refresh_head_classics, null, false);
            mTextView = (TextView) mParent.findViewById(R.id.txt_tip);
            mFinishView = (ImageView) mParent.findViewById(R.id.finish);
        }
        return mParent;
    }

    @Override
    public void showPause(boolean success) {
        mFinishView.setVisibility(View.VISIBLE);
        if (success) {
            mTextView.setText(tipIdRefreshingSuccess);
        } else {
            mTextView.setText(tipIdRefreshingFailure);
        }
    }

    @Override
    public boolean isPauseTime() {
        return mFinishView.getVisibility() == View.VISIBLE;
    }

    @Override
    public long getPauseMillTime() {
        return 0;
    }
}
