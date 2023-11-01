package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import android.net.Uri;
import android.service.notification.ZenModeConfig;
import com.android.settingslib.notification.EnableZenModeDialog;
import com.android.settingslib.notification.ZenModeDialogMetricsLogger;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.R$style;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.shared.quickaffordance.ActivationState;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SettingsProxy;
import com.android.systemui.util.settings.SettingsProxyExt;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/DoNotDisturbQuickAffordanceConfig.class */
public final class DoNotDisturbQuickAffordanceConfig implements KeyguardQuickAffordanceConfig {
    public static final Companion Companion = new Companion(null);
    public final CoroutineDispatcher backgroundDispatcher;
    public final Context context;
    public final ZenModeController controller;
    public final Lazy dialog$delegate;
    public int dndMode;
    public boolean isAvailable;
    public final String key;
    public final Flow<KeyguardQuickAffordanceConfig.LockScreenState> lockScreenState;
    public final int pickerIconResourceId;
    public final String pickerName;
    public final SecureSettings secureSettings;
    public int settingsValue;
    public final Uri testConditionId;
    public final UserTracker userTracker;

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/DoNotDisturbQuickAffordanceConfig$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public DoNotDisturbQuickAffordanceConfig(Context context, ZenModeController zenModeController, SecureSettings secureSettings, UserTracker userTracker, CoroutineDispatcher coroutineDispatcher) {
        this(context, zenModeController, secureSettings, userTracker, coroutineDispatcher, null, null);
    }

