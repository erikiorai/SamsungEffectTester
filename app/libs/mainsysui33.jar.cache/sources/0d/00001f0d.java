package com.android.systemui.people;

import android.app.PendingIntent;
import android.app.people.ConversationStatus;
import android.app.people.PeopleSpaceTile;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.util.IconDrawableFactory;
import android.util.Log;
import android.util.Pair;
import android.util.Size;
import android.util.SizeF;
import android.util.TypedValue;
import android.widget.RemoteViews;
import android.widget.TextView;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.core.math.MathUtils;
import com.android.internal.annotations.VisibleForTesting;
import com.android.launcher3.icons.FastBitmapDrawable;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.people.data.model.PeopleTileModel;
import com.android.systemui.people.widget.LaunchConversationActivity;
import com.android.systemui.people.widget.PeopleTileKey;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/* loaded from: mainsysui33.jar:com/android/systemui/people/PeopleTileViewHelper.class */
public class PeopleTileViewHelper {
    public int mAppWidgetId;
    public Context mContext;
    public float mDensity;
    public int mHeight;
    public NumberFormat mIntegerFormat;
    public boolean mIsLeftToRight;
    public PeopleTileKey mKey;
    public int mLayoutSize = getLayoutSize();
    public Locale mLocale;
    public int mMediumVerticalPadding;
    public PeopleSpaceTile mTile;
    public int mWidth;
    public static final CharSequence EMOJI_CAKE = "��";
    public static final Pattern DOUBLE_EXCLAMATION_PATTERN = Pattern.compile("[!][!]+");
    public static final Pattern DOUBLE_QUESTION_PATTERN = Pattern.compile("[?][?]+");
    public static final Pattern ANY_DOUBLE_MARK_PATTERN = Pattern.compile("[!?][!?]+");
    public static final Pattern MIXED_MARK_PATTERN = Pattern.compile("![?].*|.*[?]!");
    public static final Pattern EMOJI_PATTERN = Pattern.compile("\\p{RI}\\p{RI}|(\\p{Emoji}(\\p{EMod}|\\x{FE0F}\\x{20E3}?|[\\x{E0020}-\\x{E007E}]+\\x{E007F})|[\\p{Emoji}&&\\p{So}])(\\x{200D}\\p{Emoji}(\\p{EMod}|\\x{FE0F}\\x{20E3}?|[\\x{E0020}-\\x{E007E}]+\\x{E007F})?)*");

    /* loaded from: mainsysui33.jar:com/android/systemui/people/PeopleTileViewHelper$RemoteViewsAndSizes.class */
    public static final class RemoteViewsAndSizes {
        public final int mAvatarSize;
        public final RemoteViews mRemoteViews;

        public RemoteViewsAndSizes(RemoteViews remoteViews, int i) {
            this.mRemoteViews = remoteViews;
            this.mAvatarSize = i;
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.people.PeopleTileViewHelper$$ExternalSyntheticLambda0.test(java.lang.Object):boolean] */
    public static /* synthetic */ boolean $r8$lambda$03fLQFNH9zR5WBHusiSnejUjgD8(ConversationStatus conversationStatus) {
        return lambda$getHasNewStory$4(conversationStatus);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.people.PeopleTileViewHelper$$ExternalSyntheticLambda1.apply(java.lang.Object):java.lang.Object] */
    public static /* synthetic */ RemoteViews $r8$lambda$27vnLCwz8eG2hdMtANRmIFssoKE(Context context, PeopleSpaceTile peopleSpaceTile, int i, PeopleTileKey peopleTileKey, SizeF sizeF) {
        return lambda$createRemoteViews$0(context, peopleSpaceTile, i, peopleTileKey, sizeF);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.people.PeopleTileViewHelper$$ExternalSyntheticLambda4.test(java.lang.Object):boolean] */
    /* renamed from: $r8$lambda$JZy-TDHh4xSyzClZIoEMVG41tqY */
    public static /* synthetic */ boolean m3541$r8$lambda$JZyTDHh4xSyzClZIoEMVG41tqY(ConversationStatus conversationStatus) {
        return lambda$setCommonRemoteViewsFields$3(conversationStatus);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.people.PeopleTileViewHelper$$ExternalSyntheticLambda2.test(java.lang.Object):boolean] */
    /* renamed from: $r8$lambda$ZEemqe9wBIF2mxVq-yu89jspVxw */
    public static /* synthetic */ boolean m3542$r8$lambda$ZEemqe9wBIF2mxVqyu89jspVxw(PeopleTileViewHelper peopleTileViewHelper, ConversationStatus conversationStatus) {
        return peopleTileViewHelper.lambda$getViewForTile$1(conversationStatus);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.people.PeopleTileViewHelper$$ExternalSyntheticLambda6.onHeaderDecoded(android.graphics.ImageDecoder, android.graphics.ImageDecoder$ImageInfo, android.graphics.ImageDecoder$Source):void] */
    /* renamed from: $r8$lambda$e0vy-taytLwxzn2IGXwdLCpNByk */
    public static /* synthetic */ void m3543$r8$lambda$e0vytaytLwxzn2IGXwdLCpNByk(PeopleTileViewHelper peopleTileViewHelper, ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        peopleTileViewHelper.lambda$resolveImage$5(imageDecoder, imageInfo, source);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.people.PeopleTileViewHelper$$ExternalSyntheticLambda3.apply(java.lang.Object):java.lang.Object] */
    /* renamed from: $r8$lambda$jk9uD-wWMD6EvmamOsyJpSEGT7g */
    public static /* synthetic */ Long m3544$r8$lambda$jk9uDwWMD6EvmamOsyJpSEGT7g(ConversationStatus conversationStatus) {
        return lambda$getViewForTile$2(conversationStatus);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.people.PeopleTileViewHelper$$ExternalSyntheticLambda5.test(java.lang.Object):boolean] */
    public static /* synthetic */ boolean $r8$lambda$t4jIx4TR3ONP4yE5z9w_qpxzi3M(ConversationStatus conversationStatus) {
        return lambda$getBirthdayStatus$6(conversationStatus);
    }

    public PeopleTileViewHelper(Context context, PeopleSpaceTile peopleSpaceTile, int i, int i2, int i3, PeopleTileKey peopleTileKey) {
        this.mContext = context;
        this.mTile = peopleSpaceTile;
        this.mKey = peopleTileKey;
        this.mAppWidgetId = i;
        this.mDensity = context.getResources().getDisplayMetrics().density;
        this.mWidth = i2;
        this.mHeight = i3;
        this.mIsLeftToRight = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 0;
    }

