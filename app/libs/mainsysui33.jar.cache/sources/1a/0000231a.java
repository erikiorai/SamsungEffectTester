package com.android.systemui.qs.tiles;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Trace;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.drawable.CircleFramedDrawable;
import com.android.systemui.R$color;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.qs.PseudoGridView;
import com.android.systemui.qs.QSUserSwitcherEvent;
import com.android.systemui.qs.tiles.UserDetailView;
import com.android.systemui.qs.user.UserSwitchDialogController;
import com.android.systemui.statusbar.policy.BaseUserSwitcherAdapter;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.user.data.source.UserRecord;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/UserDetailView.class */
public class UserDetailView extends PseudoGridView {

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/UserDetailView$Adapter.class */
    public static class Adapter extends BaseUserSwitcherAdapter implements View.OnClickListener {
        public final Context mContext;
        public UserSwitcherController mController;
        public View mCurrentUserView;
        public UserSwitchDialogController.DialogShower mDialogShower;
        public final FalsingManager mFalsingManager;
        public final UiEventLogger mUiEventLogger;

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.UserDetailView$Adapter$$ExternalSyntheticLambda0.test(java.lang.Object):boolean] */
        public static /* synthetic */ boolean $r8$lambda$2Eq8D_GiFSi2ZIVdEYiby9HPnqo(UserRecord userRecord) {
            return lambda$getUsers$0(userRecord);
        }

        public Adapter(Context context, UserSwitcherController userSwitcherController, UiEventLogger uiEventLogger, FalsingManager falsingManager) {
            super(userSwitcherController);
            this.mContext = context;
            this.mController = userSwitcherController;
            this.mUiEventLogger = uiEventLogger;
            this.mFalsingManager = falsingManager;
        }

        public static Drawable getDrawable(Context context, UserRecord userRecord) {
            Drawable iconDrawable = BaseUserSwitcherAdapter.getIconDrawable(context, userRecord);
            iconDrawable.setTint(context.getResources().getColor(userRecord.isCurrent ? R$color.qs_user_switcher_selected_avatar_icon_color : !userRecord.isSwitchToEnabled ? R$color.GM2_grey_600 : R$color.qs_user_switcher_avatar_icon_color, context.getTheme()));
            return new LayerDrawable(new Drawable[]{context.getDrawable(userRecord.isCurrent ? R$drawable.bg_avatar_selected : R$drawable.qs_bg_avatar), iconDrawable});
        }

        public static /* synthetic */ boolean lambda$getUsers$0(UserRecord userRecord) {
            return !userRecord.isManageUsers;
        }

        public UserDetailItemView createUserDetailItemView(View view, ViewGroup viewGroup, UserRecord userRecord) {
            UserDetailItemView convertOrInflate = UserDetailItemView.convertOrInflate(viewGroup.getContext(), view, viewGroup);
            ColorFilter colorFilter = null;
            if (!userRecord.isCurrent || userRecord.isGuest) {
                convertOrInflate.setOnClickListener(this);
            } else {
                convertOrInflate.setOnClickListener(null);
                convertOrInflate.setClickable(false);
            }
            String name = getName(this.mContext, userRecord);
            if (userRecord.picture == null) {
                convertOrInflate.bind(name, getDrawable(this.mContext, userRecord).mutate(), userRecord.resolveId());
            } else {
                CircleFramedDrawable circleFramedDrawable = new CircleFramedDrawable(userRecord.picture, (int) this.mContext.getResources().getDimension(R$dimen.qs_framed_avatar_size));
                if (!userRecord.isSwitchToEnabled) {
                    colorFilter = BaseUserSwitcherAdapter.getDisabledUserAvatarColorFilter();
                }
                circleFramedDrawable.setColorFilter(colorFilter);
                convertOrInflate.bind(name, circleFramedDrawable, userRecord.info.id);
            }
            convertOrInflate.setActivated(userRecord.isCurrent);
            convertOrInflate.setDisabledByAdmin(userRecord.isDisabledByAdmin());
            convertOrInflate.setEnabled(userRecord.isSwitchToEnabled);
            UserSwitcherController.setSelectableAlpha(convertOrInflate);
            if (userRecord.isCurrent) {
                this.mCurrentUserView = convertOrInflate;
            }
            convertOrInflate.setTag(userRecord);
            return convertOrInflate;
        }

        public List<UserRecord> getUsers() {
            return (List) super.getUsers().stream().filter(new Predicate() { // from class: com.android.systemui.qs.tiles.UserDetailView$Adapter$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return UserDetailView.Adapter.$r8$lambda$2Eq8D_GiFSi2ZIVdEYiby9HPnqo((UserRecord) obj);
                }
            }).collect(Collectors.toList());
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            return createUserDetailItemView(view, viewGroup, getItem(i));
        }

        public void injectDialogShower(UserSwitchDialogController.DialogShower dialogShower) {
            this.mDialogShower = dialogShower;
        }

        /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: com.android.systemui.qs.tiles.UserDetailView$Adapter */
        /* JADX WARN: Multi-variable type inference failed */
        public void linkToViewGroup(ViewGroup viewGroup) {
            PseudoGridView.ViewGroupAdapterBridge.link(viewGroup, this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (this.mFalsingManager.isFalseTap(1)) {
                return;
            }
            Trace.beginSection("UserDetailView.Adapter#onClick");
            UserRecord userRecord = (UserRecord) view.getTag();
            if (userRecord.isDisabledByAdmin()) {
                this.mController.startActivity(RestrictedLockUtils.getShowAdminSupportDetailsIntent(this.mContext, userRecord.enforcedAdmin));
            } else if (userRecord.isSwitchToEnabled) {
                MetricsLogger.action(this.mContext, 156);
                this.mUiEventLogger.log(QSUserSwitcherEvent.QS_USER_SWITCH);
                if (!userRecord.isAddUser && !userRecord.isRestricted && !userRecord.isDisabledByAdmin()) {
                    View view2 = this.mCurrentUserView;
                    if (view2 != null) {
                        view2.setActivated(false);
                    }
                    view.setActivated(true);
                }
                onUserListItemClicked(userRecord, this.mDialogShower);
            }
            Trace.endSection();
        }

        public void onUserListItemClicked(UserRecord userRecord, UserSwitchDialogController.DialogShower dialogShower) {
            if (dialogShower != null) {
                this.mDialogShower.dismiss();
            }
            super.onUserListItemClicked(userRecord, dialogShower);
        }
    }

    public UserDetailView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}