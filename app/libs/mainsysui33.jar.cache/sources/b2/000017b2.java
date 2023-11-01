package com.android.systemui.flags;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import com.android.systemui.flags.FlagListenable;
import com.android.systemui.flags.ServerFlagReader;
import com.android.systemui.util.settings.SecureSettings;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/FeatureFlagsDebug.class */
public class FeatureFlagsDebug implements FeatureFlags {
    public final Map<Integer, Flag<?>> mAllFlags;
    public final Context mContext;
    public final FlagManager mFlagManager;
    public final Resources mResources;
    public final Restarter mRestarter;
    public final SecureSettings mSecureSettings;
    public final ServerFlagReader mServerFlagReader;
    public final SystemPropertiesHelper mSystemProperties;
    public final Map<Integer, Boolean> mBooleanFlagCache = new TreeMap();
    public final Map<Integer, String> mStringFlagCache = new TreeMap();
    public final Map<Integer, Integer> mIntFlagCache = new TreeMap();
    public final ServerFlagReader.ChangeListener mOnPropertiesChanged = new ServerFlagReader.ChangeListener() { // from class: com.android.systemui.flags.FeatureFlagsDebug.1
        {
            FeatureFlagsDebug.this = this;
        }

        @Override // com.android.systemui.flags.ServerFlagReader.ChangeListener
        public void onChange() {
            FeatureFlagsDebug.this.mRestarter.restartSystemUI();
        }
    };
    public final BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.flags.FeatureFlagsDebug.2
        {
            FeatureFlagsDebug.this = this;
        }

