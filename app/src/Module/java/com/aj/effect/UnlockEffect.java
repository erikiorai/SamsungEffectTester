package com.aj.effect;

import static com.aj.effect.EffectEnum.AUTUMN;
import static com.aj.effect.EffectEnum.BLIND;
import static com.aj.effect.EffectEnum.BOUNCINGCOLOR;
import static com.aj.effect.EffectEnum.COLOURDROPLET;
import static com.aj.effect.EffectEnum.LIGHTING;
import static com.aj.effect.EffectEnum.NONE;
import static com.aj.effect.EffectEnum.POPPINGCOLOURS;
import static com.aj.effect.EffectEnum.POPPINGGOODLOCK;
import static com.aj.effect.EffectEnum.RECTANGLETRAVELLER;
import static com.aj.effect.EffectEnum.SEASONAL;
import static com.aj.effect.EffectEnum.SPARKLINGBUBBLES;
import static com.aj.effect.EffectEnum.SPRING;
import static com.aj.effect.EffectEnum.SUMMER;
import static com.aj.effect.EffectEnum.WATERDROPLET;
import static com.aj.effect.EffectEnum.WINTER;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.InsetDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.keyguard.sec.KeyguardEffectViewController;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class UnlockEffect extends AppCompatActivity implements AdapterView.OnItemClickListener {
    // TODO: add mass tension and other effect names!!!!!!!!!!!! s3 firmware
    private static String[] mModeItem;
    private RadioAdapter adapter;
    private ListView mListView;
    private View mTabletView;
    private boolean mIsTablet;
    public static int mDefaultUnlock = Utils.defaultUnlock;
    private ImageView mImageView = null;

    private final EffectEnum[] order = { NONE, SEASONAL, SPRING, SUMMER, AUTUMN, WINTER,
            COLOURDROPLET, WATERDROPLET, SPARKLINGBUBBLES, POPPINGCOLOURS, LIGHTING,
            BLIND, POPPINGGOODLOCK, RECTANGLETRAVELLER, BOUNCINGCOLOR
    };

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        KeyguardEffectViewController.setResources(this.getResources());
        mIsTablet = Utils.isTablet();
        ImageView imageViewforkeyboard;
        super.onCreate(savedInstanceState);
        permissionCheck();
        if (!this.mIsTablet) {
            setContentView(R.layout.lockscreen_preview);
            if (getActionBar() != null) {
                getActionBar().setDisplayHomeAsUpEnabled(true);
                getActionBar().setHomeButtonEnabled(true);
            }
            this.mListView = (ListView) findViewById(android.R.id.list);
            this.mImageView = (ImageView) findViewById(R.id.preview_image);
            if (Utils.ConnectedMobileKeypad(this) && (imageViewforkeyboard = (ImageView) findViewById(R.id.preview_image_for_mobile_keyboard)) != null) {
                this.mImageView.setVisibility(View.GONE);
                this.mImageView = imageViewforkeyboard;
                this.mImageView.setVisibility(View.VISIBLE);
            }
            populateUnlockEffectsOptions();
            Resources resources = getResources();
            int divider_inset_size = resources.getDimensionPixelSize(R.dimen.list_item_padding) + resources.getDimensionPixelSize(R.dimen.list_divider_additional_inset) + resources.getDimensionPixelSize(R.dimen.list_radiobox_width_for_divider_inset);
            if (Utils.isRTL()) {
                InsetDrawable insetdivider = new InsetDrawable(this.mListView.getDivider(), 0, 0, divider_inset_size, 0);
                this.mListView.setDivider(insetdivider);
            } else {
                InsetDrawable insetdivider2 = new InsetDrawable(this.mListView.getDivider(), divider_inset_size, 0, 0, 0);
                this.mListView.setDivider(insetdivider2);
            }
            this.adapter = new RadioAdapter(this, R.layout.list_item_with_radiobox, mModeItem);
            this.mListView.setAdapter((ListAdapter) this.adapter);
            this.mListView.setItemsCanFocus(false);
            this.mListView.setOnItemClickListener(this);
            this.mListView.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
            updateImageResource();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mIsTablet) {
            setVisible(false);
            createDialogforTablet();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (!(requestCode == 1 && resultCode == RESULT_OK)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("The permission was not granted.").setTitle("Force exit");
            builder.setNeutralButton(android.R.string.ok, (dialogInterface, i) -> UnlockEffect.this.finishAffinity());
            AlertDialog dialog = builder.create();
            dialog.setOnCancelListener((dialogInterface) -> UnlockEffect.this.finishAffinity());
            dialog.show();
        }
    }

    private void createDialogforTablet() {
        this.mTabletView = getLayoutInflater().inflate(R.layout.unlock_effect_dialog, (ViewGroup) null);
        initViewforTablet();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(this.mTabletView);
        alertDialogBuilder.setTitle(R.string.unlock_effect);
        // todo: was R.id.SAVE
        // from class: com.android.settings.UnlockEffect.1
// android.content.DialogInterface.OnClickListener
        alertDialogBuilder.setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) (dialog, id) -> {
            SettingsManager.putInt(UnlockEffect.this, UnlockEffect.this.getContentResolver(), "lockscreen_ripple_effect", mDefaultUnlock);
            Log.d("UnlockEffect", "lockscreen_ripple_effect DB Value : " + mDefaultUnlock);
            KeyguardEffectViewController.getInstance(UnlockEffect.this).handleWallpaperTypeChanged();
            UnlockEffect.this.finish();
        });
        // from class: com.android.settings.UnlockEffect.2
