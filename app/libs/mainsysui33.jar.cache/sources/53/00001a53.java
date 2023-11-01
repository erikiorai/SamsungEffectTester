package com.android.systemui.keyguard.domain.interactor;

import com.android.keyguard.logging.KeyguardLogger;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardTransitionAuditLogger.class */
public final class KeyguardTransitionAuditLogger {
    public final KeyguardTransitionInteractor interactor;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardLogger logger;
    public final CoroutineScope scope;

    public KeyguardTransitionAuditLogger(CoroutineScope coroutineScope, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardInteractor keyguardInteractor, KeyguardLogger keyguardLogger) {
        this.scope = coroutineScope;
        this.interactor = keyguardTransitionInteractor;
        this.keyguardInteractor = keyguardInteractor;
        this.logger = keyguardLogger;
    }

    public final void start() {
        BuildersKt.launch$default(this.scope, (CoroutineContext) null, (CoroutineStart) null, new KeyguardTransitionAuditLogger$start$1(this, null), 3, (Object) null);
        BuildersKt.launch$default(this.scope, (CoroutineContext) null, (CoroutineStart) null, new KeyguardTransitionAuditLogger$start$2(this, null), 3, (Object) null);
        BuildersKt.launch$default(this.scope, (CoroutineContext) null, (CoroutineStart) null, new KeyguardTransitionAuditLogger$start$3(this, null), 3, (Object) null);
        BuildersKt.launch$default(this.scope, (CoroutineContext) null, (CoroutineStart) null, new KeyguardTransitionAuditLogger$start$4(this, null), 3, (Object) null);
        BuildersKt.launch$default(this.scope, (CoroutineContext) null, (CoroutineStart) null, new KeyguardTransitionAuditLogger$start$5(this, null), 3, (Object) null);
        BuildersKt.launch$default(this.scope, (CoroutineContext) null, (CoroutineStart) null, new KeyguardTransitionAuditLogger$start$6(this, null), 3, (Object) null);
        BuildersKt.launch$default(this.scope, (CoroutineContext) null, (CoroutineStart) null, new KeyguardTransitionAuditLogger$start$7(this, null), 3, (Object) null);
        BuildersKt.launch$default(this.scope, (CoroutineContext) null, (CoroutineStart) null, new KeyguardTransitionAuditLogger$start$8(this, null), 3, (Object) null);
    }
}