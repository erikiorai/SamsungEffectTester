package com.android.systemui.dreams.complication.dagger;

import android.view.LayoutInflater;
import androidx.constraintlayout.widget.ConstraintLayout;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/dagger/ComplicationHostViewModule_ProvidesComplicationHostViewFactory.class */
public final class ComplicationHostViewModule_ProvidesComplicationHostViewFactory implements Factory<ConstraintLayout> {
    public final Provider<LayoutInflater> layoutInflaterProvider;

    public ComplicationHostViewModule_ProvidesComplicationHostViewFactory(Provider<LayoutInflater> provider) {
        this.layoutInflaterProvider = provider;
    }

    public static ComplicationHostViewModule_ProvidesComplicationHostViewFactory create(Provider<LayoutInflater> provider) {
        return new ComplicationHostViewModule_ProvidesComplicationHostViewFactory(provider);
    }

    public static ConstraintLayout providesComplicationHostView(LayoutInflater layoutInflater) {
        return (ConstraintLayout) Preconditions.checkNotNullFromProvides(ComplicationHostViewModule.providesComplicationHostView(layoutInflater));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ConstraintLayout m2599get() {
        return providesComplicationHostView((LayoutInflater) this.layoutInflaterProvider.get());
    }
}