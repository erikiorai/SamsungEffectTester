package com.android.systemui.screenshot;

import android.graphics.Bitmap;
import android.graphics.HardwareRenderer;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.RenderNode;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import com.android.internal.util.CallbackRegistry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ImageTileSet.class */
public class ImageTileSet {
    public CallbackRegistry<OnContentChangedListener, ImageTileSet, Rect> mContentListeners;
    public final Handler mHandler;
    public final List<ImageTile> mTiles = new ArrayList();
    public final Region mRegion = new Region();

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ImageTileSet$OnContentChangedListener.class */
    public interface OnContentChangedListener {
        void onContentChanged();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ImageTileSet$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$_LStMIBHaVYrT9fDZ1BmYXPm9Ow(ImageTileSet imageTileSet, ImageTile imageTile) {
        imageTileSet.lambda$addTile$0(imageTile);
    }

    public ImageTileSet(Handler handler) {
        this.mHandler = handler;
    }

    public void addOnContentChangedListener(OnContentChangedListener onContentChangedListener) {
        if (this.mContentListeners == null) {
            this.mContentListeners = new CallbackRegistry<>(new CallbackRegistry.NotifierCallback<OnContentChangedListener, ImageTileSet, Rect>() { // from class: com.android.systemui.screenshot.ImageTileSet.1
                {
                    ImageTileSet.this = this;
                }

                public void onNotifyCallback(OnContentChangedListener onContentChangedListener2, ImageTileSet imageTileSet, int i, Rect rect) {
                    onContentChangedListener2.onContentChanged();
                }
            });
        }
        this.mContentListeners.add(onContentChangedListener);
    }

    /* renamed from: addTile */
    public void lambda$addTile$0(final ImageTile imageTile) {
        if (!this.mHandler.getLooper().isCurrentThread()) {
            this.mHandler.post(new Runnable() { // from class: com.android.systemui.screenshot.ImageTileSet$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ImageTileSet.$r8$lambda$_LStMIBHaVYrT9fDZ1BmYXPm9Ow(ImageTileSet.this, imageTile);
                }
            });
            return;
        }
        this.mTiles.add(imageTile);
        this.mRegion.op(imageTile.getLocation(), this.mRegion, Region.Op.UNION);
        notifyContentChanged();
    }

    public void clear() {
        if (this.mTiles.isEmpty()) {
            return;
        }
        this.mRegion.setEmpty();
        Iterator<ImageTile> it = this.mTiles.iterator();
        while (it.hasNext()) {
            it.next().close();
            it.remove();
        }
        notifyContentChanged();
    }

    public ImageTile get(int i) {
        return this.mTiles.get(i);
    }

    public int getBottom() {
        return this.mRegion.getBounds().bottom;
    }

    public Drawable getDrawable() {
        return new TiledImageDrawable(this);
    }

    public Rect getGaps() {
        Region region = new Region();
        region.op(this.mRegion.getBounds(), this.mRegion, Region.Op.DIFFERENCE);
        return region.getBounds();
    }

    public int getHeight() {
        return this.mRegion.getBounds().height();
    }

    public int getLeft() {
        return this.mRegion.getBounds().left;
    }

    public int getTop() {
        return this.mRegion.getBounds().top;
    }

    public int getWidth() {
        return this.mRegion.getBounds().width();
    }

    public final void notifyContentChanged() {
        CallbackRegistry<OnContentChangedListener, ImageTileSet, Rect> callbackRegistry = this.mContentListeners;
        if (callbackRegistry != null) {
            callbackRegistry.notifyCallbacks(this, 0, (Object) null);
        }
    }

    public int size() {
        return this.mTiles.size();
    }

    public Bitmap toBitmap() {
        return toBitmap(new Rect(0, 0, getWidth(), getHeight()));
    }

    public Bitmap toBitmap(Rect rect) {
        if (this.mTiles.isEmpty()) {
            return null;
        }
        RenderNode renderNode = new RenderNode("Bitmap Export");
        renderNode.setPosition(0, 0, rect.width(), rect.height());
        RecordingCanvas beginRecording = renderNode.beginRecording();
        Drawable drawable = getDrawable();
        drawable.setBounds(rect);
        drawable.draw(beginRecording);
        renderNode.endRecording();
        return HardwareRenderer.createHardwareBitmap(renderNode, rect.width(), rect.height());
    }
}