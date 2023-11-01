package com.android.systemui.power;

import android.app.Dialog;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.Annotation;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.Log;
import android.util.Slog;
import android.view.View;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.Utils;
import com.android.settingslib.fuelgauge.BatterySaverUtils;
import com.android.settingslib.utils.PowerUtil;
import com.android.systemui.R$bool;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.R$style;
import com.android.systemui.SystemUIApplication;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.power.PowerNotificationWarnings;
import com.android.systemui.power.PowerUI;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.util.NotificationChannels;
import com.android.systemui.volume.Events;
import dagger.Lazy;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

/* loaded from: mainsysui33.jar:com/android/systemui/power/PowerNotificationWarnings.class */
public class PowerNotificationWarnings implements PowerUI.WarningsUI {
    private static final String ACTION_AUTO_SAVER_NO_THANKS = "PNW.autoSaverNoThanks";
    private static final String ACTION_CLICKED_TEMP_WARNING = "PNW.clickedTempWarning";
    private static final String ACTION_CLICKED_THERMAL_SHUTDOWN_WARNING = "PNW.clickedThermalShutdownWarning";
    private static final String ACTION_DISMISSED_TEMP_WARNING = "PNW.dismissedTempWarning";
    private static final String ACTION_DISMISSED_THERMAL_SHUTDOWN_WARNING = "PNW.dismissedThermalShutdownWarning";
    private static final String ACTION_DISMISSED_WARNING = "PNW.dismissedWarning";
    private static final String ACTION_DISMISS_AUTO_SAVER_SUGGESTION = "PNW.dismissAutoSaverSuggestion";
    private static final String ACTION_ENABLE_AUTO_SAVER = "PNW.enableAutoSaver";
    private static final String ACTION_ENABLE_SEVERE_BATTERY_DIALOG = "PNW.enableSevereDialog";
    private static final String ACTION_SHOW_AUTO_SAVER_SUGGESTION = "PNW.autoSaverSuggestion";
    private static final String ACTION_SHOW_BATTERY_SAVER_SETTINGS = "PNW.batterySaverSettings";
    private static final String ACTION_SHOW_START_SAVER_CONFIRMATION = "PNW.startSaverConfirmation";
    private static final String ACTION_START_SAVER = "PNW.startSaver";
    private static final String BATTERY_SAVER_DESCRIPTION_URL_KEY = "url";
    public static final String BATTERY_SAVER_SCHEDULE_SCREEN_INTENT_ACTION = "com.android.settings.BATTERY_SAVER_SCHEDULE_SETTINGS";
    public static final String EXTRA_CONFIRM_ONLY = "extra_confirm_only";
    private static final String EXTRA_SCHEDULED_BY_PERCENTAGE = "extra_scheduled_by_percentage";
    private static final String INTERACTION_JANK_TAG = "start_power_saver";
    private static final int SHOWING_AUTO_SAVER_SUGGESTION = 4;
    private static final int SHOWING_INVALID_CHARGER = 3;
    private static final int SHOWING_NOTHING = 0;
    private static final int SHOWING_WARNING = 1;
    private static final String TAG = "PowerUI.Notification";
    private static final String TAG_AUTO_SAVER = "auto_saver";
    private static final String TAG_BATTERY = "low_battery";
    private static final String TAG_TEMPERATURE = "high_temp";
    private ActivityStarter mActivityStarter;
    private final Lazy<BatteryController> mBatteryControllerLazy;
    private int mBatteryLevel;
    private final BroadcastSender mBroadcastSender;
    private int mBucket;
    private final Context mContext;
    private BatteryStateSnapshot mCurrentBatterySnapshot;
    private final DialogLaunchAnimator mDialogLaunchAnimator;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private SystemUIDialog mHighTempDialog;
    private boolean mHighTempWarning;
    private boolean mInvalidCharger;
    private final KeyguardManager mKeyguard;
    private final NotificationManager mNoMan;
    private final Intent mOpenBatterySaverSettings;
    private final Intent mOpenBatterySettings;
    private boolean mPlaySound;
    private final PowerManager mPowerMan;
    private final Receiver mReceiver;
    private SystemUIDialog mSaverConfirmation;
    private SystemUIDialog mSaverEnabledConfirmation;
    private long mScreenOffTime;
    private boolean mShowAutoSaverSuggestion;
    private int mShowing;
    private SystemUIDialog mThermalShutdownDialog;
    private final UiEventLogger mUiEventLogger;
    public SystemUIDialog mUsbHighTempDialog;
    private final boolean mUseSevereDialog;
    private boolean mWarning;
    private long mWarningTriggerTimeMs;
    private static final boolean DEBUG = PowerUI.DEBUG;
    private static final String[] SHOWING_STRINGS = {"SHOWING_NOTHING", "SHOWING_WARNING", "SHOWING_SAVER", "SHOWING_INVALID_CHARGER", "SHOWING_AUTO_SAVER_SUGGESTION"};
    private static final AudioAttributes AUDIO_ATTRIBUTES = new AudioAttributes.Builder().setContentType(4).setUsage(13).build();

    /* renamed from: com.android.systemui.power.PowerNotificationWarnings$1 */
    /* loaded from: mainsysui33.jar:com/android/systemui/power/PowerNotificationWarnings$1.class */
    public class AnonymousClass1 implements DialogInterface.OnClickListener {
        public final /* synthetic */ String val$url;

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.power.PowerNotificationWarnings$1$$ExternalSyntheticLambda0.onActivityStarted(int):void] */
        /* renamed from: $r8$lambda$oAjnQNo4bMp0-N05bp2blBkxD04 */
        public static /* synthetic */ void m3635$r8$lambda$oAjnQNo4bMp0N05bp2blBkxD04(AnonymousClass1 anonymousClass1, int i) {
            anonymousClass1.lambda$onClick$0(i);
        }

        public AnonymousClass1(String str) {
            PowerNotificationWarnings.this = r4;
            this.val$url = str;
        }

