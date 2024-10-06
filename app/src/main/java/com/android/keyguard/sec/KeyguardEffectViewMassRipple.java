package com.android.keyguard.sec;

import static com.aj.effect.SoundManager.attr;
import static com.android.keyguard.sec.KeyguardEffectViewController.mRes;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.aj.effect.R;

/* loaded from: classes.dex */
public class KeyguardEffectViewMassRipple extends FrameLayout implements KeyguardEffectViewBase {
    protected static final String TAG = "KeyguardEffectViewMassRipple";
    protected final float ANIMATION_DURATION;
    final float CIRCLE_AFFORDANCE_ROUND_SIZE;
    final float CIRCLE_ROUND_SIZE;
    private final int COUNT_ANIMATION;
    private boolean DEBUG;
    final int MAX_RIPPLE_COUNT;
    final int MSG_AFFORDANCE_TOUCH;
    final int MSG_FIRST_TOUCH;
    final int MSG_TIME_TICK;
    final int SOUND_ID_DOWN;
    final int SOUND_ID_UP;
    protected final double UNLOCK_DRAG_THRESHOLD;
    protected final double UNLOCK_RELEASE_THRESHOLD;
    private long diffPressTime;
    int drawRippleCount;
    int firstTouch_X;
    int firstTouch_Y;
    private int indexAni;
    private boolean isStartUnlock;
    private boolean isSystemSoundChecked;
    protected FrameLayout mCircleMain;
    private Context mContext;
    private double mDistanceRatio;
    Handler mHandler;
    int mMovingRippleCount;
    private int mRDownId;
    private int mRUpId;
    private SoundPool mSoundPool;
    private ImageView[] massRipple;
    float prevMovingDistance;
    private long prevPressTime;
    Animation[] scale;
    private int soundNum;
    private int soundTime;
    private int[] sounds;
    float touchedEventX;
    float touchedEventY;
    float[] typeStorke;

