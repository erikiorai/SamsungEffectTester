package com.android.systemui.qs;

import com.android.systemui.animation.Expandable;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/FgsManagerController.class */
public interface FgsManagerController {

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/FgsManagerController$OnDialogDismissedListener.class */
    public interface OnDialogDismissedListener {
        void onDialogDismissed();
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/FgsManagerController$OnNumberOfPackagesChangedListener.class */
    public interface OnNumberOfPackagesChangedListener {
        void onNumberOfPackagesChanged(int i);
    }

    void addOnDialogDismissedListener(OnDialogDismissedListener onDialogDismissedListener);

    void addOnNumberOfPackagesChangedListener(OnNumberOfPackagesChangedListener onNumberOfPackagesChangedListener);

    boolean getNewChangesSinceDialogWasDismissed();

    int getNumRunningPackages();

    StateFlow<Boolean> getShowFooterDot();

    void init();

    StateFlow<Boolean> isAvailable();

    void removeOnDialogDismissedListener(OnDialogDismissedListener onDialogDismissedListener);

    void removeOnNumberOfPackagesChangedListener(OnNumberOfPackagesChangedListener onNumberOfPackagesChangedListener);

    void showDialog(Expandable expandable);

    int visibleButtonsCount();
}