package com.android.systemui.navigationbar.buttons;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.hardware.input.InputManager;
import android.media.AudioManager;
import android.metrics.LogMaker;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.Dependency;
import com.android.systemui.R$dimen;
import com.android.systemui.R$styleable;
import com.android.systemui.navigationbar.buttons.KeyButtonRipple;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.shared.system.QuickStepContract;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/buttons/KeyButtonView.class */
public class KeyButtonView extends ImageView implements ButtonInterface {
    public static final String TAG = KeyButtonView.class.getSimpleName();
    public AudioManager mAudioManager;
    public final Runnable mCheckLongPress;
    public int mCode;
    public int mContentDescriptionRes;
    public float mDarkIntensity;
    public long mDownTime;
    public boolean mGestureAborted;
    public boolean mHasOvalBg;
    public final InputManager mInputManager;
    public boolean mIsVertical;
    @VisibleForTesting
    public boolean mLongClicked;
    public final MetricsLogger mMetricsLogger;
    public View.OnClickListener mOnClickListener;
    public final Paint mOvalBgPaint;
    public final OverviewProxyService mOverviewProxyService;
    public final boolean mPlaySounds;
    public final KeyButtonRipple mRipple;
    public int mTouchDownX;
    public int mTouchDownY;
    public final UiEventLogger mUiEventLogger;

    @VisibleForTesting
    /* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/buttons/KeyButtonView$NavBarButtonEvent.class */
    public enum NavBarButtonEvent implements UiEventLogger.UiEventEnum {
        NAVBAR_HOME_BUTTON_TAP(533),
        NAVBAR_BACK_BUTTON_TAP(534),
        NAVBAR_OVERVIEW_BUTTON_TAP(535),
        NAVBAR_IME_SWITCHER_BUTTON_TAP(923),
        NAVBAR_HOME_BUTTON_LONGPRESS(536),
        NAVBAR_BACK_BUTTON_LONGPRESS(537),
        NAVBAR_OVERVIEW_BUTTON_LONGPRESS(538),
        NONE(0);
        
        private final int mId;

        NavBarButtonEvent(int i) {
            this.mId = i;
        }

        public int getId() {
            return this.mId;
        }
    }

