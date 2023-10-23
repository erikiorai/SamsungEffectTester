package com.android.keyguard.sec;

import android.app.KeyguardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.aj.effect.MainActivity;
import com.aj.effect.R;
import com.aj.effect.Utils;
import com.samsung.android.visualeffect.EffectDataObj;
import com.samsung.android.visualeffect.EffectView;
import com.samsung.android.visualeffect.IEffectListener;

import java.io.InputStream;
import java.util.HashMap;

/* loaded from: classes.dex */
public class KeyguardEffectViewSparklingBubbles extends EffectView implements KeyguardEffectViewBase {
    private static final int DRAG_SOUND_PATH = R.raw.ve_sparklingbubbles_drag; //"/system/media/audio/ui/ve_sparklingbubbles_drag.ogg";
    private static final int TAP_SOUND_PATH = R.raw.ve_sparklingbubbles_tap; //"/system/media/audio/ui/ve_sparklingbubbles_tap.ogg";
    private final boolean DBG;
    final int SOUND_ID_DRAG;
    final int SOUND_ID_TAB;
    final int SOUND_ID_LOCK = 2;
    final int SOUND_ID_UNLOCK = 3;
    private final String TAG;
    private final long UNLOCK_SOUND_PLAY_TIME;
    private float dragSoudMinusOffset;
    private float dragSoudVolume;
    private int dragStreamID;
    private boolean hasWindowFocus;
    private boolean isEmptyRender;
    private boolean isFadeOutSound;
    private boolean isSystemSoundChecked;
    private boolean isUnlocked;
    KeyguardManager keyguardManager;
    private float leftVolumeMax;
    private Context mContext;
    private IEffectListener mIEffectListener;
    //private KeyguardWallpaperMediator.KeyguardWindowCallback mKeyguardWindowCallback;
    long mLongPressTime;
    private float mPreTouchX;
    private float mPreTouchY;
    private SoundPool mSoundPool;
    private boolean mTouchFlagForMobileKeyboard;
    private String mWallpaperPath;
    private int prevOrientation;
    private Runnable releaseSoundRunnable;
    private float rightVolumeMax;
    private int[] sounds;
    private long touchDownTime;
    private long touchMoveDiffTime;
    private int windowHeight;
    private int windowWidth;
    private static final int LOCK_SOUND_PATH = R.raw.ve_sparklingbubbles_lock; //"/system/media/audio/ui/ve_sparklingbubbles_lock.ogg";
    private static final int UNLOCK_SOUND_PATH = R.raw.ve_sparklingbubbles_unlock; //"/system/media/audio/ui/ve_sparklingbubbles_unlock.ogg";

    public KeyguardEffectViewSparklingBubbles(Context context) {
        super(context);
        this.TAG = "SparklingBubbles_Keyguard";
        this.mWallpaperPath = null;
        this.mSoundPool = null;
        this.sounds = null;
        this.releaseSoundRunnable = null;
        this.UNLOCK_SOUND_PLAY_TIME = 2000L;
        this.touchDownTime = 0L;
        this.touchMoveDiffTime = 0L;
        this.leftVolumeMax = 1.0f;
        this.rightVolumeMax = 1.0f;
        this.SOUND_ID_TAB = 0;
        this.SOUND_ID_DRAG = 1;
        this.dragStreamID = 0;
        this.mLongPressTime = 1100L;
        this.dragSoudVolume = 1.0f;
        this.dragSoudMinusOffset = 0.04f;
        this.isFadeOutSound = false;
        this.isSystemSoundChecked = true;
        this.DBG = true;
        this.isUnlocked = false;
        this.prevOrientation = -1;
        this.windowWidth = 0;
        this.windowHeight = 0;
        this.hasWindowFocus = false;
        this.mTouchFlagForMobileKeyboard = false;
        this.isEmptyRender = true;
        init(context);
    }

