package com.android.systemui.hdmi;

import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/hdmi/HdmiCecSetMenuLanguageActivity_Factory.class */
public final class HdmiCecSetMenuLanguageActivity_Factory implements Factory<HdmiCecSetMenuLanguageActivity> {
    public final Provider<HdmiCecSetMenuLanguageHelper> hdmiCecSetMenuLanguageHelperProvider;

    public HdmiCecSetMenuLanguageActivity_Factory(Provider<HdmiCecSetMenuLanguageHelper> provider) {
        this.hdmiCecSetMenuLanguageHelperProvider = provider;
    }

    public static HdmiCecSetMenuLanguageActivity_Factory create(Provider<HdmiCecSetMenuLanguageHelper> provider) {
        return new HdmiCecSetMenuLanguageActivity_Factory(provider);
    }

    public static HdmiCecSetMenuLanguageActivity newInstance(HdmiCecSetMenuLanguageHelper hdmiCecSetMenuLanguageHelper) {
        return new HdmiCecSetMenuLanguageActivity(hdmiCecSetMenuLanguageHelper);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public HdmiCecSetMenuLanguageActivity m2781get() {
        return newInstance((HdmiCecSetMenuLanguageHelper) this.hdmiCecSetMenuLanguageHelperProvider.get());
    }
}