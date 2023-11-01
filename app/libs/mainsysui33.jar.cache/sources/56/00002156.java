package com.android.systemui.qs;

import android.content.ComponentName;
import android.text.TextUtils;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.nano.QsTileState;
import com.android.systemui.util.nano.ComponentNameProto;
import kotlin.text.StringsKt__StringsJVMKt;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/TileStateToProtoKt.class */
public final class TileStateToProtoKt {
    public static final QsTileState toProto(QSTile.State state) {
        if (TextUtils.isEmpty(state.spec)) {
            return null;
        }
        QsTileState qsTileState = new QsTileState();
        if (StringsKt__StringsJVMKt.startsWith$default(state.spec, "custom(", false, 2, (Object) null)) {
            ComponentNameProto componentNameProto = new ComponentNameProto();
            ComponentName componentFromSpec = CustomTile.getComponentFromSpec(state.spec);
            componentNameProto.packageName = componentFromSpec.getPackageName();
            componentNameProto.className = componentFromSpec.getClassName();
            qsTileState.setComponentName(componentNameProto);
        } else {
            qsTileState.setSpec(state.spec);
        }
        int i = state.state;
        int i2 = 0;
        if (i != 0) {
            i2 = i != 1 ? i != 2 ? 0 : 2 : 1;
        }
        qsTileState.state = i2;
        CharSequence charSequence = state.label;
        if (charSequence != null) {
            qsTileState.setLabel(charSequence.toString());
        }
        CharSequence charSequence2 = state.secondaryLabel;
        if (charSequence2 != null) {
            qsTileState.setSecondaryLabel(charSequence2.toString());
        }
        if (state instanceof QSTile.BooleanState) {
            qsTileState.setBooleanState(((QSTile.BooleanState) state).value);
        }
        return qsTileState;
    }
}