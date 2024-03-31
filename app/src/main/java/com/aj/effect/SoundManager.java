package com.aj.effect;

import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.annotation.RawRes;

public class SoundManager {
    public static final int TAP = 0;
    public static final int DRAG = 1;
    public static final int LOCK = 2;
    public static final int UNLOCK = 3;
    static private int[] sounds = new int[4];


    static AudioAttributes attr = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
    static SoundPool soundPool = new SoundPool.Builder().setMaxStreams(10).setAudioAttributes(attr).build();

    /**
     * Load sounds into SoundPool.
     * Order is TAP, DRAG, LOCK, UNLOCK
     */
    public static void loadSound(Context context, @RawRes Integer... sounds) {
        for (int i = 0; i < SoundManager.sounds.length; i++) {
            if (soundPool.unload(SoundManager.sounds[i])) {
                SoundManager.sounds[i] = 0;
            }
            if (sounds.length == SoundManager.sounds.length) {
                if (sounds[i] != 0) {
                    int sound = soundPool.load(context, sounds[i], 1);
                    SoundManager.sounds[i] = sound;
                }
            }
        }
    }

    public static void releaseSound() {
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }

    public static Integer playSound(Context context, int soundId, float leftVolumeMax, float rightVolumeMax, int priority, int loop, float rate) {
        if (checkSound(context) && soundPool != null) {
            return soundPool.play(sounds[soundId], leftVolumeMax, rightVolumeMax, priority, loop, rate);
        } else return null;
    }

    private static boolean checkSound(Context context) {
        ContentResolver cr = context.getContentResolver();
        int result = Settings.System.getInt(cr, "lockscreen_sounds_enabled", -2);
        return result == 1;
    }

    public static boolean isLoaded() {
        return sounds[TAP] != 0 || sounds[DRAG] != 0 || sounds[LOCK] != 0 || sounds[UNLOCK] != 0;
    }
}
