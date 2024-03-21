package com.android.keyguard.sec;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.aj.effect.MainActivity;
import com.aj.effect.R;
import com.samsung.android.visualeffect.EffectView;

import java.util.HashMap;

/* loaded from: classes.dex */
public class KeyguardEffectViewParticleSpace extends FrameLayout implements KeyguardEffectViewBase {
    private static final int DRAG_SOUND_PATH = R.raw.ve_poppingcolours_drag; //"/system/media/audio/ui/ve_poppingcolours_drag.ogg";
    private static final int TAP_SOUND_PATH = R.raw.ve_poppingcolours_tap; //"/system/media/audio/ui/ve_poppingcolours_tap.ogg";
    private final boolean DBG = true;
    final int DRAG_SOUND_COUNT_INTERVAL = 60;
    final int DRAG_SOUND_COUNT_START_POINT = 40;
    final int SOUND_ID_DRAG = 1;
    final int SOUND_ID_TAB = 0;
    final int SOUND_ID_LOCK = 2;
    final int SOUND_ID_UNLOCK = 3;
    private final String TAG = "VisualEffectParticleEffect";
    private final long UNLOCK_SOUND_PLAY_TIME = 2000L;
    private int dragSoundCount = 0;
    private boolean isSystemSoundChecked = true;
    private boolean isUnlocking = false;
    private int lastPlayedIdBeforeUnlock;
    private float leftVolumeMax = 0.3f;
    private Context mContext;
    private SoundPool mSoundPool;
    private WindowManager mWindowManager;
    private EffectView particleSpaceEffect;
    private float rightVolumeMax = 0.3f;
    private int[] sounds;
    private static final int LOCK_SOUND_PATH = R.raw.ve_poppingcolours_lock; //"/system/media/audio/ui/ve_poppingcolours_lock.ogg";
    private static final int UNLOCK_SOUND_PATH = R.raw.ve_poppingcolours_unlock; //"/system/media/audio/ui/ve_poppingcolours_unlock.ogg";

    public float fpsRatio;

    public KeyguardEffectViewParticleSpace(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        Log.d(TAG, "KeyguardEffectViewParticleSpace : Constructor");
        //Log.d(TAG, "KeyguardWindowCallback = " + callback);
        mContext = context;
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        fpsRatio = 60 / mWindowManager.getDefaultDisplay().getRefreshRate();
        particleSpaceEffect = new EffectView(context);
        particleSpaceEffect.setEffect(3);
        addView(particleSpaceEffect);
    }

    private void makeSound() {
        if (mSoundPool == null) { // (KeyguardProperties.isEffectProcessSeparated() || KeyguardUpdateMonitor.getInstance(this.mContext).hasBootCompleted()) &&
            Log.d(TAG, "new SoundPool");
            sounds = new int[4];
            AudioAttributes attr = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
            mSoundPool = new SoundPool.Builder().setMaxStreams(10).setAudioAttributes(attr).build();
            sounds[SOUND_ID_TAB] = mSoundPool.load(mContext, TAP_SOUND_PATH, 1);
            sounds[SOUND_ID_DRAG] = mSoundPool.load(mContext, DRAG_SOUND_PATH, 1);
            sounds[SOUND_ID_LOCK] = mSoundPool.load(mContext, LOCK_SOUND_PATH, 1);
            sounds[SOUND_ID_UNLOCK] = mSoundPool.load(mContext, UNLOCK_SOUND_PATH, 1);
        }
    }

    private void releaseSound() {
        if (mSoundPool != null) {
            Log.d(TAG, "releaseSound");
            mSoundPool.release();
            mSoundPool = null;
        }
    }

    private void playSound(int soundId) {
        if (!isUnlocking || soundId != 1) {
            checkSound();
            if (isSystemSoundChecked && mSoundPool != null) {
                lastPlayedIdBeforeUnlock = mSoundPool.play(sounds[soundId], leftVolumeMax, rightVolumeMax, 0, 0, 1.0f);
            }
        }
    }

