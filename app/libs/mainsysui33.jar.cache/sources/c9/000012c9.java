package com.android.systemui.classifier;

import android.util.DisplayMetrics;
import android.view.MotionEvent;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.classifier.FalsingDataProvider;
import com.android.systemui.dock.DockManager;
import com.android.systemui.statusbar.policy.BatteryController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/* loaded from: mainsysui33.jar:com/android/systemui/classifier/FalsingDataProvider.class */
public class FalsingDataProvider {
    public boolean mA11YAction;
    public BatteryController mBatteryController;
    public final DockManager mDockManager;
    public MotionEvent mFirstRecentMotionEvent;
    public final int mHeightPixels;
    public boolean mJustUnlockedWithFace;
    public MotionEvent mLastMotionEvent;
    public final int mWidthPixels;
    public final float mXdpi;
    public final float mYdpi;
    public final List<SessionListener> mSessionListeners = new ArrayList();
    public final List<MotionEventListener> mMotionEventListeners = new ArrayList();
    public final List<GestureFinalizedListener> mGestureFinalizedListeners = new ArrayList();
    public TimeLimitedMotionEventBuffer mRecentMotionEvents = new TimeLimitedMotionEventBuffer(1000);
    public List<MotionEvent> mPriorMotionEvents = new ArrayList();
    public boolean mDirty = true;
    public float mAngle = ActionBarShadowController.ELEVATION_LOW;

    /* loaded from: mainsysui33.jar:com/android/systemui/classifier/FalsingDataProvider$GestureFinalizedListener.class */
    public interface GestureFinalizedListener {
        void onGestureFinalized(long j);
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/classifier/FalsingDataProvider$MotionEventListener.class */
    public interface MotionEventListener {
        void onMotionEvent(MotionEvent motionEvent);
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/classifier/FalsingDataProvider$SessionListener.class */
    public interface SessionListener {
        void onSessionEnded();

        void onSessionStarted();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.classifier.FalsingDataProvider$$ExternalSyntheticLambda0.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$05zcr9mejHigrFbU9thpdiGo37s(MotionEvent motionEvent, MotionEventListener motionEventListener) {
        motionEventListener.onMotionEvent(motionEvent);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.classifier.FalsingDataProvider$$ExternalSyntheticLambda1.accept(java.lang.Object):void] */
    /* renamed from: $r8$lambda$9vb-bg3cgNiHnEDDCVfEJ9hITCk */
    public static /* synthetic */ void m1698$r8$lambda$9vbbg3cgNiHnEDDCVfEJ9hITCk(FalsingDataProvider falsingDataProvider, GestureFinalizedListener gestureFinalizedListener) {
        falsingDataProvider.lambda$completePriorGesture$1(gestureFinalizedListener);
    }

    public FalsingDataProvider(DisplayMetrics displayMetrics, BatteryController batteryController, DockManager dockManager) {
        this.mXdpi = displayMetrics.xdpi;
        this.mYdpi = displayMetrics.ydpi;
        this.mWidthPixels = displayMetrics.widthPixels;
        this.mHeightPixels = displayMetrics.heightPixels;
        this.mBatteryController = batteryController;
        this.mDockManager = dockManager;
        FalsingClassifier.logInfo("xdpi, ydpi: " + getXdpi() + ", " + getYdpi());
        FalsingClassifier.logInfo("width, height: " + getWidthPixels() + ", " + getHeightPixels());
    }

    public /* synthetic */ void lambda$completePriorGesture$1(GestureFinalizedListener gestureFinalizedListener) {
        TimeLimitedMotionEventBuffer timeLimitedMotionEventBuffer = this.mRecentMotionEvents;
        gestureFinalizedListener.onGestureFinalized(timeLimitedMotionEventBuffer.get(timeLimitedMotionEventBuffer.size() - 1).getEventTime());
    }

    public void addGestureCompleteListener(GestureFinalizedListener gestureFinalizedListener) {
        this.mGestureFinalizedListeners.add(gestureFinalizedListener);
    }

    public void addMotionEventListener(MotionEventListener motionEventListener) {
        this.mMotionEventListeners.add(motionEventListener);
    }

    public void addSessionListener(SessionListener sessionListener) {
        this.mSessionListeners.add(sessionListener);
    }

