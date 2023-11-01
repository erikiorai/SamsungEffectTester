package com.android.systemui.screenshot;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ICrossProfileService.class */
public interface ICrossProfileService extends IInterface {

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ICrossProfileService$Stub.class */
    public static abstract class Stub extends Binder implements ICrossProfileService {

        /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ICrossProfileService$Stub$Proxy.class */
        public static class Proxy implements ICrossProfileService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.android.systemui.screenshot.ICrossProfileService
            public void launchIntent(Intent intent, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.systemui.screenshot.ICrossProfileService");
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.android.systemui.screenshot.ICrossProfileService");
        }

        public static ICrossProfileService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.android.systemui.screenshot.ICrossProfileService");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof ICrossProfileService)) ? new Proxy(iBinder) : (ICrossProfileService) queryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.systemui.screenshot.ICrossProfileService");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.android.systemui.screenshot.ICrossProfileService");
                return true;
            } else if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel.enforceNoDataAvail();
                launchIntent((Intent) parcel.readTypedObject(Intent.CREATOR), (Bundle) parcel.readTypedObject(Bundle.CREATOR));
                parcel2.writeNoException();
                return true;
            }
        }
    }

    void launchIntent(Intent intent, Bundle bundle) throws RemoteException;
}