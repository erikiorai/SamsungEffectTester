package com.android.systemui.flags;

import android.provider.DeviceConfig;
import com.android.systemui.flags.ServerFlagReader;
import com.android.systemui.util.DeviceConfigProxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/ServerFlagReaderImpl.class */
public final class ServerFlagReaderImpl implements ServerFlagReader {
    public final DeviceConfigProxy deviceConfig;
    public final Executor executor;
    public final String namespace;
    public final List<Pair<ServerFlagReader.ChangeListener, Collection<Flag<?>>>> listeners = new ArrayList();
    public final ServerFlagReaderImpl$onPropertiesChangedListener$1 onPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.flags.ServerFlagReaderImpl$onPropertiesChangedListener$1
        public void onPropertiesChanged(DeviceConfig.Properties properties) {
            String str;
            List<Pair> list;
            String serverOverrideName;
            String namespace = properties.getNamespace();
            str = ServerFlagReaderImpl.this.namespace;
            if (Intrinsics.areEqual(namespace, str)) {
                list = ServerFlagReaderImpl.this.listeners;
                for (Pair pair : list) {
                    ServerFlagReader.ChangeListener changeListener = (ServerFlagReader.ChangeListener) pair.component1();
                    Collection<Flag> collection = (Collection) pair.component2();
                    Iterator it = properties.getKeyset().iterator();
                    while (true) {
                        if (it.hasNext()) {
                            String str2 = (String) it.next();
                            for (Flag flag : collection) {
                                serverOverrideName = ServerFlagReaderImpl.this.getServerOverrideName(flag.getId());
                                if (Intrinsics.areEqual(str2, serverOverrideName)) {
                                    changeListener.onChange();
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    };

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.flags.ServerFlagReaderImpl$onPropertiesChangedListener$1] */
    public ServerFlagReaderImpl(String str, DeviceConfigProxy deviceConfigProxy, Executor executor) {
        this.namespace = str;
        this.deviceConfig = deviceConfigProxy;
        this.executor = executor;
    }

    public final String getServerOverrideName(int i) {
        return "flag_override_" + i;
    }

    @Override // com.android.systemui.flags.ServerFlagReader
    public boolean hasOverride(String str, String str2) {
        return (StringsKt__StringsJVMKt.isBlank(str) || StringsKt__StringsJVMKt.isBlank(str2) || this.deviceConfig.getProperty(str, str2) == null) ? false : true;
    }

    @Override // com.android.systemui.flags.ServerFlagReader
    public void listenForChanges(Collection<? extends Flag<?>> collection, ServerFlagReader.ChangeListener changeListener) {
        if (this.listeners.isEmpty()) {
            this.deviceConfig.addOnPropertiesChangedListener(this.namespace, this.executor, this.onPropertiesChangedListener);
        }
        this.listeners.add(new Pair<>(changeListener, collection));
    }

    @Override // com.android.systemui.flags.ServerFlagReader
    public boolean readServerOverride(String str, String str2, boolean z) {
        return (StringsKt__StringsJVMKt.isBlank(str) || StringsKt__StringsJVMKt.isBlank(str2) || !this.deviceConfig.getBoolean(str, str2, z)) ? false : true;
    }
}