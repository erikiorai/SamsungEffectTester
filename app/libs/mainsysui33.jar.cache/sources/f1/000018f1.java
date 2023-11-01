package com.android.systemui.keyguard;

import android.app.IWallpaperManager;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.Trace;
import android.util.DisplayMetrics;
import com.android.systemui.Dumpable;
import com.android.systemui.R$dimen;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import java.io.PrintWriter;
import java.util.function.Consumer;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/WakefulnessLifecycle.class */
public class WakefulnessLifecycle extends Lifecycle<Observer> implements Dumpable {
    public final Context mContext;
    public final DisplayMetrics mDisplayMetrics;
    public final IWallpaperManager mWallpaperManagerService;
    public int mWakefulness = 2;
    public int mLastWakeReason = 0;
    public Point mLastWakeOriginLocation = null;
    public int mLastSleepReason = 0;
    public Point mLastSleepOriginLocation = null;

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/WakefulnessLifecycle$Observer.class */
    public interface Observer {
        default void onFinishedGoingToSleep() {
        }

        default void onFinishedWakingUp() {
        }

        default void onPostFinishedWakingUp() {
        }

        default void onStartedGoingToSleep() {
        }

        default void onStartedWakingUp() {
        }
    }

    public WakefulnessLifecycle(Context context, IWallpaperManager iWallpaperManager, DumpManager dumpManager) {
        this.mContext = context;
        this.mDisplayMetrics = context.getResources().getDisplayMetrics();
        this.mWallpaperManagerService = iWallpaperManager;
        dumpManager.registerDumpable(getClass().getSimpleName(), this);
    }

    public void dispatchFinishedGoingToSleep() {
        if (getWakefulness() == 0) {
            return;
        }
        setWakefulness(0);
        dispatch(new Consumer() { // from class: com.android.systemui.keyguard.WakefulnessLifecycle$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((WakefulnessLifecycle.Observer) obj).onFinishedGoingToSleep();
            }
        });
    }

    public void dispatchFinishedWakingUp() {
        if (getWakefulness() == 2) {
            return;
        }
        setWakefulness(2);
        dispatch(new Consumer() { // from class: com.android.systemui.keyguard.WakefulnessLifecycle$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((WakefulnessLifecycle.Observer) obj).onFinishedWakingUp();
            }
        });
        dispatch(new Consumer() { // from class: com.android.systemui.keyguard.WakefulnessLifecycle$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((WakefulnessLifecycle.Observer) obj).onPostFinishedWakingUp();
            }
        });
    }

    public void dispatchStartedGoingToSleep(int i) {
        if (getWakefulness() == 3) {
            return;
        }
        setWakefulness(3);
        this.mLastSleepReason = i;
        updateLastSleepOriginLocation();
        IWallpaperManager iWallpaperManager = this.mWallpaperManagerService;
        if (iWallpaperManager != null) {
            try {
                Point point = this.mLastSleepOriginLocation;
                iWallpaperManager.notifyGoingToSleep(point.x, point.y, new Bundle());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        dispatch(new Consumer() { // from class: com.android.systemui.keyguard.WakefulnessLifecycle$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((WakefulnessLifecycle.Observer) obj).onStartedGoingToSleep();
            }
        });
    }

    public void dispatchStartedWakingUp(int i) {
        if (getWakefulness() == 1) {
            return;
        }
        setWakefulness(1);
        this.mLastWakeReason = i;
        updateLastWakeOriginLocation();
        IWallpaperManager iWallpaperManager = this.mWallpaperManagerService;
        if (iWallpaperManager != null) {
            try {
                Point point = this.mLastWakeOriginLocation;
                iWallpaperManager.notifyWakingUp(point.x, point.y, new Bundle());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        dispatch(new Consumer() { // from class: com.android.systemui.keyguard.WakefulnessLifecycle$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((WakefulnessLifecycle.Observer) obj).onStartedWakingUp();
            }
        });
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("WakefulnessLifecycle:");
        printWriter.println("  mWakefulness=" + this.mWakefulness);
    }

    public final Point getDefaultWakeSleepOrigin() {
        DisplayMetrics displayMetrics = this.mDisplayMetrics;
        return new Point(displayMetrics.widthPixels / 2, displayMetrics.heightPixels);
    }

    public int getLastSleepReason() {
        return this.mLastSleepReason;
    }

    public int getLastWakeReason() {
        return this.mLastWakeReason;
    }

    public final Point getPowerButtonOrigin() {
        boolean z = true;
        if (this.mContext.getResources().getConfiguration().orientation != 1) {
            z = false;
        }
        return z ? new Point(this.mDisplayMetrics.widthPixels, this.mContext.getResources().getDimensionPixelSize(R$dimen.physical_power_button_center_screen_location_y)) : new Point(this.mContext.getResources().getDimensionPixelSize(R$dimen.physical_power_button_center_screen_location_y), this.mDisplayMetrics.heightPixels);
    }

    public int getWakefulness() {
        return this.mWakefulness;
    }

    public final void setWakefulness(int i) {
        this.mWakefulness = i;
        Trace.traceCounter(4096L, "wakefulness", i);
    }

    public final void updateLastSleepOriginLocation() {
        this.mLastSleepOriginLocation = null;
        if (this.mLastSleepReason != 4) {
            this.mLastSleepOriginLocation = getDefaultWakeSleepOrigin();
        } else {
            this.mLastSleepOriginLocation = getPowerButtonOrigin();
        }
    }

    public final void updateLastWakeOriginLocation() {
        this.mLastWakeOriginLocation = null;
        if (this.mLastWakeReason != 1) {
            this.mLastWakeOriginLocation = getDefaultWakeSleepOrigin();
        } else {
            this.mLastWakeOriginLocation = getPowerButtonOrigin();
        }
    }
}