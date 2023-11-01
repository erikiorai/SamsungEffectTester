package com.android.systemui.globalactions;

import android.content.Context;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

/* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsItem.class */
public class GlobalActionsItem extends LinearLayout {
    public GlobalActionsItem(Context context) {
        super(context);
    }

    public GlobalActionsItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public GlobalActionsItem(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public final TextView getTextView() {
        return (TextView) findViewById(16908299);
    }

    public boolean isTruncated() {
        TextView textView = getTextView();
        boolean z = false;
        if (textView != null) {
            Layout layout = textView.getLayout();
            z = false;
            if (layout != null) {
                z = false;
                if (layout.getLineCount() > 0) {
                    z = false;
                    if (layout.getEllipsisCount(layout.getLineCount() - 1) > 0) {
                        z = true;
                    }
                }
            }
        }
        return z;
    }

    public void setMarquee(boolean z) {
        TextView textView = getTextView();
        textView.setSingleLine(z);
        textView.setEllipsize(z ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
    }
}