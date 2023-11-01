package com.airbnb.lottie.model.content;

import android.graphics.Paint;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.content.Content;
import com.airbnb.lottie.animation.content.StrokeContent;
import com.airbnb.lottie.model.animatable.AnimatableColorValue;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.layer.BaseLayer;
import java.util.List;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/model/content/ShapeStroke.class */
public class ShapeStroke implements ContentModel {
    public final LineCapType capType;
    public final AnimatableColorValue color;
    public final boolean hidden;
    public final LineJoinType joinType;
    public final List<AnimatableFloatValue> lineDashPattern;
    public final float miterLimit;
    public final String name;
    public final AnimatableFloatValue offset;
    public final AnimatableIntegerValue opacity;
    public final AnimatableFloatValue width;

    /* renamed from: com.airbnb.lottie.model.content.ShapeStroke$1  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/airbnb/lottie/model/content/ShapeStroke$1.class */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$airbnb$lottie$model$content$ShapeStroke$LineCapType;
        public static final /* synthetic */ int[] $SwitchMap$com$airbnb$lottie$model$content$ShapeStroke$LineJoinType;

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x0055 -> B:32:0x0014). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x0059 -> B:28:0x001f). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:20:0x005d -> B:8:0x002a). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:22:0x0061 -> B:34:0x003e). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:24:0x0065 -> B:30:0x0049). Please submit an issue!!! */
        static {
            int[] iArr = new int[LineJoinType.values().length];
            $SwitchMap$com$airbnb$lottie$model$content$ShapeStroke$LineJoinType = iArr;
            try {
                iArr[LineJoinType.BEVEL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$content$ShapeStroke$LineJoinType[LineJoinType.MITER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$content$ShapeStroke$LineJoinType[LineJoinType.ROUND.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            int[] iArr2 = new int[LineCapType.values().length];
            $SwitchMap$com$airbnb$lottie$model$content$ShapeStroke$LineCapType = iArr2;
            try {
                iArr2[LineCapType.BUTT.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$content$ShapeStroke$LineCapType[LineCapType.ROUND.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$content$ShapeStroke$LineCapType[LineCapType.UNKNOWN.ordinal()] = 3;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/airbnb/lottie/model/content/ShapeStroke$LineCapType.class */
    public enum LineCapType {
        BUTT,
        ROUND,
        UNKNOWN;

        public Paint.Cap toPaintCap() {
            int i = AnonymousClass1.$SwitchMap$com$airbnb$lottie$model$content$ShapeStroke$LineCapType[ordinal()];
            return i != 1 ? i != 2 ? Paint.Cap.SQUARE : Paint.Cap.ROUND : Paint.Cap.BUTT;
        }
    }

    /* loaded from: mainsysui33.jar:com/airbnb/lottie/model/content/ShapeStroke$LineJoinType.class */
    public enum LineJoinType {
        MITER,
        ROUND,
        BEVEL;

        public Paint.Join toPaintJoin() {
            int i = AnonymousClass1.$SwitchMap$com$airbnb$lottie$model$content$ShapeStroke$LineJoinType[ordinal()];
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        return null;
                    }
                    return Paint.Join.ROUND;
                }
                return Paint.Join.MITER;
            }
            return Paint.Join.BEVEL;
        }
    }

    public ShapeStroke(String str, AnimatableFloatValue animatableFloatValue, List<AnimatableFloatValue> list, AnimatableColorValue animatableColorValue, AnimatableIntegerValue animatableIntegerValue, AnimatableFloatValue animatableFloatValue2, LineCapType lineCapType, LineJoinType lineJoinType, float f, boolean z) {
        this.name = str;
        this.offset = animatableFloatValue;
        this.lineDashPattern = list;
        this.color = animatableColorValue;
        this.opacity = animatableIntegerValue;
        this.width = animatableFloatValue2;
        this.capType = lineCapType;
        this.joinType = lineJoinType;
        this.miterLimit = f;
        this.hidden = z;
    }

    public LineCapType getCapType() {
        return this.capType;
    }

    public AnimatableColorValue getColor() {
        return this.color;
    }

    public AnimatableFloatValue getDashOffset() {
        return this.offset;
    }

    public LineJoinType getJoinType() {
        return this.joinType;
    }

    public List<AnimatableFloatValue> getLineDashPattern() {
        return this.lineDashPattern;
    }

    public float getMiterLimit() {
        return this.miterLimit;
    }

    public String getName() {
        return this.name;
    }

    public AnimatableIntegerValue getOpacity() {
        return this.opacity;
    }

    public AnimatableFloatValue getWidth() {
        return this.width;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    @Override // com.airbnb.lottie.model.content.ContentModel
    public Content toContent(LottieDrawable lottieDrawable, BaseLayer baseLayer) {
        return new StrokeContent(lottieDrawable, baseLayer, this);
    }
}