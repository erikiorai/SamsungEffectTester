package com.android.systemui.keyguard.data.quickaffordance;

import android.os.UserHandle;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shared.customization.data.content.CustomizationProviderClient;
import java.util.List;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceRemoteUserSelectionManager.class */
public final class KeyguardQuickAffordanceRemoteUserSelectionManager implements KeyguardQuickAffordanceSelectionManager {
    public static final Companion Companion = new Companion(null);
    public final StateFlow<Map<String, List<String>>> _selections;
    public final KeyguardQuickAffordanceProviderClientFactory clientFactory;
    public final StateFlow<CustomizationProviderClient> clientOrNull;
    public final CoroutineScope scope;
    public final Flow<Map<String, List<String>>> selections;
    public final UserHandle userHandle;
    public final Flow<Integer> userId;
    public final UserTracker userTracker;

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceRemoteUserSelectionManager$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public KeyguardQuickAffordanceRemoteUserSelectionManager(CoroutineScope coroutineScope, UserTracker userTracker, KeyguardQuickAffordanceProviderClientFactory keyguardQuickAffordanceProviderClientFactory, UserHandle userHandle) {
        this.scope = coroutineScope;
        this.userTracker = userTracker;
        this.clientFactory = keyguardQuickAffordanceProviderClientFactory;
        this.userHandle = userHandle;
        Flow<Integer> conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE.conflatedCallbackFlow(new KeyguardQuickAffordanceRemoteUserSelectionManager$userId$1(this, null));
        this.userId = conflatedCallbackFlow;
        final Flow distinctUntilChanged = FlowKt.distinctUntilChanged(conflatedCallbackFlow);
        Flow<CustomizationProviderClient> flow = new Flow<CustomizationProviderClient>() { // from class: com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceRemoteUserSelectionManager$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceRemoteUserSelectionManager$special$$inlined$map$1$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceRemoteUserSelectionManager$special$$inlined$map$1$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ KeyguardQuickAffordanceRemoteUserSelectionManager this$0;

                @DebugMetadata(c = "com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceRemoteUserSelectionManager$special$$inlined$map$1$2", f = "KeyguardQuickAffordanceRemoteUserSelectionManager.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceRemoteUserSelectionManager$special$$inlined$map$1$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceRemoteUserSelectionManager$special$$inlined$map$1$2$1.class */
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

                public AnonymousClass2(FlowCollector flowCollector, KeyguardQuickAffordanceRemoteUserSelectionManager keyguardQuickAffordanceRemoteUserSelectionManager) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = keyguardQuickAffordanceRemoteUserSelectionManager;
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
                                CustomizationProviderClient create = (!KeyguardQuickAffordanceRemoteUserSelectionManager.access$getUserHandle$p(this.this$0).isSystem() || KeyguardQuickAffordanceRemoteUserSelectionManager.access$getUserHandle$p(this.this$0).getIdentifier() == ((Number) obj).intValue()) ? null : KeyguardQuickAffordanceRemoteUserSelectionManager.access$getClientFactory$p(this.this$0).create();
                                anonymousClass1.label = 1;
                                if (flowCollector.emit(create, anonymousClass1) == coroutine_suspended) {
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
                Object collect = distinctUntilChanged.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion companion = SharingStarted.Companion;
        Flow stateIn = FlowKt.stateIn(flow, coroutineScope, companion.getEagerly(), (Object) null);
        this.clientOrNull = stateIn;
        Flow<Map<String, List<String>>> stateIn2 = FlowKt.stateIn(FlowKt.transformLatest(stateIn, new KeyguardQuickAffordanceRemoteUserSelectionManager$special$$inlined$flatMapLatest$1(null)), coroutineScope, companion.getEagerly(), MapsKt__MapsKt.emptyMap());
        this._selections = stateIn2;
        this.selections = stateIn2;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceRemoteUserSelectionManager$special$$inlined$map$1.2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object] */
    public static final /* synthetic */ KeyguardQuickAffordanceProviderClientFactory access$getClientFactory$p(KeyguardQuickAffordanceRemoteUserSelectionManager keyguardQuickAffordanceRemoteUserSelectionManager) {
        return keyguardQuickAffordanceRemoteUserSelectionManager.clientFactory;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceRemoteUserSelectionManager$special$$inlined$map$1.2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object] */
    public static final /* synthetic */ UserHandle access$getUserHandle$p(KeyguardQuickAffordanceRemoteUserSelectionManager keyguardQuickAffordanceRemoteUserSelectionManager) {
        return keyguardQuickAffordanceRemoteUserSelectionManager.userHandle;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceSelectionManager
    public Map<String, List<String>> getSelections() {
        return (Map) this._selections.getValue();
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceSelectionManager
    /* renamed from: getSelections */
    public Flow<Map<String, List<String>>> mo2945getSelections() {
        return this.selections;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceSelectionManager
    public void setSelections(String str, List<String> list) {
        CustomizationProviderClient customizationProviderClient = (CustomizationProviderClient) this.clientOrNull.getValue();
        if (customizationProviderClient != null) {
            BuildersKt.launch$default(this.scope, (CoroutineContext) null, (CoroutineStart) null, new KeyguardQuickAffordanceRemoteUserSelectionManager$setSelections$1$1(customizationProviderClient, str, list, null), 3, (Object) null);
        }
    }
}