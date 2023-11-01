package com.android.systemui.keyguard.ui.preview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.android.keyguard.ClockEventController;
import com.android.keyguard.KeyguardClockSwitch;
import com.android.keyguard.LockIconViewController;
import com.android.systemui.R$layout;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel;
import com.android.systemui.plugins.ClockController;
import com.android.systemui.plugins.ClockEvents;
import com.android.systemui.plugins.ClockFaceController;
import com.android.systemui.plugins.ClockFaceEvents;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.shared.clocks.ClockRegistry;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.phone.KeyguardBottomAreaView;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DisposableHandle;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/preview/KeyguardPreviewRenderer.class */
public final class KeyguardPreviewRenderer {
    public static final Companion Companion = new Companion(null);
    public final KeyguardBottomAreaViewModel bottomAreaViewModel;
    public final BroadcastDispatcher broadcastDispatcher;
    public final ClockEventController clockController;
    public final ClockRegistry clockRegistry;
    public View clockView;
    public final Context context;
    public final Set<DisposableHandle> disposables = new LinkedHashSet();
    public final int height;
    public SurfaceControlViewHost host;
    public final IBinder hostToken;
    public boolean isDestroyed;
    public final CoroutineDispatcher mainDispatcher;
    public final int width;
    public final WindowManager windowManager;

