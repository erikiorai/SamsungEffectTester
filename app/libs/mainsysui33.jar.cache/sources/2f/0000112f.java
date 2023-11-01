package com.android.systemui.assist.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.metrics.LogMaker;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import com.android.internal.logging.MetricsLogger;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$layout;
import com.android.systemui.assist.AssistLogger;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.assist.AssistantSessionEvent;
import dagger.Lazy;
import java.util.Locale;

/* loaded from: mainsysui33.jar:com/android/systemui/assist/ui/DefaultUiController.class */
public class DefaultUiController implements AssistManager.UiController {
    private static final long ANIM_DURATION_MS = 200;
    private static final String TAG = "DefaultUiController";
    private static final boolean VERBOSE;
    public final AssistLogger mAssistLogger;
    private final Lazy<AssistManager> mAssistManagerLazy;
    public InvocationLightsView mInvocationLightsView;
    private final WindowManager.LayoutParams mLayoutParams;
    private final MetricsLogger mMetricsLogger;
    public final FrameLayout mRoot;
    private final WindowManager mWindowManager;
    private final PathInterpolator mProgressInterpolator = new PathInterpolator(0.83f, ActionBarShadowController.ELEVATION_LOW, 0.84f, 1.0f);
    private boolean mAttached = false;
    private boolean mInvocationInProgress = false;
    private float mLastInvocationProgress = ActionBarShadowController.ELEVATION_LOW;
    private ValueAnimator mInvocationAnimator = new ValueAnimator();

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.assist.ui.DefaultUiController$$ExternalSyntheticLambda0.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$sk0ku4pQsD5FH9JgWmx0chQFSxc(DefaultUiController defaultUiController, int i, ValueAnimator valueAnimator) {
        defaultUiController.lambda$animateInvocationCompletion$0(i, valueAnimator);
    }

    static {
        String str = Build.TYPE;
        Locale locale = Locale.ROOT;
        VERBOSE = str.toLowerCase(locale).contains("debug") || str.toLowerCase(locale).equals("eng");
    }

    public DefaultUiController(Context context, AssistLogger assistLogger, WindowManager windowManager, MetricsLogger metricsLogger, Lazy<AssistManager> lazy) {
        this.mAssistLogger = assistLogger;
        FrameLayout frameLayout = new FrameLayout(context);
        this.mRoot = frameLayout;
        this.mWindowManager = windowManager;
        this.mMetricsLogger = metricsLogger;
        this.mAssistManagerLazy = lazy;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -2, 0, 0, 2024, 808, -3);
        this.mLayoutParams = layoutParams;
        layoutParams.privateFlags = 64;
        layoutParams.gravity = 80;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.setTitle("Assist");
        InvocationLightsView invocationLightsView = (InvocationLightsView) LayoutInflater.from(context).inflate(R$layout.invocation_lights, (ViewGroup) frameLayout, false);
        this.mInvocationLightsView = invocationLightsView;
        frameLayout.addView(invocationLightsView);
    }

    private void animateInvocationCompletion(final int i, float f) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mLastInvocationProgress, 1.0f);
        this.mInvocationAnimator = ofFloat;
        ofFloat.setStartDelay(1L);
        this.mInvocationAnimator.setDuration(ANIM_DURATION_MS);
        this.mInvocationAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.assist.ui.DefaultUiController$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                DefaultUiController.$r8$lambda$sk0ku4pQsD5FH9JgWmx0chQFSxc(DefaultUiController.this, i, valueAnimator);
            }
        });
        this.mInvocationAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.assist.ui.DefaultUiController.1
            {
                DefaultUiController.this = this;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                DefaultUiController.this.mInvocationInProgress = false;
                DefaultUiController.this.mLastInvocationProgress = ActionBarShadowController.ELEVATION_LOW;
                DefaultUiController.this.hide();
            }
        });
        this.mInvocationAnimator.start();
    }

    private void attach() {
        if (this.mAttached) {
            return;
        }
        this.mWindowManager.addView(this.mRoot, this.mLayoutParams);
        this.mAttached = true;
    }

    private void detach() {
        if (this.mAttached) {
            this.mWindowManager.removeViewImmediate(this.mRoot);
            this.mAttached = false;
        }
    }

    public /* synthetic */ void lambda$animateInvocationCompletion$0(int i, ValueAnimator valueAnimator) {
        setProgressInternal(i, ((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    private void setProgressInternal(int i, float f) {
        this.mInvocationLightsView.onInvocationProgress(this.mProgressInterpolator.getInterpolation(f));
    }

    @Override // com.android.systemui.assist.AssistManager.UiController
    public void hide() {
        detach();
        if (this.mInvocationAnimator.isRunning()) {
            this.mInvocationAnimator.cancel();
        }
        this.mInvocationLightsView.hide();
        this.mInvocationInProgress = false;
    }

    public void logInvocationProgressMetrics(int i, float f, boolean z) {
        if (f == 1.0f && VERBOSE) {
            Log.v(TAG, "Invocation complete: type=" + i);
        }
        if (!z && f > ActionBarShadowController.ELEVATION_LOW) {
            if (VERBOSE) {
                Log.v(TAG, "Invocation started: type=" + i);
            }
            this.mAssistLogger.reportAssistantInvocationEventFromLegacy(i, false, null, null);
            this.mMetricsLogger.write(new LogMaker(1716).setType(4).setSubtype(((AssistManager) this.mAssistManagerLazy.get()).toLoggingSubType(i)));
        }
        ValueAnimator valueAnimator = this.mInvocationAnimator;
        if ((valueAnimator == null || !valueAnimator.isRunning()) && z && f == ActionBarShadowController.ELEVATION_LOW) {
            if (VERBOSE) {
                Log.v(TAG, "Invocation cancelled: type=" + i);
            }
            this.mAssistLogger.reportAssistantSessionEvent(AssistantSessionEvent.ASSISTANT_SESSION_INVOCATION_CANCELLED);
            MetricsLogger.action(new LogMaker(1716).setType(5).setSubtype(1));
        }
    }

    @Override // com.android.systemui.assist.AssistManager.UiController
    public void onGestureCompletion(float f) {
        animateInvocationCompletion(1, f);
        logInvocationProgressMetrics(1, 1.0f, this.mInvocationInProgress);
    }

    @Override // com.android.systemui.assist.AssistManager.UiController
    public void onInvocationProgress(int i, float f) {
        boolean z = this.mInvocationInProgress;
        if (f == 1.0f) {
            animateInvocationCompletion(i, ActionBarShadowController.ELEVATION_LOW);
        } else if (f == ActionBarShadowController.ELEVATION_LOW) {
            hide();
        } else {
            if (!z) {
                attach();
                this.mInvocationInProgress = true;
            }
            setProgressInternal(i, f);
        }
        this.mLastInvocationProgress = f;
        logInvocationProgressMetrics(i, f, z);
    }
}