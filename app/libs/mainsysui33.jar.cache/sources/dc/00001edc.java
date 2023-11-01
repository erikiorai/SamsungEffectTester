package com.android.systemui.net;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.INetworkPolicyManager;
import android.net.NetworkTemplate;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.systemui.R$string;

/* loaded from: mainsysui33.jar:com/android/systemui/net/NetworkOverLimitActivity.class */
public class NetworkOverLimitActivity extends Activity {
    public static int getLimitedDialogTitleForTemplate(NetworkTemplate networkTemplate) {
        return networkTemplate.getMatchRule() != 1 ? R$string.data_usage_disabled_dialog_title : R$string.data_usage_disabled_dialog_mobile_title;
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        final NetworkTemplate parcelableExtra = getIntent().getParcelableExtra("android.net.NETWORK_TEMPLATE");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getLimitedDialogTitleForTemplate(parcelableExtra));
        builder.setMessage(R$string.data_usage_disabled_dialog);
        builder.setPositiveButton(17039370, (DialogInterface.OnClickListener) null);
        builder.setNegativeButton(R$string.data_usage_disabled_dialog_enable, new DialogInterface.OnClickListener() { // from class: com.android.systemui.net.NetworkOverLimitActivity.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                NetworkOverLimitActivity.this.snoozePolicy(parcelableExtra);
            }
        });
        AlertDialog create = builder.create();
        create.getWindow().setType(2003);
        create.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.net.NetworkOverLimitActivity.2
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                NetworkOverLimitActivity.this.finish();
            }
        });
        create.show();
    }

    public final void snoozePolicy(NetworkTemplate networkTemplate) {
        try {
            INetworkPolicyManager.Stub.asInterface(ServiceManager.getService("netpolicy")).snoozeLimit(networkTemplate);
        } catch (RemoteException e) {
            Log.w("NetworkOverLimitActivity", "problem snoozing network policy", e);
        }
    }
}