package com.android.systemui.screenshot;

import android.app.IActivityTaskManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.os.IBinder;
import android.util.Log;
import android.view.Display;
import android.view.DisplayAddress;
import android.view.SurfaceControl;
import android.window.TaskSnapshot;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ImageCaptureImpl.class */
public class ImageCaptureImpl implements ImageCapture {
    public final IActivityTaskManager atmService;
    public final CoroutineDispatcher bgContext;
    public final DisplayManager displayManager;

    public ImageCaptureImpl(DisplayManager displayManager, IActivityTaskManager iActivityTaskManager, CoroutineDispatcher coroutineDispatcher) {
        this.displayManager = displayManager;
        this.atmService = iActivityTaskManager;
        this.bgContext = coroutineDispatcher;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0094 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0096  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static /* synthetic */ Object captureTask$suspendImpl(ImageCaptureImpl imageCaptureImpl, int i, Continuation<? super Bitmap> continuation) {
        ImageCaptureImpl$captureTask$1 imageCaptureImpl$captureTask$1;
        int i2;
        Object obj;
        TaskSnapshot taskSnapshot;
        if (continuation instanceof ImageCaptureImpl$captureTask$1) {
            ImageCaptureImpl$captureTask$1 imageCaptureImpl$captureTask$12 = (ImageCaptureImpl$captureTask$1) continuation;
            int i3 = imageCaptureImpl$captureTask$12.label;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                imageCaptureImpl$captureTask$12.label = i3 - 2147483648;
                imageCaptureImpl$captureTask$1 = imageCaptureImpl$captureTask$12;
                Object obj2 = imageCaptureImpl$captureTask$1.result;
                Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                i2 = imageCaptureImpl$captureTask$1.label;
                if (i2 != 0) {
                    ResultKt.throwOnFailure(obj2);
                    CoroutineDispatcher coroutineDispatcher = imageCaptureImpl.bgContext;
                    ImageCaptureImpl$captureTask$snapshot$1 imageCaptureImpl$captureTask$snapshot$1 = new ImageCaptureImpl$captureTask$snapshot$1(imageCaptureImpl, i, null);
                    imageCaptureImpl$captureTask$1.label = 1;
                    Object withContext = BuildersKt.withContext(coroutineDispatcher, imageCaptureImpl$captureTask$snapshot$1, imageCaptureImpl$captureTask$1);
                    obj = withContext;
                    if (withContext == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                } else {
                    ResultKt.throwOnFailure(obj2);
                    obj = obj2;
                }
                taskSnapshot = (TaskSnapshot) obj;
                if (taskSnapshot != null) {
                    return null;
                }
                return Bitmap.wrapHardwareBuffer(taskSnapshot.getHardwareBuffer(), taskSnapshot.getColorSpace());
            }
        }
        imageCaptureImpl$captureTask$1 = new ImageCaptureImpl$captureTask$1(imageCaptureImpl, continuation);
        Object obj22 = imageCaptureImpl$captureTask$1.result;
        Object coroutine_suspended2 = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        i2 = imageCaptureImpl$captureTask$1.label;
        if (i2 != 0) {
        }
        taskSnapshot = (TaskSnapshot) obj;
        if (taskSnapshot != null) {
        }
    }

    @Override // com.android.systemui.screenshot.ImageCapture
    public Bitmap captureDisplay(int i, Rect rect) {
        int i2 = 0;
        int width = rect != null ? rect.width() : 0;
        if (rect != null) {
            i2 = rect.height();
        }
        Rect rect2 = rect;
        if (rect == null) {
            rect2 = new Rect();
        }
        IBinder physicalDisplayToken = physicalDisplayToken(i);
        Bitmap bitmap = null;
        if (physicalDisplayToken == null) {
            return null;
        }
        SurfaceControl.ScreenshotHardwareBuffer captureDisplay = captureDisplay(physicalDisplayToken, width, i2, rect2);
        if (captureDisplay != null) {
            bitmap = captureDisplay.asBitmap();
        }
        return bitmap;
    }

    public SurfaceControl.ScreenshotHardwareBuffer captureDisplay(IBinder iBinder, int i, int i2, Rect rect) {
        return SurfaceControl.captureDisplay(new SurfaceControl.DisplayCaptureArgs.Builder(iBinder).setSize(i, i2).setSourceCrop(rect).build());
    }

    @Override // com.android.systemui.screenshot.ImageCapture
    public Object captureTask(int i, Continuation<? super Bitmap> continuation) {
        return captureTask$suspendImpl(this, i, continuation);
    }

    public IBinder physicalDisplayToken(int i) {
        Display display = this.displayManager.getDisplay(i);
        if (display == null) {
            Log.e("ImageCaptureImpl", "No display with id: " + i);
            return null;
        }
        DisplayAddress.Physical address = display.getAddress();
        if (address instanceof DisplayAddress.Physical) {
            return SurfaceControl.getPhysicalDisplayToken(address.getPhysicalDisplayId());
        }
        Log.e("ImageCaptureImpl", "Display does not have a physical address: " + display);
        return null;
    }
}