package com.android.keyguard.sec.festivaleffect.unlockeffect;

import static com.aj.effect.SoundManager.attr;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.SoundPool;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.RawRes;

import com.android.keyguard.sec.KeyguardEffectViewBase;

public class KeyguardEffectViewFestival extends FrameLayout implements KeyguardEffectViewBase {
    protected static String TAG = "KeyguardEffectViewFestival";
    final int DRAG_SOUND_COUNT_INTERVAL;
    final int DRAG_SOUND_COUNT_START_POINT;
    final int SOUND_ID_DRAG = 2;
    final int SOUND_ID_TAB = 0;
    final int SOUND_ID_UNLOCK = 1;
    final int SOUND_ID_LOCK = 3;
    private final long UNLOCK_SOUND_PLAY_TIME = 2000L;
    protected FestivalEffect festivalEffect;
    private int dragSoundCount;
    private boolean isSystemSoundChecked;
    private boolean isUnlocking;
    private int lastPlayedIdBeforeUnlock;
    private float leftVolumeMax = 0.3f;
    private Context mContext;
    private SoundPool mSoundPool;
    private Runnable releaseSoundRunnable;
    private float rightVolumeMax = 0.3f;
    private int[] sounds;

    @RawRes protected int tap;
    @RawRes protected int drag;
    @RawRes protected int unlock;
    @RawRes protected int lock;

    public KeyguardEffectViewFestival(Context context, FestivalEffect effect) {
        super(context);
        this.dragSoundCount = 0;
        this.DRAG_SOUND_COUNT_INTERVAL = 60;
        this.DRAG_SOUND_COUNT_START_POINT = 50;

        this.mContext = context;
        festivalEffect = effect;
        addView(festivalEffect);
    }

    private void checkSound() {
        int i = Settings.System.getInt(mContext.getContentResolver(), "lockscreen_sounds_enabled", -2);
        this.isSystemSoundChecked = i == 1;
    }

    private void makeSound() {
        stopReleaseSound();
        if (this.mSoundPool == null) {
            this.sounds = new int[4];
            this.mSoundPool = new SoundPool.Builder().setMaxStreams(10).setAudioAttributes(attr).build();
            this.sounds[SOUND_ID_TAB] = this.mSoundPool.load(this.mContext, tap, 1);
            this.sounds[SOUND_ID_UNLOCK] = this.mSoundPool.load(this.mContext, unlock, 1);
            this.sounds[SOUND_ID_DRAG] = this.mSoundPool.load(this.mContext, drag, 1);
            sounds[SOUND_ID_LOCK] = mSoundPool.load(mContext, lock, 1);
        }
    }

    private void playSound(int i) {
        if (this.isUnlocking && i == SOUND_ID_DRAG) {
            return;
        }
        checkSound();
        if (!this.isSystemSoundChecked || this.mSoundPool == null) {
            return;
        }
        if (i == SOUND_ID_UNLOCK && this.lastPlayedIdBeforeUnlock != 0) {
            this.mSoundPool.stop(this.lastPlayedIdBeforeUnlock);
        }
        this.lastPlayedIdBeforeUnlock = this.mSoundPool.play(this.sounds[i], this.leftVolumeMax, this.rightVolumeMax, 0, 0, 1.0f);
    }

    private void releaseSound() {
        // from class: com.android.keyguard.sec.festivaleffect.unlockeffect.autumn.KeyguardEffectViewAutumn.1
// java.lang.Runnable
        this.releaseSoundRunnable = () -> {
            if (KeyguardEffectViewFestival.this.mSoundPool != null) {
                KeyguardEffectViewFestival.this.mSoundPool.release();
                KeyguardEffectViewFestival.this.mSoundPool = null;
            }
            KeyguardEffectViewFestival.this.releaseSoundRunnable = null;
        };
        postDelayed(this.releaseSoundRunnable, UNLOCK_SOUND_PLAY_TIME);
    }

    private void stopReleaseSound() {
        if (this.releaseSoundRunnable != null) {
            removeCallbacks(this.releaseSoundRunnable);
            this.releaseSoundRunnable = null;
        }
    }

    public void cleanUp() {
        this.isUnlocking = false;
        if (this.festivalEffect != null) {
            this.festivalEffect.clearEffect();
        }
        makeSound();
    }

    public long getUnlockDelay() {
        return 300L;
    }

    public boolean handleHoverEvent(View view, MotionEvent motionEvent) {
        return false;
    }

    public boolean handleTouchEvent(View view, MotionEvent motionEvent) {
        if (this.isUnlocking) {
            Log.d(TAG, "handleTouchEvent isUnlocked : " + this.isUnlocking);
            return false;
        }
        if (motionEvent.getActionMasked() == 0) {
            this.dragSoundCount = 50;
            stopReleaseSound();
            if (this.mSoundPool == null) {
                makeSound();
            }
            playSound(SOUND_ID_TAB);
            this.festivalEffect.add(motionEvent.getRawX(), motionEvent.getRawY());
        } else if (motionEvent.getActionMasked() == 2) {
            this.dragSoundCount++;
            if (this.dragSoundCount >= 60) {
                playSound(SOUND_ID_DRAG);
                this.dragSoundCount = 0;
            }
            this.festivalEffect.move(motionEvent.getRawX(), motionEvent.getRawY());
        } else if (motionEvent.getActionMasked() == 1 || motionEvent.getActionMasked() == 3) {
            this.festivalEffect.clearEffect();
        }
        return true;
    }

    public boolean handleTouchEventForPatternLock(View view, MotionEvent motionEvent) {
        return false;
    }

    public void handleUnlock(View view, MotionEvent motionEvent) {
        playSound(SOUND_ID_UNLOCK);
        this.isUnlocking = true;
    }

    public void playLockSound() {
        playSound(SOUND_ID_LOCK);
    }

    public void reset() {
        this.isUnlocking = false;
    }

    public void screenTurnedOff() {
        this.festivalEffect.clearEffect();
    }

    public void screenTurnedOn() {
        this.isUnlocking = false;
    }

    public void setHidden(boolean z) {
    }

    public void show() {
        this.isUnlocking = false;
        if (this.festivalEffect != null) {
            this.festivalEffect.clearEffect();
        }
        makeSound();
    }

    public void showUnlockAffordance(long j, Rect rect) {
        this.isUnlocking = false;
    }

    public void update() {
    }

    @Override
    public void setContextualWallpaper(Bitmap bmp) {

    }

    @Override
    public void drawPause() {
        festivalEffect.drawPause();
    }

    @Override
    public void drawResume() {
        festivalEffect.drawResume();
    }

    public static boolean isBackgroundEffect() {
        return false;
    }

    public static String getCounterEffectName() {
        return "Wallpaper";
    }
}