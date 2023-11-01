package com.android.systemui.privacy;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.settingslib.Utils;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.R$id;
import com.android.systemui.R$string;
import com.android.systemui.statusbar.events.BackgroundAnimatableView;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/privacy/OngoingPrivacyChip.class */
public final class OngoingPrivacyChip extends FrameLayout implements BackgroundAnimatableView {
    public int iconColor;
    public int iconMargin;
    public int iconSize;
    public LinearLayout iconsContainer;
    public List<PrivacyItem> privacyList;

    public OngoingPrivacyChip(Context context) {
        this(context, null, 0, 0, 14, null);
    }

    public OngoingPrivacyChip(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
    }

    public OngoingPrivacyChip(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, null);
    }

    public OngoingPrivacyChip(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.privacyList = CollectionsKt__CollectionsKt.emptyList();
    }

    public /* synthetic */ OngoingPrivacyChip(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    public static final void updateView$setIcons(OngoingPrivacyChip ongoingPrivacyChip, PrivacyChipBuilder privacyChipBuilder, ViewGroup viewGroup) {
        viewGroup.removeAllViews();
        int i = 0;
        for (Object obj : privacyChipBuilder.generateIcons()) {
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            Drawable drawable = (Drawable) obj;
            drawable.mutate();
            drawable.setTint(ongoingPrivacyChip.iconColor);
            ImageView imageView = new ImageView(ongoingPrivacyChip.getContext());
            imageView.setImageDrawable(drawable);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            int i2 = ongoingPrivacyChip.iconSize;
            viewGroup.addView(imageView, i2, i2);
            if (i != 0) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
                marginLayoutParams.setMarginStart(ongoingPrivacyChip.iconMargin);
                imageView.setLayoutParams(marginLayoutParams);
            }
            i++;
        }
    }

    public final void generateContentDescription(PrivacyChipBuilder privacyChipBuilder) {
        setContentDescription(getContext().getString(R$string.ongoing_privacy_chip_content_multiple_apps, privacyChipBuilder.joinTypes()));
    }

    public int getChipWidth() {
        return BackgroundAnimatableView.DefaultImpls.getChipWidth(this);
    }

    public final List<PrivacyItem> getPrivacyList() {
        return this.privacyList;
    }

    public View getView() {
        return BackgroundAnimatableView.DefaultImpls.getView(this);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.iconsContainer = (LinearLayout) requireViewById(R$id.icons_container);
        updateResources();
    }

    public void setBoundsForAnimation(int i, int i2, int i3, int i4) {
        LinearLayout linearLayout = this.iconsContainer;
        LinearLayout linearLayout2 = linearLayout;
        if (linearLayout == null) {
            linearLayout2 = null;
        }
        linearLayout2.setLeftTopRightBottom(i - getLeft(), i2 - getTop(), i3 - getLeft(), i4 - getTop());
    }

    public final void setPrivacyList(List<PrivacyItem> list) {
        this.privacyList = list;
        updateView(new PrivacyChipBuilder(getContext(), this.privacyList));
    }

    public final void updateResources() {
        this.iconMargin = getContext().getResources().getDimensionPixelSize(R$dimen.ongoing_appops_chip_icon_margin);
        this.iconSize = getContext().getResources().getDimensionPixelSize(R$dimen.ongoing_appops_chip_icon_size);
        this.iconColor = Utils.getColorAttrDefaultColor(getContext(), 16843827);
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R$dimen.ongoing_appops_chip_side_padding);
        LinearLayout linearLayout = this.iconsContainer;
        LinearLayout linearLayout2 = linearLayout;
        if (linearLayout == null) {
            linearLayout2 = null;
        }
        linearLayout2.setPaddingRelative(dimensionPixelSize, 0, dimensionPixelSize, 0);
        LinearLayout linearLayout3 = this.iconsContainer;
        if (linearLayout3 == null) {
            linearLayout3 = null;
        }
        linearLayout3.setBackground(getContext().getDrawable(R$drawable.privacy_chip_bg));
    }

    public final void updateView(PrivacyChipBuilder privacyChipBuilder) {
        LinearLayout linearLayout = null;
        if (this.privacyList.isEmpty()) {
            LinearLayout linearLayout2 = this.iconsContainer;
            if (linearLayout2 == null) {
                linearLayout2 = null;
            }
            linearLayout2.removeAllViews();
        } else {
            generateContentDescription(privacyChipBuilder);
            LinearLayout linearLayout3 = this.iconsContainer;
            if (linearLayout3 != null) {
                linearLayout = linearLayout3;
            }
            updateView$setIcons(this, privacyChipBuilder, linearLayout);
        }
        requestLayout();
    }
}