package com.airbnb.lottie.animation.content;

import android.graphics.Path;
import android.graphics.PointF;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.content.PolystarShape;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.LottieValueCallback;
import com.android.settingslib.widget.ActionBarShadowController;
import java.util.List;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/animation/content/PolystarContent.class */
public class PolystarContent implements PathContent, BaseKeyframeAnimation.AnimationListener, KeyPathElementContent {
    public final boolean hidden;
    public final BaseKeyframeAnimation<?, Float> innerRadiusAnimation;
    public final BaseKeyframeAnimation<?, Float> innerRoundednessAnimation;
    public boolean isPathValid;
    public final LottieDrawable lottieDrawable;
    public final String name;
    public final BaseKeyframeAnimation<?, Float> outerRadiusAnimation;
    public final BaseKeyframeAnimation<?, Float> outerRoundednessAnimation;
    public final BaseKeyframeAnimation<?, Float> pointsAnimation;
    public final BaseKeyframeAnimation<?, PointF> positionAnimation;
    public final BaseKeyframeAnimation<?, Float> rotationAnimation;
    public final PolystarShape.Type type;
    public final Path path = new Path();
    public CompoundTrimPathContent trimPaths = new CompoundTrimPathContent();

    /* renamed from: com.airbnb.lottie.animation.content.PolystarContent$1  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/airbnb/lottie/animation/content/PolystarContent$1.class */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$airbnb$lottie$model$content$PolystarShape$Type;

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:7:0x0020 -> B:11:0x0014). Please submit an issue!!! */
        static {
            int[] iArr = new int[PolystarShape.Type.values().length];
            $SwitchMap$com$airbnb$lottie$model$content$PolystarShape$Type = iArr;
            try {
                iArr[PolystarShape.Type.STAR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$content$PolystarShape$Type[PolystarShape.Type.POLYGON.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public PolystarContent(LottieDrawable lottieDrawable, BaseLayer baseLayer, PolystarShape polystarShape) {
        this.lottieDrawable = lottieDrawable;
        this.name = polystarShape.getName();
        PolystarShape.Type type = polystarShape.getType();
        this.type = type;
        this.hidden = polystarShape.isHidden();
        BaseKeyframeAnimation<Float, Float> createAnimation = polystarShape.getPoints().createAnimation();
        this.pointsAnimation = createAnimation;
        BaseKeyframeAnimation<PointF, PointF> createAnimation2 = polystarShape.getPosition().createAnimation();
        this.positionAnimation = createAnimation2;
        BaseKeyframeAnimation<Float, Float> createAnimation3 = polystarShape.getRotation().createAnimation();
        this.rotationAnimation = createAnimation3;
        BaseKeyframeAnimation<Float, Float> createAnimation4 = polystarShape.getOuterRadius().createAnimation();
        this.outerRadiusAnimation = createAnimation4;
        BaseKeyframeAnimation<Float, Float> createAnimation5 = polystarShape.getOuterRoundedness().createAnimation();
        this.outerRoundednessAnimation = createAnimation5;
        PolystarShape.Type type2 = PolystarShape.Type.STAR;
        if (type == type2) {
            this.innerRadiusAnimation = polystarShape.getInnerRadius().createAnimation();
            this.innerRoundednessAnimation = polystarShape.getInnerRoundedness().createAnimation();
        } else {
            this.innerRadiusAnimation = null;
            this.innerRoundednessAnimation = null;
        }
        baseLayer.addAnimation(createAnimation);
        baseLayer.addAnimation(createAnimation2);
        baseLayer.addAnimation(createAnimation3);
        baseLayer.addAnimation(createAnimation4);
        baseLayer.addAnimation(createAnimation5);
        if (type == type2) {
            baseLayer.addAnimation(this.innerRadiusAnimation);
            baseLayer.addAnimation(this.innerRoundednessAnimation);
        }
        createAnimation.addUpdateListener(this);
        createAnimation2.addUpdateListener(this);
        createAnimation3.addUpdateListener(this);
        createAnimation4.addUpdateListener(this);
        createAnimation5.addUpdateListener(this);
        if (type == type2) {
            this.innerRadiusAnimation.addUpdateListener(this);
            this.innerRoundednessAnimation.addUpdateListener(this);
        }
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public <T> void addValueCallback(T t, LottieValueCallback<T> lottieValueCallback) {
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation;
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation2;
        if (t == LottieProperty.POLYSTAR_POINTS) {
            this.pointsAnimation.setValueCallback(lottieValueCallback);
        } else if (t == LottieProperty.POLYSTAR_ROTATION) {
            this.rotationAnimation.setValueCallback(lottieValueCallback);
        } else if (t == LottieProperty.POSITION) {
            this.positionAnimation.setValueCallback(lottieValueCallback);
        } else if (t == LottieProperty.POLYSTAR_INNER_RADIUS && (baseKeyframeAnimation2 = this.innerRadiusAnimation) != null) {
            baseKeyframeAnimation2.setValueCallback(lottieValueCallback);
        } else if (t == LottieProperty.POLYSTAR_OUTER_RADIUS) {
            this.outerRadiusAnimation.setValueCallback(lottieValueCallback);
        } else if (t == LottieProperty.POLYSTAR_INNER_ROUNDEDNESS && (baseKeyframeAnimation = this.innerRoundednessAnimation) != null) {
            baseKeyframeAnimation.setValueCallback(lottieValueCallback);
        } else if (t == LottieProperty.POLYSTAR_OUTER_ROUNDEDNESS) {
            this.outerRoundednessAnimation.setValueCallback(lottieValueCallback);
        }
    }

    public final void createPolygonPath() {
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation;
        int floor = (int) Math.floor(this.pointsAnimation.getValue().floatValue());
        double radians = Math.toRadians((this.rotationAnimation == null ? 0.0d : baseKeyframeAnimation.getValue().floatValue()) - 90.0d);
        double d = floor;
        float f = (float) (6.283185307179586d / d);
        float floatValue = this.outerRoundednessAnimation.getValue().floatValue() / 100.0f;
        float floatValue2 = this.outerRadiusAnimation.getValue().floatValue();
        double d2 = floatValue2;
        float cos = (float) (Math.cos(radians) * d2);
        float sin = (float) (Math.sin(radians) * d2);
        this.path.moveTo(cos, sin);
        double d3 = f;
        double d4 = radians + d3;
        double ceil = Math.ceil(d);
        int i = 0;
        while (i < ceil) {
            float cos2 = (float) (Math.cos(d4) * d2);
            float sin2 = (float) (d2 * Math.sin(d4));
            if (floatValue != ActionBarShadowController.ELEVATION_LOW) {
                double atan2 = (float) (Math.atan2(sin, cos) - 1.5707963267948966d);
                float cos3 = (float) Math.cos(atan2);
                float sin3 = (float) Math.sin(atan2);
                double atan22 = (float) (Math.atan2(sin2, cos2) - 1.5707963267948966d);
                float cos4 = (float) Math.cos(atan22);
                float sin4 = (float) Math.sin(atan22);
                float f2 = floatValue2 * floatValue * 0.25f;
                this.path.cubicTo(cos - (cos3 * f2), sin - (sin3 * f2), cos2 + (cos4 * f2), sin2 + (f2 * sin4), cos2, sin2);
            } else {
                this.path.lineTo(cos2, sin2);
            }
            d4 += d3;
            i++;
            sin = sin2;
            cos = cos2;
        }
        PointF value = this.positionAnimation.getValue();
        this.path.offset(value.x, value.y);
        this.path.close();
    }

    public final void createStarPath() {
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation;
        float cos;
        float sin;
        double d;
        float f;
        float floatValue = this.pointsAnimation.getValue().floatValue();
        double radians = Math.toRadians((this.rotationAnimation == null ? 0.0d : baseKeyframeAnimation.getValue().floatValue()) - 90.0d);
        double d2 = floatValue;
        float f2 = (float) (6.283185307179586d / d2);
        float f3 = f2 / 2.0f;
        float f4 = floatValue - ((int) floatValue);
        int i = (f4 > ActionBarShadowController.ELEVATION_LOW ? 1 : (f4 == ActionBarShadowController.ELEVATION_LOW ? 0 : -1));
        double d3 = radians;
        if (i != 0) {
            d3 = radians + ((1.0f - f4) * f3);
        }
        float floatValue2 = this.outerRadiusAnimation.getValue().floatValue();
        float floatValue3 = this.innerRadiusAnimation.getValue().floatValue();
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation2 = this.innerRoundednessAnimation;
        float floatValue4 = baseKeyframeAnimation2 != null ? baseKeyframeAnimation2.getValue().floatValue() / 100.0f : 0.0f;
        BaseKeyframeAnimation<?, Float> baseKeyframeAnimation3 = this.outerRoundednessAnimation;
        float floatValue5 = baseKeyframeAnimation3 != null ? baseKeyframeAnimation3.getValue().floatValue() / 100.0f : 0.0f;
        if (i != 0) {
            f = ((floatValue2 - floatValue3) * f4) + floatValue3;
            double d4 = f;
            cos = (float) (d4 * Math.cos(d3));
            sin = (float) (d4 * Math.sin(d3));
            this.path.moveTo(cos, sin);
            d = d3 + ((f2 * f4) / 2.0f);
        } else {
            double d5 = floatValue2;
            cos = (float) (Math.cos(d3) * d5);
            sin = (float) (d5 * Math.sin(d3));
            this.path.moveTo(cos, sin);
            d = d3 + f3;
            f = 0.0f;
        }
        double ceil = Math.ceil(d2) * 2.0d;
        int i2 = 0;
        boolean z = false;
        float f5 = cos;
        while (true) {
            double d6 = i2;
            if (d6 >= ceil) {
                PointF value = this.positionAnimation.getValue();
                this.path.offset(value.x, value.y);
                this.path.close();
                return;
            }
            float f6 = z ? floatValue2 : floatValue3;
            int i3 = (f > ActionBarShadowController.ELEVATION_LOW ? 1 : (f == ActionBarShadowController.ELEVATION_LOW ? 0 : -1));
            float f7 = (i3 == 0 || d6 != ceil - 2.0d) ? f3 : (f2 * f4) / 2.0f;
            if (i3 != 0 && d6 == ceil - 1.0d) {
                f6 = f;
            }
            double d7 = f6;
            float cos2 = (float) (d7 * Math.cos(d));
            float sin2 = (float) (d7 * Math.sin(d));
            if (floatValue4 == ActionBarShadowController.ELEVATION_LOW && floatValue5 == ActionBarShadowController.ELEVATION_LOW) {
                this.path.lineTo(cos2, sin2);
            } else {
                float f8 = floatValue4;
                double atan2 = (float) (Math.atan2(sin, f5) - 1.5707963267948966d);
                float cos3 = (float) Math.cos(atan2);
                float sin3 = (float) Math.sin(atan2);
                double atan22 = (float) (Math.atan2(sin2, cos2) - 1.5707963267948966d);
                float cos4 = (float) Math.cos(atan22);
                float sin4 = (float) Math.sin(atan22);
                float f9 = z ? f8 : floatValue5;
                if (z) {
                    f8 = floatValue5;
                }
                float f10 = z ? floatValue3 : floatValue2;
                float f11 = z ? floatValue2 : floatValue3;
                float f12 = f10 * f9 * 0.47829f;
                float f13 = cos3 * f12;
                float f14 = f12 * sin3;
                float f15 = f11 * f8 * 0.47829f;
                float f16 = cos4 * f15;
                float f17 = f15 * sin4;
                float f18 = f16;
                float f19 = f14;
                float f20 = f17;
                float f21 = f13;
                if (i != 0) {
                    if (i2 == 0) {
                        f21 = f13 * f4;
                        f19 = f14 * f4;
                        f18 = f16;
                        f20 = f17;
                    } else {
                        f18 = f16;
                        f19 = f14;
                        f20 = f17;
                        f21 = f13;
                        if (d6 == ceil - 1.0d) {
                            f18 = f16 * f4;
                            f20 = f17 * f4;
                            f21 = f13;
                            f19 = f14;
                        }
                    }
                }
                this.path.cubicTo(f5 - f21, sin - f19, cos2 + f18, sin2 + f20, cos2, sin2);
            }
            d += f7;
            z = !z;
            i2++;
            f5 = cos2;
            sin = sin2;
        }
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public String getName() {
        return this.name;
    }

    @Override // com.airbnb.lottie.animation.content.PathContent
    public Path getPath() {
        if (this.isPathValid) {
            return this.path;
        }
        this.path.reset();
        if (this.hidden) {
            this.isPathValid = true;
            return this.path;
        }
        int i = AnonymousClass1.$SwitchMap$com$airbnb$lottie$model$content$PolystarShape$Type[this.type.ordinal()];
        if (i == 1) {
            createStarPath();
        } else if (i == 2) {
            createPolygonPath();
        }
        this.path.close();
        this.trimPaths.apply(this.path);
        this.isPathValid = true;
        return this.path;
    }

    public final void invalidate() {
        this.isPathValid = false;
        this.lottieDrawable.invalidateSelf();
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener
    public void onValueChanged() {
        invalidate();
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public void resolveKeyPath(KeyPath keyPath, int i, List<KeyPath> list, KeyPath keyPath2) {
        MiscUtils.resolveKeyPath(keyPath, i, list, keyPath2, this);
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public void setContents(List<Content> list, List<Content> list2) {
        for (int i = 0; i < list.size(); i++) {
            Content content = list.get(i);
            if (content instanceof TrimPathContent) {
                TrimPathContent trimPathContent = (TrimPathContent) content;
                if (trimPathContent.getType() == ShapeTrimPath.Type.SIMULTANEOUSLY) {
                    this.trimPaths.addTrimPath(trimPathContent);
                    trimPathContent.addListener(this);
                }
            }
        }
    }
}