package com.aj.effect;

import static com.aj.effect.EffectEnum.ABSTRACTTILES;
import static com.aj.effect.EffectEnum.BLIND;
import static com.aj.effect.EffectEnum.BOUNCINGCOLOR;
import static com.aj.effect.EffectEnum.BRILLIANTCUT;
import static com.aj.effect.EffectEnum.BRILLIANTRING;
import static com.aj.effect.EffectEnum.COLOURDROPLET;
import static com.aj.effect.EffectEnum.GEOMETRICMOSAIC;
import static com.aj.effect.EffectEnum.INDIGODIFFUSION;
import static com.aj.effect.EffectEnum.LIGHTING;
import static com.aj.effect.EffectEnum.POPPINGCOLOURS;
import static com.aj.effect.EffectEnum.POPPINGGOODLOCK;
import static com.aj.effect.EffectEnum.RECTANGLETRAVELLER;
import static com.aj.effect.EffectEnum.RIPPLE;
import static com.aj.effect.EffectEnum.SPARKLINGBUBBLES;
import static com.aj.effect.EffectEnum.WATERCOLOUR;
import static com.aj.effect.EffectEnum.WATERDROPLET;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.android.keyguard.sec.KeyguardEffectViewController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes.dex */
public class UnlockEffectGrid extends Activity implements AdapterView.OnItemClickListener {
    // TODO: add mass tension and other effect names!!!!!!!!!!!! s3 firmware
    static final int[] EffectName = {R.string.unlock_effect_none,
            R.string.unlock_effect_ripple,
            R.string.light_effect,
            R.string.unlock_effect_popping,
            R.string.unlock_effect_watercolor,
            R.string.blind_effect,
            R.string.unlock_effect, // TODO: mass tension
            R.string.unlock_effect_simple_ripple,
            R.string.unlock_effect_brilliant_ring,
            R.string.brilliant_cut,
            R.string.unlock_effect_montblanc,
            R.string.unlock_effect_abstract,
            R.string.unlock_effect_geometric_mosaic,
            R.string.unlock_effect_liquid,
            R.string.unlock_effect_particle,
            R.string.unlock_effect_colour_droplet};
    private static List<EffectEnum> mModeItem;
    String[] dbValues;
    private GridView mListView;
    private View mTabletView;
    private boolean mIsTablet;
    private int mDefaultUnlock = 0;
    private ImageView mImageView = null;

    private int checked = -1;

    protected static final EffectEnum[] effects = {ABSTRACTTILES, BRILLIANTRING, BRILLIANTCUT, LIGHTING,
            BLIND, SPARKLINGBUBBLES, POPPINGCOLOURS, COLOURDROPLET, WATERDROPLET, WATERCOLOUR,
            RIPPLE, INDIGODIFFUSION, GEOMETRICMOSAIC, POPPINGGOODLOCK, RECTANGLETRAVELLER, BOUNCINGCOLOR
    };

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        this.mIsTablet = Utils.isTablet();
        super.onCreate(savedInstanceState);
        if (!this.mIsTablet) {
            setContentView(R.layout.new_lockscreen_preview);
            if (getActionBar() != null) {
                getActionBar().setDisplayHomeAsUpEnabled(true);
                getActionBar().setHomeButtonEnabled(true);
            }
            this.mListView = findViewById(android.R.id.list);
            populateUnlockEffectsOptions();
            GridAdapter adapter = new GridAdapter(this, mModeItem);
            this.mListView.setAdapter(adapter);
            this.mListView.setOnItemClickListener(this);
            this.mListView.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
            updateImageResource();
        } else {
            setVisible(false);
            createDialogforTablet();
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
            SettingsManager.putInt(UnlockEffectGrid.this, getContentResolver(), "lockscreen_ripple_effect", mDefaultUnlock);
            Log.d("UnlockEffect", "lockscreen_ripple_effect DB Value : " + mDefaultUnlock);
            KeyguardEffectViewController.getInstance(UnlockEffectGrid.this).handleWallpaperTypeChanged();
            UnlockEffectGrid.this.finish();
        });
        // from class: com.android.settings.UnlockEffect.2
// android.content.DialogInterface.OnClickListener
        alertDialogBuilder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) (dialog, id) -> UnlockEffectGrid.this.finish());
        final AlertDialog alertDialog = alertDialogBuilder.create();
        // from class: com.android.settings.UnlockEffect.3
