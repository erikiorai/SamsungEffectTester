package com.android.keyguard.sec;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.aj.effect.MainActivity;
import com.aj.effect.R;
import com.samsung.android.visualeffect.EffectView;
import com.samsung.android.visualeffect.IEffectListener;

import java.util.HashMap;

/* loaded from: classes.dex */
public class KeyguardEffectViewGeometricMosaic extends EffectView implements KeyguardEffectViewBase {
    private static final int DRAG_SOUND_PATH = R.raw.ve_geometricmosaic_drag; //"/system/media/audio/ui/ve_geometricmosaic_drag.ogg";
    public static final int MSG_READY = 0;
    private static final int TAB_SOUND_PATH = R.raw.ve_geometricmosaic_tap; //"/system/media/audio/ui/ve_geometricmosaic_tap.ogg";
    private static final String TAG = "GeometricMosaic_Keyguard";
    private static final int UNLOCK_SOUND_PATH = R.raw.ve_geometricmosaic_unlock; //"/system/media/audio/ui/ve_geometricmosaic_unlock.ogg";
    final int SOUND_ID_DRAG = 1;
    final int SOUND_ID_TAB = 0;
    final int SOUND_ID_UNLOCK = 2;
    final int SOUND_ID_LOCK = 3;
    private final long UNLOCK_SOUND_PLAY_TIME;
    private IEffectListener callBackListener;
    private float dragSoudMinusOffset;
    private float dragSoudVolume;
    private int dragStreamID;
    private boolean isFadeOutSound;
    private boolean isSystemSoundChecked;
    private boolean isUnlocked;
    private float leftVolumeMax;
    private Context mContext;
    private EffectHandler mHandler;
    private ImageView mImageView;
    long mLongPressTime;
    protected Message mMsg;
    private SoundPool mSoundPool;
    private Runnable releaseSoundRunnable;
    private float rightVolumeMax;
    private int[] sounds;
    private long touchDownTime;
    private long touchMoveDiffTime;
    private static final int LOCK_SOUND_PATH = R.raw.ve_geometricmosaic_lock; //"/system/media/audio/ui/ve_geometricmosaic_lock.ogg";

    public KeyguardEffectViewGeometricMosaic(Context context) {
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
        this.isUnlocked = false;
        init(context);
    }

