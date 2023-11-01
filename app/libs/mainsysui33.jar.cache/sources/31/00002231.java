package com.android.systemui.qs.footer.ui.binder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/ui/binder/NumberButtonViewHolder.class */
public final class NumberButtonViewHolder {
    public static final Companion Companion = new Companion(null);
    public final ImageView newDot;
    public final TextView number;
    public final View view;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/ui/binder/NumberButtonViewHolder$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final NumberButtonViewHolder createAndAdd(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            View inflate = layoutInflater.inflate(R$layout.footer_actions_number_button, viewGroup, false);
            viewGroup.addView(inflate);
            return new NumberButtonViewHolder(inflate);
        }
    }

    public NumberButtonViewHolder(View view) {
        this.view = view;
        this.number = (TextView) view.requireViewById(R$id.number);
        this.newDot = (ImageView) view.requireViewById(R$id.new_dot);
    }

    public final ImageView getNewDot() {
        return this.newDot;
    }

    public final TextView getNumber() {
        return this.number;
    }

    public final View getView() {
        return this.view;
    }
}