package com.android.settingslib.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.DeviceConfig;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import androidx.core.graphics.drawable.IconCompat;
import com.android.settingslib.R$dimen;
import com.android.settingslib.R$string;
import java.io.IOException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/BluetoothUtils.class */
public class BluetoothUtils {
    public static ErrorListener sErrorListener;

    /* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/BluetoothUtils$ErrorListener.class */
    public interface ErrorListener {
        void onShowError(Context context, String str, int i);
    }

    public static Bitmap createBitmap(Drawable drawable, int i, int i2) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return createBitmap;
    }

    public static IconCompat createIconWithDrawable(Drawable drawable) {
        Bitmap createBitmap;
        if (drawable instanceof BitmapDrawable) {
            createBitmap = ((BitmapDrawable) drawable).getBitmap();
        } else {
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            if (intrinsicWidth <= 0) {
                intrinsicWidth = 1;
            }
            if (intrinsicHeight <= 0) {
                intrinsicHeight = 1;
            }
            createBitmap = createBitmap(drawable, intrinsicWidth, intrinsicHeight);
        }
        return IconCompat.createWithBitmap(createBitmap);
    }

    @SuppressLint({"NewApi"})
    public static boolean doesClassMatch(BluetoothClass bluetoothClass, int i) {
        return bluetoothClass.doesClassMatch(i);
    }

    public static String extraTagValue(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return null;
        }
        Matcher matcher = Pattern.compile(generateExpressionWithTag(str, "(.*?)")).matcher(str2);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static String generateExpressionWithTag(String str, String str2) {
        return getTagStart(str) + str2 + getTagEnd(str);
    }

    public static Drawable getBluetoothDrawable(Context context, int i) {
        return context.getDrawable(i);
    }

    public static boolean getBooleanMetaData(BluetoothDevice bluetoothDevice, int i) {
        byte[] metadata;
        if (bluetoothDevice == null || (metadata = bluetoothDevice.getMetadata(i)) == null) {
            return false;
        }
        return Boolean.parseBoolean(new String(metadata));
    }

    public static Pair<Drawable, String> getBtClassDrawableWithDescription(Context context, CachedBluetoothDevice cachedBluetoothDevice) {
        BluetoothClass btClass = cachedBluetoothDevice.getBtClass();
        if (btClass != null) {
            int majorDeviceClass = btClass.getMajorDeviceClass();
            if (majorDeviceClass == 256) {
                return new Pair<>(getBluetoothDrawable(context, 17302338), context.getString(R$string.bluetooth_talkback_computer));
            }
            if (majorDeviceClass == 512) {
                return new Pair<>(getBluetoothDrawable(context, 17302822), context.getString(R$string.bluetooth_talkback_phone));
            }
            if (majorDeviceClass == 1280) {
                return new Pair<>(getBluetoothDrawable(context, HidProfile.getHidClassDrawable(btClass)), context.getString(R$string.bluetooth_talkback_input_peripheral));
            }
            if (majorDeviceClass == 1536) {
                return new Pair<>(getBluetoothDrawable(context, 17302855), context.getString(R$string.bluetooth_talkback_imaging));
            }
        }
        for (LocalBluetoothProfile localBluetoothProfile : cachedBluetoothDevice.getProfiles()) {
            int drawableResource = localBluetoothProfile.getDrawableResource(btClass);
            if (drawableResource != 0) {
                return new Pair<>(getBluetoothDrawable(context, drawableResource), null);
            }
        }
        if (btClass != null) {
            if (doesClassMatch(btClass, 0)) {
                return new Pair<>(getBluetoothDrawable(context, 17302336), context.getString(R$string.bluetooth_talkback_headset));
            }
            if (doesClassMatch(btClass, 1)) {
                return new Pair<>(getBluetoothDrawable(context, 17302335), context.getString(R$string.bluetooth_talkback_headphone));
            }
        }
        return new Pair<>(getBluetoothDrawable(context, 17302853).mutate(), context.getString(R$string.bluetooth_talkback_bluetooth));
    }

    public static Pair<Drawable, String> getBtDrawableWithDescription(Context context, CachedBluetoothDevice cachedBluetoothDevice) {
        Uri uriMetaData;
        Pair<Drawable, String> btClassDrawableWithDescription = getBtClassDrawableWithDescription(context, cachedBluetoothDevice);
        BluetoothDevice device = cachedBluetoothDevice.getDevice();
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R$dimen.bt_nearby_icon_size);
        Resources resources = context.getResources();
        if (isAdvancedDetailsHeader(device) && (uriMetaData = getUriMetaData(device, 5)) != null) {
            try {
                context.getContentResolver().takePersistableUriPermission(uriMetaData, 1);
            } catch (SecurityException e) {
                Log.e("BluetoothUtils", "Failed to take persistable permission for: " + uriMetaData, e);
            }
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uriMetaData);
                if (bitmap != null) {
                    Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, dimensionPixelSize, dimensionPixelSize, false);
                    bitmap.recycle();
                    return new Pair<>(new BitmapDrawable(resources, createScaledBitmap), (String) btClassDrawableWithDescription.second);
                }
            } catch (IOException e2) {
                Log.e("BluetoothUtils", "Failed to get drawable for: " + uriMetaData, e2);
            } catch (SecurityException e3) {
                Log.e("BluetoothUtils", "Failed to get permission for: " + uriMetaData, e3);
            }
        }
        return new Pair<>((Drawable) btClassDrawableWithDescription.first, (String) btClassDrawableWithDescription.second);
    }

    public static String getControlUriMetaData(BluetoothDevice bluetoothDevice) {
        return extraTagValue("HEARABLE_CONTROL_SLICE_WITH_WIDTH", getStringMetaData(bluetoothDevice, 25));
    }

    public static String getStringMetaData(BluetoothDevice bluetoothDevice, int i) {
        byte[] metadata;
        if (bluetoothDevice == null || (metadata = bluetoothDevice.getMetadata(i)) == null) {
            return null;
        }
        return new String(metadata);
    }

    public static String getTagEnd(String str) {
        return String.format(Locale.ENGLISH, "</%s>", str);
    }

    public static String getTagStart(String str) {
        return String.format(Locale.ENGLISH, "<%s>", str);
    }

    public static Uri getUriMetaData(BluetoothDevice bluetoothDevice, int i) {
        String stringMetaData = getStringMetaData(bluetoothDevice, i);
        if (stringMetaData == null) {
            return null;
        }
        return Uri.parse(stringMetaData);
    }

    public static boolean isAdvancedDetailsHeader(BluetoothDevice bluetoothDevice) {
        if (isAdvancedHeaderEnabled()) {
            if (isUntetheredHeadset(bluetoothDevice)) {
                return true;
            }
            String stringMetaData = getStringMetaData(bluetoothDevice, 17);
            if (TextUtils.equals(stringMetaData, "Untethered Headset") || TextUtils.equals(stringMetaData, "Watch") || TextUtils.equals(stringMetaData, "Default")) {
                Log.d("BluetoothUtils", "isAdvancedDetailsHeader: deviceType is " + stringMetaData);
                return true;
            }
            return false;
        }
        return false;
    }

    public static boolean isAdvancedHeaderEnabled() {
        if (DeviceConfig.getBoolean("settings_ui", "bt_advanced_header_enabled", true)) {
            return true;
        }
        Log.d("BluetoothUtils", "isAdvancedDetailsHeader: advancedEnabled is false");
        return false;
    }

    public static boolean isAdvancedUntetheredDevice(BluetoothDevice bluetoothDevice) {
        if (isAdvancedHeaderEnabled()) {
            if (isUntetheredHeadset(bluetoothDevice)) {
                return true;
            }
            if (TextUtils.equals(getStringMetaData(bluetoothDevice, 17), "Untethered Headset")) {
                Log.d("BluetoothUtils", "isAdvancedUntetheredDevice: is untethered device ");
                return true;
            }
            return false;
        }
        return false;
    }

    public static boolean isUntetheredHeadset(BluetoothDevice bluetoothDevice) {
        if (getBooleanMetaData(bluetoothDevice, 6)) {
            Log.d("BluetoothUtils", "isAdvancedDetailsHeader: untetheredHeadset is true");
            return true;
        }
        return false;
    }

    public static void setErrorListener(ErrorListener errorListener) {
        sErrorListener = errorListener;
    }

    public static void showError(Context context, String str, int i) {
        ErrorListener errorListener = sErrorListener;
        if (errorListener != null) {
            errorListener.onShowError(context, str, i);
        }
    }
}