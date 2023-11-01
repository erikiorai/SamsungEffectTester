package com.android.keyguard.clock;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import com.android.keyguard.clock.ClockManager;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.dock.DockManager;
import com.android.systemui.plugins.ClockPlugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.settings.UserTracker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

@Deprecated
/* loaded from: mainsysui33.jar:com/android/keyguard/clock/ClockManager.class */
public final class ClockManager {
    public final List<Supplier<ClockPlugin>> mBuiltinClocks;
    public final ContentObserver mContentObserver;
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public final DockManager.DockEventListener mDockEventListener;
    public final DockManager mDockManager;
    public final int mHeight;
    public boolean mIsDocked;
    public final Map<ClockChangedListener, AvailableClocks> mListeners;
    public final Executor mMainExecutor;
    public final Handler mMainHandler;
    public final PluginManager mPluginManager;
    public final AvailableClocks mPreviewClocks;
    public final SettingsWrapper mSettingsWrapper;
    public final UserTracker.Callback mUserChangedCallback;
    public final UserTracker mUserTracker;
    public final int mWidth;

    /* loaded from: mainsysui33.jar:com/android/keyguard/clock/ClockManager$AvailableClocks.class */
    public final class AvailableClocks implements PluginListener<ClockPlugin> {
        public final List<ClockInfo> mClockInfo;
        public final Map<String, ClockPlugin> mClocks;
        public ClockPlugin mCurrentClock;

        /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.clock.ClockManager$AvailableClocks$$ExternalSyntheticLambda2.get():java.lang.Object] */
        public static /* synthetic */ Bitmap $r8$lambda$hLHTYHEn_sJRR4dSOCFM0ZDNShg(AvailableClocks availableClocks, ClockPlugin clockPlugin) {
            return availableClocks.lambda$addClockPlugin$0(clockPlugin);
        }

        public AvailableClocks() {
            ClockManager.this = r5;
            this.mClocks = new ArrayMap();
            this.mClockInfo = new ArrayList();
        }

        public /* synthetic */ Bitmap lambda$addClockPlugin$0(ClockPlugin clockPlugin) {
            return clockPlugin.getPreview(ClockManager.this.mWidth, ClockManager.this.mHeight);
        }

