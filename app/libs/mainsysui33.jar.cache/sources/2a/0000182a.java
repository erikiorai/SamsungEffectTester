package com.android.systemui.globalactions;

import com.android.systemui.statusbar.phone.CentralSurfaces;
import java.util.function.Consumer;

/* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$BugReportAction$$ExternalSyntheticLambda0.class */
public final /* synthetic */ class GlobalActionsDialogLite$BugReportAction$$ExternalSyntheticLambda0 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((CentralSurfaces) obj).collapseShadeForBugreport();
    }
}