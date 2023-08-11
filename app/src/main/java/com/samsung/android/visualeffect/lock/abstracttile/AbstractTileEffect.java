package com.samsung.android.visualeffect.lock.abstracttile;

import android.content.Context;
import com.samsung.android.visualeffect.lock.common.LockBGEffect;

/* loaded from: classes.dex */
public class AbstractTileEffect extends LockBGEffect {
    public AbstractTileEffect(Context context) {
        super(context);
        this.TAG = "AbstractTile View";
        this.veContext = context;
        setEffectRenderer(0);
    }
}