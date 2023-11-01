package com.android.systemui.dump;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;

/* loaded from: mainsysui33.jar:com/android/systemui/dump/DumpsysTableLogger.class */
public final class DumpsysTableLogger {
    public final List<String> columns;
    public final List<List<String>> rows;
    public final String sectionName;

    /* JADX DEBUG: Multi-variable search result rejected for r6v0, resolved type: java.util.List<? extends java.util.List<java.lang.String>> */
    /* JADX WARN: Multi-variable type inference failed */
    public DumpsysTableLogger(String str, List<String> list, List<? extends List<String>> list2) {
        this.sectionName = str;
        this.columns = list;
        this.rows = list2;
    }

    public final void printData(PrintWriter printWriter) {
        int size = this.columns.size();
        List<List<String>> list = this.rows;
        ArrayList<List> arrayList = new ArrayList();
        for (Object obj : list) {
            if (((List) obj).size() == size) {
                arrayList.add(obj);
            }
        }
        for (List list2 : arrayList) {
            printWriter.println(CollectionsKt___CollectionsKt.joinToString$default(list2, "|", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null));
        }
    }

    public final void printSchema(PrintWriter printWriter) {
        printWriter.println(CollectionsKt___CollectionsKt.joinToString$default(this.columns, "|", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null));
    }

    public final void printSectionEnd(PrintWriter printWriter) {
        String str = this.sectionName;
        printWriter.println("SystemUI TableSection END: " + str);
    }

    public final void printSectionStart(PrintWriter printWriter) {
        String str = this.sectionName;
        printWriter.println("SystemUI TableSection START: " + str);
        printWriter.println("version 1");
    }

    public final void printTableData(PrintWriter printWriter) {
        printSectionStart(printWriter);
        printSchema(printWriter);
        printData(printWriter);
        printSectionEnd(printWriter);
    }
}