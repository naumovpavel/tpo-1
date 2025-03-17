package com.wiftwift.task3;

public class TinyVoice {
    private boolean screeching;
    private int volume;
    private static final int MAX_VOLUME = 10;
    
    public TinyVoice(boolean screeching) {
        this.screeching = screeching;
        this.volume = screeching ? 5 : 0;
    }
    
    public boolean isScreeching() {
        return screeching;
    }
    
    public int getVolume() {
        return volume;
    }
    
    public void startScreeching() {
        this.screeching = true;
        if (this.volume == 0) {
            this.volume = 5;
        }
    }
    
    public void increaseVolume() {
        if (screeching && volume < MAX_VOLUME) {
            volume++;
        }
    }
    
    public void reactToArthurFrightened(boolean arthurFrightened) {
        if (arthurFrightened) {
            startScreeching();
            increaseVolume();
            increaseVolume();
        }
    }
}
