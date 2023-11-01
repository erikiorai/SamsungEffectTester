package com.android.systemui.controls.management;

import android.service.controls.Control;
import android.text.TextUtils;
import android.util.ArrayMap;
import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.management.ControlsModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableMap;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt___SequencesKt;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/AllModel.class */
public final class AllModel implements ControlsModel {
    public final List<ControlStatus> controls;
    public final ControlsModel.ControlsModelCallback controlsModelCallback;
    public final List<ElementWrapper> elements;
    public final CharSequence emptyZoneString;
    public final List<String> favoriteIds;
    public boolean modified;
    public final Void moveHelper;

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/management/AllModel$OrderedMap.class */
    public static final class OrderedMap<K, V> implements Map<K, V>, KMutableMap {
        public final Map<K, V> map;
        public final List<K> orderedKeys = new ArrayList();

        public OrderedMap(Map<K, V> map) {
            this.map = map;
        }

        @Override // java.util.Map
        public void clear() {
            this.orderedKeys.clear();
            this.map.clear();
        }

        @Override // java.util.Map
        public boolean containsKey(Object obj) {
            return this.map.containsKey(obj);
        }

        @Override // java.util.Map
        public boolean containsValue(Object obj) {
            return this.map.containsValue(obj);
        }

        @Override // java.util.Map
        public final /* bridge */ Set<Map.Entry<K, V>> entrySet() {
            return getEntries();
        }

        @Override // java.util.Map
        public V get(Object obj) {
            return this.map.get(obj);
        }

        public Set<Map.Entry<K, V>> getEntries() {
            return this.map.entrySet();
        }

        public Set<K> getKeys() {
            return this.map.keySet();
        }

        public final List<K> getOrderedKeys() {
            return this.orderedKeys;
        }

        public int getSize() {
            return this.map.size();
        }

        public Collection<V> getValues() {
            return this.map.values();
        }

        @Override // java.util.Map
        public boolean isEmpty() {
            return this.map.isEmpty();
        }

        @Override // java.util.Map
        public final /* bridge */ Set<K> keySet() {
            return getKeys();
        }

        @Override // java.util.Map
        public V put(K k, V v) {
            if (!this.map.containsKey(k)) {
                this.orderedKeys.add(k);
            }
            return this.map.put(k, v);
        }

        @Override // java.util.Map
        public void putAll(Map<? extends K, ? extends V> map) {
            this.map.putAll(map);
        }

        @Override // java.util.Map
        public V remove(Object obj) {
            V remove = this.map.remove(obj);
            if (remove != null) {
                this.orderedKeys.remove(obj);
            }
            return remove;
        }

        @Override // java.util.Map
        public final /* bridge */ int size() {
            return getSize();
        }

        @Override // java.util.Map
        public final /* bridge */ Collection<V> values() {
            return getValues();
        }
    }

    public AllModel(List<ControlStatus> list, List<String> list2, CharSequence charSequence, ControlsModel.ControlsModelCallback controlsModelCallback) {
        this.controls = list;
        this.emptyZoneString = charSequence;
        this.controlsModelCallback = controlsModelCallback;
        HashSet hashSet = new HashSet();
        for (ControlStatus controlStatus : list) {
            hashSet.add(controlStatus.getControl().getControlId());
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : list2) {
            if (hashSet.contains((String) obj)) {
                arrayList.add(obj);
            }
        }
        this.favoriteIds = CollectionsKt___CollectionsKt.toMutableList(arrayList);
        this.elements = createWrappers(this.controls);
    }

