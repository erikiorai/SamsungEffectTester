package com.android.systemui.controls.management;

import android.content.ComponentName;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Icon;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import com.android.systemui.R$id;
import com.android.systemui.R$string;
import com.android.systemui.controls.ControlInterface;
import com.android.systemui.controls.management.ControlsModel;
import com.android.systemui.controls.ui.RenderInfo;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlHolder.class */
public final class ControlHolder extends Holder {
    public final ControlHolderAccessibilityDelegate accessibilityDelegate;
    public final CheckBox favorite;
    public final Function2<String, Boolean, Unit> favoriteCallback;
    public final String favoriteStateDescription;
    public final ImageView icon;
    public final ControlsModel.MoveHelper moveHelper;
    public final String notFavoriteStateDescription;
    public final TextView removed;
    public final TextView subtitle;
    public final TextView title;

    /* JADX DEBUG: Multi-variable search result rejected for r10v0, resolved type: kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.Boolean, kotlin.Unit> */
    /* JADX WARN: Multi-variable type inference failed */
    public ControlHolder(View view, ControlsModel.MoveHelper moveHelper, Function2<? super String, ? super Boolean, Unit> function2) {
        super(view, null);
        this.moveHelper = moveHelper;
        this.favoriteCallback = function2;
        this.favoriteStateDescription = this.itemView.getContext().getString(R$string.accessibility_control_favorite);
        this.notFavoriteStateDescription = this.itemView.getContext().getString(R$string.accessibility_control_not_favorite);
        this.icon = (ImageView) this.itemView.requireViewById(R$id.icon);
        this.title = (TextView) this.itemView.requireViewById(R$id.title);
        this.subtitle = (TextView) this.itemView.requireViewById(R$id.subtitle);
        this.removed = (TextView) this.itemView.requireViewById(R$id.status);
        CheckBox checkBox = (CheckBox) this.itemView.requireViewById(R$id.favorite);
        checkBox.setVisibility(0);
        this.favorite = checkBox;
        ControlHolderAccessibilityDelegate controlHolderAccessibilityDelegate = new ControlHolderAccessibilityDelegate(new ControlHolder$accessibilityDelegate$1(this), new ControlHolder$accessibilityDelegate$2(this), moveHelper);
        this.accessibilityDelegate = controlHolderAccessibilityDelegate;
        ViewCompat.setAccessibilityDelegate(this.itemView, controlHolderAccessibilityDelegate);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlHolder$bindData$1.onClick(android.view.View):void] */
    public static final /* synthetic */ CheckBox access$getFavorite$p(ControlHolder controlHolder) {
        return controlHolder.favorite;
    }

    public final void applyRenderInfo(RenderInfo renderInfo, ControlInterface controlInterface) {
        Context context = this.itemView.getContext();
        ColorStateList colorStateList = context.getResources().getColorStateList(renderInfo.getForeground(), context.getTheme());
        Unit unit = null;
        this.icon.setImageTintList(null);
        Icon customIcon = controlInterface.getCustomIcon();
        if (customIcon != null) {
            this.icon.setImageIcon(customIcon);
            unit = Unit.INSTANCE;
        }
        if (unit == null) {
            this.icon.setImageDrawable(renderInfo.getIcon());
            if (controlInterface.getDeviceType() != 52) {
                this.icon.setImageTintList(colorStateList);
            }
        }
    }

    @Override // com.android.systemui.controls.management.Holder
    public void bindData(final ElementWrapper elementWrapper) {
        ControlInterface controlInterface = (ControlInterface) elementWrapper;
        RenderInfo renderInfo = getRenderInfo(controlInterface.getComponent(), controlInterface.getDeviceType());
        this.title.setText(controlInterface.getTitle());
        this.subtitle.setText(controlInterface.getSubtitle());
        updateFavorite(controlInterface.getFavorite());
        this.removed.setText(controlInterface.getRemoved() ? this.itemView.getContext().getText(R$string.controls_removed) : "");
        this.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.management.ControlHolder$bindData$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ControlHolder controlHolder = ControlHolder.this;
                controlHolder.updateFavorite(!ControlHolder.access$getFavorite$p(controlHolder).isChecked());
                ControlHolder.this.getFavoriteCallback().invoke(((ControlInterface) elementWrapper).getControlId(), Boolean.valueOf(ControlHolder.access$getFavorite$p(ControlHolder.this).isChecked()));
            }
        });
        applyRenderInfo(renderInfo, controlInterface);
    }

    public final Function2<String, Boolean, Unit> getFavoriteCallback() {
        return this.favoriteCallback;
    }

    public final RenderInfo getRenderInfo(ComponentName componentName, int i) {
        return RenderInfo.Companion.lookup$default(RenderInfo.Companion, this.itemView.getContext(), componentName, i, 0, 8, null);
    }

    public final CharSequence stateDescription(boolean z) {
        if (z) {
            if (this.moveHelper == null) {
                return this.favoriteStateDescription;
            }
            return this.itemView.getContext().getString(R$string.accessibility_control_favorite_position, Integer.valueOf(getLayoutPosition() + 1));
        }
        return this.notFavoriteStateDescription;
    }

    @Override // com.android.systemui.controls.management.Holder
    public void updateFavorite(boolean z) {
        this.favorite.setChecked(z);
        this.accessibilityDelegate.setFavorite(z);
        this.itemView.setStateDescription(stateDescription(z));
    }
}