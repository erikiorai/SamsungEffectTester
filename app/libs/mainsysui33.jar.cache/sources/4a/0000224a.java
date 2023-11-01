package com.android.systemui.qs.logging;

import com.android.systemui.plugins.log.ConstantStringsLogger;
import com.android.systemui.plugins.log.ConstantStringsLoggerImpl;
import com.android.systemui.plugins.log.LogBuffer;
import com.android.systemui.plugins.log.LogLevel;
import com.android.systemui.plugins.log.LogMessage;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.statusbar.StatusBarState;
import com.google.errorprone.annotations.CompileTimeConstant;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/logging/QSLogger.class */
public final class QSLogger implements ConstantStringsLogger {
    public final /* synthetic */ ConstantStringsLoggerImpl $$delegate_0;
    public final LogBuffer buffer;

    public QSLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
        this.$$delegate_0 = new ConstantStringsLoggerImpl(logBuffer, "QSLog");
    }

    @Override // com.android.systemui.plugins.log.ConstantStringsLogger
    public void d(@CompileTimeConstant String str) {
        this.$$delegate_0.d(str);
    }

    public final void d(@CompileTimeConstant final String str, Object obj) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.qs.logging.QSLogger$d$2
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
    public void e(@CompileTimeConstant String str) {
        this.$$delegate_0.e(str);
    }

    public final void logAllTilesChangeListening(boolean z, String str, String str2) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSLogger$logAllTilesChangeListening$2 qSLogger$logAllTilesChangeListening$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.qs.logging.QSLogger$logAllTilesChangeListening$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                boolean bool1 = logMessage.getBool1();
                String str1 = logMessage.getStr1();
                String str22 = logMessage.getStr2();
                return "Tiles listening=" + bool1 + " in " + str1 + ". " + str22;
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logAllTilesChangeListening$2, null);
        obtain.setBool1(z);
        obtain.setStr1(str);
        obtain.setStr2(str2);
        logBuffer.commit(obtain);
    }

    public final void logException(@CompileTimeConstant final String str, Exception exc) {
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("QSLog", LogLevel.ERROR, new Function1<LogMessage, String>() { // from class: com.android.systemui.qs.logging.QSLogger$logException$2
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

    public final void logHandleClick(String str, int i) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSLogger$logHandleClick$2 qSLogger$logHandleClick$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.qs.logging.QSLogger$logHandleClick$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                return "[" + str1 + "][" + int1 + "] Tile handling click.";
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logHandleClick$2, null);
        obtain.setStr1(str);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logHandleLongClick(String str, int i) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSLogger$logHandleLongClick$2 qSLogger$logHandleLongClick$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.qs.logging.QSLogger$logHandleLongClick$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                return "[" + str1 + "][" + int1 + "] Tile handling long click.";
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logHandleLongClick$2, null);
        obtain.setStr1(str);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logHandleSecondaryClick(String str, int i) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSLogger$logHandleSecondaryClick$2 qSLogger$logHandleSecondaryClick$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.qs.logging.QSLogger$logHandleSecondaryClick$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                return "[" + str1 + "][" + int1 + "] Tile handling secondary click.";
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logHandleSecondaryClick$2, null);
        obtain.setStr1(str);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logInternetTileUpdate(String str, int i, String str2) {
        LogLevel logLevel = LogLevel.VERBOSE;
        QSLogger$logInternetTileUpdate$2 qSLogger$logInternetTileUpdate$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.qs.logging.QSLogger$logInternetTileUpdate$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                String str22 = logMessage.getStr2();
                return "[" + str1 + "] mLastTileState=" + int1 + ", Callback=" + str22 + ".";
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logInternetTileUpdate$2, null);
        obtain.setStr1(str);
        obtain.setInt1(i);
        obtain.setStr2(str2);
        logBuffer.commit(obtain);
    }

    public final void logOnConfigurationChanged(int i, int i2, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSLogger$logOnConfigurationChanged$2 qSLogger$logOnConfigurationChanged$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.qs.logging.QSLogger$logOnConfigurationChanged$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                return "configuration change: " + str1 + " orientation was " + int1 + ", now " + int2;
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logOnConfigurationChanged$2, null);
        obtain.setStr1(str);
        obtain.setInt1(i);
        obtain.setInt2(i2);
        logBuffer.commit(obtain);
    }

    public final void logOnViewAttached(int i, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSLogger$logOnViewAttached$2 qSLogger$logOnViewAttached$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.qs.logging.QSLogger$logOnViewAttached$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                return "onViewAttached: " + str1 + " orientation " + int1;
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logOnViewAttached$2, null);
        obtain.setStr1(str);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logOnViewDetached(int i, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSLogger$logOnViewDetached$2 qSLogger$logOnViewDetached$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.qs.logging.QSLogger$logOnViewDetached$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                return "onViewDetached: " + str1 + " orientation " + int1;
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logOnViewDetached$2, null);
        obtain.setStr1(str);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logPanelExpanded(boolean z, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSLogger$logPanelExpanded$2 qSLogger$logPanelExpanded$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.qs.logging.QSLogger$logPanelExpanded$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                return str1 + " expanded=" + bool1;
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logPanelExpanded$2, null);
        obtain.setStr1(str);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }

    public final void logSwitchTileLayout(boolean z, boolean z2, boolean z3, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSLogger$logSwitchTileLayout$2 qSLogger$logSwitchTileLayout$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.qs.logging.QSLogger$logSwitchTileLayout$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                boolean bool3 = logMessage.getBool3();
                return "change tile layout: " + str1 + " horizontal=" + bool1 + " (was " + bool2 + "), force? " + bool3;
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logSwitchTileLayout$2, null);
        obtain.setStr1(str);
        obtain.setBool1(z);
        obtain.setBool2(z2);
        obtain.setBool3(z3);
        logBuffer.commit(obtain);
    }

    public final void logTileAdded(String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSLogger$logTileAdded$2 qSLogger$logTileAdded$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.qs.logging.QSLogger$logTileAdded$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "[" + str1 + "] Tile added";
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logTileAdded$2, null);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logTileBackgroundColorUpdateIfInternetTile(String str, int i, boolean z, int i2) {
        if (Intrinsics.areEqual(str, "internet")) {
            LogLevel logLevel = LogLevel.VERBOSE;
            QSLogger$logTileBackgroundColorUpdateIfInternetTile$2 qSLogger$logTileBackgroundColorUpdateIfInternetTile$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.qs.logging.QSLogger$logTileBackgroundColorUpdateIfInternetTile$2
                /* JADX DEBUG: Method merged with bridge method */
                public final String invoke(LogMessage logMessage) {
                    String str1 = logMessage.getStr1();
                    int int1 = logMessage.getInt1();
                    boolean bool1 = logMessage.getBool1();
                    int int2 = logMessage.getInt2();
                    return "[" + str1 + "] state=" + int1 + ", disabledByPolicy=" + bool1 + ", color=" + int2 + ".";
                }
            };
            LogBuffer logBuffer = this.buffer;
            LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logTileBackgroundColorUpdateIfInternetTile$2, null);
            obtain.setStr1(str);
            obtain.setInt1(i);
            obtain.setBool1(z);
            obtain.setInt2(i2);
            logBuffer.commit(obtain);
        }
    }

    public final void logTileChangeListening(String str, boolean z) {
        LogLevel logLevel = LogLevel.VERBOSE;
        QSLogger$logTileChangeListening$2 qSLogger$logTileChangeListening$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.qs.logging.QSLogger$logTileChangeListening$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                return "[" + str1 + "] Tile listening=" + bool1;
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logTileChangeListening$2, null);
        obtain.setBool1(z);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logTileClick(String str, int i, int i2, int i3) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSLogger$logTileClick$2 qSLogger$logTileClick$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.qs.logging.QSLogger$logTileClick$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                String str2 = logMessage.getStr2();
                String str3 = logMessage.getStr3();
                return "[" + str1 + "][" + int1 + "] Tile clicked. StatusBarState=" + str2 + ". TileState=" + str3;
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logTileClick$2, null);
        obtain.setStr1(str);
        obtain.setInt1(i3);
        obtain.setStr2(StatusBarState.toString(i));
        obtain.setStr3(toStateString(i2));
        logBuffer.commit(obtain);
    }

    public final void logTileDestroyed(String str, String str2) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSLogger$logTileDestroyed$2 qSLogger$logTileDestroyed$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.qs.logging.QSLogger$logTileDestroyed$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                String str22 = logMessage.getStr2();
                return "[" + str1 + "] Tile destroyed. Reason: " + str22;
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logTileDestroyed$2, null);
        obtain.setStr1(str);
        obtain.setStr2(str2);
        logBuffer.commit(obtain);
    }

    public final void logTileDistributed(String str, int i) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSLogger$logTileDistributed$2 qSLogger$logTileDistributed$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.qs.logging.QSLogger$logTileDistributed$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                return "Adding " + str1 + " to page number " + int1;
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logTileDistributed$2, null);
        obtain.setStr1(str);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logTileDistributionInProgress(int i, int i2) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSLogger$logTileDistributionInProgress$2 qSLogger$logTileDistributionInProgress$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.qs.logging.QSLogger$logTileDistributionInProgress$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                return "Distributing tiles: [tilesPerPageCount=" + int1 + "] [totalTilesCount=" + int2 + "]";
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logTileDistributionInProgress$2, null);
        obtain.setInt1(i);
        obtain.setInt2(i2);
        logBuffer.commit(obtain);
    }

    public final void logTileLongClick(String str, int i, int i2, int i3) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSLogger$logTileLongClick$2 qSLogger$logTileLongClick$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.qs.logging.QSLogger$logTileLongClick$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                String str2 = logMessage.getStr2();
                String str3 = logMessage.getStr3();
                return "[" + str1 + "][" + int1 + "] Tile long clicked. StatusBarState=" + str2 + ". TileState=" + str3;
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logTileLongClick$2, null);
        obtain.setStr1(str);
        obtain.setInt1(i3);
        obtain.setStr2(StatusBarState.toString(i));
        obtain.setStr3(toStateString(i2));
        logBuffer.commit(obtain);
    }

    public final void logTileSecondaryClick(String str, int i, int i2, int i3) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSLogger$logTileSecondaryClick$2 qSLogger$logTileSecondaryClick$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.qs.logging.QSLogger$logTileSecondaryClick$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                String str2 = logMessage.getStr2();
                String str3 = logMessage.getStr3();
                return "[" + str1 + "][" + int1 + "] Tile secondary clicked. StatusBarState=" + str2 + ". TileState=" + str3;
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logTileSecondaryClick$2, null);
        obtain.setStr1(str);
        obtain.setInt1(i3);
        obtain.setStr2(StatusBarState.toString(i));
        obtain.setStr3(toStateString(i2));
        logBuffer.commit(obtain);
    }

    public final void logTileUpdated(String str, QSTile.State state) {
        LogLevel logLevel = LogLevel.VERBOSE;
        QSLogger$logTileUpdated$2 qSLogger$logTileUpdated$2 = new Function1<LogMessage, String>() { // from class: com.android.systemui.qs.logging.QSLogger$logTileUpdated$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str2;
                String str1 = logMessage.getStr1();
                String str22 = logMessage.getStr2();
                int int1 = logMessage.getInt1();
                String str3 = logMessage.getStr3();
                if (logMessage.getBool1()) {
                    str2 = " Activity in/out=" + logMessage.getBool2() + "/" + logMessage.getBool3();
                } else {
                    str2 = "";
                }
                return "[" + str1 + "] Tile updated. Label=" + str22 + ". State=" + int1 + ". Icon=" + str3 + "." + str2;
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logTileUpdated$2, null);
        obtain.setStr1(str);
        CharSequence charSequence = state.label;
        obtain.setStr2(charSequence != null ? charSequence.toString() : null);
        QSTile.Icon icon = state.icon;
        String str2 = null;
        if (icon != null) {
            str2 = icon.toString();
        }
        obtain.setStr3(str2);
        obtain.setInt1(state.state);
        if (state instanceof QSTile.SignalState) {
            obtain.setBool1(true);
            QSTile.SignalState signalState = (QSTile.SignalState) state;
            obtain.setBool2(signalState.activityIn);
            obtain.setBool3(signalState.activityOut);
        }
        logBuffer.commit(obtain);
    }

    public final String toStateString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? "wrong state" : "active" : "inactive" : "unavailable";
    }

    @Override // com.android.systemui.plugins.log.ConstantStringsLogger
    public void v(@CompileTimeConstant String str) {
        this.$$delegate_0.v(str);
    }

    @Override // com.android.systemui.plugins.log.ConstantStringsLogger
    public void w(@CompileTimeConstant String str) {
        this.$$delegate_0.w(str);
    }
}