    @Override // com.android.systemui.controls.management.ControlsModel
    public void changeFavoriteStatus(String str, boolean z) {
        Object obj;
        Iterator<T> it = getElements().iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            ControlStatusWrapper controlStatusWrapper = (ElementWrapper) obj;
            if ((controlStatusWrapper instanceof ControlStatusWrapper) && Intrinsics.areEqual(controlStatusWrapper.getControlStatus().getControl().getControlId(), str)) {
                break;
            }
        }
        ControlStatusWrapper controlStatusWrapper2 = (ControlStatusWrapper) obj;
        boolean z2 = false;
        if (controlStatusWrapper2 != null) {
            ControlStatus controlStatus = controlStatusWrapper2.getControlStatus();
            z2 = false;
            if (controlStatus != null) {
                z2 = false;
                if (z == controlStatus.getFavorite()) {
                    z2 = true;
                }
            }
        }
        if (z2) {
            return;
        }
        if ((z ? this.favoriteIds.add(str) : this.favoriteIds.remove(str)) && !this.modified) {
            this.modified = true;
            this.controlsModelCallback.onFirstChange();
        }
        if (controlStatusWrapper2 != null) {
            controlStatusWrapper2.getControlStatus().setFavorite(z);
        }
    }

    /* JADX DEBUG: Type inference failed for r0v4. Raw type applied. Possible types: java.util.Iterator<T>, java.util.Iterator */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v46, types: [java.lang.Object] */
    public final List<ElementWrapper> createWrappers(List<ControlStatus> list) {
        OrderedMap orderedMap = new OrderedMap(new ArrayMap());
        for (Object obj : list) {
            CharSequence zone = ((ControlStatus) obj).getControl().getZone();
            CharSequence charSequence = zone;
            if (zone == null) {
                charSequence = "";
            }
            ?? r0 = orderedMap.get(charSequence);
            ArrayList arrayList = r0;
            if (r0 == 0) {
                arrayList = new ArrayList();
                orderedMap.put(charSequence, arrayList);
            }
            arrayList.add(obj);
        }
        ArrayList arrayList2 = new ArrayList();
        Sequence sequence = null;
        for (CharSequence charSequence2 : orderedMap.getOrderedKeys()) {
            Sequence map = SequencesKt___SequencesKt.map(CollectionsKt___CollectionsKt.asSequence((Iterable) MapsKt__MapsKt.getValue(orderedMap, charSequence2)), new Function1<ControlStatus, ControlStatusWrapper>() { // from class: com.android.systemui.controls.management.AllModel$createWrappers$values$1
                /* JADX DEBUG: Method merged with bridge method */
                public final ControlStatusWrapper invoke(ControlStatus controlStatus) {
                    return new ControlStatusWrapper(controlStatus);
                }
            });
            if (TextUtils.isEmpty(charSequence2)) {
                sequence = map;
            } else {
                arrayList2.add(new ZoneNameWrapper(charSequence2));
                CollectionsKt__MutableCollectionsKt.addAll(arrayList2, map);
            }
        }
        if (sequence != null) {
            if (orderedMap.size() != 1) {
                arrayList2.add(new ZoneNameWrapper(this.emptyZoneString));
            }
            CollectionsKt__MutableCollectionsKt.addAll(arrayList2, sequence);
        }
        return arrayList2;
    }

    @Override // com.android.systemui.controls.management.ControlsModel
    public List<ElementWrapper> getElements() {
        return this.elements;
    }

    @Override // com.android.systemui.controls.management.ControlsModel
    public List<ControlInfo> getFavorites() {
        Object obj;
        List<String> list = this.favoriteIds;
        ArrayList arrayList = new ArrayList();
        for (String str : list) {
            Iterator<T> it = this.controls.iterator();
            while (true) {
                if (!it.hasNext()) {
                    obj = null;
                    break;
                }
                obj = it.next();
                if (Intrinsics.areEqual(((ControlStatus) obj).getControl().getControlId(), str)) {
                    break;
                }
            }
            ControlStatus controlStatus = (ControlStatus) obj;
            Control control = controlStatus != null ? controlStatus.getControl() : null;
            ControlInfo fromControl = control != null ? ControlInfo.Companion.fromControl(control) : null;
            if (fromControl != null) {
                arrayList.add(fromControl);
            }
        }
        return arrayList;
    }

    @Override // com.android.systemui.controls.management.ControlsModel
    public /* bridge */ /* synthetic */ ControlsModel.MoveHelper getMoveHelper() {
        return (ControlsModel.MoveHelper) m1820getMoveHelper();
    }

    /* JADX DEBUG: Possible override for method com.android.systemui.controls.management.ControlsModel.getMoveHelper()Lcom/android/systemui/controls/management/ControlsModel$MoveHelper; */
    /* renamed from: getMoveHelper  reason: collision with other method in class */
    public Void m1820getMoveHelper() {
        return this.moveHelper;
    }
}