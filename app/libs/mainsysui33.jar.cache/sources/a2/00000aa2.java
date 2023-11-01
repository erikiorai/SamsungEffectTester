package com.airbnb.lottie.model.layer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.LPaint;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.LottieValueCallback;
import com.android.settingslib.widget.ActionBarShadowController;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/model/layer/ImageLayer.class */
public class ImageLayer extends BaseLayer {
    public BaseKeyframeAnimation<ColorFilter, ColorFilter> colorFilterAnimation;
    public final Rect dst;
    public final Paint paint;
    public final Rect src;

    public ImageLayer(LottieDrawable lottieDrawable, Layer layer) {
        super(lottieDrawable, layer);
        this.paint = new LPaint(3);
        this.src = new Rect();
        this.dst = new Rect();
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer, com.airbnb.lottie.model.KeyPathElement
    public <T> void addValueCallback(T t, LottieValueCallback<T> lottieValueCallback) {
        super.addValueCallback(t, lottieValueCallback);
        if (t == LottieProperty.COLOR_FILTER) {
            if (lottieValueCallback == null) {
                this.colorFilterAnimation = null;
            } else {
                this.colorFilterAnimation = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            }
        }
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer
    public void drawLayer(Canvas canvas, Matrix matrix, int i) {
        Bitmap bitmap = getBitmap();
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        float dpScale = Utils.dpScale();
        this.paint.setAlpha(i);
        BaseKeyframeAnimation<ColorFilter, ColorFilter> baseKeyframeAnimation = this.colorFilterAnimation;
        if (baseKeyframeAnimation != null) {
            this.paint.setColorFilter(baseKeyframeAnimation.getValue());
        }
        canvas.save();
        canvas.concat(matrix);
        this.src.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
        this.dst.set(0, 0, (int) (bitmap.getWidth() * dpScale), (int) (bitmap.getHeight() * dpScale));
        canvas.drawBitmap(bitmap, this.src, this.dst, this.paint);
        canvas.restore();
    }

    public final Bitmap getBitmap() {
        return this.lottieDrawable.getImageAsset(this.layerModel.getRefId());
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer, com.airbnb.lottie.animation.content.DrawingContent
    public void getBounds(RectF rectF, Matrix matrix, boolean z) {
        super.getBounds(rectF, matrix, z);
        Bitmap bitmap = getBitmap();
        if (bitmap != null) {
            rectF.set(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, bitmap.getWidth() * Utils.dpScale(), bitmap.getHeight() * Utils.dpScale());
            this.boundsMatrix.mapRect(rectF);
        }
    }
}