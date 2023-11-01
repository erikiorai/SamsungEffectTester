package com.android.systemui.lifecycle;

import android.view.View;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import com.android.systemui.util.Assert;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.DisposableHandle;

/* loaded from: mainsysui33.jar:com/android/systemui/lifecycle/RepeatWhenAttachedKt.class */
public final class RepeatWhenAttachedKt {
    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttached$onAttachListener$1.onViewAttachedToWindow(android.view.View):void] */
    public static final /* synthetic */ ViewLifecycleOwner access$createLifecycleOwnerAndRun(View view, CoroutineContext coroutineContext, Function3 function3) {
        return createLifecycleOwnerAndRun(view, coroutineContext, function3);
    }

    public static final ViewLifecycleOwner createLifecycleOwnerAndRun(View view, CoroutineContext coroutineContext, Function3<? super LifecycleOwner, ? super View, ? super Continuation<? super Unit>, ? extends Object> function3) {
        ViewLifecycleOwner viewLifecycleOwner = new ViewLifecycleOwner(view);
        viewLifecycleOwner.onCreate();
        BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope(viewLifecycleOwner), coroutineContext, (CoroutineStart) null, new RepeatWhenAttachedKt$createLifecycleOwnerAndRun$1$1(function3, viewLifecycleOwner, view, null), 2, (Object) null);
        return viewLifecycleOwner;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r7v0, resolved type: android.view.View */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttached$onAttachListener$1, android.view.View$OnAttachStateChangeListener] */
    public static final DisposableHandle repeatWhenAttached(final View view, CoroutineContext coroutineContext, final Function3<? super LifecycleOwner, ? super View, ? super Continuation<? super Unit>, ? extends Object> function3) {
        Assert.isMainThread();
        final CoroutineContext plus = Dispatchers.getMain().plus(coroutineContext);
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        final ?? r0 = new View.OnAttachStateChangeListener() { // from class: com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttached$onAttachListener$1
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view2) {
                Assert.isMainThread();
                ViewLifecycleOwner viewLifecycleOwner = (ViewLifecycleOwner) objectRef.element;
                if (viewLifecycleOwner != null) {
                    viewLifecycleOwner.onDestroy();
                }
                objectRef.element = RepeatWhenAttachedKt.access$createLifecycleOwnerAndRun(view, plus, function3);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view2) {
                ViewLifecycleOwner viewLifecycleOwner = (ViewLifecycleOwner) objectRef.element;
                if (viewLifecycleOwner != null) {
                    viewLifecycleOwner.onDestroy();
                }
                objectRef.element = null;
            }
        };
        view.addOnAttachStateChangeListener(r0);
        if (view.isAttachedToWindow()) {
            objectRef.element = createLifecycleOwnerAndRun(view, plus, function3);
        }
        return new DisposableHandle() { // from class: com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttached$1
            public void dispose() {
                Assert.isMainThread();
                ViewLifecycleOwner viewLifecycleOwner = (ViewLifecycleOwner) objectRef.element;
                if (viewLifecycleOwner != null) {
                    viewLifecycleOwner.onDestroy();
                }
                objectRef.element = null;
                view.removeOnAttachStateChangeListener(r0);
            }
        };
    }

    public static /* synthetic */ DisposableHandle repeatWhenAttached$default(View view, CoroutineContext coroutineContext, Function3 function3, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineContext = EmptyCoroutineContext.INSTANCE;
        }
        return repeatWhenAttached(view, coroutineContext, function3);
    }
}