package com.endymion.collection.test.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.endymion.collection.R;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        initView();
    }

    private void initView() {
        ViewPager viewPager = findViewById(R.id.view_pager);
        List<String> dataSource = new ArrayList<>();
        dataSource.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539080916556&di=7467a2f89c29adf8529afc2f0a9347ba&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0107da59be1078a8012075343682ec.jpg%401280w_1l_2o_100sh.jpg");
        dataSource.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539080916556&di=a0e584d701375a49b715bad0e470745c&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201206%2F06%2F20120606175216_jLxBw.thumb.700_0.jpeg");
        dataSource.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539080916555&di=3d29d982114fd45593e4ae53481d62f0&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0191db57f52c52a84a0e282be8bf87.jpg%402o.jpg");
        dataSource.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539080916555&di=63c2eb5bf23573d58b63d122fb82c3f6&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201511%2F07%2F20151107231644_VxMtQ.thumb.700_0.jpeg");
        dataSource.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539080916555&di=422c3e223a6722d3fd8f4d547069667e&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F016a065625c70232f87557019e87f6.jpg%401280w_1l_2o_100sh.jpg");

        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < dataSource.size(); i++) {
            fragments.add(createFragment(dataSource.get(i)));
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(fragmentManager, fragments);
        viewPager.setAdapter(adapter);
    }

    private Fragment createFragment(String s) {
        PhotoFragment fragment = new PhotoFragment();
        fragment.setUrl(s);
        return fragment;
    }
}
