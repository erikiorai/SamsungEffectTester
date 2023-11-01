package com.android.systemui.doze;

import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.doze.DozeMachine;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeFalsingManagerAdapter.class */
public class DozeFalsingManagerAdapter implements DozeMachine.Part {
    public final FalsingCollector mFalsingCollector;

    /* renamed from: com.android.systemui.doze.DozeFalsingManagerAdapter$1  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeFalsingManagerAdapter$1.class */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$doze$DozeMachine$State;

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:11:0x002f -> B:19:0x001f). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:9:0x002b -> B:15:0x0014). Please submit an issue!!! */
        static {
            int[] iArr = new int[DozeMachine.State.values().length];
            $SwitchMap$com$android$systemui$doze$DozeMachine$State = iArr;
            try {
                iArr[DozeMachine.State.DOZE_AOD.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_PAUSING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_PAUSED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public DozeFalsingManagerAdapter(FalsingCollector falsingCollector) {
        this.mFalsingCollector = falsingCollector;
    }

    public final boolean isAodMode(DozeMachine.State state) {
        int i = AnonymousClass1.$SwitchMap$com$android$systemui$doze$DozeMachine$State[state.ordinal()];
        return i == 1 || i == 2 || i == 3;
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        this.mFalsingCollector.setShowingAod(isAodMode(state2));
    }
}