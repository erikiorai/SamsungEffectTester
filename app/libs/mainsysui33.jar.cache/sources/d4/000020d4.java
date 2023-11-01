package com.android.systemui.qs;

import dagger.internal.Factory;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/QSExpansionPathInterpolator_Factory.class */
public final class QSExpansionPathInterpolator_Factory implements Factory<QSExpansionPathInterpolator> {

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/QSExpansionPathInterpolator_Factory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final QSExpansionPathInterpolator_Factory INSTANCE = new QSExpansionPathInterpolator_Factory();
    }

    public static QSExpansionPathInterpolator_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static QSExpansionPathInterpolator newInstance() {
        return new QSExpansionPathInterpolator();
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public QSExpansionPathInterpolator m3753get() {
        return newInstance();
    }
}