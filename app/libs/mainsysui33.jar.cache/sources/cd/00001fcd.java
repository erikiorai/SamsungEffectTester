package com.android.systemui.plugins.qs;

import android.view.View;
import com.android.systemui.plugins.FragmentBase;
import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import java.util.function.Consumer;

@ProvidesInterface(action = QS.ACTION, version = 15)
@DependsOn(target = HeightListener.class)
/* loaded from: mainsysui33.jar:com/android/systemui/plugins/qs/QS.class */
public interface QS extends FragmentBase {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_QS";
    public static final String TAG = "QS";
    public static final int VERSION = 15;

    @ProvidesInterface(version = 1)
    /* loaded from: mainsysui33.jar:com/android/systemui/plugins/qs/QS$HeightListener.class */
    public interface HeightListener {
        public static final int VERSION = 1;

        void onQsHeightChanged();
    }

    @ProvidesInterface(version = 1)
    /* loaded from: mainsysui33.jar:com/android/systemui/plugins/qs/QS$ScrollListener.class */
    public interface ScrollListener {
        public static final int VERSION = 1;

        void onQsPanelScrollChanged(int i);
    }

    void animateHeaderSlidingOut();

    void closeCustomizer();

    void closeDetail();

    default boolean disallowPanelTouches() {
        return isShowingDetail();
    }

    int getDesiredHeight();

    View getHeader();

    int getHeightDiff();

    int getQsMinExpansionHeight();

    void hideImmediately();

    boolean isCustomizing();

    default boolean isFullyCollapsed() {
        return true;
    }

    boolean isShowingDetail();

    void notifyCustomizeChanged();

    void setCollapseExpandAction(Runnable runnable);

    void setCollapsedMediaVisibilityChangedListener(Consumer<Boolean> consumer);

    void setContainerController(QSContainerController qSContainerController);

    void setExpanded(boolean z);

    void setFancyClipping(int i, int i2, int i3, boolean z);

    default void setHasNotifications(boolean z) {
    }

    void setHeaderClickable(boolean z);

    void setHeaderListening(boolean z);

    void setHeightOverride(int i);

    void setInSplitShade(boolean z);

    void setIsNotificationPanelFullWidth(boolean z);

    void setListening(boolean z);

    default void setOverScrollAmount(int i) {
    }

    void setOverscrolling(boolean z);

    void setPanelView(HeightListener heightListener);

    void setQsExpansion(float f, float f2, float f3, float f4);

    void setQsVisible(boolean z);

    default void setScrollListener(ScrollListener scrollListener) {
    }

    default void setTransitionToFullShadeProgress(boolean z, float f, float f2) {
    }
}