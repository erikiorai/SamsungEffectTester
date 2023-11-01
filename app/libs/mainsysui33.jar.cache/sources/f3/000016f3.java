package com.android.systemui.dreams.complication;

import android.view.View;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/Complication.class */
public interface Complication {

    /* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/Complication$Host.class */
    public interface Host {
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/Complication$ViewHolder.class */
    public interface ViewHolder {
        default int getCategory() {
            return 1;
        }

        ComplicationLayoutParams getLayoutParams();

        View getView();
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/Complication$VisibilityController.class */
    public interface VisibilityController {
        void setVisibility(int i);
    }

    ViewHolder createView(ComplicationViewModel complicationViewModel);

    default int getRequiredTypeAvailability() {
        return 0;
    }
}