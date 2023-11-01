package com.android.keyguard.dagger;

import android.view.ViewGroup;
import com.android.keyguard.KeyguardHostViewController;

/* loaded from: mainsysui33.jar:com/android/keyguard/dagger/KeyguardBouncerComponent.class */
public interface KeyguardBouncerComponent {

    /* loaded from: mainsysui33.jar:com/android/keyguard/dagger/KeyguardBouncerComponent$Factory.class */
    public interface Factory {
        KeyguardBouncerComponent create(ViewGroup viewGroup);
    }

    KeyguardHostViewController getKeyguardHostViewController();
}