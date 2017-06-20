package walkingquest.kinematicworld.library.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import walkingquest.kinematicworld.library.database.databaseHandlers.DatabaseHandler;
import walkingquest.kinematicworld.library.database.debug.FillWithSampleData;
import walkingquest.kinematicworld.library.database.sql.SQLTables;

/**
 * Created by Devon on 6/16/2017.
 */

public class DatabaseAccessor extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "walkingquest.db";
    public DatabaseHandler databaseHandler;

    public DatabaseAccessor(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        databaseHandler = null;
    }

    public DatabaseAccessor(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQLTables.StepLog.CREATE_TABLE_STEP_LOG);
        db.execSQL(SQLTables.MiniQuest.CREATE_TABLE_MINIQUESTS);
        db.execSQL(SQLTables.Event.CREATE_TABLE_EVENTS);

        // this will fill the database with some extremely simple sample data
        // todo make sample data more meaningful
        // FillWithSampleData.SampleData(db, databaseHandler);
        Log.d("Unity", "Created sample data");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(SQLTables.StepLog.DELETE_TABLE_STEP_LOG);
        db.execSQL(SQLTables.MiniQuest.DELETE_TABLE_MINIQUESTS);
        db.execSQL(SQLTables.Event.DELETE_TABLE_EVENTS);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

    public void setDatabaseHandler(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }
}
