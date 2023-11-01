package com.airbnb.lottie.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.RectF;
import android.provider.Settings;
import com.airbnb.lottie.L;
import com.airbnb.lottie.animation.content.TrimPathContent;
import com.airbnb.lottie.animation.keyframe.FloatKeyframeAnimation;
import com.android.settingslib.widget.ActionBarShadowController;
import java.io.Closeable;
import java.io.InterruptedIOException;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.nio.channels.ClosedChannelException;
import javax.net.ssl.SSLException;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/utils/Utils.class */
public final class Utils {
    public static final PathMeasure pathMeasure = new PathMeasure();
    public static final Path tempPath = new Path();
    public static final Path tempPath2 = new Path();
    public static final float[] points = new float[4];
    public static final float INV_SQRT_2 = (float) (Math.sqrt(2.0d) / 2.0d);
    public static float dpScale = -1.0f;

    public static void applyTrimPathIfNeeded(Path path, float f, float f2, float f3) {
        L.beginSection("applyTrimPathIfNeeded");
        PathMeasure pathMeasure2 = pathMeasure;
        pathMeasure2.setPath(path, false);
        float length = pathMeasure2.getLength();
        if (f == 1.0f && f2 == ActionBarShadowController.ELEVATION_LOW) {
            L.endSection("applyTrimPathIfNeeded");
        } else if (length < 1.0f || Math.abs((f2 - f) - 1.0f) < 0.01d) {
            L.endSection("applyTrimPathIfNeeded");
        } else {
            float f4 = f * length;
            float f5 = f2 * length;
            float min = Math.min(f4, f5);
            float max = Math.max(f4, f5);
            float f6 = f3 * length;
            float f7 = min + f6;
            float f8 = max + f6;
            float f9 = f7;
            float f10 = f8;
            if (f7 >= length) {
                f9 = f7;
                f10 = f8;
                if (f8 >= length) {
                    f9 = MiscUtils.floorMod(f7, length);
                    f10 = MiscUtils.floorMod(f8, length);
                }
            }
            float f11 = f9;
            if (f9 < ActionBarShadowController.ELEVATION_LOW) {
                f11 = MiscUtils.floorMod(f9, length);
            }
            float f12 = f10;
            if (f10 < ActionBarShadowController.ELEVATION_LOW) {
                f12 = MiscUtils.floorMod(f10, length);
            }
            int i = (f11 > f12 ? 1 : (f11 == f12 ? 0 : -1));
            if (i == 0) {
                path.reset();
                L.endSection("applyTrimPathIfNeeded");
                return;
            }
            float f13 = f11;
            if (i >= 0) {
                f13 = f11 - length;
            }
            Path path2 = tempPath;
            path2.reset();
            pathMeasure2.getSegment(f13, f12, path2, true);
            if (f12 > length) {
                Path path3 = tempPath2;
                path3.reset();
                pathMeasure2.getSegment(ActionBarShadowController.ELEVATION_LOW, f12 % length, path3, true);
                path2.addPath(path3);
            } else if (f13 < ActionBarShadowController.ELEVATION_LOW) {
                Path path4 = tempPath2;
                path4.reset();
                pathMeasure2.getSegment(f13 + length, length, path4, true);
                path2.addPath(path4);
            }
            path.set(path2);
            L.endSection("applyTrimPathIfNeeded");
        }
    }

