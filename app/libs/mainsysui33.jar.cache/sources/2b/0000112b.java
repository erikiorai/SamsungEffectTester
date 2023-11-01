package com.android.systemui.assist.ui;

import android.content.Context;
import android.graphics.Path;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.assist.ui.CornerPathRenderer;

/* loaded from: mainsysui33.jar:com/android/systemui/assist/ui/CircularCornerPathRenderer.class */
public final class CircularCornerPathRenderer extends CornerPathRenderer {
    public final int mCornerRadiusBottom;
    public final int mCornerRadiusTop;
    public final int mHeight;
    public final Path mPath = new Path();
    public final int mWidth;

    /* renamed from: com.android.systemui.assist.ui.CircularCornerPathRenderer$1  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/assist/ui/CircularCornerPathRenderer$1.class */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$assist$ui$CornerPathRenderer$Corner;

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:11:0x0036 -> B:21:0x0014). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:13:0x003a -> B:19:0x001f). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:15:0x003e -> B:25:0x002a). Please submit an issue!!! */
        static {
            int[] iArr = new int[CornerPathRenderer.Corner.values().length];
            $SwitchMap$com$android$systemui$assist$ui$CornerPathRenderer$Corner = iArr;
            try {
                iArr[CornerPathRenderer.Corner.BOTTOM_LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$systemui$assist$ui$CornerPathRenderer$Corner[CornerPathRenderer.Corner.BOTTOM_RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$android$systemui$assist$ui$CornerPathRenderer$Corner[CornerPathRenderer.Corner.TOP_RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$android$systemui$assist$ui$CornerPathRenderer$Corner[CornerPathRenderer.Corner.TOP_LEFT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public CircularCornerPathRenderer(Context context) {
        this.mCornerRadiusBottom = DisplayUtils.getCornerRadiusBottom(context);
        this.mCornerRadiusTop = DisplayUtils.getCornerRadiusTop(context);
        this.mHeight = DisplayUtils.getHeight(context);
        this.mWidth = DisplayUtils.getWidth(context);
    }

    @Override // com.android.systemui.assist.ui.CornerPathRenderer
    public Path getCornerPath(CornerPathRenderer.Corner corner) {
        int i;
        int i2;
        this.mPath.reset();
        int i3 = AnonymousClass1.$SwitchMap$com$android$systemui$assist$ui$CornerPathRenderer$Corner[corner.ordinal()];
        if (i3 == 1) {
            this.mPath.moveTo(ActionBarShadowController.ELEVATION_LOW, this.mHeight - this.mCornerRadiusBottom);
            Path path = this.mPath;
            int i4 = this.mHeight;
            int i5 = this.mCornerRadiusBottom;
            path.arcTo(ActionBarShadowController.ELEVATION_LOW, i4 - (i5 * 2), i5 * 2, i4, 180.0f, -90.0f, true);
        } else if (i3 == 2) {
            this.mPath.moveTo(this.mWidth - this.mCornerRadiusBottom, this.mHeight);
            Path path2 = this.mPath;
            int i6 = this.mWidth;
            path2.arcTo(i6 - (this.mCornerRadiusBottom * 2), i2 - (i * 2), i6, this.mHeight, 90.0f, -90.0f, true);
        } else if (i3 == 3) {
            this.mPath.moveTo(this.mWidth, this.mCornerRadiusTop);
            Path path3 = this.mPath;
            int i7 = this.mWidth;
            int i8 = this.mCornerRadiusTop;
            path3.arcTo(i7 - (i8 * 2), ActionBarShadowController.ELEVATION_LOW, i7, i8 * 2, ActionBarShadowController.ELEVATION_LOW, -90.0f, true);
        } else if (i3 == 4) {
            this.mPath.moveTo(this.mCornerRadiusTop, ActionBarShadowController.ELEVATION_LOW);
            Path path4 = this.mPath;
            int i9 = this.mCornerRadiusTop;
            path4.arcTo(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, i9 * 2, i9 * 2, 270.0f, -90.0f, true);
        }
        return this.mPath;
    }
}