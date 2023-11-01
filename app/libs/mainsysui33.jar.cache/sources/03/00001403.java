package com.android.systemui.controls.management;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedCallback;
import androidx.activity.ComponentActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.Prefs;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.TooltipManager;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.StructureInfo;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.management.ControlsModel;
import com.android.systemui.controls.ui.ControlsActivity;
import com.android.systemui.controls.ui.ControlsUiController;
import com.android.systemui.settings.UserTracker;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlsFavoritingActivity.class */
public class ControlsFavoritingActivity extends ComponentActivity {
    public static final Companion Companion = new Companion(null);
    public CharSequence appName;
    public Runnable cancelLoadRunnable;
    public Comparator<StructureContainer> comparator;
    public ComponentName component;
    public final ControlsControllerImpl controller;
    public View doneButton;
    public final Executor executor;
    public boolean fromProviderSelector;
    public boolean isPagerLoaded;
    public final ControlsListingController listingController;
    public TooltipManager mTooltipManager;
    public View otherAppsButton;
    public ManagementPageIndicator pageIndicator;
    public TextView statusText;
    public CharSequence structureExtra;
    public ViewPager2 structurePager;
    public TextView subtitleView;
    public TextView titleView;
    public final ControlsUiController uiController;
    public final UserTracker userTracker;
    public List<StructureContainer> listOfStructures = CollectionsKt__CollectionsKt.emptyList();
    public final UserTracker.Callback userTrackerCallback = new UserTracker.Callback() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$userTrackerCallback$1
        public final int startingUser;

        {
            ControlsControllerImpl controlsControllerImpl;
            controlsControllerImpl = ControlsFavoritingActivity.this.controller;
            this.startingUser = controlsControllerImpl.getCurrentUserId();
        }

