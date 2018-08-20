package com.endymion.common.ui.widget.refresh;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.endymion.common.R;
import com.endymion.common.util.CommonUtils;


/**
 * Created by Administrator on 16/6/10.
 */
public class LoadingView extends View {
    private static final int PER_ANGLE = 30;
    private static final int DURATION = 100;//ms
    private static final int FULL_ANGLE = 360;
    private Bitmap bitmap;
    private Paint mPaint;
    private float angle = 0;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            angle += PER_ANGLE;
            if (angle >= FULL_ANGLE) {
                angle = angle % FULL_ANGLE;
            }
            postInvalidate();
            postDelayed(runnable, DURATION);
        }
    };

    public LoadingView(Context context) {
        this(context, null);
    }

    public void setLoadingBitmap(int resId) {
        bitmap = BitmapFactory.decodeResource(getResources(), resId);
    }

    public void setLoadingScaleBitmap(int resId, int width, int height) {
        bitmap = BitmapFactory.decodeResource(getResources(), resId);
        bitmap = Bitmap.createScaledBitmap(bitmap, CommonUtils.dip2px(getContext(), width), CommonUtils.dip2px(getContext(), height), true);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.refresh_loading_blue);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        canvas.rotate(angle, getWidth() / 2, getHeight() / 2);
//        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        canvas.drawBitmap(bitmap, getWidth() / 2 - bitmap.getWidth() / 2, getHeight() / 2 - bitmap.getHeight() / 2, mPaint);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        postDelayed(runnable, DURATION);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(runnable);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (bitmap != null) {
            setMeasuredDimension(bitmap.getWidth(), bitmap.getHeight());
        } else {
            setMeasuredDimension(0, 0);
        }

    }
}
