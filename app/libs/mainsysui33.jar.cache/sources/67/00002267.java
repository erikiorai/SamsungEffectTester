package com.android.systemui.qs.tileimpl;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.android.settingslib.Utils;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.plugins.qs.QSIconView;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.AlphaControlledSignalTileView;
import java.util.Objects;
import java.util.function.Supplier;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tileimpl/QSIconViewImpl.class */
public class QSIconViewImpl extends QSIconView {
    public boolean mAnimationEnabled;
    public ValueAnimator mColorAnimator;
    public boolean mDisabledByPolicy;
    public final View mIcon;
    public int mIconSizePx;
    public QSTile.Icon mLastIcon;
    public int mState;
    public int mTint;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tileimpl/QSIconViewImpl$EndRunnableAnimatorListener.class */
    public static class EndRunnableAnimatorListener extends AnimatorListenerAdapter {
        public Runnable mRunnable;

        public EndRunnableAnimatorListener(Runnable runnable) {
            this.mRunnable = runnable;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            super.onAnimationCancel(animator);
            this.mRunnable.run();
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            super.onAnimationEnd(animator);
            this.mRunnable.run();
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tileimpl.QSIconViewImpl$$ExternalSyntheticLambda1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    /* renamed from: $r8$lambda$5aLd-TgnaqvToN08Hj40W-Ny3gg */
    public static /* synthetic */ void m3960$r8$lambda$5aLdTgnaqvToN08Hj40WNy3gg(QSIconViewImpl qSIconViewImpl, ImageView imageView, ValueAnimator valueAnimator) {
        qSIconViewImpl.lambda$animateGrayScale$1(imageView, valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tileimpl.QSIconViewImpl$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$HLhvSGMUP5859HHRkIdnzBxtjuE(QSIconViewImpl qSIconViewImpl, ImageView imageView, QSTile.State state, boolean z) {
        qSIconViewImpl.lambda$setIcon$0(imageView, state, z);
    }

    public QSIconViewImpl(Context context) {
        super(context);
        this.mAnimationEnabled = true;
        this.mState = -1;
        this.mDisabledByPolicy = false;
        this.mColorAnimator = new ValueAnimator();
        this.mIconSizePx = context.getResources().getDimensionPixelSize(R$dimen.qs_icon_size);
        View createIcon = createIcon();
        this.mIcon = createIcon;
        addView(createIcon);
        this.mColorAnimator.setDuration(350L);
    }

    public static int getIconColorForState(Context context, QSTile.State state) {
        int i;
        if (state.disabledByPolicy || (i = state.state) == 0) {
            return Utils.getColorAttrDefaultColor(context, 16843282);
        }
        if (i == 1) {
            return Utils.getColorAttrDefaultColor(context, 16842806);
        }
        if (i == 2) {
            return Utils.getColorAttrDefaultColor(context, 17957107);
        }
        Log.e("QSIconView", "Invalid state " + state);
        return 0;
    }

    public /* synthetic */ void lambda$animateGrayScale$1(ImageView imageView, ValueAnimator valueAnimator) {
        setTint(imageView, ((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    public final void animateGrayScale(int i, int i2, final ImageView imageView, Runnable runnable) {
        if (imageView instanceof AlphaControlledSignalTileView.AlphaControlledSlashImageView) {
            ((AlphaControlledSignalTileView.AlphaControlledSlashImageView) imageView).setFinalImageTintList(ColorStateList.valueOf(i2));
        }
        this.mColorAnimator.cancel();
        if (!this.mAnimationEnabled || !ValueAnimator.areAnimatorsEnabled()) {
            setTint(imageView, i2);
            runnable.run();
            return;
        }
        PropertyValuesHolder ofInt = PropertyValuesHolder.ofInt("color", i, i2);
        ofInt.setEvaluator(ArgbEvaluator.getInstance());
        this.mColorAnimator.setValues(ofInt);
        this.mColorAnimator.removeAllListeners();
        this.mColorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.qs.tileimpl.QSIconViewImpl$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                QSIconViewImpl.m3960$r8$lambda$5aLdTgnaqvToN08Hj40WNy3gg(QSIconViewImpl.this, imageView, valueAnimator);
            }
        });
        this.mColorAnimator.addListener(new EndRunnableAnimatorListener(runnable));
        this.mColorAnimator.start();
    }

    public View createIcon() {
        SlashImageView slashImageView = new SlashImageView(((ViewGroup) this).mContext);
        slashImageView.setId(16908294);
        slashImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        return slashImageView;
    }

    @Override // com.android.systemui.plugins.qs.QSIconView
    public void disableAnimation() {
        this.mAnimationEnabled = false;
    }

    public final int exactly(int i) {
        return View.MeasureSpec.makeMeasureSpec(i, 1073741824);
    }

    public int getColor(QSTile.State state) {
        return getIconColorForState(getContext(), state);
    }

    public int getIconMeasureMode() {
        return 1073741824;
    }

    @Override // com.android.systemui.plugins.qs.QSIconView
    public View getIconView() {
        return this.mIcon;
    }

    public final void layout(View view, int i, int i2) {
        view.layout(i, i2, view.getMeasuredWidth() + i, view.getMeasuredHeight() + i2);
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mIconSizePx = getContext().getResources().getDimensionPixelSize(R$dimen.qs_icon_size);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        layout(this.mIcon, (getMeasuredWidth() - this.mIcon.getMeasuredWidth()) / 2, 0);
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i);
        this.mIcon.measure(View.MeasureSpec.makeMeasureSpec(size, getIconMeasureMode()), exactly(this.mIconSizePx));
        setMeasuredDimension(size, this.mIcon.getMeasuredHeight());
    }

    public void setIcon(final ImageView imageView, final QSTile.State state, final boolean z) {
        if (state.state == this.mState && state.disabledByPolicy == this.mDisabledByPolicy) {
            lambda$setIcon$0(imageView, state, z);
            return;
        }
        int color = getColor(state);
        this.mState = state.state;
        this.mDisabledByPolicy = state.disabledByPolicy;
        if (this.mTint != 0 && z && shouldAnimate(imageView)) {
            animateGrayScale(this.mTint, color, imageView, new Runnable() { // from class: com.android.systemui.qs.tileimpl.QSIconViewImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    QSIconViewImpl.$r8$lambda$HLhvSGMUP5859HHRkIdnzBxtjuE(QSIconViewImpl.this, imageView, state, z);
                }
            });
            return;
        }
        if (imageView instanceof AlphaControlledSignalTileView.AlphaControlledSlashImageView) {
            ((AlphaControlledSignalTileView.AlphaControlledSlashImageView) imageView).setFinalImageTintList(ColorStateList.valueOf(color));
        } else {
            setTint(imageView, color);
        }
        lambda$setIcon$0(imageView, state, z);
    }

    @Override // com.android.systemui.plugins.qs.QSIconView
    public void setIcon(QSTile.State state, boolean z) {
        setIcon((ImageView) this.mIcon, state, z);
    }

    public void setTint(ImageView imageView, int i) {
        imageView.setImageTintList(ColorStateList.valueOf(i));
        this.mTint = i;
    }

    public final boolean shouldAnimate(ImageView imageView) {
        return this.mAnimationEnabled && imageView.isShown() && imageView.getDrawable() != null;
    }

    @Override // android.view.View
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append('[');
        sb.append("state=" + this.mState);
        sb.append(", tint=" + this.mTint);
        if (this.mLastIcon != null) {
            sb.append(", lastIcon=" + this.mLastIcon.toString());
        }
        sb.append("]");
        return sb.toString();
    }

    /* renamed from: updateIcon */
    public void lambda$setIcon$0(ImageView imageView, QSTile.State state, boolean z) {
        Supplier<QSTile.Icon> supplier = state.iconSupplier;
        QSTile.Icon icon = supplier != null ? supplier.get() : state.icon;
        int i = R$id.qs_icon_tag;
        if (Objects.equals(icon, imageView.getTag(i)) && Objects.equals(state.slash, imageView.getTag(R$id.qs_slash_tag))) {
            return;
        }
        boolean z2 = z && shouldAnimate(imageView);
        this.mLastIcon = icon;
        Drawable drawable = icon != null ? z2 ? icon.getDrawable(((ViewGroup) this).mContext) : icon.getInvisibleDrawable(((ViewGroup) this).mContext) : null;
        int padding = icon != null ? icon.getPadding() : 0;
        Drawable drawable2 = drawable;
        if (drawable != null) {
            drawable2 = drawable;
            if (drawable.getConstantState() != null) {
                drawable2 = drawable.getConstantState().newDrawable();
            }
            drawable2.setAutoMirrored(false);
            drawable2.setLayoutDirection(getLayoutDirection());
        }
        if (imageView instanceof SlashImageView) {
            SlashImageView slashImageView = (SlashImageView) imageView;
            slashImageView.setAnimationEnabled(z2);
            slashImageView.setState(null, drawable2);
        } else {
            imageView.setImageDrawable(drawable2);
        }
        imageView.setTag(i, icon);
        imageView.setTag(R$id.qs_slash_tag, state.slash);
        imageView.setPadding(0, padding, 0, padding);
        if (drawable2 instanceof Animatable2) {
            final Animatable2 animatable2 = (Animatable2) drawable2;
            animatable2.start();
            if (!z2) {
                animatable2.stop();
            } else if (state.isTransient) {
                animatable2.registerAnimationCallback(new Animatable2.AnimationCallback() { // from class: com.android.systemui.qs.tileimpl.QSIconViewImpl.1
                    {
                        QSIconViewImpl.this = this;
                    }

                    @Override // android.graphics.drawable.Animatable2.AnimationCallback
                    public void onAnimationEnd(Drawable drawable3) {
                        animatable2.start();
                    }
                });
            }
        }
    }
}