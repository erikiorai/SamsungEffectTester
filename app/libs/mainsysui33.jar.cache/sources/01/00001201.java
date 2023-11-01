package com.android.systemui.biometrics;

import android.graphics.Insets;
import android.graphics.Rect;
import android.hardware.biometrics.SensorLocationInternal;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.FrameLayout;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.biometrics.AuthDialog;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsDialogMeasureAdapter.class */
public class UdfpsDialogMeasureAdapter {
    public static final boolean DEBUG;
    public int mBottomSpacerHeight;
    public final FingerprintSensorPropertiesInternal mSensorProps;
    public final ViewGroup mView;
    public WindowManager mWindowManager;

    static {
        DEBUG = Build.IS_USERDEBUG || Build.IS_ENG;
    }

    public UdfpsDialogMeasureAdapter(ViewGroup viewGroup, FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal) {
        this.mView = viewGroup;
        this.mSensorProps = fingerprintSensorPropertiesInternal;
        this.mWindowManager = (WindowManager) viewGroup.getContext().getSystemService(WindowManager.class);
    }

    @VisibleForTesting
    public static int calculateBottomSpacerHeightForLandscape(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        int i8 = ((((i + i2) + i3) + i4) - (i5 + i6)) - i7;
        if (DEBUG) {
            Log.d("UdfpsDialogMeasurementAdapter", "Title height: " + i + ", Subtitle height: " + i2 + ", Description height: " + i3 + ", Top spacer height: " + i4 + ", Text indicator height: " + i5 + ", Button bar height: " + i6 + ", Navbar bottom inset: " + i7 + ", Bottom spacer height (landscape): " + i8);
        }
        return i8;
    }

    @VisibleForTesting
    public static int calculateBottomSpacerHeightForPortrait(FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal, int i, int i2, int i3, int i4, int i5, float f) {
        SensorLocationInternal location = fingerprintSensorPropertiesInternal.getLocation();
        int i6 = (i - ((int) (location.sensorLocationY * f))) - ((int) (location.sensorRadius * f));
        int i7 = (((i6 - i2) - i3) - i4) - i5;
        if (DEBUG) {
            Log.d("UdfpsDialogMeasurementAdapter", "Display height: " + i + ", Distance from bottom: " + i6 + ", Bottom margin: " + i4 + ", Navbar bottom inset: " + i5 + ", Bottom spacer height (portrait): " + i7 + ", Scale Factor: " + f);
        }
        return i7;
    }

    @VisibleForTesting
    public static int calculateHorizontalSpacerWidthForLandscape(FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal, int i, int i2, int i3, float f) {
        SensorLocationInternal location = fingerprintSensorPropertiesInternal.getLocation();
        int i4 = (i - ((int) (location.sensorLocationY * f))) - ((int) (location.sensorRadius * f));
        int i5 = (i4 - i2) - i3;
        if (DEBUG) {
            Log.d("UdfpsDialogMeasurementAdapter", "Display width: " + i + ", Distance from edge: " + i4 + ", Dialog margin: " + i2 + ", Navbar horizontal inset: " + i3 + ", Horizontal spacer width (landscape): " + i5 + ", Scale Factor: " + f);
        }
        return i5;
    }

    public static Rect getMaximumWindowBounds(WindowMetrics windowMetrics) {
        return windowMetrics != null ? windowMetrics.getBounds() : new Rect();
    }

    public static Insets getNavbarInsets(WindowMetrics windowMetrics) {
        return windowMetrics != null ? windowMetrics.getWindowInsets().getInsets(WindowInsets.Type.navigationBars()) : Insets.NONE;
    }

    public int getBottomSpacerHeight() {
        return this.mBottomSpacerHeight;
    }

    public final int getDialogMarginPx() {
        return this.mView.getResources().getDimensionPixelSize(R$dimen.biometric_dialog_border_padding);
    }

    public int getSensorDiameter(float f) {
        return (int) (f * this.mSensorProps.getLocation().sensorRadius * 2.0f);
    }

    public final int getViewHeightPx(int i) {
        View findViewById = this.mView.findViewById(i);
        return (findViewById == null || findViewById.getVisibility() == 8) ? 0 : findViewById.getMeasuredHeight();
    }

    public final int measureDescription(View view, int i, int i2, int i3) {
        int i4 = (int) (i * 0.75d);
        if (view.getMeasuredHeight() + i3 > i4) {
            view.measure(View.MeasureSpec.makeMeasureSpec(i2, 1073741824), View.MeasureSpec.makeMeasureSpec(i4 - i3, 1073741824));
        }
        return view.getMeasuredHeight();
    }

    public AuthDialog.LayoutParams onMeasureInternal(int i, int i2, AuthDialog.LayoutParams layoutParams, float f) {
        int rotation = this.mView.getDisplay().getRotation();
        if (rotation != 0) {
            if (rotation == 1 || rotation == 3) {
                return onMeasureInternalLandscape(i, i2, f);
            }
            Log.e("UdfpsDialogMeasurementAdapter", "Unsupported display rotation: " + rotation);
            return layoutParams;
        }
        return onMeasureInternalPortrait(i, i2, f);
    }

