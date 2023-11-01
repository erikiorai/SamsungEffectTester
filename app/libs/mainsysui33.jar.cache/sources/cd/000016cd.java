package com.android.systemui.dreams;

import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dreams.complication.Complication;
import com.android.systemui.statusbar.policy.CallbackController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayStateController.class */
public class DreamOverlayStateController implements CallbackController<Callback> {
    public static final boolean DEBUG = Log.isLoggable("DreamOverlayStateCtlr", 3);
    public final Executor mExecutor;
    public final boolean mOverlayEnabled;
    public int mState;
    public final ArrayList<Callback> mCallbacks = new ArrayList<>();
    public int mAvailableComplicationTypes = 0;
    public boolean mShouldShowComplications = false;
    public final Collection<Complication> mComplications = new HashSet();

    /* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayStateController$Callback.class */
    public interface Callback {
        default void onAvailableComplicationTypesChanged() {
        }

        default void onComplicationsChanged() {
        }

        default void onStateChanged() {
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.DreamOverlayStateController$$ExternalSyntheticLambda1.run():void] */
    public static /* synthetic */ void $r8$lambda$6nGRsJ7mx2k_LuDQTsThaBgtlUY(DreamOverlayStateController dreamOverlayStateController, Callback callback) {
        dreamOverlayStateController.lambda$addCallback$6(callback);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.DreamOverlayStateController$$ExternalSyntheticLambda9.run():void] */
    /* renamed from: $r8$lambda$9_RcpEerSI6dBgrZ8U6XZbr-l4I */
    public static /* synthetic */ void m2560$r8$lambda$9_RcpEerSI6dBgrZ8U6XZbrl4I(DreamOverlayStateController dreamOverlayStateController, int i) {
        dreamOverlayStateController.lambda$setAvailableComplicationTypes$8(i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.DreamOverlayStateController$$ExternalSyntheticLambda5.run():void] */
    public static /* synthetic */ void $r8$lambda$A0akB2zaZfq38QHtNA1V9D6awYc(DreamOverlayStateController dreamOverlayStateController, Complication complication) {
        dreamOverlayStateController.lambda$addComplication$1(complication);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.DreamOverlayStateController$$ExternalSyntheticLambda2.test(java.lang.Object):boolean] */
    /* renamed from: $r8$lambda$G5XFpamP0EPBMmaG53klqYF8c-Y */
    public static /* synthetic */ boolean m2561$r8$lambda$G5XFpamP0EPBMmaG53klqYF8cY(DreamOverlayStateController dreamOverlayStateController, Complication complication) {
        return dreamOverlayStateController.lambda$getComplications$4(complication);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.DreamOverlayStateController$$ExternalSyntheticLambda4.run():void] */
    /* renamed from: $r8$lambda$Ho5HSMiy5XJatUd0-NlW5VoRf9w */
    public static /* synthetic */ void m2562$r8$lambda$Ho5HSMiy5XJatUd0NlW5VoRf9w(DreamOverlayStateController dreamOverlayStateController, boolean z) {
        dreamOverlayStateController.lambda$setShouldShowComplications$9(z);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.DreamOverlayStateController$$ExternalSyntheticLambda0.run():void] */
    /* renamed from: $r8$lambda$IBktZE6yu7Cmd4rgP2IcRkXUZ-Y */
    public static /* synthetic */ void m2563$r8$lambda$IBktZE6yu7Cmd4rgP2IcRkXUZY(DreamOverlayStateController dreamOverlayStateController, Callback callback) {
        dreamOverlayStateController.lambda$removeCallback$7(callback);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.DreamOverlayStateController$$ExternalSyntheticLambda10.run():void] */
    public static /* synthetic */ void $r8$lambda$N380X9O2npd7WoKu0HYApNViVCw(DreamOverlayStateController dreamOverlayStateController, Consumer consumer) {
        dreamOverlayStateController.lambda$notifyCallbacks$5(consumer);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.DreamOverlayStateController$$ExternalSyntheticLambda6.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$uVxLFrarekI_pIvgA84ciUNvpLU(Callback callback) {
        callback.onComplicationsChanged();
    }

    @VisibleForTesting
    public DreamOverlayStateController(Executor executor, boolean z) {
        this.mExecutor = executor;
        this.mOverlayEnabled = z;
        if (DEBUG) {
            Log.d("DreamOverlayStateCtlr", "Dream overlay enabled:" + z);
        }
    }

    public /* synthetic */ void lambda$addCallback$6(Callback callback) {
        Objects.requireNonNull(callback, "Callback must not be null. b/128895449");
        if (this.mCallbacks.contains(callback)) {
            return;
        }
        this.mCallbacks.add(callback);
        if (this.mComplications.isEmpty()) {
            return;
        }
        callback.onComplicationsChanged();
    }

    public /* synthetic */ void lambda$addComplication$1(Complication complication) {
        if (this.mComplications.add(complication)) {
            if (DEBUG) {
                Log.d("DreamOverlayStateCtlr", "addComplication: added " + complication);
            }
            this.mCallbacks.stream().forEach(new Consumer() { // from class: com.android.systemui.dreams.DreamOverlayStateController$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    DreamOverlayStateController.$r8$lambda$uVxLFrarekI_pIvgA84ciUNvpLU((DreamOverlayStateController.Callback) obj);
                }
            });
        }
    }

    public /* synthetic */ boolean lambda$getComplications$4(Complication complication) {
        int requiredTypeAvailability = complication.getRequiredTypeAvailability();
        boolean z = true;
        if (!this.mShouldShowComplications) {
            return requiredTypeAvailability == 0;
        }
        if ((getAvailableComplicationTypes() & requiredTypeAvailability) != requiredTypeAvailability) {
            z = false;
        }
        return z;
    }

    public /* synthetic */ void lambda$notifyCallbacks$5(Consumer consumer) {
        Iterator<Callback> it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            consumer.accept(it.next());
        }
    }