    private void init(Context context) {
        Log.d("SparklingBubbles_Keyguard", "KeyguardEffectViewSparklingBubbles Constructor mWallpaperProcessSeparated = " + true);
        this.mContext = context;
        //this.mKeyguardWindowCallback = callback;
        this.mIEffectListener = new IEffectListener() { // from class: com.android.keyguard.sec.effect.KeyguardEffectViewSparklingBubbles.1
            public void onReceive(int status, HashMap<?, ?> params) {
                switch (status) {
                    case 0:
                        /* todo if (KeyguardEffectViewSparklingBubbles.this.mKeyguardWindowCallback != null) {
                            Log.d("SparklingBubbles_Keyguard", "KeyguardEffectViewSparklingBubbles : mKeyguardWindowCallback is called!!!");
                            KeyguardEffectViewSparklingBubbles.this.mKeyguardWindowCallback.onShown();
                            return;
                        }*/
                        return;
                    case 1:
                        KeyguardEffectViewSparklingBubbles.this.update(KeyguardEffectViewSparklingBubbles.this.setBackground(), 1);
                        KeyguardEffectViewSparklingBubbles.this.mTouchFlagForMobileKeyboard = false;
                        Log.d("SparklingBubbles_Keyguard", "mIEffectListener callback, update(1) mTouchFlagForMobileKeyboard = " + KeyguardEffectViewSparklingBubbles.this.mTouchFlagForMobileKeyboard);
                        return;
                    case 2:
                        KeyguardEffectViewSparklingBubbles.this.isEmptyRender = true;
                        return;
                    case 3:
                        KeyguardEffectViewSparklingBubbles.this.isEmptyRender = false;
                        return;
                    default:
                        return;
                }
            }
        };
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager mWindowManager = (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
        Rect rect = Utils.getViewRect(displayMetrics, mWindowManager);
        windowWidth = rect.width();
        windowHeight = rect.height();
        if (true) { // todo if wall separar-ted
            setEffect(14);
        } else {
            setEffect(15);
        }
        EffectDataObj data = new EffectDataObj();
        data.setEffect(14);
        data.sparklingBubblesData.windowWidth = this.windowWidth;
        data.sparklingBubblesData.windowHeight = this.windowHeight;
        data.sparklingBubblesData.mIEffectListener = this.mIEffectListener;
        data.sparklingBubblesData.resBmp = makeResBitmap(R.drawable.blur_mask);
        init(data);
        this.sounds = new int[4];
        //Log.d("SparklingBubbles_Keyguard", "useGPUMaxClock = " + this.useGPUMaxClock + ", gpuMaxValue = " + this.gpuMaxValue);
        //Log.d("SparklingBubbles_Keyguard", "useCPUMinClock = " + this.useCPUMinClock + ", cpuMinValue = " + this.cpuMinValue);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void show() {
        Log.d("SparklingBubbles_Keyguard", "show");
        reInit(null);
        clearScreen();
        this.isUnlocked = false;
        makeSound();
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void reset() {
        Log.d("SparklingBubbles_Keyguard", "reset");
        clearScreen();
        this.isUnlocked = false;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void cleanUp() {
        Log.d("SparklingBubbles_Keyguard", "cleanUp");
        stopReleaseSound();
        releaseSound();
        postDelayed(new Runnable() { // from class: com.android.keyguard.sec.effect.KeyguardEffectViewSparklingBubbles.2
            @Override // java.lang.Runnable
            public void run() {
                KeyguardEffectViewSparklingBubbles.this.clearScreen();
            }
        }, 0L);
        this.isUnlocked = false;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void update() {
        Log.d("SparklingBubbles_Keyguard", "update(0)");
        update(setBackground(), 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void update(Bitmap bmp, int mode) {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("Bitmap", bmp);
        map.put("Mode", Integer.valueOf(mode));
        handleCustomEvent(0, map);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void screenTurnedOn() {
        Log.d("SparklingBubbles_Keyguard", "screenTurnedOn");
        handleCustomEvent(4, null);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void screenTurnedOff() {
        Log.d("SparklingBubbles_Keyguard", "screenTurnedOff");
        handleCustomEvent(3, null);
        this.isUnlocked = false;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void showUnlockAffordance(long startDelay, Rect rect) {
        Log.i("SparklingBubbles_Keyguard", "showUnlockAffordance");
        HashMap<Object, Object> map = new HashMap<>();
        map.put("StartDelay", Long.valueOf(startDelay));
        map.put("Rect", rect);
        handleCustomEvent(1, map);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public long getUnlockDelay() {
        return 400L;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void handleUnlock(View view, MotionEvent event) {
        Log.i("SparklingBubbles_Keyguard", "handleUnlock");
        handleCustomEvent(2, null);
        this.isUnlocked = true;
        playSound(SOUND_ID_UNLOCK);
        this.dragSoudMinusOffset = 0.059f;
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
        if (this.isUnlocked || this.mTouchFlagForMobileKeyboard) {
            Log.i("SparklingBubbles_Keyguard", "handleTouchEvent return : isUnlocked = " + this.isUnlocked + ", mTouchFlag" + this.mTouchFlagForMobileKeyboard);
        } else {
            int action = event.getActionMasked();
            handleTouchEvent(event, view);
            if (action == 0) {
                Log.i("SparklingBubbles_Keyguard", "ACTION_DOWN, mTouchFlag" + this.mTouchFlagForMobileKeyboard);
                this.dragSoudVolume = 1.0f;
                this.isFadeOutSound = false;
                stopReleaseSound();
                this.touchDownTime = System.currentTimeMillis();
                if (this.mSoundPool == null) {
                    Log.d("SparklingBubbles_Keyguard", "ACTION_DOWN, mSoundPool == null");
                    makeSound();
                    checkSound();
                }
                Log.d("SparklingBubbles_Keyguard", "SOUND PLAY SOUND_ID_TAB");
                playSound(0);
                if (this.dragStreamID != 0) {
                    if (this.mSoundPool != null) {
                        this.mSoundPool.stop(this.dragStreamID);
                    }
                    this.dragStreamID = 0;
                }
                this.mPreTouchX = event.getX();
                this.mPreTouchY = event.getY();
            } else if (action == 2) {
                if (this.dragStreamID == 0) {
                    this.dragSoudVolume = 1.0f;
                    this.isFadeOutSound = false;
                    this.touchMoveDiffTime = System.currentTimeMillis() - this.touchDownTime;
                    if (this.touchMoveDiffTime > this.mLongPressTime && this.touchDownTime != 0 && Math.sqrt(Math.pow(this.mPreTouchX - event.getX(), 2.0d) + Math.pow(this.mPreTouchY - event.getY(), 2.0d)) >= 120.0d) {
                        Log.d("SparklingBubbles_Keyguard", "SOUND PLAY SOUND_ID_DRAG touchMoveDiff = " + this.touchMoveDiffTime);
                        playSound(1);
                        this.mPreTouchX = event.getX();
                        this.mPreTouchY = event.getY();
                    }
                } else if (!this.isEmptyRender) {
                    if (this.dragStreamID != 0) {
                        this.dragSoudMinusOffset = 0.039f;
                        this.isFadeOutSound = true;
                        if (this.dragSoudVolume == 1.0f) {
                            fadeOutSound();
                        }
                    }
                } else if (this.isFadeOutSound) {
                    this.isFadeOutSound = false;
                    this.dragSoudVolume = 1.0f;
                    if (this.dragStreamID != 0) {
                        if (this.mSoundPool != null) {
                            this.mSoundPool.stop(this.dragStreamID);
                        }
                        this.dragStreamID = 0;
                    }
                }
            } else if (action == 1 || action == 3 || action == 4) {
                Log.i("SparklingBubbles_Keyguard", "handleTouchEvent action : " + action);
                if (this.dragStreamID != 0) {
                    this.dragSoudMinusOffset = 0.039f;
                    this.isFadeOutSound = true;
                    if (this.dragSoudVolume == 1.0f) {
                        fadeOutSound();
                    }
                }
            }
        }
        return true;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public boolean handleTouchEventForPatternLock(View view, MotionEvent event) {
        return true;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void setHidden(boolean isHidden) {
        Log.d("SparklingBubbles_Keyguard", "setHidden() : " + isHidden);
        if (!isHidden) {
            Log.d("SparklingBubbles_Keyguard", "setHidden() - call screenTurnedOn() cause by SHOW_WHEN_LOCKED");
            screenTurnedOn();
        }
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public boolean handleHoverEvent(View view, MotionEvent event) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap setBackground() {
        Log.d("SparklingBubbles_Keyguard", "setBackground");
        /*BitmapDrawable newBitmapDrawable;// todo KeyguardEffectViewUtil.getCurrentWallpaper(this.mContext);
        if (newBitmapDrawable == null) {
            Log.i("SparklingBubbles_Keyguard", "newBitmapDrawable  is null");
            //return null;
        }
        Bitmap pBitmap = newBitmapDrawable.getBitmap();*/
        Bitmap pBitmap = MainActivity.bitm;
        if (pBitmap == null) {
            Log.i("SparklingBubbles_Keyguard", "pBitmap  is null");
            return pBitmap;
        }
        Log.d("SparklingBubbles_Keyguard", "pBitmap.width = " + pBitmap.getWidth() + ", pBitmap.height = " + pBitmap.getHeight());
        return pBitmap;
    }

    private Bitmap makeResBitmap(int res) {
        Bitmap result = null;
        try {
            InputStream is = this.mContext.getResources().openRawResource(res);
            result = BitmapFactory.decodeStream(is);
            is.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        Log.d("SparklingBubbles_Keyguard", "onWindowFocusChanged : hasWindowFocus - " + hasWindowFocus);
        if (!hasWindowFocus) {
            /*if (this.useGPUMaxClock && !hasWindowFocus) {
                VisualEffectDVFS.release(17);
            }
            if (this.useCPUMinClock && !hasWindowFocus) {
                VisualEffectDVFS.release(12);
            }*/
            if (!this.isUnlocked && this.dragStreamID != 0) {
                this.dragSoudMinusOffset = 0.039f;
                this.isFadeOutSound = true;
                if (this.dragSoudVolume == 1.0f) {
                    fadeOutSound();
                }
            }
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d("SparklingBubbles_Keyguard", "onDetachedFromWindow");
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d("SparklingBubbles_Keyguard", "onAttachedToWindow");
    }

    private void makeSound() {
        stopReleaseSound();
        if (this.mSoundPool == null) { // (KeyguardProperties.isEffectProcessSeparated() || KeyguardUpdateMonitor.getInstance(this.mContext).hasBootCompleted()) &&
            Log.d("SparklingBubbles_Keyguard", "sound : new SoundPool");
            AudioAttributes attr = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
            this.mSoundPool = new SoundPool.Builder().setMaxStreams(10).setAudioAttributes(attr).build();
            this.sounds[SOUND_ID_TAB] = this.mSoundPool.load(mContext, TAP_SOUND_PATH, 1);
            this.sounds[SOUND_ID_DRAG] = this.mSoundPool.load(mContext, DRAG_SOUND_PATH, 1);
            sounds[SOUND_ID_LOCK] = mSoundPool.load(mContext, LOCK_SOUND_PATH, 1);
            sounds[SOUND_ID_UNLOCK] = mSoundPool.load(mContext, UNLOCK_SOUND_PATH, 1);
            this.mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() { // from class: com.android.keyguard.sec.effect.KeyguardEffectViewSparklingBubbles.3
                @Override // android.media.SoundPool.OnLoadCompleteListener
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    Log.d("SparklingBubbles_Keyguard", "sound : onLoadComplete");
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
        this.releaseSoundRunnable = new Runnable() { // from class: com.android.keyguard.sec.effect.KeyguardEffectViewSparklingBubbles.4
            @Override // java.lang.Runnable
            public void run() {
                if (KeyguardEffectViewSparklingBubbles.this.mSoundPool != null) {
                    Log.d("SparklingBubbles_Keyguard", "Sparklingbubbles sound : release SoundPool");
                    KeyguardEffectViewSparklingBubbles.this.mSoundPool.release();
                    KeyguardEffectViewSparklingBubbles.this.mSoundPool = null;
                }
                KeyguardEffectViewSparklingBubbles.this.releaseSoundRunnable = null;
            }
        };
        postDelayed(this.releaseSoundRunnable, 2000L);
    }

    private void checkSound() {
        ContentResolver cr = this.mContext.getContentResolver();
        int result = Settings.System.getInt(cr, "lockscreen_sounds_enabled", -2);
        if (result == 1) {
            this.isSystemSoundChecked = true;
        } else {
            this.isSystemSoundChecked = false;
        }
    }

    private void playSound(int soundId) {
        checkSound();
        Log.d("SparklingBubbles_Keyguard", "SOUND PLAY mSoundPool = " + this.mSoundPool + ", isSystemSoundChecked = " + this.isSystemSoundChecked);
        if (this.isSystemSoundChecked && this.mSoundPool != null) {
            Log.d("SparklingBubbles_Keyguard", "SOUND PLAY soundId = " + soundId);
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
                postDelayed(new Runnable() { // from class: com.android.keyguard.sec.effect.KeyguardEffectViewSparklingBubbles.5
                    @Override // java.lang.Runnable
                    public void run() {
                        KeyguardEffectViewSparklingBubbles.this.fadeOutSound();
                    }
                }, 10L);
                return;
            }
            Log.d("SparklingBubbles_Keyguard", "SOUND STOP because UP or Unlock");
            stopReleaseSound();
        }
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void setContextualWallpaper(Bitmap bmp) {
        Log.d("SparklingBubbles_Keyguard", "setContextualWallpaper");
        if (bmp == null) {
            Log.d("SparklingBubbles_Keyguard", "bmp is null" + bmp);
            return;
        }
        Log.d("SparklingBubbles_Keyguard", "changeBackground()");
        Bitmap bmp2 = KeyguardEffectViewUtil.getPreferredConfigBitmap(bmp, Bitmap.Config.ARGB_8888);
        boolean isPreloadedWallpaper = Settings.System.getInt(this.mContext.getContentResolver(), "lockscreen_wallpaper_transparent", 1) == 1;
        /* todo if (KeyguardProperties.isSupportBlendedFilter() && !isPreloadedWallpaper) {
            Log.d("SparklingBubbles_Keyguard", "setContextualWallpaper - VignettingEffect is applyed");
            Bitmap temp = VignettingEffect.applyBlendedFilter(bmp2.copy(Bitmap.Config.ARGB_8888, true));
            update(temp, 0);
            return;
        }*/
        update(bmp2, 0);
    }

    public static boolean isBackgroundEffect() {
        return true;
    }

    public static String getCounterEffectName() {
        return null;
    }

    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        super.onSizeChanged(width, height, oldw, oldh);
        Log.i("SparklingBubbles_Keyguard", "onSizeChanged, width = " + width + ", height = " + height + ", oldw = " + oldw + ", oldh =" + oldh);
        int miniMumLenth = Math.max(this.windowWidth, this.windowHeight) / 5;
        if (width < miniMumLenth || height < miniMumLenth || oldw == 0 || oldh == 0 || Math.min(this.windowWidth, this.windowHeight) != width) {
            return;
        }
        if ((this.windowHeight > height || this.windowHeight == height) && width == oldw) {
            HashMap<Object, Object> map = new HashMap<>();
            map.put("CustomEvent", "ForceDirty");
            map.put("EventObject", new Object());
            handleCustomEvent(99, map);
            this.mTouchFlagForMobileKeyboard = true;
            Log.d("SparklingBubbles_Keyguard", "onSizeChanged called!!! mTouchFlagForMobileKeyboard = " + this.mTouchFlagForMobileKeyboard);
        }
    }
}