package com.android.systemui.dreams.complication.dagger;

import com.android.systemui.dreams.complication.Complication;
import com.android.systemui.dreams.complication.ComplicationId;
import com.android.systemui.dreams.complication.ComplicationViewModelProvider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/dagger/ComplicationViewModelComponent.class */
public interface ComplicationViewModelComponent {

    /* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/dagger/ComplicationViewModelComponent$Factory.class */
    public interface Factory {
        ComplicationViewModelComponent create(Complication complication, ComplicationId complicationId);
    }

    ComplicationViewModelProvider getViewModelProvider();
}