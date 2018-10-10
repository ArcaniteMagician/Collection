package com.endymion.collection.test.ui;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jim Lee on 2018/10/9.
 */
public class BannerAdapter extends PagerAdapter
        implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private ViewPager viewPager;

    private final static int PAGE = 0x03;
    private final static int INDEX = 1;
    private final static int START_INDEX = 0;

    private List<String> dataSource;
    private ArrayList<ImageView> views;
    private Context context;
    // 记录真正的位置
    private int currentPage;

    private ItemClickListener itemClickListener;

    public BannerAdapter(Context context, ViewPager viewPager, List<String> dataSource) {
        this.context = context;
        this.dataSource = dataSource;
        this.viewPager = viewPager;
        viewPager.clearOnPageChangeListeners();
        viewPager.addOnPageChangeListener(this);
        if (null == dataSource || dataSource.isEmpty()) return;
        initView(false);
        setDataToView();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void nextPage() {
        if (null == dataSource || dataSource.isEmpty()) return;
        // 因为位置始终为1 那么下一页就始终为2
        viewPager.setCurrentItem(2, true);
    }

    /**
     * 初始化图形控件
     * 为image填充数据
     */
    private void initView(boolean isScale) {
        views = new ArrayList<>();
        if (dataSource.size() == 1) {
            views.add(getImageView());
            return;
        }
        for (int i = 0; i < PAGE; i++) {
            views.add(getImageView());
        }
    }

    private void setDataToView() {
        int size = dataSource.size();
        if (size == 1) {
            Glide.with(context).load(dataSource.get(0)).into(views.get(0));
            return;
        }
        for (int i = 0; i < PAGE; i++) {
            int index = 0;
            if (0 == i) {
                index = size - 1;
            } else {
                index = i - 1;
            }
            Glide.with(context).load(dataSource.get(index)).into(views.get(i));
        }
    }

    public void showIndex(int index) {
        if (null == dataSource || dataSource.isEmpty()) return;
        if (index == dataSource.size()) {
            currentPage = 0;
        } else {
            currentPage = index;
        }
        if (dataSource.size() == 1) {
            Glide.with(context).load(dataSource.get(0)).into(views.get(0));
        } else {
            indexChange();
        }
    }

    /**
     * get imageView
     */
    private ImageView getImageView() {
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setClickable(true);
        imageView.setOnClickListener(this);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public int getCount() {
        return (null == views) ? 0 : views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = views.get(position);
        if (null != imageView.getParent()) {
            ViewGroup viewGroup = (ViewGroup) imageView.getParent();
            viewGroup.removeView(imageView);
        }
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (dataSource.size() == 1) {
            return;
        }
        if (0 != positionOffset) return;

        if (position == INDEX) {
            return;
        }
        if (position > INDEX) {
            currentPage++;
        } else {
            currentPage--;
        }
        if (currentPage == -INDEX) {
            currentPage = dataSource.size() - INDEX;
        } else if (currentPage == dataSource.size()) {
            currentPage = START_INDEX;
        }
        indexChange();
    }

    private void indexChange() {
        if (currentPage == START_INDEX) {
            Glide.with(context).load(dataSource.get(dataSource.size() - 1)).into(views.get(0));
        } else {
            Glide.with(context).load(dataSource.get(currentPage - 1)).into(views.get(0));
        }
        Glide.with(context).load(dataSource.get(currentPage)).into(views.get(1));
        if (currentPage == dataSource.size() - 1) {
            Glide.with(context).load(dataSource.get(0)).into(views.get(2));
        } else {
            if (currentPage == 0 && dataSource.size() == 2) {
                Glide.with(context).load(dataSource.get(dataSource.size() - 1)).into(views.get(2));
            } else {
                Glide.with(context).load(dataSource.get(currentPage + 1)).into(views.get(2));
            }
        }
        viewPager.setCurrentItem(1, false);
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onClick(View v) {
        if (null != itemClickListener) itemClickListener.onItemClick(currentPage);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public interface ItemClickListener {
        void onItemClick(int index);
    }
}
