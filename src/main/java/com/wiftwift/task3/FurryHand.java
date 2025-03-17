package com.wiftwift.task3;

public class FurryHand {
    private boolean inkStained;
    private boolean reachingThroughGap;
    private boolean grabbingInsideDoor;
    
    public FurryHand(boolean inkStained) {
        this.inkStained = inkStained;
        this.reachingThroughGap = false;
        this.grabbingInsideDoor = false;
    }
    
    public boolean isInkStained() {
        return inkStained;
    }
    
    public void stainWithInk() {
        this.inkStained = true;
    }
    
    public void reachThroughGap() {
        this.reachingThroughGap = true;
    }
    
    public void grabInsideDoor() {
        if (reachingThroughGap) {
            this.grabbingInsideDoor = true;
        }
    }
    
    public boolean isReachingThroughGap() {
        return reachingThroughGap;
    }
    
    public boolean isGrabbingInsideDoor() {
        return grabbingInsideDoor;
    }
    
    public void reactToArthurPushing() {
        reachThroughGap();
        grabInsideDoor();
    }
}
