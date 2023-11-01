package com.android.systemui.log.table;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

@DebugMetadata(c = "com.android.systemui.log.table.DiffableKt$logDiffsForTable$8", f = "Diffable.kt", l = {}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/log/table/DiffableKt$logDiffsForTable$8.class */
public final class DiffableKt$logDiffsForTable$8 extends SuspendLambda implements Function3<String, String, Continuation<? super String>, Object> {
    public final /* synthetic */ String $columnName;
    public final /* synthetic */ String $columnPrefix;
    public final /* synthetic */ TableLogBuffer $tableLogBuffer;
    public /* synthetic */ Object L$0;
    public /* synthetic */ Object L$1;
    public int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DiffableKt$logDiffsForTable$8(TableLogBuffer tableLogBuffer, String str, String str2, Continuation<? super DiffableKt$logDiffsForTable$8> continuation) {
        super(3, continuation);
        this.$tableLogBuffer = tableLogBuffer;
        this.$columnPrefix = str;
        this.$columnName = str2;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(String str, String str2, Continuation<? super String> continuation) {
        DiffableKt$logDiffsForTable$8 diffableKt$logDiffsForTable$8 = new DiffableKt$logDiffsForTable$8(this.$tableLogBuffer, this.$columnPrefix, this.$columnName, continuation);
        diffableKt$logDiffsForTable$8.L$0 = str;
        diffableKt$logDiffsForTable$8.L$1 = str2;
        return diffableKt$logDiffsForTable$8.invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            String str = (String) this.L$0;
            String str2 = (String) this.L$1;
            if (!Intrinsics.areEqual(str, str2)) {
                this.$tableLogBuffer.logChange(this.$columnPrefix, this.$columnName, str2);
            }
            return str2;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}