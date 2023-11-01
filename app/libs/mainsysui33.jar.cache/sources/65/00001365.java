package com.android.systemui.common.ui.binder;

import android.view.View;
import com.android.systemui.common.shared.model.ContentDescription;
import kotlin.NoWhenBranchMatchedException;

/* loaded from: mainsysui33.jar:com/android/systemui/common/ui/binder/ContentDescriptionViewBinder.class */
public final class ContentDescriptionViewBinder {
    public static final ContentDescriptionViewBinder INSTANCE = new ContentDescriptionViewBinder();

    public final void bind(ContentDescription contentDescription, View view) {
        String string;
        if (contentDescription == null) {
            string = null;
        } else if (contentDescription instanceof ContentDescription.Loaded) {
            string = ((ContentDescription.Loaded) contentDescription).getDescription();
        } else if (!(contentDescription instanceof ContentDescription.Resource)) {
            throw new NoWhenBranchMatchedException();
        } else {
            string = view.getContext().getResources().getString(((ContentDescription.Resource) contentDescription).getRes());
        }
        view.setContentDescription(string);
    }
}