package com.android.systemui.decor;

import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;

/* loaded from: mainsysui33.jar:com/android/systemui/decor/CornerDecorProvider.class */
public abstract class CornerDecorProvider extends DecorProvider {
    public final Lazy alignedBounds$delegate = LazyKt__LazyJVMKt.lazy(new Function0<List<? extends Integer>>() { // from class: com.android.systemui.decor.CornerDecorProvider$alignedBounds$2
        {
            super(0);
        }

        /* JADX DEBUG: Method merged with bridge method */
        public final List<Integer> invoke() {
            return CollectionsKt__CollectionsKt.listOf(new Integer[]{Integer.valueOf(CornerDecorProvider.this.getAlignedBound1()), Integer.valueOf(CornerDecorProvider.this.getAlignedBound2())});
        }
    });

    public abstract int getAlignedBound1();

    public abstract int getAlignedBound2();

    @Override // com.android.systemui.decor.DecorProvider
    public List<Integer> getAlignedBounds() {
        return (List) this.alignedBounds$delegate.getValue();
    }
}