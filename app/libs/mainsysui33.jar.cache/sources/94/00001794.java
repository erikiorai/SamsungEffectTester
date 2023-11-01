package com.android.systemui.dump;

import android.util.ArrayMap;
import com.android.systemui.Dumpable;
import com.android.systemui.ProtoDumpable;
import com.android.systemui.dump.nano.SystemUIProtoDump;
import com.android.systemui.plugins.log.LogBuffer;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;

/* loaded from: mainsysui33.jar:com/android/systemui/dump/DumpManager.class */
public class DumpManager {
    public final Map<String, RegisteredDumpable<Dumpable>> dumpables = new ArrayMap();
    public final Map<String, RegisteredDumpable<LogBuffer>> buffers = new ArrayMap();

    public static /* synthetic */ void registerDumpable$default(DumpManager dumpManager, String str, Dumpable dumpable, DumpPriority dumpPriority, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: registerDumpable");
        }
        if ((i & 4) != 0) {
            dumpPriority = DumpPriority.CRITICAL;
        }
        dumpManager.registerDumpable(str, dumpable, dumpPriority);
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0020, code lost:
        if (r0 == null) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean canAssignToNameLocked(String str, Object obj) {
        Object dumpable;
        RegisteredDumpable<Dumpable> registeredDumpable = this.dumpables.get(str);
        if (registeredDumpable != null) {
            Object obj2 = (Dumpable) registeredDumpable.getDumpable();
            dumpable = obj2;
        }
        RegisteredDumpable<LogBuffer> registeredDumpable2 = this.buffers.get(str);
        dumpable = registeredDumpable2 != null ? registeredDumpable2.getDumpable() : null;
        return dumpable == null || Intrinsics.areEqual(obj, dumpable);
    }

    public final void dumpBuffer(RegisteredDumpable<LogBuffer> registeredDumpable, PrintWriter printWriter, int i) {
        printWriter.println();
        printWriter.println();
        String name = registeredDumpable.getName();
        printWriter.println("BUFFER " + name + ":");
        printWriter.println("============================================================================");
        registeredDumpable.getDumpable().dump(printWriter, i);
    }

    public final void dumpBuffers(PrintWriter printWriter, int i) {
        synchronized (this) {
            for (RegisteredDumpable<LogBuffer> registeredDumpable : this.buffers.values()) {
                dumpBuffer(registeredDumpable, printWriter, i);
            }
        }
    }

    public final void dumpCritical(PrintWriter printWriter, String[] strArr) {
        synchronized (this) {
            for (RegisteredDumpable<Dumpable> registeredDumpable : this.dumpables.values()) {
                if (registeredDumpable.getPriority() == DumpPriority.CRITICAL) {
                    dumpDumpable(registeredDumpable, printWriter, strArr);
                }
            }
        }
    }

    public final void dumpDumpable(RegisteredDumpable<Dumpable> registeredDumpable, PrintWriter printWriter, String[] strArr) {
        printWriter.println();
        String name = registeredDumpable.getName();
        printWriter.println(name + ":");
        printWriter.println("----------------------------------------------------------------------------");
        registeredDumpable.getDumpable().dump(printWriter, strArr);
    }

    public final void dumpDumpables(PrintWriter printWriter, String[] strArr) {
        synchronized (this) {
            for (RegisteredDumpable<Dumpable> registeredDumpable : this.dumpables.values()) {
                dumpDumpable(registeredDumpable, printWriter, strArr);
            }
        }
    }

    public final void dumpNormal(PrintWriter printWriter, String[] strArr, int i) {
        synchronized (this) {
            for (RegisteredDumpable<Dumpable> registeredDumpable : this.dumpables.values()) {
                if (registeredDumpable.getPriority() == DumpPriority.NORMAL) {
                    dumpDumpable(registeredDumpable, printWriter, strArr);
                }
            }
            for (RegisteredDumpable<LogBuffer> registeredDumpable2 : this.buffers.values()) {
                dumpBuffer(registeredDumpable2, printWriter, i);
            }
        }
    }

    public final void dumpProtoDumpable(ProtoDumpable protoDumpable, SystemUIProtoDump systemUIProtoDump, String[] strArr) {
        protoDumpable.dumpProto(systemUIProtoDump, strArr);
    }

    public final void dumpProtoDumpables(SystemUIProtoDump systemUIProtoDump, String[] strArr) {
        synchronized (this) {
            for (RegisteredDumpable<Dumpable> registeredDumpable : this.dumpables.values()) {
                if (registeredDumpable.getDumpable() instanceof ProtoDumpable) {
                    dumpProtoDumpable((ProtoDumpable) registeredDumpable.getDumpable(), systemUIProtoDump, strArr);
                }
            }
        }
    }

