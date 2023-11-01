package com.android.systemui.power;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import java.util.Objects;

/* loaded from: mainsysui33.jar:com/android/systemui/power/InattentiveSleepWarningView.class */
public class InattentiveSleepWarningView extends FrameLayout {
    public boolean mDismissing;
    public Animator mFadeOutAnimator;
    public final WindowManager mWindowManager;
    public final IBinder mWindowToken;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.power.InattentiveSleepWarningView$$ExternalSyntheticLambda0.onKey(android.view.View, int, android.view.KeyEvent):boolean] */
    public static /* synthetic */ boolean $r8$lambda$LPWUhkWX6oX1M0lPSoA53KT3Dwc(View view, int i, KeyEvent keyEvent) {
        return lambda$new$0(view, i, keyEvent);
    }

    public InattentiveSleepWarningView(Context context) {
        super(context);
        this.mWindowToken = new Binder();
        this.mWindowManager = (WindowManager) ((FrameLayout) this).mContext.getSystemService(WindowManager.class);
        LayoutInflater.from(((FrameLayout) this).mContext).inflate(R$layout.inattentive_sleep_warning, (ViewGroup) this, true);
        setFocusable(true);
        setOnKeyListener(new View.OnKeyListener() { // from class: com.android.systemui.power.InattentiveSleepWarningView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                return InattentiveSleepWarningView.$r8$lambda$LPWUhkWX6oX1M0lPSoA53KT3Dwc(view, i, keyEvent);
            }
        });
        Animator loadAnimator = AnimatorInflater.loadAnimator(getContext(), 17498113);
        this.mFadeOutAnimator = loadAnimator;
        loadAnimator.setTarget(this);
        this.mFadeOutAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.power.InattentiveSleepWarningView.1
            {
                InattentiveSleepWarningView.this = this;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                InattentiveSleepWarningView.this.mDismissing = false;
                InattentiveSleepWarningView.this.setAlpha(1.0f);
                InattentiveSleepWarningView.this.setVisibility(0);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                InattentiveSleepWarningView.this.removeView();
            }
        });
    }

    public static /* synthetic */ boolean lambda$new$0(View view, int i, KeyEvent keyEvent) {
        return true;
    }

    public void dismiss(boolean z) {
        if (getParent() == null) {
            return;
        }
        this.mDismissing = true;
        if (!z) {
            removeView();
            return;
        }
        final Animator animator = this.mFadeOutAnimator;
        Objects.requireNonNull(animator);
        postOnAnimation(new Runnable() { // from class: com.android.systemui.power.InattentiveSleepWarningView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                animator.start();
            }
        });
    }

    public final WindowManager.LayoutParams getLayoutParams(IBinder iBinder) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2006, RecyclerView.ViewHolder.FLAG_TMP_DETACHED, -3);
        layoutParams.privateFlags |= 16;
        layoutParams.setTitle("InattentiveSleepWarning");
        layoutParams.token = iBinder;
        return layoutParams;
    }

    public final void removeView() {
        if (this.mDismissing) {
            setVisibility(4);
            this.mWindowManager.removeView(this);
        }
    }

    public void show() {
        if (getParent() != null) {
            if (this.mFadeOutAnimator.isStarted()) {
                this.mFadeOutAnimator.cancel();
                return;
            }
            return;
        }
        setAlpha(1.0f);
        setVisibility(0);
        this.mWindowManager.addView(this, getLayoutParams(this.mWindowToken));
        announceForAccessibility(getContext().getString(R$string.inattentive_sleep_warning_message));
    }
}