        public final void handleSetFlag(Bundle bundle) {
            if (bundle == null) {
                Log.w("SysUIFlags", "No extras");
                return;
            }
            int i = bundle.getInt("id");
            if (i <= 0) {
                Log.w("SysUIFlags", "ID not set or less than  or equal to 0: " + i);
            } else if (!FeatureFlagsDebug.this.mAllFlags.containsKey(Integer.valueOf(i))) {
                Log.w("SysUIFlags", "Tried to set unknown id: " + i);
            } else {
                Flag<?> flag = (Flag) FeatureFlagsDebug.this.mAllFlags.get(Integer.valueOf(i));
                if (!bundle.containsKey("value")) {
                    FeatureFlagsDebug.this.eraseFlag(flag);
                    return;
                }
                Object obj = bundle.get("value");
                try {
                    if (obj instanceof Boolean) {
                        FeatureFlagsDebug.this.setBooleanFlagInternal(flag, ((Boolean) obj).booleanValue());
                    } else if (!(obj instanceof String)) {
                        throw new IllegalArgumentException("Unknown value type");
                    } else {
                        FeatureFlagsDebug.this.setStringFlagInternal(flag, (String) obj);
                    }
                } catch (IllegalArgumentException e) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Unable to set ");
                    sb.append(flag.getId());
                    sb.append(" of type ");
                    sb.append(flag.getClass());
                    sb.append(" to value of type ");
                    sb.append(obj == null ? null : obj.getClass());
                    Log.w("SysUIFlags", sb.toString());
                }
            }
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent == null ? null : intent.getAction();
            if (action == null) {
                return;
            }
            if ("com.android.systemui.action.SET_FLAG".equals(action)) {
                handleSetFlag(intent.getExtras());
            } else if ("com.android.systemui.action.GET_FLAGS".equals(action)) {
                ArrayList arrayList = new ArrayList(FeatureFlagsDebug.this.mAllFlags.values());
                ArrayList<? extends Parcelable> arrayList2 = new ArrayList<>();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ParcelableFlag<?> parcelableFlag = toParcelableFlag((Flag) it.next());
                    if (parcelableFlag != null) {
                        arrayList2.add(parcelableFlag);
                    }
                }
                Bundle resultExtras = getResultExtras(true);
                if (resultExtras != null) {
                    resultExtras.putParcelableArrayList("flags", arrayList2);
                }
            }
        }

        public final ParcelableFlag<?> toParcelableFlag(Flag<?> flag) {
            boolean isEnabled;
            boolean isEmpty;
            boolean teamfood = flag.getTeamfood();
            if (flag instanceof ReleasedFlag) {
                boolean isEnabled2 = FeatureFlagsDebug.this.isEnabled((ReleasedFlag) flag);
                isEnabled = isEnabled2;
                if (FeatureFlagsDebug.this.readBooleanFlagOverride(flag.getId()) != null) {
                    isEnabled = isEnabled2;
                    isEmpty = true;
                }
                isEmpty = false;
            } else if (flag instanceof UnreleasedFlag) {
                boolean isEnabled3 = FeatureFlagsDebug.this.isEnabled((UnreleasedFlag) flag);
                isEnabled = isEnabled3;
                if (FeatureFlagsDebug.this.readBooleanFlagOverride(flag.getId()) != null) {
                    isEnabled = isEnabled3;
                    isEmpty = true;
                }
                isEmpty = false;
            } else if (flag instanceof ResourceBooleanFlag) {
                boolean isEnabled4 = FeatureFlagsDebug.this.isEnabled((ResourceBooleanFlag) flag);
                isEnabled = isEnabled4;
                if (FeatureFlagsDebug.this.readBooleanFlagOverride(flag.getId()) != null) {
                    isEnabled = isEnabled4;
                    isEmpty = true;
                }
                isEmpty = false;
            } else if (!(flag instanceof SysPropBooleanFlag)) {
                Log.w("SysUIFlags", "Unsupported Flag Type. Please file a bug.");
                return null;
            } else {
                SysPropBooleanFlag sysPropBooleanFlag = (SysPropBooleanFlag) flag;
                isEnabled = FeatureFlagsDebug.this.isEnabled(sysPropBooleanFlag);
                isEmpty = true ^ FeatureFlagsDebug.this.mSystemProperties.get(sysPropBooleanFlag.getName()).isEmpty();
                teamfood = false;
            }
            return isEnabled ? new ReleasedFlag(flag.getId(), flag.getName(), flag.getNamespace(), teamfood, isEmpty) : new UnreleasedFlag(flag.getId(), flag.getName(), flag.getNamespace(), teamfood, isEmpty);
        }
    };

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.flags.FeatureFlagsDebug$$ExternalSyntheticLambda1.accept(java.lang.Object, java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$4ihp3sdtXuYLXvGj2ukQ_Hzv0nM(PrintWriter printWriter, Integer num, String str) {
        lambda$dump$1(printWriter, num, str);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.flags.FeatureFlagsDebug$$ExternalSyntheticLambda0.accept(java.lang.Object, java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$HhAejqGzNly9fYjSqrE4Qos1hQs(PrintWriter printWriter, Integer num, Boolean bool) {
        lambda$dump$0(printWriter, num, bool);
    }

    public FeatureFlagsDebug(FlagManager flagManager, Context context, SecureSettings secureSettings, SystemPropertiesHelper systemPropertiesHelper, Resources resources, ServerFlagReader serverFlagReader, Map<Integer, Flag<?>> map, Restarter restarter) {
        this.mFlagManager = flagManager;
        this.mContext = context;
        this.mSecureSettings = secureSettings;
        this.mResources = resources;
        this.mSystemProperties = systemPropertiesHelper;
        this.mServerFlagReader = serverFlagReader;
        this.mAllFlags = map;
        this.mRestarter = restarter;
    }

    public static /* synthetic */ void lambda$dump$0(PrintWriter printWriter, Integer num, Boolean bool) {
        printWriter.println("  sysui_flag_" + num + ": " + bool);
    }

    public static /* synthetic */ void lambda$dump$1(PrintWriter printWriter, Integer num, String str) {
        printWriter.println("  sysui_flag_" + num + ": [length=" + str.length() + "] \"" + str + "\"");
    }

    @Override // com.android.systemui.flags.FlagListenable
    public void addListener(Flag<?> flag, FlagListenable.Listener listener) {
        this.mFlagManager.addListener(flag, listener);
    }

    public final void dispatchListenersAndMaybeRestart(int i, Consumer<Boolean> consumer) {
        this.mFlagManager.dispatchListenersAndMaybeRestart(i, consumer);
    }

    public void dump(final PrintWriter printWriter, String[] strArr) {
        printWriter.println("can override: true");
        printWriter.println("booleans: " + this.mBooleanFlagCache.size());
        this.mBooleanFlagCache.forEach(new BiConsumer() { // from class: com.android.systemui.flags.FeatureFlagsDebug$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                FeatureFlagsDebug.$r8$lambda$HhAejqGzNly9fYjSqrE4Qos1hQs(printWriter, (Integer) obj, (Boolean) obj2);
            }
        });
        printWriter.println("Strings: " + this.mStringFlagCache.size());
        this.mStringFlagCache.forEach(new BiConsumer() { // from class: com.android.systemui.flags.FeatureFlagsDebug$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                FeatureFlagsDebug.$r8$lambda$4ihp3sdtXuYLXvGj2ukQ_Hzv0nM(printWriter, (Integer) obj, (String) obj2);
            }
        });
    }

    public final void eraseFlag(int i) {
        eraseInternal(i);
        removeFromCache(i);
        dispatchListenersAndMaybeRestart(i, new FeatureFlagsDebug$$ExternalSyntheticLambda3(this));
    }

    public <T> void eraseFlag(Flag<T> flag) {
        if (!(flag instanceof SysPropFlag)) {
            eraseFlag(flag.getId());
            return;
        }
        this.mSystemProperties.erase(((SysPropFlag) flag).getName());
        dispatchListenersAndMaybeRestart(flag.getId(), new FeatureFlagsDebug$$ExternalSyntheticLambda2(this));
    }

    public final void eraseInternal(int i) {
        this.mSecureSettings.putStringForUser(this.mFlagManager.idToSettingsKey(i), "", -2);
        Log.i("SysUIFlags", "Erase id " + i);
    }

    public int getInt(IntFlag intFlag) {
        int id = intFlag.getId();
        if (!this.mIntFlagCache.containsKey(Integer.valueOf(id))) {
            this.mIntFlagCache.put(Integer.valueOf(id), (Integer) readFlagValueInternal(id, intFlag.getDefault(), IntFlagSerializer.INSTANCE));
        }
        return this.mIntFlagCache.get(Integer.valueOf(id)).intValue();
    }

    public int getInt(ResourceIntFlag resourceIntFlag) {
        int id = resourceIntFlag.getId();
        if (!this.mIntFlagCache.containsKey(Integer.valueOf(id))) {
            this.mIntFlagCache.put(Integer.valueOf(id), (Integer) readFlagValueInternal(id, Integer.valueOf(this.mResources.getInteger(resourceIntFlag.getResourceId())), IntFlagSerializer.INSTANCE));
        }
        return this.mIntFlagCache.get(Integer.valueOf(id)).intValue();
    }

    public String getString(ResourceStringFlag resourceStringFlag) {
        int id = resourceStringFlag.getId();
        if (!this.mStringFlagCache.containsKey(Integer.valueOf(id))) {
            this.mStringFlagCache.put(Integer.valueOf(id), (String) readFlagValueInternal(id, this.mResources.getString(resourceStringFlag.getResourceId()), StringFlagSerializer.INSTANCE));
        }
        return this.mStringFlagCache.get(Integer.valueOf(id));
    }

    public String getString(StringFlag stringFlag) {
        int id = stringFlag.getId();
        if (!this.mStringFlagCache.containsKey(Integer.valueOf(id))) {
            this.mStringFlagCache.put(Integer.valueOf(id), (String) readFlagValueInternal(id, stringFlag.getDefault(), StringFlagSerializer.INSTANCE));
        }
        return this.mStringFlagCache.get(Integer.valueOf(id));
    }

    public void init() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.android.systemui.action.SET_FLAG");
        intentFilter.addAction("com.android.systemui.action.GET_FLAGS");
        this.mFlagManager.setOnSettingsChangedAction(new FeatureFlagsDebug$$ExternalSyntheticLambda3(this));
        this.mFlagManager.setClearCacheAction(new Consumer() { // from class: com.android.systemui.flags.FeatureFlagsDebug$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                FeatureFlagsDebug.this.removeFromCache(((Integer) obj).intValue());
            }
        });
        this.mContext.registerReceiver(this.mReceiver, intentFilter, null, null, 2);
        this.mServerFlagReader.listenForChanges(this.mAllFlags.values(), this.mOnPropertiesChanged);
    }

    @Override // com.android.systemui.flags.FeatureFlags
    public boolean isEnabled(ReleasedFlag releasedFlag) {
        return isEnabledInternal(releasedFlag);
    }

    @Override // com.android.systemui.flags.FeatureFlags
    public boolean isEnabled(ResourceBooleanFlag resourceBooleanFlag) {
        int id = resourceBooleanFlag.getId();
        if (!this.mBooleanFlagCache.containsKey(Integer.valueOf(id))) {
            this.mBooleanFlagCache.put(Integer.valueOf(id), Boolean.valueOf(readBooleanFlagInternal(resourceBooleanFlag, this.mResources.getBoolean(resourceBooleanFlag.getResourceId()))));
        }
        return this.mBooleanFlagCache.get(Integer.valueOf(id)).booleanValue();
    }

    @Override // com.android.systemui.flags.FeatureFlags
    public boolean isEnabled(SysPropBooleanFlag sysPropBooleanFlag) {
        int id = sysPropBooleanFlag.getId();
        if (!this.mBooleanFlagCache.containsKey(Integer.valueOf(id))) {
            this.mBooleanFlagCache.put(Integer.valueOf(id), Boolean.valueOf(this.mSystemProperties.getBoolean(sysPropBooleanFlag.getName(), readBooleanFlagInternal(sysPropBooleanFlag, sysPropBooleanFlag.getDefault().booleanValue()))));
        }
        return this.mBooleanFlagCache.get(Integer.valueOf(id)).booleanValue();
    }

    @Override // com.android.systemui.flags.FeatureFlags
    public boolean isEnabled(UnreleasedFlag unreleasedFlag) {
        return isEnabledInternal(unreleasedFlag);
    }

    public final boolean isEnabledInternal(BooleanFlag booleanFlag) {
        int id = booleanFlag.getId();
        if (!this.mBooleanFlagCache.containsKey(Integer.valueOf(id))) {
            this.mBooleanFlagCache.put(Integer.valueOf(id), Boolean.valueOf(readBooleanFlagInternal(booleanFlag, booleanFlag.getDefault().booleanValue())));
        }
        return this.mBooleanFlagCache.get(Integer.valueOf(id)).booleanValue();
    }

    public final boolean readBooleanFlagInternal(Flag<Boolean> flag, boolean z) {
        Boolean readBooleanFlagOverride = readBooleanFlagOverride(flag.getId());
        if (!this.mServerFlagReader.hasOverride(flag.getNamespace(), flag.getName()) && !z && readBooleanFlagOverride == null) {
            int id = flag.getId();
            UnreleasedFlag unreleasedFlag = Flags.TEAMFOOD;
            if (id != unreleasedFlag.getId() && flag.getTeamfood()) {
                return isEnabled(unreleasedFlag);
            }
        }
        return readBooleanFlagOverride == null ? this.mServerFlagReader.readServerOverride(flag.getNamespace(), flag.getName(), z) : readBooleanFlagOverride.booleanValue();
    }

    public final Boolean readBooleanFlagOverride(int i) {
        return (Boolean) readFlagValueInternal(i, BooleanFlagSerializer.INSTANCE);
    }

    public final <T> T readFlagValueInternal(int i, FlagSerializer<T> flagSerializer) {
        try {
            return (T) this.mFlagManager.readFlagValue(i, flagSerializer);
        } catch (Exception e) {
            eraseInternal(i);
            return null;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v3, resolved type: java.lang.Object */
    /* JADX WARN: Multi-variable type inference failed */
    public final <T> T readFlagValueInternal(int i, T t, FlagSerializer<T> flagSerializer) {
        Objects.requireNonNull(t, "defaultValue");
        Object readFlagValueInternal = readFlagValueInternal(i, flagSerializer);
        if (readFlagValueInternal != 0) {
            t = readFlagValueInternal;
        }
        return t;
    }

    public final void removeFromCache(int i) {
        this.mBooleanFlagCache.remove(Integer.valueOf(i));
        this.mStringFlagCache.remove(Integer.valueOf(i));
    }

    @Override // com.android.systemui.flags.FlagListenable
    public void removeListener(FlagListenable.Listener listener) {
        this.mFlagManager.removeListener(listener);
    }

    public final void restartAndroid(boolean z) {
        if (z) {
            Log.i("SysUIFlags", "Android Restart Suppressed");
        } else {
            this.mRestarter.restartAndroid();
        }
    }

    public final void restartSystemUI(boolean z) {
        if (z) {
            Log.i("SysUIFlags", "SystemUI Restart Suppressed");
        } else {
            this.mRestarter.restartSystemUI();
        }
    }

    public void setBooleanFlagInternal(Flag<?> flag, boolean z) {
        if (flag instanceof BooleanFlag) {
            setFlagValue(flag.getId(), Boolean.valueOf(z), BooleanFlagSerializer.INSTANCE);
        } else if (flag instanceof ResourceBooleanFlag) {
            setFlagValue(flag.getId(), Boolean.valueOf(z), BooleanFlagSerializer.INSTANCE);
        } else if (!(flag instanceof SysPropBooleanFlag)) {
            throw new IllegalArgumentException("Unknown flag type");
        } else {
            this.mSystemProperties.setBoolean(((SysPropBooleanFlag) flag).getName(), z);
            dispatchListenersAndMaybeRestart(flag.getId(), new FeatureFlagsDebug$$ExternalSyntheticLambda2(this));
        }
    }

    public final <T> void setFlagValue(int i, T t, FlagSerializer<T> flagSerializer) {
        Objects.requireNonNull(t, "Cannot set a null value");
        if (Objects.equals(readFlagValueInternal(i, flagSerializer), t)) {
            Log.i("SysUIFlags", "Flag id " + i + " is already " + t);
            return;
        }
        String settingsData = flagSerializer.toSettingsData(t);
        if (settingsData == null) {
            Log.w("SysUIFlags", "Failed to set id " + i + " to " + t);
            return;
        }
        this.mSecureSettings.putStringForUser(this.mFlagManager.idToSettingsKey(i), settingsData, -2);
        Log.i("SysUIFlags", "Set id " + i + " to " + t);
        removeFromCache(i);
        this.mFlagManager.dispatchListenersAndMaybeRestart(i, new FeatureFlagsDebug$$ExternalSyntheticLambda3(this));
    }

    public void setIntFlagInternal(Flag<?> flag, int i) {
        if (flag instanceof IntFlag) {
            setFlagValue(flag.getId(), Integer.valueOf(i), IntFlagSerializer.INSTANCE);
        } else if (!(flag instanceof ResourceIntFlag)) {
            throw new IllegalArgumentException("Unknown flag type");
        } else {
            setFlagValue(flag.getId(), Integer.valueOf(i), IntFlagSerializer.INSTANCE);
        }
    }

    public void setStringFlagInternal(Flag<?> flag, String str) {
        if (flag instanceof StringFlag) {
            setFlagValue(flag.getId(), str, StringFlagSerializer.INSTANCE);
        } else if (!(flag instanceof ResourceStringFlag)) {
            throw new IllegalArgumentException("Unknown flag type");
        } else {
            setFlagValue(flag.getId(), str, StringFlagSerializer.INSTANCE);
        }
    }
}