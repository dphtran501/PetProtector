package edu.orangecoastcollege.cs273.petprotector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;

/**
 * A model class to manage the SQLite database used to store College data.
 *
 * @author Derek Tran
 * @version 1.0
 * @since November 5, 2017
 */
public class DBHelper extends SQLiteOpenHelper
{
    // Database constants
    private static final String DATABASE_NAME = "PetProtector";
    private static final String DATABASE_TABLE = "Pets";
    private static final int DATABASE_VERSION = 1;

    // Field constants
    private static final String[] FIELD_NAMES = {"_id", "name", "details", "phone", "image_uri"};
    private static final String[] FIELD_TYPES = {"INTEGER PRIMARY KEY", "TEXT", "TEXT", "TEXT", "TEXT"};

    /**
     * Instantiates a new <code>DBHelper</code> object with the given context.
     *
     * @param context The activity used to open or create the database.
     */
    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates the database table for the first time.
     *
     * @param sqLiteDatabase The database.
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        int numOfFields = FIELD_NAMES.length;
        StringBuilder createTable = new StringBuilder("CREATE TABLE ");
        createTable.append(DATABASE_TABLE).append("(");
        for (int i = 0; i < numOfFields; i++)
        {
            createTable.append(FIELD_NAMES[i]).append(" ").append(FIELD_TYPES[i]).append((i < numOfFields - 1) ? "," : ")");
        }

        sqLiteDatabase.execSQL(createTable.toString());
    }

    /**
     * Drops the existing database table and creates a new one when database is upgraded.
     *
     * @param sqLiteDatabase The database.
     * @param oldVersion     The old database version.
     * @param newVersion     The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(sqLiteDatabase);
    }

    /**
     * Adds a <code>Pet</code> object to the database.
     *
     * @param pet The <code>Pet</code> object to add to the database.
     * @return The auto-generated ID of the <code>Pet</code> record in the database.
     */
    public int addPet(Pet pet)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FIELD_NAMES[1], pet.getName());
        values.put(FIELD_NAMES[2], pet.getDetails());
        values.put(FIELD_NAMES[3], pet.getPhone());
        values.put(FIELD_NAMES[4], pet.getImageURI().toString());

        int id = (int) db.insert(DATABASE_TABLE, null, values);
        db.close();

        return id;
    }

    /**
     * Gets a list of all <code>Pet</code> objects in the database.
     *
     * @return List of all <code>Pet</code> objects in the database.
     */
    public ArrayList<Pet> getAllPets()
    {
        ArrayList<Pet> petsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DATABASE_TABLE, FIELD_NAMES, null, null, null, null, null);

        if (cursor.moveToFirst())
        {
            do
            {
                Pet pet = new Pet(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), Uri.parse(cursor.getString(4)));
                petsList.add(pet);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return petsList;
    }
}
