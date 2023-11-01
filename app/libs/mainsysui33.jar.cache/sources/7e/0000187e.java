package com.android.systemui.keyboard;

import android.content.Context;
import com.android.systemui.statusbar.phone.SystemUIDialog;

/* loaded from: mainsysui33.jar:com/android/systemui/keyboard/BluetoothDialog.class */
public class BluetoothDialog extends SystemUIDialog {
    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: com.android.systemui.keyboard.BluetoothDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public BluetoothDialog(Context context) {
        super(context);
        getWindow().setType(2008);
        setShowForAllUsers(true);
    }
}