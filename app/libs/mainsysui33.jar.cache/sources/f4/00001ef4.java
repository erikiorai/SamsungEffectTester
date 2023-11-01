package com.android.systemui.people;

import android.app.Notification;
import android.app.Person;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.wm.shell.bubbles.Bubbles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/* loaded from: mainsysui33.jar:com/android/systemui/people/NotificationHelper.class */
public class NotificationHelper {
    public static Comparator<NotificationEntry> notificationEntryComparator = new Comparator<NotificationEntry>() { // from class: com.android.systemui.people.NotificationHelper.1
        /* JADX DEBUG: Method merged with bridge method */
        @Override // java.util.Comparator
        public int compare(NotificationEntry notificationEntry, NotificationEntry notificationEntry2) {
            Notification notification = notificationEntry.getSbn().getNotification();
            Notification notification2 = notificationEntry2.getSbn().getNotification();
            boolean isMissedCall = NotificationHelper.isMissedCall(notification);
            boolean isMissedCall2 = NotificationHelper.isMissedCall(notification2);
            if (!isMissedCall || isMissedCall2) {
                if (isMissedCall || !isMissedCall2) {
                    List<Notification.MessagingStyle.Message> messagingStyleMessages = NotificationHelper.getMessagingStyleMessages(notification);
                    List<Notification.MessagingStyle.Message> messagingStyleMessages2 = NotificationHelper.getMessagingStyleMessages(notification2);
                    if (messagingStyleMessages != null && messagingStyleMessages2 != null) {
                        return (int) (messagingStyleMessages2.get(0).getTimestamp() - messagingStyleMessages.get(0).getTimestamp());
                    } else if (messagingStyleMessages == null) {
                        return 1;
                    } else {
                        if (messagingStyleMessages2 == null) {
                            return -1;
                        }
                        return (int) (notification2.when - notification.when);
                    }
                }
                return 1;
            }
            return -1;
        }
    };

    public static String getContactUri(StatusBarNotification statusBarNotification) {
        Person senderPerson;
        String uri;
        ArrayList parcelableArrayList = statusBarNotification.getNotification().extras.getParcelableArrayList("android.people.list");
        if (parcelableArrayList == null || parcelableArrayList.get(0) == null || (uri = ((Person) parcelableArrayList.get(0)).getUri()) == null || uri.isEmpty()) {
            List<Notification.MessagingStyle.Message> messagingStyleMessages = getMessagingStyleMessages(statusBarNotification.getNotification());
            if (messagingStyleMessages == null || messagingStyleMessages.isEmpty() || (senderPerson = messagingStyleMessages.get(0).getSenderPerson()) == null || senderPerson.getUri() == null || senderPerson.getUri().isEmpty()) {
                return null;
            }
            return senderPerson.getUri();
        }
        return uri;
    }

    public static NotificationEntry getHighestPriorityNotification(Set<NotificationEntry> set) {
        if (set == null || set.isEmpty()) {
            return null;
        }
        return set.stream().filter(new Predicate() { // from class: com.android.systemui.people.NotificationHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return NotificationHelper.isMissedCallOrHasContent((NotificationEntry) obj);
            }
        }).sorted(notificationEntryComparator).findFirst().orElse(null);
    }

    @VisibleForTesting
    public static List<Notification.MessagingStyle.Message> getMessagingStyleMessages(Notification notification) {
        Bundle bundle;
        if (notification == null || !notification.isStyle(Notification.MessagingStyle.class) || (bundle = notification.extras) == null) {
            return null;
        }
        Parcelable[] parcelableArray = bundle.getParcelableArray("android.messages");
        if (ArrayUtils.isEmpty(parcelableArray)) {
            return null;
        }
        List<Notification.MessagingStyle.Message> messagesFromBundleArray = Notification.MessagingStyle.Message.getMessagesFromBundleArray(parcelableArray);
        messagesFromBundleArray.sort(Collections.reverseOrder(Comparator.comparing(new Function() { // from class: com.android.systemui.people.NotificationHelper$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Long.valueOf(((Notification.MessagingStyle.Message) obj).getTimestamp());
            }
        })));
        return messagesFromBundleArray;
    }

    public static CharSequence getSenderIfGroupConversation(Notification notification, Notification.MessagingStyle.Message message) {
        Person senderPerson;
        if (isGroupConversation(notification) && (senderPerson = message.getSenderPerson()) != null) {
            return senderPerson.getName();
        }
        return null;
    }

    public static boolean hasContent(NotificationEntry notificationEntry) {
        if (notificationEntry == null) {
            return false;
        }
        List<Notification.MessagingStyle.Message> messagingStyleMessages = getMessagingStyleMessages(notificationEntry.getSbn().getNotification());
        boolean z = false;
        if (messagingStyleMessages != null) {
            z = false;
            if (!messagingStyleMessages.isEmpty()) {
                z = true;
            }
        }
        return z;
    }

    public static boolean hasReadContactsPermission(PackageManager packageManager, StatusBarNotification statusBarNotification) {
        return packageManager.checkPermission("android.permission.READ_CONTACTS", statusBarNotification.getPackageName()) == 0;
    }

    public static boolean isGroupConversation(Notification notification) {
        return notification.extras.getBoolean("android.isGroupConversation", false);
    }

    public static boolean isMissedCall(Notification notification) {
        return notification != null && Objects.equals(notification.category, "missed_call");
    }

    public static boolean isMissedCall(NotificationEntry notificationEntry) {
        return (notificationEntry == null || notificationEntry.getSbn().getNotification() == null || !isMissedCall(notificationEntry.getSbn().getNotification())) ? false : true;
    }

    public static boolean isMissedCallOrHasContent(NotificationEntry notificationEntry) {
        return isMissedCall(notificationEntry) || hasContent(notificationEntry);
    }

    public static boolean isValid(NotificationEntry notificationEntry) {
        return (notificationEntry == null || notificationEntry.getRanking() == null || notificationEntry.getRanking().getConversationShortcutInfo() == null || notificationEntry.getSbn().getNotification() == null) ? false : true;
    }

    public static boolean shouldFilterOut(Optional<Bubbles> optional, NotificationEntry notificationEntry) {
        boolean z = false;
        try {
            if (optional.isPresent()) {
                z = false;
                if (optional.get().isBubbleNotificationSuppressedFromShade(notificationEntry.getKey(), notificationEntry.getSbn().getGroupKey())) {
                    z = true;
                }
            }
        } catch (Exception e) {
            Log.e("PeopleNotifHelper", "Exception checking if notification is suppressed: " + e);
            z = false;
        }
        return z;
    }

    public static boolean shouldMatchNotificationByUri(StatusBarNotification statusBarNotification) {
        Notification notification = statusBarNotification.getNotification();
        if (notification == null) {
            return false;
        }
        return isMissedCall(notification);
    }
}