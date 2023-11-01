package com.android.systemui.people.widget;

import android.app.people.PeopleSpaceTile;
import android.text.TextUtils;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: mainsysui33.jar:com/android/systemui/people/widget/PeopleTileKey.class */
public class PeopleTileKey {
    public static final Pattern KEY_PATTERN = Pattern.compile("(.+)/(-?\\d+)/(\\p{L}.*)");
    public String mPackageName;
    public String mShortcutId;
    public int mUserId;

    public PeopleTileKey(PeopleSpaceTile peopleSpaceTile) {
        this.mShortcutId = peopleSpaceTile.getId();
        this.mUserId = peopleSpaceTile.getUserHandle().getIdentifier();
        this.mPackageName = peopleSpaceTile.getPackageName();
    }

    public PeopleTileKey(NotificationEntry notificationEntry) {
        this.mShortcutId = (notificationEntry.getRanking() == null || notificationEntry.getRanking().getConversationShortcutInfo() == null) ? "" : notificationEntry.getRanking().getConversationShortcutInfo().getId();
        this.mUserId = notificationEntry.getSbn().getUser() != null ? notificationEntry.getSbn().getUser().getIdentifier() : -1;
        this.mPackageName = notificationEntry.getSbn().getPackageName();
    }

    public PeopleTileKey(String str, int i, String str2) {
        this.mShortcutId = str;
        this.mUserId = i;
        this.mPackageName = str2;
    }

    public static PeopleTileKey fromString(String str) {
        if (str == null) {
            return null;
        }
        Matcher matcher = KEY_PATTERN.matcher(str);
        if (matcher.find()) {
            try {
                return new PeopleTileKey(matcher.group(1), Integer.parseInt(matcher.group(2)), matcher.group(3));
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    public static boolean isValid(PeopleTileKey peopleTileKey) {
        return peopleTileKey != null && peopleTileKey.validate();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof PeopleTileKey) {
            return Objects.equals(((PeopleTileKey) obj).toString(), toString());
        }
        return false;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getShortcutId() {
        return this.mShortcutId;
    }

    public int getUserId() {
        return this.mUserId;
    }

    public int hashCode() {
        return this.mPackageName.hashCode() + Integer.valueOf(this.mUserId).hashCode() + this.mShortcutId.hashCode();
    }

    public void setUserId(int i) {
        this.mUserId = i;
    }

    public String toString() {
        return this.mShortcutId + "/" + this.mUserId + "/" + this.mPackageName;
    }

    public final boolean validate() {
        return (TextUtils.isEmpty(this.mShortcutId) || TextUtils.isEmpty(this.mPackageName) || this.mUserId < 0) ? false : true;
    }
}