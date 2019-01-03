package com.endymion.collection.test.ui;

import java.util.ArrayList;
import java.util.List;

/**
 * 独立出评价控件的数据逻辑，方便测试。还有一些魔法数字待后续优化
 * Created by Jim Lee on 2018/11/8.
 */
public class CommentViewAgent {
    public static final int STATUS_SHINING = 2;
    public static final int STATUS_HALF_SHINING = 1;
    public static final int STATUS_DARK = 0;

    private int[] mStatus = new int[]{STATUS_DARK, STATUS_DARK, STATUS_DARK, STATUS_DARK, STATUS_DARK};
    private List<int[]> mRectList = new ArrayList<>();
    private boolean canUpdate = true;
    private OnRectInitListener listener;

    public void initRectList(int width, int height) {
        int horizontalPadding = 10;
        int starPadding = 2;

        int widthUnit = (width - horizontalPadding * 2) / 5;
        int verticalPadding = (height - (widthUnit - starPadding * 2)) / 2;

        if (listener != null) {
            listener.initRect(5);
        }

        for (int i = 0; i < 5; i++) {
            int left = widthUnit * i;
            int l = left + starPadding;
//            int t = verticalPadding;
            int r = left + widthUnit - starPadding;
            int b = height - verticalPadding;
            mRectList.add(new int[]{l, verticalPadding, r, b});
            if (listener != null) {
                listener.initRectChild(i, l, verticalPadding, r, b);
            }
        }
    }

    public void setScore(int score) {
        for (int i = 0; i < mStatus.length; i++) {
            int temp = (i + 1) * 2;
            if (score >= temp) {
                mStatus[i] = STATUS_SHINING;
            } else if (score == temp - 1) {
                mStatus[i] = STATUS_HALF_SHINING;
            } else {
                mStatus[i] = STATUS_DARK;
            }
        }
    }

    public int getScore() {
        int score = 0;
        for (int status : mStatus) {
            if (status == STATUS_SHINING) {
                score += 2;
            } else if (status == STATUS_HALF_SHINING) {
                score += 1;
                break;
            } else {
                break;
            }
        }
        return score;
    }

    public int[] getStatus() {
        return mStatus;
    }

    public void setCanUpdate(boolean canUpdate) {
        this.canUpdate = canUpdate;
    }

    public boolean updateStatus(float touchX) {
        boolean isStatusChanged = false;

        if (canUpdate) {
            for (int i = 0; i < mStatus.length; i++) {
                int left = mRectList.get(i)[0];// mRectArray[i].left;
                int right = mRectList.get(i)[2];// mRectArray[i].right;
                int center = (left + right) / 2;
                if (touchX > right && mStatus[i] != STATUS_SHINING) {
                    mStatus[i] = STATUS_SHINING;
                    isStatusChanged = true;
                } else if (center < touchX && touchX < right && mStatus[i] != STATUS_SHINING) {
                    mStatus[i] = STATUS_SHINING;
                    isStatusChanged = true;
                } else if (left < touchX && touchX < center && mStatus[i] != STATUS_HALF_SHINING) {
                    mStatus[i] = STATUS_HALF_SHINING;
                    isStatusChanged = true;
                } else if (touchX < left) {
                    if (i == 0 && mStatus[0] != STATUS_HALF_SHINING) {
                        mStatus[0] = STATUS_HALF_SHINING;
                        isStatusChanged = true;
                    } else if (mStatus[i] != STATUS_DARK) {
                        mStatus[i] = STATUS_DARK;
                        isStatusChanged = true;
                    }
                }
            }
        }

        return isStatusChanged;
    }

    public void setListener(OnRectInitListener listener) {
        this.listener = listener;
    }

    public interface OnRectInitListener {
        void initRect(int size);

        void initRectChild(int position, int left, int top, int right, int bottom);
    }
}
