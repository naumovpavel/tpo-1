package com.wiftwift.task3;

public class DoorInteractionService {
    
    public void arthurPushAgainstDoor(Arthur arthur, CabinDoor door) {
        arthur.pushDoorWithShoulder();
        
        door.reactToArthurPushing();
        
        arthur.tryToLockDoor();
        boolean doorLocked = door.tryToLock();
        
        if (door.isPoorlyFitted() && !doorLocked) {
            for (FurryHand hand : door.getFurryHands()) {
                hand.reactToArthurPushing();
            }
            
            boolean anyScreechingVoices = door.hasCrazyScreechingVoices();
            if (anyScreechingVoices) {
                arthur.reactToVoices(true);
            }
            
            for (TinyVoice voice : door.getTinyVoices()) {
                voice.reactToArthurFrightened(arthur.isFrightened());
            }
            
            if (door.hasFurryHandsInGaps()) {
                arthur.pushHarder();
            }
        }
    }
    
    public boolean areAllHandsActivelyReaching(CabinDoor door) {
        return door.getFurryHands().stream()
                .allMatch(hand -> hand.isReachingThroughGap() && hand.isGrabbingInsideDoor());
    }
    
    public boolean areAllVoicesLoud(CabinDoor door) {
        return door.getTinyVoices().stream()
                .filter(TinyVoice::isScreeching)
                .allMatch(voice -> voice.getVolume() > 7);
    }
}
