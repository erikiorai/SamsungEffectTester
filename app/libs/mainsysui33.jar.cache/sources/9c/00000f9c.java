package com.android.systemui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.IconDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;
import com.android.internal.logging.MetricsLogger;
import java.util.ArrayList;

/* loaded from: mainsysui33.jar:com/android/systemui/ForegroundServicesDialog.class */
public final class ForegroundServicesDialog extends AlertActivity implements AdapterView.OnItemSelectedListener, DialogInterface.OnClickListener, AlertController.AlertParams.OnPrepareListViewListener {
    public PackageItemAdapter mAdapter;
    public DialogInterface.OnClickListener mAppClickListener = new DialogInterface.OnClickListener() { // from class: com.android.systemui.ForegroundServicesDialog.1
        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            String str = ForegroundServicesDialog.this.mAdapter.getItem(i).packageName;
            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", str, null));
            ForegroundServicesDialog.this.startActivity(intent);
            ForegroundServicesDialog.this.finish();
        }
    };
    public LayoutInflater mInflater;
    public final MetricsLogger mMetricsLogger;
    public String[] mPackages;

    /* loaded from: mainsysui33.jar:com/android/systemui/ForegroundServicesDialog$PackageItemAdapter.class */
    public static class PackageItemAdapter extends ArrayAdapter<ApplicationInfo> {
        public final IconDrawableFactory mIconDrawableFactory;
        public final LayoutInflater mInflater;
        public final PackageManager mPm;

        public PackageItemAdapter(Context context) {
            super(context, R$layout.foreground_service_item);
            this.mPm = context.getPackageManager();
            this.mInflater = LayoutInflater.from(context);
            this.mIconDrawableFactory = IconDrawableFactory.newInstance(context, true);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view2 = view;
            if (view == null) {
                view2 = this.mInflater.inflate(R$layout.foreground_service_item, viewGroup, false);
            }
            ((ImageView) view2.findViewById(R$id.app_icon)).setImageDrawable(this.mIconDrawableFactory.getBadgedIcon(getItem(i)));
            ((TextView) view2.findViewById(R$id.app_name)).setText(getItem(i).loadLabel(this.mPm));
            return view2;
        }

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:10:0x0040 -> B:6:0x0025). Please submit an issue!!! */
        public void setPackages(String[] strArr) {
            clear();
            ArrayList arrayList = new ArrayList();
            for (String str : strArr) {
                try {
                    arrayList.add(this.mPm.getApplicationInfo(str, 4202496));
                } catch (PackageManager.NameNotFoundException e) {
                }
            }
            arrayList.sort(new ApplicationInfo.DisplayNameComparator(this.mPm));
            addAll(arrayList);
        }
    }

    public ForegroundServicesDialog(MetricsLogger metricsLogger) {
        this.mMetricsLogger = metricsLogger;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        finish();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: com.android.systemui.ForegroundServicesDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mInflater = LayoutInflater.from(this);
        PackageItemAdapter packageItemAdapter = new PackageItemAdapter(this);
        this.mAdapter = packageItemAdapter;
        AlertController.AlertParams alertParams = ((AlertActivity) this).mAlertParams;
        alertParams.mAdapter = packageItemAdapter;
        alertParams.mOnClickListener = this.mAppClickListener;
        alertParams.mCustomTitleView = this.mInflater.inflate(R$layout.foreground_service_title, (ViewGroup) null);
        alertParams.mIsSingleChoice = true;
        alertParams.mOnItemSelectedListener = this;
        alertParams.mPositiveButtonText = getString(17040179);
        alertParams.mPositiveButtonListener = this;
        alertParams.mOnPrepareListViewListener = this;
        updateApps(getIntent());
        if (this.mPackages != null) {
            setupAlert();
            return;
        }
        Log.w("ForegroundServicesDialog", "No packages supplied");
        finish();
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView adapterView, View view, int i, long j) {
    }

    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        updateApps(intent);
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView adapterView) {
    }

    public void onPause() {
        super.onPause();
        this.mMetricsLogger.hidden(944);
    }

    public void onPrepareListView(ListView listView) {
    }

    public void onResume() {
        super.onResume();
        this.mMetricsLogger.visible(944);
    }

    public void onStop() {
        super.onStop();
        if (isChangingConfigurations()) {
            return;
        }
        finish();
    }

    public void updateApps(Intent intent) {
        String[] stringArrayExtra = intent.getStringArrayExtra("packages");
        this.mPackages = stringArrayExtra;
        if (stringArrayExtra != null) {
            this.mAdapter.setPackages(stringArrayExtra);
        }
    }
}