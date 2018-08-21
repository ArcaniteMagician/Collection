package com.endymion.weather.presenter;

import com.endymion.common.presenter.BasePresenter;
import com.endymion.common.util.RxUtils;
import com.endymion.weather.conf.Constant;
import com.endymion.weather.model.entity.IPInfo;
import com.endymion.weather.model.service.IPInfoService;
import com.endymion.weather.ui.view.IPInfoViewBridge;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jim on 2018/08/21.
 */

public class IPInfoPresenter extends BasePresenter<IPInfoViewBridge> {
    private IPInfoService ipInfoService;

    public IPInfoPresenter() {
        this(new Retrofit.Builder()
                .client(new OkHttpClient())
                .baseUrl(Constant.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build());
    }

    public IPInfoPresenter(Retrofit retrofit) {
        ipInfoService = retrofit.create(IPInfoService.class);
    }

    public Observable<IPInfo> getIPInfo() {
        return ipInfoService.getIPInfo(Constant.AMAP_WEB_SERVICE_KEY)
                .compose(RxUtils.<IPInfo>applySchedulers());
    }
}
