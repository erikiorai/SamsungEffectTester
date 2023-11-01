package com.android.systemui.controls.management;

import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;
import androidx.activity.ComponentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.ui.ControlsActivity;
import com.android.systemui.controls.ui.ControlsUiController;
import com.android.systemui.settings.UserTracker;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlsProviderSelectorActivity.class */
public class ControlsProviderSelectorActivity extends ComponentActivity {
    public static final Companion Companion = new Companion(null);
    public final Executor backExecutor;
    public boolean backShouldExit;
    public final ControlsController controlsController;
    public final Executor executor;
    public final ControlsListingController listingController;
    public RecyclerView recyclerView;
    public final ControlsUiController uiController;
    public final UserTracker userTracker;
    public final UserTracker.Callback userTrackerCallback = new UserTracker.Callback() { // from class: com.android.systemui.controls.management.ControlsProviderSelectorActivity$userTrackerCallback$1
        public final int startingUser;

        {
            this.startingUser = ControlsProviderSelectorActivity.access$getListingController$p(ControlsProviderSelectorActivity.this).getCurrentUserId();
        }

        public void onUserChanged(int i, Context context) {
            if (i != this.startingUser) {
                ControlsProviderSelectorActivity.access$getUserTracker$p(ControlsProviderSelectorActivity.this).removeCallback(this);
                ControlsProviderSelectorActivity.this.finish();
            }
        }
    };
    public final OnBackInvokedCallback mOnBackInvokedCallback = new OnBackInvokedCallback() { // from class: com.android.systemui.controls.management.ControlsProviderSelectorActivity$mOnBackInvokedCallback$1
        public final void onBackInvoked() {
            ControlsProviderSelectorActivity.this.onBackPressed();
        }
    };

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlsProviderSelectorActivity$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ControlsProviderSelectorActivity(Executor executor, Executor executor2, ControlsListingController controlsListingController, ControlsController controlsController, UserTracker userTracker, ControlsUiController controlsUiController) {
        this.executor = executor;
        this.backExecutor = executor2;
        this.listingController = controlsListingController;
        this.controlsController = controlsController;
        this.userTracker = userTracker;
        this.uiController = controlsUiController;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsProviderSelectorActivity$launchFavoritingActivity$1.run():void] */
    public static final /* synthetic */ void access$animateExitAndFinish(ControlsProviderSelectorActivity controlsProviderSelectorActivity) {
        controlsProviderSelectorActivity.animateExitAndFinish();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsProviderSelectorActivity$launchFavoritingActivity$1.run():void, com.android.systemui.controls.management.ControlsProviderSelectorActivity$userTrackerCallback$1.<init>(com.android.systemui.controls.management.ControlsProviderSelectorActivity):void] */
    public static final /* synthetic */ ControlsListingController access$getListingController$p(ControlsProviderSelectorActivity controlsProviderSelectorActivity) {
        return controlsProviderSelectorActivity.listingController;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsProviderSelectorActivity$userTrackerCallback$1.onUserChanged(int, android.content.Context):void] */
    public static final /* synthetic */ UserTracker access$getUserTracker$p(ControlsProviderSelectorActivity controlsProviderSelectorActivity) {
        return controlsProviderSelectorActivity.userTracker;
    }

    public final void animateExitAndFinish() {
        ControlsAnimations.exitAnimation((ViewGroup) requireViewById(R$id.controls_management_root), new Runnable() { // from class: com.android.systemui.controls.management.ControlsProviderSelectorActivity$animateExitAndFinish$1
            @Override // java.lang.Runnable
            public void run() {
                ControlsProviderSelectorActivity.this.finish();
            }
        }).start();
    }

