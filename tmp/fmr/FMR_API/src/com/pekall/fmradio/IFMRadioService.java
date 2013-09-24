/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: packages/apps/FMRadio/src/com/pekall/fmradio/IFMRadioService.aidl
 */
package com.pekall.fmradio;
public interface IFMRadioService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.pekall.fmradio.IFMRadioService
{
private static final java.lang.String DESCRIPTOR = "com.pekall.fmradio.IFMRadioService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.pekall.fmradio.IFMRadioService interface,
 * generating a proxy if needed.
 */
public static com.pekall.fmradio.IFMRadioService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.pekall.fmradio.IFMRadioService))) {
return ((com.pekall.fmradio.IFMRadioService)iin);
}
return new com.pekall.fmradio.IFMRadioService.Stub.Proxy(obj);
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
case TRANSACTION_getFMServiceStatus:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getFMServiceStatus();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_openFMRadio:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.openFMRadio();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_closeFMRadio:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.closeFMRadio();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_openFMRadioTx:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.openFMRadioTx();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getFMFreq:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getFMFreq();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setFMFreq:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setFMFreq(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setRdsCallback:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setRdsCallback(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_seekFM:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.seekFM(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_stopSeekFM:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.stopSeekFM();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setMute:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setMute(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isMuteFM:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isMuteFM();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getFMVolume:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getFMVolume();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setFMVolume:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setFMVolume(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setFMAudioRouting:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setFMAudioRouting(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getFMAudioRouting:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getFMAudioRouting();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getFMRssiLevel:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getFMRssiLevel();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setFMRssiThreshold:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setFMRssiThreshold(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_enableRds:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.enableRds();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_disableRds:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.disableRds();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_registerCallback:
{
data.enforceInterface(DESCRIPTOR);
com.pekall.fmradio.IRemoteServiceCallback _arg0;
_arg0 = com.pekall.fmradio.IRemoteServiceCallback.Stub.asInterface(data.readStrongBinder());
this.registerCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_unregisterCallback:
{
data.enforceInterface(DESCRIPTOR);
com.pekall.fmradio.IRemoteServiceCallback _arg0;
_arg0 = com.pekall.fmradio.IRemoteServiceCallback.Stub.asInterface(data.readStrongBinder());
this.unregisterCallback(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.pekall.fmradio.IFMRadioService
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
@Override public int getFMServiceStatus() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getFMServiceStatus, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean openFMRadio() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_openFMRadio, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean closeFMRadio() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_closeFMRadio, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean openFMRadioTx() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_openFMRadioTx, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getFMFreq() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getFMFreq, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean setFMFreq(int freq) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(freq);
mRemote.transact(Stub.TRANSACTION_setFMFreq, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean setRdsCallback(int start) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(start);
mRemote.transact(Stub.TRANSACTION_setRdsCallback, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean seekFM(int direction) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(direction);
mRemote.transact(Stub.TRANSACTION_seekFM, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean stopSeekFM() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_stopSeekFM, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean setMute(int mode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mode);
mRemote.transact(Stub.TRANSACTION_setMute, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean isMuteFM() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isMuteFM, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getFMVolume() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getFMVolume, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean setFMVolume(int volume) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(volume);
mRemote.transact(Stub.TRANSACTION_setFMVolume, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean setFMAudioRouting(int routing) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(routing);
mRemote.transact(Stub.TRANSACTION_setFMAudioRouting, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getFMAudioRouting() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getFMAudioRouting, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getFMRssiLevel() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getFMRssiLevel, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean setFMRssiThreshold(int value) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(value);
mRemote.transact(Stub.TRANSACTION_setFMRssiThreshold, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean enableRds() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_enableRds, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean disableRds() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_disableRds, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void registerCallback(com.pekall.fmradio.IRemoteServiceCallback cb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((cb!=null))?(cb.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void unregisterCallback(com.pekall.fmradio.IRemoteServiceCallback cb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((cb!=null))?(cb.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_unregisterCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_getFMServiceStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_openFMRadio = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_closeFMRadio = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_openFMRadioTx = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_getFMFreq = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_setFMFreq = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_setRdsCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_seekFM = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_stopSeekFM = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_setMute = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_isMuteFM = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_getFMVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_setFMVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_setFMAudioRouting = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_getFMAudioRouting = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_getFMRssiLevel = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_setFMRssiThreshold = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_enableRds = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_disableRds = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_registerCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
static final int TRANSACTION_unregisterCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
}
public int getFMServiceStatus() throws android.os.RemoteException;
public boolean openFMRadio() throws android.os.RemoteException;
public boolean closeFMRadio() throws android.os.RemoteException;
public boolean openFMRadioTx() throws android.os.RemoteException;
public int getFMFreq() throws android.os.RemoteException;
public boolean setFMFreq(int freq) throws android.os.RemoteException;
public boolean setRdsCallback(int start) throws android.os.RemoteException;
public boolean seekFM(int direction) throws android.os.RemoteException;
public boolean stopSeekFM() throws android.os.RemoteException;
public boolean setMute(int mode) throws android.os.RemoteException;
public boolean isMuteFM() throws android.os.RemoteException;
public int getFMVolume() throws android.os.RemoteException;
public boolean setFMVolume(int volume) throws android.os.RemoteException;
public boolean setFMAudioRouting(int routing) throws android.os.RemoteException;
public int getFMAudioRouting() throws android.os.RemoteException;
public int getFMRssiLevel() throws android.os.RemoteException;
public boolean setFMRssiThreshold(int value) throws android.os.RemoteException;
public boolean enableRds() throws android.os.RemoteException;
public boolean disableRds() throws android.os.RemoteException;
public void registerCallback(com.pekall.fmradio.IRemoteServiceCallback cb) throws android.os.RemoteException;
public void unregisterCallback(com.pekall.fmradio.IRemoteServiceCallback cb) throws android.os.RemoteException;
}
