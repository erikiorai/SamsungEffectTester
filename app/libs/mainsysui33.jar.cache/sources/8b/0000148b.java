package com.android.systemui.controls.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.RemoteException;
import android.service.dreams.IDreamManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import androidx.activity.ComponentActivity;
import androidx.lifecycle.Lifecycle;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.controls.management.ControlsAnimations;
import com.android.systemui.controls.settings.ControlsSettingsDialogManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.flags.UnreleasedFlag;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/ControlsActivity.class */
public final class ControlsActivity extends ComponentActivity {
    public final BroadcastDispatcher broadcastDispatcher;
    public BroadcastReceiver broadcastReceiver;
    public final ControlsSettingsDialogManager controlsSettingsDialogManager;
    public final IDreamManager dreamManager;
    public final FeatureFlags featureFlags;
    public final KeyguardStateController keyguardStateController;
    public boolean mExitToDream;
    public ViewGroup parent;
    public final ControlsUiController uiController;

    public ControlsActivity(ControlsUiController controlsUiController, BroadcastDispatcher broadcastDispatcher, IDreamManager iDreamManager, FeatureFlags featureFlags, ControlsSettingsDialogManager controlsSettingsDialogManager, KeyguardStateController keyguardStateController) {
        this.uiController = controlsUiController;
        this.broadcastDispatcher = broadcastDispatcher;
        this.dreamManager = iDreamManager;
        this.featureFlags = featureFlags;
        this.controlsSettingsDialogManager = controlsSettingsDialogManager;
        this.keyguardStateController = keyguardStateController;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.ui.ControlsActivity$onStart$1.invoke():void] */
    public static final /* synthetic */ ViewGroup access$getParent$p(ControlsActivity controlsActivity) {
        return controlsActivity.parent;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.ui.ControlsActivity$onStart$1.invoke():void] */
    public static final /* synthetic */ ControlsUiController access$getUiController$p(ControlsActivity controlsActivity) {
        return controlsActivity.uiController;
    }

    public final void finishOrReturnToDream() {
        if (this.mExitToDream) {
            try {
                this.mExitToDream = false;
                this.dreamManager.dream();
                return;
            } catch (RemoteException e) {
            }
        }
        finish();
    }

    public final void initBroadcastReceiver() {
        this.broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.controls.ui.ControlsActivity$initBroadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (Intrinsics.areEqual(action, "android.intent.action.SCREEN_OFF") || Intrinsics.areEqual(action, "android.intent.action.DREAMING_STARTED")) {
                    ControlsActivity.this.finish();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.DREAMING_STARTED");
        BroadcastDispatcher broadcastDispatcher = this.broadcastDispatcher;
        BroadcastReceiver broadcastReceiver = this.broadcastReceiver;
        BroadcastReceiver broadcastReceiver2 = broadcastReceiver;
        if (broadcastReceiver == null) {
            broadcastReceiver2 = null;
        }
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, broadcastReceiver2, intentFilter, null, null, 0, null, 60, null);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        finishOrReturnToDream();
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FeatureFlags featureFlags = this.featureFlags;
        UnreleasedFlag unreleasedFlag = Flags.USE_APP_PANELS;
        if (featureFlags.isEnabled(unreleasedFlag)) {
            getWindow().addPrivateFlags(536870912);
        }
        setContentView(R$layout.controls_fullscreen);
        Lifecycle lifecycle = getLifecycle();
        ControlsAnimations controlsAnimations = ControlsAnimations.INSTANCE;
        int i = R$id.control_detail_root;
        lifecycle.addObserver(controlsAnimations.observerForAnimations((ViewGroup) requireViewById(i), getWindow(), getIntent(), !this.featureFlags.isEnabled(unreleasedFlag)));
        ((ViewGroup) requireViewById(i)).setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.controls.ui.ControlsActivity$onCreate$1$1
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), windowInsets.getInsets(WindowInsets.Type.systemBars()).bottom);
                return WindowInsets.CONSUMED;
            }
        });
        initBroadcastReceiver();
    }

    @Override // android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        BroadcastDispatcher broadcastDispatcher = this.broadcastDispatcher;
        BroadcastReceiver broadcastReceiver = this.broadcastReceiver;
        BroadcastReceiver broadcastReceiver2 = broadcastReceiver;
        if (broadcastReceiver == null) {
            broadcastReceiver2 = null;
        }
        broadcastDispatcher.unregisterReceiver(broadcastReceiver2);
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        this.mExitToDream = getIntent().getBooleanExtra("extra_exit_to_dream", false);
    }

    @Override // android.app.Activity
    public void onStart() {
        super.onStart();
        ViewGroup viewGroup = (ViewGroup) requireViewById(R$id.global_actions_controls);
        this.parent = viewGroup;
        ViewGroup viewGroup2 = viewGroup;
        if (viewGroup == null) {
            viewGroup2 = null;
        }
        viewGroup2.setAlpha(ActionBarShadowController.ELEVATION_LOW);
        if (!this.featureFlags.isEnabled(Flags.USE_APP_PANELS) || this.keyguardStateController.isUnlocked()) {
            ControlsUiController controlsUiController = this.uiController;
            ViewGroup viewGroup3 = this.parent;
            ViewGroup viewGroup4 = viewGroup3;
            if (viewGroup3 == null) {
                viewGroup4 = null;
            }
            controlsUiController.show(viewGroup4, new Runnable() { // from class: com.android.systemui.controls.ui.ControlsActivity$onStart$2
                @Override // java.lang.Runnable
                public final void run() {
                    ControlsActivity.this.finishOrReturnToDream();
                }
            }, this);
        } else {
            this.controlsSettingsDialogManager.maybeShowDialog(this, new Function0<Unit>() { // from class: com.android.systemui.controls.ui.ControlsActivity$onStart$1
                {
                    super(0);
                }

                public /* bridge */ /* synthetic */ Object invoke() {
                    m1871invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: collision with other method in class */
                public final void m1871invoke() {
                    ControlsUiController access$getUiController$p = ControlsActivity.access$getUiController$p(ControlsActivity.this);
                    ViewGroup access$getParent$p = ControlsActivity.access$getParent$p(ControlsActivity.this);
                    ViewGroup viewGroup5 = access$getParent$p;
                    if (access$getParent$p == null) {
                        viewGroup5 = null;
                    }
                    final ControlsActivity controlsActivity = ControlsActivity.this;
                    access$getUiController$p.show(viewGroup5, new Runnable() { // from class: com.android.systemui.controls.ui.ControlsActivity$onStart$1.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ControlsActivity.this.finishOrReturnToDream();
                        }
                    }, ControlsActivity.this);
                }
            });
        }
        ControlsAnimations controlsAnimations = ControlsAnimations.INSTANCE;
        ViewGroup viewGroup5 = this.parent;
        if (viewGroup5 == null) {
            viewGroup5 = null;
        }
        controlsAnimations.enterAnimation(viewGroup5).start();
    }

    @Override // android.app.Activity
    public void onStop() {
        super.onStop();
        this.mExitToDream = false;
        this.uiController.hide();
        this.controlsSettingsDialogManager.closeDialog();
    }
}