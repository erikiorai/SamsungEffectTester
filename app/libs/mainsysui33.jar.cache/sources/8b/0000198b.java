package com.android.systemui.keyguard.data.repository;

import android.content.Context;
import android.os.UserHandle;
import com.android.systemui.Dumpable;
import com.android.systemui.R$array;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLegacySettingSyncer;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLocalUserSelectionManager;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceRemoteUserSelectionManager;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceSelectionManager;
import com.android.systemui.keyguard.shared.model.KeyguardQuickAffordancePickerRepresentation;
import com.android.systemui.keyguard.shared.model.KeyguardSlotPickerRepresentation;
import com.android.systemui.settings.UserTracker;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardQuickAffordanceRepository.class */
public final class KeyguardQuickAffordanceRepository {
    public static final Companion Companion = new Companion(null);
    public final Lazy _slotPickerRepresentations$delegate;
    public final Context appContext;
    public final Set<KeyguardQuickAffordanceConfig> configs;
    public final KeyguardQuickAffordanceLocalUserSelectionManager localUserSelectionManager;
    public final KeyguardQuickAffordanceRemoteUserSelectionManager remoteUserSelectionManager;
    public final CoroutineScope scope;
    public final StateFlow<KeyguardQuickAffordanceSelectionManager> selectionManager;
    public final StateFlow<Map<String, List<KeyguardQuickAffordanceConfig>>> selections;
    public final Flow<Integer> userId;
    public final UserTracker userTracker;

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardQuickAffordanceRepository$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardQuickAffordanceRepository$Dumpster.class */
    public final class Dumpster implements Dumpable {
        public Dumpster() {
            KeyguardQuickAffordanceRepository.this = r4;
        }

