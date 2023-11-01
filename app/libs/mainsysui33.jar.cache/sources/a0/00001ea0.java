package com.android.systemui.navigationbar.buttons;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.android.systemui.navigationbar.buttons.ContextualButton;
import com.android.systemui.shared.rotation.RotationButton;
import com.android.systemui.shared.rotation.RotationButtonController;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/buttons/RotationContextButton.class */
public class RotationContextButton extends ContextualButton implements RotationButton {
    public RotationButtonController mRotationButtonController;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.buttons.RotationContextButton$$ExternalSyntheticLambda0.onVisibilityChanged(com.android.systemui.navigationbar.buttons.ContextualButton, boolean):void] */
    public static /* synthetic */ void $r8$lambda$HjirEcqfxRslE8fKkZ_Iuoysuxg(RotationButton.RotationButtonUpdatesCallback rotationButtonUpdatesCallback, ContextualButton contextualButton, boolean z) {
        lambda$setUpdatesCallback$0(rotationButtonUpdatesCallback, contextualButton, z);
    }

    public RotationContextButton(int i, Context context, int i2) {
        super(i, context, i2);
    }

    public static /* synthetic */ void lambda$setUpdatesCallback$0(RotationButton.RotationButtonUpdatesCallback rotationButtonUpdatesCallback, ContextualButton contextualButton, boolean z) {
        if (rotationButtonUpdatesCallback != null) {
            rotationButtonUpdatesCallback.onVisibilityChanged(z);
        }
    }

    public boolean acceptRotationProposal() {
        View currentView = getCurrentView();
        return currentView != null && currentView.isAttachedToWindow();
    }

    public /* bridge */ /* synthetic */ Drawable getImageDrawable() {
        return super.getImageDrawable();
    }

    @Override // com.android.systemui.navigationbar.buttons.ContextualButton
    public KeyButtonDrawable getNewDrawable(int i, int i2) {
        return KeyButtonDrawable.create(this.mRotationButtonController.getContext(), i, i2, this.mRotationButtonController.getIconResId(), false, null);
    }

    public void setRotationButtonController(RotationButtonController rotationButtonController) {
        this.mRotationButtonController = rotationButtonController;
    }

    public void setUpdatesCallback(final RotationButton.RotationButtonUpdatesCallback rotationButtonUpdatesCallback) {
        setListener(new ContextualButton.ContextButtonListener() { // from class: com.android.systemui.navigationbar.buttons.RotationContextButton$$ExternalSyntheticLambda0
            @Override // com.android.systemui.navigationbar.buttons.ContextualButton.ContextButtonListener
            public final void onVisibilityChanged(ContextualButton contextualButton, boolean z) {
                RotationContextButton.$r8$lambda$HjirEcqfxRslE8fKkZ_Iuoysuxg(rotationButtonUpdatesCallback, contextualButton, z);
            }
        });
    }

    @Override // com.android.systemui.navigationbar.buttons.ContextualButton, com.android.systemui.navigationbar.buttons.ButtonDispatcher
    public void setVisibility(int i) {
        super.setVisibility(i);
        KeyButtonDrawable imageDrawable = getImageDrawable();
        if (i != 0 || imageDrawable == null) {
            return;
        }
        imageDrawable.resetAnimation();
        imageDrawable.startAnimation();
    }
}