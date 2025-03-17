package com.wiftwift.task3;

public class Arthur {
    private boolean shoulderPushingDoor;
    private boolean tryingToLockDoor;
    private boolean frightened;
    
    public Arthur() {
        this.shoulderPushingDoor = false;
        this.tryingToLockDoor = false;
        this.frightened = false;
    }
    
    public void pushDoorWithShoulder() {
        this.shoulderPushingDoor = true;
    }
    
    public void tryToLockDoor() {
        this.tryingToLockDoor = true;
    }
    
    public void getScared() {
        this.frightened = true;
    }
    
    public boolean isShoulderPushingDoor() {
        return shoulderPushingDoor;
    }
    
    public boolean isTryingToLockDoor() {
        return tryingToLockDoor;
    }
    
    public boolean isFrightened() {
        return frightened;
    }
    
    public void reactToVoices(boolean areScreeching) {
        if (areScreeching) {
            getScared();
        }
    }
    
    public void pushHarder() {
        this.shoulderPushingDoor = true;
    }
}
