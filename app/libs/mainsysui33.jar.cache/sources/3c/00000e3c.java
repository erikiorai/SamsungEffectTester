package com.android.settingslib.development;

import android.os.AsyncTask;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

/* loaded from: mainsysui33.jar:com/android/settingslib/development/SystemPropPoker.class */
public class SystemPropPoker {
    public static final SystemPropPoker sInstance = new SystemPropPoker();
    public boolean mBlockPokes = false;

    /* loaded from: mainsysui33.jar:com/android/settingslib/development/SystemPropPoker$PokerTask.class */
    public static class PokerTask extends AsyncTask<Void, Void, Void> {
        public IBinder checkService(String str) {
            return ServiceManager.checkService(str);
        }

        /* JADX DEBUG: Method merged with bridge method */
        @Override // android.os.AsyncTask
        public Void doInBackground(Void... voidArr) {
            String[] listServices = listServices();
            if (listServices == null) {
                Log.e("SystemPropPoker", "There are no services, how odd");
                return null;
            }
            for (String str : listServices) {
                IBinder checkService = checkService(str);
                if (checkService != null) {
                    Parcel obtain = Parcel.obtain();
                    try {
                        checkService.transact(1599295570, obtain, null, 0);
                    } catch (RemoteException e) {
                    } catch (Exception e2) {
                        Log.i("SystemPropPoker", "Someone wrote a bad service '" + str + "' that doesn't like to be poked", e2);
                    }
                    obtain.recycle();
                }
            }
            return null;
        }

        public String[] listServices() {
            return ServiceManager.listServices();
        }
    }

    public static SystemPropPoker getInstance() {
        return sInstance;
    }

    public PokerTask createPokerTask() {
        return new PokerTask();
    }

    public void poke() {
        if (this.mBlockPokes) {
            return;
        }
        createPokerTask().execute(new Void[0]);
    }
}