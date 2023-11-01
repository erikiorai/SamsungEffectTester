package com.android.settingslib.users;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.util.UserIcons;
import com.android.settingslib.R$array;
import com.android.settingslib.R$drawable;
import com.android.settingslib.R$id;
import com.android.settingslib.R$integer;
import com.android.settingslib.R$layout;
import com.android.settingslib.R$string;
import com.android.settingslib.R$style;
import com.android.settingslib.users.AvatarPhotoController;
import com.android.settingslib.users.AvatarPickerActivity;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupdesign.util.ThemeHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/settingslib/users/AvatarPickerActivity.class */
public class AvatarPickerActivity extends Activity {
    public AvatarAdapter mAdapter;
    public AvatarPhotoController mAvatarPhotoController;
    public FooterButton mDoneButton;
    public boolean mWaitingForActivityResult;

    /* loaded from: mainsysui33.jar:com/android/settingslib/users/AvatarPickerActivity$AvatarAdapter.class */
    public class AvatarAdapter extends RecyclerView.Adapter<AvatarViewHolder> {
        public final int mChoosePhotoPosition;
        public final List<String> mImageDescriptions;
        public final List<Drawable> mImageDrawables;
        public final int mPreselectedImageStartPosition;
        public final TypedArray mPreselectedImages;
        public int mSelectedPosition = -1;
        public final int mTakePhotoPosition;
        public final int[] mUserIconColors;

        /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.users.AvatarPickerActivity$AvatarAdapter$$ExternalSyntheticLambda0.onClick(android.view.View):void] */
        public static /* synthetic */ void $r8$lambda$7plfyuJoZuOmuS4qLxBUOxD0S5I(AvatarAdapter avatarAdapter, View view) {
            avatarAdapter.lambda$onBindViewHolder$0(view);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.users.AvatarPickerActivity$AvatarAdapter$$ExternalSyntheticLambda2.onClick(android.view.View):void] */
        public static /* synthetic */ void $r8$lambda$8SqaUeoMVEDr8qBXE_l1QOf0JeY(AvatarAdapter avatarAdapter, int i, View view) {
            avatarAdapter.lambda$onBindViewHolder$2(i, view);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.users.AvatarPickerActivity$AvatarAdapter$$ExternalSyntheticLambda1.onClick(android.view.View):void] */
        public static /* synthetic */ void $r8$lambda$lYGInc2EFFkmYgShw5vVASCyH88(AvatarAdapter avatarAdapter, View view) {
            avatarAdapter.lambda$onBindViewHolder$1(view);
        }

        public AvatarAdapter() {
            AvatarPickerActivity.this = r5;
            boolean canTakePhoto = PhotoCapabilityUtils.canTakePhoto(r5);
            boolean canChoosePhoto = PhotoCapabilityUtils.canChoosePhoto(r5);
            this.mTakePhotoPosition = canTakePhoto ? 0 : -1;
            this.mChoosePhotoPosition = canChoosePhoto ? canTakePhoto ? 1 : 0 : -1;
            this.mPreselectedImageStartPosition = (canTakePhoto ? 1 : 0) + (canChoosePhoto ? 1 : 0);
            this.mPreselectedImages = r5.getResources().obtainTypedArray(R$array.avatar_images);
            this.mUserIconColors = UserIcons.getUserIconColors(r5.getResources());
            this.mImageDrawables = buildDrawableList();
            this.mImageDescriptions = buildDescriptionsList();
        }

        public /* synthetic */ void lambda$onBindViewHolder$0(View view) {
            AvatarPickerActivity.this.mAvatarPhotoController.takePhoto();
        }

        public /* synthetic */ void lambda$onBindViewHolder$1(View view) {
            AvatarPickerActivity.this.mAvatarPhotoController.choosePhoto();
        }

        public /* synthetic */ void lambda$onBindViewHolder$2(int i, View view) {
            if (this.mSelectedPosition == i) {
                deselect(i);
            } else {
                select(i);
            }
        }

        public final List<String> buildDescriptionsList() {
            if (this.mPreselectedImages.length() > 0) {
                return Arrays.asList(AvatarPickerActivity.this.getResources().getStringArray(R$array.avatar_image_descriptions));
            }
            return null;
        }

