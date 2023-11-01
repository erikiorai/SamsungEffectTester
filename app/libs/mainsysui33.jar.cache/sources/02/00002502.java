package com.android.systemui.scrim;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.graphics.ColorUtils;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.systemui.screenshot.SaveImageInBackgroundTask$$ExternalSyntheticLambda0;
import java.util.concurrent.Executor;

/* loaded from: mainsysui33.jar:com/android/systemui/scrim/ScrimView.class */
public class ScrimView extends View {
    public boolean mBlendWithMainColor;
    public Runnable mChangeRunnable;
    public Executor mChangeRunnableExecutor;
    public PorterDuffColorFilter mColorFilter;
    public final Object mColorLock;
    @GuardedBy({"mColorLock"})
    public final ColorExtractor.GradientColors mColors;
    public Drawable mDrawable;
    public Rect mDrawableBounds;
    public Executor mExecutor;
    public Looper mExecutorLooper;
    public int mTintColor;
    public final ColorExtractor.GradientColors mTmpColors;
    public float mViewAlpha;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda3.run():void] */
    public static /* synthetic */ void $r8$lambda$0hg1lN64kAlaRiHts8Cd8Pck26U(ScrimView scrimView, ColorExtractor.GradientColors gradientColors, boolean z) {
        scrimView.lambda$setColors$3(gradientColors, z);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$9tecKDmYQDIQrA495jylsEtksng(ScrimView scrimView) {
        scrimView.lambda$new$0();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda4.run():void] */
    /* renamed from: $r8$lambda$9y86aufUHnomsZcZTsxBAH-COfA */
    public static /* synthetic */ void m4346$r8$lambda$9y86aufUHnomsZcZTsxBAHCOfA(ScrimView scrimView, float f) {
        scrimView.lambda$setViewAlpha$5(f);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda1.run():void] */
    /* renamed from: $r8$lambda$HBabdK6y2c2HT3mbrXuC-nxzVDA */
    public static /* synthetic */ void m4347$r8$lambda$HBabdK6y2c2HT3mbrXuCnxzVDA(ScrimView scrimView, Drawable drawable) {
        scrimView.lambda$setDrawable$1(drawable);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda2.run():void] */
    public static /* synthetic */ void $r8$lambda$l1DylaPD_MWF2Cf28dTAo5koSm0(ScrimView scrimView, boolean z) {
        scrimView.lambda$setClickable$2(z);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda5.run():void] */
    public static /* synthetic */ void $r8$lambda$lpuNPyn69nO_rwMPfDLtfvzEjbU(ScrimView scrimView, int i, boolean z) {
        scrimView.lambda$setTint$4(i, z);
    }

    public ScrimView(Context context) {
        this(context, null);
    }

    public ScrimView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ScrimView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ScrimView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mColorLock = new Object();
        this.mTmpColors = new ColorExtractor.GradientColors();
        this.mViewAlpha = 1.0f;
        this.mBlendWithMainColor = true;
        ScrimDrawable scrimDrawable = new ScrimDrawable();
        this.mDrawable = scrimDrawable;
        scrimDrawable.setCallback(this);
        this.mColors = new ColorExtractor.GradientColors();
        this.mExecutorLooper = Looper.myLooper();
        this.mExecutor = new SaveImageInBackgroundTask$$ExternalSyntheticLambda0();
        executeOnExecutor(new Runnable() { // from class: com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ScrimView.$r8$lambda$9tecKDmYQDIQrA495jylsEtksng(ScrimView.this);
            }
        });
    }

    public /* synthetic */ void lambda$new$0() {
        updateColorWithTint(false);
    }

    public /* synthetic */ void lambda$setClickable$2(boolean z) {
        super.setClickable(z);
    }

    public /* synthetic */ void lambda$setColors$3(ColorExtractor.GradientColors gradientColors, boolean z) {
        synchronized (this.mColorLock) {
            if (this.mColors.equals(gradientColors)) {
                return;
            }
            this.mColors.set(gradientColors);
            updateColorWithTint(z);
        }
    }

    public /* synthetic */ void lambda$setDrawable$1(Drawable drawable) {
        this.mDrawable = drawable;
        drawable.setCallback(this);
        this.mDrawable.setBounds(getLeft(), getTop(), getRight(), getBottom());
        this.mDrawable.setAlpha((int) (this.mViewAlpha * 255.0f));
        invalidate();
    }

    public /* synthetic */ void lambda$setTint$4(int i, boolean z) {
        if (this.mTintColor == i) {
            return;
        }
        this.mTintColor = i;
        updateColorWithTint(z);
    }

    public /* synthetic */ void lambda$setViewAlpha$5(float f) {
        if (f != this.mViewAlpha) {
            this.mViewAlpha = f;
            this.mDrawable.setAlpha((int) (f * 255.0f));
            Runnable runnable = this.mChangeRunnable;
            if (runnable != null) {
                this.mChangeRunnableExecutor.execute(runnable);
            }
        }
    }

    public boolean canReceivePointerEvents() {
        return false;
    }

    public void enableBottomEdgeConcave(boolean z) {
        Drawable drawable = this.mDrawable;
        if (drawable instanceof ScrimDrawable) {
            ((ScrimDrawable) drawable).setBottomEdgeConcave(z);
        }
    }

    public void enableRoundedCorners(boolean z) {
        Drawable drawable = this.mDrawable;
        if (drawable instanceof ScrimDrawable) {
            ((ScrimDrawable) drawable).setRoundedCornersEnabled(z);
        }
    }

    public final void executeOnExecutor(Runnable runnable) {
        if (this.mExecutor == null || Looper.myLooper() == this.mExecutorLooper) {
            runnable.run();
        } else {
            this.mExecutor.execute(runnable);
        }
    }

    public ColorExtractor.GradientColors getColors() {
        synchronized (this.mColorLock) {
            this.mTmpColors.set(this.mColors);
        }
        return this.mTmpColors;
    }

    @VisibleForTesting
    public Drawable getDrawable() {
        return this.mDrawable;
    }

    public int getTint() {
        return this.mTintColor;
    }

    public float getViewAlpha() {
        return this.mViewAlpha;
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    @Override // android.view.View, android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        super.invalidateDrawable(drawable);
        if (drawable == this.mDrawable) {
            invalidate();
        }
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        if (this.mDrawable.getAlpha() > 0) {
            this.mDrawable.draw(canvas);
        }
    }

    @Override // android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        Rect rect = this.mDrawableBounds;
        if (rect != null) {
            this.mDrawable.setBounds(rect);
        } else if (z) {
            this.mDrawable.setBounds(i, i2, i3, i4);
            invalidate();
        }
    }

    public void setBlendWithMainColor(boolean z) {
        this.mBlendWithMainColor = z;
    }

    public void setBottomEdgePosition(int i) {
        Drawable drawable = this.mDrawable;
        if (drawable instanceof ScrimDrawable) {
            ((ScrimDrawable) drawable).setBottomEdgePosition(i);
        }
    }

    public void setChangeRunnable(Runnable runnable, Executor executor) {
        this.mChangeRunnable = runnable;
        this.mChangeRunnableExecutor = executor;
    }

    @Override // android.view.View
    public void setClickable(final boolean z) {
        executeOnExecutor(new Runnable() { // from class: com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                ScrimView.$r8$lambda$l1DylaPD_MWF2Cf28dTAo5koSm0(ScrimView.this, z);
            }
        });
    }

    public void setColors(final ColorExtractor.GradientColors gradientColors, final boolean z) {
        if (gradientColors == null) {
            throw new IllegalArgumentException("Colors cannot be null");
        }
        executeOnExecutor(new Runnable() { // from class: com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                ScrimView.$r8$lambda$0hg1lN64kAlaRiHts8Cd8Pck26U(ScrimView.this, gradientColors, z);
            }
        });
    }

    public void setCornerRadius(int i) {
        Drawable drawable = this.mDrawable;
        if (drawable instanceof ScrimDrawable) {
            ((ScrimDrawable) drawable).setRoundedCorners(i);
        }
    }

    @VisibleForTesting
    public void setDrawable(final Drawable drawable) {
        executeOnExecutor(new Runnable() { // from class: com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ScrimView.m4347$r8$lambda$HBabdK6y2c2HT3mbrXuCnxzVDA(ScrimView.this, drawable);
            }
        });
    }

    public void setDrawableBounds(float f, float f2, float f3, float f4) {
        if (this.mDrawableBounds == null) {
            this.mDrawableBounds = new Rect();
        }
        this.mDrawableBounds.set((int) f, (int) f2, (int) f3, (int) f4);
        this.mDrawable.setBounds(this.mDrawableBounds);
    }

    public void setTint(int i) {
        setTint(i, false);
    }

    public void setTint(final int i, final boolean z) {
        executeOnExecutor(new Runnable() { // from class: com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                ScrimView.$r8$lambda$lpuNPyn69nO_rwMPfDLtfvzEjbU(ScrimView.this, i, z);
            }
        });
    }

    public void setViewAlpha(final float f) {
        if (!Float.isNaN(f)) {
            executeOnExecutor(new Runnable() { // from class: com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    ScrimView.m4346$r8$lambda$9y86aufUHnomsZcZTsxBAHCOfA(ScrimView.this, f);
                }
            });
            return;
        }
        throw new IllegalArgumentException("alpha cannot be NaN: " + f);
    }

    public final void updateColorWithTint(boolean z) {
        Drawable drawable = this.mDrawable;
        if (drawable instanceof ScrimDrawable) {
            ScrimDrawable scrimDrawable = (ScrimDrawable) drawable;
            float alpha = Color.alpha(this.mTintColor) / 255.0f;
            int i = this.mTintColor;
            if (this.mBlendWithMainColor) {
                i = ColorUtils.blendARGB(this.mColors.getMainColor(), this.mTintColor, alpha);
            }
            scrimDrawable.setColor(i, z);
        } else {
            if (Color.alpha(this.mTintColor) != 0) {
                PorterDuffColorFilter porterDuffColorFilter = this.mColorFilter;
                PorterDuff.Mode mode = porterDuffColorFilter == null ? PorterDuff.Mode.SRC_OVER : porterDuffColorFilter.getMode();
                PorterDuffColorFilter porterDuffColorFilter2 = this.mColorFilter;
                if (porterDuffColorFilter2 == null || porterDuffColorFilter2.getColor() != this.mTintColor) {
                    this.mColorFilter = new PorterDuffColorFilter(this.mTintColor, mode);
                }
            } else {
                this.mColorFilter = null;
            }
            this.mDrawable.setColorFilter(this.mColorFilter);
            this.mDrawable.invalidateSelf();
        }
        Runnable runnable = this.mChangeRunnable;
        if (runnable != null) {
            this.mChangeRunnableExecutor.execute(runnable);
        }
    }
}