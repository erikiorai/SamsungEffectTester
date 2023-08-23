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
public class KeyguardEffectViewWaterColor extends EffectView implements KeyguardEffectViewBase {
    static final int MSG_READY = 1;
    private static final String TAG = "WaterColor_Keyguard";
    private static final int TAP_SOUND_PATH = R.raw.watercolor_tap; //"/system/media/audio/ui/ve_watercolour_tap.ogg";
    private static final int UNLOCK_SOUND_PATH = R.raw.watercolor_unlock; //"/system/media/audio/ui/ve_watercolour_unlock.ogg";
    final int SOUND_ID_TAB;
    final int SOUND_ID_UNLOCK;
    private final long UNLOCK_SOUND_PLAY_TIME;
    private IEffectListener callBackListener;
    private int cpuMaxValue;
    private int gpuMaxValue;
    private boolean hasWindowFocus;
    private boolean isSystemSoundChecked;
    private boolean isUnlocked;
    private float leftVolumeMax;
    private Context mContext;
    private EffectHandler mHandler;
    ImageView mImageView;
    long mLongPressTime;
    Message mMsg;
    private SoundPool mSoundPool;
    private Runnable releaseSoundRunnable;
    private float rightVolumeMax;
    private int[] sounds;
    private long touchDownTime;
    private long touchMoveDiffTime;
    private static final String SILENCE_SOUND_PATH = "/system/media/audio/ui/ve_silence.ogg";

    public KeyguardEffectViewWaterColor(Context context) {
        super(context);
        this.mSoundPool = null;
        this.sounds = null;
        this.releaseSoundRunnable = null;
        this.UNLOCK_SOUND_PLAY_TIME = 2000L;
        this.touchDownTime = 0L;
        this.touchMoveDiffTime = 0L;
        this.leftVolumeMax = 1.0f;
        this.rightVolumeMax = 1.0f;
        this.SOUND_ID_TAB = 0;
        this.SOUND_ID_UNLOCK = 1;
        this.isSystemSoundChecked = true;
        this.mLongPressTime = 411L;
        this.isUnlocked = false;
        this.mImageView = null;
        this.hasWindowFocus = true;
        init(context);
    }

