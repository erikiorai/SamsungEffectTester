package com.android.keyguard.sec;

import static com.aj.effect.SoundManager.LOCK;
import static com.aj.effect.SoundManager.TAP;
import static com.aj.effect.SoundManager.UNLOCK;
import static com.aj.effect.SoundManager.attr;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.SoundPool;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.aj.effect.R;
import com.aj.effect.SoundManager;

import java.io.File;

/* loaded from: classes.dex */
public class KeyguardEffectViewMassTension extends FrameLayout implements KeyguardEffectViewBase {
    private static final int sound_tap_path = R.raw.tap_tension;
    private final int CIRCLE_MAX_ALPHA;
    private final float CIRCLE_MAX_ALPHA_FACTOR;
    private final int CIRCLE_MIN_ALPHA;
    protected String TAG;
    private final int TENSION_BETWEEN_FACTOR;
    private final int TENSION_CIRCLE_PLACE_ADJUST;
    private final int TENSION_LINE_DELETE;
    private final float TENSION_LINE_MIN;
    private final float TENSION_RELEASE_FACTOR;
    protected final double UNLOCK_DRAG_THRESHOLD;
    protected final double UNLOCK_RELEASE_THRESHOLD;
    private final long UNLOCK_SOUND_PLAY_TIME;
    protected final double UNLOCK_TEMP_THRESHOLD;
    private int betweenLineX;
    private int betweenLineY;
    private double degree;
    private long diffPressTime;
    private boolean isIgnoreTouch;
    private boolean isSystemSoundChecked;
    private float lineSize;
    private ImageView mCircleCenterDot;
    private ImageView mCircleCenterDotAfter;
    private Animation mCircleCenterDotAnim;
    Point mCircleCenterDotFromPoint;
    private Animation mCircleCenterDotReleaseAnim;
    protected RelativeLayout mCircleCenterDotRoot;
    Point mCircleCenterDotToPoint;
    private ImageView mCircleFinger;
    private ImageView mCircleFingerAfter;
    private Animation mCircleFingerAnim;
    private Animation mCircleFingerReleaseAnim;
    protected RelativeLayout mCircleFingerRoot;
    private ImageView mCircleLine;
    private ImageView mCircleLineAfter;
    protected RelativeLayout mCircleLineRoot;
    private ImageView mCircleOuter;
    private ImageView mCircleOuterAfter;
    private Animation mCircleOuterAnim;
    protected RelativeLayout mCircleOuterRoot;
    private final Context mContext;
    private double mDistanceRatio;
    private ScaleAnimation mLineAnim;
    private final float mLockSoundVolume;
    private SoundPool mSoundPool;
    protected float mX;
    protected float mY;
    private double outerTensionFactorX;
    private double outerTensionFactorY;
    private long prevPressTime;
    private double radian;
    private Runnable releaseSoundRunnable;
    private int[] sounds = new int[4];

    public KeyguardEffectViewMassTension(Context context) {
        super(context);
        this.TAG = "TensionLockScreen";
        this.CIRCLE_MAX_ALPHA = 255;
        this.CIRCLE_MIN_ALPHA = 50;
        this.UNLOCK_TEMP_THRESHOLD = 1.2000000476837158d;
        this.UNLOCK_RELEASE_THRESHOLD = 1.399999976158142d;
        this.UNLOCK_DRAG_THRESHOLD = 2.0999999046325684d;
        this.TENSION_RELEASE_FACTOR = 0.8f;
        this.CIRCLE_MAX_ALPHA_FACTOR = 0.8f;
        this.TENSION_BETWEEN_FACTOR = 40;
        this.TENSION_LINE_MIN = 0.0f;
        this.TENSION_CIRCLE_PLACE_ADJUST = 5;
        this.isIgnoreTouch = false;
        this.UNLOCK_SOUND_PLAY_TIME = 2000L;
        this.mCircleCenterDotToPoint = new Point();
        this.mCircleCenterDotFromPoint = new Point();
        this.mSoundPool = null;
        this.diffPressTime = 0L;
        this.prevPressTime = 0L;
        this.isSystemSoundChecked = true;
        this.mContext = context;
        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        inflater.inflate(R.layout.keyguard_mass_tension_effect_view, (ViewGroup) this, true);
        this.TENSION_LINE_DELETE = (int) getResources().getDimension(R.dimen.tension_line_delete);
        setLayout();
        setAnimation();
        setLineAnim(0.0f, 0.0f);
        int lockSoundDefaultAttenuation = -6;
        this.mLockSoundVolume = (float) Math.pow(10.0d, lockSoundDefaultAttenuation / 20.0f);
    }

