package com.android.systemui.decor;

import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/decor/DecorProviderFactory.class */
public abstract class DecorProviderFactory {
    public abstract boolean getHasProviders();

    public abstract List<DecorProvider> getProviders();
}