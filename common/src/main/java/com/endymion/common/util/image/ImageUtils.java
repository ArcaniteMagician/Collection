package com.endymion.common.util.image;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.endymion.common.util.CommonUtils;
import com.nanchen.compresshelper.CompressHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;

import me.iwf.photopicker.PhotoPicker;

/**
 * Created by Jim Lee on 2018/9/4.
 */
public class ImageUtils {
    private static final String TAG = "ImageUtils";

    // 图片加载
    public enum CropType {
        // 普通，圆形，圆角，模糊
        NORMAL, CIRCLE, ROUND, BLURRY
    }

    public static void load(LoadOptions options) {
        if (options.isLocal()) {
            // 本地文件
            File file = new File(options.path);
            // 加载图片
            Glide.with(options.imageView.getContext()).load(file).apply(options.requestOptions).into(options.imageView);
        } else {
            Glide.with(options.imageView.getContext()).load(options.path).apply(options.requestOptions).into(options.imageView);
        }
    }

    public static class LoadOptions {
        // 必填
        private ImageView imageView;
        private String path;

        private RequestOptions requestOptions;
        private Boolean isLocal;

        private LoadOptions(Builder builder) {
            this.imageView = builder.imageView;
            this.path = builder.url;
            this.isLocal = builder.isLocal;

            requestOptions = new RequestOptions();
            if (builder.cropType != null) {
                switch (builder.cropType) {
                    case CIRCLE:
                        requestOptions.transform(new CircleCrop());
                        if (builder.overrideSizeDp != null) {
                            requestOptions.override(builder.overrideSizeDp);
                        }
                        break;
                    case BLURRY:
                        requestOptions.transform(new GlideBlurformation(imageView.getContext()));
                        break;
                    case ROUND:
                        if (builder.roundedCornerSizeDp == null) {
                            builder.roundedCornerSizeDp = 15;
                        }
                        requestOptions.transform(new RoundedCorners(CommonUtils.dip2px(imageView.getContext(), builder.roundedCornerSizeDp))).centerCrop();
                        break;
                    case NORMAL:
                    default:
                        break;
                }
            }

            // 本地缓存
            if (builder.diskCathe != null) {
                if (builder.diskCathe) {
                    if (builder.onlyOriginDiskCache != null && builder.onlyOriginDiskCache) {
                        requestOptions.diskCacheStrategy(DiskCacheStrategy.DATA);
                    } else {
                        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
                    }
                } else {
                    requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
                }
            } else {
                requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            }

            // 内存缓存
            if (builder.memoryCathe != null) {
                if (builder.memoryCathe) {
                    requestOptions.skipMemoryCache(false);
                } else {
                    requestOptions.skipMemoryCache(true);
                }
            } else {
                requestOptions.skipMemoryCache(false);
            }

            // 占位图及加载错误的图
            if (builder.defaultResId != null) {
                requestOptions.placeholder(builder.defaultResId).error(builder.defaultResId);
            }
            if (builder.defaultImage != null) {
                requestOptions.placeholder(builder.defaultImage).error(builder.defaultImage);
            }
        }

        private boolean isLocal() {
            return isLocal == null ? false : isLocal;
        }

        public static class Builder {
            // 必填
            private ImageView imageView;
            private String url;
            // 选填
            private CropType cropType;
            private Integer roundedCornerSizeDp;
            private Boolean diskCathe;
            private Boolean memoryCathe;
            private Boolean isLocal;
            private Integer defaultResId;
            private Drawable defaultImage;
            private Integer overrideSizeDp;
            // 只缓存原图到本地
            private Boolean onlyOriginDiskCache;

            public Builder(ImageView imageView, String url) {
                this.imageView = imageView;
                this.url = url;
            }

            public LoadOptions build() {
                return new LoadOptions(this);
            }

            public Builder setCropType(CropType cropType) {
                this.cropType = cropType;
                return this;
            }

            public Builder setDiskCathe(Boolean diskCathe) {
                this.diskCathe = diskCathe;
                return this;
            }

            public Builder setMemory(Boolean memoryCathe) {
                this.memoryCathe = memoryCathe;
                return this;
            }

            public Builder setDefaultResId(@DrawableRes Integer defaultResId) {
                this.defaultResId = defaultResId;
                return this;
            }

            public Builder setDefaultImage(Drawable defaultImage) {
                this.defaultImage = defaultImage;
                return this;
            }

            public Builder setRoundedCornerSizeDp(Integer roundedCornerSizeDp) {
                this.roundedCornerSizeDp = roundedCornerSizeDp;
                return this;
            }

