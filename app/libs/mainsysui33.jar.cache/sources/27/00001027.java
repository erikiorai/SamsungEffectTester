package com.android.systemui.accessibility;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Insets;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.MathUtils;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.graphics.SfVsyncFrameCallbackProvider;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.R$id;
import com.android.systemui.R$string;
import com.android.systemui.accessibility.MagnificationGestureDetector;
import java.util.Collections;

/* loaded from: mainsysui33.jar:com/android/systemui/accessibility/MagnificationModeSwitch.class */
public class MagnificationModeSwitch implements MagnificationGestureDetector.OnGestureListener, ComponentCallbacks {
    @VisibleForTesting
    public static final int DEFAULT_FADE_OUT_ANIMATION_DELAY_MS = 5000;
    @VisibleForTesting
    public static final long FADING_ANIMATION_DURATION_MS = 300;
    public final AccessibilityManager mAccessibilityManager;
    public final Configuration mConfiguration;
    public final Context mContext;
    @VisibleForTesting
    public final Rect mDraggableWindowBounds;
    public final Runnable mFadeInAnimationTask;
    public final Runnable mFadeOutAnimationTask;
    public final MagnificationGestureDetector mGestureDetector;
    public final ImageView mImageView;
    @VisibleForTesting
    public boolean mIsFadeOutAnimating;
    public boolean mIsVisible;
    public int mMagnificationMode;
    public final WindowManager.LayoutParams mParams;
    public final SfVsyncFrameCallbackProvider mSfVsyncFrameProvider;
    public boolean mSingleTapDetected;
    public final SwitchListener mSwitchListener;
    public boolean mToLeftScreenEdge;
    public int mUiTimeout;
    public final Runnable mWindowInsetChangeRunnable;
    public final WindowManager mWindowManager;

    /* loaded from: mainsysui33.jar:com/android/systemui/accessibility/MagnificationModeSwitch$SwitchListener.class */
    public interface SwitchListener {
        void onSwitch(int i, int i2);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.MagnificationModeSwitch$$ExternalSyntheticLambda0.doFrame(long):void] */
    public static /* synthetic */ void $r8$lambda$0kHB47NtIAsUuOsXeXiZAoRTDqI(MagnificationModeSwitch magnificationModeSwitch, float f, float f2, long j) {
        magnificationModeSwitch.lambda$moveButton$4(f, f2, j);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.MagnificationModeSwitch$$ExternalSyntheticLambda4.run():void] */
    /* renamed from: $r8$lambda$GV-kRiJy1uktjMJpSGhiL82FDJw */
    public static /* synthetic */ void m1325$r8$lambda$GVkRiJy1uktjMJpSGhiL82FDJw(MagnificationModeSwitch magnificationModeSwitch) {
        magnificationModeSwitch.lambda$new$1();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.MagnificationModeSwitch$$ExternalSyntheticLambda7.run():void] */
    public static /* synthetic */ void $r8$lambda$Pu7FSJWLyCkU1d55TiGPAOsuSs8(MagnificationModeSwitch magnificationModeSwitch) {
        magnificationModeSwitch.lambda$setSystemGestureExclusion$5();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.MagnificationModeSwitch$$ExternalSyntheticLambda3.onApplyWindowInsets(android.view.View, android.view.WindowInsets):android.view.WindowInsets] */
    /* renamed from: $r8$lambda$RmmRYLZ5-_TGOsL3BiGHr9fwq5M */
    public static /* synthetic */ WindowInsets m1326$r8$lambda$RmmRYLZ5_TGOsL3BiGHr9fwq5M(MagnificationModeSwitch magnificationModeSwitch, View view, WindowInsets windowInsets) {
        return magnificationModeSwitch.lambda$new$0(view, windowInsets);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.MagnificationModeSwitch$$ExternalSyntheticLambda5.run():void] */
    public static /* synthetic */ void $r8$lambda$XYyw3YXqrbG2VZzBqaKgU7q7tZ4(MagnificationModeSwitch magnificationModeSwitch) {
        magnificationModeSwitch.lambda$new$3();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.MagnificationModeSwitch$$ExternalSyntheticLambda6.run():void] */
    /* renamed from: $r8$lambda$ZPatOWlh3FAjs7S6F4h-3BbUufY */
    public static /* synthetic */ void m1327$r8$lambda$ZPatOWlh3FAjs7S6F4h3BbUufY(MagnificationModeSwitch magnificationModeSwitch) {
        magnificationModeSwitch.lambda$new$2();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.MagnificationModeSwitch$$ExternalSyntheticLambda2.run():void] */
    public static /* synthetic */ void $r8$lambda$eMJtrGmjwwF5JO3yP_Rm3epUIxo(MagnificationModeSwitch magnificationModeSwitch) {
        magnificationModeSwitch.onWindowInsetChanged();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.MagnificationModeSwitch$$ExternalSyntheticLambda1.onTouch(android.view.View, android.view.MotionEvent):boolean] */
    public static /* synthetic */ boolean $r8$lambda$fofjmRfBWUWHTVsQBQtJBGBBy3o(MagnificationModeSwitch magnificationModeSwitch, View view, MotionEvent motionEvent) {
        return magnificationModeSwitch.onTouch(view, motionEvent);
    }

