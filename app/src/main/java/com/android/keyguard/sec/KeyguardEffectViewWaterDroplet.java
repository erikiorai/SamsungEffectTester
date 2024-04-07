package com.android.keyguard.sec;

import android.app.KeyguardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Handler;
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
public class KeyguardEffectViewWaterDroplet extends EffectView implements KeyguardEffectViewBase, SensorEventListener {
    private static final int TAP_SOUND_PATH = R.raw.ve_waterdroplet_tap; //"/system/media/audio/ui/ve_waterdroplet_tap.ogg";
    private final boolean DBG;
    final int SOUND_ID_TAB;
    final int SOUND_ID_UNLOCK;
    final int SOUND_ID_LOCK = 2;
    private final String TAG;
    private final long UNLOCK_SOUND_PLAY_TIME;
    private boolean isSystemSoundChecked;
    private boolean isUnlocked;
    KeyguardManager keyguardManager;
    private float leftVolumeMax;
    private Context mContext;
    private Handler mHandler = new Handler();
    private IEffectListener mIEffectListener;
    private Sensor mSensor;
    private SensorManager mSensorManager;
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
    private static final int LOCK_SOUND_PATH = R.raw.ve_waterdroplet_lock; //"/system/media/audio/ui/ve_waterdroplet_lock.ogg";
    private static final int UNLOCK_SOUND_PATH = R.raw.ve_waterdroplet_unlock; //"/system/media/audio/ui/ve_waterdroplet_unlock.ogg";

    public KeyguardEffectViewWaterDroplet(Context context) {
        super(context);
        this.TAG = "WaterDroplet_Keyguard";
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
        this.SOUND_ID_UNLOCK = 1;
        this.isSystemSoundChecked = true;
        this.DBG = true;
        this.isUnlocked = false;
        this.prevOrientation = -1;
        this.windowWidth = 0;
        this.windowHeight = 0;
        this.mSensorManager = null;
        this.mSensor = null;
        this.mTouchFlagForMobileKeyboard = false;
        init(context);
    }

