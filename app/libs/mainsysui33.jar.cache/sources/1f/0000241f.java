package com.android.systemui.screenshot;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/IOnDoneCallback.class */
public interface IOnDoneCallback extends IInterface {

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/IOnDoneCallback$Stub.class */
    public static abstract class Stub extends Binder implements IOnDoneCallback {

        /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/IOnDoneCallback$Stub$Proxy.class */
        public static class Proxy implements IOnDoneCallback {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.android.systemui.screenshot.IOnDoneCallback
            public void onDone(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.systemui.screenshot.IOnDoneCallback");
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.android.systemui.screenshot.IOnDoneCallback");
        }

        public static IOnDoneCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.android.systemui.screenshot.IOnDoneCallback");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IOnDoneCallback)) ? new Proxy(iBinder) : (IOnDoneCallback) queryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.systemui.screenshot.IOnDoneCallback");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.android.systemui.screenshot.IOnDoneCallback");
                return true;
            } else if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onDone(readBoolean);
                parcel2.writeNoException();
                return true;
            }
        }
    }

    void onDone(boolean z) throws RemoteException;
}