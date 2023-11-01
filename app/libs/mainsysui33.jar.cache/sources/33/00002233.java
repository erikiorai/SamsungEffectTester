package com.android.systemui.qs.footer.ui.binder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/ui/binder/TextButtonViewHolder.class */
public final class TextButtonViewHolder {
    public static final Companion Companion = new Companion(null);
    public final ImageView chevron;
    public final ImageView icon;
    public final ImageView newDot;
    public final TextView text;
    public final View view;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/ui/binder/TextButtonViewHolder$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final TextButtonViewHolder createAndAdd(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            View inflate = layoutInflater.inflate(R$layout.footer_actions_text_button, viewGroup, false);
            viewGroup.addView(inflate);
            return new TextButtonViewHolder(inflate);
        }
    }

    public TextButtonViewHolder(View view) {
        this.view = view;
        this.icon = (ImageView) view.requireViewById(R$id.icon);
        this.text = (TextView) view.requireViewById(R$id.text);
        this.newDot = (ImageView) view.requireViewById(R$id.new_dot);
        this.chevron = (ImageView) view.requireViewById(R$id.chevron_icon);
    }

    public final ImageView getChevron() {
        return this.chevron;
    }

    public final ImageView getIcon() {
        return this.icon;
    }

    public final ImageView getNewDot() {
        return this.newDot;
    }

    public final TextView getText() {
        return this.text;
    }

    public final View getView() {
        return this.view;
    }
}