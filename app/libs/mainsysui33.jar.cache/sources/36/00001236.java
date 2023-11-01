package com.android.systemui.biometrics;

import com.android.systemui.statusbar.commandline.CommandRegistry;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsShell_Factory.class */
public final class UdfpsShell_Factory implements Factory<UdfpsShell> {
    public final Provider<CommandRegistry> commandRegistryProvider;

    public UdfpsShell_Factory(Provider<CommandRegistry> provider) {
        this.commandRegistryProvider = provider;
    }

    public static UdfpsShell_Factory create(Provider<CommandRegistry> provider) {
        return new UdfpsShell_Factory(provider);
    }

    public static UdfpsShell newInstance(CommandRegistry commandRegistry) {
        return new UdfpsShell(commandRegistry);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public UdfpsShell m1609get() {
        return newInstance((CommandRegistry) this.commandRegistryProvider.get());
    }
}