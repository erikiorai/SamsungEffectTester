package com.android.systemui.controls.management;

import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;
import androidx.activity.ComponentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.controls.CustomIconCache;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.StructureInfo;
import com.android.systemui.controls.management.FavoritesModel;
import com.android.systemui.controls.ui.ControlsActivity;
import com.android.systemui.controls.ui.ControlsUiController;
import com.android.systemui.settings.UserTracker;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlsEditingActivity.class */
public class ControlsEditingActivity extends ComponentActivity {
    public ComponentName component;
    public final ControlsControllerImpl controller;
    public final CustomIconCache customIconCache;
    public final Executor mainExecutor;
    public FavoritesModel model;
    public View saveButton;
    public CharSequence structure;
    public TextView subtitle;
    public final ControlsUiController uiController;
    public final UserTracker userTracker;
    public static final Companion Companion = new Companion(null);
    public static final int SUBTITLE_ID = R$string.controls_favorite_rearrange;
    public static final int EMPTY_TEXT_ID = R$string.controls_favorite_removed;
    public final UserTracker.Callback userTrackerCallback = new UserTracker.Callback() { // from class: com.android.systemui.controls.management.ControlsEditingActivity$userTrackerCallback$1
        public final int startingUser;

        {
            this.startingUser = ControlsEditingActivity.access$getController$p(ControlsEditingActivity.this).getCurrentUserId();
        }

        public void onUserChanged(int i, Context context) {
            if (i != this.startingUser) {
                ControlsEditingActivity.access$getUserTracker$p(ControlsEditingActivity.this).removeCallback(this);
                ControlsEditingActivity.this.finish();
            }
        }
    };
    public final OnBackInvokedCallback mOnBackInvokedCallback = new OnBackInvokedCallback() { // from class: com.android.systemui.controls.management.ControlsEditingActivity$mOnBackInvokedCallback$1
        public final void onBackInvoked() {
            ControlsEditingActivity.this.onBackPressed();
        }
    };
    public final ControlsEditingActivity$favoritesModelCallback$1 favoritesModelCallback = new FavoritesModel.FavoritesModelCallback() { // from class: com.android.systemui.controls.management.ControlsEditingActivity$favoritesModelCallback$1
        @Override // com.android.systemui.controls.management.ControlsModel.ControlsModelCallback
        public void onFirstChange() {
            View access$getSaveButton$p = ControlsEditingActivity.access$getSaveButton$p(ControlsEditingActivity.this);
            View view = access$getSaveButton$p;
            if (access$getSaveButton$p == null) {
                view = null;
            }
            view.setEnabled(true);
        }

        @Override // com.android.systemui.controls.management.FavoritesModel.FavoritesModelCallback
        public void onNoneChanged(boolean z) {
            TextView textView = null;
            if (z) {
                TextView access$getSubtitle$p = ControlsEditingActivity.access$getSubtitle$p(ControlsEditingActivity.this);
                if (access$getSubtitle$p != null) {
                    textView = access$getSubtitle$p;
                }
                textView.setText(ControlsEditingActivity.access$getEMPTY_TEXT_ID$cp());
                return;
            }
            TextView access$getSubtitle$p2 = ControlsEditingActivity.access$getSubtitle$p(ControlsEditingActivity.this);
            if (access$getSubtitle$p2 == null) {
                access$getSubtitle$p2 = null;
            }
            access$getSubtitle$p2.setText(ControlsEditingActivity.access$getSUBTITLE_ID$cp());
        }
    };

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlsEditingActivity$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.systemui.controls.management.ControlsEditingActivity$favoritesModelCallback$1] */
    public ControlsEditingActivity(Executor executor, ControlsControllerImpl controlsControllerImpl, UserTracker userTracker, CustomIconCache customIconCache, ControlsUiController controlsUiController) {
        this.mainExecutor = executor;
        this.controller = controlsControllerImpl;
        this.userTracker = userTracker;
        this.customIconCache = customIconCache;
        this.uiController = controlsUiController;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsEditingActivity$bindButtons$1$1.onClick(android.view.View):void] */
    public static final /* synthetic */ void access$animateExitAndFinish(ControlsEditingActivity controlsEditingActivity) {
        controlsEditingActivity.animateExitAndFinish();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsEditingActivity$userTrackerCallback$1.<init>(com.android.systemui.controls.management.ControlsEditingActivity):void] */
    public static final /* synthetic */ ControlsControllerImpl access$getController$p(ControlsEditingActivity controlsEditingActivity) {
        return controlsEditingActivity.controller;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsEditingActivity$favoritesModelCallback$1.onNoneChanged(boolean):void] */
    public static final /* synthetic */ int access$getEMPTY_TEXT_ID$cp() {
        return EMPTY_TEXT_ID;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsEditingActivity$favoritesModelCallback$1.onNoneChanged(boolean):void] */
    public static final /* synthetic */ int access$getSUBTITLE_ID$cp() {
        return SUBTITLE_ID;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsEditingActivity$favoritesModelCallback$1.onFirstChange():void] */
    public static final /* synthetic */ View access$getSaveButton$p(ControlsEditingActivity controlsEditingActivity) {
        return controlsEditingActivity.saveButton;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsEditingActivity$favoritesModelCallback$1.onNoneChanged(boolean):void] */
    public static final /* synthetic */ TextView access$getSubtitle$p(ControlsEditingActivity controlsEditingActivity) {
        return controlsEditingActivity.subtitle;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsEditingActivity$userTrackerCallback$1.onUserChanged(int, android.content.Context):void] */
    public static final /* synthetic */ UserTracker access$getUserTracker$p(ControlsEditingActivity controlsEditingActivity) {
        return controlsEditingActivity.userTracker;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.controls.management.ControlsEditingActivity$bindButtons$1$1.onClick(android.view.View):void] */
    public static final /* synthetic */ void access$saveFavorites(ControlsEditingActivity controlsEditingActivity) {
        controlsEditingActivity.saveFavorites();
    }

    public final void animateExitAndFinish() {
        ControlsAnimations.exitAnimation((ViewGroup) requireViewById(R$id.controls_management_root), new Runnable() { // from class: com.android.systemui.controls.management.ControlsEditingActivity$animateExitAndFinish$1
            @Override // java.lang.Runnable
            public void run() {
                ControlsEditingActivity.this.finish();
            }
        }).start();
    }

    public final void bindButtons() {
        View requireViewById = requireViewById(R$id.done);
        Button button = (Button) requireViewById;
        button.setEnabled(false);
        button.setText(R$string.save);
        button.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.management.ControlsEditingActivity$bindButtons$1$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ControlsEditingActivity.access$saveFavorites(ControlsEditingActivity.this);
                ControlsEditingActivity.this.startActivity(new Intent(ControlsEditingActivity.this.getApplicationContext(), ControlsActivity.class), ActivityOptions.makeSceneTransitionAnimation(ControlsEditingActivity.this, new Pair[0]).toBundle());
                ControlsEditingActivity.access$animateExitAndFinish(ControlsEditingActivity.this);
            }
        });
        this.saveButton = requireViewById;
    }

    public final void bindViews() {
        setContentView(R$layout.controls_management);
        getLifecycle().addObserver(ControlsAnimations.observerForAnimations$default(ControlsAnimations.INSTANCE, (ViewGroup) requireViewById(R$id.controls_management_root), getWindow(), getIntent(), false, 8, null));
        ViewStub viewStub = (ViewStub) requireViewById(R$id.stub);
        viewStub.setLayoutResource(R$layout.controls_management_editing);
        viewStub.inflate();
        TextView textView = (TextView) requireViewById(R$id.title);
        CharSequence charSequence = this.structure;
        CharSequence charSequence2 = charSequence;
        if (charSequence == null) {
            charSequence2 = null;
        }
        textView.setText(charSequence2);
        CharSequence charSequence3 = this.structure;
        if (charSequence3 == null) {
            charSequence3 = null;
        }
        setTitle(charSequence3);
        TextView textView2 = (TextView) requireViewById(R$id.subtitle);
        textView2.setText(SUBTITLE_ID);
        this.subtitle = textView2;
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        animateExitAndFinish();
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        Unit unit;
        super.onCreate(bundle);
        ComponentName componentName = (ComponentName) getIntent().getParcelableExtra("android.intent.extra.COMPONENT_NAME");
        if (componentName != null) {
            this.component = componentName;
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            finish();
        }
        CharSequence charSequenceExtra = getIntent().getCharSequenceExtra("extra_structure");
        Unit unit2 = null;
        if (charSequenceExtra != null) {
            this.structure = charSequenceExtra;
            unit2 = Unit.INSTANCE;
        }
        if (unit2 == null) {
            finish();
        }
        bindViews();
        bindButtons();
    }

    @Override // android.app.Activity
    public void onDestroy() {
        this.userTracker.removeCallback(this.userTrackerCallback);
        super.onDestroy();
    }

    @Override // android.app.Activity
    public void onStart() {
        super.onStart();
        setUpList();
        this.userTracker.addCallback(this.userTrackerCallback, this.mainExecutor);
        getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, this.mOnBackInvokedCallback);
    }

    @Override // android.app.Activity
    public void onStop() {
        super.onStop();
        this.userTracker.removeCallback(this.userTrackerCallback);
        getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(this.mOnBackInvokedCallback);
    }

    public final void saveFavorites() {
        ControlsControllerImpl controlsControllerImpl = this.controller;
        ComponentName componentName = this.component;
        FavoritesModel favoritesModel = null;
        ComponentName componentName2 = componentName;
        if (componentName == null) {
            componentName2 = null;
        }
        CharSequence charSequence = this.structure;
        CharSequence charSequence2 = charSequence;
        if (charSequence == null) {
            charSequence2 = null;
        }
        FavoritesModel favoritesModel2 = this.model;
        if (favoritesModel2 != null) {
            favoritesModel = favoritesModel2;
        }
        controlsControllerImpl.replaceFavoritesForStructure(new StructureInfo(componentName2, charSequence2, favoritesModel.getFavorites()));
    }

    public final void setUpList() {
        ControlsControllerImpl controlsControllerImpl = this.controller;
        ComponentName componentName = this.component;
        ComponentName componentName2 = componentName;
        if (componentName == null) {
            componentName2 = null;
        }
        CharSequence charSequence = this.structure;
        CharSequence charSequence2 = charSequence;
        if (charSequence == null) {
            charSequence2 = null;
        }
        List<ControlInfo> favoritesForStructure = controlsControllerImpl.getFavoritesForStructure(componentName2, charSequence2);
        CustomIconCache customIconCache = this.customIconCache;
        ComponentName componentName3 = this.component;
        ComponentName componentName4 = componentName3;
        if (componentName3 == null) {
            componentName4 = null;
        }
        this.model = new FavoritesModel(customIconCache, componentName4, favoritesForStructure, this.favoritesModelCallback);
        float f = getResources().getFloat(R$dimen.control_card_elevation);
        final RecyclerView recyclerView = (RecyclerView) requireViewById(R$id.list);
        recyclerView.setAlpha(ActionBarShadowController.ELEVATION_LOW);
        final ControlAdapter controlAdapter = new ControlAdapter(f);
        controlAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() { // from class: com.android.systemui.controls.management.ControlsEditingActivity$setUpList$adapter$1$1
            public boolean hasAnimated;

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onChanged() {
                if (this.hasAnimated) {
                    return;
                }
                this.hasAnimated = true;
                ControlsAnimations.INSTANCE.enterAnimation(RecyclerView.this).start();
            }
        });
        int dimensionPixelSize = getResources().getDimensionPixelSize(R$dimen.controls_card_margin);
        MarginItemDecorator marginItemDecorator = new MarginItemDecorator(dimensionPixelSize, dimensionPixelSize);
        final int findMaxColumns = ControlAdapter.Companion.findMaxColumns(getResources());
        recyclerView.setAdapter(controlAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(findMaxColumns, recyclerView.getContext()) { // from class: com.android.systemui.controls.management.ControlsEditingActivity$setUpList$1$1
            @Override // androidx.recyclerview.widget.GridLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public int getRowCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
                int rowCountForAccessibility = super.getRowCountForAccessibility(recycler, state);
                int i = rowCountForAccessibility;
                if (rowCountForAccessibility > 0) {
                    i = rowCountForAccessibility - 1;
                }
                return i;
            }
        };
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.android.systemui.controls.management.ControlsEditingActivity$setUpList$1$2$1
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int i) {
                ControlAdapter controlAdapter2 = ControlAdapter.this;
                boolean z = false;
                if (controlAdapter2 != null) {
                    z = false;
                    if (controlAdapter2.getItemViewType(i) == 1) {
                        z = true;
                    }
                }
                int i2 = 1;
                if (!z) {
                    i2 = findMaxColumns;
                }
                return i2;
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(marginItemDecorator);
        FavoritesModel favoritesModel = this.model;
        FavoritesModel favoritesModel2 = favoritesModel;
        if (favoritesModel == null) {
            favoritesModel2 = null;
        }
        controlAdapter.changeModel(favoritesModel2);
        FavoritesModel favoritesModel3 = this.model;
        FavoritesModel favoritesModel4 = favoritesModel3;
        if (favoritesModel3 == null) {
            favoritesModel4 = null;
        }
        favoritesModel4.attachAdapter(controlAdapter);
        FavoritesModel favoritesModel5 = this.model;
        if (favoritesModel5 == null) {
            favoritesModel5 = null;
        }
        new ItemTouchHelper(favoritesModel5.getItemTouchHelperCallback()).attachToRecyclerView(recyclerView);
    }
}