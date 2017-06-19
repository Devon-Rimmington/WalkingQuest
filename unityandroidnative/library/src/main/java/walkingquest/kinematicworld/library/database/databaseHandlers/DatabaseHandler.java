package walkingquest.kinematicworld.library.database.databaseHandlers;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Objects;

import walkingquest.kinematicworld.library.database.DatabaseAccessor;

/**
 * Created by Devon on 6/19/2017.
 */

public interface DatabaseHandler {

    // get a list of database entries
    // the db must be a readable database
    ArrayList<Object> getObjects(String[] projections, String selection, String[] selectionArgs, String sortOrder, DatabaseAccessor dba);

    // get a single database entry
    // the db must be a readable database
    Object getObject(String[] projections, String selection, String[] selectionArgs, String sortOrder, DatabaseAccessor dba);

    // insert an object into the database
    // the db must be a writable database
    boolean insertObject(Object object, DatabaseAccessor dba);

    // update an existing object from the database
    // the db must be a writable database
    boolean updateObject(Object object, DatabaseAccessor dba);

    // remove an existing object from the database
    // the db must be a writable database
    boolean deleteObject(Object object, DatabaseAccessor dba);

}
