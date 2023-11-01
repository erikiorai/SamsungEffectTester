package com.android.systemui.controls.management;

import com.android.systemui.controls.controller.ControlInfo;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlsModel.class */
public interface ControlsModel {

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlsModel$ControlsModelCallback.class */
    public interface ControlsModelCallback {
        void onFirstChange();
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlsModel$MoveHelper.class */
    public interface MoveHelper {
        boolean canMoveAfter(int i);

        boolean canMoveBefore(int i);

        void moveAfter(int i);

        void moveBefore(int i);
    }

    void changeFavoriteStatus(String str, boolean z);

    List<ElementWrapper> getElements();

    List<ControlInfo> getFavorites();

    MoveHelper getMoveHelper();
}