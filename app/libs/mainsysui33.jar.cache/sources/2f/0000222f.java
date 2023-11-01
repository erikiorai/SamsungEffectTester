package com.android.systemui.qs.footer.ui.binder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/ui/binder/IconButtonViewHolder.class */
public final class IconButtonViewHolder {
    public static final Companion Companion = new Companion(null);
    public final ImageView icon;
    public final View view;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/ui/binder/IconButtonViewHolder$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final IconButtonViewHolder createAndAdd(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
            int i = 0;
            View inflate = layoutInflater.inflate(R$layout.footer_actions_icon_button, viewGroup, false);
            if (z) {
                i = -inflate.getContext().getResources().getDimensionPixelSize(R$dimen.qs_footer_action_inset);
            }
            int dimensionPixelSize = inflate.getContext().getResources().getDimensionPixelSize(R$dimen.qs_footer_action_button_size);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dimensionPixelSize, dimensionPixelSize);
            layoutParams.setMarginEnd(i);
            Unit unit = Unit.INSTANCE;
            viewGroup.addView(inflate, layoutParams);
            return new IconButtonViewHolder(inflate);
        }
    }

    public IconButtonViewHolder(View view) {
        this.view = view;
        this.icon = (ImageView) view.requireViewById(R$id.icon);
    }

    public final ImageView getIcon() {
        return this.icon;
    }

    public final View getView() {
        return this.view;
    }
}