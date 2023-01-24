// IRemoteService.aidl
package com.lutenstudio.lutenpack_moonlight;
import com.lutenstudio.lutenpack_moonlight.IRemoteServiceCallback;

// Declare any non-default types here with import statements


interface IRemoteService {
    boolean addCallback(IRemoteServiceCallback callback);
    boolean removeCallback(IRemoteServiceCallback callbac);
    void onActivityState(int state);
}
