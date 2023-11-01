package com.android.keyguard;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.net.Uri;
import android.os.Trace;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.slice.SliceItem;
import androidx.slice.core.SliceQuery;
import androidx.slice.widget.RowContent;
import androidx.slice.widget.SliceContent;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.graphics.ColorUtils;
import com.android.settingslib.Utils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$attr;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$style;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.keyguard.KeyguardSliceProvider;
import com.android.systemui.util.wakelock.KeepAwakeAnimationListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSliceView.class */
public class KeyguardSliceView extends LinearLayout {
    public Runnable mContentChangeListener;
    public float mDarkAmount;
    public boolean mHasHeader;
    public int mIconSize;
    public int mIconSizeWithHeader;
    public final LayoutTransition mLayoutTransition;
    public View.OnClickListener mOnClickListener;
    public Row mRow;
    public int mTextColor;
    @VisibleForTesting
    public TextView mTitle;

    @VisibleForTesting
    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSliceView$KeyguardSliceTextView.class */
    public static class KeyguardSliceTextView extends TextView {
        public static int sStyleId = R$style.TextAppearance_Keyguard_Secondary;

        public KeyguardSliceTextView(Context context) {
            super(context, null, 0, sStyleId);
            onDensityOrFontScaleChanged();
            setEllipsize(TextUtils.TruncateAt.END);
        }

        public void onDensityOrFontScaleChanged() {
            updatePadding();
        }

        public void onOverlayChanged() {
            setTextAppearance(sStyleId);
        }

        @Override // android.widget.TextView
        public void setCompoundDrawablesRelative(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
            super.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
            updateDrawableColors();
            updatePadding();
        }

        @Override // android.widget.TextView
        public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
            super.setText(charSequence, bufferType);
            updatePadding();
        }

        @Override // android.widget.TextView
        public void setTextColor(int i) {
            super.setTextColor(i);
            updateDrawableColors();
        }

        public final void updateDrawableColors() {
            Drawable[] compoundDrawables;
            int currentTextColor = getCurrentTextColor();
            for (Drawable drawable : getCompoundDrawables()) {
                if (drawable != null) {
                    drawable.setTint(currentTextColor);
                }
            }
        }

        public final void updatePadding() {
            boolean isEmpty = TextUtils.isEmpty(getText());
            boolean equals = Uri.parse(KeyguardSliceProvider.KEYGUARD_DATE_URI).equals(getTag());
            int dimension = ((int) getContext().getResources().getDimension(R$dimen.widget_horizontal_padding)) / 2;
            int dimension2 = (int) ((TextView) this).mContext.getResources().getDimension(R$dimen.widget_icon_padding);
            setPadding(!equals ? dimension2 : 0, dimension, 0, isEmpty ^ true ? dimension : 0);
            setCompoundDrawablePadding(dimension2);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSliceView$Row.class */
    public static class Row extends LinearLayout {
        public float mDarkAmount;
        public final Animation.AnimationListener mKeepAwakeListener;
        public Set<KeyguardSliceTextView> mKeyguardSliceTextViewSet;
        public LayoutTransition mLayoutTransition;

        public Row(Context context) {
            this(context, null);
        }

        public Row(Context context, AttributeSet attributeSet) {
            this(context, attributeSet, 0);
        }

        public Row(Context context, AttributeSet attributeSet, int i) {
            this(context, attributeSet, i, 0);
        }

        public Row(Context context, AttributeSet attributeSet, int i, int i2) {
            super(context, attributeSet, i, i2);
            this.mKeyguardSliceTextViewSet = new HashSet();
            this.mKeepAwakeListener = new KeepAwakeAnimationListener(((LinearLayout) this).mContext);
        }

        @Override // android.view.ViewGroup
        public void addView(View view, int i) {
            super.addView(view, i);
            if (view instanceof KeyguardSliceTextView) {
                this.mKeyguardSliceTextViewSet.add((KeyguardSliceTextView) view);
            }
        }

        @Override // android.view.View
        public boolean hasOverlappingRendering() {
            return false;
        }

        @Override // android.view.View
        public void onFinishInflate() {
            LayoutTransition layoutTransition = new LayoutTransition();
            this.mLayoutTransition = layoutTransition;
            layoutTransition.setDuration(550L);
            ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(null, PropertyValuesHolder.ofInt("left", 0, 1), PropertyValuesHolder.ofInt("right", 0, 1));
            this.mLayoutTransition.setAnimator(0, ofPropertyValuesHolder);
            this.mLayoutTransition.setAnimator(1, ofPropertyValuesHolder);
            LayoutTransition layoutTransition2 = this.mLayoutTransition;
            Interpolator interpolator = Interpolators.ACCELERATE_DECELERATE;
            layoutTransition2.setInterpolator(0, interpolator);
            this.mLayoutTransition.setInterpolator(1, interpolator);
            this.mLayoutTransition.setStartDelay(0, 550L);
            this.mLayoutTransition.setStartDelay(1, 550L);
            this.mLayoutTransition.setAnimator(2, ObjectAnimator.ofFloat((Object) null, "alpha", ActionBarShadowController.ELEVATION_LOW, 1.0f));
            this.mLayoutTransition.setInterpolator(2, Interpolators.ALPHA_IN);
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat((Object) null, "alpha", 1.0f, ActionBarShadowController.ELEVATION_LOW);
            this.mLayoutTransition.setInterpolator(3, Interpolators.ALPHA_OUT);
            this.mLayoutTransition.setDuration(3, 137L);
            this.mLayoutTransition.setAnimator(3, ofFloat);
            this.mLayoutTransition.setAnimateParentHierarchy(false);
        }

        @Override // android.widget.LinearLayout, android.view.View
        public void onMeasure(int i, int i2) {
            View.MeasureSpec.getSize(i);
            int childCount = getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = getChildAt(i3);
                if (childAt instanceof KeyguardSliceTextView) {
                    ((KeyguardSliceTextView) childAt).setMaxWidth(Integer.MAX_VALUE);
                }
            }
            super.onMeasure(i, i2);
        }

        @Override // android.view.View
        public void onVisibilityAggregated(boolean z) {
            super.onVisibilityAggregated(z);
            setLayoutTransition(z ? this.mLayoutTransition : null);
        }

        @Override // android.view.ViewGroup, android.view.ViewManager
        public void removeView(View view) {
            super.removeView(view);
            if (view instanceof KeyguardSliceTextView) {
                this.mKeyguardSliceTextViewSet.remove((KeyguardSliceTextView) view);
            }
        }

        public void setDarkAmount(float f) {
            boolean z = true;
            boolean z2 = f != ActionBarShadowController.ELEVATION_LOW;
            if (this.mDarkAmount == ActionBarShadowController.ELEVATION_LOW) {
                z = false;
            }
            if (z2 == z) {
                return;
            }
            this.mDarkAmount = f;
            setLayoutAnimationListener(z2 ? null : this.mKeepAwakeListener);
        }
    }

