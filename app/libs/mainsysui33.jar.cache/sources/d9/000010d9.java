package com.android.systemui.animation;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.WindowConfiguration;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Log;
import android.util.RotationUtils;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.window.IRemoteTransition;
import android.window.IRemoteTransitionFinishedCallback;
import android.window.RemoteTransition;
import android.window.TransitionInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.animation.RemoteTransitionAdapter;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/animation/RemoteTransitionAdapter.class */
public final class RemoteTransitionAdapter {
    public static final Companion Companion = new Companion(null);

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/RemoteTransitionAdapter$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final RemoteTransition adaptRemoteAnimation(RemoteAnimationAdapter remoteAnimationAdapter) {
            return new RemoteTransition(adaptRemoteRunner(remoteAnimationAdapter.getRunner()), remoteAnimationAdapter.getCallingApplication());
        }

        public final IRemoteTransition.Stub adaptRemoteRunner(final IRemoteAnimationRunner iRemoteAnimationRunner) {
            return new IRemoteTransition.Stub() { // from class: com.android.systemui.animation.RemoteTransitionAdapter$Companion$adaptRemoteRunner$1
                public void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) {
                    transaction.close();
                    transitionInfo.releaseAllSurfaces();
                }

                /* JADX WARN: Removed duplicated region for block: B:22:0x00eb  */
                /* JADX WARN: Removed duplicated region for block: B:30:0x014a A[LOOP:0: B:5:0x0051->B:30:0x014a, LOOP_END] */
                /* JADX WARN: Removed duplicated region for block: B:82:0x0140 A[EDGE_INSN: B:82:0x0140->B:29:0x0140 ?: BREAK  , SYNTHETIC] */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public void startAnimation(IBinder iBinder, final TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, final IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) {
                    float f;
                    float f2;
                    TransitionInfo.Change change;
                    TransitionInfo.Change change2;
                    int i;
                    boolean z;
                    int i2;
                    RemoteTransitionAdapter.CounterRotator counterRotator;
                    TransitionInfo.Change change3;
                    final ArrayMap<SurfaceControl, SurfaceControl> arrayMap = new ArrayMap<>();
                    RemoteTransitionAdapter.Companion companion = RemoteTransitionAdapter.Companion;
                    RemoteAnimationTarget[] wrapTargets = companion.wrapTargets(transitionInfo, false, transaction, arrayMap);
                    RemoteAnimationTarget[] wrapTargets2 = companion.wrapTargets(transitionInfo, true, transaction, arrayMap);
                    int size = transitionInfo.getChanges().size() - 1;
                    float f3 = 0.0f;
                    if (size >= 0) {
                        boolean z2 = false;
                        int i3 = 0;
                        TransitionInfo.Change change4 = null;
                        TransitionInfo.Change change5 = null;
                        float f4 = 0.0f;
                        int i4 = 0;
                        while (true) {
                            int i5 = size - 1;
                            TransitionInfo.Change change6 = (TransitionInfo.Change) transitionInfo.getChanges().get(size);
                            if (change6.getTaskInfo() != null) {
                                ActivityManager.RunningTaskInfo taskInfo = change6.getTaskInfo();
                                Intrinsics.checkNotNull(taskInfo);
                                if (taskInfo.getActivityType() == 2) {
                                    z = change6.getMode() == 1 || change6.getMode() == 3;
                                    i = transitionInfo.getChanges().size() - size;
                                    change3 = change6;
                                    f = f3;
                                    f2 = f4;
                                    i2 = i3;
                                    if (change6.getParent() == null) {
                                        f = f3;
                                        f2 = f4;
                                        i2 = i3;
                                        if (change6.getEndRotation() >= 0) {
                                            f = f3;
                                            f2 = f4;
                                            i2 = i3;
                                            if (change6.getEndRotation() != change6.getStartRotation()) {
                                                i2 = change6.getEndRotation() - change6.getStartRotation();
                                                f = change6.getEndAbsBounds().width();
                                                f2 = change6.getEndAbsBounds().height();
                                            }
                                        }
                                    }
                                    if (i5 >= 0) {
                                        break;
                                    }
                                    size = i5;
                                    f3 = f;
                                    f4 = f2;
                                    change4 = change3;
                                    z2 = z;
                                    i4 = i;
                                    i3 = i2;
                                }
                            }
                            change3 = change4;
                            z = z2;
                            i = i4;
                            if ((change6.getFlags() & 2) != 0) {
                                change5 = change6;
                                i = i4;
                                z = z2;
                                change3 = change4;
                            }
                            f = f3;
                            f2 = f4;
                            i2 = i3;
                            if (change6.getParent() == null) {
                            }
                            if (i5 >= 0) {
                            }
                        }
                        change2 = change5;
                        change = change3;
                    } else {
                        f = 0.0f;
                        f2 = 0.0f;
                        change = null;
                        change2 = null;
                        i = 0;
                        z = false;
                        i2 = 0;
                    }
                    RemoteTransitionAdapter.CounterRotator counterRotator2 = new RemoteTransitionAdapter.CounterRotator();
                    final RemoteTransitionAdapter.CounterRotator counterRotator3 = new RemoteTransitionAdapter.CounterRotator();
                    if (change != null && i2 != 0 && change.getParent() != null) {
                        WindowContainerToken parent = change.getParent();
                        Intrinsics.checkNotNull(parent);
                        TransitionInfo.Change change7 = transitionInfo.getChange(parent);
                        Intrinsics.checkNotNull(change7);
                        counterRotator2.setup(transaction, change7.getLeash(), i2, f, f2);
                        if (counterRotator2.getSurface() != null) {
                            SurfaceControl surface = counterRotator2.getSurface();
                            Intrinsics.checkNotNull(surface);
                            transaction.setLayer(surface, i);
                        }
                    }
                    if (z) {
                        if (counterRotator2.getSurface() != null) {
                            SurfaceControl surface2 = counterRotator2.getSurface();
                            Intrinsics.checkNotNull(surface2);
                            transaction.setLayer(surface2, transitionInfo.getChanges().size() * 3);
                        }
                        int size2 = transitionInfo.getChanges().size() - 1;
                        if (size2 >= 0) {
                            while (true) {
                                int i6 = size2 - 1;
                                TransitionInfo.Change change8 = (TransitionInfo.Change) transitionInfo.getChanges().get(size2);
                                SurfaceControl surfaceControl = arrayMap.get(change8.getLeash());
                                int mode = ((TransitionInfo.Change) transitionInfo.getChanges().get(size2)).getMode();
                                if (TransitionInfo.isIndependent(change8, transitionInfo) && (mode == 2 || mode == 4)) {
                                    Intrinsics.checkNotNull(surfaceControl);
                                    transaction.setLayer(surfaceControl, (transitionInfo.getChanges().size() * 3) - size2);
                                    counterRotator2.addChild(transaction, surfaceControl);
                                }
                                if (i6 < 0) {
                                    break;
                                }
                                size2 = i6;
                            }
                        }
                        int length = wrapTargets2.length - 1;
                        counterRotator = counterRotator2;
                        if (length >= 0) {
                            while (true) {
                                int i7 = length - 1;
                                transaction.show(wrapTargets2[length].leash);
                                transaction.setAlpha(wrapTargets2[length].leash, 1.0f);
                                if (i7 < 0) {
                                    break;
                                }
                                length = i7;
                            }
                            counterRotator = counterRotator2;
                        }
                    } else {
                        if (change != null) {
                            counterRotator2.addChild(transaction, arrayMap.get(change.getLeash()));
                        }
                        counterRotator = counterRotator2;
                        if (change2 != null) {
                            counterRotator = counterRotator2;
                            if (i2 != 0) {
                                counterRotator = counterRotator2;
                                if (change2.getParent() != null) {
                                    WindowContainerToken parent2 = change2.getParent();
                                    Intrinsics.checkNotNull(parent2);
                                    TransitionInfo.Change change9 = transitionInfo.getChange(parent2);
                                    Intrinsics.checkNotNull(change9);
                                    counterRotator3.setup(transaction, change9.getLeash(), i2, f, f2);
                                    if (counterRotator3.getSurface() != null) {
                                        SurfaceControl surface3 = counterRotator3.getSurface();
                                        Intrinsics.checkNotNull(surface3);
                                        transaction.setLayer(surface3, -1);
                                        counterRotator3.addChild(transaction, arrayMap.get(change2.getLeash()));
                                    }
                                    transaction.apply();
                                    final RemoteTransitionAdapter.CounterRotator counterRotator4 = counterRotator2;
                                    iRemoteAnimationRunner.onAnimationStart(0, wrapTargets, wrapTargets2, new RemoteAnimationTarget[0], new IRemoteAnimationFinishedCallback() { // from class: com.android.systemui.animation.RemoteTransitionAdapter$Companion$adaptRemoteRunner$1$startAnimation$animationFinishedCallback$1
                                        public IBinder asBinder() {
                                            return null;
                                        }

                                        public void onAnimationFinished() {
                                            SurfaceControl.Transaction transaction2 = new SurfaceControl.Transaction();
                                            RemoteTransitionAdapter.CounterRotator.this.cleanUp(transaction2);
                                            counterRotator3.cleanUp(transaction2);
                                            transitionInfo.releaseAllSurfaces();
                                            for (int size3 = arrayMap.size() - 1; -1 < size3; size3--) {
                                                arrayMap.valueAt(size3).release();
                                            }
                                            try {
                                                iRemoteTransitionFinishedCallback.onTransitionFinished((WindowContainerTransaction) null, transaction2);
                                                transaction2.close();
                                            } catch (RemoteException e) {
                                                Log.e("ActivityOptionsCompat", "Failed to call app controlled animation finished callback", e);
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    }
                    counterRotator2 = counterRotator;
                    transaction.apply();
                    final RemoteTransitionAdapter.CounterRotator counterRotator42 = counterRotator2;
                    iRemoteAnimationRunner.onAnimationStart(0, wrapTargets, wrapTargets2, new RemoteAnimationTarget[0], new IRemoteAnimationFinishedCallback() { // from class: com.android.systemui.animation.RemoteTransitionAdapter$Companion$adaptRemoteRunner$1$startAnimation$animationFinishedCallback$1
                        public IBinder asBinder() {
                            return null;
                        }

                        public void onAnimationFinished() {
                            SurfaceControl.Transaction transaction2 = new SurfaceControl.Transaction();
                            RemoteTransitionAdapter.CounterRotator.this.cleanUp(transaction2);
                            counterRotator3.cleanUp(transaction2);
                            transitionInfo.releaseAllSurfaces();
                            for (int size3 = arrayMap.size() - 1; -1 < size3; size3--) {
                                arrayMap.valueAt(size3).release();
                            }
                            try {
                                iRemoteTransitionFinishedCallback.onTransitionFinished((WindowContainerTransaction) null, transaction2);
                                transaction2.close();
                            } catch (RemoteException e) {
                                Log.e("ActivityOptionsCompat", "Failed to call app controlled animation finished callback", e);
                            }
                        }
                    });
                }
            };
        }

        @SuppressLint({"NewApi"})
        public final SurfaceControl createLeash(TransitionInfo transitionInfo, TransitionInfo.Change change, int i, SurfaceControl.Transaction transaction) {
            SurfaceControl leash;
            if (change.getParent() == null || (change.getFlags() & 2) == 0) {
                SurfaceControl.Builder builder = new SurfaceControl.Builder();
                String surfaceControl = change.getLeash().toString();
                SurfaceControl.Builder containerLayer = builder.setName(surfaceControl + "_transition-leash").setContainerLayer();
                if (change.getParent() == null) {
                    leash = transitionInfo.getRootLeash();
                } else {
                    WindowContainerToken parent = change.getParent();
                    Intrinsics.checkNotNull(parent);
                    TransitionInfo.Change change2 = transitionInfo.getChange(parent);
                    Intrinsics.checkNotNull(change2);
                    leash = change2.getLeash();
                }
                SurfaceControl build = containerLayer.setParent(leash).build();
                setupLeash(build, change, transitionInfo.getChanges().size() - i, transitionInfo, transaction);
                transaction.reparent(change.getLeash(), build);
                transaction.setAlpha(change.getLeash(), 1.0f);
                transaction.show(change.getLeash());
                transaction.setPosition(change.getLeash(), ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW);
                transaction.setLayer(change.getLeash(), 0);
                return build;
            }
            return change.getLeash();
        }

        public final RemoteAnimationTarget createTarget(TransitionInfo.Change change, int i, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction) {
            int i2;
            WindowConfiguration windowConfiguration;
            boolean z;
            if (change.getTaskInfo() != null) {
                ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
                Intrinsics.checkNotNull(taskInfo);
                i2 = taskInfo.taskId;
            } else {
                i2 = -1;
            }
            int newModeToLegacyMode = newModeToLegacyMode(change.getMode());
            SurfaceControl createLeash = createLeash(transitionInfo, change, i, transaction);
            boolean z2 = ((change.getFlags() & 4) == 0 && (change.getFlags() & 1) == 0) ? false : true;
            Rect rect = new Rect(0, 0, 0, 0);
            Rect rectOffsetTo = rectOffsetTo(change.getEndAbsBounds(), change.getEndRelOffset());
            Rect rect2 = new Rect(change.getEndAbsBounds());
            if (change.getTaskInfo() != null) {
                ActivityManager.RunningTaskInfo taskInfo2 = change.getTaskInfo();
                Intrinsics.checkNotNull(taskInfo2);
                windowConfiguration = taskInfo2.configuration.windowConfiguration;
            } else {
                windowConfiguration = new WindowConfiguration();
            }
            if (change.getTaskInfo() != null) {
                ActivityManager.RunningTaskInfo taskInfo3 = change.getTaskInfo();
                Intrinsics.checkNotNull(taskInfo3);
                if (taskInfo3.isRunning) {
                    z = false;
                    RemoteAnimationTarget remoteAnimationTarget = new RemoteAnimationTarget(i2, newModeToLegacyMode, createLeash, z2, (Rect) null, rect, i, (Point) null, rectOffsetTo, rect2, windowConfiguration, z, (SurfaceControl) null, new Rect(change.getStartAbsBounds()), change.getTaskInfo(), change.getAllowEnterPip(), -1);
                    remoteAnimationTarget.backgroundColor = change.getBackgroundColor();
                    return remoteAnimationTarget;
                }
            }
            z = true;
            RemoteAnimationTarget remoteAnimationTarget2 = new RemoteAnimationTarget(i2, newModeToLegacyMode, createLeash, z2, (Rect) null, rect, i, (Point) null, rectOffsetTo, rect2, windowConfiguration, z, (SurfaceControl) null, new Rect(change.getStartAbsBounds()), change.getTaskInfo(), change.getAllowEnterPip(), -1);
            remoteAnimationTarget2.backgroundColor = change.getBackgroundColor();
            return remoteAnimationTarget2;
        }

        public final int newModeToLegacyMode(int i) {
            int i2;
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        if (i != 4) {
                            i2 = 2;
                            return i2;
                        }
                    }
                }
                i2 = 1;
                return i2;
            }
            i2 = 0;
            return i2;
        }

        public final Rect rectOffsetTo(Rect rect, Point point) {
            Rect rect2 = new Rect(rect);
            rect2.offsetTo(point.x, point.y);
            return rect2;
        }

        @SuppressLint({"NewApi"})
        public final void setupLeash(SurfaceControl surfaceControl, TransitionInfo.Change change, int i, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction) {
            boolean z = transitionInfo.getType() == 1 || transitionInfo.getType() == 3;
            int size = transitionInfo.getChanges().size();
            int mode = change.getMode();
            transaction.reparent(surfaceControl, transitionInfo.getRootLeash());
            transaction.setPosition(surfaceControl, change.getStartAbsBounds().left - transitionInfo.getRootOffset().x, change.getStartAbsBounds().top - transitionInfo.getRootOffset().y);
            transaction.show(surfaceControl);
            if (mode != 1) {
                if (mode != 2) {
                    if (mode != 3) {
                        if (mode != 4) {
                            transaction.setLayer(surfaceControl, (size + transitionInfo.getChanges().size()) - i);
                            return;
                        }
                    }
                }
                if (z) {
                    transaction.setLayer(surfaceControl, size - i);
                    return;
                } else {
                    transaction.setLayer(surfaceControl, (size + transitionInfo.getChanges().size()) - i);
                    return;
                }
            }
            if (!z) {
                transaction.setLayer(surfaceControl, size - i);
                return;
            }
            transaction.setLayer(surfaceControl, (size + transitionInfo.getChanges().size()) - i);
            if ((change.getFlags() & 8) == 0) {
                transaction.setAlpha(surfaceControl, ActionBarShadowController.ELEVATION_LOW);
            }
        }

        public final RemoteAnimationTarget[] wrapTargets(TransitionInfo transitionInfo, boolean z, SurfaceControl.Transaction transaction, ArrayMap<SurfaceControl, SurfaceControl> arrayMap) {
            ArrayList arrayList = new ArrayList();
            int size = transitionInfo.getChanges().size();
            for (int i = 0; i < size; i++) {
                TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(i);
                if ((!change.hasFlags((int) RecyclerView.ViewHolder.FLAG_ADAPTER_POSITION_UNKNOWN) || (change.getParent() == null && change.hasFlags((int) RecyclerView.ViewHolder.FLAG_ADAPTER_FULLUPDATE))) && z == change.hasFlags(2)) {
                    arrayList.add(createTarget(change, transitionInfo.getChanges().size() - i, transitionInfo, transaction));
                    if (arrayMap != null) {
                        arrayMap.put(change.getLeash(), ((RemoteAnimationTarget) arrayList.get(arrayList.size() - 1)).leash);
                    }
                }
            }
            return (RemoteAnimationTarget[]) arrayList.toArray(new RemoteAnimationTarget[0]);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/RemoteTransitionAdapter$CounterRotator.class */
    public static final class CounterRotator {
        public SurfaceControl surface;

        public final void addChild(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
            if (this.surface == null) {
                return;
            }
            Intrinsics.checkNotNull(surfaceControl);
            transaction.reparent(surfaceControl, this.surface);
        }

        public final void cleanUp(SurfaceControl.Transaction transaction) {
            SurfaceControl surfaceControl = this.surface;
            if (surfaceControl == null) {
                return;
            }
            Intrinsics.checkNotNull(surfaceControl);
            transaction.remove(surfaceControl);
        }

        public final SurfaceControl getSurface() {
            return this.surface;
        }

        public final void setup(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, int i, float f, float f2) {
            if (i == 0) {
                return;
            }
            SurfaceControl build = new SurfaceControl.Builder().setName("Transition Unrotate").setContainerLayer().setParent(surfaceControl).build();
            RotationUtils.rotateSurface(transaction, build, i);
            boolean z = false;
            Point point = new Point(0, 0);
            if (i % 2 != 0) {
                z = true;
            }
            float f3 = z ? f2 : f;
            if (!z) {
                f = f2;
            }
            RotationUtils.rotatePoint(point, i, (int) f3, (int) f);
            transaction.setPosition(build, point.x, point.y);
            transaction.show(build);
        }
    }

    public static final RemoteTransition adaptRemoteAnimation(RemoteAnimationAdapter remoteAnimationAdapter) {
        return Companion.adaptRemoteAnimation(remoteAnimationAdapter);
    }
}