    private void init(Context context) {
        Log.d(TAG, "KeyguardEffectViewGeometricMosaic Constructor");
        this.mContext = context;
        setEffect(1);
        this.sounds = new int[4];
        this.mImageView = new ImageView(this.mContext);
        this.mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        addView(this.mImageView, -1, -1);
        if (this.mHandler == null) {
            Log.d(TAG, "new SoundHandler()");
            this.mHandler = new EffectHandler();
        }
        this.callBackListener = new IEffectListener() { // from class: com.android.keyguard.sec.effect.KeyguardEffectViewGeometricMosaic.1
            public void onReceive(int status, HashMap<?, ?> params) {
                if (status == 0 && KeyguardEffectViewGeometricMosaic.this.mHandler != null) {
                    KeyguardEffectViewGeometricMosaic.this.mMsg = KeyguardEffectViewGeometricMosaic.this.mHandler.obtainMessage();
                    KeyguardEffectViewGeometricMosaic.this.mMsg.what = 0;
                    KeyguardEffectViewGeometricMosaic.this.mHandler.sendMessage(KeyguardEffectViewGeometricMosaic.this.mMsg);
                }
            }
        };
        setListener(this.callBackListener);
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
        postDelayed(new Runnable() { // from class: com.android.keyguard.sec.effect.KeyguardEffectViewGeometricMosaic.2
            @Override // java.lang.Runnable
            public void run() {
                KeyguardEffectViewGeometricMosaic.this.clearScreen();
            }
        }, 400L);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void update() {
        Log.i(TAG, "update");
        /* todo BitmapDrawable newBitmapDrawable = KeyguardEffectViewUtil.getCurrentWallpaper(this.mContext);
        if (newBitmapDrawable == null) {
            Log.i(TAG, "newBitmapDrawable  is null");
            return;
        }
        Bitmap originBitmap = newBitmapDrawable.getBitmap();*/
        Bitmap originBitmap = MainActivity.bitm;
        if (originBitmap == null) {
            Log.d(TAG, "originBitmap is null");
            return;
        }
        if (this.mImageView != null) {
            this.mImageView.setImageBitmap(originBitmap);
        }
        setBitmap(originBitmap);
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

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "onDetachedFromWindow");
        if (this.mHandler != null) {
            this.mHandler.removeMessages(0);
            this.mHandler = null;
        }
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        Log.d(TAG, "onWindowFocusChanged - " + hasWindowFocus);
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
        if (this.mSoundPool == null) { // todo KeyguardUpdateMonitor.getInstance(this.mContext).hasBootCompleted() &&
            Log.d(TAG, "sound : new SoundPool");
            AudioAttributes attr = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
            this.mSoundPool = new SoundPool.Builder().setMaxStreams(10).setAudioAttributes(attr).build();
            this.sounds[SOUND_ID_TAB] = this.mSoundPool.load(mContext, TAB_SOUND_PATH, 1);
            this.sounds[SOUND_ID_DRAG] = this.mSoundPool.load(mContext, DRAG_SOUND_PATH, 1);
            this.sounds[SOUND_ID_UNLOCK] = this.mSoundPool.load(mContext, UNLOCK_SOUND_PATH, 1);
            sounds[SOUND_ID_LOCK] = mSoundPool.load(mContext, LOCK_SOUND_PATH, 1);
            this.mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() { // from class: com.android.keyguard.sec.effect.KeyguardEffectViewGeometricMosaic.3
                @Override // android.media.SoundPool.OnLoadCompleteListener
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    Log.d(KeyguardEffectViewGeometricMosaic.TAG, "sound : onLoadComplete");
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
        this.releaseSoundRunnable = new Runnable() { // from class: com.android.keyguard.sec.effect.KeyguardEffectViewGeometricMosaic.4
            @Override // java.lang.Runnable
            public void run() {
                if (KeyguardEffectViewGeometricMosaic.this.mSoundPool != null) {
                    Log.d(KeyguardEffectViewGeometricMosaic.TAG, "GeometricMosaic sound : release SoundPool");
                    KeyguardEffectViewGeometricMosaic.this.mSoundPool.release();
                    KeyguardEffectViewGeometricMosaic.this.mSoundPool = null;
                }
                KeyguardEffectViewGeometricMosaic.this.releaseSoundRunnable = null;
            }
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
                postDelayed(new Runnable() { // from class: com.android.keyguard.sec.effect.KeyguardEffectViewGeometricMosaic.5
                    @Override // java.lang.Runnable
                    public void run() {
                        KeyguardEffectViewGeometricMosaic.this.fadeOutSound();
                    }
                }, 10L);
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
            return;
        }
        Bitmap bmp2 = KeyguardEffectViewUtil.getPreferredConfigBitmap(bmp, Bitmap.Config.ARGB_8888);
        if (this.mImageView != null) {
            this.mImageView.setImageBitmap(bmp2);
        }
        setBitmap(bmp2);
    }

    public static boolean isBackgroundEffect() {
        return true;
    }

    public static String getCounterEffectName() {
        return null;
    }

    /* loaded from: classes.dex */
    public class EffectHandler extends Handler {
        public EffectHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Log.d(KeyguardEffectViewGeometricMosaic.TAG, "removeView mImageView");
                    KeyguardEffectViewGeometricMosaic.this.mImageView.setImageBitmap(null);
                    KeyguardEffectViewGeometricMosaic.this.removeView(KeyguardEffectViewGeometricMosaic.this.mImageView);
                    KeyguardEffectViewGeometricMosaic.this.mImageView = null;
                    return;
                default:
                    return;
            }
        }
    }
}