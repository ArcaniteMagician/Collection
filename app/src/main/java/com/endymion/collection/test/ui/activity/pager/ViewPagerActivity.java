package com.endymion.collection.test.ui.activity.pager;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.endymion.collection.R;
import com.endymion.collection.test.ui.activity.TestActivity;
import com.endymion.collection.test.ui.activity.contact.ContactActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {
    private List<View> views = new ArrayList<>();
    private LocalActivityManager manager;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);//必须写上这一行代码，不然会报错

        viewPager = findViewById(R.id.view_pager);
        Intent intent1 = new Intent(getApplicationContext(), TestActivity.class);// 这个类的第一个参数是上下文，第二个参数是你需要转化的Activity
        views.add(manager.startActivity("TestActivity", intent1).getDecorView());// 将Activity转化为View然后放入View集合
        Intent intent2 = new Intent(getApplicationContext(), ContactActivity.class);
        views.add(manager.startActivity("ContactActivity", intent2).getDecorView());
        viewPager.setAdapter(new MyAdapter());
    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = views.get(position);
            container.addView(v);

            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View v = views.get(position);
            container.removeView(v);
        }

    }
}
