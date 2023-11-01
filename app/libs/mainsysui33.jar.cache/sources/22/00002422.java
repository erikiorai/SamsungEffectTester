package com.android.systemui.screenshot;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.systemui.screenshot.IOnDoneCallback;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/IScreenshotProxy.class */
public interface IScreenshotProxy extends IInterface {

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/IScreenshotProxy$Stub.class */
    public static abstract class Stub extends Binder implements IScreenshotProxy {

        /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/IScreenshotProxy$Stub$Proxy.class */
        public static class Proxy implements IScreenshotProxy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.android.systemui.screenshot.IScreenshotProxy
            public void dismissKeyguard(IOnDoneCallback iOnDoneCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.systemui.screenshot.IScreenshotProxy");
                    obtain.writeStrongInterface(iOnDoneCallback);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.screenshot.IScreenshotProxy
            public boolean isNotificationShadeExpanded() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.systemui.screenshot.IScreenshotProxy");
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.android.systemui.screenshot.IScreenshotProxy");
        }

        public static IScreenshotProxy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.android.systemui.screenshot.IScreenshotProxy");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IScreenshotProxy)) ? new Proxy(iBinder) : (IScreenshotProxy) queryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.systemui.screenshot.IScreenshotProxy");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.android.systemui.screenshot.IScreenshotProxy");
                return true;
            } else if (i == 1) {
                boolean isNotificationShadeExpanded = isNotificationShadeExpanded();
                parcel2.writeNoException();
                parcel2.writeBoolean(isNotificationShadeExpanded);
                return true;
            } else if (i != 2) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                IOnDoneCallback asInterface = IOnDoneCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                dismissKeyguard(asInterface);
                parcel2.writeNoException();
                return true;
            }
        }
    }

    void dismissKeyguard(IOnDoneCallback iOnDoneCallback) throws RemoteException;

    boolean isNotificationShadeExpanded() throws RemoteException;
}