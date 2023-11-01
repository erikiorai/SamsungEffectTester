package com.android.systemui.classifier;

import android.net.Uri;
import android.os.Build;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.classifier.BrightLineFalsingManager;
import com.android.systemui.classifier.FalsingClassifier;
import com.android.systemui.classifier.FalsingDataProvider;
import com.android.systemui.classifier.HistoryTracker;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: mainsysui33.jar:com/android/systemui/classifier/BrightLineFalsingManager.class */
public class BrightLineFalsingManager implements FalsingManager {
    public static final boolean DEBUG = Log.isLoggable("FalsingManager", 3);
    public static final Queue<String> RECENT_INFO_LOG = new ArrayDeque(41);
    public static final Queue<DebugSwipeRecord> RECENT_SWIPES = new ArrayDeque(21);
    public AccessibilityManager mAccessibilityManager;
    public final HistoryTracker.BeliefListener mBeliefListener;
    public final Collection<FalsingClassifier> mClassifiers;
    public final FalsingDataProvider mDataProvider;
    public boolean mDestroyed;
    public final DoubleTapClassifier mDoubleTapClassifier;
    public final List<FalsingManager.FalsingBeliefListener> mFalsingBeliefListeners = new ArrayList();
    public List<FalsingManager.FalsingTapListener> mFalsingTapListeners = new ArrayList();
    public FeatureFlags mFeatureFlags;
    public final FalsingDataProvider.GestureFinalizedListener mGestureFinalizedListener;
    public final HistoryTracker mHistoryTracker;
    public int mIsFalseTouchCalls;
    public final KeyguardStateController mKeyguardStateController;
    public FalsingManager.ProximityEvent mLastProximityEvent;
    public final LongTapClassifier mLongTapClassifier;
    public final MetricsLogger mMetricsLogger;
    public int mPriorInteractionType;
    public Collection<FalsingClassifier.Result> mPriorResults;
    public final FalsingDataProvider.SessionListener mSessionListener;
    public final SingleTapClassifier mSingleTapClassifier;
    public final boolean mTestHarness;

    /* renamed from: com.android.systemui.classifier.BrightLineFalsingManager$3 */
    /* loaded from: mainsysui33.jar:com/android/systemui/classifier/BrightLineFalsingManager$3.class */
    public class AnonymousClass3 implements FalsingDataProvider.GestureFinalizedListener {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.classifier.BrightLineFalsingManager$3$$ExternalSyntheticLambda2.apply(java.lang.Object):java.lang.Object] */
        /* renamed from: $r8$lambda$SVZi0LDN6v2FtxnAcJfE5C-IbsE */
        public static /* synthetic */ XYDt m1681$r8$lambda$SVZi0LDN6v2FtxnAcJfE5CIbsE(MotionEvent motionEvent) {
            return lambda$onGestureFinalized$1(motionEvent);
        }

        public AnonymousClass3() {
            BrightLineFalsingManager.this = r4;
        }

        public static /* synthetic */ void lambda$onGestureFinalized$0(FalsingClassifier.Result result) {
            String reason;
            if (!result.isFalse() || (reason = result.getReason()) == null) {
                return;
            }
            BrightLineFalsingManager.logInfo(reason);
        }

        public static /* synthetic */ XYDt lambda$onGestureFinalized$1(MotionEvent motionEvent) {
            return new XYDt((int) motionEvent.getX(), (int) motionEvent.getY(), (int) (motionEvent.getEventTime() - motionEvent.getDownTime()));
        }

