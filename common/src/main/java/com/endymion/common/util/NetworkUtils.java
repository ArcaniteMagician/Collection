package com.endymion.common.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * 网络状态检查类
 * Created by Jim on 2018/07/27.
 */

public class NetworkUtils {

    /***
     * 判断当前是否在网络环境下
     */
    public static boolean isNetworkConnected(Context context) {
        boolean bret = false;
        NetworkInfo mNetworkInfo = getCurNetworkInfo(context);
        if (mNetworkInfo != null) {
            bret = mNetworkInfo.isAvailable();
        }
        return bret;
    }

    /***
     * 判断当前是否使用wifi网络
     */
    public static boolean isWifiNetwork(Context context) {
        return "WIFI".equalsIgnoreCase(getApnType(context));
    }

    /***
     * 判断当前是否使用移动网络包括2G,3G,4G
     */
    public static boolean isMobileNetwork(Context context) {
        return "MOBILE".equalsIgnoreCase(getApnType(context));
    }

    public static String getApnType(Context context) {
        String ret = "";
        NetworkInfo mNetworkInfo = getCurNetworkInfo(context);
        if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
            ret = mNetworkInfo.getTypeName();
        }
        return ret;
    }

    protected static NetworkInfo getCurNetworkInfo(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mConnectivityManager != null) {
            return mConnectivityManager.getActiveNetworkInfo();
        }
        return null;
    }


    /**
     * Get the network info
     */
    private static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }


    public static final String CONNECT_TYPE_NAME_WIFI = "wifi";
    public static final String CONNECT_TYPE_NAME_4G = "4G";
    public static final String CONNECT_TYPE_NAME_3G = "3G";
    public static final String CONNECT_TYPE_NAME_2G = "2G";
    public static final String CONNECT_TYPE_NAME_UNKNOWN = "unkown";

    /**
     * 获取网络类型的名称,可选值如上
     */
    public static String getNetworkType(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        if (info == null) {
            return CONNECT_TYPE_NAME_UNKNOWN;
        }

        int type = info.getType();
        int subType = info.getSubtype();

        if (type == ConnectivityManager.TYPE_WIFI) {
            return CONNECT_TYPE_NAME_WIFI;
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            switch (subType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    // ~25 kbps
                    return CONNECT_TYPE_NAME_2G;

                case TelephonyManager.NETWORK_TYPE_UMTS:
                    // ~ 400-7000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    // ~ 600-1400 kbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    // ~ 1-23 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    // ~ 5 Mbps
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                    // ~ 1-2 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    // ~ 10-20 Mbps
                    // ~ 700-1700 kbps
                    return CONNECT_TYPE_NAME_3G;

                case TelephonyManager.NETWORK_TYPE_LTE:
                    // ~ 10+ Mbps
                    return CONNECT_TYPE_NAME_4G;

                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                default:
                    return CONNECT_TYPE_NAME_UNKNOWN;
            }
        } else {
            return CONNECT_TYPE_NAME_UNKNOWN;
        }
    }

}
