package com.android.keyguard;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.text.format.DateFormat;
import android.util.TypedValue;
import android.view.View;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$dimen;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.ClockAnimations;
import com.android.systemui.plugins.ClockController;
import com.android.systemui.plugins.ClockEvents;
import com.android.systemui.plugins.ClockFaceController;
import com.android.systemui.plugins.ClockFaceEvents;
import com.android.systemui.plugins.log.LogBuffer;
import com.android.systemui.shared.regionsampling.RegionSampler;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.Job;

/* loaded from: mainsysui33.jar:com/android/keyguard/ClockEventController.class */
public class ClockEventController {
    public final BatteryController batteryController;
    public final Executor bgExecutor;
    public final BroadcastDispatcher broadcastDispatcher;
    public ClockController clock;
    public final ConfigurationController configurationController;
    public final Context context;
    public DisposableHandle disposableHandle;
    public float dozeAmount;
    public final FeatureFlags featureFlags;
    public boolean isCharging;
    public boolean isDozing;
    public boolean isKeyguardVisible;
    public boolean isRegistered;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public RegionSampler largeRegionSampler;
    public final LogBuffer logBuffer;
    public final Executor mainExecutor;
    public final boolean regionSamplingEnabled;
    public final Resources resources;
    public RegionSampler smallRegionSampler;
    public boolean smallClockIsDark = true;
    public boolean largeClockIsDark = true;
    public final ClockEventController$configListener$1 configListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.ClockEventController$configListener$1
        public void onDensityOrFontScaleChanged() {
            ClockEventController.access$updateFontSizes(ClockEventController.this);
        }

