package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import com.android.systemui.R$array;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserTracker;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceLocalUserSelectionManager.class */
public final class KeyguardQuickAffordanceLocalUserSelectionManager implements KeyguardQuickAffordanceSelectionManager {
    public static final Companion Companion = new Companion(null);
    public final Flow<Unit> backupRestorationEvents;
    public final Lazy defaults$delegate;
    public final Flow<Map<String, List<String>>> selections;
    public SharedPreferences sharedPrefs = instantiateSharedPrefs();
    public final UserFileManager userFileManager;
    public final Flow<Integer> userId;
    public final UserTracker userTracker;

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceLocalUserSelectionManager$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public KeyguardQuickAffordanceLocalUserSelectionManager(final Context context, UserFileManager userFileManager, UserTracker userTracker, BroadcastDispatcher broadcastDispatcher) {
        this.userFileManager = userFileManager;
        this.userTracker = userTracker;
        Flow<Integer> conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE.conflatedCallbackFlow(new KeyguardQuickAffordanceLocalUserSelectionManager$userId$1(this, null));
        this.userId = conflatedCallbackFlow;
        this.defaults$delegate = LazyKt__LazyJVMKt.lazy(new Function0<Map<String, ? extends List<? extends String>>>() { // from class: com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLocalUserSelectionManager$defaults$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX DEBUG: Method merged with bridge method */
            public final Map<String, List<String>> invoke() {
                String[] stringArray = context.getResources().getStringArray(R$array.config_keyguardQuickAffordanceDefaults);
                LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt___RangesKt.coerceAtLeast(MapsKt__MapsJVMKt.mapCapacity(stringArray.length), 16));
                for (String str : stringArray) {
                    List split$default = StringsKt__StringsKt.split$default(str, new String[]{":"}, false, 0, 6, (Object) null);
                    if (!(split$default.size() == 2)) {
                        throw new IllegalStateException("Check failed.".toString());
                    }
                    Pair pair = TuplesKt.to((String) split$default.get(0), StringsKt__StringsKt.split$default((CharSequence) split$default.get(1), new String[]{","}, false, 0, 6, (Object) null));
                    linkedHashMap.put(pair.getFirst(), pair.getSecond());
                }
                return linkedHashMap;
            }
        });
        Flow<Unit> broadcastFlow$default = BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("com.android.systemui.backup.RESTORE_FINISHED"), null, 4, "com.android.systemui.permission.SELF", 2, null);
        this.backupRestorationEvents = broadcastFlow$default;
        this.selections = FlowKt.transformLatest(FlowKt.combine(conflatedCallbackFlow, FlowKt.onStart(broadcastFlow$default, new KeyguardQuickAffordanceLocalUserSelectionManager$selections$1(null)), new KeyguardQuickAffordanceLocalUserSelectionManager$selections$2(null)), new KeyguardQuickAffordanceLocalUserSelectionManager$special$$inlined$flatMapLatest$1(null, this));
    }

    public final Map<String, List<String>> getDefaults() {
        return (Map) this.defaults$delegate.getValue();
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceSelectionManager
    public Map<String, List<String>> getSelections() {
        Set<String> keySet = this.sharedPrefs.getAll().keySet();
        ArrayList<String> arrayList = new ArrayList();
        for (Object obj : keySet) {
            if (StringsKt__StringsJVMKt.startsWith$default((String) obj, "slot_", false, 2, (Object) null)) {
                arrayList.add(obj);
            }
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt___RangesKt.coerceAtLeast(MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10)), 16));
        for (String str : arrayList) {
            String substring = str.substring(5);
            String string = this.sharedPrefs.getString(str, null);
            Pair pair = TuplesKt.to(substring, !(string == null || string.length() == 0) ? StringsKt__StringsKt.split$default(string, new String[]{","}, false, 0, 6, (Object) null) : CollectionsKt__CollectionsKt.emptyList());
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        Map<String, List<String>> mutableMap = MapsKt__MapsKt.toMutableMap(linkedHashMap);
        for (Map.Entry<String, List<String>> entry : getDefaults().entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();
            if (!mutableMap.containsKey(key)) {
                mutableMap.put(key, value);
            }
        }
        return mutableMap;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceSelectionManager
    /* renamed from: getSelections  reason: collision with other method in class */
    public Flow<Map<String, List<String>>> mo2945getSelections() {
        return this.selections;
    }

    public final SharedPreferences instantiateSharedPrefs() {
        return this.userFileManager.getSharedPreferences("quick_affordance_selections", 0, this.userTracker.getUserId());
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceSelectionManager
    public void setSelections(String str, List<String> list) {
        this.sharedPrefs.edit().putString("slot_" + str, CollectionsKt___CollectionsKt.joinToString$default(list, ",", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null)).apply();
    }
}