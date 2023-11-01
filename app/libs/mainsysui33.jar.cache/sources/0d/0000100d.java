package com.android.systemui;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import com.android.systemui.dagger.GlobalRootComponent;
import com.android.systemui.dagger.SysUIComponent;
import com.android.systemui.dagger.WMComponent;
import com.android.systemui.util.InitializationChecker;
import com.android.wm.shell.dagger.WMShellConcurrencyModule;
import com.android.wm.shell.sysui.ShellInterface;
import com.android.wm.shell.transition.ShellTransitions;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/* loaded from: mainsysui33.jar:com/android/systemui/SystemUIInitializer.class */
public abstract class SystemUIInitializer {
    private static final String TAG = "SystemUIFactory";
    private final Context mContext;
    private InitializationChecker mInitializationChecker;
    private GlobalRootComponent mRootComponent;
    private SysUIComponent mSysUIComponent;
    private WMComponent mWMComponent;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.SystemUIInitializer$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$rYwcShqQbxgGPkCrftAaCSgc4LI(SystemUIInitializer systemUIInitializer, WMComponent.Builder builder, HandlerThread handlerThread) {
        systemUIInitializer.lambda$setupWmComponent$0(builder, handlerThread);
    }

    public SystemUIInitializer(Context context) {
        this.mContext = context;
    }

    public /* synthetic */ void lambda$setupWmComponent$0(WMComponent.Builder builder, HandlerThread handlerThread) {
        builder.setShellMainThread(handlerThread);
        this.mWMComponent = builder.build();
    }

    private void setupWmComponent(Context context) {
        final WMComponent.Builder wMComponentBuilder = this.mRootComponent.getWMComponentBuilder();
        if (!this.mInitializationChecker.initializeComponents() || !WMShellConcurrencyModule.enableShellMainThread(context)) {
            this.mWMComponent = wMComponentBuilder.build();
            return;
        }
        final HandlerThread createShellMainThread = WMShellConcurrencyModule.createShellMainThread();
        createShellMainThread.start();
        if (Handler.createAsync(createShellMainThread.getLooper()).runWithScissors(new Runnable() { // from class: com.android.systemui.SystemUIInitializer$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SystemUIInitializer.$r8$lambda$rYwcShqQbxgGPkCrftAaCSgc4LI(SystemUIInitializer.this, wMComponentBuilder, createShellMainThread);
            }
        }, 5000L)) {
            return;
        }
        Log.w(TAG, "Failed to initialize WMComponent");
        throw new RuntimeException();
    }

    public abstract GlobalRootComponent.Builder getGlobalRootComponentBuilder();

    public GlobalRootComponent getRootComponent() {
        return this.mRootComponent;
    }

    public SysUIComponent getSysUIComponent() {
        return this.mSysUIComponent;
    }

    public String getVendorComponent(Resources resources) {
        return resources.getString(R$string.config_systemUIVendorServiceComponent);
    }

    public WMComponent getWMComponent() {
        return this.mWMComponent;
    }

    public void init(boolean z) throws ExecutionException, InterruptedException {
        SysUIComponent.Builder desktopMode;
        if (getGlobalRootComponentBuilder() == null) {
            return;
        }
        GlobalRootComponent build = getGlobalRootComponentBuilder().context(this.mContext).instrumentationTest(z).build();
        this.mRootComponent = build;
        InitializationChecker initializationChecker = build.getInitializationChecker();
        this.mInitializationChecker = initializationChecker;
        boolean initializeComponents = initializationChecker.initializeComponents();
        setupWmComponent(this.mContext);
        SysUIComponent.Builder sysUIComponent = this.mRootComponent.getSysUIComponent();
        if (initializeComponents) {
            desktopMode = prepareSysUIComponentBuilder(sysUIComponent, this.mWMComponent).setShell(this.mWMComponent.getShell()).setPip(this.mWMComponent.getPip()).setSplitScreen(this.mWMComponent.getSplitScreen()).setOneHanded(this.mWMComponent.getOneHanded()).setBubbles(this.mWMComponent.getBubbles()).setTaskViewFactory(this.mWMComponent.getTaskViewFactory()).setTransitions(this.mWMComponent.getTransitions()).setStartingSurface(this.mWMComponent.getStartingSurface()).setDisplayAreaHelper(this.mWMComponent.getDisplayAreaHelper()).setRecentTasks(this.mWMComponent.getRecentTasks()).setBackAnimation(this.mWMComponent.getBackAnimation()).setDesktopMode(this.mWMComponent.getDesktopMode());
            this.mWMComponent.init();
        } else {
            desktopMode = prepareSysUIComponentBuilder(sysUIComponent, this.mWMComponent).setShell(new ShellInterface() { // from class: com.android.systemui.SystemUIInitializer.2
                {
                    SystemUIInitializer.this = this;
                }
            }).setPip(Optional.ofNullable(null)).setSplitScreen(Optional.ofNullable(null)).setOneHanded(Optional.ofNullable(null)).setBubbles(Optional.ofNullable(null)).setTaskViewFactory(Optional.ofNullable(null)).setTransitions(new ShellTransitions() { // from class: com.android.systemui.SystemUIInitializer.1
                {
                    SystemUIInitializer.this = this;
                }
            }).setDisplayAreaHelper(Optional.ofNullable(null)).setStartingSurface(Optional.ofNullable(null)).setRecentTasks(Optional.ofNullable(null)).setBackAnimation(Optional.ofNullable(null)).setDesktopMode(Optional.ofNullable(null));
        }
        SysUIComponent build2 = desktopMode.build();
        this.mSysUIComponent = build2;
        if (initializeComponents) {
            build2.init();
        }
        this.mSysUIComponent.createDependency().start();
    }

    public SysUIComponent.Builder prepareSysUIComponentBuilder(SysUIComponent.Builder builder, WMComponent wMComponent) {
        return builder;
    }
}