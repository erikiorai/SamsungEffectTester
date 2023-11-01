package com.android.systemui.plugins.annotations;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Repeatable(Dependencies.class)
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: mainsysui33.jar:com/android/systemui/plugins/annotations/DependsOn.class */
public @interface DependsOn {
    Class<?> target();
}