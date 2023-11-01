package com.android.systemui.dreams;

import dagger.internal.Factory;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayCallbackController_Factory.class */
public final class DreamOverlayCallbackController_Factory implements Factory<DreamOverlayCallbackController> {

    /* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayCallbackController_Factory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final DreamOverlayCallbackController_Factory INSTANCE = new DreamOverlayCallbackController_Factory();
    }

    public static DreamOverlayCallbackController_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static DreamOverlayCallbackController newInstance() {
        return new DreamOverlayCallbackController();
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DreamOverlayCallbackController m2543get() {
        return newInstance();
    }
}