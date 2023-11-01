package com.android.systemui.notetask.shortcut;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.activity.ComponentActivity;
import com.android.systemui.notetask.NoteTaskController;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/notetask/shortcut/LaunchNoteTaskActivity.class */
public final class LaunchNoteTaskActivity extends ComponentActivity {
    public static final Companion Companion = new Companion(null);
    public final NoteTaskController noteTaskController;

    /* loaded from: mainsysui33.jar:com/android/systemui/notetask/shortcut/LaunchNoteTaskActivity$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Intent newIntent(Context context) {
            Intent intent = new Intent(context, LaunchNoteTaskActivity.class);
            intent.setAction("android.intent.action.NOTES");
            return intent;
        }
    }

    public LaunchNoteTaskActivity(NoteTaskController noteTaskController) {
        this.noteTaskController = noteTaskController;
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.noteTaskController.showNoteTask(isInMultiWindowMode());
        finish();
    }
}