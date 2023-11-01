package android.frameworks.stats;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: mainsysui33.jar:android/frameworks/stats/IStats.class */
public interface IStats extends IInterface {
    public static final String DESCRIPTOR = "android$frameworks$stats$IStats".replace('$', '.');
    public static final String HASH = "ee0b303cae7889e83a6a198c9b33781ad74ae633";
    public static final int VERSION = 1;

    /* loaded from: mainsysui33.jar:android/frameworks/stats/IStats$Default.class */
    public static class Default implements IStats {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.frameworks.stats.IStats
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.frameworks.stats.IStats
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.frameworks.stats.IStats
        public void reportVendorAtom(VendorAtom vendorAtom) throws RemoteException {
        }
    }

    /* loaded from: mainsysui33.jar:android/frameworks/stats/IStats$Stub.class */
    public static abstract class Stub extends Binder implements IStats {
        public static final int TRANSACTION_getInterfaceHash = 16777214;
        public static final int TRANSACTION_getInterfaceVersion = 16777215;
        public static final int TRANSACTION_reportVendorAtom = 1;

        /* loaded from: mainsysui33.jar:android/frameworks/stats/IStats$Stub$Proxy.class */
        public static class Proxy implements IStats {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IStats.DESCRIPTOR;
            }

            @Override // android.frameworks.stats.IStats
            public String getInterfaceHash() throws RemoteException {
                String str;
                synchronized (this) {
                    if ("-1".equals(this.mCachedHash)) {
                        Parcel obtain = Parcel.obtain();
                        Parcel obtain2 = Parcel.obtain();
                        obtain.writeInterfaceToken(IStats.DESCRIPTOR);
                        this.mRemote.transact(Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedHash = obtain2.readString();
                        obtain2.recycle();
                        obtain.recycle();
                    }
                    str = this.mCachedHash;
                }
                return str;
            }

            @Override // android.frameworks.stats.IStats
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel obtain = Parcel.obtain();
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(IStats.DESCRIPTOR);
                        this.mRemote.transact(Stub.TRANSACTION_getInterfaceVersion, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.frameworks.stats.IStats
            public void reportVendorAtom(VendorAtom vendorAtom) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IStats.DESCRIPTOR);
                    obtain.writeTypedObject(vendorAtom, 0);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                        return;
                    }
                    throw new RemoteException("Method reportVendorAtom is unimplemented.");
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, IStats.DESCRIPTOR);
        }

        public static IStats asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IStats.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IStats)) ? new Proxy(iBinder) : (IStats) queryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            String str = IStats.DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            switch (i) {
                case TRANSACTION_getInterfaceHash /* 16777214 */:
                    parcel2.writeNoException();
                    parcel2.writeString(getInterfaceHash());
                    return true;
                case TRANSACTION_getInterfaceVersion /* 16777215 */:
                    parcel2.writeNoException();
                    parcel2.writeInt(getInterfaceVersion());
                    return true;
                case 1598968902:
                    parcel2.writeString(str);
                    return true;
                default:
                    if (i != 1) {
                        return super.onTransact(i, parcel, parcel2, i2);
                    }
                    parcel.enforceNoDataAvail();
                    reportVendorAtom((VendorAtom) parcel.readTypedObject(VendorAtom.CREATOR));
                    return true;
            }
        }
    }

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void reportVendorAtom(VendorAtom vendorAtom) throws RemoteException;
}