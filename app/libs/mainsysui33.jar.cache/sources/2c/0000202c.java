package com.android.systemui.privacy;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.appcompat.R$styleable;
import com.android.systemui.R$string;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt___MapsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function1;

/* loaded from: mainsysui33.jar:com/android/systemui/privacy/PrivacyChipBuilder.class */
public final class PrivacyChipBuilder {
    public final List<Pair<PrivacyApplication, List<PrivacyType>>> appsAndTypes;
    public final Context context;
    public final String lastSeparator;
    public final String separator;
    public final List<PrivacyType> types;

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:17:0x0075 */
    public PrivacyChipBuilder(Context context, List<PrivacyItem> list) {
        this.context = context;
        this.separator = context.getString(R$string.ongoing_privacy_dialog_separator);
        this.lastSeparator = context.getString(R$string.ongoing_privacy_dialog_last_separator);
        List<PrivacyItem> list2 = list;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (PrivacyItem privacyItem : list2) {
            PrivacyApplication application = privacyItem.getApplication();
            Object obj = linkedHashMap.get(application);
            ArrayList arrayList = obj;
            if (obj == null) {
                arrayList = new ArrayList();
                linkedHashMap.put(application, arrayList);
            }
            ((List) arrayList).add(privacyItem.getPrivacyType());
        }
        this.appsAndTypes = CollectionsKt___CollectionsKt.sortedWith(MapsKt___MapsKt.toList(linkedHashMap), ComparisonsKt__ComparisonsKt.compareBy(new Function1[]{new Function1<Pair<? extends PrivacyApplication, ? extends List<? extends PrivacyType>>, Comparable<?>>() { // from class: com.android.systemui.privacy.PrivacyChipBuilder.3
            /* JADX DEBUG: Method merged with bridge method */
            public final Comparable<?> invoke(Pair<PrivacyApplication, ? extends List<? extends PrivacyType>> pair) {
                return Integer.valueOf(-((List) pair.getSecond()).size());
            }
        }, new Function1<Pair<? extends PrivacyApplication, ? extends List<? extends PrivacyType>>, Comparable<?>>() { // from class: com.android.systemui.privacy.PrivacyChipBuilder.4
            /* JADX DEBUG: Method merged with bridge method */
            public final Comparable<?> invoke(Pair<PrivacyApplication, ? extends List<? extends PrivacyType>> pair) {
                return CollectionsKt___CollectionsKt.minOrNull((Iterable) pair.getSecond());
            }
        }}));
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        for (PrivacyItem privacyItem2 : list2) {
            arrayList2.add(privacyItem2.getPrivacyType());
        }
        this.types = CollectionsKt___CollectionsKt.sorted(CollectionsKt___CollectionsKt.distinct(arrayList2));
    }

    public final List<Drawable> generateIcons() {
        List<PrivacyType> list = this.types;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        for (PrivacyType privacyType : list) {
            arrayList.add(privacyType.getIcon(this.context));
        }
        return arrayList;
    }

    public final String joinTypes() {
        String str;
        int size = this.types.size();
        if (size == 0) {
            str = "";
        } else if (size != 1) {
            List<PrivacyType> list = this.types;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
            for (PrivacyType privacyType : list) {
                arrayList.add(privacyType.getName(this.context));
            }
            str = joinWithAnd(arrayList).toString();
        } else {
            str = this.types.get(0).getName(this.context);
        }
        return str;
    }

    public final <T> StringBuilder joinWithAnd(List<? extends T> list) {
        StringBuilder sb = (StringBuilder) CollectionsKt___CollectionsKt.joinTo$default(list.subList(0, list.size() - 1), new StringBuilder(), this.separator, (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, (int) R$styleable.AppCompatTheme_windowMinWidthMajor, (Object) null);
        sb.append(this.lastSeparator);
        sb.append(CollectionsKt___CollectionsKt.last(list));
        return sb;
    }
}