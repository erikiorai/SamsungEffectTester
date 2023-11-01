package com.android.systemui.common.ui.binder;

import android.widget.ImageView;
import com.android.settingslib.Utils;
import com.android.systemui.common.shared.model.TintedIcon;

/* loaded from: mainsysui33.jar:com/android/systemui/common/ui/binder/TintedIconViewBinder.class */
public final class TintedIconViewBinder {
    public static final TintedIconViewBinder INSTANCE = new TintedIconViewBinder();

    public final void bind(TintedIcon tintedIcon, ImageView imageView) {
        IconViewBinder.INSTANCE.bind(tintedIcon.getIcon(), imageView);
        imageView.setImageTintList(tintedIcon.getTintAttr() != null ? Utils.getColorAttr(imageView.getContext(), tintedIcon.getTintAttr().intValue()) : null);
    }
}