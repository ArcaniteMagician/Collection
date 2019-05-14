package com.endymion.collection.test.ui.activity.camera;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Jim Lee on 2019/3/20.
 */
public class TouchTestView extends View {
    public TouchTestView(Context context) {
        super(context);
    }

    public TouchTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.w("CJT_onTouchEvent", "point count = " + event.getPointerCount());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                Log.w("CJT_onTouchEvent", "point count = " + event.getPointerCount());
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }
}