        @Override // com.android.systemui.classifier.FalsingDataProvider.GestureFinalizedListener
        public void onGestureFinalized(long j) {
            if (BrightLineFalsingManager.this.mPriorResults == null) {
                BrightLineFalsingManager.this.mHistoryTracker.addResults(Collections.singleton(FalsingClassifier.Result.falsed(BrightLineFalsingManager.this.mSingleTapClassifier.isTap(BrightLineFalsingManager.this.mDataProvider.getRecentMotionEvents(), 0.0d).isFalse() ? 0.7d : 0.8d, getClass().getSimpleName(), "unclassified")), j);
                return;
            }
            boolean anyMatch = BrightLineFalsingManager.this.mPriorResults.stream().anyMatch(new Predicate() { // from class: com.android.systemui.classifier.BrightLineFalsingManager$3$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((FalsingClassifier.Result) obj).isFalse();
                }
            });
            BrightLineFalsingManager.this.mPriorResults.forEach(new Consumer() { // from class: com.android.systemui.classifier.BrightLineFalsingManager$3$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    BrightLineFalsingManager.AnonymousClass3.lambda$onGestureFinalized$0((FalsingClassifier.Result) obj);
                }
            });
            if (Build.IS_ENG || Build.IS_USERDEBUG) {
                BrightLineFalsingManager.RECENT_SWIPES.add(new DebugSwipeRecord(anyMatch, BrightLineFalsingManager.this.mPriorInteractionType, (List) BrightLineFalsingManager.this.mDataProvider.getRecentMotionEvents().stream().map(new Function() { // from class: com.android.systemui.classifier.BrightLineFalsingManager$3$$ExternalSyntheticLambda2
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return BrightLineFalsingManager.AnonymousClass3.m1681$r8$lambda$SVZi0LDN6v2FtxnAcJfE5CIbsE((MotionEvent) obj);
                    }
                }).collect(Collectors.toList())));
                while (BrightLineFalsingManager.RECENT_SWIPES.size() > 40) {
                    BrightLineFalsingManager.RECENT_SWIPES.remove();
                }
            }
            BrightLineFalsingManager.this.mHistoryTracker.addResults(BrightLineFalsingManager.this.mPriorResults, j);
            BrightLineFalsingManager.this.mPriorResults = null;
            BrightLineFalsingManager.this.mPriorInteractionType = 7;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/classifier/BrightLineFalsingManager$DebugSwipeRecord.class */
    public static class DebugSwipeRecord {
        public final int mInteractionType;
        public final boolean mIsFalse;
        public final List<XYDt> mRecentMotionEvents;

        public DebugSwipeRecord(boolean z, int i, List<XYDt> list) {
            this.mIsFalse = z;
            this.mInteractionType = i;
            this.mRecentMotionEvents = list;
        }

        public String getString() {
            StringJoiner stringJoiner = new StringJoiner(",");
            stringJoiner.add(Integer.toString(1)).add(this.mIsFalse ? "1" : "0").add(Integer.toString(this.mInteractionType));
            for (XYDt xYDt : this.mRecentMotionEvents) {
                stringJoiner.add(xYDt.toString());
            }
            return stringJoiner.toString();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/classifier/BrightLineFalsingManager$XYDt.class */
    public static class XYDt {
        public final int mDT;
        public final int mX;
        public final int mY;

        public XYDt(int i, int i2, int i3) {
            this.mX = i;
            this.mY = i2;
            this.mDT = i3;
        }

        public String toString() {
            return this.mX + "," + this.mY + "," + this.mDT;
        }
    }

    public BrightLineFalsingManager(FalsingDataProvider falsingDataProvider, MetricsLogger metricsLogger, Set<FalsingClassifier> set, SingleTapClassifier singleTapClassifier, LongTapClassifier longTapClassifier, DoubleTapClassifier doubleTapClassifier, HistoryTracker historyTracker, KeyguardStateController keyguardStateController, AccessibilityManager accessibilityManager, boolean z, FeatureFlags featureFlags) {
        FalsingDataProvider.SessionListener sessionListener = new FalsingDataProvider.SessionListener() { // from class: com.android.systemui.classifier.BrightLineFalsingManager.1
            {
                BrightLineFalsingManager.this = this;
            }

            @Override // com.android.systemui.classifier.FalsingDataProvider.SessionListener
            public void onSessionEnded() {
                BrightLineFalsingManager.this.mLastProximityEvent = null;
                BrightLineFalsingManager.this.mClassifiers.forEach(new Consumer() { // from class: com.android.systemui.classifier.BrightLineFalsingManager$1$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((FalsingClassifier) obj).onSessionEnded();
                    }
                });
            }

            @Override // com.android.systemui.classifier.FalsingDataProvider.SessionListener
            public void onSessionStarted() {
                BrightLineFalsingManager.this.mClassifiers.forEach(new Consumer() { // from class: com.android.systemui.classifier.BrightLineFalsingManager$1$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((FalsingClassifier) obj).onSessionStarted();
                    }
                });
            }
        };
        this.mSessionListener = sessionListener;
        HistoryTracker.BeliefListener beliefListener = new HistoryTracker.BeliefListener() { // from class: com.android.systemui.classifier.BrightLineFalsingManager.2
            {
                BrightLineFalsingManager.this = this;
            }

            @Override // com.android.systemui.classifier.HistoryTracker.BeliefListener
            public void onBeliefChanged(double d) {
                BrightLineFalsingManager.logInfo(String.format("{belief=%s confidence=%s}", Double.valueOf(BrightLineFalsingManager.this.mHistoryTracker.falseBelief()), Double.valueOf(BrightLineFalsingManager.this.mHistoryTracker.falseConfidence())));
                if (d > 0.9d) {
                    BrightLineFalsingManager.this.mFalsingBeliefListeners.forEach(new Consumer() { // from class: com.android.systemui.classifier.BrightLineFalsingManager$2$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((FalsingManager.FalsingBeliefListener) obj).onFalse();
                        }
                    });
                    BrightLineFalsingManager.logInfo("Triggering False Event (Threshold: 0.9)");
                }
            }
        };
        this.mBeliefListener = beliefListener;
        AnonymousClass3 anonymousClass3 = new AnonymousClass3();
        this.mGestureFinalizedListener = anonymousClass3;
        this.mPriorInteractionType = 7;
        this.mDataProvider = falsingDataProvider;
        this.mMetricsLogger = metricsLogger;
        this.mClassifiers = set;
        this.mSingleTapClassifier = singleTapClassifier;
        this.mLongTapClassifier = longTapClassifier;
        this.mDoubleTapClassifier = doubleTapClassifier;
        this.mHistoryTracker = historyTracker;
        this.mKeyguardStateController = keyguardStateController;
        this.mAccessibilityManager = accessibilityManager;
        this.mTestHarness = z;
        this.mFeatureFlags = featureFlags;
        falsingDataProvider.addSessionListener(sessionListener);
        falsingDataProvider.addGestureCompleteListener(anonymousClass3);
        historyTracker.addBeliefListener(beliefListener);
    }

    public static Collection<FalsingClassifier.Result> getPassedResult(double d) {
        return Collections.singleton(FalsingClassifier.Result.passed(d));
    }

    public /* synthetic */ FalsingClassifier.Result lambda$isFalseTouch$0(int i, boolean[] zArr, FalsingClassifier falsingClassifier) {
        FalsingClassifier.Result classifyGesture = falsingClassifier.classifyGesture(i, this.mHistoryTracker.falseBelief(), this.mHistoryTracker.falseConfidence());
        zArr[0] = zArr[0] | classifyGesture.isFalse();
        return classifyGesture;
    }

    public static void logDebug(String str) {
        logDebug(str, null);
    }

    public static void logDebug(String str, Throwable th) {
        if (DEBUG) {
            Log.d("FalsingManager", str, th);
        }
    }

    public static void logInfo(String str) {
        Log.i("FalsingManager", str);
        RECENT_INFO_LOG.add(str);
        while (true) {
            Queue<String> queue = RECENT_INFO_LOG;
            if (queue.size() <= 40) {
                return;
            }
            queue.remove();
        }
    }

    public static void logVerbose(String str) {
        if (DEBUG) {
            Log.v("FalsingManager", str);
        }
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public void addFalsingBeliefListener(FalsingManager.FalsingBeliefListener falsingBeliefListener) {
        this.mFalsingBeliefListeners.add(falsingBeliefListener);
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public void addTapListener(FalsingManager.FalsingTapListener falsingTapListener) {
        this.mFalsingTapListeners.add(falsingTapListener);
    }

    public final void checkDestroyed() {
        if (this.mDestroyed) {
            Log.wtf("FalsingManager", "Tried to use FalsingManager after being destroyed!");
        }
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public void cleanupInternal() {
        this.mDestroyed = true;
        this.mDataProvider.removeSessionListener(this.mSessionListener);
        this.mDataProvider.removeGestureCompleteListener(this.mGestureFinalizedListener);
        this.mClassifiers.forEach(new Consumer() { // from class: com.android.systemui.classifier.BrightLineFalsingManager$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((FalsingClassifier) obj).cleanup();
            }
        });
        this.mFalsingBeliefListeners.clear();
        this.mHistoryTracker.removeBeliefListener(this.mBeliefListener);
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println("BRIGHTLINE FALSING MANAGER");
        indentingPrintWriter.print("classifierEnabled=");
        indentingPrintWriter.println(isClassifierEnabled() ? 1 : 0);
        indentingPrintWriter.print("mJustUnlockedWithFace=");
        indentingPrintWriter.println(this.mDataProvider.isJustUnlockedWithFace() ? 1 : 0);
        indentingPrintWriter.print("isDocked=");
        indentingPrintWriter.println(this.mDataProvider.isDocked() ? 1 : 0);
        indentingPrintWriter.print("width=");
        indentingPrintWriter.println(this.mDataProvider.getWidthPixels());
        indentingPrintWriter.print("height=");
        indentingPrintWriter.println(this.mDataProvider.getHeightPixels());
        indentingPrintWriter.println();
        Queue<DebugSwipeRecord> queue = RECENT_SWIPES;
        if (queue.size() != 0) {
            indentingPrintWriter.println("Recent swipes:");
            indentingPrintWriter.increaseIndent();
            for (DebugSwipeRecord debugSwipeRecord : queue) {
                indentingPrintWriter.println(debugSwipeRecord.getString());
                indentingPrintWriter.println();
            }
            indentingPrintWriter.decreaseIndent();
        } else {
            indentingPrintWriter.println("No recent swipes");
        }
        indentingPrintWriter.println();
        indentingPrintWriter.println("Recent falsing info:");
        indentingPrintWriter.increaseIndent();
        for (String str : RECENT_INFO_LOG) {
            indentingPrintWriter.println(str);
        }
        indentingPrintWriter.println();
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public boolean isClassifierEnabled() {
        return true;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public boolean isFalseDoubleTap() {
        checkDestroyed();
        if (skipFalsing(7)) {
            this.mPriorResults = getPassedResult(1.0d);
            logDebug("Skipped falsing");
            return false;
        }
        FalsingClassifier.Result classifyGesture = this.mDoubleTapClassifier.classifyGesture(7, this.mHistoryTracker.falseBelief(), this.mHistoryTracker.falseConfidence());
        this.mPriorResults = Collections.singleton(classifyGesture);
        logDebug("False Double Tap: " + classifyGesture.isFalse() + " reason=" + classifyGesture.getReason());
        return classifyGesture.isFalse();
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public boolean isFalseLongTap(int i) {
        if (this.mFeatureFlags.isEnabled(Flags.FALSING_FOR_LONG_TAPS)) {
            checkDestroyed();
            if (skipFalsing(7)) {
                this.mPriorResults = getPassedResult(1.0d);
                logDebug("Skipped falsing");
                return false;
            }
            double d = 0.0d;
            if (i != 0) {
                d = i != 1 ? i != 2 ? i != 3 ? 0.0d : 0.6d : 0.3d : 0.1d;
            }
            FalsingClassifier.Result isTap = this.mLongTapClassifier.isTap(this.mDataProvider.getRecentMotionEvents().isEmpty() ? this.mDataProvider.getPriorMotionEvents() : this.mDataProvider.getRecentMotionEvents(), d);
            this.mPriorResults = Collections.singleton(isTap);
            if (isTap.isFalse()) {
                logDebug("False Long Tap: " + isTap.isFalse() + " (simple)");
                return isTap.isFalse();
            } else if (this.mDataProvider.isJustUnlockedWithFace()) {
                this.mPriorResults = getPassedResult(1.0d);
                logDebug("False Long Tap: false (face detected)");
                return false;
            } else {
                this.mPriorResults = getPassedResult(0.1d);
                logDebug("False Long Tap: false (default)");
                return false;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:60:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0102  */
    @Override // com.android.systemui.plugins.FalsingManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean isFalseTap(int i) {
        double d;
        FalsingClassifier.Result isTap;
        checkDestroyed();
        if (skipFalsing(7)) {
            this.mPriorResults = getPassedResult(1.0d);
            logDebug("Skipped falsing");
            return false;
        }
        if (i != 0) {
            if (i == 1) {
                d = 0.1d;
            } else if (i == 2) {
                d = 0.3d;
            } else if (i == 3) {
                d = 0.6d;
            }
            isTap = this.mSingleTapClassifier.isTap(!this.mDataProvider.getRecentMotionEvents().isEmpty() ? this.mDataProvider.getPriorMotionEvents() : this.mDataProvider.getRecentMotionEvents(), d);
            this.mPriorResults = Collections.singleton(isTap);
            if (!isTap.isFalse()) {
                logDebug("False Single Tap: " + isTap.isFalse() + " (simple)");
                return isTap.isFalse();
            } else if (this.mDataProvider.isJustUnlockedWithFace()) {
                this.mPriorResults = getPassedResult(1.0d);
                logDebug("False Single Tap: false (face detected)");
                return false;
            } else if (!isFalseDoubleTap()) {
                logDebug("False Single Tap: false (double tapped)");
                return false;
            } else if (this.mHistoryTracker.falseBelief() <= 0.7d) {
                this.mPriorResults = getPassedResult(0.1d);
                logDebug("False Single Tap: false (default)");
                return false;
            } else {
                this.mPriorResults = Collections.singleton(FalsingClassifier.Result.falsed(0.0d, getClass().getSimpleName(), "bad history"));
                logDebug("False Single Tap: true (bad history)");
                this.mFalsingTapListeners.forEach(new Consumer() { // from class: com.android.systemui.classifier.BrightLineFalsingManager$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((FalsingManager.FalsingTapListener) obj).onAdditionalTapRequired();
                    }
                });
                return true;
            }
        }
        d = 0.0d;
        isTap = this.mSingleTapClassifier.isTap(!this.mDataProvider.getRecentMotionEvents().isEmpty() ? this.mDataProvider.getPriorMotionEvents() : this.mDataProvider.getRecentMotionEvents(), d);
        this.mPriorResults = Collections.singleton(isTap);
        if (!isTap.isFalse()) {
        }
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public boolean isFalseTouch(final int i) {
        checkDestroyed();
        this.mPriorInteractionType = i;
        if (skipFalsing(i)) {
            this.mPriorResults = getPassedResult(1.0d);
            logDebug("Skipped falsing");
            return false;
        }
        int i2 = 1;
        final boolean[] zArr = {false};
        this.mPriorResults = (Collection) this.mClassifiers.stream().map(new Function() { // from class: com.android.systemui.classifier.BrightLineFalsingManager$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                FalsingClassifier.Result lambda$isFalseTouch$0;
                lambda$isFalseTouch$0 = BrightLineFalsingManager.this.lambda$isFalseTouch$0(i, zArr, (FalsingClassifier) obj);
                return lambda$isFalseTouch$0;
            }
        }).collect(Collectors.toList());
        if (i == 18) {
            boolean z = zArr[0];
            if (this.mFeatureFlags.isEnabled(Flags.MEDIA_FALSING_PENALTY)) {
                i2 = 2;
            }
            zArr[0] = isFalseTap(i2) & z;
        }
        logDebug("False Gesture (type: " + i + "): " + zArr[0]);
        return zArr[0];
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public boolean isProximityNear() {
        FalsingManager.ProximityEvent proximityEvent = this.mLastProximityEvent;
        return proximityEvent != null && proximityEvent.getCovered();
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public boolean isReportingEnabled() {
        return false;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public boolean isSimpleTap() {
        checkDestroyed();
        FalsingClassifier.Result isTap = this.mSingleTapClassifier.isTap(this.mDataProvider.getRecentMotionEvents(), 0.0d);
        this.mPriorResults = Collections.singleton(isTap);
        return !isTap.isFalse();
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public boolean isUnlockingDisabled() {
        return false;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public void onProximityEvent(final FalsingManager.ProximityEvent proximityEvent) {
        this.mLastProximityEvent = proximityEvent;
        this.mClassifiers.forEach(new Consumer() { // from class: com.android.systemui.classifier.BrightLineFalsingManager$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((FalsingClassifier) obj).onProximityEvent(FalsingManager.ProximityEvent.this);
            }
        });
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public void onSuccessfulUnlock() {
        int i = this.mIsFalseTouchCalls;
        if (i != 0) {
            this.mMetricsLogger.histogram("falsing_success_after_attempts", i);
            this.mIsFalseTouchCalls = 0;
        }
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public void removeFalsingBeliefListener(FalsingManager.FalsingBeliefListener falsingBeliefListener) {
        this.mFalsingBeliefListeners.remove(falsingBeliefListener);
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public void removeTapListener(FalsingManager.FalsingTapListener falsingTapListener) {
        this.mFalsingTapListeners.remove(falsingTapListener);
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public Uri reportRejectedTouch() {
        return null;
    }

    @Override // com.android.systemui.plugins.FalsingManager
    public boolean shouldEnforceBouncer() {
        return false;
    }

    public final boolean skipFalsing(int i) {
        return i == 16 || !this.mKeyguardStateController.isShowing() || this.mTestHarness || this.mDataProvider.isJustUnlockedWithFace() || this.mDataProvider.isDocked() || this.mAccessibilityManager.isTouchExplorationEnabled() || this.mDataProvider.isA11yAction();
    }
}