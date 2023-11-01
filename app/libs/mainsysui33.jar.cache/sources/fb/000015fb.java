package com.android.systemui.demomode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.util.Assert;
import com.android.systemui.util.settings.GlobalSettings;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.flow.Flow;

/* loaded from: mainsysui33.jar:com/android/systemui/demomode/DemoModeController.class */
public final class DemoModeController implements CallbackController<DemoMode>, Dumpable {
    public static final Companion Companion = new Companion(null);
    public final BroadcastDispatcher broadcastDispatcher;
    public final DemoModeController$broadcastReceiver$1 broadcastReceiver;
    public final Context context;
    public final DumpManager dumpManager;
    public final GlobalSettings globalSettings;
    public boolean initialized;
    public boolean isInDemoMode;
    public final Map<String, List<DemoMode>> receiverMap;
    public final List<DemoMode> receivers = new ArrayList();
    public final DemoModeController$tracker$1 tracker;

    /* loaded from: mainsysui33.jar:com/android/systemui/demomode/DemoModeController$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.systemui.demomode.DemoModeController$tracker$1] */
    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.systemui.demomode.DemoModeController$broadcastReceiver$1] */
    public DemoModeController(Context context, DumpManager dumpManager, GlobalSettings globalSettings, BroadcastDispatcher broadcastDispatcher) {
        this.context = context;
        this.dumpManager = dumpManager;
        this.globalSettings = globalSettings;
        this.broadcastDispatcher = broadcastDispatcher;
        requestFinishDemoMode();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        List<String> list = DemoMode.COMMANDS;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        for (String str : list) {
            arrayList.add((List) linkedHashMap.put(str, new ArrayList()));
        }
        this.receiverMap = linkedHashMap;
        this.tracker = new DemoModeAvailabilityTracker(this.context, this.globalSettings) { // from class: com.android.systemui.demomode.DemoModeController$tracker$1
            @Override // com.android.systemui.demomode.DemoModeAvailabilityTracker
            public void onDemoModeAvailabilityChanged() {
                DemoModeController.access$setIsDemoModeAllowed(DemoModeController.this, isDemoModeAvailable());
            }

            @Override // com.android.systemui.demomode.DemoModeAvailabilityTracker
            public void onDemoModeFinished() {
                if (DemoModeController.this.isInDemoMode() != isInDemoMode()) {
                    DemoModeController.access$exitDemoMode(DemoModeController.this);
                }
            }

            @Override // com.android.systemui.demomode.DemoModeAvailabilityTracker
            public void onDemoModeStarted() {
                if (DemoModeController.this.isInDemoMode() != isInDemoMode()) {
                    DemoModeController.access$enterDemoMode(DemoModeController.this);
                }
            }
        };
        this.broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.demomode.DemoModeController$broadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                Bundle extras;
                if ("com.android.systemui.demo".equals(intent.getAction()) && (extras = intent.getExtras()) != null) {
                    String lowerCase = StringsKt__StringsKt.trim(extras.getString("command", "")).toString().toLowerCase(Locale.ROOT);
                    if (lowerCase.length() == 0) {
                        return;
                    }
                    try {
                        DemoModeController.this.dispatchDemoCommand(lowerCase, extras);
                    } catch (Throwable th) {
                        Log.w("DemoModeController", "Error running demo command, intent=" + intent + " " + th);
                    }
                }
            }
        };
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.demomode.DemoModeController$tracker$1.onDemoModeStarted():void] */
    public static final /* synthetic */ void access$enterDemoMode(DemoModeController demoModeController) {
        demoModeController.enterDemoMode();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.demomode.DemoModeController$tracker$1.onDemoModeFinished():void] */
    public static final /* synthetic */ void access$exitDemoMode(DemoModeController demoModeController) {
        demoModeController.exitDemoMode();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.demomode.DemoModeController$tracker$1.onDemoModeAvailabilityChanged():void] */
    public static final /* synthetic */ void access$setIsDemoModeAllowed(DemoModeController demoModeController, boolean z) {
        demoModeController.setIsDemoModeAllowed(z);
    }

    public void addCallback(DemoMode demoMode) {
        for (String str : demoMode.demoCommands()) {
            if (!this.receiverMap.containsKey(str)) {
                throw new IllegalStateException("Command (" + str + ") not recognized. See DemoMode.java for valid commands");
            }
            List<DemoMode> list = this.receiverMap.get(str);
            Intrinsics.checkNotNull(list);
            list.add(demoMode);
        }
        synchronized (this) {
            this.receivers.add(demoMode);
        }
        if (this.isInDemoMode) {
            demoMode.onDemoModeStarted();
        }
    }

    public final Flow<Bundle> demoFlowForCommand(String str) {
        return ConflatedCallbackFlow.INSTANCE.conflatedCallbackFlow(new DemoModeController$demoFlowForCommand$1(this, str, null));
    }

    public final void dispatchDemoCommand(String str, Bundle bundle) {
        Assert.isMainThread();
        if (isAvailable()) {
            if (Intrinsics.areEqual(str, "enter")) {
                enterDemoMode();
            } else if (Intrinsics.areEqual(str, "exit")) {
                exitDemoMode();
            } else if (!this.isInDemoMode) {
                enterDemoMode();
            }
            List<DemoMode> list = this.receiverMap.get(str);
            Intrinsics.checkNotNull(list);
            for (DemoMode demoMode : list) {
                demoMode.dispatchDemoCommand(str, bundle);
            }
        }
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        List<DemoModeCommandReceiver> list;
        printWriter.println("DemoModeController state -");
        boolean z = this.isInDemoMode;
        printWriter.println("  isInDemoMode=" + z);
        boolean isAvailable = isAvailable();
        printWriter.println("  isDemoModeAllowed=" + isAvailable);
        printWriter.print("  receivers=[");
        synchronized (this) {
            list = CollectionsKt___CollectionsKt.toList(this.receivers);
            Unit unit = Unit.INSTANCE;
        }
        for (DemoModeCommandReceiver demoModeCommandReceiver : list) {
            String simpleName = demoModeCommandReceiver.getClass().getSimpleName();
            printWriter.print(" " + simpleName);
        }
        printWriter.println(" ]");
        printWriter.println("  receiverMap= [");
        for (String str : this.receiverMap.keySet()) {
            printWriter.print("    " + str + " : [");
            List<DemoMode> list2 = this.receiverMap.get(str);
            Intrinsics.checkNotNull(list2);
            List<DemoMode> list3 = list2;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
            for (DemoMode demoMode : list3) {
                arrayList.add(demoMode.getClass().getSimpleName());
            }
            String joinToString$default = CollectionsKt___CollectionsKt.joinToString$default(arrayList, ",", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null);
            printWriter.println(joinToString$default + " ]");
        }
    }

    public final void enterDemoMode() {
        List<DemoModeCommandReceiver> list;
        this.isInDemoMode = true;
        Assert.isMainThread();
        synchronized (this) {
            list = CollectionsKt___CollectionsKt.toList(this.receivers);
            Unit unit = Unit.INSTANCE;
        }
        for (DemoModeCommandReceiver demoModeCommandReceiver : list) {
            demoModeCommandReceiver.onDemoModeStarted();
        }
    }

    public final void exitDemoMode() {
        List<DemoModeCommandReceiver> list;
        this.isInDemoMode = false;
        Assert.isMainThread();
        synchronized (this) {
            list = CollectionsKt___CollectionsKt.toList(this.receivers);
            Unit unit = Unit.INSTANCE;
        }
        for (DemoModeCommandReceiver demoModeCommandReceiver : list) {
            demoModeCommandReceiver.onDemoModeFinished();
        }
    }

    public final void initialize() {
        if (this.initialized) {
            throw new IllegalStateException("Already initialized");
        }
        this.initialized = true;
        this.dumpManager.registerNormalDumpable("DemoModeController", this);
        startTracking();
        this.isInDemoMode = isInDemoMode();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.android.systemui.demo");
        BroadcastDispatcher.registerReceiver$default(this.broadcastDispatcher, this.broadcastReceiver, intentFilter, null, UserHandle.ALL, 0, "android.permission.DUMP", 20, null);
    }

    public final boolean isAvailable() {
        return isDemoModeAvailable();
    }

    public final boolean isInDemoMode() {
        return this.isInDemoMode;
    }

    public void removeCallback(DemoMode demoMode) {
        synchronized (this) {
            for (String str : demoMode.demoCommands()) {
                List<DemoMode> list = this.receiverMap.get(str);
                Intrinsics.checkNotNull(list);
                list.remove(demoMode);
            }
            this.receivers.remove(demoMode);
        }
    }

    public final void requestFinishDemoMode() {
        setGlobal("sysui_tuner_demo_on", 0);
    }

    public final void requestSetDemoModeAllowed(boolean z) {
        setGlobal("sysui_demo_allowed", z ? 1 : 0);
    }

    public final void requestStartDemoMode() {
        setGlobal("sysui_tuner_demo_on", 1);
    }

    public final void setGlobal(String str, int i) {
        this.globalSettings.putInt(str, i);
    }

    public final void setIsDemoModeAllowed(boolean z) {
        if (!this.isInDemoMode || z) {
            return;
        }
        requestFinishDemoMode();
    }
}