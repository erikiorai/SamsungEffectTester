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

import com.aj.effect.R;
import com.samsung.android.visualeffect.EffectView;

import java.util.HashMap;

public class KeyguardEffectViewBrilliantCut extends EffectView implements KeyguardEffectViewBase {
    private static final int DRAG_SOUND_PATH = R.raw.ve_brilliantcut_drag; //"/system/media/audio/ui/ve_brilliantcut_drag.ogg";
    private static final int TAB_SOUND_PATH = R.raw.ve_brilliantcut_tap; //"/system/media/audio/ui/ve_brilliantcut_tap.ogg";
    private static final String TAG = "BrilliantCut_Keyguard";
    private static final int UNLOCK_SOUND_PATH = R.raw.ve_brilliantcut_unlock; //"/system/media/audio/ui/ve_brilliantcut_unlock.ogg";
    final int SOUND_ID_DRAG = 1;
    final int SOUND_ID_TAB = 0;
    final int SOUND_ID_UNLOCK = 2;
    final int SOUND_ID_LOCK = 3;
    private final long UNLOCK_SOUND_PLAY_TIME;
    private float dragSoudMinusOffset;
    private float dragSoudVolume;
    private int dragStreamID;
    private boolean hasWindowFocus;
    private int imageType;
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
    private static final int LOCK_SOUND_PATH = R.raw.ve_brilliantcut_lock; //"/system/media/audio/ui/ve_brilliantcut_lock.ogg";

    public KeyguardEffectViewBrilliantCut(Context context) {
        super(context);
        this.mSoundPool = null;
        this.sounds = null;
        this.releaseSoundRunnable = null;
        this.UNLOCK_SOUND_PLAY_TIME = 2000L;
        this.touchDownTime = 0L;
        this.touchMoveDiffTime = 0L;
        this.isSystemSoundChecked = true;
        this.leftVolumeMax = 1.0f;
        this.rightVolumeMax = 1.0f;
        this.dragStreamID = 0;
        this.mLongPressTime = 411L;
        this.dragSoudVolume = 1.0f;
        this.dragSoudMinusOffset = 0.04f;
        this.isFadeOutSound = false;
        this.imageType = 1;
        this.isUnlocked = false;
        this.hasWindowFocus = true;
        init(context);
    }

