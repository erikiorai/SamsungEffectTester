package com.android.systemui.keyguard;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Icon;
import android.icu.text.DateFormat;
import android.icu.text.DisplayContext;
import android.media.MediaMetadata;
import android.net.Uri;
import android.os.Handler;
import android.os.Trace;
import android.service.notification.ZenModeConfig;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.Slice;
import androidx.slice.SliceProvider;
import androidx.slice.builders.ListBuilder;
import androidx.slice.builders.SliceAction;
import com.android.internal.annotations.VisibleForTesting;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.SystemUIAppComponentFactoryBase;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.NextAlarmController;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.util.wakelock.SettableWakeLock;
import com.android.systemui.util.wakelock.WakeLock;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/KeyguardSliceProvider.class */
public class KeyguardSliceProvider extends SliceProvider implements NextAlarmController.NextAlarmChangeCallback, ZenModeController.Callback, NotificationMediaManager.MediaListener, StatusBarStateController.StateListener, SystemUIAppComponentFactoryBase.ContextInitializer {
    @VisibleForTesting
    public static final int ALARM_VISIBILITY_HOURS = 12;
    public static final String KEYGUARD_ACTION_URI = "content://com.android.systemui.keyguard/action";
    public static final String KEYGUARD_DATE_URI = "content://com.android.systemui.keyguard/date";
    public static final String KEYGUARD_DND_URI = "content://com.android.systemui.keyguard/dnd";
    private static final String KEYGUARD_HEADER_URI = "content://com.android.systemui.keyguard/header";
    public static final String KEYGUARD_MEDIA_URI = "content://com.android.systemui.keyguard/media";
    public static final String KEYGUARD_NEXT_ALARM_URI = "content://com.android.systemui.keyguard/alarm";
    public static final String KEYGUARD_SLICE_URI = "content://com.android.systemui.keyguard/main";
    private static final String TAG = "KgdSliceProvider";
    private static KeyguardSliceProvider sInstance;
    public AlarmManager mAlarmManager;
    public ContentResolver mContentResolver;
    private SystemUIAppComponentFactoryBase.ContextAvailableCallback mContextAvailableCallback;
    private DateFormat mDateFormat;
    private String mDatePattern;
    public DozeParameters mDozeParameters;
    public boolean mDozing;
    public KeyguardBypassController mKeyguardBypassController;
    public KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    private String mLastText;
    private CharSequence mMediaArtist;
    private boolean mMediaIsVisible;
    public NotificationMediaManager mMediaManager;
    private CharSequence mMediaTitle;
    @VisibleForTesting
    public SettableWakeLock mMediaWakeLock;
    private String mNextAlarm;
    public NextAlarmController mNextAlarmController;
    private AlarmManager.AlarmClockInfo mNextAlarmInfo;
    private PendingIntent mPendingIntent;
    private boolean mRegistered;
    private int mStatusBarState;
    public StatusBarStateController mStatusBarStateController;
    public UserTracker mUserTracker;
    public ZenModeController mZenModeController;
    private static final StyleSpan BOLD_STYLE = new StyleSpan(1);
    private static final Object sInstanceLock = new Object();
    private final Date mCurrentTime = new Date();
    private final AlarmManager.OnAlarmListener mUpdateNextAlarm = new AlarmManager.OnAlarmListener() { // from class: com.android.systemui.keyguard.KeyguardSliceProvider$$ExternalSyntheticLambda1
        @Override // android.app.AlarmManager.OnAlarmListener
        public final void onAlarm() {
            KeyguardSliceProvider.m2822$r8$lambda$LUa7NUO2bSgQxLI5RKHF8mUWuo(KeyguardSliceProvider.this);
        }
    };
    @VisibleForTesting
    public final BroadcastReceiver mIntentReceiver = new BroadcastReceiver() { // from class: com.android.systemui.keyguard.KeyguardSliceProvider.1
        {
            KeyguardSliceProvider.this = this;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.DATE_CHANGED".equals(action)) {
                synchronized (this) {
                    KeyguardSliceProvider.this.updateClockLocked();
                }
            } else if ("android.intent.action.LOCALE_CHANGED".equals(action)) {
                synchronized (this) {
                    KeyguardSliceProvider.this.cleanDateFormatLocked();
                }
            }
        }
    };
    @VisibleForTesting
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.keyguard.KeyguardSliceProvider.2
        {
            KeyguardSliceProvider.this = this;
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onTimeChanged() {
            synchronized (this) {
                KeyguardSliceProvider.this.updateClockLocked();
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onTimeZoneChanged(TimeZone timeZone) {
            synchronized (this) {
                KeyguardSliceProvider.this.cleanDateFormatLocked();
            }
        }
    };
    private final Handler mHandler = new Handler();
    private final Handler mMediaHandler = new Handler();
    public final Uri mSliceUri = Uri.parse(KEYGUARD_SLICE_URI);
    public final Uri mHeaderUri = Uri.parse(KEYGUARD_HEADER_URI);
    public final Uri mDateUri = Uri.parse(KEYGUARD_DATE_URI);
    public final Uri mAlarmUri = Uri.parse(KEYGUARD_NEXT_ALARM_URI);
    public final Uri mDndUri = Uri.parse(KEYGUARD_DND_URI);
    public final Uri mMediaUri = Uri.parse(KEYGUARD_MEDIA_URI);

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.KeyguardSliceProvider$$ExternalSyntheticLambda1.onAlarm():void] */
    /* renamed from: $r8$lambda$LUa7NUO2bSgQxLI5RKHF8m-UWuo */
    public static /* synthetic */ void m2822$r8$lambda$LUa7NUO2bSgQxLI5RKHF8mUWuo(KeyguardSliceProvider keyguardSliceProvider) {
        keyguardSliceProvider.updateNextAlarm();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.KeyguardSliceProvider$$ExternalSyntheticLambda0.run():void] */
    /* renamed from: $r8$lambda$f9ybxQY6o71GYAJ838lmm_S-j5U */
    public static /* synthetic */ void m2823$r8$lambda$f9ybxQY6o71GYAJ838lmm_Sj5U(KeyguardSliceProvider keyguardSliceProvider, MediaMetadata mediaMetadata, int i) {
        keyguardSliceProvider.lambda$onPrimaryMetadataOrStateChanged$0(mediaMetadata, i);
    }

    public static KeyguardSliceProvider getAttachedInstance() {
        return sInstance;
    }

    public /* synthetic */ void lambda$onPrimaryMetadataOrStateChanged$0(MediaMetadata mediaMetadata, int i) {
        synchronized (this) {
            updateMediaStateLocked(mediaMetadata, i);
            this.mMediaWakeLock.setAcquired(false);
        }
    }

    private void updateMediaStateLocked(MediaMetadata mediaMetadata, int i) {
        String str;
        boolean isPlayingState = NotificationMediaManager.isPlayingState(i);
        if (mediaMetadata != null) {
            CharSequence text = mediaMetadata.getText("android.media.metadata.TITLE");
            str = text;
            if (TextUtils.isEmpty(text)) {
                str = getContext().getResources().getString(R$string.music_controls_no_title);
            }
        } else {
            str = null;
        }
        CharSequence text2 = mediaMetadata == null ? null : mediaMetadata.getText("android.media.metadata.ARTIST");
        if (isPlayingState == this.mMediaIsVisible && TextUtils.equals(str, this.mMediaTitle) && TextUtils.equals(text2, this.mMediaArtist)) {
            return;
        }
        this.mMediaTitle = str;
        this.mMediaArtist = text2;
        this.mMediaIsVisible = isPlayingState;
        notifyChange();
    }

    public void updateNextAlarm() {
        synchronized (this) {
            if (withinNHoursLocked(this.mNextAlarmInfo, 12)) {
                this.mNextAlarm = android.text.format.DateFormat.format(android.text.format.DateFormat.is24HourFormat(getContext(), this.mUserTracker.getUserId()) ? "HH:mm" : "h:mm", this.mNextAlarmInfo.getTriggerTime()).toString();
            } else {
                this.mNextAlarm = "";
            }
        }
        notifyChange();
    }

    private boolean withinNHoursLocked(AlarmManager.AlarmClockInfo alarmClockInfo, int i) {
        boolean z = false;
        if (alarmClockInfo == null) {
            return false;
        }
        if (this.mNextAlarmInfo.getTriggerTime() <= System.currentTimeMillis() + TimeUnit.HOURS.toMillis(i)) {
            z = true;
        }
        return z;
    }

    public void addMediaLocked(ListBuilder listBuilder) {
        if (TextUtils.isEmpty(this.mMediaTitle)) {
            return;
        }
        listBuilder.setHeader(new ListBuilder.HeaderBuilder(this.mHeaderUri).setTitle(this.mMediaTitle));
        if (TextUtils.isEmpty(this.mMediaArtist)) {
            return;
        }
        ListBuilder.RowBuilder rowBuilder = new ListBuilder.RowBuilder(this.mMediaUri);
        rowBuilder.setTitle(this.mMediaArtist);
        NotificationMediaManager notificationMediaManager = this.mMediaManager;
        Icon mediaIcon = notificationMediaManager == null ? null : notificationMediaManager.getMediaIcon();
        IconCompat createFromIcon = mediaIcon == null ? null : IconCompat.createFromIcon(getContext(), mediaIcon);
        if (createFromIcon != null) {
            rowBuilder.addEndItem(createFromIcon, 0);
        }
        listBuilder.addRow(rowBuilder);
    }

    public void addNextAlarmLocked(ListBuilder listBuilder) {
        if (TextUtils.isEmpty(this.mNextAlarm)) {
            return;
        }
        listBuilder.addRow(new ListBuilder.RowBuilder(this.mAlarmUri).setTitle(this.mNextAlarm).addEndItem(IconCompat.createWithResource(getContext(), R$drawable.ic_access_alarms_big), 0));
    }

    public void addPrimaryActionLocked(ListBuilder listBuilder) {
        listBuilder.addRow(new ListBuilder.RowBuilder(Uri.parse(KEYGUARD_ACTION_URI)).setPrimaryAction(SliceAction.createDeeplink(this.mPendingIntent, IconCompat.createWithResource(getContext(), R$drawable.ic_access_alarms_big), 0, this.mLastText)));
    }

    public void addZenModeLocked(ListBuilder listBuilder) {
        if (isDndOn()) {
            listBuilder.addRow(new ListBuilder.RowBuilder(this.mDndUri).setContentDescription(getContext().getResources().getString(R$string.accessibility_quick_settings_dnd)).addEndItem(IconCompat.createWithResource(getContext(), R$drawable.stat_sys_dnd), 0));
        }
    }

    @VisibleForTesting
    public void cleanDateFormatLocked() {
        this.mDateFormat = null;
    }

    public String getFormattedDateLocked() {
        if (this.mDateFormat == null) {
            DateFormat instanceForSkeleton = DateFormat.getInstanceForSkeleton(this.mDatePattern, Locale.getDefault());
            instanceForSkeleton.setContext(DisplayContext.CAPITALIZATION_FOR_STANDALONE);
            this.mDateFormat = instanceForSkeleton;
        }
        this.mCurrentTime.setTime(System.currentTimeMillis());
        return this.mDateFormat.format(this.mCurrentTime);
    }

    public boolean isDndOn() {
        return this.mZenModeController.getZen() != 0;
    }

    @VisibleForTesting
    public boolean isRegistered() {
        boolean z;
        synchronized (this) {
            z = this.mRegistered;
        }
        return z;
    }

    public boolean needsMediaLocked() {
        boolean z;
        KeyguardBypassController keyguardBypassController = this.mKeyguardBypassController;
        boolean z2 = keyguardBypassController != null && keyguardBypassController.getBypassEnabled() && this.mDozeParameters.getAlwaysOn();
        boolean z3 = this.mStatusBarState == 0 && this.mMediaIsVisible;
        if (!TextUtils.isEmpty(this.mMediaTitle) && this.mMediaIsVisible) {
            z = true;
            if (!this.mDozing) {
                z = true;
                if (!z2) {
                    if (z3) {
                        z = true;
                    }
                }
            }
            return z;
        }
        z = false;
        return z;
    }

    public void notifyChange() {
        this.mContentResolver.notifyChange(this.mSliceUri, null);
    }

    @Override // androidx.slice.SliceProvider
    public Slice onBindSlice(Uri uri) {
        Slice build;
        Trace.beginSection("KeyguardSliceProvider#onBindSlice");
        synchronized (this) {
            ListBuilder listBuilder = new ListBuilder(getContext(), this.mSliceUri, -1L);
            if (needsMediaLocked()) {
                addMediaLocked(listBuilder);
            } else {
                listBuilder.addRow(new ListBuilder.RowBuilder(this.mDateUri).setTitle(this.mLastText));
            }
            addNextAlarmLocked(listBuilder);
            addZenModeLocked(listBuilder);
            addPrimaryActionLocked(listBuilder);
            build = listBuilder.build();
        }
        Trace.endSection();
        return build;
    }

    public void onConfigChanged(ZenModeConfig zenModeConfig) {
        notifyChange();
    }

    @Override // androidx.slice.SliceProvider
    public boolean onCreateSliceProvider() {
        this.mContextAvailableCallback.onContextAvailable(getContext());
        this.mMediaWakeLock = new SettableWakeLock(WakeLock.createPartial(getContext(), "media"), "media");
        synchronized (sInstanceLock) {
            KeyguardSliceProvider keyguardSliceProvider = sInstance;
            if (keyguardSliceProvider != null) {
                keyguardSliceProvider.onDestroy();
            }
            this.mDatePattern = getContext().getString(R$string.system_ui_aod_date_pattern);
            this.mPendingIntent = PendingIntent.getActivity(getContext(), 0, new Intent(getContext(), KeyguardSliceProvider.class), 67108864);
            this.mMediaManager.addCallback(this);
            this.mStatusBarStateController.addCallback(this);
            this.mNextAlarmController.addCallback(this);
            this.mZenModeController.addCallback(this);
            sInstance = this;
            registerClockUpdate();
            updateClockLocked();
        }
        return true;
    }

    @VisibleForTesting
    public void onDestroy() {
        synchronized (sInstanceLock) {
            this.mNextAlarmController.removeCallback(this);
            this.mZenModeController.removeCallback(this);
            this.mMediaWakeLock.setAcquired(false);
            this.mAlarmManager.cancel(this.mUpdateNextAlarm);
            if (this.mRegistered) {
                this.mRegistered = false;
                this.mKeyguardUpdateMonitor.removeCallback(this.mKeyguardUpdateMonitorCallback);
                getContext().unregisterReceiver(this.mIntentReceiver);
            }
            sInstance = null;
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public void onDozingChanged(boolean z) {
        boolean z2;
        synchronized (this) {
            boolean needsMediaLocked = needsMediaLocked();
            this.mDozing = z;
            z2 = needsMediaLocked != needsMediaLocked();
        }
        if (z2) {
            notifyChange();
        }
    }

    public void onNextAlarmChanged(AlarmManager.AlarmClockInfo alarmClockInfo) {
        synchronized (this) {
            this.mNextAlarmInfo = alarmClockInfo;
            this.mAlarmManager.cancel(this.mUpdateNextAlarm);
            AlarmManager.AlarmClockInfo alarmClockInfo2 = this.mNextAlarmInfo;
            long triggerTime = alarmClockInfo2 == null ? -1L : alarmClockInfo2.getTriggerTime() - TimeUnit.HOURS.toMillis(12L);
            if (triggerTime > 0) {
                this.mAlarmManager.setExact(1, triggerTime, "lock_screen_next_alarm", this.mUpdateNextAlarm, this.mHandler);
            }
        }
        updateNextAlarm();
    }

    public void onPrimaryMetadataOrStateChanged(final MediaMetadata mediaMetadata, final int i) {
        synchronized (this) {
            boolean isPlayingState = NotificationMediaManager.isPlayingState(i);
            this.mMediaHandler.removeCallbacksAndMessages(null);
            if (!this.mMediaIsVisible || isPlayingState || this.mStatusBarState == 0) {
                this.mMediaWakeLock.setAcquired(false);
                updateMediaStateLocked(mediaMetadata, i);
            } else {
                this.mMediaWakeLock.setAcquired(true);
                this.mMediaHandler.postDelayed(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardSliceProvider$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardSliceProvider.m2823$r8$lambda$f9ybxQY6o71GYAJ838lmm_Sj5U(KeyguardSliceProvider.this, mediaMetadata, i);
                    }
                }, 2000L);
            }
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public void onStateChanged(int i) {
        boolean z;
        synchronized (this) {
            boolean needsMediaLocked = needsMediaLocked();
            this.mStatusBarState = i;
            z = needsMediaLocked != needsMediaLocked();
        }
        if (z) {
            notifyChange();
        }
    }

    public void onZenChanged(int i) {
        notifyChange();
    }

    @VisibleForTesting
    public void registerClockUpdate() {
        synchronized (this) {
            if (this.mRegistered) {
                return;
            }
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.DATE_CHANGED");
            intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
            getContext().registerReceiver(this.mIntentReceiver, intentFilter, null, null);
            this.mKeyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
            this.mRegistered = true;
        }
    }

    @Override // com.android.systemui.SystemUIAppComponentFactoryBase.ContextInitializer
    public void setContextAvailableCallback(SystemUIAppComponentFactoryBase.ContextAvailableCallback contextAvailableCallback) {
        this.mContextAvailableCallback = contextAvailableCallback;
    }

    public void updateClockLocked() {
        String formattedDateLocked = getFormattedDateLocked();
        if (formattedDateLocked.equals(this.mLastText)) {
            return;
        }
        this.mLastText = formattedDateLocked;
        notifyChange();
    }
}