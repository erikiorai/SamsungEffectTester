package com.android.systemui.dump;

import android.content.Context;
import android.os.SystemClock;
import android.os.Trace;
import com.android.systemui.CoreStartable;
import com.android.systemui.R$array;
import com.android.systemui.R$string;
import com.android.systemui.dump.nano.SystemUIProtoDump;
import com.android.systemui.shared.system.UncaughtExceptionPreHandlerManager;
import com.google.protobuf.nano.MessageNano;
import java.io.BufferedOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Provider;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;

/* loaded from: mainsysui33.jar:com/android/systemui/dump/DumpHandler.class */
public final class DumpHandler {
    public static final Companion Companion = new Companion(null);
    public final Context context;
    public final DumpManager dumpManager;
    public final LogBufferEulogizer logBufferEulogizer;
    public final Map<Class<?>, Provider<CoreStartable>> startables;
    public final UncaughtExceptionPreHandlerManager uncaughtExceptionPreHandlerManager;

    /* loaded from: mainsysui33.jar:com/android/systemui/dump/DumpHandler$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public DumpHandler(Context context, DumpManager dumpManager, LogBufferEulogizer logBufferEulogizer, Map<Class<?>, Provider<CoreStartable>> map, UncaughtExceptionPreHandlerManager uncaughtExceptionPreHandlerManager) {
        this.context = context;
        this.dumpManager = dumpManager;
        this.logBufferEulogizer = logBufferEulogizer;
        this.startables = map;
        this.uncaughtExceptionPreHandlerManager = uncaughtExceptionPreHandlerManager;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dump.DumpHandler$init$1.uncaughtException(java.lang.Thread, java.lang.Throwable):void] */
    public static final /* synthetic */ LogBufferEulogizer access$getLogBufferEulogizer$p(DumpHandler dumpHandler) {
        return dumpHandler.logBufferEulogizer;
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        Trace.beginSection("DumpManager#dump()");
        long uptimeMillis = SystemClock.uptimeMillis();
        try {
            ParsedArgs parseArgs = parseArgs(strArr);
            if (Intrinsics.areEqual(parseArgs.getDumpPriority(), "CRITICAL")) {
                dumpCritical(printWriter, parseArgs);
            } else if (!Intrinsics.areEqual(parseArgs.getDumpPriority(), "NORMAL") || parseArgs.getProto()) {
                dumpParameterized(fileDescriptor, printWriter, parseArgs);
            } else {
                dumpNormal(printWriter, parseArgs);
            }
            printWriter.println();
            long uptimeMillis2 = SystemClock.uptimeMillis();
            printWriter.println("Dump took " + (uptimeMillis2 - uptimeMillis) + "ms");
            Trace.endSection();
        } catch (ArgParseException e) {
            printWriter.println(e.getMessage());
        }
    }

    public final void dumpBuffers(PrintWriter printWriter, ParsedArgs parsedArgs) {
        if (parsedArgs.getListOnly()) {
            this.dumpManager.listBuffers(printWriter);
        } else {
            this.dumpManager.dumpBuffers(printWriter, parsedArgs.getTailLength());
        }
    }

