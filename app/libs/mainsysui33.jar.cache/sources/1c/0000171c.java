package com.android.systemui.dreams.complication;

import com.android.systemui.dreams.complication.ComplicationId;
import com.android.systemui.dreams.complication.dagger.ComplicationViewModelComponent;
import java.util.HashMap;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/ComplicationViewModelTransformer.class */
public class ComplicationViewModelTransformer {
    public final ComplicationId.Factory mComplicationIdFactory = new ComplicationId.Factory();
    public final HashMap<Complication, ComplicationId> mComplicationIdMapping = new HashMap<>();
    public final ComplicationViewModelComponent.Factory mViewModelComponentFactory;

    public ComplicationViewModelTransformer(ComplicationViewModelComponent.Factory factory) {
        this.mViewModelComponentFactory = factory;
    }

    public final ComplicationId getComplicationId(Complication complication) {
        if (!this.mComplicationIdMapping.containsKey(complication)) {
            this.mComplicationIdMapping.put(complication, this.mComplicationIdFactory.getNextId());
        }
        return this.mComplicationIdMapping.get(complication);
    }

    public ComplicationViewModel getViewModel(Complication complication) {
        ComplicationId complicationId = getComplicationId(complication);
        return (ComplicationViewModel) this.mViewModelComponentFactory.create(complication, complicationId).getViewModelProvider().get(complicationId.toString(), ComplicationViewModel.class);
    }
}