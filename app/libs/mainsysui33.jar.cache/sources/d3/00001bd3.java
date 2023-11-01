package com.android.systemui.media.controls.models;

import android.content.res.ColorStateList;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.android.systemui.R$id;
import com.android.systemui.media.controls.ui.MediaColorSchemesKt;
import com.android.systemui.monet.ColorScheme;
import java.util.Set;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/models/GutsViewHolder.class */
public final class GutsViewHolder {
    public static final Companion Companion = new Companion(null);
    public static final Set<Integer> ids = SetsKt__SetsKt.setOf(new Integer[]{Integer.valueOf(R$id.remove_text), Integer.valueOf(R$id.cancel), Integer.valueOf(R$id.dismiss), Integer.valueOf(R$id.settings)});
    public final View cancel;
    public final TextView cancelText;
    public ColorScheme colorScheme;
    public final ViewGroup dismiss;
    public final TextView dismissText;
    public final TextView gutsText;
    public boolean isDismissible = true;
    public final ImageButton settings;

    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/models/GutsViewHolder$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Set<Integer> getIds() {
            return GutsViewHolder.ids;
        }
    }

    public GutsViewHolder(View view) {
        this.gutsText = (TextView) view.requireViewById(R$id.remove_text);
        this.cancel = view.requireViewById(R$id.cancel);
        this.cancelText = (TextView) view.requireViewById(R$id.cancel_text);
        this.dismiss = (ViewGroup) view.requireViewById(R$id.dismiss);
        this.dismissText = (TextView) view.requireViewById(R$id.dismiss_text);
        this.settings = (ImageButton) view.requireViewById(R$id.settings);
    }

    public final View getCancel() {
        return this.cancel;
    }

    public final TextView getCancelText() {
        return this.cancelText;
    }

    public final ViewGroup getDismiss() {
        return this.dismiss;
    }

    public final TextView getDismissText() {
        return this.dismissText;
    }

    public final TextView getGutsText() {
        return this.gutsText;
    }

    public final ImageButton getSettings() {
        return this.settings;
    }

    public final void marquee(final boolean z, long j, String str) {
        Handler handler = this.gutsText.getHandler();
        if (handler == null) {
            Log.d(str, "marquee while longPressText.getHandler() is null", new Exception());
        } else {
            handler.postDelayed(new Runnable() { // from class: com.android.systemui.media.controls.models.GutsViewHolder$marquee$1
                @Override // java.lang.Runnable
                public final void run() {
                    GutsViewHolder.this.getGutsText().setSelected(z);
                }
            }, j);
        }
    }

    public final void setAccentPrimaryColor(int i) {
        ColorStateList valueOf = ColorStateList.valueOf(i);
        this.settings.setImageTintList(valueOf);
        this.cancelText.setBackgroundTintList(valueOf);
        this.dismissText.setBackgroundTintList(valueOf);
    }

    public final void setColorScheme(ColorScheme colorScheme) {
        this.colorScheme = colorScheme;
    }

    public final void setColors(ColorScheme colorScheme) {
        this.colorScheme = colorScheme;
        setSurfaceColor(MediaColorSchemesKt.surfaceFromScheme(colorScheme));
        setTextPrimaryColor(MediaColorSchemesKt.textPrimaryFromScheme(colorScheme));
        setAccentPrimaryColor(MediaColorSchemesKt.accentPrimaryFromScheme(colorScheme));
    }

    public final void setDismissible(boolean z) {
        if (this.isDismissible == z) {
            return;
        }
        this.isDismissible = z;
        ColorScheme colorScheme = this.colorScheme;
        if (colorScheme != null) {
            setColors(colorScheme);
        }
    }

    public final void setSurfaceColor(int i) {
        this.dismissText.setTextColor(i);
        if (this.isDismissible) {
            return;
        }
        this.cancelText.setTextColor(i);
    }

    public final void setTextPrimaryColor(int i) {
        ColorStateList valueOf = ColorStateList.valueOf(i);
        this.gutsText.setTextColor(valueOf);
        if (this.isDismissible) {
            this.cancelText.setTextColor(valueOf);
        }
    }
}