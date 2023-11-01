package com.android.systemui.controls.controller;

import android.content.ComponentName;
import android.service.controls.Control;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/Favorites.class */
public final class Favorites {
    public static final Favorites INSTANCE = new Favorites();
    public static Map<ComponentName, ? extends List<StructureInfo>> favMap = MapsKt__MapsKt.emptyMap();

    public final boolean addFavorite(ComponentName componentName, CharSequence charSequence, ControlInfo controlInfo) {
        boolean z;
        StructureInfo structureInfo;
        Object obj;
        List<ControlInfo> controlsForComponent = getControlsForComponent(componentName);
        if (!(controlsForComponent instanceof Collection) || !controlsForComponent.isEmpty()) {
            for (ControlInfo controlInfo2 : controlsForComponent) {
                if (Intrinsics.areEqual(controlInfo2.getControlId(), controlInfo.getControlId())) {
                    z = true;
                    break;
                }
            }
        }
        z = false;
        if (z) {
            return false;
        }
        List<StructureInfo> list = favMap.get(componentName);
        if (list != null) {
            Iterator<T> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    obj = null;
                    break;
                }
                obj = it.next();
                if (Intrinsics.areEqual(((StructureInfo) obj).getStructure(), charSequence)) {
                    break;
                }
            }
            StructureInfo structureInfo2 = (StructureInfo) obj;
            if (structureInfo2 != null) {
                structureInfo = structureInfo2;
                replaceControls(StructureInfo.copy$default(structureInfo, null, null, CollectionsKt___CollectionsKt.plus(structureInfo.getControls(), controlInfo), 3, null));
                return true;
            }
        }
        structureInfo = new StructureInfo(componentName, charSequence, CollectionsKt__CollectionsKt.emptyList());
        replaceControls(StructureInfo.copy$default(structureInfo, null, null, CollectionsKt___CollectionsKt.plus(structureInfo.getControls(), controlInfo), 3, null));
        return true;
    }

    public final void clear() {
        favMap = MapsKt__MapsKt.emptyMap();
    }

    public final List<StructureInfo> getAllStructures() {
        Map<ComponentName, ? extends List<StructureInfo>> map = favMap;
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<ComponentName, ? extends List<StructureInfo>> entry : map.entrySet()) {
            CollectionsKt__MutableCollectionsKt.addAll(arrayList, entry.getValue());
        }
        return arrayList;
    }

    public final List<ControlInfo> getControlsForComponent(ComponentName componentName) {
        List<StructureInfo> structuresForComponent = getStructuresForComponent(componentName);
        ArrayList arrayList = new ArrayList();
        for (StructureInfo structureInfo : structuresForComponent) {
            CollectionsKt__MutableCollectionsKt.addAll(arrayList, structureInfo.getControls());
        }
        return arrayList;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0048, code lost:
        if (r4 != null) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final List<ControlInfo> getControlsForStructure(StructureInfo structureInfo) {
        Object obj;
        List<ControlInfo> emptyList;
        Iterator<T> it = getStructuresForComponent(structureInfo.getComponentName()).iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            Object next = it.next();
            if (Intrinsics.areEqual(((StructureInfo) next).getStructure(), structureInfo.getStructure())) {
                obj = next;
                break;
            }
        }
        StructureInfo structureInfo2 = (StructureInfo) obj;
        if (structureInfo2 != null) {
            emptyList = structureInfo2.getControls();
        }
        emptyList = CollectionsKt__CollectionsKt.emptyList();
        return emptyList;
    }

    public final List<StructureInfo> getStructuresForComponent(ComponentName componentName) {
        List<StructureInfo> list = favMap.get(componentName);
        List<StructureInfo> list2 = list;
        if (list == null) {
            list2 = CollectionsKt__CollectionsKt.emptyList();
        }
        return list2;
    }

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:13:0x0053 */
    public final void load(List<StructureInfo> list) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : list) {
            ComponentName componentName = ((StructureInfo) obj).getComponentName();
            Object obj2 = linkedHashMap.get(componentName);
            ArrayList arrayList = obj2;
            if (obj2 == null) {
                arrayList = new ArrayList();
                linkedHashMap.put(componentName, arrayList);
            }
            ((List) arrayList).add(obj);
        }
        favMap = linkedHashMap;
    }

    public final void removeStructures(ComponentName componentName) {
        Map<ComponentName, ? extends List<StructureInfo>> mutableMap = MapsKt__MapsKt.toMutableMap(favMap);
        mutableMap.remove(componentName);
        favMap = mutableMap;
    }

    public final void replaceControls(StructureInfo structureInfo) {
        Map<ComponentName, ? extends List<StructureInfo>> mutableMap = MapsKt__MapsKt.toMutableMap(favMap);
        ArrayList arrayList = new ArrayList();
        ComponentName componentName = structureInfo.getComponentName();
        boolean z = false;
        for (StructureInfo structureInfo2 : getStructuresForComponent(componentName)) {
            boolean z2 = z;
            StructureInfo structureInfo3 = structureInfo2;
            if (Intrinsics.areEqual(structureInfo2.getStructure(), structureInfo.getStructure())) {
                z2 = true;
                structureInfo3 = structureInfo;
            }
            z = z2;
            if (!structureInfo3.getControls().isEmpty()) {
                arrayList.add(structureInfo3);
                z = z2;
            }
        }
        if (!z && !structureInfo.getControls().isEmpty()) {
            arrayList.add(structureInfo);
        }
        mutableMap.put(componentName, arrayList);
        favMap = mutableMap;
    }

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:47:0x017e */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00e7, code lost:
        if (r0.getDeviceType() != r0.getDeviceType()) goto L33;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean updateControls(ComponentName componentName, List<Control> list) {
        Pair pair;
        ControlInfo copy$default;
        List<Control> list2 = list;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt___RangesKt.coerceAtLeast(MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10)), 16));
        for (Object obj : list2) {
            linkedHashMap.put(((Control) obj).getControlId(), obj);
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        boolean z = false;
        for (StructureInfo structureInfo : getStructuresForComponent(componentName)) {
            Iterator<T> it = structureInfo.getControls().iterator();
            boolean z2 = z;
            while (true) {
                z = z2;
                if (it.hasNext()) {
                    ControlInfo controlInfo = (ControlInfo) it.next();
                    Control control = (Control) linkedHashMap.get(controlInfo.getControlId());
                    if (control != null) {
                        if (Intrinsics.areEqual(control.getTitle(), controlInfo.getControlTitle()) && Intrinsics.areEqual(control.getSubtitle(), controlInfo.getControlSubtitle())) {
                            copy$default = controlInfo;
                        }
                        copy$default = ControlInfo.copy$default(controlInfo, null, control.getTitle(), control.getSubtitle(), control.getDeviceType(), 1, null);
                        z2 = true;
                        CharSequence structure = control.getStructure();
                        CharSequence charSequence = structure;
                        if (structure == null) {
                            charSequence = "";
                        }
                        if (!Intrinsics.areEqual(structureInfo.getStructure(), charSequence)) {
                            z2 = true;
                        }
                        pair = new Pair(charSequence, copy$default);
                    } else {
                        pair = new Pair(structureInfo.getStructure(), controlInfo);
                    }
                    CharSequence charSequence2 = (CharSequence) pair.component1();
                    ControlInfo controlInfo2 = (ControlInfo) pair.component2();
                    Object obj2 = linkedHashMap2.get(charSequence2);
                    ArrayList arrayList = obj2;
                    if (obj2 == null) {
                        arrayList = new ArrayList();
                        linkedHashMap2.put(charSequence2, arrayList);
                    }
                    ((List) arrayList).add(controlInfo2);
                }
            }
        }
        if (z) {
            ArrayList arrayList2 = new ArrayList(linkedHashMap2.size());
            for (Map.Entry entry : linkedHashMap2.entrySet()) {
                arrayList2.add(new StructureInfo(componentName, (CharSequence) entry.getKey(), (List) entry.getValue()));
            }
            Map<ComponentName, ? extends List<StructureInfo>> mutableMap = MapsKt__MapsKt.toMutableMap(favMap);
            mutableMap.put(componentName, arrayList2);
            favMap = mutableMap;
            return true;
        }
        return false;
    }
}