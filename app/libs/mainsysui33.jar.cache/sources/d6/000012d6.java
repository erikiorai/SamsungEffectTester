package com.android.systemui.classifier;

import android.content.res.Resources;
import android.view.ViewConfiguration;
import com.android.systemui.R$dimen;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/* loaded from: mainsysui33.jar:com/android/systemui/classifier/FalsingModule.class */
public interface FalsingModule {
    static Set<FalsingClassifier> providesBrightLineGestureClassifiers(DistanceClassifier distanceClassifier, ProximityClassifier proximityClassifier, PointerCountClassifier pointerCountClassifier, TypeClassifier typeClassifier, DiagonalClassifier diagonalClassifier, ZigZagClassifier zigZagClassifier) {
        return new HashSet(Arrays.asList(pointerCountClassifier, typeClassifier, diagonalClassifier, distanceClassifier, proximityClassifier, zigZagClassifier));
    }

    static long providesDoubleTapTimeoutMs() {
        return 1200L;
    }

    static float providesDoubleTapTouchSlop(Resources resources) {
        return resources.getDimension(R$dimen.double_tap_slop);
    }

    static float providesLongTapTouchSlop(ViewConfiguration viewConfiguration) {
        return viewConfiguration.getScaledTouchSlop() * 1.25f;
    }

    static float providesSingleTapTouchSlop(ViewConfiguration viewConfiguration) {
        return viewConfiguration.getScaledTouchSlop();
    }
}