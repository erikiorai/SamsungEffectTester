package com.android.systemui.dreams.complication;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import java.util.Collection;
import java.util.stream.Collectors;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/ComplicationCollectionViewModel.class */
public class ComplicationCollectionViewModel extends ViewModel {
    public final LiveData<Collection<ComplicationViewModel>> mComplications;
    public final ComplicationViewModelTransformer mTransformer;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.complication.ComplicationCollectionViewModel$$ExternalSyntheticLambda0.apply(java.lang.Object):java.lang.Object] */
    public static /* synthetic */ Collection $r8$lambda$DnTmflUQ6_cJPQz5kChrTdlYFPA(ComplicationCollectionViewModel complicationCollectionViewModel, Collection collection) {
        return complicationCollectionViewModel.lambda$new$0(collection);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.complication.ComplicationCollectionViewModel$$ExternalSyntheticLambda1.apply(java.lang.Object):java.lang.Object] */
    public static /* synthetic */ ComplicationViewModel $r8$lambda$UD8c8ZpTb0c2jTZspIWwDQrQltM(ComplicationCollectionViewModel complicationCollectionViewModel, Complication complication) {
        return complicationCollectionViewModel.lambda$convert$1(complication);
    }

    public ComplicationCollectionViewModel(ComplicationCollectionLiveData complicationCollectionLiveData, ComplicationViewModelTransformer complicationViewModelTransformer) {
        this.mComplications = Transformations.map(complicationCollectionLiveData, new Function() { // from class: com.android.systemui.dreams.complication.ComplicationCollectionViewModel$$ExternalSyntheticLambda0
            @Override // androidx.arch.core.util.Function
            public final Object apply(Object obj) {
                return ComplicationCollectionViewModel.$r8$lambda$DnTmflUQ6_cJPQz5kChrTdlYFPA(ComplicationCollectionViewModel.this, (Collection) obj);
            }
        });
        this.mTransformer = complicationViewModelTransformer;
    }

    public /* synthetic */ ComplicationViewModel lambda$convert$1(Complication complication) {
        return this.mTransformer.getViewModel(complication);
    }

    /* renamed from: convert */
    public final Collection<ComplicationViewModel> lambda$new$0(Collection<Complication> collection) {
        return (Collection) collection.stream().map(new java.util.function.Function() { // from class: com.android.systemui.dreams.complication.ComplicationCollectionViewModel$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ComplicationCollectionViewModel.$r8$lambda$UD8c8ZpTb0c2jTZspIWwDQrQltM(ComplicationCollectionViewModel.this, (Complication) obj);
            }
        }).collect(Collectors.toSet());
    }

    public LiveData<Collection<ComplicationViewModel>> getComplications() {
        return this.mComplications;
    }
}