    public KeyguardEffectViewMassRipple(Context context) {
        super(context);
        this.DEBUG = false;
        this.indexAni = 0;
        this.COUNT_ANIMATION = 3;
        this.scale = new Animation[6];
        this.UNLOCK_RELEASE_THRESHOLD = 0.800000011920929d;
        this.UNLOCK_DRAG_THRESHOLD = 1.2999999523162842d;
        this.ANIMATION_DURATION = 1300.0f;
        this.firstTouch_X = -1;
        this.firstTouch_Y = -1;
        this.prevMovingDistance = 0.0f;
        this.drawRippleCount = 0;
        this.typeStorke = new float[]{49.0f, 26.6f, 37.0f, 30.0f};
        this.CIRCLE_ROUND_SIZE = 290.0f;
        this.CIRCLE_AFFORDANCE_ROUND_SIZE = 224.0f;
        this.MSG_FIRST_TOUCH = 0;
        this.MSG_AFFORDANCE_TOUCH = 1;
        this.MSG_TIME_TICK = 1;
        this.MAX_RIPPLE_COUNT = 2;
        this.mMovingRippleCount = 0;
        this.isStartUnlock = false;
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.keyguard.sec.effect.KeyguardEffectViewMassRipple.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                if (msg.what == MSG_FIRST_TOUCH) {
                    KeyguardEffectViewMassRipple.this.rippeDown(msg.arg1, msg.arg2, 1, false);
                } else if (msg.what == MSG_AFFORDANCE_TOUCH) {
                    KeyguardEffectViewMassRipple.this.rippeDown(msg.arg1, msg.arg2, 1, true);
                }
            }
        };
        this.mSoundPool = null;
        this.sounds = null;
        this.diffPressTime = 0L;
        this.prevPressTime = 0L;
        this.soundNum = 5;
        this.soundTime = 1;
        this.SOUND_ID_DOWN = 0;
        this.SOUND_ID_UP = 1;
        this.mRDownId = R.raw.simple_ripple_down;
        this.mRUpId = R.raw.simple_ripple_up;
        this.isSystemSoundChecked = true;
        this.mContext = context;
        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        inflater.inflate(R.layout.keyguard_mass_ripple_effect_view, (ViewGroup) this, true);
        this.massRipple = new ImageView[6];
        setLayout();
    }

    private void setLayout() {
        this.scale[0] = AnimationUtils.loadAnimation(this.mContext, R.anim.scale_1);
        this.scale[1] = AnimationUtils.loadAnimation(this.mContext, R.anim.scale_1);
        this.scale[2] = AnimationUtils.loadAnimation(this.mContext, R.anim.scale_1);
        this.scale[3] = AnimationUtils.loadAnimation(this.mContext, R.anim.scale_1);
        this.scale[4] = AnimationUtils.loadAnimation(this.mContext, R.anim.scale_1);
        this.scale[5] = AnimationUtils.loadAnimation(this.mContext, R.anim.scale_1);
        for (Animation animation : this.scale) {
            animation.setDuration(1300L);
        }
        this.mCircleMain = (FrameLayout) findViewById(R.id.keyguard_circle_effect_circle_main);
        this.mCircleMain.removeAllViews();
    }

    // TODO: Animation loader

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public boolean handleTouchEvent(View view, MotionEvent event) {
        float insdieViewTouchedEventX = event.getRawX();
        float insdieViewTouchedEventY = event.getRawY();
        touchedEventX = event.getX();
        touchedEventY = event.getY();
        if (this.DEBUG) {
            Log.d(TAG, "insdieViewTouchedEventX = " + insdieViewTouchedEventX + "insdieViewTouchedEventY = " + insdieViewTouchedEventY);
        }
        if (event.getActionMasked() == 0) {
            Log.i(TAG, "ACTION_DOWN");
            if (this.firstTouch_X == -1 && this.firstTouch_Y == -1) {
                Log.i(TAG, "ACTION_DOWN First Touch");
                this.firstTouch_X = (int) insdieViewTouchedEventX;
                this.firstTouch_Y = (int) insdieViewTouchedEventY;
            } else {
                this.firstTouch_X = -1;
                this.firstTouch_Y = -1;
                this.prevMovingDistance = 0.0f;
                this.mDistanceRatio = 0.0d;
            }
            this.mMovingRippleCount = 0;
            setSound();
            this.prevPressTime = SystemClock.uptimeMillis();
            this.diffPressTime = 0L;
            rippeDown(this.touchedEventX, this.touchedEventY, 0, false);
            playSound(0);
            Message message = new Message();
            message.what = MSG_FIRST_TOUCH;
            message.arg1 = (int) this.touchedEventX;
            message.arg2 = (int) this.touchedEventY;
            this.mHandler.sendMessageDelayed(message, 400L);
        } else if (event.getActionMasked() == 2 && event.getActionIndex() == 0) {
            if (this.DEBUG) {
                Log.i(TAG, "ACTION_MOVE");
            }
            if (moveToDistanceIs20percent(insdieViewTouchedEventX, insdieViewTouchedEventY)) {
                if (this.drawRippleCount % 2 == 0) {
                    rippeDown(insdieViewTouchedEventX, insdieViewTouchedEventY, 2, false);
                } else {
                    rippeDown(insdieViewTouchedEventX, insdieViewTouchedEventY, 3, false);
                }
                this.drawRippleCount++;
                this.mMovingRippleCount++;
                playDragSound(1);
            }
            if (this.mDistanceRatio > 1.2999999523162842d) {
                Log.i(TAG, "mDistanceRatio ove DRAG threshold " + this.mDistanceRatio);
            }
        } else if (event.getActionMasked() == 1 || event.getActionMasked() == 3) {
            Log.i(TAG, "ACTION_UP");
            this.firstTouch_X = -1;
            this.firstTouch_Y = -1;
            this.prevMovingDistance = 0.0f;
            this.diffPressTime = SystemClock.uptimeMillis() - this.prevPressTime;
            if (this.diffPressTime > 600) {
                playSound(0);
            }
            if (this.mDistanceRatio > 0.800000011920929d) {
                Log.i(TAG, "mDistanceRatio ove RELEASE threshold " + this.mDistanceRatio);
            }
        } else if (event.getActionMasked() == 9) {
            Log.d(TAG, "ACTION_HOVER_ENTER");
        }
        return true;
    }

    public void rippeDown(float x, float y, int lineStroke, boolean isAffordance) {
        if (this.mMovingRippleCount <= 2) {
            if (this.massRipple[this.indexAni] != null) {
                this.massRipple[this.indexAni].clearAnimation();
                this.massRipple[this.indexAni].setVisibility(View.GONE);
                this.mCircleMain.removeView(this.massRipple[this.indexAni]);
                this.massRipple[this.indexAni] = null;
            }
            float CIRCLE_MAX_SIZE = getCircleSize(lineStroke, isAffordance);
            this.massRipple[this.indexAni] = new MassRippleImageView(this.mContext, this.typeStorke[lineStroke], (int) CIRCLE_MAX_SIZE, (int) CIRCLE_MAX_SIZE, 1300.0f);
            FrameLayout.LayoutParams addViewParams = new FrameLayout.LayoutParams(-2, -2);
            this.mCircleMain.addView(this.massRipple[this.indexAni], addViewParams);
            int moveX = ((int) x) - (this.massRipple[this.indexAni].getBackground().getIntrinsicWidth() / 2);
            int moveY = ((int) y) - (this.massRipple[this.indexAni].getBackground().getIntrinsicHeight() / 2);
            Log.d(TAG, "moveX X = " + moveX + "moveY = " + moveY);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-2, -2);
            params.leftMargin = moveX;
            params.topMargin = moveY;
            params.width = this.massRipple[this.indexAni].getBackground().getIntrinsicWidth();
            params.height = this.massRipple[this.indexAni].getBackground().getIntrinsicHeight();
            this.massRipple[this.indexAni].setLayoutParams(params);
            this.massRipple[this.indexAni].startAnimation(this.scale[this.indexAni]);
            int i = this.indexAni + 1;
            this.indexAni = i;
            this.indexAni = i % 6;
            Log.i(TAG, "lineStroke = " + lineStroke);
            Log.i(TAG, "indexAni = " + this.indexAni);
        }
    }

    public float getCircleSize(int lineStorke, boolean isAffordance) {
        if (isAffordance) {
            return 224.0f;
        }
        return 290.0f - (((this.mMovingRippleCount * 290.0f) * 20.0f) / 100.0f);
    }

    public float translatedFromDPToPixel(float dp) {
        new DisplayMetrics();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float dpi = metrics.densityDpi;
        float ret = dp * (dpi / 160.0f);
        if (this.DEBUG) {
            Log.i(TAG, "dp = " + dp + ", to Pixel = " + ret);
        }
        return ret;
    }

    public boolean moveToDistanceIs20percent(float x, float y) {
        if (this.firstTouch_X == -1 && this.firstTouch_Y == -1) {
            return false;
        }
        int diffX = Math.abs(this.firstTouch_X - ((int) x));
        int diffY = Math.abs(this.firstTouch_Y - ((int) y));
        if (this.DEBUG) {
            Log.d(TAG, "onTouchEvent() : diffX=" + diffX + ",diffY=" + diffY);
        }
        double distance_square = Math.pow(diffX, 2.0d) + Math.pow(diffY, 2.0d);
        double distance = Math.sqrt(distance_square);
        int min = Math.min(this.mCircleMain.getWidth(), this.mCircleMain.getHeight());
        double threshold = min / 2.0d;
        this.mDistanceRatio = distance / threshold;
        Log.d(TAG, "onTouchEvent() : threshold=" + threshold + ",mDistanceRatio=" + this.mDistanceRatio);
        if (this.mDistanceRatio >= 0.45d && Math.abs(this.prevMovingDistance - this.mDistanceRatio) > 0.45d) {
            this.prevMovingDistance = (float) this.mDistanceRatio;
            return true;
        }
        return false;
    }

    /*@Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent event) {
        float originalCircleX = event.getX();
        float originalCircleY = event.getY();
        if (this.DEBUG) {
            Log.i(TAG, " originalCircleX = " + originalCircleX + ", originalCircleY" + originalCircleY);
            Log.i(TAG, " touchedEventX = " + this.touchedEventX + ", touchedEventY" + this.touchedEventY);
        }
        this.touchedEventX = originalCircleX;
        this.touchedEventY = originalCircleY;
        return false;
    }*/

    private void clearAllViews() {
        this.mCircleMain.setVisibility(View.INVISIBLE);
        for (int i = 0; i < this.massRipple.length; i++) {
            if (this.massRipple[i] != null) {
                this.massRipple[i].clearAnimation();
                this.massRipple[i].setVisibility(View.GONE);
                this.mCircleMain.removeView(this.massRipple[i]);
                this.massRipple[i] = null;
            }
        }
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void handleUnlock(View view, MotionEvent event) {
        this.firstTouch_X = -1;
        this.firstTouch_Y = -1;
        this.prevMovingDistance = 0.0f;
        this.isStartUnlock = true;
        playSound(3);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void show() {
        this.mCircleMain.setVisibility(View.VISIBLE);
        checkSound();
        setSound();
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void cleanUp() {
        clearAllViews();
        if (this.mSoundPool != null) {
            this.mSoundPool.release();
            this.mSoundPool = null;
            this.sounds = null;
        }
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void reset() {
        Log.d(TAG, "reset");
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void screenTurnedOn() {
        Log.d(TAG, "screenTurnedOn");
        this.mCircleMain.setVisibility(View.VISIBLE);
        this.isStartUnlock = false;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public long getUnlockDelay() {
        return 301L;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void showUnlockAffordance(long startDelay, Rect rect) {
        Log.d(TAG, "showUnlockAffordance : " + rect.left + ", " + rect.right + ", " + rect.top + ", " + rect.bottom + ", startDelay : " + startDelay);
        this.mCircleMain.setVisibility(View.VISIBLE);
        this.mMovingRippleCount = 0;
        this.touchedEventX = rect.left + ((float) (rect.right - rect.left) / 2);
        this.touchedEventY = rect.top + ((float) (rect.bottom - rect.top) / 2);
        rippeDown(this.touchedEventX, this.touchedEventY, 0, true);
        Message message = new Message();
        message.what = MSG_AFFORDANCE_TOUCH;
        message.arg1 = (int) this.touchedEventX;
        message.arg2 = (int) this.touchedEventY;
        this.mHandler.sendMessageDelayed(message, 400L);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void playLockSound() {
        playSound(2);
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
            Log.d(TAG, "show mSoundPool is null");

            this.mSoundPool = new SoundPool.Builder().setMaxStreams(10).setAudioAttributes(attr).build();
            this.sounds = new int[4];
            this.sounds[0] = this.mSoundPool.load(mRes.openRawResourceFd(mRDownId), 1);
            this.sounds[1] = this.mSoundPool.load(mRes.openRawResourceFd(mRUpId), 1);
            sounds[2] = mSoundPool.load(mRes.openRawResourceFd(R.raw.lock_ripple), 1);
            sounds[3] = mSoundPool.load(mRes.openRawResourceFd(R.raw.unlock_ripple), 1);
        }
    }

    private void playSound(int soundId) {
        if (!this.isStartUnlock && this.isSystemSoundChecked && this.mSoundPool != null) {
            this.mSoundPool.play(this.sounds[soundId], 1.0f, 1.0f, 0, 0, 1.0f);
        }
    }

    private void playDragSound(int soundId) {
        if (!this.isStartUnlock && this.isSystemSoundChecked && this.mSoundPool != null) {
            int streanID = this.mSoundPool.play(this.sounds[soundId], 1.0f, 1.0f, 0, 0, 1.0f);
            SoundPoolThread soundpoolThread = new SoundPoolThread(streanID - 1);
            soundpoolThread.run();
        }
    }

    public class SoundPoolThread extends Thread {
        private int streamID;

        public SoundPoolThread(int tStreamID) {
            this.streamID = tStreamID;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            float leftVolume = 1.0f;
            float rightVolume = 1.0f;
            float decreaseUnit = 1.0f / KeyguardEffectViewMassRipple.this.soundNum;
            if (KeyguardEffectViewMassRipple.this.isSystemSoundChecked && KeyguardEffectViewMassRipple.this.mSoundPool != null) {
                for (int i = 0; i < KeyguardEffectViewMassRipple.this.soundNum; i++) {
                    if (leftVolume <= 1.5f * decreaseUnit) {
                        leftVolume = 0.0f;
                        rightVolume = 0.0f;
                    } else {
                        leftVolume -= decreaseUnit;
                        rightVolume -= decreaseUnit;
                    }
                    if (KeyguardEffectViewMassRipple.this.mSoundPool != null) {
                        KeyguardEffectViewMassRipple.this.mSoundPool.setVolume(this.streamID, leftVolume, rightVolume);
                        SystemClock.sleep(KeyguardEffectViewMassRipple.this.soundTime);
                    } else {
                        return;
                    }
                }
            }
        }
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void update() {
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void screenTurnedOff() {
        clearAllViews();
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public boolean handleTouchEventForPatternLock(View view, MotionEvent event) {
        int action = event.getActionMasked();
        Log.d(TAG, "handleTouchEventForPatternLock action = " + action);
        switch (action) {
            case 0:
                Log.d(TAG, "ACTION_DOWN => ACTION_HOVER_ENTER");
                break;
        }
        return false;
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