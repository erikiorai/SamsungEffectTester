package com.android.systemui.notetask.shortcut;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.ComponentActivity;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutManagerCompat;
import androidx.core.graphics.drawable.IconCompat;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/notetask/shortcut/CreateNoteTaskShortcutActivity.class */
public final class CreateNoteTaskShortcutActivity extends ComponentActivity {
    public static final Companion Companion = new Companion(null);

    /* loaded from: mainsysui33.jar:com/android/systemui/notetask/shortcut/CreateNoteTaskShortcutActivity$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public final Intent createShortcutIntent(String str, String str2, Intent intent, int i) {
        return ShortcutManagerCompat.createShortcutResultIntent(this, new ShortcutInfoCompat.Builder(this, str).setIntent(intent).setShortLabel(str2).setLongLived(true).setIcon(IconCompat.createWithResource(this, i)).build());
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setResult(-1, createShortcutIntent("note-task-shortcut-id", getString(R$string.note_task_button_label), LaunchNoteTaskActivity.Companion.newIntent(this), R$drawable.ic_note_task_button));
        finish();
    }
}