// android.content.DialogInterface.OnClickListener
        alertDialogBuilder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) (dialog, id) -> UnlockEffect.this.finish());
        final AlertDialog alertDialog = alertDialogBuilder.create();
        // from class: com.android.settings.UnlockEffect.3
// android.content.DialogInterface.OnCancelListener
        alertDialog.setOnCancelListener((DialogInterface.OnCancelListener) dialog -> {
            alertDialog.dismiss();
            UnlockEffect.this.finish();
        });
        alertDialog.show();
    }

    private void permissionCheck() {
        if (!Settings.System.canWrite(this)) {
            Intent set = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            set.setData(Uri.parse("package:" + this.getPackageName()));
            startActivityForResult(set, 1);
        }
    }

    private void initViewforTablet() {
        this.mListView = (ListView) this.mTabletView.findViewById(android.R.id.list);
        this.mImageView = (ImageView) this.mTabletView.findViewById(R.id.preview_image);
        populateUnlockEffectsOptions();
        this.adapter = new RadioAdapter(this, R.layout.list_item_with_radiobox_for_dialog, mModeItem);
        this.mListView.setAdapter((ListAdapter) this.adapter);
        this.mListView.setItemsCanFocus(false);
        this.mListView.setOnItemClickListener(this);
        this.mListView.setOverScrollMode(1);
        updateImageResource();
    }

    private void updateImageResource() {
        mDefaultUnlock = SettingsManager.getInt(UnlockEffect.this, getContentResolver(), "lockscreen_ripple_effect", Utils.defaultUnlock);
        for (int i = 0; i < order.length; i++) {
            if (order[i].assigned == mDefaultUnlock) {
                this.mListView.setItemChecked(i, true);
                this.mImageView.setImageResource(order[i].drawable);
            }
        }
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(Bundle SavedInstanceState) {
        super.onSaveInstanceState(SavedInstanceState);
        SavedInstanceState.putInt("selected_idx", this.mListView.getCheckedItemPosition());
    }

    @Override // android.app.Activity
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int idx = savedInstanceState.getInt("selected_idx");
        try {
            this.mImageView.setImageResource(order[idx].drawable);
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.d("UnlockEffect", "ArrayIndexOutOfBoundsException Occured.  set to Popping Colour.");
        }
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.mImageView.setImageResource(order[position].drawable);
        int effect = order[this.mListView.getCheckedItemPosition()].assigned;
        if (this.mIsTablet) {
            mDefaultUnlock = effect;
        } else {
            SettingsManager.putInt(UnlockEffect.this, getContentResolver(), "lockscreen_ripple_effect", effect);
            //controller.handleWallpaperTypeChanged();
        }
        Log.d("UnlockEffect", "lockscreen_ripple_effect DB Value : " + effect);
    }

    // TODO: rework
    void populateUnlockEffectsOptions() {
        List<String> aChangedEffectEntry = new ArrayList<>();
        Resources res = this.getResources();
        for (EffectEnum e : order) {
            aChangedEffectEntry.add(res.getString(e.name));
        }
        mModeItem = aChangedEffectEntry.toArray(new String[order.length]);
    }

    /* loaded from: classes.dex */
    public static class RadioAdapter extends ArrayAdapter<String> {
        Context mContext;

        public RadioAdapter(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
            this.mContext = context;
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater li = LayoutInflater.from(this.mContext);
                if (Utils.isTablet()) {
                    convertView = li.inflate(R.layout.list_item_with_radiobox_for_dialog, parent, false);
                } else {
                    convertView = li.inflate(R.layout.list_item_with_radiobox, parent, false);
                }
            }
            return super.getView(position, convertView, parent);
        }
    }

    @Override // android.app.Activity
    public boolean onNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}