package com.endymion.common.util;

import android.content.Context;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 时间操作工具
 * Created by Jim on 2018/07/24.
 */

public class TimeMillisUtils {
    private static final String TAG = "TimeMillisUtils";

    private static TimeMillisUtils timeUtils;
    private Context mContext;
    private String mServerHost;
    private long mDeviceOpenTimeMillisOnServer;
    private boolean isDateTimeAuto;

    private TimeMillisUtils() {

    }

    public static TimeMillisUtils getInstance() {
        if (timeUtils == null) {
            synchronized (TimeMillisUtils.class) {
                if (timeUtils == null) {
                    timeUtils = new TimeMillisUtils();
                }
            }
        }
        return timeUtils;
    }

    /**
     * 判断目前设备是否自动获取时间
     */
    public boolean isDateTimeAuto(Context mContext) {
        try {
            return Settings.Global.getInt(mContext.getContentResolver(), Settings.Global.AUTO_TIME) > 0;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 仅当设备不为自动获取时间时，进行初始化。
     * 确定本次开机时，对应的服务器时间
     * @param applicationContext Application的Context
     * @param serverHost 服务器地址
     */
    public void initServerTime(Context applicationContext, String serverHost) {
        if (TextUtils.isEmpty(serverHost)) {
            return;
        } else if (isDateTimeAuto(applicationContext)) {
            isDateTimeAuto = true;
            return;
        }

        mContext = applicationContext;
        mServerHost = serverHost;
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url;// 取得资源对象
                try {
                    url = new URL(mServerHost);
                    URLConnection urlConnection = url.openConnection();// 生成连接对象
                    urlConnection.setConnectTimeout(3000);
                    urlConnection.setReadTimeout(2000);
                    urlConnection.connect();// 发出连接
                    // TODO- 还需要优化，考虑设备时区不在东八区时的情况
                    long elapsedRealTimeMillis = SystemClock.elapsedRealtime();// 获取手机最后一次开机至今的时间（包含睡眠时间），用户无法修改的时间
                    long serverTimeMillis = urlConnection.getDate();// 获取服务器时间
                    if (serverTimeMillis != 0) {
                        mDeviceOpenTimeMillisOnServer = serverTimeMillis - elapsedRealTimeMillis;
                    }
                    Log.w(TAG, "mDeviceOpenTimeMillisOnServer = " + mDeviceOpenTimeMillisOnServer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public long getServerTimeMillis() {
        if (isDateTimeAuto || mDeviceOpenTimeMillisOnServer == 0) {
            return System.currentTimeMillis();
        }
        return SystemClock.elapsedRealtime() + mDeviceOpenTimeMillisOnServer;
    }


    // 短日期格式
    public static String DATE_FORMAT = "yyyy-MM-dd";
    public static String DATE_FORMAT_SAME_YEAR = "MM-dd";

    // 长日期格式
    public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String TIME_ZONE = "GMT+08:00";

    /**
     * 将日期格式的字符串转换为长整型
     *
     * @param date   时间字符串
     * @param format 时间格式
     * @return 默认时区毫秒数
     */
    public static long dateStr2long(Context mContext, String date, String format) {
        if (date != null && date.length() > 0) {
            if (format == null || format.length() == 0) {
                format = TimeMillisUtils.TIME_FORMAT;
            }
            return dateStr2Long(mContext, date, format, TimeZone.getTimeZone(TIME_ZONE));
        } else {
            return 0L;
        }
    }

    /**
     * 将长整型数字转换为日期格式的字符串
     *
     * @param time   时间
     * @param format 时间格式
     * @return 时间字符
     */
    public static String dateLong2String(Context mContext, Long time, String format) {
        if (time != null) {
            if (format == null || format.length() == 0) {
                if (isSameYear(time, new Date(System.currentTimeMillis()))) {
                    format = TimeMillisUtils.DATE_FORMAT_SAME_YEAR;
                } else {
                    format = TimeMillisUtils.DATE_FORMAT;
                }
            }
            return dateLong2Str(mContext, time, format, TimeZone.getTimeZone(TIME_ZONE));
        } else {
            return "";
        }
    }

    /**
     * 手机默认时区 时间格式化
     *
     * @param context  context
     * @param timeLong 时间long值
     * @param format   格式字符串
     * @return 格式化时间
     */
    public static String dateLong2StrDefault(Context context, Long timeLong, String format) {
        return dateLong2Str(context, timeLong, format, TimeZone.getDefault());
    }

    /**
     * 手机默认时区 时间格式化
     *
     * @param context context
     * @param timeStr 时间string值
     * @param format  格式字符串
     * @return 时间long值
     */
    public static long dateStr2LongDefault(Context context, String timeStr, String format) {

        return dateStr2Long(context, timeStr, format, TimeZone.getDefault());
    }

    /**
     * GMT+08:00时区 时间格式化
     *
     * @param context  context
     * @param timeLong Long类型时间
     * @param format   格式字符串
     * @return 格式化后的时间
     */
    public static String dateLong2StrGMT8(Context context, Long timeLong, String format) {
        return dateLong2Str(context, timeLong, format, TimeZone.getTimeZone("GMT+08:00"));
    }

    /**
     * GMT+08:00时区 时间格式化
     *
     * @param context context
     * @param timeStr 时间string值
     * @param format  格式字符串
     * @return 时间long值
     */
    public static long dateStr2LongGMT8(Context context, String timeStr, String format) {
        return dateStr2Long(context, timeStr, format, TimeZone.getTimeZone("GMT+08:00"));
    }


    /**
     * 时间格式化
     */
    private static String dateLong2Str(Context context, Long timeLong, String format, TimeZone timeZone) {
        try {
            if (timeLong != null) {
                SimpleDateFormat sf = new SimpleDateFormat(format, Locale.getDefault());
                sf.setTimeZone(timeZone);
                Date date = new Date(timeLong);

                return sf.format(date);
            }
        } catch (IllegalArgumentException iae) {
            Toast.makeText(context, "日期格式错误", Toast.LENGTH_SHORT).show();
        }
        return "";
    }

    /**
     * 时间转为long
     */
    public static long dateStr2Long(Context mContext, String timeStr, String format, TimeZone timeZone) {
        try {
            if (timeStr != null && timeStr.length() > 0) {
                SimpleDateFormat sf = new SimpleDateFormat(format, Locale.getDefault());
                sf.setTimeZone(timeZone);
                return sf.parse(timeStr).getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "日期格式错误", Toast.LENGTH_SHORT).show();
        }
        return 0L;
    }

    public static boolean isSameYear(Date date, Date dateLater) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year1 = calendar.get(Calendar.YEAR);

        calendar.setTime(dateLater);
        int year2 = calendar.get(Calendar.YEAR);
        return year1 == year2;
    }

    public static boolean isSameYear(long date, Date datelater) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(date));
        int year1 = calendar.get(Calendar.YEAR);

        calendar.setTime(datelater);
        int year2 = calendar.get(Calendar.YEAR);
        return year1 == year2;
    }
}
