package com.android.systemui.keyguard.data.repository;

import com.android.systemui.keyguard.shared.model.BiometricUnlockModel;
import com.android.systemui.statusbar.LiftReveal;
import com.android.systemui.statusbar.LightRevealEffect;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

@DebugMetadata(c = "com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$revealEffect$1", f = "LightRevealScrimRepository.kt", l = {}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/LightRevealScrimRepositoryImpl$revealEffect$1.class */
public final class LightRevealScrimRepositoryImpl$revealEffect$1 extends SuspendLambda implements Function4<BiometricUnlockModel, LightRevealEffect, LightRevealEffect, Continuation<? super LightRevealEffect>, Object> {
    public /* synthetic */ Object L$0;
    public /* synthetic */ Object L$1;
    public /* synthetic */ Object L$2;
    public int label;

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/LightRevealScrimRepositoryImpl$revealEffect$1$WhenMappings.class */
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[BiometricUnlockModel.values().length];
            try {
                iArr[BiometricUnlockModel.WAKE_AND_UNLOCK.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[BiometricUnlockModel.WAKE_AND_UNLOCK_PULSING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[BiometricUnlockModel.WAKE_AND_UNLOCK_FROM_DREAM.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public LightRevealScrimRepositoryImpl$revealEffect$1(Continuation<? super LightRevealScrimRepositoryImpl$revealEffect$1> continuation) {
        super(4, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(BiometricUnlockModel biometricUnlockModel, LightRevealEffect lightRevealEffect, LightRevealEffect lightRevealEffect2, Continuation<? super LightRevealEffect> continuation) {
        LightRevealScrimRepositoryImpl$revealEffect$1 lightRevealScrimRepositoryImpl$revealEffect$1 = new LightRevealScrimRepositoryImpl$revealEffect$1(continuation);
        lightRevealScrimRepositoryImpl$revealEffect$1.L$0 = biometricUnlockModel;
        lightRevealScrimRepositoryImpl$revealEffect$1.L$1 = lightRevealEffect;
        lightRevealScrimRepositoryImpl$revealEffect$1.L$2 = lightRevealEffect2;
        return lightRevealScrimRepositoryImpl$revealEffect$1.invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            BiometricUnlockModel biometricUnlockModel = (BiometricUnlockModel) this.L$0;
            LiftReveal liftReveal = (LightRevealEffect) this.L$1;
            LiftReveal liftReveal2 = (LightRevealEffect) this.L$2;
            int i = WhenMappings.$EnumSwitchMapping$0[biometricUnlockModel.ordinal()];
            LiftReveal liftReveal3 = liftReveal;
            if (i != 1) {
                liftReveal3 = liftReveal;
                if (i != 2) {
                    liftReveal3 = liftReveal;
                    if (i != 3) {
                        liftReveal3 = liftReveal2;
                    }
                }
            }
            LiftReveal liftReveal4 = liftReveal3;
            if (liftReveal3 == null) {
                liftReveal4 = LightRevealScrimRepositoryKt.getDEFAULT_REVEAL_EFFECT();
            }
            return liftReveal4;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}