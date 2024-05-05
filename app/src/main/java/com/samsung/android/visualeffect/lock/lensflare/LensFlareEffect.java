package com.samsung.android.visualeffect.lock.lensflare;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.BaseInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.aj.effect.interpolator.QuintEaseOut;
import com.aj.effect.Utils;
import com.samsung.android.visualeffect.EffectDataObj;
import com.samsung.android.visualeffect.IEffectListener;
import com.samsung.android.visualeffect.IEffectView;
import com.samsung.android.visualeffect.common.ImageViewBlended;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

class BackEaseOut extends BaseInterpolator {
    private float overshot;

    public BackEaseOut(float overshot) {
        this.overshot = overshot;
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float t) {
        return out(t, this.overshot);
    }

    private float out(float t, float o) {
        if (o == 0.0f) {
            o = 1.70158f;
        }
        float t2 = t - 1.0f;
        return (t2 * t2 * (((o + 1.0f) * t2) + o)) + 1.0f;
    }
}

public class LensFlareEffect extends FrameLayout implements IEffectView {
    private final int AFFORDANCE_OFF_DURATION = 1100;
    private final int AFFORDANCE_ON_DURATION = 200;
    private final boolean DBG = true;
    private final int FADEOUT_ANIMATION_DURATION = 500;
    private int FINGER_HOVER_Y_OFFSET = -80;
    private final float FOG_MAX_ALPHA = 0.6f;
    private final int FOG_ON_DURATION = 100;
    private final int HEXAGON_CIRCLE_TOTAL = 0;
    private int HEXAGON_TAP_TOTAL = 5;
    private int HEXAGON_TOTAL;
    private final int HOVER_DURATION = 100000;
    private final int HOVER_LIGHT_IN_DURATION = 500;
    private final int HOVER_LIGHT_OUT_DURATION = 300;
    private int MAX_ALPHA_DISTANCE = 1500;
    private int PEN_HOVER_Y_OFFSET = 0;
    private final int SHOW_ANIMATION_DURATION = 6000;
    private final String TAG = "LensFlare";
    private final int TAP_ANIMATION_DURATION = 4000;
    private int TAP_AREA_RADIUS = 600;
    private final int UNLOCK_ANIMATION_DURATION = 1200;
    private final long UNLOCK_SOUND_PLAY_TIME = 2000L;
    private final int X_OFFSET = 0;
    private int Y_OFFSET = -80;
    private float affordanceAnimationValue;
    private ValueAnimator affordanceOffAnimator;
    private ValueAnimator affordanceOnAnimator;
    private Point affordancePoint = new Point();
    private Runnable affordanceRunnable;
    private long createdDelaytime = 100L;
    private float currentX;
    private float currentY;
    private Bitmap.Config defaultConfig = Bitmap.Config.RGB_565;
    private final float defaultInSampleSize = 0.7f; // todo: size
    private double distance;
    private float distancePerMaxAlpha;
    private ValueAnimator fadeOutAnimator;
    private float fadeoutAnimationValue;
    private float fogAlpha;
    private float fogAnimationValue;
    private ValueAnimator fogOnAnimator;
    private float globalAlpha = 0.8f;
    private ImageViewBlended[] hexagon;
    private ArrayList<Float> hexagonDistance;
    private int[] hexagonRes;
    private int[] hexagonRotation;
    private float[] hexagonScale;
    private int hexagon_blue_id;
    private int hexagon_green_id;
    private int hexagon_orange_id;
    private ValueAnimator hoverAnimator;
    private ImageViewBlended hoverLight1;
    private float hoverLightAnimationValue;
    private ValueAnimator hoverLightInAnimator;
    private ValueAnimator hoverLightOutAnimator;
    private float hoverX;
    private float hoverY;
    private int hoverlight_id;
    private boolean isBeforeInit = true;
    private boolean isPlayAffordance = false;
    private boolean isSoundEnable = true;
    private boolean isSystemSoundChecked;
    private boolean isTouched = false;
    private ImageViewBlended lightFog;
    private FrameLayout lightObj;
    private FrameLayout lightTap;
    private int light_id;
    private ImageViewBlended longLight;
    private int long_light_id;
    private Context mContext;
    private Runnable mFirstCreatedRunnable;
    private float objAlpha;
    private float objAnimationValue;
    private ValueAnimator objAnimator;
    private ImageViewBlended particle;
    private int particle_id;
    private ImageViewBlended rainbow;
    private int rainbow_id;
    private float randomRotation;
    private Runnable releaseSoundRunnable;
    private ImageViewBlended ring;
    private int ring_id;
    private int screenHeight;
    private int screenWidth;
    private float showStartX;
    private float showStartY;
    private int sound_tap;
    private int sound_unlock;
    private int sound_lock;
    private SoundPool soundpool;
    private float tapAnimationValue;
    private ValueAnimator tapAnimator;
    private Point[] tapHexRandomPoint;
    private ImageViewBlended[] tapHexagon;
    private float[] tapHexagonScale;
    private int tap_sound_path;
    private float unlockAnimationValue;
    private ValueAnimator unlockAnimator;
    private int unlock_sound_path;
    private int lock_sound_path;
    private ImageView vignetting;
    private float vignettingAlpha;
    private int vignetting_id;

    public LensFlareEffect(Context context) {
        super(context);
        this.mContext = context;
    }

    public LensFlareEffect(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
    }

