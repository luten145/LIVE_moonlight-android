package com.limelight.LutenPack;

import com.limelight.binding.input.KeyboardTranslator;
import com.limelight.nvstream.NvConnection;
import com.lutenstudio.moonlightlutenpack.LutenPack.ConnectionHandle;

public class LutenPackHandle extends ConnectionHandle {

    private final KeyboardTranslator keyboardTranslator = new KeyboardTranslator();
    private final NvConnection conn;
    public LutenPackHandle(NvConnection connection){
        this.conn = connection;
    }

    @Override
    public short translate(int keycode, int deviceId) {
        return keyboardTranslator.translate(keycode,deviceId);
    }

    @Override
    public void sendLutenPack(byte[] packet) {
        super.sendLutenPack(packet);
        conn.sendLutenPack(packet);
    }


    @Override
    public void sendControllerInput(short controllerNumber, short activeGamepadMask, int buttonFlags, byte leftTrigger, byte rightTrigger, short leftStickX, short leftStickY, short rightStickX, short rightStickY) {
        super.sendControllerInput(controllerNumber, activeGamepadMask, buttonFlags, leftTrigger, rightTrigger, leftStickX, leftStickY, rightStickX, rightStickY);
        conn.sendControllerInput(controllerNumber,activeGamepadMask,buttonFlags,leftTrigger, rightTrigger, leftStickX, leftStickY, rightStickX, rightStickY);
    }

    @Override
    public void sendMouseMove(short deltaX, short deltaY) {
        super.sendMouseMove(deltaX, deltaY);
        conn.sendMouseMove(deltaX, deltaY);
    }


}
