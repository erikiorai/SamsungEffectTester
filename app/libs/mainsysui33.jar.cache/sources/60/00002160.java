package com.android.systemui.qs.carrier;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.settingslib.Utils;
import com.android.settingslib.graph.SignalDrawable;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.R$id;
import com.android.systemui.R$integer;
import com.android.systemui.R$string;
import com.android.systemui.util.LargeScreenUtils;
import java.util.Objects;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/carrier/QSCarrier.class */
public class QSCarrier extends LinearLayout {
    public TextView mCarrierText;
    public boolean mIsSingleCarrier;
    public CellSignalState mLastSignalState;
    public View mMobileGroup;
    public ImageView mMobileRoaming;
    public ImageView mMobileSignal;
    public boolean mMobileSignalInitialized;
    public View mSpacer;

    public QSCarrier(Context context) {
        super(context);
        this.mMobileSignalInitialized = false;
    }

    public QSCarrier(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMobileSignalInitialized = false;
    }

    public QSCarrier(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mMobileSignalInitialized = false;
    }

    public QSCarrier(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mMobileSignalInitialized = false;
    }

    public View getRSSIView() {
        return this.mMobileGroup;
    }

    public final boolean hasValidTypeContentDescription(String str) {
        return TextUtils.equals(str, ((LinearLayout) this).mContext.getString(R$string.data_connection_no_internet)) || TextUtils.equals(str, ((LinearLayout) this).mContext.getString(com.android.settingslib.R$string.cell_data_off_content_description)) || TextUtils.equals(str, ((LinearLayout) this).mContext.getString(com.android.settingslib.R$string.not_default_data_content_description));
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateResources();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mMobileGroup = findViewById(R$id.mobile_combo);
        this.mMobileRoaming = (ImageView) findViewById(R$id.mobile_roaming);
        this.mMobileSignal = (ImageView) findViewById(R$id.mobile_signal);
        this.mCarrierText = (TextView) findViewById(R$id.qs_carrier_text);
        this.mSpacer = findViewById(R$id.spacer);
        updateResources();
    }

    public void setCarrierText(CharSequence charSequence) {
        this.mCarrierText.setText(charSequence);
    }

    public final void updateResources() {
        this.mCarrierText.setMaxEms(LargeScreenUtils.shouldUseLargeScreenShadeHeader(getResources()) ? Integer.MAX_VALUE : getResources().getInteger(R$integer.qs_carrier_max_em));
    }

    public boolean updateState(CellSignalState cellSignalState, boolean z) {
        if (Objects.equals(cellSignalState, this.mLastSignalState) && z == this.mIsSingleCarrier) {
            return false;
        }
        this.mLastSignalState = cellSignalState;
        this.mIsSingleCarrier = z;
        boolean z2 = cellSignalState.visible && !z;
        this.mMobileGroup.setVisibility(z2 ? 0 : 8);
        this.mSpacer.setVisibility(z ? 0 : 8);
        if (z2) {
            this.mMobileRoaming.setVisibility(cellSignalState.roaming ? 0 : 8);
            ColorStateList colorAttr = Utils.getColorAttr(((LinearLayout) this).mContext, 16842806);
            this.mMobileRoaming.setImageTintList(colorAttr);
            this.mMobileSignal.setImageTintList(colorAttr);
            if (!this.mMobileSignalInitialized) {
                this.mMobileSignalInitialized = true;
                this.mMobileSignal.setImageDrawable(new SignalDrawable(((LinearLayout) this).mContext));
            }
            this.mMobileSignal.setImageLevel(cellSignalState.mobileSignalIconId);
            StringBuilder sb = new StringBuilder();
            String str = cellSignalState.contentDescription;
            if (str != null) {
                sb.append(str);
                sb.append(", ");
            }
            if (cellSignalState.roaming) {
                sb.append(((LinearLayout) this).mContext.getString(R$string.data_connection_roaming));
                sb.append(", ");
            }
            if (hasValidTypeContentDescription(cellSignalState.typeContentDescription)) {
                sb.append(cellSignalState.typeContentDescription);
            }
            this.mMobileSignal.setContentDescription(sb);
            return true;
        }
        return true;
    }

    public void updateTextAppearance(int i) {
        FontSizeUtils.updateFontSizeFromStyle(this.mCarrierText, i);
    }
}