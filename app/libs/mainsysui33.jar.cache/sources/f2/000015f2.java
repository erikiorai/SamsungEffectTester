package com.android.systemui.decor;

import com.android.systemui.R$id;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;

/* loaded from: mainsysui33.jar:com/android/systemui/decor/RoundedCornerDecorProviderFactory.class */
public final class RoundedCornerDecorProviderFactory extends DecorProviderFactory {
    public final RoundedCornerResDelegate roundedCornerResDelegate;

    public RoundedCornerDecorProviderFactory(RoundedCornerResDelegate roundedCornerResDelegate) {
        this.roundedCornerResDelegate = roundedCornerResDelegate;
    }

    @Override // com.android.systemui.decor.DecorProviderFactory
    public boolean getHasProviders() {
        RoundedCornerResDelegate roundedCornerResDelegate = this.roundedCornerResDelegate;
        return roundedCornerResDelegate.getHasTop() || roundedCornerResDelegate.getHasBottom();
    }

    @Override // com.android.systemui.decor.DecorProviderFactory
    public List<DecorProvider> getProviders() {
        boolean hasTop = this.roundedCornerResDelegate.getHasTop();
        boolean hasBottom = this.roundedCornerResDelegate.getHasBottom();
        return (hasTop && hasBottom) ? CollectionsKt__CollectionsKt.listOf(new RoundedCornerDecorProviderImpl[]{new RoundedCornerDecorProviderImpl(R$id.rounded_corner_top_left, 1, 0, this.roundedCornerResDelegate), new RoundedCornerDecorProviderImpl(R$id.rounded_corner_top_right, 1, 2, this.roundedCornerResDelegate), new RoundedCornerDecorProviderImpl(R$id.rounded_corner_bottom_left, 3, 0, this.roundedCornerResDelegate), new RoundedCornerDecorProviderImpl(R$id.rounded_corner_bottom_right, 3, 2, this.roundedCornerResDelegate)}) : hasTop ? CollectionsKt__CollectionsKt.listOf(new RoundedCornerDecorProviderImpl[]{new RoundedCornerDecorProviderImpl(R$id.rounded_corner_top_left, 1, 0, this.roundedCornerResDelegate), new RoundedCornerDecorProviderImpl(R$id.rounded_corner_top_right, 1, 2, this.roundedCornerResDelegate)}) : hasBottom ? CollectionsKt__CollectionsKt.listOf(new RoundedCornerDecorProviderImpl[]{new RoundedCornerDecorProviderImpl(R$id.rounded_corner_bottom_left, 3, 0, this.roundedCornerResDelegate), new RoundedCornerDecorProviderImpl(R$id.rounded_corner_bottom_right, 3, 2, this.roundedCornerResDelegate)}) : CollectionsKt__CollectionsKt.emptyList();
    }
}