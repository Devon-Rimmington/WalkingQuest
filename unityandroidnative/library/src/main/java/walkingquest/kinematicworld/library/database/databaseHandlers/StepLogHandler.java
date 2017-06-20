package walkingquest.kinematicworld.library.database.databaseHandlers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import walkingquest.kinematicworld.library.database.contracts.EventContract;
import walkingquest.kinematicworld.library.database.contracts.StepLogContract;
import walkingquest.kinematicworld.library.database.objects.Event;
import walkingquest.kinematicworld.library.database.objects.StepLog;
import walkingquest.kinematicworld.library.database.sql.SQLTables;

/**
 * Created by Devon on 6/20/2017.
 */

public class StepLogHandler implements DatabaseHandler {


    @Override
    public ArrayList<Object> getObjects(String[] projections, String selection, String[] selectionArgs, String sortOrder, SQLiteDatabase db) {

        Cursor cursor = db.query(
                StepLogContract.TABLE_NAME,
                projections,
                selection,
                selectionArgs,
                null, null,
                sortOrder
        );
        // db.close();

        ArrayList<Object> stepLogs = new ArrayList<>();
        while(!cursor.moveToNext()){
            stepLogs.add(new StepLog(
                    Long.toString(cursor.getLong(0)),   // id
                    cursor.getString(1))                // date
            );
        }

        if(stepLogs.size() <= 0){
            return null;
        }

        return stepLogs;
    }

    @Override
    public Object getObject(String[] projections, String selection, String[] selectionArgs, String sortOrder, SQLiteDatabase db) {

        Cursor cursor = db.query(
                StepLogContract.TABLE_NAME,
                projections,
                selection,
                selectionArgs,
                null, null,
                sortOrder
        );
        // db.close();

        Object stepLog = null;
        if(cursor.moveToFirst()){
            stepLog = new StepLog(
                    Long.toString(cursor.getLong(0)),   // id
                    cursor.getString(1)                 // date
            );
        }

        return stepLog;
    }

    // sortOrder is not going to be changed so it has been hard coded
    public Object getMostRecentStepLog(String[] projections, String selection, String[] selectionArgs, SQLiteDatabase db) {

        Cursor cursor = db.query(
                StepLogContract.TABLE_NAME,
                projections,
                selection,
                selectionArgs,
                null, null,
                StepLogContract.COLUMN_NAME_DATE + " DESC"
        );
        // db.close();

        Object stepLog = null;
        if(cursor.moveToFirst()){
            stepLog = new StepLog(
                    Long.toString(cursor.getLong(0)),   // id
                    cursor.getString(1)                 // date
            );
        }

        cursor.close();

        return stepLog;
    }


    // gets the total number of steps that have been collected
    // todo find a place to save a single global number of steps
    public long getStepLogCount(SQLiteDatabase db) {

        // create the query for counting the number of step entries
        String count = "Select count(*) from " + StepLogContract.TABLE_NAME;
        Cursor cursor = db.rawQuery(count, null);
        // db.close();

        // move the cursor to the correct location to find the count
        if(cursor.moveToFirst()) {

            // close the cursor and return the count
            long steps = (long) cursor.getInt(0);
            cursor.close();

            Log.d("Unity", "Counting entries from StepLog " + steps);

            return steps;
        }

        return -1;
    }

    @Override
    public boolean insertObject(Object object, SQLiteDatabase db) {
        StepLog stepLog = (StepLog) object;

        ContentValues values = new ContentValues();
        values.put(StepLogContract.COLUMN_NAME_DATE, stepLog.getDate());

        long id = db.insert(StepLogContract.TABLE_NAME, null, values);
        // db.close();

        Log.d("Unity", "Adding a new entry to the StepLog " + id);

        // return if an error has occurred or not
        return id != -1;
    }

    @Override
    public boolean updateObject(Object object, SQLiteDatabase db) {
        // as this is a log of steps entries will not be updated thus the implementation that you see
        return false;
    }

    @Override
    public boolean deleteObject(Object object, SQLiteDatabase db) {
        // as this is a log of steps entries will not be deleted thus the implementation that you see
        return false;
    }

    // because all of the entries need to be deleted when they are recorded we will just delete them all
    public boolean deleteAllStepLogs(SQLiteDatabase db) {

        String selection = " 1 = ?";
        String[] selectionArgs = { "1" };

        int success = db.delete(StepLogContract.TABLE_NAME, selection, selectionArgs);
        // db.close();

        // return if the delete was successful or not
        return success != 0;
    }
}
