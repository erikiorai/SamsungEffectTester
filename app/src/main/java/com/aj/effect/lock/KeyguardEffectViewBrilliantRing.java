package com.aj.effect.lock;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.SoundPool;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.aj.effect.R;
import com.samsung.android.visualeffect.EffectView;

import java.util.HashMap;

public class KeyguardEffectViewBrilliantRing extends EffectView implements KeyguardEffectViewBase {
    private static final String TAG = "BrilliantRing_Keyguard";
    private Context mContext;
    private int[] sounds;
    private Handler mHandler = new Handler();
    private SoundPool mSoundPool = null;
    private Runnable releaseSoundRunnable = null;
    private final long UNLOCK_SOUND_PLAY_TIME = 2000;
    private long touchDownTime = 0;
    private long touchMoveDiffTime = 0;
    private boolean isPlayPossible = true;
    private float leftVolumeMax = 1.0f;
    private float rightVolumeMax = 1.0f;
    private int dragStreamID = 0;
    long mLongPressTime = 411;
    private float dragSoudVolume = 1.0f;
    private float dragSoudMinusOffset = 0.04f;
    private boolean isFadeOutSound = false;
    private boolean isUnlocked = false;
    public boolean hasWindowFocus = true;
    private static KeyguardEffectViewBrilliantRing sKeyguardEffectViewBrilliantRing = null;

    public KeyguardEffectViewBrilliantRing(Context context) {
        super(context);
        this.sounds = null;
        this.mContext = context;
        setEffect(1);
        this.sounds = new int[3];
    }

    private void playSound(int soundId)  {
        Log.d(TAG, "SOUND PLAY mSoundPool = " + this.mSoundPool + ", isSystemSoundChecked = " + this.isPlayPossible);
        if (this.isPlayPossible() && this.mSoundPool != null) {
            Log.d(TAG, "SOUND PLAY soundId = " + soundId);
            if (soundId != 1) {
                this.mSoundPool.play(this.sounds[soundId], this.leftVolumeMax, this.rightVolumeMax, 0, 0, 1.0f);
            } else {
                this.dragStreamID = this.mSoundPool.play(this.sounds[soundId], this.leftVolumeMax, this.rightVolumeMax, 0, -1, 1.0f);
            }
        }
    }