    public KeyguardSliceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDarkAmount = ActionBarShadowController.ELEVATION_LOW;
        context.getResources();
        LayoutTransition layoutTransition = new LayoutTransition();
        this.mLayoutTransition = layoutTransition;
        layoutTransition.setStagger(0, 275L);
        layoutTransition.setDuration(2, 550L);
        layoutTransition.setDuration(3, 275L);
        layoutTransition.disableTransitionType(0);
        layoutTransition.disableTransitionType(1);
        layoutTransition.setInterpolator(2, Interpolators.FAST_OUT_SLOW_IN);
        layoutTransition.setInterpolator(3, Interpolators.ALPHA_OUT);
        layoutTransition.setAnimateParentHierarchy(false);
    }

    @VisibleForTesting
    public int getTextColor() {
        return ColorUtils.blendARGB(this.mTextColor, -1, this.mDarkAmount);
    }

    public void hideSlice() {
        this.mTitle.setVisibility(8);
        this.mRow.setVisibility(8);
        this.mHasHeader = false;
        Runnable runnable = this.mContentChangeListener;
        if (runnable != null) {
            runnable.run();
        }
    }

    public void onDensityOrFontScaleChanged() {
        this.mIconSize = ((LinearLayout) this).mContext.getResources().getDimensionPixelSize(R$dimen.widget_icon_size);
        this.mIconSizeWithHeader = (int) ((LinearLayout) this).mContext.getResources().getDimension(R$dimen.header_icon_size);
        for (int i = 0; i < this.mRow.getChildCount(); i++) {
            View childAt = this.mRow.getChildAt(i);
            if (childAt instanceof KeyguardSliceTextView) {
                ((KeyguardSliceTextView) childAt).onDensityOrFontScaleChanged();
            }
        }
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mTitle = (TextView) findViewById(R$id.title);
        this.mRow = (Row) findViewById(R$id.row);
        this.mTextColor = Utils.getColorAttrDefaultColor(((LinearLayout) this).mContext, R$attr.wallpaperTextColor);
        this.mIconSize = (int) ((LinearLayout) this).mContext.getResources().getDimension(R$dimen.widget_icon_size);
        this.mIconSizeWithHeader = (int) ((LinearLayout) this).mContext.getResources().getDimension(R$dimen.header_icon_size);
        this.mTitle.setBreakStrategy(2);
    }

    public void onOverlayChanged() {
        for (int i = 0; i < this.mRow.getChildCount(); i++) {
            View childAt = this.mRow.getChildAt(i);
            if (childAt instanceof KeyguardSliceTextView) {
                ((KeyguardSliceTextView) childAt).onOverlayChanged();
            }
        }
    }

    @Override // android.view.View
    public void onVisibilityAggregated(boolean z) {
        super.onVisibilityAggregated(z);
        setLayoutTransition(z ? this.mLayoutTransition : null);
    }

    public void setDarkAmount(float f) {
        this.mDarkAmount = f;
        this.mRow.setDarkAmount(f);
        updateTextColors();
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
        this.mTitle.setOnClickListener(onClickListener);
    }

    @VisibleForTesting
    public void setTextColor(int i) {
        this.mTextColor = i;
        updateTextColors();
    }

    public Map<View, PendingIntent> showSlice(RowContent rowContent, List<SliceContent> list) {
        int i;
        Drawable drawable;
        Trace.beginSection("KeyguardSliceView#showSlice");
        this.mHasHeader = rowContent != null;
        HashMap hashMap = new HashMap();
        int i2 = 8;
        if (this.mHasHeader) {
            this.mTitle.setVisibility(0);
            SliceItem titleItem = rowContent.getTitleItem();
            this.mTitle.setText(titleItem != null ? titleItem.getText() : null);
            if (rowContent.getPrimaryAction() != null && rowContent.getPrimaryAction().getAction() != null) {
                hashMap.put(this.mTitle, rowContent.getPrimaryAction().getAction());
            }
        } else {
            this.mTitle.setVisibility(8);
        }
        int size = list.size();
        int textColor = getTextColor();
        int i3 = this.mHasHeader ? 1 : 0;
        Row row = this.mRow;
        if (size > 0) {
            i2 = 0;
        }
        row.setVisibility(i2);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mRow.getLayoutParams();
        layoutParams.gravity = 8388611;
        this.mRow.setLayoutParams(layoutParams);
        while (true) {
            i = 0;
            if (i3 >= size) {
                break;
            }
            RowContent rowContent2 = (RowContent) list.get(i3);
            SliceItem sliceItem = rowContent2.getSliceItem();
            Uri uri = sliceItem.getSlice().getUri();
            KeyguardSliceTextView keyguardSliceTextView = (KeyguardSliceTextView) this.mRow.findViewWithTag(uri);
            KeyguardSliceTextView keyguardSliceTextView2 = keyguardSliceTextView;
            if (keyguardSliceTextView == null) {
                keyguardSliceTextView2 = new KeyguardSliceTextView(((LinearLayout) this).mContext);
                keyguardSliceTextView2.setTextColor(textColor);
                keyguardSliceTextView2.setTag(uri);
                this.mRow.addView(keyguardSliceTextView2, i3 - (this.mHasHeader ? 1 : 0));
            }
            PendingIntent action = rowContent2.getPrimaryAction() != null ? rowContent2.getPrimaryAction().getAction() : null;
            hashMap.put(keyguardSliceTextView2, action);
            SliceItem titleItem2 = rowContent2.getTitleItem();
            keyguardSliceTextView2.setText(titleItem2 == null ? null : titleItem2.getText());
            keyguardSliceTextView2.setContentDescription(rowContent2.getContentDescription());
            SliceItem find = SliceQuery.find(sliceItem.getSlice(), "image");
            if (find != null) {
                int i4 = this.mHasHeader ? this.mIconSizeWithHeader : this.mIconSize;
                Drawable loadDrawable = find.getIcon().loadDrawable(((LinearLayout) this).mContext);
                drawable = loadDrawable;
                if (loadDrawable != null) {
                    drawable = loadDrawable;
                    if (loadDrawable instanceof InsetDrawable) {
                        drawable = ((InsetDrawable) loadDrawable).getDrawable();
                    }
                    drawable.setBounds(0, 0, Math.max((int) ((drawable.getIntrinsicWidth() / drawable.getIntrinsicHeight()) * i4), 1), i4);
                }
            } else {
                drawable = null;
            }
            keyguardSliceTextView2.setCompoundDrawablesRelative(drawable, null, null, null);
            keyguardSliceTextView2.setOnClickListener(this.mOnClickListener);
            keyguardSliceTextView2.setClickable(action != null);
            i3++;
        }
        while (i < this.mRow.getChildCount()) {
            View childAt = this.mRow.getChildAt(i);
            int i5 = i;
            if (!hashMap.containsKey(childAt)) {
                this.mRow.removeView(childAt);
                i5 = i - 1;
            }
            i = i5 + 1;
        }
        Runnable runnable = this.mContentChangeListener;
        if (runnable != null) {
            runnable.run();
        }
        Trace.endSection();
        return hashMap;
    }

    public final void updateTextColors() {
        int textColor = getTextColor();
        this.mTitle.setTextColor(textColor);
        int childCount = this.mRow.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.mRow.getChildAt(i);
            if (childAt instanceof TextView) {
                ((TextView) childAt).setTextColor(textColor);
            }
        }
    }
}