    private void setLayout() {
        this.mCircleOuter = (ImageView) findViewById(R.id.keyguard_tension_effect_tension_in_press);
        this.mCircleFinger = (ImageView) findViewById(R.id.keyguard_tension_effect_tension_finger);
        this.mCircleCenterDot = (ImageView) findViewById(R.id.keyguard_tension_effect_tension_center_dot);
        this.mCircleLine = (ImageView) findViewById(R.id.keyguard_tension_effect_tension_line);
        this.mCircleOuterRoot = (RelativeLayout) findViewById(R.id.keyguard_tension_effect_tension_in_press_root);
        this.mCircleOuterAfter = (ImageView) findViewById(R.id.keyguard_tension_effect_tension_in_press_after);
        this.mCircleFingerRoot = (RelativeLayout) findViewById(R.id.keyguard_tension_effect_tension_finger_root);
        this.mCircleFingerAfter = (ImageView) findViewById(R.id.keyguard_tension_effect_tension_finger_after);
        this.mCircleCenterDotRoot = (RelativeLayout) findViewById(R.id.keyguard_tension_effect_tension_center_dot_root);
        this.mCircleCenterDotAfter = (ImageView) findViewById(R.id.keyguard_tension_effect_tension_center_dot_after);
        this.mCircleLineRoot = (RelativeLayout) findViewById(R.id.keyguard_tension_effect_tension_line_root);
        this.mCircleLineAfter = (ImageView) findViewById(R.id.keyguard_tension_effect_tension_line_after);
    }

    private void setAnimation() {
        this.mCircleFingerAnim = AnimationUtils.loadAnimation(this.mContext, R.anim.keyguard_tention_animate_fadeout);
        this.mCircleCenterDotAnim = AnimationUtils.loadAnimation(this.mContext, R.anim.keyguard_tention_animate_fadeout);
        this.mCircleOuterAnim = AnimationUtils.loadAnimation(this.mContext, R.anim.keyguard_tention_animate_alpha);
        this.mCircleFingerReleaseAnim = AnimationUtils.loadAnimation(this.mContext, R.anim.keyguard_tention_animate_finger);
        this.mCircleCenterDotReleaseAnim = AnimationUtils.loadAnimation(this.mContext, R.anim.keyguard_tention_animate_centerdot_release);
    }

