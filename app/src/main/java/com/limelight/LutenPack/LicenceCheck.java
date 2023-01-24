package com.limelight.LutenPack;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.limelight.PcView;
import com.limelight.R;
import com.lutenstudio.lutenpack_manager.ManagerService;
import com.lutenstudio.lutenpack_manager.ManagerServiceCallback;


public class LicenceCheck extends Activity {

    private static final String TAG = "LicenceCheck";

    private final static String MANAGER_PACKAGE_NAME = "com.lutenstudio.lutenpack_manager.release";

    private final String[][] sb = new String[5][10];

    private void history(String action,int index){
        StringBuilder ss= new StringBuilder();

        System.arraycopy(sb[index], 0, sb[index], 1, sb[index].length-1);
        sb[index][0]=(action+"\n");

        for(int i = 0; i<10; i++) ss.append(sb[index][i]);

        Log.d(TAG,ss.toString());
        runOnUiThread(() -> log.setText(ss.toString()));
    }

    private void printLog(String action){
        history(action,0);
    }

    private TextView log;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_check);

        log = findViewById(R.id.log);

        mContext = this;
        process();
    }

    private void process(){
        if(checkLutenPack()) printLog("Find LutenPack Manager");
        else {
            printLog("Cannot find LutenPack Manager");
            printLog("Please Install LutenPack Manager");
            return;
        }

        if(!connectLutenPack()) printLog("Connot Connect LutenPack Manager");
    }

    private boolean checkLutenPack(){
        PackageManager packageManager = this.getPackageManager();
        try {
            printLog("Check LutenPack Manager");
            packageManager.getPackageInfo(MANAGER_PACKAGE_NAME,PackageManager.GET_ACTIVITIES);
            return true;
        }catch (PackageManager.NameNotFoundException e){
            return false;
        }
    }

    private final ManagerServiceCallback.Stub licenseCallback = new ManagerServiceCallback.Stub() {
        @Override
        public void resultLicence(int Result) {
            runOnUiThread(() -> {
                printLog("Licence : "+ Result);

                if(Result == 500) {
                    Toast.makeText(mContext,"라이센스가 확인되었습니다.",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, PcView.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    };

    private final ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG,"Connected LutenPack Manager.");
            ManagerService managerService = ManagerService.Stub.asInterface(service);
            try {
                managerService.checkLicence(licenseCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            printLog("Disconnected LutenPack Manager");
        }
    };


    private boolean connectLutenPack(){
        Intent intent = new Intent("com.lutenstudio.lutenpack_manager.Services.ActivationService");
        intent.setPackage(MANAGER_PACKAGE_NAME);

        try {
            printLog("Try to connect LutenPack Manager");
            bindService(intent,conn, Context.BIND_AUTO_CREATE);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}