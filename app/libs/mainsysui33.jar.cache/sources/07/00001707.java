package com.android.systemui.dreams.complication;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/ComplicationId.class */
public class ComplicationId {
    public int mId;

    /* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/ComplicationId$Factory.class */
    public static class Factory {
        public int mNextId;

        public ComplicationId getNextId() {
            int i = this.mNextId;
            this.mNextId = i + 1;
            return new ComplicationId(i, null);
        }
    }

    public ComplicationId(int i) {
        this.mId = i;
    }

    public /* synthetic */ ComplicationId(int i, ComplicationId-IA r5) {
        this(i);
    }

    public String toString() {
        return "ComplicationId{mId=" + this.mId + "}";
    }
}