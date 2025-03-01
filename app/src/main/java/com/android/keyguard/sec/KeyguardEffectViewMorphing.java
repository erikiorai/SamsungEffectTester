package com.android.keyguard.sec;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.samsung.android.visualeffect.EffectView;

import java.util.HashMap;

public class KeyguardEffectViewMorphing extends FrameLayout implements KeyguardEffectViewBase {

    private Context context;
    private EffectView morphingEffect;
    private final String TAG = KeyguardEffectViewMorphing.class.getName();

    public KeyguardEffectViewMorphing(Context context) {
        super(context);
        this.context = context;
        morphingEffect = new EffectView(context);
        morphingEffect.setEffect(18);
        addView(morphingEffect);
    }

    @Override
    public void cleanUp() {
        morphingEffect.clearScreen();
    }

    @Override
    public long getUnlockDelay() {
        return 0;
    }

    @Override
    public boolean handleHoverEvent(View view, MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean handleTouchEvent(View view, MotionEvent motionEvent) {
        if (morphingEffect != null) {
            morphingEffect.handleTouchEvent(motionEvent, view);
        }
        return true;
    }

    @Override
    public boolean handleTouchEventForPatternLock(View view, MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void handleUnlock(View view, MotionEvent motionEvent) {
        morphingEffect.handleCustomEvent(2, null);
    }

    @Override
    public void playLockSound() {

    }

    @Override
    public void reset() {

    }

    @Override
    public void screenTurnedOff() {
        morphingEffect.clearScreen();
    }

    @Override
    public void screenTurnedOn() {

    }

    @Override
    public void setHidden(boolean hidden) {

    }

    @Override
    public void show() {
        if (morphingEffect != null) {
            morphingEffect.clearScreen();
        }
    }

    @Override
    public void showUnlockAffordance(long startDelay, Rect rect) {
        HashMap<String, Object> hm1 = new HashMap<>();
        hm1.put("StartDelay", startDelay);
        hm1.put("Rect", rect);
        morphingEffect.handleCustomEvent(1, hm1);
    }

    @Override
    public void update() {
        Log.i(TAG, "update");
        BitmapDrawable newBitmapDrawable = KeyguardEffectViewUtil.getCurrentWallpaper(context);
        if (newBitmapDrawable == null) {
            Log.i(TAG, "newBitmapDrawable  is null");
            return;
        }
        Bitmap originBitmap = newBitmapDrawable.getBitmap();
        if (originBitmap != null) {
            HashMap<String, Bitmap> map = new HashMap<>();
            map.put("BGBitmap", originBitmap);
            morphingEffect.handleCustomEvent(0, map);
        }
    }

    @Override
    public void setContextualWallpaper(Bitmap bmp) {

    }
// TODO: i dont even know if i should keep this.
    @Override
    public void drawPause() {

    }

    @Override
    public void drawResume() {

    }

    public static boolean isBackgroundEffect() {
        return true;
    }

    public static String getCounterEffectName() {
        return null;
    }
}
