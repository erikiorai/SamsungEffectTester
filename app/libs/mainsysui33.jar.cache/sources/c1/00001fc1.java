package com.android.systemui.plugins.log;

import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.plugins.log.LogMessage;
import java.io.PrintWriter;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/plugins/log/LogMessageImpl.class */
public final class LogMessageImpl implements LogMessage {
    public static final Factory Factory = new Factory(null);
    private boolean bool1;
    private boolean bool2;
    private boolean bool3;
    private boolean bool4;
    private double double1;
    private Throwable exception;
    private int int1;
    private int int2;
    private LogLevel level;
    private long long1;
    private long long2;
    private Function1<? super LogMessage, String> messagePrinter;
    private String str1;
    private String str2;
    private String str3;
    private String tag;
    private long timestamp;

    /* loaded from: mainsysui33.jar:com/android/systemui/plugins/log/LogMessageImpl$Factory.class */
    public static final class Factory {
        private Factory() {
        }

        public /* synthetic */ Factory(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final LogMessageImpl create() {
            Function1 function1;
            LogLevel logLevel = LogLevel.DEBUG;
            function1 = LogMessageImplKt.DEFAULT_PRINTER;
            return new LogMessageImpl(logLevel, "UnknownTag", 0L, function1, null, null, null, null, 0, 0, 0L, 0L, 0.0d, false, false, false, false);
        }
    }

    public LogMessageImpl(LogLevel logLevel, String str, long j, Function1<? super LogMessage, String> function1, Throwable th, String str2, String str3, String str4, int i, int i2, long j2, long j3, double d, boolean z, boolean z2, boolean z3, boolean z4) {
        this.level = logLevel;
        this.tag = str;
        this.timestamp = j;
        this.messagePrinter = function1;
        this.exception = th;
        this.str1 = str2;
        this.str2 = str3;
        this.str3 = str4;
        this.int1 = i;
        this.int2 = i2;
        this.long1 = j2;
        this.long2 = j3;
        this.double1 = d;
        this.bool1 = z;
        this.bool2 = z2;
        this.bool3 = z3;
        this.bool4 = z4;
    }

    public static /* synthetic */ LogMessageImpl copy$default(LogMessageImpl logMessageImpl, LogLevel logLevel, String str, long j, Function1 function1, Throwable th, String str2, String str3, String str4, int i, int i2, long j2, long j3, double d, boolean z, boolean z2, boolean z3, boolean z4, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            logLevel = logMessageImpl.getLevel();
        }
        if ((i3 & 2) != 0) {
            str = logMessageImpl.getTag();
        }
        if ((i3 & 4) != 0) {
            j = logMessageImpl.getTimestamp();
        }
        if ((i3 & 8) != 0) {
            function1 = logMessageImpl.getMessagePrinter();
        }
        if ((i3 & 16) != 0) {
            th = logMessageImpl.getException();
        }
        if ((i3 & 32) != 0) {
            str2 = logMessageImpl.getStr1();
        }
        if ((i3 & 64) != 0) {
            str3 = logMessageImpl.getStr2();
        }
        if ((i3 & RecyclerView.ViewHolder.FLAG_IGNORE) != 0) {
            str4 = logMessageImpl.getStr3();
        }
        if ((i3 & RecyclerView.ViewHolder.FLAG_TMP_DETACHED) != 0) {
            i = logMessageImpl.getInt1();
        }
        if ((i3 & RecyclerView.ViewHolder.FLAG_ADAPTER_POSITION_UNKNOWN) != 0) {
            i2 = logMessageImpl.getInt2();
        }
        if ((i3 & RecyclerView.ViewHolder.FLAG_ADAPTER_FULLUPDATE) != 0) {
            j2 = logMessageImpl.getLong1();
        }
        if ((i3 & RecyclerView.ViewHolder.FLAG_MOVED) != 0) {
            j3 = logMessageImpl.getLong2();
        }
        if ((i3 & RecyclerView.ViewHolder.FLAG_APPEARED_IN_PRE_LAYOUT) != 0) {
            d = logMessageImpl.getDouble1();
        }
        if ((i3 & RecyclerView.ViewHolder.FLAG_BOUNCED_FROM_HIDDEN_LIST) != 0) {
            z = logMessageImpl.getBool1();
        }
        if ((i3 & 16384) != 0) {
            z2 = logMessageImpl.getBool2();
        }
        if ((i3 & 32768) != 0) {
            z3 = logMessageImpl.getBool3();
        }
        if ((i3 & 65536) != 0) {
            z4 = logMessageImpl.getBool4();
        }
        return logMessageImpl.copy(logLevel, str, j, function1, th, str2, str3, str4, i, i2, j2, j3, d, z, z2, z3, z4);
    }

