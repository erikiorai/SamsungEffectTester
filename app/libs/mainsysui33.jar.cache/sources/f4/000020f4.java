package com.android.systemui.qs;

import android.content.ComponentName;
import android.content.res.Configuration;
import android.metrics.LogMaker;
import android.util.Log;
import android.view.View;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.ui.MediaHost;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QSPanel;
import com.android.systemui.qs.QSPanelControllerBase;
import com.android.systemui.qs.customize.QSCustomizerController;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileViewImpl;
import com.android.systemui.util.LargeScreenUtils;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.animation.DisappearParameters;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/QSPanelControllerBase.class */
public abstract class QSPanelControllerBase<T extends QSPanel> extends ViewController<T> implements Dumpable {
    public String mCachedSpecs;
    public final DumpManager mDumpManager;
    public final QSTileHost mHost;
    @Configuration.Orientation
    public int mLastOrientation;
    public final MediaHost mMediaHost;
    public final Function1<Boolean, Unit> mMediaHostVisibilityListener;
    public Consumer<Boolean> mMediaVisibilityChangedListener;
    public final MetricsLogger mMetricsLogger;
    @VisibleForTesting
    public final QSPanel.OnConfigurationChangedListener mOnConfigurationChangedListener;
    public final QSHost.Callback mQSHostCallback;
    public final QSLogger mQSLogger;
    public final QSCustomizerController mQsCustomizerController;
    public QSTileRevealController mQsTileRevealController;
    public final ArrayList<TileRecord> mRecords;
    public float mRevealExpansion;
    public boolean mShouldUseSplitNotificationShade;
    public final UiEventLogger mUiEventLogger;
    public boolean mUsingHorizontalLayout;
    public Runnable mUsingHorizontalLayoutChangedListener;
    public final boolean mUsingMediaPlayer;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/QSPanelControllerBase$TileRecord.class */
    public static final class TileRecord {
        public QSTile.Callback callback;
        public QSTile tile;
        public QSTileView tileView;

        public TileRecord(QSTile qSTile, QSTileView qSTileView) {
            this.tile = qSTile;
            this.tileView = qSTileView;
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSPanelControllerBase$$ExternalSyntheticLambda1.invoke(java.lang.Object):java.lang.Object] */
    /* renamed from: $r8$lambda$b-SAS4OwGLYW8QzhsBcLvTgt2pQ */
    public static /* synthetic */ Unit m3766$r8$lambda$bSAS4OwGLYW8QzhsBcLvTgt2pQ(QSPanelControllerBase qSPanelControllerBase, Boolean bool) {
        return qSPanelControllerBase.lambda$new$0(bool);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSPanelControllerBase$$ExternalSyntheticLambda2.apply(java.lang.Object):java.lang.Object] */
    public static /* synthetic */ String $r8$lambda$yucgqQQ1leBGXgslWx18KV4BlMA(TileRecord tileRecord) {
        return lambda$getTilesSpecs$1(tileRecord);
    }

    public QSPanelControllerBase(T t, QSTileHost qSTileHost, QSCustomizerController qSCustomizerController, boolean z, MediaHost mediaHost, MetricsLogger metricsLogger, UiEventLogger uiEventLogger, QSLogger qSLogger, DumpManager dumpManager) {
        super(t);
        this.mRecords = new ArrayList<>();
        this.mCachedSpecs = "";
        this.mQSHostCallback = new QSHost.Callback() { // from class: com.android.systemui.qs.QSPanelControllerBase$$ExternalSyntheticLambda0
            @Override // com.android.systemui.qs.QSHost.Callback
            public final void onTilesChanged() {
                QSPanelControllerBase.this.setTiles();
            }
        };
        this.mOnConfigurationChangedListener = new QSPanel.OnConfigurationChangedListener() { // from class: com.android.systemui.qs.QSPanelControllerBase.1
            {
                QSPanelControllerBase.this = this;
            }

            @Override // com.android.systemui.qs.QSPanel.OnConfigurationChangedListener
            public void onConfigurationChange(Configuration configuration) {
                QSPanelControllerBase qSPanelControllerBase = QSPanelControllerBase.this;
                qSPanelControllerBase.mQSLogger.logOnConfigurationChanged(qSPanelControllerBase.mLastOrientation, configuration.orientation, ((QSPanel) ((ViewController) QSPanelControllerBase.this).mView).getDumpableTag());
                QSPanelControllerBase qSPanelControllerBase2 = QSPanelControllerBase.this;
                boolean z2 = qSPanelControllerBase2.mShouldUseSplitNotificationShade;
                qSPanelControllerBase2.mShouldUseSplitNotificationShade = LargeScreenUtils.shouldUseSplitNotificationShade(qSPanelControllerBase2.getResources());
                QSPanelControllerBase.this.mLastOrientation = configuration.orientation;
                QSPanelControllerBase.this.switchTileLayoutIfNeeded();
                QSPanelControllerBase.this.onConfigurationChanged();
                QSPanelControllerBase qSPanelControllerBase3 = QSPanelControllerBase.this;
                if (z2 != qSPanelControllerBase3.mShouldUseSplitNotificationShade) {
                    qSPanelControllerBase3.onSplitShadeChanged();
                }
            }
        };
        this.mMediaHostVisibilityListener = new Function1() { // from class: com.android.systemui.qs.QSPanelControllerBase$$ExternalSyntheticLambda1
            public final Object invoke(Object obj) {
                return QSPanelControllerBase.m3766$r8$lambda$bSAS4OwGLYW8QzhsBcLvTgt2pQ(QSPanelControllerBase.this, (Boolean) obj);
            }
        };
        this.mHost = qSTileHost;
        this.mQsCustomizerController = qSCustomizerController;
        this.mUsingMediaPlayer = z;
        this.mMediaHost = mediaHost;
        this.mMetricsLogger = metricsLogger;
        this.mUiEventLogger = uiEventLogger;
        this.mQSLogger = qSLogger;
        this.mDumpManager = dumpManager;
        this.mShouldUseSplitNotificationShade = LargeScreenUtils.shouldUseSplitNotificationShade(getResources());
    }

