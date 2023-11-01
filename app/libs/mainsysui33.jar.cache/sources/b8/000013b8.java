package com.android.systemui.controls.controller;

import android.app.backup.BackupManager;
import android.content.ComponentName;
import android.util.AtomicFile;
import android.util.Log;
import android.util.Xml;
import androidx.core.graphics.drawable.IconCompat;
import com.android.systemui.backup.BackupHelper;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/ControlsFavoritePersistenceWrapper.class */
public final class ControlsFavoritePersistenceWrapper {
    public static final Companion Companion = new Companion(null);
    public BackupManager backupManager;
    public final Executor executor;
    public File file;

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/ControlsFavoritePersistenceWrapper$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ControlsFavoritePersistenceWrapper(File file, Executor executor, BackupManager backupManager) {
        this.file = file;
        this.executor = executor;
        this.backupManager = backupManager;
    }

    public /* synthetic */ ControlsFavoritePersistenceWrapper(File file, Executor executor, BackupManager backupManager, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(file, executor, (i & 4) != 0 ? null : backupManager);
    }

    public final void changeFileAndBackupManager(File file, BackupManager backupManager) {
        this.file = file;
        this.backupManager = backupManager;
    }

    public final void deleteFile() {
        this.file.delete();
    }

    public final boolean getFileExists() {
        return this.file.exists();
    }

    public final List<StructureInfo> parseXml(XmlPullParser xmlPullParser) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ComponentName componentName = null;
        String str = null;
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1) {
                return arrayList;
            }
            String name = xmlPullParser.getName();
            String str2 = name;
            if (name == null) {
                str2 = "";
            }
            if (next == 2 && Intrinsics.areEqual(str2, "structure")) {
                componentName = ComponentName.unflattenFromString(xmlPullParser.getAttributeValue(null, "component"));
                str = xmlPullParser.getAttributeValue(null, "structure");
                if (str == null) {
                    str = "";
                }
            } else if (next == 2 && Intrinsics.areEqual(str2, "control")) {
                String attributeValue = xmlPullParser.getAttributeValue(null, "id");
                String attributeValue2 = xmlPullParser.getAttributeValue(null, "title");
                String attributeValue3 = xmlPullParser.getAttributeValue(null, "subtitle");
                if (attributeValue3 == null) {
                    attributeValue3 = "";
                }
                String attributeValue4 = xmlPullParser.getAttributeValue(null, IconCompat.EXTRA_TYPE);
                Integer valueOf = attributeValue4 != null ? Integer.valueOf(Integer.parseInt(attributeValue4)) : null;
                if (attributeValue != null && attributeValue2 != null && valueOf != null) {
                    arrayList2.add(new ControlInfo(attributeValue, attributeValue2, attributeValue3, valueOf.intValue()));
                }
            } else if (next == 3 && Intrinsics.areEqual(str2, "structure")) {
                Intrinsics.checkNotNull(componentName);
                Intrinsics.checkNotNull(str);
                arrayList.add(new StructureInfo(componentName, str, CollectionsKt___CollectionsKt.toList(arrayList2)));
                arrayList2.clear();
            }
        }
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[]}, finally: {[INVOKE] complete} */
    public final List<StructureInfo> readFavorites() {
        BufferedInputStream bufferedInputStream;
        List<StructureInfo> parseXml;
        if (!this.file.exists()) {
            Log.d("ControlsFavoritePersistenceWrapper", "No favorites, returning empty list");
            return CollectionsKt__CollectionsKt.emptyList();
        }
        try {
            try {
                bufferedInputStream = new BufferedInputStream(new FileInputStream(this.file));
                try {
                    File file = this.file;
                    Log.d("ControlsFavoritePersistenceWrapper", "Reading data from file: " + file);
                    synchronized (BackupHelper.Companion.getControlsDataLock()) {
                        XmlPullParser newPullParser = Xml.newPullParser();
                        newPullParser.setInput(bufferedInputStream, null);
                        parseXml = parseXml(newPullParser);
                    }
                    return parseXml;
                } catch (IOException e) {
                    File file2 = this.file;
                    throw new IllegalStateException("Failed parsing favorites file: " + file2, e);
                } catch (XmlPullParserException e2) {
                    File file3 = this.file;
                    throw new IllegalStateException("Failed parsing favorites file: " + file3, e2);
                }
            } finally {
                IoUtils.closeQuietly(bufferedInputStream);
            }
        } catch (FileNotFoundException e3) {
            Log.i("ControlsFavoritePersistenceWrapper", "No file found");
            return CollectionsKt__CollectionsKt.emptyList();
        }
    }

    public final void storeFavorites(final List<StructureInfo> list) {
        if (!list.isEmpty() || this.file.exists()) {
            this.executor.execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsFavoritePersistenceWrapper$storeFavorites$1
                /* JADX WARN: Code restructure failed: missing block: B:26:0x01d4, code lost:
                    r0 = r5.this$0.backupManager;
                 */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void run() {
                    File file;
                    File file2;
                    BackupManager backupManager;
                    file = ControlsFavoritePersistenceWrapper.this.file;
                    Log.d("ControlsFavoritePersistenceWrapper", "Saving data to file: " + file);
                    file2 = ControlsFavoritePersistenceWrapper.this.file;
                    AtomicFile atomicFile = new AtomicFile(file2);
                    Object controlsDataLock = BackupHelper.Companion.getControlsDataLock();
                    List<StructureInfo> list2 = list;
                    synchronized (controlsDataLock) {
                        try {
                            FileOutputStream startWrite = atomicFile.startWrite();
                            XmlSerializer newSerializer = Xml.newSerializer();
                            newSerializer.setOutput(startWrite, "utf-8");
                            newSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                            newSerializer.startDocument(null, Boolean.TRUE);
                            newSerializer.startTag(null, "version");
                            newSerializer.text("1");
                            newSerializer.endTag(null, "version");
                            newSerializer.startTag(null, "structures");
                            for (StructureInfo structureInfo : list2) {
                                newSerializer.startTag(null, "structure");
                                newSerializer.attribute(null, "component", structureInfo.getComponentName().flattenToString());
                                newSerializer.attribute(null, "structure", structureInfo.getStructure().toString());
                                newSerializer.startTag(null, "controls");
                                for (ControlInfo controlInfo : structureInfo.getControls()) {
                                    newSerializer.startTag(null, "control");
                                    newSerializer.attribute(null, "id", controlInfo.getControlId());
                                    newSerializer.attribute(null, "title", controlInfo.getControlTitle().toString());
                                    newSerializer.attribute(null, "subtitle", controlInfo.getControlSubtitle().toString());
                                    newSerializer.attribute(null, IconCompat.EXTRA_TYPE, String.valueOf(controlInfo.getDeviceType()));
                                    newSerializer.endTag(null, "control");
                                }
                                newSerializer.endTag(null, "controls");
                                newSerializer.endTag(null, "structure");
                            }
                            newSerializer.endTag(null, "structures");
                            newSerializer.endDocument();
                            atomicFile.finishWrite(startWrite);
                            IoUtils.closeQuietly(startWrite);
                        } catch (IOException e) {
                            Log.e("ControlsFavoritePersistenceWrapper", "Failed to start write file", e);
                            return;
                        }
                    }
                    if (1 == 0 || backupManager == null) {
                        return;
                    }
                    backupManager.dataChanged();
                }
            });
        }
    }
}