        public void onThemeChanged() {
            ClockEvents events;
            ClockController clock = ClockEventController.this.getClock();
            if (clock != null && (events = clock.getEvents()) != null) {
                events.onColorPaletteChanged(ClockEventController.access$getResources$p(ClockEventController.this));
            }
            ClockEventController.access$updateColors(ClockEventController.this);
        }
    };
    public final ClockEventController$batteryCallback$1 batteryCallback = new BatteryController.BatteryStateChangeCallback() { // from class: com.android.keyguard.ClockEventController$batteryCallback$1
        public void onBatteryLevelChanged(int i, boolean z, boolean z2) {
            ClockController clock;
            ClockAnimations animations;
            if (ClockEventController.access$isKeyguardVisible$p(ClockEventController.this) && !ClockEventController.access$isCharging$p(ClockEventController.this) && z2 && (clock = ClockEventController.this.getClock()) != null && (animations = clock.getAnimations()) != null) {
                animations.charge();
            }
            ClockEventController.access$setCharging$p(ClockEventController.this, z2);
        }
    };
    public final ClockEventController$localeBroadcastReceiver$1 localeBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.keyguard.ClockEventController$localeBroadcastReceiver$1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            ClockEvents events;
            ClockController clock = ClockEventController.this.getClock();
            if (clock == null || (events = clock.getEvents()) == null) {
                return;
            }
            events.onLocaleChanged(Locale.getDefault());
        }
    };
    public final ClockEventController$keyguardUpdateMonitorCallback$1 keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.ClockEventController$keyguardUpdateMonitorCallback$1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onKeyguardVisibilityChanged(boolean z) {
            FeatureFlags featureFlags;
            boolean z2;
            ClockController clock;
            ClockAnimations animations;
            boolean z3;
            ClockEventController.this.isKeyguardVisible = z;
            featureFlags = ClockEventController.this.featureFlags;
            if (featureFlags.isEnabled(Flags.DOZING_MIGRATION_1)) {
                return;
            }
            z2 = ClockEventController.this.isKeyguardVisible;
            if (z2 || (clock = ClockEventController.this.getClock()) == null || (animations = clock.getAnimations()) == null) {
                return;
            }
            z3 = ClockEventController.this.isDozing;
            animations.doze(z3 ? 1.0f : 0.0f);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onTimeFormatChanged(String str) {
            ClockEvents events;
            Context context;
            ClockController clock = ClockEventController.this.getClock();
            if (clock == null || (events = clock.getEvents()) == null) {
                return;
            }
            context = ClockEventController.this.context;
            events.onTimeFormatChanged(DateFormat.is24HourFormat(context));
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onTimeZoneChanged(TimeZone timeZone) {
            ClockEvents events;
            ClockController clock = ClockEventController.this.getClock();
            if (clock == null || (events = clock.getEvents()) == null) {
                return;
            }
            events.onTimeZoneChanged(timeZone);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onUserSwitchComplete(int i) {
            ClockEvents events;
            Context context;
            ClockController clock = ClockEventController.this.getClock();
            if (clock == null || (events = clock.getEvents()) == null) {
                return;
            }
            context = ClockEventController.this.context;
            events.onTimeFormatChanged(DateFormat.is24HourFormat(context));
        }
    };

    /* JADX WARN: Type inference failed for: r1v16, types: [com.android.keyguard.ClockEventController$configListener$1] */
    /* JADX WARN: Type inference failed for: r1v17, types: [com.android.keyguard.ClockEventController$batteryCallback$1] */
    /* JADX WARN: Type inference failed for: r1v18, types: [com.android.keyguard.ClockEventController$localeBroadcastReceiver$1] */
    /* JADX WARN: Type inference failed for: r1v19, types: [com.android.keyguard.ClockEventController$keyguardUpdateMonitorCallback$1] */
    public ClockEventController(KeyguardInteractor keyguardInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, BroadcastDispatcher broadcastDispatcher, BatteryController batteryController, KeyguardUpdateMonitor keyguardUpdateMonitor, ConfigurationController configurationController, Resources resources, Context context, Executor executor, Executor executor2, LogBuffer logBuffer, FeatureFlags featureFlags) {
        this.keyguardInteractor = keyguardInteractor;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
        this.broadcastDispatcher = broadcastDispatcher;
        this.batteryController = batteryController;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.configurationController = configurationController;
        this.resources = resources;
        this.context = context;
        this.mainExecutor = executor;
        this.bgExecutor = executor2;
        this.logBuffer = logBuffer;
        this.featureFlags = featureFlags;
        this.regionSamplingEnabled = featureFlags.isEnabled(Flags.INSTANCE.getREGION_SAMPLING());
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.ClockEventController$configListener$1.onThemeChanged():void] */
    public static final /* synthetic */ Resources access$getResources$p(ClockEventController clockEventController) {
        return clockEventController.resources;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.ClockEventController$batteryCallback$1.onBatteryLevelChanged(int, boolean, boolean):void] */
    public static final /* synthetic */ boolean access$isCharging$p(ClockEventController clockEventController) {
        return clockEventController.isCharging;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.ClockEventController$batteryCallback$1.onBatteryLevelChanged(int, boolean, boolean):void] */
    public static final /* synthetic */ boolean access$isKeyguardVisible$p(ClockEventController clockEventController) {
        return clockEventController.isKeyguardVisible;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.ClockEventController$batteryCallback$1.onBatteryLevelChanged(int, boolean, boolean):void] */
    public static final /* synthetic */ void access$setCharging$p(ClockEventController clockEventController, boolean z) {
        clockEventController.isCharging = z;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.ClockEventController$configListener$1.onThemeChanged():void] */
    public static final /* synthetic */ void access$updateColors(ClockEventController clockEventController) {
        clockEventController.updateColors();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.ClockEventController$configListener$1.onDensityOrFontScaleChanged():void] */
    public static final /* synthetic */ void access$updateFontSizes(ClockEventController clockEventController) {
        clockEventController.updateFontSizes();
    }

    public RegionSampler createRegionSampler(View view, Executor executor, Executor executor2, boolean z, Function0<Unit> function0) {
        return new RegionSampler(view, executor, executor2, z, function0);
    }

    public final ClockController getClock() {
        return this.clock;
    }

    public final Job listenForAnyStateToAodTransition$frameworks__base__packages__SystemUI__android_common__SystemUI_core(CoroutineScope coroutineScope) {
        return BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new ClockEventController$listenForAnyStateToAodTransition$1(this, null), 3, (Object) null);
    }

    public final Job listenForDozeAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core(CoroutineScope coroutineScope) {
        return BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new ClockEventController$listenForDozeAmount$1(this, null), 3, (Object) null);
    }

    public final Job listenForDozeAmountTransition$frameworks__base__packages__SystemUI__android_common__SystemUI_core(CoroutineScope coroutineScope) {
        return BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new ClockEventController$listenForDozeAmountTransition$1(this, null), 3, (Object) null);
    }

    public final Job listenForDozing$frameworks__base__packages__SystemUI__android_common__SystemUI_core(CoroutineScope coroutineScope) {
        return BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new ClockEventController$listenForDozing$1(this, null), 3, (Object) null);
    }

    public final void registerListeners(View view) {
        if (this.isRegistered) {
            return;
        }
        this.isRegistered = true;
        BroadcastDispatcher.registerReceiver$default(this.broadcastDispatcher, this.localeBroadcastReceiver, new IntentFilter("android.intent.action.LOCALE_CHANGED"), null, null, 0, null, 60, null);
        this.configurationController.addCallback(this.configListener);
        this.batteryController.addCallback(this.batteryCallback);
        this.keyguardUpdateMonitor.registerCallback(this.keyguardUpdateMonitorCallback);
        RegionSampler regionSampler = this.smallRegionSampler;
        if (regionSampler != null) {
            regionSampler.startRegionSampler();
        }
        RegionSampler regionSampler2 = this.largeRegionSampler;
        if (regionSampler2 != null) {
            regionSampler2.startRegionSampler();
        }
        this.disposableHandle = RepeatWhenAttachedKt.repeatWhenAttached$default(view, null, new ClockEventController$registerListeners$1(this, null), 1, null);
    }

    public final void setClock(ClockController clockController) {
        this.clock = clockController;
        if (clockController != null) {
            LogBuffer logBuffer = this.logBuffer;
            if (logBuffer != null) {
                clockController.setLogBuffer(logBuffer);
            }
            clockController.initialize(this.resources, this.dozeAmount, ActionBarShadowController.ELEVATION_LOW);
            updateRegionSamplers(clockController);
            updateFontSizes();
        }
    }

    public final void unregisterListeners() {
        if (this.isRegistered) {
            this.isRegistered = false;
            DisposableHandle disposableHandle = this.disposableHandle;
            if (disposableHandle != null) {
                disposableHandle.dispose();
            }
            this.broadcastDispatcher.unregisterReceiver(this.localeBroadcastReceiver);
            this.configurationController.removeCallback(this.configListener);
            this.batteryController.removeCallback(this.batteryCallback);
            this.keyguardUpdateMonitor.removeCallback(this.keyguardUpdateMonitorCallback);
            RegionSampler regionSampler = this.smallRegionSampler;
            if (regionSampler != null) {
                regionSampler.stopRegionSampler();
            }
            RegionSampler regionSampler2 = this.largeRegionSampler;
            if (regionSampler2 != null) {
                regionSampler2.stopRegionSampler();
            }
        }
    }

    public final void updateColors() {
        ClockFaceController largeClock;
        ClockFaceEvents events;
        ClockFaceController smallClock;
        ClockFaceEvents events2;
        if (!this.regionSamplingEnabled || this.smallRegionSampler == null || this.largeRegionSampler == null) {
            TypedValue typedValue = new TypedValue();
            this.context.getTheme().resolveAttribute(16844176, typedValue, true);
            int i = typedValue.data;
            this.smallClockIsDark = i == 0;
            this.largeClockIsDark = i == 0;
        } else if (!WallpaperManager.getInstance(this.context).lockScreenWallpaperExists()) {
            RegionSampler regionSampler = this.smallRegionSampler;
            Intrinsics.checkNotNull(regionSampler);
            this.smallClockIsDark = regionSampler.currentRegionDarkness().isDark();
            RegionSampler regionSampler2 = this.largeRegionSampler;
            Intrinsics.checkNotNull(regionSampler2);
            this.largeClockIsDark = regionSampler2.currentRegionDarkness().isDark();
        }
        ClockController clockController = this.clock;
        if (clockController != null && (smallClock = clockController.getSmallClock()) != null && (events2 = smallClock.getEvents()) != null) {
            events2.onRegionDarknessChanged(this.smallClockIsDark);
        }
        ClockController clockController2 = this.clock;
        if (clockController2 == null || (largeClock = clockController2.getLargeClock()) == null || (events = largeClock.getEvents()) == null) {
            return;
        }
        events.onRegionDarknessChanged(this.largeClockIsDark);
    }

    public final void updateFontSizes() {
        ClockFaceController largeClock;
        ClockFaceEvents events;
        ClockFaceController smallClock;
        ClockFaceEvents events2;
        ClockController clockController = this.clock;
        if (clockController != null && (smallClock = clockController.getSmallClock()) != null && (events2 = smallClock.getEvents()) != null) {
            events2.onFontSettingChanged(this.resources.getDimensionPixelSize(R$dimen.small_clock_text_size));
        }
        ClockController clockController2 = this.clock;
        if (clockController2 == null || (largeClock = clockController2.getLargeClock()) == null || (events = largeClock.getEvents()) == null) {
            return;
        }
        events.onFontSettingChanged(this.resources.getDimensionPixelSize(R$dimen.large_clock_text_size));
    }

    public final void updateRegionSamplers(ClockController clockController) {
        ClockFaceController smallClock;
        RegionSampler regionSampler = this.smallRegionSampler;
        if (regionSampler != null) {
            regionSampler.stopRegionSampler();
        }
        RegionSampler regionSampler2 = this.largeRegionSampler;
        if (regionSampler2 != null) {
            regionSampler2.stopRegionSampler();
        }
        this.smallRegionSampler = createRegionSampler((clockController == null || (smallClock = clockController.getSmallClock()) == null) ? null : smallClock.getView(), this.mainExecutor, this.bgExecutor, this.regionSamplingEnabled, new ClockEventController$updateRegionSamplers$1(this));
        View view = null;
        if (clockController != null) {
            ClockFaceController largeClock = clockController.getLargeClock();
            view = null;
            if (largeClock != null) {
                view = largeClock.getView();
            }
        }
        this.largeRegionSampler = createRegionSampler(view, this.mainExecutor, this.bgExecutor, this.regionSamplingEnabled, new ClockEventController$updateRegionSamplers$2(this));
        RegionSampler regionSampler3 = this.smallRegionSampler;
        Intrinsics.checkNotNull(regionSampler3);
        regionSampler3.startRegionSampler();
        RegionSampler regionSampler4 = this.largeRegionSampler;
        Intrinsics.checkNotNull(regionSampler4);
        regionSampler4.startRegionSampler();
        updateColors();
    }
}