    private void init(Context context) {
        Log.d(TAG, "KeyguardEffectViewBrilliantCut Constructor");
        this.mContext = context;
        setEffect(6);
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
        // from class: com.android.keyguard.sec.effect.KeyguardEffectViewBrilliantCut.1
// java.lang.Runnable
        postDelayed(() -> KeyguardEffectViewBrilliantCut.this.clearScreen(), 400L);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void update() {Log.i(TAG, "update");
        BitmapDrawable newBitmapDrawable = KeyguardEffectViewUtil.getCurrentWallpaper(this.mContext);
        if (newBitmapDrawable == null) {
            Log.i(TAG, "newBitmapDrawable  is null");
            return;
        }
        Bitmap originBitmap = newBitmapDrawable.getBitmap();
        if (originBitmap != null) {
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
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void screenTurnedOff() {
        Log.i(TAG, "screenTurnedOff");
        clearScreen();
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void showUnlockAffordance(long startDelay, Rect rect) {
        Log.i(TAG, "showUnlockAffordance");
        this.isUnlocked = false;
        HashMap<Object, Object> map = new HashMap<>();
        map.put("StartDelay", startDelay);
        map.put("Rect", rect);
        handleCustomEvent(1, map);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public long getUnlockDelay() {
        int returnValue = 400;
        if (this.imageType == 0) {
            returnValue = 200;
        }
        return returnValue;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void handleUnlock(View view, MotionEvent event) {
        Log.i(TAG, "handleUnlock");
        this.isUnlocked = true;
        this.dragSoudMinusOffset = 0.059f;
        playSound(SOUND_ID_UNLOCK);
        this.isFadeOutSound = true;
        if (this.dragSoudVolume == 1.0f) {
            fadeOutSound();
        }
        if (this.imageType == 0) {
            Log.d(TAG, "handleUnlock, but return because imageType is Normal");
            return;
        }
        Log.d(TAG, "handleUnlock, do it because imageType is Special");
        handleCustomEvent(2, null);
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
        if (!hasWindowFocus && !this.isUnlocked && this.dragStreamID != 0) {
            this.dragSoudMinusOffset = 0.039f;
            this.isFadeOutSound = true;
            if (this.dragSoudVolume == 1.0f) {
                fadeOutSound();
            }
        }
    }

    public void settingsForImageType(int type) {
        Log.d(TAG, "settingsForImageType type = " + type);
        int[] nums = {1};
        float[] values = {type};
        HashMap<Object, Object> map = new HashMap<>();
        map.put("Nums", nums);
        map.put("Values", values);
        handleCustomEvent(99, map);
        this.imageType = type;
    }

    private void makeSound() {
        stopReleaseSound();
        if (this.mSoundPool == null) { // todo KeyguardUpdateMonitor.getInstance(this.mContext).hasBootCompleted() &&
            Log.d(TAG, "sound : new SoundPool");
            AudioAttributes attr = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
            this.mSoundPool = new SoundPool.Builder().setMaxStreams(10).setAudioAttributes(attr).build();
            this.sounds[SOUND_ID_TAB] = this.mSoundPool.load(mContext, TAB_SOUND_PATH, 1);
            this.sounds[SOUND_ID_DRAG] = this.mSoundPool.load(mContext, DRAG_SOUND_PATH, 1);
            this.sounds[SOUND_ID_UNLOCK] = this.mSoundPool.load(mContext, UNLOCK_SOUND_PATH, 1);
            sounds[SOUND_ID_LOCK] = mSoundPool.load(mContext, LOCK_SOUND_PATH, 1);
            // from class: com.android.keyguard.sec.effect.KeyguardEffectViewBrilliantCut.2
// android.media.SoundPool.OnLoadCompleteListener
            this.mSoundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> Log.d(KeyguardEffectViewBrilliantCut.TAG, "sound : onLoadComplete"));
        }
    }

    private void stopReleaseSound() {
        if (this.releaseSoundRunnable != null) {
            removeCallbacks(this.releaseSoundRunnable);
            this.releaseSoundRunnable = null;
        }
    }

    private void releaseSound() {
        // from class: com.android.keyguard.sec.effect.KeyguardEffectViewBrilliantCut.3
// java.lang.Runnable
        this.releaseSoundRunnable = () -> {
            if (KeyguardEffectViewBrilliantCut.this.mSoundPool != null) {
                Log.d(KeyguardEffectViewBrilliantCut.TAG, "BrilliantCut sound : release SoundPool");
                KeyguardEffectViewBrilliantCut.this.mSoundPool.release();
                KeyguardEffectViewBrilliantCut.this.mSoundPool = null;
            }
            KeyguardEffectViewBrilliantCut.this.releaseSoundRunnable = null;
        };
        postDelayed(this.releaseSoundRunnable, 2000L);
    }

    private void checkSound() {
        ContentResolver cr = this.mContext.getContentResolver();
        int result = Settings.System.getInt(cr, "lockscreen_sounds_enabled", -2);
        this.isSystemSoundChecked = result == 1;
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
                // from class: com.android.keyguard.sec.effect.KeyguardEffectViewBrilliantCut.4
// java.lang.Runnable
                postDelayed(() -> KeyguardEffectViewBrilliantCut.this.fadeOutSound(), 10L);
                return;
            }
            Log.d(TAG, "SOUND STOP because UP or Unlock");
            stopReleaseSound();
            releaseSound();
        }
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void setContextualWallpaper(Bitmap bmp) {
        Log.d(TAG, "setContextualWallpaper");
        if (bmp == null) {
            Log.d(TAG, "bmp is null");
        } else {
            setBitmap(KeyguardEffectViewUtil.getPreferredConfigBitmap(bmp, Bitmap.Config.ARGB_8888));
        }
    }

    public static boolean isBackgroundEffect() {
        return true;
    }

    public static String getCounterEffectName() {
        return null;
    }
}