    public static /* synthetic */ void reset$default(LogMessageImpl logMessageImpl, String str, LogLevel logLevel, long j, Function1 function1, Throwable th, int i, Object obj) {
        if ((i & 16) != 0) {
            th = null;
        }
        logMessageImpl.reset(str, logLevel, j, function1, th);
    }

    public final LogLevel component1() {
        return getLevel();
    }

    public final int component10() {
        return getInt2();
    }

    public final long component11() {
        return getLong1();
    }

    public final long component12() {
        return getLong2();
    }

    public final double component13() {
        return getDouble1();
    }

    public final boolean component14() {
        return getBool1();
    }

    public final boolean component15() {
        return getBool2();
    }

    public final boolean component16() {
        return getBool3();
    }

    public final boolean component17() {
        return getBool4();
    }

    public final String component2() {
        return getTag();
    }

    public final long component3() {
        return getTimestamp();
    }

    public final Function1<LogMessage, String> component4() {
        return getMessagePrinter();
    }

    public final Throwable component5() {
        return getException();
    }

    public final String component6() {
        return getStr1();
    }

    public final String component7() {
        return getStr2();
    }

    public final String component8() {
        return getStr3();
    }

    public final int component9() {
        return getInt1();
    }

    public final LogMessageImpl copy(LogLevel logLevel, String str, long j, Function1<? super LogMessage, String> function1, Throwable th, String str2, String str3, String str4, int i, int i2, long j2, long j3, double d, boolean z, boolean z2, boolean z3, boolean z4) {
        return new LogMessageImpl(logLevel, str, j, function1, th, str2, str3, str4, i, i2, j2, j3, d, z, z2, z3, z4);
    }

