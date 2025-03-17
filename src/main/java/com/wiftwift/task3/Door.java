package com.wiftwift.task3;

public class Door {
    private boolean poorlyFitted;
    private boolean locked;
    private boolean hasGaps;
    
    public Door(boolean poorlyFitted) {
        this.poorlyFitted = poorlyFitted;
        this.locked = false;
        this.hasGaps = poorlyFitted;
    }
    
    public boolean isPoorlyFitted() {
        return poorlyFitted;
    }
    
    public boolean isLocked() {
        return locked;
    }
    
    public boolean hasGaps() {
        return hasGaps;
    }
    
    public boolean tryToLock() {
        if (!poorlyFitted) {
            locked = true;
            return true;
        }
        return false;
    }
}
