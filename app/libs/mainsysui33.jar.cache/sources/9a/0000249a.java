package com.android.systemui.screenshot;

import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.IBinder;
import android.os.Process;
import android.os.UserManager;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.infra.ServiceConnector;
import com.android.systemui.SystemUIService;
import com.android.systemui.screenshot.IScreenshotProxy;
import com.android.systemui.screenshot.ScreenshotPolicy;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.SafeContinuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotPolicyImpl.class */
public class ScreenshotPolicyImpl implements ScreenshotPolicy {
    public final IActivityTaskManager atmService;
    public final CoroutineDispatcher bgDispatcher;
    public final ServiceConnector<IScreenshotProxy> proxyConnector;
    public final ScreenshotPolicy.DisplayContentInfo systemUiContent;
    public final UserManager userMgr;

    public ScreenshotPolicyImpl(Context context, UserManager userManager, IActivityTaskManager iActivityTaskManager, CoroutineDispatcher coroutineDispatcher) {
        this.userMgr = userManager;
        this.atmService = iActivityTaskManager;
        this.bgDispatcher = coroutineDispatcher;
        this.proxyConnector = new ServiceConnector.Impl(context, new Intent(context, ScreenshotProxyService.class), 1073741857, context.getUserId(), new Function() { // from class: com.android.systemui.screenshot.ScreenshotPolicyImpl$proxyConnector$1
            /* JADX DEBUG: Method merged with bridge method */
            @Override // java.util.function.Function
            public final IScreenshotProxy apply(IBinder iBinder) {
                return IScreenshotProxy.Stub.asInterface(iBinder);
            }
        });
        this.systemUiContent = new ScreenshotPolicy.DisplayContentInfo(new ComponentName(context, SystemUIService.class), new Rect(), Process.myUserHandle(), -1);
    }

