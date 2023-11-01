package com.android.systemui.dreams.complication;

import androidx.lifecycle.ViewModel;
import com.android.systemui.dreams.complication.Complication;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/ComplicationViewModel.class */
public class ComplicationViewModel extends ViewModel {
    public final Complication mComplication;
    public final Complication.Host mHost;
    public final ComplicationId mId;

    public ComplicationViewModel(Complication complication, ComplicationId complicationId, Complication.Host host) {
        this.mComplication = complication;
        this.mId = complicationId;
        this.mHost = host;
    }

    public Complication getComplication() {
        return this.mComplication;
    }

    public ComplicationId getId() {
        return this.mId;
    }

    public String toString() {
        return this.mId + "=" + this.mComplication.toString();
    }
}