package com.android.settingslib.wifi;

import android.net.ScoredNetwork;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: mainsysui33.jar:com/android/settingslib/wifi/TimestampedScoredNetwork.class */
public class TimestampedScoredNetwork implements Parcelable {
    public static final Parcelable.Creator<TimestampedScoredNetwork> CREATOR = new Parcelable.Creator<TimestampedScoredNetwork>() { // from class: com.android.settingslib.wifi.TimestampedScoredNetwork.1
        /* JADX DEBUG: Method merged with bridge method */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimestampedScoredNetwork createFromParcel(Parcel parcel) {
            return new TimestampedScoredNetwork(parcel);
        }

        /* JADX DEBUG: Method merged with bridge method */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimestampedScoredNetwork[] newArray(int i) {
            return new TimestampedScoredNetwork[i];
        }
    };
    public ScoredNetwork mScore;
    public long mUpdatedTimestampMillis;

    public TimestampedScoredNetwork(ScoredNetwork scoredNetwork, long j) {
        this.mScore = scoredNetwork;
        this.mUpdatedTimestampMillis = j;
    }

    public TimestampedScoredNetwork(Parcel parcel) {
        this.mScore = parcel.readParcelable(ScoredNetwork.class.getClassLoader());
        this.mUpdatedTimestampMillis = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ScoredNetwork getScore() {
        return this.mScore;
    }

    public long getUpdatedTimestampMillis() {
        return this.mUpdatedTimestampMillis;
    }

    public void update(ScoredNetwork scoredNetwork, long j) {
        this.mScore = scoredNetwork;
        this.mUpdatedTimestampMillis = j;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mScore, i);
        parcel.writeLong(this.mUpdatedTimestampMillis);
    }
}