    private void init(Context context) {
        Log.d(TAG, "KeyguardEffectViewWaterColor Constructor");
        this.mContext = context;
        setEffect(5);
        this.sounds = new int[2];
        this.mImageView = new ImageView(this.mContext);
        this.mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        addView(this.mImageView, -1, -1);
        if (this.mHandler == null) {
            Log.d(TAG, "new SoundHandler()");
            this.mHandler = new EffectHandler();
        }
        this.callBackListener = new IEffectListener() { // from class: com.android.keyguard.sec.effect.KeyguardEffectViewWaterColor.1
            public void onReceive(int status, HashMap<?, ?> params) {
                if (status == 0 && KeyguardEffectViewWaterColor.this.mHandler != null) {
                    KeyguardEffectViewWaterColor.this.mMsg = KeyguardEffectViewWaterColor.this.mHandler.obtainMessage();
                    KeyguardEffectViewWaterColor.this.mMsg.what = 1;
                    KeyguardEffectViewWaterColor.this.mHandler.sendMessage(KeyguardEffectViewWaterColor.this.mMsg);
                }
            }
        };
        setListener(this.callBackListener);
        /*if (this.useGPUMaxClock) {
            VisualEffectDVFS.setLimit(this.mContext, 17, this.gpuMaxValue, 40000);
        }
        if (this.useCPUMaxClock) {
            VisualEffectDVFS.setLimit(this.mContext, 13, this.cpuMaxValue, 40000);
        }*/
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
        postDelayed(new Runnable() { // from class: com.android.keyguard.sec.effect.KeyguardEffectViewWaterColor.2
            @Override // java.lang.Runnable
            public void run() {
                KeyguardEffectViewWaterColor.this.clearScreen();
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
        /*BitmapDrawable newBitmapDrawable = KeyguardEffectViewUtil.getCurrentWallpaper(this.mContext);
        if (newBitmapDrawable == null) {
            Log.i(TAG, "newBitmapDrawable  is null");
            return;
        }*/
        Bitmap originBitmap = MainActivity.bitm; //newBitmapDrawable.getBitmap();
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
        playSound(1);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void playLockSound() {
        playSound(SOUND_ID_UNLOCK);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public boolean handleTouchEvent(View view, MotionEvent event) {
        if (!this.isUnlocked) {
            int action = event.getActionMasked();
            if (action == 0) {
                Log.i(TAG, "handleTouchEvent action : " + action);
                stopReleaseSound();
                this.touchDownTime = System.currentTimeMillis();
                if (this.mSoundPool == null) {
                    Log.d(TAG, "ACTION_DOWN, mSoundPool == null");
                    makeSound();
                    checkSound();
                }
                Log.d(TAG, "SOUND PLAY SOUND_ID_TAB");
                playSound(0);
            } else if (action != 2 && (action == 1 || action == 3 || action == 4)) {
                Log.i(TAG, "handleTouchEvent action : " + action);
                this.touchMoveDiffTime = System.currentTimeMillis() - this.touchDownTime;
                if (this.touchMoveDiffTime > this.mLongPressTime) {
                    playSound(0);
                }
            }
            handleTouchEvent(event, view);
        }
        return true;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public boolean handleTouchEventForPatternLock(View view, MotionEvent event) {
        int action = event.getActionMasked();
        MotionEvent eventForPattern = MotionEvent.obtain(event);
        if (action == 0) {
            Log.i(TAG, "handleTouchEventForPatternLock action DOWN : " + action);
            eventForPattern.setAction(9);
        } else if (action == 2) {
            eventForPattern.setAction(7);
        } else if (action == 1 || action == 3 || action == 4) {
            Log.i(TAG, "handleTouchEventForPatternLock action UP : " + action);
            eventForPattern.setAction(10);
        }
        HashMap<String, MotionEvent> map = new HashMap<>();
        map.put("MotionEvent", eventForPattern);
        handleTouchEvent(event, view);
        eventForPattern.recycle();
        return true;
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
            this.mHandler.removeMessages(1);
            this.mHandler = null;
        }
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
        if (hasWindowFocus || !this.isUnlocked) {
        }
    }

    private void makeSound() {
        stopReleaseSound();
        if (this.mSoundPool == null) {
            Log.d(TAG, "sound : new SoundPool");
            AudioAttributes attr = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
            this.mSoundPool = new SoundPool.Builder().setMaxStreams(10).setAudioAttributes(attr).build();
            this.sounds[0] = this.mSoundPool.load(mContext, TAP_SOUND_PATH, 1);
            this.sounds[1] = this.mSoundPool.load(mContext, UNLOCK_SOUND_PATH, 1);
            this.mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() { // from class: com.android.keyguard.sec.effect.KeyguardEffectViewWaterColor.3
                @Override // android.media.SoundPool.OnLoadCompleteListener
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    Log.d(KeyguardEffectViewWaterColor.TAG, "sound : onLoadComplete");
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
        this.releaseSoundRunnable = new Runnable() { // from class: com.android.keyguard.sec.effect.KeyguardEffectViewWaterColor.4
            @Override // java.lang.Runnable
            public void run() {
                if (KeyguardEffectViewWaterColor.this.mSoundPool != null) {
                    Log.d(KeyguardEffectViewWaterColor.TAG, "WaterColor sound : release SoundPool");
                    KeyguardEffectViewWaterColor.this.mSoundPool.release();
                    KeyguardEffectViewWaterColor.this.mSoundPool = null;
                }
                KeyguardEffectViewWaterColor.this.releaseSoundRunnable = null;
            }
        };
        postDelayed(this.releaseSoundRunnable, 2000L);
    }

    private void checkSound() {
        ContentResolver cr = this.mContext.getContentResolver();
        int result = 0;
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
            this.mSoundPool.play(this.sounds[soundId], this.leftVolumeMax, this.rightVolumeMax, 0, 0, 1.0f);
        }
    }

    /* loaded from: classes.dex */
    public class EffectHandler extends Handler {
        public EffectHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Log.d(KeyguardEffectViewWaterColor.TAG, "removeView mImageView");
                    KeyguardEffectViewWaterColor.this.mImageView.setImageBitmap(null);
                    KeyguardEffectViewWaterColor.this.removeView(KeyguardEffectViewWaterColor.this.mImageView);
                    KeyguardEffectViewWaterColor.this.mImageView = null;
                    return;
                default:
                    return;
            }
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
}