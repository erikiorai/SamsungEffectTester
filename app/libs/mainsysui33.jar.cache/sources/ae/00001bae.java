package com.android.systemui.log.table;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

@DebugMetadata(c = "com.android.systemui.log.table.DiffableKt$logDiffsForTable$4", f = "Diffable.kt", l = {}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/log/table/DiffableKt$logDiffsForTable$4.class */
public final class DiffableKt$logDiffsForTable$4 extends SuspendLambda implements Function3<Boolean, Boolean, Continuation<? super Boolean>, Object> {
    public final /* synthetic */ String $columnName;
    public final /* synthetic */ String $columnPrefix;
    public final /* synthetic */ TableLogBuffer $tableLogBuffer;
    public /* synthetic */ boolean Z$0;
    public /* synthetic */ boolean Z$1;
    public int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DiffableKt$logDiffsForTable$4(TableLogBuffer tableLogBuffer, String str, String str2, Continuation<? super DiffableKt$logDiffsForTable$4> continuation) {
        super(3, continuation);
        this.$tableLogBuffer = tableLogBuffer;
        this.$columnPrefix = str;
        this.$columnName = str2;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        return invoke(((Boolean) obj).booleanValue(), ((Boolean) obj2).booleanValue(), (Continuation) obj3);
    }

    public final Object invoke(boolean z, boolean z2, Continuation<? super Boolean> continuation) {
        DiffableKt$logDiffsForTable$4 diffableKt$logDiffsForTable$4 = new DiffableKt$logDiffsForTable$4(this.$tableLogBuffer, this.$columnPrefix, this.$columnName, continuation);
        diffableKt$logDiffsForTable$4.Z$0 = z;
        diffableKt$logDiffsForTable$4.Z$1 = z2;
        return diffableKt$logDiffsForTable$4.invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            boolean z = this.Z$0;
            boolean z2 = this.Z$1;
            if (z != z2) {
                this.$tableLogBuffer.logChange(this.$columnPrefix, this.$columnName, z2);
            }
            return Boxing.boxBoolean(z2);
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}