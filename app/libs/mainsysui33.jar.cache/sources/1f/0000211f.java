package com.android.systemui.qs;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.Dumpable;
import com.android.systemui.ProtoDumpable;
import com.android.systemui.R$string;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.dump.nano.SystemUIProtoDump;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.plugins.qs.QSFactory;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.external.CustomTileStatePersister;
import com.android.systemui.qs.external.TileLifecycleManager;
import com.android.systemui.qs.external.TileServiceKey;
import com.android.systemui.qs.external.TileServiceRequestController;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.nano.QsTileState;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.phone.AutoTileManager;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.settings.SecureSettings;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/QSTileHost.class */
public class QSTileHost implements QSHost, TunerService.Tunable, PluginListener<QSFactory>, ProtoDumpable {
    public static final boolean DEBUG = Log.isLoggable("QSTileHost", 3);
    @VisibleForTesting
    public static final String TILES = "tiles_prefs";
    public AutoTileManager mAutoTiles;
    public final Optional<CentralSurfaces> mCentralSurfacesOptional;
    public final Context mContext;
    public int mCurrentUser;
    public final CustomTileStatePersister mCustomTileStatePersister;
    public final DumpManager mDumpManager;
    public final StatusBarIconController mIconController;
    public final InstanceIdSequence mInstanceIdSequence;
    public final Executor mMainExecutor;
    public final PluginManager mPluginManager;
    public final QSLogger mQSLogger;
    public final ArrayList<QSFactory> mQsFactories;
    public SecureSettings mSecureSettings;
    public TileLifecycleManager.Factory mTileLifeCycleManagerFactory;
    public final TileServiceRequestController mTileServiceRequestController;
    public boolean mTilesListDirty;
    public final TunerService mTunerService;
    public final UiEventLogger mUiEventLogger;
    public Context mUserContext;
    public final UserFileManager mUserFileManager;
    public UserTracker mUserTracker;
    public final LinkedHashMap<String, QSTile> mTiles = new LinkedHashMap<>();
    public final ArrayList<String> mTileSpecs = new ArrayList<>();
    public final List<QSHost.Callback> mCallbacks = new ArrayList();

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda15.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$7JmCW4pXfgI2gpMVKJdV0sreRY4(PrintWriter printWriter, String[] strArr, QSTile qSTile) {
        lambda$dump$12(printWriter, strArr, qSTile);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda3.test(java.lang.Object):boolean] */
    /* renamed from: $r8$lambda$7NKkLNoTEovnZtDu0p-vSoJ8pWY */
    public static /* synthetic */ boolean m3792$r8$lambda$7NKkLNoTEovnZtDu0pvSoJ8pWY(String str, int i, List list) {
        return lambda$addTile$8(str, i, list);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda1.test(java.lang.Object):boolean] */
    public static /* synthetic */ boolean $r8$lambda$A7aqLgSxuC7E7UNkzwrFd8Fd5q4(List list, Map.Entry entry) {
        return lambda$onTuningChanged$2(list, entry);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda14.test(java.lang.Object):boolean] */
    /* renamed from: $r8$lambda$ATvDgigol9Wl1kbM07Mv5-qSlrk */
    public static /* synthetic */ boolean m3793$r8$lambda$ATvDgigol9Wl1kbM07Mv5qSlrk(QSTile qSTile) {
        return lambda$dump$11(qSTile);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda8.test(java.lang.Object):boolean] */
    public static /* synthetic */ boolean $r8$lambda$AXuyaAnlEk0cZqAskxVaeSVedbY(String str, List list) {
        return lambda$removeTile$4(str, list);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda2.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$FtdNtvBVz7jju3NlQNMPQyd3z5w(QSTileHost qSTileHost, Map.Entry entry) {
        qSTileHost.lambda$onTuningChanged$3(entry);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda0.run():void] */
    /* renamed from: $r8$lambda$VJhULIWXIesrfXd1frt-1MRsSzM */
    public static /* synthetic */ void m3794$r8$lambda$VJhULIWXIesrfXd1frt1MRsSzM(QSTileHost qSTileHost, String str, int i) {
        qSTileHost.lambda$addTile$9(str, i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda7.run():void] */
    public static /* synthetic */ void $r8$lambda$XnnK_0WQ5wdF75eDMbUERBqrjpk(QSTileHost qSTileHost, TunerService tunerService, Provider provider) {
        qSTileHost.lambda$new$0(tunerService, provider);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda6.run():void] */
    public static /* synthetic */ void $r8$lambda$jWFwN9AXjBHd_Pqnj1XiHNk9Pac(QSTileHost qSTileHost, Collection collection) {
        qSTileHost.lambda$removeTiles$7(collection);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda9.test(java.lang.Object):boolean] */
    public static /* synthetic */ boolean $r8$lambda$rWFB3R_lIl6oklFO9lMjKlJcT_Q(Collection collection, List list) {
        return lambda$removeTiles$6(collection, list);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda4.run():void] */
    public static /* synthetic */ void $r8$lambda$wuS65nlqtQac9wnsH1EflAeZrdA(QSTileHost qSTileHost, String str) {
        qSTileHost.lambda$removeTile$5(str);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda16.run():void] */
    /* renamed from: $r8$lambda$zL-okBYrx74BRYPi4s_28IWyvzs */
    public static /* synthetic */ void m3795$r8$lambda$zLokBYrx74BRYPi4s_28IWyvzs(QSTileHost qSTileHost, ComponentName componentName) {
        qSTileHost.lambda$removeTileByUser$10(componentName);
    }

    public QSTileHost(Context context, StatusBarIconController statusBarIconController, QSFactory qSFactory, Executor executor, PluginManager pluginManager, final TunerService tunerService, final Provider<AutoTileManager> provider, DumpManager dumpManager, Optional<CentralSurfaces> optional, QSLogger qSLogger, UiEventLogger uiEventLogger, UserTracker userTracker, SecureSettings secureSettings, CustomTileStatePersister customTileStatePersister, TileServiceRequestController.Builder builder, TileLifecycleManager.Factory factory, UserFileManager userFileManager) {
        ArrayList<QSFactory> arrayList = new ArrayList<>();
        this.mQsFactories = arrayList;
        this.mTilesListDirty = true;
        this.mIconController = statusBarIconController;
        this.mContext = context;
        this.mUserContext = context;
        this.mTunerService = tunerService;
        this.mPluginManager = pluginManager;
        this.mDumpManager = dumpManager;
        this.mQSLogger = qSLogger;
        this.mUiEventLogger = uiEventLogger;
        this.mMainExecutor = executor;
        this.mTileServiceRequestController = builder.create(this);
        this.mTileLifeCycleManagerFactory = factory;
        this.mUserFileManager = userFileManager;
        this.mInstanceIdSequence = new InstanceIdSequence(1048576);
        this.mCentralSurfacesOptional = optional;
        arrayList.add(qSFactory);
        pluginManager.addPluginListener((PluginListener) this, QSFactory.class, true);
        dumpManager.registerDumpable("QSTileHost", this);
        this.mUserTracker = userTracker;
        this.mSecureSettings = secureSettings;
        this.mCustomTileStatePersister = customTileStatePersister;
        executor.execute(new Runnable() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                QSTileHost.$r8$lambda$XnnK_0WQ5wdF75eDMbUERBqrjpk(QSTileHost.this, tunerService, provider);
            }
        });
    }

