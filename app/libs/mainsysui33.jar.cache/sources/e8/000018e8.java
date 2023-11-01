package com.android.systemui.keyguard;

import android.os.Trace;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.unfold.updates.screen.ScreenStatusProvider;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/LifecycleScreenStatusProvider.class */
public final class LifecycleScreenStatusProvider implements ScreenStatusProvider, ScreenLifecycle.Observer {
    public final List<ScreenStatusProvider.ScreenListener> listeners;

    public LifecycleScreenStatusProvider(ScreenLifecycle screenLifecycle) {
        screenLifecycle.addObserver(this);
        this.listeners = new ArrayList();
    }

    /* JADX DEBUG: Method merged with bridge method */
    public void addCallback(ScreenStatusProvider.ScreenListener screenListener) {
        this.listeners.add(screenListener);
    }

    @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
    public void onScreenTurnedOn() {
        if (!Trace.isTagEnabled(4096L)) {
            for (ScreenStatusProvider.ScreenListener screenListener : this.listeners) {
                screenListener.onScreenTurnedOn();
            }
            return;
        }
        Trace.traceBegin(4096L, "LifecycleScreenStatusProvider#onScreenTurnedOn");
        try {
            for (ScreenStatusProvider.ScreenListener screenListener2 : this.listeners) {
                screenListener2.onScreenTurnedOn();
            }
            Unit unit = Unit.INSTANCE;
            Trace.traceEnd(4096L);
        } catch (Throwable th) {
            Trace.traceEnd(4096L);
            throw th;
        }
    }

    @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
    public void onScreenTurningOff() {
        if (!Trace.isTagEnabled(4096L)) {
            for (ScreenStatusProvider.ScreenListener screenListener : this.listeners) {
                screenListener.onScreenTurningOff();
            }
            return;
        }
        Trace.traceBegin(4096L, "LifecycleScreenStatusProvider#onScreenTurningOff");
        try {
            for (ScreenStatusProvider.ScreenListener screenListener2 : this.listeners) {
                screenListener2.onScreenTurningOff();
            }
            Unit unit = Unit.INSTANCE;
            Trace.traceEnd(4096L);
        } catch (Throwable th) {
            Trace.traceEnd(4096L);
            throw th;
        }
    }

    @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
    public void onScreenTurningOn() {
        if (!Trace.isTagEnabled(4096L)) {
            for (ScreenStatusProvider.ScreenListener screenListener : this.listeners) {
                screenListener.onScreenTurningOn();
            }
            return;
        }
        Trace.traceBegin(4096L, "LifecycleScreenStatusProvider#onScreenTurningOn");
        try {
            for (ScreenStatusProvider.ScreenListener screenListener2 : this.listeners) {
                screenListener2.onScreenTurningOn();
            }
            Unit unit = Unit.INSTANCE;
            Trace.traceEnd(4096L);
        } catch (Throwable th) {
            Trace.traceEnd(4096L);
            throw th;
        }
    }
}