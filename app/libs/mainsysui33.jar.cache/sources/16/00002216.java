package com.android.systemui.qs.footer.domain.interactor;

import com.android.systemui.qs.QSSecurityFooterUtils;
import com.android.systemui.qs.footer.domain.model.SecurityButtonConfig;
import com.android.systemui.security.data.model.SecurityModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

@DebugMetadata(c = "com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl$securityButtonConfig$1$1", f = "FooterActionsInteractor.kt", l = {}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/domain/interactor/FooterActionsInteractorImpl$securityButtonConfig$1$1.class */
public final class FooterActionsInteractorImpl$securityButtonConfig$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super SecurityButtonConfig>, Object> {
    public final /* synthetic */ SecurityModel $security;
    public int label;
    public final /* synthetic */ FooterActionsInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FooterActionsInteractorImpl$securityButtonConfig$1$1(FooterActionsInteractorImpl footerActionsInteractorImpl, SecurityModel securityModel, Continuation<? super FooterActionsInteractorImpl$securityButtonConfig$1$1> continuation) {
        super(2, continuation);
        this.this$0 = footerActionsInteractorImpl;
        this.$security = securityModel;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new FooterActionsInteractorImpl$securityButtonConfig$1$1(this.this$0, this.$security, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super SecurityButtonConfig> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        QSSecurityFooterUtils qSSecurityFooterUtils;
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            qSSecurityFooterUtils = this.this$0.qsSecurityFooterUtils;
            return qSSecurityFooterUtils.getButtonConfig(this.$security);
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}