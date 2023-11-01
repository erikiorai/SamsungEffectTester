package com.android.systemui.notetask;

import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.UserManager;
import com.android.systemui.notetask.shortcut.CreateNoteTaskShortcutActivity;
import com.android.wm.shell.bubbles.Bubbles;
import java.util.Optional;

/* loaded from: mainsysui33.jar:com/android/systemui/notetask/NoteTaskController.class */
public final class NoteTaskController {
    public final Context context;
    public final NoteTaskIntentResolver intentResolver;
    public final boolean isEnabled;
    public final Optional<Bubbles> optionalBubbles;
    public final Optional<KeyguardManager> optionalKeyguardManager;
    public final Optional<UserManager> optionalUserManager;

    public NoteTaskController(Context context, NoteTaskIntentResolver noteTaskIntentResolver, Optional<Bubbles> optional, Optional<KeyguardManager> optional2, Optional<UserManager> optional3, boolean z) {
        this.context = context;
        this.intentResolver = noteTaskIntentResolver;
        this.optionalBubbles = optional;
        this.optionalKeyguardManager = optional2;
        this.optionalUserManager = optional3;
        this.isEnabled = z;
    }

    public static /* synthetic */ void showNoteTask$default(NoteTaskController noteTaskController, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        noteTaskController.showNoteTask(z);
    }

    public final void setNoteTaskShortcutEnabled(boolean z) {
        this.context.getPackageManager().setComponentEnabledSetting(new ComponentName(this.context, CreateNoteTaskShortcutActivity.class), z ? 1 : 2, 1);
    }

    public final void showNoteTask(boolean z) {
        Bubbles orElse;
        KeyguardManager orElse2;
        UserManager orElse3;
        Intent resolveIntent;
        if (!this.isEnabled || (orElse = this.optionalBubbles.orElse(null)) == null || (orElse2 = this.optionalKeyguardManager.orElse(null)) == null || (orElse3 = this.optionalUserManager.orElse(null)) == null || (resolveIntent = this.intentResolver.resolveIntent()) == null || !orElse3.isUserUnlocked()) {
            return;
        }
        if (z || orElse2.isKeyguardLocked()) {
            this.context.startActivity(resolveIntent);
        } else {
            orElse.showAppBubble(resolveIntent);
        }
    }
}