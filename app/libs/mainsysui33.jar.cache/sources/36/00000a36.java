package com.airbnb.lottie;

import androidx.collection.ArraySet;
import androidx.core.util.Pair;
import com.airbnb.lottie.utils.MeanCalculator;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/PerformanceTracker.class */
public class PerformanceTracker {
    public boolean enabled = false;
    public final Set<FrameListener> frameListeners = new ArraySet();
    public final Map<String, MeanCalculator> layerRenderTimes = new HashMap();
    public final Comparator<Pair<String, Float>> floatComparator = new Comparator<Pair<String, Float>>() { // from class: com.airbnb.lottie.PerformanceTracker.1
        /* JADX DEBUG: Method merged with bridge method */
        @Override // java.util.Comparator
        public int compare(Pair<String, Float> pair, Pair<String, Float> pair2) {
            float floatValue = pair.second.floatValue();
            float floatValue2 = pair2.second.floatValue();
            if (floatValue2 > floatValue) {
                return 1;
            }
            return floatValue > floatValue2 ? -1 : 0;
        }
    };

    /* loaded from: mainsysui33.jar:com/airbnb/lottie/PerformanceTracker$FrameListener.class */
    public interface FrameListener {
        void onFrameRendered(float f);
    }

    public void recordRenderTime(String str, float f) {
        if (this.enabled) {
            MeanCalculator meanCalculator = this.layerRenderTimes.get(str);
            MeanCalculator meanCalculator2 = meanCalculator;
            if (meanCalculator == null) {
                meanCalculator2 = new MeanCalculator();
                this.layerRenderTimes.put(str, meanCalculator2);
            }
            meanCalculator2.add(f);
            if (str.equals("__container")) {
                for (FrameListener frameListener : this.frameListeners) {
                    frameListener.onFrameRendered(f);
                }
            }
        }
    }

    public void setEnabled(boolean z) {
        this.enabled = z;
    }
}