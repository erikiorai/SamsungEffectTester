package com.android.systemui.dreams;

import android.view.View;
import com.android.systemui.statusbar.policy.CallbackController;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayStatusBarItemsProvider.class */
public class DreamOverlayStatusBarItemsProvider implements CallbackController<Callback> {
    public final Executor mExecutor;
    public final List<StatusBarItem> mItems = new ArrayList();
    public final List<Callback> mCallbacks = new ArrayList();

    /* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayStatusBarItemsProvider$Callback.class */
    public interface Callback {
        void onStatusBarItemsChanged(List<StatusBarItem> list);
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayStatusBarItemsProvider$StatusBarItem.class */
    public interface StatusBarItem {
        View getView();
    }

    public DreamOverlayStatusBarItemsProvider(Executor executor) {
        this.mExecutor = executor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addCallback$0(Callback callback) {
        Objects.requireNonNull(callback, "Callback must not be null.");
        if (this.mCallbacks.contains(callback)) {
            return;
        }
        this.mCallbacks.add(callback);
        if (this.mItems.isEmpty()) {
            return;
        }
        callback.onStatusBarItemsChanged(this.mItems);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeCallback$1(Callback callback) {
        Objects.requireNonNull(callback, "Callback must not be null.");
        this.mCallbacks.remove(callback);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public void addCallback(final Callback callback) {
        this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.dreams.DreamOverlayStatusBarItemsProvider$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                DreamOverlayStatusBarItemsProvider.this.lambda$addCallback$0(callback);
            }
        });
    }

    /* JADX DEBUG: Method merged with bridge method */
    public void removeCallback(final Callback callback) {
        this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.dreams.DreamOverlayStatusBarItemsProvider$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DreamOverlayStatusBarItemsProvider.this.lambda$removeCallback$1(callback);
            }
        });
    }
}