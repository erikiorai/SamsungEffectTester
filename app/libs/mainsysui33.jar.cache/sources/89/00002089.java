package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.android.settingslib.Utils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$color;
import com.android.systemui.R$dimen;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/DataUsageGraph.class */
public class DataUsageGraph extends View {
    public long mLimitLevel;
    public final int mMarkerWidth;
    public long mMaxLevel;
    public final int mOverlimitColor;
    public final Paint mTmpPaint;
    public final RectF mTmpRect;
    public final int mTrackColor;
    public final int mUsageColor;
    public long mUsageLevel;
    public final int mWarningColor;
    public long mWarningLevel;

    public DataUsageGraph(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTmpRect = new RectF();
        this.mTmpPaint = new Paint();
        Resources resources = context.getResources();
        this.mTrackColor = Utils.getColorStateListDefaultColor(context, R$color.data_usage_graph_track);
        this.mWarningColor = Utils.getColorStateListDefaultColor(context, R$color.data_usage_graph_warning);
        this.mUsageColor = Utils.getColorAccentDefaultColor(context);
        this.mOverlimitColor = Utils.getColorErrorDefaultColor(context);
        this.mMarkerWidth = resources.getDimensionPixelSize(R$dimen.data_usage_graph_marker_width);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        int i;
        super.onDraw(canvas);
        RectF rectF = this.mTmpRect;
        Paint paint = this.mTmpPaint;
        int width = getWidth();
        int height = getHeight();
        long j = this.mLimitLevel;
        boolean z = j > 0 && this.mUsageLevel > j;
        float f = width;
        float f2 = (float) this.mUsageLevel;
        long j2 = this.mMaxLevel;
        float f3 = (f2 / ((float) j2)) * f;
        if (z) {
            float f4 = ((float) j) / ((float) j2);
            f3 = Math.min(Math.max((f4 * f) - (i / 2), this.mMarkerWidth), width - (this.mMarkerWidth * 2));
            rectF.set(this.mMarkerWidth + f3, ActionBarShadowController.ELEVATION_LOW, f, height);
            paint.setColor(this.mOverlimitColor);
            canvas.drawRect(rectF, paint);
        } else {
            rectF.set(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, f, height);
            paint.setColor(this.mTrackColor);
            canvas.drawRect(rectF, paint);
        }
        float f5 = height;
        rectF.set(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, f3, f5);
        paint.setColor(this.mUsageColor);
        canvas.drawRect(rectF, paint);
        float min = Math.min(Math.max((f * (((float) this.mWarningLevel) / ((float) this.mMaxLevel))) - (this.mMarkerWidth / 2), (float) ActionBarShadowController.ELEVATION_LOW), width - this.mMarkerWidth);
        rectF.set(min, ActionBarShadowController.ELEVATION_LOW, this.mMarkerWidth + min, f5);
        paint.setColor(this.mWarningColor);
        canvas.drawRect(rectF, paint);
    }
}