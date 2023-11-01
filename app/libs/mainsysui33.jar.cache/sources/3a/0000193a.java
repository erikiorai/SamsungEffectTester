package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLegacySettingSyncer;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SettingsProxyExt;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceLegacySettingSyncer.class */
public final class KeyguardQuickAffordanceLegacySettingSyncer {
    public final CoroutineDispatcher backgroundDispatcher;
    public final CoroutineScope scope;
    public final SecureSettings secureSettings;
    public final KeyguardQuickAffordanceLocalUserSelectionManager selectionsManager;
    public static final Companion Companion = new Companion(null);
    public static final List<Binding> BINDINGS = CollectionsKt__CollectionsKt.listOf(new Binding[]{new Binding("lockscreen_show_controls", "bottom_start", BcSmartspaceDataPlugin.UI_SURFACE_HOME_SCREEN), new Binding("lockscreen_show_wallet", "bottom_end", "wallet"), new Binding("lock_screen_show_qr_code_scanner", "bottom_end", "qr_code_scanner")});

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceLegacySettingSyncer$Binding.class */
    public static final class Binding {
        public final String affordanceId;
        public final String settingsKey;
        public final String slotId;

        public Binding(String str, String str2, String str3) {
            this.settingsKey = str;
            this.slotId = str2;
            this.affordanceId = str3;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof Binding) {
                Binding binding = (Binding) obj;
                return Intrinsics.areEqual(this.settingsKey, binding.settingsKey) && Intrinsics.areEqual(this.slotId, binding.slotId) && Intrinsics.areEqual(this.affordanceId, binding.affordanceId);
            }
            return false;
        }

        public final String getAffordanceId() {
            return this.affordanceId;
        }

        public final String getSettingsKey() {
            return this.settingsKey;
        }

        public final String getSlotId() {
            return this.slotId;
        }

        public int hashCode() {
            return (((this.settingsKey.hashCode() * 31) + this.slotId.hashCode()) * 31) + this.affordanceId.hashCode();
        }

        public String toString() {
            String str = this.settingsKey;
            String str2 = this.slotId;
            String str3 = this.affordanceId;
            return "Binding(settingsKey=" + str + ", slotId=" + str2 + ", affordanceId=" + str3 + ")";
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceLegacySettingSyncer$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public KeyguardQuickAffordanceLegacySettingSyncer(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, SecureSettings secureSettings, KeyguardQuickAffordanceLocalUserSelectionManager keyguardQuickAffordanceLocalUserSelectionManager) {
        this.scope = coroutineScope;
        this.backgroundDispatcher = coroutineDispatcher;
        this.secureSettings = secureSettings;
        this.selectionsManager = keyguardQuickAffordanceLocalUserSelectionManager;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$$inlined$map$1.2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object] */
    public static final /* synthetic */ boolean access$isSet(KeyguardQuickAffordanceLegacySettingSyncer keyguardQuickAffordanceLegacySettingSyncer, String str) {
        return keyguardQuickAffordanceLegacySettingSyncer.isSet(str);
    }

    public static /* synthetic */ Job startSyncing$default(KeyguardQuickAffordanceLegacySettingSyncer keyguardQuickAffordanceLegacySettingSyncer, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            list = BINDINGS;
        }
        return keyguardQuickAffordanceLegacySettingSyncer.startSyncing(list);
    }

    public final boolean isSelected(String str) {
        return CollectionsKt___CollectionsKt.toSet(CollectionsKt__IterablesKt.flatten(this.selectionsManager.getSelections().values())).contains(str);
    }

    public final boolean isSet(String str) {
        boolean z = false;
        if (this.secureSettings.getIntForUser(str, 0, -2) != 0) {
            z = true;
        }
        return z;
    }

    public final void select(String str, String str2) {
        List<String> list = this.selectionsManager.getSelections().get(str);
        List<String> list2 = list;
        if (list == null) {
            list2 = CollectionsKt__CollectionsKt.emptyList();
        }
        this.selectionsManager.setSelections(str, CollectionsKt___CollectionsKt.plus(list2, CollectionsKt__CollectionsJVMKt.listOf(str2)));
    }

    public final Object set(String str, boolean z, Continuation<? super Unit> continuation) {
        Object withContext = BuildersKt.withContext(this.backgroundDispatcher, new KeyguardQuickAffordanceLegacySettingSyncer$set$2(this, str, z, null), continuation);
        return withContext == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? withContext : Unit.INSTANCE;
    }

    public final Job startSyncing(List<Binding> list) {
        return BuildersKt.launch$default(this.scope, (CoroutineContext) null, (CoroutineStart) null, new KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$1(list, this, null), 3, (Object) null);
    }

