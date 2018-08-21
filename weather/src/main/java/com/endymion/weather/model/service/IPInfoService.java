package com.endymion.weather.model.service;

import com.endymion.weather.model.entity.IPInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * https://lbs.amap.com/api/webservice/guide/api/ipconfig
 * Created by Jim on 2018/08/21.
 */

public interface IPInfoService {
    /**
     * @param key 请求服务权限标识
     */
    @GET("ip")
    Observable<IPInfo> getIPInfo(@Query("key") String key);
}