    public static /* synthetic */ String lambda$getTilesSpecs$1(TileRecord tileRecord) {
        return tileRecord.tile.getTileSpec();
    }

    public /* synthetic */ Unit lambda$new$0(Boolean bool) {
        Consumer<Boolean> consumer = this.mMediaVisibilityChangedListener;
        if (consumer != null) {
            consumer.accept(bool);
        }
        switchTileLayout(false);
        return null;
    }

    public final void addTile(QSTile qSTile, boolean z) {
        TileRecord tileRecord = new TileRecord(qSTile, this.mHost.createTileView(getContext(), qSTile, z));
        try {
            QSTileViewImpl qSTileViewImpl = (QSTileViewImpl) tileRecord.tileView;
            if (qSTileViewImpl != null) {
                qSTileViewImpl.setQsLogger(this.mQSLogger);
            }
        } catch (ClassCastException e) {
            Log.e("QSPanelControllerBase", "Failed to cast QSTileView to QSTileViewImpl", e);
        }
        ((QSPanel) ((ViewController) this).mView).addTile(tileRecord);
        this.mRecords.add(tileRecord);
        this.mCachedSpecs = getTilesSpecs();
    }

    public boolean areThereTiles() {
        return !this.mRecords.isEmpty();
    }

    public void clickTile(ComponentName componentName) {
        String spec = CustomTile.toSpec(componentName);
        Iterator<TileRecord> it = this.mRecords.iterator();
        while (it.hasNext()) {
            TileRecord next = it.next();
            if (next.tile.getTileSpec().equals(spec)) {
                next.tile.click(null);
                return;
            }
        }
    }

    public void closeDetail() {
        if (this.mQsCustomizerController.isShown()) {
            this.mQsCustomizerController.hide();
        }
    }

    public QSTileRevealController createTileRevealController() {
        return null;
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println(getClass().getSimpleName() + ":");
        printWriter.println("  Tile records:");
        Iterator<TileRecord> it = this.mRecords.iterator();
        while (it.hasNext()) {
            TileRecord next = it.next();
            if (next.tile instanceof Dumpable) {
                printWriter.print("    ");
                ((Dumpable) next.tile).dump(printWriter, strArr);
                printWriter.print("    ");
                printWriter.println(next.tileView.toString());
            }
        }
        if (this.mMediaHost != null) {
            printWriter.println("  media bounds: " + this.mMediaHost.getCurrentBounds());
            printWriter.println("  horizontal layout: " + this.mUsingHorizontalLayout);
            printWriter.println("  last orientation: " + this.mLastOrientation);
        }
        printWriter.println("  mShouldUseSplitNotificationShade: " + this.mShouldUseSplitNotificationShade);
    }

    public View getBrightnessView() {
        return ((QSPanel) ((ViewController) this).mView).getBrightnessView();
    }

    public MediaHost getMediaHost() {
        return this.mMediaHost;
    }

    public QSPanel.QSTileLayout getTileLayout() {
        return ((QSPanel) ((ViewController) this).mView).getTileLayout();
    }

    public QSTileView getTileView(QSTile qSTile) {
        Iterator<TileRecord> it = this.mRecords.iterator();
        while (it.hasNext()) {
            TileRecord next = it.next();
            if (next.tile == qSTile) {
                return next.tileView;
            }
        }
        return null;
    }

