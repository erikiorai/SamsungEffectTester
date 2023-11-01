package com.android.systemui.media.controls.models.player;

import android.app.PendingIntent;
import android.graphics.drawable.Icon;
import android.media.session.MediaSession;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.logging.InstanceId;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/models/player/MediaData.class */
public final class MediaData {
    public static final Companion Companion = new Companion(null);
    public final List<MediaAction> actions;
    public final List<Integer> actionsToShowInCompact;
    public boolean active;
    public final String app;
    public final Icon appIcon;
    public final int appUid;
    public final CharSequence artist;
    public final Icon artwork;
    public final PendingIntent clickIntent;
    public final MediaDeviceData device;
    public boolean hasCheckedForResume;
    public final boolean initialized;
    public final InstanceId instanceId;
    public final boolean isClearable;
    public final Boolean isPlaying;
    public long lastActive;
    public final String notificationKey;
    public final String packageName;
    public int playbackLocation;
    public Runnable resumeAction;
    public boolean resumption;
    public final MediaButton semanticActions;
    public final CharSequence song;
    public final MediaSession.Token token;
    public final int userId;

    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/models/player/MediaData$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public MediaData(int i, boolean z, String str, Icon icon, CharSequence charSequence, CharSequence charSequence2, Icon icon2, List<MediaAction> list, List<Integer> list2, MediaButton mediaButton, String str2, MediaSession.Token token, PendingIntent pendingIntent, MediaDeviceData mediaDeviceData, boolean z2, Runnable runnable, int i2, boolean z3, String str3, boolean z4, Boolean bool, boolean z5, long j, InstanceId instanceId, int i3) {
        this.userId = i;
        this.initialized = z;
        this.app = str;
        this.appIcon = icon;
        this.artist = charSequence;
        this.song = charSequence2;
        this.artwork = icon2;
        this.actions = list;
        this.actionsToShowInCompact = list2;
        this.semanticActions = mediaButton;
        this.packageName = str2;
        this.token = token;
        this.clickIntent = pendingIntent;
        this.device = mediaDeviceData;
        this.active = z2;
        this.resumeAction = runnable;
        this.playbackLocation = i2;
        this.resumption = z3;
        this.notificationKey = str3;
        this.hasCheckedForResume = z4;
        this.isPlaying = bool;
        this.isClearable = z5;
        this.lastActive = j;
        this.instanceId = instanceId;
        this.appUid = i3;
    }