        @Override // com.android.systemui.Dumpable
        public void dump(PrintWriter printWriter, String[] strArr) {
            List<KeyguardSlotPickerRepresentation> slotPickerRepresentations = KeyguardQuickAffordanceRepository.this.getSlotPickerRepresentations();
            Map<String, List<String>> selections = KeyguardQuickAffordanceRepository.this.getSelections();
            printWriter.println("Slots & selections:");
            for (KeyguardSlotPickerRepresentation keyguardSlotPickerRepresentation : slotPickerRepresentations) {
                String id = keyguardSlotPickerRepresentation.getId();
                int maxSelectedAffordances = keyguardSlotPickerRepresentation.getMaxSelectedAffordances();
                List<String> list = selections.get(id);
                List<String> list2 = list;
                printWriter.println("    " + id + (list2 == null || list2.isEmpty() ? " is empty" : ": " + CollectionsKt___CollectionsKt.joinToString$default(list, ", ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null)) + " (capacity = " + maxSelectedAffordances + ")");
            }
            printWriter.println("Available affordances on device:");
            for (KeyguardQuickAffordanceConfig keyguardQuickAffordanceConfig : KeyguardQuickAffordanceRepository.this.configs) {
                printWriter.println("    " + keyguardQuickAffordanceConfig.getKey() + " (\"" + keyguardQuickAffordanceConfig.getPickerName() + "\")");
            }
        }
    }

    public KeyguardQuickAffordanceRepository(Context context, CoroutineScope coroutineScope, KeyguardQuickAffordanceLocalUserSelectionManager keyguardQuickAffordanceLocalUserSelectionManager, KeyguardQuickAffordanceRemoteUserSelectionManager keyguardQuickAffordanceRemoteUserSelectionManager, UserTracker userTracker, KeyguardQuickAffordanceLegacySettingSyncer keyguardQuickAffordanceLegacySettingSyncer, Set<KeyguardQuickAffordanceConfig> set, DumpManager dumpManager, final UserHandle userHandle) {
        this.appContext = context;
        this.scope = coroutineScope;
        this.localUserSelectionManager = keyguardQuickAffordanceLocalUserSelectionManager;
        this.remoteUserSelectionManager = keyguardQuickAffordanceRemoteUserSelectionManager;
        this.userTracker = userTracker;
        this.configs = set;
        Flow<Integer> conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE.conflatedCallbackFlow(new KeyguardQuickAffordanceRepository$userId$1(this, null));
        this.userId = conflatedCallbackFlow;
        final Flow distinctUntilChanged = FlowKt.distinctUntilChanged(conflatedCallbackFlow);
        Flow<KeyguardQuickAffordanceSelectionManager> flow = new Flow<KeyguardQuickAffordanceSelectionManager>() { // from class: com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$special$$inlined$map$1$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardQuickAffordanceRepository$special$$inlined$map$1$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ UserHandle $userHandle$inlined;
                public final /* synthetic */ KeyguardQuickAffordanceRepository this$0;

                @DebugMetadata(c = "com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$special$$inlined$map$1$2", f = "KeyguardQuickAffordanceRepository.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$special$$inlined$map$1$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardQuickAffordanceRepository$special$$inlined$map$1$2$1.class */
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

                public AnonymousClass2(FlowCollector flowCollector, UserHandle userHandle, KeyguardQuickAffordanceRepository keyguardQuickAffordanceRepository) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$userHandle$inlined = userHandle;
                    this.this$0 = keyguardQuickAffordanceRepository;
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
                                KeyguardQuickAffordanceSelectionManager access$getLocalUserSelectionManager$p = this.$userHandle$inlined.getIdentifier() == ((Number) obj).intValue() ? KeyguardQuickAffordanceRepository.access$getLocalUserSelectionManager$p(this.this$0) : KeyguardQuickAffordanceRepository.access$getRemoteUserSelectionManager$p(this.this$0);
                                anonymousClass1.label = 1;
                                if (flowCollector.emit(access$getLocalUserSelectionManager$p, anonymousClass1) == coroutine_suspended) {
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
                Object collect = distinctUntilChanged.collect(new AnonymousClass2(flowCollector, userHandle, this), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion companion = SharingStarted.Companion;
        Flow stateIn = FlowKt.stateIn(flow, coroutineScope, companion.getEagerly(), keyguardQuickAffordanceLocalUserSelectionManager);
        this.selectionManager = stateIn;
        this.selections = FlowKt.stateIn(FlowKt.transformLatest(stateIn, new KeyguardQuickAffordanceRepository$special$$inlined$flatMapLatest$1(null, this)), coroutineScope, companion.getEagerly(), MapsKt__MapsKt.emptyMap());
        this._slotPickerRepresentations$delegate = LazyKt__LazyJVMKt.lazy(new Function0<List<? extends KeyguardSlotPickerRepresentation>>() { // from class: com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$_slotPickerRepresentations$2
            {
                super(0);
            }

            public static final Pair<String, Integer> invoke$parseSlot(String str) {
                List split$default = StringsKt__StringsKt.split$default(str, new String[]{":"}, false, 0, 6, (Object) null);
                if (split$default.size() == 2) {
                    return TuplesKt.to((String) split$default.get(0), Integer.valueOf(Integer.parseInt((String) split$default.get(1))));
                }
                throw new IllegalStateException("Check failed.".toString());
            }

            /* JADX DEBUG: Method merged with bridge method */
            public final List<KeyguardSlotPickerRepresentation> invoke() {
                String[] stringArray = KeyguardQuickAffordanceRepository.access$getAppContext$p(KeyguardQuickAffordanceRepository.this).getResources().getStringArray(R$array.config_keyguardQuickAffordanceSlots);
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                ArrayList arrayList = new ArrayList();
                for (String str : stringArray) {
                    Pair<String, Integer> invoke$parseSlot = invoke$parseSlot(str);
                    String str2 = (String) invoke$parseSlot.component1();
                    int intValue = ((Number) invoke$parseSlot.component2()).intValue();
                    if (!(!linkedHashSet.contains(str2))) {
                        throw new IllegalStateException(("Duplicate slot \"" + str2 + "\"!").toString());
                    }
                    linkedHashSet.add(str2);
                    arrayList.add(new KeyguardSlotPickerRepresentation(str2, intValue));
                }
                return arrayList;
            }
        });
        KeyguardQuickAffordanceLegacySettingSyncer.startSyncing$default(keyguardQuickAffordanceLegacySettingSyncer, null, 1, null);
        DumpManager.registerDumpable$default(dumpManager, "KeyguardQuickAffordances", new Dumpster(), null, 4, null);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$_slotPickerRepresentations$2.invoke():java.util.List<com.android.systemui.keyguard.shared.model.KeyguardSlotPickerRepresentation>] */
    public static final /* synthetic */ Context access$getAppContext$p(KeyguardQuickAffordanceRepository keyguardQuickAffordanceRepository) {
        return keyguardQuickAffordanceRepository.appContext;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$special$$inlined$map$1.2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object] */
    public static final /* synthetic */ KeyguardQuickAffordanceLocalUserSelectionManager access$getLocalUserSelectionManager$p(KeyguardQuickAffordanceRepository keyguardQuickAffordanceRepository) {
        return keyguardQuickAffordanceRepository.localUserSelectionManager;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository$special$$inlined$map$1.2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object] */
    public static final /* synthetic */ KeyguardQuickAffordanceRemoteUserSelectionManager access$getRemoteUserSelectionManager$p(KeyguardQuickAffordanceRepository keyguardQuickAffordanceRepository) {
        return keyguardQuickAffordanceRepository.remoteUserSelectionManager;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0111  */
    /* JADX WARN: Type inference failed for: r0v101, types: [java.util.Map] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:81:0x00fc -> B:82:0x00fe). Please submit an issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getAffordancePickerRepresentations(Continuation<? super List<KeyguardQuickAffordancePickerRepresentation>> continuation) {
        KeyguardQuickAffordanceRepository$getAffordancePickerRepresentations$1 keyguardQuickAffordanceRepository$getAffordancePickerRepresentations$1;
        int i;
        LinkedHashMap linkedHashMap;
        Iterator it;
        KeyguardQuickAffordanceRepository$getAffordancePickerRepresentations$1 keyguardQuickAffordanceRepository$getAffordancePickerRepresentations$12;
        if (continuation instanceof KeyguardQuickAffordanceRepository$getAffordancePickerRepresentations$1) {
            keyguardQuickAffordanceRepository$getAffordancePickerRepresentations$1 = (KeyguardQuickAffordanceRepository$getAffordancePickerRepresentations$1) continuation;
            int i2 = keyguardQuickAffordanceRepository$getAffordancePickerRepresentations$1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                keyguardQuickAffordanceRepository$getAffordancePickerRepresentations$1.label = i2 - 2147483648;
                Object obj = keyguardQuickAffordanceRepository$getAffordancePickerRepresentations$1.result;
                Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                i = keyguardQuickAffordanceRepository$getAffordancePickerRepresentations$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    Set<KeyguardQuickAffordanceConfig> set = this.configs;
                    linkedHashMap = new LinkedHashMap(RangesKt___RangesKt.coerceAtLeast(MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(set, 10)), 16));
                    it = set.iterator();
                    keyguardQuickAffordanceRepository$getAffordancePickerRepresentations$12 = keyguardQuickAffordanceRepository$getAffordancePickerRepresentations$1;
                    if (it.hasNext()) {
                    }
                } else if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                } else {
                    Object next = keyguardQuickAffordanceRepository$getAffordancePickerRepresentations$1.L$3;
                    linkedHashMap = (Map) keyguardQuickAffordanceRepository$getAffordancePickerRepresentations$1.L$2;
                    it = (Iterator) keyguardQuickAffordanceRepository$getAffordancePickerRepresentations$1.L$1;
                    LinkedHashMap linkedHashMap2 = (LinkedHashMap) keyguardQuickAffordanceRepository$getAffordancePickerRepresentations$1.L$0;
                    ResultKt.throwOnFailure(obj);
                    keyguardQuickAffordanceRepository$getAffordancePickerRepresentations$12 = keyguardQuickAffordanceRepository$getAffordancePickerRepresentations$1;
                    LinkedHashMap linkedHashMap3 = linkedHashMap2;
                    linkedHashMap.put(next, (KeyguardQuickAffordanceConfig.PickerScreenState) obj);
                    linkedHashMap = linkedHashMap3;
                    if (it.hasNext()) {
                        next = it.next();
                        keyguardQuickAffordanceRepository$getAffordancePickerRepresentations$12.L$0 = linkedHashMap;
                        keyguardQuickAffordanceRepository$getAffordancePickerRepresentations$12.L$1 = it;
                        keyguardQuickAffordanceRepository$getAffordancePickerRepresentations$12.L$2 = linkedHashMap;
                        keyguardQuickAffordanceRepository$getAffordancePickerRepresentations$12.L$3 = next;
                        keyguardQuickAffordanceRepository$getAffordancePickerRepresentations$12.label = 1;
                        obj = ((KeyguardQuickAffordanceConfig) next).getPickerScreenState(keyguardQuickAffordanceRepository$getAffordancePickerRepresentations$12);
                        if (obj == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                        linkedHashMap3 = linkedHashMap;
                        linkedHashMap.put(next, (KeyguardQuickAffordanceConfig.PickerScreenState) obj);
                        linkedHashMap = linkedHashMap3;
                        if (it.hasNext()) {
                            LinkedHashMap linkedHashMap4 = new LinkedHashMap();
                            for (Map.Entry entry : linkedHashMap.entrySet()) {
                                if (!(((KeyguardQuickAffordanceConfig.PickerScreenState) entry.getValue()) instanceof KeyguardQuickAffordanceConfig.PickerScreenState.UnavailableOnDevice)) {
                                    linkedHashMap4.put(entry.getKey(), entry.getValue());
                                }
                            }
                            ArrayList arrayList = new ArrayList(linkedHashMap4.size());
                            for (Map.Entry entry2 : linkedHashMap4.entrySet()) {
                                KeyguardQuickAffordanceConfig keyguardQuickAffordanceConfig = (KeyguardQuickAffordanceConfig) entry2.getKey();
                                KeyguardQuickAffordanceConfig.PickerScreenState pickerScreenState = (KeyguardQuickAffordanceConfig.PickerScreenState) entry2.getValue();
                                String str = null;
                                KeyguardQuickAffordanceConfig.PickerScreenState.Disabled disabled = pickerScreenState instanceof KeyguardQuickAffordanceConfig.PickerScreenState.Disabled ? (KeyguardQuickAffordanceConfig.PickerScreenState.Disabled) pickerScreenState : null;
                                String key = keyguardQuickAffordanceConfig.getKey();
                                String pickerName = keyguardQuickAffordanceConfig.getPickerName();
                                int pickerIconResourceId = keyguardQuickAffordanceConfig.getPickerIconResourceId();
                                boolean z = pickerScreenState instanceof KeyguardQuickAffordanceConfig.PickerScreenState.Default;
                                List<String> instructions = disabled != null ? disabled.getInstructions() : null;
                                String actionText = disabled != null ? disabled.getActionText() : null;
                                if (disabled != null) {
                                    str = disabled.getActionComponentName();
                                }
                                arrayList.add(new KeyguardQuickAffordancePickerRepresentation(key, pickerName, pickerIconResourceId, z, instructions, actionText, str));
                            }
                            return arrayList;
                        }
                    }
                }
            }
        }
        keyguardQuickAffordanceRepository$getAffordancePickerRepresentations$1 = new KeyguardQuickAffordanceRepository$getAffordancePickerRepresentations$1(this, continuation);
        Object obj2 = keyguardQuickAffordanceRepository$getAffordancePickerRepresentations$1.result;
        Object coroutine_suspended2 = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        i = keyguardQuickAffordanceRepository$getAffordancePickerRepresentations$1.label;
        if (i != 0) {
        }
    }

    public final Map<String, List<String>> getSelections() {
        return ((KeyguardQuickAffordanceSelectionManager) this.selectionManager.getValue()).getSelections();
    }

    /* renamed from: getSelections */
    public final StateFlow<Map<String, List<KeyguardQuickAffordanceConfig>>> m2964getSelections() {
        return this.selections;
    }

    public final List<KeyguardSlotPickerRepresentation> getSlotPickerRepresentations() {
        return get_slotPickerRepresentations();
    }

    public final List<KeyguardSlotPickerRepresentation> get_slotPickerRepresentations() {
        return (List) this._slotPickerRepresentations$delegate.getValue();
    }

    public final void setSelections(String str, List<String> list) {
        ((KeyguardQuickAffordanceSelectionManager) this.selectionManager.getValue()).setSelections(str, list);
    }
}