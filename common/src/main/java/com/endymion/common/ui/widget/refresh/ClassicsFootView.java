package com.endymion.common.ui.widget.refresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.endymion.common.R;


/**
 * Created by Administrator on 2018/1/21.
 */

public class ClassicsFootView implements FootView {
    private Context mContext;
    private View mParent;

    private TextView mTextView;
    private ImageView mFinishView;

    public ClassicsFootView(Context context) {
        mContext = context;
    }

    @Override
    public void begin() {
        reset();
    }

    @Override
    public void progress(float progress) {
        if (progress >= 1f) {
            mTextView.setText(R.string.loosen_to_load_more);
        } else {
            mTextView.setText(R.string.pull_to_load_more);
        }
    }

    @Override
    public void loading() {
        mTextView.setText(R.string.loading);
    }

    @Override
    public void reset() {
        mFinishView.setVisibility(View.INVISIBLE);
        mTextView.setText(R.string.load_more);
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
            mTextView.setText(R.string.load_more_success);
        } else {
            mTextView.setText(R.string.load_more_failure);
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
