package androidx.core.view.accessibility;

import android.os.Bundle;
import android.view.View;

/* loaded from: mainsysui33.jar:androidx/core/view/accessibility/AccessibilityViewCommand.class */
public interface AccessibilityViewCommand {

    /* loaded from: mainsysui33.jar:androidx/core/view/accessibility/AccessibilityViewCommand$CommandArguments.class */
    public static abstract class CommandArguments {
        public Bundle mBundle;

        public void setBundle(Bundle bundle) {
            this.mBundle = bundle;
        }
    }

    /* loaded from: mainsysui33.jar:androidx/core/view/accessibility/AccessibilityViewCommand$MoveAtGranularityArguments.class */
    public static final class MoveAtGranularityArguments extends CommandArguments {
    }

    /* loaded from: mainsysui33.jar:androidx/core/view/accessibility/AccessibilityViewCommand$MoveHtmlArguments.class */
    public static final class MoveHtmlArguments extends CommandArguments {
    }

    /* loaded from: mainsysui33.jar:androidx/core/view/accessibility/AccessibilityViewCommand$MoveWindowArguments.class */
    public static final class MoveWindowArguments extends CommandArguments {
    }

    /* loaded from: mainsysui33.jar:androidx/core/view/accessibility/AccessibilityViewCommand$ScrollToPositionArguments.class */
    public static final class ScrollToPositionArguments extends CommandArguments {
    }

    /* loaded from: mainsysui33.jar:androidx/core/view/accessibility/AccessibilityViewCommand$SetProgressArguments.class */
    public static final class SetProgressArguments extends CommandArguments {
    }

    /* loaded from: mainsysui33.jar:androidx/core/view/accessibility/AccessibilityViewCommand$SetSelectionArguments.class */
    public static final class SetSelectionArguments extends CommandArguments {
    }

    /* loaded from: mainsysui33.jar:androidx/core/view/accessibility/AccessibilityViewCommand$SetTextArguments.class */
    public static final class SetTextArguments extends CommandArguments {
    }

    boolean perform(View view, CommandArguments commandArguments);
}