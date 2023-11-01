package com.android.systemui.navigationbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/NavigationBarModule.class */
public interface NavigationBarModule {
    static EdgeBackGestureHandler provideEdgeBackGestureHandler(EdgeBackGestureHandler.Factory factory, Context context) {
        return factory.create(context);
    }

    static LayoutInflater provideLayoutInflater(Context context) {
        return LayoutInflater.from(context);
    }

    static NavigationBarFrame provideNavigationBarFrame(LayoutInflater layoutInflater) {
        return (NavigationBarFrame) layoutInflater.inflate(R$layout.navigation_bar_window, (ViewGroup) null);
    }

    static NavigationBarView provideNavigationBarview(LayoutInflater layoutInflater, NavigationBarFrame navigationBarFrame) {
        return (NavigationBarView) layoutInflater.inflate(R$layout.navigation_bar, navigationBarFrame).findViewById(R$id.navigation_bar_view);
    }

    static WindowManager provideWindowManager(Context context) {
        return (WindowManager) context.getSystemService(WindowManager.class);
    }
}