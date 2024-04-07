package com.aj.effect;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.keyguard.sec.KeyguardEffectViewBase;
import com.android.keyguard.sec.KeyguardEffectViewBouncingColor;
import com.android.keyguard.sec.KeyguardEffectViewController;
import com.android.keyguard.sec.KeyguardEffectViewPoppingColor;
import com.android.keyguard.sec.KeyguardEffectViewRectangleTraveller;
import com.android.keyguard.sec.KeyguardUnlockView;

public class MainActivity extends Activity {

    public static Bitmap bitm;
    public static Canvas canv;
    public Button multiactionButton;
    public static boolean unlockBool;

    public static int effect = EffectEnum.NONE.assigned;

    ImageView imgView;

    KeyguardEffectViewController controller;
    KeyguardUnlockView mUnlockView;
    FrameLayout mBackgroundRootLayout;
    FrameLayout mForegroundRootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        w.addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);

        int width;
        int height;

        Rect viewRect = Utils.getViewRect(new DisplayMetrics(), getWindowManager());
        width = viewRect.width();
        height = viewRect.height();

        bitm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canv = new Canvas(bitm);

        setContentView(R.layout.activity_main);

        imgView = findViewById(R.id.imageView);

        controller = KeyguardEffectViewController.getInstance(this);
        mUnlockView = findViewById(R.id.keyguard_unlock_view);
        mBackgroundRootLayout = findViewById(R.id.keyguard_effect_behind);
        mForegroundRootLayout = findViewById(R.id.keyguard_effect_front);

        multiactionButton = findViewById(R.id.multiact);
        Drawable realWall = getDrawable(R.drawable.bluesky); //wallpaperManager.getFastDrawable();
        Drawable prev = getDrawable(R.drawable.setting_preview_unlock);
        multiactionButton.setOnClickListener(v -> {
            if (imgView.getDrawable() == prev)
                imgView.setImageDrawable(realWall);
            else
                imgView.setImageDrawable(prev);

            imgView.draw(canv);
            controller.handleWallpaperImageChanged();
        });
        multiactionButton.setOnLongClickListener(v -> {
            Intent picker = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            picker.addCategory(Intent.CATEGORY_OPENABLE);
            picker.setType("image/*");
            startActivityForResult(picker, 0);
            return true;
        });

        Button affordance = findViewById(R.id.affordance);
        affordance.setOnClickListener(v -> mUnlockView.showUnlockAffordance());

        Switch unlock = findViewById(R.id.unlock);
        unlockBool = unlock.isChecked();
        unlock.setOnCheckedChangeListener((compoundButton, b) -> unlockBool = b);

        LinearLayout buttons = findViewById(R.id.buttons);
        Switch palette = buttons.findViewById(R.id.palette);
        Utils.usePCColorPalette = palette.isChecked();
        palette.setOnCheckedChangeListener((c, b) -> Utils.usePCColorPalette = b);
        Switch customActions = buttons.findViewById(R.id.customact);
        Utils.customActions = customActions.isChecked();
        customActions.setOnCheckedChangeListener((c, b) -> Utils.customActions = b);

        Button effectSwitch = findViewById(R.id.effectsw);
        effectSwitch.setText(R.string.unlock_effect);
        effectSwitch.setOnClickListener(view -> {
            controller.playLockSound();
            switchActivity(this);
        });

        TextView clock = findViewById(R.id.clock);
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) clock.getLayoutParams();
        int fontSize = this.getResources().getDimensionPixelSize(R.dimen.kg_singleclock_time_text_size_normal);
        lp.leftMargin = -((int) (fontSize / 15.0f));
        clock.setLayoutParams(lp);
        controller.setEffectLayout(mBackgroundRootLayout, mForegroundRootLayout, null);
    }

    public static void switchActivity(Context context) {
        Intent intent = new Intent(context, UnlockEffect.class);
        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            imgView.setImageURI(data.getData());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        controller.screenTurnedOff();
    }

    @Override
    public void onResume() {
        super.onResume();
        imgView.draw(canv);
        controller.handleWallpaperImageChanged();

        mUnlockView.reset();

        controller.screenTurnedOn();
        controller.show();
        mUnlockView.showUnlockAffordance();

        KeyguardEffectViewBase effect = controller.getUnlockEffect();
        if (effect instanceof KeyguardEffectViewPoppingColor ||
                effect instanceof KeyguardEffectViewBouncingColor ||
                effect instanceof KeyguardEffectViewRectangleTraveller) {
            findViewById(R.id.palette).setVisibility(View.VISIBLE);
            findViewById(R.id.customact).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.palette).setVisibility(View.GONE);
            findViewById(R.id.customact).setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //imgView.draw(canv);
        //controller.handleWallpaperTypeChanged();
    }
}