    public final void dumpProtoTarget(String str, SystemUIProtoDump systemUIProtoDump, String[] strArr) {
        synchronized (this) {
            for (RegisteredDumpable<Dumpable> registeredDumpable : this.dumpables.values()) {
                if ((registeredDumpable.getDumpable() instanceof ProtoDumpable) && StringsKt__StringsJVMKt.endsWith$default(registeredDumpable.getName(), str, false, 2, (Object) null)) {
                    dumpProtoDumpable((ProtoDumpable) registeredDumpable.getDumpable(), systemUIProtoDump, strArr);
                    return;
                }
            }
        }
    }

    public final void dumpTarget(String str, PrintWriter printWriter, String[] strArr, int i) {
        RegisteredDumpable<LogBuffer> next;
        RegisteredDumpable<Dumpable> next2;
        synchronized (this) {
            Iterator<RegisteredDumpable<Dumpable>> it = this.dumpables.values().iterator();
            do {
                if (!it.hasNext()) {
                    Iterator<RegisteredDumpable<LogBuffer>> it2 = this.buffers.values().iterator();
                    do {
                        if (!it2.hasNext()) {
                            return;
                        }
                        next = it2.next();
                    } while (!StringsKt__StringsJVMKt.endsWith$default(next.getName(), str, false, 2, (Object) null));
                    dumpBuffer(next, printWriter, i);
                    return;
                }
                next2 = it.next();
            } while (!StringsKt__StringsJVMKt.endsWith$default(next2.getName(), str, false, 2, (Object) null));
            dumpDumpable(next2, printWriter, strArr);
        }
    }

    public final void freezeBuffers() {
        synchronized (this) {
            for (RegisteredDumpable<LogBuffer> registeredDumpable : this.buffers.values()) {
                registeredDumpable.getDumpable().freeze();
            }
        }
    }

    public final void listBuffers(PrintWriter printWriter) {
        synchronized (this) {
            for (RegisteredDumpable<LogBuffer> registeredDumpable : this.buffers.values()) {
                printWriter.println(registeredDumpable.getName());
            }
        }
    }

    public final void listDumpables(PrintWriter printWriter) {
        synchronized (this) {
            for (RegisteredDumpable<Dumpable> registeredDumpable : this.dumpables.values()) {
                printWriter.println(registeredDumpable.getName());
            }
        }
    }

    public final void registerBuffer(String str, LogBuffer logBuffer) {
        synchronized (this) {
            if (!canAssignToNameLocked(str, logBuffer)) {
                throw new IllegalArgumentException("'" + str + "' is already registered");
            }
            this.buffers.put(str, new RegisteredDumpable<>(str, logBuffer, DumpPriority.NORMAL));
        }
    }

    public final void registerCriticalDumpable(String str, Dumpable dumpable) {
        registerDumpable(str, dumpable, DumpPriority.CRITICAL);
    }

    public final void registerDumpable(Dumpable dumpable) {
        synchronized (this) {
            registerDumpable$default(this, dumpable.getClass().getSimpleName(), dumpable, null, 4, null);
        }
    }

    public final void registerDumpable(String str, Dumpable dumpable) {
        registerDumpable$default(this, str, dumpable, null, 4, null);
    }

    public final void registerDumpable(String str, Dumpable dumpable, DumpPriority dumpPriority) {
        synchronized (this) {
            if (!canAssignToNameLocked(str, dumpable)) {
                throw new IllegalArgumentException("'" + str + "' is already registered");
            }
            this.dumpables.put(str, new RegisteredDumpable<>(str, dumpable, dumpPriority));
        }
    }

    public final void registerNormalDumpable(Dumpable dumpable) {
        registerNormalDumpable(dumpable.getClass().getSimpleName(), dumpable);
    }

    public final void registerNormalDumpable(String str, Dumpable dumpable) {
        registerDumpable(str, dumpable, DumpPriority.NORMAL);
    }

    public final void unfreezeBuffers() {
        synchronized (this) {
            for (RegisteredDumpable<LogBuffer> registeredDumpable : this.buffers.values()) {
                registeredDumpable.getDumpable().unfreeze();
            }
        }
    }

    public final void unregisterDumpable(String str) {
        synchronized (this) {
            this.dumpables.remove(str);
        }
    }
}