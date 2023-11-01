package com.android.systemui.charging;

import android.content.Context;
import android.content.res.Configuration;
import android.os.SystemProperties;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.Utils;
import com.android.systemui.R$dimen;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.surfaceeffects.ripple.RippleShader;
import com.android.systemui.surfaceeffects.ripple.RippleView;
import com.android.systemui.util.leak.RotationUtils;
import com.android.systemui.util.time.SystemClock;
import java.io.PrintWriter;
import java.util.List;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/charging/WiredChargingRippleController.class */
public final class WiredChargingRippleController {
    public final BatteryController batteryController;
    public final ConfigurationController configurationController;
    public final Context context;
    public int debounceLevel;
    public Long lastTriggerTime;
    public float normalizedPortPosX;
    public float normalizedPortPosY;
    public boolean pluggedIn;
    public final boolean rippleEnabled;
    public RippleView rippleView;
    public final SystemClock systemClock;
    public final UiEventLogger uiEventLogger;
    public final WindowManager.LayoutParams windowLayoutParams;
    public final WindowManager windowManager;

    /* loaded from: mainsysui33.jar:com/android/systemui/charging/WiredChargingRippleController$ChargingRippleCommand.class */
    public final class ChargingRippleCommand implements Command {
        public ChargingRippleCommand() {
            WiredChargingRippleController.this = r4;
        }

