package com.endymion.collection.apply.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.endymion.collection.R;
import com.endymion.collection.apply.model.entity.MainTask;
import com.endymion.common.component.ComponentProxyFactory;
import com.endymion.common.ui.adapter.BaseRecyclerViewAdapter;
import com.endymion.common.ui.adapter.RecyclerViewHolder;

import java.util.List;

/**
 * Created by Jim on 2018/07/30.
 */

public class MainTaskAdapter extends BaseRecyclerViewAdapter<MainTask> implements ItemTouchHelperAdapter {

    public MainTaskAdapter(Activity activity, List<MainTask> dataList, int layoutId) {
        super(activity, dataList, layoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, final MainTask data) {
        holder.setText(R.id.txt_title, data.getTitle())
                .setText(R.id.txt_description, data.getDescription())
                .setOnClickListener(R.id.cl_main, v -> {
                    if (data.getForwardClass() != null) {
                        mActivity.startActivityForResult(new Intent(mActivity, data.getForwardClass()), 1);
                        // 以下四行为多任务显示方式，适用于打开不同书籍或文档、重要人物的聊天对话的情况
//                        Intent intent = new Intent(mActivity.getApplication(), data.getForwardClass());
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//                        mActivity.startActivity(intent);
                    } else {
                        ComponentProxyFactory.getInstance().getComponentProxy(data.getComponentIndex()).start(mActivity);
                    }
                });
    }

    @Override
    public void onItemDrag(int fromPosition, int toPosition) {
        Log.w("RecyclerViewTest", "onItemDrag");
    }

    @Override
    public void onItemDelete(int position) {
        Log.w("RecyclerViewTest", "onItemDelete");
    }
}
