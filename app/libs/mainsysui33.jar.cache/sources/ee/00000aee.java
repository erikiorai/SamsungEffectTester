package com.android.app.motiontool;

import android.media.permission.SafeCloseable;
import android.util.Log;
import android.view.View;
import android.view.WindowManagerGlobal;
import com.android.app.motiontool.nano.WindowIdentifier;
import com.android.app.viewcapture.ViewCapture;
import com.android.app.viewcapture.data.nano.ExportedData;
import com.android.app.viewcapture.data.nano.FrameData;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.FutureTask;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/app/motiontool/MotionToolManager.class */
public final class MotionToolManager {
    public static final Companion Companion = new Companion(null);
    public static MotionToolManager INSTANCE;
    public int traceIdCounter;
    public final Map<Integer, TraceMetadata> traces;
    public final ViewCapture viewCapture;
    public final WindowManagerGlobal windowManagerGlobal;

    /* loaded from: mainsysui33.jar:com/android/app/motiontool/MotionToolManager$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final MotionToolManager getInstance(ViewCapture viewCapture, WindowManagerGlobal windowManagerGlobal) {
            MotionToolManager motionToolManager;
            synchronized (this) {
                MotionToolManager motionToolManager2 = MotionToolManager.INSTANCE;
                motionToolManager = motionToolManager2;
                if (motionToolManager2 == null) {
                    motionToolManager = new MotionToolManager(viewCapture, windowManagerGlobal, null);
                    Companion companion = MotionToolManager.Companion;
                    MotionToolManager.INSTANCE = motionToolManager;
                }
            }
            return motionToolManager;
        }
    }

    public MotionToolManager(ViewCapture viewCapture, WindowManagerGlobal windowManagerGlobal) {
        this.viewCapture = viewCapture;
        this.windowManagerGlobal = windowManagerGlobal;
        this.traces = new LinkedHashMap();
    }

    public /* synthetic */ MotionToolManager(ViewCapture viewCapture, WindowManagerGlobal windowManagerGlobal, DefaultConstructorMarker defaultConstructorMarker) {
        this(viewCapture, windowManagerGlobal);
    }

    public final int beginTrace(String str) {
        int i;
        synchronized (this) {
            i = this.traceIdCounter + 1;
            this.traceIdCounter = i;
            Log.d("MotionToolManager", "Begin Trace for id: " + i);
            View rootView = getRootView(str);
            if (rootView == null) {
                throw new WindowNotFoundException(str);
            }
            SafeCloseable startCapture = this.viewCapture.startCapture(rootView, str);
            this.traces.put(Integer.valueOf(i), new TraceMetadata(str, 0L, new MotionToolManager$beginTrace$1(startCapture)));
        }
        return i;
    }

    public final ExportedData endTrace(int i) {
        ExportedData pollTrace;
        synchronized (this) {
            Log.d("MotionToolManager", "End Trace for id: " + i);
            TraceMetadata traceMetadata = this.traces.get(Integer.valueOf(i));
            if (traceMetadata == null) {
                throw new UnknownTraceIdException(i);
            }
            TraceMetadata traceMetadata2 = traceMetadata;
            pollTrace = pollTrace(i);
            traceMetadata2.getStopTrace().invoke();
            this.traces.remove(Integer.valueOf(i));
        }
        return pollTrace;
    }

    public final ExportedData getExportedDataFromViewCapture(TraceMetadata traceMetadata) {
        ExportedData exportedData;
        ExportedData exportedData2;
        View rootView = getRootView(traceMetadata.getWindowId());
        if (rootView != null) {
            Optional<FutureTask<ExportedData>> dumpTask = this.viewCapture.getDumpTask(rootView);
            if (dumpTask != null) {
                FrameData[] frameDataArr = null;
                FutureTask<ExportedData> orElse = dumpTask.orElse(null);
                if (orElse != null && (exportedData2 = orElse.get()) != null) {
                    FrameData[] frameDataArr2 = exportedData2.frameData;
                    if (frameDataArr2 != null) {
                        ArrayList arrayList = new ArrayList();
                        for (FrameData frameData : frameDataArr2) {
                            if (frameData.timestamp > traceMetadata.getLastPolledTime()) {
                                arrayList.add(frameData);
                            }
                        }
                        frameDataArr = (FrameData[]) arrayList.toArray(new FrameData[0]);
                    }
                    exportedData2.frameData = frameDataArr;
                    exportedData = exportedData2;
                    return exportedData;
                }
            }
            exportedData = new ExportedData();
            return exportedData;
        }
        throw new WindowNotFoundException(traceMetadata.getWindowId());
    }

    public final View getRootView(String str) {
        return this.windowManagerGlobal.getRootView(str);
    }

    public final boolean hasWindow(WindowIdentifier windowIdentifier) {
        boolean z;
        synchronized (this) {
            z = getRootView(windowIdentifier.rootWindow) != null;
        }
        return z;
    }

    public final ExportedData pollTrace(int i) {
        ExportedData exportedDataFromViewCapture;
        synchronized (this) {
            TraceMetadata traceMetadata = this.traces.get(Integer.valueOf(i));
            if (traceMetadata == null) {
                throw new UnknownTraceIdException(i);
            }
            TraceMetadata traceMetadata2 = traceMetadata;
            exportedDataFromViewCapture = getExportedDataFromViewCapture(traceMetadata2);
            traceMetadata2.updateLastPolledTime(exportedDataFromViewCapture);
        }
        return exportedDataFromViewCapture;
    }

    public final void reset() {
        synchronized (this) {
            for (TraceMetadata traceMetadata : this.traces.values()) {
                traceMetadata.getStopTrace().invoke();
            }
            this.traces.clear();
            this.traceIdCounter = 0;
        }
    }
}