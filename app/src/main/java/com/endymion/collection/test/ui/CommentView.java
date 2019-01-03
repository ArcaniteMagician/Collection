package com.endymion.collection.test.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.endymion.collection.R;

/**
 * 评价控件
 * 需要设置成clickable=true才能使得MOVE事件生效
 * Created by Jim Lee on 2018/11/8.
 */
public class CommentView extends View {
    private static final int DEFAULT_WIDTH = 200;
    private static final int DEFAULT_HEIGHT = 40;

    private static int DEFAULT_RES_ID_SHINING = R.drawable.ic_comment_star_shining;
    private static int DEFAULT_RES_ID_HALF_SHINING = R.drawable.ic_comment_star_shining_half;
    private static int DEFAULT_RES_ID_DARK = R.drawable.ic_comment_star_dark;

    private Drawable shiningStar;
    private Drawable halfShiningStar;
    private Drawable darkStar;
    private CommentViewAgent commentViewAgent;

    private Rect[] mRectArray;

    public CommentView(Context context) {
        super(context);
        init();
    }

    public CommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setScore(int score) {
        commentViewAgent.setScore(score);
        invalidate();
    }

    public int getScore() {
        return commentViewAgent.getScore();
    }

    private void init() {
        shiningStar = ContextCompat.getDrawable(getContext(), DEFAULT_RES_ID_SHINING);
        halfShiningStar = ContextCompat.getDrawable(getContext(), DEFAULT_RES_ID_HALF_SHINING);
        darkStar = ContextCompat.getDrawable(getContext(), DEFAULT_RES_ID_DARK);

        commentViewAgent = new CommentViewAgent();
        commentViewAgent.setListener(new CommentViewAgent.OnRectInitListener() {
            @Override
            public void initRect(int size) {
                mRectArray = new Rect[size];
            }

            @Override
            public void initRectChild(int position, int left, int top, int right, int bottom) {
                mRectArray[position] = new Rect(left, top, right, bottom);
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < commentViewAgent.getStatus().length; i++) {
            // 绘制图标
            switch (commentViewAgent.getStatus()[i]) {
                case CommentViewAgent.STATUS_DARK:
                    darkStar.setBounds(mRectArray[i]);
                    darkStar.draw(canvas);
                    break;

                case CommentViewAgent.STATUS_HALF_SHINING:
                    halfShiningStar.setBounds(mRectArray[i]);
                    halfShiningStar.draw(canvas);
                    break;

                case CommentViewAgent.STATUS_SHINING:
                    shiningStar.setBounds(mRectArray[i]);
                    shiningStar.draw(canvas);
                    break;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = (int) (DEFAULT_WIDTH * 1.5);
        if (MeasureSpec.UNSPECIFIED != MeasureSpec.getMode(widthMeasureSpec)) {
            width = Math.min(width, MeasureSpec.getSize(widthMeasureSpec));
        }

        int height = (int) (DEFAULT_HEIGHT * 1.5);
        if (MeasureSpec.UNSPECIFIED != MeasureSpec.getMode(heightMeasureSpec)) {
            height = Math.min(height, MeasureSpec.getSize(heightMeasureSpec));
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        commentViewAgent.initRectList(w, h);
    }

    private int dp2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
                updateStatus(event);
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setCanUpdate(boolean canUpdate) {
        commentViewAgent.setCanUpdate(canUpdate);
    }

    private void updateStatus(MotionEvent event) {
        if (commentViewAgent.updateStatus(event.getX())) {
            invalidate();
        }
    }
}
