package com.track.brachio.donationtracker.model;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.List;
import java.util.Arrays;

import static org.junit.Assert.*;

public class JessicaTest {
    private static final List<ItemType> legalItemTypes = Arrays.asList(ItemType.values());

    @Test
    public void testNullInput() {
        //legalItemTypes is constant
        //If while loop runs without finding anything should return
        assertEquals("Null should return 0", 0, Item.findItemTypePosition(null));
    }

//    @Test
//    public void testInvalidCodeInput() {
//        //Test when a code that is invalid is passed in
//        //actually shouldnt be able to receive this input
//        assertEquals("Invalid Item type should return 0", 0, Item.findItemTypePosition());
//    }

    @Test
    public void testValidCodeInputs() {
        int i = 0;
        while (i < legalItemTypes.size()) {
            assertEquals("Return value should equal: " + i, i, Item.findItemTypePosition(legalItemTypes.get(i)));
            i++;
        }
    }



}