        public void execute(PrintWriter printWriter, List<String> list) {
            WiredChargingRippleController.this.startRipple();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/charging/WiredChargingRippleController$WiredChargingRippleEvent.class */
    public enum WiredChargingRippleEvent implements UiEventLogger.UiEventEnum {
        CHARGING_RIPPLE_PLAYED(829);
        
        private final int _id;

        WiredChargingRippleEvent(int i) {
            this._id = i;
        }

        public int getId() {
            return this._id;
        }
    }

    public WiredChargingRippleController(CommandRegistry commandRegistry, BatteryController batteryController, ConfigurationController configurationController, FeatureFlags featureFlags, Context context, WindowManager windowManager, SystemClock systemClock, UiEventLogger uiEventLogger) {
        this.batteryController = batteryController;
        this.configurationController = configurationController;
        this.context = context;
        this.windowManager = windowManager;
        this.systemClock = systemClock;
        this.uiEventLogger = uiEventLogger;
        this.rippleEnabled = featureFlags.isEnabled(Flags.INSTANCE.getCHARGING_RIPPLE()) && !SystemProperties.getBoolean("persist.debug.suppress-charging-ripple", false);
        this.normalizedPortPosX = context.getResources().getFloat(R$dimen.physical_charger_port_location_normalized_x);
        this.normalizedPortPosY = context.getResources().getFloat(R$dimen.physical_charger_port_location_normalized_y);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = -1;
        layoutParams.height = -1;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.format = -3;
        layoutParams.type = 2006;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.setTitle("Wired Charging Animation");
        layoutParams.flags = 24;
        layoutParams.setTrustedOverlay();
        this.windowLayoutParams = layoutParams;
        RippleView rippleView = new RippleView(context, (AttributeSet) null);
        RippleView.setupShader$default(rippleView, (RippleShader.RippleShape) null, 1, (Object) null);
        this.rippleView = rippleView;
        this.pluggedIn = batteryController.isPluggedIn();
        commandRegistry.registerCommand("charging-ripple", new Function0<Command>() { // from class: com.android.systemui.charging.WiredChargingRippleController.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                WiredChargingRippleController.this = this;
            }

            /* renamed from: invoke */
            public final Command m1657invoke() {
                return new ChargingRippleCommand();
            }
        });
        updateRippleColor();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.charging.WiredChargingRippleController$registerCallbacks$batteryStateChangeCallback$1.onBatteryLevelChanged(int, boolean, boolean):void] */
    public static final /* synthetic */ BatteryController access$getBatteryController$p(WiredChargingRippleController wiredChargingRippleController) {
        return wiredChargingRippleController.batteryController;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.charging.WiredChargingRippleController$registerCallbacks$configurationChangedListener$1.onConfigChanged(android.content.res.Configuration):void] */
    public static final /* synthetic */ Context access$getContext$p(WiredChargingRippleController wiredChargingRippleController) {
        return wiredChargingRippleController.context;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.charging.WiredChargingRippleController$registerCallbacks$batteryStateChangeCallback$1.onBatteryLevelChanged(int, boolean, boolean):void] */
    public static final /* synthetic */ boolean access$getPluggedIn$p(WiredChargingRippleController wiredChargingRippleController) {
        return wiredChargingRippleController.pluggedIn;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.charging.WiredChargingRippleController$registerCallbacks$batteryStateChangeCallback$1.onBatteryLevelChanged(int, boolean, boolean):void] */
    public static final /* synthetic */ boolean access$getRippleEnabled$p(WiredChargingRippleController wiredChargingRippleController) {
        return wiredChargingRippleController.rippleEnabled;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.charging.WiredChargingRippleController$startRipple$1$onViewAttachedToWindow$1.run():void] */
    public static final /* synthetic */ WindowManager access$getWindowManager$p(WiredChargingRippleController wiredChargingRippleController) {
        return wiredChargingRippleController.windowManager;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.charging.WiredChargingRippleController$startRipple$1.onViewAttachedToWindow(android.view.View):void] */
    public static final /* synthetic */ void access$layoutRipple(WiredChargingRippleController wiredChargingRippleController) {
        wiredChargingRippleController.layoutRipple();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.charging.WiredChargingRippleController$registerCallbacks$configurationChangedListener$1.onConfigChanged(android.content.res.Configuration):void] */
    public static final /* synthetic */ void access$setNormalizedPortPosX$p(WiredChargingRippleController wiredChargingRippleController, float f) {
        wiredChargingRippleController.normalizedPortPosX = f;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.charging.WiredChargingRippleController$registerCallbacks$configurationChangedListener$1.onConfigChanged(android.content.res.Configuration):void] */
    public static final /* synthetic */ void access$setNormalizedPortPosY$p(WiredChargingRippleController wiredChargingRippleController, float f) {
        wiredChargingRippleController.normalizedPortPosY = f;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.charging.WiredChargingRippleController$registerCallbacks$batteryStateChangeCallback$1.onBatteryLevelChanged(int, boolean, boolean):void] */
    public static final /* synthetic */ void access$setPluggedIn$p(WiredChargingRippleController wiredChargingRippleController, boolean z) {
        wiredChargingRippleController.pluggedIn = z;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.charging.WiredChargingRippleController$registerCallbacks$configurationChangedListener$1.onThemeChanged():void, com.android.systemui.charging.WiredChargingRippleController$registerCallbacks$configurationChangedListener$1.onUiModeChanged():void] */
    public static final /* synthetic */ void access$updateRippleColor(WiredChargingRippleController wiredChargingRippleController) {
        wiredChargingRippleController.updateRippleColor();
    }

    @VisibleForTesting
    public static /* synthetic */ void getRippleView$annotations() {
    }

    public final RippleView getRippleView() {
        return this.rippleView;
    }

    public final void layoutRipple() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.context.getDisplay().getRealMetrics(displayMetrics);
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        float max = Integer.max(i, i2) * 2.0f;
        this.rippleView.setMaxSize(max, max);
        int exactRotation = RotationUtils.getExactRotation(this.context);
        if (exactRotation == 1) {
            this.rippleView.setCenter(i * this.normalizedPortPosY, i2 * (1 - this.normalizedPortPosX));
        } else if (exactRotation == 2) {
            float f = i;
            float f2 = 1;
            this.rippleView.setCenter(f * (f2 - this.normalizedPortPosX), i2 * (f2 - this.normalizedPortPosY));
        } else if (exactRotation != 3) {
            this.rippleView.setCenter(i * this.normalizedPortPosX, i2 * this.normalizedPortPosY);
        } else {
            this.rippleView.setCenter(i * (1 - this.normalizedPortPosY), i2 * this.normalizedPortPosX);
        }
    }

