package com.android.keyguard.sec;

import static com.android.keyguard.sec.KeyguardEffectViewController.mRes;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.aj.effect.R;
import com.samsung.android.visualeffect.EffectDataObj;
import com.samsung.android.visualeffect.EffectView;

import java.util.HashMap;

/* loaded from: classes.dex */
public class KeyguardEffectViewBlind extends FrameLayout implements KeyguardEffectViewBase {
    private static final int TOUCH_SOUND_PATH = R.raw.blind_touch; //"/system/media/audio/ui/ve_blind_touch.ogg";
    private static final int UNLOCK_SOUND_PATH = R.raw.blind_unlock; //"/system/media/audio/ui/ve_blind_unlock.ogg";
    private final boolean DBG;
    final int DRAG_SOUND_COUNT_INTERVAL;
    final int DRAG_SOUND_COUNT_START_POINT;
    final int DRAG_SOUNT_DISTANCE_THRESHOLD;
    final int SOUND_ID_TAB;
    final int SOUND_ID_UNLOC;
    final int SOUND_ID_LOCK;
    private final String TAG;
    private final long UNLOCK_SOUND_PLAY_TIME;
    private long affordanceDelay;
    private Rect affordanceRect;
    private EffectView blindEffect;
    private EffectDataObj data;
    private boolean isInit;
    private boolean isCleanUp;
    private boolean isHandleUnlock;
    private boolean isOnConfigurationChanged;
    private boolean isShow;
    private boolean isShowUnlockAffordance;
    private boolean isSystemSoundChecked;
    private boolean isUpdate;
    private boolean isWindowFocused;
    private float leftVolumeMax;
    private Context mContext;
    private SoundPool mSoundPool;
    private WindowManager mWindowManager;
    private Runnable releaseSoundRunnable;
    private float rightVolumeMax;
    private int[] sounds;
    private static final int LOCK_SOUND_PATH = R.raw.blind_lock; //"/system/media/audio/ui/ve_blind_lock.ogg";
    private static final String SILENCE_SOUND_PATH = "/system/media/audio/ui/ve_silence.ogg";
    Bitmap light;
    Bitmap wallpaper;

    public KeyguardEffectViewBlind(Context context) {
        super(context);
        this.TAG = "BlindEffect";
        this.DBG = true;
        this.isInit = false;
        this.mSoundPool = null;
        this.leftVolumeMax = 1.0f;
        this.rightVolumeMax = 1.0f;
        this.UNLOCK_SOUND_PLAY_TIME = 2000L;
        this.releaseSoundRunnable = null;
        this.isSystemSoundChecked = true;
        this.isWindowFocused = true;
        this.sounds = null;
        this.DRAG_SOUND_COUNT_START_POINT = 20;
        this.DRAG_SOUND_COUNT_INTERVAL = 25;
        this.DRAG_SOUNT_DISTANCE_THRESHOLD = 200;
        this.SOUND_ID_TAB = 0;
        this.SOUND_ID_UNLOC = 1;
        this.SOUND_ID_LOCK = 2;
        this.isOnConfigurationChanged = false;
        this.isShow = false;
        this.isCleanUp = false;
        this.isUpdate = false;
        this.isHandleUnlock = false;
        this.isShowUnlockAffordance = false;
        init(context);
    }

