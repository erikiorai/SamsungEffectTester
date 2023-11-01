package com.android.systemui.keyguard;

import android.os.Trace;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.ScreenLifecycle;
import java.io.PrintWriter;
import java.util.function.Consumer;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ScreenLifecycle.class */
public class ScreenLifecycle extends Lifecycle<Observer> implements Dumpable {
    public int mScreenState = 0;

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ScreenLifecycle$Observer.class */
    public interface Observer {
        default void onScreenTurnedOff() {
        }

        default void onScreenTurnedOn() {
        }

        default void onScreenTurningOff() {
        }

        default void onScreenTurningOn() {
        }
    }

    public ScreenLifecycle(DumpManager dumpManager) {
        dumpManager.registerDumpable(getClass().getSimpleName(), this);
    }

    public void dispatchScreenTurnedOff() {
        setScreenState(0);
        dispatch(new Consumer() { // from class: com.android.systemui.keyguard.ScreenLifecycle$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((ScreenLifecycle.Observer) obj).onScreenTurnedOff();
            }
        });
    }

    public void dispatchScreenTurnedOn() {
        setScreenState(2);
        dispatch(new Consumer() { // from class: com.android.systemui.keyguard.ScreenLifecycle$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((ScreenLifecycle.Observer) obj).onScreenTurnedOn();
            }
        });
    }

    public void dispatchScreenTurningOff() {
        setScreenState(3);
        dispatch(new Consumer() { // from class: com.android.systemui.keyguard.ScreenLifecycle$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((ScreenLifecycle.Observer) obj).onScreenTurningOff();
            }
        });
    }

    public void dispatchScreenTurningOn() {
        setScreenState(1);
        dispatch(new Consumer() { // from class: com.android.systemui.keyguard.ScreenLifecycle$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((ScreenLifecycle.Observer) obj).onScreenTurningOn();
            }
        });
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("ScreenLifecycle:");
        printWriter.println("  mScreenState=" + this.mScreenState);
    }

    public int getScreenState() {
        return this.mScreenState;
    }

    public final void setScreenState(int i) {
        this.mScreenState = i;
        Trace.traceCounter(4096L, "screenState", i);
    }
}