    public final void launchFavoritingActivity(final ComponentName componentName) {
        this.executor.execute(new Runnable() { // from class: com.android.systemui.controls.management.ControlsProviderSelectorActivity$launchFavoritingActivity$1
            @Override // java.lang.Runnable
            public final void run() {
                ComponentName componentName2 = componentName;
                if (componentName2 != null) {
                    ControlsProviderSelectorActivity controlsProviderSelectorActivity = this;
                    Intent intent = new Intent(controlsProviderSelectorActivity.getApplicationContext(), ControlsFavoritingActivity.class);
                    intent.putExtra("extra_app_label", ControlsProviderSelectorActivity.access$getListingController$p(controlsProviderSelectorActivity).getAppLabel(componentName2));
                    intent.putExtra("android.intent.extra.COMPONENT_NAME", componentName2);
                    intent.putExtra("extra_from_provider_selector", true);
                    controlsProviderSelectorActivity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(controlsProviderSelectorActivity, new Pair[0]).toBundle());
                    ControlsProviderSelectorActivity.access$animateExitAndFinish(controlsProviderSelectorActivity);
                }
            }
        });
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (!this.backShouldExit) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(getApplicationContext(), ControlsActivity.class));
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, new Pair[0]).toBundle());
        }
        animateExitAndFinish();
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R$layout.controls_management);
        getLifecycle().addObserver(ControlsAnimations.observerForAnimations$default(ControlsAnimations.INSTANCE, (ViewGroup) requireViewById(R$id.controls_management_root), getWindow(), getIntent(), false, 8, null));
        ViewStub viewStub = (ViewStub) requireViewById(R$id.stub);
        viewStub.setLayoutResource(R$layout.controls_management_apps);
        viewStub.inflate();
        RecyclerView recyclerView = (RecyclerView) requireViewById(R$id.list);
        this.recyclerView = recyclerView;
        RecyclerView recyclerView2 = recyclerView;
        if (recyclerView == null) {
            recyclerView2 = null;
        }
        recyclerView2.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        TextView textView = (TextView) requireViewById(R$id.title);
        textView.setText(textView.getResources().getText(R$string.controls_providers_title));
        Button button = (Button) requireViewById(R$id.other_apps);
        button.setVisibility(0);
        button.setText(17039360);
        button.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.management.ControlsProviderSelectorActivity$onCreate$3$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ControlsProviderSelectorActivity.this.onBackPressed();
            }
        });
        requireViewById(R$id.done).setVisibility(8);
        this.backShouldExit = getIntent().getBooleanExtra("back_should_exit", false);
    }

    @Override // android.app.Activity
    public void onDestroy() {
        this.userTracker.removeCallback(this.userTrackerCallback);
        super.onDestroy();
    }

    @Override // android.app.Activity
    public void onStart() {
        super.onStart();
        this.userTracker.addCallback(this.userTrackerCallback, this.executor);
        RecyclerView recyclerView = this.recyclerView;
        RecyclerView recyclerView2 = recyclerView;
        if (recyclerView == null) {
            recyclerView2 = null;
        }
        recyclerView2.setAlpha(ActionBarShadowController.ELEVATION_LOW);
        RecyclerView recyclerView3 = this.recyclerView;
        if (recyclerView3 == null) {
            recyclerView3 = null;
        }
        AppAdapter appAdapter = new AppAdapter(this.backExecutor, this.executor, getLifecycle(), this.listingController, LayoutInflater.from(this), new ControlsProviderSelectorActivity$onStart$1(this), new FavoritesRenderer(getResources(), new ControlsProviderSelectorActivity$onStart$2(this.controlsController)), getResources());
        appAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() { // from class: com.android.systemui.controls.management.ControlsProviderSelectorActivity$onStart$3$1
            public boolean hasAnimated;

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onChanged() {
                RecyclerView recyclerView4;
                if (this.hasAnimated) {
                    return;
                }
                this.hasAnimated = true;
                ControlsAnimations controlsAnimations = ControlsAnimations.INSTANCE;
                recyclerView4 = ControlsProviderSelectorActivity.this.recyclerView;
                RecyclerView recyclerView5 = recyclerView4;
                if (recyclerView4 == null) {
                    recyclerView5 = null;
                }
                controlsAnimations.enterAnimation(recyclerView5).start();
            }
        });
        recyclerView3.setAdapter(appAdapter);
        getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, this.mOnBackInvokedCallback);
    }

    @Override // android.app.Activity
    public void onStop() {
        super.onStop();
        this.userTracker.removeCallback(this.userTrackerCallback);
        getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(this.mOnBackInvokedCallback);
    }
}