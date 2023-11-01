package com.android.systemui.biometrics;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.util.PathParser;
import com.android.systemui.R$string;
import kotlin.jvm.functions.Function1;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsDrawableKt.class */
public final class UdfpsDrawableKt {
    public static final Function1<Context, ShapeDrawable> defaultFactory = new Function1<Context, ShapeDrawable>() { // from class: com.android.systemui.biometrics.UdfpsDrawableKt$defaultFactory$1
        /* JADX DEBUG: Method merged with bridge method */
        public final ShapeDrawable invoke(Context context) {
            ShapeDrawable shapeDrawable = new ShapeDrawable(new PathShape(PathParser.createPathFromPathData(context.getResources().getString(R$string.config_udfpsIcon)), 72.0f, 72.0f));
            shapeDrawable.mutate();
            shapeDrawable.getPaint().setStyle(Paint.Style.STROKE);
            shapeDrawable.getPaint().setStrokeCap(Paint.Cap.ROUND);
            shapeDrawable.getPaint().setStrokeWidth(3.0f);
            return shapeDrawable;
        }
    };
}