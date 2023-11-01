package com.android.systemui.biometrics.udfps;

import android.view.MotionEvent;
import com.android.systemui.biometrics.UdfpsOverlayParams;
import com.android.systemui.biometrics.udfps.TouchProcessorResult;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/udfps/SinglePointerTouchProcessor.class */
public final class SinglePointerTouchProcessor implements TouchProcessor {
    public final OverlapDetector overlapDetector;

    public SinglePointerTouchProcessor(OverlapDetector overlapDetector) {
        this.overlapDetector = overlapDetector;
    }

    public static final PreprocessedTouch processTouch$preprocess(MotionEvent motionEvent, UdfpsOverlayParams udfpsOverlayParams, SinglePointerTouchProcessor singlePointerTouchProcessor, int i) {
        NormalizedTouchData normalize;
        normalize = SinglePointerTouchProcessorKt.normalize(motionEvent, 0, udfpsOverlayParams);
        return new PreprocessedTouch(normalize, i, singlePointerTouchProcessor.overlapDetector.isGoodOverlap(normalize, udfpsOverlayParams.getNativeSensorBounds()));
    }

    @Override // com.android.systemui.biometrics.udfps.TouchProcessor
    public TouchProcessorResult processTouch(MotionEvent motionEvent, int i, UdfpsOverlayParams udfpsOverlayParams) {
        TouchProcessorResult processActionDown;
        TouchProcessorResult.Failure failure;
        TouchProcessorResult processActionUp;
        TouchProcessorResult processActionMove;
        NormalizedTouchData normalize;
        TouchProcessorResult processActionCancel;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            processActionDown = SinglePointerTouchProcessorKt.processActionDown(processTouch$preprocess(motionEvent, udfpsOverlayParams, this, i));
            failure = processActionDown;
        } else if (actionMasked == 1) {
            processActionUp = SinglePointerTouchProcessorKt.processActionUp(processTouch$preprocess(motionEvent, udfpsOverlayParams, this, i));
            failure = processActionUp;
        } else if (actionMasked == 2) {
            processActionMove = SinglePointerTouchProcessorKt.processActionMove(processTouch$preprocess(motionEvent, udfpsOverlayParams, this, i));
            failure = processActionMove;
        } else if (actionMasked != 3) {
            String actionToString = MotionEvent.actionToString(motionEvent.getActionMasked());
            failure = new TouchProcessorResult.Failure("Unsupported MotionEvent." + actionToString);
        } else {
            normalize = SinglePointerTouchProcessorKt.normalize(motionEvent, 0, udfpsOverlayParams);
            processActionCancel = SinglePointerTouchProcessorKt.processActionCancel(normalize);
            failure = processActionCancel;
        }
        return failure;
    }
}