        public final List<Drawable> buildDrawableList() {
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < this.mPreselectedImages.length(); i++) {
                Drawable drawable = this.mPreselectedImages.getDrawable(i);
                if (!(drawable instanceof BitmapDrawable)) {
                    throw new IllegalStateException("Avatar drawables must be bitmaps");
                }
                arrayList.add(circularDrawableFrom((BitmapDrawable) drawable));
            }
            if (arrayList.isEmpty()) {
                for (int i2 = 0; i2 < this.mUserIconColors.length; i2++) {
                    arrayList.add(UserIcons.getDefaultUserIconInColor(AvatarPickerActivity.this.getResources(), this.mUserIconColors[i2]));
                }
                return arrayList;
            }
            return arrayList;
        }

        public final Drawable circularDrawableFrom(BitmapDrawable bitmapDrawable) {
            RoundedBitmapDrawable create = RoundedBitmapDrawableFactory.create(AvatarPickerActivity.this.getResources(), bitmapDrawable.getBitmap());
            create.setCircular(true);
            return create;
        }

        public final void deselect(int i) {
            this.mSelectedPosition = -1;
            notifyItemChanged(i);
            AvatarPickerActivity.this.mDoneButton.setEnabled(false);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.mPreselectedImageStartPosition + this.mImageDrawables.size();
        }

        public final int indexFromPosition(int i) {
            return i - this.mPreselectedImageStartPosition;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(AvatarViewHolder avatarViewHolder, final int i) {
            if (i == this.mTakePhotoPosition) {
                avatarViewHolder.setDrawable(AvatarPickerActivity.this.getDrawable(R$drawable.avatar_take_photo_circled));
                avatarViewHolder.setContentDescription(AvatarPickerActivity.this.getString(R$string.user_image_take_photo));
                avatarViewHolder.setClickListener(new View.OnClickListener() { // from class: com.android.settingslib.users.AvatarPickerActivity$AvatarAdapter$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        AvatarPickerActivity.AvatarAdapter.$r8$lambda$7plfyuJoZuOmuS4qLxBUOxD0S5I(AvatarPickerActivity.AvatarAdapter.this, view);
                    }
                });
            } else if (i == this.mChoosePhotoPosition) {
                avatarViewHolder.setDrawable(AvatarPickerActivity.this.getDrawable(R$drawable.avatar_choose_photo_circled));
                avatarViewHolder.setContentDescription(AvatarPickerActivity.this.getString(R$string.user_image_choose_photo));
                avatarViewHolder.setClickListener(new View.OnClickListener() { // from class: com.android.settingslib.users.AvatarPickerActivity$AvatarAdapter$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        AvatarPickerActivity.AvatarAdapter.$r8$lambda$lYGInc2EFFkmYgShw5vVASCyH88(AvatarPickerActivity.AvatarAdapter.this, view);
                    }
                });
            } else if (i >= this.mPreselectedImageStartPosition) {
                int indexFromPosition = indexFromPosition(i);
                avatarViewHolder.setSelected(i == this.mSelectedPosition);
                avatarViewHolder.setDrawable(this.mImageDrawables.get(indexFromPosition));
                List<String> list = this.mImageDescriptions;
                if (list != null) {
                    avatarViewHolder.setContentDescription(list.get(indexFromPosition));
                } else {
                    avatarViewHolder.setContentDescription(AvatarPickerActivity.this.getString(R$string.default_user_icon_description));
                }
                avatarViewHolder.setClickListener(new View.OnClickListener() { // from class: com.android.settingslib.users.AvatarPickerActivity$AvatarAdapter$$ExternalSyntheticLambda2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        AvatarPickerActivity.AvatarAdapter.$r8$lambda$8SqaUeoMVEDr8qBXE_l1QOf0JeY(AvatarPickerActivity.AvatarAdapter.this, i, view);
                    }
                });
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public AvatarViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new AvatarViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R$layout.avatar_item, viewGroup, false));
        }

        public final void returnSelectionResult() {
            int indexFromPosition = indexFromPosition(this.mSelectedPosition);
            if (this.mPreselectedImages.length() <= 0) {
                AvatarPickerActivity.this.returnColorResult(this.mUserIconColors[indexFromPosition]);
                return;
            }
            int resourceId = this.mPreselectedImages.getResourceId(indexFromPosition, -1);
            if (resourceId == -1) {
                throw new IllegalStateException("Preselected avatar images must be resources.");
            }
            AvatarPickerActivity.this.returnUriResult(uriForResourceId(resourceId));
        }

        public final void select(int i) {
            int i2 = this.mSelectedPosition;
            this.mSelectedPosition = i;
            notifyItemChanged(i);
            if (i2 != -1) {
                notifyItemChanged(i2);
            } else {
                AvatarPickerActivity.this.mDoneButton.setEnabled(true);
            }
        }

        public final Uri uriForResourceId(int i) {
            return new Uri.Builder().scheme("android.resource").authority(AvatarPickerActivity.this.getResources().getResourcePackageName(i)).appendPath(AvatarPickerActivity.this.getResources().getResourceTypeName(i)).appendPath(AvatarPickerActivity.this.getResources().getResourceEntryName(i)).build();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/settingslib/users/AvatarPickerActivity$AvatarViewHolder.class */
    public static class AvatarViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mImageView;

        public AvatarViewHolder(View view) {
            super(view);
            this.mImageView = (ImageView) view.findViewById(R$id.avatar_image);
        }

        public void setClickListener(View.OnClickListener onClickListener) {
            this.mImageView.setOnClickListener(onClickListener);
        }

        public void setContentDescription(String str) {
            this.mImageView.setContentDescription(str);
        }

        public void setDrawable(Drawable drawable) {
            this.mImageView.setImageDrawable(drawable);
        }

        public void setSelected(boolean z) {
            this.mImageView.setSelected(z);
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.users.AvatarPickerActivity$$ExternalSyntheticLambda1.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$FZS4gdvVJXeA-iNLtPY01ZUyHE8 */
    public static /* synthetic */ void m1140$r8$lambda$FZS4gdvVJXeAiNLtPY01ZUyHE8(AvatarPickerActivity avatarPickerActivity, View view) {
        avatarPickerActivity.lambda$setUpButtons$1(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.users.AvatarPickerActivity$$ExternalSyntheticLambda0.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$OKEMNwKmHcLuxgywUDcZx8bzIRA(AvatarPickerActivity avatarPickerActivity, View view) {
        avatarPickerActivity.lambda$setUpButtons$0(view);
    }

    public /* synthetic */ void lambda$setUpButtons$0(View view) {
        cancel();
    }

    public /* synthetic */ void lambda$setUpButtons$1(View view) {
        this.mAdapter.returnSelectionResult();
    }

    public final void cancel() {
        setResult(0);
        finish();
    }

    public final String getFileAuthority() {
        String stringExtra = getIntent().getStringExtra("file_authority");
        if (stringExtra != null) {
            return stringExtra;
        }
        throw new IllegalStateException("File authority must be provided");
    }

    @Override // android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        this.mWaitingForActivityResult = false;
        this.mAvatarPhotoController.onActivityResult(i, i2, intent);
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setTheme(R$style.SudThemeGlifV3_DayNight);
        ThemeHelper.trySetDynamicColor(this);
        setContentView(R$layout.avatar_picker);
        setUpButtons();
        RecyclerView recyclerView = (RecyclerView) findViewById(R$id.avatar_grid);
        AvatarAdapter avatarAdapter = new AvatarAdapter();
        this.mAdapter = avatarAdapter;
        recyclerView.setAdapter(avatarAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R$integer.avatar_picker_columns)));
        restoreState(bundle);
        this.mAvatarPhotoController = new AvatarPhotoController(new AvatarPhotoController.AvatarUiImpl(this), new AvatarPhotoController.ContextInjectorImpl(this, getFileAuthority()), this.mWaitingForActivityResult);
    }

    @Override // android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putBoolean("awaiting_result", this.mWaitingForActivityResult);
        bundle.putInt("selected_position", this.mAdapter.mSelectedPosition);
        super.onSaveInstanceState(bundle);
    }

    public final void restoreState(Bundle bundle) {
        if (bundle != null) {
            boolean z = false;
            this.mWaitingForActivityResult = bundle.getBoolean("awaiting_result", false);
            this.mAdapter.mSelectedPosition = bundle.getInt("selected_position", -1);
            FooterButton footerButton = this.mDoneButton;
            if (this.mAdapter.mSelectedPosition != -1) {
                z = true;
            }
            footerButton.setEnabled(z);
        }
    }

    public void returnColorResult(int i) {
        Intent intent = new Intent();
        intent.putExtra("default_icon_tint_color", i);
        setResult(-1, intent);
        finish();
    }

    public void returnUriResult(Uri uri) {
        Intent intent = new Intent();
        intent.setData(uri);
        setResult(-1, intent);
        finish();
    }

    public final void setUpButtons() {
        FooterBarMixin mixin = findViewById(R$id.glif_layout).getMixin(FooterBarMixin.class);
        FooterButton build = new FooterButton.Builder(this).setText(getString(17039360)).setListener(new View.OnClickListener() { // from class: com.android.settingslib.users.AvatarPickerActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AvatarPickerActivity.$r8$lambda$OKEMNwKmHcLuxgywUDcZx8bzIRA(AvatarPickerActivity.this, view);
            }
        }).build();
        FooterButton build2 = new FooterButton.Builder(this).setText(getString(R$string.done)).setListener(new View.OnClickListener() { // from class: com.android.settingslib.users.AvatarPickerActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AvatarPickerActivity.m1140$r8$lambda$FZS4gdvVJXeAiNLtPY01ZUyHE8(AvatarPickerActivity.this, view);
            }
        }).build();
        this.mDoneButton = build2;
        build2.setEnabled(false);
        mixin.setSecondaryButton(build);
        mixin.setPrimaryButton(this.mDoneButton);
    }

    @Override // android.app.Activity
    public void startActivityForResult(Intent intent, int i) {
        this.mWaitingForActivityResult = true;
        super.startActivityForResult(intent, i);
    }
}