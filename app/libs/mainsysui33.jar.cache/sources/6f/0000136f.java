package com.android.systemui.compose;

import androidx.activity.ComponentActivity;
import com.android.systemui.people.ui.viewmodel.PeopleViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: mainsysui33.jar:com/android/systemui/compose/ComposeFacade.class */
public final class ComposeFacade {
    public static final ComposeFacade INSTANCE = new ComposeFacade();

    public boolean isComposeAvailable() {
        return false;
    }

    public void setPeopleSpaceActivityContent(ComponentActivity componentActivity, PeopleViewModel peopleViewModel, Function1<? super PeopleViewModel.Result, Unit> function1) {
        throwComposeUnavailableError();
    }

    public final void throwComposeUnavailableError() {
        throw new IllegalStateException("Compose is not available. Make sure to check isComposeAvailable() before calling any other function on ComposeFacade.".toString());
    }
}