    public final void startSyncing(CoroutineScope coroutineScope, final Binding binding) {
        final Flow observerFlow = SettingsProxyExt.INSTANCE.observerFlow(this.secureSettings, new String[]{binding.getSettingsKey()}, -1);
        FlowKt.launchIn(FlowKt.flowOn(FlowKt.onEach(FlowKt.distinctUntilChanged(new Flow<Boolean>() { // from class: com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$$inlined$map$1$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$$inlined$map$1$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ KeyguardQuickAffordanceLegacySettingSyncer.Binding $binding$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ KeyguardQuickAffordanceLegacySettingSyncer this$0;

                @DebugMetadata(c = "com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$$inlined$map$1$2", f = "KeyguardQuickAffordanceLegacySettingSyncer.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$$inlined$map$1$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$$inlined$map$1$2$1.class */
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

                public AnonymousClass2(FlowCollector flowCollector, KeyguardQuickAffordanceLegacySettingSyncer keyguardQuickAffordanceLegacySettingSyncer, KeyguardQuickAffordanceLegacySettingSyncer.Binding binding) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = keyguardQuickAffordanceLegacySettingSyncer;
                    this.$binding$inlined = binding;
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
                                Boolean boxBoolean = Boxing.boxBoolean(KeyguardQuickAffordanceLegacySettingSyncer.access$isSet(this.this$0, this.$binding$inlined.getSettingsKey()));
                                anonymousClass1.label = 1;
                                if (flowCollector.emit(boxBoolean, anonymousClass1) == coroutine_suspended) {
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
                Object collect = observerFlow.collect(new AnonymousClass2(flowCollector, this, binding), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        }), new KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$3(this, binding, null)), this.backgroundDispatcher), coroutineScope);
        final Flow<Map<String, List<String>>> mo2945getSelections = this.selectionsManager.mo2945getSelections();
        final Flow<Set<? extends String>> flow = new Flow<Set<? extends String>>() { // from class: com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$$inlined$map$2

            /* renamed from: com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$$inlined$map$2$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$$inlined$map$2$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                @DebugMetadata(c = "com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$$inlined$map$2$2", f = "KeyguardQuickAffordanceLegacySettingSyncer.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$$inlined$map$2$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$$inlined$map$2$2$1.class */
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
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
                                Set set = CollectionsKt___CollectionsKt.toSet(CollectionsKt__IterablesKt.flatten(((Map) obj).values()));
                                anonymousClass1.label = 1;
                                if (flowCollector.emit(set, anonymousClass1) == coroutine_suspended) {
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
                Object collect = mo2945getSelections.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        };
        FlowKt.launchIn(FlowKt.flowOn(FlowKt.onEach(FlowKt.distinctUntilChanged(new Flow<Boolean>() { // from class: com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$$inlined$map$3

            /* renamed from: com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$$inlined$map$3$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$$inlined$map$3$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ KeyguardQuickAffordanceLegacySettingSyncer.Binding $binding$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                @DebugMetadata(c = "com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$$inlined$map$3$2", f = "KeyguardQuickAffordanceLegacySettingSyncer.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$$inlined$map$3$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$$inlined$map$3$2$1.class */
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

                public AnonymousClass2(FlowCollector flowCollector, KeyguardQuickAffordanceLegacySettingSyncer.Binding binding) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$binding$inlined = binding;
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
                                Boolean boxBoolean = Boxing.boxBoolean(((Set) obj).contains(this.$binding$inlined.getAffordanceId()));
                                anonymousClass1.label = 1;
                                if (flowCollector.emit(boxBoolean, anonymousClass1) == coroutine_suspended) {
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
                Object collect = flow.collect(new AnonymousClass2(flowCollector, binding), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        }), new KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$6(this, binding, null)), this.backgroundDispatcher), coroutineScope);
    }

    public final void unselect(String str) {
        Map<String, List<String>> selections = this.selectionsManager.getSelections();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<String, List<String>> entry : selections.entrySet()) {
            if (entry.getValue().contains(str)) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        ArrayList<String> arrayList = new ArrayList(linkedHashMap.size());
        for (Map.Entry entry2 : linkedHashMap.entrySet()) {
            arrayList.add((String) entry2.getKey());
        }
        for (String str2 : arrayList) {
            List<String> list = selections.get(str2);
            List<String> list2 = list;
            if (list == null) {
                list2 = CollectionsKt__CollectionsKt.emptyList();
            }
            List<String> mutableList = CollectionsKt___CollectionsKt.toMutableList(list2);
            mutableList.remove(str);
            this.selectionsManager.setSelections(str2, mutableList);
        }
    }
}