            public Builder setOverrideSizeDp(Integer overrideSizeDp) {
                this.overrideSizeDp = overrideSizeDp;
                return this;
            }

            public Builder setIsLocal(Boolean isLocal) {
                this.isLocal = isLocal;
                return this;
            }

            public Builder setOnlyOriginDiskCache(Boolean onlyOriginDiskCache) {
                this.onlyOriginDiskCache = onlyOriginDiskCache;
                return this;
            }
        }
    }

    // 拍照
    public static void takePhoto(Activity activity, int requestCode, String path) {
        File outputImage = new File(path);
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri imageUri = Uri.fromFile(outputImage);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        activity.startActivityForResult(intent, requestCode);
    }

    // 裁剪
    public static void cropPhoto(Activity activity, int requestCode, String path) {
        Uri imageUri = getImageUri(activity, "com.scysun.vein.fileProvider", path);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        activity.startActivityForResult(intent, requestCode);// 启动裁剪程序
    }

    public static Uri getImageUri(Context context, String authority, String path) {
        File file = new File(path);
        if (Build.VERSION.SDK_INT >= 24) {
            // 判断版本是否在7.0以上
            // 参数1 上下文, 参数2 Provider主机地址和配置文件中保持一致, 参数3 共享的文件
            return FileProvider.getUriForFile(context, authority, file);
        } else {
            return Uri.fromFile(file);
        }
    }

    // 图片选择器
    public static final int REQUEST_CODE_PICK_PHOTO = PhotoPicker.REQUEST_CODE;
    public static final String KEY_SELECTED_PHOTOS = PhotoPicker.KEY_SELECTED_PHOTOS;

    public static void openPicker(Activity activity, int photoCount) {
        openPicker(activity, photoCount, REQUEST_CODE_PICK_PHOTO);
    }

    public static void openPicker(Activity activity, int photoCount, int requestCode) {
        PhotoPicker.builder()
                .setShowCamera(true)
                .setPhotoCount(photoCount)
                .setPreviewEnabled(true)
                .setShowGif(false)
                .start(activity, requestCode);
    }

    // 地址末尾不加“/”是为了避免在操作目录时，还需要去除“/”的麻烦
    // 压缩图片
    public static final String TEMP_FILE_DIRECTORY = Environment.getExternalStorageDirectory().getPath() + "/zsh/temp";
    // 图片保存地址使用默认图库地址，自定义地址可能存在，部分机型通知图库更新不起作用（如魅族），或者自定义目录在相册中隐藏地很深（如vivo）的问题
    public static final String IMAGE_DIRECTORY = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();

    public static String compressImage(Context context, String path, int maxKbSize) {
        return compressImage(context, path, maxKbSize, 720, 960, 90);
    }

    /**
     * @param path      带文件名的完整路径
     * @param maxKbSize 最大能接受的文件大小
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @param quality   压缩质量，[0,100]
     * @return 压缩后的文件路径，清除临时文件调用{@see clearTempDirectory}方法
     */
    public static String compressImage(Context context, String path, int maxKbSize, int maxWidth, int maxHeight, int quality) {
        File oldFile = new File(path);
        if (!oldFile.exists()) {
            Log.w(TAG, "文件不存在");
            return path;
        }
        String fileName = getFileName(path);

        File newFile = null;
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = Double.valueOf(df.format((double) oldFile.length() / 1024));

        while (fileSizeLong > maxKbSize) {
            newFile = new CompressHelper.Builder(context)
                    .setMaxWidth(maxWidth)  // 默认最大宽度为720
                    .setMaxHeight(maxHeight) // 默认最大高度为960
                    .setQuality(quality)    // 默认压缩质量为80
                    .setFileName(fileName) // 设置需要修改的文件名
                    .setCompressFormat(Bitmap.CompressFormat.JPEG) // 设置默认压缩为jpg格式
                    .setDestinationDirectoryPath(TEMP_FILE_DIRECTORY)
                    .build()
                    .compressToFile(oldFile);
            fileSizeLong = Double.valueOf(df.format((double) newFile.length() / 1024));
        }
        if (newFile == null) {
            if (fileSizeLong < maxKbSize) {
                Log.w(TAG, "原图已小于" + maxKbSize + "KB");
                return path;
            }
            Log.w(TAG, "压缩图片异常");
            return path;
        }
        return newFile.getAbsolutePath();
    }