    public /* synthetic */ void lambda$removeCallback$7(Callback callback) {
        Objects.requireNonNull(callback, "Callback must not be null. b/128895449");
        this.mCallbacks.remove(callback);
    }

    public /* synthetic */ void lambda$setAvailableComplicationTypes$8(int i) {
        this.mAvailableComplicationTypes = i;
        this.mCallbacks.forEach(new DreamOverlayStateController$$ExternalSyntheticLambda7());
    }

    public /* synthetic */ void lambda$setShouldShowComplications$9(boolean z) {
        this.mShouldShowComplications = z;
        this.mCallbacks.forEach(new DreamOverlayStateController$$ExternalSyntheticLambda7());
    }

    public void addCallback(final Callback callback) {
        this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.dreams.DreamOverlayStateController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                DreamOverlayStateController.$r8$lambda$6nGRsJ7mx2k_LuDQTsThaBgtlUY(DreamOverlayStateController.this, callback);
            }
        });
    }

    public void addComplication(final Complication complication) {
        if (this.mOverlayEnabled) {
            this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.dreams.DreamOverlayStateController$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    DreamOverlayStateController.$r8$lambda$A0akB2zaZfq38QHtNA1V9D6awYc(DreamOverlayStateController.this, complication);
                }
            });
        } else if (DEBUG) {
            Log.d("DreamOverlayStateCtlr", "Ignoring adding complication due to overlay disabled:" + complication);
        }
    }

    public boolean areEntryAnimationsFinished() {
        return containsState(4);
    }

    public boolean areExitAnimationsRunning() {
        return containsState(8);
    }

    public final boolean containsState(int i) {
        return (this.mState & i) != 0;
    }

    public int getAvailableComplicationTypes() {
        return this.mAvailableComplicationTypes;
    }

    public Collection<Complication> getComplications() {
        return getComplications(true);
    }

    public Collection<Complication> getComplications(boolean z) {
        return Collections.unmodifiableCollection(z ? (Collection) this.mComplications.stream().filter(new Predicate() { // from class: com.android.systemui.dreams.DreamOverlayStateController$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return DreamOverlayStateController.m2561$r8$lambda$G5XFpamP0EPBMmaG53klqYF8cY(DreamOverlayStateController.this, (Complication) obj);
            }
        }).collect(Collectors.toCollection(new DreamOverlayStateController$$ExternalSyntheticLambda3())) : this.mComplications);
    }

    public boolean isLowLightActive() {
        return containsState(2);
    }

    public boolean isOverlayActive() {
        boolean z = true;
        if (!this.mOverlayEnabled || !containsState(1)) {
            z = false;
        }
        return z;
    }

    public final void modifyState(int i, int i2) {
        int i3 = this.mState;
        if (i == 1) {
            this.mState = (i2 ^ (-1)) & i3;
        } else if (i == 2) {
            this.mState = i3 | i2;
        }
        if (i3 != this.mState) {
            notifyCallbacks(new Consumer() { // from class: com.android.systemui.dreams.DreamOverlayStateController$$ExternalSyntheticLambda8
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((DreamOverlayStateController.Callback) obj).onStateChanged();
                }
            });
        }
    }

    public final void notifyCallbacks(final Consumer<Callback> consumer) {
        this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.dreams.DreamOverlayStateController$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                DreamOverlayStateController.$r8$lambda$N380X9O2npd7WoKu0HYApNViVCw(DreamOverlayStateController.this, consumer);
            }
        });
    }

    public void removeCallback(final Callback callback) {
        this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.dreams.DreamOverlayStateController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DreamOverlayStateController.m2563$r8$lambda$IBktZE6yu7Cmd4rgP2IcRkXUZY(DreamOverlayStateController.this, callback);
            }
        });
    }

    public void setAvailableComplicationTypes(final int i) {
        this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.dreams.DreamOverlayStateController$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                DreamOverlayStateController.m2560$r8$lambda$9_RcpEerSI6dBgrZ8U6XZbrl4I(DreamOverlayStateController.this, i);
            }
        });
    }

    public void setEntryAnimationsFinished(boolean z) {
        modifyState(z ? 2 : 1, 4);
    }

    public void setLowLightActive(boolean z) {
        modifyState(z ? 2 : 1, 2);
    }

    public void setOverlayActive(boolean z) {
        modifyState(z ? 2 : 1, 1);
    }

    public void setShouldShowComplications(final boolean z) {
        this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.dreams.DreamOverlayStateController$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                DreamOverlayStateController.m2562$r8$lambda$Ho5HSMiy5XJatUd0NlW5VoRf9w(DreamOverlayStateController.this, z);
            }
        });
    }
}