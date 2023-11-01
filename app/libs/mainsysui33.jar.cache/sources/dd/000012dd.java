package com.android.systemui.classifier;

import com.android.systemui.classifier.FalsingClassifier;
import com.android.systemui.classifier.HistoryTracker;
import com.android.systemui.util.time.SystemClock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;

/* loaded from: mainsysui33.jar:com/android/systemui/classifier/HistoryTracker.class */
public class HistoryTracker {
    public static final double HISTORY_DECAY = Math.pow(10.0d, (Math.log10(0.1d) / 10000.0d) * 100.0d);
    public final SystemClock mSystemClock;
    public DelayQueue<CombinedResult> mResults = new DelayQueue<>();
    public final List<BeliefListener> mBeliefListeners = new ArrayList();

    /* loaded from: mainsysui33.jar:com/android/systemui/classifier/HistoryTracker$BeliefListener.class */
    public interface BeliefListener {
        void onBeliefChanged(double d);
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/classifier/HistoryTracker$CombinedResult.class */
    public class CombinedResult implements Delayed {
        public final long mExpiryMs;
        public final double mScore;

        public CombinedResult(long j, double d) {
            HistoryTracker.this = r7;
            this.mExpiryMs = j + 10000;
            this.mScore = d;
        }

        @Override // java.lang.Comparable
        public int compareTo(Delayed delayed) {
            TimeUnit timeUnit = TimeUnit.MILLISECONDS;
            return Long.compare(getDelay(timeUnit), delayed.getDelay(timeUnit));
        }

        public double getDecayedScore(long j) {
            return ((this.mScore - 0.5d) * Math.pow(HistoryTracker.HISTORY_DECAY, (10000 - (this.mExpiryMs - j)) / 100.0d)) + 0.5d;
        }

        @Override // java.util.concurrent.Delayed
        public long getDelay(TimeUnit timeUnit) {
            return timeUnit.convert(this.mExpiryMs - HistoryTracker.this.mSystemClock.uptimeMillis(), TimeUnit.MILLISECONDS);
        }

        public double getScore() {
            return this.mScore;
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.classifier.HistoryTracker$$ExternalSyntheticLambda4.apply(java.lang.Object):java.lang.Object] */
    /* renamed from: $r8$lambda$NsXRZjeRU1zndXn4-WHgJ341TE8 */
    public static /* synthetic */ Double m1712$r8$lambda$NsXRZjeRU1zndXn4WHgJ341TE8(double d, CombinedResult combinedResult) {
        return lambda$falseConfidence$2(d, combinedResult);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.classifier.HistoryTracker$$ExternalSyntheticLambda0.apply(java.lang.Object):java.lang.Object] */
    /* renamed from: $r8$lambda$OUsyX-Yv9TYNpjVP09yI7fxzQdk */
    public static /* synthetic */ Double m1713$r8$lambda$OUsyXYv9TYNpjVP09yI7fxzQdk(long j, CombinedResult combinedResult) {
        return lambda$falseBelief$0(j, combinedResult);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.classifier.HistoryTracker$$ExternalSyntheticLambda1.apply(java.lang.Object, java.lang.Object):java.lang.Object] */
    public static /* synthetic */ Double $r8$lambda$Y_8E7bDXltKYg6GAjLTe9FzG5o4(Double d, Double d2) {
        return lambda$falseBelief$1(d, d2);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.classifier.HistoryTracker$$ExternalSyntheticLambda5.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$l3cB1RVFd0kFdpTMwikC3APtt2I(HistoryTracker historyTracker, BeliefListener beliefListener) {
        historyTracker.lambda$addResults$3(beliefListener);
    }

    public HistoryTracker(SystemClock systemClock) {
        this.mSystemClock = systemClock;
    }

    public /* synthetic */ void lambda$addResults$3(BeliefListener beliefListener) {
        beliefListener.onBeliefChanged(falseBelief());
    }

    public static /* synthetic */ Double lambda$falseBelief$0(long j, CombinedResult combinedResult) {
        return Double.valueOf(combinedResult.getDecayedScore(j));
    }

    public static /* synthetic */ Double lambda$falseBelief$1(Double d, Double d2) {
        return Double.valueOf((d.doubleValue() * d2.doubleValue()) / ((d.doubleValue() * d2.doubleValue()) + ((1.0d - d.doubleValue()) * (1.0d - d2.doubleValue()))));
    }

    public static /* synthetic */ Double lambda$falseConfidence$2(double d, CombinedResult combinedResult) {
        return Double.valueOf(Math.pow(combinedResult.getScore() - d, 2.0d));
    }

    public void addBeliefListener(BeliefListener beliefListener) {
        this.mBeliefListeners.add(beliefListener);
    }

    public void addResults(Collection<FalsingClassifier.Result> collection, long j) {
        double d;
        double d2;
        Iterator<FalsingClassifier.Result> it = collection.iterator();
        double d3 = 0.0d;
        while (true) {
            d = d3;
            if (!it.hasNext()) {
                break;
            }
            FalsingClassifier.Result next = it.next();
            d3 = d + ((next.isFalse() ? 0.5d : -0.5d) * next.getConfidence()) + 0.5d;
        }
        double size = d / collection.size();
        if (size == 1.0d) {
            d2 = 0.99999d;
        } else {
            d2 = size;
            if (size == 0.0d) {
                d2 = 1.0E-5d;
            }
        }
        do {
        } while (this.mResults.poll() != null);
        this.mResults.add((DelayQueue<CombinedResult>) new CombinedResult(j, d2));
        this.mBeliefListeners.forEach(new Consumer() { // from class: com.android.systemui.classifier.HistoryTracker$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                HistoryTracker.$r8$lambda$l3cB1RVFd0kFdpTMwikC3APtt2I(HistoryTracker.this, (HistoryTracker.BeliefListener) obj);
            }
        });
    }

