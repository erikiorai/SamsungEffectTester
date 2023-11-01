package com.android.systemui.qs.customize;

import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R$dimen;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.plugins.qs.QSContainerController;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSEditEvent;
import com.android.systemui.qs.QSFragment;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.ViewController;
import java.util.ArrayList;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/customize/QSCustomizerController.class */
public class QSCustomizerController extends ViewController<QSCustomizer> {
    public final ConfigurationController mConfigurationController;
    public final ConfigurationController.ConfigurationListener mConfigurationListener;
    public final KeyguardStateController.Callback mKeyguardCallback;
    public final KeyguardStateController mKeyguardStateController;
    public final LightBarController mLightBarController;
    public final Toolbar.OnMenuItemClickListener mOnMenuItemClickListener;
    public final QSTileHost mQsTileHost;
    public final ScreenLifecycle mScreenLifecycle;
    public final TileAdapter mTileAdapter;
    public final TileQueryHelper mTileQueryHelper;
    public final Toolbar mToolbar;
    public final UiEventLogger mUiEventLogger;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.customize.QSCustomizerController$$ExternalSyntheticLambda0.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$RZvIGkw5K4FX4bWXXGUAKKE01uc(QSCustomizerController qSCustomizerController, View view) {
        qSCustomizerController.lambda$onViewAttached$0(view);
    }

