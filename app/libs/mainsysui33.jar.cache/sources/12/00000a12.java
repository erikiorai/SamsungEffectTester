package com.airbnb.lottie;

import android.graphics.Rect;
import androidx.collection.LongSparseArray;
import androidx.collection.SparseArrayCompat;
import com.airbnb.lottie.model.Font;
import com.airbnb.lottie.model.FontCharacter;
import com.airbnb.lottie.model.Marker;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.utils.Logger;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/LottieComposition.class */
public class LottieComposition {
    public Rect bounds;
    public SparseArrayCompat<FontCharacter> characters;
    public float endFrame;
    public Map<String, Font> fonts;
    public float frameRate;
    public boolean hasDashPattern;
    public Map<String, LottieImageAsset> images;
    public LongSparseArray<Layer> layerMap;
    public List<Layer> layers;
    public List<Marker> markers;
    public Map<String, List<Layer>> precomps;
    public float startFrame;
    public final PerformanceTracker performanceTracker = new PerformanceTracker();
    public final HashSet<String> warnings = new HashSet<>();
    public int maskAndMatteCount = 0;

    public void addWarning(String str) {
        Logger.warning(str);
        this.warnings.add(str);
    }

    public Rect getBounds() {
        return this.bounds;
    }

    public SparseArrayCompat<FontCharacter> getCharacters() {
        return this.characters;
    }

    public float getDuration() {
        return (getDurationFrames() / this.frameRate) * 1000.0f;
    }

    public float getDurationFrames() {
        return this.endFrame - this.startFrame;
    }

    public float getEndFrame() {
        return this.endFrame;
    }

    public Map<String, Font> getFonts() {
        return this.fonts;
    }

    public float getFrameRate() {
        return this.frameRate;
    }

    public Map<String, LottieImageAsset> getImages() {
        return this.images;
    }

    public List<Layer> getLayers() {
        return this.layers;
    }

    public Marker getMarker(String str) {
        this.markers.size();
        for (int i = 0; i < this.markers.size(); i++) {
            Marker marker = this.markers.get(i);
            if (marker.matchesName(str)) {
                return marker;
            }
        }
        return null;
    }

    public int getMaskAndMatteCount() {
        return this.maskAndMatteCount;
    }

    public PerformanceTracker getPerformanceTracker() {
        return this.performanceTracker;
    }

    public List<Layer> getPrecomps(String str) {
        return this.precomps.get(str);
    }

    public float getStartFrame() {
        return this.startFrame;
    }

    public boolean hasDashPattern() {
        return this.hasDashPattern;
    }

    public void incrementMatteOrMaskCount(int i) {
        this.maskAndMatteCount += i;
    }

    public void init(Rect rect, float f, float f2, float f3, List<Layer> list, LongSparseArray<Layer> longSparseArray, Map<String, List<Layer>> map, Map<String, LottieImageAsset> map2, SparseArrayCompat<FontCharacter> sparseArrayCompat, Map<String, Font> map3, List<Marker> list2) {
        this.bounds = rect;
        this.startFrame = f;
        this.endFrame = f2;
        this.frameRate = f3;
        this.layers = list;
        this.layerMap = longSparseArray;
        this.precomps = map;
        this.images = map2;
        this.characters = sparseArrayCompat;
        this.fonts = map3;
        this.markers = list2;
    }

    public Layer layerModelForId(long j) {
        return this.layerMap.get(j);
    }

    public void setHasDashPattern(boolean z) {
        this.hasDashPattern = z;
    }

    public void setPerformanceTrackingEnabled(boolean z) {
        this.performanceTracker.setEnabled(z);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("LottieComposition:\n");
        for (Layer layer : this.layers) {
            sb.append(layer.toString("\t"));
        }
        return sb.toString();
    }
}