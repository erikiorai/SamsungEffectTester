package com.android.systemui.decor;

import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.jvm.functions.Function0;

/* loaded from: mainsysui33.jar:com/android/systemui/decor/BoundDecorProvider.class */
public abstract class BoundDecorProvider extends DecorProvider {
    public final Lazy alignedBounds$delegate = LazyKt__LazyJVMKt.lazy(new Function0<List<? extends Integer>>() { // from class: com.android.systemui.decor.BoundDecorProvider$alignedBounds$2
        {
            super(0);
        }

        /* JADX DEBUG: Method merged with bridge method */
        public final List<Integer> invoke() {
            return CollectionsKt__CollectionsJVMKt.listOf(Integer.valueOf(BoundDecorProvider.this.getAlignedBound()));
        }
    });

    public abstract int getAlignedBound();

    @Override // com.android.systemui.decor.DecorProvider
    public List<Integer> getAlignedBounds() {
        return (List) this.alignedBounds$delegate.getValue();
    }
}