package com.android.systemui;

import com.android.internal.annotations.GuardedBy;
import com.android.systemui.BootCompleteCache;
import com.android.systemui.dump.DumpManager;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/BootCompleteCacheImpl.class */
public final class BootCompleteCacheImpl implements BootCompleteCache, Dumpable {
    public static final Companion Companion = new Companion(null);
    public final AtomicBoolean bootComplete;
    @GuardedBy({"listeners"})
    public final List<WeakReference<BootCompleteCache.BootCompleteListener>> listeners;

    /* loaded from: mainsysui33.jar:com/android/systemui/BootCompleteCacheImpl$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public BootCompleteCacheImpl(DumpManager dumpManager) {
        DumpManager.registerDumpable$default(dumpManager, "BootCompleteCacheImpl", this, null, 4, null);
        this.listeners = new ArrayList();
        this.bootComplete = new AtomicBoolean(false);
    }

    @Override // com.android.systemui.BootCompleteCache
    public boolean addListener(BootCompleteCache.BootCompleteListener bootCompleteListener) {
        if (this.bootComplete.get()) {
            return true;
        }
        synchronized (this.listeners) {
            if (this.bootComplete.get()) {
                return true;
            }
            this.listeners.add(new WeakReference<>(bootCompleteListener));
            return false;
        }
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("BootCompleteCache state:");
        boolean isBootComplete = isBootComplete();
        printWriter.println("  boot complete: " + isBootComplete);
        if (isBootComplete()) {
            return;
        }
        printWriter.println("  listeners:");
        synchronized (this.listeners) {
            Iterator<T> it = this.listeners.iterator();
            while (it.hasNext()) {
                WeakReference weakReference = (WeakReference) it.next();
                printWriter.println("    " + weakReference);
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    @Override // com.android.systemui.BootCompleteCache
    public boolean isBootComplete() {
        return this.bootComplete.get();
    }

    public final void setBootComplete() {
        if (this.bootComplete.compareAndSet(false, true)) {
            synchronized (this.listeners) {
                Iterator<T> it = this.listeners.iterator();
                while (it.hasNext()) {
                    BootCompleteCache.BootCompleteListener bootCompleteListener = (BootCompleteCache.BootCompleteListener) ((WeakReference) it.next()).get();
                    if (bootCompleteListener != null) {
                        bootCompleteListener.onBootComplete();
                    }
                }
                this.listeners.clear();
                Unit unit = Unit.INSTANCE;
            }
        }
    }
}