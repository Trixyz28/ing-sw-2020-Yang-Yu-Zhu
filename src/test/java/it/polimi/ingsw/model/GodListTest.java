package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;

public class GodListTest extends TestCase{

    GodList godList = new GodList(3);

    @Test
    public void testSetComplete() {
        ArrayList<String> completeGodList = godList.getCompleteGodList();

        assertEquals("APOLLO",completeGodList.get(0));
        assertEquals("ARTEMIS",completeGodList.get(1));
        assertEquals("ATHENA",completeGodList.get(2));
        assertEquals("ATLAS",completeGodList.get(3));
        assertEquals("DEMETER",completeGodList.get(4));
        assertEquals("HEPHAESTUS",completeGodList.get(5));
        assertEquals("HERA",completeGodList.get(6));
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