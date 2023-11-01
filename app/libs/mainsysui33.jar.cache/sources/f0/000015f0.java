package com.android.systemui.decor;

import android.content.res.Resources;
import com.android.systemui.R$bool;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;

/* loaded from: mainsysui33.jar:com/android/systemui/decor/PrivacyDotDecorProviderFactory.class */
public final class PrivacyDotDecorProviderFactory extends DecorProviderFactory {
    public final Resources res;

    public PrivacyDotDecorProviderFactory(Resources resources) {
        this.res = resources;
    }

    @Override // com.android.systemui.decor.DecorProviderFactory
    public boolean getHasProviders() {
        return isPrivacyDotEnabled();
    }

    @Override // com.android.systemui.decor.DecorProviderFactory
    public List<DecorProvider> getProviders() {
        return getHasProviders() ? CollectionsKt__CollectionsKt.listOf(new PrivacyDotCornerDecorProviderImpl[]{new PrivacyDotCornerDecorProviderImpl(R$id.privacy_dot_top_left_container, 1, 0, R$layout.privacy_dot_top_left), new PrivacyDotCornerDecorProviderImpl(R$id.privacy_dot_top_right_container, 1, 2, R$layout.privacy_dot_top_right), new PrivacyDotCornerDecorProviderImpl(R$id.privacy_dot_bottom_left_container, 3, 0, R$layout.privacy_dot_bottom_left), new PrivacyDotCornerDecorProviderImpl(R$id.privacy_dot_bottom_right_container, 3, 2, R$layout.privacy_dot_bottom_right)}) : CollectionsKt__CollectionsKt.emptyList();
    }

    public final boolean isPrivacyDotEnabled() {
        return this.res.getBoolean(R$bool.config_enablePrivacyDot);
    }
}