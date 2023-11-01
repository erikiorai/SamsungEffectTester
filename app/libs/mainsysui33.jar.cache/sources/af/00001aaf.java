package com.android.systemui.keyguard.domain.quickaffordance;

import com.android.systemui.keyguard.data.quickaffordance.HomeControlsKeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.data.quickaffordance.QrCodeScannerKeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/quickaffordance/KeyguardQuickAffordanceRegistryImpl.class */
public final class KeyguardQuickAffordanceRegistryImpl implements KeyguardQuickAffordanceRegistry<KeyguardQuickAffordanceConfig> {
    public final Map<String, KeyguardQuickAffordanceConfig> configByKey;
    public final Map<KeyguardQuickAffordancePosition, List<KeyguardQuickAffordanceConfig>> configsByPosition;

    public KeyguardQuickAffordanceRegistryImpl(HomeControlsKeyguardQuickAffordanceConfig homeControlsKeyguardQuickAffordanceConfig, QuickAccessWalletKeyguardQuickAffordanceConfig quickAccessWalletKeyguardQuickAffordanceConfig, QrCodeScannerKeyguardQuickAffordanceConfig qrCodeScannerKeyguardQuickAffordanceConfig) {
        Map<KeyguardQuickAffordancePosition, List<KeyguardQuickAffordanceConfig>> mapOf = MapsKt__MapsKt.mapOf(new Pair[]{TuplesKt.to(KeyguardQuickAffordancePosition.BOTTOM_START, CollectionsKt__CollectionsJVMKt.listOf(homeControlsKeyguardQuickAffordanceConfig)), TuplesKt.to(KeyguardQuickAffordancePosition.BOTTOM_END, CollectionsKt__CollectionsKt.listOf(new KeyguardQuickAffordanceConfig[]{quickAccessWalletKeyguardQuickAffordanceConfig, qrCodeScannerKeyguardQuickAffordanceConfig}))});
        this.configsByPosition = mapOf;
        List flatten = CollectionsKt__IterablesKt.flatten(mapOf.values());
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt___RangesKt.coerceAtLeast(MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(flatten, 10)), 16));
        for (Object obj : flatten) {
            linkedHashMap.put(((KeyguardQuickAffordanceConfig) obj).getKey(), obj);
        }
        this.configByKey = linkedHashMap;
    }

    @Override // com.android.systemui.keyguard.domain.quickaffordance.KeyguardQuickAffordanceRegistry
    public KeyguardQuickAffordanceConfig get(String str) {
        return (KeyguardQuickAffordanceConfig) MapsKt__MapsKt.getValue(this.configByKey, str);
    }

    @Override // com.android.systemui.keyguard.domain.quickaffordance.KeyguardQuickAffordanceRegistry
    public List<KeyguardQuickAffordanceConfig> getAll(KeyguardQuickAffordancePosition keyguardQuickAffordancePosition) {
        return (List) MapsKt__MapsKt.getValue(this.configsByPosition, keyguardQuickAffordancePosition);
    }
}