    public static void applyTrimPathIfNeeded(Path path, TrimPathContent trimPathContent) {
        if (trimPathContent == null || trimPathContent.isHidden()) {
            return;
        }
        applyTrimPathIfNeeded(path, ((FloatKeyframeAnimation) trimPathContent.getStart()).getFloatValue() / 100.0f, ((FloatKeyframeAnimation) trimPathContent.getEnd()).getFloatValue() / 100.0f, ((FloatKeyframeAnimation) trimPathContent.getOffset()).getFloatValue() / 360.0f);
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e2) {
            }
        }
    }

    public static Path createPath(PointF pointF, PointF pointF2, PointF pointF3, PointF pointF4) {
        Path path = new Path();
        path.moveTo(pointF.x, pointF.y);
        if (pointF3 == null || pointF4 == null || (pointF3.length() == ActionBarShadowController.ELEVATION_LOW && pointF4.length() == ActionBarShadowController.ELEVATION_LOW)) {
            path.lineTo(pointF2.x, pointF2.y);
        } else {
            float f = pointF.x;
            float f2 = pointF3.x;
            float f3 = pointF.y;
            float f4 = pointF3.y;
            float f5 = pointF2.x;
            float f6 = pointF4.x;
            float f7 = pointF2.y;
            path.cubicTo(f2 + f, f3 + f4, f5 + f6, f7 + pointF4.y, f5, f7);
        }
        return path;
    }

    public static float dpScale() {
        if (dpScale == -1.0f) {
            dpScale = Resources.getSystem().getDisplayMetrics().density;
        }
        return dpScale;
    }

    public static float getAnimationScale(Context context) {
        return Settings.Global.getFloat(context.getContentResolver(), "animator_duration_scale", 1.0f);
    }

    public static float getScale(Matrix matrix) {
        float[] fArr = points;
        fArr[0] = 0.0f;
        fArr[1] = 0.0f;
        float f = INV_SQRT_2;
        fArr[2] = f;
        fArr[3] = f;
        matrix.mapPoints(fArr);
        return (float) Math.hypot(fArr[2] - fArr[0], fArr[3] - fArr[1]);
    }

    public static boolean hasZeroScaleAxis(Matrix matrix) {
        float[] fArr = points;
        fArr[0] = 0.0f;
        fArr[1] = 0.0f;
        fArr[2] = 37394.73f;
        fArr[3] = 39575.234f;
        matrix.mapPoints(fArr);
        return fArr[0] == fArr[2] || fArr[1] == fArr[3];
    }

    public static int hashFor(float f, float f2, float f3, float f4) {
        int i = f != ActionBarShadowController.ELEVATION_LOW ? (int) (527 * f) : 17;
        int i2 = i;
        if (f2 != ActionBarShadowController.ELEVATION_LOW) {
            i2 = (int) (i * 31 * f2);
        }
        int i3 = i2;
        if (f3 != ActionBarShadowController.ELEVATION_LOW) {
            i3 = (int) (i2 * 31 * f3);
        }
        int i4 = i3;
        if (f4 != ActionBarShadowController.ELEVATION_LOW) {
            i4 = (int) (i3 * 31 * f4);
        }
        return i4;
    }

    public static boolean isAtLeastVersion(int i, int i2, int i3, int i4, int i5, int i6) {
        boolean z = false;
        if (i < i4) {
            return false;
        }
        if (i > i4) {
            return true;
        }
        if (i2 < i5) {
            return false;
        }
        if (i2 > i5) {
            return true;
        }
        if (i3 >= i6) {
            z = true;
        }
        return z;
    }

    public static boolean isNetworkException(Throwable th) {
        return (th instanceof SocketException) || (th instanceof ClosedChannelException) || (th instanceof InterruptedIOException) || (th instanceof ProtocolException) || (th instanceof SSLException) || (th instanceof UnknownHostException) || (th instanceof UnknownServiceException);
    }

    public static Bitmap resizeBitmapIfNeeded(Bitmap bitmap, int i, int i2) {
        if (bitmap.getWidth() == i && bitmap.getHeight() == i2) {
            return bitmap;
        }
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, i, i2, true);
        bitmap.recycle();
        return createScaledBitmap;
    }

    public static void saveLayerCompat(Canvas canvas, RectF rectF, Paint paint) {
        saveLayerCompat(canvas, rectF, paint, 31);
    }

    public static void saveLayerCompat(Canvas canvas, RectF rectF, Paint paint, int i) {
        L.beginSection("Utils#saveLayer");
        canvas.saveLayer(rectF, paint);
        L.endSection("Utils#saveLayer");
    }
}