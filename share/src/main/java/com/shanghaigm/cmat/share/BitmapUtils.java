package com.shanghaigm.cmat.share;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class BitmapUtils {
    public static final String TAG = "BitmapUtils";


    public static Bitmap scale(Bitmap bitmap, float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Bitmap outBitmap = Bitmap.createBitmap(bitmap, 0, 0, (int) (bitmap.getWidth()), (int) (bitmap.getHeight()), matrix, true);
        bitmap.recycle();
        return outBitmap;
    }

    public static byte[] compressImageStream(Bitmap image, int size) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;

        while (baos.toByteArray().length / 1024 > size) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        try {
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();

    }

    /**
     * 根据图片URL获取Bitmap
     *
     * @param url 图片URL
     * @return Bitmap
     * @throws Exception 图片处理异常
     */
    public static Bitmap url2bitmap(String url) throws Exception {
        Bitmap bitmap;
        URL iconUrl = new URL(url);
        URLConnection conn = iconUrl.openConnection();
        HttpURLConnection http = (HttpURLConnection) conn;
        int length = http.getContentLength();
        conn.connect();
        // 获得图像的字符流
        InputStream is = conn.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(is, length);
        bitmap = BitmapFactory.decodeStream(bis);
        bis.close();
        is.close();
        return bitmap;
    }

}