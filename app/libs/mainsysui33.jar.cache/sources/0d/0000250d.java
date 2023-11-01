package com.android.systemui.security.data.repository;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.security.data.model.SecurityModel;
import com.android.systemui.statusbar.policy.SecurityController;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;

/* loaded from: mainsysui33.jar:com/android/systemui/security/data/repository/SecurityRepositoryImpl.class */
public final class SecurityRepositoryImpl implements SecurityRepository {
    public static final Companion Companion = new Companion(null);
    public final CoroutineDispatcher bgDispatcher;
    public final Flow<SecurityModel> security = ConflatedCallbackFlow.INSTANCE.conflatedCallbackFlow(new SecurityRepositoryImpl$security$1(this, null));
    public final SecurityController securityController;

    /* loaded from: mainsysui33.jar:com/android/systemui/security/data/repository/SecurityRepositoryImpl$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public SecurityRepositoryImpl(SecurityController securityController, CoroutineDispatcher coroutineDispatcher) {
        this.securityController = securityController;
        this.bgDispatcher = coroutineDispatcher;
    }

    @Override // com.android.systemui.security.data.repository.SecurityRepository
    public Flow<SecurityModel> getSecurity() {
        return this.security;
    }
}