    /*  JADX ERROR: IF instruction can be used only in fallback mode
        jadx.core.utils.exceptions.CodegenException: IF instruction can be used only in fallback mode
        	at jadx.core.codegen.InsnGen.fallbackOnlyInsn(InsnGen.java:686)
        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:544)
        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:302)
        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:272)
        	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:91)
        	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
        	at jadx.core.dex.regions.Region.generate(Region.java:35)
        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:80)
        	at jadx.core.codegen.RegionGen.makeLoop(RegionGen.java:175)
        	at jadx.core.dex.regions.loops.LoopRegion.generate(LoopRegion.java:171)
        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
        	at jadx.core.dex.regions.Region.generate(Region.java:35)
        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
        	at jadx.core.dex.regions.Region.generate(Region.java:35)
        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
        	at jadx.core.dex.regions.Region.generate(Region.java:35)
        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:80)
        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:123)
        	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
        	at jadx.core.dex.regions.Region.generate(Region.java:35)
        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:80)
        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:123)
        	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
        	at jadx.core.dex.regions.Region.generate(Region.java:35)
        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
        	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:296)
        	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:275)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:377)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:306)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:272)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
        */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00fb A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static /* synthetic */ java.lang.Object findPrimaryContent$suspendImpl(com.android.systemui.screenshot.ScreenshotPolicyImpl r5, int r6, kotlin.coroutines.Continuation<? super com.android.systemui.screenshot.ScreenshotPolicy.DisplayContentInfo> r7) {
        /*
            r0 = r7
            boolean r0 = r0 instanceof com.android.systemui.screenshot.ScreenshotPolicyImpl$findPrimaryContent$1
            if (r0 == 0) goto L26
            r0 = r7
            com.android.systemui.screenshot.ScreenshotPolicyImpl$findPrimaryContent$1 r0 = (com.android.systemui.screenshot.ScreenshotPolicyImpl$findPrimaryContent$1) r0
            r8 = r0
            r0 = r8
            int r0 = r0.label
            r9 = r0
            r0 = r9
            r1 = -2147483648(0xffffffff80000000, float:-0.0)
            r0 = r0 & r1
            if (r0 == 0) goto L26
            r0 = r8
            r1 = r9
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            int r1 = r1 + r2
            r0.label = r1
            goto L30
        L26:
            com.android.systemui.screenshot.ScreenshotPolicyImpl$findPrimaryContent$1 r0 = new com.android.systemui.screenshot.ScreenshotPolicyImpl$findPrimaryContent$1
            r1 = r0
            r2 = r5
            r3 = r7
            r1.<init>(r2, r3)
            r8 = r0
        L30:
            r0 = r8
            java.lang.Object r0 = r0.result
            r7 = r0
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED()
            r10 = r0
            r0 = r8
            int r0 = r0.label
            r9 = r0
            r0 = r9
            if (r0 == 0) goto L7e
            r0 = r9
            r1 = 1
            if (r0 == r1) goto L6a
            r0 = r9
            r1 = 2
            if (r0 != r1) goto L60
            r0 = r8
            java.lang.Object r0 = r0.L$0
            com.android.systemui.screenshot.ScreenshotPolicyImpl r0 = (com.android.systemui.screenshot.ScreenshotPolicyImpl) r0
            r5 = r0
            r0 = r7
            kotlin.ResultKt.throwOnFailure(r0)
            goto Ld0
        L60:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            r1 = r0
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r1.<init>(r2)
            throw r0
        L6a:
            r0 = r8
            int r0 = r0.I$0
            r6 = r0
            r0 = r8
            java.lang.Object r0 = r0.L$0
            com.android.systemui.screenshot.ScreenshotPolicyImpl r0 = (com.android.systemui.screenshot.ScreenshotPolicyImpl) r0
            r5 = r0
            r0 = r7
            kotlin.ResultKt.throwOnFailure(r0)
            goto La5
        L7e:
            r0 = r7
            kotlin.ResultKt.throwOnFailure(r0)
            r0 = r8
            r1 = r5
            r0.L$0 = r1
            r0 = r8
            r1 = r6
            r0.I$0 = r1
            r0 = r8
            r1 = 1
            r0.label = r1
            r0 = r5
            r1 = r8
            java.lang.Object r0 = r0.isNotificationShadeExpanded(r1)
            r11 = r0
            r0 = r11
            r7 = r0
            r0 = r11
            r1 = r10
            if (r0 != r1) goto La5
            r0 = r10
            return r0
        La5:
            r0 = r7
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            if (r0 == 0) goto Lb4
            r0 = r5
            com.android.systemui.screenshot.ScreenshotPolicy$DisplayContentInfo r0 = r0.systemUiContent
            return r0
        Lb4:
            r0 = r8
            r1 = r5
            r0.L$0 = r1
            r0 = r8
            r1 = 2
            r0.label = r1
            r0 = r5
            r1 = r6
            r2 = r8
            java.lang.Object r0 = r0.getAllRootTaskInfosOnDisplay(r1, r2)
            r8 = r0
            r0 = r8
            r7 = r0
            r0 = r8
            r1 = r10
            if (r0 != r1) goto Ld0
            r0 = r10
            return r0
        Ld0:
            r0 = r7
            java.util.List r0 = (java.util.List) r0
            java.lang.Iterable r0 = (java.lang.Iterable) r0
            java.util.Iterator r0 = r0.iterator()
            r8 = r0
        Ldd:
            r0 = r8
            boolean r0 = r0.hasNext()
            if (r0 == 0) goto Lfb
            r0 = r8
            java.lang.Object r0 = r0.next()
            r7 = r0
            r0 = r5
            r1 = r7
            android.app.ActivityTaskManager$RootTaskInfo r1 = (android.app.ActivityTaskManager.RootTaskInfo) r1
            boolean r0 = r0.nonPipVisibleTask(r1)
            if (r0 == 0) goto Ldd
            goto Lfd
        Lfb:
            r0 = 0
            r7 = r0
        Lfd:
            r0 = r7
            android.app.ActivityTaskManager$RootTaskInfo r0 = (android.app.ActivityTaskManager.RootTaskInfo) r0
            r7 = r0
            r0 = r7
            if (r0 != 0) goto L10b
            r0 = r5
            com.android.systemui.screenshot.ScreenshotPolicy$DisplayContentInfo r0 = r0.systemUiContent
            return r0
        L10b:
            r0 = r7
            com.android.systemui.screenshot.ScreenshotPolicy$DisplayContentInfo r0 = com.android.systemui.screenshot.ScreenshotPolicyImplKt.toDisplayContentInfo(r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.ScreenshotPolicyImpl.findPrimaryContent$suspendImpl(com.android.systemui.screenshot.ScreenshotPolicyImpl, int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @VisibleForTesting
    public static /* synthetic */ Object getAllRootTaskInfosOnDisplay$suspendImpl(ScreenshotPolicyImpl screenshotPolicyImpl, int i, Continuation<? super List<? extends ActivityTaskManager.RootTaskInfo>> continuation) {
        return BuildersKt.withContext(screenshotPolicyImpl.bgDispatcher, new ScreenshotPolicyImpl$getAllRootTaskInfosOnDisplay$2(screenshotPolicyImpl, i, null), continuation);
    }

    @VisibleForTesting
    public static /* synthetic */ void getSystemUiContent$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0060  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static /* synthetic */ Object isManagedProfile$suspendImpl(ScreenshotPolicyImpl screenshotPolicyImpl, int i, Continuation<? super Boolean> continuation) {
        ScreenshotPolicyImpl$isManagedProfile$1 screenshotPolicyImpl$isManagedProfile$1;
        int i2;
        Object obj;
        if (continuation instanceof ScreenshotPolicyImpl$isManagedProfile$1) {
            ScreenshotPolicyImpl$isManagedProfile$1 screenshotPolicyImpl$isManagedProfile$12 = (ScreenshotPolicyImpl$isManagedProfile$1) continuation;
            int i3 = screenshotPolicyImpl$isManagedProfile$12.label;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                screenshotPolicyImpl$isManagedProfile$12.label = i3 - 2147483648;
                screenshotPolicyImpl$isManagedProfile$1 = screenshotPolicyImpl$isManagedProfile$12;
                Object obj2 = screenshotPolicyImpl$isManagedProfile$1.result;
                Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                i2 = screenshotPolicyImpl$isManagedProfile$1.label;
                if (i2 != 0) {
                    ResultKt.throwOnFailure(obj2);
                    CoroutineDispatcher coroutineDispatcher = screenshotPolicyImpl.bgDispatcher;
                    ScreenshotPolicyImpl$isManagedProfile$managed$1 screenshotPolicyImpl$isManagedProfile$managed$1 = new ScreenshotPolicyImpl$isManagedProfile$managed$1(screenshotPolicyImpl, i, null);
                    screenshotPolicyImpl$isManagedProfile$1.label = 1;
                    Object withContext = BuildersKt.withContext(coroutineDispatcher, screenshotPolicyImpl$isManagedProfile$managed$1, screenshotPolicyImpl$isManagedProfile$1);
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
                boolean booleanValue = ((Boolean) obj).booleanValue();
                Log.d("ScreenshotPolicyImpl", "isManagedProfile: " + booleanValue);
                return Boxing.boxBoolean(booleanValue);
            }
        }
        screenshotPolicyImpl$isManagedProfile$1 = new ScreenshotPolicyImpl$isManagedProfile$1(screenshotPolicyImpl, continuation);
        Object obj22 = screenshotPolicyImpl$isManagedProfile$1.result;
        Object coroutine_suspended2 = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        i2 = screenshotPolicyImpl$isManagedProfile$1.label;
        if (i2 != 0) {
        }
        boolean booleanValue2 = ((Boolean) obj).booleanValue();
        Log.d("ScreenshotPolicyImpl", "isManagedProfile: " + booleanValue2);
        return Boxing.boxBoolean(booleanValue2);
    }

    @VisibleForTesting
    public static /* synthetic */ Object isNotificationShadeExpanded$suspendImpl(ScreenshotPolicyImpl screenshotPolicyImpl, Continuation<? super Boolean> continuation) {
        final SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation));
        screenshotPolicyImpl.proxyConnector.postForResult(new ServiceConnector.Job() { // from class: com.android.systemui.screenshot.ScreenshotPolicyImpl$isNotificationShadeExpanded$2$1
            /* JADX DEBUG: Method merged with bridge method */
            public final Boolean run(IScreenshotProxy iScreenshotProxy) {
                return Boolean.valueOf(iScreenshotProxy.isNotificationShadeExpanded());
            }
        }).whenComplete(new BiConsumer() { // from class: com.android.systemui.screenshot.ScreenshotPolicyImpl$isNotificationShadeExpanded$2$2
            /* JADX DEBUG: Method merged with bridge method */
            @Override // java.util.function.BiConsumer
            public final void accept(Boolean bool, Throwable th) {
                if (th != null) {
                    Log.e("ScreenshotPolicyImpl", "isNotificationShadeExpanded", th);
                }
                Continuation<Boolean> continuation2 = safeContinuation;
                Result.Companion companion = Result.Companion;
                continuation2.resumeWith(Result.constructor-impl(Boolean.valueOf(bool == null ? false : bool.booleanValue())));
            }
        });
        Object orThrow = safeContinuation.getOrThrow();
        if (orThrow == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return orThrow;
    }

