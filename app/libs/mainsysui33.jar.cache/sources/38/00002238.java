package com.android.systemui.qs.footer.ui.viewmodel;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ContextThemeWrapper;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.android.settingslib.Utils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$attr;
import com.android.systemui.R$drawable;
import com.android.systemui.R$id;
import com.android.systemui.R$string;
import com.android.systemui.R$style;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.qs.footer.data.model.UserSwitcherStatusModel;
import com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractor;
import com.android.systemui.qs.footer.domain.model.SecurityButtonConfig;
import javax.inject.Provider;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/ui/viewmodel/FooterActionsViewModel.class */
public final class FooterActionsViewModel {
    public static final Companion Companion = new Companion(null);
    public final MutableStateFlow<Float> _alpha;
    public final MutableStateFlow<Float> _backgroundAlpha;
    public final MutableStateFlow<Boolean> _isVisible;
    public final StateFlow<Float> alpha;
    public final StateFlow<Float> backgroundAlpha;
    public final ContextThemeWrapper context;
    public final FalsingManager falsingManager;
    public final FooterActionsInteractor footerActionsInteractor;
    public final Flow<FooterActionsForegroundServicesButtonViewModel> foregroundServices;
    public final GlobalActionsDialogLite globalActionsDialogLite;
    public final StateFlow<Boolean> isVisible;
    public final FooterActionsButtonViewModel power;
    public final Flow<FooterActionsSecurityButtonViewModel> security;
    public final FooterActionsButtonViewModel settings;
    public final Flow<FooterActionsButtonViewModel> userSwitcher;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/ui/viewmodel/FooterActionsViewModel$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/ui/viewmodel/FooterActionsViewModel$Factory.class */
    public static final class Factory {
        public final Context context;
        public final FalsingManager falsingManager;
        public final FooterActionsInteractor footerActionsInteractor;
        public final Provider<GlobalActionsDialogLite> globalActionsDialogLiteProvider;
        public final boolean showPowerButton;

        public Factory(Context context, FalsingManager falsingManager, FooterActionsInteractor footerActionsInteractor, Provider<GlobalActionsDialogLite> provider, boolean z) {
            this.context = context;
            this.falsingManager = falsingManager;
            this.footerActionsInteractor = footerActionsInteractor;
            this.globalActionsDialogLiteProvider = provider;
            this.showPowerButton = z;
        }

        public final FooterActionsViewModel create(LifecycleOwner lifecycleOwner) {
            final GlobalActionsDialogLite globalActionsDialogLite = (GlobalActionsDialogLite) this.globalActionsDialogLiteProvider.get();
            if (lifecycleOwner.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
                globalActionsDialogLite.destroy();
            } else {
                lifecycleOwner.getLifecycle().addObserver(new DefaultLifecycleObserver() { // from class: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModel$Factory$create$1
                    @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
                    public void onDestroy(LifecycleOwner lifecycleOwner2) {
                        GlobalActionsDialogLite.this.destroy();
                    }
                });
            }
            return new FooterActionsViewModel(this.context, this.footerActionsInteractor, this.falsingManager, globalActionsDialogLite, this.showPowerButton);
        }
    }

