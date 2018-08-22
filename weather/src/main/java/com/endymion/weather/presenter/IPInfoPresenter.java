package com.endymion.weather.presenter;

import com.endymion.common.net.RetrofitHelper;
import com.endymion.common.presenter.BasePresenter;
import com.endymion.common.util.RxUtils;
import com.endymion.weather.conf.Constant;
import com.endymion.weather.model.entity.IPInfo;
import com.endymion.weather.model.service.IPInfoService;
import com.endymion.weather.ui.view.IPInfoViewBridge;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * Created by Jim on 2018/08/21.
 */

public class IPInfoPresenter extends BasePresenter<IPInfoViewBridge> {
    private IPInfoService ipInfoService;

    public IPInfoPresenter() {
        this(RetrofitHelper.getInstance().buildRetrofit(Constant.BASE_URL));
    }

    public IPInfoPresenter(Retrofit retrofit) {
        ipInfoService = retrofit.create(IPInfoService.class);
    }

    public Observable<IPInfo> getIPInfo() {
        return ipInfoService.getIPInfo(Constant.AMAP_WEB_SERVICE_KEY)
                .compose(RxUtils.<IPInfo>applySchedulers());
    }
}
