package walkingquest.kinematicworld.library.database.databaseHandlers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Objects;

import walkingquest.kinematicworld.library.database.DatabaseAccessor;
import walkingquest.kinematicworld.library.database.contracts.MiniQuestContract;
import walkingquest.kinematicworld.library.database.objects.MiniQuest;

/**
 * Created by Devon on 6/19/2017.
 */

public class MiniQuestHandler implements DatabaseHandler {


    // remember to cast the resulting objects as a miniquest
    @Override
    public ArrayList<Object> getObjects(String[] projections, String selection, String[] selectionArgs, String sortOrder, DatabaseAccessor dba) {

        SQLiteDatabase db = dba.getReadableDatabase();

        // execute the query
        Cursor cursor = db.query(
                MiniQuestContract.TABLE_NAME,
                projections,
                selection,
                selectionArgs,
                null, null,
                sortOrder
        );

        // build the return value
        ArrayList<Object> miniQuests = new ArrayList<Object>();
        while(!cursor.moveToNext()){
            miniQuests.add(new MiniQuest(
                    Long.toString(cursor.getLong(0)),   // id
                    cursor.getString(1),                // name
                    cursor.getString(2),                // description
                    cursor.getString(3),                // story
                    cursor.getShort(4),                 // difficulty
                    cursor.getLong(5),                  // required-steps
                    cursor.getLong(6),                  // completed-steps
                    (cursor.getInt(7) == 1)             // active
            ));
        }

        cursor.close();
        db.close();

        // if not objects can be found return null to indicate that no objects could be found or an error has occurred
        if(miniQuests.size() <= 0)
            return null;

        return miniQuests;
    }

    @Override
    public Object getObject(String[] projections, String selection, String[] selectionArgs, String sortOrder, DatabaseAccessor dba) {

        SQLiteDatabase db = dba.getReadableDatabase();

        // execute the query
        Cursor cursor = db.query(
                MiniQuestContract.TABLE_NAME,
                projections,
                selection,
                selectionArgs,
                null, null,
                sortOrder
        );

        // build the return value
        Object miniQuests = null;

        // isFirst and isLast will tell us if their is only one item in the list
        while(!cursor.isFirst() && !cursor.isLast()){
            miniQuests = new MiniQuest(
                    Long.toString(cursor.getLong(0)),   // id
                    cursor.getString(1),                // name
                    cursor.getString(2),                // description
                    cursor.getString(3),                // story
                    cursor.getShort(4),                 // difficulty
                    cursor.getLong(5),                  // required-steps
                    cursor.getLong(6),                  // completed-steps
                    (cursor.getInt(7) == 1)             // active
            );
        }

        cursor.close();
        db.close();

        return miniQuests;
    }

    @Override
    public boolean insertObject(Object object, DatabaseAccessor dba) {

        SQLiteDatabase db = dba.getWritableDatabase();

        MiniQuest miniQuest = (MiniQuest) object;

        ContentValues values = new ContentValues();
        values.put(MiniQuestContract.COLUMN_MINIQUEST_NAME, miniQuest.getName());
        values.put(MiniQuestContract.COLUMN_MINIQUEST_DESCRIPTION, miniQuest.getDescription());
        values.put(MiniQuestContract.COLUMN_MINIQUEST_STORY, miniQuest.getStory());
        values.put(MiniQuestContract.COLUMN_MINIQUEST_DIFFICULTY, miniQuest.getDifficulty());
        values.put(MiniQuestContract.COLUMN_MINIQUEST_REQUIRED_STEPS, miniQuest.getRequiredSteps());
        values.put(MiniQuestContract.COLUMN_MINIQUEST_COMPLETED_STEPS, miniQuest.getRequiredSteps());
        values.put(MiniQuestContract.COLUMN_MINIQUEST_ACTIVE, miniQuest.isActive());

        long id = db.insert(MiniQuestContract.TABLE_NAME, null, values);

        db.close();

        // returns if the insert was a success
        return id != -1;
    }

    @Override
    public boolean updateObject(Object object, DatabaseAccessor dba) {

        SQLiteDatabase db = dba.getWritableDatabase();

        MiniQuest miniQuest = (MiniQuest) object;

        String selection = MiniQuestContract.COLUMN_MINIQUEST_STORY  + " = ?";
        String[] selectionArgs = { miniQuest.getName() };

        ContentValues values = new ContentValues();
        values.put(MiniQuestContract.COLUMN_MINIQUEST_NAME, miniQuest.getName());
        values.put(MiniQuestContract.COLUMN_MINIQUEST_DESCRIPTION, miniQuest.getDescription());
        values.put(MiniQuestContract.COLUMN_MINIQUEST_STORY, miniQuest.getStory());
        values.put(MiniQuestContract.COLUMN_MINIQUEST_DIFFICULTY, miniQuest.getDifficulty());
        values.put(MiniQuestContract.COLUMN_MINIQUEST_REQUIRED_STEPS, miniQuest.getRequiredSteps());
        values.put(MiniQuestContract.COLUMN_MINIQUEST_COMPLETED_STEPS, miniQuest.getRequiredSteps());
        values.put(MiniQuestContract.COLUMN_MINIQUEST_ACTIVE, miniQuest.isActive());

        int count = db.update(MiniQuestContract.TABLE_NAME, values, selection, selectionArgs);

        db.close();

        // returns if any rows have been updated
        return count != 0;
    }

    @Override
    public boolean deleteObject(Object object, DatabaseAccessor dba) {

        SQLiteDatabase db = dba.getWritableDatabase();

        String selection = MiniQuestContract.COLUMN_MINIQUEST_STORY  + " = ?";
        String[] selectionArgs = { ((MiniQuest) object).getName() };

        int success = db.delete(MiniQuestContract.TABLE_NAME, selection, selectionArgs);

        db.close();

        // if there were 0 rows effected then return false
        return success != 0;
    }
}
