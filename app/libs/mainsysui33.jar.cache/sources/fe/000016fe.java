package com.android.systemui.dreams.complication;

import android.os.Debug;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dreams.complication.Complication;
import com.android.systemui.util.ViewController;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/ComplicationHostViewController.class */
public class ComplicationHostViewController extends ViewController<ConstraintLayout> {
    public static final boolean DEBUG = Log.isLoggable("ComplicationHostVwCtrl", 3);
    public final ComplicationCollectionViewModel mComplicationCollectionViewModel;
    public final HashMap<ComplicationId, Complication.ViewHolder> mComplications;
    public final DreamOverlayStateController mDreamOverlayStateController;
    public boolean mEntryAnimationsFinished;
    public final ComplicationLayoutEngine mLayoutEngine;
    public final LifecycleOwner mLifecycleOwner;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.complication.ComplicationHostViewController$$ExternalSyntheticLambda1.apply(java.lang.Object):java.lang.Object] */
    public static /* synthetic */ ComplicationId $r8$lambda$61Fi56HvtQadmxf2OPPOilX9rj4(ComplicationViewModel complicationViewModel) {
        return complicationViewModel.getId();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.complication.ComplicationHostViewController$$ExternalSyntheticLambda4.test(java.lang.Object):boolean] */
    public static /* synthetic */ boolean $r8$lambda$A7fSBw4IQc1KsYIb8zmL6rSqUfk(ComplicationHostViewController complicationHostViewController, ComplicationViewModel complicationViewModel) {
        return complicationHostViewController.lambda$updateComplications$4(complicationViewModel);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.complication.ComplicationHostViewController$$ExternalSyntheticLambda2.test(java.lang.Object):boolean] */
    public static /* synthetic */ boolean $r8$lambda$MOP03vYBGOBgWqbfUiEYhUVOqP8(Collection collection, ComplicationId complicationId) {
        return lambda$updateComplications$2(collection, complicationId);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.complication.ComplicationHostViewController$$ExternalSyntheticLambda0.onChanged(java.lang.Object):void] */
    /* renamed from: $r8$lambda$TyJuS1xMJXbTVzec-NGbxOTdXjU */
    public static /* synthetic */ void m2577$r8$lambda$TyJuS1xMJXbTVzecNGbxOTdXjU(ComplicationHostViewController complicationHostViewController, Collection collection) {
        complicationHostViewController.lambda$onInit$0(collection);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.complication.ComplicationHostViewController$$ExternalSyntheticLambda5.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$XUlGZcUTELmMLnr1nVfBpIgmMfc(ComplicationHostViewController complicationHostViewController, ComplicationViewModel complicationViewModel) {
        complicationHostViewController.lambda$updateComplications$5(complicationViewModel);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.complication.ComplicationHostViewController$$ExternalSyntheticLambda3.accept(java.lang.Object):void] */
    /* renamed from: $r8$lambda$oBztZej-i2G_FhU5XC4yqZQXJ3A */
    public static /* synthetic */ void m2578$r8$lambda$oBztZeji2G_FhU5XC4yqZQXJ3A(ComplicationHostViewController complicationHostViewController, ComplicationId complicationId) {
        complicationHostViewController.lambda$updateComplications$3(complicationId);
    }

    public ComplicationHostViewController(ConstraintLayout constraintLayout, ComplicationLayoutEngine complicationLayoutEngine, DreamOverlayStateController dreamOverlayStateController, LifecycleOwner lifecycleOwner, ComplicationCollectionViewModel complicationCollectionViewModel) {
        super(constraintLayout);
        this.mComplications = new HashMap<>();
        this.mEntryAnimationsFinished = false;
        this.mLayoutEngine = complicationLayoutEngine;
        this.mLifecycleOwner = lifecycleOwner;
        this.mComplicationCollectionViewModel = complicationCollectionViewModel;
        this.mDreamOverlayStateController = dreamOverlayStateController;
        dreamOverlayStateController.addCallback(new DreamOverlayStateController.Callback() { // from class: com.android.systemui.dreams.complication.ComplicationHostViewController.1
            {
                ComplicationHostViewController.this = this;
            }

            @Override // com.android.systemui.dreams.DreamOverlayStateController.Callback
            public void onStateChanged() {
                ComplicationHostViewController complicationHostViewController = ComplicationHostViewController.this;
                complicationHostViewController.mEntryAnimationsFinished = complicationHostViewController.mDreamOverlayStateController.areEntryAnimationsFinished();
            }
        });
    }

    public static /* synthetic */ boolean lambda$updateComplications$2(Collection collection, ComplicationId complicationId) {
        return !collection.contains(complicationId);
    }

    public /* synthetic */ void lambda$updateComplications$3(ComplicationId complicationId) {
        this.mLayoutEngine.removeComplication(complicationId);
        this.mComplications.remove(complicationId);
    }

    public /* synthetic */ boolean lambda$updateComplications$4(ComplicationViewModel complicationViewModel) {
        return !this.mComplications.containsKey(complicationViewModel.getId());
    }

    public /* synthetic */ void lambda$updateComplications$5(ComplicationViewModel complicationViewModel) {
        ComplicationId id = complicationViewModel.getId();
        Complication.ViewHolder createView = complicationViewModel.getComplication().createView(complicationViewModel);
        View view = createView.getView();
        if (view == null) {
            Log.e("ComplicationHostVwCtrl", "invalid complication view. null view supplied by ViewHolder");
            return;
        }
        if (!this.mEntryAnimationsFinished) {
            view.setVisibility(4);
        }
        this.mComplications.put(id, createView);
        if (view.getParent() != null) {
            Log.e("ComplicationHostVwCtrl", "View for complication " + complicationViewModel.getComplication().getClass() + " already has a parent. Make sure not to reuse complication views!");
        }
        ComplicationLayoutEngine complicationLayoutEngine = this.mLayoutEngine;
        createView.getLayoutParams();
        complicationLayoutEngine.addComplication(id, view, null, createView.getCategory());
    }

    public View getView() {
        return ((ViewController) this).mView;
    }

    public List<View> getViewsAtPosition(int i) {
        return this.mLayoutEngine.getViewsAtPosition(i);
    }

    public void onInit() {
        super.onInit();
        this.mComplicationCollectionViewModel.getComplications().observe(this.mLifecycleOwner, new Observer() { // from class: com.android.systemui.dreams.complication.ComplicationHostViewController$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ComplicationHostViewController.m2577$r8$lambda$TyJuS1xMJXbTVzecNGbxOTdXjU(ComplicationHostViewController.this, (Collection) obj);
            }
        });
    }

    public void onViewAttached() {
    }

    public void onViewDetached() {
    }

    /* renamed from: updateComplications */
    public final void lambda$onInit$0(Collection<ComplicationViewModel> collection) {
        if (DEBUG) {
            Log.d("ComplicationHostVwCtrl", "updateComplications called. Callers = " + Debug.getCallers(25));
            Log.d("ComplicationHostVwCtrl", "    mComplications = " + this.mComplications.toString());
            Log.d("ComplicationHostVwCtrl", "    complications = " + collection.toString());
        }
        final Collection collection2 = (Collection) collection.stream().map(new Function() { // from class: com.android.systemui.dreams.complication.ComplicationHostViewController$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ComplicationHostViewController.$r8$lambda$61Fi56HvtQadmxf2OPPOilX9rj4((ComplicationViewModel) obj);
            }
        }).collect(Collectors.toSet());
        ((Collection) this.mComplications.keySet().stream().filter(new Predicate() { // from class: com.android.systemui.dreams.complication.ComplicationHostViewController$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ComplicationHostViewController.$r8$lambda$MOP03vYBGOBgWqbfUiEYhUVOqP8(collection2, (ComplicationId) obj);
            }
        }).collect(Collectors.toSet())).forEach(new Consumer() { // from class: com.android.systemui.dreams.complication.ComplicationHostViewController$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ComplicationHostViewController.m2578$r8$lambda$oBztZeji2G_FhU5XC4yqZQXJ3A(ComplicationHostViewController.this, (ComplicationId) obj);
            }
        });
        ((Collection) collection.stream().filter(new Predicate() { // from class: com.android.systemui.dreams.complication.ComplicationHostViewController$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ComplicationHostViewController.$r8$lambda$A7fSBw4IQc1KsYIb8zmL6rSqUfk(ComplicationHostViewController.this, (ComplicationViewModel) obj);
            }
        }).collect(Collectors.toSet())).forEach(new Consumer() { // from class: com.android.systemui.dreams.complication.ComplicationHostViewController$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ComplicationHostViewController.$r8$lambda$XUlGZcUTELmMLnr1nVfBpIgmMfc(ComplicationHostViewController.this, (ComplicationViewModel) obj);
            }
        });
    }
}