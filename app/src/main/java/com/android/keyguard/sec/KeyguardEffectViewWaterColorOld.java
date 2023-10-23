package com.android.keyguard.sec;

import android.app.KeyguardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
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
import android.widget.FrameLayout;

import com.aj.effect.MainActivity;
import com.aj.effect.R;
import com.aj.effect.Utils;
import com.sec.android.visualeffect.watercolor.WaterColorView;

import java.io.InputStream;

/* loaded from: classes.dex */
public class KeyguardEffectViewWaterColorOld extends FrameLayout implements KeyguardEffectViewBase {
    private final boolean DBG;
    final int SOUND_ID_TAB = 0;
    final int SOUND_ID_UNLOC = 1;
    private final String TAG;
    private final long UNLOCK_SOUND_PLAY_TIME;
    private boolean isSystemSoundChecked;
    private boolean isTablet;
    private boolean isUnlocked;
    KeyguardManager keyguardManager;
    private float leftVolumeMax;
    private Context mContext;
    private SoundPool mSoundPool;
    private int mSound_tap_id;
    private int mSound_unlock_id;
    private WaterColorView mView;
    private String mWallpaperPath;
    private int phoneMode;
    private int prevOrientation;
    private Runnable releaseSoundRunnable;
    private float rightVolumeMax;
    private int[] sounds;
    private int windowHeight;
    private int windowWidth;