    public final void registerCallbacks() {
        this.batteryController.addCallback(new BatteryController.BatteryStateChangeCallback() { // from class: com.android.systemui.charging.WiredChargingRippleController$registerCallbacks$batteryStateChangeCallback$1
            public void onBatteryLevelChanged(int i, boolean z, boolean z2) {
                if (!WiredChargingRippleController.access$getRippleEnabled$p(WiredChargingRippleController.this) || WiredChargingRippleController.access$getBatteryController$p(WiredChargingRippleController.this).isPluggedInWireless() || WiredChargingRippleController.access$getBatteryController$p(WiredChargingRippleController.this).isChargingSourceDock()) {
                    return;
                }
                if (!WiredChargingRippleController.access$getPluggedIn$p(WiredChargingRippleController.this) && z) {
                    WiredChargingRippleController.this.startRippleWithDebounce$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
                }
                WiredChargingRippleController.access$setPluggedIn$p(WiredChargingRippleController.this, z);
            }
        });
        this.configurationController.addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.charging.WiredChargingRippleController$registerCallbacks$configurationChangedListener$1
            public void onConfigChanged(Configuration configuration) {
                WiredChargingRippleController wiredChargingRippleController = WiredChargingRippleController.this;
                WiredChargingRippleController.access$setNormalizedPortPosX$p(wiredChargingRippleController, WiredChargingRippleController.access$getContext$p(wiredChargingRippleController).getResources().getFloat(R$dimen.physical_charger_port_location_normalized_x));
                WiredChargingRippleController wiredChargingRippleController2 = WiredChargingRippleController.this;
                WiredChargingRippleController.access$setNormalizedPortPosY$p(wiredChargingRippleController2, WiredChargingRippleController.access$getContext$p(wiredChargingRippleController2).getResources().getFloat(R$dimen.physical_charger_port_location_normalized_y));
            }

            public void onThemeChanged() {
                WiredChargingRippleController.access$updateRippleColor(WiredChargingRippleController.this);
            }

            public void onUiModeChanged() {
                WiredChargingRippleController.access$updateRippleColor(WiredChargingRippleController.this);
            }
        });
    }

    public final void startRipple() {
        if (this.rippleEnabled && !this.rippleView.rippleInProgress() && this.rippleView.getParent() == null) {
            this.windowLayoutParams.packageName = this.context.getOpPackageName();
            this.rippleView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.charging.WiredChargingRippleController$startRipple$1
                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewAttachedToWindow(View view) {
                    WiredChargingRippleController.access$layoutRipple(WiredChargingRippleController.this);
                    RippleView rippleView = WiredChargingRippleController.this.getRippleView();
                    final WiredChargingRippleController wiredChargingRippleController = WiredChargingRippleController.this;
                    rippleView.startRipple(new Runnable() { // from class: com.android.systemui.charging.WiredChargingRippleController$startRipple$1$onViewAttachedToWindow$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            WiredChargingRippleController.access$getWindowManager$p(WiredChargingRippleController.this).removeView(WiredChargingRippleController.this.getRippleView());
                        }
                    });
                    WiredChargingRippleController.this.getRippleView().removeOnAttachStateChangeListener(this);
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewDetachedFromWindow(View view) {
                }
            });
            this.windowManager.addView(this.rippleView, this.windowLayoutParams);
            this.uiEventLogger.log(WiredChargingRippleEvent.CHARGING_RIPPLE_PLAYED);
        }
    }

    public final void startRippleWithDebounce$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        long elapsedRealtime = this.systemClock.elapsedRealtime();
        Long l = this.lastTriggerTime;
        if (l != null) {
            Intrinsics.checkNotNull(l);
            if (elapsedRealtime - l.longValue() <= ((double) RecyclerView.MAX_SCROLL_DURATION) * Math.pow(2.0d, this.debounceLevel)) {
                this.debounceLevel = Math.min(3, this.debounceLevel + 1);
                this.lastTriggerTime = Long.valueOf(elapsedRealtime);
            }
        }
        startRipple();
        this.debounceLevel = 0;
        this.lastTriggerTime = Long.valueOf(elapsedRealtime);
    }

    public final void updateRippleColor() {
        RippleView.setColor$default(this.rippleView, Utils.getColorAttr(this.context, 16843829).getDefaultColor(), 0, 2, (Object) null);
    }
}