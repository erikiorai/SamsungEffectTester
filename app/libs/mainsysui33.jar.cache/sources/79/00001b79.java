package com.android.systemui.lifecycle;

import android.view.View;
import android.view.ViewTreeObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

/* loaded from: mainsysui33.jar:com/android/systemui/lifecycle/ViewLifecycleOwner.class */
public final class ViewLifecycleOwner implements LifecycleOwner {
    public final View view;
    public final ViewTreeObserver.OnWindowVisibilityChangeListener windowVisibleListener = new ViewTreeObserver.OnWindowVisibilityChangeListener() { // from class: com.android.systemui.lifecycle.ViewLifecycleOwner$windowVisibleListener$1
        public final void onWindowVisibilityChanged(int i) {
            ViewLifecycleOwner.access$updateState(ViewLifecycleOwner.this);
        }
    };
    public final ViewTreeObserver.OnWindowFocusChangeListener windowFocusListener = new ViewTreeObserver.OnWindowFocusChangeListener() { // from class: com.android.systemui.lifecycle.ViewLifecycleOwner$windowFocusListener$1
        @Override // android.view.ViewTreeObserver.OnWindowFocusChangeListener
        public final void onWindowFocusChanged(boolean z) {
            ViewLifecycleOwner.access$updateState(ViewLifecycleOwner.this);
        }
    };
    public final LifecycleRegistry registry = new LifecycleRegistry(this);

    public ViewLifecycleOwner(View view) {
        this.view = view;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.lifecycle.ViewLifecycleOwner$windowFocusListener$1.onWindowFocusChanged(boolean):void, com.android.systemui.lifecycle.ViewLifecycleOwner$windowVisibleListener$1.onWindowVisibilityChanged(int):void] */
    public static final /* synthetic */ void access$updateState(ViewLifecycleOwner viewLifecycleOwner) {
        viewLifecycleOwner.updateState();
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public Lifecycle getLifecycle() {
        return this.registry;
    }

    public final void onCreate() {
        this.registry.setCurrentState(Lifecycle.State.CREATED);
        this.view.getViewTreeObserver().addOnWindowVisibilityChangeListener(this.windowVisibleListener);
        this.view.getViewTreeObserver().addOnWindowFocusChangeListener(this.windowFocusListener);
        updateState();
    }

    public final void onDestroy() {
        this.view.getViewTreeObserver().removeOnWindowVisibilityChangeListener(this.windowVisibleListener);
        this.view.getViewTreeObserver().removeOnWindowFocusChangeListener(this.windowFocusListener);
        this.registry.setCurrentState(Lifecycle.State.DESTROYED);
    }

    public final void updateState() {
        this.registry.setCurrentState(this.view.getWindowVisibility() != 0 ? Lifecycle.State.CREATED : !this.view.hasWindowFocus() ? Lifecycle.State.STARTED : Lifecycle.State.RESUMED);
    }
}