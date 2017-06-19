package walkingquest.kinematicworld.library.database.contracts;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.Date;

/**
 * Created by Devon on 6/17/2017.
 */

public final class StepLogContract implements BaseColumns {

    public  StepLogContract(){}

    public static final String TABLE_NAME = "step_log";
    public static final String COLUMN_NAME_DATE = "date";


    // todo remove these and add a new class that will serve as the interface for the StepLog
    public static class StepCommands{

        public static ContentValues AddEntry(){
            ContentValues contentValues = new ContentValues();
            contentValues.put(StepLogContract.COLUMN_NAME_DATE, new java.sql.Date(new Date().getTime()).toString());
            return contentValues;
        }

        public static long GetCompleteStepCount(SQLiteDatabase db){
            // create the query for counting the number of step entries
            String count = "Select count(*) from " + StepLogContract.TABLE_NAME;
            Cursor cursor = db.rawQuery(count, null);

            // move the cursor to the correct location to find the count
            cursor.moveToFirst();

            // close the cursor and return the count
            long steps = (long)cursor.getInt(0);
            cursor.close();
            return steps;
        }

    }



}
