package com.android.systemui.biometrics.udfps;

import android.graphics.PointF;
import android.util.RotationUtils;
import android.view.MotionEvent;
import com.android.systemui.biometrics.UdfpsOverlayParams;
import com.android.systemui.biometrics.udfps.TouchProcessorResult;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/udfps/SinglePointerTouchProcessorKt.class */
public final class SinglePointerTouchProcessorKt {

    /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/udfps/SinglePointerTouchProcessorKt$WhenMappings.class */
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[InteractionEvent.values().length];
            try {
                iArr[InteractionEvent.UNCHANGED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[InteractionEvent.DOWN.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final NormalizedTouchData normalize(MotionEvent motionEvent, int i, UdfpsOverlayParams udfpsOverlayParams) {
        PointF rotateToNaturalOrientation = rotateToNaturalOrientation(motionEvent, i, udfpsOverlayParams);
        return new NormalizedTouchData(motionEvent.getPointerId(i), rotateToNaturalOrientation.x / udfpsOverlayParams.getScaleFactor(), rotateToNaturalOrientation.y / udfpsOverlayParams.getScaleFactor(), motionEvent.getTouchMinor(i) / udfpsOverlayParams.getScaleFactor(), motionEvent.getTouchMajor(i) / udfpsOverlayParams.getScaleFactor(), motionEvent.getOrientation(i), motionEvent.getEventTime(), motionEvent.getDownTime());
    }

    public static final TouchProcessorResult processActionCancel(NormalizedTouchData normalizedTouchData) {
        return new TouchProcessorResult.ProcessedTouch(InteractionEvent.CANCEL, -1, normalizedTouchData);
    }

    public static final TouchProcessorResult processActionDown(PreprocessedTouch preprocessedTouch) {
        TouchProcessorResult.ProcessedTouch processedTouch;
        if (preprocessedTouch.isGoodOverlap()) {
            processedTouch = new TouchProcessorResult.ProcessedTouch(InteractionEvent.DOWN, preprocessedTouch.getData().getPointerId(), preprocessedTouch.getData());
        } else {
            processedTouch = new TouchProcessorResult.ProcessedTouch(preprocessedTouch.getData().getPointerId() == preprocessedTouch.getPreviousPointerOnSensorId() ? InteractionEvent.UP : InteractionEvent.UNCHANGED, -1, preprocessedTouch.getData());
        }
        return processedTouch;
    }

    public static final TouchProcessorResult processActionMove(PreprocessedTouch preprocessedTouch) {
        boolean z = preprocessedTouch.getPreviousPointerOnSensorId() != -1;
        InteractionEvent interactionEvent = (!preprocessedTouch.isGoodOverlap() || z) ? (preprocessedTouch.isGoodOverlap() || !z) ? InteractionEvent.UNCHANGED : InteractionEvent.UP : InteractionEvent.DOWN;
        int i = WhenMappings.$EnumSwitchMapping$0[interactionEvent.ordinal()];
        return new TouchProcessorResult.ProcessedTouch(interactionEvent, i != 1 ? i != 2 ? -1 : preprocessedTouch.getData().getPointerId() : preprocessedTouch.getPreviousPointerOnSensorId(), preprocessedTouch.getData());
    }

    public static final TouchProcessorResult processActionUp(PreprocessedTouch preprocessedTouch) {
        TouchProcessorResult.ProcessedTouch processedTouch;
        if (preprocessedTouch.isGoodOverlap()) {
            processedTouch = new TouchProcessorResult.ProcessedTouch(InteractionEvent.UP, -1, preprocessedTouch.getData());
        } else {
            processedTouch = new TouchProcessorResult.ProcessedTouch(preprocessedTouch.getPreviousPointerOnSensorId() != -1 ? InteractionEvent.UP : InteractionEvent.UNCHANGED, -1, preprocessedTouch.getData());
        }
        return processedTouch;
    }

    public static final PointF rotateToNaturalOrientation(MotionEvent motionEvent, int i, UdfpsOverlayParams udfpsOverlayParams) {
        PointF pointF = new PointF(motionEvent.getRawX(i), motionEvent.getRawY(i));
        int rotation = udfpsOverlayParams.getRotation();
        if (rotation == 1 || rotation == 3) {
            RotationUtils.rotatePointF(pointF, RotationUtils.deltaRotation(rotation, 0), udfpsOverlayParams.getLogicalDisplayWidth(), udfpsOverlayParams.getLogicalDisplayHeight());
        }
        return pointF;
    }
}