    public double falseBelief() {
        do {
        } while (this.mResults.poll() != null);
        if (this.mResults.isEmpty()) {
            return 0.5d;
        }
        final long uptimeMillis = this.mSystemClock.uptimeMillis();
        return ((Double) this.mResults.stream().map(new Function() { // from class: com.android.systemui.classifier.HistoryTracker$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return HistoryTracker.m1713$r8$lambda$OUsyXYv9TYNpjVP09yI7fxzQdk(uptimeMillis, (HistoryTracker.CombinedResult) obj);
            }
        }).reduce(Double.valueOf(0.5d), new BinaryOperator() { // from class: com.android.systemui.classifier.HistoryTracker$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return HistoryTracker.$r8$lambda$Y_8E7bDXltKYg6GAjLTe9FzG5o4((Double) obj, (Double) obj2);
            }
        })).doubleValue();
    }

    public double falseConfidence() {
        do {
        } while (this.mResults.poll() != null);
        if (this.mResults.isEmpty()) {
            return 0.0d;
        }
        final double doubleValue = ((Double) this.mResults.stream().map(new Function() { // from class: com.android.systemui.classifier.HistoryTracker$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((HistoryTracker.CombinedResult) obj).getScore());
            }
        }).reduce(Double.valueOf(0.0d), new BinaryOperator() { // from class: com.android.systemui.classifier.HistoryTracker$$ExternalSyntheticLambda3
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return Double.valueOf(Double.sum(((Double) obj).doubleValue(), ((Double) obj2).doubleValue()));
            }
        })).doubleValue() / this.mResults.size();
        return 1.0d - Math.sqrt(((Double) this.mResults.stream().map(new Function() { // from class: com.android.systemui.classifier.HistoryTracker$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return HistoryTracker.m1712$r8$lambda$NsXRZjeRU1zndXn4WHgJ341TE8(doubleValue, (HistoryTracker.CombinedResult) obj);
            }
        }).reduce(Double.valueOf(0.0d), new BinaryOperator() { // from class: com.android.systemui.classifier.HistoryTracker$$ExternalSyntheticLambda3
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return Double.valueOf(Double.sum(((Double) obj).doubleValue(), ((Double) obj2).doubleValue()));
            }
        })).doubleValue() / this.mResults.size());
    }

    public void removeBeliefListener(BeliefListener beliefListener) {
        this.mBeliefListeners.remove(beliefListener);
    }
}