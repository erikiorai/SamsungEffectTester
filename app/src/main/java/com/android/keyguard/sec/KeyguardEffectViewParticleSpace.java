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
    private final boolean DBG;
    final int DRAG_SOUND_COUNT_INTERVAL;
    final int DRAG_SOUND_COUNT_START_POINT;
    final int SOUND_ID_DRAG;
    final int SOUND_ID_TAB;
    final int SOUND_ID_LOCK = 2;
    final int SOUND_ID_UNLOCK = 3;
    private final String TAG;
    private final long UNLOCK_SOUND_PLAY_TIME;
    private int dragSoundCount;
    private boolean isSystemSoundChecked;
    private boolean isUnlocking;
    private int lastPlayedIdBeforeUnlock;
    private float leftVolumeMax;
    private Context mContext;
    private SoundPool mSoundPool;
    private WindowManager mWindowManager;
    private EffectView particleSpaceEffect;
    private float rightVolumeMax;
    private int[] sounds;
    private static final int LOCK_SOUND_PATH = R.raw.ve_poppingcolours_lock; //"/system/media/audio/ui/ve_poppingcolours_lock.ogg";
    private static final int UNLOCK_SOUND_PATH = R.raw.ve_poppingcolours_unlock; //"/system/media/audio/ui/ve_poppingcolours_unlock.ogg";

    public KeyguardEffectViewParticleSpace(Context context) {
        super(context);
        this.TAG = "VisualEffectParticleEffect";
        this.DBG = true;
        this.mSoundPool = null;
        this.leftVolumeMax = 0.3f;
        this.rightVolumeMax = 0.3f;
        this.UNLOCK_SOUND_PLAY_TIME = 2000L;
        this.isSystemSoundChecked = true;
        this.sounds = null;
        this.dragSoundCount = 0;
        this.DRAG_SOUND_COUNT_INTERVAL = 60;
        this.DRAG_SOUND_COUNT_START_POINT = 40;
        this.SOUND_ID_TAB = 0;
        this.SOUND_ID_DRAG = 1;
        this.isUnlocking = false;
        init(context);
    }

    private void init(Context context) {
        Log.d("VisualEffectParticleEffect", "KeyguardEffectViewParticleSpace : Constructor");
        //Log.d("VisualEffectParticleEffect", "KeyguardWindowCallback = " + callback);
        this.mContext = context;
        this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        this.particleSpaceEffect = new EffectView(context);
        this.particleSpaceEffect.setEffect(3);
        addView(this.particleSpaceEffect);
    }

    private void makeSound() {
        if (this.mSoundPool == null) { // (KeyguardProperties.isEffectProcessSeparated() || KeyguardUpdateMonitor.getInstance(this.mContext).hasBootCompleted()) &&
            Log.d("VisualEffectParticleEffect", "new SoundPool");
            this.sounds = new int[4];
            AudioAttributes attr = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
            this.mSoundPool = new SoundPool.Builder().setMaxStreams(10).setAudioAttributes(attr).build();
            this.sounds[SOUND_ID_TAB] = this.mSoundPool.load(mContext, TAP_SOUND_PATH, 1);
            this.sounds[SOUND_ID_DRAG] = this.mSoundPool.load(mContext, DRAG_SOUND_PATH, 1);
            sounds[SOUND_ID_LOCK] = mSoundPool.load(mContext, LOCK_SOUND_PATH, 1);
            sounds[SOUND_ID_UNLOCK] = mSoundPool.load(mContext, UNLOCK_SOUND_PATH, 1);
        }
    }

    private void releaseSound() {
        if (this.mSoundPool != null) {
            Log.d("VisualEffectParticleEffect", "releaseSound");
            this.mSoundPool.release();
            this.mSoundPool = null;
        }
    }

    private void playSound(int soundId) {
        if (!this.isUnlocking || soundId != 1) {
            checkSound();
            if (this.isSystemSoundChecked && this.mSoundPool != null) {
                this.lastPlayedIdBeforeUnlock = this.mSoundPool.play(this.sounds[soundId], this.leftVolumeMax, this.rightVolumeMax, 0, 0, 1.0f);
            }
        }
    }

    private void checkSound() {
        ContentResolver cr = this.mContext.getContentResolver();
        int result = Settings.System.getInt(cr, "lockscreen_sounds_enabled", -2);
        this.isSystemSoundChecked = result == 1;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void show() {
        Log.d("VisualEffectParticleEffect", "KeyguardEffectViewParticleSpace : show");
        this.isUnlocking = false;
        if (this.particleSpaceEffect != null) {
            this.particleSpaceEffect.clearScreen();
        }
        makeSound();
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void reset() {
        Log.d("VisualEffectParticleEffect", "KeyguardEffectViewParticleSpace : reset");
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void cleanUp() {
        Log.d("VisualEffectParticleEffect", "KeyguardEffectViewParticleSpace : cleanUp");
        this.particleSpaceEffect.clearScreen();
        releaseSound();
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void update() {
        Log.i("VisualEffectParticleEffect", "update");
        /*BitmapDrawable newBitmapDrawable = // todo KeyguardEffectViewUtil.getCurrentWallpaper(this.mContext);
        if (newBitmapDrawable == null) {
            Log.i("VisualEffectParticleEffect", "newBitmapDrawable  is null");
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
        this.particleSpaceEffect.handleCustomEvent(0, map);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void screenTurnedOn() {
        Log.d("VisualEffectParticleEffect", "KeyguardEffectViewParticleSpace : screenTurnedOn");
        this.particleSpaceEffect.handleCustomEvent(99, (HashMap) null);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void screenTurnedOff() {
        Log.d("VisualEffectParticleEffect", "KeyguardEffectViewParticleSpace : screenTurnedOff");
        this.particleSpaceEffect.clearScreen();
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void showUnlockAffordance(long startDelay, Rect rect) {
        Log.d("VisualEffectParticleEffect", "KeyguardEffectViewParticleSpace : showUnlockAffordance, startDelay = " + startDelay);
        HashMap<String, Object> hm1 = new HashMap<>();
        hm1.put("StartDelay", startDelay);
        hm1.put("Rect", rect);
        this.particleSpaceEffect.handleCustomEvent(1, hm1);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public long getUnlockDelay() {
        return 300L;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void handleUnlock(View view, MotionEvent event) {
        Log.d("VisualEffectParticleEffect", "KeyguardEffectViewParticleSpace : handleUnlock");
        this.isUnlocking = true;
        playSound(SOUND_ID_UNLOCK);
        this.particleSpaceEffect.handleCustomEvent(2, (HashMap) null);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void playLockSound() {
        playSound(SOUND_ID_LOCK);
        Log.d("VisualEffectParticleEffect", "KeyguardEffectViewParticleSpace : playLockSound");
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public boolean handleTouchEvent(View view, MotionEvent event) {
        if (event.getActionMasked() == 0) {
            this.dragSoundCount = 40;
            if (this.mSoundPool == null) {
                Log.d("VisualEffectParticleEffect", "ACTION_DOWN, mSoundPool == null");
                makeSound();
            }
            playSound(0);
        } else if (event.getActionMasked() == 2) {
            this.dragSoundCount++;
            if (this.dragSoundCount >= 60) {
                playSound(1);
                this.dragSoundCount = 0;
            }
        }
        this.particleSpaceEffect.handleTouchEvent(event, view);
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
        Log.d("VisualEffectParticleEffect", "KeyguardEffectViewParticleSpace : onWindowFocusChanged - " + hasWindowFocus);
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
                    Log.d("VisualEffectParticleEffect", "dispatchDraw() mKeyguardWindowCallback.onShown()");
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
        Log.i("VisualEffectParticleEffect", "setContextualWallpaper");
        if (bmp == null) {
            Log.i("VisualEffectParticleEffect", "bmp  is null");
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