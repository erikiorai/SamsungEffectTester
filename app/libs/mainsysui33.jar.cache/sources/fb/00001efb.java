package com.android.systemui.people;

import android.os.Bundle;
import android.view.ViewGroup;
import androidx.activity.ComponentActivity;
import androidx.lifecycle.ViewModelProvider;
import com.android.systemui.compose.ComposeFacade;
import com.android.systemui.people.ui.view.PeopleViewBinder;
import com.android.systemui.people.ui.viewmodel.PeopleViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: mainsysui33.jar:com/android/systemui/people/PeopleSpaceActivity.class */
public class PeopleSpaceActivity extends ComponentActivity {
    public final PeopleViewModel.Factory mViewModelFactory;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.people.PeopleSpaceActivity$$ExternalSyntheticLambda0.invoke(java.lang.Object):java.lang.Object] */
    public static /* synthetic */ Unit $r8$lambda$E4BxCs3hpEvM3l23rFTIdGvtCsQ(PeopleSpaceActivity peopleSpaceActivity, PeopleViewModel.Result result) {
        return peopleSpaceActivity.lambda$onCreate$0(result);
    }

    public PeopleSpaceActivity(PeopleViewModel.Factory factory) {
        this.mViewModelFactory = factory;
    }

    public /* synthetic */ Unit lambda$onCreate$0(PeopleViewModel.Result result) {
        finishActivity(result);
        return null;
    }

    public final void finishActivity(PeopleViewModel.Result result) {
        if (result instanceof PeopleViewModel.Result.Success) {
            setResult(-1, ((PeopleViewModel.Result.Success) result).getData());
        } else {
            setResult(0);
        }
        finish();
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setResult(0);
        PeopleViewModel peopleViewModel = (PeopleViewModel) new ViewModelProvider(this, this.mViewModelFactory).get(PeopleViewModel.class);
        peopleViewModel.onWidgetIdChanged(getIntent().getIntExtra("appWidgetId", 0));
        Function1<? super PeopleViewModel.Result, Unit> function1 = new Function1() { // from class: com.android.systemui.people.PeopleSpaceActivity$$ExternalSyntheticLambda0
            public final Object invoke(Object obj) {
                return PeopleSpaceActivity.$r8$lambda$E4BxCs3hpEvM3l23rFTIdGvtCsQ(PeopleSpaceActivity.this, (PeopleViewModel.Result) obj);
            }
        };
        ComposeFacade composeFacade = ComposeFacade.INSTANCE;
        if (composeFacade.isComposeAvailable()) {
            composeFacade.setPeopleSpaceActivityContent(this, peopleViewModel, function1);
            return;
        }
        ViewGroup create = PeopleViewBinder.create(this);
        PeopleViewBinder.bind(create, peopleViewModel, this, function1);
        setContentView(create);
    }
}