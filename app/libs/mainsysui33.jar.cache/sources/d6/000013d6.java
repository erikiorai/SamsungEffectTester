package com.android.systemui.controls.controller;

import android.content.Context;
import android.os.UserHandle;
import com.android.systemui.settings.UserFileManager;
import java.io.File;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/UserStructure.class */
public final class UserStructure {
    public final File auxiliaryFile;
    public final File file;
    public final Context userContext;

    public UserStructure(Context context, UserHandle userHandle, UserFileManager userFileManager) {
        this.userContext = context.createContextAsUser(userHandle, 0);
        this.file = userFileManager.getFile("controls_favorites.xml", userHandle.getIdentifier());
        this.auxiliaryFile = userFileManager.getFile("aux_controls_favorites.xml", userHandle.getIdentifier());
    }

    public final File getAuxiliaryFile() {
        return this.auxiliaryFile;
    }

    public final File getFile() {
        return this.file;
    }

    public final Context getUserContext() {
        return this.userContext;
    }
}