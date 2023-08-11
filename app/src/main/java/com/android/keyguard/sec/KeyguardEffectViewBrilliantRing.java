package com.android.keyguard.sec;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.aj.effect.MainActivity;
import com.aj.effect.R;
import com.samsung.android.visualeffect.EffectType;
import com.samsung.android.visualeffect.EffectView;

import java.util.HashMap;

/* loaded from: classes.dex */
public class KeyguardEffectViewBrilliantRing extends EffectView implements KeyguardEffectViewBase {
    //private static final String DRAG_SOUND_PATH = "/system/media/audio/ui/ve_brilliantring_drag.ogg";
    //private static final String TAB_SOUND_PATH = "/system/media/audio/ui/ve_brilliantring_tap.ogg";
    private static final String TAG = "BrilliantRing_Keyguard";
    //private static final String UNLOCK_SOUND_PATH = "/system/media/audio/ui/ve_brilliantring_unlock.ogg";

    private static final int DRAG_SOUND_PATH = R.raw.brilliantring_drag;
    private static final int TAB_SOUND_PATH = R.raw.brilliantring_tap;
    private static final int UNLOCK_SOUND_PATH = R.raw.brilliantring_unlock;
    private static final int LOCK_SOUND_PATH = R.raw.brilliantring_lock;

    final int SOUND_ID_DRAG;
    final int SOUND_ID_TAB;
    final int SOUND_ID_UNLOCK;
    final int SOUND_ID_LOCK;
    private final long UNLOCK_SOUND_PLAY_TIME;
    private int cpuMaxValue;
    private float dragSoudMinusOffset;
    private float dragSoudVolume;
    private int dragStreamID;
    private int gpuMaxValue;
    private boolean hasWindowFocus;
    private boolean isFadeOutSound;
    private boolean isSystemSoundChecked;
    private boolean isUnlocked;
    private float leftVolumeMax;
    private Context mContext;
    long mLongPressTime;
    private SoundPool mSoundPool;
    private Runnable releaseSoundRunnable;
    private float rightVolumeMax;
    private int[] sounds;
    private long touchDownTime;
    private long touchMoveDiffTime;
    private boolean useCPUMaxClock;
    private boolean useGPUMaxClock;
    private static final String SILENCE_SOUND_PATH = "/system/media/audio/ui/ve_silence.ogg";
    public KeyguardEffectViewBrilliantRing(Context context) {
        super(context);
        this.mSoundPool = null;
        this.sounds = null;
        this.releaseSoundRunnable = null;
        this.UNLOCK_SOUND_PLAY_TIME = 2000L;
        this.touchDownTime = 0L;
        this.touchMoveDiffTime = 0L;
        this.SOUND_ID_TAB = 0;
        this.SOUND_ID_DRAG = 1;
        this.SOUND_ID_UNLOCK = 2;
        SOUND_ID_LOCK = 3;
        this.isSystemSoundChecked = true;
        this.leftVolumeMax = 1.0f;
        this.rightVolumeMax = 1.0f;
        this.dragStreamID = 0;
        this.mLongPressTime = 411L;
        this.dragSoudVolume = 1.0f;
        this.dragSoudMinusOffset = 0.04f;
        this.isFadeOutSound = false;
        this.isUnlocked = false;
        this.hasWindowFocus = true;
        init(context);
    }

