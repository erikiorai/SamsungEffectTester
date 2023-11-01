package com.android.systemui.doze.dagger;

import com.android.systemui.doze.DozeAuthRemover;
import com.android.systemui.doze.DozeDockHandler;
import com.android.systemui.doze.DozeFalsingManagerAdapter;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.doze.DozePauser;
import com.android.systemui.doze.DozeScreenBrightness;
import com.android.systemui.doze.DozeScreenState;
import com.android.systemui.doze.DozeSuppressor;
import com.android.systemui.doze.DozeTransitionListener;
import com.android.systemui.doze.DozeTriggers;
import com.android.systemui.doze.DozeUi;
import com.android.systemui.doze.DozeWallpaperState;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/dagger/DozeModule_ProvidesDozeMachinePartsFactory.class */
public final class DozeModule_ProvidesDozeMachinePartsFactory implements Factory<DozeMachine.Part[]> {
    public final Provider<DozeAuthRemover> dozeAuthRemoverProvider;
    public final Provider<DozeDockHandler> dozeDockHandlerProvider;
    public final Provider<DozeFalsingManagerAdapter> dozeFalsingManagerAdapterProvider;
    public final Provider<DozePauser> dozePauserProvider;
    public final Provider<DozeScreenBrightness> dozeScreenBrightnessProvider;
    public final Provider<DozeScreenState> dozeScreenStateProvider;
    public final Provider<DozeSuppressor> dozeSuppressorProvider;
    public final Provider<DozeTransitionListener> dozeTransitionListenerProvider;
    public final Provider<DozeTriggers> dozeTriggersProvider;
    public final Provider<DozeUi> dozeUiProvider;
    public final Provider<DozeWallpaperState> dozeWallpaperStateProvider;

    public DozeModule_ProvidesDozeMachinePartsFactory(Provider<DozePauser> provider, Provider<DozeFalsingManagerAdapter> provider2, Provider<DozeTriggers> provider3, Provider<DozeUi> provider4, Provider<DozeScreenState> provider5, Provider<DozeScreenBrightness> provider6, Provider<DozeWallpaperState> provider7, Provider<DozeDockHandler> provider8, Provider<DozeAuthRemover> provider9, Provider<DozeSuppressor> provider10, Provider<DozeTransitionListener> provider11) {
        this.dozePauserProvider = provider;
        this.dozeFalsingManagerAdapterProvider = provider2;
        this.dozeTriggersProvider = provider3;
        this.dozeUiProvider = provider4;
        this.dozeScreenStateProvider = provider5;
        this.dozeScreenBrightnessProvider = provider6;
        this.dozeWallpaperStateProvider = provider7;
        this.dozeDockHandlerProvider = provider8;
        this.dozeAuthRemoverProvider = provider9;
        this.dozeSuppressorProvider = provider10;
        this.dozeTransitionListenerProvider = provider11;
    }

    public static DozeModule_ProvidesDozeMachinePartsFactory create(Provider<DozePauser> provider, Provider<DozeFalsingManagerAdapter> provider2, Provider<DozeTriggers> provider3, Provider<DozeUi> provider4, Provider<DozeScreenState> provider5, Provider<DozeScreenBrightness> provider6, Provider<DozeWallpaperState> provider7, Provider<DozeDockHandler> provider8, Provider<DozeAuthRemover> provider9, Provider<DozeSuppressor> provider10, Provider<DozeTransitionListener> provider11) {
        return new DozeModule_ProvidesDozeMachinePartsFactory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11);
    }

    public static DozeMachine.Part[] providesDozeMachineParts(DozePauser dozePauser, DozeFalsingManagerAdapter dozeFalsingManagerAdapter, DozeTriggers dozeTriggers, DozeUi dozeUi, DozeScreenState dozeScreenState, DozeScreenBrightness dozeScreenBrightness, DozeWallpaperState dozeWallpaperState, DozeDockHandler dozeDockHandler, DozeAuthRemover dozeAuthRemover, DozeSuppressor dozeSuppressor, DozeTransitionListener dozeTransitionListener) {
        return (DozeMachine.Part[]) Preconditions.checkNotNullFromProvides(DozeModule.providesDozeMachineParts(dozePauser, dozeFalsingManagerAdapter, dozeTriggers, dozeUi, dozeScreenState, dozeScreenBrightness, dozeWallpaperState, dozeDockHandler, dozeAuthRemover, dozeSuppressor, dozeTransitionListener));
    }

    /* JADX DEBUG: Method merged with bridge method */
    public DozeMachine.Part[] get() {
        return providesDozeMachineParts((DozePauser) this.dozePauserProvider.get(), (DozeFalsingManagerAdapter) this.dozeFalsingManagerAdapterProvider.get(), (DozeTriggers) this.dozeTriggersProvider.get(), (DozeUi) this.dozeUiProvider.get(), (DozeScreenState) this.dozeScreenStateProvider.get(), (DozeScreenBrightness) this.dozeScreenBrightnessProvider.get(), (DozeWallpaperState) this.dozeWallpaperStateProvider.get(), (DozeDockHandler) this.dozeDockHandlerProvider.get(), (DozeAuthRemover) this.dozeAuthRemoverProvider.get(), (DozeSuppressor) this.dozeSuppressorProvider.get(), (DozeTransitionListener) this.dozeTransitionListenerProvider.get());
    }
}