    public AnimationSet createBouncerAnimation() {
        int fromX = this.mCircleCenterDotFromPoint.x;
        int fromY = this.mCircleCenterDotFromPoint.y;
        int toX = this.mCircleCenterDotToPoint.x;
        int toY = this.mCircleCenterDotToPoint.y;
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translate = new TranslateAnimation(0.0f, toX - fromX, 0.0f, toY - fromY);
        translate.setDuration(250L);
        translate.setFillAfter(true);
        translate.setInterpolator(new DecelerateInterpolator());
        animationSet.addAnimation(translate);
        animationSet.setAnimationListener(new Animation.AnimationListener() { // from class: com.android.keyguard.sec.effect.KeyguardEffectViewMassTension.1
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                KeyguardEffectViewMassTension.this.mCircleCenterDot.setVisibility(View.INVISIBLE);
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }
        });
        return animationSet;
    }

    private void setLineAnim(float firstvalue, float lastvalue) {
        this.mLineAnim = new ScaleAnimation(firstvalue, lastvalue, 1.0f, 1.0f);
        this.mLineAnim.setStartOffset(50L);
        this.mLineAnim.setDuration(250L);
        this.mLineAnim.setFillAfter(true);
        this.mLineAnim.setInterpolator(new DecelerateInterpolator());
        this.mLineAnim.setAnimationListener(new Animation.AnimationListener() { // from class: com.android.keyguard.sec.effect.KeyguardEffectViewMassTension.2
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
                int fromX = KeyguardEffectViewMassTension.this.mCircleCenterDotFromPoint.x;
                int fromY = KeyguardEffectViewMassTension.this.mCircleCenterDotFromPoint.y;
                int i = KeyguardEffectViewMassTension.this.mCircleCenterDotToPoint.x;
                int i2 = KeyguardEffectViewMassTension.this.mCircleCenterDotToPoint.y;
                KeyguardEffectViewMassTension.this.mCircleCenterDot.setX(fromX);
                KeyguardEffectViewMassTension.this.mCircleCenterDot.setY(fromY);
                KeyguardEffectViewMassTension.this.mCircleCenterDot.setVisibility(View.VISIBLE);
                AnimationSet animationSet = KeyguardEffectViewMassTension.this.createBouncerAnimation();
                animationSet.setDuration(250L);
                KeyguardEffectViewMassTension.this.mCircleCenterDot.startAnimation(animationSet);
            }
        });
    }

    private void setOuterCircle(View v, double value) {
        int alpha = (int) (50.0d + (255.0d * value * 0.800000011920929d));
        if (alpha >= 255) {
            alpha = 255;
        }
        this.mCircleOuter.setImageAlpha(alpha);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public boolean handleTouchEvent(View view, MotionEvent event) {
        if (this.isIgnoreTouch) {
            if (event.getAction() == 1) {
                this.isIgnoreTouch = false;
            }
            return false;
        }
        switch (event.getAction()) {
            case 0:
                clearAllViews();
                this.mCircleOuter.setImageAlpha(50);
                this.mCircleFinger.setImageAlpha(255);
                this.mCircleCenterDot.setImageAlpha(255);
                this.mCircleLine.setImageAlpha(255);
                this.mX = event.getRawX();
                this.mY = event.getRawY();
                this.mCircleOuter.setVisibility(View.VISIBLE);
                this.mCircleOuter.setX(((int) event.getRawX()) - (mCircleOuter.getMeasuredWidth() / 2.0f));
                this.mCircleOuter.setY(((int) event.getRawY()) - (mCircleOuter.getMeasuredHeight() / 2.0f));
                this.mCircleFinger.setVisibility(View.VISIBLE);
                this.mCircleFinger.setX(((int) event.getRawX()) - (mCircleFinger.getMeasuredWidth() / 2.0f));
                this.mCircleFinger.setY(((int) event.getRawY()) - (mCircleFinger.getMeasuredHeight() / 2.0f));
                this.mCircleCenterDot.setVisibility(View.VISIBLE);
                this.mCircleCenterDot.setX(((int) event.getRawX()) - ((float) this.mCircleCenterDot.getMeasuredWidth() / 2));
                this.mCircleCenterDot.setY(((int) event.getRawY()) - ((float) this.mCircleCenterDot.getMeasuredHeight() / 2));
                this.mCircleCenterDotToPoint.x = ((int) event.getRawX()) - (this.mCircleCenterDot.getMeasuredWidth() / 2);
                this.mCircleCenterDotToPoint.y = ((int) event.getRawY()) - (this.mCircleCenterDot.getMeasuredHeight() / 2);
                this.mCircleLine.setVisibility(View.VISIBLE);
                this.mCircleLine.setScaleX(0.0f);
                this.prevPressTime = SystemClock.uptimeMillis();
                this.diffPressTime = 0L;
                playSound(TAP);
                break;
            case 1:
                this.mCircleOuter.setVisibility(View.INVISIBLE);
                this.mCircleFinger.setVisibility(View.INVISIBLE);
                this.mCircleLine.setVisibility(View.INVISIBLE);
                this.mCircleOuterRoot.setX(((int) this.mX) - ((float) this.mCircleOuterAfter.getMeasuredWidth() / 2));
                this.mCircleOuterRoot.setY(((int) this.mY) - ((float) this.mCircleOuterAfter.getMeasuredHeight() / 2));
                this.mCircleOuterAfter.startAnimation(this.mCircleOuterAnim);
                this.betweenLineX = (int) (this.mX + ((event.getRawX() - this.mX) / 40.0f));
                this.betweenLineY = (int) (this.mY + ((event.getRawY() - this.mY) / 40.0f));
                this.diffPressTime = SystemClock.uptimeMillis() - this.prevPressTime;
                if (this.mDistanceRatio < 1.399999976158142d) {
                    this.mCircleFingerRoot.setX(((int) event.getRawX()) - ((float) this.mCircleFingerAfter.getMeasuredWidth() / 2));
                    this.mCircleFingerRoot.setY(((int) event.getRawY()) - ((float) this.mCircleFingerAfter.getMeasuredHeight() / 2));
                    this.mCircleFingerAfter.startAnimation(this.mCircleFingerAnim);
                    this.mCircleCenterDotRoot.setX(this.betweenLineX - ((float) this.mCircleCenterDotAfter.getMeasuredWidth() / 2));
                    this.mCircleCenterDotRoot.setY(this.betweenLineY - ((float) this.mCircleCenterDotAfter.getMeasuredHeight() / 2));
                    this.mCircleCenterDotAfter.startAnimation(this.mCircleCenterDotAnim);
                    this.mCircleCenterDot.setVisibility(View.INVISIBLE);
                    if (this.diffPressTime > 600) {
                        playSound(TAP);
                        break;
                    }
                } else if (this.mDistanceRatio >= 1.399999976158142d && this.mDistanceRatio <= 2.0999999046325684d) {
                    this.mCircleFingerRoot.setX(((int) event.getRawX()) - ((float) this.mCircleFingerAfter.getMeasuredWidth() / 2));
                    this.mCircleFingerRoot.setY(((int) event.getRawY()) - ((float) this.mCircleFingerAfter.getMeasuredHeight() / 2));
                    this.mCircleFingerAfter.startAnimation(this.mCircleFingerReleaseAnim);
                    this.mCircleLineRoot.setX(this.betweenLineX);
                    this.mCircleLineRoot.setY(this.betweenLineY - ((float) this.mCircleLine.getMeasuredHeight() / 2));
                    this.mCircleLineRoot.setPivotX(0.0f);
                    this.mCircleLineRoot.setPivotY((float) this.mCircleLineAfter.getMeasuredHeight() / 2);
                    this.mCircleLineRoot.setRotation((float) this.degree);
                    setLineAnim(this.lineSize, 0.0f);
                    this.mCircleLineAfter.startAnimation(this.mLineAnim);
                    this.mCircleCenterDotFromPoint.x = this.betweenLineX - (this.mCircleCenterDot.getMeasuredWidth() / 2);
                    this.mCircleCenterDotFromPoint.y = this.betweenLineY - (this.mCircleCenterDot.getMeasuredHeight() / 2);
                    break;
                }
                break;
            case 2:
                int diffX = (int) (event.getRawX() - this.mX);
                int diffY = (int) (event.getRawY() - this.mY);
                double distance_square = Math.pow(diffX, 2.0d) + Math.pow(diffY, 2.0d);
                double distance = Math.sqrt(distance_square);
                double threshold = (double) this.mCircleOuter.getWidth() / 2;
                this.mDistanceRatio = distance / threshold;
                this.betweenLineX = (int) (this.mX + ((event.getRawX() - this.mX) / 40.0f));
                this.betweenLineY = (int) (this.mY + ((event.getRawY() - this.mY) / 40.0f));
                setOuterCircle(view, this.mDistanceRatio);
                double absX = event.getRawX() - this.mX;
                double absY = (-1.0f) * (event.getRawY() - this.mY);
                this.radian = Math.atan2(absY, absX);
                this.degree = ((-this.radian) / 3.141592653589793d) * 180.0d;
                this.outerTensionFactorX = this.mX + (((((double) this.mCircleFinger.getMeasuredWidth() / 2) + ((double) this.mCircleOuter.getMeasuredWidth() / 2)) - 5) * Math.cos((this.degree / 180.0d) * 3.141592653589793d));
                this.outerTensionFactorY = this.mY - (((((double) this.mCircleFinger.getMeasuredHeight() / 2) + ((double) this.mCircleOuter.getMeasuredHeight() / 2)) - 5) * Math.sin(((-this.degree) / 180.0d) * 3.141592653589793d));
                if (this.mDistanceRatio < 1.2000000476837158d) {
                    this.mCircleFinger.setX(((int) event.getRawX()) - ((float) this.mCircleFinger.getMeasuredWidth() / 2));
                    this.mCircleFinger.setY(((int) event.getRawY()) - ((float) this.mCircleFinger.getMeasuredHeight() / 2));
                    this.mCircleCenterDot.setX(this.betweenLineX - ((float) this.mCircleCenterDot.getMeasuredWidth() / 2));
                    this.mCircleCenterDot.setY(this.betweenLineY - ((float) this.mCircleCenterDot.getMeasuredHeight() / 2));
                    this.mCircleLine.setX(this.betweenLineX);
                    this.mCircleLine.setY(this.betweenLineY - ((float) this.mCircleLine.getMeasuredHeight() / 2));
                    this.mCircleLine.setPivotX(0.0f);
                    this.mCircleLine.setPivotY((float) this.mCircleLine.getMeasuredHeight() / 2);
                    float lineSizebaseX = event.getRawX() - (this.mX + ((event.getRawX() - this.mX) / 40.0f));
                    float lineSizebaseY = event.getRawY() - (this.mY + ((event.getRawY() - this.mY) / 40.0f));
                    this.lineSize = (float) ((Math.sqrt((lineSizebaseX * lineSizebaseX) + (lineSizebaseY * lineSizebaseY)) - (this.mCircleCenterDot.getMeasuredWidth() / 2)) - this.TENSION_LINE_DELETE);
                    this.lineSize = Math.max(this.lineSize, 0.0f);
                    this.mCircleLine.setScaleX(this.lineSize);
                    this.mCircleLine.setRotation((float) this.degree);
                    break;
                } else if (this.mDistanceRatio >= 1.2000000476837158d && this.mDistanceRatio <= 2.0999999046325684d) {
                    this.mCircleFinger.setX((int) (this.outerTensionFactorX - (this.mCircleFinger.getMeasuredWidth() / 2)));
                    this.mCircleFinger.setY((int) (this.outerTensionFactorY - (this.mCircleFinger.getMeasuredHeight() / 2)));
                    this.mCircleCenterDot.setX(this.betweenLineX - ((float) this.mCircleCenterDot.getMeasuredWidth() / 2));
                    this.mCircleCenterDot.setY(this.betweenLineY - ((float) this.mCircleCenterDot.getMeasuredHeight() / 2));
                    this.mCircleLine.setX(this.betweenLineX);
                    this.mCircleLine.setY(this.betweenLineY - ((float) this.mCircleLine.getMeasuredHeight() / 2));
                    this.mCircleLine.setPivotX(0.0f);
                    this.mCircleLine.setPivotY((float) this.mCircleLine.getMeasuredHeight() / 2);
                    float lineSizebaseX2 = (float) (this.outerTensionFactorX - this.betweenLineX);
                    float lineSizebaseY2 = (float) (this.outerTensionFactorY - this.betweenLineY);
                    this.lineSize = (float) ((Math.sqrt((lineSizebaseX2 * lineSizebaseX2) + (lineSizebaseY2 * lineSizebaseY2)) - (this.mCircleCenterDot.getMeasuredWidth() / 2)) - this.TENSION_LINE_DELETE);
                    this.lineSize = Math.max(this.lineSize, 0.0f);
                    this.mCircleLine.setScaleX(this.lineSize);
                    this.mCircleLine.setRotation((float) this.degree);
                    break;
                } else {
                    this.mCircleOuter.setVisibility(View.INVISIBLE);
                    this.mCircleFinger.setVisibility(View.INVISIBLE);
                    this.mCircleLine.setVisibility(View.INVISIBLE);
                    this.mCircleFingerRoot.setX((int) (event.getRawX() - (this.mCircleFingerAfter.getMeasuredWidth() / 2)));
                    this.mCircleFingerRoot.setY((int) (event.getRawY() - (this.mCircleFingerAfter.getMeasuredHeight() / 2)));
                    this.mCircleFingerAfter.startAnimation(this.mCircleFingerReleaseAnim);
                    this.mCircleOuterRoot.setX(((int) this.mX) - ((float) this.mCircleOuterAfter.getMeasuredWidth() / 2));
                    this.mCircleOuterRoot.setY((this.mY) - (this.mCircleOuterAfter.getMeasuredHeight() / 2.0f));
                    this.mCircleOuterAfter.startAnimation(this.mCircleOuterAnim);
                    this.mCircleLineRoot.setX(this.betweenLineX);
                    this.mCircleLineRoot.setY(this.betweenLineY - (this.mCircleLine.getMeasuredHeight() / 2.0f));
                    this.mCircleLineRoot.setPivotX(0.0f);
                    this.mCircleLineRoot.setPivotY(this.mCircleLineAfter.getMeasuredHeight() / 2.0f);
                    this.mCircleLineRoot.setRotation((float) this.degree);
                    setLineAnim(this.lineSize, 0.0f);
                    this.mCircleLineAfter.startAnimation(this.mLineAnim);
                    this.mCircleCenterDotFromPoint.x = this.betweenLineX - (this.mCircleCenterDot.getMeasuredWidth() / 2);
                    this.mCircleCenterDotFromPoint.y = this.betweenLineY - (this.mCircleCenterDot.getMeasuredHeight() / 2);
                    break;
                }
        }
        return true;
    }

    private void clearAllViews() {
        this.mCircleOuter.setImageAlpha(0);
        this.mCircleFinger.setImageAlpha(0);
        this.mCircleCenterDot.setImageAlpha(0);
        this.mCircleLine.setImageAlpha(0);
        this.mCircleLineAfter.clearAnimation();
        this.mCircleOuter.setVisibility(View.INVISIBLE);
        this.mCircleCenterDot.setVisibility(View.INVISIBLE);
        this.mCircleFinger.setVisibility(View.INVISIBLE);
        this.mCircleLine.setVisibility(View.INVISIBLE);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void handleUnlock(View view, MotionEvent event) {
        playSound(UNLOCK);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void show() {
        checkSound();
        setSound();
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void cleanUp() {
        clearAllViews();
        if (this.mSoundPool != null) {
            this.mSoundPool.release();
            this.mSoundPool = null;
        }
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void reset() {
        Log.d(this.TAG, "reset");
        clearAllViews();
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void screenTurnedOn() {
        Log.d(this.TAG, "screenTurnedOn");
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public long getUnlockDelay() {
        return 500L;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void playLockSound() {
        playSound(LOCK);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(this.TAG, "onConfigurationChanged");
    }

    public boolean handleHoverEvent(MotionEvent event) {
        return false;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void showUnlockAffordance(long startDelay, Rect rect) {
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void update() {
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void screenTurnedOff() {
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public boolean handleTouchEventForPatternLock(View view, MotionEvent event) {
        return false;
    }

    private void checkSound() {
        ContentResolver cr = this.mContext.getContentResolver();
        int result = 0;
        try {
            result = Settings.System.getInt(cr, "lockscreen_sounds_enabled");
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        this.isSystemSoundChecked = result == 1;
    }

    private void setSound() {
        if (this.mSoundPool == null) {
            Log.d(this.TAG, "show mSoundPool is null");
            this.mSoundPool = new SoundPool.Builder().setMaxStreams(10).setAudioAttributes(attr).build();
            this.sounds[TAP] = this.mSoundPool.load(mContext, sound_tap_path, 1);
            sounds[LOCK] = mSoundPool.load(mContext, R.raw.lock_tension, 1);
            sounds[UNLOCK] = mSoundPool.load(mContext, R.raw.unlock_tension, 1);
        }
    }

    private void playSound(int sound) {
        if (this.isSystemSoundChecked && this.mSoundPool != null) {
            this.mSoundPool.play(this.sounds[sound], this.mLockSoundVolume, this.mLockSoundVolume, 1, 0, 1.0f);
        }
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void setHidden(boolean isHidden) {
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