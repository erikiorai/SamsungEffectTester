package com.android.systemui;

import android.service.dreams.DreamService;
import com.android.systemui.DessertCaseView;

/* loaded from: mainsysui33.jar:com/android/systemui/DessertCaseDream.class */
public class DessertCaseDream extends DreamService {
    public DessertCaseView.RescalingContainer mContainer;
    public DessertCaseView mView;

    @Override // android.service.dreams.DreamService, android.view.Window.Callback
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setInteractive(false);
        this.mView = new DessertCaseView(this);
        DessertCaseView.RescalingContainer rescalingContainer = new DessertCaseView.RescalingContainer(this);
        this.mContainer = rescalingContainer;
        rescalingContainer.setView(this.mView);
        setContentView(this.mContainer);
    }

    @Override // android.service.dreams.DreamService
    public void onDreamingStarted() {
        super.onDreamingStarted();
        this.mView.postDelayed(new Runnable() { // from class: com.android.systemui.DessertCaseDream.1
            @Override // java.lang.Runnable
            public void run() {
                DessertCaseDream.this.mView.start();
            }
        }, 1000L);
    }

    @Override // android.service.dreams.DreamService
    public void onDreamingStopped() {
        super.onDreamingStopped();
        this.mView.stop();
    }
}