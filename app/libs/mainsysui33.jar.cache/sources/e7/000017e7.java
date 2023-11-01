package com.android.systemui.flags;

import android.os.Parcelable;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/ParcelableFlag.class */
public interface ParcelableFlag<T> extends Flag<T>, Parcelable {

    /* loaded from: mainsysui33.jar:com/android/systemui/flags/ParcelableFlag$DefaultImpls.class */
    public static final class DefaultImpls {
        public static <T> int describeContents(ParcelableFlag<T> parcelableFlag) {
            return 0;
        }
    }
}