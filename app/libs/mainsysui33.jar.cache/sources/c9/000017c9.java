package com.android.systemui.flags;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import com.android.systemui.flags.FlagListenable;
import com.android.systemui.flags.FlagManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/FlagManager.class */
public final class FlagManager implements FlagListenable {
    public static final Companion Companion = new Companion(null);
    public Consumer<Integer> clearCacheAction;
    public final Context context;
    public final Handler handler;
    public final Set<PerFlagListener> listeners;
    public Consumer<Boolean> onSettingsChangedAction;
    public final FlagSettingsHelper settings;
    public final ContentObserver settingsObserver;

    /* loaded from: mainsysui33.jar:com/android/systemui/flags/FlagManager$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/flags/FlagManager$PerFlagListener.class */
    public static final class PerFlagListener {
        public final int id;
        public final FlagListenable.Listener listener;

        public PerFlagListener(int i, FlagListenable.Listener listener) {
            this.id = i;
            this.listener = listener;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof PerFlagListener) {
                PerFlagListener perFlagListener = (PerFlagListener) obj;
                return this.id == perFlagListener.id && Intrinsics.areEqual(this.listener, perFlagListener.listener);
            }
            return false;
        }

        public final int getId() {
            return this.id;
        }

        public final FlagListenable.Listener getListener() {
            return this.listener;
        }

        public int hashCode() {
            return (Integer.hashCode(this.id) * 31) + this.listener.hashCode();
        }

        public String toString() {
            int i = this.id;
            FlagListenable.Listener listener = this.listener;
            return "PerFlagListener(id=" + i + ", listener=" + listener + ")";
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/flags/FlagManager$SettingsObserver.class */
    public final class SettingsObserver extends ContentObserver {
        /* JADX DEBUG: Incorrect args count in method signature: ()V */
        public SettingsObserver() {
            super(FlagManager.this.handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            if (uri == null) {
                return;
            }
            List<String> pathSegments = uri.getPathSegments();
            try {
                int parseInt = Integer.parseInt(pathSegments.get(pathSegments.size() - 1));
                Consumer<Integer> clearCacheAction = FlagManager.this.getClearCacheAction();
                if (clearCacheAction != null) {
                    clearCacheAction.accept(Integer.valueOf(parseInt));
                }
                FlagManager flagManager = FlagManager.this;
                flagManager.dispatchListenersAndMaybeRestart(parseInt, flagManager.getOnSettingsChangedAction());
            } catch (NumberFormatException e) {
            }
        }
    }

    public FlagManager(Context context, Handler handler) {
        this(context, new FlagSettingsHelper(context.getContentResolver()), handler);
    }

    public FlagManager(Context context, FlagSettingsHelper flagSettingsHelper, Handler handler) {
        this.context = context;
        this.settings = flagSettingsHelper;
        this.handler = handler;
        this.listeners = new LinkedHashSet();
        this.settingsObserver = new SettingsObserver();
    }

    @Override // com.android.systemui.flags.FlagListenable
    public void addListener(Flag<?> flag, FlagListenable.Listener listener) {
        synchronized (this.listeners) {
            boolean isEmpty = this.listeners.isEmpty();
            this.listeners.add(new PerFlagListener(flag.getId(), listener));
            if (isEmpty) {
                this.settings.registerContentObserver("systemui/flags", true, this.settingsObserver);
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void dispatchListenersAndMaybeRestart(int i, Consumer<Boolean> consumer) {
        ArrayList<FlagListenable.Listener> arrayList;
        boolean z;
        synchronized (this.listeners) {
            Set<PerFlagListener> set = this.listeners;
            arrayList = new ArrayList();
            for (PerFlagListener perFlagListener : set) {
                FlagListenable.Listener listener = perFlagListener.getId() == i ? perFlagListener.getListener() : null;
                if (listener != null) {
                    arrayList.add(listener);
                }
            }
        }
        if (arrayList.isEmpty()) {
            if (consumer != null) {
                consumer.accept(Boolean.FALSE);
                return;
            }
            return;
        }
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
        for (FlagListenable.Listener listener2 : arrayList) {
            Ref.BooleanRef booleanRef = new Ref.BooleanRef();
            listener2.onFlagChanged(new FlagListenable.FlagEvent(i, booleanRef) { // from class: com.android.systemui.flags.FlagManager$dispatchListenersAndMaybeRestart$suppressRestartList$1$event$1
                public final /* synthetic */ Ref.BooleanRef $didRequestNoRestart;
                public final int flagId;

                {
                    this.$didRequestNoRestart = booleanRef;
                    this.flagId = i;
                }

                @Override // com.android.systemui.flags.FlagListenable.FlagEvent
                public int getFlagId() {
                    return this.flagId;
                }

                @Override // com.android.systemui.flags.FlagListenable.FlagEvent
                public void requestNoRestart() {
                    this.$didRequestNoRestart.element = true;
                }
            });
            arrayList2.add(Boolean.valueOf(booleanRef.element));
        }
        if (!arrayList2.isEmpty()) {
            Iterator it = arrayList2.iterator();
            while (true) {
                z = true;
                if (it.hasNext()) {
                    if (!((Boolean) it.next()).booleanValue()) {
                        z = false;
                        break;
                    }
                } else {
                    break;
                }
            }
        } else {
            z = true;
        }
        if (consumer != null) {
            consumer.accept(Boolean.valueOf(z));
        }
    }

    public final Consumer<Integer> getClearCacheAction() {
        return this.clearCacheAction;
    }

    public final Consumer<Boolean> getOnSettingsChangedAction() {
        return this.onSettingsChangedAction;
    }

    public final String idToSettingsKey(int i) {
        return "systemui/flags/" + i;
    }

    public final <T> T readFlagValue(int i, FlagSerializer<T> flagSerializer) {
        return flagSerializer.fromSettingsData(this.settings.getString(idToSettingsKey(i)));
    }

    @Override // com.android.systemui.flags.FlagListenable
    public void removeListener(final FlagListenable.Listener listener) {
        synchronized (this.listeners) {
            if (this.listeners.isEmpty()) {
                return;
            }
            this.listeners.removeIf(new Predicate() { // from class: com.android.systemui.flags.FlagManager$removeListener$1$1
                /* JADX DEBUG: Method merged with bridge method */
                @Override // java.util.function.Predicate
                public final boolean test(FlagManager.PerFlagListener perFlagListener) {
                    return Intrinsics.areEqual(perFlagListener.getListener(), FlagListenable.Listener.this);
                }
            });
            if (this.listeners.isEmpty()) {
                this.settings.unregisterContentObserver(this.settingsObserver);
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void setClearCacheAction(Consumer<Integer> consumer) {
        this.clearCacheAction = consumer;
    }

    public final void setOnSettingsChangedAction(Consumer<Boolean> consumer) {
        this.onSettingsChangedAction = consumer;
    }
}