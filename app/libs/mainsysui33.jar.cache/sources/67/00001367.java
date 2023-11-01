package com.android.systemui.common.ui.binder;

import android.widget.TextView;
import com.android.systemui.common.shared.model.Text;
import kotlin.NoWhenBranchMatchedException;

/* loaded from: mainsysui33.jar:com/android/systemui/common/ui/binder/TextViewBinder.class */
public final class TextViewBinder {
    public static final TextViewBinder INSTANCE = new TextViewBinder();

    public final void bind(TextView textView, Text text) {
        String text2;
        if (text instanceof Text.Resource) {
            text2 = textView.getContext().getString(((Text.Resource) text).getRes());
        } else if (!(text instanceof Text.Loaded)) {
            throw new NoWhenBranchMatchedException();
        } else {
            text2 = ((Text.Loaded) text).getText();
        }
        textView.setText(text2);
    }
}