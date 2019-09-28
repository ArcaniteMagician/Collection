package com.endymion.collection.test.ui.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.endymion.ajchatui.TestCode;
import com.endymion.collection.CollectionApplication;
import com.endymion.collection.R;
import com.endymion.collection.apply.presenter.MultiPresenter;
import com.endymion.collection.apply.ui.view.MultiViewBridge;
import com.endymion.collection.test.model.Occupation;
import com.endymion.common.ui.activity.BasePresenterActivity;
import com.endymion.common.util.CommonUtils;
import com.endymion.common.util.TimeMillisUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

public class TestActivity extends BasePresenterActivity<MultiPresenter> implements MultiViewBridge {
    private static final String TAG = "TestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initDataTest();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.test_activity_main;
    }

    private void initDataTest() {
        List<Occupation> originList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Occupation item = new Occupation("" + (i + 1), "origin" + (new Random().nextBoolean() ? "A" : "B"));
            originList.add(item);
        }
        Log.w("REPLACE_TEST", originList.toString());

        SparseArray<Occupation> listA = new SparseArray<>();
        for (int i = originList.size() - 1; i > -1; i--) {
            Occupation item = originList.get(i);
            if (item.getName().contains("A")) {
                listA.put(i, item);
                originList.remove(item);
            }
        }

        // 实际应用场景为异步处理，异步方法受保护，不能增加判断条件
        // 这里为了测试方便使用同步方法
        for (Occupation item : originList) {
            item.setName(item.getName().replace("B", "C"));
        }

        for (int i = 0; i < listA.size(); i++) {
            int key = listA.keyAt(i);
            originList.add(key, listA.get(key));
        }
        Log.w("REPLACE_TEST", originList.toString());
    }

    private void init() {
        RadioGroup radioGroup = findViewById(R.id.rg);
        for (int i = 0; i < 5; i++) {
            RadioButton radioButton = (RadioButton) LayoutInflater.from(this).inflate(R.layout.item_radio_button, radioGroup, false);

            radioButton.setText("TEST" + i);
            radioGroup.addView(radioButton);
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String date = TimeMillisUtils.dateLong2StrGMT8(TestActivity.this, System.currentTimeMillis(), TimeMillisUtils.TIME_FORMAT);
                String date1 = TimeMillisUtils.dateLong2StrGMT8(TestActivity.this, TimeMillisUtils.getInstance().getServerTimeMillis(), TimeMillisUtils.TIME_FORMAT);
                String date2 = TimeMillisUtils.dateLong2StrDefault(TestActivity.this, TimeMillisUtils.getInstance().getServerTimeMillis(), TimeMillisUtils.TIME_FORMAT);
                Log.w(TAG, "System GMT+08:00 is " + date);
                Log.w(TAG, "Server GMT+08:00 is " + date1);
                Log.w(TAG, "Server " + TimeZone.getDefault() + " is " + date2);
            }
        }, 3000);

        mPresenter.getTestPresenter().test();
        mPresenter.getTestPresenter().allMethod();
        mPresenter.getSecondPresenter().allMethod();

        ExpandableTextView txt = findViewById(R.id.id_text);

        try {
            txt.initWidth(CommonUtils.getScreenSize(this)[CommonUtils.INDEX_WIDTH] - CommonUtils.dip2px(this, 30));
        } catch (Exception e) {
            e.printStackTrace();
        }
        txt.setCloseText("茫茫的长白大山，浩瀚的原始森林，大山脚下，原始森林环抱中散落着几十户人家的" +
                "一个小山村，茅草房，对面炕，烟筒立在屋后边。在村东头有一个独立的房子，那就是青年点，" +
                "窗前有一道小溪流过。学子在这里吃饭，由这里出发每天随社员去地里干活。干的活要么上山伐" +
                "树，抬树，要么砍柳树毛子开荒种地。在山里，可听那吆呵声：“顺山倒了！”放树谨防回头棒！" +
                "树上的枯枝打到别的树上再蹦回来，这回头棒打人最厉害。");
        txt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(TestActivity.this, "长按事件", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        final TextView textView = findViewById(R.id.tv_duiqi);
        textView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (textView.getText().length() < 2) {
                    return true;  //字符个数小于2，没有对齐的必要
                }
                int width = textView.getWidth();
                int number;

                Paint p = textView.getPaint();
                float fisrtLength = p.measureText(firstKG, 0, 1);
                float secondLength = p.measureText(secondKG, 0, 1);
                int sizeCha = (int) (fisrtLength - secondLength);//基于当前的Textsize，算出两种空格的长度和差值
                number = textView.getText().length();
                StringBuilder stringBuilderNullFirst = new StringBuilder();
                StringBuilder stringBuilderNullSecond = new StringBuilder();
                StringBuilder stringBuilderValid = new StringBuilder();
                for (int k = 0; k < number; k++) {
                    if (textView.getText().charAt(k) == '\u00A0') {
                        stringBuilderNullFirst.append(textView.getText().charAt(k));
                    } else if (textView.getText().charAt(k) == '\u2009') {
                        stringBuilderNullSecond.append(textView.getText().charAt(k));
                    } else {
                        stringBuilderValid.append(textView.getText().charAt(k));
                    }
                }//计算当前文本所有字符的数量并归类，主要针对重新设置size导致的重绘
                float checkSum = stringBuilderNullFirst.length() * fisrtLength + stringBuilderNullSecond.length() * secondLength + p.measureText(stringBuilderValid.toString(), 0, stringBuilderValid.length());//算出按照目前的文字和size，总长度会是多少
                if (checkSum > width) {
                    textView.setText(stringBuilderValid.toString());
                    /*因为size的增加，导致View容不下字，重新分配空格*/
                } else if (checkSum == width) {
                    /*完美显示，直接返回绘制*/
                    return true;
                } else {
                    number = stringBuilderValid.length();
                    if ((width - checkSum) > (sizeCha * (number - 1))) {
                        if (stringBuilderNullSecond.length() != 0) {
                            /*总余量大于每个缝隙的差值，且存在小空格,重置*/
                            textView.setText(stringBuilderValid.toString());
                        } else {
                            /*只有大空格的情况，总余量大于每个缝隙插一个小空格,重置*/
                            if ((width - checkSum) > (secondLength * (number - 1))) {
                                textView.setText(stringBuilderValid.toString());
                            } else {
                                return true;
                            }
                        }
                    } else {
                        return true;
                    }
                }
                number = textView.getText().length();

                int rest = width - (int) p.measureText(textView.getText().toString(), 0, textView.getText().length());
                if (rest < 0) {
                    /*不加空格的情况下，字号超过组件需要换行了，本方法不生效*/
                    return true;
                }
                int restPer = rest / (number - 1);
                int secondPer = restPer / (int) secondLength;
                int secondLeft = restPer % (int) secondLength;//先用小字号空格填缝隙
                int firstPer = 0;
                if (sizeCha != 0) {
                    firstPer = secondLeft / sizeCha;
                }
                if (secondPer > firstPer) {
                    secondPer -= firstPer;
                } else {
                    firstPer = secondPer;
                    secondPer = 0;
                }//根据计算剩下的空间，将相应的小空格替换为大空格进一步缩小误差
                StringBuilder stringBuilder = new StringBuilder();
                for (int j = 0; j < number; j++) {
                    stringBuilder.append(textView.getText().charAt(j));
                    if (j == number - 1) {
                        break;
                    } else {
                        for (int i = 0; i < firstPer; i++) {
                            stringBuilder.append("\u00A0");
                        }
                        for (int n = 0; n < secondPer; n++) {
                            stringBuilder.append("\u2009");
                        }
                    }
                }//重设我们的文本，完成！
                textView.setText(stringBuilder.toString());
                return true;
            }
        });

        AlignedTextView alignedTextView = findViewById(R.id.atv);
        alignedTextView.setText("<string name=\"text\">时间改变着一切，一切改变着我们。原先看不惯的，如今习惯了；曾经很想要的，现在不需要了；开始很执着的，后来很洒脱了。失去产生了痛苦，也铸就了坚强；经历付出了代价，也锤炼了成长。没流泪，不代表没眼泪；无所谓，不代表无所累。当你知道什么是欲哭无泪，欲诉无语，欲笑无声的时候，你成熟了。累了没人疼，你要学会休息；哭了没人哄，你要知道自立；痛了没人懂，你要扛起压力抱怨的话不要说。有些委屈，是说不出来的。即使有人问，也不知从何说起；就算有人疼，也代替不了自己。嘴里有话却说不出，沉默代表了一切；心中有疼却表不明，泪水倾诉着所有。一些经历，只有自己感受；一些心情，只有自己懂得。说不出的委屈，才最委屈；心里的疼痛，才最疼痛！总是为别人着想，却要独自去疗伤；一直在嘴上逞强，心却没那么坚强。</string>");
    }

    static final String firstKG = "\u00A0";
    static final String secondKG = "\u2009";//两种不同长度的空格

    @Override
    protected void initPresenter() {
        mPresenter = new MultiPresenter();
    }

    @Override
    public void extraMethod() {
        Log.w(TAG, "extraMethod()");
    }

    @Override
    public void secondViewTest() {
        Log.w(TAG, "secondTest()");
    }

    public void exit(View view) {
        CollectionApplication.getInstance().exit();
    }
}
