package com.android.systemui.flags;

import android.content.Context;
import android.os.Handler;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/FlagsModule.class */
public abstract class FlagsModule {
    public static final Companion Companion = new Companion(null);

    /* loaded from: mainsysui33.jar:com/android/systemui/flags/FlagsModule$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final FlagManager provideFlagManager(Context context, Handler handler) {
            return new FlagManager(context, handler);
        }
    }

    public static final FlagManager provideFlagManager(Context context, Handler handler) {
        return Companion.provideFlagManager(context, handler);
    }
}