package com.android.systemui.log.table;

import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.CallableReference;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/log/table/DiffableKt$logDiffsForTable$7.class */
public final /* synthetic */ class DiffableKt$logDiffsForTable$7 extends FunctionReferenceImpl implements Function1<Continuation<? super String>, Object> {
    public DiffableKt$logDiffsForTable$7(Object obj) {
        super(1, obj, Intrinsics.Kotlin.class, "suspendConversion0", "logDiffsForTable$suspendConversion0$2(Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", 0);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(Continuation<? super String> continuation) {
        Object invoke;
        invoke = ((Function0) ((CallableReference) this).receiver).invoke();
        return invoke;
    }
}