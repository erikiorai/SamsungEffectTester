package com.android.systemui.dump;

import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/dump/ParsedArgs.class */
public final class ParsedArgs {
    public String command;
    public String dumpPriority;
    public boolean listOnly;
    public final List<String> nonFlagArgs;
    public boolean proto;
    public final String[] rawArgs;
    public int tailLength;

    public ParsedArgs(String[] strArr, List<String> list) {
        this.rawArgs = strArr;
        this.nonFlagArgs = list;
    }

    public final String getCommand() {
        return this.command;
    }

    public final String getDumpPriority() {
        return this.dumpPriority;
    }

    public final boolean getListOnly() {
        return this.listOnly;
    }

    public final List<String> getNonFlagArgs() {
        return this.nonFlagArgs;
    }

    public final boolean getProto() {
        return this.proto;
    }

    public final String[] getRawArgs() {
        return this.rawArgs;
    }

    public final int getTailLength() {
        return this.tailLength;
    }

    public final void setCommand(String str) {
        this.command = str;
    }

    public final void setDumpPriority(String str) {
        this.dumpPriority = str;
    }

    public final void setListOnly(boolean z) {
        this.listOnly = z;
    }

    public final void setProto(boolean z) {
        this.proto = z;
    }

    public final void setTailLength(int i) {
        this.tailLength = i;
    }
}