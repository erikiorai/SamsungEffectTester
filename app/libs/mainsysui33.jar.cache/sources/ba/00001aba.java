package com.android.systemui.keyguard.shared.model;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/shared/model/DozeTransitionModel.class */
public final class DozeTransitionModel {
    public final DozeStateModel from;
    public final DozeStateModel to;

    public DozeTransitionModel() {
        this(null, null, 3, null);
    }

    public DozeTransitionModel(DozeStateModel dozeStateModel, DozeStateModel dozeStateModel2) {
        this.from = dozeStateModel;
        this.to = dozeStateModel2;
    }

    public /* synthetic */ DozeTransitionModel(DozeStateModel dozeStateModel, DozeStateModel dozeStateModel2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? DozeStateModel.UNINITIALIZED : dozeStateModel, (i & 2) != 0 ? DozeStateModel.UNINITIALIZED : dozeStateModel2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof DozeTransitionModel) {
            DozeTransitionModel dozeTransitionModel = (DozeTransitionModel) obj;
            return this.from == dozeTransitionModel.from && this.to == dozeTransitionModel.to;
        }
        return false;
    }

    public final DozeStateModel getTo() {
        return this.to;
    }

    public int hashCode() {
        return (this.from.hashCode() * 31) + this.to.hashCode();
    }

    public String toString() {
        DozeStateModel dozeStateModel = this.from;
        DozeStateModel dozeStateModel2 = this.to;
        return "DozeTransitionModel(from=" + dozeStateModel + ", to=" + dozeStateModel2 + ")";
    }
}