    public static List<String> getDefaultSpecs(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(Arrays.asList(context.getResources().getString(R$string.quick_settings_tiles_default).split(",")));
        if (Build.IS_DEBUGGABLE) {
            arrayList.add("dbg:mem");
        }
        return arrayList;
    }

    public static /* synthetic */ boolean lambda$addTile$8(String str, int i, List list) {
        if (list.contains(str)) {
            return false;
        }
        int size = list.size();
        if (i == -1 || i >= size) {
            list.add(str);
            return true;
        }
        list.add(i, str);
        return true;
    }

    public /* synthetic */ void lambda$addTile$9(final String str, final int i) {
        changeTileSpecs(new Predicate() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return QSTileHost.m3792$r8$lambda$7NKkLNoTEovnZtDu0pvSoJ8pWY(str, i, (List) obj);
            }
        });
    }

    public static /* synthetic */ boolean lambda$dump$11(QSTile qSTile) {
        return qSTile instanceof Dumpable;
    }

    public static /* synthetic */ void lambda$dump$12(PrintWriter printWriter, String[] strArr, QSTile qSTile) {
        ((Dumpable) qSTile).dump(printWriter, strArr);
    }

    public /* synthetic */ void lambda$new$0(TunerService tunerService, Provider provider) {
        tunerService.addTunable(this, new String[]{"sysui_qs_tiles"});
        this.mAutoTiles = (AutoTileManager) provider.get();
        this.mTileServiceRequestController.init();
    }

    public static /* synthetic */ boolean lambda$onTuningChanged$2(List list, Map.Entry entry) {
        return !list.contains(entry.getKey());
    }

    public /* synthetic */ void lambda$onTuningChanged$3(Map.Entry entry) {
        Log.d("QSTileHost", "Destroying tile: " + ((String) entry.getKey()));
        this.mQSLogger.logTileDestroyed((String) entry.getKey(), "Tile removed");
        ((QSTile) entry.getValue()).destroy();
    }

    public static /* synthetic */ boolean lambda$removeTile$4(String str, List list) {
        return list.remove(str);
    }

    public /* synthetic */ void lambda$removeTile$5(final String str) {
        changeTileSpecs(new Predicate() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return QSTileHost.$r8$lambda$AXuyaAnlEk0cZqAskxVaeSVedbY(str, (List) obj);
            }
        });
    }

    public /* synthetic */ void lambda$removeTileByUser$10(ComponentName componentName) {
        ArrayList arrayList = new ArrayList(this.mTileSpecs);
        if (arrayList.remove(CustomTile.toSpec(componentName))) {
            changeTilesByUser(this.mTileSpecs, arrayList);
        }
    }

    public static /* synthetic */ boolean lambda$removeTiles$6(Collection collection, List list) {
        return list.removeAll(collection);
    }

    public /* synthetic */ void lambda$removeTiles$7(final Collection collection) {
        changeTileSpecs(new Predicate() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return QSTileHost.$r8$lambda$rWFB3R_lIl6oklFO9lMjKlJcT_Q(collection, (List) obj);
            }
        });
    }

    public static List<String> loadTileSpecs(Context context, String str) {
        String str2;
        boolean z;
        Resources resources = context.getResources();
        if (TextUtils.isEmpty(str)) {
            String string = resources.getString(R$string.quick_settings_tiles);
            str2 = string;
            if (DEBUG) {
                Log.d("QSTileHost", "Loaded tile specs from default config: " + string);
                str2 = string;
            }
        } else {
            str2 = str;
            if (DEBUG) {
                Log.d("QSTileHost", "Loaded tile specs from setting: " + str);
                str2 = str;
            }
        }
        ArrayList arrayList = new ArrayList();
        ArraySet arraySet = new ArraySet();
        String[] split = str2.split(",");
        int length = split.length;
        int i = 0;
        boolean z2 = false;
        while (true) {
            boolean z3 = z2;
            if (i >= length) {
                return arrayList;
            }
            String trim = split[i].trim();
            if (trim.isEmpty()) {
                z = z3;
            } else if (trim.equals("default")) {
                z = z3;
                if (!z3) {
                    for (String str3 : getDefaultSpecs(context)) {
                        if (!arraySet.contains(str3)) {
                            arrayList.add(str3);
                            arraySet.add(str3);
                        }
                    }
                    z = true;
                }
            } else {
                z = z3;
                if (!arraySet.contains(trim)) {
                    arrayList.add(trim);
                    arraySet.add(trim);
                    z = z3;
                }
            }
            i++;
            z2 = z;
        }
    }

    public void addCallback(QSHost.Callback callback) {
        this.mCallbacks.add(callback);
    }

    public void addTile(ComponentName componentName) {
        addTile(componentName, false);
    }

    public void addTile(ComponentName componentName, boolean z) {
        addTile(CustomTile.toSpec(componentName), z ? -1 : 0);
    }

    public void addTile(String str) {
        addTile(str, -1);
    }

    public void addTile(final String str, final int i) {
        this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                QSTileHost.m3794$r8$lambda$VJhULIWXIesrfXd1frt1MRsSzM(QSTileHost.this, str, i);
            }
        });
    }

    public final void changeTileSpecs(Predicate<List<String>> predicate) {
        ArrayList arrayList = !this.mTilesListDirty ? new ArrayList(this.mTileSpecs) : loadTileSpecs(this.mContext, this.mSecureSettings.getStringForUser("sysui_qs_tiles", this.mCurrentUser));
        if (predicate.test(arrayList)) {
            this.mTilesListDirty = true;
            saveTilesToSettings(arrayList);
        }
    }

    public void changeTilesByUser(List<String> list, List<String> list2) {
        ArrayList arrayList = new ArrayList(list);
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            String str = (String) arrayList.get(i);
            if (str.startsWith("custom(") && !list2.contains(str)) {
                ComponentName componentFromSpec = CustomTile.getComponentFromSpec(str);
                TileLifecycleManager create = this.mTileLifeCycleManagerFactory.create(new Intent().setComponent(componentFromSpec), new UserHandle(this.mCurrentUser));
                create.onStopListening();
                create.onTileRemoved();
                this.mCustomTileStatePersister.removeState(new TileServiceKey(componentFromSpec, this.mCurrentUser));
                setTileAdded(componentFromSpec, this.mCurrentUser, false);
                create.flushMessagesAndUnbind();
            }
        }
        if (DEBUG) {
            Log.d("QSTileHost", "saveCurrentTiles " + list2);
        }
        this.mTilesListDirty = true;
        saveTilesToSettings(list2);
    }

    @Override // com.android.systemui.qs.QSHost
    public void collapsePanels() {
        this.mCentralSurfacesOptional.ifPresent(new Consumer() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda17
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((CentralSurfaces) obj).postAnimateCollapsePanels();
            }
        });
    }

    public QSTile createTile(String str) {
        for (int i = 0; i < this.mQsFactories.size(); i++) {
            QSTile createTile = this.mQsFactories.get(i).createTile(str);
            if (createTile != null) {
                return createTile;
            }
        }
        return null;
    }

    public QSTileView createTileView(Context context, QSTile qSTile, boolean z) {
        for (int i = 0; i < this.mQsFactories.size(); i++) {
            QSTileView createTileView = this.mQsFactories.get(i).createTileView(context, qSTile, z);
            if (createTileView != null) {
                return createTileView;
            }
        }
        throw new RuntimeException("Default factory didn't create view for " + qSTile.getTileSpec());
    }

    @Override // com.android.systemui.Dumpable
    public void dump(final PrintWriter printWriter, final String[] strArr) {
        printWriter.println("QSTileHost:");
        this.mTiles.values().stream().filter(new Predicate() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return QSTileHost.m3793$r8$lambda$ATvDgigol9Wl1kbM07Mv5qSlrk((QSTile) obj);
            }
        }).forEach(new Consumer() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda15
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                QSTileHost.$r8$lambda$7JmCW4pXfgI2gpMVKJdV0sreRY4(printWriter, strArr, (QSTile) obj);
            }
        });
    }

    @Override // com.android.systemui.ProtoDumpable
    public void dumpProto(SystemUIProtoDump systemUIProtoDump, String[] strArr) {
        systemUIProtoDump.tiles = (QsTileState[]) ((List) this.mTiles.values().stream().map(new Function() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((QSTile) obj).getState();
            }
        }).map(new Function() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda12
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return TileStateToProtoKt.toProto((QSTile.State) obj);
            }
        }).filter(new Predicate() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.nonNull((QsTileState) obj);
            }
        }).collect(Collectors.toList())).toArray(new QsTileState[0]);
    }

    public void forceCollapsePanels() {
        this.mCentralSurfacesOptional.ifPresent(new Consumer() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((CentralSurfaces) obj).postAnimateForceCollapsePanels();
            }
        });
    }

    @Override // com.android.systemui.qs.QSHost
    public Context getContext() {
        return this.mContext;
    }

    public StatusBarIconController getIconController() {
        return this.mIconController;
    }

    @Override // com.android.systemui.qs.QSHost
    public InstanceId getNewInstanceId() {
        return this.mInstanceIdSequence.newInstanceId();
    }

    public Collection<QSTile> getTiles() {
        return this.mTiles.values();
    }

    @Override // com.android.systemui.qs.QSHost
    public UiEventLogger getUiEventLogger() {
        return this.mUiEventLogger;
    }

    @Override // com.android.systemui.qs.QSHost
    public Context getUserContext() {
        return this.mUserContext;
    }

    @Override // com.android.systemui.qs.QSHost
    public int getUserId() {
        return this.mCurrentUser;
    }

    @Override // com.android.systemui.qs.QSHost
    public int indexOf(String str) {
        return this.mTileSpecs.indexOf(str);
    }

    public boolean isTileAdded(ComponentName componentName, int i) {
        return this.mUserFileManager.getSharedPreferences(TILES, 0, i).getBoolean(componentName.flattenToString(), false);
    }

    @Override // com.android.systemui.plugins.PluginListener
    public void onPluginConnected(QSFactory qSFactory, Context context) {
        this.mQsFactories.add(0, qSFactory);
        String value = this.mTunerService.getValue("sysui_qs_tiles");
        onTuningChanged("sysui_qs_tiles", "");
        onTuningChanged("sysui_qs_tiles", value);
    }

    @Override // com.android.systemui.plugins.PluginListener
    public void onPluginDisconnected(QSFactory qSFactory) {
        this.mQsFactories.remove(qSFactory);
        String value = this.mTunerService.getValue("sysui_qs_tiles");
        onTuningChanged("sysui_qs_tiles", "");
        onTuningChanged("sysui_qs_tiles", value);
    }

    public void onTuningChanged(String str, String str2) {
        boolean z;
        if ("sysui_qs_tiles".equals(str)) {
            Log.d("QSTileHost", "Recreating tiles");
            String str3 = str2;
            if (str2 == null) {
                str3 = str2;
                if (UserManager.isDeviceInDemoMode(this.mContext)) {
                    str3 = this.mContext.getResources().getString(R$string.quick_settings_tiles_retail_mode);
                }
            }
            final List<String> loadTileSpecs = loadTileSpecs(this.mContext, str3);
            int userId = this.mUserTracker.getUserId();
            if (userId != this.mCurrentUser) {
                this.mUserContext = this.mUserTracker.getUserContext();
                AutoTileManager autoTileManager = this.mAutoTiles;
                if (autoTileManager != null) {
                    autoTileManager.changeUser(UserHandle.of(userId));
                }
            }
            if (loadTileSpecs.equals(this.mTileSpecs) && userId == this.mCurrentUser) {
                return;
            }
            this.mTiles.entrySet().stream().filter(new Predicate() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return QSTileHost.$r8$lambda$A7aqLgSxuC7E7UNkzwrFd8Fd5q4(loadTileSpecs, (Map.Entry) obj);
                }
            }).forEach(new Consumer() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    QSTileHost.$r8$lambda$FtdNtvBVz7jju3NlQNMPQyd3z5w(QSTileHost.this, (Map.Entry) obj);
                }
            });
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (String str4 : loadTileSpecs) {
                QSTile qSTile = this.mTiles.get(str4);
                if (qSTile == null || (((z = qSTile instanceof CustomTile)) && ((CustomTile) qSTile).getUser() != userId)) {
                    if (qSTile != null) {
                        qSTile.destroy();
                        Log.d("QSTileHost", "Destroying tile for wrong user: " + str4);
                        this.mQSLogger.logTileDestroyed(str4, "Tile for wrong user");
                    }
                    Log.d("QSTileHost", "Creating tile: " + str4);
                    try {
                        QSTile createTile = createTile(str4);
                        if (createTile != null) {
                            createTile.setTileSpec(str4);
                            if (createTile.isAvailable()) {
                                linkedHashMap.put(str4, createTile);
                                this.mQSLogger.logTileAdded(str4);
                            } else {
                                createTile.destroy();
                                Log.d("QSTileHost", "Destroying not available tile: " + str4);
                                this.mQSLogger.logTileDestroyed(str4, "Tile not available");
                            }
                        }
                    } catch (Throwable th) {
                        Log.w("QSTileHost", "Error creating tile for spec: " + str4, th);
                    }
                } else if (qSTile.isAvailable()) {
                    if (DEBUG) {
                        Log.d("QSTileHost", "Adding " + qSTile);
                    }
                    qSTile.removeCallbacks();
                    if (!z && this.mCurrentUser != userId) {
                        qSTile.userSwitch(userId);
                    }
                    linkedHashMap.put(str4, qSTile);
                    this.mQSLogger.logTileAdded(str4);
                } else {
                    qSTile.destroy();
                    Log.d("QSTileHost", "Destroying not available tile: " + str4);
                    this.mQSLogger.logTileDestroyed(str4, "Tile not available");
                }
            }
            this.mCurrentUser = userId;
            ArrayList arrayList = new ArrayList(this.mTileSpecs);
            this.mTileSpecs.clear();
            this.mTileSpecs.addAll(linkedHashMap.keySet());
            this.mTiles.clear();
            this.mTiles.putAll(linkedHashMap);
            if (linkedHashMap.isEmpty() && !loadTileSpecs.isEmpty()) {
                Log.d("QSTileHost", "No valid tiles on tuning changed. Setting to default.");
                changeTilesByUser(arrayList, loadTileSpecs(this.mContext, ""));
                return;
            }
            if (!TextUtils.join(",", this.mTileSpecs).equals(str3)) {
                saveTilesToSettings(this.mTileSpecs);
            }
            this.mTilesListDirty = false;
            for (int i = 0; i < this.mCallbacks.size(); i++) {
                this.mCallbacks.get(i).onTilesChanged();
            }
        }
    }

    @Override // com.android.systemui.qs.QSHost
    public void openPanels() {
        this.mCentralSurfacesOptional.ifPresent(new Consumer() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((CentralSurfaces) obj).postAnimateOpenPanels();
            }
        });
    }

    public void removeCallback(QSHost.Callback callback) {
        this.mCallbacks.remove(callback);
    }

    @Override // com.android.systemui.qs.QSHost
    public void removeTile(final String str) {
        if (str != null && str.startsWith("custom(")) {
            setTileAdded(CustomTile.getComponentFromSpec(str), this.mCurrentUser, false);
        }
        if (str != null) {
            this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    QSTileHost.$r8$lambda$wuS65nlqtQac9wnsH1EflAeZrdA(QSTileHost.this, str);
                }
            });
        }
    }

    public void removeTileByUser(final ComponentName componentName) {
        this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                QSTileHost.m3795$r8$lambda$zLokBYrx74BRYPi4s_28IWyvzs(QSTileHost.this, componentName);
            }
        });
    }

    @Override // com.android.systemui.qs.QSHost
    public void removeTiles(final Collection<String> collection) {
        this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                QSTileHost.$r8$lambda$jWFwN9AXjBHd_Pqnj1XiHNk9Pac(QSTileHost.this, collection);
            }
        });
    }

    public final void saveTilesToSettings(List<String> list) {
        this.mSecureSettings.putStringForUser("sysui_qs_tiles", TextUtils.join(",", list), (String) null, false, this.mCurrentUser, true);
    }

    public void setTileAdded(ComponentName componentName, int i, boolean z) {
        this.mUserFileManager.getSharedPreferences(TILES, 0, i).edit().putBoolean(componentName.flattenToString(), z).apply();
    }

    @Override // com.android.systemui.qs.QSHost
    public void warn(String str, Throwable th) {
    }
}