    public QSTileView getTileView(String str) {
        Iterator<TileRecord> it = this.mRecords.iterator();
        while (it.hasNext()) {
            TileRecord next = it.next();
            if (Objects.equals(next.tile.getTileSpec(), str)) {
                return next.tileView;
            }
        }
        return null;
    }

    public final String getTilesSpecs() {
        return (String) this.mRecords.stream().map(new Function() { // from class: com.android.systemui.qs.QSPanelControllerBase$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return QSPanelControllerBase.$r8$lambda$yucgqQQ1leBGXgslWx18KV4BlMA((QSPanelControllerBase.TileRecord) obj);
            }
        }).collect(Collectors.joining(","));
    }

    public final void logTiles() {
        for (int i = 0; i < this.mRecords.size(); i++) {
            QSTile qSTile = this.mRecords.get(i).tile;
            this.mMetricsLogger.write(qSTile.populate(new LogMaker(qSTile.getMetricsCategory()).setType(1)));
        }
    }

    public void onConfigurationChanged() {
    }

    public void onInit() {
        ((QSPanel) ((ViewController) this).mView).initialize(this.mQSLogger);
        this.mQSLogger.logAllTilesChangeListening(((QSPanel) ((ViewController) this).mView).isListening(), ((QSPanel) ((ViewController) this).mView).getDumpableTag(), "");
    }

    public void onSplitShadeChanged() {
    }

    public void onViewAttached() {
        QSTileRevealController createTileRevealController = createTileRevealController();
        this.mQsTileRevealController = createTileRevealController;
        if (createTileRevealController != null) {
            createTileRevealController.setExpansion(this.mRevealExpansion);
        }
        this.mMediaHost.addVisibilityChangeListener(this.mMediaHostVisibilityListener);
        ((QSPanel) ((ViewController) this).mView).addOnConfigurationChangedListener(this.mOnConfigurationChangedListener);
        this.mHost.addCallback(this.mQSHostCallback);
        setTiles();
        int i = getResources().getConfiguration().orientation;
        this.mLastOrientation = i;
        this.mQSLogger.logOnViewAttached(i, ((QSPanel) ((ViewController) this).mView).getDumpableTag());
        switchTileLayout(true);
        this.mDumpManager.registerDumpable(((QSPanel) ((ViewController) this).mView).getDumpableTag(), this);
    }

    public void onViewDetached() {
        this.mQSLogger.logOnViewDetached(this.mLastOrientation, ((QSPanel) ((ViewController) this).mView).getDumpableTag());
        ((QSPanel) ((ViewController) this).mView).removeOnConfigurationChangedListener(this.mOnConfigurationChangedListener);
        this.mHost.removeCallback(this.mQSHostCallback);
        ((QSPanel) ((ViewController) this).mView).getTileLayout().setListening(false, this.mUiEventLogger);
        this.mMediaHost.removeVisibilityChangeListener(this.mMediaHostVisibilityListener);
        Iterator<TileRecord> it = this.mRecords.iterator();
        while (it.hasNext()) {
            it.next().tile.removeCallbacks();
        }
        this.mRecords.clear();
        this.mDumpManager.unregisterDumpable(((QSPanel) ((ViewController) this).mView).getDumpableTag());
    }

    public void refreshAllTiles() {
        Iterator<TileRecord> it = this.mRecords.iterator();
        while (it.hasNext()) {
            TileRecord next = it.next();
            if (!next.tile.isListening()) {
                next.tile.refreshState();
            }
        }
    }

    public void setCollapseExpandAction(Runnable runnable) {
        ((QSPanel) ((ViewController) this).mView).setCollapseExpandAction(runnable);
    }

    public void setExpanded(boolean z) {
        if (((QSPanel) ((ViewController) this).mView).isExpanded() == z) {
            return;
        }
        this.mQSLogger.logPanelExpanded(z, ((QSPanel) ((ViewController) this).mView).getDumpableTag());
        ((QSPanel) ((ViewController) this).mView).setExpanded(z);
        this.mMetricsLogger.visibility(111, z);
        if (z) {
            this.mUiEventLogger.log(((QSPanel) ((ViewController) this).mView).openPanelEvent());
            logTiles();
            return;
        }
        this.mUiEventLogger.log(((QSPanel) ((ViewController) this).mView).closePanelEvent());
        closeDetail();
    }

    public void setIsOnKeyguard(boolean z) {
        ((QSPanel) ((ViewController) this).mView).setShouldMoveMediaOnExpansion(!(this.mShouldUseSplitNotificationShade && z));
    }

