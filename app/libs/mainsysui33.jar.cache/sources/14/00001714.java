package com.android.systemui.dreams.complication;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.touch.TouchInsetManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/ComplicationLayoutEngine_Factory.class */
public final class ComplicationLayoutEngine_Factory implements Factory<ComplicationLayoutEngine> {
    public final Provider<Integer> defaultMarginProvider;
    public final Provider<Integer> fadeInDurationProvider;
    public final Provider<Integer> fadeOutDurationProvider;
    public final Provider<ConstraintLayout> layoutProvider;
    public final Provider<TouchInsetManager.TouchInsetSession> sessionProvider;

    public ComplicationLayoutEngine_Factory(Provider<ConstraintLayout> provider, Provider<Integer> provider2, Provider<TouchInsetManager.TouchInsetSession> provider3, Provider<Integer> provider4, Provider<Integer> provider5) {
        this.layoutProvider = provider;
        this.defaultMarginProvider = provider2;
        this.sessionProvider = provider3;
        this.fadeInDurationProvider = provider4;
        this.fadeOutDurationProvider = provider5;
    }

    public static ComplicationLayoutEngine_Factory create(Provider<ConstraintLayout> provider, Provider<Integer> provider2, Provider<TouchInsetManager.TouchInsetSession> provider3, Provider<Integer> provider4, Provider<Integer> provider5) {
        return new ComplicationLayoutEngine_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static ComplicationLayoutEngine newInstance(ConstraintLayout constraintLayout, int i, TouchInsetManager.TouchInsetSession touchInsetSession, int i2, int i3) {
        return new ComplicationLayoutEngine(constraintLayout, i, touchInsetSession, i2, i3);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ComplicationLayoutEngine m2587get() {
        return newInstance((ConstraintLayout) this.layoutProvider.get(), ((Integer) this.defaultMarginProvider.get()).intValue(), (TouchInsetManager.TouchInsetSession) this.sessionProvider.get(), ((Integer) this.fadeInDurationProvider.get()).intValue(), ((Integer) this.fadeOutDurationProvider.get()).intValue());
    }
}