    private static String getFileName(String path) {
        String[] str = path.split(File.separator);
        String fileName = str[str.length - 1];
        fileName = fileName.replace(".jpg", "")
                .replace(".JPG", "")
                .replace(".png", "")
                .replace(".PNG", "")
                .replace("jpeg", "")
                .replace(".JPEG", "")
                .replace(".bmp", "")
                .replace(".BMP", "");
        return fileName;
    }

    public static void clearTempDirectory() {
        deleteFile(new File(TEMP_FILE_DIRECTORY));
    }

    private static void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                deleteFile(f);
            }
//            file.delete();// 保留文件夹，仅删除文件
        } else if (file.exists()) {
            file.delete();
        }
    }

    private static boolean copyFile(File source, File target) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        boolean isSuccess = true;
        try {
            fileInputStream = new FileInputStream(source);
            fileOutputStream = new FileOutputStream(target);
            byte[] buffer = new byte[1024];
            while (fileInputStream.read(buffer) > 0) {
                fileOutputStream.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isSuccess;
    }

    // 文件是否不存在，文件已存在则直接返回false，不存在时进行创建
    private static boolean fileNotExists(File file) {
        if (!file.exists()) {
            try {
                if (fileDirNotExists(file)) {
                    return true;
                }
                if (!file.createNewFile()) {
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return true;
            }
        }
        return false;
    }

    // 目录是否不存在
    private static boolean fileDirNotExists(File file) {
        String fileName = getFileName(file.getPath());
        File fileDir = new File(file.getPath().replace("/" + fileName + ".jpg", ""));
        return !fileDir.exists() && !fileDir.mkdirs();
    }

    // 从Glide获取图片缓存路径
    private static String getImagePath(Context context, String imgUrl) {
        String path = null;
        FutureTarget<File> future = Glide.with(context)
                .asFile()
                .load(imgUrl)
                .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
        try {
            // 需要在非主线程操作
            File cacheFile = future.get();
            path = cacheFile.getAbsolutePath();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        Log.w("scan TEST", "path = " + path);
        return path;
    }

    public static void saveImage(WeakReference<Context> reference, String imageUrl, String filePath, @Nullable Callback callback) {
        new Thread(() -> {
            String cachePath = null;
            if (reference.get() != null) {
                cachePath = getImagePath(reference.get(), imageUrl);
            }
            if (cachePath != null && cachePath.length() > 0 && reference.get() != null) {
                if (saveImage(reference.get(), cachePath, filePath) && callback != null) {
                    callback.onSuccess();
                } else if (callback != null) {
                    callback.onFailure();
                }
            }
        }).start();
    }

    private static boolean saveImage(Context context, String cachePath, String filePath) {
        File file = new File(filePath);
        if (fileNotExists(file)) {
            return false;
        }
        if (copyFile(new File(cachePath), file)) {
            notifyGallery(context, file);
            return true;
        } else {
            return false;
        }
    }

    // 将控件视图保存为图像
    public static boolean saveViewAsBitmap(View view, String filePath) {
        // 创建对应大小的bitmap
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        FileOutputStream outStream = null;
        File file = new File(filePath);
        if (file.isDirectory()) {
            // 如果是目录则不保存
            return false;
        }
        if (fileNotExists(file)) {
            // 文件不存在且创建失败则停止操作
            return false;
        }

        boolean isSuccess = false;
        try {
            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            isSuccess = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (isSuccess) {
            notifyGallery(view.getContext(), file);
            bitmap.recycle();
            return true;
        } else {
            return false;
        }
    }

    // 通知相册更新
    public static void notifyGallery(Context context, String filePath) {
        File file = new File(filePath);
        if (!fileNotExists(file)) {
            notifyGallery(context, file);
        }
    }

    private static void notifyGallery(Context context, File file) {
        // 把文件插入到系统图库，与通知无关，做记录用。
//            MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                    bitmap, getFileName(filePath), null);
//            MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                    filePath, getFileName(filePath), null);

        // 发送广播
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);

//        String externalStoragePath = Environment.getExternalStorageDirectory().getPath();
//        Log.w("scan TEST", "intent = " + intent
//                + ";\npath = " + uri.getPath() + ";\nexternalStoragePath = " + externalStoragePath);

        // MediaScannerConnection，可以扫描多个路径，配置监听器
//        MediaScannerConnection.scanFile(context, new String[]{file.getPath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
//            @Override
//            public void onScanCompleted(String path, Uri uri) {
//                Log.w("scan", "onScanCompleted + " + path);
//            }
//        });
    }

    public interface Callback {
        void onSuccess();

        void onFailure();
    }
}
