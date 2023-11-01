package com.android.systemui.qs.external;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.tileimpl.QSIconViewImpl;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tileimpl.QSTileViewImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/external/TileRequestDialog.class */
public final class TileRequestDialog extends SystemUIDialog {
    public static final Companion Companion = new Companion(null);
    public static final int CONTENT_ID = R$id.content;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/external/TileRequestDialog$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/external/TileRequestDialog$TileData.class */
    public static final class TileData {
        public final CharSequence appName;
        public final Icon icon;
        public final CharSequence label;

        public TileData(CharSequence charSequence, CharSequence charSequence2, Icon icon) {
            this.appName = charSequence;
            this.label = charSequence2;
            this.icon = icon;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof TileData) {
                TileData tileData = (TileData) obj;
                return Intrinsics.areEqual(this.appName, tileData.appName) && Intrinsics.areEqual(this.label, tileData.label) && Intrinsics.areEqual(this.icon, tileData.icon);
            }
            return false;
        }

        public final CharSequence getAppName() {
            return this.appName;
        }

        public final Icon getIcon() {
            return this.icon;
        }

        public final CharSequence getLabel() {
            return this.label;
        }

        public int hashCode() {
            int hashCode = this.appName.hashCode();
            int hashCode2 = this.label.hashCode();
            Icon icon = this.icon;
            return (((hashCode * 31) + hashCode2) * 31) + (icon == null ? 0 : icon.hashCode());
        }

        public String toString() {
            CharSequence charSequence = this.appName;
            CharSequence charSequence2 = this.label;
            Icon icon = this.icon;
            return "TileData(appName=" + ((Object) charSequence) + ", label=" + ((Object) charSequence2) + ", icon=" + icon + ")";
        }
    }

    public TileRequestDialog(Context context) {
        super(context);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r7v0, resolved type: com.android.systemui.qs.external.TileRequestDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public final QSTileView createTileView(TileData tileData) {
        Drawable loadDrawable;
        final QSTileViewImpl qSTileViewImpl = new QSTileViewImpl(getContext(), new QSIconViewImpl(getContext()), true);
        QSTile.BooleanState booleanState = new QSTile.BooleanState();
        booleanState.label = tileData.getLabel();
        booleanState.handlesLongClick = false;
        Icon icon = tileData.getIcon();
        booleanState.icon = (icon == null || (loadDrawable = icon.loadDrawable(getContext())) == null) ? QSTileImpl.ResourceIcon.get(R$drawable.f8android) : new QSTileImpl.DrawableIcon(loadDrawable);
        booleanState.contentDescription = booleanState.label;
        qSTileViewImpl.onStateChanged(booleanState);
        qSTileViewImpl.post(new Runnable() { // from class: com.android.systemui.qs.external.TileRequestDialog$createTileView$1
            @Override // java.lang.Runnable
            public final void run() {
                QSTileViewImpl.this.setStateDescription("");
                QSTileViewImpl.this.setClickable(false);
                QSTileViewImpl.this.setSelected(true);
            }
        });
        return qSTileViewImpl;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r8v0, resolved type: com.android.systemui.qs.external.TileRequestDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public final void setTileData(TileData tileData) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getContext()).inflate(R$layout.tile_service_request_dialog, (ViewGroup) null);
        TextView textView = (TextView) viewGroup.requireViewById(R$id.text);
        textView.setText(textView.getContext().getString(R$string.qs_tile_request_dialog_text, tileData.getAppName()));
        viewGroup.addView(createTileView(tileData), viewGroup.getContext().getResources().getDimensionPixelSize(R$dimen.qs_tile_service_request_tile_width), viewGroup.getContext().getResources().getDimensionPixelSize(R$dimen.qs_quick_tile_size));
        viewGroup.setSelected(true);
        setView(viewGroup, 0, 0, 0, 0);
    }
}