    public final AuthDialog.LayoutParams onMeasureInternalLandscape(int i, int i2, float f) {
        WindowMetrics maximumWindowMetrics = this.mWindowManager.getMaximumWindowMetrics();
        int viewHeightPx = getViewHeightPx(R$id.title);
        int viewHeightPx2 = getViewHeightPx(R$id.subtitle);
        int viewHeightPx3 = getViewHeightPx(R$id.description);
        int viewHeightPx4 = getViewHeightPx(R$id.space_above_icon);
        int viewHeightPx5 = getViewHeightPx(R$id.indicator);
        int viewHeightPx6 = getViewHeightPx(R$id.button_bar);
        Insets navbarInsets = getNavbarInsets(maximumWindowMetrics);
        int calculateBottomSpacerHeightForLandscape = calculateBottomSpacerHeightForLandscape(viewHeightPx, viewHeightPx2, viewHeightPx3, viewHeightPx4, viewHeightPx5, viewHeightPx6, navbarInsets.bottom);
        int calculateHorizontalSpacerWidthForLandscape = calculateHorizontalSpacerWidthForLandscape(this.mSensorProps, getMaximumWindowBounds(maximumWindowMetrics).width(), getDialogMarginPx(), navbarInsets.left + navbarInsets.right, f);
        int sensorDiameter = getSensorDiameter(f);
        int i3 = (calculateHorizontalSpacerWidthForLandscape * 2) + sensorDiameter;
        int childCount = this.mView.getChildCount();
        int i4 = 0;
        int i5 = 0;
        while (true) {
            int i6 = i5;
            if (i4 >= childCount) {
                return new AuthDialog.LayoutParams(i3, i6);
            }
            View childAt = this.mView.getChildAt(i4);
            if (childAt.getId() == R$id.biometric_icon_frame) {
                FrameLayout frameLayout = (FrameLayout) childAt;
                View childAt2 = frameLayout.getChildAt(0);
                frameLayout.measure(View.MeasureSpec.makeMeasureSpec(i3, 1073741824), View.MeasureSpec.makeMeasureSpec(sensorDiameter, 1073741824));
                childAt2.measure(View.MeasureSpec.makeMeasureSpec(sensorDiameter, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(sensorDiameter, Integer.MIN_VALUE));
            } else if (childAt.getId() == R$id.space_above_icon) {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(i3, 1073741824), View.MeasureSpec.makeMeasureSpec(childAt.getLayoutParams().height - Math.min(calculateBottomSpacerHeightForLandscape, 0), 1073741824));
            } else if (childAt.getId() == R$id.button_bar) {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(i3, 1073741824), View.MeasureSpec.makeMeasureSpec(childAt.getLayoutParams().height, 1073741824));
            } else if (childAt.getId() == R$id.space_below_icon) {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(i3, 1073741824), View.MeasureSpec.makeMeasureSpec(Math.max(calculateBottomSpacerHeightForLandscape, 0), 1073741824));
            } else {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(i3, 1073741824), View.MeasureSpec.makeMeasureSpec(i2, Integer.MIN_VALUE));
            }
            int i7 = i6;
            if (childAt.getVisibility() != 8) {
                i7 = i6 + childAt.getMeasuredHeight();
            }
            i4++;
            i5 = i7;
        }
    }

    public final AuthDialog.LayoutParams onMeasureInternalPortrait(int i, int i2, float f) {
        int i3;
        int i4;
        WindowMetrics maximumWindowMetrics = this.mWindowManager.getMaximumWindowMetrics();
        int viewHeightPx = getViewHeightPx(R$id.indicator);
        int viewHeightPx2 = getViewHeightPx(R$id.button_bar);
        int dialogMarginPx = getDialogMarginPx();
        int height = getMaximumWindowBounds(maximumWindowMetrics).height();
        this.mBottomSpacerHeight = calculateBottomSpacerHeightForPortrait(this.mSensorProps, height, viewHeightPx, viewHeightPx2, dialogMarginPx, getNavbarInsets(maximumWindowMetrics).bottom, f);
        int childCount = this.mView.getChildCount();
        int sensorDiameter = getSensorDiameter(f);
        int i5 = 0;
        int i6 = 0;
        while (true) {
            i3 = i6;
            if (i5 >= childCount) {
                break;
            }
            View childAt = this.mView.getChildAt(i5);
            if (childAt.getId() == R$id.biometric_icon_frame) {
                FrameLayout frameLayout = (FrameLayout) childAt;
                View childAt2 = frameLayout.getChildAt(0);
                frameLayout.measure(View.MeasureSpec.makeMeasureSpec(childAt.getLayoutParams().width, 1073741824), View.MeasureSpec.makeMeasureSpec(sensorDiameter, 1073741824));
                childAt2.measure(View.MeasureSpec.makeMeasureSpec(sensorDiameter, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(sensorDiameter, Integer.MIN_VALUE));
            } else if (childAt.getId() == R$id.space_above_icon) {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(i, 1073741824), View.MeasureSpec.makeMeasureSpec(childAt.getLayoutParams().height, 1073741824));
            } else if (childAt.getId() == R$id.button_bar) {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(i, 1073741824), View.MeasureSpec.makeMeasureSpec(childAt.getLayoutParams().height, 1073741824));
            } else if (childAt.getId() == R$id.space_below_icon) {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(i, 1073741824), View.MeasureSpec.makeMeasureSpec(Math.max(this.mBottomSpacerHeight, 0), 1073741824));
            } else if (childAt.getId() == R$id.description) {
                i4 = i3;
                i5++;
                i6 = i4;
            } else {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(i, 1073741824), View.MeasureSpec.makeMeasureSpec(i2, Integer.MIN_VALUE));
            }
            i4 = i3;
            if (childAt.getVisibility() != 8) {
                i4 = i3 + childAt.getMeasuredHeight();
            }
            i5++;
            i6 = i4;
        }
        View findViewById = this.mView.findViewById(R$id.description);
        int i7 = i3;
        if (findViewById != null) {
            i7 = i3;
            if (findViewById.getVisibility() != 8) {
                i7 = i3 + measureDescription(findViewById, height, i, i3);
            }
        }
        return new AuthDialog.LayoutParams(i, i7);
    }
}