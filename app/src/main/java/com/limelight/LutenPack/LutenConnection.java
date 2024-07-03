package com.limelight.LutenPack;

import android.util.Log;

public class LutenConnection {

    public static final String TAG = "LutenConnection";
    public void sendLutenPack(final byte[] packet)
    {
        try {
            Log.e(TAG,"초기설정이 되어있지 않습니다! sendLutenPack");
            throw new Exception();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