    @DebugMetadata(c = "com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$1", f = "KeyguardPreviewRenderer.kt", l = {}, m = "invokeSuspend")
    /* renamed from: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$1 */
    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/preview/KeyguardPreviewRenderer$1.class */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Boolean>, Object> {
        public final /* synthetic */ Bundle $bundle;
        public final /* synthetic */ DisplayManager $displayManager;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(DisplayManager displayManager, Bundle bundle, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            KeyguardPreviewRenderer.this = r5;
            this.$displayManager = displayManager;
            this.$bundle = bundle;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$displayManager, this.$bundle, continuation);
        }

        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Boolean> continuation) {
            return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                KeyguardPreviewRenderer.this.host = new SurfaceControlViewHost(KeyguardPreviewRenderer.this.context, this.$displayManager.getDisplay(this.$bundle.getInt("display_id")), KeyguardPreviewRenderer.this.getHostToken());
                Set set = KeyguardPreviewRenderer.this.disposables;
                final KeyguardPreviewRenderer keyguardPreviewRenderer = KeyguardPreviewRenderer.this;
                return Boxing.boxBoolean(set.add(new DisposableHandle() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer.1.1
                    public final void dispose() {
                        keyguardPreviewRenderer.host.release();
                    }
                }));
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/preview/KeyguardPreviewRenderer$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public KeyguardPreviewRenderer(Context context, CoroutineDispatcher coroutineDispatcher, KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, DisplayManager displayManager, WindowManager windowManager, ClockEventController clockEventController, ClockRegistry clockRegistry, BroadcastDispatcher broadcastDispatcher, Bundle bundle) {
        this.context = context;
        this.mainDispatcher = coroutineDispatcher;
        this.bottomAreaViewModel = keyguardBottomAreaViewModel;
        this.windowManager = windowManager;
        this.clockController = clockEventController;
        this.clockRegistry = clockRegistry;
        this.broadcastDispatcher = broadcastDispatcher;
        this.hostToken = bundle.getBinder("host_token");
        this.width = bundle.getInt("width");
        this.height = bundle.getInt("height");
        keyguardBottomAreaViewModel.enablePreviewMode(bundle.getString("initially_selected_slot_id"));
        BuildersKt.runBlocking(coroutineDispatcher, new AnonymousClass1(displayManager, bundle, null));
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$setUpClock$1.dispose():void] */
    public static final /* synthetic */ ClockRegistry access$getClockRegistry$p(KeyguardPreviewRenderer keyguardPreviewRenderer) {
        return keyguardPreviewRenderer.clockRegistry;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$setUpClock$clockChangeListener$1.onClockChanged():void] */
    public static final /* synthetic */ void access$onClockChanged(KeyguardPreviewRenderer keyguardPreviewRenderer, ViewGroup viewGroup) {
        keyguardPreviewRenderer.onClockChanged(viewGroup);
    }

    public final void destroy() {
        this.isDestroyed = true;
        for (DisposableHandle disposableHandle : this.disposables) {
            disposableHandle.dispose();
        }
    }

    public final IBinder getHostToken() {
        return this.hostToken;
    }

    public final SurfaceControlViewHost.SurfacePackage getSurfacePackage() {
        return this.host.getSurfacePackage();
    }

    public final void onClockChanged(ViewGroup viewGroup) {
        View view;
        ClockFaceController largeClock;
        View view2;
        ClockFaceController largeClock2;
        ClockFaceEvents events;
        this.clockController.setClock(this.clockRegistry.createCurrentClock());
        ClockController clock = this.clockController.getClock();
        if (clock != null && (largeClock2 = clock.getLargeClock()) != null && (events = largeClock2.getEvents()) != null) {
            events.onTargetRegionChanged(KeyguardClockSwitch.getLargeClockRegion(viewGroup));
        }
        View view3 = this.clockView;
        if (view3 != null) {
            viewGroup.removeView(view3);
        }
        ClockController clock2 = this.clockController.getClock();
        if (clock2 == null || (largeClock = clock2.getLargeClock()) == null || (view2 = largeClock.getView()) == null) {
            view = null;
        } else {
            viewGroup.addView(view2);
            view = view2;
        }
        this.clockView = view;
    }

    public final void onSlotSelected(String str) {
        this.bottomAreaViewModel.onPreviewSlotSelected(str);
    }

    public final void render() {
        BuildersKt.runBlocking(this.mainDispatcher, new KeyguardPreviewRenderer$render$1(this, null));
    }

    public final void setUpBottomArea(ViewGroup viewGroup) {
        KeyguardBottomAreaView inflate = LayoutInflater.from(this.context).inflate(R$layout.keyguard_bottom_area, viewGroup, false);
        KeyguardBottomAreaView.init$default(inflate, this.bottomAreaViewModel, (FalsingManager) null, (LockIconViewController) null, (KeyguardBottomAreaView.MessageDisplayer) null, (VibratorHelper) null, 30, (Object) null);
        viewGroup.addView((View) inflate, (ViewGroup.LayoutParams) new FrameLayout.LayoutParams(-1, -2, 80));
    }

    /* JADX WARN: Type inference failed for: r0v12, types: [com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$setUpClock$receiver$1, android.content.BroadcastReceiver] */
    public final void setUpClock(final ViewGroup viewGroup) {
        final ClockRegistry.ClockChangeListener clockChangeListener = new ClockRegistry.ClockChangeListener() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$setUpClock$clockChangeListener$1
            public final void onClockChanged() {
                KeyguardPreviewRenderer.access$onClockChanged(KeyguardPreviewRenderer.this, viewGroup);
            }
        };
        this.clockRegistry.registerClockChangeListener(clockChangeListener);
        this.disposables.add(new DisposableHandle() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$setUpClock$1
            public final void dispose() {
                KeyguardPreviewRenderer.access$getClockRegistry$p(KeyguardPreviewRenderer.this).unregisterClockChangeListener(clockChangeListener);
            }
        });
        this.clockController.registerListeners(viewGroup);
        this.disposables.add(new DisposableHandle() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$setUpClock$2
            public final void dispose() {
                ClockEventController clockEventController;
                clockEventController = KeyguardPreviewRenderer.this.clockController;
                clockEventController.unregisterListeners();
            }
        });
        final ?? r0 = new BroadcastReceiver() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$setUpClock$receiver$1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                ClockEventController clockEventController;
                ClockEvents events;
                clockEventController = KeyguardPreviewRenderer.this.clockController;
                ClockController clock = clockEventController.getClock();
                if (clock == null || (events = clock.getEvents()) == null) {
                    return;
                }
                events.onTimeTick();
            }
        };
        BroadcastDispatcher broadcastDispatcher = this.broadcastDispatcher;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.TIME_TICK");
        intentFilter.addAction("android.intent.action.TIME_SET");
        Unit unit = Unit.INSTANCE;
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, r0, intentFilter, null, null, 0, null, 60, null);
        this.disposables.add(new DisposableHandle() { // from class: com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer$setUpClock$4
            public final void dispose() {
                BroadcastDispatcher broadcastDispatcher2;
                broadcastDispatcher2 = KeyguardPreviewRenderer.this.broadcastDispatcher;
                broadcastDispatcher2.unregisterReceiver(r0);
            }
        });
        onClockChanged(viewGroup);
    }
}