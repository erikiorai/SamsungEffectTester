package com.android.systemui.model;

import android.util.Log;
import com.android.systemui.Dumpable;
import com.android.systemui.model.SysUiState;
import com.android.systemui.shared.system.QuickStepContract;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* loaded from: mainsysui33.jar:com/android/systemui/model/SysUiState.class */
public class SysUiState implements Dumpable {
    public static final String TAG = "SysUiState";
    public int mFlags;
    public final List<SysUiStateCallback> mCallbacks = new ArrayList();
    public int mFlagsToSet = 0;
    public int mFlagsToClear = 0;

    /* loaded from: mainsysui33.jar:com/android/systemui/model/SysUiState$SysUiStateCallback.class */
    public interface SysUiStateCallback {
        void onSystemUiStateChanged(int i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.model.SysUiState$$ExternalSyntheticLambda0.accept(java.lang.Object):void] */
    /* renamed from: $r8$lambda$KFax9YyMU3-i58zdBHJCjXC_Tks */
    public static /* synthetic */ void m3378$r8$lambda$KFax9YyMU3i58zdBHJCjXC_Tks(int i, SysUiStateCallback sysUiStateCallback) {
        sysUiStateCallback.onSystemUiStateChanged(i);
    }

    public void addCallback(SysUiStateCallback sysUiStateCallback) {
        this.mCallbacks.add(sysUiStateCallback);
        sysUiStateCallback.onSystemUiStateChanged(this.mFlags);
    }

    public void commitUpdate(int i) {
        updateFlags(i);
        this.mFlagsToSet = 0;
        this.mFlagsToClear = 0;
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("SysUiState state:");
        printWriter.print("  mSysUiStateFlags=");
        printWriter.println(this.mFlags);
        printWriter.println("    " + QuickStepContract.getSystemUiStateString(this.mFlags));
        printWriter.print("    backGestureDisabled=");
        printWriter.println(QuickStepContract.isBackGestureDisabled(this.mFlags));
        printWriter.print("    assistantGestureDisabled=");
        printWriter.println(QuickStepContract.isAssistantGestureDisabled(this.mFlags));
    }

    public int getFlags() {
        return this.mFlags;
    }

    public final void notifyAndSetSystemUiStateChanged(final int i, int i2) {
        if (i != i2) {
            this.mCallbacks.forEach(new Consumer() { // from class: com.android.systemui.model.SysUiState$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    SysUiState.m3378$r8$lambda$KFax9YyMU3i58zdBHJCjXC_Tks(i, (SysUiState.SysUiStateCallback) obj);
                }
            });
            this.mFlags = i;
        }
    }

    public void removeCallback(SysUiStateCallback sysUiStateCallback) {
        this.mCallbacks.remove(sysUiStateCallback);
    }

    public SysUiState setFlag(int i, boolean z) {
        if (z) {
            this.mFlagsToSet = i | this.mFlagsToSet;
        } else {
            this.mFlagsToClear = i | this.mFlagsToClear;
        }
        return this;
    }

    public final void updateFlags(int i) {
        if (i == 0) {
            int i2 = this.mFlags;
            notifyAndSetSystemUiStateChanged((this.mFlagsToSet | i2) & (this.mFlagsToClear ^ (-1)), i2);
            return;
        }
        String str = TAG;
        Log.w(str, "Ignoring flag update for display: " + i, new Throwable());
    }
}