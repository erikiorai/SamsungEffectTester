package com.android.systemui.log.table;

import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.CallableReference;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: Add missing generic type declarations: [T] */
/* loaded from: mainsysui33.jar:com/android/systemui/log/table/DiffableKt$logDiffsForTable$1.class */
public final /* synthetic */ class DiffableKt$logDiffsForTable$1<T> extends FunctionReferenceImpl implements Function1<Continuation<? super T>, Object> {
    public DiffableKt$logDiffsForTable$1(Object obj) {
        super(1, obj, Intrinsics.Kotlin.class, "suspendConversion0", "logDiffsForTable$suspendConversion0(Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", 0);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        return invoke((Continuation) ((Continuation) obj));
    }

    public final Object invoke(Continuation<? super T> continuation) {
        Object invoke;
        invoke = ((Function0) ((CallableReference) this).receiver).invoke();
        return invoke;
    }
}