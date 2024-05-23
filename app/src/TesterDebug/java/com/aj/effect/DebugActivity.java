package com.aj.effect;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

public class DebugActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);

        TextView version = findViewById(R.id.vers);
        version.setText(BuildConfig.VERSION_NAME);

        TextView debug = findViewById(R.id.debug_lines);
        StringBuilder lines = new StringBuilder(Utils.getSecUiVersionStg() + "\n");
        lines.append("Android version ").append(Build.VERSION.RELEASE).append(", API level ").append(Build.VERSION.SDK_INT).append("\n");
        debug.setText(lines);
    }
}