    @Override // com.android.systemui.plugins.log.LogMessage
    public void dump(PrintWriter printWriter) {
        LogMessage.DefaultImpls.dump(this, printWriter);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof LogMessageImpl) {
            LogMessageImpl logMessageImpl = (LogMessageImpl) obj;
            return getLevel() == logMessageImpl.getLevel() && Intrinsics.areEqual(getTag(), logMessageImpl.getTag()) && getTimestamp() == logMessageImpl.getTimestamp() && Intrinsics.areEqual(getMessagePrinter(), logMessageImpl.getMessagePrinter()) && Intrinsics.areEqual(getException(), logMessageImpl.getException()) && Intrinsics.areEqual(getStr1(), logMessageImpl.getStr1()) && Intrinsics.areEqual(getStr2(), logMessageImpl.getStr2()) && Intrinsics.areEqual(getStr3(), logMessageImpl.getStr3()) && getInt1() == logMessageImpl.getInt1() && getInt2() == logMessageImpl.getInt2() && getLong1() == logMessageImpl.getLong1() && getLong2() == logMessageImpl.getLong2() && Double.compare(getDouble1(), logMessageImpl.getDouble1()) == 0 && getBool1() == logMessageImpl.getBool1() && getBool2() == logMessageImpl.getBool2() && getBool3() == logMessageImpl.getBool3() && getBool4() == logMessageImpl.getBool4();
        }
        return false;
    }

    @Override // com.android.systemui.plugins.log.LogMessage
    public boolean getBool1() {
        return this.bool1;
    }

    @Override // com.android.systemui.plugins.log.LogMessage
    public boolean getBool2() {
        return this.bool2;
    }

    @Override // com.android.systemui.plugins.log.LogMessage
    public boolean getBool3() {
        return this.bool3;
    }

    @Override // com.android.systemui.plugins.log.LogMessage
    public boolean getBool4() {
        return this.bool4;
    }

    @Override // com.android.systemui.plugins.log.LogMessage
    public double getDouble1() {
        return this.double1;
    }

    @Override // com.android.systemui.plugins.log.LogMessage
    public Throwable getException() {
        return this.exception;
    }

    @Override // com.android.systemui.plugins.log.LogMessage
    public int getInt1() {
        return this.int1;
    }

    @Override // com.android.systemui.plugins.log.LogMessage
    public int getInt2() {
        return this.int2;
    }

    @Override // com.android.systemui.plugins.log.LogMessage
    public LogLevel getLevel() {
        return this.level;
    }

    @Override // com.android.systemui.plugins.log.LogMessage
    public long getLong1() {
        return this.long1;
    }

    @Override // com.android.systemui.plugins.log.LogMessage
    public long getLong2() {
        return this.long2;
    }

    /* JADX DEBUG: Type inference failed for r0v1. Raw type applied. Possible types: kotlin.jvm.functions.Function1<? super com.android.systemui.plugins.log.LogMessage, java.lang.String>, kotlin.jvm.functions.Function1<com.android.systemui.plugins.log.LogMessage, java.lang.String> */
    @Override // com.android.systemui.plugins.log.LogMessage
    public Function1<LogMessage, String> getMessagePrinter() {
        return this.messagePrinter;
    }

    @Override // com.android.systemui.plugins.log.LogMessage
    public String getStr1() {
        return this.str1;
    }

    @Override // com.android.systemui.plugins.log.LogMessage
    public String getStr2() {
        return this.str2;
    }

    @Override // com.android.systemui.plugins.log.LogMessage
    public String getStr3() {
        return this.str3;
    }

    @Override // com.android.systemui.plugins.log.LogMessage
    public String getTag() {
        return this.tag;
    }

    @Override // com.android.systemui.plugins.log.LogMessage
    public long getTimestamp() {
        return this.timestamp;
    }

    public int hashCode() {
        int hashCode = getLevel().hashCode();
        int hashCode2 = getTag().hashCode();
        int hashCode3 = Long.hashCode(getTimestamp());
        int hashCode4 = getMessagePrinter().hashCode();
        int i = 0;
        int hashCode5 = getException() == null ? 0 : getException().hashCode();
        int hashCode6 = getStr1() == null ? 0 : getStr1().hashCode();
        int hashCode7 = getStr2() == null ? 0 : getStr2().hashCode();
        if (getStr3() != null) {
            i = getStr3().hashCode();
        }
        int hashCode8 = Integer.hashCode(getInt1());
        int hashCode9 = Integer.hashCode(getInt2());
        int hashCode10 = Long.hashCode(getLong1());
        int hashCode11 = Long.hashCode(getLong2());
        int hashCode12 = Double.hashCode(getDouble1());
        boolean bool1 = getBool1();
        int i2 = 1;
        int i3 = bool1;
        if (bool1) {
            i3 = 1;
        }
        boolean bool2 = getBool2();
        int i4 = bool2;
        if (bool2) {
            i4 = 1;
        }
        boolean bool3 = getBool3();
        int i5 = bool3;
        if (bool3) {
            i5 = 1;
        }
        boolean bool4 = getBool4();
        if (!bool4) {
            i2 = bool4;
        }
        return (((((((((((((((((((((((((((((((hashCode * 31) + hashCode2) * 31) + hashCode3) * 31) + hashCode4) * 31) + hashCode5) * 31) + hashCode6) * 31) + hashCode7) * 31) + i) * 31) + hashCode8) * 31) + hashCode9) * 31) + hashCode10) * 31) + hashCode11) * 31) + hashCode12) * 31) + i3) * 31) + i4) * 31) + i5) * 31) + i2;
    }

    public final void reset(String str, LogLevel logLevel, long j, Function1<? super LogMessage, String> function1, Throwable th) {
        setLevel(logLevel);
        setTag(str);
        setTimestamp(j);
        setMessagePrinter(function1);
        setException(th);
        setStr1(null);
        setStr2(null);
        setStr3(null);
        setInt1(0);
        setInt2(0);
        setLong1(0L);
        setLong2(0L);
        setDouble1(0.0d);
        setBool1(false);
        setBool2(false);
        setBool3(false);
        setBool4(false);
    }

    @Override // com.android.systemui.plugins.log.LogMessage
    public void setBool1(boolean z) {
        this.bool1 = z;
    }

    @Override // com.android.systemui.plugins.log.LogMessage
    public void setBool2(boolean z) {
        this.bool2 = z;
    }

    @Override // com.android.systemui.plugins.log.LogMessage
    public void setBool3(boolean z) {
        this.bool3 = z;
    }

    @Override // com.android.systemui.plugins.log.LogMessage
    public void setBool4(boolean z) {
        this.bool4 = z;
    }

    @Override // com.android.systemui.plugins.log.LogMessage
    public void setDouble1(double d) {
        this.double1 = d;
    }

    public void setException(Throwable th) {
        this.exception = th;
    }

    @Override // com.android.systemui.plugins.log.LogMessage
    public void setInt1(int i) {
        this.int1 = i;
    }

    @Override // com.android.systemui.plugins.log.LogMessage
    public void setInt2(int i) {
        this.int2 = i;
    }

    public void setLevel(LogLevel logLevel) {
        this.level = logLevel;
    }

    @Override // com.android.systemui.plugins.log.LogMessage
    public void setLong1(long j) {
        this.long1 = j;
    }

    @Override // com.android.systemui.plugins.log.LogMessage
    public void setLong2(long j) {
        this.long2 = j;
    }

    public void setMessagePrinter(Function1<? super LogMessage, String> function1) {
        this.messagePrinter = function1;
    }

    @Override // com.android.systemui.plugins.log.LogMessage
    public void setStr1(String str) {
        this.str1 = str;
    }

    @Override // com.android.systemui.plugins.log.LogMessage
    public void setStr2(String str) {
        this.str2 = str;
    }

    @Override // com.android.systemui.plugins.log.LogMessage
    public void setStr3(String str) {
        this.str3 = str;
    }

    public void setTag(String str) {
        this.tag = str;
    }

    public void setTimestamp(long j) {
        this.timestamp = j;
    }

    public String toString() {
        LogLevel level = getLevel();
        String tag = getTag();
        long timestamp = getTimestamp();
        Function1<LogMessage, String> messagePrinter = getMessagePrinter();
        Throwable exception = getException();
        String str1 = getStr1();
        String str2 = getStr2();
        String str3 = getStr3();
        int int1 = getInt1();
        int int2 = getInt2();
        long long1 = getLong1();
        long long2 = getLong2();
        double double1 = getDouble1();
        boolean bool1 = getBool1();
        boolean bool2 = getBool2();
        boolean bool3 = getBool3();
        boolean bool4 = getBool4();
        return "LogMessageImpl(level=" + level + ", tag=" + tag + ", timestamp=" + timestamp + ", messagePrinter=" + messagePrinter + ", exception=" + exception + ", str1=" + str1 + ", str2=" + str2 + ", str3=" + str3 + ", int1=" + int1 + ", int2=" + int2 + ", long1=" + long1 + ", long2=" + long2 + ", double1=" + double1 + ", bool1=" + bool1 + ", bool2=" + bool2 + ", bool3=" + bool3 + ", bool4=" + bool4 + ")";
    }
}