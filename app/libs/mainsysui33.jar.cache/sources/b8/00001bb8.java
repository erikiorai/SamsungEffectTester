package com.android.systemui.log.table;

import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;

/* loaded from: mainsysui33.jar:com/android/systemui/log/table/TableChange.class */
public final class TableChange {
    public boolean bool;
    public String columnName;
    public String columnPrefix;

    /* renamed from: int  reason: not valid java name */
    public int f7int;
    public String str;
    public long timestamp;
    public DataType type;

    /* loaded from: mainsysui33.jar:com/android/systemui/log/table/TableChange$DataType.class */
    public enum DataType {
        STRING,
        BOOLEAN,
        INT,
        EMPTY
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/log/table/TableChange$WhenMappings.class */
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DataType.values().length];
            try {
                iArr[DataType.EMPTY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[DataType.STRING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[DataType.INT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[DataType.BOOLEAN.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public TableChange() {
        this(0L, null, null, null, false, 0, null, 127, null);
    }

    public TableChange(long j, String str, String str2, DataType dataType, boolean z, int i, String str3) {
        this.timestamp = j;
        this.columnPrefix = str;
        this.columnName = str2;
        this.type = dataType;
        this.bool = z;
        this.f7int = i;
        this.str = str3;
    }

    public /* synthetic */ TableChange(long j, String str, String str2, DataType dataType, boolean z, int i, String str3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0L : j, (i2 & 2) != 0 ? "" : str, (i2 & 4) != 0 ? "" : str2, (i2 & 8) != 0 ? DataType.EMPTY : dataType, (i2 & 16) != 0 ? false : z, (i2 & 32) != 0 ? 0 : i, (i2 & 64) != 0 ? null : str3);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof TableChange) {
            TableChange tableChange = (TableChange) obj;
            return this.timestamp == tableChange.timestamp && Intrinsics.areEqual(this.columnPrefix, tableChange.columnPrefix) && Intrinsics.areEqual(this.columnName, tableChange.columnName) && this.type == tableChange.type && this.bool == tableChange.bool && this.f7int == tableChange.f7int && Intrinsics.areEqual(this.str, tableChange.str);
        }
        return false;
    }

    public final String getName() {
        String str;
        if (!StringsKt__StringsJVMKt.isBlank(this.columnPrefix)) {
            str = this.columnPrefix + "." + this.columnName;
        } else {
            str = this.columnName;
        }
        return str;
    }

    public final long getTimestamp() {
        return this.timestamp;
    }

    public final String getVal() {
        Boolean bool;
        int i = WhenMappings.$EnumSwitchMapping$0[this.type.ordinal()];
        if (i == 1) {
            bool = null;
        } else if (i == 2) {
            bool = this.str;
        } else if (i == 3) {
            bool = Integer.valueOf(this.f7int);
        } else if (i != 4) {
            throw new NoWhenBranchMatchedException();
        } else {
            bool = Boolean.valueOf(this.bool);
        }
        return String.valueOf(bool);
    }

    public final boolean hasData() {
        boolean z = true;
        if (!(!StringsKt__StringsJVMKt.isBlank(this.columnName)) || this.type == DataType.EMPTY) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        int hashCode = Long.hashCode(this.timestamp);
        int hashCode2 = this.columnPrefix.hashCode();
        int hashCode3 = this.columnName.hashCode();
        int hashCode4 = this.type.hashCode();
        boolean z = this.bool;
        int i = z ? 1 : 0;
        if (z) {
            i = 1;
        }
        int hashCode5 = Integer.hashCode(this.f7int);
        String str = this.str;
        return (((((((((((hashCode * 31) + hashCode2) * 31) + hashCode3) * 31) + hashCode4) * 31) + i) * 31) + hashCode5) * 31) + (str == null ? 0 : str.hashCode());
    }

    public final void reset(long j, String str, String str2) {
        this.timestamp = j;
        this.columnPrefix = str;
        this.columnName = str2;
        this.type = DataType.EMPTY;
        this.bool = false;
        this.f7int = 0;
        this.str = null;
    }

    public final void set(int i) {
        this.type = DataType.INT;
        this.f7int = i;
    }

    public final void set(String str) {
        this.type = DataType.STRING;
        this.str = str;
    }

    public final void set(boolean z) {
        this.type = DataType.BOOLEAN;
        this.bool = z;
    }

    public String toString() {
        long j = this.timestamp;
        String str = this.columnPrefix;
        String str2 = this.columnName;
        DataType dataType = this.type;
        boolean z = this.bool;
        int i = this.f7int;
        String str3 = this.str;
        return "TableChange(timestamp=" + j + ", columnPrefix=" + str + ", columnName=" + str2 + ", type=" + dataType + ", bool=" + z + ", int=" + i + ", str=" + str3 + ")";
    }
}