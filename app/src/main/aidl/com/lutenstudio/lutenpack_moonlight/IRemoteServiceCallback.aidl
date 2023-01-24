// IRemoteServiceCallback.aidl
package com.lutenstudio.lutenpack_moonlight;

// Declare any non-default types here with import statements

interface IRemoteServiceCallback {
    void sendPacket(inout byte[] packet);
}