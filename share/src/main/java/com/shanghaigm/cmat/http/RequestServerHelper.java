package com.shanghaigm.cmat.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络请求类RequestServer的帮助类
 * Created by chumi.darren on 2016/7/7.
 */
public class RequestServerHelper {

    private static final String TAG = "RequestServerHelper";

    /**
     * 检测网络是否可用
     *
     * @return true可用，false不可用
     */
    static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
//            Toast.makeText(context, context.getString(R.string.connect_network), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}
