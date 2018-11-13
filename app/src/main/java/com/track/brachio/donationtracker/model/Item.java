package com.track.brachio.donationtracker.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Arrays;

/**
 * Information holder for Item
 */
@SuppressWarnings("ClassWithTooManyDependents")
public class Item {
    private String key;
    private String name;
    private Date dateCreated;
    private String locationID;
    private String shortDescription;
    private String longDescription;
    private double dollarValue;
    private ItemType category;
    private static final List<ItemType> legalItemTypes = Arrays.asList(ItemType.values());
    private ArrayList<String> comments = new ArrayList<>();
    private Bitmap picture;
    private static final int compressed = 50;

    /**
     * Constructor for Item
     */
    public Item() {

    }

    /**
     * Constructor for item
     * @param k key
     * @param n name
     * @param d date created
     * @param l locationID
     * @param val dollarValue
     * @param cat category
     */
    @SuppressWarnings("ConstructorWithTooManyParameters")
    public Item(String k, String n, Date d, String l, double val, String cat){
        key = k;
        name = n;
        dateCreated = (Date) d.clone();
        locationID = l;
        dollarValue = val;
        category = stringToItemType(cat);
    }

    /**
     * returns legal user types in a list
     * @return list of legal user types
     */
    public static List<ItemType> getLegalItemTypes() {
        return Collections.unmodifiableList(legalItemTypes);
    }

    /**
     * finds Item by the position
     * @param code code of ItemType
     * @return position
     */
    public static int findItemTypePosition(ItemType code) {
        int i = 0;
        while (i < legalItemTypes.size()) {
            if (code != null) {
                if (code.equals(legalItemTypes.get(i))) {
                    return i;
                }
            }
            i++;
        }
        return 0;
    }

    /**
     * getter - key
     * @return key
     */
    public String getKey() {
        return key;
    }

    /**
     * setter - key
     * @param key key being set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * getter - name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter - name
     * @param name name being set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * setter - date
     * @param dateCreated date being set
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = (Date) dateCreated.clone();
    }

    /**
     * setter - location
     * @param locationID location being set
     */
    public void setLocation(String locationID) {
        this.locationID = locationID;
    }

    /**
     * setter - short description
     * @param shortDescription description being set
     */
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    /**
     * setter - long description
     * @param longDescription description being set
     */
    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    /**
     * setter - dollar value
     * @param dollarValue dollar being set
     */
    public void setDollarValue(double dollarValue) {
        this.dollarValue = dollarValue;
    }

    /**
     * setter - category
     * @param category category being set
     */
    public void setCategory(ItemType category) {
        this.category = category;
    }

    /**
     * setter - comments
     * @param comments comments being set
     */
    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    /**
     * adds comments
     * @param comment comment being add to list
     */
    public void addComment(String comment) {
        comments.add(comment);
    }

    /**
     * setter - Picture
     * @param picture picture being set
     */
    public void setPicture(Bitmap picture) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.PNG, 100, out);
        this.picture = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
    }

    /**
     * getter - date
     * @return date
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * getter -location
     * @return location
     */
    public String getLocation() {
        return locationID;
    }

    /**
     * getter - short description
     * @return short description
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * getter - long description
     * @return long description
     */
    public String getLongDescription() {
        return longDescription;
    }

    /**
     * getter - dollar value
     * @return dollar value
     */
    public double getDollarValue() {
        return dollarValue;
    }

    /**
     * getter - category
     * @return category
     */
    public ItemType getCategory() {
        return category;
    }

    /**
     * getter - comments
     * @return comments
     */
    public ArrayList<String> getComments() {
        return comments;
    }

    /**
     * getter - picture
     * @return picture
     */
    public Bitmap getPicture() {
        return picture;
    }

    /**
     * gets ItemType by string
     * @param str string being searched for
     * @return ItemType of str
     */
    private ItemType stringToItemType(String str) {
        switch (str) {
            case "Food":
                return ItemType.Food;
            case "Clothes":
                return ItemType.Clothes;
            case "Furniture":
                return ItemType.Furniture;
            case "Sport":
                return ItemType.Sport;
            case "Electronics":
                return ItemType.Electronics;
            case "Miscellaneous":
                return ItemType.Miscellaneous;
        }
        return null;
    }

    /**
     * setter - picture
     * @param input the encoded Image of image
     * @param context activity context
     */
    public void setPicture(String input, Context context){
        /*
        if(input != null) {
            byte[] decodedByte = Base64.decode( input, 0 );

            Boolean isSDPresent = android.os.Environment
                    .getExternalStorageState().equals(
                            android.os.Environment.MEDIA_MOUNTED );

            File sdCardDirectory;
            if (isSDPresent) {
                // yes SD-card is present
                sdCardDirectory = new File(
                        Environment
                               .getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES ),
                        "IMG" );

                if (!sdCardDirectory.exists()) {
                    if (!sdCardDirectory.mkdirs()) {
                        Log.d( "MySnaps", "failed to create directory" );

                    }
                }
            } else {
                // Sorry
                sdCardDirectory = new File( context.getCacheDir(), "" );
            }


            String timeStamp = new SimpleDateFormat( "yyyyMMdd_HHmmss" )
                    .format( new Date() );

            Random rand = new Random();

            // nextInt is normally exclusive of the top value,
            // so add 1 to make it inclusive
            int randomNum = rand.nextInt( (1000 - 0) + 1 ) + 0;

            String nw = "IMG_" + timeStamp + randomNum + ".txt";
            File image = new File( sdCardDirectory, nw );


            // Encode the file as a PNG image.
            FileOutputStream outStream;
            try {


                outStream = new FileOutputStream( image );
                outStream.write( input.getBytes() );

                outStream.flush();
                outStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            Log.i( "Compress bitmap path", image.getPath() );
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeByteArray( decodedByte, 0, decodedByte.length );
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            //InputStream is = context.getResources().openRawResource( R.drawable.ic_menu_camera );
                //bitmap = BitmapFactory.decodeStream( is );

            } catch (Exception e) {
                e.printStackTrace();
                bitmap = null;
            }
        }
        */
        Log.d("ToDo", "need to uncomment");
        }


    /**
     * encodes picture
     * @return of encoded pic
     */
    public String encodePic(){
        if (picture != null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ByteArrayOutputStream baos=new  ByteArrayOutputStream();
            picture.compress(Bitmap.CompressFormat.JPEG,100, baos);
            byte [] b=baos.toByteArray();
            String temp=null;
            try{
                //noinspection CallToSystemGC
                System.gc();
                temp=Base64.encodeToString(b, Base64.DEFAULT);
            }catch(Exception e){
                e.printStackTrace();
            }catch(OutOfMemoryError e){
                baos=new  ByteArrayOutputStream();
                picture.compress(Bitmap.CompressFormat.JPEG, compressed, baos);
                b=baos.toByteArray();
                temp=Base64.encodeToString(b, Base64.DEFAULT);
                Log.e("EWN", "Out of memory error catched");
            }
            return temp;

        } else {
            return null;
        }
    }

}
