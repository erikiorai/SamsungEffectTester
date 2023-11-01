package com.android.systemui.log.table;

import com.android.systemui.util.kotlin.FlowKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.Flow;

/* loaded from: mainsysui33.jar:com/android/systemui/log/table/DiffableKt.class */
public final class DiffableKt {
    public static final <T extends Diffable<T>> Flow<T> logDiffsForTable(Flow<? extends T> flow, final TableLogBuffer tableLogBuffer, final String str, final T t) {
        return FlowKt.pairwiseBy(flow, new DiffableKt$logDiffsForTable$1(new Function0<T>() { // from class: com.android.systemui.log.table.DiffableKt$logDiffsForTable$getInitialValue$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Incorrect types in method signature: (Lcom/android/systemui/log/table/TableLogBuffer;Ljava/lang/String;TT;)V */
            {
                super(0);
            }

            /* JADX DEBUG: Method merged with bridge method */
            /* JADX WARN: Incorrect return type in method signature: ()TT; */
            /* renamed from: invoke */
            public final Diffable m3137invoke() {
                TableLogBuffer tableLogBuffer2 = TableLogBuffer.this;
                String str2 = str;
                final Diffable diffable = t;
                tableLogBuffer2.logChange(str2, new Function1<TableRowLogger, Unit>() { // from class: com.android.systemui.log.table.DiffableKt$logDiffsForTable$getInitialValue$1.1
                    /* JADX WARN: Incorrect types in method signature: (TT;)V */
                    {
                        super(1);
                    }

                    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                        invoke((TableRowLogger) obj);
                        return Unit.INSTANCE;
                    }

                    public final void invoke(TableRowLogger tableRowLogger) {
                        Diffable.this.logFull(tableRowLogger);
                    }
                });
                return t;
            }
        }), new DiffableKt$logDiffsForTable$2(tableLogBuffer, str, null));
    }

    public static final Flow<Integer> logDiffsForTable(Flow<Integer> flow, final TableLogBuffer tableLogBuffer, final String str, final String str2, final int i) {
        return FlowKt.pairwiseBy(flow, new DiffableKt$logDiffsForTable$5(new Function0<Integer>() { // from class: com.android.systemui.log.table.DiffableKt$logDiffsForTable$initialValueFun$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX DEBUG: Method merged with bridge method */
            /* renamed from: invoke */
            public final Integer m3139invoke() {
                TableLogBuffer.this.logChange(str, str2, i);
                return Integer.valueOf(i);
            }
        }), new DiffableKt$logDiffsForTable$6(tableLogBuffer, str, str2, null));
    }

    public static final Flow<String> logDiffsForTable(Flow<String> flow, final TableLogBuffer tableLogBuffer, final String str, final String str2, final String str3) {
        return FlowKt.pairwiseBy(flow, new DiffableKt$logDiffsForTable$7(new Function0<String>() { // from class: com.android.systemui.log.table.DiffableKt$logDiffsForTable$initialValueFun$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke() {
                TableLogBuffer.this.logChange(str, str2, str3);
                return str3;
            }
        }), new DiffableKt$logDiffsForTable$8(tableLogBuffer, str, str2, null));
    }

    public static final Flow<Boolean> logDiffsForTable(Flow<Boolean> flow, final TableLogBuffer tableLogBuffer, final String str, final String str2, final boolean z) {
        return FlowKt.pairwiseBy(flow, new DiffableKt$logDiffsForTable$3(new Function0<Boolean>() { // from class: com.android.systemui.log.table.DiffableKt$logDiffsForTable$initialValueFun$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX DEBUG: Method merged with bridge method */
            /* renamed from: invoke */
            public final Boolean m3138invoke() {
                TableLogBuffer.this.logChange(str, str2, z);
                return Boolean.valueOf(z);
            }
        }), new DiffableKt$logDiffsForTable$4(tableLogBuffer, str, str2, null));
    }
}