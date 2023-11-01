package com.android.systemui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.plugins.ViewProvider;

/* loaded from: mainsysui33.jar:com/android/systemui/PluginInflateContainer.class */
public class PluginInflateContainer extends AutoReinflateContainer implements PluginListener<ViewProvider> {
    public Class<ViewProvider> mClass;
    public View mPluginView;

    /* JADX DEBUG: Type inference failed for r1v10. Raw type applied. Possible types: java.lang.Class<?>, java.lang.Class<com.android.systemui.plugins.ViewProvider> */
    public PluginInflateContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.PluginInflateContainer);
        String string = obtainStyledAttributes.getString(R$styleable.PluginInflateContainer_viewType);
        obtainStyledAttributes.recycle();
        try {
            this.mClass = Class.forName(string);
        } catch (Exception e) {
            Log.d("PluginInflateContainer", "Problem getting class info " + string, e);
            this.mClass = null;
        }
    }

    @Override // com.android.systemui.AutoReinflateContainer
    public void inflateLayoutImpl() {
        View view = this.mPluginView;
        if (view != null) {
            addView(view);
        } else {
            super.inflateLayoutImpl();
        }
    }

    @Override // com.android.systemui.AutoReinflateContainer, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mClass != null) {
            ((PluginManager) Dependency.get(PluginManager.class)).addPluginListener(this, this.mClass);
        }
    }

    @Override // com.android.systemui.AutoReinflateContainer, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mClass != null) {
            ((PluginManager) Dependency.get(PluginManager.class)).removePluginListener(this);
        }
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // com.android.systemui.plugins.PluginListener
    public void onPluginConnected(ViewProvider viewProvider, Context context) {
        this.mPluginView = viewProvider.getView();
        inflateLayout();
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // com.android.systemui.plugins.PluginListener
    public void onPluginDisconnected(ViewProvider viewProvider) {
        this.mPluginView = null;
        inflateLayout();
    }
}