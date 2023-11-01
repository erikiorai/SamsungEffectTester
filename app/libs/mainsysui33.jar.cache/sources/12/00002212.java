package com.android.systemui.qs.footer.domain.interactor;

import android.app.admin.DevicePolicyEventLogger;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.animation.Expandable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.FgsManagerController;
import com.android.systemui.qs.QSSecurityFooterUtils;
import com.android.systemui.qs.footer.data.model.UserSwitcherStatusModel;
import com.android.systemui.qs.footer.data.repository.ForegroundServicesRepository;
import com.android.systemui.qs.footer.data.repository.UserSwitcherRepository;
import com.android.systemui.qs.footer.domain.model.SecurityButtonConfig;
import com.android.systemui.security.data.model.SecurityModel;
import com.android.systemui.security.data.repository.SecurityRepository;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.user.domain.interactor.UserInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/domain/interactor/FooterActionsInteractorImpl.class */
public final class FooterActionsInteractorImpl implements FooterActionsInteractor {
    public final ActivityStarter activityStarter;
    public final Flow<Unit> deviceMonitoringDialogRequests;
    public final DeviceProvisionedController deviceProvisionedController;
    public final FgsManagerController fgsManagerController;
    public final Flow<Integer> foregroundServicesCount;
    public final Flow<Boolean> hasNewForegroundServices;
    public final MetricsLogger metricsLogger;
    public final QSSecurityFooterUtils qsSecurityFooterUtils;
    public final Flow<SecurityButtonConfig> securityButtonConfig;
    public final UiEventLogger uiEventLogger;
    public final UserInteractor userInteractor;
    public final Flow<UserSwitcherStatusModel> userSwitcherStatus;