        public void onUserChanged(int i, Context context) {
            UserTracker userTracker;
            if (i != this.startingUser) {
                userTracker = ControlsFavoritingActivity.this.userTracker;
                userTracker.removeCallback(this);
                ControlsFavoritingActivity.this.finish();
            }
        }
    };
    public final OnBackInvokedCallback mOnBackInvokedCallback = new OnBackInvokedCallback() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$mOnBackInvokedCallback$1
        public final void onBackInvoked() {
            ControlsFavoritingActivity.this.onBackPressed();
        }
    };
    public final ControlsFavoritingActivity$listingCallback$1 listingCallback = new ControlsListingController.ControlsListingCallback() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$listingCallback$1
        @Override // com.android.systemui.controls.management.ControlsListingController.ControlsListingCallback
        public void onServicesUpdated(List<ControlsServiceInfo> list) {
            if (list.size() > 1) {
                View access$getOtherAppsButton$p = ControlsFavoritingActivity.access$getOtherAppsButton$p(ControlsFavoritingActivity.this);
                View view = access$getOtherAppsButton$p;
                if (access$getOtherAppsButton$p == null) {
                    view = null;
                }
                final ControlsFavoritingActivity controlsFavoritingActivity = ControlsFavoritingActivity.this;
                view.post(new Runnable() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$listingCallback$1$onServicesUpdated$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        View access$getOtherAppsButton$p2 = ControlsFavoritingActivity.access$getOtherAppsButton$p(ControlsFavoritingActivity.this);
                        View view2 = access$getOtherAppsButton$p2;
                        if (access$getOtherAppsButton$p2 == null) {
                            view2 = null;
                        }
                        view2.setVisibility(0);
                    }
                });
            }
        }
    };
    public final ControlsFavoritingActivity$controlsModelCallback$1 controlsModelCallback = new ControlsModel.ControlsModelCallback() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$controlsModelCallback$1
        @Override // com.android.systemui.controls.management.ControlsModel.ControlsModelCallback
        public void onFirstChange() {
            View access$getDoneButton$p = ControlsFavoritingActivity.access$getDoneButton$p(ControlsFavoritingActivity.this);
            View view = access$getDoneButton$p;
            if (access$getDoneButton$p == null) {
                view = null;
            }
            view.setEnabled(true);
        }
    };

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlsFavoritingActivity$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.systemui.controls.management.ControlsFavoritingActivity$listingCallback$1] */
    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.systemui.controls.management.ControlsFavoritingActivity$controlsModelCallback$1] */
    public ControlsFavoritingActivity(Executor executor, ControlsControllerImpl controlsControllerImpl, ControlsListingController controlsListingController, UserTracker userTracker, ControlsUiController controlsUiController) {
        this.executor = executor;
        this.controller = controlsControllerImpl;
        this.listingController = controlsListingController;
        this.userTracker = userTracker;
        this.uiController = controlsUiController;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsFavoritingActivity$bindButtons$1$1.onClick(android.view.View):void] */
    public static final /* synthetic */ void access$animateExitAndFinish(ControlsFavoritingActivity controlsFavoritingActivity) {
        controlsFavoritingActivity.animateExitAndFinish();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsFavoritingActivity$setUpPager$1$1.onPageSelected(int):void] */
    public static final /* synthetic */ CharSequence access$getAppName$p(ControlsFavoritingActivity controlsFavoritingActivity) {
        return controlsFavoritingActivity.appName;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsFavoritingActivity$bindButtons$1$1.onClick(android.view.View):void, com.android.systemui.controls.management.ControlsFavoritingActivity$controlsModelCallback$1.onFirstChange():void] */
    public static final /* synthetic */ View access$getDoneButton$p(ControlsFavoritingActivity controlsFavoritingActivity) {
        return controlsFavoritingActivity.doneButton;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsFavoritingActivity$setUpPager$1$1.onPageSelected(int):void] */
    public static final /* synthetic */ List access$getListOfStructures$p(ControlsFavoritingActivity controlsFavoritingActivity) {
        return controlsFavoritingActivity.listOfStructures;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsFavoritingActivity$bindViews$2$1.invoke(int):void, com.android.systemui.controls.management.ControlsFavoritingActivity$bindViews$5.onPageSelected(int):void] */
    public static final /* synthetic */ TooltipManager access$getMTooltipManager$p(ControlsFavoritingActivity controlsFavoritingActivity) {
        return controlsFavoritingActivity.mTooltipManager;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsFavoritingActivity$listingCallback$1.onServicesUpdated(java.util.List<com.android.systemui.controls.ControlsServiceInfo>):void, com.android.systemui.controls.management.ControlsFavoritingActivity$listingCallback$1$onServicesUpdated$1.run():void] */
    public static final /* synthetic */ View access$getOtherAppsButton$p(ControlsFavoritingActivity controlsFavoritingActivity) {
        return controlsFavoritingActivity.otherAppsButton;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsFavoritingActivity$setUpPager$1$1.onPageScrolled(int, float, int):void] */
    public static final /* synthetic */ ManagementPageIndicator access$getPageIndicator$p(ControlsFavoritingActivity controlsFavoritingActivity) {
        return controlsFavoritingActivity.pageIndicator;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsFavoritingActivity$setUpPager$1$1.onPageSelected(int):void] */
    public static final /* synthetic */ TextView access$getTitleView$p(ControlsFavoritingActivity controlsFavoritingActivity) {
        return controlsFavoritingActivity.titleView;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsFavoritingActivity$loadControls$1$2.accept(java.lang.Runnable):void] */
    public static final /* synthetic */ void access$setCancelLoadRunnable$p(ControlsFavoritingActivity controlsFavoritingActivity, Runnable runnable) {
        controlsFavoritingActivity.cancelLoadRunnable = runnable;
    }

    public final void animateExitAndFinish() {
        ControlsAnimations.exitAnimation((ViewGroup) requireViewById(R$id.controls_management_root), new Runnable() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$animateExitAndFinish$1
            @Override // java.lang.Runnable
            public void run() {
                ControlsFavoritingActivity.this.finish();
            }
        }).start();
    }

    public final void bindButtons() {
        View requireViewById = requireViewById(R$id.other_apps);
        final Button button = (Button) requireViewById;
        button.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$bindButtons$1$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                View access$getDoneButton$p = ControlsFavoritingActivity.access$getDoneButton$p(ControlsFavoritingActivity.this);
                View view2 = access$getDoneButton$p;
                if (access$getDoneButton$p == null) {
                    view2 = null;
                }
                if (view2.isEnabled()) {
                    Toast.makeText(ControlsFavoritingActivity.this.getApplicationContext(), R$string.controls_favorite_toast_no_changes, 0).show();
                }
                ControlsFavoritingActivity.this.startActivity(new Intent(button.getContext(), ControlsProviderSelectorActivity.class), ActivityOptions.makeSceneTransitionAnimation(ControlsFavoritingActivity.this, new Pair[0]).toBundle());
                ControlsFavoritingActivity.access$animateExitAndFinish(ControlsFavoritingActivity.this);
            }
        });
        this.otherAppsButton = requireViewById;
        View requireViewById2 = requireViewById(R$id.done);
        Button button2 = (Button) requireViewById2;
        button2.setEnabled(false);
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$bindButtons$2$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ComponentName componentName;
                List list;
                ControlsControllerImpl controlsControllerImpl;
                ComponentName componentName2;
                componentName = ControlsFavoritingActivity.this.component;
                if (componentName == null) {
                    return;
                }
                list = ControlsFavoritingActivity.this.listOfStructures;
                List<StructureContainer> list2 = list;
                ControlsFavoritingActivity controlsFavoritingActivity = ControlsFavoritingActivity.this;
                for (StructureContainer structureContainer : list2) {
                    List<ControlInfo> favorites = structureContainer.getModel().getFavorites();
                    controlsControllerImpl = controlsFavoritingActivity.controller;
                    componentName2 = controlsFavoritingActivity.component;
                    Intrinsics.checkNotNull(componentName2);
                    controlsControllerImpl.replaceFavoritesForStructure(new StructureInfo(componentName2, structureContainer.getStructureName(), favorites));
                }
                ControlsFavoritingActivity.this.animateExitAndFinish();
                ControlsFavoritingActivity.this.openControlsOrigin();
            }
        });
        this.doneButton = requireViewById2;
    }

    public final void bindViews() {
        setContentView(R$layout.controls_management);
        getLifecycle().addObserver(ControlsAnimations.observerForAnimations$default(ControlsAnimations.INSTANCE, (ViewGroup) requireViewById(R$id.controls_management_root), getWindow(), getIntent(), false, 8, null));
        ViewStub viewStub = (ViewStub) requireViewById(R$id.stub);
        viewStub.setLayoutResource(R$layout.controls_management_favorites);
        viewStub.inflate();
        this.statusText = (TextView) requireViewById(R$id.status_message);
        if (shouldShowTooltip()) {
            TextView textView = this.statusText;
            TextView textView2 = textView;
            if (textView == null) {
                textView2 = null;
            }
            TooltipManager tooltipManager = new TooltipManager(textView2.getContext(), "ControlsStructureSwipeTooltipCount", 2, false, 8, null);
            this.mTooltipManager = tooltipManager;
            addContentView(tooltipManager.getLayout(), new FrameLayout.LayoutParams(-2, -2, 51));
        }
        ManagementPageIndicator managementPageIndicator = (ManagementPageIndicator) requireViewById(R$id.structure_page_indicator);
        managementPageIndicator.setVisibilityListener(new Function1<Integer, Unit>() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$bindViews$2$1
            {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke(((Number) obj).intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i) {
                TooltipManager access$getMTooltipManager$p;
                if (i == 0 || (access$getMTooltipManager$p = ControlsFavoritingActivity.access$getMTooltipManager$p(ControlsFavoritingActivity.this)) == null) {
                    return;
                }
                access$getMTooltipManager$p.hide(true);
            }
        });
        this.pageIndicator = managementPageIndicator;
        CharSequence charSequence = this.structureExtra;
        CharSequence charSequence2 = charSequence;
        if (charSequence == null) {
            CharSequence charSequence3 = this.appName;
            charSequence2 = charSequence3;
            if (charSequence3 == null) {
                charSequence2 = getResources().getText(R$string.controls_favorite_default_title);
            }
        }
        TextView textView3 = (TextView) requireViewById(R$id.title);
        textView3.setText(charSequence2);
        this.titleView = textView3;
        TextView textView4 = (TextView) requireViewById(R$id.subtitle);
        textView4.setText(textView4.getResources().getText(R$string.controls_favorite_subtitle));
        this.subtitleView = textView4;
        ViewPager2 viewPager2 = (ViewPager2) requireViewById(R$id.structure_pager);
        this.structurePager = viewPager2;
        if (viewPager2 == null) {
            viewPager2 = null;
        }
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$bindViews$5
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int i) {
                super.onPageSelected(i);
                TooltipManager access$getMTooltipManager$p = ControlsFavoritingActivity.access$getMTooltipManager$p(ControlsFavoritingActivity.this);
                if (access$getMTooltipManager$p != null) {
                    access$getMTooltipManager$p.hide(true);
                }
            }
        });
        bindButtons();
    }

    public final void loadControls() {
        ComponentName componentName = this.component;
        if (componentName != null) {
            TextView textView = this.statusText;
            TextView textView2 = textView;
            if (textView == null) {
                textView2 = null;
            }
            textView2.setText(getResources().getText(17040621));
            final CharSequence text = getResources().getText(R$string.controls_favorite_other_zone_header);
            this.controller.loadForComponent(componentName, new Consumer() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$loadControls$1$1
                /* JADX DEBUG: Method merged with bridge method */
                @Override // java.util.function.Consumer
                public final void accept(ControlsController.LoadData loadData) {
                    Comparator comparator;
                    List list;
                    Executor executor;
                    List list2;
                    CharSequence charSequence;
                    ControlsFavoritingActivity$controlsModelCallback$1 controlsFavoritingActivity$controlsModelCallback$1;
                    List<ControlStatus> allControls = loadData.getAllControls();
                    List<String> favoritesIds = loadData.getFavoritesIds();
                    final boolean errorOnLoad = loadData.getErrorOnLoad();
                    List<ControlStatus> list3 = allControls;
                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                    for (T t : list3) {
                        CharSequence structure = ((ControlStatus) t).getControl().getStructure();
                        CharSequence charSequence2 = structure;
                        if (structure == null) {
                            charSequence2 = "";
                        }
                        Object obj = linkedHashMap.get(charSequence2);
                        ArrayList arrayList = obj;
                        if (obj == null) {
                            arrayList = new ArrayList();
                            linkedHashMap.put(charSequence2, arrayList);
                        }
                        ((List) arrayList).add(t);
                    }
                    ControlsFavoritingActivity controlsFavoritingActivity = ControlsFavoritingActivity.this;
                    CharSequence charSequence3 = text;
                    ArrayList arrayList2 = new ArrayList(linkedHashMap.size());
                    for (Map.Entry entry : linkedHashMap.entrySet()) {
                        controlsFavoritingActivity$controlsModelCallback$1 = controlsFavoritingActivity.controlsModelCallback;
                        arrayList2.add(new StructureContainer((CharSequence) entry.getKey(), new AllModel((List) entry.getValue(), favoritesIds, charSequence3, controlsFavoritingActivity$controlsModelCallback$1)));
                    }
                    comparator = ControlsFavoritingActivity.this.comparator;
                    Comparator comparator2 = comparator;
                    if (comparator == null) {
                        comparator2 = null;
                    }
                    controlsFavoritingActivity.listOfStructures = CollectionsKt___CollectionsKt.sortedWith(arrayList2, comparator2);
                    list = ControlsFavoritingActivity.this.listOfStructures;
                    ControlsFavoritingActivity controlsFavoritingActivity2 = ControlsFavoritingActivity.this;
                    Iterator it = list.iterator();
                    int i = 0;
                    while (true) {
                        if (!it.hasNext()) {
                            i = -1;
                            break;
                        }
                        CharSequence structureName = ((StructureContainer) it.next()).getStructureName();
                        charSequence = controlsFavoritingActivity2.structureExtra;
                        if (Intrinsics.areEqual(structureName, charSequence)) {
                            break;
                        }
                        i++;
                    }
                    int i2 = i;
                    if (i == -1) {
                        i2 = 0;
                    }
                    if (ControlsFavoritingActivity.this.getIntent().getBooleanExtra("extra_single_structure", false)) {
                        ControlsFavoritingActivity controlsFavoritingActivity3 = ControlsFavoritingActivity.this;
                        list2 = controlsFavoritingActivity3.listOfStructures;
                        controlsFavoritingActivity3.listOfStructures = CollectionsKt__CollectionsJVMKt.listOf(list2.get(i2));
                    }
                    executor = ControlsFavoritingActivity.this.executor;
                    final ControlsFavoritingActivity controlsFavoritingActivity4 = ControlsFavoritingActivity.this;
                    final int i3 = i2;
                    executor.execute(new Runnable() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$loadControls$1$1.2
                        @Override // java.lang.Runnable
                        public final void run() {
                            ViewPager2 viewPager2;
                            List list4;
                            ViewPager2 viewPager22;
                            List list5;
                            TextView textView3;
                            ManagementPageIndicator managementPageIndicator;
                            List list6;
                            ManagementPageIndicator managementPageIndicator2;
                            ManagementPageIndicator managementPageIndicator3;
                            List list7;
                            ManagementPageIndicator managementPageIndicator4;
                            ViewPager2 viewPager23;
                            TextView textView4;
                            TextView textView5;
                            TextView textView6;
                            CharSequence charSequence4;
                            TextView textView7;
                            viewPager2 = ControlsFavoritingActivity.this.structurePager;
                            ViewPager2 viewPager24 = viewPager2;
                            if (viewPager2 == null) {
                                viewPager24 = null;
                            }
                            list4 = ControlsFavoritingActivity.this.listOfStructures;
                            viewPager24.setAdapter(new StructureAdapter(list4));
                            viewPager22 = ControlsFavoritingActivity.this.structurePager;
                            ViewPager2 viewPager25 = viewPager22;
                            if (viewPager22 == null) {
                                viewPager25 = null;
                            }
                            viewPager25.setCurrentItem(i3);
                            int i4 = 0;
                            if (errorOnLoad) {
                                textView6 = ControlsFavoritingActivity.this.statusText;
                                TextView textView8 = textView6;
                                if (textView6 == null) {
                                    textView8 = null;
                                }
                                Resources resources = ControlsFavoritingActivity.this.getResources();
                                int i5 = R$string.controls_favorite_load_error;
                                charSequence4 = ControlsFavoritingActivity.this.appName;
                                CharSequence charSequence5 = charSequence4;
                                if (charSequence4 == null) {
                                    charSequence5 = "";
                                }
                                textView8.setText(resources.getString(i5, charSequence5));
                                textView7 = ControlsFavoritingActivity.this.subtitleView;
                                TextView textView9 = textView7;
                                if (textView9 == null) {
                                    textView9 = null;
                                }
                                textView9.setVisibility(8);
                                return;
                            }
                            list5 = ControlsFavoritingActivity.this.listOfStructures;
                            if (list5.isEmpty()) {
                                textView4 = ControlsFavoritingActivity.this.statusText;
                                TextView textView10 = textView4;
                                if (textView4 == null) {
                                    textView10 = null;
                                }
                                textView10.setText(ControlsFavoritingActivity.this.getResources().getString(R$string.controls_favorite_load_none));
                                textView5 = ControlsFavoritingActivity.this.subtitleView;
                                TextView textView11 = textView5;
                                if (textView11 == null) {
                                    textView11 = null;
                                }
                                textView11.setVisibility(8);
                                return;
                            }
                            textView3 = ControlsFavoritingActivity.this.statusText;
                            TextView textView12 = textView3;
                            if (textView3 == null) {
                                textView12 = null;
                            }
                            textView12.setVisibility(8);
                            managementPageIndicator = ControlsFavoritingActivity.this.pageIndicator;
                            ManagementPageIndicator managementPageIndicator5 = managementPageIndicator;
                            if (managementPageIndicator == null) {
                                managementPageIndicator5 = null;
                            }
                            list6 = ControlsFavoritingActivity.this.listOfStructures;
                            managementPageIndicator5.setNumPages(list6.size());
                            managementPageIndicator2 = ControlsFavoritingActivity.this.pageIndicator;
                            ManagementPageIndicator managementPageIndicator6 = managementPageIndicator2;
                            if (managementPageIndicator2 == null) {
                                managementPageIndicator6 = null;
                            }
                            managementPageIndicator6.setLocation(ActionBarShadowController.ELEVATION_LOW);
                            managementPageIndicator3 = ControlsFavoritingActivity.this.pageIndicator;
                            ManagementPageIndicator managementPageIndicator7 = managementPageIndicator3;
                            if (managementPageIndicator3 == null) {
                                managementPageIndicator7 = null;
                            }
                            list7 = ControlsFavoritingActivity.this.listOfStructures;
                            if (list7.size() <= 1) {
                                i4 = 4;
                            }
                            managementPageIndicator7.setVisibility(i4);
                            ControlsAnimations controlsAnimations = ControlsAnimations.INSTANCE;
                            managementPageIndicator4 = ControlsFavoritingActivity.this.pageIndicator;
                            ManagementPageIndicator managementPageIndicator8 = managementPageIndicator4;
                            if (managementPageIndicator4 == null) {
                                managementPageIndicator8 = null;
                            }
                            Animator enterAnimation = controlsAnimations.enterAnimation(managementPageIndicator8);
                            final ControlsFavoritingActivity controlsFavoritingActivity5 = ControlsFavoritingActivity.this;
                            enterAnimation.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$loadControls$1$1$2$1$1
                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public void onAnimationEnd(Animator animator) {
                                    ManagementPageIndicator managementPageIndicator9;
                                    TooltipManager tooltipManager;
                                    ManagementPageIndicator managementPageIndicator10;
                                    ManagementPageIndicator managementPageIndicator11;
                                    ManagementPageIndicator managementPageIndicator12;
                                    TooltipManager tooltipManager2;
                                    managementPageIndicator9 = ControlsFavoritingActivity.this.pageIndicator;
                                    ManagementPageIndicator managementPageIndicator13 = managementPageIndicator9;
                                    if (managementPageIndicator9 == null) {
                                        managementPageIndicator13 = null;
                                    }
                                    if (managementPageIndicator13.getVisibility() == 0) {
                                        tooltipManager = ControlsFavoritingActivity.this.mTooltipManager;
                                        if (tooltipManager != null) {
                                            int[] iArr = new int[2];
                                            managementPageIndicator10 = ControlsFavoritingActivity.this.pageIndicator;
                                            ManagementPageIndicator managementPageIndicator14 = managementPageIndicator10;
                                            if (managementPageIndicator10 == null) {
                                                managementPageIndicator14 = null;
                                            }
                                            managementPageIndicator14.getLocationOnScreen(iArr);
                                            int i6 = iArr[0];
                                            managementPageIndicator11 = ControlsFavoritingActivity.this.pageIndicator;
                                            ManagementPageIndicator managementPageIndicator15 = managementPageIndicator11;
                                            if (managementPageIndicator11 == null) {
                                                managementPageIndicator15 = null;
                                            }
                                            int width = managementPageIndicator15.getWidth() / 2;
                                            int i7 = iArr[1];
                                            managementPageIndicator12 = ControlsFavoritingActivity.this.pageIndicator;
                                            ManagementPageIndicator managementPageIndicator16 = managementPageIndicator12;
                                            if (managementPageIndicator16 == null) {
                                                managementPageIndicator16 = null;
                                            }
                                            int height = managementPageIndicator16.getHeight();
                                            tooltipManager2 = ControlsFavoritingActivity.this.mTooltipManager;
                                            if (tooltipManager2 != null) {
                                                tooltipManager2.show(R$string.controls_structure_tooltip, i6 + width, i7 + height);
                                            }
                                        }
                                    }
                                }
                            });
                            enterAnimation.start();
                            viewPager23 = ControlsFavoritingActivity.this.structurePager;
                            ViewPager2 viewPager26 = viewPager23;
                            if (viewPager26 == null) {
                                viewPager26 = null;
                            }
                            controlsAnimations.enterAnimation(viewPager26).start();
                        }
                    });
                }
            }, new Consumer() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$loadControls$1$2
                /* JADX DEBUG: Method merged with bridge method */
                @Override // java.util.function.Consumer
                public final void accept(Runnable runnable) {
                    ControlsFavoritingActivity.access$setCancelLoadRunnable$p(ControlsFavoritingActivity.this, runnable);
                }
            });
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (!this.fromProviderSelector) {
            openControlsOrigin();
        }
        animateExitAndFinish();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        TooltipManager tooltipManager = this.mTooltipManager;
        if (tooltipManager != null) {
            tooltipManager.hide(false);
        }
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        final Collator collator = Collator.getInstance(getResources().getConfiguration().getLocales().get(0));
        this.comparator = new Comparator() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$onCreate$$inlined$compareBy$1
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return collator.compare(((StructureContainer) t).getStructureName(), ((StructureContainer) t2).getStructureName());
            }
        };
        this.appName = getIntent().getCharSequenceExtra("extra_app_label");
        this.structureExtra = getIntent().getCharSequenceExtra("extra_structure");
        this.component = (ComponentName) getIntent().getParcelableExtra("android.intent.extra.COMPONENT_NAME");
        this.fromProviderSelector = getIntent().getBooleanExtra("extra_from_provider_selector", false);
        bindViews();
    }

    @Override // android.app.Activity
    public void onDestroy() {
        Runnable runnable = this.cancelLoadRunnable;
        if (runnable != null) {
            runnable.run();
        }
        super.onDestroy();
    }

    @Override // android.app.Activity
    public void onPause() {
        super.onPause();
        TooltipManager tooltipManager = this.mTooltipManager;
        if (tooltipManager != null) {
            tooltipManager.hide(false);
        }
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        if (this.isPagerLoaded) {
            return;
        }
        setUpPager();
        loadControls();
        this.isPagerLoaded = true;
    }

    @Override // android.app.Activity
    public void onStart() {
        super.onStart();
        this.listingController.addCallback(this.listingCallback);
        this.userTracker.addCallback(this.userTrackerCallback, this.executor);
        getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, this.mOnBackInvokedCallback);
    }

    @Override // android.app.Activity
    public void onStop() {
        super.onStop();
        this.listingController.removeCallback(this.listingCallback);
        this.userTracker.removeCallback(this.userTrackerCallback);
        getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(this.mOnBackInvokedCallback);
    }

    public final void openControlsOrigin() {
        startActivity(new Intent(getApplicationContext(), ControlsActivity.class), ActivityOptions.makeSceneTransitionAnimation(this, new Pair[0]).toBundle());
    }

    public final void setUpPager() {
        ViewPager2 viewPager2 = this.structurePager;
        ViewPager2 viewPager22 = viewPager2;
        if (viewPager2 == null) {
            viewPager22 = null;
        }
        viewPager22.setAlpha(ActionBarShadowController.ELEVATION_LOW);
        ManagementPageIndicator managementPageIndicator = this.pageIndicator;
        ManagementPageIndicator managementPageIndicator2 = managementPageIndicator;
        if (managementPageIndicator == null) {
            managementPageIndicator2 = null;
        }
        managementPageIndicator2.setAlpha(ActionBarShadowController.ELEVATION_LOW);
        ViewPager2 viewPager23 = this.structurePager;
        if (viewPager23 == null) {
            viewPager23 = null;
        }
        viewPager23.setAdapter(new StructureAdapter(CollectionsKt__CollectionsKt.emptyList()));
        viewPager23.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$setUpPager$1$1
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageScrolled(int i, float f, int i2) {
                super.onPageScrolled(i, f, i2);
                ManagementPageIndicator access$getPageIndicator$p = ControlsFavoritingActivity.access$getPageIndicator$p(ControlsFavoritingActivity.this);
                ManagementPageIndicator managementPageIndicator3 = access$getPageIndicator$p;
                if (access$getPageIndicator$p == null) {
                    managementPageIndicator3 = null;
                }
                managementPageIndicator3.setLocation(i + f);
            }

            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int i) {
                super.onPageSelected(i);
                CharSequence structureName = ((StructureContainer) ControlsFavoritingActivity.access$getListOfStructures$p(ControlsFavoritingActivity.this).get(i)).getStructureName();
                if (TextUtils.isEmpty(structureName)) {
                    structureName = ControlsFavoritingActivity.access$getAppName$p(ControlsFavoritingActivity.this);
                }
                TextView access$getTitleView$p = ControlsFavoritingActivity.access$getTitleView$p(ControlsFavoritingActivity.this);
                TextView textView = access$getTitleView$p;
                if (access$getTitleView$p == null) {
                    textView = null;
                }
                textView.setText(structureName);
                TextView access$getTitleView$p2 = ControlsFavoritingActivity.access$getTitleView$p(ControlsFavoritingActivity.this);
                if (access$getTitleView$p2 == null) {
                    access$getTitleView$p2 = null;
                }
                access$getTitleView$p2.requestFocus();
            }
        });
    }

    public final boolean shouldShowTooltip() {
        boolean z = false;
        if (Prefs.getInt(getApplicationContext(), "ControlsStructureSwipeTooltipCount", 0) < 2) {
            z = true;
        }
        return z;
    }
}