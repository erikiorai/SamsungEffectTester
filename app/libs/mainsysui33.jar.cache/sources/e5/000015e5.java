package com.android.systemui.decor;

import android.content.res.Resources;
import android.util.Log;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;

/* loaded from: mainsysui33.jar:com/android/systemui/decor/CutoutDecorProviderFactory.class */
public final class CutoutDecorProviderFactory extends DecorProviderFactory {
    public final Display display;
    public final DisplayInfo displayInfo = new DisplayInfo();
    public final Resources res;

    public CutoutDecorProviderFactory(Resources resources, Display display) {
        this.res = resources;
        this.display = display;
    }

    @Override // com.android.systemui.decor.DecorProviderFactory
    public boolean getHasProviders() {
        Display display = this.display;
        if (display != null) {
            display.getDisplayInfo(this.displayInfo);
        } else {
            Log.w("CutoutDecorProviderFactory", "display is null, can't update displayInfo");
        }
        return DisplayCutout.getFillBuiltInDisplayCutout(this.res, this.displayInfo.uniqueId);
    }

    @Override // com.android.systemui.decor.DecorProviderFactory
    public List<DecorProvider> getProviders() {
        List<Integer> boundBaseOnCurrentRotation;
        if (getHasProviders()) {
            ArrayList arrayList = new ArrayList();
            DisplayCutout displayCutout = this.displayInfo.displayCutout;
            if (displayCutout != null && (boundBaseOnCurrentRotation = FaceScanningProviderFactoryKt.getBoundBaseOnCurrentRotation(displayCutout)) != null) {
                for (Integer num : boundBaseOnCurrentRotation) {
                    arrayList.add(new CutoutDecorProviderImpl(FaceScanningProviderFactoryKt.baseOnRotation0(num.intValue(), this.displayInfo.rotation)));
                }
            }
            return arrayList;
        }
        return CollectionsKt__CollectionsKt.emptyList();
    }
}