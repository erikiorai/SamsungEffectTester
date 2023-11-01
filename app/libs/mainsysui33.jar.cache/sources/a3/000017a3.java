package com.android.systemui.dump;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: mainsysui33.jar:com/android/systemui/dump/SystemUIAuxiliaryDumpService.class */
public class SystemUIAuxiliaryDumpService extends Service {
    public final DumpHandler mDumpHandler;

    public SystemUIAuxiliaryDumpService(DumpHandler dumpHandler) {
        this.mDumpHandler = dumpHandler;
    }

    @Override // android.app.Service
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        this.mDumpHandler.dump(fileDescriptor, printWriter, new String[]{"--dump-priority", "NORMAL"});
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }
}