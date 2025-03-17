package com.wiftwift.task3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DomainModelTest {
    private Arthur arthur;
    private CabinDoor cabinDoor;
    private DoorInteractionService service;
    
    @BeforeEach
    void setUp() {
        arthur = new Arthur();
        cabinDoor = new CabinDoor(true);
        service = new DoorInteractionService();
    }
    
    @Test
    void testArthurPushesAgainstDoor() {
        service.arthurPushAgainstDoor(arthur, cabinDoor);
        
        assertTrue(arthur.isShoulderPushingDoor(), "Артур должен упираться плечом в дверь");
        assertTrue(arthur.isTryingToLockDoor(), "Артур должен пытаться запереть дверь");
        assertFalse(cabinDoor.isLocked(), "Дверь не должна запираться, т.к. она плохо подогнана");
    }
    
    @Test
    void testFurryHandsInGaps() {
        FurryHand hand1 = new FurryHand(true);
        FurryHand hand2 = new FurryHand(true);
        cabinDoor.addFurryHand(hand1);
        cabinDoor.addFurryHand(hand2);
        
        assertTrue(cabinDoor.hasFurryHandsInGaps(), "В щелях должны быть мохнатые ручки");
        assertTrue(hand1.isInkStained(), "Ручки должны быть испачканы чернилами");
        assertTrue(hand1.isReachingThroughGap(), "Ручка должна просовываться в щель");
        
        service.arthurPushAgainstDoor(arthur, cabinDoor);
        
        assertTrue(hand1.isGrabbingInsideDoor(), "Ручка должна хвататься внутри двери");
        assertTrue(hand2.isGrabbingInsideDoor(), "Ручка должна хвататься внутри двери");
        assertTrue(service.areAllHandsActivelyReaching(cabinDoor), "Все руки должны активно просовываться в щели");
    }
    
    @Test
    void testTinyVoicesScreeching() {
        TinyVoice voice1 = new TinyVoice(false);
        TinyVoice voice2 = new TinyVoice(false);
        
        assertFalse(voice1.isScreeching(), "Голос не должен верещать изначально");
        assertEquals(0, voice1.getVolume(), "Голос должен быть тихим изначально");
        
        cabinDoor.addTinyVoice(voice1);
        cabinDoor.addTinyVoice(voice2);
        
        assertTrue(cabinDoor.hasCrazyScreechingVoices(), "После добавления в дверь с щелями голоса должны верещать");
        assertTrue(voice1.isScreeching(), "Голос должен начать верещать после добавления в дверь с щелями");
        
        service.arthurPushAgainstDoor(arthur, cabinDoor);
        
        assertTrue(cabinDoor.hasCrazyScreechingVoices(), "После взаимодействия голоса должны верещать");
        assertTrue(voice1.isScreeching(), "Голос должен верещать после взаимодействия");
        assertTrue(voice2.isScreeching(), "Голос должен верещать после взаимодействия");
        assertTrue(voice1.getVolume() > 0, "Громкость голоса должна увеличиться");
        assertTrue(voice2.getVolume() > 0, "Громкость голоса должна увеличиться");
    }

    
    @Test
    void testArthurGetsScared() {
        TinyVoice voice1 = new TinyVoice(true);
        cabinDoor.addTinyVoice(voice1);
        
        assertFalse(arthur.isFrightened(), "Артур не должен быть испуган изначально");
        
        service.arthurPushAgainstDoor(arthur, cabinDoor);
        
        assertTrue(arthur.isFrightened(), "Артур должен испугаться верещащих голосов");
        assertTrue(voice1.getVolume() > 5, "Голос должен стать громче из-за испуга Артура");
    }
    
    @Test
    void testHandsReactToArthurPushing() {
        FurryHand hand = new FurryHand(true);
        cabinDoor.addFurryHand(hand);
        
        assertTrue(hand.isReachingThroughGap(), "Изначально рука просовывается в щель");
        assertFalse(hand.isGrabbingInsideDoor(), "Изначально рука не хватается внутри");
        
        hand.reactToArthurPushing();
        
        assertTrue(hand.isReachingThroughGap(), "Рука должна просовываться в щель");
        assertTrue(hand.isGrabbingInsideDoor(), "Рука должна хвататься внутри двери");
    }
    
    @Test
    void testVoicesReactToArthurFrightened() {
        TinyVoice voice = new TinyVoice(false);
        
        assertFalse(voice.isScreeching(), "Голос не должен верещать изначально");
        assertEquals(0, voice.getVolume(), "Громкость должна быть нулевой изначально");
        
        voice.reactToArthurFrightened(true);
        
        assertTrue(voice.isScreeching(), "Голос должен начать верещать");
        assertTrue(voice.getVolume() > 5, "Громкость должна значительно увеличиться");
    }
    
    @Test
    void testCompleteScenario() {
        FurryHand hand1 = new FurryHand(true);
        FurryHand hand2 = new FurryHand(true);
        cabinDoor.addFurryHand(hand1);
        cabinDoor.addFurryHand(hand2);
        
        TinyVoice voice1 = new TinyVoice(false);
        TinyVoice voice2 = new TinyVoice(false);
        cabinDoor.addTinyVoice(voice1);
        cabinDoor.addTinyVoice(voice2);
        
        service.arthurPushAgainstDoor(arthur, cabinDoor);
        
        assertTrue(arthur.isShoulderPushingDoor(), "Артур должен упираться плечом в дверь");
        assertTrue(arthur.isTryingToLockDoor(), "Артур должен пытаться запереть дверь");
        assertTrue(cabinDoor.isPoorlyFitted(), "Дверь должна быть плохо подогнана");
        assertFalse(cabinDoor.isLocked(), "Дверь не должна запираться");
        assertTrue(cabinDoor.hasFurryHandsInGaps(), "В щелях должны быть мохнатые ручки");
        assertTrue(cabinDoor.hasCrazyScreechingVoices(), "Должны быть верещащие голоса");
        assertTrue(arthur.isFrightened(), "Артур должен быть испуган");
        
        for (FurryHand hand : cabinDoor.getFurryHands()) {
            assertTrue(hand.isInkStained(), "Все ручки должны быть испачканы чернилами");
            assertTrue(hand.isReachingThroughGap(), "Ручки должны просовываться в щели");
            assertTrue(hand.isGrabbingInsideDoor(), "Ручки должны хвататься внутри двери");
        }
        
        for (TinyVoice voice : cabinDoor.getTinyVoices()) {
            assertTrue(voice.isScreeching(), "Все голоса должны верещать");
            assertTrue(voice.getVolume() > 5, "Голоса должны быть достаточно громкими");
        }
    }
    
    @Test
    void testDoorServiceHelperMethods() {
        FurryHand hand1 = new FurryHand(true);
        FurryHand hand2 = new FurryHand(true);
        cabinDoor.addFurryHand(hand1);
        cabinDoor.addFurryHand(hand2);
        
        TinyVoice voice1 = new TinyVoice(true);
        voice1.increaseVolume();
        voice1.increaseVolume();
        voice1.increaseVolume();
        
        TinyVoice voice2 = new TinyVoice(true);
        voice2.increaseVolume();
        voice2.increaseVolume();
        voice2.increaseVolume();
        
        cabinDoor.addTinyVoice(voice1);
        cabinDoor.addTinyVoice(voice2);
        
        hand1.reactToArthurPushing();
        hand2.reactToArthurPushing();
        
        assertTrue(service.areAllHandsActivelyReaching(cabinDoor), "Все руки должны активно просовываться и хвататься");
        assertTrue(service.areAllVoicesLoud(cabinDoor), "Все голоса должны быть громкими");
    }
}
