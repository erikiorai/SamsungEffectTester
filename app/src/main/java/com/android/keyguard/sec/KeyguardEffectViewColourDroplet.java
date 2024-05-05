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

import com.aj.effect.R;
import com.aj.effect.Utils;
import com.samsung.android.visualeffect.EffectDataObj;
import com.samsung.android.visualeffect.EffectView;
import com.samsung.android.visualeffect.IEffectListener;

import java.io.InputStream;
import java.util.HashMap;

/* loaded from: classes.dex */
public class KeyguardEffectViewColourDroplet extends EffectView implements KeyguardEffectViewBase, SensorEventListener {
    private static final int MSG_REGISTER_ACCELROMETER = 999;
    private static final int TAP_SOUND_PATH = R.raw.ve_colourdroplet_tap; //"/system/media/audio/ui/ve_colourdroplet_tap.ogg";
    private final boolean DBG;
    final int SOUND_ID_TAB = 0;
    final int SOUND_ID_UNLOCK = 1;
    final int SOUND_ID_LOCK = 2;
    private final String TAG;
    private final long UNLOCK_SOUND_PLAY_TIME;
    private boolean isSystemSoundChecked;
    private boolean isUnlocked;
    KeyguardManager keyguardManager;
    private float leftVolumeMax;
    private Context mContext;
    private Handler mEffectHandler;
    private IEffectListener mIEffectListener;
    private Sensor mSensor;
    private SensorManager mSensorManager;
    private SoundPool mSoundPool;
    private boolean mTouchFlagForMobileKeyboard;
    private Runnable releaseSoundRunnable;
    private float rightVolumeMax;
    private int[] sounds;
    private int windowHeight;
    private int windowWidth;
    private static final int LOCK_SOUND_PATH = R.raw.ve_colourdroplet_lock; //"/system/media/audio/ui/ve_colourdroplet_lock.ogg";
    private static final int UNLOCK_SOUND_PATH = R.raw.ve_colourdroplet_unlock; //"/system/media/audio/ui/ve_colourdroplet_unlock.ogg";

    public KeyguardEffectViewColourDroplet(Context context) {
        super(context);
        this.TAG = "KeyguardEffectViewColourDroplet";
        this.UNLOCK_SOUND_PLAY_TIME = 2000L;
        this.leftVolumeMax = 1.0f;
        this.rightVolumeMax = 1.0f;
        this.isSystemSoundChecked = true;
        this.DBG = true;
        this.isUnlocked = false;
        this.windowWidth = 0;
        this.windowHeight = 0;
        this.mSensorManager = null;
        this.mSensor = null;
        this.mTouchFlagForMobileKeyboard = false;
        init(context);
    }