// android.content.DialogInterface.OnCancelListener
        alertDialog.setOnCancelListener((DialogInterface.OnCancelListener) dialog -> {
            alertDialog.dismiss();
            UnlockEffectGrid.this.finish();
        });
        alertDialog.show();
    }

    private void initViewforTablet() {
        this.mListView = (GridView) this.mTabletView.findViewById(android.R.id.list);
        this.mImageView = (ImageView) this.mTabletView.findViewById(R.id.preview_image);
        populateUnlockEffectsOptions();
        GridAdapter adapter = new GridAdapter(this, mModeItem);
        this.mListView.setAdapter(adapter);
        this.mListView.setOnItemClickListener(this);
        this.mListView.setOverScrollMode(1);
        updateImageResource();
    }

    private void updateImageResource() {
        this.mDefaultUnlock = SettingsManager.getInt(UnlockEffectGrid.this, getContentResolver(), "lockscreen_ripple_effect", Utils.defaultUnlock);
        for (int i = 0; i < mModeItem.size(); i++) {
            if (mModeItem.get(i).assigned == this.mDefaultUnlock) {
                ((GridAdapter)mListView.getAdapter()).setSelection(i);
                this.mListView.setItemChecked(i, true);
                checked = i;
                //this.mImageView.setImageResource(this.backgroundImage[i]);
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
            //this.mImageView.setImageResource(this.backgroundImage[idx]);
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.d("UnlockEffect", "ArrayIndexOutOfBoundsException Occured.  set to Popping Colour.");
        }
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //this.mImageView.setImageResource(this.backgroundImage[position]);
        int effect = mModeItem.get(mListView.getCheckedItemPosition()).assigned;
        ((GridAdapter)parent.getAdapter()).setSelection(position);

        //((CheckedTextView) ((View) parent.getItemAtPosition(checked)).findViewById(android.R.id.text1)).setChecked(false);
        checked = position;
        if (this.mIsTablet) {
            this.mDefaultUnlock = effect;
        } else {
            SettingsManager.putInt(UnlockEffectGrid.this, getContentResolver(), "lockscreen_ripple_effect", Integer.parseInt(this.dbValues[this.mListView.getCheckedItemPosition()]));
        }
        controller.handleWallpaperTypeChanged();
        Log.d("UnlockEffect", "lockscreen_ripple_effect DB Value : " + effect); //Settings.System.getInt(getContentResolver(), "lockscreen_ripple_effect", 0));
    }

    // TODO: rework
    void populateUnlockEffectsOptions() {
        List<EffectEnum> available = new LinkedList<>(Arrays.asList(effects));
        List<EffectEnum> aChangedEffect = new ArrayList<>();
        /*aChangedEffectEntry.add(getResources().getString(R.string.unlock_effect_none));
        aChangedEffectEntryValue.add("0");
        int ctr2 = 1;
        this.backgroundImage[0] = R.drawable.setting_preview_unlock_none;*/
        if (available.contains(INDIGODIFFUSION)) {//Utils.hasPackage(this, "com.sec.android.app.montblanc")) {
            aChangedEffect.add(INDIGODIFFUSION);
            available.remove(INDIGODIFFUSION);
        }
        if (available.contains(COLOURDROPLET)) {
            aChangedEffect.add(COLOURDROPLET);
            available.remove(COLOURDROPLET);
        }
        if (available.contains(WATERDROPLET)) {
            aChangedEffect.add(WATERDROPLET);
            available.remove(WATERDROPLET);
        }
        if (available.contains(SPARKLINGBUBBLES)) {
            aChangedEffect.add(SPARKLINGBUBBLES);
            available.remove(SPARKLINGBUBBLES);
        }
        if (available.contains(ABSTRACTTILES)) {
            aChangedEffect.add(ABSTRACTTILES);
            available.remove(ABSTRACTTILES);
        }
        if (available.contains(GEOMETRICMOSAIC)) {
            aChangedEffect.add(GEOMETRICMOSAIC);
            available.remove(GEOMETRICMOSAIC);
        }
        if (available.contains(BRILLIANTRING)) {
            aChangedEffect.add(BRILLIANTRING);
            available.remove(BRILLIANTRING);
        }
        if (available.contains(POPPINGCOLOURS)) {
            aChangedEffect.add(POPPINGCOLOURS);
            available.remove(POPPINGCOLOURS);
        }
        if (available.contains(WATERCOLOUR)) {
            aChangedEffect.add(WATERCOLOUR);
            available.remove(WATERCOLOUR);
        }
        if (available.contains(RIPPLE)) {
            aChangedEffect.add(RIPPLE);
            available.remove(RIPPLE);
        }
        aChangedEffect.addAll(available);
        mModeItem = aChangedEffect;
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

    KeyguardEffectViewController controller = KeyguardEffectViewController.getInstance(UnlockEffectGrid.this);

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