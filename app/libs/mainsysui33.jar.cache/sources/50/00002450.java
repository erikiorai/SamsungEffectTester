package com.android.systemui.screenshot;

import android.app.PendingIntent;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import java.util.Objects;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/OverlayActionChip.class */
public class OverlayActionChip extends FrameLayout {
    public ImageView mIconView;
    public boolean mIsPending;
    public TextView mTextView;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.OverlayActionChip$$ExternalSyntheticLambda0.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$VkI4hO9Zj_UqQVFkA7kJgbGZncI(PendingIntent pendingIntent, Runnable runnable, View view) {
        lambda$setPendingIntent$0(pendingIntent, runnable, view);
    }

    public OverlayActionChip(Context context) {
        this(context, null);
    }

    public OverlayActionChip(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public OverlayActionChip(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public OverlayActionChip(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mIsPending = false;
    }

    public static /* synthetic */ void lambda$setPendingIntent$0(PendingIntent pendingIntent, Runnable runnable, View view) {
        try {
            pendingIntent.send();
            runnable.run();
        } catch (PendingIntent.CanceledException e) {
            Log.e("ScreenshotActionChip", "Intent cancelled", e);
        }
    }

    @Override // android.view.View
    public void onFinishInflate() {
        ImageView imageView = (ImageView) findViewById(R$id.overlay_action_chip_icon);
        Objects.requireNonNull(imageView);
        this.mIconView = imageView;
        TextView textView = (TextView) findViewById(R$id.overlay_action_chip_text);
        Objects.requireNonNull(textView);
        this.mTextView = textView;
        updatePadding(textView.getText().length() > 0);
    }

    public void setIcon(Icon icon, boolean z) {
        this.mIconView.setImageIcon(icon);
        if (z) {
            return;
        }
        this.mIconView.setImageTintList(null);
    }

    public void setIsPending(boolean z) {
        this.mIsPending = z;
        setPressed(z);
    }

    public void setPendingIntent(final PendingIntent pendingIntent, final Runnable runnable) {
        setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenshot.OverlayActionChip$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                OverlayActionChip.$r8$lambda$VkI4hO9Zj_UqQVFkA7kJgbGZncI(pendingIntent, runnable, view);
            }
        });
    }

    @Override // android.view.View
    public void setPressed(boolean z) {
        super.setPressed(this.mIsPending || z);
    }

    public void setText(CharSequence charSequence) {
        this.mTextView.setText(charSequence);
        updatePadding(charSequence.length() > 0);
    }

    public final void updatePadding(boolean z) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mIconView.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mTextView.getLayoutParams();
        if (z) {
            int dimensionPixelSize = ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R$dimen.overlay_action_chip_padding_horizontal);
            int dimensionPixelSize2 = ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R$dimen.overlay_action_chip_spacing);
            layoutParams.setMarginStart(dimensionPixelSize);
            layoutParams.setMarginEnd(dimensionPixelSize2);
            layoutParams2.setMarginEnd(dimensionPixelSize);
        } else {
            int dimensionPixelSize3 = ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R$dimen.overlay_action_chip_icon_only_padding_horizontal);
            layoutParams.setMarginStart(dimensionPixelSize3);
            layoutParams.setMarginEnd(dimensionPixelSize3);
        }
        this.mIconView.setLayoutParams(layoutParams);
        this.mTextView.setLayoutParams(layoutParams2);
    }
}