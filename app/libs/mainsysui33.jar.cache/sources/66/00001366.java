package com.android.systemui.common.ui.binder;

import android.widget.ImageView;
import com.android.systemui.common.shared.model.Icon;

/* loaded from: mainsysui33.jar:com/android/systemui/common/ui/binder/IconViewBinder.class */
public final class IconViewBinder {
    public static final IconViewBinder INSTANCE = new IconViewBinder();

    public final void bind(Icon icon, ImageView imageView) {
        ContentDescriptionViewBinder.INSTANCE.bind(icon.getContentDescription(), imageView);
        if (icon instanceof Icon.Loaded) {
            imageView.setImageDrawable(((Icon.Loaded) icon).getDrawable());
        } else if (icon instanceof Icon.Resource) {
            imageView.setImageResource(((Icon.Resource) icon).getRes());
        }
    }
}