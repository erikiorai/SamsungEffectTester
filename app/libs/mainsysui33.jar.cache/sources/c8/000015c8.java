package com.android.systemui.dagger;

import dagger.internal.Factory;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/ReferenceSystemUIModule_ProvideAllowNotificationLongPressFactory.class */
public final class ReferenceSystemUIModule_ProvideAllowNotificationLongPressFactory implements Factory<Boolean> {

    /* loaded from: mainsysui33.jar:com/android/systemui/dagger/ReferenceSystemUIModule_ProvideAllowNotificationLongPressFactory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final ReferenceSystemUIModule_ProvideAllowNotificationLongPressFactory INSTANCE = new ReferenceSystemUIModule_ProvideAllowNotificationLongPressFactory();
    }

    public static ReferenceSystemUIModule_ProvideAllowNotificationLongPressFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static boolean provideAllowNotificationLongPress() {
        return ReferenceSystemUIModule.provideAllowNotificationLongPress();
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Boolean m2375get() {
        return Boolean.valueOf(provideAllowNotificationLongPress());
    }
}