package com.android.systemui.media.taptotransfer.sender;

import com.android.internal.logging.UiEventLogger;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/sender/SenderEndItem.class */
public abstract class SenderEndItem {

    /* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/sender/SenderEndItem$Error.class */
    public static final class Error extends SenderEndItem {
        public static final Error INSTANCE = new Error();

        public Error() {
            super(null);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/sender/SenderEndItem$Loading.class */
    public static final class Loading extends SenderEndItem {
        public static final Loading INSTANCE = new Loading();

        public Loading() {
            super(null);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/sender/SenderEndItem$UndoButton.class */
    public static final class UndoButton extends SenderEndItem {
        public final int newState;
        public final UiEventLogger.UiEventEnum uiEventOnClick;

        public UndoButton(UiEventLogger.UiEventEnum uiEventEnum, int i) {
            super(null);
            this.uiEventOnClick = uiEventEnum;
            this.newState = i;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof UndoButton) {
                UndoButton undoButton = (UndoButton) obj;
                return Intrinsics.areEqual(this.uiEventOnClick, undoButton.uiEventOnClick) && this.newState == undoButton.newState;
            }
            return false;
        }

        public final int getNewState() {
            return this.newState;
        }

        public final UiEventLogger.UiEventEnum getUiEventOnClick() {
            return this.uiEventOnClick;
        }

        public int hashCode() {
            return (this.uiEventOnClick.hashCode() * 31) + Integer.hashCode(this.newState);
        }

        public String toString() {
            UiEventLogger.UiEventEnum uiEventEnum = this.uiEventOnClick;
            int i = this.newState;
            return "UndoButton(uiEventOnClick=" + uiEventEnum + ", newState=" + i + ")";
        }
    }

    public SenderEndItem() {
    }

    public /* synthetic */ SenderEndItem(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}