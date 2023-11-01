package com.android.systemui.qs.footer.data.repository;

import com.android.systemui.qs.FgsManagerController;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/data/repository/ForegroundServicesRepositoryImpl.class */
public final class ForegroundServicesRepositoryImpl implements ForegroundServicesRepository {
    public static final Companion Companion = new Companion(null);
    public final Flow<Integer> foregroundServicesCount;
    public final Flow<Boolean> hasNewChanges;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/data/repository/ForegroundServicesRepositoryImpl$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ForegroundServicesRepositoryImpl(FgsManagerController fgsManagerController) {
        this.foregroundServicesCount = FlowKt.distinctUntilChanged(FlowKt.transformLatest(fgsManagerController.isAvailable(), new ForegroundServicesRepositoryImpl$special$$inlined$flatMapLatest$1(null, fgsManagerController)));
        this.hasNewChanges = FlowKt.transformLatest(fgsManagerController.getShowFooterDot(), new ForegroundServicesRepositoryImpl$special$$inlined$flatMapLatest$2(null, this, fgsManagerController));
    }

    @Override // com.android.systemui.qs.footer.data.repository.ForegroundServicesRepository
    public Flow<Integer> getForegroundServicesCount() {
        return this.foregroundServicesCount;
    }

    @Override // com.android.systemui.qs.footer.data.repository.ForegroundServicesRepository
    public Flow<Boolean> getHasNewChanges() {
        return this.hasNewChanges;
    }
}