    private boolean isPlayPossible()  {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        try {
            while (Settings.System.getInt(contentResolver, "lockscreen_sounds_enabled") == 1) {
                //try {
                return true;
            /*} catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }*/
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return true;
        }

        return false;
    }

    private void fadeOutSound() {
        if (this.isFadeOutSound && this.mSoundPool != null) {
            if (this.dragSoudVolume < 0.0f) {
                this.dragSoudVolume = 0.0f;
            }
            this.mSoundPool.setVolume(this.dragStreamID, this.dragSoudVolume, this.dragSoudVolume);
            if (this.dragSoudVolume > 0.0f) {
                this.dragSoudVolume -= this.dragSoudMinusOffset;
                this.mHandler.postDelayed(new Runnable() { // from class: com.galaxytheme.brilliantring.effect.KeyguardEffectViewBrilliantRing.1
                    @Override // java.lang.Runnable
                    public void run() {
                        KeyguardEffectViewBrilliantRing.this.fadeOutSound();
                    }
                }, 10L);
                return;
            }
            Log.d(TAG, "SOUND STOP because UP or Unlock");
            stopReleaseSound();
            releaseSound();
        }
    }

    private void makeSound() {
        stopReleaseSound();
        if (this.mSoundPool == null) {
            Log.d(TAG, "sound : new SoundPool");
            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setMaxStreams(10);
            this.mSoundPool = builder.build();
            this.sounds[0] = this.mSoundPool.load(this.mContext, R.raw.brilliantring_tap, 1);
            this.sounds[1] = this.mSoundPool.load(this.mContext, R.raw.brilliantring_drag, 1);
            this.sounds[2] = this.mSoundPool.load(this.mContext, R.raw.brilliantring_unlock, 1);
            this.mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() { // from class: com.galaxytheme.brilliantring.a.2
                @Override // android.media.SoundPool.OnLoadCompleteListener
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    Log.d(TAG, "sound : onLoadComplete");
                }
            });
        }
    }

    private void releaseSound() {
        Handler handler = this.mHandler;
        Runnable runnable = new Runnable() { // from class: com.galaxytheme.brilliantring.effect.KeyguardEffectViewBrilliantring.3
            @Override // java.lang.Runnable
            public void run() {
                if (KeyguardEffectViewBrilliantRing.this.mSoundPool != null) {
                    Log.d(TAG, "BrilliantRing sound : release SoundPool");
                    KeyguardEffectViewBrilliantRing.this.mSoundPool.release();
                    KeyguardEffectViewBrilliantRing.this.mSoundPool = null;
                }
                KeyguardEffectViewBrilliantRing.this.releaseSoundRunnable = null;
            }
        };
        this.releaseSoundRunnable = runnable;
        handler.postDelayed(runnable, 2000L);
    }

    private void stopReleaseSound() {
        if (this.releaseSoundRunnable != null) {
            removeCallbacks(this.releaseSoundRunnable);
            this.releaseSoundRunnable = null;
        }
    }

    private void setBitmap(Bitmap bitmap) {
        HashMap<String, Bitmap> hashMap = new HashMap<>();
        hashMap.put("Bitmap", bitmap);
        handleCustomEvent(0, hashMap);
    }

    @Override // com.galaxytheme.effect.KeyguardEffectViewBase
    public void reset() {
        Log.i(TAG, "reset");
        this.isUnlocked = false;
        clearScreen();
    }

    @Override // com.galaxytheme.effect.KeyguardEffectViewBase
    public void showUnlockAffordance(long startDelay, Rect rect) {
        Log.i(TAG, "showUnlockAffordance");
        this.isUnlocked = false;
        HashMap<String, Long> hashMap = new HashMap<>();
        hashMap.put("StartDelay", startDelay);
        HashMap<String, Rect> hashMap2 = new HashMap<>();
        hashMap2.put("Rect", rect);
        handleCustomEvent(1, hashMap);
    }

    @Override
    public void update() {
        Log.i(TAG, "update");

        Drawable drawable = getResources().getDrawable(R.drawable.wall);
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        BitmapDrawable newBitmapDrawable = new BitmapDrawable(getResources(), bitmap);

        if (newBitmapDrawable == null) {
            Log.i(TAG, "newBitmapDrawable  is null");
            return;
        }
        Bitmap originBitmap = newBitmapDrawable.getBitmap();
        if (originBitmap != null) {
            setBitmap(originBitmap);
        }
    }


    @Override // com.galaxytheme.effect.KeyguardEffectViewBase
    public boolean handleTouchEventForPatternLock(MotionEvent motionEvent) {
        return false;
    }

    @Override // com.galaxytheme.effect.KeyguardEffectViewBase
    public boolean handleTouchEvent(View view, MotionEvent motionEvent) {
        if (isUnlocked) {
            Log.i(TAG, "handleTouchEvent isUnlocked : " + true);
        } else {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                this.dragSoudVolume = 1.0f;
                this.isFadeOutSound = false;
                stopReleaseSound();
                this.touchDownTime = System.currentTimeMillis();
                if (this.mSoundPool == null) {
                    Log.d(TAG, "ACTION_DOWN, mSoundPool == null");
                    makeSound();
                }
                Log.d(TAG, "SOUND PLAY SOUND_ID_TAB");
                playSound(0);
                if (this.dragStreamID != 0) {
                    if (this.mSoundPool != null) {
                        this.mSoundPool.stop(this.dragStreamID);
                    }
                    this.dragStreamID = 0;
                }
            } else if (actionMasked == 2) {
                if (this.dragStreamID == 0) {
                    this.dragSoudVolume = 1.0f;
                    this.touchMoveDiffTime = System.currentTimeMillis() - this.touchDownTime;
                    if (this.touchMoveDiffTime > this.mLongPressTime && this.touchDownTime != 0) {
                        Log.d(TAG, "SOUND PLAY SOUND_ID_DRAG touchMoveDiff = " + this.touchMoveDiffTime);
                        playSound(1);
                    }
                }
            } else if (actionMasked == 1 || actionMasked == 3 || actionMasked == 4) {
                if (this.dragStreamID != 0) {
                    this.dragSoudMinusOffset = 0.039f;
                    this.isFadeOutSound = true;
                    if (this.dragSoudVolume == 1.0f) {
                        fadeOutSound();
                    }
                }
            }
            handleTouchEvent(motionEvent, view);
        }
        return true;
    }

    public static KeyguardEffectViewBrilliantRing getInstance(Context context) {
        if (sKeyguardEffectViewBrilliantRing == null) {
            sKeyguardEffectViewBrilliantRing = new KeyguardEffectViewBrilliantRing(context);
            Log.i(TAG, "*** KeyguardEffectView create instance ***");
        }
        return sKeyguardEffectViewBrilliantRing;
    }


    @Override // com.galaxytheme.effect.KeyguardEffectViewBase
    public void screenTurnedOn() {
        Log.i(TAG, "screenTurnedOn");
        this.isUnlocked = false;
        clearScreen();
    }

    @Override
    public void screenTurnedOff() {
        Log.i(TAG, "screenTurnedOff");
        clearScreen();
    }

    @Override // com.galaxytheme.effect.KeyguardEffectViewBase
    public void handleUnlock(View view, MotionEvent motionEvent) {
        Log.i(TAG, "handleUnlock");
        handleCustomEvent(2, null);
        this.isUnlocked = true;
        this.dragSoudMinusOffset = 0.059f;
        playSound(2);
        this.isFadeOutSound = true;
        if (this.dragSoudVolume == 1.0f) {
            fadeOutSound();
        }
    }

    @Override // com.galaxytheme.effect.KeyguardEffectViewBase
    public void show() {
        Log.i(TAG, "show");
        makeSound();
        clearScreen();
        this.isUnlocked = false;
    }

    @Override // com.galaxytheme.effect.KeyguardEffectViewBase
    public void playLockSound() {
    }

    @Override // com.galaxytheme.effect.KeyguardEffectViewBase
    public long getUnlockDelay() {
        return 250L;
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        this.hasWindowFocus = hasWindowFocus;
        Log.d(TAG, "onWindowFocusChanged - " + hasWindowFocus);
        if (!hasWindowFocus && !this.isUnlocked && this.dragStreamID != 0 && !this.isUnlocked) {
            this.dragSoudMinusOffset = 0.039f;
            this.isFadeOutSound = true;
            if (this.dragSoudVolume == 1.0f) {
                fadeOutSound();
            }
        }
    }

    public void setContextualWallpaper(Bitmap bitmap) {
        Log.d(TAG, "setContextualWallpaper");
        if (bitmap == null) {
            Log.d(TAG, "bmp is null");
        } else {
            setBitmap(bitmap);
        }
    }

    public void setHidden(boolean z) {
    }
}