        public /* synthetic */ void lambda$onClick$0(int i) {
            PowerNotificationWarnings.this.mHighTempDialog = null;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            PowerNotificationWarnings.this.mActivityStarter.startActivity(new Intent("android.intent.action.VIEW").setData(Uri.parse(this.val$url)).setFlags(268435456), true, new ActivityStarter.Callback() { // from class: com.android.systemui.power.PowerNotificationWarnings$1$$ExternalSyntheticLambda0
                @Override // com.android.systemui.plugins.ActivityStarter.Callback
                public final void onActivityStarted(int i2) {
                    PowerNotificationWarnings.AnonymousClass1.m3635$r8$lambda$oAjnQNo4bMp0N05bp2blBkxD04(PowerNotificationWarnings.AnonymousClass1.this, i2);
                }
            });
        }
    }

    /* renamed from: com.android.systemui.power.PowerNotificationWarnings$2 */
    /* loaded from: mainsysui33.jar:com/android/systemui/power/PowerNotificationWarnings$2.class */
    public class AnonymousClass2 implements DialogInterface.OnClickListener {
        public final /* synthetic */ String val$url;

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.power.PowerNotificationWarnings$2$$ExternalSyntheticLambda0.onActivityStarted(int):void] */
        /* renamed from: $r8$lambda$M6dTW-gRkKSWSTvLHmHmL3S3mWg */
        public static /* synthetic */ void m3636$r8$lambda$M6dTWgRkKSWSTvLHmHmL3S3mWg(AnonymousClass2 anonymousClass2, int i) {
            anonymousClass2.lambda$onClick$0(i);
        }

        public AnonymousClass2(String str) {
            PowerNotificationWarnings.this = r4;
            this.val$url = str;
        }

        public /* synthetic */ void lambda$onClick$0(int i) {
            PowerNotificationWarnings.this.mThermalShutdownDialog = null;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            PowerNotificationWarnings.this.mActivityStarter.startActivity(new Intent("android.intent.action.VIEW").setData(Uri.parse(this.val$url)).setFlags(268435456), true, new ActivityStarter.Callback() { // from class: com.android.systemui.power.PowerNotificationWarnings$2$$ExternalSyntheticLambda0
                @Override // com.android.systemui.plugins.ActivityStarter.Callback
                public final void onActivityStarted(int i2) {
                    PowerNotificationWarnings.AnonymousClass2.m3636$r8$lambda$M6dTWgRkKSWSTvLHmHmL3S3mWg(PowerNotificationWarnings.AnonymousClass2.this, i2);
                }
            });
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/power/PowerNotificationWarnings$Receiver.class */
    public final class Receiver extends BroadcastReceiver {
        public Receiver() {
            PowerNotificationWarnings.this = r4;
        }

        public void init() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(PowerNotificationWarnings.ACTION_SHOW_BATTERY_SAVER_SETTINGS);
            intentFilter.addAction(PowerNotificationWarnings.ACTION_START_SAVER);
            intentFilter.addAction(PowerNotificationWarnings.ACTION_DISMISSED_WARNING);
            intentFilter.addAction(PowerNotificationWarnings.ACTION_CLICKED_TEMP_WARNING);
            intentFilter.addAction(PowerNotificationWarnings.ACTION_DISMISSED_TEMP_WARNING);
            intentFilter.addAction(PowerNotificationWarnings.ACTION_CLICKED_THERMAL_SHUTDOWN_WARNING);
            intentFilter.addAction(PowerNotificationWarnings.ACTION_DISMISSED_THERMAL_SHUTDOWN_WARNING);
            intentFilter.addAction(PowerNotificationWarnings.ACTION_SHOW_START_SAVER_CONFIRMATION);
            intentFilter.addAction(PowerNotificationWarnings.ACTION_SHOW_AUTO_SAVER_SUGGESTION);
            intentFilter.addAction(PowerNotificationWarnings.ACTION_ENABLE_AUTO_SAVER);
            intentFilter.addAction(PowerNotificationWarnings.ACTION_AUTO_SAVER_NO_THANKS);
            intentFilter.addAction(PowerNotificationWarnings.ACTION_DISMISS_AUTO_SAVER_SUGGESTION);
            PowerNotificationWarnings.this.mContext.registerReceiverAsUser(this, UserHandle.ALL, intentFilter, "android.permission.DEVICE_POWER", PowerNotificationWarnings.this.mHandler, 2);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Slog.i(PowerNotificationWarnings.TAG, "Received " + action);
            if (action.equals(PowerNotificationWarnings.ACTION_SHOW_BATTERY_SAVER_SETTINGS)) {
                PowerNotificationWarnings.this.logEvent(BatteryWarningEvents$LowBatteryWarningEvent.LOW_BATTERY_NOTIFICATION_SETTINGS);
                PowerNotificationWarnings.this.dismissLowBatteryNotification();
                PowerNotificationWarnings.this.mContext.startActivityAsUser(PowerNotificationWarnings.this.mOpenBatterySaverSettings, UserHandle.CURRENT);
            } else if (action.equals(PowerNotificationWarnings.ACTION_START_SAVER)) {
                PowerNotificationWarnings.this.logEvent(BatteryWarningEvents$LowBatteryWarningEvent.LOW_BATTERY_NOTIFICATION_TURN_ON);
                PowerNotificationWarnings.this.setSaverMode(true, true);
                PowerNotificationWarnings.this.dismissLowBatteryNotification();
            } else if (action.equals(PowerNotificationWarnings.ACTION_SHOW_START_SAVER_CONFIRMATION)) {
                PowerNotificationWarnings.this.dismissLowBatteryNotification();
                PowerNotificationWarnings.this.showStartSaverConfirmation(intent.getExtras());
            } else if (action.equals(PowerNotificationWarnings.ACTION_DISMISSED_WARNING)) {
                PowerNotificationWarnings.this.logEvent(BatteryWarningEvents$LowBatteryWarningEvent.LOW_BATTERY_NOTIFICATION_CANCEL);
                PowerNotificationWarnings.this.dismissLowBatteryWarning();
            } else if (PowerNotificationWarnings.ACTION_CLICKED_TEMP_WARNING.equals(action)) {
                PowerNotificationWarnings.this.dismissHighTemperatureWarningInternal();
                PowerNotificationWarnings.this.showHighTemperatureDialog();
            } else if (PowerNotificationWarnings.ACTION_DISMISSED_TEMP_WARNING.equals(action)) {
                PowerNotificationWarnings.this.dismissHighTemperatureWarningInternal();
            } else if (PowerNotificationWarnings.ACTION_CLICKED_THERMAL_SHUTDOWN_WARNING.equals(action)) {
                PowerNotificationWarnings.this.dismissThermalShutdownWarning();
                PowerNotificationWarnings.this.showThermalShutdownDialog();
            } else if (PowerNotificationWarnings.ACTION_DISMISSED_THERMAL_SHUTDOWN_WARNING.equals(action)) {
                PowerNotificationWarnings.this.dismissThermalShutdownWarning();
            } else if (PowerNotificationWarnings.ACTION_SHOW_AUTO_SAVER_SUGGESTION.equals(action)) {
                PowerNotificationWarnings.this.showAutoSaverSuggestion();
            } else if (PowerNotificationWarnings.ACTION_DISMISS_AUTO_SAVER_SUGGESTION.equals(action)) {
                PowerNotificationWarnings.this.dismissAutoSaverSuggestion();
            } else if (PowerNotificationWarnings.ACTION_ENABLE_AUTO_SAVER.equals(action)) {
                PowerNotificationWarnings.this.dismissAutoSaverSuggestion();
                PowerNotificationWarnings.this.startBatterySaverSchedulePage();
            } else if (PowerNotificationWarnings.ACTION_AUTO_SAVER_NO_THANKS.equals(action)) {
                PowerNotificationWarnings.this.dismissAutoSaverSuggestion();
                BatterySaverUtils.suppressAutoBatterySaver(context);
            }
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.power.PowerNotificationWarnings$$ExternalSyntheticLambda7.onClick(android.content.DialogInterface, int):void] */
    /* renamed from: $r8$lambda$-mVqW3gRbkEne8QwZFv1UdTMCN8 */
    public static /* synthetic */ void m3611$r8$lambda$mVqW3gRbkEne8QwZFv1UdTMCN8(PowerNotificationWarnings powerNotificationWarnings, DialogInterface dialogInterface, int i) {
        powerNotificationWarnings.lambda$showStartSaverConfirmation$9(dialogInterface, i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.power.PowerNotificationWarnings$$ExternalSyntheticLambda2.onClick(android.content.DialogInterface, int):void] */
    /* renamed from: $r8$lambda$A-U3_uT6uZ3uHxf6PyohDdjqwbc */
    public static /* synthetic */ void m3612$r8$lambda$AU3_uT6uZ3uHxf6PyohDdjqwbc(PowerNotificationWarnings powerNotificationWarnings, DialogInterface dialogInterface, int i) {
        powerNotificationWarnings.lambda$showUsbHighTemperatureAlarmInternal$5(dialogInterface, i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.power.PowerNotificationWarnings$$ExternalSyntheticLambda5.onClick(android.content.DialogInterface, int):void] */
    public static /* synthetic */ void $r8$lambda$EUubSN7qVaz2GhFRG4Eijr_svoY(PowerNotificationWarnings powerNotificationWarnings, int i, int i2, DialogInterface dialogInterface, int i3) {
        powerNotificationWarnings.lambda$showStartSaverConfirmation$7(i, i2, dialogInterface, i3);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.power.PowerNotificationWarnings$$ExternalSyntheticLambda9.onDismiss(android.content.DialogInterface):void] */
    /* renamed from: $r8$lambda$KD-9LowwRmJbXjNV72LqYrHoaWY */
    public static /* synthetic */ void m3613$r8$lambda$KD9LowwRmJbXjNV72LqYrHoaWY(PowerNotificationWarnings powerNotificationWarnings, DialogInterface dialogInterface) {
        powerNotificationWarnings.lambda$showHighTemperatureDialog$0(dialogInterface);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.power.PowerNotificationWarnings$$ExternalSyntheticLambda10.run():void] */
    /* renamed from: $r8$lambda$OAwUN_OHnSI9S6XRP6K-TTWkM94 */
    public static /* synthetic */ void m3614$r8$lambda$OAwUN_OHnSI9S6XRP6KTTWkM94(PowerNotificationWarnings powerNotificationWarnings) {
        powerNotificationWarnings.lambda$showUsbHighTemperatureAlarm$2();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.power.PowerNotificationWarnings$$ExternalSyntheticLambda3.onDismiss(android.content.DialogInterface):void] */
    public static /* synthetic */ void $r8$lambda$boN4KTlUPlRx7MXCH14MFcZqhEA(PowerNotificationWarnings powerNotificationWarnings, DialogInterface dialogInterface) {
        powerNotificationWarnings.lambda$showUsbHighTemperatureAlarmInternal$6(dialogInterface);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.power.PowerNotificationWarnings$$ExternalSyntheticLambda4.onDismiss(android.content.DialogInterface):void] */
    public static /* synthetic */ void $r8$lambda$fGCxfjSnE2zNj45qDgLHyRYHcXg(PowerNotificationWarnings powerNotificationWarnings, DialogInterface dialogInterface) {
        powerNotificationWarnings.lambda$showThermalShutdownDialog$1(dialogInterface);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.power.PowerNotificationWarnings$$ExternalSyntheticLambda8.onDismiss(android.content.DialogInterface):void] */
    public static /* synthetic */ void $r8$lambda$iC0GAri_wbtmyOvisiUSJr2uYCY(PowerNotificationWarnings powerNotificationWarnings, DialogInterface dialogInterface) {
        powerNotificationWarnings.lambda$showStartSaverConfirmation$10(dialogInterface);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.power.PowerNotificationWarnings$$ExternalSyntheticLambda6.onClick(android.content.DialogInterface, int):void] */
    public static /* synthetic */ void $r8$lambda$p5salmuhygAMPQaKCdlpWu7Fn20(PowerNotificationWarnings powerNotificationWarnings, DialogInterface dialogInterface, int i) {
        powerNotificationWarnings.lambda$showStartSaverConfirmation$8(dialogInterface, i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.power.PowerNotificationWarnings$$ExternalSyntheticLambda1.onClick(android.content.DialogInterface, int):void] */
    /* renamed from: $r8$lambda$rX89-K38-RQSB8X0yDYx6nhAd5Q */
    public static /* synthetic */ void m3615$r8$lambda$rX89K38RQSB8X0yDYx6nhAd5Q(PowerNotificationWarnings powerNotificationWarnings, DialogInterface dialogInterface, int i) {
        powerNotificationWarnings.lambda$showUsbHighTemperatureAlarmInternal$3(dialogInterface, i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.power.PowerNotificationWarnings$$ExternalSyntheticLambda0.onActivityStarted(int):void] */
    public static /* synthetic */ void $r8$lambda$v3iOoNUgyUrwTaTpFs6vuu6BZYc(PowerNotificationWarnings powerNotificationWarnings, int i) {
        powerNotificationWarnings.lambda$showUsbHighTemperatureAlarmInternal$4(i);
    }

    public PowerNotificationWarnings(Context context, ActivityStarter activityStarter, BroadcastSender broadcastSender, Lazy<BatteryController> lazy, DialogLaunchAnimator dialogLaunchAnimator, UiEventLogger uiEventLogger) {
        Receiver receiver = new Receiver();
        this.mReceiver = receiver;
        this.mOpenBatterySettings = settings("android.intent.action.POWER_USAGE_SUMMARY");
        this.mOpenBatterySaverSettings = settings("android.settings.BATTERY_SAVER_SETTINGS");
        this.mContext = context;
        this.mNoMan = (NotificationManager) context.getSystemService(NotificationManager.class);
        this.mPowerMan = (PowerManager) context.getSystemService("power");
        this.mKeyguard = (KeyguardManager) context.getSystemService(KeyguardManager.class);
        receiver.init();
        this.mActivityStarter = activityStarter;
        this.mBroadcastSender = broadcastSender;
        this.mBatteryControllerLazy = lazy;
        this.mDialogLaunchAnimator = dialogLaunchAnimator;
        this.mUseSevereDialog = context.getResources().getBoolean(R$bool.config_severe_battery_dialog);
        this.mUiEventLogger = uiEventLogger;
    }

    public void dismissAutoSaverSuggestion() {
        this.mShowAutoSaverSuggestion = false;
        updateNotification();
    }

    public void dismissHighTemperatureWarningInternal() {
        this.mNoMan.cancelAsUser(TAG_TEMPERATURE, 4, UserHandle.ALL);
        this.mHighTempWarning = false;
    }

    private void dismissInvalidChargerNotification() {
        if (this.mInvalidCharger) {
            Slog.i(TAG, "dismissing invalid charger notification");
        }
        this.mInvalidCharger = false;
        updateNotification();
    }

    public void dismissLowBatteryNotification() {
        if (this.mWarning) {
            Slog.i(TAG, "dismissing low battery notification");
        }
        this.mWarning = false;
        updateNotification();
    }

    private CharSequence getBatterySaverDescription() {
        Annotation[] annotationArr;
        String charSequence = this.mContext.getText(R$string.help_uri_battery_saver_learn_more_link_target).toString();
        if (TextUtils.isEmpty(charSequence)) {
            return this.mContext.getText(R$string.battery_low_intro);
        }
        SpannableString spannableString = new SpannableString(this.mContext.getText(17039809));
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(spannableString);
        for (Annotation annotation : (Annotation[]) spannableString.getSpans(0, spannableString.length(), Annotation.class)) {
            if (BATTERY_SAVER_DESCRIPTION_URL_KEY.equals(annotation.getValue())) {
                int spanStart = spannableString.getSpanStart(annotation);
                int spanEnd = spannableString.getSpanEnd(annotation);
                URLSpan uRLSpan = new URLSpan(charSequence) { // from class: com.android.systemui.power.PowerNotificationWarnings.3
                    {
                        PowerNotificationWarnings.this = this;
                    }

                    @Override // android.text.style.URLSpan, android.text.style.ClickableSpan
                    public void onClick(View view) {
                        if (PowerNotificationWarnings.this.mSaverConfirmation != null) {
                            PowerNotificationWarnings.this.mSaverConfirmation.dismiss();
                        }
                        PowerNotificationWarnings.this.mBroadcastSender.sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS").setFlags(268435456));
                        Uri parse = Uri.parse(getURL());
                        Context context = view.getContext();
                        Intent flags = new Intent("android.intent.action.VIEW", parse).setFlags(268435456);
                        try {
                            context.startActivity(flags);
                        } catch (ActivityNotFoundException e) {
                            Log.w(PowerNotificationWarnings.TAG, "Activity was not found for intent, " + flags.toString());
                        }
                    }

                    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                    public void updateDrawState(TextPaint textPaint) {
                        super.updateDrawState(textPaint);
                        textPaint.setUnderlineText(false);
                    }
                };
                spannableStringBuilder.setSpan(uRLSpan, spanStart, spanEnd, spannableString.getSpanFlags(uRLSpan));
            }
        }
        return spannableStringBuilder;
    }

    private String getHybridContentString(String str) {
        return PowerUtil.getBatteryRemainingStringFormatted(this.mContext, this.mCurrentBatterySnapshot.getTimeRemainingMillis(), str, this.mCurrentBatterySnapshot.isBasedOnUsage());
    }

    private int getLowBatteryAutoTriggerDefaultLevel() {
        return this.mContext.getResources().getInteger(17694878);
    }

    private boolean hasBatterySettings() {
        return this.mOpenBatterySettings.resolveActivity(this.mContext.getPackageManager()) != null;
    }

    private boolean isEnglishLocale() {
        return Objects.equals(Locale.getDefault().getLanguage(), Locale.ENGLISH.getLanguage());
    }

    private boolean isScheduledByPercentage() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        boolean z = false;
        if (Settings.Global.getInt(contentResolver, "automatic_power_save_mode", 0) == 0) {
            z = false;
            if (Settings.Global.getInt(contentResolver, "low_power_trigger_level", 0) > 0) {
                z = true;
            }
        }
        return z;
    }

    public /* synthetic */ void lambda$showHighTemperatureDialog$0(DialogInterface dialogInterface) {
        this.mHighTempDialog = null;
    }

    public /* synthetic */ void lambda$showStartSaverConfirmation$10(DialogInterface dialogInterface) {
        this.mSaverConfirmation = null;
        logEvent(BatteryWarningEvents$LowBatteryWarningEvent.SAVER_CONFIRM_DISMISS);
    }

    public /* synthetic */ void lambda$showStartSaverConfirmation$7(int i, int i2, DialogInterface dialogInterface, int i3) {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        Settings.Global.putInt(contentResolver, "automatic_power_save_mode", i);
        Settings.Global.putInt(contentResolver, "low_power_trigger_level", i2);
        Settings.Secure.putIntForUser(contentResolver, "low_power_warning_acknowledged", 1, -2);
    }

    public /* synthetic */ void lambda$showStartSaverConfirmation$8(DialogInterface dialogInterface, int i) {
        setSaverMode(true, false);
        logEvent(BatteryWarningEvents$LowBatteryWarningEvent.SAVER_CONFIRM_OK);
    }

    public /* synthetic */ void lambda$showStartSaverConfirmation$9(DialogInterface dialogInterface, int i) {
        logEvent(BatteryWarningEvents$LowBatteryWarningEvent.SAVER_CONFIRM_CANCEL);
    }

    public /* synthetic */ void lambda$showThermalShutdownDialog$1(DialogInterface dialogInterface) {
        this.mThermalShutdownDialog = null;
    }

    public /* synthetic */ void lambda$showUsbHighTemperatureAlarmInternal$3(DialogInterface dialogInterface, int i) {
        this.mUsbHighTempDialog = null;
    }

    public /* synthetic */ void lambda$showUsbHighTemperatureAlarmInternal$4(int i) {
        this.mUsbHighTempDialog = null;
    }

    public /* synthetic */ void lambda$showUsbHighTemperatureAlarmInternal$5(DialogInterface dialogInterface, int i) {
        String string = this.mContext.getString(R$string.high_temp_alarm_help_url);
        Intent intent = new Intent();
        intent.setClassName("com.android.settings", "com.android.settings.HelpTrampoline");
        intent.putExtra("android.intent.extra.TEXT", string);
        this.mActivityStarter.startActivity(intent, true, new ActivityStarter.Callback() { // from class: com.android.systemui.power.PowerNotificationWarnings$$ExternalSyntheticLambda0
            @Override // com.android.systemui.plugins.ActivityStarter.Callback
            public final void onActivityStarted(int i2) {
                PowerNotificationWarnings.$r8$lambda$v3iOoNUgyUrwTaTpFs6vuu6BZYc(PowerNotificationWarnings.this, i2);
            }
        });
    }

    public /* synthetic */ void lambda$showUsbHighTemperatureAlarmInternal$6(DialogInterface dialogInterface) {
        this.mUsbHighTempDialog = null;
        Events.writeEvent(20, new Object[]{9, Boolean.valueOf(this.mKeyguard.isKeyguardLocked())});
    }

    public void logEvent(BatteryWarningEvents$LowBatteryWarningEvent batteryWarningEvents$LowBatteryWarningEvent) {
        UiEventLogger uiEventLogger = this.mUiEventLogger;
        if (uiEventLogger != null) {
            uiEventLogger.log(batteryWarningEvents$LowBatteryWarningEvent);
        }
    }

    private PendingIntent pendingBroadcast(String str) {
        return PendingIntent.getBroadcastAsUser(this.mContext, 0, new Intent(str).setPackage(this.mContext.getPackageName()).setFlags(268435456), 67108864, UserHandle.CURRENT);
    }

    public void setSaverMode(boolean z, boolean z2) {
        BatterySaverUtils.setPowerSaveMode(this.mContext, z, z2);
    }

    private static Intent settings(String str) {
        return new Intent(str).setFlags(1551892480);
    }

    public void showAutoSaverSuggestion() {
        this.mShowAutoSaverSuggestion = true;
        updateNotification();
    }

    private void showAutoSaverSuggestionNotification() {
        String string = this.mContext.getString(R$string.auto_saver_text);
        Notification.Builder contentText = new Notification.Builder(this.mContext, NotificationChannels.HINTS).setSmallIcon(R$drawable.ic_power_saver).setWhen(0L).setShowWhen(false).setContentTitle(this.mContext.getString(R$string.auto_saver_title)).setStyle(new Notification.BigTextStyle().bigText(string)).setContentText(string);
        contentText.setContentIntent(pendingBroadcast(ACTION_ENABLE_AUTO_SAVER));
        contentText.setDeleteIntent(pendingBroadcast(ACTION_DISMISS_AUTO_SAVER_SUGGESTION));
        contentText.addAction(0, this.mContext.getString(R$string.no_auto_saver_action), pendingBroadcast(ACTION_AUTO_SAVER_NO_THANKS));
        SystemUIApplication.overrideNotificationAppName(this.mContext, contentText, false);
        this.mNoMan.notifyAsUser(TAG_AUTO_SAVER, 49, contentText.build(), UserHandle.ALL);
    }

    public void showHighTemperatureDialog() {
        if (this.mHighTempDialog != null) {
            return;
        }
        SystemUIDialog systemUIDialog = new SystemUIDialog(this.mContext);
        systemUIDialog.setIconAttribute(16843605);
        systemUIDialog.setTitle(R$string.high_temp_title);
        systemUIDialog.setMessage(R$string.high_temp_dialog_message);
        systemUIDialog.setPositiveButton(17039370, (DialogInterface.OnClickListener) null);
        systemUIDialog.setShowForAllUsers(true);
        systemUIDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.power.PowerNotificationWarnings$$ExternalSyntheticLambda9
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                PowerNotificationWarnings.m3613$r8$lambda$KD9LowwRmJbXjNV72LqYrHoaWY(PowerNotificationWarnings.this, dialogInterface);
            }
        });
        String string = this.mContext.getString(R$string.high_temp_dialog_help_url);
        if (!string.isEmpty()) {
            systemUIDialog.setNeutralButton(R$string.high_temp_dialog_help_text, new AnonymousClass1(string));
        }
        systemUIDialog.show();
        this.mHighTempDialog = systemUIDialog;
    }

    private void showInvalidChargerNotification() {
        Notification.Builder color = new Notification.Builder(this.mContext, NotificationChannels.ALERTS).setSmallIcon(R$drawable.ic_power_low).setWhen(0L).setShowWhen(false).setOngoing(true).setContentTitle(this.mContext.getString(R$string.invalid_charger_title)).setContentText(this.mContext.getString(R$string.invalid_charger_text)).setColor(this.mContext.getColor(17170460));
        SystemUIApplication.overrideNotificationAppName(this.mContext, color, false);
        Notification build = color.build();
        this.mNoMan.cancelAsUser(TAG_BATTERY, 3, UserHandle.ALL);
        this.mNoMan.notifyAsUser(TAG_BATTERY, 2, build, UserHandle.ALL);
    }

    private boolean showSevereLowBatteryDialog() {
        return this.mBucket < -1 && this.mUseSevereDialog;
    }

    public void showStartSaverConfirmation(Bundle bundle) {
        if (this.mSaverConfirmation != null) {
            return;
        }
        SystemUIDialog systemUIDialog = new SystemUIDialog(this.mContext);
        boolean z = bundle.getBoolean(EXTRA_CONFIRM_ONLY);
        final int i = bundle.getInt("extra_power_save_mode_trigger", 0);
        final int i2 = bundle.getInt("extra_power_save_mode_trigger_level", 0);
        systemUIDialog.setMessage(getBatterySaverDescription());
        if (isEnglishLocale()) {
            systemUIDialog.setMessageHyphenationFrequency(0);
        }
        systemUIDialog.setMessageMovementMethod(LinkMovementMethod.getInstance());
        if (z) {
            systemUIDialog.setTitle(R$string.battery_saver_confirmation_title_generic);
            systemUIDialog.setPositiveButton(17040090, new DialogInterface.OnClickListener() { // from class: com.android.systemui.power.PowerNotificationWarnings$$ExternalSyntheticLambda5
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i3) {
                    PowerNotificationWarnings.$r8$lambda$EUubSN7qVaz2GhFRG4Eijr_svoY(PowerNotificationWarnings.this, i, i2, dialogInterface, i3);
                }
            });
        } else {
            systemUIDialog.setTitle(R$string.battery_saver_confirmation_title);
            systemUIDialog.setPositiveButton(R$string.battery_saver_confirmation_ok, new DialogInterface.OnClickListener() { // from class: com.android.systemui.power.PowerNotificationWarnings$$ExternalSyntheticLambda6
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i3) {
                    PowerNotificationWarnings.$r8$lambda$p5salmuhygAMPQaKCdlpWu7Fn20(PowerNotificationWarnings.this, dialogInterface, i3);
                }
            });
            systemUIDialog.setNegativeButton(17039360, new DialogInterface.OnClickListener() { // from class: com.android.systemui.power.PowerNotificationWarnings$$ExternalSyntheticLambda7
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i3) {
                    PowerNotificationWarnings.m3611$r8$lambda$mVqW3gRbkEne8QwZFv1UdTMCN8(PowerNotificationWarnings.this, dialogInterface, i3);
                }
            });
        }
        systemUIDialog.setShowForAllUsers(true);
        systemUIDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.power.PowerNotificationWarnings$$ExternalSyntheticLambda8
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                PowerNotificationWarnings.$r8$lambda$iC0GAri_wbtmyOvisiUSJr2uYCY(PowerNotificationWarnings.this, dialogInterface);
            }
        });
        WeakReference lastPowerSaverStartView = ((BatteryController) this.mBatteryControllerLazy.get()).getLastPowerSaverStartView();
        if (lastPowerSaverStartView == null || lastPowerSaverStartView.get() == null || !((View) lastPowerSaverStartView.get()).isAggregatedVisible()) {
            systemUIDialog.show();
        } else {
            this.mDialogLaunchAnimator.showFromView(systemUIDialog, (View) lastPowerSaverStartView.get(), new DialogCuj(58, INTERACTION_JANK_TAG));
        }
        logEvent(BatteryWarningEvents$LowBatteryWarningEvent.SAVER_CONFIRM_DIALOG);
        this.mSaverConfirmation = systemUIDialog;
        ((BatteryController) this.mBatteryControllerLazy.get()).clearLastPowerSaverStartView();
    }

    public void showThermalShutdownDialog() {
        if (this.mThermalShutdownDialog != null) {
            return;
        }
        SystemUIDialog systemUIDialog = new SystemUIDialog(this.mContext);
        systemUIDialog.setIconAttribute(16843605);
        systemUIDialog.setTitle(R$string.thermal_shutdown_title);
        systemUIDialog.setMessage(R$string.thermal_shutdown_dialog_message);
        systemUIDialog.setPositiveButton(17039370, (DialogInterface.OnClickListener) null);
        systemUIDialog.setShowForAllUsers(true);
        systemUIDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.power.PowerNotificationWarnings$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                PowerNotificationWarnings.$r8$lambda$fGCxfjSnE2zNj45qDgLHyRYHcXg(PowerNotificationWarnings.this, dialogInterface);
            }
        });
        String string = this.mContext.getString(R$string.thermal_shutdown_dialog_help_url);
        if (!string.isEmpty()) {
            systemUIDialog.setNeutralButton(R$string.thermal_shutdown_dialog_help_text, new AnonymousClass2(string));
        }
        systemUIDialog.show();
        this.mThermalShutdownDialog = systemUIDialog;
    }

    /* renamed from: showUsbHighTemperatureAlarmInternal */
    public void lambda$showUsbHighTemperatureAlarm$2() {
        if (this.mUsbHighTempDialog != null) {
            return;
        }
        SystemUIDialog systemUIDialog = new SystemUIDialog(this.mContext, R$style.Theme_SystemUI_Dialog_Alert);
        systemUIDialog.setCancelable(false);
        systemUIDialog.setIconAttribute(16843605);
        systemUIDialog.setTitle(R$string.high_temp_alarm_title);
        systemUIDialog.setShowForAllUsers(true);
        systemUIDialog.setMessage(this.mContext.getString(R$string.high_temp_alarm_notify_message, ""));
        systemUIDialog.setPositiveButton(17039370, new DialogInterface.OnClickListener() { // from class: com.android.systemui.power.PowerNotificationWarnings$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                PowerNotificationWarnings.m3615$r8$lambda$rX89K38RQSB8X0yDYx6nhAd5Q(PowerNotificationWarnings.this, dialogInterface, i);
            }
        });
        systemUIDialog.setNegativeButton(R$string.high_temp_alarm_help_care_steps, new DialogInterface.OnClickListener() { // from class: com.android.systemui.power.PowerNotificationWarnings$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                PowerNotificationWarnings.m3612$r8$lambda$AU3_uT6uZ3uHxf6PyohDdjqwbc(PowerNotificationWarnings.this, dialogInterface, i);
            }
        });
        systemUIDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.power.PowerNotificationWarnings$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                PowerNotificationWarnings.$r8$lambda$boN4KTlUPlRx7MXCH14MFcZqhEA(PowerNotificationWarnings.this, dialogInterface);
            }
        });
        systemUIDialog.getWindow().addFlags(2097280);
        systemUIDialog.show();
        this.mUsbHighTempDialog = systemUIDialog;
        Events.writeEvent(19, new Object[]{3, Boolean.valueOf(this.mKeyguard.isKeyguardLocked())});
    }

    public void startBatterySaverSchedulePage() {
        Intent intent = new Intent(BATTERY_SAVER_SCHEDULE_SCREEN_INTENT_ACTION);
        intent.setFlags(268468224);
        this.mActivityStarter.startActivity(intent, true);
    }

    private void updateNotification() {
        if (DEBUG) {
            Slog.d(TAG, "updateNotification mWarning=" + this.mWarning + " mPlaySound=" + this.mPlaySound + " mInvalidCharger=" + this.mInvalidCharger);
        }
        if (this.mInvalidCharger) {
            showInvalidChargerNotification();
            this.mShowing = 3;
        } else if (this.mWarning) {
            showWarningNotification();
            this.mShowing = 1;
        } else if (this.mShowAutoSaverSuggestion) {
            if (this.mShowing != 4) {
                showAutoSaverSuggestionNotification();
            }
            this.mShowing = 4;
        } else {
            this.mNoMan.cancelAsUser(TAG_BATTERY, 2, UserHandle.ALL);
            this.mNoMan.cancelAsUser(TAG_BATTERY, 3, UserHandle.ALL);
            this.mNoMan.cancelAsUser(TAG_AUTO_SAVER, 49, UserHandle.ALL);
            this.mShowing = 0;
        }
    }

    @Override // com.android.systemui.power.PowerUI.WarningsUI
    public void dismissHighTemperatureWarning() {
        if (this.mHighTempWarning) {
            dismissHighTemperatureWarningInternal();
        }
    }

    @Override // com.android.systemui.power.PowerUI.WarningsUI
    public void dismissInvalidChargerWarning() {
        dismissInvalidChargerNotification();
    }

    @Override // com.android.systemui.power.PowerUI.WarningsUI
    public void dismissLowBatteryWarning() {
        if (DEBUG) {
            Slog.d(TAG, "dismissing low battery warning: level=" + this.mBatteryLevel);
        }
        dismissLowBatteryNotification();
    }

    public void dismissThermalShutdownWarning() {
        this.mNoMan.cancelAsUser(TAG_TEMPERATURE, 39, UserHandle.ALL);
    }

    @Override // com.android.systemui.power.PowerUI.WarningsUI
    public void dump(PrintWriter printWriter) {
        printWriter.print("mWarning=");
        printWriter.println(this.mWarning);
        printWriter.print("mPlaySound=");
        printWriter.println(this.mPlaySound);
        printWriter.print("mInvalidCharger=");
        printWriter.println(this.mInvalidCharger);
        printWriter.print("mShowing=");
        printWriter.println(SHOWING_STRINGS[this.mShowing]);
        printWriter.print("mSaverConfirmation=");
        printWriter.println(this.mSaverConfirmation != null ? "not null" : null);
        printWriter.print("mSaverEnabledConfirmation=");
        printWriter.print("mHighTempWarning=");
        printWriter.println(this.mHighTempWarning);
        printWriter.print("mHighTempDialog=");
        printWriter.println(this.mHighTempDialog != null ? "not null" : null);
        printWriter.print("mThermalShutdownDialog=");
        printWriter.println(this.mThermalShutdownDialog != null ? "not null" : null);
        printWriter.print("mUsbHighTempDialog=");
        printWriter.println(this.mUsbHighTempDialog != null ? "not null" : null);
    }

    public Dialog getSaverConfirmationDialog() {
        return this.mSaverConfirmation;
    }

    @Override // com.android.systemui.power.PowerUI.WarningsUI
    public boolean isInvalidChargerWarningShowing() {
        return this.mInvalidCharger;
    }

    @Override // com.android.systemui.power.PowerUI.WarningsUI
    public void showHighTemperatureWarning() {
        if (this.mHighTempWarning) {
            return;
        }
        this.mHighTempWarning = true;
        String string = this.mContext.getString(R$string.high_temp_notif_message);
        Notification.Builder color = new Notification.Builder(this.mContext, NotificationChannels.ALERTS).setSmallIcon(R$drawable.ic_device_thermostat_24).setWhen(0L).setShowWhen(false).setContentTitle(this.mContext.getString(R$string.high_temp_title)).setContentText(string).setStyle(new Notification.BigTextStyle().bigText(string)).setVisibility(1).setContentIntent(pendingBroadcast(ACTION_CLICKED_TEMP_WARNING)).setDeleteIntent(pendingBroadcast(ACTION_DISMISSED_TEMP_WARNING)).setColor(Utils.getColorAttrDefaultColor(this.mContext, 16844099));
        SystemUIApplication.overrideNotificationAppName(this.mContext, color, false);
        this.mNoMan.notifyAsUser(TAG_TEMPERATURE, 4, color.build(), UserHandle.ALL);
    }

    @Override // com.android.systemui.power.PowerUI.WarningsUI
    public void showInvalidChargerWarning() {
        this.mInvalidCharger = true;
        updateNotification();
    }

    @Override // com.android.systemui.power.PowerUI.WarningsUI
    public void showLowBatteryWarning(boolean z) {
        Slog.i(TAG, "show low battery warning: level=" + this.mBatteryLevel + " [" + this.mBucket + "] playSound=" + z);
        logEvent(BatteryWarningEvents$LowBatteryWarningEvent.LOW_BATTERY_NOTIFICATION);
        this.mPlaySound = z;
        this.mWarning = true;
        updateNotification();
    }

    @Override // com.android.systemui.power.PowerUI.WarningsUI
    public void showThermalShutdownWarning() {
        String string = this.mContext.getString(R$string.thermal_shutdown_message);
        Notification.Builder color = new Notification.Builder(this.mContext, NotificationChannels.ALERTS).setSmallIcon(R$drawable.ic_device_thermostat_24).setWhen(0L).setShowWhen(false).setContentTitle(this.mContext.getString(R$string.thermal_shutdown_title)).setContentText(string).setStyle(new Notification.BigTextStyle().bigText(string)).setVisibility(1).setContentIntent(pendingBroadcast(ACTION_CLICKED_THERMAL_SHUTDOWN_WARNING)).setDeleteIntent(pendingBroadcast(ACTION_DISMISSED_THERMAL_SHUTDOWN_WARNING)).setColor(Utils.getColorAttrDefaultColor(this.mContext, 16844099));
        SystemUIApplication.overrideNotificationAppName(this.mContext, color, false);
        this.mNoMan.notifyAsUser(TAG_TEMPERATURE, 39, color.build(), UserHandle.ALL);
    }

    @Override // com.android.systemui.power.PowerUI.WarningsUI
    public void showUsbHighTemperatureAlarm() {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.power.PowerNotificationWarnings$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                PowerNotificationWarnings.m3614$r8$lambda$OAwUN_OHnSI9S6XRP6KTTWkM94(PowerNotificationWarnings.this);
            }
        });
    }

    public void showWarningNotification() {
        if (showSevereLowBatteryDialog()) {
            this.mBroadcastSender.sendBroadcast(new Intent(ACTION_ENABLE_SEVERE_BATTERY_DIALOG).setPackage(this.mContext.getPackageName()).putExtra(EXTRA_SCHEDULED_BY_PERCENTAGE, isScheduledByPercentage()).addFlags(1342177280));
            dismissLowBatteryNotification();
            this.mPlaySound = false;
        } else if (isScheduledByPercentage()) {
        } else {
            String format = NumberFormat.getPercentInstance().format(this.mCurrentBatterySnapshot.getBatteryLevel() / 100.0d);
            String string = this.mContext.getString(R$string.battery_low_title);
            String string2 = this.mContext.getString(R$string.battery_low_description, format);
            Notification.Builder visibility = new Notification.Builder(this.mContext, NotificationChannels.BATTERY).setSmallIcon(R$drawable.ic_power_low).setWhen(this.mWarningTriggerTimeMs).setShowWhen(false).setContentText(string2).setContentTitle(string).setOnlyAlertOnce(true).setDeleteIntent(pendingBroadcast(ACTION_DISMISSED_WARNING)).setStyle(new Notification.BigTextStyle().bigText(string2)).setVisibility(1);
            if (hasBatterySettings()) {
                visibility.setContentIntent(pendingBroadcast(ACTION_SHOW_BATTERY_SAVER_SETTINGS));
            }
            if (!this.mCurrentBatterySnapshot.isHybrid() || this.mBucket < -1 || this.mCurrentBatterySnapshot.getTimeRemainingMillis() < this.mCurrentBatterySnapshot.getSevereThresholdMillis()) {
                visibility.setColor(Utils.getColorAttrDefaultColor(this.mContext, 16844099));
            }
            if (!this.mPowerMan.isPowerSaveMode()) {
                visibility.addAction(0, this.mContext.getString(R$string.battery_saver_dismiss_action), pendingBroadcast(ACTION_DISMISSED_WARNING));
                visibility.addAction(0, this.mContext.getString(R$string.battery_saver_start_action), pendingBroadcast(ACTION_START_SAVER));
            }
            visibility.setOnlyAlertOnce(!this.mPlaySound);
            this.mPlaySound = false;
            SystemUIApplication.overrideNotificationAppName(this.mContext, visibility, false);
            Notification build = visibility.build();
            this.mNoMan.cancelAsUser(TAG_BATTERY, 2, UserHandle.ALL);
            this.mNoMan.notifyAsUser(TAG_BATTERY, 3, build, UserHandle.ALL);
        }
    }

    @Override // com.android.systemui.power.PowerUI.WarningsUI
    public void update(int i, int i2, long j) {
        this.mBatteryLevel = i;
        if (i2 >= 0) {
            this.mWarningTriggerTimeMs = 0L;
        } else if (i2 < this.mBucket) {
            this.mWarningTriggerTimeMs = System.currentTimeMillis();
        }
        this.mBucket = i2;
        this.mScreenOffTime = j;
    }

    @Override // com.android.systemui.power.PowerUI.WarningsUI
    public void updateLowBatteryWarning() {
        updateNotification();
    }

    @Override // com.android.systemui.power.PowerUI.WarningsUI
    public void updateSnapshot(BatteryStateSnapshot batteryStateSnapshot) {
        this.mCurrentBatterySnapshot = batteryStateSnapshot;
    }

    @Override // com.android.systemui.power.PowerUI.WarningsUI
    public void userSwitched() {
        updateNotification();
    }
}