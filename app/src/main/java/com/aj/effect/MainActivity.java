package com.aj.effect;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
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

import com.android.keyguard.sec.KeyguardEffectViewController;
import com.android.keyguard.sec.KeyguardUnlockView;

public class MainActivity extends Activity {

    public static Bitmap bitm;
    public static Canvas canv;
    public static Button multiactionButton;
    public static boolean unlockBool;
    public static int effect;

    KeyguardEffectViewController controller;
    KeyguardUnlockView mUnlockView;
    FrameLayout mBackgroundRootLayout;
    FrameLayout mForegroundRootLayout;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        w.addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        /*EffectView mEffectView = new EffectView(this);
        KeyguardEffectViewBase mEffectView = new KeyguardEffectViewBrilliantRing(this);
        KeyguardUnlockView mUnlockView = new KeyguardUnlockView(this);
        mEffectView.setEffect(7);
        if (mEffectView != null) {
            mEffectView.reset();
            mEffectView.show();
            mEffectView.update();
        }
        mUnlockView.setUnlockView(mEffectView); */

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        bitm = Bitmap.createBitmap(metrics.widthPixels, metrics.heightPixels, Bitmap.Config.ARGB_8888);
        canv = new Canvas(bitm);

        setContentView(R.layout.activity_main);

        LinearLayout layout = findViewById(R.id.buttons);
        ImageView imgView = findViewById(R.id.imageView);

        controller = KeyguardEffectViewController.getInstance(this);
        mUnlockView = findViewById(R.id.keyguard_unlock_view);
        mBackgroundRootLayout = findViewById(R.id.keyguard_effect_behind);
        mForegroundRootLayout = findViewById(R.id.keyguard_effect_front);

        /*KeyguardUnlockView mUnlockView = findViewById(R.id.keyguard_unlock_view);
        Handler handler = new Handler();*/

        multiactionButton = findViewById(R.id.multiact);
        multiactionButton.setOnClickListener(v -> {
            mUnlockView.reset();
            controller.reset();
            multiactionButton.setAlpha(0.5F);
            multiactionButton.setText(R.string.wall);
        });
        //WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        Drawable realWall = getDrawable(R.drawable.bluesky); //wallpaperManager.getFastDrawable();
        Drawable wall = getDrawable(R.drawable.wall);
        Drawable wall1 = getDrawable(R.drawable.wall1);
        multiactionButton.setOnLongClickListener(v -> {
            if (imgView.getDrawable() == wall)
                imgView.setImageDrawable(wall1);
            else if (imgView.getDrawable() == wall1)
                imgView.setImageDrawable(realWall);
            else
                imgView.setImageDrawable(wall);

            imgView.draw(canv);
            controller.update();
            return true;
        });

        Button affordance = findViewById(R.id.affordance);
        affordance.setOnClickListener(v -> {
            mUnlockView.showUnlockAffordance();
        });

        Switch unlock = findViewById(R.id.unlock);
        unlockBool = unlock.isChecked();
        unlock.setOnCheckedChangeListener((compoundButton, b) -> unlockBool = b);

        Button effectSwitch = findViewById(R.id.effectsw);
        effectSwitch.setText(R.string.unlock_effect_abstract);
        effect = KeyguardEffectViewController.EFFECT_ABSTRACTTILE;
        effectSwitch.setOnClickListener(view -> {
            if (effect == KeyguardEffectViewController.EFFECT_ABSTRACTTILE) {
                effectSwitch.setText(R.string.unlock_effect_brilliant_ring);
                effect = KeyguardEffectViewController.EFFECT_BRILLIANTRING;
            } else if (effect == KeyguardEffectViewController.EFFECT_BRILLIANTRING) {
                effectSwitch.setText(R.string.light_effect);
                effect = KeyguardEffectViewController.EFFECT_LIGHT;
            } else {
                effectSwitch.setText(R.string.unlock_effect_abstract);
                effect = KeyguardEffectViewController.EFFECT_ABSTRACTTILE;
            }
            controller.handleWallpaperTypeChanged();
            controller.show();
            mUnlockView.showUnlockAffordance();
        });

        controller.setEffectLayout(mBackgroundRootLayout, mForegroundRootLayout, null);
        /*mBackgroundRootLayout.addView(controller, -1, -1);
        controller.update();
        /*getWindow().setBackgroundDrawableResource(R.drawable.wall);
        FrameLayout view = findViewById(R.id.lay);
        view.addView(mUnlockView); */
    }

    @Override
    public void onStart() {
        super.onStart();
        //controller.setEffectLayout(mBackgroundRootLayout, mBackgroundRootLayout, null);
        //checkChild();

        //findViewById(R.id.imageView).draw(canv);
        //controller.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        controller.screenTurnedOff();
    }

    @Override
    public void onResume() {
        super.onResume();
        mUnlockView.showUnlockAffordance();
        controller.playLockSound();
    }
}