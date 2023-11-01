package com.android.systemui.media.controls.pipeline;

import android.content.Context;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.media.InfoMediaManager;
import com.android.settingslib.media.LocalMediaManager;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/pipeline/LocalMediaManagerFactory.class */
public final class LocalMediaManagerFactory {
    public final Context context;
    public final LocalBluetoothManager localBluetoothManager;

    public LocalMediaManagerFactory(Context context, LocalBluetoothManager localBluetoothManager) {
        this.context = context;
        this.localBluetoothManager = localBluetoothManager;
    }

    public final LocalMediaManager create(String str) {
        return new LocalMediaManager(this.context, this.localBluetoothManager, new InfoMediaManager(this.context, str, null, this.localBluetoothManager), str);
    }
}