    public static RemoteViews createRemoteViews(final Context context, final PeopleSpaceTile peopleSpaceTile, final int i, Bundle bundle, final PeopleTileKey peopleTileKey) {
        return new RemoteViews((Map) getWidgetSizes(context, bundle).stream().distinct().collect(Collectors.toMap(Function.identity(), new Function() { // from class: com.android.systemui.people.PeopleTileViewHelper$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return PeopleTileViewHelper.$r8$lambda$27vnLCwz8eG2hdMtANRmIFssoKE(context, peopleSpaceTile, i, peopleTileKey, (SizeF) obj);
            }
        })));
    }

    public static boolean getHasNewStory(PeopleSpaceTile peopleSpaceTile) {
        return peopleSpaceTile.getStatuses() != null && peopleSpaceTile.getStatuses().stream().anyMatch(new Predicate() { // from class: com.android.systemui.people.PeopleTileViewHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PeopleTileViewHelper.$r8$lambda$03fLQFNH9zR5WBHusiSnejUjgD8((ConversationStatus) obj);
            }
        });
    }

    public static String getLastInteractionString(Context context, long j) {
        if (j == 0) {
            Log.e("PeopleTileView", "Could not get valid last interaction");
            return null;
        }
        Duration ofMillis = Duration.ofMillis(System.currentTimeMillis() - j);
        if (ofMillis.toDays() <= 1) {
            return null;
        }
        return ofMillis.toDays() < 7 ? context.getString(R$string.days_timestamp, Long.valueOf(ofMillis.toDays())) : ofMillis.toDays() == 7 ? context.getString(R$string.one_week_timestamp) : ofMillis.toDays() < 14 ? context.getString(R$string.over_one_week_timestamp) : ofMillis.toDays() == 14 ? context.getString(R$string.two_weeks_timestamp) : context.getString(R$string.over_two_weeks_timestamp);
    }

    public static Bitmap getPersonIconBitmap(Context context, int i, boolean z, Icon icon, String str, int i2, boolean z2, boolean z3) {
        if (icon == null) {
            Drawable mutate = context.getDrawable(R$drawable.ic_avatar_with_badge).mutate();
            mutate.setColorFilter(FastBitmapDrawable.getDisabledColorFilter());
            return PeopleSpaceUtils.convertDrawableToBitmap(mutate);
        }
        Drawable peopleTileDrawable = new PeopleStoryIconFactory(context, context.getPackageManager(), IconDrawableFactory.newInstance(context, false), i).getPeopleTileDrawable(RoundedBitmapDrawableFactory.create(context.getResources(), icon.getBitmap()), str, i2, z2, z);
        if (z3) {
            peopleTileDrawable.setColorFilter(FastBitmapDrawable.getDisabledColorFilter());
        }
        return PeopleSpaceUtils.convertDrawableToBitmap(peopleTileDrawable);
    }

    public static Bitmap getPersonIconBitmap(Context context, PeopleSpaceTile peopleSpaceTile, int i, boolean z) {
        return getPersonIconBitmap(context, i, z, peopleSpaceTile.getUserIcon(), peopleSpaceTile.getPackageName(), PeopleSpaceUtils.getUserId(peopleSpaceTile), peopleSpaceTile.isImportantConversation(), isDndBlockingTileData(peopleSpaceTile));
    }

    public static Bitmap getPersonIconBitmap(Context context, PeopleTileModel peopleTileModel, int i) {
        return getPersonIconBitmap(context, i, peopleTileModel.getHasNewStory(), peopleTileModel.getUserIcon(), peopleTileModel.getKey().getPackageName(), peopleTileModel.getKey().getUserId(), peopleTileModel.isImportant(), peopleTileModel.isDndBlocking());
    }

    public static int getPowerOfTwoForSampleRatio(double d) {
        return Math.max(1, Integer.highestOneBit((int) Math.floor(d)));
    }

    public static int getSizeInDp(Context context, int i, float f) {
        return (int) (context.getResources().getDimension(i) / f);
    }

    public static List<SizeF> getWidgetSizes(Context context, Bundle bundle) {
        float f = context.getResources().getDisplayMetrics().density;
        ArrayList parcelableArrayList = bundle.getParcelableArrayList("appWidgetSizes");
        if (parcelableArrayList == null || parcelableArrayList.isEmpty()) {
            int sizeInDp = getSizeInDp(context, R$dimen.default_width, f);
            int sizeInDp2 = getSizeInDp(context, R$dimen.default_height, f);
            ArrayList arrayList = new ArrayList(2);
            arrayList.add(new SizeF(bundle.getInt("appWidgetMinWidth", sizeInDp), bundle.getInt("appWidgetMaxHeight", sizeInDp2)));
            arrayList.add(new SizeF(bundle.getInt("appWidgetMaxWidth", sizeInDp), bundle.getInt("appWidgetMinHeight", sizeInDp2)));
            return arrayList;
        }
        return parcelableArrayList;
    }

    public static boolean isDndBlockingTileData(PeopleSpaceTile peopleSpaceTile) {
        if (peopleSpaceTile == null) {
            return false;
        }
        int notificationPolicyState = peopleSpaceTile.getNotificationPolicyState();
        if ((notificationPolicyState & 1) != 0) {
            return false;
        }
        if ((notificationPolicyState & 4) == 0 || !peopleSpaceTile.isImportantConversation()) {
            if ((notificationPolicyState & 8) == 0 || peopleSpaceTile.getContactAffinity() != 1.0f) {
                if ((notificationPolicyState & 16) == 0 || !(peopleSpaceTile.getContactAffinity() == 0.5f || peopleSpaceTile.getContactAffinity() == 1.0f)) {
                    return !peopleSpaceTile.canBypassDnd();
                }
                return false;
            }
            return false;
        }
        return false;
    }

    public static /* synthetic */ RemoteViews lambda$createRemoteViews$0(Context context, PeopleSpaceTile peopleSpaceTile, int i, PeopleTileKey peopleTileKey, SizeF sizeF) {
        return new PeopleTileViewHelper(context, peopleSpaceTile, i, (int) sizeF.getWidth(), (int) sizeF.getHeight(), peopleTileKey).getViews();
    }

    public static /* synthetic */ boolean lambda$getBirthdayStatus$6(ConversationStatus conversationStatus) {
        boolean z = true;
        if (conversationStatus.getActivity() != 1) {
            z = false;
        }
        return z;
    }

    public static /* synthetic */ boolean lambda$getHasNewStory$4(ConversationStatus conversationStatus) {
        return conversationStatus.getActivity() == 3;
    }

    public static /* synthetic */ Long lambda$getViewForTile$2(ConversationStatus conversationStatus) {
        return Long.valueOf(conversationStatus.getStartTimeMillis());
    }

    public static /* synthetic */ boolean lambda$setCommonRemoteViewsFields$3(ConversationStatus conversationStatus) {
        return conversationStatus.getAvailability() == 0;
    }

    public final StaticLayout buildStaticLayout(CharSequence charSequence, int i, int i2) {
        try {
            TextView textView = new TextView(this.mContext);
            textView.setTextSize(0, this.mContext.getResources().getDimension(i));
            textView.setTextAppearance(16974253);
            return StaticLayout.Builder.obtain(charSequence, 0, charSequence.length(), textView.getPaint(), dpToPx(i2)).setBreakStrategy(0).build();
        } catch (Exception e) {
            Log.e("PeopleTileView", "Could not create static layout: " + e);
            return null;
        }
    }

    public final RemoteViewsAndSizes createDndRemoteViews() {
        int maxAvatarSize;
        RemoteViews remoteViews = new RemoteViews(this.mContext.getPackageName(), getViewForDndRemoteViews());
        int sizeInDp = getSizeInDp(R$dimen.avatar_size_for_medium_empty);
        int sizeInDp2 = getSizeInDp(R$dimen.max_people_avatar_size);
        String string = this.mContext.getString(R$string.paused_by_dnd);
        int i = R$id.text_content;
        remoteViews.setTextViewText(i, string);
        int i2 = this.mLayoutSize == 2 ? R$dimen.content_text_size_for_large : R$dimen.content_text_size_for_medium;
        remoteViews.setTextViewTextSize(i, 0, this.mContext.getResources().getDimension(i2));
        int lineHeightFromResource = getLineHeightFromResource(i2);
        if (this.mLayoutSize == 1) {
            remoteViews.setInt(i, "setMaxLines", (this.mHeight - 16) / lineHeightFromResource);
            maxAvatarSize = sizeInDp;
        } else {
            int dpToPx = dpToPx(16);
            int dpToPx2 = dpToPx(14);
            int sizeInDp3 = getSizeInDp(this.mLayoutSize == 0 ? R$dimen.regular_predefined_icon : R$dimen.largest_predefined_icon);
            int i3 = (this.mHeight - 32) - sizeInDp3;
            int sizeInDp4 = getSizeInDp(R$dimen.padding_between_suppressed_layout_items) * 2;
            int i4 = (i3 - sizeInDp) - sizeInDp4;
            int estimateTextHeight = estimateTextHeight(string, i2, this.mWidth - 32);
            if (estimateTextHeight > i4 || this.mLayoutSize != 2) {
                if (this.mLayoutSize != 0) {
                    remoteViews = new RemoteViews(this.mContext.getPackageName(), R$layout.people_tile_small);
                }
                maxAvatarSize = getMaxAvatarSize(remoteViews);
                remoteViews.setViewVisibility(R$id.messages_count, 8);
                remoteViews.setViewVisibility(R$id.name, 8);
                remoteViews.setContentDescription(R$id.predefined_icon, string);
            } else {
                remoteViews.setViewVisibility(i, 0);
                remoteViews.setInt(i, "setMaxLines", i4 / lineHeightFromResource);
                int i5 = R$id.predefined_icon;
                remoteViews.setContentDescription(i5, null);
                maxAvatarSize = MathUtils.clamp(Math.min(this.mWidth - 32, (i3 - estimateTextHeight) - sizeInDp4), dpToPx(10.0f), sizeInDp2);
                remoteViews.setViewPadding(16908288, dpToPx, dpToPx2, dpToPx, dpToPx);
                float f = sizeInDp3;
                remoteViews.setViewLayoutWidth(i5, f, 1);
                remoteViews.setViewLayoutHeight(i5, f, 1);
            }
            int i6 = R$id.predefined_icon;
            remoteViews.setViewVisibility(i6, 0);
            remoteViews.setImageViewResource(i6, R$drawable.ic_qs_dnd_on);
        }
        return new RemoteViewsAndSizes(remoteViews, maxAvatarSize);
    }

    public final RemoteViews createLastInteractionRemoteViews() {
        RemoteViews remoteViews = new RemoteViews(this.mContext.getPackageName(), getEmptyLayout());
        int i = R$id.name;
        remoteViews.setInt(i, "setMaxLines", 1);
        if (this.mLayoutSize == 0) {
            remoteViews.setViewVisibility(i, 0);
            remoteViews.setViewVisibility(R$id.predefined_icon, 8);
            remoteViews.setViewVisibility(R$id.messages_count, 8);
        }
        if (this.mTile.getUserName() != null) {
            remoteViews.setTextViewText(i, this.mTile.getUserName());
        }
        String lastInteractionString = getLastInteractionString(this.mContext, this.mTile.getLastInteractionTimestamp());
        if (lastInteractionString != null) {
            int i2 = R$id.last_interaction;
            remoteViews.setViewVisibility(i2, 0);
            remoteViews.setTextViewText(i2, lastInteractionString);
        } else {
            remoteViews.setViewVisibility(R$id.last_interaction, 8);
            if (this.mLayoutSize == 1) {
                remoteViews.setInt(i, "setMaxLines", 3);
            }
        }
        return remoteViews;
    }

    public final RemoteViews createMissedCallRemoteViews() {
        RemoteViews viewForContentLayout = setViewForContentLayout(new RemoteViews(this.mContext.getPackageName(), getLayoutForContent()));
        setPredefinedIconVisible(viewForContentLayout);
        int i = R$id.text_content;
        viewForContentLayout.setViewVisibility(i, 0);
        viewForContentLayout.setViewVisibility(R$id.messages_count, 8);
        setMaxLines(viewForContentLayout, false);
        CharSequence notificationContent = this.mTile.getNotificationContent();
        viewForContentLayout.setTextViewText(i, notificationContent);
        setContentDescriptionForNotificationTextContent(viewForContentLayout, notificationContent, this.mTile.getUserName());
        viewForContentLayout.setColorAttr(i, "setTextColor", 16844099);
        int i2 = R$id.predefined_icon;
        viewForContentLayout.setColorAttr(i2, "setColorFilter", 16844099);
        viewForContentLayout.setImageViewResource(i2, R$drawable.ic_phone_missed);
        if (this.mLayoutSize == 2) {
            viewForContentLayout.setInt(R$id.content, "setGravity", 80);
            int i3 = R$dimen.larger_predefined_icon;
            viewForContentLayout.setViewLayoutHeightDimen(i2, i3);
            viewForContentLayout.setViewLayoutWidthDimen(i2, i3);
        }
        setAvailabilityDotPadding(viewForContentLayout, R$dimen.availability_dot_notification_padding);
        return viewForContentLayout;
    }

    public final RemoteViews createNotificationRemoteViews() {
        RemoteViews decorateBackground;
        RemoteViews viewForContentLayout = setViewForContentLayout(new RemoteViews(this.mContext.getPackageName(), getLayoutForNotificationContent()));
        CharSequence notificationSender = this.mTile.getNotificationSender();
        Uri notificationDataUri = this.mTile.getNotificationDataUri();
        if (notificationDataUri != null) {
            String string = this.mContext.getString(R$string.new_notification_image_content_description, this.mTile.getUserName());
            int i = R$id.image;
            viewForContentLayout.setContentDescription(i, string);
            viewForContentLayout.setViewVisibility(i, 0);
            viewForContentLayout.setViewVisibility(R$id.text_content, 8);
            try {
                viewForContentLayout.setImageViewBitmap(i, PeopleSpaceUtils.convertDrawableToBitmap(resolveImage(notificationDataUri, this.mContext)));
                decorateBackground = viewForContentLayout;
            } catch (IOException | SecurityException e) {
                Log.e("PeopleTileView", "Could not decode image: " + e);
                int i2 = R$id.text_content;
                viewForContentLayout.setTextViewText(i2, string);
                viewForContentLayout.setViewVisibility(i2, 0);
                viewForContentLayout.setViewVisibility(R$id.image, 8);
                decorateBackground = viewForContentLayout;
            }
        } else {
            setMaxLines(viewForContentLayout, !TextUtils.isEmpty(notificationSender));
            CharSequence notificationContent = this.mTile.getNotificationContent();
            setContentDescriptionForNotificationTextContent(viewForContentLayout, notificationContent, notificationSender != null ? notificationSender : this.mTile.getUserName());
            decorateBackground = decorateBackground(viewForContentLayout, notificationContent);
            int i3 = R$id.text_content;
            decorateBackground.setColorAttr(i3, "setTextColor", 16842806);
            decorateBackground.setTextViewText(i3, this.mTile.getNotificationContent());
            if (this.mLayoutSize == 2) {
                decorateBackground.setViewPadding(R$id.name, 0, 0, 0, this.mContext.getResources().getDimensionPixelSize(R$dimen.above_notification_text_padding));
            }
            decorateBackground.setViewVisibility(R$id.image, 8);
            decorateBackground.setImageViewResource(R$id.predefined_icon, R$drawable.ic_message);
        }
        if (this.mTile.getMessagesCount() > 1) {
            if (this.mLayoutSize == 1) {
                int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R$dimen.before_messages_count_padding);
                int i4 = R$id.name;
                boolean z = this.mIsLeftToRight;
                int i5 = z ? 0 : dimensionPixelSize;
                if (!z) {
                    dimensionPixelSize = 0;
                }
                decorateBackground.setViewPadding(i4, i5, 0, dimensionPixelSize, 0);
            }
            int i6 = R$id.messages_count;
            decorateBackground.setViewVisibility(i6, 0);
            decorateBackground.setTextViewText(i6, getMessagesCountText(this.mTile.getMessagesCount()));
            if (this.mLayoutSize == 0) {
                decorateBackground.setViewVisibility(R$id.predefined_icon, 8);
            }
        }
        if (TextUtils.isEmpty(notificationSender)) {
            decorateBackground.setViewVisibility(R$id.subtext, 8);
        } else {
            int i7 = R$id.subtext;
            decorateBackground.setViewVisibility(i7, 0);
            decorateBackground.setTextViewText(i7, notificationSender);
        }
        setAvailabilityDotPadding(decorateBackground, R$dimen.availability_dot_notification_padding);
        return decorateBackground;
    }

    public final RemoteViews createStatusRemoteViews(ConversationStatus conversationStatus) {
        RemoteViews viewForContentLayout = setViewForContentLayout(new RemoteViews(this.mContext.getPackageName(), getLayoutForContent()));
        CharSequence description = conversationStatus.getDescription();
        String str = description;
        if (TextUtils.isEmpty(description)) {
            str = getStatusTextByType(conversationStatus.getActivity());
        }
        setPredefinedIconVisible(viewForContentLayout);
        int i = R$id.text_content;
        viewForContentLayout.setTextViewText(i, str);
        if (conversationStatus.getActivity() == 1 || conversationStatus.getActivity() == 8) {
            setEmojiBackground(viewForContentLayout, EMOJI_CAKE);
        }
        Icon icon = conversationStatus.getIcon();
        if (icon != null) {
            viewForContentLayout.setViewVisibility(R$id.scrim_layout, 0);
            viewForContentLayout.setImageViewIcon(R$id.status_icon, icon);
            int i2 = this.mLayoutSize;
            if (i2 == 2) {
                viewForContentLayout.setInt(R$id.content, "setGravity", 80);
                viewForContentLayout.setViewVisibility(R$id.name, 8);
                viewForContentLayout.setColorAttr(i, "setTextColor", 16842806);
            } else if (i2 == 1) {
                viewForContentLayout.setViewVisibility(i, 8);
                viewForContentLayout.setTextViewText(R$id.name, str);
            }
        } else {
            viewForContentLayout.setColorAttr(i, "setTextColor", 16842808);
            setMaxLines(viewForContentLayout, false);
        }
        setAvailabilityDotPadding(viewForContentLayout, R$dimen.availability_dot_status_padding);
        int i3 = R$id.predefined_icon;
        viewForContentLayout.setImageViewResource(i3, getDrawableForStatus(conversationStatus));
        String string = this.mContext.getString(R$string.new_status_content_description, this.mTile.getUserName(), getContentDescriptionForStatus(conversationStatus));
        int i4 = this.mLayoutSize;
        if (i4 == 0) {
            viewForContentLayout.setContentDescription(i3, string);
        } else if (i4 == 1) {
            if (icon != null) {
                i = R$id.name;
            }
            viewForContentLayout.setContentDescription(i, string);
        } else if (i4 == 2) {
            viewForContentLayout.setContentDescription(i, string);
        }
        return viewForContentLayout;
    }

    public final RemoteViews createSuppressedView() {
        PeopleSpaceTile peopleSpaceTile = this.mTile;
        RemoteViews remoteViews = (peopleSpaceTile == null || !peopleSpaceTile.isUserQuieted()) ? new RemoteViews(this.mContext.getPackageName(), R$layout.people_tile_suppressed_layout) : new RemoteViews(this.mContext.getPackageName(), R$layout.people_tile_work_profile_quiet_layout);
        Drawable mutate = this.mContext.getDrawable(R$drawable.ic_conversation_icon).mutate();
        mutate.setColorFilter(FastBitmapDrawable.getDisabledColorFilter());
        remoteViews.setImageViewBitmap(R$id.icon, PeopleSpaceUtils.convertDrawableToBitmap(mutate));
        return remoteViews;
    }

    public final RemoteViews decorateBackground(RemoteViews remoteViews, CharSequence charSequence) {
        CharSequence doubleEmoji = getDoubleEmoji(charSequence);
        if (!TextUtils.isEmpty(doubleEmoji)) {
            setEmojiBackground(remoteViews, doubleEmoji);
            setPunctuationBackground(remoteViews, null);
            return remoteViews;
        }
        CharSequence doublePunctuation = getDoublePunctuation(charSequence);
        setEmojiBackground(remoteViews, null);
        setPunctuationBackground(remoteViews, doublePunctuation);
        return remoteViews;
    }

    public final int dpToPx(float f) {
        return (int) (f * this.mDensity);
    }

    public final int estimateTextHeight(CharSequence charSequence, int i, int i2) {
        StaticLayout buildStaticLayout = buildStaticLayout(charSequence, i, i2);
        if (buildStaticLayout == null) {
            return Integer.MAX_VALUE;
        }
        return pxToDp(buildStaticLayout.getHeight());
    }

    public final ConversationStatus getBirthdayStatus(List<ConversationStatus> list) {
        Optional<ConversationStatus> findFirst = list.stream().filter(new Predicate() { // from class: com.android.systemui.people.PeopleTileViewHelper$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PeopleTileViewHelper.$r8$lambda$t4jIx4TR3ONP4yE5z9w_qpxzi3M((ConversationStatus) obj);
            }
        }).findFirst();
        if (findFirst.isPresent()) {
            return findFirst.get();
        }
        if (TextUtils.isEmpty(this.mTile.getBirthdayText())) {
            return null;
        }
        return new ConversationStatus.Builder(this.mTile.getId(), 1).build();
    }

    public final CharSequence getContentDescriptionForStatus(ConversationStatus conversationStatus) {
        CharSequence userName = this.mTile.getUserName();
        if (TextUtils.isEmpty(conversationStatus.getDescription())) {
            switch (conversationStatus.getActivity()) {
                case 1:
                    return this.mContext.getString(R$string.birthday_status_content_description, userName);
                case 2:
                    return this.mContext.getString(R$string.anniversary_status_content_description, userName);
                case 3:
                    return this.mContext.getString(R$string.new_story_status_content_description, userName);
                case 4:
                    return this.mContext.getString(R$string.audio_status);
                case 5:
                    return this.mContext.getString(R$string.video_status);
                case 6:
                    return this.mContext.getString(R$string.game_status);
                case 7:
                    return this.mContext.getString(R$string.location_status_content_description, userName);
                case 8:
                    return this.mContext.getString(R$string.upcoming_birthday_status_content_description, userName);
                default:
                    return "";
            }
        }
        return conversationStatus.getDescription();
    }

    public final int getContentHeightForLayout(int i, boolean z) {
        int i2 = this.mLayoutSize;
        if (i2 != 1) {
            if (i2 != 2) {
                return -1;
            }
            return this.mHeight - ((getSizeInDp(R$dimen.max_people_avatar_size_for_large_content) + i) + (z ? 76 : 62));
        }
        return this.mHeight - ((i + 12) + (this.mMediumVerticalPadding * 2));
    }

    @VisibleForTesting
    public CharSequence getDoubleEmoji(CharSequence charSequence) {
        Matcher matcher = EMOJI_PATTERN.matcher(charSequence);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            arrayList.add(new Pair(Integer.valueOf(start), Integer.valueOf(end)));
            arrayList2.add(charSequence.subSequence(start, end));
        }
        if (arrayList.size() < 2) {
            return null;
        }
        for (int i = 1; i < arrayList.size(); i++) {
            Pair pair = (Pair) arrayList.get(i);
            int i2 = i - 1;
            if (pair.first == ((Pair) arrayList.get(i2)).second && Objects.equals(arrayList2.get(i), arrayList2.get(i2))) {
                return (CharSequence) arrayList2.get(i);
            }
        }
        return null;
    }

    @VisibleForTesting
    public CharSequence getDoublePunctuation(CharSequence charSequence) {
        if (ANY_DOUBLE_MARK_PATTERN.matcher(charSequence).find()) {
            if (MIXED_MARK_PATTERN.matcher(charSequence).find()) {
                return "!?";
            }
            Matcher matcher = DOUBLE_QUESTION_PATTERN.matcher(charSequence);
            if (matcher.find()) {
                Matcher matcher2 = DOUBLE_EXCLAMATION_PATTERN.matcher(charSequence);
                return (matcher2.find() && matcher.start() >= matcher2.start()) ? "!" : "?";
            }
            return "!";
        }
        return null;
    }

    public final int getDrawableForStatus(ConversationStatus conversationStatus) {
        switch (conversationStatus.getActivity()) {
            case 1:
                return R$drawable.ic_cake;
            case 2:
                return R$drawable.ic_celebration;
            case 3:
                return R$drawable.ic_pages;
            case 4:
                return R$drawable.ic_music_note;
            case 5:
                return R$drawable.ic_video;
            case 6:
                return R$drawable.ic_play_games;
            case 7:
                return R$drawable.ic_location;
            case 8:
                return R$drawable.ic_gift;
            default:
                return R$drawable.ic_person;
        }
    }

    public final int getEmptyLayout() {
        int i = this.mLayoutSize;
        return i != 1 ? i != 2 ? getLayoutSmallByHeight() : R$layout.people_tile_large_empty : R$layout.people_tile_medium_empty;
    }

    public final int getLayoutForContent() {
        int i = this.mLayoutSize;
        return i != 1 ? i != 2 ? getLayoutSmallByHeight() : R$layout.people_tile_large_with_status_content : R$layout.people_tile_medium_with_content;
    }

    public final int getLayoutForNotificationContent() {
        int i = this.mLayoutSize;
        return i != 1 ? i != 2 ? getLayoutSmallByHeight() : R$layout.people_tile_large_with_notification_content : R$layout.people_tile_medium_with_content;
    }

    public final int getLayoutSize() {
        if (this.mHeight < getSizeInDp(R$dimen.required_height_for_large) || this.mWidth < getSizeInDp(R$dimen.required_width_for_large)) {
            if (this.mHeight < getSizeInDp(R$dimen.required_height_for_medium) || this.mWidth < getSizeInDp(R$dimen.required_width_for_medium)) {
                return 0;
            }
            this.mMediumVerticalPadding = Math.max(4, Math.min(Math.floorDiv(this.mHeight - ((getSizeInDp(R$dimen.avatar_size_for_medium) + 4) + getLineHeightFromResource(R$dimen.name_text_size_for_medium_content)), 2), 16));
            return 1;
        }
        return 2;
    }

    public final int getLayoutSmallByHeight() {
        return this.mHeight >= getSizeInDp(R$dimen.required_height_for_medium) ? R$layout.people_tile_small : R$layout.people_tile_small_horizontal;
    }

    public final int getLineHeightFromResource(int i) {
        try {
            TextView textView = new TextView(this.mContext);
            textView.setTextSize(0, this.mContext.getResources().getDimension(i));
            textView.setTextAppearance(16974253);
            return (int) (textView.getLineHeight() / this.mDensity);
        } catch (Exception e) {
            Log.e("PeopleTileView", "Could not create text view: " + e);
            return getSizeInDp(R$dimen.content_text_size_for_medium);
        }
    }

    public final int getMaxAvatarSize(RemoteViews remoteViews) {
        int layoutId = remoteViews.getLayoutId();
        int i = R$dimen.avatar_size_for_medium;
        int sizeInDp = getSizeInDp(i);
        if (layoutId == R$layout.people_tile_medium_empty) {
            return getSizeInDp(R$dimen.max_people_avatar_size_for_large_content);
        }
        if (layoutId == R$layout.people_tile_medium_with_content) {
            return getSizeInDp(i);
        }
        if (layoutId == R$layout.people_tile_small) {
            sizeInDp = Math.min(this.mHeight - (Math.max(18, getLineHeightFromResource(R$dimen.name_text_size_for_small)) + 18), this.mWidth - 8);
        }
        if (layoutId == R$layout.people_tile_small_horizontal) {
            sizeInDp = Math.min(this.mHeight - 10, this.mWidth - 16);
        }
        if (layoutId == R$layout.people_tile_large_with_notification_content) {
            return Math.min(this.mHeight - ((getLineHeightFromResource(R$dimen.content_text_size_for_large) * 3) + 62), getSizeInDp(R$dimen.max_people_avatar_size_for_large_content));
        }
        if (layoutId == R$layout.people_tile_large_with_status_content) {
            return Math.min(this.mHeight - ((getLineHeightFromResource(R$dimen.content_text_size_for_large) * 3) + 76), getSizeInDp(R$dimen.max_people_avatar_size_for_large_content));
        }
        if (layoutId == R$layout.people_tile_large_empty) {
            sizeInDp = Math.min(this.mHeight - (((((getLineHeightFromResource(R$dimen.name_text_size_for_large) + 28) + getLineHeightFromResource(R$dimen.content_text_size_for_large)) + 16) + 10) + 16), this.mWidth - 28);
        }
        int i2 = sizeInDp;
        if (isDndBlockingTileData(this.mTile)) {
            i2 = sizeInDp;
            if (this.mLayoutSize != 0) {
                i2 = createDndRemoteViews().mAvatarSize;
            }
        }
        return Math.min(i2, getSizeInDp(R$dimen.max_people_avatar_size));
    }

    public final String getMessagesCountText(int i) {
        if (i >= 6) {
            return this.mContext.getResources().getString(R$string.messages_count_overflow_indicator, 6);
        }
        Locale locale = this.mContext.getResources().getConfiguration().getLocales().get(0);
        if (!locale.equals(this.mLocale)) {
            this.mLocale = locale;
            this.mIntegerFormat = NumberFormat.getIntegerInstance(locale);
        }
        return this.mIntegerFormat.format(i);
    }

    public final int getSizeInDp(int i) {
        return getSizeInDp(this.mContext, i, this.mDensity);
    }

    public final String getStatusTextByType(int i) {
        switch (i) {
            case 1:
                return this.mContext.getString(R$string.birthday_status);
            case 2:
                return this.mContext.getString(R$string.anniversary_status);
            case 3:
                return this.mContext.getString(R$string.new_story_status);
            case 4:
                return this.mContext.getString(R$string.audio_status);
            case 5:
                return this.mContext.getString(R$string.video_status);
            case 6:
                return this.mContext.getString(R$string.game_status);
            case 7:
                return this.mContext.getString(R$string.location_status);
            case 8:
                return this.mContext.getString(R$string.upcoming_birthday_status);
            default:
                return "";
        }
    }

    public final int getViewForDndRemoteViews() {
        int i = this.mLayoutSize;
        return i != 1 ? i != 2 ? getLayoutSmallByHeight() : R$layout.people_tile_with_suppression_detail_content_vertical : R$layout.people_tile_with_suppression_detail_content_horizontal;
    }

    public final RemoteViews getViewForTile() {
        PeopleSpaceTile peopleSpaceTile = this.mTile;
        if (peopleSpaceTile == null || peopleSpaceTile.isPackageSuspended() || this.mTile.isUserQuieted()) {
            return createSuppressedView();
        }
        if (isDndBlockingTileData(this.mTile)) {
            return createDndRemoteViews().mRemoteViews;
        }
        if (Objects.equals(this.mTile.getNotificationCategory(), "missed_call")) {
            return createMissedCallRemoteViews();
        }
        if (this.mTile.getNotificationKey() != null) {
            return createNotificationRemoteViews();
        }
        List<ConversationStatus> asList = this.mTile.getStatuses() == null ? Arrays.asList(new ConversationStatus[0]) : (List) this.mTile.getStatuses().stream().filter(new Predicate() { // from class: com.android.systemui.people.PeopleTileViewHelper$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PeopleTileViewHelper.m3542$r8$lambda$ZEemqe9wBIF2mxVqyu89jspVxw(PeopleTileViewHelper.this, (ConversationStatus) obj);
            }
        }).collect(Collectors.toList());
        ConversationStatus birthdayStatus = getBirthdayStatus(asList);
        return birthdayStatus != null ? createStatusRemoteViews(birthdayStatus) : !asList.isEmpty() ? createStatusRemoteViews(asList.stream().max(Comparator.comparing(new Function() { // from class: com.android.systemui.people.PeopleTileViewHelper$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return PeopleTileViewHelper.m3544$r8$lambda$jk9uDwWMD6EvmamOsyJpSEGT7g((ConversationStatus) obj);
            }
        })).get()) : createLastInteractionRemoteViews();
    }

    @VisibleForTesting
    public RemoteViews getViews() {
        RemoteViews viewForTile = getViewForTile();
        return setLaunchIntents(setCommonRemoteViewsFields(viewForTile, getMaxAvatarSize(viewForTile)));
    }

    /* renamed from: isStatusValidForEntireStatusView */
    public final boolean lambda$getViewForTile$1(ConversationStatus conversationStatus) {
        int activity = conversationStatus.getActivity();
        boolean z = true;
        if (activity != 1) {
            z = true;
            if (activity != 2) {
                z = true;
                if (TextUtils.isEmpty(conversationStatus.getDescription())) {
                    z = conversationStatus.getIcon() != null;
                }
            }
        }
        return z;
    }

    /* renamed from: onHeaderDecoded */
    public final void lambda$resolveImage$5(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        int applyDimension = (int) TypedValue.applyDimension(1, this.mWidth, this.mContext.getResources().getDisplayMetrics());
        int applyDimension2 = (int) TypedValue.applyDimension(1, this.mHeight, this.mContext.getResources().getDisplayMetrics());
        int max = Math.max(applyDimension, applyDimension2);
        int min = (int) (Math.min(applyDimension, applyDimension2) * 1.5d);
        int i = max;
        if (min < max) {
            i = min;
        }
        Size size = imageInfo.getSize();
        int max2 = Math.max(size.getHeight(), size.getWidth());
        imageDecoder.setTargetSampleSize(getPowerOfTwoForSampleRatio(max2 > i ? (max2 * 1.0f) / i : 1.0d));
    }

    public final int pxToDp(float f) {
        return (int) (f / this.mDensity);
    }

    public Drawable resolveImage(Uri uri, Context context) throws IOException {
        return ImageDecoder.decodeDrawable(ImageDecoder.createSource(context.getContentResolver(), uri), new ImageDecoder.OnHeaderDecodedListener() { // from class: com.android.systemui.people.PeopleTileViewHelper$$ExternalSyntheticLambda6
            @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
            public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                PeopleTileViewHelper.m3543$r8$lambda$e0vytaytLwxzn2IGXwdLCpNByk(PeopleTileViewHelper.this, imageDecoder, imageInfo, source);
            }
        });
    }

    public final void setAvailabilityDotPadding(RemoteViews remoteViews, int i) {
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(i);
        int dimensionPixelSize2 = this.mContext.getResources().getDimensionPixelSize(R$dimen.medium_content_padding_above_name);
        int i2 = R$id.medium_content;
        boolean z = this.mIsLeftToRight;
        int i3 = z ? dimensionPixelSize : 0;
        if (z) {
            dimensionPixelSize = 0;
        }
        remoteViews.setViewPadding(i2, i3, 0, dimensionPixelSize, dimensionPixelSize2);
    }

    public final RemoteViews setCommonRemoteViewsFields(RemoteViews remoteViews, int i) {
        int dimensionPixelSize;
        try {
            PeopleSpaceTile peopleSpaceTile = this.mTile;
            if (peopleSpaceTile == null) {
                return remoteViews;
            }
            if (peopleSpaceTile.getStatuses() != null && this.mTile.getStatuses().stream().anyMatch(new Predicate() { // from class: com.android.systemui.people.PeopleTileViewHelper$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return PeopleTileViewHelper.m3541$r8$lambda$JZyTDHh4xSyzClZIoEMVG41tqY((ConversationStatus) obj);
                }
            })) {
                int i2 = R$id.availability;
                remoteViews.setViewVisibility(i2, 0);
                dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R$dimen.availability_dot_shown_padding);
                remoteViews.setContentDescription(i2, this.mContext.getString(R$string.person_available));
            } else {
                remoteViews.setViewVisibility(R$id.availability, 8);
                dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R$dimen.availability_dot_missing_padding);
            }
            boolean z = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 0;
            int i3 = R$id.padding_before_availability;
            int i4 = z ? dimensionPixelSize : 0;
            if (z) {
                dimensionPixelSize = 0;
            }
            remoteViews.setViewPadding(i3, i4, 0, dimensionPixelSize, 0);
            boolean hasNewStory = getHasNewStory(this.mTile);
            int i5 = R$id.person_icon;
            remoteViews.setImageViewBitmap(i5, getPersonIconBitmap(this.mContext, this.mTile, i, hasNewStory));
            if (hasNewStory) {
                remoteViews.setContentDescription(i5, this.mContext.getString(R$string.new_story_status_content_description, this.mTile.getUserName()));
            } else {
                remoteViews.setContentDescription(i5, null);
            }
            return remoteViews;
        } catch (Exception e) {
            Log.e("PeopleTileView", "Failed to set common fields: " + e);
            return remoteViews;
        }
    }

    public final void setContentDescriptionForNotificationTextContent(RemoteViews remoteViews, CharSequence charSequence, CharSequence charSequence2) {
        remoteViews.setContentDescription(this.mLayoutSize == 0 ? R$id.predefined_icon : R$id.text_content, this.mContext.getString(R$string.new_notification_text_content_description, charSequence2, charSequence));
    }

    public final RemoteViews setEmojiBackground(RemoteViews remoteViews, CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            remoteViews.setViewVisibility(R$id.emojis, 8);
            return remoteViews;
        }
        remoteViews.setTextViewText(R$id.emoji1, charSequence);
        remoteViews.setTextViewText(R$id.emoji2, charSequence);
        remoteViews.setTextViewText(R$id.emoji3, charSequence);
        remoteViews.setViewVisibility(R$id.emojis, 0);
        return remoteViews;
    }

    public final RemoteViews setLaunchIntents(RemoteViews remoteViews) {
        if (PeopleTileKey.isValid(this.mKey) && this.mTile != null) {
            try {
                Intent intent = new Intent(this.mContext, LaunchConversationActivity.class);
                intent.addFlags(1350598656);
                intent.putExtra("extra_tile_id", this.mKey.getShortcutId());
                intent.putExtra("extra_package_name", this.mKey.getPackageName());
                intent.putExtra("extra_user_handle", new UserHandle(this.mKey.getUserId()));
                PeopleSpaceTile peopleSpaceTile = this.mTile;
                if (peopleSpaceTile != null) {
                    intent.putExtra("extra_notification_key", peopleSpaceTile.getNotificationKey());
                }
                remoteViews.setOnClickPendingIntent(16908288, PendingIntent.getActivity(this.mContext, this.mAppWidgetId, intent, 167772160));
                return remoteViews;
            } catch (Exception e) {
                Log.e("PeopleTileView", "Failed to add launch intents: " + e);
            }
        }
        return remoteViews;
    }

    public final void setMaxLines(RemoteViews remoteViews, boolean z) {
        int i;
        int lineHeightFromResource;
        if (this.mLayoutSize == 2) {
            i = R$dimen.content_text_size_for_large;
            lineHeightFromResource = getLineHeightFromResource(R$dimen.name_text_size_for_large_content);
        } else {
            i = R$dimen.content_text_size_for_medium;
            lineHeightFromResource = getLineHeightFromResource(R$dimen.name_text_size_for_medium_content);
        }
        int max = Math.max(2, Math.floorDiv(getContentHeightForLayout(lineHeightFromResource, remoteViews.getLayoutId() == R$layout.people_tile_large_with_status_content), getLineHeightFromResource(i)));
        int i2 = max;
        if (z) {
            i2 = max - 1;
        }
        remoteViews.setInt(R$id.text_content, "setMaxLines", i2);
    }

    public final void setPredefinedIconVisible(RemoteViews remoteViews) {
        remoteViews.setViewVisibility(R$id.predefined_icon, 0);
        if (this.mLayoutSize == 1) {
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R$dimen.before_predefined_icon_padding);
            int i = R$id.name;
            boolean z = this.mIsLeftToRight;
            int i2 = z ? 0 : dimensionPixelSize;
            if (!z) {
                dimensionPixelSize = 0;
            }
            remoteViews.setViewPadding(i, i2, 0, dimensionPixelSize, 0);
        }
    }

    public final RemoteViews setPunctuationBackground(RemoteViews remoteViews, CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            remoteViews.setViewVisibility(R$id.punctuations, 8);
            return remoteViews;
        }
        remoteViews.setTextViewText(R$id.punctuation1, charSequence);
        remoteViews.setTextViewText(R$id.punctuation2, charSequence);
        remoteViews.setTextViewText(R$id.punctuation3, charSequence);
        remoteViews.setTextViewText(R$id.punctuation4, charSequence);
        remoteViews.setTextViewText(R$id.punctuation5, charSequence);
        remoteViews.setTextViewText(R$id.punctuation6, charSequence);
        remoteViews.setViewVisibility(R$id.punctuations, 0);
        return remoteViews;
    }

    public final RemoteViews setViewForContentLayout(RemoteViews remoteViews) {
        RemoteViews decorateBackground = decorateBackground(remoteViews, "");
        int i = R$id.predefined_icon;
        decorateBackground.setContentDescription(i, null);
        int i2 = R$id.text_content;
        decorateBackground.setContentDescription(i2, null);
        int i3 = R$id.name;
        decorateBackground.setContentDescription(i3, null);
        int i4 = R$id.image;
        decorateBackground.setContentDescription(i4, null);
        decorateBackground.setAccessibilityTraversalAfter(i2, i3);
        if (this.mLayoutSize == 0) {
            decorateBackground.setViewVisibility(i, 0);
            decorateBackground.setViewVisibility(i3, 8);
        } else {
            decorateBackground.setViewVisibility(i, 8);
            decorateBackground.setViewVisibility(i3, 0);
            decorateBackground.setViewVisibility(i2, 0);
            decorateBackground.setViewVisibility(R$id.subtext, 8);
            decorateBackground.setViewVisibility(i4, 8);
            decorateBackground.setViewVisibility(R$id.scrim_layout, 8);
        }
        if (this.mLayoutSize == 1) {
            int floor = (int) Math.floor(this.mDensity * 16.0f);
            int floor2 = (int) Math.floor(this.mMediumVerticalPadding * this.mDensity);
            decorateBackground.setViewPadding(R$id.content, floor, floor2, floor, floor2);
            decorateBackground.setViewPadding(i3, 0, 0, 0, 0);
            if (this.mHeight > ((int) (this.mContext.getResources().getDimension(R$dimen.medium_height_for_max_name_text_size) / this.mDensity))) {
                decorateBackground.setTextViewTextSize(i3, 0, (int) this.mContext.getResources().getDimension(R$dimen.max_name_text_size_for_medium));
            }
        }
        if (this.mLayoutSize == 2) {
            decorateBackground.setViewPadding(i3, 0, 0, 0, this.mContext.getResources().getDimensionPixelSize(R$dimen.below_name_text_padding));
            decorateBackground.setInt(R$id.content, "setGravity", 48);
        }
        int i5 = R$dimen.regular_predefined_icon;
        decorateBackground.setViewLayoutHeightDimen(i, i5);
        decorateBackground.setViewLayoutWidthDimen(i, i5);
        decorateBackground.setViewVisibility(R$id.messages_count, 8);
        if (this.mTile.getUserName() != null) {
            decorateBackground.setTextViewText(i3, this.mTile.getUserName());
        }
        return decorateBackground;
    }
}