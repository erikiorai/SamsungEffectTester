package com.android.systemui.flags;

import com.android.systemui.statusbar.commandline.Command;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/FlagCommand.class */
public class FlagCommand implements Command {
    public final Map<Integer, Flag<?>> mAllFlags;
    public final FeatureFlagsDebug mFeatureFlags;
    public final List<String> mOnCommands = List.of("true", "on", "1", "enabled");
    public final List<String> mOffCommands = List.of("false", "off", "0", "disable");
    public final List<String> mSetCommands = List.of("set", "put");

    public FlagCommand(FeatureFlagsDebug featureFlagsDebug, Map<Integer, Flag<?>> map) {
        this.mFeatureFlags = featureFlagsDebug;
        this.mAllFlags = map;
    }

    public void execute(PrintWriter printWriter, List<String> list) {
        int i;
        if (list.size() == 0) {
            printWriter.println("Error: no flag id supplied");
            help(printWriter);
            printWriter.println();
            printKnownFlags(printWriter);
            return;
        }
        try {
            int parseInt = Integer.parseInt(list.get(0));
            i = parseInt;
            if (!this.mAllFlags.containsKey(Integer.valueOf(parseInt))) {
                printWriter.println("Unknown flag id: " + parseInt);
                printWriter.println();
                printKnownFlags(printWriter);
                return;
            }
        } catch (NumberFormatException e) {
            int flagNameToId = flagNameToId(list.get(0));
            i = flagNameToId;
            if (flagNameToId == 0) {
                printWriter.println("Invalid flag. Must an integer id or flag name: " + list.get(0));
                return;
            }
        }
        Flag<?> flag = this.mAllFlags.get(Integer.valueOf(i));
        String lowerCase = list.size() > 1 ? list.get(1).toLowerCase() : "";
        if ("erase".equals(lowerCase) || "reset".equals(lowerCase)) {
            if (list.size() <= 2) {
                this.mFeatureFlags.eraseFlag(flag);
                return;
            }
            printWriter.println("Invalid number of arguments to reset a flag.");
            help(printWriter);
            return;
        }
        boolean z = list.size() != 1;
        if (isBooleanFlag(flag)) {
            if (list.size() > 2) {
                printWriter.println("Invalid number of arguments for a boolean flag.");
                help(printWriter);
                return;
            }
            boolean isBooleanFlagEnabled = isBooleanFlagEnabled(flag);
            if ("toggle".equals(lowerCase)) {
                isBooleanFlagEnabled = !isBooleanFlagEnabled;
            } else if (this.mOnCommands.contains(lowerCase)) {
                isBooleanFlagEnabled = true;
            } else if (this.mOffCommands.contains(lowerCase)) {
                isBooleanFlagEnabled = false;
            } else if (z) {
                printWriter.println("Invalid on/off argument supplied");
                help(printWriter);
                return;
            }
            printWriter.println("Flag " + i + " is " + isBooleanFlagEnabled);
            printWriter.flush();
            if (z) {
                this.mFeatureFlags.setBooleanFlagInternal(flag, isBooleanFlagEnabled);
            }
        } else if (isStringFlag(flag)) {
            if (!z) {
                printWriter.println("Flag " + i + " is " + getStringFlag(flag));
            } else if (list.size() != 3) {
                printWriter.println("Invalid number of arguments a StringFlag.");
                help(printWriter);
            } else if (!this.mSetCommands.contains(lowerCase)) {
                printWriter.println("Unknown command: " + lowerCase);
                help(printWriter);
            } else {
                printWriter.println("Setting Flag " + i + " to " + list.get(2));
                printWriter.flush();
                this.mFeatureFlags.setStringFlagInternal(flag, list.get(2));
            }
        } else if (isIntFlag(flag)) {
            if (!z) {
                printWriter.println("Flag " + i + " is " + getIntFlag(flag));
            } else if (list.size() != 3) {
                printWriter.println("Invalid number of arguments for an IntFlag.");
                help(printWriter);
            } else if (!this.mSetCommands.contains(lowerCase)) {
                printWriter.println("Unknown command: " + lowerCase);
                help(printWriter);
            } else {
                int parseInt2 = Integer.parseInt(list.get(2));
                printWriter.println("Setting Flag " + i + " to " + parseInt2);
                printWriter.flush();
                this.mFeatureFlags.setIntFlagInternal(flag, parseInt2);
            }
        }
    }

