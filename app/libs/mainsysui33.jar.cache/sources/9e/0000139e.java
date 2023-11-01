package com.android.systemui.controls.controller;

import android.content.ComponentName;
import android.service.controls.Control;
import android.service.controls.actions.ControlAction;
import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.ui.SelectedItem;
import com.android.systemui.util.UserAwareController;
import java.util.List;
import java.util.function.Consumer;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/ControlsController.class */
public interface ControlsController extends UserAwareController {

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/ControlsController$LoadData.class */
    public interface LoadData {
        List<ControlStatus> getAllControls();

        boolean getErrorOnLoad();

        List<String> getFavoritesIds();
    }

    void action(ComponentName componentName, ControlInfo controlInfo, ControlAction controlAction);

    void addFavorite(ComponentName componentName, CharSequence charSequence, ControlInfo controlInfo);

    boolean addSeedingFavoritesCallback(Consumer<Boolean> consumer);

    int countFavoritesForComponent(ComponentName componentName);

    List<StructureInfo> getFavorites();

    List<StructureInfo> getFavoritesForComponent(ComponentName componentName);

    SelectedItem getPreferredSelection();

    void onActionResponse(ComponentName componentName, String str, int i);

    void refreshStatus(ComponentName componentName, Control control);

    void seedFavoritesForComponents(List<ComponentName> list, Consumer<SeedResponse> consumer);

    void subscribeToFavorites(StructureInfo structureInfo);

    void unsubscribe();
}