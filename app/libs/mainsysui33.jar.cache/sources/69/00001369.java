package com.android.systemui.common.ui.drawable;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* loaded from: mainsysui33.jar:com/android/systemui/common/ui/drawable/CircularDrawable.class */
public final class CircularDrawable extends DrawableWrapper {
    public final Lazy path$delegate;

    public CircularDrawable(Drawable drawable) {
        super(drawable);
        this.path$delegate = LazyKt__LazyJVMKt.lazy(new Function0<Path>() { // from class: com.android.systemui.common.ui.drawable.CircularDrawable$path$2
            /* JADX DEBUG: Method merged with bridge method */
            /* renamed from: invoke */
            public final Path m1785invoke() {
                return new Path();
            }
        });
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.clipPath(getPath());
        Drawable drawable = getDrawable();
        if (drawable != null) {
            drawable.draw(canvas);
        }
        canvas.restore();
    }

    public final Path getPath() {
        return (Path) this.path$delegate.getValue();
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        updateClipPath();
    }

    public final void updateClipPath() {
        getPath().reset();
        getPath().addCircle(getBounds().centerX(), getBounds().centerY(), Math.min(getBounds().width(), getBounds().height()) / 2.0f, Path.Direction.CW);
    }
}