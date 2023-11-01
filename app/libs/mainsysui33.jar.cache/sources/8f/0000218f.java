package com.android.systemui.qs.customize;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.Settings;
import android.util.ArraySet;
import android.widget.Button;
import com.android.systemui.R$string;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.qs.customize.TileQueryHelper;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.settings.UserTracker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/customize/TileQueryHelper.class */
public class TileQueryHelper {
    public final Executor mBgExecutor;
    public final Context mContext;
    public boolean mFinished;
    public TileStateListener mListener;
    public final Executor mMainExecutor;
    public final UserTracker mUserTracker;
    public final ArrayList<TileInfo> mTiles = new ArrayList<>();
    public final ArraySet<String> mSpecs = new ArraySet<>();

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/customize/TileQueryHelper$TileCollector.class */
    public class TileCollector implements QSTile.Callback {
        public final QSTileHost mQSTileHost;
        public final List<TilePair> mQSTileList = new ArrayList();

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.customize.TileQueryHelper$TileCollector$$ExternalSyntheticLambda0.run():void] */
        public static /* synthetic */ void $r8$lambda$OxVyAcyugVvsEl1TINlEslrdqg8(TileCollector tileCollector) {
            tileCollector.finished();
        }

        public TileCollector(List<QSTile> list, QSTileHost qSTileHost) {
            TileQueryHelper.this = r6;
            for (QSTile qSTile : list) {
                this.mQSTileList.add(new TilePair(qSTile));
            }
            this.mQSTileHost = qSTileHost;
            if (list.isEmpty()) {
                r6.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.customize.TileQueryHelper$TileCollector$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        TileQueryHelper.TileCollector.$r8$lambda$OxVyAcyugVvsEl1TINlEslrdqg8(TileQueryHelper.TileCollector.this);
                    }
                });
            }
        }

        public final void finished() {
            TileQueryHelper.this.notifyTilesChanged(false);
            TileQueryHelper.this.addPackageTiles(this.mQSTileHost);
        }

        @Override // com.android.systemui.plugins.qs.QSTile.Callback
        public void onStateChanged(QSTile.State state) {
            boolean z = true;
            for (TilePair tilePair : this.mQSTileList) {
                if (!tilePair.mReady && tilePair.mTile.isTileReady()) {
                    tilePair.mTile.removeCallback(this);
                    tilePair.mTile.setListening(this, false);
                    tilePair.mReady = true;
                } else if (!tilePair.mReady) {
                    z = false;
                }
            }
            if (z) {
                for (TilePair tilePair2 : this.mQSTileList) {
                    QSTile qSTile = tilePair2.mTile;
                    QSTile.State copy = qSTile.getState().copy();
                    copy.label = qSTile.getTileLabel();
                    qSTile.destroy();
                    TileQueryHelper.this.addTile(qSTile.getTileSpec(), null, copy, true);
                }
                finished();
            }
        }

        public final void startListening() {
            for (TilePair tilePair : this.mQSTileList) {
                tilePair.mTile.addCallback(this);
                tilePair.mTile.setListening(this, true);
                tilePair.mTile.refreshState();
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/customize/TileQueryHelper$TileInfo.class */
    public static class TileInfo {
        public boolean isSystem;
        public String spec;
        public QSTile.State state;

        public TileInfo(String str, QSTile.State state, boolean z) {
            this.spec = str;
            this.state = state;
            this.isSystem = z;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/customize/TileQueryHelper$TilePair.class */
    public static class TilePair {
        public boolean mReady;
        public QSTile mTile;

        public TilePair(QSTile qSTile) {
            this.mReady = false;
            this.mTile = qSTile;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/customize/TileQueryHelper$TileStateListener.class */
    public interface TileStateListener {
        void onTilesChanged(List<TileInfo> list);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.customize.TileQueryHelper$$ExternalSyntheticLambda1.run():void] */
    public static /* synthetic */ void $r8$lambda$cY1pmHwI544hj6NCXfEHeL6gERQ(TileQueryHelper tileQueryHelper, ArrayList arrayList, boolean z) {
        tileQueryHelper.lambda$notifyTilesChanged$1(arrayList, z);
    }

    public TileQueryHelper(Context context, UserTracker userTracker, Executor executor, Executor executor2) {
        this.mContext = context;
        this.mMainExecutor = executor;
        this.mBgExecutor = executor2;
        this.mUserTracker = userTracker;
    }

    public /* synthetic */ void lambda$addPackageTiles$0(QSTileHost qSTileHost) {
        Collection<QSTile> tiles = qSTileHost.getTiles();
        PackageManager packageManager = this.mContext.getPackageManager();
        List<ResolveInfo> queryIntentServicesAsUser = packageManager.queryIntentServicesAsUser(new Intent("android.service.quicksettings.action.QS_TILE"), 0, this.mUserTracker.getUserId());
        String string = this.mContext.getString(R$string.quick_settings_tiles_stock);
        for (ResolveInfo resolveInfo : queryIntentServicesAsUser) {
            ComponentName componentName = new ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name);
            if (!string.contains(componentName.flattenToString())) {
                CharSequence loadLabel = resolveInfo.serviceInfo.applicationInfo.loadLabel(packageManager);
                String spec = CustomTile.toSpec(componentName);
                QSTile.State state = getState(tiles, spec);
                if (state != null) {
                    addTile(spec, loadLabel, state, false);
                } else {
                    ServiceInfo serviceInfo = resolveInfo.serviceInfo;
                    if (serviceInfo.icon != 0 || serviceInfo.applicationInfo.icon != 0) {
                        Drawable loadIcon = serviceInfo.loadIcon(packageManager);
                        if ("android.permission.BIND_QUICK_SETTINGS_TILE".equals(resolveInfo.serviceInfo.permission) && loadIcon != null) {
                            loadIcon.mutate();
                            loadIcon.setTint(this.mContext.getColor(17170443));
                            CharSequence loadLabel2 = resolveInfo.serviceInfo.loadLabel(packageManager);
                            createStateAndAddTile(spec, loadIcon, loadLabel2 != null ? loadLabel2.toString() : "null", loadLabel);
                        }
                    }
                }
            }
        }
        notifyTilesChanged(true);
    }

    public /* synthetic */ void lambda$notifyTilesChanged$1(ArrayList arrayList, boolean z) {
        TileStateListener tileStateListener = this.mListener;
        if (tileStateListener != null) {
            tileStateListener.onTilesChanged(arrayList);
        }
        this.mFinished = z;
    }

    public final void addCurrentAndStockTiles(QSTileHost qSTileHost) {
        String[] split;
        QSTile createTile;
        String string = this.mContext.getString(R$string.quick_settings_tiles_stock);
        String string2 = Settings.Secure.getString(this.mContext.getContentResolver(), "sysui_qs_tiles");
        ArrayList arrayList = new ArrayList();
        if (string2 != null) {
            arrayList.addAll(Arrays.asList(string2.split(",")));
        } else {
            string2 = "";
        }
        for (String str : string.split(",")) {
            if (!string2.contains(str)) {
                arrayList.add(str);
            }
        }
        if (Build.IS_DEBUGGABLE && !string2.contains("dbg:mem")) {
            arrayList.add("dbg:mem");
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            if (!str2.startsWith("custom(") && (createTile = qSTileHost.createTile(str2)) != null) {
                if (createTile.isAvailable()) {
                    createTile.setTileSpec(str2);
                    arrayList2.add(createTile);
                } else {
                    createTile.setTileSpec(str2);
                    createTile.destroy();
                }
            }
        }
        new TileCollector(arrayList2, qSTileHost).startListening();
    }

    public final void addPackageTiles(final QSTileHost qSTileHost) {
        this.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.customize.TileQueryHelper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                TileQueryHelper.this.lambda$addPackageTiles$0(qSTileHost);
            }
        });
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x002b, code lost:
        if (android.text.TextUtils.equals(r9.label, r8) != false) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void addTile(String str, CharSequence charSequence, QSTile.State state, boolean z) {
        CharSequence charSequence2;
        if (this.mSpecs.contains(str)) {
            return;
        }
        state.dualTarget = false;
        state.expandedAccessibilityClassName = Button.class.getName();
        if (!z) {
            charSequence2 = charSequence;
        }
        charSequence2 = null;
        state.secondaryLabel = charSequence2;
        this.mTiles.add(new TileInfo(str, state, z));
        this.mSpecs.add(str);
    }

    public final void createStateAndAddTile(String str, Drawable drawable, CharSequence charSequence, CharSequence charSequence2) {
        QSTile.State state = new QSTile.State();
        state.state = 1;
        state.label = charSequence;
        state.contentDescription = charSequence;
        state.icon = new QSTileImpl.DrawableIcon(drawable);
        addTile(str, charSequence2, state, false);
    }

    public final QSTile.State getState(Collection<QSTile> collection, String str) {
        for (QSTile qSTile : collection) {
            if (str.equals(qSTile.getTileSpec())) {
                return qSTile.getState().copy();
            }
        }
        return null;
    }

    public boolean isFinished() {
        return this.mFinished;
    }

    public final void notifyTilesChanged(final boolean z) {
        final ArrayList arrayList = new ArrayList(this.mTiles);
        this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.customize.TileQueryHelper$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                TileQueryHelper.$r8$lambda$cY1pmHwI544hj6NCXfEHeL6gERQ(TileQueryHelper.this, arrayList, z);
            }
        });
    }

    public void queryTiles(QSTileHost qSTileHost) {
        this.mTiles.clear();
        this.mSpecs.clear();
        this.mFinished = false;
        addCurrentAndStockTiles(qSTileHost);
    }

    public void setListener(TileStateListener tileStateListener) {
        this.mListener = tileStateListener;
    }
}