    private void checkSound() {
        ContentResolver cr = mContext.getContentResolver();
        int result = Settings.System.getInt(cr, "lockscreen_sounds_enabled", -2);
        isSystemSoundChecked = result == 1;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void show() {
        Log.d(TAG, "KeyguardEffectViewParticleSpace : show");
        isUnlocking = false;
        if (particleSpaceEffect != null) {
            particleSpaceEffect.clearScreen();
        }
        makeSound();
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void reset() {
        Log.d(TAG, "KeyguardEffectViewParticleSpace : reset");
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void cleanUp() {
        Log.d(TAG, "KeyguardEffectViewParticleSpace : cleanUp");
        particleSpaceEffect.clearScreen();
        releaseSound();
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void update() {
        Log.i(TAG, "update");
        /*BitmapDrawable newBitmapDrawable = // todo KeyguardEffectViewUtil.getCurrentWallpaper(this.mContext);
        if (newBitmapDrawable == null) {
            Log.i(TAG, "newBitmapDrawable  is null");
            return;
        }*/
        Bitmap originBitmap = MainActivity.bitm; //newBitmapDrawable.getBitmap();
        if (originBitmap != null) {
            setBitmap(originBitmap);
        }
    }

    private void setBitmap(Bitmap originBitmap) {
        HashMap<String, Bitmap> map = new HashMap<>();
        map.put("BGBitmap", originBitmap);
        particleSpaceEffect.handleCustomEvent(0, map);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void screenTurnedOn() {
        Log.d(TAG, "KeyguardEffectViewParticleSpace : screenTurnedOn");
        particleSpaceEffect.handleCustomEvent(99, (HashMap) null);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void screenTurnedOff() {
        Log.d(TAG, "KeyguardEffectViewParticleSpace : screenTurnedOff");
        particleSpaceEffect.clearScreen();
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void showUnlockAffordance(long startDelay, Rect rect) {
        Log.d(TAG, "KeyguardEffectViewParticleSpace : showUnlockAffordance, startDelay = " + startDelay);
        HashMap<String, Object> hm1 = new HashMap<>();
        hm1.put("StartDelay", startDelay);
        hm1.put("Rect", rect);
        particleSpaceEffect.handleCustomEvent(1, hm1);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public long getUnlockDelay() {
        return 300L;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void handleUnlock(View view, MotionEvent event) {
        Log.d(TAG, "KeyguardEffectViewParticleSpace : handleUnlock");
        isUnlocking = true;
        playSound(SOUND_ID_UNLOCK);
        particleSpaceEffect.handleCustomEvent(2, (HashMap) null);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void playLockSound() {
        playSound(SOUND_ID_LOCK);
        Log.d(TAG, "KeyguardEffectViewParticleSpace : playLockSound");
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public boolean handleTouchEvent(View view, MotionEvent event) {
        if (event.getActionMasked() == 0) {
            dragSoundCount = (int) (DRAG_SOUND_COUNT_START_POINT / fpsRatio);
            if (mSoundPool == null) {
                Log.d(TAG, "ACTION_DOWN, mSoundPool == null");
                makeSound();
            }
            playSound(0);
        } else if (event.getActionMasked() == 2) {
            dragSoundCount++;
            if (dragSoundCount >= (int) (DRAG_SOUND_COUNT_INTERVAL / fpsRatio)) {
                playSound(1);
                dragSoundCount = 0;
            }
        }
        particleSpaceEffect.handleTouchEvent(event, view);
        return true;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public boolean handleTouchEventForPatternLock(View view, MotionEvent event) {
        return false;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void setHidden(boolean isHidden) {
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        Log.d(TAG, "KeyguardEffectViewParticleSpace : onWindowFocusChanged - " + hasWindowFocus);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public boolean handleHoverEvent(View view, MotionEvent event) {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        /*if (this.mKeyguardWindowCallback != null) {
            postDelayed(new Runnable() { // from class: com.android.keyguard.sec.effect.KeyguardEffectViewParticleSpace.1
                @Override // java.lang.Runnable
                public void run() {
                    Log.d(TAG, "dispatchDraw() mKeyguardWindowCallback.onShown()");
                    if (KeyguardEffectViewParticleSpace.this.mKeyguardWindowCallback != null) {
                        KeyguardEffectViewParticleSpace.this.mKeyguardWindowCallback.onShown();
                    }
                }
            }, 100L);
        }*/
    }

    /*public static LockSoundInfo getLockSoundInfo() {
        return mLockSoundInfo;
    }*/

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void setContextualWallpaper(Bitmap bmp) {
        Log.i(TAG, "setContextualWallpaper");
        if (bmp == null) {
            Log.i(TAG, "bmp  is null");
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