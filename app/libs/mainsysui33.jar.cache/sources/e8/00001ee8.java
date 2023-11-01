package com.android.systemui.notetask;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.UserManager;
import androidx.core.content.ContextCompat;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import java.util.Optional;

/* loaded from: mainsysui33.jar:com/android/systemui/notetask/NoteTaskModule.class */
public interface NoteTaskModule {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* loaded from: mainsysui33.jar:com/android/systemui/notetask/NoteTaskModule$Companion.class */
    public static final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        public final boolean provideIsNoteTaskEnabled(FeatureFlags featureFlags) {
            return featureFlags.isEnabled(Flags.NOTE_TASKS);
        }

        public final Optional<KeyguardManager> provideOptionalKeyguardManager(Context context) {
            return Optional.ofNullable(ContextCompat.getSystemService(context, KeyguardManager.class));
        }

        public final Optional<UserManager> provideOptionalUserManager(Context context) {
            return Optional.ofNullable(ContextCompat.getSystemService(context, UserManager.class));
        }
    }
}