    public FooterActionsViewModel(Context context, FooterActionsInteractor footerActionsInteractor, FalsingManager falsingManager, GlobalActionsDialogLite globalActionsDialogLite, boolean z) {
        this.footerActionsInteractor = footerActionsInteractor;
        this.falsingManager = falsingManager;
        this.globalActionsDialogLite = globalActionsDialogLite;
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, R$style.Theme_SystemUI_QuickSettings);
        this.context = contextThemeWrapper;
        MutableStateFlow<Boolean> MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.TRUE);
        this._isVisible = MutableStateFlow;
        this.isVisible = FlowKt.asStateFlow(MutableStateFlow);
        Float valueOf = Float.valueOf(1.0f);
        MutableStateFlow<Float> MutableStateFlow2 = StateFlowKt.MutableStateFlow(valueOf);
        this._alpha = MutableStateFlow2;
        this.alpha = FlowKt.asStateFlow(MutableStateFlow2);
        MutableStateFlow<Float> MutableStateFlow3 = StateFlowKt.MutableStateFlow(valueOf);
        this._backgroundAlpha = MutableStateFlow3;
        this.backgroundAlpha = FlowKt.asStateFlow(MutableStateFlow3);
        final Flow<SecurityButtonConfig> securityButtonConfig = footerActionsInteractor.getSecurityButtonConfig();
        Flow<FooterActionsSecurityButtonViewModel> distinctUntilChanged = FlowKt.distinctUntilChanged(new Flow<FooterActionsSecurityButtonViewModel>() { // from class: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModel$special$$inlined$map$1$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/ui/viewmodel/FooterActionsViewModel$special$$inlined$map$1$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ FooterActionsViewModel this$0;

                @DebugMetadata(c = "com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModel$special$$inlined$map$1$2", f = "FooterActionsViewModel.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModel$special$$inlined$map$1$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/ui/viewmodel/FooterActionsViewModel$special$$inlined$map$1$2$1.class */
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

                public AnonymousClass2(FlowCollector flowCollector, FooterActionsViewModel footerActionsViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = footerActionsViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:10:0x0047  */
                /* JADX WARN: Removed duplicated region for block: B:15:0x005e  */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    FooterActionsSecurityButtonViewModel footerActionsSecurityButtonViewModel;
                    if (continuation instanceof AnonymousClass1) {
                        AnonymousClass1 anonymousClass12 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass12.label;
                        if ((i2 & Integer.MIN_VALUE) != 0) {
                            anonymousClass12.label = i2 - 2147483648;
                            anonymousClass1 = anonymousClass12;
                            Object obj2 = anonymousClass1.result;
                            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                FlowCollector flowCollector = this.$this_unsafeFlow;
                                SecurityButtonConfig securityButtonConfig = (SecurityButtonConfig) obj;
                                FooterActionsViewModel$security$1$1 footerActionsViewModel$security$1$1 = null;
                                if (securityButtonConfig == null) {
                                    footerActionsSecurityButtonViewModel = null;
                                } else {
                                    Icon component1 = securityButtonConfig.component1();
                                    String component2 = securityButtonConfig.component2();
                                    if (securityButtonConfig.component3()) {
                                        footerActionsViewModel$security$1$1 = new FooterActionsViewModel$security$1$1(this.this$0);
                                    }
                                    footerActionsSecurityButtonViewModel = new FooterActionsSecurityButtonViewModel(component1, component2, footerActionsViewModel$security$1$1);
                                }
                                anonymousClass1.label = 1;
                                if (flowCollector.emit(footerActionsSecurityButtonViewModel, anonymousClass1) == coroutine_suspended) {
                                    return coroutine_suspended;
                                }
                            } else if (i != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            } else {
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    Object coroutine_suspended2 = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = securityButtonConfig.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        });
        this.security = distinctUntilChanged;
        FooterActionsButtonViewModel footerActionsButtonViewModel = null;
        this.foregroundServices = FlowKt.distinctUntilChanged(FlowKt.combine(footerActionsInteractor.getForegroundServicesCount(), footerActionsInteractor.getHasNewForegroundServices(), distinctUntilChanged, new FooterActionsViewModel$foregroundServices$1(this, null)));
        final Flow<UserSwitcherStatusModel> userSwitcherStatus = footerActionsInteractor.getUserSwitcherStatus();
        this.userSwitcher = FlowKt.distinctUntilChanged(new Flow<FooterActionsButtonViewModel>() { // from class: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModel$special$$inlined$map$2

            /* renamed from: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModel$special$$inlined$map$2$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/ui/viewmodel/FooterActionsViewModel$special$$inlined$map$2$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ FooterActionsViewModel this$0;

                @DebugMetadata(c = "com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModel$special$$inlined$map$2$2", f = "FooterActionsViewModel.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModel$special$$inlined$map$2$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/ui/viewmodel/FooterActionsViewModel$special$$inlined$map$2$2$1.class */
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

                public AnonymousClass2(FlowCollector flowCollector, FooterActionsViewModel footerActionsViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = footerActionsViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:10:0x0047  */
                /* JADX WARN: Removed duplicated region for block: B:15:0x005f  */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        AnonymousClass1 anonymousClass12 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass12.label;
                        if ((i2 & Integer.MIN_VALUE) != 0) {
                            anonymousClass12.label = i2 - 2147483648;
                            anonymousClass1 = anonymousClass12;
                            Object obj2 = anonymousClass1.result;
                            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                FlowCollector flowCollector = this.$this_unsafeFlow;
                                UserSwitcherStatusModel userSwitcherStatusModel = (UserSwitcherStatusModel) obj;
                                FooterActionsButtonViewModel footerActionsButtonViewModel = null;
                                if (!Intrinsics.areEqual(userSwitcherStatusModel, UserSwitcherStatusModel.Disabled.INSTANCE)) {
                                    if (!(userSwitcherStatusModel instanceof UserSwitcherStatusModel.Enabled)) {
                                        throw new NoWhenBranchMatchedException();
                                    }
                                    UserSwitcherStatusModel.Enabled enabled = (UserSwitcherStatusModel.Enabled) userSwitcherStatusModel;
                                    if (enabled.getCurrentUserImage() == null) {
                                        Log.e("FooterActionsViewModel", "Skipped the addition of user switcher button because currentUserImage is missing");
                                    } else {
                                        footerActionsButtonViewModel = FooterActionsViewModel.access$userSwitcherButton(this.this$0, enabled);
                                    }
                                }
                                anonymousClass1.label = 1;
                                if (flowCollector.emit(footerActionsButtonViewModel, anonymousClass1) == coroutine_suspended) {
                                    return coroutine_suspended;
                                }
                            } else if (i != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            } else {
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    Object coroutine_suspended2 = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = userSwitcherStatus.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        });
        this.settings = new FooterActionsButtonViewModel(R$id.settings_button_container, new Icon.Resource(R$drawable.ic_settings, new ContentDescription.Resource(R$string.accessibility_quick_settings_settings)), null, R$attr.offStateColor, new FooterActionsViewModel$settings$1(this));
        this.power = z ? new FooterActionsButtonViewModel(R$id.pm_lite, new Icon.Resource(17301552, new ContentDescription.Resource(R$string.accessibility_quick_settings_power_menu)), Integer.valueOf(Utils.getColorAttrDefaultColor(contextThemeWrapper, 17957107)), 16843829, new FooterActionsViewModel$power$1(this)) : footerActionsButtonViewModel;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModel$observeDeviceMonitoringDialogRequests$2.emit(kotlin.Unit, kotlin.coroutines.Continuation<? super kotlin.Unit>):java.lang.Object] */
    public static final /* synthetic */ FooterActionsInteractor access$getFooterActionsInteractor$p(FooterActionsViewModel footerActionsViewModel) {
        return footerActionsViewModel.footerActionsInteractor;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModel$special$$inlined$map$2.2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object] */
    public static final /* synthetic */ FooterActionsButtonViewModel access$userSwitcherButton(FooterActionsViewModel footerActionsViewModel, UserSwitcherStatusModel.Enabled enabled) {
        return footerActionsViewModel.userSwitcherButton(enabled);
    }

    public final StateFlow<Float> getAlpha() {
        return this.alpha;
    }

    public final StateFlow<Float> getBackgroundAlpha() {
        return this.backgroundAlpha;
    }

    public final Flow<FooterActionsForegroundServicesButtonViewModel> getForegroundServices() {
        return this.foregroundServices;
    }

    public final FooterActionsButtonViewModel getPower() {
        return this.power;
    }

    public final Flow<FooterActionsSecurityButtonViewModel> getSecurity() {
        return this.security;
    }

    public final FooterActionsButtonViewModel getSettings() {
        return this.settings;
    }

    public final Flow<FooterActionsButtonViewModel> getUserSwitcher() {
        return this.userSwitcher;
    }

    public final StateFlow<Boolean> isVisible() {
        return this.isVisible;
    }

    public final Object observeDeviceMonitoringDialogRequests(final Context context, Continuation<? super Unit> continuation) {
        Object collect = this.footerActionsInteractor.getDeviceMonitoringDialogRequests().collect(new FlowCollector() { // from class: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModel$observeDeviceMonitoringDialogRequests$2
            public /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation2) {
                return emit((Unit) obj, (Continuation<? super Unit>) continuation2);
            }

            public final Object emit(Unit unit, Continuation<? super Unit> continuation2) {
                FooterActionsViewModel.access$getFooterActionsInteractor$p(FooterActionsViewModel.this).showDeviceMonitoringDialog(context, null);
                return Unit.INSTANCE;
            }
        }, continuation);
        return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
    }

    public final void onForegroundServiceButtonClicked(Expandable expandable) {
        if (this.falsingManager.isFalseTap(1)) {
            return;
        }
        this.footerActionsInteractor.showForegroundServicesDialog(expandable);
    }

    public final void onPowerButtonClicked(Expandable expandable) {
        if (this.falsingManager.isFalseTap(1)) {
            return;
        }
        this.footerActionsInteractor.showPowerMenuDialog(this.globalActionsDialogLite, expandable);
    }

    public final void onQuickSettingsExpansionChanged(float f, boolean z) {
        if (z) {
            this._alpha.setValue(Float.valueOf(f));
            this._backgroundAlpha.setValue(Float.valueOf(Math.max((float) ActionBarShadowController.ELEVATION_LOW, f - 0.99f) / 0.00999999f));
            return;
        }
        this._alpha.setValue(Float.valueOf(Math.max((float) ActionBarShadowController.ELEVATION_LOW, f - 0.9f) / (1 - 0.9f)));
        this._backgroundAlpha.setValue(Float.valueOf(1.0f));
    }

    public final void onSecurityButtonClicked(Context context, Expandable expandable) {
        if (this.falsingManager.isFalseTap(1)) {
            return;
        }
        this.footerActionsInteractor.showDeviceMonitoringDialog(context, expandable);
    }

    public final void onSettingsButtonClicked(Expandable expandable) {
        if (this.falsingManager.isFalseTap(1)) {
            return;
        }
        this.footerActionsInteractor.showSettings(expandable);
    }

    public final void onUserSwitcherClicked(Expandable expandable) {
        if (this.falsingManager.isFalseTap(1)) {
            return;
        }
        this.footerActionsInteractor.showUserSwitcher(this.context, expandable);
    }

    public final void onVisibilityChangeRequested(boolean z) {
        this._isVisible.setValue(Boolean.valueOf(z));
    }

    public final FooterActionsButtonViewModel userSwitcherButton(UserSwitcherStatusModel.Enabled enabled) {
        Drawable currentUserImage = enabled.getCurrentUserImage();
        Intrinsics.checkNotNull(currentUserImage);
        return new FooterActionsButtonViewModel(R$id.multi_user_switch, new Icon.Loaded(currentUserImage, new ContentDescription.Loaded(userSwitcherContentDescription(enabled.getCurrentUserName()))), null, R$attr.offStateColor, new FooterActionsViewModel$userSwitcherButton$1(this));
    }

    public final String userSwitcherContentDescription(String str) {
        return str != null ? this.context.getString(R$string.accessibility_quick_settings_user, new Object[]{str}) : null;
    }
}