    public DoNotDisturbQuickAffordanceConfig(Context context, ZenModeController zenModeController, SecureSettings secureSettings, UserTracker userTracker, CoroutineDispatcher coroutineDispatcher, Uri uri, final EnableZenModeDialog enableZenModeDialog) {
        this.context = context;
        this.controller = zenModeController;
        this.secureSettings = secureSettings;
        this.userTracker = userTracker;
        this.backgroundDispatcher = coroutineDispatcher;
        this.testConditionId = uri;
        this.dialog$delegate = LazyKt__LazyJVMKt.lazy(new Function0<EnableZenModeDialog>() { // from class: com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$dialog$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX DEBUG: Method merged with bridge method */
            /* renamed from: invoke */
            public final EnableZenModeDialog m2919invoke() {
                EnableZenModeDialog enableZenModeDialog2 = EnableZenModeDialog.this;
                EnableZenModeDialog enableZenModeDialog3 = enableZenModeDialog2;
                if (enableZenModeDialog2 == null) {
                    enableZenModeDialog3 = new EnableZenModeDialog(DoNotDisturbQuickAffordanceConfig.access$getContext$p(this), R$style.Theme_SystemUI_Dialog, true, new ZenModeDialogMetricsLogger(DoNotDisturbQuickAffordanceConfig.access$getContext$p(this)));
                }
                return enableZenModeDialog3;
            }
        });
        this.key = "do_not_disturb";
        this.pickerName = context.getString(R$string.quick_settings_dnd_label);
        this.pickerIconResourceId = R$drawable.ic_do_not_disturb;
        Flow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE.conflatedCallbackFlow(new DoNotDisturbQuickAffordanceConfig$lockScreenState$1(this, null));
        final Flow onStart = FlowKt.onStart(SettingsProxyExt.observerFlow$default(SettingsProxyExt.INSTANCE, (SettingsProxy) secureSettings, new String[]{"zen_duration"}, 0, 2, (Object) null), new DoNotDisturbQuickAffordanceConfig$lockScreenState$2(null));
        this.lockScreenState = FlowKt.combine(conflatedCallbackFlow, FlowKt.onEach(FlowKt.distinctUntilChanged(FlowKt.flowOn(new Flow<Integer>() { // from class: com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$special$$inlined$map$1$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/DoNotDisturbQuickAffordanceConfig$special$$inlined$map$1$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ DoNotDisturbQuickAffordanceConfig this$0;

                @DebugMetadata(c = "com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$special$$inlined$map$1$2", f = "DoNotDisturbQuickAffordanceConfig.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$special$$inlined$map$1$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/DoNotDisturbQuickAffordanceConfig$special$$inlined$map$1$2$1.class */
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

                public AnonymousClass2(FlowCollector flowCollector, DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = doNotDisturbQuickAffordanceConfig;
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
                                Unit unit = (Unit) obj;
                                Integer boxInt = Boxing.boxInt(DoNotDisturbQuickAffordanceConfig.access$getSecureSettings$p(this.this$0).getInt("zen_duration", 0));
                                anonymousClass1.label = 1;
                                if (flowCollector.emit(boxInt, anonymousClass1) == coroutine_suspended) {
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
                Object collect = onStart.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        }, coroutineDispatcher)), new DoNotDisturbQuickAffordanceConfig$lockScreenState$4(this, null)), new DoNotDisturbQuickAffordanceConfig$lockScreenState$5(null));
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$dialog$2.invoke():com.android.settingslib.notification.EnableZenModeDialog] */
    public static final /* synthetic */ Context access$getContext$p(DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig) {
        return doNotDisturbQuickAffordanceConfig.context;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$special$$inlined$map$1.2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object] */
    public static final /* synthetic */ SecureSettings access$getSecureSettings$p(DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig) {
        return doNotDisturbQuickAffordanceConfig.secureSettings;
    }

    public final Uri getConditionUri() {
        Uri uri = this.testConditionId;
        Uri uri2 = uri;
        if (uri == null) {
            uri2 = ZenModeConfig.toTimeCondition(this.context, this.settingsValue, this.userTracker.getUserId(), true).id;
        }
        return uri2;
    }

    public final EnableZenModeDialog getDialog() {
        return (EnableZenModeDialog) this.dialog$delegate.getValue();
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public String getKey() {
        return this.key;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public Flow<KeyguardQuickAffordanceConfig.LockScreenState> getLockScreenState() {
        return this.lockScreenState;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public int getPickerIconResourceId() {
        return this.pickerIconResourceId;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public String getPickerName() {
        return this.pickerName;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public Object getPickerScreenState(Continuation<? super KeyguardQuickAffordanceConfig.PickerScreenState> continuation) {
        return this.controller.isZenAvailable() ? KeyguardQuickAffordanceConfig.PickerScreenState.Default.INSTANCE : KeyguardQuickAffordanceConfig.PickerScreenState.UnavailableOnDevice.INSTANCE;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered(Expandable expandable) {
        KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggeredResult;
        if (!this.isAvailable) {
            onTriggeredResult = KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled.INSTANCE;
        } else if (this.dndMode != 0) {
            this.controller.setZen(0, (Uri) null, "DoNotDisturbQuickAffordanceConfig");
            onTriggeredResult = KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled.INSTANCE;
        } else {
            int i = this.settingsValue;
            if (i == -1) {
                onTriggeredResult = new KeyguardQuickAffordanceConfig.OnTriggeredResult.ShowDialog(getDialog().createDialog(), expandable);
            } else if (i == 0) {
                this.controller.setZen(1, (Uri) null, "DoNotDisturbQuickAffordanceConfig");
                onTriggeredResult = KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled.INSTANCE;
            } else {
                this.controller.setZen(1, getConditionUri(), "DoNotDisturbQuickAffordanceConfig");
                onTriggeredResult = KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled.INSTANCE;
            }
        }
        return onTriggeredResult;
    }

    public final KeyguardQuickAffordanceConfig.LockScreenState updateState() {
        return !this.isAvailable ? KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE : this.dndMode == 0 ? new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Resource(R$drawable.qs_dnd_icon_off, new ContentDescription.Resource(R$string.dnd_is_off)), ActivationState.Inactive.INSTANCE) : new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Resource(R$drawable.qs_dnd_icon_on, new ContentDescription.Resource(R$string.dnd_is_on)), ActivationState.Active.INSTANCE);
    }
}