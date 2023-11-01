package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.shared.customization.data.content.CustomizationProviderClient;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

@DebugMetadata(c = "com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceRemoteUserSelectionManager$setSelections$1$1", f = "KeyguardQuickAffordanceRemoteUserSelectionManager.kt", l = {118, 120}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceRemoteUserSelectionManager$setSelections$1$1.class */
public final class KeyguardQuickAffordanceRemoteUserSelectionManager$setSelections$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ List<String> $affordanceIds;
    public final /* synthetic */ CustomizationProviderClient $client;
    public final /* synthetic */ String $slotId;
    public Object L$0;
    public Object L$1;
    public Object L$2;
    public int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardQuickAffordanceRemoteUserSelectionManager$setSelections$1$1(CustomizationProviderClient customizationProviderClient, String str, List<String> list, Continuation<? super KeyguardQuickAffordanceRemoteUserSelectionManager$setSelections$1$1> continuation) {
        super(2, continuation);
        this.$client = customizationProviderClient;
        this.$slotId = str;
        this.$affordanceIds = list;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new KeyguardQuickAffordanceRemoteUserSelectionManager$setSelections$1$1(this.$client, this.$slotId, this.$affordanceIds, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0097  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        CustomizationProviderClient customizationProviderClient;
        String str;
        Iterator it;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CustomizationProviderClient customizationProviderClient2 = this.$client;
            String str2 = this.$slotId;
            this.label = 1;
            if (customizationProviderClient2.deleteAllSelections(str2, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else if (i != 1) {
            if (i == 2) {
                it = (Iterator) this.L$2;
                String str3 = (String) this.L$1;
                customizationProviderClient = (CustomizationProviderClient) this.L$0;
                ResultKt.throwOnFailure(obj);
                str = str3;
                while (it.hasNext()) {
                    String str4 = (String) it.next();
                    this.L$0 = customizationProviderClient;
                    this.L$1 = str;
                    this.L$2 = it;
                    this.label = 2;
                    if (customizationProviderClient.insertSelection(str, str4, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                }
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        } else {
            ResultKt.throwOnFailure(obj);
        }
        List<String> list = this.$affordanceIds;
        customizationProviderClient = this.$client;
        str = this.$slotId;
        it = list.iterator();
        while (it.hasNext()) {
        }
        return Unit.INSTANCE;
    }
}