package com.android.keyguard.logging;

import com.android.systemui.plugins.log.ConstantStringsLogger;
import com.android.systemui.plugins.log.ConstantStringsLoggerImpl;
import com.android.systemui.plugins.log.LogBuffer;
import com.android.systemui.plugins.log.LogLevel;
import com.android.systemui.plugins.log.LogMessage;
import com.google.errorprone.annotations.CompileTimeConstant;
import kotlin.jvm.functions.Function1;

/* loaded from: mainsysui33.jar:com/android/keyguard/logging/KeyguardLogger.class */
public final class KeyguardLogger implements ConstantStringsLogger {
    public final /* synthetic */ ConstantStringsLoggerImpl $$delegate_0;
    public final LogBuffer buffer;

    public KeyguardLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
        this.$$delegate_0 = new ConstantStringsLoggerImpl(logBuffer, "KeyguardLog");
    }

    public static /* synthetic */ void logBiometricMessage$default(KeyguardLogger keyguardLogger, String str, Integer num, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            num = null;
        }
        if ((i & 4) != 0) {
            str2 = null;
        }
        keyguardLogger.logBiometricMessage(str, num, str2);
    }

    @Override // com.android.systemui.plugins.log.ConstantStringsLogger
    public void d(@CompileTimeConstant String str) {
        this.$$delegate_0.d(str);
    }

    @Override // com.android.systemui.plugins.log.ConstantStringsLogger
    public void e(@CompileTimeConstant String str) {
        this.$$delegate_0.e(str);
    }

    public final void i(final String str, Object obj) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("KeyguardLog", LogLevel.INFO, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardLogger$i$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str2 = str;
                String str1 = logMessage.getStr1();
                return str2 + ": " + str1;
            }
        }, null);
        obtain.setStr1(obj.toString());
        logBuffer.commit(obtain);
    }

    public final void logBiometricMessage(@CompileTimeConstant String str) {
        logBiometricMessage$default(this, str, null, null, 6, null);
    }

    public final void logBiometricMessage(@CompileTimeConstant String str, Integer num, String str2) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("KeyguardLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardLogger$logBiometricMessage$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                String str22 = logMessage.getStr2();
                String str3 = logMessage.getStr3();
                return str1 + " msgId: " + str22 + " msg: " + str3;
            }
        }, null);
        obtain.setStr1(str);
        obtain.setStr2(String.valueOf(num));
        obtain.setStr3(str2);
        logBuffer.commit(obtain);
    }

    public final void logException(Exception exc, @CompileTimeConstant final String str) {
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("KeyguardLog", LogLevel.ERROR, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardLogger$logException$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                return str;
            }
        }, exc));
    }

    @Override // com.android.systemui.plugins.log.ConstantStringsLogger
    public void v(@CompileTimeConstant String str) {
        this.$$delegate_0.v(str);
    }

    public final void v(final String str, Object obj) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("KeyguardLog", LogLevel.VERBOSE, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardLogger$v$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str2 = str;
                String str1 = logMessage.getStr1();
                return str2 + ": " + str1;
            }
        }, null);
        obtain.setStr1(obj.toString());
        logBuffer.commit(obtain);
    }

    @Override // com.android.systemui.plugins.log.ConstantStringsLogger
    public void w(@CompileTimeConstant String str) {
        this.$$delegate_0.w(str);
    }
}