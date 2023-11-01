package androidx.lifecycle;

/* loaded from: mainsysui33.jar:androidx/lifecycle/LifecycleOwnerKt.class */
public final class LifecycleOwnerKt {
    public static final LifecycleCoroutineScope getLifecycleScope(LifecycleOwner lifecycleOwner) {
        return LifecycleKt.getCoroutineScope(lifecycleOwner.getLifecycle());
    }
}