    public KeyButtonView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public KeyButtonView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, InputManager.getInstance(), new UiEventLoggerImpl());
    }

    @VisibleForTesting
    public KeyButtonView(Context context, AttributeSet attributeSet, int i, InputManager inputManager, UiEventLogger uiEventLogger) {
        super(context, attributeSet);
        this.mMetricsLogger = (MetricsLogger) Dependency.get(MetricsLogger.class);
        this.mOvalBgPaint = new Paint(3);
        this.mHasOvalBg = false;
        this.mCheckLongPress = new Runnable() { // from class: com.android.systemui.navigationbar.buttons.KeyButtonView.1
            @Override // java.lang.Runnable
            public void run() {
                if (KeyButtonView.this.isPressed()) {
                    if (KeyButtonView.this.mCode == 21 || KeyButtonView.this.mCode == 22) {
                        KeyButtonView.this.sendEvent(1, 6, System.currentTimeMillis(), false);
                        KeyButtonView.this.sendEvent(0, 6, System.currentTimeMillis(), false);
                        KeyButtonView keyButtonView = KeyButtonView.this;
                        keyButtonView.postDelayed(keyButtonView.mCheckLongPress, ViewConfiguration.getKeyRepeatDelay());
                    } else if (KeyButtonView.this.isLongClickable()) {
                        KeyButtonView.this.performLongClick();
                        KeyButtonView.this.mLongClicked = true;
                    } else {
                        if (KeyButtonView.this.mCode != 0) {
                            KeyButtonView.this.sendEvent(0, RecyclerView.ViewHolder.FLAG_IGNORE);
                            KeyButtonView.this.sendAccessibilityEvent(2);
                        }
                        KeyButtonView.this.mLongClicked = true;
                    }
                }
            }
        };
        this.mUiEventLogger = uiEventLogger;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.KeyButtonView, i, 0);
        this.mCode = obtainStyledAttributes.getInteger(R$styleable.KeyButtonView_keyCode, 0);
        this.mPlaySounds = obtainStyledAttributes.getBoolean(R$styleable.KeyButtonView_playSound, true);
        TypedValue typedValue = new TypedValue();
        if (obtainStyledAttributes.getValue(R$styleable.KeyButtonView_android_contentDescription, typedValue)) {
            this.mContentDescriptionRes = typedValue.resourceId;
        }
        obtainStyledAttributes.recycle();
        setClickable(true);
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
        KeyButtonRipple keyButtonRipple = new KeyButtonRipple(context, this, R$dimen.key_button_ripple_max_width);
        this.mRipple = keyButtonRipple;
        this.mOverviewProxyService = (OverviewProxyService) Dependency.get(OverviewProxyService.class);
        this.mInputManager = inputManager;
        setBackground(keyButtonRipple);
        setWillNotDraw(false);
        forceHasOverlappingRendering(false);
    }

    public void abortCurrentGesture() {
        Log.d("b/63783866", "KeyButtonView.abortCurrentGesture");
        if (this.mCode != 0) {
            sendEvent(1, 32);
        }
        setPressed(false);
        this.mRipple.abortDelayedRipple();
        this.mGestureAborted = true;
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        if (this.mHasOvalBg) {
            float min = Math.min(getWidth(), getHeight());
            canvas.drawOval(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, min, min, this.mOvalBgPaint);
        }
        super.draw(canvas);
    }

    @Override // android.view.View
    public boolean isClickable() {
        return this.mCode != 0 || super.isClickable();
    }

    public void loadAsync(Icon icon) {
        new AsyncTask<Icon, Void, Drawable>() { // from class: com.android.systemui.navigationbar.buttons.KeyButtonView.2
            /* JADX DEBUG: Method merged with bridge method */
            @Override // android.os.AsyncTask
            public Drawable doInBackground(Icon... iconArr) {
                return iconArr[0].loadDrawable(((ImageView) KeyButtonView.this).mContext);
            }

            /* JADX DEBUG: Method merged with bridge method */
            @Override // android.os.AsyncTask
            public void onPostExecute(Drawable drawable) {
                KeyButtonView.this.setImageDrawable(drawable);
            }
        }.execute(icon);
    }

    public final void logSomePresses(int i, int i2) {
        boolean z = (i2 & RecyclerView.ViewHolder.FLAG_IGNORE) != 0;
        NavBarButtonEvent navBarButtonEvent = NavBarButtonEvent.NONE;
        if (i == 1 && this.mLongClicked) {
            return;
        }
        if ((i != 0 || z) && (i2 & 32) == 0 && (i2 & RecyclerView.ViewHolder.FLAG_TMP_DETACHED) == 0) {
            int i3 = this.mCode;
            NavBarButtonEvent navBarButtonEvent2 = i3 != 3 ? i3 != 4 ? i3 != 187 ? navBarButtonEvent : z ? NavBarButtonEvent.NAVBAR_OVERVIEW_BUTTON_LONGPRESS : NavBarButtonEvent.NAVBAR_OVERVIEW_BUTTON_TAP : z ? NavBarButtonEvent.NAVBAR_BACK_BUTTON_LONGPRESS : NavBarButtonEvent.NAVBAR_BACK_BUTTON_TAP : z ? NavBarButtonEvent.NAVBAR_HOME_BUTTON_LONGPRESS : NavBarButtonEvent.NAVBAR_HOME_BUTTON_TAP;
            if (navBarButtonEvent2 != navBarButtonEvent) {
                this.mUiEventLogger.log(navBarButtonEvent2);
            }
        }
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int i = this.mContentDescriptionRes;
        if (i != 0) {
            setContentDescription(((ImageView) this).mContext.getString(i));
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (this.mCode != 0) {
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, null));
            if (isLongClickable()) {
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(32, null));
            }
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        View.OnClickListener onClickListener;
        boolean shouldShowSwipeUpUI = this.mOverviewProxyService.shouldShowSwipeUpUI();
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mGestureAborted = false;
        }
        if (this.mGestureAborted) {
            setPressed(false);
            return false;
        } else if (action == 0) {
            this.mDownTime = SystemClock.uptimeMillis();
            this.mLongClicked = false;
            setPressed(true);
            this.mTouchDownX = (int) motionEvent.getX();
            this.mTouchDownY = (int) motionEvent.getY();
            int i = this.mCode;
            if (i == 21 || i == 22) {
                sendEvent(0, 68, this.mDownTime, false);
            } else if (i != 0) {
                sendEvent(0, 0, this.mDownTime);
            } else {
                performHapticFeedback(1);
            }
            if (!shouldShowSwipeUpUI) {
                playSoundEffect(0);
            }
            removeCallbacks(this.mCheckLongPress);
            postDelayed(this.mCheckLongPress, ViewConfiguration.getLongPressTimeout());
            return true;
        } else if (action != 1) {
            if (action != 2) {
                if (action != 3) {
                    return true;
                }
                setPressed(false);
                if (this.mCode != 0) {
                    sendEvent(1, 32);
                }
                removeCallbacks(this.mCheckLongPress);
                return true;
            }
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            float quickStepTouchSlopPx = QuickStepContract.getQuickStepTouchSlopPx(getContext());
            if (Math.abs(x - this.mTouchDownX) > quickStepTouchSlopPx || Math.abs(y - this.mTouchDownY) > quickStepTouchSlopPx) {
                setPressed(false);
                removeCallbacks(this.mCheckLongPress);
                return true;
            }
            return true;
        } else {
            boolean z = isPressed() && !this.mLongClicked;
            setPressed(false);
            boolean z2 = SystemClock.uptimeMillis() - this.mDownTime > 150;
            if (shouldShowSwipeUpUI) {
                if (z) {
                    performHapticFeedback(1);
                    playSoundEffect(0);
                }
            } else if (z2 && !this.mLongClicked) {
                performHapticFeedback(8);
            }
            if (this.mCode != 0) {
                if (z) {
                    sendEvent(1, 0);
                    sendAccessibilityEvent(1);
                } else {
                    sendEvent(1, 32);
                }
            } else if (z && (onClickListener = this.mOnClickListener) != null) {
                onClickListener.onClick(this);
                sendAccessibilityEvent(1);
            }
            removeCallbacks(this.mCheckLongPress);
            return true;
        }
    }

    @Override // android.view.View
    public void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        if (i != 0) {
            jumpDrawablesToCurrentState();
        }
    }

    public boolean performAccessibilityActionInternal(int i, Bundle bundle) {
        if (i == 16 && this.mCode != 0) {
            sendEvent(0, 0, SystemClock.uptimeMillis());
            sendEvent(1, 0);
            sendAccessibilityEvent(1);
            playSoundEffect(0);
            return true;
        } else if (i != 32 || this.mCode == 0) {
            return super.performAccessibilityActionInternal(i, bundle);
        } else {
            sendEvent(0, RecyclerView.ViewHolder.FLAG_IGNORE);
            sendEvent(1, 0);
            sendAccessibilityEvent(2);
            return true;
        }
    }

    @Override // android.view.View
    public void playSoundEffect(int i) {
        if (this.mPlaySounds) {
            this.mAudioManager.playSoundEffect(i, ActivityManager.getCurrentUser());
        }
    }

    public void sendEvent(int i, int i2) {
        sendEvent(i, i2, SystemClock.uptimeMillis());
    }

    public final void sendEvent(int i, int i2, long j) {
        sendEvent(i, i2, j, true);
    }

    public final void sendEvent(int i, int i2, long j, boolean z) {
        this.mMetricsLogger.write(new LogMaker(931).setType(4).setSubtype(this.mCode).addTaggedData(933, Integer.valueOf(i)).addTaggedData(932, Integer.valueOf(i2)));
        logSomePresses(i, i2);
        if (this.mCode == 4 && i2 != 128) {
            String str = TAG;
            Log.i(str, "Back button event: " + KeyEvent.actionToString(i));
        }
        int i3 = (i2 & RecyclerView.ViewHolder.FLAG_IGNORE) != 0 ? 1 : 0;
        int i4 = i2;
        if (z) {
            i4 = i2 | 72;
        }
        KeyEvent keyEvent = new KeyEvent(this.mDownTime, j, i, this.mCode, i3, 0, -1, 0, i4, 134217729);
        int displayId = getDisplay() != null ? getDisplay().getDisplayId() : -1;
        if (displayId != -1) {
            keyEvent.setDisplayId(displayId);
        }
        this.mInputManager.injectInputEvent(keyEvent, 0);
    }

    public void setCode(int i) {
        this.mCode = i;
    }

    @Override // com.android.systemui.navigationbar.buttons.ButtonInterface
    public void setDarkIntensity(float f) {
        this.mDarkIntensity = f;
        Drawable drawable = getDrawable();
        if (drawable != null) {
            ((KeyButtonDrawable) drawable).setDarkIntensity(f);
            invalidate();
        }
        this.mRipple.setDarkIntensity(f);
    }

    @Override // com.android.systemui.navigationbar.buttons.ButtonInterface
    public void setDelayTouchFeedback(boolean z) {
        this.mRipple.setDelayTouchFeedback(z);
    }

    @Override // android.widget.ImageView, com.android.systemui.navigationbar.buttons.ButtonInterface
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        if (drawable == null) {
            return;
        }
        KeyButtonDrawable keyButtonDrawable = (KeyButtonDrawable) drawable;
        keyButtonDrawable.setDarkIntensity(this.mDarkIntensity);
        boolean hasOvalBg = keyButtonDrawable.hasOvalBg();
        this.mHasOvalBg = hasOvalBg;
        if (hasOvalBg) {
            this.mOvalBgPaint.setColor(keyButtonDrawable.getDrawableBackgroundColor());
        }
        this.mRipple.setType(keyButtonDrawable.hasOvalBg() ? KeyButtonRipple.Type.OVAL : KeyButtonRipple.Type.ROUNDED_RECT);
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener onClickListener) {
        super.setOnClickListener(onClickListener);
        this.mOnClickListener = onClickListener;
    }

    @Override // com.android.systemui.navigationbar.buttons.ButtonInterface
    public void setVertical(boolean z) {
        this.mIsVertical = z;
    }
}