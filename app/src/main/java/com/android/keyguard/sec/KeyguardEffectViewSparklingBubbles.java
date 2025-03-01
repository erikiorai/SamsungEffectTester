package com.android.keyguard.sec;

import static com.android.keyguard.sec.KeyguardEffectViewController.mRes;

import android.app.KeyguardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

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
    private final String TAG = "SparklingBubbles_Keyguard";;
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
        Log.d(TAG, "KeyguardEffectViewSparklingBubbles Constructor mWallpaperProcessSeparated = " + true);
        this.mContext = context;
        //this.mKeyguardWindowCallback = callback;
        // from class: com.android.keyguard.sec.effect.KeyguardEffectViewSparklingBubbles.1
        this.mIEffectListener = (status, params) -> {
            switch (status) {
                case 0:
                    /* todo if (KeyguardEffectViewSparklingBubbles.this.mKeyguardWindowCallback != null) {
                        Log.d(TAG, "KeyguardEffectViewSparklingBubbles : mKeyguardWindowCallback is called!!!");
                        KeyguardEffectViewSparklingBubbles.this.mKeyguardWindowCallback.onShown();
                        return;
                    }*/
                    return;
                case 1:
                    KeyguardEffectViewSparklingBubbles.this.update(KeyguardEffectViewSparklingBubbles.this.setBackground(), 1);
                    KeyguardEffectViewSparklingBubbles.this.mTouchFlagForMobileKeyboard = false;
                    Log.d(TAG, "mIEffectListener callback, update(1) mTouchFlagForMobileKeyboard = " + KeyguardEffectViewSparklingBubbles.this.mTouchFlagForMobileKeyboard);
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
        //Log.d(TAG, "useGPUMaxClock = " + this.useGPUMaxClock + ", gpuMaxValue = " + this.gpuMaxValue);
        //Log.d(TAG, "useCPUMinClock = " + this.useCPUMinClock + ", cpuMinValue = " + this.cpuMinValue);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void show() {
        Log.d(TAG, "show");
        reInit(null);
        clearScreen();
        this.isUnlocked = false;
        makeSound();
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void reset() {
        Log.d(TAG, "reset");
        clearScreen();
        this.isUnlocked = false;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void cleanUp() {
        Log.d(TAG, "cleanUp");
        stopReleaseSound();
        releaseSound();
        postDelayed(KeyguardEffectViewSparklingBubbles.this::clearScreen, 0L);
        this.isUnlocked = false;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void update() {
        Log.d(TAG, "update(0)");
        update(setBackground(), 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void update(Bitmap bmp, int mode) {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("Bitmap", bmp);
        map.put("Mode", mode);
        handleCustomEvent(0, map);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void screenTurnedOn() {
        Log.d(TAG, "screenTurnedOn");
        handleCustomEvent(4, null);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void screenTurnedOff() {
        Log.d(TAG, "screenTurnedOff");
        handleCustomEvent(3, null);
        this.isUnlocked = false;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void showUnlockAffordance(long startDelay, Rect rect) {
        Log.i(TAG, "showUnlockAffordance");
        HashMap<Object, Object> map = new HashMap<>();
        map.put("StartDelay", startDelay);
        map.put("Rect", rect);
        handleCustomEvent(1, map);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public long getUnlockDelay() {
        return 400L;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void handleUnlock(View view, MotionEvent event) {
        Log.i(TAG, "handleUnlock");
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
            Log.i(TAG, "handleTouchEvent return : isUnlocked = " + this.isUnlocked + ", mTouchFlag " + this.mTouchFlagForMobileKeyboard);
        } else {
            int action = event.getActionMasked();
            handleTouchEvent(event, view);
            if (action == 0) {
                Log.i(TAG, "ACTION_DOWN, mTouchFlag" + this.mTouchFlagForMobileKeyboard);
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
                this.mPreTouchX = event.getX();
                this.mPreTouchY = event.getY();
            } else if (action == 2) {
                if (this.dragStreamID == 0) {
                    this.dragSoudVolume = 1.0f;
                    this.isFadeOutSound = false;
                    this.touchMoveDiffTime = System.currentTimeMillis() - this.touchDownTime;
                    if (this.touchMoveDiffTime > this.mLongPressTime && this.touchDownTime != 0 && Math.sqrt(Math.pow(this.mPreTouchX - event.getX(), 2.0d) + Math.pow(this.mPreTouchY - event.getY(), 2.0d)) >= 120.0d) {
                        Log.d(TAG, "SOUND PLAY SOUND_ID_DRAG touchMoveDiff = " + this.touchMoveDiffTime);
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
                Log.i(TAG, "handleTouchEvent action : " + action);
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
        Log.d(TAG, "setHidden() : " + isHidden);
        if (!isHidden) {
            Log.d(TAG, "setHidden() - call screenTurnedOn() cause by SHOW_WHEN_LOCKED");
            screenTurnedOn();
        }
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public boolean handleHoverEvent(View view, MotionEvent event) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap setBackground() {
        Log.d(TAG, "setBackground");
        BitmapDrawable newBitmapDrawable = KeyguardEffectViewUtil.getCurrentWallpaper(mContext);
        if (newBitmapDrawable == null) {
            Log.i(TAG, "newBitmapDrawable  is null");
            return null;
        }
        Bitmap pBitmap = newBitmapDrawable.getBitmap();
        if (pBitmap == null) {
            Log.i(TAG, "pBitmap  is null");
            return pBitmap;
        }
        Log.d(TAG, "pBitmap.width = " + pBitmap.getWidth() + ", pBitmap.height = " + pBitmap.getHeight());
        return pBitmap;
    }

    private Bitmap makeResBitmap(int res) {
        Bitmap result = null;
        try {
            InputStream is = mRes.openRawResource(res);
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
        Log.d(TAG, "onWindowFocusChanged : hasWindowFocus - " + hasWindowFocus);
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
        Log.d(TAG, "onDetachedFromWindow");
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "onAttachedToWindow");
    }

    private void makeSound() {
        stopReleaseSound();
        if (this.mSoundPool == null) { // (KeyguardProperties.isEffectProcessSeparated() || KeyguardUpdateMonitor.getInstance(this.mContext).hasBootCompleted()) &&
            Log.d(TAG, "sound : new SoundPool");
            AudioAttributes attr = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
            this.mSoundPool = new SoundPool.Builder().setMaxStreams(10).setAudioAttributes(attr).build();
            this.sounds[SOUND_ID_TAB] = this.mSoundPool.load(mRes.openRawResourceFd(TAP_SOUND_PATH), 1);
            this.sounds[SOUND_ID_DRAG] = this.mSoundPool.load(mRes.openRawResourceFd(DRAG_SOUND_PATH), 1);
            sounds[SOUND_ID_LOCK] = mSoundPool.load(mRes.openRawResourceFd(LOCK_SOUND_PATH), 1);
            sounds[SOUND_ID_UNLOCK] = mSoundPool.load(mRes.openRawResourceFd(UNLOCK_SOUND_PATH), 1);
            // from class: com.android.keyguard.sec.effect.KeyguardEffectViewSparklingBubbles.3
// android.media.SoundPool.OnLoadCompleteListener
            this.mSoundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> Log.d(TAG, "sound : onLoadComplete"));
        }
    }

    private void stopReleaseSound() {
        if (this.releaseSoundRunnable != null) {
            removeCallbacks(this.releaseSoundRunnable);
            this.releaseSoundRunnable = null;
        }
    }

    private void releaseSound() {
        // from class: com.android.keyguard.sec.effect.KeyguardEffectViewSparklingBubbles.4
// java.lang.Runnable
        this.releaseSoundRunnable = () -> {
            if (KeyguardEffectViewSparklingBubbles.this.mSoundPool != null) {
                Log.d(TAG, "Sparklingbubbles sound : release SoundPool");
                KeyguardEffectViewSparklingBubbles.this.mSoundPool.release();
                KeyguardEffectViewSparklingBubbles.this.mSoundPool = null;
            }
            KeyguardEffectViewSparklingBubbles.this.releaseSoundRunnable = null;
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
                // from class: com.android.keyguard.sec.effect.KeyguardEffectViewSparklingBubbles.5
// java.lang.Runnable
                postDelayed(() -> KeyguardEffectViewSparklingBubbles.this.fadeOutSound(), 10L);
                return;
            }
            Log.d(TAG, "SOUND STOP because UP or Unlock");
            stopReleaseSound();
        }
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void setContextualWallpaper(Bitmap bmp) {
        Log.d(TAG, "setContextualWallpaper");
        if (bmp == null) {
            Log.d(TAG, "bmp is null" + bmp);
            return;
        }
        Log.d(TAG, "changeBackground()");
        Bitmap bmp2 = KeyguardEffectViewUtil.getPreferredConfigBitmap(bmp, Bitmap.Config.ARGB_8888);
        boolean isPreloadedWallpaper = Settings.System.getInt(this.mContext.getContentResolver(), "lockscreen_wallpaper_transparent", 1) == 1;
        /* todo if (KeyguardProperties.isSupportBlendedFilter() && !isPreloadedWallpaper) {
            Log.d(TAG, "setContextualWallpaper - VignettingEffect is applyed");
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
        Thread.dumpStack();
        Log.i(TAG, "onSizeChanged, width = " + width + ", height = " + height + ", oldw = " + oldw + ", oldh =" + oldh);
        int miniMumLenth = Math.max(this.windowWidth, this.windowHeight) / 5;
        if (width < miniMumLenth || height < miniMumLenth || oldw == 0 || oldh == 0 || Math.min(this.windowWidth, this.windowHeight) != width) {
            return;
        }
        if ((this.windowHeight > height || this.windowHeight == height) && width == oldw) {
            HashMap<Object, Object> map = new HashMap<>();
            map.put("CustomEvent", "ForceDirty");
            map.put("EventObject", new Object());
            handleCustomEvent(99, map);
            //TODO this.mTouchFlagForMobileKeyboard = true;
            Log.d(TAG, "onSizeChanged called!!! mTouchFlagForMobileKeyboard = " + this.mTouchFlagForMobileKeyboard);
        }
    }
}