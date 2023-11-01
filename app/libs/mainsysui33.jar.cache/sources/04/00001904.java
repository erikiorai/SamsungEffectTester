package com.android.systemui.keyguard.data;

import java.lang.ref.WeakReference;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/BouncerViewImpl.class */
public final class BouncerViewImpl implements BouncerView {
    public WeakReference<BouncerViewDelegate> _delegate = new WeakReference<>(null);

    @Override // com.android.systemui.keyguard.data.BouncerView
    public BouncerViewDelegate getDelegate() {
        return this._delegate.get();
    }

    @Override // com.android.systemui.keyguard.data.BouncerView
    public void setDelegate(BouncerViewDelegate bouncerViewDelegate) {
        this._delegate = new WeakReference<>(bouncerViewDelegate);
    }
}