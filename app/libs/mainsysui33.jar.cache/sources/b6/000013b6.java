package com.android.systemui.controls.controller;

import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.controller.ControlsController;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/ControlsControllerKt.class */
public final class ControlsControllerKt {
    public static final ControlsController.LoadData createLoadDataObject(final List<ControlStatus> list, final List<String> list2, final boolean z) {
        return new ControlsController.LoadData(list, list2, z) { // from class: com.android.systemui.controls.controller.ControlsControllerKt$createLoadDataObject$1
            public final List<ControlStatus> allControls;
            public final boolean errorOnLoad;
            public final List<String> favoritesIds;

            {
                this.allControls = list;
                this.favoritesIds = list2;
                this.errorOnLoad = z;
            }

            @Override // com.android.systemui.controls.controller.ControlsController.LoadData
            public List<ControlStatus> getAllControls() {
                return this.allControls;
            }

            @Override // com.android.systemui.controls.controller.ControlsController.LoadData
            public boolean getErrorOnLoad() {
                return this.errorOnLoad;
            }

            @Override // com.android.systemui.controls.controller.ControlsController.LoadData
            public List<String> getFavoritesIds() {
                return this.favoritesIds;
            }
        };
    }

    public static /* synthetic */ ControlsController.LoadData createLoadDataObject$default(List list, List list2, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return createLoadDataObject(list, list2, z);
    }
}