    public QSCustomizerController(QSCustomizer qSCustomizer, TileQueryHelper tileQueryHelper, QSTileHost qSTileHost, TileAdapter tileAdapter, ScreenLifecycle screenLifecycle, KeyguardStateController keyguardStateController, LightBarController lightBarController, ConfigurationController configurationController, UiEventLogger uiEventLogger) {
        super(qSCustomizer);
        this.mOnMenuItemClickListener = new Toolbar.OnMenuItemClickListener() { // from class: com.android.systemui.qs.customize.QSCustomizerController.1
            {
                QSCustomizerController.this = this;
            }

            @Override // android.widget.Toolbar.OnMenuItemClickListener
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == 1) {
                    QSCustomizerController.this.mUiEventLogger.log(QSEditEvent.QS_EDIT_RESET);
                    QSCustomizerController.this.reset();
                    return false;
                }
                return false;
            }
        };
        this.mKeyguardCallback = new KeyguardStateController.Callback() { // from class: com.android.systemui.qs.customize.QSCustomizerController.2
            {
                QSCustomizerController.this = this;
            }

            public void onKeyguardShowingChanged() {
                if (((QSCustomizer) ((ViewController) QSCustomizerController.this).mView).isAttachedToWindow() && QSCustomizerController.this.mKeyguardStateController.isShowing() && !((QSCustomizer) ((ViewController) QSCustomizerController.this).mView).isOpening()) {
                    QSCustomizerController.this.hide();
                }
            }
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.qs.customize.QSCustomizerController.3
            {
                QSCustomizerController.this = this;
            }

            public void onConfigChanged(Configuration configuration) {
                ((QSCustomizer) ((ViewController) QSCustomizerController.this).mView).updateNavBackDrop(configuration, QSCustomizerController.this.mLightBarController);
                ((QSCustomizer) ((ViewController) QSCustomizerController.this).mView).updateResources();
                if (QSCustomizerController.this.mTileAdapter.updateNumColumns()) {
                    RecyclerView.LayoutManager layoutManager = ((QSCustomizer) ((ViewController) QSCustomizerController.this).mView).getRecyclerView().getLayoutManager();
                    if (layoutManager instanceof GridLayoutManager) {
                        ((GridLayoutManager) layoutManager).setSpanCount(QSCustomizerController.this.mTileAdapter.getNumColumns());
                    }
                }
            }
        };
        this.mTileQueryHelper = tileQueryHelper;
        this.mQsTileHost = qSTileHost;
        this.mTileAdapter = tileAdapter;
        this.mScreenLifecycle = screenLifecycle;
        this.mKeyguardStateController = keyguardStateController;
        this.mLightBarController = lightBarController;
        this.mConfigurationController = configurationController;
        this.mUiEventLogger = uiEventLogger;
        this.mToolbar = (Toolbar) ((QSCustomizer) ((ViewController) this).mView).findViewById(16908731);
    }

    public /* synthetic */ void lambda$onViewAttached$0(View view) {
        hide();
    }

    public void hide() {
        boolean z = this.mScreenLifecycle.getScreenState() != 0;
        if (((QSCustomizer) ((ViewController) this).mView).isShown()) {
            this.mUiEventLogger.log(QSEditEvent.QS_EDIT_CLOSED);
            this.mToolbar.dismissPopupMenus();
            ((QSCustomizer) ((ViewController) this).mView).setCustomizing(false);
            save();
            ((QSCustomizer) ((ViewController) this).mView).hide(z);
            ((QSCustomizer) ((ViewController) this).mView).updateNavColors(this.mLightBarController);
            this.mKeyguardStateController.removeCallback(this.mKeyguardCallback);
        }
    }

    public boolean isCustomizing() {
        return ((QSCustomizer) ((ViewController) this).mView).isCustomizing();
    }

    public boolean isShown() {
        return ((QSCustomizer) ((ViewController) this).mView).isShown();
    }

    public void onViewAttached() {
        ((QSCustomizer) ((ViewController) this).mView).updateNavBackDrop(getResources().getConfiguration(), this.mLightBarController);
        this.mConfigurationController.addCallback(this.mConfigurationListener);
        this.mTileQueryHelper.setListener(this.mTileAdapter);
        this.mTileAdapter.changeHalfMargin(getResources().getDimensionPixelSize(R$dimen.qs_tile_margin_horizontal) / 2);
        final RecyclerView recyclerView = ((QSCustomizer) ((ViewController) this).mView).getRecyclerView();
        recyclerView.setAdapter(this.mTileAdapter);
        this.mTileAdapter.getItemTouchHelper().attachToRecyclerView(recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), this.mTileAdapter.getNumColumns()) { // from class: com.android.systemui.qs.customize.QSCustomizerController.4
            {
                QSCustomizerController.this = this;
            }

            @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
            public void calculateItemDecorationsForChild(View view, Rect rect) {
                if (view instanceof TextView) {
                    return;
                }
                rect.setEmpty();
                QSCustomizerController.this.mTileAdapter.getMarginItemDecoration().getItemOffsets(rect, view, recyclerView, new RecyclerView.State());
                ((ViewGroup.MarginLayoutParams) ((GridLayoutManager.LayoutParams) view.getLayoutParams())).leftMargin = rect.left;
                ((ViewGroup.MarginLayoutParams) ((GridLayoutManager.LayoutParams) view.getLayoutParams())).rightMargin = rect.right;
            }

            @Override // androidx.recyclerview.widget.GridLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler recycler, RecyclerView.State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            }
        };
        gridLayoutManager.setSpanSizeLookup(this.mTileAdapter.getSizeLookup());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(this.mTileAdapter.getItemDecoration());
        recyclerView.addItemDecoration(this.mTileAdapter.getMarginItemDecoration());
        this.mToolbar.setOnMenuItemClickListener(this.mOnMenuItemClickListener);
        this.mToolbar.setNavigationOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.customize.QSCustomizerController$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QSCustomizerController.$r8$lambda$RZvIGkw5K4FX4bWXXGUAKKE01uc(QSCustomizerController.this, view);
            }
        });
    }

    public void onViewDetached() {
        this.mTileQueryHelper.setListener(null);
        this.mToolbar.setOnMenuItemClickListener(null);
        this.mConfigurationController.removeCallback(this.mConfigurationListener);
    }

    public final void reset() {
        this.mTileAdapter.resetTileSpecs(QSTileHost.getDefaultSpecs(getContext()));
    }

    public void restoreInstanceState(Bundle bundle) {
        if (bundle.getBoolean("qs_customizing")) {
            ((QSCustomizer) ((ViewController) this).mView).setVisibility(0);
            ((QSCustomizer) ((ViewController) this).mView).addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.qs.customize.QSCustomizerController.5
                {
                    QSCustomizerController.this = this;
                }

                @Override // android.view.View.OnLayoutChangeListener
                public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    ((QSCustomizer) ((ViewController) QSCustomizerController.this).mView).removeOnLayoutChangeListener(this);
                    QSCustomizerController.this.show(0, 0, true);
                }
            });
        }
    }

    public final void save() {
        if (this.mTileQueryHelper.isFinished()) {
            this.mTileAdapter.saveSpecs(this.mQsTileHost);
        }
    }

    public void saveInstanceState(Bundle bundle) {
        if (((QSCustomizer) ((ViewController) this).mView).isShown()) {
            this.mKeyguardStateController.removeCallback(this.mKeyguardCallback);
        }
        bundle.putBoolean("qs_customizing", ((QSCustomizer) ((ViewController) this).mView).isCustomizing());
    }

    public void setContainerController(QSContainerController qSContainerController) {
        ((QSCustomizer) ((ViewController) this).mView).setContainerController(qSContainerController);
    }

    public void setEditLocation(int i, int i2) {
        ((QSCustomizer) ((ViewController) this).mView).setEditLocation(i, i2);
    }

    public void setQs(QSFragment qSFragment) {
        ((QSCustomizer) ((ViewController) this).mView).setQs(qSFragment);
    }

    public final void setTileSpecs() {
        ArrayList arrayList = new ArrayList();
        for (QSTile qSTile : this.mQsTileHost.getTiles()) {
            arrayList.add(qSTile.getTileSpec());
        }
        this.mTileAdapter.setTileSpecs(arrayList);
    }

    public void show(int i, int i2, boolean z) {
        if (((QSCustomizer) ((ViewController) this).mView).isShown()) {
            return;
        }
        setTileSpecs();
        if (z) {
            ((QSCustomizer) ((ViewController) this).mView).showImmediately();
        } else {
            ((QSCustomizer) ((ViewController) this).mView).show(i, i2, this.mTileAdapter);
            this.mUiEventLogger.log(QSEditEvent.QS_EDIT_OPEN);
        }
        this.mTileQueryHelper.queryTiles(this.mQsTileHost);
        this.mKeyguardStateController.addCallback(this.mKeyguardCallback);
        ((QSCustomizer) ((ViewController) this).mView).updateNavColors(this.mLightBarController);
    }
}