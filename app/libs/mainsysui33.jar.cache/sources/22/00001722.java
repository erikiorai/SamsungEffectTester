package com.android.systemui.dreams.complication;

import com.android.systemui.CoreStartable;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dreams.smartspace.DreamSmartspaceController;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/SmartSpaceComplication$Registrant.class */
public class SmartSpaceComplication$Registrant implements CoreStartable {
    public final DreamOverlayStateController mDreamOverlayStateController;
    public final BcSmartspaceDataPlugin.SmartspaceTargetListener mSmartspaceListener;

    /* renamed from: -$$Nest$fgetmSmartSpaceController  reason: not valid java name */
    public static /* bridge */ /* synthetic */ DreamSmartspaceController m2597$$Nest$fgetmSmartSpaceController(SmartSpaceComplication$Registrant smartSpaceComplication$Registrant) {
        smartSpaceComplication$Registrant.getClass();
        return null;
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        this.mDreamOverlayStateController.addCallback(new DreamOverlayStateController.Callback() { // from class: com.android.systemui.dreams.complication.SmartSpaceComplication$Registrant.2
            @Override // com.android.systemui.dreams.DreamOverlayStateController.Callback
            public void onStateChanged() {
                if (SmartSpaceComplication$Registrant.this.mDreamOverlayStateController.isOverlayActive()) {
                    SmartSpaceComplication$Registrant.m2597$$Nest$fgetmSmartSpaceController(SmartSpaceComplication$Registrant.this);
                    BcSmartspaceDataPlugin.SmartspaceTargetListener unused = SmartSpaceComplication$Registrant.this.mSmartspaceListener;
                    throw null;
                }
                SmartSpaceComplication$Registrant.m2597$$Nest$fgetmSmartSpaceController(SmartSpaceComplication$Registrant.this);
                BcSmartspaceDataPlugin.SmartspaceTargetListener unused2 = SmartSpaceComplication$Registrant.this.mSmartspaceListener;
                throw null;
            }
        });
    }
}