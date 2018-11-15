package com.track.brachio.donationtracker;

import org.junit.Test;
import java.lang.reflect.Method;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import com.track.brachio.donationtracker.model.Location;
import com.track.brachio.donationtracker.model.LocationType;

public class CarolinaTest {

    private static Location tester;

    @BeforeClass
    public static void beforeClass() {
        tester = new Location();
    }

    /**
     * DropOff passed in test
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws java.lang.reflect.InvocationTargetException
     */
    @Test
    public void dropOffTest() throws NoSuchMethodException,
            IllegalAccessException, java.lang.reflect.InvocationTargetException{
        String entry = "DropOff";
        Method m1 = Location.class.getDeclaredMethod("getTypeByString", String.class);
        m1.setAccessible(true);
        LocationType result = (LocationType) m1.invoke(tester, entry);
        assertEquals("Returns DropOff location type if 'Dropoff' passed in",
                LocationType.DropOff, result);
    }

    /**
     * Store passed in test
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws java.lang.reflect.InvocationTargetException
     */
    @Test
    public void storeTest() throws NoSuchMethodException,
            IllegalAccessException, java.lang.reflect.InvocationTargetException{
        String entry = "Store";
        Method m1 = Location.class.getDeclaredMethod("getTypeByString", String.class);
        m1.setAccessible(true);
        LocationType result = (LocationType) m1.invoke(tester, entry);
        assertEquals("Returns Store location type if 'Store' passed in",
                LocationType.Store, result);
    }
    /**
     * Warehouse passed in test
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws java.lang.reflect.InvocationTargetException
     */
    @Test
    public void warehouseTest() throws NoSuchMethodException,
            IllegalAccessException, java.lang.reflect.InvocationTargetException{
        String entry = "Warehouse";
        Method m1 = Location.class.getDeclaredMethod("getTypeByString", String.class);
        m1.setAccessible(true);
        LocationType result = (LocationType) m1.invoke(tester, entry);
        assertEquals("Returns Warehouse location type if 'Warehouse' passed in",
                LocationType.Warehouse, result);
    }

    /**
     * Empty string test
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws java.lang.reflect.InvocationTargetException
     */
    @Test
    public void nullTest() throws NoSuchMethodException,
            IllegalAccessException, java.lang.reflect.InvocationTargetException{
        String entry = "";
        Method m1 = Location.class.getDeclaredMethod("getTypeByString", String.class);
        m1.setAccessible(true);
        LocationType result = (LocationType) m1.invoke(tester, entry);
        assertEquals("Returns null if string is empty", null, result);
    }
    /**
     * Wrong string test
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws java.lang.reflect.InvocationTargetException
     */
    @Test
    public void wrongStringTest() throws NoSuchMethodException,
            IllegalAccessException, java.lang.reflect.InvocationTargetException{
        String entry = "location";
        Method m1 = Location.class.getDeclaredMethod("getTypeByString", String.class);
        m1.setAccessible(true);
        LocationType result = (LocationType) m1.invoke(tester, entry);
        assertEquals("Return null if string is invalid", null, result);
    }
}
