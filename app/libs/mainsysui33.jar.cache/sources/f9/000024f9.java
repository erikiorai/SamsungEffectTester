package com.android.systemui.screenshot;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.android.systemui.screenshot.ImageTileSet;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/TiledImageDrawable.class */
public class TiledImageDrawable extends Drawable {
    public RenderNode mNode;
    public final ImageTileSet mTiles;

    public TiledImageDrawable(ImageTileSet imageTileSet) {
        this.mTiles = imageTileSet;
        imageTileSet.addOnContentChangedListener(new ImageTileSet.OnContentChangedListener() { // from class: com.android.systemui.screenshot.TiledImageDrawable$$ExternalSyntheticLambda0
            @Override // com.android.systemui.screenshot.ImageTileSet.OnContentChangedListener
            public final void onContentChanged() {
                TiledImageDrawable.this.onContentChanged();
            }
        });
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        rebuildDisplayListIfNeeded();
        if (!canvas.isHardwareAccelerated()) {
            Log.d("TiledImageDrawable", "Canvas is not hardware accelerated. Skipping draw!");
            return;
        }
        Rect bounds = getBounds();
        canvas.save();
        canvas.clipRect(0, 0, bounds.width(), bounds.height());
        canvas.translate(-bounds.left, -bounds.top);
        canvas.drawRenderNode(this.mNode);
        canvas.restore();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mTiles.getHeight();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mTiles.getWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -1;
    }

    public final void onContentChanged() {
        RenderNode renderNode = this.mNode;
        if (renderNode != null && renderNode.hasDisplayList()) {
            this.mNode.discardDisplayList();
        }
        invalidateSelf();
    }

    public final void rebuildDisplayListIfNeeded() {
        RenderNode renderNode = this.mNode;
        if (renderNode == null || !renderNode.hasDisplayList()) {
            if (this.mNode == null) {
                this.mNode = new RenderNode("TiledImageDrawable");
            }
            this.mNode.setPosition(0, 0, this.mTiles.getWidth(), this.mTiles.getHeight());
            RecordingCanvas beginRecording = this.mNode.beginRecording();
            beginRecording.translate(-this.mTiles.getLeft(), -this.mTiles.getTop());
            for (int i = 0; i < this.mTiles.size(); i++) {
                ImageTile imageTile = this.mTiles.get(i);
                beginRecording.save();
                beginRecording.translate(imageTile.getLeft(), imageTile.getTop());
                beginRecording.drawRenderNode(imageTile.getDisplayList());
                beginRecording.restore();
            }
            this.mNode.endRecording();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (this.mNode.setAlpha(i / 255.0f)) {
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        throw new IllegalArgumentException("not implemented");
    }
}