    @Override // com.android.systemui.screenshot.ScreenshotPolicy
    public Object findPrimaryContent(int i, Continuation<? super ScreenshotPolicy.DisplayContentInfo> continuation) {
        return findPrimaryContent$suspendImpl(this, i, continuation);
    }

    @VisibleForTesting
    public Object getAllRootTaskInfosOnDisplay(int i, Continuation<? super List<? extends ActivityTaskManager.RootTaskInfo>> continuation) {
        return getAllRootTaskInfosOnDisplay$suspendImpl(this, i, continuation);
    }

    @Override // com.android.systemui.screenshot.ScreenshotPolicy
    public int getDefaultDisplayId() {
        return 0;
    }

    @Override // com.android.systemui.screenshot.ScreenshotPolicy
    public Object isManagedProfile(int i, Continuation<? super Boolean> continuation) {
        return isManagedProfile$suspendImpl(this, i, continuation);
    }

    @VisibleForTesting
    public Object isNotificationShadeExpanded(Continuation<? super Boolean> continuation) {
        return isNotificationShadeExpanded$suspendImpl(this, continuation);
    }

    public final boolean nonPipVisibleTask(ActivityTaskManager.RootTaskInfo rootTaskInfo) {
        boolean z = false;
        if (rootTaskInfo.getWindowingMode() != 2) {
            z = false;
            if (rootTaskInfo.isVisible) {
                z = false;
                if (rootTaskInfo.isRunning) {
                    z = false;
                    if (rootTaskInfo.numActivities > 0) {
                        z = false;
                        if (rootTaskInfo.topActivity != null) {
                            z = false;
                            if (!(rootTaskInfo.childTaskIds.length == 0)) {
                                z = true;
                            }
                        }
                    }
                }
            }
        }
        return z;
    }
}