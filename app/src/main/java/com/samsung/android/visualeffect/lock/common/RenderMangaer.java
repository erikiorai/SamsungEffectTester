package com.samsung.android.visualeffect.lock.common;

import android.content.Context;
import com.samsung.android.visualeffect.lock.brilliantring.BrilliantRingRenderer;
import com.samsung.android.visualeffect.common.GLTextureView;

public class RenderMangaer {
    public static GLTextureViewRenderer getInstance(Context context, int argv, GLTextureView view) {
        return new BrilliantRingRenderer(context, view);
    }
}
