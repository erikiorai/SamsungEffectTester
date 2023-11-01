package com.android.systemui.dreams;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.R$id;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayStatusBarView.class */
public class DreamOverlayStatusBarView extends ConstraintLayout {
    public ViewGroup mExtraSystemStatusViewGroup;
    public final Map<Integer, View> mStatusIcons;
    public ViewGroup mSystemStatusViewGroup;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.DreamOverlayStatusBarView$$ExternalSyntheticLambda0.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$d7b1_rodhD29bQD1qlAbU4k4deg(DreamOverlayStatusBarView dreamOverlayStatusBarView, View view) {
        dreamOverlayStatusBarView.lambda$setExtraStatusBarItemViews$0(view);
    }

    public DreamOverlayStatusBarView(Context context) {
        this(context, null);
    }

    public DreamOverlayStatusBarView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DreamOverlayStatusBarView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public DreamOverlayStatusBarView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mStatusIcons = new HashMap();
    }

    public /* synthetic */ void lambda$setExtraStatusBarItemViews$0(View view) {
        this.mExtraSystemStatusViewGroup.addView(view);
    }

    public final boolean areAnyStatusIconsVisible() {
        for (int i = 0; i < this.mSystemStatusViewGroup.getChildCount(); i++) {
            if (this.mSystemStatusViewGroup.getChildAt(i).getVisibility() == 0) {
                return true;
            }
        }
        return false;
    }

    public final View fetchStatusIconForResId(int i) {
        View findViewById = findViewById(i);
        Objects.requireNonNull(findViewById);
        return findViewById;
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mStatusIcons.put(1, fetchStatusIconForResId(R$id.dream_overlay_wifi_status));
        this.mStatusIcons.put(2, fetchStatusIconForResId(R$id.dream_overlay_alarm_set));
        this.mStatusIcons.put(3, fetchStatusIconForResId(R$id.dream_overlay_camera_off));
        this.mStatusIcons.put(4, fetchStatusIconForResId(R$id.dream_overlay_mic_off));
        this.mStatusIcons.put(5, fetchStatusIconForResId(R$id.dream_overlay_camera_mic_off));
        this.mStatusIcons.put(0, fetchStatusIconForResId(R$id.dream_overlay_notification_indicator));
        this.mStatusIcons.put(6, fetchStatusIconForResId(R$id.dream_overlay_priority_mode));
        this.mSystemStatusViewGroup = (ViewGroup) findViewById(R$id.dream_overlay_system_status);
        this.mExtraSystemStatusViewGroup = (ViewGroup) findViewById(R$id.dream_overlay_extra_items);
    }

    public void removeAllExtraStatusBarItemViews() {
        this.mExtraSystemStatusViewGroup.removeAllViews();
    }

    public void setExtraStatusBarItemViews(List<View> list) {
        removeAllExtraStatusBarItemViews();
        list.forEach(new Consumer() { // from class: com.android.systemui.dreams.DreamOverlayStatusBarView$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                DreamOverlayStatusBarView.$r8$lambda$d7b1_rodhD29bQD1qlAbU4k4deg(DreamOverlayStatusBarView.this, (View) obj);
            }
        });
    }

    public void showIcon(int i, boolean z, String str) {
        View view = this.mStatusIcons.get(Integer.valueOf(i));
        if (view == null) {
            return;
        }
        if (z && str != null) {
            view.setContentDescription(str);
        }
        view.setVisibility(z ? 0 : 8);
        this.mSystemStatusViewGroup.setVisibility(areAnyStatusIconsVisible() ? 0 : 8);
    }
}