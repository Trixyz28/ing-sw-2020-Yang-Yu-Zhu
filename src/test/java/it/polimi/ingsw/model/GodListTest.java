package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Test;

public class GodListTest extends TestCase{

    GodList godList = new GodList(3);

    @Test
    public void testSetComplete() {
        godList.setComplete();
        assertEquals("APOLLO",godList.showComplete()[0]);
        assertEquals("ARTEMIS",godList.showComplete()[1]);
        assertEquals("ATHENA",godList.showComplete()[2]);
        assertEquals("ATLAS",godList.showComplete()[3]);
        assertEquals("DEMETER",godList.showComplete()[4]);
        assertEquals("HEPHAESTUS",godList.showComplete()[5]);
        assertEquals("MINOTAUR",godList.showComplete()[6]);
        assertEquals("PAN",godList.showComplete()[7]);
        assertEquals("PROMETHEUS",godList.showComplete()[8]);
    }


    @Test
    public void testAddInGodList() {
        godList.selectGod("APOLLO");
        godList.addInGodList();
        assertEquals("APOLLO",godList.getCurrentGodList().get(0));
        assertEquals(1,godList.getLength());
    }

    @Test
    public void testRemoveFromGodList() {
        testAddInGodList();
        godList.selectGod("MINOTAUR");
        godList.addInGodList();
        godList.removeFromGodList("APOLLO");
        assertEquals(1,godList.getLength());
        assertEquals("MINOTAUR",godList.getCurrentGodList().get(0));
    }


    @Test
    public void testSelectGod() {
        godList.selectGod("APOLLO");
        assertEquals("APOLLO",godList.getSelectedGod());
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