    public final void calculateAngleInternal() {
        if (this.mRecentMotionEvents.size() < 2) {
            this.mAngle = Float.MAX_VALUE;
            return;
        }
        this.mAngle = (float) Math.atan2(this.mLastMotionEvent.getY() - this.mFirstRecentMotionEvent.getY(), this.mLastMotionEvent.getX() - this.mFirstRecentMotionEvent.getX());
        while (true) {
            float f = this.mAngle;
            if (f >= ActionBarShadowController.ELEVATION_LOW) {
                break;
            }
            this.mAngle = f + 6.2831855f;
        }
        while (true) {
            float f2 = this.mAngle;
            if (f2 <= 6.2831855f) {
                return;
            }
            this.mAngle = f2 - 6.2831855f;
        }
    }

    public final void completePriorGesture() {
        if (!this.mRecentMotionEvents.isEmpty()) {
            this.mGestureFinalizedListeners.forEach(new Consumer() { // from class: com.android.systemui.classifier.FalsingDataProvider$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    FalsingDataProvider.m1698$r8$lambda$9vbbg3cgNiHnEDDCVfEJ9hITCk(FalsingDataProvider.this, (FalsingDataProvider.GestureFinalizedListener) obj);
                }
            });
            this.mPriorMotionEvents = this.mRecentMotionEvents;
            this.mRecentMotionEvents = new TimeLimitedMotionEventBuffer(1000L);
        }
        this.mA11YAction = false;
    }

    public float getAngle() {
        recalculateData();
        return this.mAngle;
    }

    public MotionEvent getFirstRecentMotionEvent() {
        recalculateData();
        return this.mFirstRecentMotionEvent;
    }

    public int getHeightPixels() {
        return this.mHeightPixels;
    }

    public MotionEvent getLastMotionEvent() {
        recalculateData();
        return this.mLastMotionEvent;
    }

    public List<MotionEvent> getPriorMotionEvents() {
        return this.mPriorMotionEvents;
    }

    public List<MotionEvent> getRecentMotionEvents() {
        return this.mRecentMotionEvents;
    }

    public int getWidthPixels() {
        return this.mWidthPixels;
    }

    public float getXdpi() {
        return this.mXdpi;
    }

    public float getYdpi() {
        return this.mYdpi;
    }

    public boolean isA11yAction() {
        return this.mA11YAction;
    }

    public boolean isDocked() {
        return this.mBatteryController.isWirelessCharging() || this.mDockManager.isDocked();
    }

    public boolean isHorizontal() {
        recalculateData();
        boolean z = false;
        if (this.mRecentMotionEvents.isEmpty()) {
            return false;
        }
        if (Math.abs(this.mFirstRecentMotionEvent.getX() - this.mLastMotionEvent.getX()) > Math.abs(this.mFirstRecentMotionEvent.getY() - this.mLastMotionEvent.getY())) {
            z = true;
        }
        return z;
    }

    public boolean isJustUnlockedWithFace() {
        return this.mJustUnlockedWithFace;
    }

    public boolean isRight() {
        recalculateData();
        boolean z = false;
        if (this.mRecentMotionEvents.isEmpty()) {
            return false;
        }
        if (this.mLastMotionEvent.getX() > this.mFirstRecentMotionEvent.getX()) {
            z = true;
        }
        return z;
    }

    public boolean isUp() {
        recalculateData();
        boolean z = false;
        if (this.mRecentMotionEvents.isEmpty()) {
            return false;
        }
        if (this.mLastMotionEvent.getY() < this.mFirstRecentMotionEvent.getY()) {
            z = true;
        }
        return z;
    }

    public boolean isVertical() {
        return !isHorizontal();
    }

    public void onA11yAction() {
        completePriorGesture();
        this.mA11YAction = true;
    }

    public void onMotionEvent(final MotionEvent motionEvent) {
        List<MotionEvent> unpackMotionEvent = unpackMotionEvent(motionEvent);
        FalsingClassifier.logVerbose("Unpacked into: " + unpackMotionEvent.size());
        if (BrightLineFalsingManager.DEBUG) {
            for (MotionEvent motionEvent2 : unpackMotionEvent) {
                FalsingClassifier.logVerbose("x,y,t: " + motionEvent2.getX() + "," + motionEvent2.getY() + "," + motionEvent2.getEventTime());
            }
        }
        if (motionEvent.getActionMasked() == 0) {
            completePriorGesture();
        }
        this.mRecentMotionEvents.addAll(unpackMotionEvent);
        FalsingClassifier.logVerbose("Size: " + this.mRecentMotionEvents.size());
        this.mMotionEventListeners.forEach(new Consumer() { // from class: com.android.systemui.classifier.FalsingDataProvider$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                FalsingDataProvider.$r8$lambda$05zcr9mejHigrFbU9thpdiGo37s(motionEvent, (FalsingDataProvider.MotionEventListener) obj);
            }
        });
        this.mDirty = true;
    }

