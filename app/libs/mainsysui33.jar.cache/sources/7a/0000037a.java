package androidx.core.content;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Process;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.util.ObjectsCompat;
import java.io.File;
import java.util.concurrent.Executor;

@SuppressLint({"PrivateConstructorForUtilityClass"})
/* loaded from: mainsysui33.jar:androidx/core/content/ContextCompat.class */
public class ContextCompat {
    public static final Object sLock = new Object();
    public static final Object sSync = new Object();

    /* loaded from: mainsysui33.jar:androidx/core/content/ContextCompat$Api16Impl.class */
    public static class Api16Impl {
        public static void startActivities(Context context, Intent[] intentArr, Bundle bundle) {
            context.startActivities(intentArr, bundle);
        }

        public static void startActivity(Context context, Intent intent, Bundle bundle) {
            context.startActivity(intent, bundle);
        }
    }

    /* loaded from: mainsysui33.jar:androidx/core/content/ContextCompat$Api19Impl.class */
    public static class Api19Impl {
        public static File[] getExternalCacheDirs(Context context) {
            return context.getExternalCacheDirs();
        }

        public static File[] getExternalFilesDirs(Context context, String str) {
            return context.getExternalFilesDirs(str);
        }
    }

    /* loaded from: mainsysui33.jar:androidx/core/content/ContextCompat$Api21Impl.class */
    public static class Api21Impl {
        public static Drawable getDrawable(Context context, int i) {
            return context.getDrawable(i);
        }
    }

    /* loaded from: mainsysui33.jar:androidx/core/content/ContextCompat$Api23Impl.class */
    public static class Api23Impl {
        public static int getColor(Context context, int i) {
            return context.getColor(i);
        }

        public static <T> T getSystemService(Context context, Class<T> cls) {
            return (T) context.getSystemService(cls);
        }
    }

    /* loaded from: mainsysui33.jar:androidx/core/content/ContextCompat$Api24Impl.class */
    public static class Api24Impl {
        public static Context createDeviceProtectedStorageContext(Context context) {
            return context.createDeviceProtectedStorageContext();
        }
    }

    /* loaded from: mainsysui33.jar:androidx/core/content/ContextCompat$Api28Impl.class */
    public static class Api28Impl {
        public static Executor getMainExecutor(Context context) {
            return context.getMainExecutor();
        }
    }

    public static int checkSelfPermission(Context context, String str) {
        ObjectsCompat.requireNonNull(str, "permission must be non-null");
        return context.checkPermission(str, Process.myPid(), Process.myUid());
    }

    public static Context createDeviceProtectedStorageContext(Context context) {
        return Api24Impl.createDeviceProtectedStorageContext(context);
    }

    public static int getColor(Context context, int i) {
        return Api23Impl.getColor(context, i);
    }

    public static ColorStateList getColorStateList(Context context, int i) {
        return ResourcesCompat.getColorStateList(context.getResources(), i, context.getTheme());
    }

    public static Drawable getDrawable(Context context, int i) {
        return Api21Impl.getDrawable(context, i);
    }

    public static File[] getExternalCacheDirs(Context context) {
        return Api19Impl.getExternalCacheDirs(context);
    }

    public static File[] getExternalFilesDirs(Context context, String str) {
        return Api19Impl.getExternalFilesDirs(context, str);
    }

    public static Executor getMainExecutor(Context context) {
        return Api28Impl.getMainExecutor(context);
    }

    public static <T> T getSystemService(Context context, Class<T> cls) {
        return (T) Api23Impl.getSystemService(context, cls);
    }

    public static boolean startActivities(Context context, Intent[] intentArr, Bundle bundle) {
        Api16Impl.startActivities(context, intentArr, bundle);
        return true;
    }

    public static void startActivity(Context context, Intent intent, Bundle bundle) {
        Api16Impl.startActivity(context, intent, bundle);
    }
}