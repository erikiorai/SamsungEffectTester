package com.android.systemui.classifier;

import com.android.systemui.classifier.FalsingClassifier;

/* loaded from: mainsysui33.jar:com/android/systemui/classifier/TypeClassifier.class */
public class TypeClassifier extends FalsingClassifier {
    public TypeClassifier(FalsingDataProvider falsingDataProvider) {
        super(falsingDataProvider);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0097, code lost:
        if (r0 != false) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x009d, code lost:
        r15 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00b9, code lost:
        if (r0 != false) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00c6, code lost:
        if (r0 != false) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00d3, code lost:
        if (r0 != false) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00e9, code lost:
        if (r0 == false) goto L12;
     */
    @Override // com.android.systemui.classifier.FalsingClassifier
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public FalsingClassifier.Result calculateFalsingResult(int i, double d, double d2) {
        double d3 = 0.0d;
        if (i == 13 || i == 14) {
            return FalsingClassifier.Result.passed(0.0d);
        }
        boolean isVertical = isVertical();
        boolean isUp = isUp();
        boolean isRight = isRight();
        boolean z = isVertical;
        boolean z2 = isVertical;
        switch (i) {
            case 0:
            case 2:
            case 9:
                if (isVertical) {
                    break;
                }
                z = true;
                d3 = 1.0d;
                z2 = z;
                break;
            case 1:
            case 15:
                d3 = 1.0d;
                z2 = z;
                break;
            case 3:
            case 7:
            case 13:
            case 14:
            case 16:
            default:
                d3 = 1.0d;
                z2 = true;
                break;
            case 4:
            case 8:
                if (isVertical) {
                    break;
                }
                z = true;
                d3 = 1.0d;
                z2 = z;
                break;
            case 5:
                if (isRight) {
                    break;
                }
                z = true;
                d3 = 1.0d;
                z2 = z;
                break;
            case 6:
                if (!isRight) {
                    break;
                }
                z = true;
                d3 = 1.0d;
                z2 = z;
                break;
            case 10:
            case 18:
                break;
            case 11:
            case 17:
                z = !isVertical;
                d3 = 1.0d;
                z2 = z;
                break;
            case 12:
                if (isVertical) {
                    break;
                }
                z = true;
                d3 = 1.0d;
                z2 = z;
                break;
        }
        return z2 ? falsed(d3, getReason(i)) : FalsingClassifier.Result.passed(0.5d);
    }

    public final String getReason(int i) {
        return String.format("{interaction=%s, vertical=%s, up=%s, right=%s}", Integer.valueOf(i), Boolean.valueOf(isVertical()), Boolean.valueOf(isUp()), Boolean.valueOf(isRight()));
    }
}