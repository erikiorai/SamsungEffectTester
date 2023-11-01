package com.android.systemui.screenshot;

import android.graphics.Bitmap;
import android.graphics.Insets;
import android.util.Log;
import com.android.internal.util.ScreenshotHelper;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.screenshot.ScreenshotPolicy;
import java.util.function.Consumer;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/RequestProcessor.class */
public final class RequestProcessor {
    public final ImageCapture capture;
    public final FeatureFlags flags;
    public final CoroutineScope mainScope;
    public final ScreenshotPolicy policy;

    public RequestProcessor(ImageCapture imageCapture, ScreenshotPolicy screenshotPolicy, FeatureFlags featureFlags, CoroutineScope coroutineScope) {
        this.capture = imageCapture;
        this.policy = screenshotPolicy;
        this.flags = featureFlags;
        this.mainScope = coroutineScope;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0169  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x016c  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0181  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x01c1  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x01ef  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x01fc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object process(ScreenshotHelper.ScreenshotRequest screenshotRequest, Continuation<? super ScreenshotHelper.ScreenshotRequest> continuation) {
        RequestProcessor$process$1 requestProcessor$process$1;
        Object coroutine_suspended;
        int i;
        ScreenshotHelper.ScreenshotRequest screenshotRequest2;
        RequestProcessor requestProcessor;
        Object isManagedProfile;
        ScreenshotPolicy.DisplayContentInfo displayContentInfo;
        RequestProcessor requestProcessor2;
        Bitmap bitmap;
        if (continuation instanceof RequestProcessor$process$1) {
            requestProcessor$process$1 = (RequestProcessor$process$1) continuation;
            int i2 = requestProcessor$process$1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                requestProcessor$process$1.label = i2 - 2147483648;
                Object obj = requestProcessor$process$1.result;
                coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                i = requestProcessor$process$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    screenshotRequest2 = screenshotRequest;
                    if (screenshotRequest.getType() != 3) {
                        screenshotRequest2 = screenshotRequest;
                        if (this.flags.isEnabled(Flags.SCREENSHOT_WORK_PROFILE_POLICY)) {
                            ScreenshotPolicy screenshotPolicy = this.policy;
                            int defaultDisplayId = screenshotPolicy.getDefaultDisplayId();
                            requestProcessor$process$1.L$0 = this;
                            requestProcessor$process$1.L$1 = screenshotRequest;
                            requestProcessor$process$1.label = 1;
                            Object findPrimaryContent = screenshotPolicy.findPrimaryContent(defaultDisplayId, requestProcessor$process$1);
                            requestProcessor = this;
                            obj = findPrimaryContent;
                            if (findPrimaryContent == coroutine_suspended) {
                                return coroutine_suspended;
                            }
                        }
                    }
                    return screenshotRequest2;
                } else if (i == 1) {
                    screenshotRequest = (ScreenshotHelper.ScreenshotRequest) requestProcessor$process$1.L$1;
                    requestProcessor = (RequestProcessor) requestProcessor$process$1.L$0;
                    ResultKt.throwOnFailure(obj);
                } else if (i != 2) {
                    if (i == 3) {
                        displayContentInfo = (ScreenshotPolicy.DisplayContentInfo) requestProcessor$process$1.L$1;
                        screenshotRequest = (ScreenshotHelper.ScreenshotRequest) requestProcessor$process$1.L$0;
                        ResultKt.throwOnFailure(obj);
                        bitmap = (Bitmap) obj;
                        if (bitmap == null) {
                            screenshotRequest2 = new ScreenshotHelper.ScreenshotRequest(3, screenshotRequest.getSource(), ScreenshotHelper.HardwareBitmapBundler.hardwareBitmapToBundle(bitmap), displayContentInfo.getBounds(), Insets.NONE, displayContentInfo.getTaskId(), displayContentInfo.getUser().getIdentifier(), displayContentInfo.getComponent());
                            return screenshotRequest2;
                        }
                        throw new IllegalStateException("Task snapshot returned a null Bitmap!".toString());
                    }
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                } else {
                    displayContentInfo = (ScreenshotPolicy.DisplayContentInfo) requestProcessor$process$1.L$2;
                    screenshotRequest = (ScreenshotHelper.ScreenshotRequest) requestProcessor$process$1.L$1;
                    requestProcessor2 = (RequestProcessor) requestProcessor$process$1.L$0;
                    ResultKt.throwOnFailure(obj);
                    isManagedProfile = obj;
                    if (((Boolean) isManagedProfile).booleanValue()) {
                        screenshotRequest2 = new ScreenshotHelper.ScreenshotRequest(screenshotRequest.getType(), screenshotRequest.getSource(), displayContentInfo.getComponent());
                        return screenshotRequest2;
                    }
                    ImageCapture imageCapture = requestProcessor2.capture;
                    int taskId = displayContentInfo.getTaskId();
                    requestProcessor$process$1.L$0 = screenshotRequest;
                    requestProcessor$process$1.L$1 = displayContentInfo;
                    requestProcessor$process$1.L$2 = null;
                    requestProcessor$process$1.label = 3;
                    Object captureTask = imageCapture.captureTask(taskId, requestProcessor$process$1);
                    obj = captureTask;
                    if (captureTask == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    bitmap = (Bitmap) obj;
                    if (bitmap == null) {
                    }
                }
                ScreenshotPolicy.DisplayContentInfo displayContentInfo2 = (ScreenshotPolicy.DisplayContentInfo) obj;
                Log.d("RequestProcessor", "findPrimaryContent: " + displayContentInfo2);
                ScreenshotPolicy screenshotPolicy2 = requestProcessor.policy;
                int identifier = displayContentInfo2.getUser().getIdentifier();
                requestProcessor$process$1.L$0 = requestProcessor;
                requestProcessor$process$1.L$1 = screenshotRequest;
                requestProcessor$process$1.L$2 = displayContentInfo2;
                requestProcessor$process$1.label = 2;
                isManagedProfile = screenshotPolicy2.isManagedProfile(identifier, requestProcessor$process$1);
                if (isManagedProfile != coroutine_suspended) {
                    return coroutine_suspended;
                }
                RequestProcessor requestProcessor3 = requestProcessor;
                displayContentInfo = displayContentInfo2;
                requestProcessor2 = requestProcessor3;
                if (((Boolean) isManagedProfile).booleanValue()) {
                }
            }
        }
        requestProcessor$process$1 = new RequestProcessor$process$1(this, continuation);
        Object obj2 = requestProcessor$process$1.result;
        coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        i = requestProcessor$process$1.label;
        if (i != 0) {
        }
        ScreenshotPolicy.DisplayContentInfo displayContentInfo22 = (ScreenshotPolicy.DisplayContentInfo) obj2;
        Log.d("RequestProcessor", "findPrimaryContent: " + displayContentInfo22);
        ScreenshotPolicy screenshotPolicy22 = requestProcessor.policy;
        int identifier2 = displayContentInfo22.getUser().getIdentifier();
        requestProcessor$process$1.L$0 = requestProcessor;
        requestProcessor$process$1.L$1 = screenshotRequest;
        requestProcessor$process$1.L$2 = displayContentInfo22;
        requestProcessor$process$1.label = 2;
        isManagedProfile = screenshotPolicy22.isManagedProfile(identifier2, requestProcessor$process$1);
        if (isManagedProfile != coroutine_suspended) {
        }
    }

    public final void processAsync(ScreenshotHelper.ScreenshotRequest screenshotRequest, Consumer<ScreenshotHelper.ScreenshotRequest> consumer) {
        BuildersKt.launch$default(this.mainScope, (CoroutineContext) null, (CoroutineStart) null, new RequestProcessor$processAsync$1(this, screenshotRequest, consumer, null), 3, (Object) null);
    }
}