package com.android.keyguard.sec;

import android.app.KeyguardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
public class KeyguardEffectViewIndigoDiffusion extends EffectView implements KeyguardEffectViewBase {
    private static final int DOWN_SOUND_PATH = R.raw.ve_ripple_down; //"/system/media/audio/ui/ve_indigodiffusion_ripple_down.ogg";
    public static final int IMAGE_TYPE_BURGUNDY_RED = 1;
    public static final int IMAGE_TYPE_MIDNIGHT_BLUE_OR_NORMAL = 0;
    public static final int UPDATE_TYPE_CHANGE_BACKGROUND = 1;
    public static final int UPDATE_TYPE_USER_SWITCHING = 2;
    private static final int UP_SOUND_PATH = R.raw.ve_ripple_up; //"/system/media/audio/ui/ve_indigodiffusion_ripple_up.ogg";
    final int SOUND_ID_DOWN = 0;
    final int SOUND_ID_DRAG = 1;
    final int SOUND_ID_LOCK = 2;
    final int SOUND_ID_UNLOCK = 3;
    private final String TAG;
    private final long UNLOCK_SOUND_PLAY_TIME;
    int imageType;
    private boolean isSystemSoundChecked;
    private boolean isUnlocked;
    KeyguardManager keyguardManager;
    private float leftVolumeMax;
    private Context mContext;
    private SoundHandler mHandler;
    private IEffectListener mListener;
    private SoundPool mSoundPool;
    private Runnable releaseSoundRunnable;
    private float rightVolumeMax;
    Message soundMsg;
    private int[] sounds;
    private int windowHeight;
    private int windowWidth;

    public KeyguardEffectViewIndigoDiffusion(Context context) {
        super(context);
        this.TAG = "KeyguardEffectViewIndigoDiffusion";
        this.imageType = 0;
        this.mSoundPool = null;
        this.leftVolumeMax = 1.0f;
        this.rightVolumeMax = 1.0f;
        this.UNLOCK_SOUND_PLAY_TIME = 2000L;
        this.releaseSoundRunnable = null;
        this.isSystemSoundChecked = true;
        this.sounds = null;
        this.isUnlocked = false;
        this.windowWidth = 0;
        this.windowHeight = 0;
        init(context);
    }

