package com.android.keyguard.sec;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.aj.effect.R;
import com.aj.effect.SoundManager;
import com.aj.effect.Utils;
import com.samsung.android.visualeffect.EffectDataObj;
import com.samsung.android.visualeffect.EffectView;

import java.util.HashMap;

public class KeyguardEffectViewNone extends FrameLayout implements KeyguardEffectViewBase {
    public static final int TYPE_SHORTCUT = 1;
    public static final int TYPE_UNLOCK = 0;
    private final boolean DBG = true;
    private final String TAG = "VisualEffectCircleUnlockEffect";
    private EffectView circleEffect;
    private Context mContext;
    private HashMap<String, Object> touchHashmap;
    private static final int LOCK_SOUND_PATH = R.raw.ve_none_lock;
    private static final int UNLOCK_SOUND_PATH = R.raw.ve_none_unlock;
    private boolean soundLoaded = false;

    public KeyguardEffectViewNone(Context context) {
        super(context);
        init(context, TYPE_UNLOCK);
    }

    public void init(Context context, int type) {
        Log.d(TAG, "KeyguardEffectViewNone : Constructor");
        mContext = context.getApplicationContext();
        Rect dm = Utils.getViewRect(new DisplayMetrics(), (WindowManager) context.getSystemService(Context.WINDOW_SERVICE));
        int screenWidth = dm.width();
        int screenHeight = dm.height();
        int smallestWidth = Math.min(screenWidth, screenHeight);
        float ratio = smallestWidth / 1080.0f;
        Log.d(TAG, "screenWidth : " + screenWidth);
        Log.d(TAG, "screenHeight : " + screenHeight);
        Log.d(TAG, "ratio : " + ratio);
        touchHashmap = new HashMap<>();
        int circleUnlockMaxWidth = /*0;
        if (type == TYPE_UNLOCK) {
            circleUnlockMaxWidth = */((int) KeyguardEffectViewController.mRes.getDimension(R.dimen.keyguard_lockscreen_first_border)) * 2;
        /*} else if (type == TYPE_SHORTCUT) {
            circleUnlockMaxWidth = ((int) mContext.getResources().getDimension(R.dimen.keyguard_lockscreen_first_border)) * 2; // shortcut usage, but both the same
        }*/
        int outerStrokeWidth = (int) (4.0f * ratio);
        int innerStrokeWidth = (int) (6.0f * ratio);
        int[] lockSequenceImageId = {
                R.drawable.keyguard_none_lock_01,
                R.drawable.keyguard_none_lock_02,
                R.drawable.keyguard_none_lock_03,
                R.drawable.keyguard_none_lock_04,
                R.drawable.keyguard_none_lock_05,
                R.drawable.keyguard_none_lock_06,
                R.drawable.keyguard_none_lock_07,
                R.drawable.keyguard_none_lock_08,
                R.drawable.keyguard_none_lock_09,
                R.drawable.keyguard_none_lock_10,
                R.drawable.keyguard_none_lock_11,
                R.drawable.keyguard_none_lock_12,
                R.drawable.keyguard_none_lock_13,
                R.drawable.keyguard_none_lock_14,
                R.drawable.keyguard_none_lock_15,
                R.drawable.keyguard_none_lock_16,
                R.drawable.keyguard_none_lock_17,
                R.drawable.keyguard_none_lock_18,
                R.drawable.keyguard_none_lock_19,
                R.drawable.keyguard_none_lock_20,
                R.drawable.keyguard_none_lock_21,
                R.drawable.keyguard_none_lock_22,
                R.drawable.keyguard_none_lock_23,
                R.drawable.keyguard_none_lock_24,
                R.drawable.keyguard_none_lock_25,
                R.drawable.keyguard_none_lock_26,
                R.drawable.keyguard_none_lock_27,
                R.drawable.keyguard_none_lock_28,
                R.drawable.keyguard_none_lock_29,
                R.drawable.keyguard_none_lock_30
        };
        circleEffect = new EffectView(mContext);
        circleEffect.setEffect(2);
        EffectDataObj data = new EffectDataObj();
        data.setEffect(2);
        data.circleData.circleUnlockMaxWidth = circleUnlockMaxWidth;
        data.circleData.outerStrokeWidth = outerStrokeWidth;
        data.circleData.innerStrokeWidth = innerStrokeWidth;
        data.circleData.lockSequenceImageId = lockSequenceImageId;
        data.circleData.arrowId = R.drawable.keyguard_none_arrow;
        data.circleData.hasNoOuterCircle = false; // KeyguardProperties.isUSAFeature(); todo implement usa switch
        circleEffect.init(data);
        /*if (KeyguardProperties.isLatestPhoneUX() || KeyguardProperties.isLatestTabletUX()) { // todo shortcut parameters
            setMinWidthOffset((int) mContext.getResources().getDimension(R.dimen.keyguard_shortcut_min_width_offset)); // 0dp?
            setArrowForButton(R.drawable.keyguard_shortcut_arrow);
        }
        if (KeyguardProperties.isLatestShortcutEffect()) {
            setOuterCircleType(false);
            showSwipeCircleEffect(false);
        }*/
        addView(circleEffect);
        loadSound();
    }

