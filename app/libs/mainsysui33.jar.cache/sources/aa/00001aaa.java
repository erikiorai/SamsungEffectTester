package com.android.systemui.keyguard.domain.interactor;

import kotlin.Pair;
import kotlin.Triple;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/TransitionInteractor.class */
public abstract class TransitionInteractor {
    public final String name;

    public TransitionInteractor(String str) {
        this.name = str;
    }

    public /* synthetic */ TransitionInteractor(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    public final String getName() {
        return this.name;
    }

    public abstract void start();

    public final <A, B, C> Triple<A, B, C> toTriple(A a, Pair<? extends B, ? extends C> pair) {
        return new Triple<>(a, pair.getFirst(), pair.getSecond());
    }
}