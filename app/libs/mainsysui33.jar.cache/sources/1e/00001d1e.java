package com.android.systemui.media.dialog;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.core.widget.CompoundButtonCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.android.settingslib.media.MediaDevice;
import com.android.systemui.R$drawable;
import com.android.systemui.R$id;
import com.android.systemui.R$string;
import com.android.systemui.media.dialog.MediaOutputAdapter;
import com.android.systemui.media.dialog.MediaOutputBaseAdapter;
import java.util.List;
import java.util.Objects;

/* loaded from: mainsysui33.jar:com/android/systemui/media/dialog/MediaOutputAdapter.class */
public class MediaOutputAdapter extends MediaOutputBaseAdapter {
    public static final boolean DEBUG = Log.isLoggable("MediaOutputAdapter", 3);

    /* loaded from: mainsysui33.jar:com/android/systemui/media/dialog/MediaOutputAdapter$MediaDeviceViewHolder.class */
    public class MediaDeviceViewHolder extends MediaOutputBaseAdapter.MediaDeviceBaseViewHolder {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda4.onClick(android.view.View):void] */
        public static /* synthetic */ void $r8$lambda$30HeOMqtnuq2U0B37xeYT6LE1eM(MediaDeviceViewHolder mediaDeviceViewHolder, MediaDevice mediaDevice, View view) {
            mediaDeviceViewHolder.lambda$onBind$3(mediaDevice, view);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda3.onClick(android.view.View):void] */
        public static /* synthetic */ void $r8$lambda$4elHMTdcP05KDh8iOgg0eeJ7dbk(MediaDeviceViewHolder mediaDeviceViewHolder, View view) {
            mediaDeviceViewHolder.lambda$onBind$2(view);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda6.onClick(android.view.View):void] */
        public static /* synthetic */ void $r8$lambda$ZB4rzHeFPCz5wh4hKHuaEkSWWuo(MediaDeviceViewHolder mediaDeviceViewHolder, MediaDevice mediaDevice, View view) {
            mediaDeviceViewHolder.lambda$onBind$5(mediaDevice, view);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda7.onClick(android.view.View):void] */
        public static /* synthetic */ void $r8$lambda$ZlPoZG_phVRzq22sSZW1kcn5rbQ(MediaDeviceViewHolder mediaDeviceViewHolder, View view) {
            mediaDeviceViewHolder.lambda$updateEndClickArea$6(view);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda1.onClick(android.view.View):void] */
        /* renamed from: $r8$lambda$bzAaL5-61UDEuRwi_xklou58yPc */
        public static /* synthetic */ void m3297$r8$lambda$bzAaL561UDEuRwi_xklou58yPc(MediaDeviceViewHolder mediaDeviceViewHolder, MediaDevice mediaDevice, View view) {
            mediaDeviceViewHolder.lambda$onBind$0(mediaDevice, view);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda2.onClick(android.view.View):void] */
        /* renamed from: $r8$lambda$dBmIg-GQm3aosK4WbWaq0qCCnBQ */
        public static /* synthetic */ void m3298$r8$lambda$dBmIgGQm3aosK4WbWaq0qCCnBQ(MediaDeviceViewHolder mediaDeviceViewHolder, MediaDevice mediaDevice, View view) {
            mediaDeviceViewHolder.lambda$onBind$1(mediaDevice, view);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda8.onCheckedChanged(android.widget.CompoundButton, boolean):void] */
        /* renamed from: $r8$lambda$jqdUUmtfnV6fVFymmL3J2LJ-bng */
        public static /* synthetic */ void m3299$r8$lambda$jqdUUmtfnV6fVFymmL3J2LJbng(MediaDeviceViewHolder mediaDeviceViewHolder, boolean z, MediaDevice mediaDevice, CompoundButton compoundButton, boolean z2) {
            mediaDeviceViewHolder.lambda$updateGroupableCheckBox$7(z, mediaDevice, compoundButton, z2);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda5.onClick(android.view.View):void] */
        public static /* synthetic */ void $r8$lambda$uaEfDFx0c7MbnxtxexYZOuqxtjI(MediaDeviceViewHolder mediaDeviceViewHolder, MediaDevice mediaDevice, View view) {
            mediaDeviceViewHolder.lambda$onBind$4(mediaDevice, view);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MediaDeviceViewHolder(View view) {
            super(view);
            MediaOutputAdapter.this = r5;
        }

        public /* synthetic */ void lambda$onBind$2(View view) {
            cancelMuteAwaitConnection();
        }

        public /* synthetic */ void lambda$onBind$4(MediaDevice mediaDevice, View view) {
            onGroupActionTriggered(true, mediaDevice);
        }

        public /* synthetic */ void lambda$updateEndClickArea$6(View view) {
            this.mCheckBox.performClick();
        }

        public /* synthetic */ void lambda$updateGroupableCheckBox$7(boolean z, MediaDevice mediaDevice, CompoundButton compoundButton, boolean z2) {
            onGroupActionTriggered(!z, mediaDevice);
        }

        public final void cancelMuteAwaitConnection() {
            MediaOutputAdapter.this.mController.cancelMuteAwaitConnection();
            MediaOutputAdapter.this.notifyDataSetChanged();
        }

        public void onBind(int i) {
            if (i == 1) {
                this.mTitleText.setTextColor(MediaOutputAdapter.this.mController.getColorItemContent());
                this.mCheckBox.setVisibility(8);
                setSingleLineLayout(MediaOutputAdapter.this.mContext.getText(R$string.media_output_dialog_pairing_new));
                this.mTitleIcon.setImageDrawable(MediaOutputAdapter.this.mContext.getDrawable(R$drawable.ic_add));
                this.mTitleIcon.setColorFilter(MediaOutputAdapter.this.mController.getColorItemContent());
                if (MediaOutputAdapter.this.mController.isAdvancedLayoutSupported()) {
                    this.mIconAreaLayout.getBackground().setColorFilter(new PorterDuffColorFilter(MediaOutputAdapter.this.mController.getColorItemBackground(), PorterDuff.Mode.SRC_IN));
                }
                ViewGroup viewGroup = this.mContainerLayout;
                final MediaOutputController mediaOutputController = MediaOutputAdapter.this.mController;
                Objects.requireNonNull(mediaOutputController);
                viewGroup.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.dialog.MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        MediaOutputController.this.launchBluetoothPairing(view);
                    }
                });
            }
        }

        @Override // com.android.systemui.media.dialog.MediaOutputBaseAdapter.MediaDeviceBaseViewHolder
        public void onBind(final MediaDevice mediaDevice, int i) {
            super.onBind(mediaDevice, i);
            boolean hasMutingExpectedDevice = MediaOutputAdapter.this.mController.hasMutingExpectedDevice();
            boolean isCurrentlyConnected = MediaOutputAdapter.this.isCurrentlyConnected(mediaDevice);
            boolean z = this.mSeekBar.getVisibility() == 8;
            MediaOutputAdapter mediaOutputAdapter = MediaOutputAdapter.this;
            if (mediaOutputAdapter.mCurrentActivePosition == i) {
                mediaOutputAdapter.mCurrentActivePosition = -1;
            }
            if (mediaOutputAdapter.mController.isAnyDeviceTransferring()) {
                if (mediaDevice.getState() != 1 || MediaOutputAdapter.this.mController.hasAdjustVolumeUserRestriction()) {
                    setUpDeviceIcon(mediaDevice);
                    setSingleLineLayout(MediaOutputAdapter.this.getItemTitle(mediaDevice));
                    return;
                }
                setUpDeviceIcon(mediaDevice);
                updateProgressBarColor();
                setSingleLineLayout(MediaOutputAdapter.this.getItemTitle(mediaDevice), false, true, false, false);
            } else if (mediaDevice.isMutingExpectedDevice() && !MediaOutputAdapter.this.mController.isCurrentConnectedDeviceRemote()) {
                if (!MediaOutputAdapter.this.mController.isAdvancedLayoutSupported()) {
                    updateTitleIcon(R$drawable.media_output_icon_volume, MediaOutputAdapter.this.mController.getColorItemContent());
                }
                initMutingExpectedDevice();
                MediaOutputAdapter.this.mCurrentActivePosition = i;
                updateFullItemClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.dialog.MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        MediaOutputAdapter.MediaDeviceViewHolder.m3297$r8$lambda$bzAaL561UDEuRwi_xklou58yPc(MediaOutputAdapter.MediaDeviceViewHolder.this, mediaDevice, view);
                    }
                });
                setSingleLineLayout(MediaOutputAdapter.this.getItemTitle(mediaDevice));
            } else if (mediaDevice.getState() == 3) {
                setUpDeviceIcon(mediaDevice);
                updateConnectionFailedStatusIcon();
                this.mSubTitleText.setText(R$string.media_output_dialog_connect_failed);
                updateFullItemClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.dialog.MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        MediaOutputAdapter.MediaDeviceViewHolder.m3298$r8$lambda$dBmIgGQm3aosK4WbWaq0qCCnBQ(MediaOutputAdapter.MediaDeviceViewHolder.this, mediaDevice, view);
                    }
                });
                setTwoLineLayout(mediaDevice, false, false, false, true, true);
            } else if (mediaDevice.getState() == 5) {
                setUpDeviceIcon(mediaDevice);
                updateProgressBarColor();
                setSingleLineLayout(MediaOutputAdapter.this.getItemTitle(mediaDevice), false, true, false, false);
            } else {
                if (MediaOutputAdapter.this.mController.getSelectedMediaDevice().size() > 1) {
                    MediaOutputAdapter mediaOutputAdapter2 = MediaOutputAdapter.this;
                    if (mediaOutputAdapter2.isDeviceIncluded(mediaOutputAdapter2.mController.getSelectedMediaDevice(), mediaDevice)) {
                        MediaOutputAdapter mediaOutputAdapter3 = MediaOutputAdapter.this;
                        boolean isDeviceIncluded = mediaOutputAdapter3.isDeviceIncluded(mediaOutputAdapter3.mController.getDeselectableMediaDevice(), mediaDevice);
                        if (!MediaOutputAdapter.this.mController.isAdvancedLayoutSupported()) {
                            updateTitleIcon(R$drawable.media_output_icon_volume, MediaOutputAdapter.this.mController.getColorItemContent());
                        }
                        updateGroupableCheckBox(true, isDeviceIncluded, mediaDevice);
                        updateEndClickArea(mediaDevice, isDeviceIncluded);
                        setUpContentDescriptionForView(this.mContainerLayout, false, mediaDevice);
                        setSingleLineLayout(MediaOutputAdapter.this.getItemTitle(mediaDevice), true, false, true, true);
                        initSeekbar(mediaDevice, z);
                        return;
                    }
                }
                if (MediaOutputAdapter.this.mController.hasAdjustVolumeUserRestriction() || !isCurrentlyConnected) {
                    MediaOutputAdapter mediaOutputAdapter4 = MediaOutputAdapter.this;
                    if (!mediaOutputAdapter4.isDeviceIncluded(mediaOutputAdapter4.mController.getSelectableMediaDevice(), mediaDevice)) {
                        setUpDeviceIcon(mediaDevice);
                        setSingleLineLayout(MediaOutputAdapter.this.getItemTitle(mediaDevice));
                        updateFullItemClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.dialog.MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda6
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                MediaOutputAdapter.MediaDeviceViewHolder.$r8$lambda$ZB4rzHeFPCz5wh4hKHuaEkSWWuo(MediaOutputAdapter.MediaDeviceViewHolder.this, mediaDevice, view);
                            }
                        });
                        return;
                    }
                    setUpDeviceIcon(mediaDevice);
                    updateGroupableCheckBox(false, true, mediaDevice);
                    if (MediaOutputAdapter.this.mController.isAdvancedLayoutSupported()) {
                        updateEndClickArea(mediaDevice, true);
                    }
                    updateFullItemClickListener(MediaOutputAdapter.this.mController.isAdvancedLayoutSupported() ? new View.OnClickListener() { // from class: com.android.systemui.media.dialog.MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda4
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            MediaOutputAdapter.MediaDeviceViewHolder.$r8$lambda$30HeOMqtnuq2U0B37xeYT6LE1eM(MediaOutputAdapter.MediaDeviceViewHolder.this, mediaDevice, view);
                        }
                    } : new View.OnClickListener() { // from class: com.android.systemui.media.dialog.MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda5
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            MediaOutputAdapter.MediaDeviceViewHolder.$r8$lambda$uaEfDFx0c7MbnxtxexYZOuqxtjI(MediaOutputAdapter.MediaDeviceViewHolder.this, mediaDevice, view);
                        }
                    });
                    setSingleLineLayout(MediaOutputAdapter.this.getItemTitle(mediaDevice), false, false, true, true);
                } else if (hasMutingExpectedDevice && !MediaOutputAdapter.this.mController.isCurrentConnectedDeviceRemote()) {
                    setUpDeviceIcon(mediaDevice);
                    updateFullItemClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.dialog.MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda3
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            MediaOutputAdapter.MediaDeviceViewHolder.$r8$lambda$4elHMTdcP05KDh8iOgg0eeJ7dbk(MediaOutputAdapter.MediaDeviceViewHolder.this, view);
                        }
                    });
                    setSingleLineLayout(MediaOutputAdapter.this.getItemTitle(mediaDevice));
                } else if (!MediaOutputAdapter.this.mController.isCurrentConnectedDeviceRemote() || MediaOutputAdapter.this.mController.getSelectableMediaDevice().isEmpty() || !MediaOutputAdapter.this.mController.isAdvancedLayoutSupported()) {
                    updateTitleIcon(R$drawable.media_output_icon_volume, MediaOutputAdapter.this.mController.getColorItemContent());
                    setUpContentDescriptionForView(this.mContainerLayout, false, mediaDevice);
                    MediaOutputAdapter mediaOutputAdapter5 = MediaOutputAdapter.this;
                    mediaOutputAdapter5.mCurrentActivePosition = i;
                    setSingleLineLayout(mediaOutputAdapter5.getItemTitle(mediaDevice), true, false, false, false);
                    initSeekbar(mediaDevice, z);
                } else {
                    MediaOutputAdapter mediaOutputAdapter6 = MediaOutputAdapter.this;
                    boolean isDeviceIncluded2 = mediaOutputAdapter6.isDeviceIncluded(mediaOutputAdapter6.mController.getDeselectableMediaDevice(), mediaDevice);
                    updateGroupableCheckBox(true, isDeviceIncluded2, mediaDevice);
                    updateEndClickArea(mediaDevice, isDeviceIncluded2);
                    setUpContentDescriptionForView(this.mContainerLayout, false, mediaDevice);
                    setSingleLineLayout(MediaOutputAdapter.this.getItemTitle(mediaDevice), true, false, true, true);
                    initSeekbar(mediaDevice, z);
                }
            }
        }

        public final void onGroupActionTriggered(boolean z, MediaDevice mediaDevice) {
            disableSeekBar();
            if (z) {
                MediaOutputAdapter mediaOutputAdapter = MediaOutputAdapter.this;
                if (mediaOutputAdapter.isDeviceIncluded(mediaOutputAdapter.mController.getSelectableMediaDevice(), mediaDevice)) {
                    MediaOutputAdapter.this.mController.addDeviceToPlayMedia(mediaDevice);
                    return;
                }
            }
            if (z) {
                return;
            }
            MediaOutputAdapter mediaOutputAdapter2 = MediaOutputAdapter.this;
            if (mediaOutputAdapter2.isDeviceIncluded(mediaOutputAdapter2.mController.getDeselectableMediaDevice(), mediaDevice)) {
                MediaOutputAdapter.this.mController.removeDeviceFromPlayMedia(mediaDevice);
            }
        }

        /* renamed from: onItemClick */
        public final void lambda$onBind$5(View view, MediaDevice mediaDevice) {
            if (MediaOutputAdapter.this.mController.isAnyDeviceTransferring()) {
                return;
            }
            if (MediaOutputAdapter.this.isCurrentlyConnected(mediaDevice)) {
                Log.d("MediaOutputAdapter", "This device is already connected! : " + mediaDevice.getName());
                return;
            }
            MediaOutputAdapter.this.mController.setTemporaryAllowListExceptionIfNeeded(mediaDevice);
            MediaOutputAdapter mediaOutputAdapter = MediaOutputAdapter.this;
            mediaOutputAdapter.mCurrentActivePosition = -1;
            mediaOutputAdapter.mController.connectDevice(mediaDevice);
            mediaDevice.setState(1);
            MediaOutputAdapter.this.notifyDataSetChanged();
        }

        /* JADX WARN: Type inference failed for: r3v1, types: [int[], int[][]] */
        public void setCheckBoxColor(CheckBox checkBox, int i) {
            CompoundButtonCompat.setButtonTintList(checkBox, new ColorStateList(new int[]{new int[]{16842912}, new int[0]}, new int[]{i, i}));
        }

        public final void setUpContentDescriptionForView(View view, boolean z, MediaDevice mediaDevice) {
            view.setClickable(z);
            view.setContentDescription(MediaOutputAdapter.this.mContext.getString(mediaDevice.getDeviceType() == 5 ? R$string.accessibility_bluetooth_name : R$string.accessibility_cast_name, mediaDevice.getName()));
        }

        public final void updateConnectionFailedStatusIcon() {
            this.mStatusIcon.setImageDrawable(MediaOutputAdapter.this.mContext.getDrawable(R$drawable.media_output_status_failed));
            this.mStatusIcon.setColorFilter(MediaOutputAdapter.this.mController.getColorItemContent());
        }

        public void updateEndClickArea(MediaDevice mediaDevice, boolean z) {
            View.OnClickListener onClickListener = null;
            this.mEndTouchArea.setOnClickListener(null);
            ViewGroup viewGroup = this.mEndTouchArea;
            if (z) {
                onClickListener = new View.OnClickListener() { // from class: com.android.systemui.media.dialog.MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda7
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        MediaOutputAdapter.MediaDeviceViewHolder.$r8$lambda$ZlPoZG_phVRzq22sSZW1kcn5rbQ(MediaOutputAdapter.MediaDeviceViewHolder.this, view);
                    }
                };
            }
            viewGroup.setOnClickListener(onClickListener);
            this.mEndTouchArea.setImportantForAccessibility(1);
            if (MediaOutputAdapter.this.mController.isAdvancedLayoutSupported()) {
                this.mEndTouchArea.getBackground().setColorFilter(new PorterDuffColorFilter(MediaOutputAdapter.this.mController.getColorItemBackground(), PorterDuff.Mode.SRC_IN));
            }
            setUpContentDescriptionForView(this.mEndTouchArea, true, mediaDevice);
        }

        public final void updateFullItemClickListener(View.OnClickListener onClickListener) {
            this.mContainerLayout.setOnClickListener(onClickListener);
            updateIconAreaClickListener(onClickListener);
        }

        public final void updateGroupableCheckBox(final boolean z, boolean z2, final MediaDevice mediaDevice) {
            CompoundButton.OnCheckedChangeListener onCheckedChangeListener = null;
            this.mCheckBox.setOnCheckedChangeListener(null);
            this.mCheckBox.setChecked(z);
            CheckBox checkBox = this.mCheckBox;
            if (z2) {
                onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.media.dialog.MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda8
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z3) {
                        MediaOutputAdapter.MediaDeviceViewHolder.m3299$r8$lambda$jqdUUmtfnV6fVFymmL3J2LJbng(MediaOutputAdapter.MediaDeviceViewHolder.this, z, mediaDevice, compoundButton, z3);
                    }
                };
            }
            checkBox.setOnCheckedChangeListener(onCheckedChangeListener);
            this.mCheckBox.setEnabled(z2);
            setCheckBoxColor(this.mCheckBox, MediaOutputAdapter.this.mController.getColorItemContent());
        }

        public final void updateProgressBarColor() {
            this.mProgressBar.getIndeterminateDrawable().setColorFilter(new PorterDuffColorFilter(MediaOutputAdapter.this.mController.getColorItemContent(), PorterDuff.Mode.SRC_IN));
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/media/dialog/MediaOutputAdapter$MediaGroupDividerViewHolder.class */
    public class MediaGroupDividerViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTitleText;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MediaGroupDividerViewHolder(View view) {
            super(view);
            MediaOutputAdapter.this = r5;
            this.mTitleText = (TextView) view.requireViewById(R$id.title);
        }

        public void onBind(String str) {
            this.mTitleText.setTextColor(MediaOutputAdapter.this.mController.getColorItemContent());
            this.mTitleText.setText(str);
        }
    }

    public MediaOutputAdapter(MediaOutputController mediaOutputController) {
        super(mediaOutputController);
        setHasStableIds(true);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mController.isAdvancedLayoutSupported() ? this.mController.getMediaItemList().size() : this.mController.getMediaDevices().size() + 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        if (this.mController.isAdvancedLayoutSupported()) {
            if (i < this.mController.getMediaItemList().size()) {
                MediaItem mediaItem = this.mController.getMediaItemList().get(i);
                return mediaItem.getMediaDevice().isPresent() ? mediaItem.getMediaDevice().get().getId().hashCode() : i;
            }
            Log.d("MediaOutputAdapter", "Incorrect position for item id: " + i);
            return i;
        }
        int size = this.mController.getMediaDevices().size();
        if (i == size) {
            return -1L;
        }
        if (i < size) {
            return ((MediaDevice) ((List) this.mController.getMediaDevices()).get(i)).getId().hashCode();
        }
        if (DEBUG) {
            Log.d("MediaOutputAdapter", "Incorrect position for item id: " + i);
        }
        return i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return this.mController.isAdvancedLayoutSupported() ? this.mController.getMediaItemList().get(i).getMediaItemType() : super.getItemViewType(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (!this.mController.isAdvancedLayoutSupported()) {
            int size = this.mController.getMediaDevices().size();
            if (i == size) {
                ((MediaDeviceViewHolder) viewHolder).onBind(1);
            } else if (i < size) {
                ((MediaDeviceViewHolder) viewHolder).onBind((MediaDevice) ((List) this.mController.getMediaDevices()).get(i), i);
            } else if (DEBUG) {
                Log.d("MediaOutputAdapter", "Incorrect position: " + i);
            }
        } else if (i >= this.mController.getMediaItemList().size()) {
            if (DEBUG) {
                Log.d("MediaOutputAdapter", "Incorrect position: " + i + " list size: " + this.mController.getMediaItemList().size());
            }
        } else {
            MediaItem mediaItem = this.mController.getMediaItemList().get(i);
            int mediaItemType = mediaItem.getMediaItemType();
            if (mediaItemType == 0) {
                ((MediaDeviceViewHolder) viewHolder).onBind(mediaItem.getMediaDevice().get(), i);
            } else if (mediaItemType == 1) {
                ((MediaGroupDividerViewHolder) viewHolder).onBind(mediaItem.getTitle());
            } else if (mediaItemType == 2) {
                ((MediaDeviceViewHolder) viewHolder).onBind(1);
            } else {
                Log.d("MediaOutputAdapter", "Incorrect position: " + i);
            }
        }
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        super.onCreateViewHolder(viewGroup, i);
        if (this.mController.isAdvancedLayoutSupported() && i == 1) {
            return new MediaGroupDividerViewHolder(this.mHolderView);
        }
        return new MediaDeviceViewHolder(this.mHolderView);
    }
}