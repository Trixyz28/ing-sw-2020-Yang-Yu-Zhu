package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GodListTest extends TestCase{

    GodList godList = new GodList(3);

    /**
     * Test: setup complete GodList
     */
    @Test
    public void testSetComplete() {
        List<String> completeGodList = godList.getCompleteGodList();

        assertEquals("APOLLO",completeGodList.get(0));
        assertEquals("ARTEMIS",completeGodList.get(1));
        assertEquals("ATHENA",completeGodList.get(2));
        assertEquals("ATLAS",completeGodList.get(3));
        assertEquals("DEMETER",completeGodList.get(4));
        assertEquals("HEPHAESTUS",completeGodList.get(5));
        assertEquals("HERA",completeGodList.get(6));
        assertEquals(15, completeGodList.size());
    }

    /**
     * Test: simulating the godlist define process
     */
    @Test
    public void testAddInGodList() {
        godList.selectGod("APOLLO");
        assertTrue(godList.addInGodList());
        assertEquals("APOLLO",godList.getCurrentGodList().get(0));

        /* "APOLLO" can't be added again */
        godList.selectGod("APOLLO");
        assertTrue(godList.checkGod());
        assertFalse(godList.addInGodList());

        /* "ATHENA" is not in the list can be added */
        godList.selectGod("ATHENA");
        assertFalse(godList.checkGod());
        assertTrue(godList.addInGodList());

        /* 3 players 2 gods -> not end of define */
        assertFalse(godList.checkLength());
        assertEquals(2, godList.getCurrentGodList().size());


    }

    /**
     * Test: simulating the currentList update process
     */
    @Test
    public void testRemoveFromGodList() {
        testAddInGodList();
        godList.selectGod("MINOTAUR");
        godList.addInGodList();
        /* remove "APOLLO" */
        godList.removeFromGodList("APOLLO");
        assertEquals("ATHENA",godList.getCurrentGodList().get(0));
        assertEquals("MINOTAUR",godList.getCurrentGodList().get(1));
    }

    /**
     * Test: assert all gods added correctly
     */
    @Test
    public void testLength() {
        testAddInGodList();
        /* add "ATLAS" */
        godList.selectGod("ATLAS");
        godList.addInGodList();
        /* 3 players 3 gods */
        assertTrue(godList.checkLength());
    }

}