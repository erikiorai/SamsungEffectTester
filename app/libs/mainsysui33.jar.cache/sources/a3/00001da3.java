package com.android.systemui.media.taptotransfer.receiver;

import com.android.systemui.util.animation.AnimationUtil;

/* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/receiver/MediaTttChipControllerReceiverKt.class */
public final class MediaTttChipControllerReceiverKt {
    public static final long ICON_ALPHA_ANIM_DURATION;
    public static final long ICON_TRANSLATION_ANIM_DURATION;

    static {
        AnimationUtil.Companion companion = AnimationUtil.Companion;
        ICON_TRANSLATION_ANIM_DURATION = companion.getFrames(30);
        ICON_ALPHA_ANIM_DURATION = companion.getFrames(5);
    }

    public static final long getICON_ALPHA_ANIM_DURATION() {
        return ICON_ALPHA_ANIM_DURATION;
    }

    public static final long getICON_TRANSLATION_ANIM_DURATION() {
        return ICON_TRANSLATION_ANIM_DURATION;
    }
}