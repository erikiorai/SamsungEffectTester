package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

/* loaded from: mainsysui33.jar:androidx/lifecycle/RepeatOnLifecycleKt.class */
public final class RepeatOnLifecycleKt {
    public static final Object repeatOnLifecycle(Lifecycle lifecycle, Lifecycle.State state, Function2<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object> function2, Continuation<? super Unit> continuation) {
        Object coroutineScope;
        if (state != Lifecycle.State.INITIALIZED) {
            if (lifecycle.getCurrentState() != Lifecycle.State.DESTROYED && (coroutineScope = CoroutineScopeKt.coroutineScope(new RepeatOnLifecycleKt$repeatOnLifecycle$3(lifecycle, state, function2, null), continuation)) == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                return coroutineScope;
            }
            return Unit.INSTANCE;
        }
        throw new IllegalArgumentException("repeatOnLifecycle cannot start work with the INITIALIZED lifecycle state.".toString());
    }

    public static final Object repeatOnLifecycle(LifecycleOwner lifecycleOwner, Lifecycle.State state, Function2<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object> function2, Continuation<? super Unit> continuation) {
        Object repeatOnLifecycle = repeatOnLifecycle(lifecycleOwner.getLifecycle(), state, function2, continuation);
        return repeatOnLifecycle == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? repeatOnLifecycle : Unit.INSTANCE;
    }
}