package com.endymion.collection.test.ui.activity;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.endymion.collection.R;

/**
 * Created by Jim Lee on 2018/9/30.
 */
public class AlignedTextView extends LinearLayout {
    private int marginAndPaddingOfLeftRight = dip2px(40);//文本域左右已使用的间距值
    private int lineSpacingExtra = dip2px(5);//文本行距
    private int offset = dip2px(0);//偏差值,用作于误差调整
    private int textSize = dip2px(14);//字体大小
    private int textColor = Color.BLACK;// 字体颜色

    private LayoutParams layoutParamsMW;

    public AlignedTextView(Context context) {
        this(context, null);
    }

    public AlignedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlignedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AlignedTextView);
            marginAndPaddingOfLeftRight = ta.getDimensionPixelSize(R.styleable.AlignedTextView_atv_marginAndPaddingOfLeftRight, marginAndPaddingOfLeftRight);
            lineSpacingExtra = ta.getDimensionPixelSize(R.styleable.AlignedTextView_atv_lineSpacingExtra, lineSpacingExtra);
            offset = ta.getDimensionPixelSize(R.styleable.AlignedTextView_atv_offset, offset);
            textSize = ta.getDimensionPixelSize(R.styleable.AlignedTextView_atv_textSize, textSize);
            textColor = ta.getColor(R.styleable.AlignedTextView_atv_textColor, textColor);
            ta.recycle();
        }
        init();
    }

    private void init() {
        layoutParamsMW = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParamsMW);
        setOrientation(VERTICAL);
        layoutParamsMW.bottomMargin = lineSpacingExtra;
    }

    public void setText(int resId) {
        setText(getResources().getString(resId));
    }

    public void setText(String content) {
        if (TextUtils.isEmpty(content)) {
            return;
        }
        content = content.trim();
        int usedWidth = marginAndPaddingOfLeftRight + offset;
        int remainWidth = getContext().getResources().getDisplayMetrics().widthPixels - usedWidth;
        int lineWordNum = remainWidth / textSize;
        if (lineWordNum <= 0) {
            return;
        }
        int contentLength = content.length();
        if (contentLength <= lineWordNum) {
            addOneLineContent(content);
            return;
        }

        int line = contentLength / lineWordNum + 1;
        for (int x = 1; x <= line; x++) {
            String lineContent;
            if (x == 1) {
                lineContent = content.substring(0, lineWordNum);
            } else if (x == line) {
                lineContent = content.substring(lineWordNum * (x - 1), content.length());
            } else {
                lineContent = content.substring(lineWordNum * (x - 1), lineWordNum * x);
            }
            addOneLineContent(lineContent);
        }
    }

    private void addOneLineContent(String content) {
        TextView lineText = new TextView(getContext());
        lineText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        lineText.setTextColor(textColor);
        lineText.setText(content);
        lineText.setLayoutParams(layoutParamsMW);
        addView(lineText);
    }

    private int dip2px(int dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }
}
