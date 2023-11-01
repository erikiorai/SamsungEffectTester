package com.android.systemui.assist;

import com.android.internal.logging.UiEventLogger;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/assist/AssistantInvocationEvent.class */
public enum AssistantInvocationEvent implements UiEventLogger.UiEventEnum {
    ASSISTANT_INVOCATION_UNKNOWN(442),
    ASSISTANT_INVOCATION_TOUCH_GESTURE(443),
    ASSISTANT_INVOCATION_TOUCH_GESTURE_ALT(444),
    ASSISTANT_INVOCATION_HOTWORD(445),
    ASSISTANT_INVOCATION_QUICK_SEARCH_BAR(446),
    ASSISTANT_INVOCATION_HOME_LONG_PRESS(447),
    ASSISTANT_INVOCATION_PHYSICAL_GESTURE(448),
    ASSISTANT_INVOCATION_START_UNKNOWN(530),
    ASSISTANT_INVOCATION_START_TOUCH_GESTURE(531),
    ASSISTANT_INVOCATION_START_PHYSICAL_GESTURE(532),
    ASSISTANT_INVOCATION_POWER_LONG_PRESS(758);
    
    public static final Companion Companion = new Companion(null);
    private final int id;

    /* loaded from: mainsysui33.jar:com/android/systemui/assist/AssistantInvocationEvent$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final int deviceStateFromLegacyDeviceState(int i) {
            int i2;
            switch (i) {
                case 1:
                    i2 = 1;
                    break;
                case 2:
                    i2 = 2;
                    break;
                case 3:
                    i2 = 3;
                    break;
                case 4:
                    i2 = 4;
                    break;
                case 5:
                    i2 = 5;
                    break;
                case 6:
                    i2 = 6;
                    break;
                case 7:
                    i2 = 7;
                    break;
                case 8:
                    i2 = 8;
                    break;
                case 9:
                    i2 = 9;
                    break;
                case 10:
                    i2 = 10;
                    break;
                default:
                    i2 = 0;
                    break;
            }
            return i2;
        }

        public final AssistantInvocationEvent eventFromLegacyInvocationType(int i, boolean z) {
            AssistantInvocationEvent assistantInvocationEvent;
            if (z) {
                switch (i) {
                    case 1:
                        assistantInvocationEvent = AssistantInvocationEvent.ASSISTANT_INVOCATION_TOUCH_GESTURE;
                        break;
                    case 2:
                        assistantInvocationEvent = AssistantInvocationEvent.ASSISTANT_INVOCATION_PHYSICAL_GESTURE;
                        break;
                    case 3:
                        assistantInvocationEvent = AssistantInvocationEvent.ASSISTANT_INVOCATION_HOTWORD;
                        break;
                    case 4:
                        assistantInvocationEvent = AssistantInvocationEvent.ASSISTANT_INVOCATION_QUICK_SEARCH_BAR;
                        break;
                    case 5:
                        assistantInvocationEvent = AssistantInvocationEvent.ASSISTANT_INVOCATION_HOME_LONG_PRESS;
                        break;
                    case 6:
                        assistantInvocationEvent = AssistantInvocationEvent.ASSISTANT_INVOCATION_POWER_LONG_PRESS;
                        break;
                    default:
                        assistantInvocationEvent = AssistantInvocationEvent.ASSISTANT_INVOCATION_UNKNOWN;
                        break;
                }
            } else {
                assistantInvocationEvent = i != 1 ? i != 2 ? AssistantInvocationEvent.ASSISTANT_INVOCATION_START_UNKNOWN : AssistantInvocationEvent.ASSISTANT_INVOCATION_START_PHYSICAL_GESTURE : AssistantInvocationEvent.ASSISTANT_INVOCATION_START_TOUCH_GESTURE;
            }
            return assistantInvocationEvent;
        }
    }

    AssistantInvocationEvent(int i) {
        this.id = i;
    }

    public int getId() {
        return this.id;
    }
}