    public void onMotionEventComplete() {
        if (this.mRecentMotionEvents.isEmpty()) {
            return;
        }
        TimeLimitedMotionEventBuffer timeLimitedMotionEventBuffer = this.mRecentMotionEvents;
        int actionMasked = timeLimitedMotionEventBuffer.get(timeLimitedMotionEventBuffer.size() - 1).getActionMasked();
        if (actionMasked == 1 || actionMasked == 3) {
            completePriorGesture();
        }
    }

    public void onSessionEnd() {
        Iterator<MotionEvent> it = this.mRecentMotionEvents.iterator();
        while (it.hasNext()) {
            it.next().recycle();
        }
        this.mRecentMotionEvents.clear();
        this.mDirty = true;
        this.mSessionListeners.forEach(new Consumer() { // from class: com.android.systemui.classifier.FalsingDataProvider$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((FalsingDataProvider.SessionListener) obj).onSessionEnded();
            }
        });
    }

    public void onSessionStarted() {
        this.mSessionListeners.forEach(new Consumer() { // from class: com.android.systemui.classifier.FalsingDataProvider$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((FalsingDataProvider.SessionListener) obj).onSessionStarted();
            }
        });
    }

    public final void recalculateData() {
        if (this.mDirty) {
            if (this.mRecentMotionEvents.isEmpty()) {
                this.mFirstRecentMotionEvent = null;
                this.mLastMotionEvent = null;
            } else {
                this.mFirstRecentMotionEvent = this.mRecentMotionEvents.get(0);
                TimeLimitedMotionEventBuffer timeLimitedMotionEventBuffer = this.mRecentMotionEvents;
                this.mLastMotionEvent = timeLimitedMotionEventBuffer.get(timeLimitedMotionEventBuffer.size() - 1);
            }
            calculateAngleInternal();
            this.mDirty = false;
        }
    }

    public void removeGestureCompleteListener(GestureFinalizedListener gestureFinalizedListener) {
        this.mGestureFinalizedListeners.remove(gestureFinalizedListener);
    }

    public void removeMotionEventListener(MotionEventListener motionEventListener) {
        this.mMotionEventListeners.remove(motionEventListener);
    }

    public void removeSessionListener(SessionListener sessionListener) {
        this.mSessionListeners.remove(sessionListener);
    }

    public void setJustUnlockedWithFace(boolean z) {
        this.mJustUnlockedWithFace = z;
    }

    public final List<MotionEvent> unpackMotionEvent(MotionEvent motionEvent) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int pointerCount = motionEvent.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            MotionEvent.PointerProperties pointerProperties = new MotionEvent.PointerProperties();
            motionEvent.getPointerProperties(i, pointerProperties);
            arrayList2.add(pointerProperties);
        }
        MotionEvent.PointerProperties[] pointerPropertiesArr = new MotionEvent.PointerProperties[arrayList2.size()];
        arrayList2.toArray(pointerPropertiesArr);
        int historySize = motionEvent.getHistorySize();
        for (int i2 = 0; i2 < historySize; i2++) {
            ArrayList arrayList3 = new ArrayList();
            for (int i3 = 0; i3 < pointerCount; i3++) {
                MotionEvent.PointerCoords pointerCoords = new MotionEvent.PointerCoords();
                motionEvent.getHistoricalPointerCoords(i3, i2, pointerCoords);
                arrayList3.add(pointerCoords);
            }
            arrayList.add(MotionEvent.obtain(motionEvent.getDownTime(), motionEvent.getHistoricalEventTime(i2), motionEvent.getAction(), pointerCount, pointerPropertiesArr, (MotionEvent.PointerCoords[]) arrayList3.toArray(new MotionEvent.PointerCoords[0]), motionEvent.getMetaState(), motionEvent.getButtonState(), motionEvent.getXPrecision(), motionEvent.getYPrecision(), motionEvent.getDeviceId(), motionEvent.getEdgeFlags(), motionEvent.getSource(), motionEvent.getFlags()));
        }
        arrayList.add(MotionEvent.obtainNoHistory(motionEvent));
        return arrayList;
    }
}