package com.android.keyguard.sec;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.aj.effect.R;
import com.samsung.android.visualeffect.EffectDataObj;
import com.samsung.android.visualeffect.EffectType;
import com.samsung.android.visualeffect.EffectView;

import java.util.HashMap;

/* loaded from: classes.dex */
public class KeyguardEffectViewLensFlare extends FrameLayout implements KeyguardEffectViewBase {
    private static final String TAG = "KeyguardEffectViewLensFlare";
    private static final int TAP_SOUND_PATH = R.raw.lens_flare_tap; //String TAP_SOUND_PATH = "/system/media/audio/ui/ve_lensflare_tap.ogg";
    private static final int UNLOCK_SOUND_PATH = R.raw.lens_flare_unlock; //String UNLOCK_SOUND_PATH = "/system/media/audio/ui/ve_lensflare_unlock.ogg";
    private EffectView lensFlareEffect;
    private Context mContext;
    private static final int LOCK_SOUND_PATH = R.raw.lens_flare_lock; //String LOCK_SOUND_PATH = "/system/media/audio/ui/ve_lensflare_lock.ogg";
    private static final String SILENCE_SOUND_PATH = "/system/media/audio/ui/ve_silence.ogg";

    public KeyguardEffectViewLensFlare(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        Log.d(TAG, "KeyguardEffectViewLensFlare Constructor");
        this.mContext = context;
        this.lensFlareEffect = new EffectView(this.mContext);
        this.lensFlareEffect.setEffect(EffectType.LENSFLARE);
        EffectDataObj data = new EffectDataObj();
        data.setEffect(EffectType.LENSFLARE);
        data.lensFlareData.hexagon_blue = R.drawable.keyguard_flare_hexagon_blue;
        data.lensFlareData.hexagon_green = R.drawable.keyguard_flare_hexagon_green;
        data.lensFlareData.hexagon_orange = R.drawable.keyguard_flare_hexagon_orange;
        data.lensFlareData.hoverlight = R.drawable.keyguard_flare_hoverlight;
        data.lensFlareData.light = R.drawable.keyguard_flare_light_00040;
        data.lensFlareData.long_light = R.drawable.keyguard_flare_long;
        data.lensFlareData.particle = R.drawable.keyguard_flare_particle;
        data.lensFlareData.rainbow = R.drawable.keyguard_flare_rainbow;
        data.lensFlareData.ring = R.drawable.keyguard_flare_ring;
        data.lensFlareData.vignetting = R.drawable.keyguard_flare_vignetting;
        data.lensFlareData.tapSoundPath = TAP_SOUND_PATH;
        data.lensFlareData.unlockSoundPath = UNLOCK_SOUND_PATH;
        data.lensFlareData.lockSoundPath = LOCK_SOUND_PATH;
        this.lensFlareEffect.init(data);
        addView(this.lensFlareEffect);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void show() {
        Log.d(TAG, "show");
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("show", null);
        this.lensFlareEffect.handleCustomEvent(99, hm);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void reset() {
        Log.d(TAG, "reset");
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("reset", null);
        this.lensFlareEffect.handleCustomEvent(99, hm);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void cleanUp() {
        Log.d(TAG, "cleanUp");
        this.lensFlareEffect.clearScreen();
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void update() {
        Log.d(TAG, "update");
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void screenTurnedOn() {
        Log.d(TAG, "screenTurnedOn");
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("screenTurnedOn", null);
        this.lensFlareEffect.handleCustomEvent(99, hm);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void screenTurnedOff() {
        Log.d(TAG, "screenTurnedOff");
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void showUnlockAffordance(long startDelay, Rect rect) {
        Log.d(TAG, "showUnlockAffordance");
        HashMap<Object, Object> map = new HashMap<>();
        map.put("startDelay", startDelay);
        map.put("rect", rect);
        this.lensFlareEffect.handleCustomEvent(1, map);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public long getUnlockDelay() {
        return 250L;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void handleUnlock(View view, MotionEvent event) {
        Log.d(TAG, "handleUnlock");
        this.lensFlareEffect.handleCustomEvent(2, (HashMap) null);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void playLockSound() {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("playLockSound", null);
        this.lensFlareEffect.handleCustomEvent(99, hm);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public boolean handleTouchEvent(View view, MotionEvent event) {
        this.lensFlareEffect.handleTouchEvent(event, view);
        return true;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public boolean handleTouchEventForPatternLock(View view, MotionEvent event) {
        return true;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void setHidden(boolean isHidden) {
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public boolean handleHoverEvent(View view, MotionEvent event) {
        // todo switch to enable, was disabled
        return lensFlareEffect.handleHoverEvent(event); //false;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void setContextualWallpaper(Bitmap bmp) {
    }

    @Override
    public void drawPause() {
        lensFlareEffect.drawPause();
    }

    @Override
    public void drawResume() {
        lensFlareEffect.drawResume();
    }

    public static boolean isBackgroundEffect() {
        return false;
    }

    public static String getCounterEffectName() {
        return "Wallpaper";
    }
}