package com.android.systemui.navigationbar.buttons;

import android.view.View;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/buttons/ContextualButtonGroup.class */
public class ContextualButtonGroup extends ButtonDispatcher {
    public final List<ButtonData> mButtonData;

    /* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/buttons/ContextualButtonGroup$ButtonData.class */
    public static final class ButtonData {
        public ContextualButton button;
        public boolean markedVisible = false;

        public ButtonData(ContextualButton contextualButton) {
            this.button = contextualButton;
        }

        public void setVisibility(int i) {
            this.button.setVisibility(i);
        }
    }

    public ContextualButtonGroup(int i) {
        super(i);
        this.mButtonData = new ArrayList();
    }

    public void addButton(ContextualButton contextualButton) {
        contextualButton.setVisibility(4);
        contextualButton.attachToGroup(this);
        this.mButtonData.add(new ButtonData(contextualButton));
    }

    public void dump(PrintWriter printWriter) {
        View currentView = getCurrentView();
        printWriter.println("ContextualButtonGroup");
        printWriter.println("  getVisibleContextButton(): " + getVisibleContextButton());
        printWriter.println("  isVisible(): " + isVisible());
        StringBuilder sb = new StringBuilder();
        sb.append("  attached(): ");
        sb.append(currentView != null && currentView.isAttachedToWindow());
        printWriter.println(sb.toString());
        printWriter.println("  mButtonData [ ");
        for (int size = this.mButtonData.size() - 1; size >= 0; size--) {
            ButtonData buttonData = this.mButtonData.get(size);
            View currentView2 = buttonData.button.getCurrentView();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("    ");
            sb2.append(size);
            sb2.append(": markedVisible=");
            sb2.append(buttonData.markedVisible);
            sb2.append(" visible=");
            sb2.append(buttonData.button.getVisibility());
            sb2.append(" attached=");
            sb2.append(currentView2 != null && currentView2.isAttachedToWindow());
            sb2.append(" alpha=");
            sb2.append(buttonData.button.getAlpha());
            printWriter.println(sb2.toString());
        }
        printWriter.println("  ]");
    }

    public ContextualButton getContextButton(int i) {
        int contextButtonIndex = getContextButtonIndex(i);
        if (contextButtonIndex != -1) {
            return this.mButtonData.get(contextButtonIndex).button;
        }
        return null;
    }

    public final int getContextButtonIndex(int i) {
        for (int i2 = 0; i2 < this.mButtonData.size(); i2++) {
            if (this.mButtonData.get(i2).button.getId() == i) {
                return i2;
            }
        }
        return -1;
    }

    public ContextualButton getVisibleContextButton() {
        for (int size = this.mButtonData.size() - 1; size >= 0; size--) {
            if (this.mButtonData.get(size).markedVisible) {
                return this.mButtonData.get(size).button;
            }
        }
        return null;
    }

    public void removeButton(int i) {
        int contextButtonIndex = getContextButtonIndex(i);
        if (contextButtonIndex != -1) {
            this.mButtonData.remove(contextButtonIndex);
        }
    }

    public int setButtonVisibility(int i, boolean z) {
        int contextButtonIndex = getContextButtonIndex(i);
        if (contextButtonIndex == -1) {
            throw new RuntimeException("Cannot find the button id of " + i + " in context group");
        }
        setVisibility(4);
        this.mButtonData.get(contextButtonIndex).markedVisible = z;
        boolean z2 = false;
        for (int size = this.mButtonData.size() - 1; size >= 0; size--) {
            ButtonData buttonData = this.mButtonData.get(size);
            if (z2 || !buttonData.markedVisible) {
                buttonData.setVisibility(4);
            } else {
                buttonData.setVisibility(0);
                setVisibility(0);
                z2 = true;
            }
        }
        return this.mButtonData.get(contextButtonIndex).button.getVisibility();
    }

    public void updateIcons(int i, int i2) {
        for (ButtonData buttonData : this.mButtonData) {
            buttonData.button.updateIcon(i, i2);
        }
    }
}