    public final void dumpConfig(PrintWriter printWriter) {
        printWriter.println("SystemUiServiceComponents configuration:");
        printWriter.print("vendor component: ");
        printWriter.println(this.context.getResources().getString(R$string.config_systemUIVendorServiceComponent));
        Set<Class<?>> keySet = this.startables.keySet();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(keySet, 10));
        Iterator<T> it = keySet.iterator();
        while (it.hasNext()) {
            arrayList.add(((Class) it.next()).getSimpleName());
        }
        List mutableList = CollectionsKt___CollectionsKt.toMutableList(arrayList);
        mutableList.add(this.context.getResources().getString(R$string.config_systemUIVendorServiceComponent));
        dumpServiceList(printWriter, "global", (String[]) mutableList.toArray(new String[0]));
        dumpServiceList(printWriter, "per-user", R$array.config_systemUIServiceComponentsPerUser);
    }

    public final void dumpCritical(PrintWriter printWriter, ParsedArgs parsedArgs) {
        this.dumpManager.dumpCritical(printWriter, parsedArgs.getRawArgs());
        dumpConfig(printWriter);
    }

    public final void dumpDumpables(PrintWriter printWriter, ParsedArgs parsedArgs) {
        if (parsedArgs.getListOnly()) {
            this.dumpManager.listDumpables(printWriter);
        } else {
            this.dumpManager.dumpDumpables(printWriter, parsedArgs.getRawArgs());
        }
    }

    public final void dumpHelp(PrintWriter printWriter) {
        printWriter.println("Let <invocation> be:");
        printWriter.println("$ adb shell dumpsys activity service com.android.systemui/.SystemUIService");
        printWriter.println();
        printWriter.println("Most common usage:");
        printWriter.println("$ <invocation> <targets>");
        printWriter.println("$ <invocation> NotifLog");
        printWriter.println("$ <invocation> StatusBar FalsingManager BootCompleteCacheImpl");
        printWriter.println("etc.");
        printWriter.println();
        printWriter.println("Special commands:");
        printWriter.println("$ <invocation> dumpables");
        printWriter.println("$ <invocation> buffers");
        printWriter.println("$ <invocation> bugreport-critical");
        printWriter.println("$ <invocation> bugreport-normal");
        printWriter.println("$ <invocation> config");
        printWriter.println();
        printWriter.println("Targets can be listed:");
        printWriter.println("$ <invocation> --list");
        printWriter.println("$ <invocation> dumpables --list");
        printWriter.println("$ <invocation> buffers --list");
        printWriter.println();
        printWriter.println("Show only the most recent N lines of buffers");
        printWriter.println("$ <invocation> NotifLog --tail 30");
    }

    public final void dumpNormal(PrintWriter printWriter, ParsedArgs parsedArgs) {
        this.dumpManager.dumpNormal(printWriter, parsedArgs.getRawArgs(), parsedArgs.getTailLength());
        this.logBufferEulogizer.readEulogyIfPresent(printWriter);
    }

    public final void dumpParameterized(FileDescriptor fileDescriptor, PrintWriter printWriter, ParsedArgs parsedArgs) {
        String command = parsedArgs.getCommand();
        if (command != null) {
            switch (command.hashCode()) {
                case -1354792126:
                    if (command.equals("config")) {
                        dumpConfig(printWriter);
                        return;
                    }
                    break;
                case -1353714459:
                    if (command.equals("dumpables")) {
                        dumpDumpables(printWriter, parsedArgs);
                        return;
                    }
                    break;
                case -1045369428:
                    if (command.equals("bugreport-normal")) {
                        dumpNormal(printWriter, parsedArgs);
                        return;
                    }
                    break;
                case 3198785:
                    if (command.equals("help")) {
                        dumpHelp(printWriter);
                        return;
                    }
                    break;
                case 227996723:
                    if (command.equals("buffers")) {
                        dumpBuffers(printWriter, parsedArgs);
                        return;
                    }
                    break;
                case 842828580:
                    if (command.equals("bugreport-critical")) {
                        dumpCritical(printWriter, parsedArgs);
                        return;
                    }
                    break;
            }
        }
        if (parsedArgs.getProto()) {
            dumpProtoTargets(parsedArgs.getNonFlagArgs(), fileDescriptor, parsedArgs);
        } else {
            dumpTargets(parsedArgs.getNonFlagArgs(), printWriter, parsedArgs);
        }
    }

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    public final void dumpProtoTargets(List<String> list, FileDescriptor fileDescriptor, ParsedArgs parsedArgs) {
        SystemUIProtoDump systemUIProtoDump = new SystemUIProtoDump();
        if (!list.isEmpty()) {
            for (String str : list) {
                this.dumpManager.dumpProtoTarget(str, systemUIProtoDump, parsedArgs.getRawArgs());
            }
        } else {
            this.dumpManager.dumpProtoDumpables(systemUIProtoDump, parsedArgs.getRawArgs());
        }
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fileDescriptor));
        try {
            bufferedOutputStream.write(MessageNano.toByteArray(systemUIProtoDump));
            bufferedOutputStream.flush();
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(bufferedOutputStream, (Throwable) null);
        } finally {
        }
    }

    public final void dumpServiceList(PrintWriter printWriter, String str, int i) {
        dumpServiceList(printWriter, str, this.context.getResources().getStringArray(i));
    }

    public final void dumpServiceList(PrintWriter printWriter, String str, String[] strArr) {
        printWriter.print(str);
        printWriter.print(": ");
        if (strArr == null) {
            printWriter.println("N/A");
            return;
        }
        printWriter.print(strArr.length);
        printWriter.println(" services");
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            printWriter.print("  ");
            printWriter.print(i);
            printWriter.print(": ");
            printWriter.println(strArr[i]);
        }
    }

    public final void dumpTargets(List<String> list, PrintWriter printWriter, ParsedArgs parsedArgs) {
        if (!list.isEmpty()) {
            for (String str : list) {
                this.dumpManager.dumpTarget(str, printWriter, parsedArgs.getRawArgs(), parsedArgs.getTailLength());
            }
        } else if (!parsedArgs.getListOnly()) {
            printWriter.println("Nothing to dump :(");
        } else {
            printWriter.println("Dumpables:");
            this.dumpManager.listDumpables(printWriter);
            printWriter.println();
            printWriter.println("Buffers:");
            this.dumpManager.listBuffers(printWriter);
        }
    }

    public final void init() {
        this.uncaughtExceptionPreHandlerManager.registerHandler(new Thread.UncaughtExceptionHandler() { // from class: com.android.systemui.dump.DumpHandler$init$1
            @Override // java.lang.Thread.UncaughtExceptionHandler
            public final void uncaughtException(Thread thread, Throwable th) {
                if (th instanceof Exception) {
                    DumpHandler.access$getLogBufferEulogizer$p(DumpHandler.this).record((Exception) th);
                }
            }
        });
    }

    public final ParsedArgs parseArgs(String[] strArr) {
        String[] strArr2;
        List mutableList = ArraysKt___ArraysKt.toMutableList(strArr);
        ParsedArgs parsedArgs = new ParsedArgs(strArr, mutableList);
        Iterator<String> it = mutableList.iterator();
        while (it.hasNext()) {
            String next = it.next();
            if (StringsKt__StringsJVMKt.startsWith$default(next, "-", false, 2, (Object) null)) {
                it.remove();
                switch (next.hashCode()) {
                    case -1616754616:
                        if (!next.equals("--proto")) {
                            throw new ArgParseException("Unknown flag: " + next);
                        }
                        parsedArgs.setProto(true);
                        break;
                    case 1492:
                        if (!next.equals("-a")) {
                            throw new ArgParseException("Unknown flag: " + next);
                        }
                        break;
                    case 1499:
                        if (!next.equals("-h")) {
                            throw new ArgParseException("Unknown flag: " + next);
                        }
                        parsedArgs.setCommand("help");
                        break;
                    case 1503:
                        if (!next.equals("-l")) {
                            throw new ArgParseException("Unknown flag: " + next);
                        }
                        parsedArgs.setListOnly(true);
                        break;
                    case 1511:
                        if (!next.equals("-t")) {
                            throw new ArgParseException("Unknown flag: " + next);
                        }
                        parsedArgs.setTailLength(((Number) readArgument(it, next, new Function1<String, Integer>() { // from class: com.android.systemui.dump.DumpHandler$parseArgs$2
                            /* JADX DEBUG: Method merged with bridge method */
                            public final Integer invoke(String str) {
                                return Integer.valueOf(Integer.parseInt(str));
                            }
                        })).intValue());
                        break;
                    case 1056887741:
                        if (!next.equals("--dump-priority")) {
                            throw new ArgParseException("Unknown flag: " + next);
                        }
                        parsedArgs.setDumpPriority((String) readArgument(it, "--dump-priority", new Function1<String, String>() { // from class: com.android.systemui.dump.DumpHandler$parseArgs$1
                            /* JADX DEBUG: Method merged with bridge method */
                            public final String invoke(String str) {
                                String[] strArr3;
                                strArr3 = DumpHandlerKt.PRIORITY_OPTIONS;
                                if (ArraysKt___ArraysKt.contains(strArr3, str)) {
                                    return str;
                                }
                                throw new IllegalArgumentException();
                            }
                        }));
                        break;
                    case 1333069025:
                        if (!next.equals("--help")) {
                            throw new ArgParseException("Unknown flag: " + next);
                        }
                        parsedArgs.setCommand("help");
                        break;
                    case 1333192254:
                        if (!next.equals("--list")) {
                            throw new ArgParseException("Unknown flag: " + next);
                        }
                        parsedArgs.setListOnly(true);
                        break;
                    case 1333422576:
                        if (!next.equals("--tail")) {
                            throw new ArgParseException("Unknown flag: " + next);
                        }
                        parsedArgs.setTailLength(((Number) readArgument(it, next, new Function1<String, Integer>() { // from class: com.android.systemui.dump.DumpHandler$parseArgs$2
                            /* JADX DEBUG: Method merged with bridge method */
                            public final Integer invoke(String str) {
                                return Integer.valueOf(Integer.parseInt(str));
                            }
                        })).intValue());
                        break;
                    default:
                        throw new ArgParseException("Unknown flag: " + next);
                }
            }
        }
        if (parsedArgs.getCommand() == null && (!mutableList.isEmpty())) {
            strArr2 = DumpHandlerKt.COMMANDS;
            if (ArraysKt___ArraysKt.contains(strArr2, mutableList.get(0))) {
                parsedArgs.setCommand((String) mutableList.remove(0));
            }
        }
        return parsedArgs;
    }

    public final <T> T readArgument(Iterator<String> it, String str, Function1<? super String, ? extends T> function1) {
        if (!it.hasNext()) {
            throw new ArgParseException("Missing argument for " + str);
        }
        String next = it.next();
        try {
            T t = (T) function1.invoke(next);
            it.remove();
            return t;
        } catch (Exception e) {
            throw new ArgParseException("Invalid argument '" + next + "' for flag " + str);
        }
    }
}