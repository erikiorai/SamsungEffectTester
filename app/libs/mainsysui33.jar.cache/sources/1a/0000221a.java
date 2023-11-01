package com.android.systemui.qs.footer.ui.binder;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.lifecycle.LifecycleOwner;
import com.android.systemui.R$attr;
import com.android.systemui.R$drawable;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.ui.binder.IconViewBinder;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.qs.footer.ui.binder.IconButtonViewHolder;
import com.android.systemui.qs.footer.ui.binder.TextButtonViewHolder;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsButtonViewModel;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsForegroundServicesButtonViewModel;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsSecurityButtonViewModel;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/ui/binder/FooterActionsViewBinder.class */
public final class FooterActionsViewBinder {
    public static final FooterActionsViewBinder INSTANCE = new FooterActionsViewBinder();

    public static final void bind(LinearLayout linearLayout, FooterActionsViewModel footerActionsViewModel, LifecycleOwner lifecycleOwner) {
        linearLayout.setImportantForAccessibility(1);
        LayoutInflater from = LayoutInflater.from(linearLayout.getContext());
        TextButtonViewHolder.Companion companion = TextButtonViewHolder.Companion;
        TextButtonViewHolder createAndAdd = companion.createAndAdd(from, linearLayout);
        TextButtonViewHolder createAndAdd2 = companion.createAndAdd(from, linearLayout);
        NumberButtonViewHolder createAndAdd3 = NumberButtonViewHolder.Companion.createAndAdd(from, linearLayout);
        IconButtonViewHolder.Companion companion2 = IconButtonViewHolder.Companion;
        boolean z = false;
        IconButtonViewHolder createAndAdd4 = companion2.createAndAdd(from, linearLayout, false);
        if (footerActionsViewModel.getPower() == null) {
            z = true;
        }
        IconButtonViewHolder createAndAdd5 = companion2.createAndAdd(from, linearLayout, z);
        FooterActionsViewBinder footerActionsViewBinder = INSTANCE;
        footerActionsViewBinder.bindButton(createAndAdd5, footerActionsViewModel.getSettings());
        if (footerActionsViewModel.getPower() != null) {
            footerActionsViewBinder.bindButton(companion2.createAndAdd(from, linearLayout, true), footerActionsViewModel.getPower());
        }
        RepeatWhenAttachedKt.repeatWhenAttached$default(linearLayout, null, new FooterActionsViewBinder$bind$1(lifecycleOwner, footerActionsViewModel, linearLayout, new Ref.ObjectRef(), createAndAdd, new Ref.ObjectRef(), createAndAdd3, createAndAdd2, new Ref.ObjectRef(), createAndAdd4, null), 1, null);
    }

    public final void bindButton(IconButtonViewHolder iconButtonViewHolder, final FooterActionsButtonViewModel footerActionsButtonViewModel) {
        int i;
        final View view = iconButtonViewHolder.getView();
        view.setId(footerActionsButtonViewModel != null ? footerActionsButtonViewModel.getId() : -1);
        view.setVisibility(footerActionsButtonViewModel != null ? 0 : 8);
        if (footerActionsButtonViewModel == null) {
            return;
        }
        int backgroundColor = footerActionsButtonViewModel.getBackgroundColor();
        if (backgroundColor == R$attr.offStateColor) {
            i = R$drawable.qs_footer_action_circle;
        } else if (backgroundColor != 16843829) {
            int backgroundColor2 = footerActionsButtonViewModel.getBackgroundColor();
            throw new IllegalStateException(("Unsupported icon background resource " + backgroundColor2).toString());
        } else {
            i = R$drawable.qs_footer_action_circle_color;
        }
        view.setBackgroundResource(i);
        view.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.footer.ui.binder.FooterActionsViewBinder$bindButton$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                FooterActionsButtonViewModel.this.getOnClick().invoke(Expandable.Companion.fromView(view));
            }
        });
        Icon icon = footerActionsButtonViewModel.getIcon();
        ImageView icon2 = iconButtonViewHolder.getIcon();
        IconViewBinder.INSTANCE.bind(icon, icon2);
        if (footerActionsButtonViewModel.getIconTint() != null) {
            icon2.setColorFilter(footerActionsButtonViewModel.getIconTint().intValue(), PorterDuff.Mode.SRC_IN);
        } else {
            icon2.clearColorFilter();
        }
    }

    public final void bindForegroundService(NumberButtonViewHolder numberButtonViewHolder, TextButtonViewHolder textButtonViewHolder, final FooterActionsForegroundServicesButtonViewModel footerActionsForegroundServicesButtonViewModel) {
        final View view = numberButtonViewHolder.getView();
        final View view2 = textButtonViewHolder.getView();
        int i = 8;
        if (footerActionsForegroundServicesButtonViewModel == null) {
            view.setVisibility(8);
            view2.setVisibility(8);
            return;
        }
        int foregroundServicesCount = footerActionsForegroundServicesButtonViewModel.getForegroundServicesCount();
        if (footerActionsForegroundServicesButtonViewModel.getDisplayText()) {
            view.setVisibility(8);
            view2.setVisibility(0);
            view2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.footer.ui.binder.FooterActionsViewBinder$bindForegroundService$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view3) {
                    FooterActionsForegroundServicesButtonViewModel.this.getOnClick().invoke(Expandable.Companion.fromView(view2));
                }
            });
            textButtonViewHolder.getText().setText(footerActionsForegroundServicesButtonViewModel.getText());
            ImageView newDot = textButtonViewHolder.getNewDot();
            if (footerActionsForegroundServicesButtonViewModel.getHasNewChanges()) {
                i = 0;
            }
            newDot.setVisibility(i);
            return;
        }
        view2.setVisibility(8);
        view.setVisibility(0);
        view.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.footer.ui.binder.FooterActionsViewBinder$bindForegroundService$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                FooterActionsForegroundServicesButtonViewModel.this.getOnClick().invoke(Expandable.Companion.fromView(view));
            }
        });
        numberButtonViewHolder.getNumber().setText(String.valueOf(foregroundServicesCount));
        numberButtonViewHolder.getNumber().setContentDescription(footerActionsForegroundServicesButtonViewModel.getText());
        ImageView newDot2 = numberButtonViewHolder.getNewDot();
        if (footerActionsForegroundServicesButtonViewModel.getHasNewChanges()) {
            i = 0;
        }
        newDot2.setVisibility(i);
    }

    public final void bindSecurity(final Context context, TextButtonViewHolder textButtonViewHolder, FooterActionsSecurityButtonViewModel footerActionsSecurityButtonViewModel) {
        final View view = textButtonViewHolder.getView();
        view.setVisibility(footerActionsSecurityButtonViewModel != null ? 0 : 8);
        if (footerActionsSecurityButtonViewModel == null) {
            return;
        }
        ImageView chevron = textButtonViewHolder.getChevron();
        final Function2<Context, Expandable, Unit> onClick = footerActionsSecurityButtonViewModel.getOnClick();
        if (onClick != null) {
            view.setClickable(true);
            view.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.footer.ui.binder.FooterActionsViewBinder$bindSecurity$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    onClick.invoke(context, Expandable.Companion.fromView(view));
                }
            });
            chevron.setVisibility(0);
        } else {
            view.setClickable(false);
            view.setOnClickListener(null);
            chevron.setVisibility(8);
        }
        textButtonViewHolder.getText().setText(footerActionsSecurityButtonViewModel.getText());
        textButtonViewHolder.getNewDot().setVisibility(8);
        IconViewBinder.INSTANCE.bind(footerActionsSecurityButtonViewModel.getIcon(), textButtonViewHolder.getIcon());
    }
}