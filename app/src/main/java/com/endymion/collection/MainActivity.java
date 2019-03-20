package com.endymion.collection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.endymion.collection.apply.model.entity.MainTask;
import com.endymion.collection.apply.ui.adapter.MainTaskAdapter;
import com.endymion.collection.test.ui.GalleryActivity;
import com.endymion.collection.test.ui.activity.TestActivity;
import com.endymion.collection.test.ui.activity.contact.ContactActivity;
import com.endymion.collection.test.ui.activity.pager.ViewPagerActivity;
import com.endymion.common.component.ComponentProxyFactory;
import com.endymion.common.ui.activity.BaseActivity;
import com.endymion.common.ui.adapter.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    RecyclerView mRecyclerView;
    private List<MainTask> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private void init() {
        Log.w(TAG, "init()");
        mList = new ArrayList<>();
        mList.add(new MainTask("UtilTests", "对Common模块中的Util类进行测试").setForwardClass(TestActivity.class));
        mList.add(new MainTask("Gallery", "查看照片").setForwardClass(GalleryActivity.class));
        mList.add(new MainTask("Contact", "读取通讯录").setForwardClass(ContactActivity.class));
        mList.add(new MainTask("Weather", "天气组件跳转").setComponentIndex(ComponentProxyFactory.WEATHER));
        mList.add(new MainTask("ViewPager", "Test for viewPager with activity page.").setForwardClass(ViewPagerActivity.class));

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL));
        mRecyclerView.setAdapter(new MainTaskAdapter(this, mList, R.layout.main_item_task));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP, ItemTouchHelper.START) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w("TEST", "onResume");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.w("TEST", "onActivityResult");
    }
}
