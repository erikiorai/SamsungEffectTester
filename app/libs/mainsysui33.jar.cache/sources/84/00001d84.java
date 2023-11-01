package com.android.systemui.media.taptotransfer;

import android.app.StatusBarManager;
import android.content.Context;
import android.media.MediaRoute2Info;
import android.util.Log;
import com.android.systemui.CoreStartable;
import com.android.systemui.media.taptotransfer.MediaTttCommandLineHelper;
import com.android.systemui.media.taptotransfer.receiver.ChipStateReceiver;
import com.android.systemui.media.taptotransfer.sender.ChipStateSender;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/MediaTttCommandLineHelper.class */
public final class MediaTttCommandLineHelper implements CoreStartable {
    public final CommandRegistry commandRegistry;
    public final Context context;
    public final Executor mainExecutor;

    /* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/MediaTttCommandLineHelper$ReceiverCommand.class */
    public final class ReceiverCommand implements Command {
        /* JADX DEBUG: Incorrect args count in method signature: ()V */
        public ReceiverCommand() {
        }

        public void execute(PrintWriter printWriter, List<String> list) {
            if (list.isEmpty()) {
                help(printWriter);
                return;
            }
            boolean z = false;
            String str = list.get(0);
            try {
                int receiverStateIdFromName = ChipStateReceiver.Companion.getReceiverStateIdFromName(str);
                StatusBarManager statusBarManager = (StatusBarManager) MediaTttCommandLineHelper.this.context.getSystemService("statusbar");
                MediaRoute2Info.Builder addFeature = new MediaRoute2Info.Builder(list.size() >= 3 ? list.get(2) : "id", "Test Name").addFeature("feature");
                if (list.size() < 2 || !Intrinsics.areEqual(list.get(1), "useAppIcon=false")) {
                    z = true;
                }
                if (z) {
                    addFeature.setClientPackageName("com.android.systemui");
                }
                statusBarManager.updateMediaTapToTransferReceiverDisplay(Integer.valueOf(receiverStateIdFromName).intValue(), addFeature.build(), null, null);
            } catch (IllegalArgumentException e) {
                printWriter.println("Invalid command name " + str);
            }
        }

        public void help(PrintWriter printWriter) {
            printWriter.println("Usage: adb shell cmd statusbar media-ttt-chip-receiver <chipState> useAppIcon=[true|false] <id>");
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/MediaTttCommandLineHelper$SenderArgs.class */
    public static final class SenderArgs {
        public final String commandName;
        public final String deviceName;
        public String id;
        public boolean showUndo;
        public boolean useAppIcon;

        public SenderArgs(String str, String str2, String str3, boolean z, boolean z2) {
            this.deviceName = str;
            this.commandName = str2;
            this.id = str3;
            this.useAppIcon = z;
            this.showUndo = z2;
        }

        public /* synthetic */ SenderArgs(String str, String str2, String str3, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, str2, (i & 4) != 0 ? "id" : str3, (i & 8) != 0 ? true : z, (i & 16) != 0 ? true : z2);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof SenderArgs) {
                SenderArgs senderArgs = (SenderArgs) obj;
                return Intrinsics.areEqual(this.deviceName, senderArgs.deviceName) && Intrinsics.areEqual(this.commandName, senderArgs.commandName) && Intrinsics.areEqual(this.id, senderArgs.id) && this.useAppIcon == senderArgs.useAppIcon && this.showUndo == senderArgs.showUndo;
            }
            return false;
        }

        public final String getCommandName() {
            return this.commandName;
        }

        public final String getDeviceName() {
            return this.deviceName;
        }

        public final String getId() {
            return this.id;
        }

        public final boolean getShowUndo() {
            return this.showUndo;
        }

        public final boolean getUseAppIcon() {
            return this.useAppIcon;
        }

        public int hashCode() {
            int hashCode = this.deviceName.hashCode();
            int hashCode2 = this.commandName.hashCode();
            int hashCode3 = this.id.hashCode();
            boolean z = this.useAppIcon;
            int i = 1;
            int i2 = z ? 1 : 0;
            if (z) {
                i2 = 1;
            }
            boolean z2 = this.showUndo;
            if (!z2) {
                i = z2 ? 1 : 0;
            }
            return (((((((hashCode * 31) + hashCode2) * 31) + hashCode3) * 31) + i2) * 31) + i;
        }

        public final void setId(String str) {
            this.id = str;
        }

        public final void setShowUndo(boolean z) {
            this.showUndo = z;
        }

