package com.android.systemui.navigationbar.buttons;

import android.content.Context;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/buttons/ContextualButton.class */
public class ContextualButton extends ButtonDispatcher {
    public ContextualButtonGroup mGroup;
    public final int mIconResId;
    public final Context mLightContext;
    public ContextButtonListener mListener;

    /* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/buttons/ContextualButton$ContextButtonListener.class */
    public interface ContextButtonListener {
        void onVisibilityChanged(ContextualButton contextualButton, boolean z);
    }

    public ContextualButton(int i, Context context, int i2) {
        super(i);
        this.mLightContext = context;
        this.mIconResId = i2;
    }

    public void attachToGroup(ContextualButtonGroup contextualButtonGroup) {
        this.mGroup = contextualButtonGroup;
    }

    public KeyButtonDrawable getNewDrawable(int i, int i2) {
        return KeyButtonDrawable.create(this.mLightContext, i, i2, this.mIconResId, false, null);
    }

    public boolean hide() {
        ContextualButtonGroup contextualButtonGroup = this.mGroup;
        boolean z = false;
        if (contextualButtonGroup == null) {
            setVisibility(4);
            return false;
        }
        if (contextualButtonGroup.setButtonVisibility(getId(), false) != 0) {
            z = true;
        }
        return z;
    }

    public void setListener(ContextButtonListener contextButtonListener) {
        this.mListener = contextButtonListener;
    }

    @Override // com.android.systemui.navigationbar.buttons.ButtonDispatcher
    public void setVisibility(int i) {
        super.setVisibility(i);
        KeyButtonDrawable imageDrawable = getImageDrawable();
        if (i != 0 && imageDrawable != null && imageDrawable.canAnimate()) {
            imageDrawable.clearAnimationCallbacks();
            imageDrawable.resetAnimation();
        }
        ContextButtonListener contextButtonListener = this.mListener;
        if (contextButtonListener != null) {
            contextButtonListener.onVisibilityChanged(this, i == 0);
        }
    }

    public boolean show() {
        ContextualButtonGroup contextualButtonGroup = this.mGroup;
        boolean z = false;
        if (contextualButtonGroup == null) {
            setVisibility(0);
            return true;
        }
        if (contextualButtonGroup.setButtonVisibility(getId(), true) == 0) {
            z = true;
        }
        return z;
    }

    public void updateIcon(int i, int i2) {
        if (this.mIconResId == 0) {
            return;
        }
        KeyButtonDrawable imageDrawable = getImageDrawable();
        KeyButtonDrawable newDrawable = getNewDrawable(i, i2);
        if (imageDrawable != null) {
            newDrawable.setDarkIntensity(imageDrawable.getDarkIntensity());
        }
        setImageDrawable(newDrawable);
    }
}