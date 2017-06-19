package walkingquest.kinematicworld.library.database.databaseHandlers;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Devon on 6/19/2017.
 */

public interface DatabaseHandler {

    // get a list of database entries
    // the db must be a readable database
    ArrayList<Object> getObjects(String[] projections, String selection, String[] selectionArgs, String sortOrder, SQLiteDatabase db);

    // get a single database entry
    // the db must be a readable database
    Object getObject(String[] projections, String selection, String[] selectionArgs, String sortOrder, SQLiteDatabase db);

    // insert an object into the database
    // the db must be a writable database
    boolean insertObject(Object object, SQLiteDatabase db);

    // update an existing object from the database
    // the db must be a writable database
    boolean updateObject(Object object, SQLiteDatabase db);

    // remove an existing object from the database
    // the db must be a writable database
    boolean deleteObject(Object object, SQLiteDatabase db);

}