        public final void setUseAppIcon(boolean z) {
            this.useAppIcon = z;
        }

        public String toString() {
            String str = this.deviceName;
            String str2 = this.commandName;
            String str3 = this.id;
            boolean z = this.useAppIcon;
            boolean z2 = this.showUndo;
            return "SenderArgs(deviceName=" + str + ", commandName=" + str2 + ", id=" + str3 + ", useAppIcon=" + z + ", showUndo=" + z2 + ")";
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/MediaTttCommandLineHelper$SenderCommand.class */
    public final class SenderCommand implements Command {
        /* JADX DEBUG: Incorrect args count in method signature: ()V */
        public SenderCommand() {
        }

        public void execute(PrintWriter printWriter, List<String> list) {
            Runnable runnable;
            if (list.size() < 2) {
                help(printWriter);
                return;
            }
            SenderArgs processArgs = processArgs(list);
            try {
                final Integer valueOf = Integer.valueOf(ChipStateSender.Companion.getSenderStateIdFromName(processArgs.getCommandName()));
                StatusBarManager statusBarManager = (StatusBarManager) MediaTttCommandLineHelper.this.context.getSystemService("statusbar");
                MediaRoute2Info.Builder addFeature = new MediaRoute2Info.Builder(processArgs.getId(), processArgs.getDeviceName()).addFeature("feature");
                if (processArgs.getUseAppIcon()) {
                    addFeature.setClientPackageName("com.android.systemui");
                }
                Executor executor = null;
                if (isSucceededState(valueOf.intValue()) && processArgs.getShowUndo()) {
                    executor = MediaTttCommandLineHelper.this.mainExecutor;
                    runnable = new Runnable() { // from class: com.android.systemui.media.taptotransfer.MediaTttCommandLineHelper$SenderCommand$execute$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Integer num = valueOf;
                            Log.i("MediaTransferCli", "Undo triggered for " + num);
                        }
                    };
                } else {
                    runnable = null;
                }
                statusBarManager.updateMediaTapToTransferSenderDisplay(valueOf.intValue(), addFeature.build(), executor, runnable);
            } catch (IllegalArgumentException e) {
                printWriter.println("Invalid command name " + processArgs.getCommandName());
            }
        }

        public void help(PrintWriter printWriter) {
            printWriter.println("Usage: adb shell cmd statusbar media-ttt-chip-sender <deviceName> <chipState> useAppIcon=[true|false] id=<id> showUndo=[true|false]");
            printWriter.println("Note: useAppIcon, id, and showUndo are optional additional commands.");
        }

        public final boolean isSucceededState(int i) {
            return i == 4 || i == 5;
        }

        public final SenderArgs processArgs(List<String> list) {
            SenderArgs senderArgs = new SenderArgs(list.get(0), list.get(1), null, false, false, 28, null);
            if (list.size() == 2) {
                return senderArgs;
            }
            for (String str : list.subList(2, list.size())) {
                if (Intrinsics.areEqual(str, "useAppIcon=false")) {
                    senderArgs.setUseAppIcon(false);
                } else if (Intrinsics.areEqual(str, "showUndo=false")) {
                    senderArgs.setShowUndo(false);
                } else if (Intrinsics.areEqual(str.substring(0, 3), "id=")) {
                    senderArgs.setId(str.substring(3));
                }
            }
            return senderArgs;
        }
    }

    public MediaTttCommandLineHelper(CommandRegistry commandRegistry, Context context, Executor executor) {
        this.commandRegistry = commandRegistry;
        this.context = context;
        this.mainExecutor = executor;
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        this.commandRegistry.registerCommand("media-ttt-chip-sender", new Function0<Command>() { // from class: com.android.systemui.media.taptotransfer.MediaTttCommandLineHelper$start$1
            {
                super(0);
            }

            /* JADX DEBUG: Method merged with bridge method */
            /* renamed from: invoke */
            public final Command m3344invoke() {
                return new MediaTttCommandLineHelper.SenderCommand();
            }
        });
        this.commandRegistry.registerCommand("media-ttt-chip-receiver", new Function0<Command>() { // from class: com.android.systemui.media.taptotransfer.MediaTttCommandLineHelper$start$2
            {
                super(0);
            }

            /* JADX DEBUG: Method merged with bridge method */
            /* renamed from: invoke */
            public final Command m3345invoke() {
                return new MediaTttCommandLineHelper.ReceiverCommand();
            }
        });
    }
}