package com.android.systemui.plugins.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
/* loaded from: mainsysui33.jar:com/android/systemui/plugins/annotations/Dependencies.class */
public @interface Dependencies {
    DependsOn[] value();
}