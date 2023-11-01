package com.android.systemui.accessibility.floatingmenu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.accessibility.dialog.AccessibilityTarget;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/accessibility/floatingmenu/AccessibilityTargetAdapter.class */
public class AccessibilityTargetAdapter extends RecyclerView.Adapter<ViewHolder> {
    public int mIconWidthHeight;
    public int mItemPadding;
    public final List<AccessibilityTarget> mTargets;

    /* loaded from: mainsysui33.jar:com/android/systemui/accessibility/floatingmenu/AccessibilityTargetAdapter$BottomViewHolder.class */
    public static class BottomViewHolder extends ViewHolder {
        public BottomViewHolder(View view) {
            super(view);
        }

        @Override // com.android.systemui.accessibility.floatingmenu.AccessibilityTargetAdapter.ViewHolder
        public void updateItemPadding(int i, int i2) {
            this.itemView.setPaddingRelative(i, i, i, i);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/accessibility/floatingmenu/AccessibilityTargetAdapter$TopViewHolder.class */
    public static class TopViewHolder extends ViewHolder {
        public TopViewHolder(View view) {
            super(view);
        }

        @Override // com.android.systemui.accessibility.floatingmenu.AccessibilityTargetAdapter.ViewHolder
        public void updateItemPadding(int i, int i2) {
            this.itemView.setPaddingRelative(i, i, i, i2 <= 1 ? i : 0);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/accessibility/floatingmenu/AccessibilityTargetAdapter$ViewHolder.class */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mIconView;

        public ViewHolder(View view) {
            super(view);
            this.mIconView = view.findViewById(R$id.icon_view);
        }

        public void updateIconWidthHeight(int i) {
            ViewGroup.LayoutParams layoutParams = this.mIconView.getLayoutParams();
            if (layoutParams.width == i) {
                return;
            }
            layoutParams.width = i;
            layoutParams.height = i;
            this.mIconView.setLayoutParams(layoutParams);
        }

        public void updateItemPadding(int i, int i2) {
            this.itemView.setPaddingRelative(i, i, i, 0);
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.floatingmenu.AccessibilityTargetAdapter$$ExternalSyntheticLambda0.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$Se3-v07gvUakgpo-GgiJtmYU4Mg */
    public static /* synthetic */ void m1412$r8$lambda$Se3v07gvUakgpoGgiJtmYU4Mg(AccessibilityTarget accessibilityTarget, View view) {
        accessibilityTarget.onSelected();
    }

    public AccessibilityTargetAdapter(List<AccessibilityTarget> list) {
        this.mTargets = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mTargets.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        if (i == 0) {
            return 0;
        }
        return i == getItemCount() - 1 ? 2 : 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final AccessibilityTarget accessibilityTarget = this.mTargets.get(i);
        viewHolder.mIconView.setBackground(accessibilityTarget.getIcon());
        viewHolder.updateIconWidthHeight(this.mIconWidthHeight);
        viewHolder.updateItemPadding(this.mItemPadding, getItemCount());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityTargetAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AccessibilityTargetAdapter.m1412$r8$lambda$Se3v07gvUakgpoGgiJtmYU4Mg(accessibilityTarget, view);
            }
        });
        viewHolder.itemView.setStateDescription(accessibilityTarget.getStateDescription());
        viewHolder.itemView.setContentDescription(accessibilityTarget.getLabel());
        ViewCompat.replaceAccessibilityAction(viewHolder.itemView, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK, accessibilityTarget.getFragmentType() == 2 ? viewHolder.itemView.getResources().getString(R$string.accessibility_floating_button_action_double_tap_to_toggle) : null, null);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R$layout.accessibility_floating_menu_item, viewGroup, false);
        return i == 0 ? new TopViewHolder(inflate) : i == 2 ? new BottomViewHolder(inflate) : new ViewHolder(inflate);
    }

    public void setIconWidthHeight(int i) {
        this.mIconWidthHeight = i;
    }

    public void setItemPadding(int i) {
        this.mItemPadding = i;
    }
}