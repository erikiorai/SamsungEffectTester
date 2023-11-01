package com.android.systemui.controls.controller;

import android.service.controls.IControlsActionCallback;
import android.service.controls.IControlsProvider;
import android.service.controls.IControlsSubscriber;
import android.service.controls.IControlsSubscription;
import android.service.controls.actions.ControlAction;
import android.service.controls.actions.ControlActionWrapper;
import android.util.Log;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/ServiceWrapper.class */
public final class ServiceWrapper {
    public static final Companion Companion = new Companion(null);
    public final IControlsProvider service;

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/ServiceWrapper$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ServiceWrapper(IControlsProvider iControlsProvider) {
        this.service = iControlsProvider;
    }

    public final boolean action(String str, ControlAction controlAction, IControlsActionCallback iControlsActionCallback) {
        boolean z;
        try {
            this.service.action(str, new ControlActionWrapper(controlAction), iControlsActionCallback);
            z = true;
        } catch (Exception e) {
            Log.e("ServiceWrapper", "Caught exception from ControlsProviderService", e);
            z = false;
        }
        return z;
    }

    public final boolean cancel(IControlsSubscription iControlsSubscription) {
        boolean z;
        try {
            iControlsSubscription.cancel();
            z = true;
        } catch (Exception e) {
            Log.e("ServiceWrapper", "Caught exception from ControlsProviderService", e);
            z = false;
        }
        return z;
    }

    public final boolean load(IControlsSubscriber iControlsSubscriber) {
        boolean z;
        try {
            this.service.load(iControlsSubscriber);
            z = true;
        } catch (Exception e) {
            Log.e("ServiceWrapper", "Caught exception from ControlsProviderService", e);
            z = false;
        }
        return z;
    }

    public final boolean loadSuggested(IControlsSubscriber iControlsSubscriber) {
        boolean z;
        try {
            this.service.loadSuggested(iControlsSubscriber);
            z = true;
        } catch (Exception e) {
            Log.e("ServiceWrapper", "Caught exception from ControlsProviderService", e);
            z = false;
        }
        return z;
    }

    public final boolean request(IControlsSubscription iControlsSubscription, long j) {
        boolean z;
        try {
            iControlsSubscription.request(j);
            z = true;
        } catch (Exception e) {
            Log.e("ServiceWrapper", "Caught exception from ControlsProviderService", e);
            z = false;
        }
        return z;
    }

    public final boolean subscribe(List<String> list, IControlsSubscriber iControlsSubscriber) {
        boolean z;
        try {
            this.service.subscribe(list, iControlsSubscriber);
            z = true;
        } catch (Exception e) {
            Log.e("ServiceWrapper", "Caught exception from ControlsProviderService", e);
            z = false;
        }
        return z;
    }
}