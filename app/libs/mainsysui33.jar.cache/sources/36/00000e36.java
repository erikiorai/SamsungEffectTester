package com.android.settingslib.core.lifecycle;

import android.util.Log;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.OnLifecycleEvent;

/* loaded from: mainsysui33.jar:com/android/settingslib/core/lifecycle/Lifecycle.class */
public class Lifecycle extends LifecycleRegistry {

    /* renamed from: com.android.settingslib.core.lifecycle.Lifecycle$1  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/settingslib/core/lifecycle/Lifecycle$1.class */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$androidx$lifecycle$Lifecycle$Event;

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:17:0x0059 -> B:33:0x0014). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x005d -> B:43:0x001f). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x0061 -> B:39:0x002a). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x0065 -> B:35:0x0035). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:25:0x0069 -> B:31:0x0040). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:27:0x006d -> B:41:0x004c). Please submit an issue!!! */
        static {
            int[] iArr = new int[Lifecycle.Event.values().length];
            $SwitchMap$androidx$lifecycle$Lifecycle$Event = iArr;
            try {
                iArr[Lifecycle.Event.ON_CREATE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_START.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_RESUME.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_PAUSE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_STOP.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_DESTROY.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$Event[Lifecycle.Event.ON_ANY.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/settingslib/core/lifecycle/Lifecycle$LifecycleProxy.class */
    public class LifecycleProxy implements LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
        public void onLifecycleEvent(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            switch (AnonymousClass1.$SwitchMap$androidx$lifecycle$Lifecycle$Event[event.ordinal()]) {
                case 2:
                    Lifecycle.m1080$$Nest$monStart(null);
                    return;
                case 3:
                    Lifecycle.m1079$$Nest$monResume(null);
                    return;
                case 4:
                    Lifecycle.m1078$$Nest$monPause(null);
                    return;
                case 5:
                    Lifecycle.m1081$$Nest$monStop(null);
                    return;
                case 6:
                    Lifecycle.m1077$$Nest$monDestroy(null);
                    return;
                case 7:
                    Log.wtf("LifecycleObserver", "Should not receive an 'ANY' event!");
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: -$$Nest$monDestroy  reason: not valid java name */
    public static /* bridge */ /* synthetic */ void m1077$$Nest$monDestroy(Lifecycle lifecycle) {
        throw null;
    }

    /* renamed from: -$$Nest$monPause  reason: not valid java name */
    public static /* bridge */ /* synthetic */ void m1078$$Nest$monPause(Lifecycle lifecycle) {
        throw null;
    }

    /* renamed from: -$$Nest$monResume  reason: not valid java name */
    public static /* bridge */ /* synthetic */ void m1079$$Nest$monResume(Lifecycle lifecycle) {
        throw null;
    }

    /* renamed from: -$$Nest$monStart  reason: not valid java name */
    public static /* bridge */ /* synthetic */ void m1080$$Nest$monStart(Lifecycle lifecycle) {
        throw null;
    }

    /* renamed from: -$$Nest$monStop  reason: not valid java name */
    public static /* bridge */ /* synthetic */ void m1081$$Nest$monStop(Lifecycle lifecycle) {
        throw null;
    }
}