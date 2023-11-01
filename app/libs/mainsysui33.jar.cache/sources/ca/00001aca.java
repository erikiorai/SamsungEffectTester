package com.android.systemui.keyguard.shared.quickaffordance;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/shared/quickaffordance/ActivationState.class */
public abstract class ActivationState {

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/shared/quickaffordance/ActivationState$Active.class */
    public static final class Active extends ActivationState {
        public static final Active INSTANCE = new Active();

        public Active() {
            super(null);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/shared/quickaffordance/ActivationState$Inactive.class */
    public static final class Inactive extends ActivationState {
        public static final Inactive INSTANCE = new Inactive();

        public Inactive() {
            super(null);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/shared/quickaffordance/ActivationState$NotSupported.class */
    public static final class NotSupported extends ActivationState {
        public static final NotSupported INSTANCE = new NotSupported();

        public NotSupported() {
            super(null);
        }
    }

    public ActivationState() {
    }

    public /* synthetic */ ActivationState(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}