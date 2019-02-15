package com.endymion.collection.apply.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.endymion.collection.R;
import com.endymion.collection.apply.model.entity.MainTask;
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
                .setOnClickListener(R.id.cl_main, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (data.getForwardClass() != null) {
                            mActivity.startActivityForResult(new Intent(mActivity, data.getForwardClass()), 1);
                        } else {
                            data.getCallback().onClick();
                        }
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
