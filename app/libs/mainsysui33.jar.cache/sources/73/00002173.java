package com.android.systemui.qs.customize;

import android.content.Context;
import android.text.TextUtils;
import com.android.systemui.plugins.qs.QSIconView;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.tileimpl.QSTileViewImpl;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/customize/CustomizeTileView.class */
public final class CustomizeTileView extends QSTileViewImpl {
    public boolean showAppLabel;
    public boolean showSideView;

    public CustomizeTileView(Context context, QSIconView qSIconView) {
        super(context, qSIconView, false);
        this.showSideView = true;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileViewImpl
    public boolean animationsEnabled() {
        return false;
    }

    public final void changeState(QSTile.State state) {
        handleStateChanged(state);
    }

    public final int getVisibilityState(CharSequence charSequence) {
        return (!this.showAppLabel || TextUtils.isEmpty(charSequence)) ? 8 : 0;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileViewImpl
    public void handleStateChanged(QSTile.State state) {
        super.handleStateChanged(state);
        setShowRippleEffect(false);
        getSecondaryLabel().setVisibility(getVisibilityState(state.secondaryLabel));
        if (this.showSideView) {
            return;
        }
        getSideView().setVisibility(8);
    }

    @Override // android.view.View
    public boolean isLongClickable() {
        return false;
    }

    public final void setShowAppLabel(boolean z) {
        this.showAppLabel = z;
        getSecondaryLabel().setVisibility(getVisibilityState(getSecondaryLabel().getText()));
    }

    public final void setShowSideView(boolean z) {
        this.showSideView = z;
        if (z) {
            return;
        }
        getSideView().setVisibility(8);
    }
}