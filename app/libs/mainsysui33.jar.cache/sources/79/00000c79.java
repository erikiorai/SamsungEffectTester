package com.android.keyguard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.R$id;
import com.android.systemui.plugins.FalsingManager;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardUserSwitcherPopupMenu.class */
public class KeyguardUserSwitcherPopupMenu extends ListPopupWindow {
    public Context mContext;
    public FalsingManager mFalsingManager;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardUserSwitcherPopupMenu$$ExternalSyntheticLambda0.onTouch(android.view.View, android.view.MotionEvent):boolean] */
    /* renamed from: $r8$lambda$Hx53FLPA_BXvFfxKzMZi3Bza-HE */
    public static /* synthetic */ boolean m774$r8$lambda$Hx53FLPA_BXvFfxKzMZi3BzaHE(KeyguardUserSwitcherPopupMenu keyguardUserSwitcherPopupMenu, View view, MotionEvent motionEvent) {
        return keyguardUserSwitcherPopupMenu.lambda$show$0(view, motionEvent);
    }

    public KeyguardUserSwitcherPopupMenu(Context context, FalsingManager falsingManager) {
        super(context);
        this.mContext = context;
        this.mFalsingManager = falsingManager;
        setBackgroundDrawable(context.getResources().getDrawable(R$drawable.bouncer_user_switcher_popup_bg, context.getTheme()));
        setModal(true);
        setOverlapAnchor(true);
    }

    public /* synthetic */ boolean lambda$show$0(View view, MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == 0) {
            return this.mFalsingManager.isFalseTap(1);
        }
        return false;
    }

    public final View createSpacer(final int i) {
        return new View(this.mContext) { // from class: com.android.keyguard.KeyguardUserSwitcherPopupMenu.1
            {
                KeyguardUserSwitcherPopupMenu.this = this;
            }

            @Override // android.view.View
            public void draw(Canvas canvas) {
            }

            @Override // android.view.View
            public void onMeasure(int i2, int i3) {
                setMeasuredDimension(1, i);
            }
        };
    }

    @Override // android.widget.ListPopupWindow
    public void show() {
        super.show();
        ListView listView = getListView();
        listView.setVerticalScrollBarEnabled(false);
        listView.setHorizontalScrollBarEnabled(false);
        ShapeDrawable shapeDrawable = new ShapeDrawable();
        shapeDrawable.setAlpha(0);
        listView.setDivider(shapeDrawable);
        listView.setDividerHeight(this.mContext.getResources().getDimensionPixelSize(R$dimen.bouncer_user_switcher_popup_divider_height));
        int i = R$id.header_footer_views_added_tag_key;
        if (listView.getTag(i) == null) {
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R$dimen.bouncer_user_switcher_popup_header_height);
            listView.addHeaderView(createSpacer(dimensionPixelSize), null, false);
            listView.addFooterView(createSpacer(dimensionPixelSize), null, false);
            listView.setTag(i, new Object());
        }
        listView.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.keyguard.KeyguardUserSwitcherPopupMenu$$ExternalSyntheticLambda0
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return KeyguardUserSwitcherPopupMenu.m774$r8$lambda$Hx53FLPA_BXvFfxKzMZi3BzaHE(KeyguardUserSwitcherPopupMenu.this, view, motionEvent);
            }
        });
        super.show();
    }
}