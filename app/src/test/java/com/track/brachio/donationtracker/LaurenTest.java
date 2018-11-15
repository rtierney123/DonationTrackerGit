package com.track.brachio.donationtracker;
import com.track.brachio.donationtracker.model.Location;
import com.track.brachio.donationtracker.model.LocationType;

import org.junit.Assert;
import org.junit.Test;
import java.util.List;
import java.util.Arrays;

import static org.junit.Assert.*;

public class LaurenTest {
    private static final List<LocationType> legalLocationTypes = Arrays.asList(LocationType.values());
    /**
     * Tests if input is null
     */
    @Test
    public void testNullLocation() {
        //legalLocationTypes is constant
        //If while loop runs without finding anything should return
        Assert.assertEquals("Null should return 0", 0, Location.findLocationTypePosition(null));
    }

    /**
     * Tests if the code input is valid
     */
    @Test
    public void testValidLocation() {
        int i = 0;
        while (i < legalLocationTypes.size()) {
            assertEquals("Return value should equal: " + i,
                    i, Location.findLocationTypePosition(legalLocationTypes.get(i)));
            i++;
        }
    }
}
