package com.aj.effect;

import static android.view.View.INVISIBLE;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.aj.effect.lock.KeyguardEffectViewBrilliantRing;
import com.aj.effect.lock.KeyguardUnlockView;

public class MainActivity extends Activity {

    KeyguardUnlockView mUnlockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        setContentView(R.layout.activity_main);
        mUnlockView = findViewById(R.id.keyguard_unlock_view);
        KeyguardEffectViewBrilliantRing unl = (KeyguardEffectViewBrilliantRing) mUnlockView.getChildAt(0);
        Button reset = findViewById(R.id.button);
        reset.setOnClickListener(v -> {
            unl.reset();
            reset.setVisibility(INVISIBLE);
        });
        mUnlockView.setReset(reset);
        /*getWindow().setBackgroundDrawableResource(R.drawable.wall);
        FrameLayout view = findViewById(R.id.lay);
        view.addView(mUnlockView); */
    }
}