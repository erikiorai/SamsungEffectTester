package com.android.systemui.mediaprojection.appselector.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowMetrics;
import androidx.core.content.ContextCompat;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$dimen;
import com.android.systemui.mediaprojection.appselector.data.RecentTask;
import com.android.systemui.shared.recents.model.ThumbnailData;
import com.android.systemui.shared.recents.utilities.PreviewPositionHelper;
import com.android.systemui.shared.recents.utilities.Utilities;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/view/MediaProjectionTaskView.class */
public final class MediaProjectionTaskView extends View {
    public final Paint backgroundPaint;
    public BitmapShader bitmapShader;
    public final int cornerRadius;
    public final int defaultBackgroundColor;
    public final Paint paint;
    public final PreviewPositionHelper previewPositionHelper;
    public final Rect previewRect;
    public RecentTask task;
    public ThumbnailData thumbnailData;
    public final WindowManager windowManager;

    public MediaProjectionTaskView(Context context) {
        this(context, null, 0, 6, null);
    }

    public MediaProjectionTaskView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public MediaProjectionTaskView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{16844002});
        int color = obtainStyledAttributes.getColor(0, -16777216);
        obtainStyledAttributes.recycle();
        this.defaultBackgroundColor = color;
        Object systemService = ContextCompat.getSystemService(context, WindowManager.class);
        Intrinsics.checkNotNull(systemService);
        this.windowManager = (WindowManager) systemService;
        this.paint = new Paint(1);
        Paint paint = new Paint(1);
        paint.setColor(color);
        this.backgroundPaint = paint;
        this.cornerRadius = context.getResources().getDimensionPixelSize(R$dimen.media_projection_app_selector_task_rounded_corners);
        this.previewPositionHelper = new PreviewPositionHelper();
        this.previewRect = new Rect();
    }

    public /* synthetic */ MediaProjectionTaskView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public final void bindTask(RecentTask recentTask, ThumbnailData thumbnailData) {
        Integer colorBackground;
        this.task = recentTask;
        this.thumbnailData = thumbnailData;
        int intValue = ((recentTask == null || (colorBackground = recentTask.getColorBackground()) == null) ? -16777216 : colorBackground.intValue()) | (-16777216);
        this.paint.setColor(intValue);
        this.backgroundPaint.setColor(intValue);
        refresh();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        float width = getWidth();
        float height = getHeight() - 1;
        int i = this.cornerRadius;
        canvas.drawRoundRect(ActionBarShadowController.ELEVATION_LOW, 1.0f, width, height, i, i, this.backgroundPaint);
        boolean z = true;
        if (this.task != null) {
            z = true;
            if (this.bitmapShader != null) {
                z = this.thumbnailData == null;
            }
        }
        if (z) {
            return;
        }
        float width2 = getWidth();
        float height2 = getHeight();
        int i2 = this.cornerRadius;
        canvas.drawRoundRect(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, width2, height2, i2, i2, this.paint);
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        updateThumbnailMatrix();
        invalidate();
    }

    public final void refresh() {
        ThumbnailData thumbnailData = this.thumbnailData;
        Bitmap bitmap = thumbnailData != null ? thumbnailData.thumbnail : null;
        if (bitmap != null) {
            bitmap.prepareToDraw();
            Shader.TileMode tileMode = Shader.TileMode.CLAMP;
            BitmapShader bitmapShader = new BitmapShader(bitmap, tileMode, tileMode);
            this.bitmapShader = bitmapShader;
            this.paint.setShader(bitmapShader);
            updateThumbnailMatrix();
        } else {
            this.bitmapShader = null;
            this.paint.setShader(null);
        }
        invalidate();
    }

    public final void updateThumbnailMatrix() {
        ThumbnailData thumbnailData;
        Display display;
        int i = 0;
        this.previewPositionHelper.setOrientationChanged(false);
        BitmapShader bitmapShader = this.bitmapShader;
        if (bitmapShader == null || (thumbnailData = this.thumbnailData) == null || (display = getContext().getDisplay()) == null) {
            return;
        }
        WindowMetrics maximumWindowMetrics = this.windowManager.getMaximumWindowMetrics();
        this.previewRect.set(0, 0, thumbnailData.thumbnail.getWidth(), thumbnailData.thumbnail.getHeight());
        int rotation = display.getRotation();
        int width = maximumWindowMetrics.getBounds().width();
        int height = maximumWindowMetrics.getBounds().height();
        boolean z = getLayoutDirection() == 1;
        boolean isTablet = Utilities.isTablet(getContext());
        if (isTablet) {
            i = getResources().getDimensionPixelSize(17105567);
        }
        this.previewPositionHelper.updateThumbnailMatrix(this.previewRect, thumbnailData, getMeasuredWidth(), getMeasuredHeight(), width, height, i, isTablet, rotation, z);
        bitmapShader.setLocalMatrix(this.previewPositionHelper.getMatrix());
        this.paint.setShader(bitmapShader);
    }
}