    private void init(Context context) {
        Log.d("KeyguardEffectViewIndigoDiffusion", "KeyguardEffectViewIndigoDiffusion Constructor");
        this.mContext = context;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager mWindowManager = (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
        Rect rect = Utils.getViewRect(displayMetrics, mWindowManager);
        windowWidth = rect.width();
        windowHeight = rect.height();
        setEffect(9);
        EffectDataObj data = new EffectDataObj();
        data.setEffect(9);
        data.indigoDiffuseData.windowWidth = this.windowWidth;
        data.indigoDiffuseData.windowHeight = this.windowHeight;
        data.indigoDiffuseData.reflectionBitmap = makeResBitmap(R.drawable.keyguard_blind_light); // todo R.drawable.reflectionmap
        changeColor(35, 35, 85);
        init(data);
        this.sounds = new int[4];
        HashMap<String, Bitmap> map2 = new HashMap<>();
        map2.put("Bitmap", setBackground());
        handleCustomEvent(0, map2);
        // from class: com.android.keyguard.sec.effect.KeyguardEffectViewIndigoDiffusion.1
        this.mListener = (status, params) -> {
            if (status == 1) {
                if (((String) params.get("sound")).contentEquals("down")) {
                    KeyguardEffectViewIndigoDiffusion.this.playSound(0);
                } else if (((String) params.get("sound")).contentEquals("drag")) {
                    KeyguardEffectViewIndigoDiffusion.this.playSound(1);
                }
            }
        };
        setListener(this.mListener);
    }

    private Bitmap setBackground() {
        Log.d("KeyguardEffectViewIndigoDiffusion", "setBackground");
        BitmapDrawable newBitmapDrawable = KeyguardEffectViewUtil.getCurrentWallpaper(this.mContext);
        Bitmap pBitmap;
        if (newBitmapDrawable != null) {
            pBitmap = newBitmapDrawable.getBitmap();
            if (pBitmap != null) {
                Log.d("KeyguardEffectViewIndigoDiffusion", "pBitmap.width = " + pBitmap.getWidth() + ", pBitmap.height = " + pBitmap.getHeight());
            } else {
                Log.d("KeyguardEffectViewIndigoDiffusion", "pBitmap is null");
            }
        } else {
            Log.d("KeyguardEffectViewIndigoDiffusion", "newBitmapDrawable is null");
            return null;
        }
        return pBitmap;
    }

    public void settingsForImageType(int type) {
        Log.d("KeyguardEffectViewIndigoDiffusion", "settingsForImageType");
        if (this.imageType != type) {
            this.imageType = type;
            if (type == 0) {
                changeColor(35, 35, 85);
            } else {
                changeColor(80, 10, 25);
            }
        }
    }

    private void changeColor(int r, int g, int b) {
        EffectDataObj data = new EffectDataObj();
        data.setEffect(9);
        data.indigoDiffuseData.red = r;
        data.indigoDiffuseData.green = g;
        data.indigoDiffuseData.blue = b;
        reInit(data);
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
        if (this.mSoundPool == null) { // todo KeyguardUpdateMonitor.getInstance(this.mContext).hasBootCompleted() &&
            Log.d("KeyguardEffectViewIndigoDiffusion", "IndigoDiffusion sound : new SoundPool");
            AudioAttributes attr = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
            this.mSoundPool = new SoundPool.Builder().setMaxStreams(10).setAudioAttributes(attr).build();
            this.sounds[0] = this.mSoundPool.load(mContext, DOWN_SOUND_PATH, 1);
            this.sounds[1] = this.mSoundPool.load(mContext, UP_SOUND_PATH, 1);
            this.sounds[SOUND_ID_LOCK] = this.mSoundPool.load(mContext, R.raw.lock_ripple, 1);
            this.sounds[SOUND_ID_UNLOCK] = this.mSoundPool.load(mContext, R.raw.unlock_ripple, 1);
        }
        if (this.mHandler == null) {
            Log.d("KeyguardEffectViewIndigoDiffusion", "new SoundHandler()");
            this.mHandler = new SoundHandler(Looper.myLooper());
        }
    }

    private void releaseSound() {
        // from class: com.android.keyguard.sec.effect.KeyguardEffectViewIndigoDiffusion.2
// java.lang.Runnable
        this.releaseSoundRunnable = () -> {
            if (KeyguardEffectViewIndigoDiffusion.this.mSoundPool != null) {
                Log.d("KeyguardEffectViewIndigoDiffusion", "IndigoDiffusion sound : release SoundPool");
                KeyguardEffectViewIndigoDiffusion.this.mSoundPool.release();
                KeyguardEffectViewIndigoDiffusion.this.mSoundPool = null;
            }
            KeyguardEffectViewIndigoDiffusion.this.releaseSoundRunnable = null;
        };
        postDelayed(this.releaseSoundRunnable, 2000L);
    }

    private void stopReleaseSound() {
        if (this.releaseSoundRunnable != null) {
            removeCallbacks(this.releaseSoundRunnable);
            this.releaseSoundRunnable = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playSound(int soundId) {
        checkSound();
        stopReleaseSound();
        if (this.mSoundPool == null) {
            Log.d("KeyguardEffectViewIndigoDiffusion", "ACTION_DOWN, mSoundPool == null");
            makeSound();
        }
        if (this.isSystemSoundChecked && this.mSoundPool != null) {
            int streanID = this.mSoundPool.play(this.sounds[soundId], this.leftVolumeMax, this.rightVolumeMax, 0, 0, 1.0f);
            if (soundId == 1 && this.mHandler != null) {
                this.soundMsg = this.mHandler.obtainMessage();
                this.soundMsg.arg1 = streanID - 1;
                this.soundMsg.arg2 = 4;
                this.mHandler.sendMessage(this.soundMsg);
            }
        }
    }

    private void checkSound() {
        ContentResolver cr = this.mContext.getContentResolver();
        int result = Settings.System.getInt(cr, "lockscreen_sounds_enabled", -2);
        this.isSystemSoundChecked = result == 1;
    }

    /* loaded from: classes.dex */
    public class SoundHandler extends Handler {
        public SoundHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (KeyguardEffectViewIndigoDiffusion.this.mSoundPool != null) {
                float volume = 0.2f * KeyguardEffectViewIndigoDiffusion.this.soundMsg.arg2;
                KeyguardEffectViewIndigoDiffusion.this.mSoundPool.setVolume(KeyguardEffectViewIndigoDiffusion.this.soundMsg.arg1, volume, volume);
                if (msg.arg2 != 0) {
                    KeyguardEffectViewIndigoDiffusion.this.soundMsg = KeyguardEffectViewIndigoDiffusion.this.mHandler.obtainMessage();
                    KeyguardEffectViewIndigoDiffusion.this.soundMsg.arg1 = msg.arg1;
                    KeyguardEffectViewIndigoDiffusion.this.soundMsg.arg2 = msg.arg2 - 1;
                    sendMessageDelayed(KeyguardEffectViewIndigoDiffusion.this.soundMsg, 10L);
                }
            }
        }
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void show() {
        Log.d("KeyguardEffectViewIndigoDiffusion", "show");
        makeSound();
        clearScreen();
        this.isUnlocked = false;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void reset() {
        Log.d("KeyguardEffectViewIndigoDiffusion", "reset");
        clearScreen();
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void cleanUp() {
        Log.d("KeyguardEffectViewIndigoDiffusion", "cleanUp");
        stopReleaseSound();
        releaseSound();
        // from class: com.android.keyguard.sec.effect.KeyguardEffectViewIndigoDiffusion.3
// java.lang.Runnable
        postDelayed(() -> KeyguardEffectViewIndigoDiffusion.this.clearScreen(), 400L);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void update() {
        Log.d("KeyguardEffectViewIndigoDiffusion", "update");
        HashMap<String, Bitmap> map = new HashMap<>();
        map.put("Bitmap", setBackground());
        handleCustomEvent(0, map);
    }

    public void update(int updateType) {
        Log.d("KeyguardEffectViewIndigoDiffusion", "update() updateType = " + updateType);
        HashMap<String, Bitmap> map = new HashMap<>();
        map.put("Bitmap", setBackground());
        handleCustomEvent(0, map);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void screenTurnedOn() {
        Log.d("KeyguardEffectViewIndigoDiffusion", "screenTurnedOn");
        this.isUnlocked = false;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void screenTurnedOff() {
        Log.d("KeyguardEffectViewIndigoDiffusion", "screenTurnedOff");
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void showUnlockAffordance(long startDelay, Rect rect) {
        Log.d("KeyguardEffectViewIndigoDiffusion", "showUnlockAffordance");
        HashMap<Object, Object> map = new HashMap<>();
        map.put("StartDelay", startDelay);
        map.put("Rect", rect);
        handleCustomEvent(1, map);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public long getUnlockDelay() {
        return 0L;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void handleUnlock(View view, MotionEvent event) {
        Log.d("KeyguardEffectViewIndigoDiffusion", "handleUnlock");
        this.isUnlocked = true;
        playSound(SOUND_ID_UNLOCK);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void playLockSound() {
        playSound(SOUND_ID_LOCK);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public boolean handleTouchEvent(View view, MotionEvent event) {
        if (!this.isUnlocked) {
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

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d("KeyguardEffectViewIndigoDiffusion", "onDetachedFromWindow");
        if (this.mHandler != null) {
            this.mHandler.removeMessages(0);
            this.mHandler = null;
        }
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void setHidden(boolean isHidden) {
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        Log.d("KeyguardEffectViewIndigoDiffusion", "onWindowFocusChanged hasWindowFocus = " + hasWindowFocus);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void setContextualWallpaper(Bitmap bmp) {
        Log.d("KeyguardEffectViewIndigoDiffusion", "setContextualWallpaper");
        if (bmp == null) {
            Log.d("KeyguardEffectViewIndigoDiffusion", "bmp is null" + bmp);
            return;
        }
        Bitmap bmp2 = KeyguardEffectViewUtil.getPreferredConfigBitmap(bmp, Bitmap.Config.ARGB_8888);
        HashMap<String, Bitmap> map = new HashMap<>();
        map.put("Bitmap", bmp2);
        handleCustomEvent(0, map);
    }

    public static boolean isBackgroundEffect() {
        return true;
    }

    public static String getCounterEffectName() {
        return null;
    }
}