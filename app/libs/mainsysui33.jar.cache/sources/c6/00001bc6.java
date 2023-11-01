package com.android.systemui.media;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/media/MediaProjectionCaptureTarget.class */
public final class MediaProjectionCaptureTarget implements Parcelable {
    public static final CREATOR CREATOR = new CREATOR(null);
    public final IBinder launchCookie;

    /* loaded from: mainsysui33.jar:com/android/systemui/media/MediaProjectionCaptureTarget$CREATOR.class */
    public static final class CREATOR implements Parcelable.Creator<MediaProjectionCaptureTarget> {
        public CREATOR() {
        }

        public /* synthetic */ CREATOR(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Method merged with bridge method */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaProjectionCaptureTarget createFromParcel(Parcel parcel) {
            return new MediaProjectionCaptureTarget(parcel);
        }

        /* JADX DEBUG: Method merged with bridge method */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaProjectionCaptureTarget[] newArray(int i) {
            return new MediaProjectionCaptureTarget[i];
        }
    }

    public MediaProjectionCaptureTarget(IBinder iBinder) {
        this.launchCookie = iBinder;
    }

    public MediaProjectionCaptureTarget(Parcel parcel) {
        this(parcel.readStrongBinder());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof MediaProjectionCaptureTarget) && Intrinsics.areEqual(this.launchCookie, ((MediaProjectionCaptureTarget) obj).launchCookie);
    }

    public final IBinder getLaunchCookie() {
        return this.launchCookie;
    }

    public int hashCode() {
        IBinder iBinder = this.launchCookie;
        return iBinder == null ? 0 : iBinder.hashCode();
    }

    public String toString() {
        IBinder iBinder = this.launchCookie;
        return "MediaProjectionCaptureTarget(launchCookie=" + iBinder + ")";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.launchCookie);
    }
}