    public /* synthetic */ MediaData(int i, boolean z, String str, Icon icon, CharSequence charSequence, CharSequence charSequence2, Icon icon2, List list, List list2, MediaButton mediaButton, String str2, MediaSession.Token token, PendingIntent pendingIntent, MediaDeviceData mediaDeviceData, boolean z2, Runnable runnable, int i2, boolean z3, String str3, boolean z4, Boolean bool, boolean z5, long j, InstanceId instanceId, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i4 & 2) != 0 ? false : z, str, icon, charSequence, charSequence2, icon2, list, list2, (i4 & RecyclerView.ViewHolder.FLAG_ADAPTER_POSITION_UNKNOWN) != 0 ? null : mediaButton, str2, token, pendingIntent, mediaDeviceData, z2, runnable, (65536 & i4) != 0 ? 0 : i2, (131072 & i4) != 0 ? false : z3, (262144 & i4) != 0 ? null : str3, (524288 & i4) != 0 ? false : z4, (1048576 & i4) != 0 ? null : bool, (2097152 & i4) != 0 ? true : z5, (i4 & 4194304) != 0 ? 0L : j, instanceId, i3);
    }

    public static /* synthetic */ MediaData copy$default(MediaData mediaData, int i, boolean z, String str, Icon icon, CharSequence charSequence, CharSequence charSequence2, Icon icon2, List list, List list2, MediaButton mediaButton, String str2, MediaSession.Token token, PendingIntent pendingIntent, MediaDeviceData mediaDeviceData, boolean z2, Runnable runnable, int i2, boolean z3, String str3, boolean z4, Boolean bool, boolean z5, long j, InstanceId instanceId, int i3, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i = mediaData.userId;
        }
        if ((i4 & 2) != 0) {
            z = mediaData.initialized;
        }
        if ((i4 & 4) != 0) {
            str = mediaData.app;
        }
        if ((i4 & 8) != 0) {
            icon = mediaData.appIcon;
        }
        if ((i4 & 16) != 0) {
            charSequence = mediaData.artist;
        }
        if ((i4 & 32) != 0) {
            charSequence2 = mediaData.song;
        }
        if ((i4 & 64) != 0) {
            icon2 = mediaData.artwork;
        }
        if ((i4 & RecyclerView.ViewHolder.FLAG_IGNORE) != 0) {
            list = mediaData.actions;
        }
        if ((i4 & RecyclerView.ViewHolder.FLAG_TMP_DETACHED) != 0) {
            list2 = mediaData.actionsToShowInCompact;
        }
        if ((i4 & RecyclerView.ViewHolder.FLAG_ADAPTER_POSITION_UNKNOWN) != 0) {
            mediaButton = mediaData.semanticActions;
        }
        if ((i4 & RecyclerView.ViewHolder.FLAG_ADAPTER_FULLUPDATE) != 0) {
            str2 = mediaData.packageName;
        }
        if ((i4 & RecyclerView.ViewHolder.FLAG_MOVED) != 0) {
            token = mediaData.token;
        }
        if ((i4 & RecyclerView.ViewHolder.FLAG_APPEARED_IN_PRE_LAYOUT) != 0) {
            pendingIntent = mediaData.clickIntent;
        }
        if ((i4 & RecyclerView.ViewHolder.FLAG_BOUNCED_FROM_HIDDEN_LIST) != 0) {
            mediaDeviceData = mediaData.device;
        }
        if ((i4 & 16384) != 0) {
            z2 = mediaData.active;
        }
        if ((i4 & 32768) != 0) {
            runnable = mediaData.resumeAction;
        }
        if ((i4 & 65536) != 0) {
            i2 = mediaData.playbackLocation;
        }
        if ((i4 & 131072) != 0) {
            z3 = mediaData.resumption;
        }
        if ((i4 & 262144) != 0) {
            str3 = mediaData.notificationKey;
        }
        if ((i4 & 524288) != 0) {
            z4 = mediaData.hasCheckedForResume;
        }
        if ((i4 & 1048576) != 0) {
            bool = mediaData.isPlaying;
        }
        if ((i4 & 2097152) != 0) {
            z5 = mediaData.isClearable;
        }
        if ((i4 & 4194304) != 0) {
            j = mediaData.lastActive;
        }
        if ((i4 & 8388608) != 0) {
            instanceId = mediaData.instanceId;
        }
        if ((i4 & 16777216) != 0) {
            i3 = mediaData.appUid;
        }
        return mediaData.copy(i, z, str, icon, charSequence, charSequence2, icon2, list, list2, mediaButton, str2, token, pendingIntent, mediaDeviceData, z2, runnable, i2, z3, str3, z4, bool, z5, j, instanceId, i3);
    }

    public final MediaData copy(int i, boolean z, String str, Icon icon, CharSequence charSequence, CharSequence charSequence2, Icon icon2, List<MediaAction> list, List<Integer> list2, MediaButton mediaButton, String str2, MediaSession.Token token, PendingIntent pendingIntent, MediaDeviceData mediaDeviceData, boolean z2, Runnable runnable, int i2, boolean z3, String str3, boolean z4, Boolean bool, boolean z5, long j, InstanceId instanceId, int i3) {
        return new MediaData(i, z, str, icon, charSequence, charSequence2, icon2, list, list2, mediaButton, str2, token, pendingIntent, mediaDeviceData, z2, runnable, i2, z3, str3, z4, bool, z5, j, instanceId, i3);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof MediaData) {
            MediaData mediaData = (MediaData) obj;
            return this.userId == mediaData.userId && this.initialized == mediaData.initialized && Intrinsics.areEqual(this.app, mediaData.app) && Intrinsics.areEqual(this.appIcon, mediaData.appIcon) && Intrinsics.areEqual(this.artist, mediaData.artist) && Intrinsics.areEqual(this.song, mediaData.song) && Intrinsics.areEqual(this.artwork, mediaData.artwork) && Intrinsics.areEqual(this.actions, mediaData.actions) && Intrinsics.areEqual(this.actionsToShowInCompact, mediaData.actionsToShowInCompact) && Intrinsics.areEqual(this.semanticActions, mediaData.semanticActions) && Intrinsics.areEqual(this.packageName, mediaData.packageName) && Intrinsics.areEqual(this.token, mediaData.token) && Intrinsics.areEqual(this.clickIntent, mediaData.clickIntent) && Intrinsics.areEqual(this.device, mediaData.device) && this.active == mediaData.active && Intrinsics.areEqual(this.resumeAction, mediaData.resumeAction) && this.playbackLocation == mediaData.playbackLocation && this.resumption == mediaData.resumption && Intrinsics.areEqual(this.notificationKey, mediaData.notificationKey) && this.hasCheckedForResume == mediaData.hasCheckedForResume && Intrinsics.areEqual(this.isPlaying, mediaData.isPlaying) && this.isClearable == mediaData.isClearable && this.lastActive == mediaData.lastActive && Intrinsics.areEqual(this.instanceId, mediaData.instanceId) && this.appUid == mediaData.appUid;
        }
        return false;
    }

    public final List<MediaAction> getActions() {
        return this.actions;
    }

    public final List<Integer> getActionsToShowInCompact() {
        return this.actionsToShowInCompact;
    }

    public final boolean getActive() {
        return this.active;
    }

    public final String getApp() {
        return this.app;
    }

    public final Icon getAppIcon() {
        return this.appIcon;
    }

    public final int getAppUid() {
        return this.appUid;
    }

    public final CharSequence getArtist() {
        return this.artist;
    }

    public final Icon getArtwork() {
        return this.artwork;
    }

    public final PendingIntent getClickIntent() {
        return this.clickIntent;
    }

    public final MediaDeviceData getDevice() {
        return this.device;
    }

    public final boolean getHasCheckedForResume() {
        return this.hasCheckedForResume;
    }

    public final InstanceId getInstanceId() {
        return this.instanceId;
    }

    public final long getLastActive() {
        return this.lastActive;
    }

    public final String getNotificationKey() {
        return this.notificationKey;
    }

    public final String getPackageName() {
        return this.packageName;
    }

    public final int getPlaybackLocation() {
        return this.playbackLocation;
    }

    public final Runnable getResumeAction() {
        return this.resumeAction;
    }

    public final boolean getResumption() {
        return this.resumption;
    }

    public final MediaButton getSemanticActions() {
        return this.semanticActions;
    }

    public final CharSequence getSong() {
        return this.song;
    }

    public final MediaSession.Token getToken() {
        return this.token;
    }

    public final int getUserId() {
        return this.userId;
    }

    public int hashCode() {
        int hashCode = Integer.hashCode(this.userId);
        boolean z = this.initialized;
        int i = 1;
        int i2 = z ? 1 : 0;
        if (z) {
            i2 = 1;
        }
        String str = this.app;
        int i3 = 0;
        int hashCode2 = str == null ? 0 : str.hashCode();
        Icon icon = this.appIcon;
        int hashCode3 = icon == null ? 0 : icon.hashCode();
        CharSequence charSequence = this.artist;
        int hashCode4 = charSequence == null ? 0 : charSequence.hashCode();
        CharSequence charSequence2 = this.song;
        int hashCode5 = charSequence2 == null ? 0 : charSequence2.hashCode();
        Icon icon2 = this.artwork;
        int hashCode6 = icon2 == null ? 0 : icon2.hashCode();
        int hashCode7 = this.actions.hashCode();
        int hashCode8 = this.actionsToShowInCompact.hashCode();
        MediaButton mediaButton = this.semanticActions;
        int hashCode9 = mediaButton == null ? 0 : mediaButton.hashCode();
        int hashCode10 = this.packageName.hashCode();
        MediaSession.Token token = this.token;
        int hashCode11 = token == null ? 0 : token.hashCode();
        PendingIntent pendingIntent = this.clickIntent;
        int hashCode12 = pendingIntent == null ? 0 : pendingIntent.hashCode();
        MediaDeviceData mediaDeviceData = this.device;
        int hashCode13 = mediaDeviceData == null ? 0 : mediaDeviceData.hashCode();
        boolean z2 = this.active;
        int i4 = z2 ? 1 : 0;
        if (z2) {
            i4 = 1;
        }
        Runnable runnable = this.resumeAction;
        int hashCode14 = runnable == null ? 0 : runnable.hashCode();
        int hashCode15 = Integer.hashCode(this.playbackLocation);
        boolean z3 = this.resumption;
        int i5 = z3 ? 1 : 0;
        if (z3) {
            i5 = 1;
        }
        String str2 = this.notificationKey;
        int hashCode16 = str2 == null ? 0 : str2.hashCode();
        boolean z4 = this.hasCheckedForResume;
        int i6 = z4 ? 1 : 0;
        if (z4) {
            i6 = 1;
        }
        Boolean bool = this.isPlaying;
        if (bool != null) {
            i3 = bool.hashCode();
        }
        boolean z5 = this.isClearable;
        if (!z5) {
            i = z5 ? 1 : 0;
        }
        return (((((((((((((((((((((((((((((((((((((((((((((((hashCode * 31) + i2) * 31) + hashCode2) * 31) + hashCode3) * 31) + hashCode4) * 31) + hashCode5) * 31) + hashCode6) * 31) + hashCode7) * 31) + hashCode8) * 31) + hashCode9) * 31) + hashCode10) * 31) + hashCode11) * 31) + hashCode12) * 31) + hashCode13) * 31) + i4) * 31) + hashCode14) * 31) + hashCode15) * 31) + i5) * 31) + hashCode16) * 31) + i6) * 31) + i3) * 31) + i) * 31) + Long.hashCode(this.lastActive)) * 31) + this.instanceId.hashCode()) * 31) + Integer.hashCode(this.appUid);
    }

    public final boolean isClearable() {
        return this.isClearable;
    }

    public final boolean isLocalSession() {
        return this.playbackLocation == 0;
    }

    public final Boolean isPlaying() {
        return this.isPlaying;
    }

    public final void setActive(boolean z) {
        this.active = z;
    }

    public final void setHasCheckedForResume(boolean z) {
        this.hasCheckedForResume = z;
    }

    public final void setResumeAction(Runnable runnable) {
        this.resumeAction = runnable;
    }

    public String toString() {
        int i = this.userId;
        boolean z = this.initialized;
        String str = this.app;
        Icon icon = this.appIcon;
        CharSequence charSequence = this.artist;
        CharSequence charSequence2 = this.song;
        Icon icon2 = this.artwork;
        List<MediaAction> list = this.actions;
        List<Integer> list2 = this.actionsToShowInCompact;
        MediaButton mediaButton = this.semanticActions;
        String str2 = this.packageName;
        MediaSession.Token token = this.token;
        PendingIntent pendingIntent = this.clickIntent;
        MediaDeviceData mediaDeviceData = this.device;
        boolean z2 = this.active;
        Runnable runnable = this.resumeAction;
        int i2 = this.playbackLocation;
        boolean z3 = this.resumption;
        String str3 = this.notificationKey;
        boolean z4 = this.hasCheckedForResume;
        Boolean bool = this.isPlaying;
        boolean z5 = this.isClearable;
        long j = this.lastActive;
        InstanceId instanceId = this.instanceId;
        int i3 = this.appUid;
        return "MediaData(userId=" + i + ", initialized=" + z + ", app=" + str + ", appIcon=" + icon + ", artist=" + ((Object) charSequence) + ", song=" + ((Object) charSequence2) + ", artwork=" + icon2 + ", actions=" + list + ", actionsToShowInCompact=" + list2 + ", semanticActions=" + mediaButton + ", packageName=" + str2 + ", token=" + token + ", clickIntent=" + pendingIntent + ", device=" + mediaDeviceData + ", active=" + z2 + ", resumeAction=" + runnable + ", playbackLocation=" + i2 + ", resumption=" + z3 + ", notificationKey=" + str3 + ", hasCheckedForResume=" + z4 + ", isPlaying=" + bool + ", isClearable=" + z5 + ", lastActive=" + j + ", instanceId=" + instanceId + ", appUid=" + i3 + ")";
    }
}