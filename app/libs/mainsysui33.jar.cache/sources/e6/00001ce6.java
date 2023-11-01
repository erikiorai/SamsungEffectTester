package com.android.systemui.media.controls.ui;

import android.os.Trace;
import com.android.systemui.util.animation.MeasurementOutput;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import kotlin.Unit;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaHostStatesManager.class */
public final class MediaHostStatesManager {
    public final Set<Callback> callbacks = new LinkedHashSet();
    public final Set<MediaViewController> controllers = new LinkedHashSet();
    public final Map<Integer, MeasurementOutput> carouselSizes = new LinkedHashMap();
    public final Map<Integer, MediaHostState> mediaHostStates = new LinkedHashMap();

    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaHostStatesManager$Callback.class */
    public interface Callback {
        void onHostStateChanged(int i, MediaHostState mediaHostState);
    }

    public final void addCallback(Callback callback) {
        this.callbacks.add(callback);
    }

    public final void addController(MediaViewController mediaViewController) {
        this.controllers.add(mediaViewController);
    }

    public final Map<Integer, MeasurementOutput> getCarouselSizes() {
        return this.carouselSizes;
    }

    public final Map<Integer, MediaHostState> getMediaHostStates() {
        return this.mediaHostStates;
    }

    public final void removeController(MediaViewController mediaViewController) {
        this.controllers.remove(mediaViewController);
    }

    public final MeasurementOutput updateCarouselDimensions(int i, MediaHostState mediaHostState) {
        if (!Trace.isTagEnabled(4096L)) {
            MeasurementOutput measurementOutput = new MeasurementOutput(0, 0);
            for (MediaViewController mediaViewController : this.controllers) {
                MeasurementOutput measurementsForState = mediaViewController.getMeasurementsForState(mediaHostState);
                if (measurementsForState != null) {
                    if (measurementsForState.getMeasuredHeight() > measurementOutput.getMeasuredHeight()) {
                        measurementOutput.setMeasuredHeight(measurementsForState.getMeasuredHeight());
                    }
                    if (measurementsForState.getMeasuredWidth() > measurementOutput.getMeasuredWidth()) {
                        measurementOutput.setMeasuredWidth(measurementsForState.getMeasuredWidth());
                    }
                }
            }
            this.carouselSizes.put(Integer.valueOf(i), measurementOutput);
            return measurementOutput;
        }
        Trace.traceBegin(4096L, "MediaHostStatesManager#updateCarouselDimensions");
        try {
            MeasurementOutput measurementOutput2 = new MeasurementOutput(0, 0);
            for (MediaViewController mediaViewController2 : this.controllers) {
                MeasurementOutput measurementsForState2 = mediaViewController2.getMeasurementsForState(mediaHostState);
                if (measurementsForState2 != null) {
                    if (measurementsForState2.getMeasuredHeight() > measurementOutput2.getMeasuredHeight()) {
                        measurementOutput2.setMeasuredHeight(measurementsForState2.getMeasuredHeight());
                    }
                    if (measurementsForState2.getMeasuredWidth() > measurementOutput2.getMeasuredWidth()) {
                        measurementOutput2.setMeasuredWidth(measurementsForState2.getMeasuredWidth());
                    }
                }
            }
            this.carouselSizes.put(Integer.valueOf(i), measurementOutput2);
            Trace.traceEnd(4096L);
            return measurementOutput2;
        } catch (Throwable th) {
            Trace.traceEnd(4096L);
            throw th;
        }
    }

    public final void updateHostState(int i, MediaHostState mediaHostState) {
        if (!Trace.isTagEnabled(4096L)) {
            if (mediaHostState.equals(this.mediaHostStates.get(Integer.valueOf(i)))) {
                return;
            }
            MediaHostState copy = mediaHostState.copy();
            this.mediaHostStates.put(Integer.valueOf(i), copy);
            updateCarouselDimensions(i, mediaHostState);
            for (MediaViewController mediaViewController : this.controllers) {
                mediaViewController.getStateCallback().onHostStateChanged(i, copy);
            }
            for (Callback callback : this.callbacks) {
                callback.onHostStateChanged(i, copy);
            }
            return;
        }
        Trace.traceBegin(4096L, "MediaHostStatesManager#updateHostState");
        try {
            if (!mediaHostState.equals(this.mediaHostStates.get(Integer.valueOf(i)))) {
                MediaHostState copy2 = mediaHostState.copy();
                this.mediaHostStates.put(Integer.valueOf(i), copy2);
                updateCarouselDimensions(i, mediaHostState);
                for (MediaViewController mediaViewController2 : this.controllers) {
                    mediaViewController2.getStateCallback().onHostStateChanged(i, copy2);
                }
                for (Callback callback2 : this.callbacks) {
                    callback2.onHostStateChanged(i, copy2);
                }
            }
            Unit unit = Unit.INSTANCE;
            Trace.traceEnd(4096L);
        } catch (Throwable th) {
            Trace.traceEnd(4096L);
            throw th;
        }
    }
}