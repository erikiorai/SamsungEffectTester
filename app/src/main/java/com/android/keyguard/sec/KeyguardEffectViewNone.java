package com.android.keyguard.sec;

/* todo doesn't work... yet
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

    public KeyguardEffectViewNone(Context context) {
        super(context);
        init(context, 0, true);
    }

    public void init(Context context, int type, boolean mWallpaperProcessSeparated) {
        Log.d("VisualEffectCircleUnlockEffect", "KeyguardEffectViewNone : Constructor");
        this.mContext = context.getApplicationContext();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        int smallestWidth = Math.min(screenWidth, screenHeight);
        float ratio = smallestWidth / 1080.0f;
        Log.d("VisualEffectCircleUnlockEffect", "screenWidth : " + screenWidth);
        Log.d("VisualEffectCircleUnlockEffect", "screenHeight : " + screenHeight);
        Log.d("VisualEffectCircleUnlockEffect", "ratio : " + ratio);
        this.touchHashmap = new HashMap<>();
        int circleUnlockMaxWidth = 0;
        if (type == 0) {
            circleUnlockMaxWidth = ((int) this.mContext.getResources().getDimension(R.dimen.keyguard_lockscreen_first_border)) * 2;
        } else if (type == 1) {
            circleUnlockMaxWidth = ((int) this.mContext.getResources().getDimension(R.dimen.keyguard_lockscreen_first_border)) * 2; // shortcut usage, but both the same
        }
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
        this.circleEffect = new EffectView(this.mContext);
        this.circleEffect.setEffect(2);
        EffectDataObj data = new EffectDataObj();
        data.setEffect(2);
        data.circleData.circleUnlockMaxWidth = circleUnlockMaxWidth;
        data.circleData.outerStrokeWidth = outerStrokeWidth;
        data.circleData.innerStrokeWidth = innerStrokeWidth;
        data.circleData.lockSequenceImageId = lockSequenceImageId;
        data.circleData.arrowId = R.drawable.keyguard_none_arrow;
        data.circleData.hasNoOuterCircle = KeyguardProperties.isUSAFeature(); // todo implement usa switch
        this.circleEffect.init(data);
        if (KeyguardProperties.isLatestPhoneUX() || KeyguardProperties.isLatestTabletUX()) { // todo implement latest switch
            setMinWidthOffset((int) this.mContext.getResources().getDimension(R.dimen.keyguard_shortcut_min_width_offset)); // 0dp?
            setArrowForButton(R.drawable.keyguard_shortcut_arrow);
        }
        if (KeyguardProperties.isLatestShortcutEffect()) {
            setOuterCircleType(false);
            showSwipeCircleEffect(false);
        }
        addView(this.circleEffect);
    }

    public void setMinWidthOffset(int offset) {
        EffectDataObj data = new EffectDataObj();
        data.setEffect(2);
        data.circleData.minWidthOffset = offset;
        this.circleEffect.reInit(data);
    }

    public void showSwipeCircleEffect(boolean value) {
        Log.d("VisualEffectCircleUnlockEffect", "KeyguardEffectViewNone : showSwipeCircleEffect");
        HashMap<String, Boolean> hm = new HashMap<>();
        hm.put("showSwipeCircleEffect", Boolean.valueOf(value));
        this.circleEffect.handleCustomEvent(99, hm);
    }

    private void setOuterCircleType(boolean isStroke) {
        Log.d("VisualEffectCircleUnlockEffect", "KeyguardEffectViewNone : setOuterCircleType");
        HashMap<String, Boolean> hm = new HashMap<>();
        hm.put("setOuterCircleType", Boolean.valueOf(isStroke));
        this.circleEffect.handleCustomEvent(99, hm);
    }

    public void setArrowForButton(int arrowForButtonId) {
        EffectDataObj data = new EffectDataObj();
        data.setEffect(2);
        data.circleData.arrowForButtonId = arrowForButtonId;
        this.circleEffect.reInit(data);
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public boolean handleTouchEvent(View view, MotionEvent event) {
        this.circleEffect.handleTouchEvent(event, view);
        return true;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void show() {
        Log.d("VisualEffectCircleUnlockEffect", "KeyguardEffectViewNone : show");
        if (this.circleEffect != null) {
            this.circleEffect.clearScreen();
        }
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void reset() {
        Log.d("VisualEffectCircleUnlockEffect", "KeyguardEffectViewNone : reset");
        if (this.circleEffect != null) {
            this.circleEffect.clearScreen();
        }
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void cleanUp() {
        Log.d("VisualEffectCircleUnlockEffect", "KeyguardEffectViewNone : cleanUp");
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void update() {
        Log.d("VisualEffectCircleUnlockEffect", "KeyguardEffectViewNone : update");
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void screenTurnedOn() {
        Log.d("VisualEffectCircleUnlockEffect", "KeyguardEffectViewNone : screenTurnedOn");
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void screenTurnedOff() {
        Log.d("VisualEffectCircleUnlockEffect", "KeyguardEffectViewNone : screenTurnedOff");
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void showUnlockAffordance(long startDelay, Rect rect) {
        Log.d("VisualEffectCircleUnlockEffect", "KeyguardEffectViewNone : showUnlockAffordance");
        if (this.circleEffect != null) {
            HashMap<String, Object> hm = new HashMap<>();
            hm.put("StartDelay", Long.valueOf(startDelay));
            hm.put("Rect", rect);
            this.circleEffect.handleCustomEvent(1, hm);
        }
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public long getUnlockDelay() {
        return 0L;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void handleUnlock(View view, MotionEvent event) {
        Log.d("VisualEffectCircleUnlockEffect", "KeyguardEffectViewNone : handleUnlock");
        if (this.circleEffect != null) {
            this.circleEffect.handleCustomEvent(2, (HashMap) null);
        }
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void playLockSound() {
        // todo play lock sound betch
        Log.d("VisualEffectCircleUnlockEffect", "KeyguardEffectViewNone : playLockSound");
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public boolean handleTouchEventForPatternLock(View view, MotionEvent event) {
        return false;
    }

    @Override // com.android.keyguard.sec.effect.KeyguardEffectViewBase
    public void setHidden(boolean isHidden) {
        if (this.circleEffect != null) {
            this.circleEffect.clearScreen();
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
}*/