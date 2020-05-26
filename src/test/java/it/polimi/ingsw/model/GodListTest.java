package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.Test;

public class GodListTest extends TestCase{

    GodList godList = new GodList(3);

    @Test
    public void testSetComplete() {
        String[] completeGodList = godList.getCompleteGodList();

        assertEquals("APOLLO",completeGodList[0]);
        assertEquals("ARTEMIS",completeGodList[1]);
        assertEquals("ATHENA",completeGodList[2]);
        assertEquals("ATLAS",completeGodList[3]);
        assertEquals("DEMETER",completeGodList[4]);
        assertEquals("HEPHAESTUS",completeGodList[5]);
        assertEquals("MINOTAUR",completeGodList[6]);
        assertEquals("PAN",completeGodList[7]);
        assertEquals("PROMETHEUS",completeGodList[8]);
    }


    @Test
    public void testAddInGodList() {
        godList.selectGod("APOLLO");
        assertTrue(godList.addInGodList());
        assertEquals("APOLLO",godList.getCurrentGodList().get(0));

        godList.selectGod("APOLLO");
        assertTrue(godList.checkGod());
        assertFalse(godList.addInGodList());
        godList.selectGod("ATHENA");
        assertFalse(godList.checkGod());


    }

    @Test
    public void testRemoveFromGodList() {
        testAddInGodList();
        godList.selectGod("MINOTAUR");
        godList.addInGodList();
        godList.removeFromGodList("APOLLO");
        assertEquals("MINOTAUR",godList.getCurrentGodList().get(0));
    }

    @Test
    public void testLength() {
        testAddInGodList();
        godList.selectGod("MINOTAUR");
        godList.addInGodList();
        godList.selectGod("ATLAS");
        godList.addInGodList();
        assertTrue(godList.checkLength());
    }

}