        public void addClockPlugin(final ClockPlugin clockPlugin) {
            String name = clockPlugin.getClass().getName();
            this.mClocks.put(clockPlugin.getClass().getName(), clockPlugin);
            this.mClockInfo.add(ClockInfo.builder().setName(clockPlugin.getName()).setTitle(new Supplier() { // from class: com.android.keyguard.clock.ClockManager$AvailableClocks$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return ClockPlugin.this.getTitle();
                }
            }).setId(name).setThumbnail(new Supplier() { // from class: com.android.keyguard.clock.ClockManager$AvailableClocks$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return ClockPlugin.this.getThumbnail();
                }
            }).setPreview(new Supplier() { // from class: com.android.keyguard.clock.ClockManager$AvailableClocks$$ExternalSyntheticLambda2
                @Override // java.util.function.Supplier
                public final Object get() {
                    return ClockManager.AvailableClocks.$r8$lambda$hLHTYHEn_sJRR4dSOCFM0ZDNShg(ClockManager.AvailableClocks.this, clockPlugin);
                }
            }).build());
        }

        public final ClockPlugin getClockPlugin() {
            ClockPlugin clockPlugin;
            String dockedClockFace;
            if (!ClockManager.this.isDocked() || (dockedClockFace = ClockManager.this.mSettingsWrapper.getDockedClockFace(ClockManager.this.mUserTracker.getUserId())) == null) {
                clockPlugin = null;
            } else {
                ClockPlugin clockPlugin2 = this.mClocks.get(dockedClockFace);
                clockPlugin = clockPlugin2;
                if (clockPlugin2 != null) {
                    return clockPlugin2;
                }
            }
            String lockScreenCustomClockFace = ClockManager.this.mSettingsWrapper.getLockScreenCustomClockFace(ClockManager.this.mUserTracker.getUserId());
            if (lockScreenCustomClockFace != null) {
                clockPlugin = this.mClocks.get(lockScreenCustomClockFace);
            }
            return clockPlugin;
        }

        public ClockPlugin getCurrentClock() {
            return this.mCurrentClock;
        }

        public List<ClockInfo> getInfo() {
            return this.mClockInfo;
        }

        @Override // com.android.systemui.plugins.PluginListener
        public void onPluginConnected(ClockPlugin clockPlugin, Context context) {
            addClockPlugin(clockPlugin);
            reloadIfNeeded(clockPlugin);
        }

        @Override // com.android.systemui.plugins.PluginListener
        public void onPluginDisconnected(ClockPlugin clockPlugin) {
            removeClockPlugin(clockPlugin);
            reloadIfNeeded(clockPlugin);
        }

        public void reloadCurrentClock() {
            this.mCurrentClock = getClockPlugin();
        }

        public final void reloadIfNeeded(ClockPlugin clockPlugin) {
            boolean z = true;
            boolean z2 = clockPlugin == this.mCurrentClock;
            reloadCurrentClock();
            if (clockPlugin != this.mCurrentClock) {
                z = false;
            }
            if (z2 || z) {
                ClockManager.this.reload();
            }
        }

        public final void removeClockPlugin(ClockPlugin clockPlugin) {
            String name = clockPlugin.getClass().getName();
            this.mClocks.remove(name);
            for (int i = 0; i < this.mClockInfo.size(); i++) {
                if (name.equals(this.mClockInfo.get(i).getId())) {
                    this.mClockInfo.remove(i);
                    return;
                }
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/keyguard/clock/ClockManager$ClockChangedListener.class */
    public interface ClockChangedListener {
        void onClockChanged(ClockPlugin clockPlugin);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.clock.ClockManager$$ExternalSyntheticLambda0.get():java.lang.Object] */
    public static /* synthetic */ ClockPlugin $r8$lambda$AyYKd70UcvQTCjomzYrWBj5g5aM(Resources resources, LayoutInflater layoutInflater, SysuiColorExtractor sysuiColorExtractor) {
        return lambda$new$0(resources, layoutInflater, sysuiColorExtractor);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.clock.ClockManager$$ExternalSyntheticLambda1.accept(java.lang.Object, java.lang.Object):void] */
    /* renamed from: $r8$lambda$M1DCXP8EfZWG4M-EPpEnsOEcGBw */
    public static /* synthetic */ void m826$r8$lambda$M1DCXP8EfZWG4MEPpEnsOEcGBw(ClockManager clockManager, ClockChangedListener clockChangedListener, AvailableClocks availableClocks) {
        clockManager.lambda$reload$2(clockChangedListener, availableClocks);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.clock.ClockManager$$ExternalSyntheticLambda2.run():void] */
    /* renamed from: $r8$lambda$kKdSWl-BKeHRoTOSCfIkyaqwMxQ */
    public static /* synthetic */ void m827$r8$lambda$kKdSWlBKeHRoTOSCfIkyaqwMxQ(ClockChangedListener clockChangedListener, ClockPlugin clockPlugin) {
        lambda$reload$1(clockChangedListener, clockPlugin);
    }

    public ClockManager(Context context, final LayoutInflater layoutInflater, PluginManager pluginManager, final SysuiColorExtractor sysuiColorExtractor, ContentResolver contentResolver, UserTracker userTracker, Executor executor, SettingsWrapper settingsWrapper, DockManager dockManager) {
        this.mBuiltinClocks = new ArrayList();
        Handler handler = new Handler(Looper.getMainLooper());
        this.mMainHandler = handler;
        this.mContentObserver = new ContentObserver(handler) { // from class: com.android.keyguard.clock.ClockManager.1
            {
                ClockManager.this = this;
            }

            public void onChange(boolean z, Collection<Uri> collection, int i, int i2) {
                if (Objects.equals(Integer.valueOf(i2), Integer.valueOf(ClockManager.this.mUserTracker.getUserId()))) {
                    ClockManager.this.reload();
                }
            }
        };
        this.mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.keyguard.clock.ClockManager.2
            {
                ClockManager.this = this;
            }

            public void onUserChanged(int i, Context context2) {
                ClockManager.this.reload();
            }
        };
        this.mDockEventListener = new DockManager.DockEventListener() { // from class: com.android.keyguard.clock.ClockManager.3
            {
                ClockManager.this = this;
            }

            @Override // com.android.systemui.dock.DockManager.DockEventListener
            public void onEvent(int i) {
                ClockManager clockManager = ClockManager.this;
                boolean z = true;
                if (i != 1) {
                    z = i == 2;
                }
                clockManager.mIsDocked = z;
                ClockManager.this.reload();
            }
        };
        this.mListeners = new ArrayMap();
        this.mContext = context;
        this.mPluginManager = pluginManager;
        this.mContentResolver = contentResolver;
        this.mSettingsWrapper = settingsWrapper;
        this.mUserTracker = userTracker;
        this.mMainExecutor = executor;
        this.mDockManager = dockManager;
        this.mPreviewClocks = new AvailableClocks();
        final Resources resources = context.getResources();
        addBuiltinClock(new Supplier() { // from class: com.android.keyguard.clock.ClockManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return ClockManager.$r8$lambda$AyYKd70UcvQTCjomzYrWBj5g5aM(resources, layoutInflater, sysuiColorExtractor);
            }
        });
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        this.mWidth = displayMetrics.widthPixels;
        this.mHeight = displayMetrics.heightPixels;
    }

    public ClockManager(Context context, LayoutInflater layoutInflater, PluginManager pluginManager, SysuiColorExtractor sysuiColorExtractor, DockManager dockManager, UserTracker userTracker, Executor executor) {
        this(context, layoutInflater, pluginManager, sysuiColorExtractor, context.getContentResolver(), userTracker, executor, new SettingsWrapper(context.getContentResolver()), dockManager);
    }

    public static /* synthetic */ ClockPlugin lambda$new$0(Resources resources, LayoutInflater layoutInflater, SysuiColorExtractor sysuiColorExtractor) {
        return new DefaultClockController(resources, layoutInflater, sysuiColorExtractor);
    }

    public static /* synthetic */ void lambda$reload$1(ClockChangedListener clockChangedListener, ClockPlugin clockPlugin) {
        ClockPlugin clockPlugin2 = clockPlugin;
        if (clockPlugin instanceof DefaultClockController) {
            clockPlugin2 = null;
        }
        clockChangedListener.onClockChanged(clockPlugin2);
    }

    public /* synthetic */ void lambda$reload$2(final ClockChangedListener clockChangedListener, AvailableClocks availableClocks) {
        availableClocks.reloadCurrentClock();
        final ClockPlugin currentClock = availableClocks.getCurrentClock();
        if (Looper.myLooper() != Looper.getMainLooper()) {
            this.mMainHandler.post(new Runnable() { // from class: com.android.keyguard.clock.ClockManager$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    ClockManager.m827$r8$lambda$kKdSWlBKeHRoTOSCfIkyaqwMxQ(ClockManager.ClockChangedListener.this, currentClock);
                }
            });
            return;
        }
        ClockPlugin clockPlugin = currentClock;
        if (currentClock instanceof DefaultClockController) {
            clockPlugin = null;
        }
        clockChangedListener.onClockChanged(clockPlugin);
    }

    public void addBuiltinClock(Supplier<ClockPlugin> supplier) {
        this.mPreviewClocks.addClockPlugin(supplier.get());
        this.mBuiltinClocks.add(supplier);
    }

    public List<ClockInfo> getClockInfos() {
        return this.mPreviewClocks.getInfo();
    }

    public ContentObserver getContentObserver() {
        return this.mContentObserver;
    }

    public boolean isDocked() {
        return this.mIsDocked;
    }

    public final void reload() {
        this.mPreviewClocks.reloadCurrentClock();
        this.mListeners.forEach(new BiConsumer() { // from class: com.android.keyguard.clock.ClockManager$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ClockManager.m826$r8$lambda$M1DCXP8EfZWG4MEPpEnsOEcGBw(ClockManager.this, (ClockManager.ClockChangedListener) obj, (ClockManager.AvailableClocks) obj2);
            }
        });
    }
}