    public LensFlareEffect(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    private void setImageResourceId(int hexagon_blue, int hexagon_green, int hexagon_orange, int hoverlight, int light, int long_light, int particle, int rainbow, int ring, int vignetting) {
        this.hexagon_blue_id = hexagon_blue;
        this.hexagon_green_id = hexagon_green;
        this.hexagon_orange_id = hexagon_orange;
        this.hoverlight_id = hoverlight;
        this.light_id = light;
        this.long_light_id = long_light;
        this.particle_id = particle;
        this.rainbow_id = rainbow;
        this.ring_id = ring;
        this.vignetting_id = vignetting;
    }

    private void setSoundResourceId(int tapSound, int unlockSound, int lockSound) {
        this.tap_sound_path = tapSound;
        this.unlock_sound_path = unlockSound;
        lock_sound_path = lockSound;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lensFlareinit() {
        if (getChildCount() == 0) {
            Log.d(TAG, "this.getChildCount() == 0");
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager dis = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            Rect rect = Utils.getViewRect(dm, dis);
            screenWidth = rect.width();
            screenHeight = rect.height();
            int smallestWidth = Math.min(this.screenWidth, this.screenHeight);
            Log.d(TAG, "lensFlareinit ============================");
            Log.d(TAG, "screenWidth : " + this.screenWidth);
            Log.d(TAG, "screenHeight : " + this.screenHeight);
            Log.d(TAG, "smallestWidth : " + smallestWidth);
            Log.d(TAG, "GLOBALCONFIG_LOCKSCREEN_LIGHT_OPACITY : 0.8");
            this.globalAlpha = Float.parseFloat("0.8");
            if (smallestWidth != 1080) {
                float ratio = smallestWidth / 1080.0f;
                Log.d(TAG, "ratio : " + ratio);
                this.Y_OFFSET = (int) (this.Y_OFFSET * ratio);
                this.MAX_ALPHA_DISTANCE = (int) (this.MAX_ALPHA_DISTANCE * ratio);
                this.TAP_AREA_RADIUS = (int) (this.TAP_AREA_RADIUS * ratio);
            }
            Log.d(TAG, "Y_OFFSET : " + this.Y_OFFSET);
            Log.d(TAG, "MAX_ALPHA_DISTANCE : " + this.MAX_ALPHA_DISTANCE);
            Log.d(TAG, "TAP_AREA_RADIUS : " + this.TAP_AREA_RADIUS);
            setLayout();
            setHover();
            setHexagon();
            setTapHexagon();
            setAnimator();
        }
        this.isBeforeInit = false;
        this.mFirstCreatedRunnable = null;
    }

    private void setLayout() {
        this.vignetting = new ImageView(this.mContext);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ALPHA_8;
        this.vignetting.setImageBitmap(BitmapFactory.decodeResource(getResources(), this.vignetting_id, options));
        this.vignetting.setScaleType(ImageView.ScaleType.FIT_XY);
        this.vignetting.setAlpha(0.0f);
        addView(this.vignetting);
        this.lightFog = new ImageViewBlended(this.mContext);
        setImageWithOption(this.lightFog, this.light_id, null);
        addView(this.lightFog, -2, -2);
        setAlphaAndVisibility(this.lightFog, 0.0f);
        this.lightObj = new FrameLayout(this.mContext);
        addView(this.lightObj);
        this.lightTap = new FrameLayout(this.mContext);
        addView(this.lightTap);
        this.ring = new ImageViewBlended(this.mContext);
        setImageWithOption(this.ring, this.ring_id, null);
        addView(this.ring, -2, -2);
        setAlphaAndVisibility(this.ring, 0.0f);
        this.longLight = new ImageViewBlended(this.mContext);
        setImageWithOption(this.longLight, this.long_light_id, null);
        addView(this.longLight, -2, -2);
        setAlphaAndVisibility(this.longLight, 0.0f);
        this.particle = new ImageViewBlended(this.mContext);
        setImageWithOption(this.particle, this.particle_id, null);
        addView(this.particle, -2, -2);
        setAlphaAndVisibility(this.particle, 0.0f);
        this.rainbow = new ImageViewBlended(this.mContext);
        setImageWithOption(this.rainbow, this.rainbow_id, null);
        addView(this.rainbow, -2, -2);
        setAlphaAndVisibility(this.rainbow, 0.0f);
    }

    private void setImageWithOption(ImageView imageView, int resId, Bitmap.Config config) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        if (config == null) {
            options.inPreferredConfig = Bitmap.Config.RGB_565;
        } else {
            options.inPreferredConfig = config;
        }
        imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), resId, options));
        imageView.setScaleX(this.defaultInSampleSize);
        imageView.setScaleY(this.defaultInSampleSize);
    }

    private void setHover() {
        this.hoverLight1 = new ImageViewBlended(this.mContext);
        setImageWithOption(this.hoverLight1, this.hoverlight_id, null);
        this.hoverLight1.setScaleX(this.defaultInSampleSize * 0.0f);
        this.hoverLight1.setScaleY(this.defaultInSampleSize * 0.0f);
        addView(this.hoverLight1, -2, -2);
    }

    private void setHexagon() {
        this.hexagonRes = new int[]{this.hexagon_blue_id, this.hexagon_orange_id, this.hexagon_blue_id, this.hexagon_orange_id, this.hexagon_green_id, this.hexagon_green_id};
        this.HEXAGON_TOTAL = this.hexagonRes.length;
        this.hexagon = new ImageViewBlended[this.HEXAGON_TOTAL];
        this.hexagonRotation = new int[this.HEXAGON_TOTAL];
        this.hexagonDistance = new ArrayList<>();
        this.hexagonScale = new float[this.HEXAGON_TOTAL];
        for (int i = 0; i < this.HEXAGON_TOTAL; i++) {
            ImageViewBlended hex = new ImageViewBlended(this.mContext);
            setImageWithOption(hex, this.hexagonRes[i], null);
            float rotation = (float) (Math.random() * 20.0d);
            setAlphaAndVisibility(hex, 0.0f);
            hex.setRotation(rotation);
            this.lightObj.addView(hex, -2, -2);
            this.hexagon[i] = hex;
        }
    }

    private void setTapHexagon() {
        this.tapHexagon = new ImageViewBlended[this.HEXAGON_TAP_TOTAL];
        for (int i = 0; i < this.HEXAGON_TAP_TOTAL; i++) {
            int index = i % 3;
            int resource = 0;
            switch (index) {
                case 0:
                    resource = this.hexagon_blue_id;
                    break;
                case 1:
                    resource = this.hexagon_orange_id;
                    break;
                case 2:
                    resource = this.hexagon_green_id;
                    break;
            }
            ImageViewBlended hex = new ImageViewBlended(this.mContext);
            setImageWithOption(hex, resource, null);
            setAlphaAndVisibility(hex, 0.0f);
            int randomRotation = (int) (Math.random() * 360.0d);
            hex.setRotation(randomRotation);
            this.lightTap.addView(hex, -2, -2);
            this.tapHexagon[i] = hex;
        }
    }

    private void setHexagonRandomTarget(boolean isForUnlockAffordance) {
        this.tapHexRandomPoint = new Point[this.HEXAGON_TAP_TOTAL];
        this.tapHexagonScale = new float[this.HEXAGON_TAP_TOTAL];
        this.randomRotation = (int) (Math.random() * 360.0d);
        for (int i = 0; i < this.HEXAGON_TAP_TOTAL; i++) {
            if (isForUnlockAffordance) {
                this.randomRotation = (int) (Math.random() * 360.0d);
            }
            int randomDistance = (int) (Math.random() * this.TAP_AREA_RADIUS);
            int tx = (int) (Math.cos(this.randomRotation) * randomDistance);
            int ty = (int) (Math.sin(this.randomRotation) * randomDistance);
            this.tapHexRandomPoint[i] = new Point(tx, ty);
            this.tapHexagonScale[i] = 0.2f + ((float) (Math.random() * 0.800000011920929d));
            float alpha = 0.5f + (0.5f * ((float) Math.random()));
            setAlphaAndVisibility(this.tapHexagon[i], alpha);
        }
        if (!isForUnlockAffordance) {
            this.hexagonDistance.clear();
            for (int j = 0; j < this.HEXAGON_TOTAL; j++) {
                float random = (((float) Math.random()) - 0.5f) * 0.4f;
                this.hexagonDistance.add((j * 0.24f) + 0.2f + random);
            }
            Collections.shuffle(this.hexagonDistance);
            for (int i2 = 0; i2 < this.HEXAGON_TOTAL; i2++) {
                ImageViewBlended hex = this.hexagon[i2];
                if (i2 < this.HEXAGON_TOTAL) {
                    this.hexagonRotation[i2] = 0;
                    this.hexagonScale[i2] = this.hexagonDistance.get(i2) + 0.1f;
                } else {
                    this.hexagonRotation[i2] = (int) (Math.random() * 360.0d);
                    this.hexagonScale[i2] = 0.6f + ((float) (Math.random()));
                }
                hex.setScaleX(this.hexagonScale[i2] * this.defaultInSampleSize);
                hex.setScaleY(this.hexagonScale[i2] * this.defaultInSampleSize);
            }
            this.particle.setRotation(this.randomRotation);
        }
    }

    private void setSound() {
        stopReleaseSound();
        ContentResolver cr = this.mContext.getContentResolver();
        int result = 0;
        try {
            result = Settings.System.getInt(cr, "lockscreen_sounds_enabled");
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        this.isSystemSoundChecked = result == 1;
        if (this.soundpool == null) {
            AudioAttributes attr = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
            this.soundpool = new SoundPool.Builder().setMaxStreams(10).setAudioAttributes(attr).build();
            this.sound_tap = this.soundpool.load(mContext, tap_sound_path, 1);
            this.sound_unlock = this.soundpool.load(mContext, unlock_sound_path, 1);
            sound_lock = soundpool.load(mContext, lock_sound_path, 1);
            Log.d(TAG, "LensFlare sound : load");
        }
    }

    private void setAnimator() {
        this.objAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.objAnimator.setInterpolator(new QuintEaseOut());
        this.objAnimator.setDuration(SHOW_ANIMATION_DURATION);
        // from class: com.samsung.android.visualeffect.lock.lensflare.LensFlareEffect.1
// android.animation.ValueAnimator.AnimatorUpdateListener
        this.objAnimator.addUpdateListener(animation -> {
            LensFlareEffect.this.objAnimationValue = (Float) animation.getAnimatedValue();
            LensFlareEffect.this.animatedDragPos();
        });
        this.fogOnAnimator = ValueAnimator.ofFloat(0.0f, FOG_MAX_ALPHA);
        this.fogOnAnimator.setInterpolator(new QuintEaseOut());
        this.fogOnAnimator.setDuration(FOG_ON_DURATION);
        // from class: com.samsung.android.visualeffect.lock.lensflare.LensFlareEffect.2
// android.animation.ValueAnimator.AnimatorUpdateListener
        this.fogOnAnimator.addUpdateListener(animation -> {
            LensFlareEffect.this.fogAnimationValue = (Float) animation.getAnimatedValue();
            LensFlareEffect.this.animatedDragAlpha();
        });
        this.tapAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.tapAnimator.setInterpolator(new QuintEaseOut());
        this.tapAnimator.setDuration(TAP_ANIMATION_DURATION);
        // from class: com.samsung.android.visualeffect.lock.lensflare.LensFlareEffect.3
// android.animation.ValueAnimator.AnimatorUpdateListener
        this.tapAnimator.addUpdateListener(animation -> {
            LensFlareEffect.this.tapAnimationValue = (Float) animation.getAnimatedValue();
            LensFlareEffect.this.animatedTap();
        });
        this.fadeOutAnimator = ValueAnimator.ofFloat(1.0f, 0.0f);
        this.fadeOutAnimator.setInterpolator(new LinearInterpolator());
        this.fadeOutAnimator.setDuration(FADEOUT_ANIMATION_DURATION);
        // from class: com.samsung.android.visualeffect.lock.lensflare.LensFlareEffect.4
// android.animation.ValueAnimator.AnimatorUpdateListener
        this.fadeOutAnimator.addUpdateListener(animation -> {
            LensFlareEffect.this.fadeoutAnimationValue = (Float) animation.getAnimatedValue();
            LensFlareEffect.this.animatedFadeOut();
        });
        this.unlockAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.unlockAnimator.setInterpolator(new QuintEaseOut());
        this.unlockAnimator.setDuration(UNLOCK_ANIMATION_DURATION);
        // from class: com.samsung.android.visualeffect.lock.lensflare.LensFlareEffect.5
// android.animation.ValueAnimator.AnimatorUpdateListener
        this.unlockAnimator.addUpdateListener(animation -> {
            LensFlareEffect.this.unlockAnimationValue = (Float) animation.getAnimatedValue();
            LensFlareEffect.this.animatedUnlock();
        });
        this.hoverAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.hoverAnimator.setInterpolator(new LinearInterpolator());
        this.hoverAnimator.setRepeatCount(10000);
        this.hoverAnimator.setDuration(HOVER_DURATION);
        // from class: com.samsung.android.visualeffect.lock.lensflare.LensFlareEffect.6
// android.animation.ValueAnimator.AnimatorUpdateListener
        this.hoverAnimator.addUpdateListener(animation -> LensFlareEffect.this.animatedHover());
        this.hoverLightInAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.hoverLightInAnimator.setInterpolator(new BackEaseOut(8.0f));
        this.hoverLightInAnimator.setDuration(HOVER_LIGHT_IN_DURATION);
        // from class: com.samsung.android.visualeffect.lock.lensflare.LensFlareEffect.7
// android.animation.ValueAnimator.AnimatorUpdateListener
        this.hoverLightInAnimator.addUpdateListener(animation -> {
            LensFlareEffect.this.hoverLightAnimationValue = (Float) animation.getAnimatedValue();
            LensFlareEffect.this.animatedHoverLight();
        });
        this.hoverLightOutAnimator = ValueAnimator.ofFloat(1.0f, 0.0f);
        this.hoverLightOutAnimator.setInterpolator(new QuintEaseOut());
        this.hoverLightOutAnimator.setDuration(HOVER_LIGHT_OUT_DURATION);
        // from class: com.samsung.android.visualeffect.lock.lensflare.LensFlareEffect.8
// android.animation.ValueAnimator.AnimatorUpdateListener
        this.hoverLightOutAnimator.addUpdateListener(animation -> {
            LensFlareEffect.this.hoverLightAnimationValue = (Float) animation.getAnimatedValue();
            LensFlareEffect.this.animatedHoverLight();
        });
        this.affordanceOnAnimator = ValueAnimator.ofFloat(0.0f, 0.6f);
        this.affordanceOnAnimator.setInterpolator(new LinearInterpolator());
        this.affordanceOnAnimator.setDuration(AFFORDANCE_ON_DURATION);
        // from class: com.samsung.android.visualeffect.lock.lensflare.LensFlareEffect.9
// android.animation.ValueAnimator.AnimatorUpdateListener
        this.affordanceOnAnimator.addUpdateListener(animation -> {
            LensFlareEffect.this.affordanceAnimationValue = (Float) animation.getAnimatedValue();
            LensFlareEffect.this.animatedAffordance();
            if (Float.compare(LensFlareEffect.this.affordanceAnimationValue, 0.6f) == 0) {
                LensFlareEffect.this.cancelAnimator(LensFlareEffect.this.affordanceOnAnimator);
                LensFlareEffect.this.affordanceOffAnimator.start();
            }
        });
        this.affordanceOffAnimator = ValueAnimator.ofFloat(0.6f, 0.0f);
        this.affordanceOffAnimator.setInterpolator(new LinearInterpolator());
        this.affordanceOffAnimator.setDuration(AFFORDANCE_OFF_DURATION);
        // from class: com.samsung.android.visualeffect.lock.lensflare.LensFlareEffect.10
// android.animation.ValueAnimator.AnimatorUpdateListener
        this.affordanceOffAnimator.addUpdateListener(animation -> {
            LensFlareEffect.this.affordanceAnimationValue = (Float) animation.getAnimatedValue();
            LensFlareEffect.this.animatedAffordance();
        });
    }

    private void playSound(int soundId) {
        if (this.isSoundEnable && this.isSystemSoundChecked && this.soundpool != null) {
            this.soundpool.play(soundId, 1.0f, 1.0f, 0, 0, 1.0f);
        }
    }

    private void showLight(float x, float y) {
        this.isTouched = true;
        stopUnlockAffordance();
        this.distance = 0.0d;
        this.distancePerMaxAlpha = 0.0f;
        this.showStartX = x + 0.0f;
        this.showStartY = this.Y_OFFSET + y;
        this.currentX = this.showStartX;
        this.currentY = this.showStartY;
        setHexagonRandomTarget(false);
        animatedDragPos();
        setCenterPos(this.lightFog, this.showStartX, this.showStartY, this.showStartX, this.showStartY, 1.0f);
        setCenterPos(this.ring, this.showStartX, this.showStartY, this.showStartX, this.showStartY, 1.0f);
        setCenterPos(this.longLight, this.showStartX, this.showStartY, this.showStartX, this.showStartY, 1.0f);
        setCenterPos(this.particle, this.showStartX, this.showStartY, this.showStartX, this.showStartY, 1.0f);
        cancelAnimator(this.fadeOutAnimator);
        cancelAnimator(this.objAnimator);
        cancelAnimator(this.fogOnAnimator);
        cancelAnimator(this.tapAnimator);
        cancelAnimator(this.affordanceOnAnimator);
        cancelAnimator(this.affordanceOffAnimator);
        this.objAnimator.start();
        this.fogOnAnimator.start();
        this.tapAnimator.start();
        playSound(this.sound_tap);
    }

    private void move(float x, float y) {
        if (!this.isTouched) {
            showLight(x, y);
            return;
        }
        this.currentX = 0.0f + x;
        this.currentY = this.Y_OFFSET + y;
        calculateDistance(this.currentX, this.currentY);
        if (!this.fogOnAnimator.isRunning()) {
            animatedDragAlpha();
        }
        if (!this.objAnimator.isRunning()) {
            animatedDragPos();
        }
    }

    private void hide() {
        this.isTouched = false;
        cancelAnimator(this.fogOnAnimator);
        this.fadeOutAnimator.start();
    }

    private void unlock() {
        if (this.isBeforeInit) {
            Log.d(TAG, "unlock before init");
            lensFlareinit();
            return;
        }
        playSound(this.sound_unlock);
        float degree = ((float) ((Math.atan2(this.currentY - this.showStartY, this.currentX - this.showStartX) * 180.0d) / 3.141592653589793d)) - 40.0f;
        this.rainbow.setRotation(degree);
        this.unlockAnimator.start();
    }

    private void hoverEnter(float x, float y) {
        this.hoverX = 0.0f + x;
        this.hoverY = this.Y_OFFSET + y;
        this.hoverAnimator.start();
        cancelAnimator(this.hoverLightInAnimator);
        cancelAnimator(this.hoverLightOutAnimator);
        this.hoverLightInAnimator.start();
    }

    private void hoverMove(float x, float y) {
        this.hoverX = 0.0f + x;
        this.hoverY = this.Y_OFFSET + y;
    }

    private void hoverExit() {
        cancelAnimator(this.hoverAnimator);
        cancelAnimator(this.hoverLightInAnimator);
        cancelAnimator(this.hoverLightOutAnimator);
        this.hoverLightOutAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animatedHover() {
        this.hoverLight1.setX(this.hoverLight1.getX() + (((this.hoverX - (this.hoverLight1.getWidth() / 2.0f)) - this.hoverLight1.getX()) / 3.0f));
        this.hoverLight1.setY(this.hoverLight1.getY() + (((this.hoverY - (this.hoverLight1.getHeight() / 2.0f)) - this.hoverLight1.getY()) / 3.0f));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animatedHoverLight() {
        this.hoverLight1.setScaleX(this.hoverLightAnimationValue * this.defaultInSampleSize);
        this.hoverLight1.setScaleY(this.hoverLightAnimationValue * this.defaultInSampleSize);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animatedTap() {
        float alpha = this.tapAnimationValue < 0.5f ? 1.0f : 1.0f - ((this.tapAnimationValue - 0.5f) * 2.0f);
        float alpha2 = alpha * this.globalAlpha;
        float distanceScale = 0.2f + (0.8f * this.tapAnimationValue);
        for (int i = 0; i < this.HEXAGON_TAP_TOTAL; i++) {
            ImageView hex = this.tapHexagon[i];
            setAlphaAndVisibility(hex, alpha2);
            float scale = this.tapHexagonScale[i] * ((this.tapAnimationValue * 0.8f) + 0.7f);
            hex.setScaleX(this.defaultInSampleSize * scale);
            hex.setScaleY(this.defaultInSampleSize * scale);
            float tx = this.showStartX + (this.tapHexRandomPoint[i].x * distanceScale);
            float ty = this.showStartY + (this.tapHexRandomPoint[i].y * distanceScale);
            hex.setX(tx - (hex.getWidth() / 2.0f));
            hex.setY(ty - (hex.getHeight() / 2.0f));
        }
        float tapAniamationParticleValue = this.tapAnimationValue * 1.8f;
        float prticleAalpha = getCorrectAlpha(tapAniamationParticleValue < 0.5f ? 1.0f : 1.0f - ((tapAniamationParticleValue - 0.5f) * 2.0f));
        float particleScale = this.tapAnimationValue * 1.2f;
        setAlphaAndVisibility(this.particle, prticleAalpha);
        this.particle.setScaleX(this.defaultInSampleSize * particleScale);
        this.particle.setScaleY(this.defaultInSampleSize * particleScale);
        float tapAniamationRingValue = this.tapAnimationValue * 1.4f;
        float ringAlpha = getCorrectAlpha(tapAniamationRingValue < 0.5f ? 1.0f : 1.0f - ((tapAniamationRingValue - 0.5f) * 2.0f));
        setAlphaAndVisibility(this.ring, ringAlpha);
        float ringScale = 0.5f + this.tapAnimationValue;
        this.ring.setScaleX(this.defaultInSampleSize * ringScale);
        this.ring.setScaleY(this.defaultInSampleSize * ringScale);
        setAlphaAndVisibility(this.longLight, ringAlpha);
        float longScale = 1.5f + (this.tapAnimationValue * 2.0f);
        this.longLight.setScaleX(this.defaultInSampleSize * longScale);
        this.longLight.setScaleY(this.defaultInSampleSize * longScale);
        this.longLight.setRotation(this.randomRotation + (30.0f * this.tapAnimationValue));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animatedDragPos() {
        float scale = 1.0f + (this.distancePerMaxAlpha);
        this.lightFog.setScaleX(this.defaultInSampleSize * scale);
        this.lightFog.setScaleY(this.defaultInSampleSize * scale);
        float rotation = ((-this.objAnimationValue) * 30.0f) - (this.distancePerMaxAlpha * 160.0f);
        this.lightFog.setRotation(rotation);
        setCenterPos(this.lightFog, this.showStartX, this.showStartY, this.currentX, this.currentY, 1.0f);
        for (int i = 0; i < this.HEXAGON_TOTAL; i++) {
            ImageView hex = this.hexagon[i];
            setCenterPos(hex, this.showStartX, this.showStartY, this.currentX, this.currentY, this.hexagonDistance.get(i), this.hexagonScale[i], this.hexagonRotation[i]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animatedDragAlpha() {
        this.fogAlpha = getCorrectAlpha(this.fogAnimationValue * (1.0f - this.distancePerMaxAlpha));
        this.fogAlpha *= this.globalAlpha;
        this.objAlpha = getCorrectAlpha(this.distancePerMaxAlpha * 3.0f);
        this.vignettingAlpha = getCorrectAlpha(this.distancePerMaxAlpha * 1.3f);
        setAlphaAndVisibility(this.lightFog, this.fogAlpha);
        setAlphaAndVisibility(this.vignetting, this.vignettingAlpha);
        for (int i = 0; i < this.HEXAGON_TOTAL; i++) {
            ImageViewBlended hex = this.hexagon[i];
            setAlphaAndVisibility(hex, this.objAlpha);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animatedFadeOut() {
        setAlphaAndVisibility(this.lightFog, this.fogAlpha * this.fadeoutAnimationValue);
        setAlphaAndVisibility(this.vignetting, this.vignettingAlpha * this.fadeoutAnimationValue);
        for (int i = 0; i < this.HEXAGON_TOTAL; i++) {
            ImageViewBlended hex = this.hexagon[i];
            setAlphaAndVisibility(hex, this.objAlpha * this.fadeoutAnimationValue);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animatedUnlock() {
        float rainbowScale = 1.0f + (this.unlockAnimationValue * 1.3f);
        float rainbowAlpha = this.unlockAnimationValue < 0.5f ? this.unlockAnimationValue * 2.0f : 1.0f - ((this.unlockAnimationValue - 0.5f) * 2.0f);
        setAlphaAndVisibility(this.rainbow, rainbowAlpha);
        setCenterPos(this.rainbow, this.showStartX, this.showStartY, this.currentX, this.currentY, 0.4f);
        this.rainbow.setScaleX(this.defaultInSampleSize * rainbowScale);
        this.rainbow.setScaleY(this.defaultInSampleSize * rainbowScale);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animatedAffordance() {
        this.fogAlpha = getCorrectAlpha(this.affordanceAnimationValue);
        setAlphaAndVisibility(this.lightFog, this.fogAlpha);
    }

    private float getCorrectAlpha(float alpha) {
        if (alpha <= 0.0f) {
            return 0.0f;
        }
        if (alpha >= 1.0f) {
            return 1.0f;
        }
        return alpha;
    }

    private void calculateDistance(float x, float y) {
        float diffX = x - this.showStartX;
        float diffY = y - this.showStartY;
        this.distance = Math.sqrt(Math.pow(diffX, 2.0d) + Math.pow(diffY, 2.0d));
        this.distancePerMaxAlpha = ((float) this.distance) / this.MAX_ALPHA_DISTANCE;
    }

    private void setCenterPos(View view, float fromX, float fromY, float x, float y, float distanceScale) {
        float tx = ((x - fromX) * distanceScale) + fromX;
        float ty = ((y - fromY) * distanceScale) + fromY;
        float tx2 = tx - (view.getWidth() / 2.0f);
        float ty2 = ty - (view.getHeight() / 2.0f);
        if (view.getWidth() != 0) {
            view.setX(tx2);
            view.setY(ty2);
        }
    }

    private void setCenterPos(View view, float fromX, float fromY, float x, float y, float distanceScale, float scale, int rotation) {
        float scaleByDistance = 0.5f + ((((float) this.distance) / 720.0f) * 0.5f);
        float scaleByAnimationValue = 0.5f + (this.objAnimationValue * 0.5f);
        float tscale = scale * scaleByDistance * scaleByAnimationValue;
        view.setScaleX(this.defaultInSampleSize * tscale);
        view.setScaleY(this.defaultInSampleSize * tscale);
        float distanceScaleByAnimation = 0.5f + (this.objAnimationValue * 0.5f);
        float tx = ((x - fromX) * distanceScale * distanceScaleByAnimation) + fromX;
        float ty = ((y - fromY) * distanceScale * distanceScaleByAnimation) + fromY;
        if (rotation != 0) {
            float dist = scale * 300.0f;
            float rotationByDistance = (((float) this.distance) / 1000.0f) * scale * scale;
            float rotationByAnimation = this.objAnimationValue;
            double rad = ((rotation * 3.141592653589793d) / 180.0d) + rotationByDistance + rotationByAnimation;
            tx = ((float) ((dist * Math.cos(rad)) + (dist * Math.sin(rad)))) + x;
            ty = ((float) ((dist * (-Math.sin(rad))) + (dist * Math.cos(rad)))) + y;
        }
        view.setX(tx - (view.getWidth() / 2.0f));
        view.setY(ty - (view.getHeight() / 2.0f));
    }

    private void setAlphaAndVisibility(View view, float alpha) {
        if (alpha == 0.0f) {
            if (view.getVisibility() != View.GONE) {
                view.setVisibility(view.getWidth() == 0 ? View.INVISIBLE : View.GONE);
                return;
            }
            return;
        }
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
        view.setAlpha(alpha);
    }

    private void cleanUpAllViews() {
        setAlphaAndVisibility(this.lightFog, 0.0f);
        setAlphaAndVisibility(this.ring, 0.0f);
        setAlphaAndVisibility(this.longLight, 0.0f);
        setAlphaAndVisibility(this.rainbow, 0.0f);
        setAlphaAndVisibility(this.particle, 0.0f);
        setAlphaAndVisibility(this.vignetting, 0.0f);
        for (int i = 0; i < this.HEXAGON_TOTAL; i++) {
            ImageViewBlended hex = this.hexagon[i];
            setAlphaAndVisibility(hex, 0.0f);
        }
        for (int i2 = 0; i2 < this.HEXAGON_TAP_TOTAL; i2++) {
            ImageViewBlended hex2 = this.tapHexagon[i2];
            setAlphaAndVisibility(hex2, 0.0f);
        }
        cancelAnimator(this.tapAnimator);
        cancelAnimator(this.objAnimator);
        cancelAnimator(this.fadeOutAnimator);
        cancelAnimator(this.fogOnAnimator);
        cancelAnimator(this.unlockAnimator);
        cancelAnimator(this.hoverAnimator);
        cancelAnimator(this.affordanceOnAnimator);
        cancelAnimator(this.affordanceOffAnimator);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelAnimator(Animator animator) {
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
    }

    private void showUnlockAffordance(long startDelay, Rect rect) {
        Log.d(TAG, "showUnlockAffordance : " + rect.left + ", " + rect.right + ", " + rect.top + ", " + rect.bottom + ", startDelay : " + startDelay);
        stopUnlockAffordance();
        this.affordancePoint.x = rect.left + ((rect.right - rect.left) / 2);
        this.affordancePoint.y = rect.top + ((rect.bottom - rect.top) / 2);
        // from class: com.samsung.android.visualeffect.lock.lensflare.LensFlareEffect.11
// java.lang.Runnable
        this.affordanceRunnable = () -> {
            LensFlareEffect.this.playUnlockAffordance();
            LensFlareEffect.this.isPlayAffordance = false;
            LensFlareEffect.this.affordanceRunnable = null;
        };
        postDelayed(this.affordanceRunnable, startDelay);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playUnlockAffordance() {
        if (!this.isBeforeInit) {
            Log.d(TAG, "playUnlockAffordance");
            this.showStartX = this.affordancePoint.x;
            this.showStartY = this.affordancePoint.y;
            setHexagonRandomTarget(false);
            setCenterPos(this.ring, this.showStartX, this.showStartY, this.showStartX, this.showStartY, 1.0f);
            setCenterPos(this.longLight, this.showStartX, this.showStartY, this.showStartX, this.showStartY, 1.0f);
            setCenterPos(this.particle, this.showStartX, this.showStartY, this.showStartX, this.showStartY, 1.0f);
            setCenterPos(this.lightFog, this.showStartX, this.showStartY, this.showStartX, this.showStartY, 1.0f);
            cancelAnimator(this.tapAnimator);
            this.tapAnimator.start();
            this.affordanceOnAnimator.start();
        }
    }

    private void stopUnlockAffordance() {
        if (this.affordanceRunnable != null) {
            Log.d(TAG, "remove delayed UnlockAffordance callback");
            removeCallbacks(this.affordanceRunnable);
            this.affordanceRunnable = null;
        }
    }

    private void playLockSound() {
        Log.d(TAG, "playSound");
        playSound(sound_lock); // todo: was sound_unlock
    }

    private void show() {
        Log.d(TAG, "show");
        this.isPlayAffordance = false;
        if (this.isBeforeInit) {
            Log.d(TAG, "isBeforeInit is true");
            if (this.mFirstCreatedRunnable == null) {
                // from class: com.samsung.android.visualeffect.lock.lensflare.LensFlareEffect.12
// java.lang.Runnable
                this.mFirstCreatedRunnable = () -> {
                    Log.d(TAG, "mFirstCreatedRunnable,  isBeforeInit is True and called lensFlareinit()");
                    LensFlareEffect.this.lensFlareinit();
                };
                Log.d(TAG, "this.postDelayed, createdDelaytime = " + this.createdDelaytime);
                postDelayed(this.mFirstCreatedRunnable, this.createdDelaytime);
            }
        }
        setSound();
    }

    private void cleanUp() {
        Log.d(TAG, "cleanUp");
        if (!this.isBeforeInit) {
            cleanUpAllViews();
            this.isPlayAffordance = false;
            stopReleaseSound();
            // from class: com.samsung.android.visualeffect.lock.lensflare.LensFlareEffect.13
// java.lang.Runnable
            this.releaseSoundRunnable = () -> {
                if (LensFlareEffect.this.soundpool != null) {
                    Log.d(TAG, "LensFlare sound : release");
                    LensFlareEffect.this.soundpool.release();
                    LensFlareEffect.this.soundpool = null;
                }
                LensFlareEffect.this.releaseSoundRunnable = null;
            };
            postDelayed(this.releaseSoundRunnable, UNLOCK_SOUND_PLAY_TIME);
        }
    }

    private void stopReleaseSound() {
        if (this.releaseSoundRunnable != null) {
            removeCallbacks(this.releaseSoundRunnable);
            this.releaseSoundRunnable = null;
        }
    }

    private void reset() {
        Log.d(TAG, "reset");
        if (!this.isBeforeInit) {
            cleanUpAllViews();
            this.isPlayAffordance = false;
            stopUnlockAffordance();
        }
    }

    private void screenTurnedOn() {
        Log.d(TAG, "screenTurnedOn");
        this.isPlayAffordance = true;
    }

    private long getUnlockDelay() {
        return 300L;
    }

    public boolean handleHoverEvent(MotionEvent event) {
        if (this.isBeforeInit) {
            Log.d(TAG, "Return handleTouchEvent!!! Becuase isBeforeInit is true");
        } else {
            switch (event.getActionMasked()) {
                case MotionEvent.AXIS_SIZE:
                case MotionEvent.ACTION_HOVER_EXIT:
                    hoverExit();
                    break;
                case MotionEvent.ACTION_HOVER_MOVE:
                    hoverMove(event.getX(), event.getY());
                    break;
                case MotionEvent.ACTION_HOVER_ENTER:
                    if ((event.getSource() & 16386) == 16386 || (event.getSource() & 8194) == 8194) {
                        this.Y_OFFSET = this.PEN_HOVER_Y_OFFSET;
                    } else {
                        this.Y_OFFSET = this.FINGER_HOVER_Y_OFFSET;
                    }
                    Log.d(TAG, "InputDevice.SOURCE_STYLUS = 16386, Y_OFFSET = " + this.Y_OFFSET);
                    hoverEnter(event.getX(), event.getY());
                    break;
            }
        }
        return false;
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void init(EffectDataObj data) {
        setImageResourceId(data.lensFlareData.hexagon_blue, data.lensFlareData.hexagon_green, data.lensFlareData.hexagon_orange, data.lensFlareData.hoverlight, data.lensFlareData.light, data.lensFlareData.long_light, data.lensFlareData.particle, data.lensFlareData.rainbow, data.lensFlareData.ring, data.lensFlareData.vignetting);
        setSoundResourceId(data.lensFlareData.tapSoundPath, data.lensFlareData.unlockSoundPath, data.lensFlareData.lockSoundPath);
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void reInit(EffectDataObj data) {
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void handleCustomEvent(int cmd, HashMap<?, ?> params) {
        if (cmd == 2) {
            unlock();
        } else if (cmd == 1) {
            showUnlockAffordance((Long) params.get("startDelay"), (Rect) params.get("rect"));
        } else if (cmd == 99) {
            if (params.containsKey("manualInit")) {
                lensFlareinit();
            } else if (params.containsKey("show")) {
                show();
            } else if (params.containsKey("reset")) {
                reset();
            } else if (params.containsKey("screenTurnedOn")) {
                screenTurnedOn();
            } else if (params.containsKey("showUnlockAffordance")) {
                showUnlockAffordance((Long) params.get("startDelay"), (Rect) params.get("rect"));
            } else if (params.containsKey("playLockSound")) {
                playLockSound();
            }
        }
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void setListener(IEffectListener listener) {
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void removeListener() {
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void handleTouchEvent(MotionEvent event, View view) {
        if (this.isBeforeInit) {
            Log.d(TAG, "Return handleTouchEvent!!! Becuase isBeforeInit is true");
        } else if (event.getActionMasked() == 0) {
            if ((event.getSource() & 16386) == 16386 || (event.getSource() & 8194) == 8194) {
                this.Y_OFFSET = this.PEN_HOVER_Y_OFFSET;
            } else {
                this.Y_OFFSET = this.FINGER_HOVER_Y_OFFSET;
            }
            Log.d(TAG, "InputDevice.SOURCE_STYLUS = 16386, Y_OFFSET = " + this.Y_OFFSET);
            showLight(event.getX(), event.getY());
        } else if (event.getActionMasked() == 2 && event.getActionIndex() == 0) {
            move(event.getX(), event.getY());
        } else if (event.getActionMasked() == 1 || event.getActionMasked() == 3) {
            hide();
        }
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void clearScreen() {
        cleanUp();
    }
}