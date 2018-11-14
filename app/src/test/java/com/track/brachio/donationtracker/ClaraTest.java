package com.track.brachio.donationtracker;

import com.track.brachio.donationtracker.model.Item;
import com.track.brachio.donationtracker.model.ItemType;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * Clara Junit Tests
 */
public class ClaraTest {
    private static Item holder;

    /**
     * Initializes an Item variable for the testing class
     */
    @BeforeClass
    public static void beforeClass() {
        holder = new Item();
    }

    /**
    * Tests if input is empty string
     * @throws IllegalAccessException if method cannot be invoked
     * @throws InvocationTargetException if method cannot be invoked
     * @throws NoSuchMethodException if stringToItemType does not exist
    */
    @Test
    public void testNullInput() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException{
        String input = "";
        Method method = Item.class.getDeclaredMethod("stringToItemType", String.class);
        method.setAccessible(true);
        ItemType output = (ItemType) method.invoke(holder, input);
        assertEquals("Empty string should return null", null, output);
    }
    /**
    * Tests if input is not one of the strings
     * @throws IllegalAccessException if method cannot be invoked
     * @throws InvocationTargetException if method cannot be invoked
     * @throws NoSuchMethodException if stringToItemType does not exist
    */
    @Test
    public void testInvalidInput() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException{
        String input = "clara";
        Method method = Item.class.getDeclaredMethod("stringToItemType", String.class);
        method.setAccessible(true);
        ItemType output = (ItemType) method.invoke(holder, input);
        assertEquals("Invalid string should return null", null, output);
    }

    /**
     * Tests if input is food
     * @throws IllegalAccessException if method cannot be invoked
     * @throws InvocationTargetException if method cannot be invoked
     * @throws NoSuchMethodException if stringToItemType does not exist
     */
    @Test
    public void testFoodInput() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException{
        String input = "Food";
        Method method = Item.class.getDeclaredMethod("stringToItemType", String.class);
        method.setAccessible(true);
        ItemType output = (ItemType) method.invoke(holder, input);
        assertEquals("Food string should return food item type", ItemType.Food, output);
    }

    /**
     * Tests if input is clothes
     * @throws IllegalAccessException if method cannot be invoked
     * @throws InvocationTargetException if method cannot be invoked
     * @throws NoSuchMethodException if stringToItemType does not exist
      */
    @Test
    public void testClothesInput() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException{
        String input = "Clothes";
        Method method = Item.class.getDeclaredMethod("stringToItemType", String.class);
        method.setAccessible(true);
        ItemType output = (ItemType) method.invoke(holder, input);
        assertEquals("Clothes string should return clothes item type", ItemType.Clothes, output);
    }

    /**
     * Tests if input is sport
     * @throws IllegalAccessException if method cannot be invoked
     * @throws InvocationTargetException if method cannot be invoked
     * @throws NoSuchMethodException if stringToItemType does not exist
      */
    @Test
    public void testSportInput() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException{
        String input = "Sport";
        Method method = Item.class.getDeclaredMethod("stringToItemType", String.class);
        method.setAccessible(true);
        ItemType output = (ItemType) method.invoke(holder, input);
        assertEquals("Sport string should return sport item type", ItemType.Sport, output);
    }

    /**
     * Tests if input is electronic
     * @throws IllegalAccessException if method cannot be invoked
     * @throws InvocationTargetException if method cannot be invoked
     * @throws NoSuchMethodException if stringToItemType does not exist
      */
    @Test
    public void testElectronicInput() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException{
        String input = "Sport";
        Method method = Item.class.getDeclaredMethod("stringToItemType", String.class);
        method.setAccessible(true);
        ItemType output = (ItemType) method.invoke(holder, input);
        assertEquals("Electronic string should return electronic item type",
                ItemType.Electronics, output);
    }

    /**
     * Tests if input is miscellaneous
     * @throws IllegalAccessException if method cannot be invoked
     * @throws InvocationTargetException if method cannot be invoked
     * @throws NoSuchMethodException if stringToItemType does not exist
      */
    @Test
    public void testMiscInput() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException{
        String input = "Miscellaneous";
        Method method = Item.class.getDeclaredMethod("stringToItemType", String.class);
        method.setAccessible(true);
        ItemType output = (ItemType) method.invoke(holder, input);
        assertEquals("Misc string should return misc item type", ItemType.Miscellaneous, output);
    }

    /**
     * Tests if input is furniture
     * @throws IllegalAccessException if method cannot be invoked
     * @throws InvocationTargetException if method cannot be invoked
     * @throws NoSuchMethodException if stringToItemType does not exist
      */
    @Test
    public void testFurnitureInput() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException{
        String input = "Furniture";
        Method method = Item.class.getDeclaredMethod("stringToItemType", String.class);
        method.setAccessible(true);
        ItemType output = (ItemType) method.invoke(holder, input);
        assertEquals("Furniture string should return furniture item type",
                ItemType.Furniture, output);
    }
}
