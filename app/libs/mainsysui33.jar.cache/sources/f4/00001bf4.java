package com.android.systemui.media.controls.models.recommendation;

import android.app.smartspace.SmartspaceTarget;
import android.util.Log;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.TypeIntrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/models/recommendation/SmartspaceMediaDataProvider.class */
public final class SmartspaceMediaDataProvider implements BcSmartspaceDataPlugin {
    public final List<BcSmartspaceDataPlugin.SmartspaceTargetListener> smartspaceMediaTargetListeners = new ArrayList();

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin
    public void onTargetsAvailable(List<SmartspaceTarget> list) {
        Log.d("SsMediaDataProvider", "Forwarding Smartspace updates " + list);
        for (BcSmartspaceDataPlugin.SmartspaceTargetListener smartspaceTargetListener : this.smartspaceMediaTargetListeners) {
            smartspaceTargetListener.onSmartspaceTargetsUpdated(list);
        }
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin
    public void registerListener(BcSmartspaceDataPlugin.SmartspaceTargetListener smartspaceTargetListener) {
        this.smartspaceMediaTargetListeners.add(smartspaceTargetListener);
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin
    public void unregisterListener(BcSmartspaceDataPlugin.SmartspaceTargetListener smartspaceTargetListener) {
        TypeIntrinsics.asMutableCollection(this.smartspaceMediaTargetListeners).remove(smartspaceTargetListener);
    }
}