    private void loadSound() {
        if (!soundLoaded) {
            SoundManager.loadSound(0, 0, LOCK_SOUND_PATH, UNLOCK_SOUND_PATH);
            soundLoaded = true;
        }
    }

    public void setMinWidthOffset(int offset) {
        EffectDataObj data = new EffectDataObj();
        data.setEffect(2);
        data.circleData.minWidthOffset = offset;
        circleEffect.reInit(data);
    }

    public void showSwipeCircleEffect(boolean value) {
        Log.d(TAG, "KeyguardEffectViewNone : showSwipeCircleEffect");
        HashMap<String, Boolean> hm = new HashMap<>();
        hm.put("showSwipeCircleEffect", value);
        circleEffect.handleCustomEvent(99, hm);
    }

    private void setOuterCircleType(boolean isStroke) {
        Log.d(TAG, "KeyguardEffectViewNone : setOuterCircleType");
        HashMap<String, Boolean> hm = new HashMap<>();
        hm.put("setOuterCircleType", isStroke);
        circleEffect.handleCustomEvent(99, hm);
    }

    public void setArrowForButton(int arrowForButtonId) {
        EffectDataObj data = new EffectDataObj();
        data.setEffect(2);
        data.circleData.arrowForButtonId = arrowForButtonId;
        circleEffect.reInit(data);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public boolean handleTouchEvent(View view, MotionEvent event) {
        circleEffect.handleTouchEvent(event, view);
        return true;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void show() {
        Log.d(TAG, "KeyguardEffectViewNone : show");
        loadSound();
        if (circleEffect != null) {
            circleEffect.clearScreen();
        }
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void reset() {
        Log.d(TAG, "KeyguardEffectViewNone : reset");
        if (circleEffect != null) {
            circleEffect.clearScreen();
        }
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void cleanUp() {
        SoundManager.loadSound(0);
        soundLoaded = false;
        Log.d(TAG, "KeyguardEffectViewNone : cleanUp");
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void update() {
        Log.d(TAG, "KeyguardEffectViewNone : update");
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void screenTurnedOn() {
        Log.d(TAG, "KeyguardEffectViewNone : screenTurnedOn");
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void screenTurnedOff() {
        Log.d(TAG, "KeyguardEffectViewNone : screenTurnedOff");
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void showUnlockAffordance(long startDelay, Rect rect) {
        Log.d(TAG, "KeyguardEffectViewNone : showUnlockAffordance");
        if (circleEffect != null) {
            HashMap<String, Object> hm = new HashMap<>();
            hm.put("StartDelay", startDelay);
            hm.put("Rect", rect);
            circleEffect.handleCustomEvent(1, hm);
        }
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public long getUnlockDelay() {
        return 0L;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void handleUnlock(View view, MotionEvent event) {
        Log.d(TAG, "KeyguardEffectViewNone : handleUnlock");
        if (circleEffect != null) {
            circleEffect.handleCustomEvent(2, null);
        }
        SoundManager.playSound(mContext, SoundManager.UNLOCK, 0.3f, 0.3f, 1, 0, 1);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void playLockSound() {
        SoundManager.playSound(mContext, SoundManager.LOCK, 0.3f,0.3f,1,0,1);
        Log.d(TAG, "KeyguardEffectViewNone : playLockSound");
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public boolean handleTouchEventForPatternLock(View view, MotionEvent event) {
        return false;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void setHidden(boolean isHidden) {
        if (circleEffect != null) {
            circleEffect.clearScreen();
        }
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public boolean handleHoverEvent(View view, MotionEvent event) {
        return false;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void setContextualWallpaper(Bitmap bmp) {
    }

    public static boolean isBackgroundEffect() {
        return false;
    }

    public static String getCounterEffectName() {
        return "Wallpaper";
    }
}