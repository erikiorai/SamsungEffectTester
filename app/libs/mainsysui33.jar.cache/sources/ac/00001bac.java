package com.android.systemui.log.table;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* JADX INFO: Add missing generic type declarations: [T] */
@DebugMetadata(c = "com.android.systemui.log.table.DiffableKt$logDiffsForTable$2", f = "Diffable.kt", l = {}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/log/table/DiffableKt$logDiffsForTable$2.class */
public final class DiffableKt$logDiffsForTable$2<T> extends SuspendLambda implements Function3<T, T, Continuation<? super T>, Object> {
    public final /* synthetic */ String $columnPrefix;
    public final /* synthetic */ TableLogBuffer $tableLogBuffer;
    public /* synthetic */ Object L$0;
    public /* synthetic */ Object L$1;
    public int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DiffableKt$logDiffsForTable$2(TableLogBuffer tableLogBuffer, String str, Continuation<? super DiffableKt$logDiffsForTable$2> continuation) {
        super(3, continuation);
        this.$tableLogBuffer = tableLogBuffer;
        this.$columnPrefix = str;
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* JADX WARN: Incorrect types in method signature: (TT;TT;Lkotlin/coroutines/Continuation<-TT;>;)Ljava/lang/Object; */
    public final Object invoke(Diffable diffable, Diffable diffable2, Continuation continuation) {
        DiffableKt$logDiffsForTable$2 diffableKt$logDiffsForTable$2 = new DiffableKt$logDiffsForTable$2(this.$tableLogBuffer, this.$columnPrefix, continuation);
        diffableKt$logDiffsForTable$2.L$0 = diffable;
        diffableKt$logDiffsForTable$2.L$1 = diffable2;
        return diffableKt$logDiffsForTable$2.invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            Diffable diffable = (Diffable) this.L$0;
            Diffable diffable2 = (Diffable) this.L$1;
            this.$tableLogBuffer.logDiffs(this.$columnPrefix, diffable, diffable2);
            return diffable2;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}