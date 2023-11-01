package com.android.systemui.controls;

import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.logging.InstanceIdSequence;
import com.android.systemui.controls.ControlsMetricsLogger;
import com.android.systemui.controls.ui.ControlViewHolder;
import com.android.systemui.shared.system.SysUiStatsLog;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ControlsMetricsLoggerImpl.class */
public final class ControlsMetricsLoggerImpl implements ControlsMetricsLogger {
    public static final Companion Companion = new Companion(null);
    public int instanceId;
    public final InstanceIdSequence instanceIdSequence = new InstanceIdSequence((int) RecyclerView.ViewHolder.FLAG_BOUNCED_FROM_HIDDEN_LIST);

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/ControlsMetricsLoggerImpl$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Override // com.android.systemui.controls.ControlsMetricsLogger
    public void assignInstanceId() {
        this.instanceId = this.instanceIdSequence.newInstanceId().getId();
    }

    @Override // com.android.systemui.controls.ControlsMetricsLogger
    public void drag(ControlViewHolder controlViewHolder, boolean z) {
        ControlsMetricsLogger.DefaultImpls.drag(this, controlViewHolder, z);
    }

    @Override // com.android.systemui.controls.ControlsMetricsLogger
    public void log(int i, int i2, int i3, boolean z) {
        SysUiStatsLog.write(349, i, this.instanceId, i2, i3, z);
    }

    @Override // com.android.systemui.controls.ControlsMetricsLogger
    public void longPress(ControlViewHolder controlViewHolder, boolean z) {
        ControlsMetricsLogger.DefaultImpls.longPress(this, controlViewHolder, z);
    }

    @Override // com.android.systemui.controls.ControlsMetricsLogger
    public void refreshBegin(int i, boolean z) {
        ControlsMetricsLogger.DefaultImpls.refreshBegin(this, i, z);
    }

    @Override // com.android.systemui.controls.ControlsMetricsLogger
    public void refreshEnd(ControlViewHolder controlViewHolder, boolean z) {
        ControlsMetricsLogger.DefaultImpls.refreshEnd(this, controlViewHolder, z);
    }

    @Override // com.android.systemui.controls.ControlsMetricsLogger
    public void touch(ControlViewHolder controlViewHolder, boolean z) {
        ControlsMetricsLogger.DefaultImpls.touch(this, controlViewHolder, z);
    }
}