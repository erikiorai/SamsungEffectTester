package com.android.systemui.controls.management;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.systemui.R$id;
import com.android.systemui.R$string;
import com.android.systemui.controls.management.ControlsModel;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlHolderAccessibilityDelegate.class */
public final class ControlHolderAccessibilityDelegate extends AccessibilityDelegateCompat {
    public boolean isFavorite;
    public final ControlsModel.MoveHelper moveHelper;
    public final Function0<Integer> positionRetriever;
    public final Function1<Boolean, CharSequence> stateRetriever;
    public static final Companion Companion = new Companion(null);
    public static final int MOVE_BEFORE_ID = R$id.accessibility_action_controls_move_before;
    public static final int MOVE_AFTER_ID = R$id.accessibility_action_controls_move_after;

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlHolderAccessibilityDelegate$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: kotlin.jvm.functions.Function1<? super java.lang.Boolean, ? extends java.lang.CharSequence> */
    /* JADX WARN: Multi-variable type inference failed */
    public ControlHolderAccessibilityDelegate(Function1<? super Boolean, ? extends CharSequence> function1, Function0<Integer> function0, ControlsModel.MoveHelper moveHelper) {
        this.stateRetriever = function1;
        this.positionRetriever = function0;
        this.moveHelper = moveHelper;
    }

    public final void addClickAction(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        accessibilityNodeInfoCompat.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(16, this.isFavorite ? view.getContext().getString(R$string.accessibility_control_change_unfavorite) : view.getContext().getString(R$string.accessibility_control_change_favorite)));
    }

    public final void maybeAddMoveAfterAction(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        ControlsModel.MoveHelper moveHelper = this.moveHelper;
        if (moveHelper != null ? moveHelper.canMoveAfter(((Number) this.positionRetriever.invoke()).intValue()) : false) {
            accessibilityNodeInfoCompat.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(MOVE_AFTER_ID, view.getContext().getString(R$string.accessibility_control_move, Integer.valueOf(((Number) this.positionRetriever.invoke()).intValue() + 1 + 1))));
            accessibilityNodeInfoCompat.setContextClickable(true);
        }
    }

    public final void maybeAddMoveBeforeAction(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        ControlsModel.MoveHelper moveHelper = this.moveHelper;
        if (moveHelper != null ? moveHelper.canMoveBefore(((Number) this.positionRetriever.invoke()).intValue()) : false) {
            accessibilityNodeInfoCompat.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(MOVE_BEFORE_ID, view.getContext().getString(R$string.accessibility_control_move, Integer.valueOf((((Number) this.positionRetriever.invoke()).intValue() + 1) - 1))));
            accessibilityNodeInfoCompat.setContextClickable(true);
        }
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
        accessibilityNodeInfoCompat.setContextClickable(false);
        addClickAction(view, accessibilityNodeInfoCompat);
        maybeAddMoveBeforeAction(view, accessibilityNodeInfoCompat);
        maybeAddMoveAfterAction(view, accessibilityNodeInfoCompat);
        accessibilityNodeInfoCompat.setStateDescription((CharSequence) this.stateRetriever.invoke(Boolean.valueOf(this.isFavorite)));
        accessibilityNodeInfoCompat.setCollectionItemInfo(null);
        accessibilityNodeInfoCompat.setClassName(Switch.class.getName());
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        boolean z;
        if (super.performAccessibilityAction(view, i, bundle)) {
            return true;
        }
        if (i == MOVE_BEFORE_ID) {
            ControlsModel.MoveHelper moveHelper = this.moveHelper;
            z = true;
            if (moveHelper != null) {
                moveHelper.moveBefore(((Number) this.positionRetriever.invoke()).intValue());
                z = true;
            }
        } else if (i == MOVE_AFTER_ID) {
            ControlsModel.MoveHelper moveHelper2 = this.moveHelper;
            z = true;
            if (moveHelper2 != null) {
                moveHelper2.moveAfter(((Number) this.positionRetriever.invoke()).intValue());
                z = true;
            }
        } else {
            z = false;
        }
        return z;
    }

    public final void setFavorite(boolean z) {
        this.isFavorite = z;
    }
}