package com.android.systemui.notetask;

import com.android.systemui.statusbar.CommandQueue;
import com.android.wm.shell.bubbles.Bubbles;
import java.util.Optional;

/* loaded from: mainsysui33.jar:com/android/systemui/notetask/NoteTaskInitializer.class */
public final class NoteTaskInitializer {
    public final CommandQueue.Callbacks callbacks = new CommandQueue.Callbacks() { // from class: com.android.systemui.notetask.NoteTaskInitializer$callbacks$1
        public void handleSystemKey(int i) {
            NoteTaskController noteTaskController;
            if (i == 289) {
                noteTaskController = NoteTaskInitializer.this.noteTaskController;
                NoteTaskController.showNoteTask$default(noteTaskController, false, 1, null);
            }
        }
    };
    public final CommandQueue commandQueue;
    public final boolean isEnabled;
    public final NoteTaskController noteTaskController;
    public final Optional<Bubbles> optionalBubbles;

    public NoteTaskInitializer(Optional<Bubbles> optional, NoteTaskController noteTaskController, CommandQueue commandQueue, boolean z) {
        this.optionalBubbles = optional;
        this.noteTaskController = noteTaskController;
        this.commandQueue = commandQueue;
        this.isEnabled = z;
    }

    public static /* synthetic */ void getCallbacks$annotations() {
    }

    public final void initialize() {
        if (this.isEnabled && this.optionalBubbles.isPresent()) {
            this.commandQueue.addCallback(this.callbacks);
        }
        this.noteTaskController.setNoteTaskShortcutEnabled(this.isEnabled);
    }
}