    @VisibleForTesting
    public MagnificationModeSwitch(Context context, ImageView imageView, SfVsyncFrameCallbackProvider sfVsyncFrameCallbackProvider, SwitchListener switchListener) {
        this.mIsFadeOutAnimating = false;
        this.mMagnificationMode = 0;
        this.mDraggableWindowBounds = new Rect();
        this.mIsVisible = false;
        this.mSingleTapDetected = false;
        this.mToLeftScreenEdge = false;
        this.mContext = context;
        this.mConfiguration = new Configuration(context.getResources().getConfiguration());
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        this.mWindowManager = (WindowManager) context.getSystemService(WindowManager.class);
        this.mSfVsyncFrameProvider = sfVsyncFrameCallbackProvider;
        this.mSwitchListener = switchListener;
        this.mParams = createLayoutParams(context);
        this.mImageView = imageView;
        imageView.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.accessibility.MagnificationModeSwitch$$ExternalSyntheticLambda1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return MagnificationModeSwitch.$r8$lambda$fofjmRfBWUWHTVsQBQtJBGBBy3o(MagnificationModeSwitch.this, view, motionEvent);
            }
        });
        imageView.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.accessibility.MagnificationModeSwitch.1
            {
                MagnificationModeSwitch.this = this;
            }

            @Override // android.view.View.AccessibilityDelegate
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.setStateDescription(MagnificationModeSwitch.this.formatStateDescription());
                accessibilityNodeInfo.setContentDescription(MagnificationModeSwitch.this.mContext.getResources().getString(R$string.magnification_mode_switch_description));
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId(), MagnificationModeSwitch.this.mContext.getResources().getString(R$string.magnification_mode_switch_click_label)));
                accessibilityNodeInfo.setClickable(true);
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R$id.accessibility_action_move_up, MagnificationModeSwitch.this.mContext.getString(R$string.accessibility_control_move_up)));
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R$id.accessibility_action_move_down, MagnificationModeSwitch.this.mContext.getString(R$string.accessibility_control_move_down)));
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R$id.accessibility_action_move_left, MagnificationModeSwitch.this.mContext.getString(R$string.accessibility_control_move_left)));
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R$id.accessibility_action_move_right, MagnificationModeSwitch.this.mContext.getString(R$string.accessibility_control_move_right)));
            }

            public final boolean performA11yAction(int i) {
                Rect bounds = MagnificationModeSwitch.this.mWindowManager.getCurrentWindowMetrics().getBounds();
                if (i == AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId()) {
                    MagnificationModeSwitch.this.handleSingleTap();
                    return true;
                } else if (i == R$id.accessibility_action_move_up) {
                    MagnificationModeSwitch.this.moveButton(ActionBarShadowController.ELEVATION_LOW, -bounds.height());
                    return true;
                } else if (i == R$id.accessibility_action_move_down) {
                    MagnificationModeSwitch.this.moveButton(ActionBarShadowController.ELEVATION_LOW, bounds.height());
                    return true;
                } else if (i == R$id.accessibility_action_move_left) {
                    MagnificationModeSwitch.this.moveButton(-bounds.width(), ActionBarShadowController.ELEVATION_LOW);
                    return true;
                } else if (i == R$id.accessibility_action_move_right) {
                    MagnificationModeSwitch.this.moveButton(bounds.width(), ActionBarShadowController.ELEVATION_LOW);
                    return true;
                } else {
                    return false;
                }
            }

            @Override // android.view.View.AccessibilityDelegate
            public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                if (performA11yAction(i)) {
                    return true;
                }
                return super.performAccessibilityAction(view, i, bundle);
            }
        });
        this.mWindowInsetChangeRunnable = new Runnable() { // from class: com.android.systemui.accessibility.MagnificationModeSwitch$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                MagnificationModeSwitch.$r8$lambda$eMJtrGmjwwF5JO3yP_Rm3epUIxo(MagnificationModeSwitch.this);
            }
        };
        imageView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.accessibility.MagnificationModeSwitch$$ExternalSyntheticLambda3
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                return MagnificationModeSwitch.m1326$r8$lambda$RmmRYLZ5_TGOsL3BiGHr9fwq5M(MagnificationModeSwitch.this, view, windowInsets);
            }
        });
        this.mFadeInAnimationTask = new Runnable() { // from class: com.android.systemui.accessibility.MagnificationModeSwitch$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                MagnificationModeSwitch.m1325$r8$lambda$GVkRiJy1uktjMJpSGhiL82FDJw(MagnificationModeSwitch.this);
            }
        };
        this.mFadeOutAnimationTask = new Runnable() { // from class: com.android.systemui.accessibility.MagnificationModeSwitch$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                MagnificationModeSwitch.$r8$lambda$XYyw3YXqrbG2VZzBqaKgU7q7tZ4(MagnificationModeSwitch.this);
            }
        };
        this.mGestureDetector = new MagnificationGestureDetector(context, context.getMainThreadHandler(), this);
    }

    public MagnificationModeSwitch(Context context, SwitchListener switchListener) {
        this(context, createView(context), new SfVsyncFrameCallbackProvider(), switchListener);
    }

    public static WindowManager.LayoutParams createLayoutParams(Context context) {
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R$dimen.magnification_switch_button_size);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(dimensionPixelSize, dimensionPixelSize, 2039, 8, -2);
        layoutParams.gravity = 51;
        layoutParams.accessibilityTitle = getAccessibilityWindowTitle(context);
        layoutParams.layoutInDisplayCutoutMode = 3;
        return layoutParams;
    }

    public static ImageView createView(Context context) {
        ImageView imageView = new ImageView(context);
        imageView.setClickable(true);
        imageView.setFocusable(true);
        imageView.setAlpha(ActionBarShadowController.ELEVATION_LOW);
        return imageView;
    }

    public static String getAccessibilityWindowTitle(Context context) {
        return context.getString(17039675);
    }

    @VisibleForTesting
    public static int getIconResId(int i) {
        return i == 1 ? R$drawable.ic_open_in_new_window : R$drawable.ic_open_in_new_fullscreen;
    }

    public /* synthetic */ void lambda$moveButton$4(float f, float f2, long j) {
        WindowManager.LayoutParams layoutParams = this.mParams;
        layoutParams.x = (int) (layoutParams.x + f);
        layoutParams.y = (int) (layoutParams.y + f2);
        updateButtonViewLayoutIfNeeded();
    }

    public /* synthetic */ WindowInsets lambda$new$0(View view, WindowInsets windowInsets) {
        if (!this.mImageView.getHandler().hasCallbacks(this.mWindowInsetChangeRunnable)) {
            this.mImageView.getHandler().post(this.mWindowInsetChangeRunnable);
        }
        return view.onApplyWindowInsets(windowInsets);
    }

    public /* synthetic */ void lambda$new$1() {
        this.mImageView.animate().alpha(1.0f).setDuration(300L).start();
    }

    public /* synthetic */ void lambda$new$3() {
        this.mImageView.animate().alpha(ActionBarShadowController.ELEVATION_LOW).setDuration(300L).withEndAction(new Runnable() { // from class: com.android.systemui.accessibility.MagnificationModeSwitch$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                MagnificationModeSwitch.m1327$r8$lambda$ZPatOWlh3FAjs7S6F4h3BbUufY(MagnificationModeSwitch.this);
            }
        }).start();
        this.mIsFadeOutAnimating = true;
    }

    public /* synthetic */ void lambda$setSystemGestureExclusion$5() {
        this.mImageView.setSystemGestureExclusionRects(Collections.singletonList(new Rect(0, 0, this.mImageView.getWidth(), this.mImageView.getHeight())));
    }

    public final void applyResourcesValuesWithDensityChanged() {
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R$dimen.magnification_switch_button_size);
        WindowManager.LayoutParams layoutParams = this.mParams;
        layoutParams.height = dimensionPixelSize;
        layoutParams.width = dimensionPixelSize;
        if (this.mIsVisible) {
            stickToScreenEdge(this.mToLeftScreenEdge);
            lambda$new$2();
            showButton(this.mMagnificationMode, false);
        }
    }

    public final CharSequence formatStateDescription() {
        return this.mContext.getResources().getString(this.mMagnificationMode == 2 ? R$string.magnification_mode_switch_state_window : R$string.magnification_mode_switch_state_full_screen);
    }

    public final Rect getDraggableWindowBounds() {
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R$dimen.magnification_switch_button_margin);
        WindowMetrics currentWindowMetrics = this.mWindowManager.getCurrentWindowMetrics();
        Insets insetsIgnoringVisibility = currentWindowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
        Rect rect = new Rect(currentWindowMetrics.getBounds());
        rect.offsetTo(0, 0);
        WindowManager.LayoutParams layoutParams = this.mParams;
        rect.inset(0, 0, layoutParams.width, layoutParams.height);
        rect.inset(insetsIgnoringVisibility);
        rect.inset(dimensionPixelSize, dimensionPixelSize);
        return rect;
    }

    public final void handleSingleTap() {
        lambda$new$2();
        toggleMagnificationMode();
    }

    public final void moveButton(final float f, final float f2) {
        this.mSfVsyncFrameProvider.postFrameCallback(new Choreographer.FrameCallback() { // from class: com.android.systemui.accessibility.MagnificationModeSwitch$$ExternalSyntheticLambda0
            @Override // android.view.Choreographer.FrameCallback
            public final void doFrame(long j) {
                MagnificationModeSwitch.$r8$lambda$0kHB47NtIAsUuOsXeXiZAoRTDqI(MagnificationModeSwitch.this, f, f2, j);
            }
        });
    }

    public void onConfigurationChanged(int i) {
        if (i == 0) {
            return;
        }
        if ((i & 1152) != 0) {
            Rect rect = new Rect(this.mDraggableWindowBounds);
            this.mDraggableWindowBounds.set(getDraggableWindowBounds());
            float height = (this.mParams.y - rect.top) / rect.height();
            this.mParams.y = ((int) (height * this.mDraggableWindowBounds.height())) + this.mDraggableWindowBounds.top;
            stickToScreenEdge(this.mToLeftScreenEdge);
        } else if ((i & RecyclerView.ViewHolder.FLAG_APPEARED_IN_PRE_LAYOUT) != 0) {
            applyResourcesValuesWithDensityChanged();
        } else if ((i & 4) != 0) {
            updateAccessibilityWindowTitle();
        }
    }

    @Override // android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        int diff = configuration.diff(this.mConfiguration);
        this.mConfiguration.setTo(configuration);
        onConfigurationChanged(diff);
    }

    @Override // com.android.systemui.accessibility.MagnificationGestureDetector.OnGestureListener
    public boolean onDrag(float f, float f2) {
        moveButton(f, f2);
        return true;
    }

    @Override // com.android.systemui.accessibility.MagnificationGestureDetector.OnGestureListener
    public boolean onFinish(float f, float f2) {
        if (this.mIsVisible) {
            boolean z = this.mParams.x < this.mWindowManager.getCurrentWindowMetrics().getBounds().width() / 2;
            this.mToLeftScreenEdge = z;
            stickToScreenEdge(z);
        }
        if (!this.mSingleTapDetected) {
            showButton(this.mMagnificationMode);
        }
        this.mSingleTapDetected = false;
        return true;
    }

    @Override // android.content.ComponentCallbacks
    public void onLowMemory() {
    }

    @Override // com.android.systemui.accessibility.MagnificationGestureDetector.OnGestureListener
    public boolean onSingleTap() {
        this.mSingleTapDetected = true;
        handleSingleTap();
        return true;
    }

    @Override // com.android.systemui.accessibility.MagnificationGestureDetector.OnGestureListener
    public boolean onStart(float f, float f2) {
        stopFadeOutAnimation();
        return true;
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        if (this.mIsVisible) {
            return this.mGestureDetector.onTouch(motionEvent);
        }
        return false;
    }

    public final void onWindowInsetChanged() {
        Rect draggableWindowBounds = getDraggableWindowBounds();
        if (this.mDraggableWindowBounds.equals(draggableWindowBounds)) {
            return;
        }
        this.mDraggableWindowBounds.set(draggableWindowBounds);
        stickToScreenEdge(this.mToLeftScreenEdge);
    }

    /* renamed from: removeButton */
    public void lambda$new$2() {
        if (this.mIsVisible) {
            this.mImageView.removeCallbacks(this.mFadeInAnimationTask);
            this.mImageView.removeCallbacks(this.mFadeOutAnimationTask);
            this.mImageView.animate().cancel();
            this.mIsFadeOutAnimating = false;
            this.mImageView.setAlpha(ActionBarShadowController.ELEVATION_LOW);
            this.mWindowManager.removeView(this.mImageView);
            this.mContext.unregisterComponentCallbacks(this);
            this.mIsVisible = false;
        }
    }

    public final void setSystemGestureExclusion() {
        this.mImageView.post(new Runnable() { // from class: com.android.systemui.accessibility.MagnificationModeSwitch$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                MagnificationModeSwitch.$r8$lambda$Pu7FSJWLyCkU1d55TiGPAOsuSs8(MagnificationModeSwitch.this);
            }
        });
    }

    public void showButton(int i) {
        showButton(i, true);
    }

    public final void showButton(int i, boolean z) {
        if (this.mMagnificationMode != i) {
            this.mMagnificationMode = i;
            this.mImageView.setImageResource(getIconResId(i));
        }
        if (!this.mIsVisible) {
            onConfigurationChanged(this.mContext.getResources().getConfiguration());
            this.mContext.registerComponentCallbacks(this);
            if (z) {
                this.mDraggableWindowBounds.set(getDraggableWindowBounds());
                WindowManager.LayoutParams layoutParams = this.mParams;
                Rect rect = this.mDraggableWindowBounds;
                layoutParams.x = rect.right;
                layoutParams.y = rect.bottom;
                this.mToLeftScreenEdge = false;
            }
            this.mWindowManager.addView(this.mImageView, this.mParams);
            setSystemGestureExclusion();
            this.mIsVisible = true;
            this.mImageView.postOnAnimation(this.mFadeInAnimationTask);
            this.mUiTimeout = this.mAccessibilityManager.getRecommendedTimeoutMillis(DEFAULT_FADE_OUT_ANIMATION_DELAY_MS, 5);
        }
        stopFadeOutAnimation();
        this.mImageView.postOnAnimationDelayed(this.mFadeOutAnimationTask, this.mUiTimeout);
    }

    public final void stickToScreenEdge(boolean z) {
        this.mParams.x = z ? this.mDraggableWindowBounds.left : this.mDraggableWindowBounds.right;
        updateButtonViewLayoutIfNeeded();
    }

    public final void stopFadeOutAnimation() {
        this.mImageView.removeCallbacks(this.mFadeOutAnimationTask);
        if (this.mIsFadeOutAnimating) {
            this.mImageView.animate().cancel();
            this.mImageView.setAlpha(1.0f);
            this.mIsFadeOutAnimating = false;
        }
    }

    public final void toggleMagnificationMode() {
        int i = this.mMagnificationMode ^ 3;
        this.mMagnificationMode = i;
        this.mImageView.setImageResource(getIconResId(i));
        this.mSwitchListener.onSwitch(this.mContext.getDisplayId(), i);
    }

    public final void updateAccessibilityWindowTitle() {
        this.mParams.accessibilityTitle = getAccessibilityWindowTitle(this.mContext);
        if (this.mIsVisible) {
            this.mWindowManager.updateViewLayout(this.mImageView, this.mParams);
        }
    }

    public final void updateButtonViewLayoutIfNeeded() {
        if (this.mIsVisible) {
            WindowManager.LayoutParams layoutParams = this.mParams;
            int i = layoutParams.x;
            Rect rect = this.mDraggableWindowBounds;
            layoutParams.x = MathUtils.constrain(i, rect.left, rect.right);
            WindowManager.LayoutParams layoutParams2 = this.mParams;
            int i2 = layoutParams2.y;
            Rect rect2 = this.mDraggableWindowBounds;
            layoutParams2.y = MathUtils.constrain(i2, rect2.top, rect2.bottom);
            this.mWindowManager.updateViewLayout(this.mImageView, this.mParams);
        }
    }
}