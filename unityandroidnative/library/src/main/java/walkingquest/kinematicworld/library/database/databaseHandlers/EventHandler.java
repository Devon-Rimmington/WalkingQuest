package walkingquest.kinematicworld.library.database.databaseHandlers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Objects;

import walkingquest.kinematicworld.library.database.contracts.EventContract;
import walkingquest.kinematicworld.library.database.contracts.MiniQuestContract;
import walkingquest.kinematicworld.library.database.objects.Event;

/**
 * Created by Devon on 6/19/2017.
 */

public class EventHandler implements DatabaseHandler {
    @Override
    public ArrayList<Object> getObjects(String[] projections, String selection, String[] selectionArgs, String sortOrder, SQLiteDatabase db) {

        Cursor cursor = db.query(
                EventContract.TABLE_NAME,
                projections,
                selection,
                selectionArgs,
                null, null,
                sortOrder
        );

        ArrayList<Object> events = new ArrayList<Object>();
        while(!cursor.moveToNext()){
            events.add(new Event(
                    Long.toString(cursor.getLong(0)),   // id
                    cursor.getString(1),                // name
                    cursor.getString(2),                // description
                    cursor.getString(3),                // story
                    cursor.getShort(4),                 // difficulty
                    cursor.getString(5),                // how-to
                    cursor.getShort(6)                  // type
            ));
        }

        // if not objects can be found return null to indicate that no objects could be found or an error has occurred
        if(events.size() <= 0)
            return null;

        return events;
    }

    @Override
    public Object getObject(String[] projections, String selection, String[] selectionArgs, String sortOrder, SQLiteDatabase db) {

        Cursor cursor = db.query(
                EventContract.TABLE_NAME,
                projections,
                selection,
                selectionArgs,
                null, null,
                sortOrder
        );

        Event event = null;

        while(cursor.isFirst() && cursor.isLast()){
            event = new Event(
                    Long.toString(cursor.getLong(0)),   // id
                    cursor.getString(1),                // name
                    cursor.getString(2),                // description
                    cursor.getString(3),                // story
                    cursor.getShort(4),                 // difficulty
                    cursor.getString(5),                // how-to
                    cursor.getShort(6)                  // type
            );
        }

        return event;
    }

    @Override
    public boolean insertObject(Object object, SQLiteDatabase db) {

        Event event = (Event) object;

        ContentValues values = new ContentValues();
        values.put(EventContract.COLUMN_EVENT_NAME, event.getName());
        values.put(EventContract.COLUMN_EVENT_DESCRIPTION, event.getDescription());
        values.put(EventContract.COLUMN_EVENT_STORY, event.getStory());
        values.put(EventContract.COLUMN_EVENT_DIFFICULTY, event.getDifficulty());
        values.put(EventContract.COLUMN_EVENT_HOW_TO, event.getHowTo());
        values.put(EventContract.COLUMN_EVENT_TYPE, event.getType());

        long id = db.insert(EventContract.TABLE_NAME, null, values);

        // return if an error has occurred or not
        return id != -1;
    }

    @Override
    public boolean updateObject(Object object, SQLiteDatabase db) {

        Event event = (Event) object;


        String selection = EventContract.COLUMN_EVENT_NAME  + " = ?";
        String[] selectionArgs = { event.getName() };

        ContentValues values = new ContentValues();
        values.put(EventContract.COLUMN_EVENT_NAME, event.getName());
        values.put(EventContract.COLUMN_EVENT_DESCRIPTION, event.getDescription());
        values.put(EventContract.COLUMN_EVENT_STORY, event.getStory());
        values.put(EventContract.COLUMN_EVENT_DIFFICULTY, event.getDifficulty());
        values.put(EventContract.COLUMN_EVENT_HOW_TO, event.getHowTo());
        values.put(EventContract.COLUMN_EVENT_TYPE, event.getType());


        int count = db.update(EventContract.TABLE_NAME, values, selection, selectionArgs);

        // return is the row has been updated
        return count != 0;
    }

    @Override
    public boolean deleteObject(Object object, SQLiteDatabase db) {

        Event event = (Event) object;


        String selection = EventContract.COLUMN_EVENT_NAME  + " = ?";
        String[] selectionArgs = { event.getName() };

        int success = db.delete(EventContract.TABLE_NAME, selection, selectionArgs);

        // return if the delete was successful or not
        return success != 0;
    }
}
