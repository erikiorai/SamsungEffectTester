package com.android.systemui.log.table;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

@DebugMetadata(c = "com.android.systemui.log.table.DiffableKt$logDiffsForTable$6", f = "Diffable.kt", l = {}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/log/table/DiffableKt$logDiffsForTable$6.class */
public final class DiffableKt$logDiffsForTable$6 extends SuspendLambda implements Function3<Integer, Integer, Continuation<? super Integer>, Object> {
    public final /* synthetic */ String $columnName;
    public final /* synthetic */ String $columnPrefix;
    public final /* synthetic */ TableLogBuffer $tableLogBuffer;
    public /* synthetic */ int I$0;
    public /* synthetic */ int I$1;
    public int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DiffableKt$logDiffsForTable$6(TableLogBuffer tableLogBuffer, String str, String str2, Continuation<? super DiffableKt$logDiffsForTable$6> continuation) {
        super(3, continuation);
        this.$tableLogBuffer = tableLogBuffer;
        this.$columnPrefix = str;
        this.$columnName = str2;
    }

    public final Object invoke(int i, int i2, Continuation<? super Integer> continuation) {
        DiffableKt$logDiffsForTable$6 diffableKt$logDiffsForTable$6 = new DiffableKt$logDiffsForTable$6(this.$tableLogBuffer, this.$columnPrefix, this.$columnName, continuation);
        diffableKt$logDiffsForTable$6.I$0 = i;
        diffableKt$logDiffsForTable$6.I$1 = i2;
        return diffableKt$logDiffsForTable$6.invokeSuspend(Unit.INSTANCE);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        return invoke(((Number) obj).intValue(), ((Number) obj2).intValue(), (Continuation) obj3);
    }

    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            int i = this.I$0;
            int i2 = this.I$1;
            if (i != i2) {
                this.$tableLogBuffer.logChange(this.$columnPrefix, this.$columnName, i2);
            }
            return Boxing.boxInt(i2);
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}