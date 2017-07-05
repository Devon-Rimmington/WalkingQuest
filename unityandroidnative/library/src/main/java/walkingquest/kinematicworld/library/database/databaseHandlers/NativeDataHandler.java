package walkingquest.kinematicworld.library.database.databaseHandlers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Debug;
import android.util.Log;

import walkingquest.kinematicworld.library.database.DatabaseAccessor;
import walkingquest.kinematicworld.library.database.contracts.NativeDataContract;
import walkingquest.kinematicworld.library.database.objects.NativeData;

/**
 * Created by Devon on 6/28/2017.
 */

public class NativeDataHandler {

    public static NativeData getNativeData(SQLiteDatabase db){
        return getNativeData(new String[]{
                NativeDataContract._ID,
                NativeDataContract.COLUMN_USER_ID,
                NativeDataContract.COLUMN_CHARACTER_ID,
                NativeDataContract.COLUMN_ACTIVE_EVENT_AVAILABLE_COUNT,
                NativeDataContract.COLUMN_MINI_QUEST_ID,
                NativeDataContract.COLUMN_MINI_QUEST_STEPS_REQUIRED,
                NativeDataContract.COLUMN_MINI_QUEST_STEPS_COMPLETED,
                NativeDataContract.COLUMN_MINI_QUEST_START_TIME,
                NativeDataContract.COLUMN_TOTAL_USER_STEPS,
                NativeDataContract.COLUMN_TRIP_COUNTER_STEPS,
                NativeDataContract.COLUMN_MINI_QUEST_AVAILABLE
        }, null, null, db);
    }

    public static NativeData getNativeData(String[] projections, String selection, String[] selectionArgs, SQLiteDatabase db){

        NativeData nativeData = null;

        Cursor cursor = db.query(
          NativeDataContract.TABLE_NAME,
                projections,
                selection,
                selectionArgs,
                null, null, null
        );

        cursor.moveToFirst();
        while(cursor.moveToNext()){
            Log.i("Unity", "Entry");
        }

        if(cursor.moveToFirst()) {

            Log.i("Unity", "getting native data result");

            nativeData = new NativeData(
                    cursor.getString(1),        // User ID
                    cursor.getLong(2),          // CharacterID
                    cursor.getLong(3),          // number of ActiveEvents
                    cursor.getLong(4),          // MiniQuestID
                    cursor.getLong(5),          // MiniQuestStepsRequired
                    cursor.getLong(6),          // MiniQuestStepCompleted
                    cursor.getLong(7),          // MiniQuestStartTime
                    cursor.getLong(8),          // TotalUserSteps
                    cursor.getLong(9),          // TripCounterSteps
                    (1 == cursor.getInt(10))    // is MiniQuestAvailable
            );

            Log.i("Unity", "title " + cursor.getString(1));
        }
        cursor.close();

        return nativeData;
    }

    public static boolean insertNativeData(SQLiteDatabase db, NativeData nativeData){

        ContentValues contentValues = new ContentValues();

        contentValues.put(NativeDataContract.COLUMN_USER_ID, nativeData.getUserId());
        contentValues.put(NativeDataContract.COLUMN_CHARACTER_ID, nativeData.getCharacterId());
        contentValues.put(NativeDataContract.COLUMN_ACTIVE_EVENT_AVAILABLE_COUNT, nativeData.getAvailableEventCount());
        contentValues.put(NativeDataContract.COLUMN_MINI_QUEST_AVAILABLE, nativeData.isAvailableMiniQuest());
        contentValues.put(NativeDataContract.COLUMN_MINI_QUEST_ID, nativeData.getMiniquestId());
        contentValues.put(NativeDataContract.COLUMN_MINI_QUEST_STEPS_REQUIRED, nativeData.getMiniquestStepRequired());
        contentValues.put(NativeDataContract.COLUMN_MINI_QUEST_STEPS_COMPLETED, nativeData.getMiniquestStepCompleted());
        contentValues.put(NativeDataContract.COLUMN_MINI_QUEST_START_TIME, nativeData.getMiniquestStartTime());
        contentValues.put(NativeDataContract.COLUMN_TOTAL_USER_STEPS, nativeData.getUserTotalStepCount());
        contentValues.put(NativeDataContract.COLUMN_TRIP_COUNTER_STEPS, nativeData.getTripCounterSteps());

        long id = db.insert(NativeDataContract.TABLE_NAME,
                null, contentValues);

        // if the insert didn't work then return false
        return id != -1;
    }

    public static boolean updateNativeData(NativeData nativeData, SQLiteDatabase db){


        Log.i("Unity", nativeData.getTripCounterSteps() + " " + nativeData.getUserTotalStepCount());

        String selection = NativeDataContract.COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {nativeData.getUserId()};

        ContentValues contentValues = new ContentValues();
        contentValues.put(NativeDataContract.COLUMN_USER_ID, nativeData.getUserId());
        contentValues.put(NativeDataContract.COLUMN_CHARACTER_ID, nativeData.getCharacterId());
        contentValues.put(NativeDataContract.COLUMN_ACTIVE_EVENT_AVAILABLE_COUNT, nativeData.getAvailableEventCount());
        contentValues.put(NativeDataContract.COLUMN_MINI_QUEST_AVAILABLE, nativeData.isAvailableMiniQuest());
        contentValues.put(NativeDataContract.COLUMN_MINI_QUEST_ID, nativeData.getMiniquestId());
        contentValues.put(NativeDataContract.COLUMN_MINI_QUEST_STEPS_REQUIRED, nativeData.getMiniquestStepRequired());
        contentValues.put(NativeDataContract.COLUMN_MINI_QUEST_STEPS_COMPLETED, nativeData.getMiniquestStepCompleted());
        contentValues.put(NativeDataContract.COLUMN_MINI_QUEST_START_TIME, nativeData.getMiniquestStartTime());
        contentValues.put(NativeDataContract.COLUMN_TOTAL_USER_STEPS, nativeData.getUserTotalStepCount());
        contentValues.put(NativeDataContract.COLUMN_TRIP_COUNTER_STEPS, nativeData.getTripCounterSteps());


        int count = db.update(NativeDataContract.TABLE_NAME,
                contentValues, null, null);

        // return if the entry gets updated
        return count != 0;
    }

}