    public FooterActionsInteractorImpl(ActivityStarter activityStarter, MetricsLogger metricsLogger, UiEventLogger uiEventLogger, DeviceProvisionedController deviceProvisionedController, QSSecurityFooterUtils qSSecurityFooterUtils, FgsManagerController fgsManagerController, UserInteractor userInteractor, SecurityRepository securityRepository, ForegroundServicesRepository foregroundServicesRepository, UserSwitcherRepository userSwitcherRepository, BroadcastDispatcher broadcastDispatcher, final CoroutineDispatcher coroutineDispatcher) {
        this.activityStarter = activityStarter;
        this.metricsLogger = metricsLogger;
        this.uiEventLogger = uiEventLogger;
        this.deviceProvisionedController = deviceProvisionedController;
        this.qsSecurityFooterUtils = qSSecurityFooterUtils;
        this.fgsManagerController = fgsManagerController;
        this.userInteractor = userInteractor;
        final Flow<SecurityModel> security = securityRepository.getSecurity();
        this.securityButtonConfig = new Flow<SecurityButtonConfig>() { // from class: com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl$special$$inlined$map$1

            /* renamed from: com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl$special$$inlined$map$1$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/domain/interactor/FooterActionsInteractorImpl$special$$inlined$map$1$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ CoroutineDispatcher $bgDispatcher$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ FooterActionsInteractorImpl this$0;

                @DebugMetadata(c = "com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl$special$$inlined$map$1$2", f = "FooterActionsInteractor.kt", l = {224, 223}, m = "emit")
                /* renamed from: com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl$special$$inlined$map$1$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/domain/interactor/FooterActionsInteractorImpl$special$$inlined$map$1$2$1.class */
                public static final class AnonymousClass1 extends ContinuationImpl {
                    public Object L$0;
                    public int label;
                    public /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, CoroutineDispatcher coroutineDispatcher, FooterActionsInteractorImpl footerActionsInteractorImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$bgDispatcher$inlined = coroutineDispatcher;
                    this.this$0 = footerActionsInteractorImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:10:0x0047  */
                /* JADX WARN: Removed duplicated region for block: B:18:0x0073  */
                /* JADX WARN: Removed duplicated region for block: B:25:0x00ca  */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Object obj2;
                    Object coroutine_suspended;
                    int i;
                    FlowCollector flowCollector;
                    if (continuation instanceof AnonymousClass1) {
                        AnonymousClass1 anonymousClass12 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass12.label;
                        if ((i2 & Integer.MIN_VALUE) != 0) {
                            anonymousClass12.label = i2 - 2147483648;
                            anonymousClass1 = anonymousClass12;
                            obj2 = anonymousClass1.result;
                            coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                FlowCollector flowCollector2 = this.$this_unsafeFlow;
                                CoroutineDispatcher coroutineDispatcher = this.$bgDispatcher$inlined;
                                FooterActionsInteractorImpl$securityButtonConfig$1$1 footerActionsInteractorImpl$securityButtonConfig$1$1 = new FooterActionsInteractorImpl$securityButtonConfig$1$1(this.this$0, (SecurityModel) obj, null);
                                anonymousClass1.L$0 = flowCollector2;
                                anonymousClass1.label = 1;
                                obj2 = BuildersKt.withContext(coroutineDispatcher, footerActionsInteractorImpl$securityButtonConfig$1$1, anonymousClass1);
                                if (obj2 == coroutine_suspended) {
                                    return coroutine_suspended;
                                }
                                flowCollector = flowCollector2;
                            } else if (i != 1) {
                                if (i == 2) {
                                    ResultKt.throwOnFailure(obj2);
                                    return Unit.INSTANCE;
                                }
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            } else {
                                flowCollector = (FlowCollector) anonymousClass1.L$0;
                                ResultKt.throwOnFailure(obj2);
                            }
                            anonymousClass1.L$0 = null;
                            anonymousClass1.label = 2;
                            if (flowCollector.emit(obj2, anonymousClass1) == coroutine_suspended) {
                                return coroutine_suspended;
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    obj2 = anonymousClass1.result;
                    coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    anonymousClass1.L$0 = null;
                    anonymousClass1.label = 2;
                    if (flowCollector.emit(obj2, anonymousClass1) == coroutine_suspended) {
                    }
                    return Unit.INSTANCE;
                }
            }

            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = security.collect(new AnonymousClass2(flowCollector, coroutineDispatcher, this), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        };
        this.foregroundServicesCount = foregroundServicesRepository.getForegroundServicesCount();
        this.hasNewForegroundServices = foregroundServicesRepository.getHasNewChanges();
        this.userSwitcherStatus = userSwitcherRepository.getUserSwitcherStatus();
        this.deviceMonitoringDialogRequests = broadcastDispatcher.broadcastFlow(new IntentFilter("android.app.action.SHOW_DEVICE_MONITORING_DIALOG"), UserHandle.ALL, 2, null);
    }

    @Override // com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractor
    public Flow<Unit> getDeviceMonitoringDialogRequests() {
        return this.deviceMonitoringDialogRequests;
    }

    @Override // com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractor
    public Flow<Integer> getForegroundServicesCount() {
        return this.foregroundServicesCount;
    }

    @Override // com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractor
    public Flow<Boolean> getHasNewForegroundServices() {
        return this.hasNewForegroundServices;
    }

    @Override // com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractor
    public Flow<SecurityButtonConfig> getSecurityButtonConfig() {
        return this.securityButtonConfig;
    }

    @Override // com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractor
    public Flow<UserSwitcherStatusModel> getUserSwitcherStatus() {
        return this.userSwitcherStatus;
    }

    @Override // com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractor
    public void showDeviceMonitoringDialog(Context context, Expandable expandable) {
        this.qsSecurityFooterUtils.showDeviceMonitoringDialog(context, expandable);
        if (expandable != null) {
            DevicePolicyEventLogger.createEvent(57).write();
        }
    }

    @Override // com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractor
    public void showForegroundServicesDialog(Expandable expandable) {
        this.fgsManagerController.showDialog(expandable);
    }

    @Override // com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractor
    public void showPowerMenuDialog(GlobalActionsDialogLite globalActionsDialogLite, Expandable expandable) {
        this.uiEventLogger.log(GlobalActionsDialogLite.GlobalActionsEvent.GA_OPEN_QS);
        globalActionsDialogLite.showOrHideDialog(false, true, expandable);
    }

    @Override // com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractor
    public void showSettings(Expandable expandable) {
        if (!this.deviceProvisionedController.isCurrentUserSetup()) {
            this.activityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl$showSettings$1
                @Override // java.lang.Runnable
                public final void run() {
                }
            });
            return;
        }
        this.metricsLogger.action(406);
        this.activityStarter.startActivity(new Intent("android.settings.SETTINGS"), true, expandable.activityLaunchController(33));
    }

    @Override // com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractor
    public void showUserSwitcher(Context context, Expandable expandable) {
        this.userInteractor.showUserSwitcher(context, expandable);
    }
}