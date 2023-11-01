package com.android.keyguard;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.euicc.EuiccManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.android.systemui.R$string;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardEsimArea.class */
class KeyguardEsimArea extends Button implements View.OnClickListener {
    public EuiccManager mEuiccManager;
    public BroadcastReceiver mReceiver;
    public int mSubscriptionId;

    public KeyguardEsimArea(Context context) {
        this(context, null);
    }

    public KeyguardEsimArea(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public KeyguardEsimArea(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 16974425);
    }

    public KeyguardEsimArea(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.keyguard.KeyguardEsimArea.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                int resultCode;
                if (!"com.android.keyguard.disable_esim".equals(intent.getAction()) || (resultCode = getResultCode()) == 0) {
                    return;
                }
                Log.e("KeyguardEsimArea", "Error disabling esim, result code = " + resultCode);
                AlertDialog create = new AlertDialog.Builder(((Button) KeyguardEsimArea.this).mContext).setMessage(R$string.error_disable_esim_msg).setTitle(R$string.error_disable_esim_title).setCancelable(false).setPositiveButton(R$string.ok, (DialogInterface.OnClickListener) null).create();
                create.getWindow().setType(2009);
                create.show();
            }
        };
        this.mEuiccManager = (EuiccManager) context.getSystemService("euicc");
        setOnClickListener(this);
    }

    public static boolean isEsimLocked(Context context, int i) {
        if (((EuiccManager) context.getSystemService("euicc")).isEnabled()) {
            SubscriptionInfo activeSubscriptionInfo = SubscriptionManager.from(context).getActiveSubscriptionInfo(i);
            boolean z = false;
            if (activeSubscriptionInfo != null) {
                z = false;
                if (activeSubscriptionInfo.isEmbedded()) {
                    z = true;
                }
            }
            return z;
        }
        return false;
    }

    @Override // android.widget.TextView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((Button) this).mContext.registerReceiver(this.mReceiver, new IntentFilter("com.android.keyguard.disable_esim"), "com.android.systemui.permission.SELF", null, 2);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        SubscriptionInfo activeSubscriptionInfo = SubscriptionManager.from(((Button) this).mContext).getActiveSubscriptionInfo(this.mSubscriptionId);
        if (activeSubscriptionInfo == null) {
            Log.e("KeyguardEsimArea", "No active subscription with subscriptionId: " + this.mSubscriptionId);
            return;
        }
        Intent intent = new Intent("com.android.keyguard.disable_esim");
        intent.setPackage(((Button) this).mContext.getPackageName());
        this.mEuiccManager.switchToSubscription(-1, activeSubscriptionInfo.getPortIndex(), PendingIntent.getBroadcastAsUser(((Button) this).mContext, 0, intent, 167772160, UserHandle.SYSTEM));
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        ((Button) this).mContext.unregisterReceiver(this.mReceiver);
        super.onDetachedFromWindow();
    }

    public void setSubscriptionId(int i) {
        this.mSubscriptionId = i;
    }
}