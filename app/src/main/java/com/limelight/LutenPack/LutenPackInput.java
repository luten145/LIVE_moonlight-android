package com.limelight.LutenPack;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.limelight.nvstream.NvConnection;
import com.lutenstudio.lutenpack_moonlight.IRemoteService;
import com.lutenstudio.lutenpack_moonlight.IRemoteServiceCallback;

public class LutenPackInput {

    private final static String TAG = "LutenPackInput";

    private final static String LUTENPACK_PACKAGE_NAME = "com.lutenstudio.lutenpack_moonlight.release";

    private final Context context;
    private final NvConnection conn;

    public LutenPackInput(Context context, NvConnection conn){
        this.context = context;
        this.conn = conn;
        process();
    }

    private void process(){
        if(checkLutenPack()) Log.d(TAG,"Find Luten Pack");
        else {
            Log.d(TAG,"Cannot find LutenPack");
            return;
        }
        if(!connectLutenPack()) Log.d(TAG," Failed to connect LutenPack");
    }

    private boolean checkLutenPack(){
        PackageManager packageManager = context.getPackageManager();
        try {
            Log.d(TAG,"Check LutenPack...");
            packageManager.getPackageInfo(LUTENPACK_PACKAGE_NAME,PackageManager.GET_ACTIVITIES);
            return true;
        }catch (PackageManager.NameNotFoundException e){
            return false;
        }
    }

    private final IRemoteServiceCallback.Stub callback = new IRemoteServiceCallback.Stub() {
        @Override
        public void sendPacket(byte[] packet) {
            conn.sendLutenPack(packet);
        }
    };

    private IRemoteService iRemoteService = null;

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG,"LutenPack connected!");
            iRemoteService = IRemoteService.Stub.asInterface(service);
            try {
                iRemoteService.addCallback(callback);
                Log.d(TAG,"added callback");
            } catch (RemoteException e) {
                Log.d(TAG,"Failed add callback");
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG,"Disconnected LutenPack");
            iRemoteService = null;
        }
    };

    private boolean connectLutenPack(){
        Intent intent = new Intent("com.lutenstudio.lutenpack_moonlight.InputServiceServer");
        intent.setPackage(LUTENPACK_PACKAGE_NAME);
        try {
            Log.d(TAG,"Try Connect LutenPack");
            context.bindService(intent,connection, Context.BIND_AUTO_CREATE);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public void onActivityEvent(int state){
        if(iRemoteService != null) {
            try {
                iRemoteService.onActivityState(state);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

}
