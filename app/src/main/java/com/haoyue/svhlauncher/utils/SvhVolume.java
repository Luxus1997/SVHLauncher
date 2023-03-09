package com.haoyue.svhlauncher.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.Settings;

public class SvhVolume {

    private Context context;
    private AudioManager mAudioManager;
    private MediaPlayer mPlayer;

    public SvhVolume(Context context) {
        this.context = context;
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
    }

    public void playMusic(int paramInt) {
        stopMusic();
        mPlayer = MediaPlayer.create(context, paramInt);
        if (mPlayer != null) {
            mPlayer.setLooping(false);
            mPlayer.start();
        }
    }

    public void saveVolume(int paramInt) {
        Uri uri = Settings.System.getUriFor("auto_time");
        Settings.System.putInt(context.getContentResolver(), "auto_time", paramInt);
        context.getContentResolver().notifyChange(uri, null);
    }

    public void setSystemVolume(float f) {
        if (mAudioManager != null) {
            int streamMaxVolume = mAudioManager.getStreamMaxVolume(3);
            mAudioManager.setStreamVolume(3, (int) (streamMaxVolume * f), 0);
            saveVolume((int) (streamMaxVolume * f));
        }
    }

    public void setTouchVolume(boolean isChecked) {
        if (mAudioManager != null) {
            mAudioManager.loadSoundEffects();
            ContentResolver contentResolver = context.getContentResolver();
            if (!isChecked) {
                Settings.System.putInt(contentResolver, "sound_effects_enabled", 0);
            } else {
                Settings.System.putInt(contentResolver, "sound_effects_enabled", 1);
            }
            if (!isChecked) {
                mAudioManager.unloadSoundEffects();
            }
        }
    }

    public void stopMusic() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

}