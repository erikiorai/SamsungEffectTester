package com.android.systemui.biometrics;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.systemui.R$id;
import java.util.Objects;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsEnrollView.class */
public class UdfpsEnrollView extends UdfpsAnimationView {
    public final UdfpsEnrollDrawable mFingerprintDrawable;
    public final UdfpsEnrollProgressBarDrawable mFingerprintProgressDrawable;
    public ImageView mFingerprintProgressView;
    public ImageView mFingerprintView;
    public final Handler mHandler;
    public float mProgressBarRadius;
    public FrameLayout.LayoutParams mProgressParams;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsEnrollView$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$3fyEijjqCwXTZ9eGYlNR8uSTZys(UdfpsEnrollView udfpsEnrollView, int i, int i2) {
        udfpsEnrollView.lambda$onEnrollmentProgress$0(i, i2);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsEnrollView$$ExternalSyntheticLambda1.run():void] */
    public static /* synthetic */ void $r8$lambda$szBVW0G5dEJL0XyFac_l4BXD_Pc(UdfpsEnrollView udfpsEnrollView, int i, int i2) {
        udfpsEnrollView.lambda$onEnrollmentHelp$1(i, i2);
    }

    public UdfpsEnrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFingerprintDrawable = new UdfpsEnrollDrawable(((FrameLayout) this).mContext, attributeSet);
        this.mFingerprintProgressDrawable = new UdfpsEnrollProgressBarDrawable(context, attributeSet);
        this.mHandler = new Handler(Looper.getMainLooper());
    }

    public /* synthetic */ void lambda$onEnrollmentHelp$1(int i, int i2) {
        this.mFingerprintProgressDrawable.onEnrollmentHelp(i, i2);
    }

    public /* synthetic */ void lambda$onEnrollmentProgress$0(int i, int i2) {
        this.mFingerprintProgressDrawable.onEnrollmentProgress(i, i2);
        this.mFingerprintDrawable.onEnrollmentProgress(i, i2);
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationView
    public UdfpsDrawable getDrawable() {
        return this.mFingerprintDrawable;
    }

    public void onEnrollmentHelp(final int i, final int i2) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.biometrics.UdfpsEnrollView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                UdfpsEnrollView.$r8$lambda$szBVW0G5dEJL0XyFac_l4BXD_Pc(UdfpsEnrollView.this, i, i2);
            }
        });
    }

    public void onEnrollmentProgress(final int i, final int i2) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.biometrics.UdfpsEnrollView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                UdfpsEnrollView.$r8$lambda$3fyEijjqCwXTZ9eGYlNR8uSTZys(UdfpsEnrollView.this, i, i2);
            }
        });
    }

    @Override // android.view.View
    public void onFinishInflate() {
        this.mFingerprintView = (ImageView) findViewById(R$id.udfps_enroll_animation_fp_view);
        this.mFingerprintProgressView = (ImageView) findViewById(R$id.udfps_enroll_animation_fp_progress_view);
        this.mFingerprintView.setImageDrawable(this.mFingerprintDrawable);
        this.mFingerprintProgressView.setImageDrawable(this.mFingerprintProgressDrawable);
    }

    public void onLastStepAcquired() {
        Handler handler = this.mHandler;
        final UdfpsEnrollProgressBarDrawable udfpsEnrollProgressBarDrawable = this.mFingerprintProgressDrawable;
        Objects.requireNonNull(udfpsEnrollProgressBarDrawable);
        handler.post(new Runnable() { // from class: com.android.systemui.biometrics.UdfpsEnrollView$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                UdfpsEnrollProgressBarDrawable.this.onLastStepAcquired();
            }
        });
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationView
    public void onSensorRectUpdated(RectF rectF) {
        if (!this.mUseExpandedOverlay) {
            super.onSensorRectUpdated(rectF);
            return;
        }
        RectF boundsRelativeToView = getBoundsRelativeToView(rectF);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) (boundsRelativeToView.width() + (this.mProgressBarRadius * 2.0f)), (int) (boundsRelativeToView.height() + (this.mProgressBarRadius * 2.0f)));
        this.mProgressParams = layoutParams;
        float f = boundsRelativeToView.left;
        float f2 = this.mProgressBarRadius;
        layoutParams.setMargins((int) (f - f2), (int) (boundsRelativeToView.top - f2), (int) (boundsRelativeToView.right + f2), (int) (boundsRelativeToView.bottom + f2));
        this.mFingerprintProgressView.setLayoutParams(this.mProgressParams);
        super.onSensorRectUpdated(boundsRelativeToView);
    }

    public void setEnrollHelper(UdfpsEnrollHelper udfpsEnrollHelper) {
        this.mFingerprintDrawable.setEnrollHelper(udfpsEnrollHelper);
    }

    public void setProgressBarRadius(float f) {
        this.mProgressBarRadius = f;
    }

    public void updateSensorLocation(Rect rect) {
        View findViewById = findViewById(R$id.udfps_enroll_accessibility_view);
        ViewGroup.LayoutParams layoutParams = findViewById.getLayoutParams();
        layoutParams.width = rect.width();
        layoutParams.height = rect.height();
        findViewById.setLayoutParams(layoutParams);
        findViewById.requestLayout();
    }
}