    private void init(Context context) {
        Log.d(TAG, "KeyguardEffectViewBlind : Constructor");
        this.mContext = context.getApplicationContext();
        this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Log.d(TAG, "KeyguardEffectViewBlind : onPreExecute");
        Log.d(TAG, "KeyguardEffectViewBlind : isAsyncPostExecuted = " + KeyguardEffectViewBlind.this.isInit);
        this.light = BitmapFactory.decodeResource(mRes, R.drawable.keyguard_blind_light);
        KeyguardEffectViewBlind.this.blindEffect = new EffectView(KeyguardEffectViewBlind.this.mContext);
        KeyguardEffectViewBlind.this.blindEffect.setEffect(10);
        KeyguardEffectViewBlind.this.data = new EffectDataObj();
        HashMap<String, Long> hm0 = new HashMap<>();
        hm0.put("unlockDelay", KeyguardEffectViewBlind.this.getUnlockDelay());
        KeyguardEffectViewBlind.this.blindEffect.handleCustomEvent(2, hm0);
        HashMap<String, Bitmap> hm1 = new HashMap<>();
        hm1.put("light", this.light);
        hm1.put("background", KeyguardEffectViewBlind.this.setBackground());
        KeyguardEffectViewBlind.this.blindEffect.handleCustomEvent(0, hm1);
        KeyguardEffectViewBlind.this.blindEffect.init(KeyguardEffectViewBlind.this.data);
        Log.d(TAG, "KeyguardEffectViewBlind : doInBackground");
        KeyguardEffectViewBlind.this.addView(KeyguardEffectViewBlind.this.blindEffect);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap setBackground() {
        Log.d(TAG, "setBackground");
        Bitmap pBitmap = null;
        try {
            pBitmap = KeyguardEffectViewUtil.getCurrentWallpaper(mContext).getBitmap();
            if (pBitmap != null) {
                Log.d(TAG, "pBitmap.width = " + pBitmap.getWidth() + ", pBitmap.height = " + pBitmap.getHeight());
                for (int i = 1; i <= 2; i++) {
                    for (int j = 1; j <= 2; j++) {
                        int pixel = pBitmap.getPixel((pBitmap.getWidth() / 3) * i, (pBitmap.getHeight() / 3) * j);
                        int redValue = Color.red(pixel);
                        int blueValue = Color.blue(pixel);
                        int greenValue = Color.green(pixel);
                        Log.d(TAG, "pBitmap.getPixel(" + ((pBitmap.getWidth() / 3) * i) + "," + ((pBitmap.getHeight() / 3) * j) + ") : " + redValue + " " + greenValue + " " + blueValue);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pBitmap;
    }

    private void makeSound() {
        stopReleaseSound();
        if (this.mSoundPool == null) {
            Log.d(TAG, "new SoundPool");
            this.sounds = new int[3];
            AudioAttributes attr = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
            this.mSoundPool = new SoundPool.Builder().setMaxStreams(10).setAudioAttributes(attr).build();
            this.sounds[0] = this.mSoundPool.load(mRes.openRawResourceFd(TOUCH_SOUND_PATH), 1);
            this.sounds[1] = this.mSoundPool.load(mRes.openRawResourceFd(UNLOCK_SOUND_PATH), 1);
            this.sounds[SOUND_ID_LOCK] = mSoundPool.load(mRes.openRawResourceFd(LOCK_SOUND_PATH), 1);
        }
    }

    private void releaseSound() {
        // from class: com.android.keyguard.sec.effect.KeyguardEffectViewBlind.1
// java.lang.Runnable
        this.releaseSoundRunnable = () -> {
            if (KeyguardEffectViewBlind.this.mSoundPool != null) {
                Log.d(TAG, "releaseSound");
                KeyguardEffectViewBlind.this.mSoundPool.release();
                KeyguardEffectViewBlind.this.mSoundPool = null;
            }
            KeyguardEffectViewBlind.this.releaseSoundRunnable = null;
        };
        postDelayed(this.releaseSoundRunnable, 2000L);
    }

    private void stopReleaseSound() {
        if (this.releaseSoundRunnable != null) {
            removeCallbacks(this.releaseSoundRunnable);
            this.releaseSoundRunnable = null;
        }
    }

    private void playSound(int soundId) {
        checkSound();
        if (this.isSystemSoundChecked && this.mSoundPool != null) {
            this.mSoundPool.play(this.sounds[soundId], this.leftVolumeMax, this.rightVolumeMax, 0, 0, 1.0f);
        }
    }

    private void checkSound() {
        ContentResolver cr = this.mContext.getContentResolver();
        int result = 0;
        result = Settings.System.getInt(cr, "lockscreen_sounds_enabled", -2);
        this.isSystemSoundChecked = result == 1;
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.isWindowFocused) {
            Log.d(TAG, "KeyguardEffectViewBlind : onConfigurationChanged");
        }
        HashMap<String, Boolean> hm0 = new HashMap<>();
        hm0.put("onConfigurationChanged", this.isWindowFocused);
        this.blindEffect.handleCustomEvent(99, hm0);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void show() {
        Log.d(TAG, "KeyguardEffectViewBlind : show");
        HashMap<String, Boolean> hm0 = new HashMap<>();
        hm0.put("show", true);
        this.blindEffect.handleCustomEvent(99, hm0);
        makeSound();
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void reset() {
        Log.d(TAG, "KeyguardEffectViewBlind : reset");
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void cleanUp() {
        Log.d(TAG, "KeyguardEffectViewBlind : cleanUp");
        this.blindEffect.clearScreen();
        stopReleaseSound();
        releaseSound();
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void update() {
        Log.d(TAG, "KeyguardEffectViewBlind : update");
        HashMap<String, Bitmap> hm0 = new HashMap<>();
        hm0.put("background", setBackground());
        this.blindEffect.handleCustomEvent(0, hm0);
    }

    @Override
    public void setContextualWallpaper(Bitmap bmp) {

    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void screenTurnedOn() {
        Log.d(TAG, "KeyguardEffectViewBlind : screenTurnedOn");
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void screenTurnedOff() {
        Log.d(TAG, "KeyguardEffectViewBlind : screenTurnedOff");
        if (this.isInit) {
            HashMap<String, Boolean> hm0 = new HashMap<>();
            hm0.put("initAnimationValue", true);
            this.blindEffect.clearScreen();
            this.blindEffect.handleCustomEvent(99, hm0);
        }
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void showUnlockAffordance(long startDelay, Rect rect) {
        Log.d(TAG, "KeyguardEffectViewBlind : showUnlockAffordance");
        HashMap<String, Object> hm0 = new HashMap<>();
        hm0.put("StartDelay", startDelay);
        hm0.put("Rect", rect);
        this.blindEffect.handleCustomEvent(1, hm0);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public long getUnlockDelay() {
        return 0L;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void handleUnlock(View view, MotionEvent event) {
        Log.d(TAG, "KeyguardEffectViewBlind : handleUnlock (exit xml animation removed)");
        HashMap<String, Boolean> hm0 = new HashMap<>();
        hm0.put("unlock", true);
        this.blindEffect.handleCustomEvent(2, hm0);
        playSound(1);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void playLockSound() {
        playSound(SOUND_ID_LOCK);
        Log.d(TAG, "KeyguardEffectViewBlind : playLockSound");
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public boolean handleTouchEvent(View view, MotionEvent event) {
        this.blindEffect.handleTouchEvent(event, view);
        if (event.getActionMasked() == 0) {
            stopReleaseSound();
            if (this.mSoundPool == null) {
                Log.d(TAG, "ACTION_DOWN, mSoundPool == null");
                makeSound();
            }
            playSound(0);
        }
        return true;
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        Log.d(TAG, "KeyguardEffectViewBlind : onWindowFocusChanged " + hasWindowFocus);
        this.isWindowFocused = hasWindowFocus;
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

    public static boolean isBackgroundEffect() {
        return true;
    }

    public static String getCounterEffectName() {
        return null;
    }
}