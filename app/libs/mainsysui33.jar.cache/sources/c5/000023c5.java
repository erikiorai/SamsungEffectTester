package com.android.systemui.recents;

import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.drawable.shapes.PathShape;
import com.android.settingslib.widget.ActionBarShadowController;

/* loaded from: mainsysui33.jar:com/android/systemui/recents/TriangleShape.class */
public class TriangleShape extends PathShape {
    public Path mTriangularPath;

    public TriangleShape(Path path, float f, float f2) {
        super(path, f, f2);
        this.mTriangularPath = path;
    }

    public static TriangleShape create(float f, float f2, boolean z) {
        Path path = new Path();
        if (z) {
            path.moveTo(ActionBarShadowController.ELEVATION_LOW, f2);
            path.lineTo(f, f2);
            path.lineTo(f / 2.0f, ActionBarShadowController.ELEVATION_LOW);
            path.close();
        } else {
            path.moveTo(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW);
            path.lineTo(f / 2.0f, f2);
            path.lineTo(f, ActionBarShadowController.ELEVATION_LOW);
            path.close();
        }
        return new TriangleShape(path, f, f2);
    }

    public static TriangleShape createHorizontal(float f, float f2, boolean z) {
        Path path = new Path();
        if (z) {
            path.moveTo(ActionBarShadowController.ELEVATION_LOW, f2 / 2.0f);
            path.lineTo(f, f2);
            path.lineTo(f, ActionBarShadowController.ELEVATION_LOW);
            path.close();
        } else {
            path.moveTo(ActionBarShadowController.ELEVATION_LOW, f2);
            path.lineTo(f, f2 / 2.0f);
            path.lineTo(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW);
            path.close();
        }
        return new TriangleShape(path, f, f2);
    }

    @Override // android.graphics.drawable.shapes.Shape
    public void getOutline(Outline outline) {
        outline.setPath(this.mTriangularPath);
    }
}