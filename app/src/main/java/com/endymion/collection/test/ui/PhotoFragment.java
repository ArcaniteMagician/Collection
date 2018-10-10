package com.endymion.collection.test.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.endymion.collection.R;

/**
 * Created by Jim Lee on 2018/10/9.
 */
public class PhotoFragment extends Fragment {
    private ImageView imageView;
    private String url;
    private Context context;

    public PhotoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        imageView = view.findViewById(R.id.iv);
        return view;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void onResume() {
        super.onResume();
        Glide.with(context).load(url).into(imageView);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