    private void init(Context context) {
        Log.d("WaterDroplet_Keyguard", "KeyguardEffectViewWaterDroplet Constructor mWallpaperProcessSeparated = " + true);
        this.mContext = context;
        this.mIEffectListener = new IEffectListener() { // from class: com.android.keyguard.sec.effect.KeyguardEffectViewWaterDroplet.1
            public void onReceive(int status, HashMap<?, ?> params) {
                switch (status) {
                    case 0:
                        /* todo if (KeyguardEffectViewWaterDroplet.this.mKeyguardWindowCallback != null) {
                            Log.d("WaterDroplet_Keyguard", "KeyguardEffectViewWaterDroplet : mKeyguardWindowCallback is called!!!");
                            KeyguardEffectViewWaterDroplet.this.mKeyguardWindowCallback.onShown();
                            return;
                        }*/
                        return;
                    case 1:
                        KeyguardEffectViewWaterDroplet.this.update(KeyguardEffectViewWaterDroplet.this.setBackground(), 1);
                        KeyguardEffectViewWaterDroplet.this.mTouchFlagForMobileKeyboard = false;
                        Log.d("WaterDroplet_Keyguard", "mIEffectListener callback, update(1) mTouchFlagForMobileKeyboard = " + KeyguardEffectViewWaterDroplet.this.mTouchFlagForMobileKeyboard);
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
        if (true) {
            setEffect(12);
        } else {
            setEffect(13);
        }
        EffectDataObj data = new EffectDataObj();
        data.setEffect(12);
        data.dropletData.windowWidth = this.windowWidth;
        data.dropletData.windowHeight = this.windowHeight;
        data.dropletData.mIEffectListener = this.mIEffectListener;
        data.dropletData.resNormal = makeResBitmap(R.drawable.normal_low_z_256);
        if (Math.min(this.windowWidth, this.windowHeight) >= 720) {
            data.dropletData.resEdgeDensity = makeResBitmap(R.drawable.edge_density_720);
        } else {
            data.dropletData.resEdgeDensity = makeResBitmap(R.drawable.edge_density_360);
        }
        init(data);
        this.sounds = new int[3];
        this.mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.mSensor = this.mSensorManager.getDefaultSensor(1);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void show() {
        Log.d("WaterDroplet_Keyguard", "show");
        reInit(null);
        clearScreen();
        this.isUnlocked = false;
        makeSound();
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void reset() {
        Log.d("WaterDroplet_Keyguard", "reset");
        clearScreen();
        this.isUnlocked = false;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void cleanUp() {
        Log.d("WaterDroplet_Keyguard", "cleanUp");
        stopReleaseSound();
        releaseSound();
        postDelayed(new Runnable() { // from class: com.android.keyguard.sec.effect.KeyguardEffectViewWaterDroplet.2
            @Override // java.lang.Runnable
            public void run() {
                KeyguardEffectViewWaterDroplet.this.clearScreen();
            }
        }, 0L);
        this.isUnlocked = false;
        unregisterAccelrometer();
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void update() {
        Log.d("WaterDroplet_Keyguard", "update(0)");
        update(setBackground(), 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void update(Bitmap bmp, int mode) {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("Bitmap", bmp);
        map.put("Mode", mode);
        handleCustomEvent(0, map);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerAccelrometer() {
        Log.d("WaterDroplet_Keyguard", "registerAccelrometer()");
        if (this.mSensorManager != null && this.mSensor != null) {
            this.mSensorManager.registerListener(this, this.mSensor, 2);
        }
    }

    private void unregisterAccelrometer() {
        Log.d("WaterDroplet_Keyguard", "unregisterAccelrometer()");
        if (this.mSensorManager != null) {
            this.mSensorManager.unregisterListener(this);
        }
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void screenTurnedOn() {
        Log.d("WaterDroplet_Keyguard", "screenTurnedOn");
        handleCustomEvent(4, null);
        this.mHandler.postDelayed(this::registerAccelrometer, 10L);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void screenTurnedOff() {
        Log.d("WaterDroplet_Keyguard", "screenTurnedOff");
        handleCustomEvent(3, null);
        this.isUnlocked = false;
        unregisterAccelrometer();
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void showUnlockAffordance(long startDelay, Rect rect) {
        Log.i("WaterDroplet_Keyguard", "showUnlockAffordance");
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
        Log.i("WaterDroplet_Keyguard", "handleUnlock");
        handleCustomEvent(2, null);
        this.isUnlocked = true;
        playSound(SOUND_ID_UNLOCK);
        unregisterAccelrometer();
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void playLockSound() {
        playSound(SOUND_ID_LOCK);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public boolean handleTouchEvent(View view, MotionEvent event) {
        if (this.isUnlocked || this.mTouchFlagForMobileKeyboard) {
            Log.i("WaterDroplet_Keyguard", "handleTouchEvent return : isUnlocked = " + this.isUnlocked + ", mTouchFlag" + this.mTouchFlagForMobileKeyboard);
        } else {
            int action = event.getActionMasked();
            if (action == 0) {
                Log.i("WaterDroplet_Keyguard", "ACTION_DOWN, mTouchFlag" + this.mTouchFlagForMobileKeyboard);
                Log.i("WaterDroplet_Keyguard", "handleTouchEvent action : " + action);
                stopReleaseSound();
                if (this.mSoundPool == null) {
                    Log.d("WaterDroplet_Keyguard", "ACTION_DOWN, mSoundPool == null");
                    makeSound();
                    checkSound();
                }
                Log.d("WaterDroplet_Keyguard", "SOUND PLAY SOUND_ID_TAB");
                playSound(SOUND_ID_TAB);
            } else if (action == 2 || action == 1 || action == 3 || action == 4) {
            }
            handleTouchEvent(event, view);
        }
        return true;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public boolean handleHoverEvent(View view, MotionEvent event) {
        return true;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public boolean handleTouchEventForPatternLock(View view, MotionEvent event) {
        return true;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void setHidden(boolean isHidden) {
        Log.d("WaterDroplet_Keyguard", "setHidden() : " + isHidden);
        if (!isHidden) {
            Log.d("WaterDroplet_Keyguard", "setHidden() - call screenTurnedOn() cause by SHOW_WHEN_LOCKED");
            screenTurnedOn();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap setBackground() {
        Log.d("WaterDroplet_Keyguard", "setBackground");
        /* todo BitmapDrawable newBitmapDrawable = KeyguardEffectViewUtil.getCurrentWallpaper(this.mContext);
        if (newBitmapDrawable == null) {
            Log.i("WaterDroplet_Keyguard", "newBitmapDrawable  is null");
            return null;
        }
        Bitmap pBitmap = newBitmapDrawable.getBitmap(); */
        Bitmap pBitmap = MainActivity.bitm;
        if (pBitmap == null) {
            Log.i("WaterDroplet_Keyguard", "pBitmap  is null");
            return pBitmap;
        }
        Log.d("WaterDroplet_Keyguard", "pBitmap.width = " + pBitmap.getWidth() + ", pBitmap.height = " + pBitmap.getHeight());
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

    private void makeSound() {
        stopReleaseSound();
        if (this.mSoundPool == null) { // (KeyguardProperties.isEffectProcessSeparated() || KeyguardUpdateMonitor.getInstance(this.mContext).hasBootCompleted()) &&
            Log.d("WaterDroplet_Keyguard", "sound : new SoundPool");
            AudioAttributes attr = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
            this.mSoundPool = new SoundPool.Builder().setMaxStreams(10).setAudioAttributes(attr).build();
            this.sounds[0] = this.mSoundPool.load(mContext, TAP_SOUND_PATH, 1);
            sounds[SOUND_ID_UNLOCK] = mSoundPool.load(mContext, UNLOCK_SOUND_PATH, 1);
            sounds[SOUND_ID_LOCK] = mSoundPool.load(mContext, LOCK_SOUND_PATH, 1);
            this.mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() { // from class: com.android.keyguard.sec.effect.KeyguardEffectViewWaterDroplet.4
                @Override // android.media.SoundPool.OnLoadCompleteListener
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    Log.d("WaterDroplet_Keyguard", "sound : onLoadComplete");
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
        this.releaseSoundRunnable = new Runnable() { // from class: com.android.keyguard.sec.effect.KeyguardEffectViewWaterDroplet.5
            @Override // java.lang.Runnable
            public void run() {
                if (KeyguardEffectViewWaterDroplet.this.mSoundPool != null) {
                    Log.d("WaterDroplet_Keyguard", "WaterDroplet sound : release SoundPool");
                    KeyguardEffectViewWaterDroplet.this.mSoundPool.release();
                    KeyguardEffectViewWaterDroplet.this.mSoundPool = null;
                }
                KeyguardEffectViewWaterDroplet.this.releaseSoundRunnable = null;
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
        Log.d("WaterDroplet_Keyguard", "SOUND PLAY mSoundPool = " + this.mSoundPool + ", isSystemSoundChecked = " + this.isSystemSoundChecked);
        if (this.isSystemSoundChecked && this.mSoundPool != null) {
            Log.d("WaterDroplet_Keyguard", "SOUND PLAY soundId = " + soundId);
            this.mSoundPool.play(this.sounds[soundId], this.leftVolumeMax, this.rightVolumeMax, 0, 0, 1.0f);
        }
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        Log.d("WaterDroplet_Keyguard", "onWindowFocusChanged : hasWindowFocus - " + hasWindowFocus);
        if (!hasWindowFocus) {
            unregisterAccelrometer();
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d("WaterDroplet_Keyguard", "onDetachedFromWindow");
        unregisterAccelrometer();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d("WaterDroplet_Keyguard", "onAttachedToWindow");
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent event) {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("CustomEvent", "SensorEvent");
        map.put("EventObject", event);
        handleCustomEvent(99, map);
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void setContextualWallpaper(Bitmap bmp) {
        Log.d("WaterDroplet_Keyguard", "setContextualWallpaper");
        if (bmp == null) {
            Log.d("WaterDroplet_Keyguard", "bmp is null" + bmp);
            return;
        }
        Bitmap bmp2 = KeyguardEffectViewUtil.getPreferredConfigBitmap(bmp, Bitmap.Config.ARGB_8888);
        Log.d("WaterDroplet_Keyguard", "changeBackground()");
        boolean isPreloadedWallpaper = Settings.System.getInt(this.mContext.getContentResolver(), "lockscreen_wallpaper_transparent", 1) == 1;
        /* todo if (KeyguardProperties.isSupportBlendedFilter() && !isPreloadedWallpaper) {
            Log.d("WaterDroplet_Keyguard", "setContextualWallpaper - VignettingEffect is applyed");
            Bitmap temp = VignettingEffect.applyBlendedFilter(bmp2.copy(Bitmap.Config.ARGB_8888, true));
            update(temp, 0);
            return;
        } */
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
        Log.i("WaterDroplet_Keyguard", "onSizeChanged, width = " + width + ", height = " + height + ", oldw = " + oldw + ", oldh =" + oldh);
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
            Log.d("WaterDroplet_Keyguard", "onSizeChanged called!!! mTouchFlagForMobileKeyboard = " + this.mTouchFlagForMobileKeyboard);
        }
    }
}