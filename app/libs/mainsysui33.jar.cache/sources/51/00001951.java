package com.android.systemui.keyguard.data.quickaffordance;

import android.content.SharedPreferences;
import java.util.List;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

@DebugMetadata(c = "com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLocalUserSelectionManager$selections$3$1", f = "KeyguardQuickAffordanceLocalUserSelectionManager.kt", l = {121, 123}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceLocalUserSelectionManager$selections$3$1.class */
public final class KeyguardQuickAffordanceLocalUserSelectionManager$selections$3$1 extends SuspendLambda implements Function2<ProducerScope<? super Map<String, ? extends List<? extends String>>>, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    public Object L$1;
    public int label;
    public final /* synthetic */ KeyguardQuickAffordanceLocalUserSelectionManager this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardQuickAffordanceLocalUserSelectionManager$selections$3$1(KeyguardQuickAffordanceLocalUserSelectionManager keyguardQuickAffordanceLocalUserSelectionManager, Continuation<? super KeyguardQuickAffordanceLocalUserSelectionManager$selections$3$1> continuation) {
        super(2, continuation);
        this.this$0 = keyguardQuickAffordanceLocalUserSelectionManager;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        KeyguardQuickAffordanceLocalUserSelectionManager$selections$3$1 keyguardQuickAffordanceLocalUserSelectionManager$selections$3$1 = new KeyguardQuickAffordanceLocalUserSelectionManager$selections$3$1(this.this$0, continuation);
        keyguardQuickAffordanceLocalUserSelectionManager$selections$3$1.L$0 = obj;
        return keyguardQuickAffordanceLocalUserSelectionManager$selections$3$1;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(ProducerScope<? super Map<String, ? extends List<String>>> producerScope, Continuation<? super Unit> continuation) {
        return create(producerScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        final ProducerScope producerScope;
        SharedPreferences instantiateSharedPrefs;
        SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener;
        SharedPreferences sharedPreferences;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            producerScope = (ProducerScope) this.L$0;
            KeyguardQuickAffordanceLocalUserSelectionManager keyguardQuickAffordanceLocalUserSelectionManager = this.this$0;
            instantiateSharedPrefs = keyguardQuickAffordanceLocalUserSelectionManager.instantiateSharedPrefs();
            keyguardQuickAffordanceLocalUserSelectionManager.sharedPrefs = instantiateSharedPrefs;
            final KeyguardQuickAffordanceLocalUserSelectionManager keyguardQuickAffordanceLocalUserSelectionManager2 = this.this$0;
            onSharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() { // from class: com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLocalUserSelectionManager$selections$3$1$listener$1
                @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
                public final void onSharedPreferenceChanged(SharedPreferences sharedPreferences2, String str) {
                    producerScope.trySend-JP2dKIU(keyguardQuickAffordanceLocalUserSelectionManager2.getSelections());
                }
            };
            sharedPreferences = this.this$0.sharedPrefs;
            sharedPreferences.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
            Map<String, List<String>> selections = this.this$0.getSelections();
            this.L$0 = producerScope;
            this.L$1 = onSharedPreferenceChangeListener;
            this.label = 1;
            if (producerScope.send(selections, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else if (i != 1) {
            if (i == 2) {
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        } else {
            onSharedPreferenceChangeListener = (SharedPreferences.OnSharedPreferenceChangeListener) this.L$1;
            ResultKt.throwOnFailure(obj);
            producerScope = (ProducerScope) this.L$0;
        }
        final KeyguardQuickAffordanceLocalUserSelectionManager keyguardQuickAffordanceLocalUserSelectionManager3 = this.this$0;
        final SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener2 = onSharedPreferenceChangeListener;
        Function0<Unit> function0 = new Function0<Unit>() { // from class: com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLocalUserSelectionManager$selections$3$1.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            public /* bridge */ /* synthetic */ Object invoke() {
                m2947invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke  reason: collision with other method in class */
            public final void m2947invoke() {
                SharedPreferences sharedPreferences2;
                sharedPreferences2 = KeyguardQuickAffordanceLocalUserSelectionManager.this.sharedPrefs;
                sharedPreferences2.unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener2);
            }
        };
        this.L$0 = null;
        this.L$1 = null;
        this.label = 2;
        if (ProduceKt.awaitClose(producerScope, function0, this) == coroutine_suspended) {
            return coroutine_suspended;
        }
        return Unit.INSTANCE;
    }
}