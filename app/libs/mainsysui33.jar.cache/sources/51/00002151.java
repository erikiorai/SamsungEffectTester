package com.android.systemui.qs;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.tileimpl.QSIconViewImpl;
import com.android.systemui.qs.tileimpl.SlashImageView;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/SignalTileView.class */
public class SignalTileView extends QSIconViewImpl {
    public static final long DEFAULT_DURATION;
    public static final long SHORT_DURATION;
    public FrameLayout mIconFrame;
    public ImageView mIn;
    public ImageView mOut;
    public ImageView mOverlay;
    public ImageView mSignal;
    public int mSignalIndicatorToIconFrameSpacing;
    public int mWideOverlayIconStartPadding;

    static {
        long duration = new ValueAnimator().getDuration();
        DEFAULT_DURATION = duration;
        SHORT_DURATION = duration / 3;
    }

    public SignalTileView(Context context) {
        super(context);
        this.mIn = addTrafficView(R$drawable.ic_qs_signal_in);
        this.mOut = addTrafficView(R$drawable.ic_qs_signal_out);
        setClipChildren(false);
        setClipToPadding(false);
        this.mWideOverlayIconStartPadding = context.getResources().getDimensionPixelSize(R$dimen.wide_type_icon_start_padding_qs);
        this.mSignalIndicatorToIconFrameSpacing = context.getResources().getDimensionPixelSize(R$dimen.signal_indicator_to_icon_frame_spacing);
    }

    public final ImageView addTrafficView(int i) {
        ImageView imageView = new ImageView(((ViewGroup) this).mContext);
        imageView.setImageResource(i);
        imageView.setAlpha(ActionBarShadowController.ELEVATION_LOW);
        addView(imageView);
        return imageView;
    }

    @Override // com.android.systemui.qs.tileimpl.QSIconViewImpl
    public View createIcon() {
        this.mIconFrame = new FrameLayout(((ViewGroup) this).mContext);
        SlashImageView createSlashImageView = createSlashImageView(((ViewGroup) this).mContext);
        this.mSignal = createSlashImageView;
        this.mIconFrame.addView(createSlashImageView);
        ImageView imageView = new ImageView(((ViewGroup) this).mContext);
        this.mOverlay = imageView;
        this.mIconFrame.addView(imageView, -2, -2);
        return this.mIconFrame;
    }

    public SlashImageView createSlashImageView(Context context) {
        return new SlashImageView(context);
    }

    @Override // com.android.systemui.qs.tileimpl.QSIconViewImpl
    public int getIconMeasureMode() {
        return Integer.MIN_VALUE;
    }

    public final void layoutIndicator(View view) {
        int right;
        int measuredWidth;
        boolean z = true;
        if (getLayoutDirection() != 1) {
            z = false;
        }
        if (z) {
            measuredWidth = getLeft() - this.mSignalIndicatorToIconFrameSpacing;
            right = measuredWidth - view.getMeasuredWidth();
        } else {
            right = this.mSignalIndicatorToIconFrameSpacing + getRight();
            measuredWidth = view.getMeasuredWidth() + right;
        }
        view.layout(right, this.mIconFrame.getBottom() - view.getMeasuredHeight(), measuredWidth, this.mIconFrame.getBottom());
    }

    @Override // com.android.systemui.qs.tileimpl.QSIconViewImpl, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        layoutIndicator(this.mIn);
        layoutIndicator(this.mOut);
    }

    @Override // com.android.systemui.qs.tileimpl.QSIconViewImpl, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mIconFrame.getMeasuredHeight(), 1073741824);
        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(this.mIconFrame.getMeasuredHeight(), Integer.MIN_VALUE);
        this.mIn.measure(makeMeasureSpec2, makeMeasureSpec);
        this.mOut.measure(makeMeasureSpec2, makeMeasureSpec);
    }

    @Override // com.android.systemui.qs.tileimpl.QSIconViewImpl, com.android.systemui.plugins.qs.QSIconView
    public void setIcon(QSTile.State state, boolean z) {
        QSTile.SignalState signalState = (QSTile.SignalState) state;
        setIcon(this.mSignal, signalState, z);
        if (signalState.overlayIconId > 0) {
            this.mOverlay.setVisibility(0);
            this.mOverlay.setImageResource(signalState.overlayIconId);
        } else {
            this.mOverlay.setVisibility(8);
        }
        if (signalState.overlayIconId <= 0 || !signalState.isOverlayIconWide) {
            this.mSignal.setPaddingRelative(0, 0, 0, 0);
        } else {
            this.mSignal.setPaddingRelative(this.mWideOverlayIconStartPadding, 0, 0, 0);
        }
        if (z) {
            isShown();
        }
    }
}