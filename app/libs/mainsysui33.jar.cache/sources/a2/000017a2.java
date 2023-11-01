package com.android.systemui.dump;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/dump/RegisteredDumpable.class */
public final class RegisteredDumpable<T> {
    public final T dumpable;
    public final String name;
    public final DumpPriority priority;

    public RegisteredDumpable(String str, T t, DumpPriority dumpPriority) {
        this.name = str;
        this.dumpable = t;
        this.priority = dumpPriority;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof RegisteredDumpable) {
            RegisteredDumpable registeredDumpable = (RegisteredDumpable) obj;
            return Intrinsics.areEqual(this.name, registeredDumpable.name) && Intrinsics.areEqual(this.dumpable, registeredDumpable.dumpable) && this.priority == registeredDumpable.priority;
        }
        return false;
    }

    public final T getDumpable() {
        return this.dumpable;
    }

    public final String getName() {
        return this.name;
    }

    public final DumpPriority getPriority() {
        return this.priority;
    }

    public int hashCode() {
        int hashCode = this.name.hashCode();
        T t = this.dumpable;
        return (((hashCode * 31) + (t == null ? 0 : t.hashCode())) * 31) + this.priority.hashCode();
    }

    public String toString() {
        String str = this.name;
        T t = this.dumpable;
        DumpPriority dumpPriority = this.priority;
        return "RegisteredDumpable(name=" + str + ", dumpable=" + t + ", priority=" + dumpPriority + ")";
    }
}