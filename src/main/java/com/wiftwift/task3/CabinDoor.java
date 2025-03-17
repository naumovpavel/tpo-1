package com.wiftwift.task3;

import java.util.ArrayList;
import java.util.List;

public class CabinDoor extends Door {
    private List<FurryHand> furryHands;
    private List<TinyVoice> tinyVoices;
    private boolean handsInsideGaps;
    
    public CabinDoor(boolean poorlyFitted) {
        super(poorlyFitted);
        this.furryHands = new ArrayList<>();
        this.tinyVoices = new ArrayList<>();
        this.handsInsideGaps = false;
    }
    
    public void addFurryHand(FurryHand hand) {
        furryHands.add(hand);
        if (hasGaps()) {
            handsInsideGaps = true;
            hand.reachThroughGap();
        }
    }
    
    public void addTinyVoice(TinyVoice voice) {
        tinyVoices.add(voice);
        if (hasGaps()) {
            voice.startScreeching();
        }
    }
    
    public List<FurryHand> getFurryHands() {
        return new ArrayList<>(furryHands);
    }
    
    public List<TinyVoice> getTinyVoices() {
        return new ArrayList<>(tinyVoices);
    }
    
    public boolean hasFurryHandsInGaps() {
        return handsInsideGaps && hasGaps();
    }
    
    public boolean hasCrazyScreechingVoices() {
        return !tinyVoices.isEmpty() && tinyVoices.stream().anyMatch(TinyVoice::isScreeching);
    }
    
    public void reactToArthurPushing() {
        if (hasGaps()) {
            for (FurryHand hand : furryHands) {
                hand.reachThroughGap();
                hand.grabInsideDoor();
            }
            
            for (TinyVoice voice : tinyVoices) {
                voice.startScreeching();
                voice.increaseVolume();
            }
        }
    }
}
