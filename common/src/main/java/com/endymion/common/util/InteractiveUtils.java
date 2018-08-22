package com.endymion.common.util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.annotation.NonNull;

import java.io.IOException;

/**
 * 交互反馈
 * Created by Jim on 2018/07/25.
 */

public class InteractiveUtils {
    private static final String TAG = "InteractiveUtils";
    private static final int VIBRATE_DURATION = 500;

    public static void interactive(Context context, int audioId) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            switch (audioManager.getRingerMode()) {
                case AudioManager.RINGER_MODE_NORMAL:// 普通
                    playAudio(context, audioId, audioManager);
                    break;

                case AudioManager.RINGER_MODE_VIBRATE:// 震动
                    vibrate(context);
                    break;

                case AudioManager.RINGER_MODE_SILENT:// 静音
                default:
                    break;
            }
        }
    }

    private static void playAudio(Context context, int audioId, @NonNull AudioManager audioManager) {
        int volume = audioManager.getStreamVolume(AudioManager.STREAM_ALARM);

        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        AssetFileDescriptor file = context.getResources().openRawResourceFd(audioId);
        try {
            mediaPlayer.setDataSource(file.getFileDescriptor(),
                    file.getStartOffset(), file.getLength());
            file.close();
            mediaPlayer.setVolume(volume, volume);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            mediaPlayer = null;
        }
        if (mediaPlayer != null) {
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                    }
                }
            });
            mediaPlayer.start();
        }
    }

    private static void vibrate(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }
}
