package com.android.systemui.qs.tiles;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.ContentObserver;
import android.hardware.display.ColorDisplayManager;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import com.android.internal.custom.hardware.LiveDisplayManager;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.ArrayUtils;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/LiveDisplayTile.class */
public class LiveDisplayTile extends QSTileImpl<QSTile.LiveDisplayState> {
    public static final Intent DISPLAY_SETTINGS = new Intent("android.settings.DISPLAY_SETTINGS");
    public String[] mAnnouncementEntries;
    public int mDayTemperature;
    public String[] mDescriptionEntries;
    public String[] mEntries;
    public final int[] mEntryIconRes;
    public boolean mListening;
    public final LiveDisplayManager mLiveDisplay;
    public final boolean mNightDisplayAvailable;
    public final LiveDisplayObserver mObserver;
    public final boolean mOutdoorModeAvailable;
    public String mTitle;
    public String[] mValues;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/LiveDisplayTile$LiveDisplayObserver.class */
    public class LiveDisplayObserver extends ContentObserver {
        public LiveDisplayObserver(Handler handler) {
            super(handler);
        }

        public void endObserving() {
            LiveDisplayTile.this.mContext.getContentResolver().unregisterContentObserver(this);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            LiveDisplayTile liveDisplayTile = LiveDisplayTile.this;
            liveDisplayTile.mDayTemperature = liveDisplayTile.mLiveDisplay.getDayColorTemperature();
            LiveDisplayTile liveDisplayTile2 = LiveDisplayTile.this;
            liveDisplayTile2.refreshState(Integer.valueOf(liveDisplayTile2.getCurrentModeIndex()));
        }

        public void startObserving() {
            LiveDisplayTile.this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("display_temperature_mode"), false, this, -1);
            LiveDisplayTile.this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("display_temperature_day"), false, this, -1);
        }
    }

    public LiveDisplayTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mNightDisplayAvailable = ColorDisplayManager.isNightDisplayAvailable(this.mContext);
        TypedArray obtainTypedArray = this.mContext.getResources().obtainTypedArray(17236179);
        this.mEntryIconRes = new int[obtainTypedArray.length()];
        int i = 0;
        while (true) {
            int[] iArr = this.mEntryIconRes;
            if (i >= iArr.length) {
                break;
            }
            iArr[i] = obtainTypedArray.getResourceId(i, 0);
            i++;
        }
        obtainTypedArray.recycle();
        updateEntries();
        LiveDisplayManager liveDisplayManager = LiveDisplayManager.getInstance(this.mContext);
        this.mLiveDisplay = liveDisplayManager;
        if (liveDisplayManager.getConfig() != null) {
            boolean z = false;
            if (liveDisplayManager.getConfig().hasFeature(3)) {
                z = false;
                if (!liveDisplayManager.getConfig().hasFeature(14)) {
                    z = true;
                }
            }
            this.mOutdoorModeAvailable = z;
            this.mDayTemperature = liveDisplayManager.getDayColorTemperature();
        } else {
            this.mOutdoorModeAvailable = false;
            this.mDayTemperature = -1;
        }
        LiveDisplayObserver liveDisplayObserver = new LiveDisplayObserver(this.mHandler);
        this.mObserver = liveDisplayObserver;
        liveDisplayObserver.startObserving();
    }

    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:56)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:30)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:18)
        */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0029  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x005d -> B:5:0x0015). Please submit an issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void changeToNextMode() {
        /*
            r5 = this;
            r0 = r5
            int r0 = r0.getCurrentModeIndex()
            r1 = 1
            int r0 = r0 + r1
            r6 = r0
            r0 = r6
            r7 = r0
            r0 = r6
            r1 = r5
            java.lang.String[] r1 = r1.mValues
            int r1 = r1.length
            if (r0 < r1) goto L15
            goto L5d
        L15:
            r0 = r5
            java.lang.String[] r0 = r0.mValues
            r1 = r7
            r0 = r0[r1]
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            int r0 = r0.intValue()
            r6 = r0
            r0 = r5
            boolean r0 = r0.mOutdoorModeAvailable
            if (r0 != 0) goto L2e
            r0 = r6
            r1 = 3
            if (r0 == r1) goto L4e
        L2e:
            r0 = r5
            int r0 = r0.mDayTemperature
            r1 = 6500(0x1964, float:9.108E-42)
            if (r0 != r1) goto L3d
            r0 = r6
            r1 = 4
            if (r0 == r1) goto L4e
        L3d:
            r0 = r5
            boolean r0 = r0.mNightDisplayAvailable
            if (r0 == 0) goto L62
            r0 = r6
            r1 = 4
            if (r0 == r1) goto L4e
            r0 = r6
            r1 = 1
            if (r0 != r1) goto L62
        L4e:
            r0 = r7
            r1 = 1
            int r0 = r0 + r1
            r6 = r0
            r0 = r6
            r7 = r0
            r0 = r6
            r1 = r5
            java.lang.String[] r1 = r1.mValues
            int r1 = r1.length
            if (r0 < r1) goto L15
        L5d:
            r0 = 0
            r7 = r0
            goto L15
        L62:
            r0 = r5
            android.content.Context r0 = r0.mContext
            android.content.ContentResolver r0 = r0.getContentResolver()
            java.lang.String r1 = "display_temperature_mode"
            r2 = r6
            r3 = -2
            boolean r0 = android.provider.Settings.System.putIntForUser(r0, r1, r2, r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.LiveDisplayTile.changeToNextMode():void");
    }

    public final int getCurrentModeIndex() {
        try {
            return ArrayUtils.indexOf(this.mValues, String.valueOf(this.mLiveDisplay.getMode()));
        } catch (NullPointerException e) {
            return ArrayUtils.indexOf(this.mValues, String.valueOf(2));
        } catch (Throwable th) {
            return ArrayUtils.indexOf(this.mValues, (Object) null);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return DISPLAY_SETTINGS;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 1999;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(17040620);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(View view) {
        changeToNextMode();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSetListening(boolean z) {
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        if (z) {
            this.mObserver.startObserving();
        } else {
            this.mObserver.endObserving();
        }
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.LiveDisplayState liveDisplayState, Object obj) {
        updateEntries();
        int currentModeIndex = obj == null ? getCurrentModeIndex() : ((Integer) obj).intValue();
        liveDisplayState.mode = currentModeIndex;
        liveDisplayState.label = this.mTitle;
        liveDisplayState.secondaryLabel = this.mEntries[currentModeIndex];
        liveDisplayState.icon = QSTileImpl.ResourceIcon.get(this.mEntryIconRes[currentModeIndex]);
        liveDisplayState.contentDescription = this.mDescriptionEntries[liveDisplayState.mode];
        liveDisplayState.state = this.mLiveDisplay.getMode() != 0 ? 2 : 1;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public boolean isAvailable() {
        return !this.mNightDisplayAvailable || this.mOutdoorModeAvailable;
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.LiveDisplayState newTileState() {
        return new QSTile.LiveDisplayState();
    }

    public final void updateEntries() {
        Resources resources = this.mContext.getResources();
        this.mTitle = resources.getString(17040620);
        this.mEntries = resources.getStringArray(17236180);
        this.mDescriptionEntries = resources.getStringArray(17236178);
        this.mAnnouncementEntries = resources.getStringArray(17236177);
        this.mValues = resources.getStringArray(17236182);
    }
}