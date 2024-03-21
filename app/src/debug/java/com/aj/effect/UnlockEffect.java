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
import android.content.res.Resources;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.keyguard.sec.KeyguardEffectViewController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes.dex */
public class UnlockEffect extends Activity implements AdapterView.OnItemClickListener {
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
    private static String[] mModeItem;
    private RadioAdapter adapter;
    int[] backgroundImage;
    String[] dbValues;
    private ListView mListView;
    private View mTabletView;
    private boolean mIsTablet;
    private int mDefaultUnlock = 0;
    private ImageView mImageView = null;

    private final EffectEnum[] effects = {ABSTRACTTILES, BRILLIANTRING, BRILLIANTCUT, LIGHTING,
            BLIND, SPARKLINGBUBBLES, POPPINGCOLOURS, COLOURDROPLET, WATERDROPLET, WATERCOLOUR,
            RIPPLE, INDIGODIFFUSION, GEOMETRICMOSAIC, POPPINGGOODLOCK, RECTANGLETRAVELLER, BOUNCINGCOLOR
    };

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        this.mIsTablet = Utils.isTablet(this);
        ImageView imageViewforkeyboard;
        super.onCreate(savedInstanceState);
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
            if (Utils.isRTL(this)) {
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
        alertDialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.android.settings.UnlockEffect.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int id) {
                MainActivity.effect = mDefaultUnlock; // TODO Settings.System.putInt(UnlockEffect.this.getContentResolver(), "lockscreen_ripple_effect", UnlockEffect.this.mDefaultUnlock);
                Log.d("UnlockEffect", "lockscreen_ripple_effect DB Value : " + UnlockEffect.this.mDefaultUnlock);
                KeyguardEffectViewController.getInstance(UnlockEffect.this).handleWallpaperTypeChanged();
                UnlockEffect.this.finish();
            }
        });
        alertDialogBuilder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.android.settings.UnlockEffect.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int id) {
                UnlockEffect.this.finish();
            }
        });
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.android.settings.UnlockEffect.3
            @Override // android.content.DialogInterface.OnCancelListener
            public void onCancel(DialogInterface dialog) {
                alertDialog.dismiss();
                UnlockEffect.this.finish();
            }
        });
        alertDialog.show();
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
        this.mDefaultUnlock = MainActivity.effect; // TODO Settings.System.getInt(getContentResolver(), "lockscreen_ripple_effect", 0);
        for (int i = 0; i < this.dbValues.length; i++) {
            if (Integer.parseInt(this.dbValues[i]) == this.mDefaultUnlock) {
                this.mListView.setItemChecked(i, true);
                this.mImageView.setImageResource(this.backgroundImage[i]);
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
            this.mImageView.setImageResource(this.backgroundImage[idx]);
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.d("UnlockEffect", "ArrayIndexOutOfBoundsException Occured.  set to Popping Colour.");
        }
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.mImageView.setImageResource(this.backgroundImage[position]);
        int effect = Integer.parseInt(this.dbValues[this.mListView.getCheckedItemPosition()]);
        if (this.mIsTablet) {
            this.mDefaultUnlock = effect;
        } else {
            MainActivity.effect = effect; //TODO Settings.System.putInt(getContentResolver(), "lockscreen_ripple_effect", Integer.parseInt(this.dbValues[this.mListView.getCheckedItemPosition()]));
        }
        controller.handleWallpaperTypeChanged();;
        Log.d("UnlockEffect", "lockscreen_ripple_effect DB Value : " + effect); //Settings.System.getInt(getContentResolver(), "lockscreen_ripple_effect", 0));
    }

    // TODO: rework
    void populateUnlockEffectsOptions() {
        int ctr = 0;
        List<EffectEnum> available = new LinkedList<>(Arrays.asList(effects));
        List<String> aChangedEffectEntry = new ArrayList<>();
        List<String> aChangedEffectEntryValue = new ArrayList<>();
        this.backgroundImage = new int[16];
        /*aChangedEffectEntry.add(getResources().getString(R.string.unlock_effect_none));
        aChangedEffectEntryValue.add("0");
        int ctr2 = 1;*/
        this.backgroundImage[0] = R.drawable.setting_preview_unlock_none;
        if (available.contains(INDIGODIFFUSION)) {//Utils.hasPackage(this, "com.sec.android.app.montblanc")) {
            aChangedEffectEntry.add(getResources().getString(R.string.unlock_effect_montblanc));
            aChangedEffectEntryValue.add("10");
            available.remove(INDIGODIFFUSION);
            this.backgroundImage[ctr] = R.drawable.setting_preview_unlock_montblanc;
            ctr++;
        }
        if (available.contains(COLOURDROPLET)) {
            aChangedEffectEntry.add(getResources().getString(R.string.unlock_effect_colour_droplet));
            aChangedEffectEntryValue.add("15");
            available.remove(COLOURDROPLET);
            this.backgroundImage[ctr] = R.drawable.setting_preview_unlock_liquid;
            ctr++;
        }
        if (available.contains(WATERDROPLET)) {
            aChangedEffectEntry.add(getResources().getString(R.string.unlock_effect_liquid));
            aChangedEffectEntryValue.add("13");
            available.remove(WATERDROPLET);
            this.backgroundImage[ctr] = R.drawable.setting_preview_unlock_liquid_w;
            ctr++;
        }
        if (available.contains(SPARKLINGBUBBLES)) {
            aChangedEffectEntry.add(getResources().getString(R.string.unlock_effect_particle));
            aChangedEffectEntryValue.add("14");
            available.remove(SPARKLINGBUBBLES);
            this.backgroundImage[ctr] = R.drawable.setting_preview_unlock_particle;
            ctr++;
        }
        if (available.contains(ABSTRACTTILES)) {
            aChangedEffectEntry.add(getResources().getString(R.string.unlock_effect_abstract));
            aChangedEffectEntryValue.add("11");
            available.remove(ABSTRACTTILES);
            this.backgroundImage[ctr] = R.drawable.setting_preview_unlock_abstract_tiles;
            ctr++;
        }
        if (available.contains(GEOMETRICMOSAIC)) {
            aChangedEffectEntry.add(getResources().getString(R.string.unlock_effect_geometric_mosaic));
            aChangedEffectEntryValue.add("12");
            available.remove(GEOMETRICMOSAIC);
            this.backgroundImage[ctr] = R.drawable.setting_preview_unlock_geometric_mosaic;
            ctr++;
        }
        if (available.contains(BRILLIANTRING)) {
            aChangedEffectEntry.add(getResources().getString(R.string.unlock_effect_brilliant_ring));
            aChangedEffectEntryValue.add("8");
            available.remove(BRILLIANTRING);
            this.backgroundImage[ctr] = R.drawable.setting_preview_unlock_brilliantring;
            ctr++;
        }
        if (available.contains(POPPINGCOLOURS)) {
            aChangedEffectEntry.add(getResources().getString(R.string.unlock_effect_popping));
            aChangedEffectEntryValue.add("3");
            available.remove(POPPINGCOLOURS);
            this.backgroundImage[ctr] = R.drawable.setting_preview_unlock_poppingcolor;
            ctr++;
        }
        if (available.contains(WATERCOLOUR)) {
            aChangedEffectEntry.add(getResources().getString(R.string.unlock_effect_watercolor));
            aChangedEffectEntryValue.add("4");
            available.remove(WATERCOLOUR);
            this.backgroundImage[ctr] = R.drawable.setting_preview_unlock_watercolor;
            ctr++;
        }
        if (available.contains(RIPPLE)) {
            aChangedEffectEntry.add(getResources().getString(R.string.unlock_effect_ripple));
            aChangedEffectEntryValue.add("1");
            available.remove(RIPPLE);
            this.backgroundImage[ctr] = R.drawable.setting_preview_unlock_ripple;
            ctr++;
        }
        for (EffectEnum e : available) {
            aChangedEffectEntry.add(getResources().getString(e.name));
            aChangedEffectEntryValue.add(String.valueOf(e.assigned));
            backgroundImage[ctr] = e.drawable;
            ctr++;
        }
        mModeItem = aChangedEffectEntry.toArray(new String[aChangedEffectEntry.size()]);
        this.dbValues = aChangedEffectEntryValue.toArray(new String[aChangedEffectEntryValue.size()]);
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
                if (Utils.isTablet(this.getContext())) {
                    convertView = li.inflate(R.layout.list_item_with_radiobox_for_dialog, parent, false);
                } else {
                    convertView = li.inflate(R.layout.list_item_with_radiobox, parent, false);
                }
            }
            return super.getView(position, convertView, parent);
        }
    }

    KeyguardEffectViewController controller = KeyguardEffectViewController.getInstance(UnlockEffect.this);

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