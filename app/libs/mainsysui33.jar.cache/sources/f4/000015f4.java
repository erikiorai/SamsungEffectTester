package com.android.systemui.decor;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.android.systemui.R$drawable;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/decor/RoundedCornerDecorProviderImplKt.class */
public final class RoundedCornerDecorProviderImplKt {
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0051, code lost:
        if (r0 != false) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0083, code lost:
        if (r0 == false) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00a8, code lost:
        if (r0 != false) goto L11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00d5, code lost:
        if (r0 != false) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void adjustRotation(ImageView imageView, List<Integer> list, int i) {
        float f;
        boolean contains = list.contains(1);
        boolean contains2 = list.contains(0);
        float f2 = 1.0f;
        float f3 = 180.0f;
        if (i == 0) {
            if (!contains || !contains2) {
                if (!contains || contains2) {
                    if (!contains) {
                    }
                    f = 1.0f;
                }
                f3 = 0.0f;
                f = 1.0f;
                f2 = -1.0f;
            }
            f = 1.0f;
            f3 = 0.0f;
        } else if (i == 1) {
            if (!contains || !contains2) {
                if (!contains || contains2) {
                    if (!contains) {
                    }
                    f3 = 0.0f;
                    f = 1.0f;
                    f2 = -1.0f;
                }
                f = 1.0f;
                f3 = 0.0f;
            }
            f = -1.0f;
            f3 = 0.0f;
        } else if (i != 3) {
            if (!contains || !contains2) {
                if (!contains || contains2) {
                    if (!contains) {
                    }
                    f = 1.0f;
                    f3 = 0.0f;
                }
                f = -1.0f;
                f3 = 0.0f;
            }
            f = 1.0f;
        } else {
            if (!contains || !contains2) {
                if (!contains || contains2) {
                    f = -1.0f;
                    if (!contains) {
                    }
                    f3 = 0.0f;
                }
                f = 1.0f;
            }
            f3 = 0.0f;
            f = 1.0f;
            f2 = -1.0f;
        }
        imageView.setRotation(f3);
        imageView.setScaleX(f2);
        imageView.setScaleY(f);
    }

    public static final void setRoundedCornerImage(ImageView imageView, RoundedCornerResDelegate roundedCornerResDelegate, boolean z) {
        Drawable topRoundedDrawable = z ? roundedCornerResDelegate.getTopRoundedDrawable() : roundedCornerResDelegate.getBottomRoundedDrawable();
        if (topRoundedDrawable != null) {
            imageView.setImageDrawable(topRoundedDrawable);
        } else {
            imageView.setImageResource(z ? R$drawable.rounded_corner_top : R$drawable.rounded_corner_bottom);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x001e, code lost:
        if (r3 != 2) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0031, code lost:
        if (r3 != 2) goto L13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0044, code lost:
        if (r3 != 2) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final int toLayoutGravity(int i, int i2) {
        int i3;
        if (i2 == 0) {
            if (i != 0) {
                if (i != 1) {
                    if (i != 2) {
                        i3 = 80;
                    }
                    i3 = 5;
                }
                i3 = 48;
            }
            i3 = 3;
        } else if (i2 == 1) {
            i3 = 80;
            if (i != 0) {
                if (i != 1) {
                }
                i3 = 3;
            }
        } else if (i2 != 3) {
            if (i != 0) {
                i3 = 80;
                if (i != 1) {
                }
            }
            i3 = 5;
        } else {
            if (i != 0) {
                if (i != 1) {
                    i3 = 80;
                }
                i3 = 5;
            }
            i3 = 48;
        }
        return i3;
    }
}