    private void init(Context context) {
        Log.d(TAG, "KeyguardEffectViewBrilliantRing Constructor");
        this.mContext = context;
        setEffect(EffectType.BRILLIANT_RING);
        this.sounds = new int[4];
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void show() {
        Log.i(TAG, "show");
        makeSound();
        clearScreen();
        this.isUnlocked = false;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void reset() {
        Log.i(TAG, "reset");
        this.isUnlocked = false;
        clearScreen();
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void cleanUp() {
        Log.i(TAG, "cleanUp");
        stopReleaseSound();
        releaseSound();
        postDelayed(new Runnable() { // from class: com.android.keyguard.sec.effect.KeyguardEffectViewBrilliantRing.1
            @Override // java.lang.Runnable
            public void run() {
                KeyguardEffectViewBrilliantRing.this.clearScreen();
            }
        }, 400L);
        /*if (this.useGPUMaxClock) {
            VisualEffectDVFS.release(17);
        }
        if (this.useCPUMaxClock) {
            VisualEffectDVFS.release(13);
        }*/
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void update() {
        Log.i(TAG, "update");
        BitmapDrawable newBitmapDrawable = new BitmapDrawable(getResources(), MainActivity.bitm); // TODO: KeyguardEffectViewUtil.getCurrentWallpaper(this.mContext);
        if (newBitmapDrawable == null) {
            Log.i(TAG, "newBitmapDrawable  is null");
            return;
        }
        Bitmap originBitmap = newBitmapDrawable.getBitmap();
        if (originBitmap == null) {
            Log.d(TAG, "originBitmap is null");
        } else {
            setBitmap(originBitmap);
        }
    }

    private void setBitmap(Bitmap originBitmap) {
        HashMap<String, Bitmap> map = new HashMap<>();
        map.put("Bitmap", originBitmap);
        handleCustomEvent(0, map);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void screenTurnedOn() {
        Log.i(TAG, "screenTurnedOn");
        this.isUnlocked = false;
        clearScreen();
        /*if (this.useGPUMaxClock && this.hasWindowFocus) {
            VisualEffectDVFS.lock(17);
        }
        if (!this.useCPUMaxClock || !this.hasWindowFocus) {
            return;
        }
        VisualEffectDVFS.lock(13);*/
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void screenTurnedOff() {
        Log.i(TAG, "screenTurnedOff");
        clearScreen();
        /*if (this.useGPUMaxClock) {
            VisualEffectDVFS.release(17);
        }
        if (this.useCPUMaxClock) {
            VisualEffectDVFS.release(13);
        }*/
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void showUnlockAffordance(long startDelay, Rect rect) {
        Log.i(TAG, "showUnlockAffordance");
        this.isUnlocked = false;
        HashMap<Object, Object> map = new HashMap<>();
        map.put("StartDelay", Long.valueOf(startDelay));
        map.put("Rect", rect);
        handleCustomEvent(1, map);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public long getUnlockDelay() {
        return 250L;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void handleUnlock(View view, MotionEvent event) {
        Log.i(TAG, "handleUnlock");
        handleCustomEvent(2, null);
        this.isUnlocked = true;
        this.dragSoudMinusOffset = 0.059f;
        playSound(2);
        this.isFadeOutSound = true;
        if (this.dragSoudVolume == 1.0f) {
            fadeOutSound();
        }
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void playLockSound() {
        playSound(SOUND_ID_LOCK);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public boolean handleTouchEvent(View view, MotionEvent event) {
        if (this.isUnlocked) {
            Log.i(TAG, "handleTouchEvent isUnlocked : " + this.isUnlocked);
        } else {
            int action = event.getActionMasked();
            if (action == 0) {
                Log.i(TAG, "handleTouchEvent action : " + action);
                this.dragSoudVolume = 1.0f;
                this.isFadeOutSound = false;
                stopReleaseSound();
                this.touchDownTime = System.currentTimeMillis();
                if (this.mSoundPool == null) {
                    Log.d(TAG, "ACTION_DOWN, mSoundPool == null");
                    makeSound();
                    checkSound();
                }
                Log.d(TAG, "SOUND PLAY SOUND_ID_TAB");
                playSound(0);
                if (this.dragStreamID != 0) {
                    if (this.mSoundPool != null) {
                        this.mSoundPool.stop(this.dragStreamID);
                    }
                    this.dragStreamID = 0;
                }
            } else if (action == 2) {
                if (this.dragStreamID == 0) {
                    this.dragSoudVolume = 1.0f;
                    this.touchMoveDiffTime = System.currentTimeMillis() - this.touchDownTime;
                    if (this.touchMoveDiffTime > this.mLongPressTime && this.touchDownTime != 0) {
                        Log.d(TAG, "SOUND PLAY SOUND_ID_DRAG touchMoveDiff = " + this.touchMoveDiffTime);
                        playSound(1);
                    }
                }
            } else if (action == 1 || action == 3 || action == 4) {
                Log.i(TAG, "handleTouchEvent action : " + action);
                if (this.dragStreamID != 0) {
                    this.dragSoudMinusOffset = 0.039f;
                    this.isFadeOutSound = true;
                    if (this.dragSoudVolume == 1.0f) {
                        fadeOutSound();
                    }
                }
            }
            handleTouchEvent(event, view);
        }
        return true;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public boolean handleTouchEventForPatternLock(View view, MotionEvent event) {
        return false;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void setHidden(boolean isHidden) {
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public boolean handleHoverEvent(View view, MotionEvent event) {
        return false;
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        this.hasWindowFocus = hasWindowFocus;
        Log.d(TAG, "onWindowFocusChanged - " + hasWindowFocus);
        /*if (this.useGPUMaxClock && !hasWindowFocus) {
            VisualEffectDVFS.release(17);
        }
        if (this.useCPUMaxClock && !hasWindowFocus) {
            VisualEffectDVFS.release(13);
        }*/
        if (!hasWindowFocus && !this.isUnlocked && this.dragStreamID != 0) {
            this.dragSoudMinusOffset = 0.039f;
            this.isFadeOutSound = true;
            if (this.dragSoudVolume == 1.0f) {
                fadeOutSound();
            }
        }
    }

    private void makeSound() {
        stopReleaseSound();
        if (this.mSoundPool == null) { // KeyguardUpdateMonitor.getInstance(this.mContext).hasBootCompleted() &&
            Log.d(TAG, "sound : new SoundPool");
            AudioAttributes attr = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
            this.mSoundPool = new SoundPool.Builder().setMaxStreams(10).setAudioAttributes(attr).build();
            this.sounds[SOUND_ID_TAB] = this.mSoundPool.load(mContext, TAB_SOUND_PATH, 1);
            this.sounds[SOUND_ID_DRAG] = this.mSoundPool.load(mContext, DRAG_SOUND_PATH, 1);
            this.sounds[SOUND_ID_UNLOCK] = this.mSoundPool.load(mContext, UNLOCK_SOUND_PATH, 1);
            sounds[SOUND_ID_LOCK] = mSoundPool.load(mContext, LOCK_SOUND_PATH, 1);
            this.mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() { // from class: com.android.keyguard.sec.effect.KeyguardEffectViewBrilliantRing.2
                @Override // android.media.SoundPool.OnLoadCompleteListener
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    Log.d(KeyguardEffectViewBrilliantRing.TAG, "sound : onLoadComplete");
                }
            });
        }
    }

    private void stopReleaseSound() {
        if (this.releaseSoundRunnable != null) {
            removeCallbacks(this.releaseSoundRunnable);
            this.releaseSoundRunnable = null;
        }
    }

    private void releaseSound() {
        this.releaseSoundRunnable = new Runnable() { // from class: com.android.keyguard.sec.effect.KeyguardEffectViewBrilliantRing.3
            @Override // java.lang.Runnable
            public void run() {
                if (KeyguardEffectViewBrilliantRing.this.mSoundPool != null) {
                    Log.d(KeyguardEffectViewBrilliantRing.TAG, "BrilliantRing sound : release SoundPool");
                    KeyguardEffectViewBrilliantRing.this.mSoundPool.release();
                    KeyguardEffectViewBrilliantRing.this.mSoundPool = null;
                }
                KeyguardEffectViewBrilliantRing.this.releaseSoundRunnable = null;
            }
        };
        postDelayed(this.releaseSoundRunnable, 2000L);
    }

    private void checkSound() {
        ContentResolver cr = this.mContext.getContentResolver();
        int result;
        result = Settings.System.getInt(cr, "lockscreen_sounds_enabled", -2);
        if (result == 1) {
            this.isSystemSoundChecked = true;
        } else {
            this.isSystemSoundChecked = false;
        }
    }

    private void playSound(int soundId) {
        checkSound();
        Log.d(TAG, "SOUND PLAY mSoundPool = " + this.mSoundPool + ", isSystemSoundChecked = " + this.isSystemSoundChecked);
        if (this.isSystemSoundChecked && this.mSoundPool != null) {
            Log.d(TAG, "SOUND PLAY soundId = " + soundId);
            if (soundId == 1) {
                this.dragStreamID = this.mSoundPool.play(this.sounds[soundId], this.leftVolumeMax, this.rightVolumeMax, 0, -1, 1.0f);
            } else {
                this.mSoundPool.play(this.sounds[soundId], this.leftVolumeMax, this.rightVolumeMax, 0, 0, 1.0f);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fadeOutSound() {
        if (this.isFadeOutSound && this.mSoundPool != null) {
            if (this.dragSoudVolume < 0.0f) {
                this.dragSoudVolume = 0.0f;
            }
            this.mSoundPool.setVolume(this.dragStreamID, this.dragSoudVolume, this.dragSoudVolume);
            if (this.dragSoudVolume > 0.0f) {
                this.dragSoudVolume -= this.dragSoudMinusOffset;
                postDelayed(new Runnable() { // from class: com.android.keyguard.sec.effect.KeyguardEffectViewBrilliantRing.4
                    @Override // java.lang.Runnable
                    public void run() {
                        KeyguardEffectViewBrilliantRing.this.fadeOutSound();
                    }
                }, 10L);
                return;
            }
            Log.d(TAG, "SOUND STOP because UP or Unlock");
            stopReleaseSound();
            releaseSound();
        }
    }


     // TODO: @Override com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void setContextualWallpaper(Bitmap bmp) {
        Log.d(TAG, "setContextualWallpaper");
        if (bmp == null) {
            Log.d(TAG, "bmp is null");
        } else {
            setBitmap(bmp);//KeyguardEffectViewUtil.getPreferredConfigBitmap(bmp, Bitmap.Config.ARGB_8888));
        }
    }

    public static boolean isBackgroundEffect() {
        return true;
    }

    public static String getCounterEffectName() {
        return null;
    }
}