package com.aj.effect;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DebugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        if (Build.VERSION.SDK_INT < 28) {
            TextView version = findViewById(R.id.vers);
            version.setText(BuildConfig.VERSION_NAME);
        }

        TextView debug = findViewById(R.id.debug_lines);
        StringBuilder lines = new StringBuilder(Utils.getSecUiVersionStg() + "\n");
        lines.append("Android version ").append(Build.VERSION.RELEASE).append(", API level ").append(Build.VERSION.SDK_INT).append("\n");
        debug.setText(lines);
    }
}