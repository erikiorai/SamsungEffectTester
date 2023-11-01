package com.android.systemui.navigationbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import com.android.systemui.navigationbar.buttons.DeadZone;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/NavigationBarFrame.class */
public class NavigationBarFrame extends FrameLayout {
    public DeadZone mDeadZone;

    public NavigationBarFrame(Context context) {
        super(context);
        this.mDeadZone = null;
    }

    public NavigationBarFrame(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDeadZone = null;
    }

    public NavigationBarFrame(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDeadZone = null;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        DeadZone deadZone;
        return (motionEvent.getAction() != 4 || (deadZone = this.mDeadZone) == null) ? super.dispatchTouchEvent(motionEvent) : deadZone.onTouchEvent(motionEvent);
    }

    public void setDeadZone(DeadZone deadZone) {
        this.mDeadZone = deadZone;
    }
}