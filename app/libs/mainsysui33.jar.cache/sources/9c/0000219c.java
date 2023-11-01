package com.android.systemui.qs.dagger;

import com.android.systemui.qs.QSAnimator;
import com.android.systemui.qs.QSContainerImplController;
import com.android.systemui.qs.QSFooter;
import com.android.systemui.qs.QSFragment;
import com.android.systemui.qs.QSPanelController;
import com.android.systemui.qs.QSSquishinessController;
import com.android.systemui.qs.QuickQSPanelController;
import com.android.systemui.qs.customize.QSCustomizerController;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/dagger/QSFragmentComponent.class */
public interface QSFragmentComponent {

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/dagger/QSFragmentComponent$Factory.class */
    public interface Factory {
        QSFragmentComponent create(QSFragment qSFragment);
    }

    QSAnimator getQSAnimator();

    QSContainerImplController getQSContainerImplController();

    QSCustomizerController getQSCustomizerController();

    QSFooter getQSFooter();

    QSPanelController getQSPanelController();

    QSSquishinessController getQSSquishinessController();

    QuickQSPanelController getQuickQSPanelController();
}