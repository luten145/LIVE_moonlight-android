package com.limelight.LutenPack;

import com.limelight.nvstream.NvConnectionListener;
import com.lutenstudio.moonlightlutenpack.LutenPack.ConnectionListener;

public class LutenConnhandle extends ConnectionListener {

    private final NvConnectionListener origListener;

    public LutenConnhandle(NvConnectionListener listener){
        this.origListener = listener;
    }

    public NvConnectionListener listener = new NvConnectionListener() {
        @Override
        public void stageStarting(String stage) {
            origListener.stageStarting(stage); //이벤트가 그대로 게임에게 감
            LutenConnhandle.this.stageStarting(stage);
        }

        @Override
        public void stageComplete(String stage) {
            origListener.stageComplete(stage);
            LutenConnhandle.this.stageComplete(stage);
        }

        @Override
        public void stageFailed(String stage, int portFlags, int errorCode) {
            origListener.stageFailed(stage,portFlags,errorCode);
            LutenConnhandle.this.stageFailed(stage,portFlags,errorCode);
        }

        @Override
        public void connectionStarted() {
            origListener.connectionStarted();
            LutenConnhandle.this.connectionStarted();
        }

        @Override
        public void connectionTerminated(int errorCode) {
            origListener.connectionTerminated(errorCode);
            LutenConnhandle.this.connectionTerminated(errorCode);
        }

        @Override
        public void connectionStatusUpdate(int connectionStatus) {
            origListener.connectionStatusUpdate(connectionStatus);
            LutenConnhandle.this.connectionStatusUpdate(connectionStatus);
        }

        @Override
        public void displayMessage(String message) {
            origListener.displayMessage(message);
            LutenConnhandle.this.displayMessage(message);
        }

        @Override
        public void displayTransientMessage(String message) {
            origListener.displayTransientMessage(message);
            LutenConnhandle.this.displayTransientMessage(message);

        }

        @Override
        public void rumble(short controllerNumber, short lowFreqMotor, short highFreqMotor) {
            origListener.rumble(controllerNumber,lowFreqMotor,highFreqMotor);
            LutenConnhandle.this.rumble(controllerNumber,lowFreqMotor,highFreqMotor);;
        }

        @Override
        public void rumbleTriggers(short controllerNumber, short leftTrigger, short rightTrigger) {
            origListener.rumbleTriggers(controllerNumber,leftTrigger,rightTrigger);
            LutenConnhandle.this.rumbleTriggers(controllerNumber,leftTrigger,rightTrigger);
        }

        @Override
        public void setHdrMode(boolean enabled, byte[] hdrMetadata) {
            origListener.setHdrMode(enabled,hdrMetadata);
            LutenConnhandle.this.setHdrMode(enabled,hdrMetadata);
        }

        @Override
        public void setMotionEventState(short controllerNumber, byte motionType, short reportRateHz) {
            origListener.setMotionEventState(controllerNumber,motionType,reportRateHz);
            LutenConnhandle.this.setMotionEventState(controllerNumber,motionType,reportRateHz);
        }

        @Override
        public void setControllerLED(short controllerNumber, byte r, byte g, byte b) {
            origListener.setControllerLED(controllerNumber,r,g,b);
            LutenConnhandle.this.setControllerLED(controllerNumber,r,g,b);
        }
    };

    public NvConnectionListener getListener(){ // Moonbridge로 전달할 Listener
        isUseGetListener = true;
        return listener;
    }
}
