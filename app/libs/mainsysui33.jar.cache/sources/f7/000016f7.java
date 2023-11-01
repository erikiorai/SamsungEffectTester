package com.android.systemui.dreams.complication;

import androidx.lifecycle.LiveData;
import com.android.systemui.dreams.DreamOverlayStateController;
import java.util.Collection;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/ComplicationCollectionLiveData.class */
public class ComplicationCollectionLiveData extends LiveData<Collection<Complication>> {
    public final DreamOverlayStateController mDreamOverlayStateController;
    public final DreamOverlayStateController.Callback mStateControllerCallback = new DreamOverlayStateController.Callback() { // from class: com.android.systemui.dreams.complication.ComplicationCollectionLiveData.1
        @Override // com.android.systemui.dreams.DreamOverlayStateController.Callback
        public void onAvailableComplicationTypesChanged() {
            ComplicationCollectionLiveData complicationCollectionLiveData = ComplicationCollectionLiveData.this;
            complicationCollectionLiveData.setValue(complicationCollectionLiveData.mDreamOverlayStateController.getComplications());
        }

        @Override // com.android.systemui.dreams.DreamOverlayStateController.Callback
        public void onComplicationsChanged() {
            ComplicationCollectionLiveData complicationCollectionLiveData = ComplicationCollectionLiveData.this;
            complicationCollectionLiveData.setValue(complicationCollectionLiveData.mDreamOverlayStateController.getComplications());
        }
    };

    public ComplicationCollectionLiveData(DreamOverlayStateController dreamOverlayStateController) {
        this.mDreamOverlayStateController = dreamOverlayStateController;
    }

    @Override // androidx.lifecycle.LiveData
    public void onActive() {
        super.onActive();
        this.mDreamOverlayStateController.addCallback(this.mStateControllerCallback);
        setValue(this.mDreamOverlayStateController.getComplications());
    }

    @Override // androidx.lifecycle.LiveData
    public void onInactive() {
        this.mDreamOverlayStateController.removeCallback(this.mStateControllerCallback);
        super.onInactive();
    }
}