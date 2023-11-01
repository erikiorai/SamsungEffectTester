package android.hidl.base.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.Objects;

/* loaded from: mainsysui33.jar:android/hidl/base/V1_0/DebugInfo.class */
public final class DebugInfo {
    public int pid = 0;
    public long ptr = 0;
    public int arch = 0;

    /* loaded from: mainsysui33.jar:android/hidl/base/V1_0/DebugInfo$Architecture.class */
    public static final class Architecture {
        public static final String toString(int i) {
            if (i == 0) {
                return "UNKNOWN";
            }
            if (i == 1) {
                return "IS_64BIT";
            }
            if (i == 2) {
                return "IS_32BIT";
            }
            return "0x" + Integer.toHexString(i);
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && obj.getClass() == DebugInfo.class) {
            DebugInfo debugInfo = (DebugInfo) obj;
            return this.pid == debugInfo.pid && this.ptr == debugInfo.ptr && this.arch == debugInfo.arch;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.pid))), Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.ptr))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.arch))));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.pid = hwBlob.getInt32(0 + j);
        this.ptr = hwBlob.getInt64(8 + j);
        this.arch = hwBlob.getInt32(j + 16);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public final String toString() {
        return "{.pid = " + this.pid + ", .ptr = " + this.ptr + ", .arch = " + Architecture.toString(this.arch) + "}";
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(0 + j, this.pid);
        hwBlob.putInt64(8 + j, this.ptr);
        hwBlob.putInt32(j + 16, this.arch);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(24);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}