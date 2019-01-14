package com.endymion.collection.apply.ui.adapter;

/**
 * Created by Jim Lee on 2019/1/3.
 */
public interface ItemTouchHelperAdapter {
    // 数据交换
    void onItemDrag(int fromPosition, int toPosition);

    // 删除
    void onItemDelete(int position);
}
