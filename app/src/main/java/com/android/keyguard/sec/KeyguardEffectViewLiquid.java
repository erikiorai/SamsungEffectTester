package com.android.keyguard.sec;

import static com.aj.effect.SoundManager.attr;

import android.app.KeyguardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.SoundPool;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.aj.effect.R;
import com.aj.effect.Utils;
import com.samsung.android.visualeffect.liquid.LiquidEffect;
import java.io.InputStream;

/* loaded from: classes.dex */
public class KeyguardEffectViewLiquid extends FrameLayout implements KeyguardEffectViewBase {
    final int SOUND_ID_TAB;
    final int SOUND_ID_UNLOCK;
    private final String TAG;
    private final long UNLOCK_SOUND_PLAY_TIME;
    private boolean isSystemSoundChecked;
    private boolean isUnlocked;
    KeyguardManager keyguardManager;
    private float leftVolumeMax;
    private Context mContext;
    private SoundPool mSoundPool;
    private LiquidEffect mView;
    private String mWallpaperPath;
    private int prevOrientation;
    private Runnable releaseSoundRunnable;
    private float rightVolumeMax;
    private int[] sounds;
    private long touchDownTime;
    private long touchMoveDiffTime;
    private int windowHeight;
    private int windowWidth;

    public KeyguardEffectViewLiquid(Context context) {
        super(context);
        this.TAG = "Liquid_KeyguardEffectView";
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
        this.isUnlocked = false;
        this.prevOrientation = -1;
        this.windowWidth = 0;
        this.windowHeight = 0;
        this.mView = null;
        Log.d("Liquid_KeyguardEffectView", "KeyguardEffectViewLiquidLock Constructor");
        this.mContext = context;
        Rect rect = Utils.getViewRect(new DisplayMetrics(), (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE));
        this.windowWidth = rect.width();
        this.windowHeight = rect.height();
        this.mView = new LiquidEffect(this.mContext, this.windowWidth, this.windowHeight);
        this.mView.setResourcesBitmap1(makeResBitmap(R.drawable.kernel_512));
        this.sounds = new int[2];
        addView(this.mView);
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public void show() {
        Log.d("Liquid_KeyguardEffectView", "show");
        this.mView.show();
        this.isUnlocked = false;
        makeSound();
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public void reset() {
        this.mView.reset();
        this.isUnlocked = false;
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public void cleanUp() {
        stopReleaseSound();
        releaseSound();
        // from class: com.android.keyguard.sec.KeyguardEffectViewLiquid.1
// java.lang.Runnable
        postDelayed(() -> KeyguardEffectViewLiquid.this.mView.cleanUp(), 400L);
        this.isUnlocked = false;
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public void update() {
        Log.d("Liquid_KeyguardEffectView", "update");
        if (this.mView != null) {
            Log.d("Liquid_KeyguardEffectView", "changeBackground()");
            this.mView.changeBackground(setBackground());
        }
    }

    public static boolean isBackgroundEffect() {
        return true;
    }

    public static String getCounterEffectName() {
        return null;
    }

    public void setContextualWallpaper(Bitmap bmp) {
        Log.d("Liquid_KeyguardEffectView", "setContextualWallpaper");
        if (bmp == null) {
            Log.d("Liquid_KeyguardEffectView", "bmp is null" + bmp);
        } else if (this.mView != null) {
            Log.d("Liquid_KeyguardEffectView", "changeBackground()");
            this.mView.changeBackground(bmp);
        }
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public void screenTurnedOn() {
        this.mView.screenTurnedOn();
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public void screenTurnedOff() {
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public void showUnlockAffordance(long startDelay, Rect rect) {
        Log.i("Liquid_KeyguardEffectView", "showUnlockAffordance");
        this.mView.showUnlockAffordance();
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public long getUnlockDelay() {
        return 250L;
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public void handleUnlock(View view, MotionEvent event) {
        Log.i("Liquid_KeyguardEffectView", "handleUnlock");
        this.mView.unlockEffect();
        this.isUnlocked = true;
        playSound(1);
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public void playLockSound() {
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public boolean handleTouchEvent(View view, MotionEvent event) {
        if (!this.isUnlocked) {
            int action = event.getActionMasked();
            if (action == 0) {
                Log.i("Liquid_KeyguardEffectView", "handleTouchEvent action : " + action);
                stopReleaseSound();
                if (this.mSoundPool == null) {
                    Log.d("Liquid_KeyguardEffectView", "ACTION_DOWN, mSoundPool == null");
                    makeSound();
                    checkSound();
                }
                Log.d("Liquid_KeyguardEffectView", "SOUND PLAY SOUND_ID_TAB");
                playSound(0);
            } else if (action == 2 || action == 1 || action == 3 || action == 4) {
            }
            this.mView.handleTouchEvent(event);
        }
        return true;
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public boolean handleHoverEvent(View view, MotionEvent event) {
        return true;
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public boolean handleTouchEventForPatternLock(View view, MotionEvent event) {
        return true;
    }

    @Override // com.android.keyguard.sec.KeyguardEffectViewBase
    public void setHidden(boolean isHidden) {
    }

    private Bitmap setBackground() {
        Log.d("Liquid_KeyguardEffectView", "setBackground");
        BitmapDrawable newBitmapDrawable = KeyguardEffectViewUtil.getCurrentWallpaper(this.mContext);
        if (newBitmapDrawable == null) {
            Log.i("Liquid_KeyguardEffectView", "newBitmapDrawable  is null");
            return null;
        }
        Bitmap pBitmap = newBitmapDrawable.getBitmap();
        if (pBitmap == null) {
            Log.i("Liquid_KeyguardEffectView", "pBitmap  is null");
            return pBitmap;
        }
        Log.d("Liquid_KeyguardEffectView", "pBitmap.width = " + pBitmap.getWidth() + ", pBitmap.height = " + pBitmap.getHeight());
        return pBitmap;
    }

    private Bitmap makeResBitmap(int res) {
        Bitmap result = null;
        try {
            InputStream is = KeyguardEffectViewController.mRes.openRawResource(res);
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
        if (this.mSoundPool == null) {
            Log.d("Liquid_KeyguardEffectView", "sound : new SoundPool");
            this.mSoundPool = new SoundPool.Builder().setMaxStreams(10).setAudioAttributes(attr).build();
            this.sounds[0] = this.mSoundPool.load(KeyguardEffectViewController.mRes.openRawResourceFd(R.raw.liquid_tap), 1);
            this.sounds[1] = this.mSoundPool.load(KeyguardEffectViewController.mRes.openRawResourceFd(R.raw.liquid_unlock), 1);
            // from class: com.android.keyguard.sec.KeyguardEffectViewLiquid.2
// android.media.SoundPool.OnLoadCompleteListener
            this.mSoundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> Log.d("Liquid_KeyguardEffectView", "sound : onLoadComplete"));
        }
    }

    private void stopReleaseSound() {
        if (this.releaseSoundRunnable != null) {
            removeCallbacks(this.releaseSoundRunnable);
            this.releaseSoundRunnable = null;
        }
    }

    private void releaseSound() {
        // from class: com.android.keyguard.sec.KeyguardEffectViewLiquid.3
// java.lang.Runnable
        this.releaseSoundRunnable = () -> {
            if (KeyguardEffectViewLiquid.this.mSoundPool != null) {
                Log.d("Liquid_KeyguardEffectView", "BrilliantRing sound : release SoundPool");
                KeyguardEffectViewLiquid.this.mSoundPool.release();
                KeyguardEffectViewLiquid.this.mSoundPool = null;
            }
            KeyguardEffectViewLiquid.this.releaseSoundRunnable = null;
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
        Log.d("Liquid_KeyguardEffectView", "SOUND PLAY mSoundPool = " + this.mSoundPool + ", isSystemSoundChecked = " + this.isSystemSoundChecked);
        if (this.isSystemSoundChecked && this.mSoundPool != null) {
            Log.d("Liquid_KeyguardEffectView", "SOUND PLAY soundId = " + soundId);
            this.mSoundPool.play(this.sounds[soundId], this.leftVolumeMax, this.rightVolumeMax, 0, 0, 1.0f);
        }
    }
}