    private void init(Context context) {
        Log.d("KeyguardEffectViewColourDroplet", "KeyguardEffectViewColourDroplet Constructor mWallpaperProcessSeparated = " + true);
        this.mContext = context;
        this.mEffectHandler = new Handler();
        //this.mKeyguardWindowCallback = callback;
        // from class: com.android.keyguard.sec.effect.KeyguardEffectViewColourDroplet.1
        this.mIEffectListener = (status, params) -> {
            switch (status) {
                case 0:
                    /*if (KeyguardEffectViewColourDroplet.this.mKeyguardWindowCallback != null) {
                        Log.d("KeyguardEffectViewColourDroplet", "KeyguardEffectViewColourDroplet : mKeyguardWindowCallback is called!!!");
                        KeyguardEffectViewColourDroplet.this.mKeyguardWindowCallback.onShown();
                        return;
                    }*/
                    return;
                case 1:
                    KeyguardEffectViewColourDroplet.this.update(KeyguardEffectViewUtil.getCurrentWallpaper(context).getBitmap(), 1);
                    KeyguardEffectViewColourDroplet.this.mTouchFlagForMobileKeyboard = false;
                    Log.d("KeyguardEffectViewColourDroplet", "mIEffectListener callback, update(1) mTouchFlagForMobileKeyboard = " + KeyguardEffectViewColourDroplet.this.mTouchFlagForMobileKeyboard);
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
        if (true) {
            setEffect(16);
        } else {
            setEffect(17);
        }
        EffectDataObj data = new EffectDataObj();
        data.setEffect(16);
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
        Log.d("KeyguardEffectViewColourDroplet", "show");
        reInit(null);
        clearScreen();
        this.isUnlocked = false;
        makeSound();
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void reset() {
        Log.d("KeyguardEffectViewColourDroplet", "reset");
        clearScreen();
        this.isUnlocked = false;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void cleanUp() {
        Log.d("KeyguardEffectViewColourDroplet", "cleanUp");
        stopReleaseSound();
        releaseSound();
        // from class: com.android.keyguard.sec.effect.KeyguardEffectViewColourDroplet.2
// java.lang.Runnable
        postDelayed(() -> KeyguardEffectViewColourDroplet.this.clearScreen(), 0L);
        this.isUnlocked = false;
        unregisterAccelrometer();
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void update() {
        Log.d("KeyguardEffectViewColourDroplet", "update(0)");
        update(KeyguardEffectViewUtil.getCurrentWallpaper(mContext).getBitmap(), 0);
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
        Log.d("KeyguardEffectViewColourDroplet", "registerAccelrometer()");
        if (this.mSensorManager != null && this.mSensor != null) {
            this.mSensorManager.registerListener(this, this.mSensor, 2);
        }
    }

    private void unregisterAccelrometer() {
        Log.d("KeyguardEffectViewColourDroplet", "unregisterAccelrometer()");
        if (this.mSensorManager != null) {
            this.mSensorManager.unregisterListener(this);
        }
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void screenTurnedOn() {
        Log.d("KeyguardEffectViewColourDroplet", "screenTurnedOn");
        handleCustomEvent(4, null);
        mEffectHandler.postDelayed(this::registerAccelrometer, 10L);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void screenTurnedOff() {
        Log.d("KeyguardEffectViewColourDroplet", "screenTurnedOff");
        handleCustomEvent(3, null);
        this.isUnlocked = false;
        unregisterAccelrometer();
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void showUnlockAffordance(long startDelay, Rect rect) {
        Log.i("KeyguardEffectViewColourDroplet", "showUnlockAffordance");
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
        Log.i("KeyguardEffectViewColourDroplet", "handleUnlock");
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
            Log.i("KeyguardEffectViewColourDroplet", "handleTouchEvent return : isUnlocked = " + this.isUnlocked + ", mTouchFlag" + this.mTouchFlagForMobileKeyboard);
        } else {
            int action = event.getActionMasked();
            if (action == 0) {
                Log.i("KeyguardEffectViewColourDroplet", "ACTION_DOWN, mTouchFlag" + this.mTouchFlagForMobileKeyboard);
                stopReleaseSound();
                if (this.mSoundPool == null) {
                    Log.d("KeyguardEffectViewColourDroplet", "ACTION_DOWN, mSoundPool == null");
                    makeSound();
                    checkSound();
                }
                Log.d("KeyguardEffectViewColourDroplet", "SOUND PLAY SOUND_ID_TAB");
                playSound(0);
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
        Log.d("KeyguardEffectViewColourDroplet", "setHidden() : " + isHidden);
        if (!isHidden) {
            Log.d("KeyguardEffectViewColourDroplet", "setHidden() - call screenTurnedOn() cause by SHOW_WHEN_LOCKED");
            screenTurnedOn();
        }
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
        if (this.mSoundPool == null) { // todo (KeyguardProperties.isEffectProcessSeparated() || KeyguardUpdateMonitor.getInstance(this.mContext).hasBootCompleted()) &&
            Log.d("KeyguardEffectViewColourDroplet", "sound : new SoundPool");
            AudioAttributes attr = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
            this.mSoundPool = new SoundPool.Builder().setMaxStreams(10).setAudioAttributes(attr).build();
            this.sounds[SOUND_ID_TAB] = this.mSoundPool.load(mContext, TAP_SOUND_PATH, 1);
            sounds[SOUND_ID_UNLOCK] = mSoundPool.load(mContext, UNLOCK_SOUND_PATH, 1);
            sounds[SOUND_ID_LOCK] = mSoundPool.load(mContext, LOCK_SOUND_PATH, 1);
            // from class: com.android.keyguard.sec.effect.KeyguardEffectViewColourDroplet.3
// android.media.SoundPool.OnLoadCompleteListener
            this.mSoundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> Log.d("KeyguardEffectViewColourDroplet", "sound : onLoadComplete"));
        }
    }

    private void stopReleaseSound() {
        if (this.releaseSoundRunnable != null) {
            removeCallbacks(this.releaseSoundRunnable);
            this.releaseSoundRunnable = null;
        }
    }

    private void releaseSound() {
        // from class: com.android.keyguard.sec.effect.KeyguardEffectViewColourDroplet.4
// java.lang.Runnable
        this.releaseSoundRunnable = () -> {
            if (KeyguardEffectViewColourDroplet.this.mSoundPool != null) {
                Log.d("KeyguardEffectViewColourDroplet", "WaterDroplet sound : release SoundPool");
                KeyguardEffectViewColourDroplet.this.mSoundPool.release();
                KeyguardEffectViewColourDroplet.this.mSoundPool = null;
            }
            KeyguardEffectViewColourDroplet.this.releaseSoundRunnable = null;
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
        Log.d("KeyguardEffectViewColourDroplet", "SOUND PLAY mSoundPool = " + this.mSoundPool + ", isSystemSoundChecked = " + this.isSystemSoundChecked);
        if (this.isSystemSoundChecked && this.mSoundPool != null) {
            Log.d("KeyguardEffectViewColourDroplet", "SOUND PLAY soundId = " + soundId);
            this.mSoundPool.play(this.sounds[soundId], this.leftVolumeMax, this.rightVolumeMax, 0, 0, 1.0f);
        }
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        Log.d("KeyguardEffectViewColourDroplet", "onWindowFocusChanged : hasWindowFocus - " + hasWindowFocus);
        if (!hasWindowFocus) {
            unregisterAccelrometer();
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d("KeyguardEffectViewColourDroplet", "onDetachedFromWindow");
        unregisterAccelrometer();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d("KeyguardEffectViewColourDroplet", "onAttachedToWindow");
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
        Log.d("KeyguardEffectViewColourDroplet", "setContextualWallpaper");
        if (bmp == null) {
            Log.d("KeyguardEffectViewColourDroplet", "bmp is null" + bmp);
            return;
        }
        Bitmap bmp2 = KeyguardEffectViewUtil.getPreferredConfigBitmap(bmp, Bitmap.Config.ARGB_8888);
        Log.d("KeyguardEffectViewColourDroplet", "changeBackground()");
        boolean isPreloadedWallpaper = Settings.System.getInt(this.mContext.getContentResolver(), "lockscreen_wallpaper_transparent", 1) == 1;
        /* todo as usual if (KeyguardProperties.isSupportBlendedFilter() && !isPreloadedWallpaper) {
            Log.d("KeyguardEffectViewColourDroplet", "setContextualWallpaper - VignettingEffect is applyed");
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
        Log.i("KeyguardEffectViewColourDroplet", "onSizeChanged, width = " + width + ", height = " + height + ", oldw = " + oldw + ", oldh =" + oldh);
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
            Log.d("KeyguardEffectViewColourDroplet", "onSizeChanged called!!! mTouchFlagForMobileKeyboard = " + this.mTouchFlagForMobileKeyboard);
        }
    }
}