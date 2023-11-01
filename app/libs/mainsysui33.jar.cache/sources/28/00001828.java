package com.android.systemui.globalactions;

import com.android.systemui.statusbar.phone.CentralSurfaces;
import java.util.function.Function;

/* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda9.class */
public final /* synthetic */ class GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda9 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return Boolean.valueOf(((CentralSurfaces) obj).isKeyguardShowing());
    }
}