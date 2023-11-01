package com.android.systemui.media.muteawait;

import android.content.Context;
import android.media.AudioDeviceAttributes;
import android.media.AudioManager;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/media/muteawait/MediaMuteAwaitConnectionCli.class */
public final class MediaMuteAwaitConnectionCli {
    public final Context context;

    /* loaded from: mainsysui33.jar:com/android/systemui/media/muteawait/MediaMuteAwaitConnectionCli$MuteAwaitCommand.class */
    public final class MuteAwaitCommand implements Command {
        /* JADX DEBUG: Incorrect args count in method signature: ()V */
        public MuteAwaitCommand() {
        }

        public void execute(PrintWriter printWriter, List<String> list) {
            TimeUnit timeUnit;
            AudioDeviceAttributes audioDeviceAttributes = new AudioDeviceAttributes(2, Integer.parseInt(list.get(0)), "address", list.get(1), CollectionsKt__CollectionsKt.emptyList(), CollectionsKt__CollectionsKt.emptyList());
            String str = list.get(2);
            AudioManager audioManager = (AudioManager) MediaMuteAwaitConnectionCli.this.context.getSystemService("audio");
            if (Intrinsics.areEqual(str, "start")) {
                timeUnit = MediaMuteAwaitConnectionCliKt.TIMEOUT_UNITS;
                audioManager.muteAwaitConnection(new int[]{1}, audioDeviceAttributes, 5L, timeUnit);
            } else if (Intrinsics.areEqual(str, "cancel")) {
                audioManager.cancelMuteAwaitConnection(audioDeviceAttributes);
            } else {
                printWriter.println("Must specify `start` or `cancel`; was " + str);
            }
        }
    }

    public MediaMuteAwaitConnectionCli(CommandRegistry commandRegistry, Context context) {
        this.context = context;
        commandRegistry.registerCommand("media-mute-await", new Function0<Command>() { // from class: com.android.systemui.media.muteawait.MediaMuteAwaitConnectionCli.1
            {
                super(0);
            }

            /* JADX DEBUG: Method merged with bridge method */
            /* renamed from: invoke */
            public final Command m3329invoke() {
                return new MuteAwaitCommand();
            }
        });
    }
}