    public final int flagNameToId(String str) {
        Map<String, Flag<?>> knownFlags = FlagsFactory.INSTANCE.getKnownFlags();
        for (String str2 : knownFlags.keySet()) {
            if (str.equals(str2)) {
                return knownFlags.get(str2).getId();
            }
        }
        return 0;
    }

    public final int getIntFlag(Flag<?> flag) {
        if (flag instanceof IntFlag) {
            return this.mFeatureFlags.getInt((IntFlag) flag);
        }
        if (flag instanceof ResourceIntFlag) {
            return this.mFeatureFlags.getInt((ResourceIntFlag) flag);
        }
        return 0;
    }

    public final String getStringFlag(Flag<?> flag) {
        return flag instanceof StringFlag ? this.mFeatureFlags.getString((StringFlag) flag) : flag instanceof ResourceStringFlag ? this.mFeatureFlags.getString((ResourceStringFlag) flag) : "";
    }

    public void help(PrintWriter printWriter) {
        printWriter.println("Usage: adb shell cmd statusbar flag <id> [options]");
        printWriter.println();
        printWriter.println("  Boolean Flag Options: [true|false|1|0|on|off|enable|disable|toggle|erase|reset]");
        printWriter.println("  String Flag Options: [set|put \"<value>\"]");
        printWriter.println("  Int Flag Options: [set|put <value>]");
        printWriter.println();
        printWriter.println("The id can either be a numeric integer or the corresponding field name");
        printWriter.println("If no argument is supplied after the id, the flags runtime value is output");
    }

    public final boolean isBooleanFlag(Flag<?> flag) {
        return (flag instanceof BooleanFlag) || (flag instanceof ResourceBooleanFlag) || (flag instanceof SysPropFlag) || (flag instanceof DeviceConfigBooleanFlag);
    }

    public final boolean isBooleanFlagEnabled(Flag<?> flag) {
        if (flag instanceof ReleasedFlag) {
            return this.mFeatureFlags.isEnabled((ReleasedFlag) flag);
        }
        if (flag instanceof UnreleasedFlag) {
            return this.mFeatureFlags.isEnabled((UnreleasedFlag) flag);
        }
        if (flag instanceof ResourceBooleanFlag) {
            return this.mFeatureFlags.isEnabled((ResourceBooleanFlag) flag);
        }
        if (flag instanceof SysPropFlag) {
            return this.mFeatureFlags.isEnabled((SysPropBooleanFlag) flag);
        }
        return false;
    }

    public final boolean isIntFlag(Flag<?> flag) {
        return (flag instanceof IntFlag) || (flag instanceof ResourceIntFlag);
    }

    public final boolean isStringFlag(Flag<?> flag) {
        return (flag instanceof StringFlag) || (flag instanceof ResourceStringFlag);
    }

    public final void printKnownFlags(PrintWriter printWriter) {
        int i;
        Map<String, Flag<?>> knownFlags = FlagsFactory.INSTANCE.getKnownFlags();
        Iterator<String> it = knownFlags.keySet().iterator();
        int i2 = 0;
        while (true) {
            i = i2;
            if (!it.hasNext()) {
                break;
            }
            i2 = Math.max(i, it.next().length());
        }
        printWriter.println("Known Flags:");
        printWriter.print("Flag Name");
        for (int i3 = 0; i3 < (i - 9) + 1; i3++) {
            printWriter.print(" ");
        }
        printWriter.println("ID   Value");
        for (int i4 = 0; i4 < i; i4++) {
            printWriter.print("=");
        }
        printWriter.println(" ==== ========");
        for (String str : knownFlags.keySet()) {
            Flag<?> flag = knownFlags.get(str);
            int id = flag.getId();
            if (id != 0 && this.mAllFlags.containsKey(Integer.valueOf(id))) {
                printWriter.print(str);
                int length = str.length();
                for (int i5 = 0; i5 < (i - length) + 1; i5++) {
                    printWriter.print(" ");
                }
                printWriter.printf("%-4d ", Integer.valueOf(id));
                if (isBooleanFlag(flag)) {
                    printWriter.println(isBooleanFlagEnabled(this.mAllFlags.get(Integer.valueOf(id))));
                } else if (isStringFlag(flag)) {
                    printWriter.println(getStringFlag(flag));
                } else if (isIntFlag(flag)) {
                    printWriter.println(getIntFlag(flag));
                } else {
                    printWriter.println("<unknown flag type>");
                }
            }
        }
    }
}