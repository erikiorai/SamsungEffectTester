package android.frameworks.stats;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: mainsysui33.jar:android/frameworks/stats/VendorAtomValue.class */
public final class VendorAtomValue implements Parcelable {
    public static final Parcelable.Creator<VendorAtomValue> CREATOR = new Parcelable.Creator<VendorAtomValue>() { // from class: android.frameworks.stats.VendorAtomValue.1
        /* JADX DEBUG: Method merged with bridge method */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VendorAtomValue createFromParcel(Parcel parcel) {
            return new VendorAtomValue(parcel, (VendorAtomValue-IA) null);
        }

        /* JADX DEBUG: Method merged with bridge method */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VendorAtomValue[] newArray(int i) {
            return new VendorAtomValue[i];
        }
    };
    public static final int floatValue = 2;
    public static final int intValue = 0;
    public static final int longValue = 1;
    public static final int stringValue = 3;
    private int _tag;
    private Object _value;

    /* loaded from: mainsysui33.jar:android/frameworks/stats/VendorAtomValue$Tag.class */
    public @interface Tag {
        public static final int floatValue = 2;
        public static final int intValue = 0;
        public static final int longValue = 1;
        public static final int stringValue = 3;
    }

    public VendorAtomValue() {
        this._tag = 0;
        this._value = 0;
    }

    private VendorAtomValue(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    private VendorAtomValue(Parcel parcel) {
        readFromParcel(parcel);
    }

    public /* synthetic */ VendorAtomValue(Parcel parcel, VendorAtomValue-IA r5) {
        this(parcel);
    }

    private void _assertTag(int i) {
        if (getTag() == i) {
            return;
        }
        throw new IllegalStateException("bad access: " + _tagString(i) + ", " + _tagString(getTag()) + " is available.");
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    private String _tagString(int i) {
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i == 3) {
                        return "stringValue";
                    }
                    throw new IllegalStateException("unknown field: " + i);
                }
                return "floatValue";
            }
            return "longValue";
        }
        return "intValue";
    }

    public static VendorAtomValue floatValue(float f) {
        return new VendorAtomValue(2, Float.valueOf(f));
    }

    public static VendorAtomValue intValue(int i) {
        return new VendorAtomValue(0, Integer.valueOf(i));
    }

    public static VendorAtomValue longValue(long j) {
        return new VendorAtomValue(1, Long.valueOf(j));
    }

    public static VendorAtomValue stringValue(String str) {
        return new VendorAtomValue(3, str);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        getTag();
        return 0;
    }

    public float getFloatValue() {
        _assertTag(2);
        return ((Float) this._value).floatValue();
    }

    public int getIntValue() {
        _assertTag(0);
        return ((Integer) this._value).intValue();
    }

    public long getLongValue() {
        _assertTag(1);
        return ((Long) this._value).longValue();
    }

    public final int getStability() {
        return 1;
    }

    public String getStringValue() {
        _assertTag(3);
        return (String) this._value;
    }

    public int getTag() {
        return this._tag;
    }

    public void readFromParcel(Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt == 0) {
            _set(readInt, Integer.valueOf(parcel.readInt()));
        } else if (readInt == 1) {
            _set(readInt, Long.valueOf(parcel.readLong()));
        } else if (readInt == 2) {
            _set(readInt, Float.valueOf(parcel.readFloat()));
        } else if (readInt == 3) {
            _set(readInt, parcel.readString());
        } else {
            throw new IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    public void setFloatValue(float f) {
        _set(2, Float.valueOf(f));
    }

    public void setIntValue(int i) {
        _set(0, Integer.valueOf(i));
    }

    public void setLongValue(long j) {
        _set(1, Long.valueOf(j));
    }

    public void setStringValue(String str) {
        _set(3, str);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeInt(getIntValue());
        } else if (i2 == 1) {
            parcel.writeLong(getLongValue());
        } else if (i2 == 2) {
            parcel.writeFloat(getFloatValue());
        } else if (i2 != 3) {
        } else {
            parcel.writeString(getStringValue());
        }
    }
}