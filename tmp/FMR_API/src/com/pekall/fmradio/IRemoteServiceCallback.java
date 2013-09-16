/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: packages/apps/FMRadio/src/com/pekall/fmradio/IRemoteServiceCallback.aidl
 */
package com.pekall.fmradio;
public interface IRemoteServiceCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.pekall.fmradio.IRemoteServiceCallback
{
private static final java.lang.String DESCRIPTOR = "com.pekall.fmradio.IRemoteServiceCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.pekall.fmradio.IRemoteServiceCallback interface,
 * generating a proxy if needed.
 */
public static com.pekall.fmradio.IRemoteServiceCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.pekall.fmradio.IRemoteServiceCallback))) {
return ((com.pekall.fmradio.IRemoteServiceCallback)iin);
}
return new com.pekall.fmradio.IRemoteServiceCallback.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_openFMRadioCallBack:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.openFMRadioCallBack(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_openFMRadioTxCallBack:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.openFMRadioTxCallBack(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setFrequencyCallback:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.setFrequencyCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_seekStationCallback:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
int _arg1;
_arg1 = data.readInt();
this.seekStationCallback(_arg0, _arg1);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.pekall.fmradio.IRemoteServiceCallback
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void openFMRadioCallBack(boolean result) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((result)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_openFMRadioCallBack, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void openFMRadioTxCallBack(boolean result) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((result)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_openFMRadioTxCallBack, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setFrequencyCallback(boolean result) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((result)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setFrequencyCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void seekStationCallback(boolean result, int frequency) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((result)?(1):(0)));
_data.writeInt(frequency);
mRemote.transact(Stub.TRANSACTION_seekStationCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_openFMRadioCallBack = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_openFMRadioTxCallBack = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_setFrequencyCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_seekStationCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
}
public void openFMRadioCallBack(boolean result) throws android.os.RemoteException;
public void openFMRadioTxCallBack(boolean result) throws android.os.RemoteException;
public void setFrequencyCallback(boolean result) throws android.os.RemoteException;
public void seekStationCallback(boolean result, int frequency) throws android.os.RemoteException;
}
