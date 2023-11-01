package com.android.systemui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.ArrayList;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/AutoReinflateContainer.class */
public class AutoReinflateContainer extends FrameLayout implements ConfigurationController.ConfigurationListener {
    private final List<InflateListener> mInflateListeners;
    private final int mLayout;

    /* loaded from: mainsysui33.jar:com/android/systemui/AutoReinflateContainer$InflateListener.class */
    public interface InflateListener {
        void onInflated(View view);
    }

    public AutoReinflateContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mInflateListeners = new ArrayList();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.AutoReinflateContainer);
        int i = R$styleable.AutoReinflateContainer_android_layout;
        if (!obtainStyledAttributes.hasValue(i)) {
            throw new IllegalArgumentException("AutoReinflateContainer must contain a layout");
        }
        this.mLayout = obtainStyledAttributes.getResourceId(i, 0);
        obtainStyledAttributes.recycle();
        inflateLayout();
    }

    public void addInflateListener(InflateListener inflateListener) {
        this.mInflateListeners.add(inflateListener);
        inflateListener.onInflated(getChildAt(0));
    }

    public void inflateLayout() {
        removeAllViews();
        inflateLayoutImpl();
        int size = this.mInflateListeners.size();
        for (int i = 0; i < size; i++) {
            this.mInflateListeners.get(i).onInflated(getChildAt(0));
        }
    }

    public void inflateLayoutImpl() {
        LayoutInflater.from(getContext()).inflate(this.mLayout, this);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((ConfigurationController) Dependency.get(ConfigurationController.class)).addCallback(this);
    }

    public void onDensityOrFontScaleChanged() {
        inflateLayout();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((ConfigurationController) Dependency.get(ConfigurationController.class)).removeCallback(this);
    }

    public void onLocaleListChanged() {
        inflateLayout();
    }

    public void onThemeChanged() {
        inflateLayout();
    }

    public void onUiModeChanged() {
        inflateLayout();
    }
}