    public KeyguardEffectViewWaterColorOld(Context context) {
        super(context);
        this.DBG = true;
        this.TAG = "WaterColor_KeyguardEffectView";
        this.mWallpaperPath = null;
        this.mSoundPool = null;
        this.mSound_tap_id = 0;
        this.mSound_unlock_id = 0;
        this.leftVolumeMax = 1.0f;
        this.rightVolumeMax = 1.0f;
        this.UNLOCK_SOUND_PLAY_TIME = 2000L;
        this.releaseSoundRunnable = null;
        this.isSystemSoundChecked = true;
        this.sounds = null;
        this.isUnlocked = false;
        this.prevOrientation = -1;
        this.windowWidth = 0;
        this.windowHeight = 0;
        this.isTablet = false;
        this.phoneMode = 0;
        this.mView = null;
        Log.d("WaterColor_KeyguardEffectView", "KeyguardEffectViewWaterColor Constructor");
        this.mContext = context;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager mWindowManager = (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
        Rect rect = Utils.getViewRect(displayMetrics, mWindowManager);
        windowWidth = rect.width();
        windowHeight = rect.height();
        this.isTablet = Utils.isTablet(mContext);
        if (this.isTablet) {
            if (this.windowWidth > this.windowHeight) {
                this.prevOrientation = 2;
            } else {
                this.prevOrientation = 1;
            }
            this.phoneMode = 1;
            Log.d("WaterColor_KeyguardEffectView", "isTablet is true");
        }
        this.mView = new WaterColorView(this.mContext, this.phoneMode, 2, this.windowWidth, this.windowHeight);
        this.mView.setResourcesBitmap(makeResBitmap(R.drawable.watercolor_mask1), makeResBitmap(R.drawable.watercolor_mask2), makeResBitmap(R.drawable.watercolor_mask3), makeResBitmap(R.drawable.waterbrush_tube), makeResBitmap(R.drawable.watercolor_noise));
        this.mView.setBackground(setBackground());
        this.sounds = new int[2];
        addView(this.mView);
    }

    private Bitmap setBackground() {
        Log.d("WaterColor_KeyguardEffectView", "setBackground");
        Bitmap pBitmap = MainActivity.bitm;/* todo null;
        BitmapDrawable newBitmapDrawable = KeyguardEffectViewUtil.getCurrentWallpaper(this.mContext);
        if (newBitmapDrawable != null) {
            pBitmap = newBitmapDrawable.getBitmap();
            if (pBitmap != null) {
                Log.d("WaterColor_KeyguardEffectView", "pBitmap.width = " + pBitmap.getWidth() + ", pBitmap.height = " + pBitmap.getHeight());
            } else {
                Log.d("WaterColor_KeyguardEffectView", "pBitmap is null");
            }
        } else {
            Log.d("WaterColor_KeyguardEffectView", "newBitmapDrawable is null");
        }*/
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
        if (this.mSoundPool == null) { // KeyguardUpdateMonitor.getInstance(this.mContext).hasBootCompleted() &&
            Log.d("WaterColor_KeyguardEffectView", "WaterColor sound : new SoundPool");
            AudioAttributes attr = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
            this.mSoundPool = new SoundPool.Builder().setMaxStreams(10).setAudioAttributes(attr).build();
            this.sounds[SOUND_ID_TAB] = this.mSoundPool.load(this.mContext, R.raw.watercolor_tap, 1);
            this.sounds[SOUND_ID_UNLOC] = this.mSoundPool.load(this.mContext, R.raw.watercolor_unlock, 1);
        }
    }

    private void releaseSound() {
        this.releaseSoundRunnable = new Runnable() { // from class: com.android.keyguard.sec.KeyguardEffectViewWaterColor.1
            @Override // java.lang.Runnable
            public void run() {
                if (KeyguardEffectViewWaterColorOld.this.mSoundPool != null) {
                    Log.d("WaterColor_KeyguardEffectView", "WaterColor sound : release SoundPool");
                    KeyguardEffectViewWaterColorOld.this.mSoundPool.release();
                    KeyguardEffectViewWaterColorOld.this.mSoundPool = null;
                }
                KeyguardEffectViewWaterColorOld.this.releaseSoundRunnable = null;
            }
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
        if (this.isSystemSoundChecked && this.mSoundPool != null) {
            this.mSoundPool.play(this.sounds[soundId], this.leftVolumeMax, this.rightVolumeMax, 0, 0, 1.0f);
        }
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.isTablet) {
            Log.d("WaterColor_KeyguardEffectView", "keyguardManager.isKeyguardLocked() = " + this.keyguardManager.isKeyguardLocked());
            if (this.keyguardManager.isKeyguardLocked()) {
                if (newConfig.orientation == 2) {
                    Log.d("WaterColor_KeyguardEffectView", "= onConfigurationChanged = ORIENTATION_LANDSCAPE");
                    if (this.prevOrientation != 2) {
                        this.prevOrientation = 2;
                        this.mView.onConfigurationChanged();
                    }
                } else if (newConfig.orientation == 1) {
                    Log.d("WaterColor_KeyguardEffectView", "= onConfigurationChanged = ORIENTATION_PORTRAIT");
                    if (this.prevOrientation != 1) {
                        this.prevOrientation = 1;
                        this.mView.onConfigurationChanged();
                    }
                }
            }
        }
    }

    private void checkSound() {
        ContentResolver cr = this.mContext.getContentResolver();
        int result = Settings.System.getInt(cr, "lockscreen_sounds_enabled", -2);
        this.isSystemSoundChecked = result == 1;
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public void show() {
        Log.d("WaterColor_KeyguardEffectView", "show");
        makeSound();
        this.mView.show();
        this.isUnlocked = false;
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public void reset() {
        this.mView.reset();
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public void cleanUp() {
        this.mView.cleanUp();
        stopReleaseSound();
        releaseSound();
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public void update() {
        Log.d("WaterColor_KeyguardEffectView", "update");
        if (this.mView != null) {
            Log.d("WaterColor_KeyguardEffectView", "changeBackground()");
            this.mView.changeBackground(setBackground());
        }
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public void screenTurnedOn() {
        this.mView.screenTurnedOn();
        this.isUnlocked = false;
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public void screenTurnedOff() {
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public void showUnlockAffordance(long startDelay, Rect rect) {
        Log.d("WaterColor_KeyguardEffectView", "showUnlockAffordance startDelay : " + startDelay);
        this.mView.showUnlockAffordance(startDelay, rect);
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public long getUnlockDelay() {
        return 400L;
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public void handleUnlock(View view, MotionEvent event) {
        Log.d("WaterColor_KeyguardEffectView", "handleUnlock, SOUND PLAY SOUND_ID_UNLOC");
        this.mView.unlockEffect();
        playSound(SOUND_ID_UNLOC);
        this.isUnlocked = true;
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public void playLockSound() {
        playSound(SOUND_ID_UNLOC);
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public boolean handleTouchEvent(View view, MotionEvent event) {
        if (this.mView.handleTouchEvent(event)) {
            checkSound();
            if (event.getActionMasked() == 0) {
                stopReleaseSound();
                if (this.mSoundPool == null) {
                    Log.d("WaterColor_KeyguardEffectView", "ACTION_DOWN, mSoundPool == null");
                    makeSound();
                }
                playSound(0);
            } else if (event.getActionMasked() == 1) {
                playSound(0);
            }
        }
        return true;
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public boolean handleTouchEventForPatternLock(View view, MotionEvent event) {
        this.mView.handleTouchEventForPatternLock(event);
        return true;
    }

    public void setContextualWallpaper(Bitmap bmp) {
        if (bmp != null && this.mView != null) {
            this.mView.changeBackground(bmp);
        }
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public void setHidden(boolean isHidden) {
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public boolean handleHoverEvent(View view, MotionEvent event) {
        return false;
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        Log.d("WaterColor_KeyguardEffectView", "onWindowFocusChanged hasWindowFocus = " + hasWindowFocus);
        if (hasWindowFocus) {
            this.mView.startAnimation();
        } else if (!hasWindowFocus && !this.isUnlocked) {
            this.mView.stopAnimation();
        }
    }
}