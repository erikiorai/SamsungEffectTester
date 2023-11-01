package androidx.slice.widget;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.net.Uri;
import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.Slice;
import androidx.slice.SliceItem;
import androidx.slice.SliceUtils;
import androidx.slice.core.SliceAction;
import androidx.slice.core.SliceActionImpl;
import androidx.slice.core.SliceQuery;
import com.android.systemui.assist.AssistManager;

/* loaded from: mainsysui33.jar:androidx/slice/widget/SliceContent.class */
public class SliceContent {
    public SliceItem mColorItem;
    public SliceItem mContentDescr;
    public SliceItem mLayoutDirItem;
    public int mRowIndex;
    public SliceItem mSliceItem;

    public SliceContent(Slice slice) {
        if (slice == null) {
            return;
        }
        init(new SliceItem(slice, "slice", (String) null, slice.getHints()));
        this.mRowIndex = -1;
    }

    public SliceContent(SliceItem sliceItem, int i) {
        if (sliceItem == null) {
            return;
        }
        init(sliceItem);
        this.mRowIndex = i;
    }

    public final SliceAction fallBackToAppData(Context context, SliceItem sliceItem, SliceItem sliceItem2, int i, SliceItem sliceItem3) {
        SliceItem find = SliceQuery.find(this.mSliceItem, "slice", (String) null, (String) null);
        if (find == null) {
            return null;
        }
        Uri uri = find.getSlice().getUri();
        IconCompat icon = sliceItem2 != null ? sliceItem2.getIcon() : null;
        CharSequence text = sliceItem != null ? sliceItem.getText() : null;
        CharSequence charSequence = text;
        IconCompat iconCompat = icon;
        int i2 = i;
        SliceItem sliceItem4 = sliceItem3;
        if (context != null) {
            PackageManager packageManager = context.getPackageManager();
            ProviderInfo resolveContentProvider = packageManager.resolveContentProvider(uri.getAuthority(), 0);
            ApplicationInfo applicationInfo = resolveContentProvider != null ? resolveContentProvider.applicationInfo : null;
            charSequence = text;
            iconCompat = icon;
            i2 = i;
            sliceItem4 = sliceItem3;
            if (applicationInfo != null) {
                IconCompat iconCompat2 = icon;
                if (icon == null) {
                    iconCompat2 = SliceViewUtil.createIconFromDrawable(packageManager.getApplicationIcon(applicationInfo));
                    i = 2;
                }
                CharSequence charSequence2 = text;
                if (text == null) {
                    charSequence2 = packageManager.getApplicationLabel(applicationInfo);
                }
                charSequence = charSequence2;
                iconCompat = iconCompat2;
                i2 = i;
                sliceItem4 = sliceItem3;
                if (sliceItem3 == null) {
                    Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage(applicationInfo.packageName);
                    charSequence = charSequence2;
                    iconCompat = iconCompat2;
                    i2 = i;
                    sliceItem4 = sliceItem3;
                    if (launchIntentForPackage != null) {
                        sliceItem4 = new SliceItem(PendingIntent.getActivity(context, 0, launchIntentForPackage, 67108864), new Slice.Builder(uri).build(), AssistManager.ACTION_KEY, null, new String[0]);
                        i2 = i;
                        iconCompat = iconCompat2;
                        charSequence = charSequence2;
                    }
                }
            }
        }
        SliceItem sliceItem5 = sliceItem4;
        if (sliceItem4 == null) {
            sliceItem5 = new SliceItem(PendingIntent.getActivity(context, 0, new Intent(), 67108864), null, AssistManager.ACTION_KEY, null, null);
        }
        if (charSequence == null || iconCompat == null) {
            return null;
        }
        return new SliceActionImpl(sliceItem5.getAction(), iconCompat, i2, charSequence);
    }

    public int getAccentColor() {
        SliceItem sliceItem = this.mColorItem;
        return sliceItem != null ? sliceItem.getInt() : -1;
    }

    public CharSequence getContentDescription() {
        SliceItem sliceItem = this.mContentDescr;
        return sliceItem != null ? sliceItem.getText() : null;
    }

    public int getHeight(SliceStyle sliceStyle, SliceViewPolicy sliceViewPolicy) {
        return 0;
    }

    public int getLayoutDir() {
        SliceItem sliceItem = this.mLayoutDirItem;
        return sliceItem != null ? SliceViewUtil.resolveLayoutDirection(sliceItem.getInt()) : -1;
    }

    public int getRowIndex() {
        return this.mRowIndex;
    }

    public SliceAction getShortcut(Context context) {
        SliceItem sliceItem;
        SliceItem sliceItem2;
        SliceItem sliceItem3 = this.mSliceItem;
        if (sliceItem3 == null) {
            return null;
        }
        SliceItem find = SliceQuery.find(sliceItem3, AssistManager.ACTION_KEY, new String[]{"title", "shortcut"}, (String[]) null);
        if (find != null) {
            sliceItem = SliceQuery.find(find, "image", "title", (String) null);
            sliceItem2 = SliceQuery.find(find, "text", (String) null, (String) null);
        } else {
            sliceItem = null;
            sliceItem2 = null;
        }
        SliceItem sliceItem4 = find;
        if (find == null) {
            sliceItem4 = SliceQuery.find(this.mSliceItem, AssistManager.ACTION_KEY, (String) null, (String) null);
        }
        SliceItem sliceItem5 = sliceItem;
        if (sliceItem == null) {
            sliceItem5 = SliceQuery.find(this.mSliceItem, "image", "title", (String) null);
        }
        SliceItem sliceItem6 = sliceItem2;
        if (sliceItem2 == null) {
            sliceItem6 = SliceQuery.find(this.mSliceItem, "text", "title", (String) null);
        }
        SliceItem sliceItem7 = sliceItem5;
        if (sliceItem5 == null) {
            sliceItem7 = SliceQuery.find(this.mSliceItem, "image", (String) null, (String) null);
        }
        SliceItem sliceItem8 = sliceItem6;
        if (sliceItem6 == null) {
            sliceItem8 = SliceQuery.find(this.mSliceItem, "text", (String) null, (String) null);
        }
        int parseImageMode = sliceItem7 != null ? SliceUtils.parseImageMode(sliceItem7) : 5;
        if (context != null) {
            return fallBackToAppData(context, sliceItem8, sliceItem7, parseImageMode, sliceItem4);
        }
        if (sliceItem7 == null || sliceItem4 == null || sliceItem8 == null) {
            return null;
        }
        return new SliceActionImpl(sliceItem4.getAction(), sliceItem7.getIcon(), parseImageMode, sliceItem8.getText());
    }

    public SliceItem getSliceItem() {
        return this.mSliceItem;
    }

    public final void init(SliceItem sliceItem) {
        this.mSliceItem = sliceItem;
        if ("slice".equals(sliceItem.getFormat()) || AssistManager.ACTION_KEY.equals(sliceItem.getFormat())) {
            this.mColorItem = SliceQuery.findTopLevelItem(sliceItem.getSlice(), "int", "color", null, null);
            this.mLayoutDirItem = SliceQuery.findTopLevelItem(sliceItem.getSlice(), "int", "layout_direction", null, null);
        }
        this.mContentDescr = SliceQuery.findSubtype(sliceItem, "text", "content_description");
    }

    public boolean isValid() {
        return this.mSliceItem != null;
    }
}