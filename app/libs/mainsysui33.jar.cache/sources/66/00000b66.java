package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import com.android.internal.util.EmergencyAffordanceManager;
import com.android.internal.widget.LockPatternUtils;

/* loaded from: mainsysui33.jar:com/android/keyguard/EmergencyButton.class */
public class EmergencyButton extends Button {
    public int mDownX;
    public int mDownY;
    public final EmergencyAffordanceManager mEmergencyAffordanceManager;
    public final boolean mEnableEmergencyCallWhileSimLocked;
    public LockPatternUtils mLockPatternUtils;
    public boolean mLongPressWasDragged;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.EmergencyButton$$ExternalSyntheticLambda0.onLongClick(android.view.View):boolean] */
    /* renamed from: $r8$lambda$2aGPXZbJN74ysWEks-vXezI9mJQ */
    public static /* synthetic */ boolean m549$r8$lambda$2aGPXZbJN74ysWEksvXezI9mJQ(EmergencyButton emergencyButton, View view) {
        return emergencyButton.lambda$onFinishInflate$0(view);
    }

    public EmergencyButton(Context context) {
        this(context, null);
    }

    public EmergencyButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mEnableEmergencyCallWhileSimLocked = ((Button) this).mContext.getResources().getBoolean(17891666);
        this.mEmergencyAffordanceManager = new EmergencyAffordanceManager(context);
    }

    public /* synthetic */ boolean lambda$onFinishInflate$0(View view) {
        if (this.mLongPressWasDragged || !this.mEmergencyAffordanceManager.needsEmergencyAffordance()) {
            return false;
        }
        this.mEmergencyAffordanceManager.performEmergencyCall();
        return true;
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mLockPatternUtils = new LockPatternUtils(((Button) this).mContext);
        if (this.mEmergencyAffordanceManager.needsEmergencyAffordance()) {
            setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.keyguard.EmergencyButton$$ExternalSyntheticLambda0
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    return EmergencyButton.m549$r8$lambda$2aGPXZbJN74ysWEksvXezI9mJQ(EmergencyButton.this, view);
                }
            });
        }
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (motionEvent.getActionMasked() == 0) {
            this.mDownX = x;
            this.mDownY = y;
            this.mLongPressWasDragged = false;
        } else {
            int abs = Math.abs(x - this.mDownX);
            int abs2 = Math.abs(y - this.mDownY);
            int scaledTouchSlop = ViewConfiguration.get(((Button) this).mContext).getScaledTouchSlop();
            if (Math.abs(abs2) > scaledTouchSlop || Math.abs(abs) > scaledTouchSlop) {
                this.mLongPressWasDragged = true;
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.widget.TextView, android.view.View
    public boolean performLongClick() {
        return super.performLongClick();
    }

    public void updateEmergencyCallButton(boolean z, boolean z2, boolean z3) {
        if (!(z2 ? z ? true : z3 ? this.mEnableEmergencyCallWhileSimLocked : this.mLockPatternUtils.isSecure(KeyguardUpdateMonitor.getCurrentUser()) : false)) {
            setVisibility(8);
            return;
        }
        setVisibility(0);
        setText(z ? 17040665 : 17040638);
    }
}