    public void setListening(boolean z) {
        if (((QSPanel) ((ViewController) this).mView).isListening() == z) {
            return;
        }
        ((QSPanel) ((ViewController) this).mView).setListening(z);
        if (((QSPanel) ((ViewController) this).mView).getTileLayout() != null) {
            this.mQSLogger.logAllTilesChangeListening(z, ((QSPanel) ((ViewController) this).mView).getDumpableTag(), this.mCachedSpecs);
            ((QSPanel) ((ViewController) this).mView).getTileLayout().setListening(z, this.mUiEventLogger);
        }
        if (((QSPanel) ((ViewController) this).mView).isListening()) {
            refreshAllTiles();
        }
    }

    public void setMediaVisibilityChangedListener(Consumer<Boolean> consumer) {
        this.mMediaVisibilityChangedListener = consumer;
    }

    public void setRevealExpansion(float f) {
        this.mRevealExpansion = f;
        QSTileRevealController qSTileRevealController = this.mQsTileRevealController;
        if (qSTileRevealController != null) {
            qSTileRevealController.setExpansion(f);
        }
    }

    public void setSquishinessFraction(float f) {
        ((QSPanel) ((ViewController) this).mView).setSquishinessFraction(f);
    }

    public void setTiles() {
        setTiles(this.mHost.getTiles(), false);
    }

    public void setTiles(Collection<QSTile> collection, boolean z) {
        QSTileRevealController qSTileRevealController;
        if (!z && (qSTileRevealController = this.mQsTileRevealController) != null) {
            qSTileRevealController.updateRevealedTiles(collection);
        }
        Iterator<TileRecord> it = this.mRecords.iterator();
        while (it.hasNext()) {
            TileRecord next = it.next();
            ((QSPanel) ((ViewController) this).mView).removeTile(next);
            next.tile.removeCallback(next.callback);
        }
        this.mRecords.clear();
        this.mCachedSpecs = "";
        for (QSTile qSTile : collection) {
            addTile(qSTile, z);
        }
    }

    public void setUsingHorizontalLayoutChangeListener(Runnable runnable) {
        this.mUsingHorizontalLayoutChangedListener = runnable;
    }

    public boolean shouldUseHorizontalLayout() {
        if (this.mShouldUseSplitNotificationShade) {
            return false;
        }
        boolean z = false;
        if (this.mUsingMediaPlayer) {
            z = false;
            if (this.mMediaHost.getVisible()) {
                z = false;
                if (this.mLastOrientation == 2) {
                    z = true;
                }
            }
        }
        return z;
    }

    public boolean switchTileLayout(boolean z) {
        boolean shouldUseHorizontalLayout = shouldUseHorizontalLayout();
        boolean z2 = this.mUsingHorizontalLayout;
        if (shouldUseHorizontalLayout != z2 || z) {
            this.mQSLogger.logSwitchTileLayout(shouldUseHorizontalLayout, z2, z, ((QSPanel) ((ViewController) this).mView).getDumpableTag());
            this.mUsingHorizontalLayout = shouldUseHorizontalLayout;
            ((QSPanel) ((ViewController) this).mView).setUsingHorizontalLayout(shouldUseHorizontalLayout, this.mMediaHost.getHostView(), z);
            updateMediaDisappearParameters();
            Runnable runnable = this.mUsingHorizontalLayoutChangedListener;
            if (runnable != null) {
                runnable.run();
                return true;
            }
            return true;
        }
        return false;
    }

    public final void switchTileLayoutIfNeeded() {
        switchTileLayout(false);
    }

    public void updateMediaDisappearParameters() {
        if (this.mUsingMediaPlayer) {
            DisappearParameters disappearParameters = this.mMediaHost.getDisappearParameters();
            if (this.mUsingHorizontalLayout) {
                disappearParameters.getDisappearSize().set(ActionBarShadowController.ELEVATION_LOW, 0.4f);
                disappearParameters.getGonePivot().set(1.0f, 1.0f);
                disappearParameters.getContentTranslationFraction().set(0.25f, 1.0f);
                disappearParameters.setDisappearEnd(0.6f);
            } else {
                disappearParameters.getDisappearSize().set(1.0f, ActionBarShadowController.ELEVATION_LOW);
                disappearParameters.getGonePivot().set(ActionBarShadowController.ELEVATION_LOW, 1.0f);
                disappearParameters.getContentTranslationFraction().set(ActionBarShadowController.ELEVATION_LOW, 1.05f);
                disappearParameters.setDisappearEnd(0.95f);
            }
            disappearParameters.setFadeStartPosition(0.95f);
            disappearParameters.setDisappearStart((float) ActionBarShadowController.ELEVATION_LOW);
            this.mMediaHost.setDisappearParameters(disappearParameters);
        }
    }
}