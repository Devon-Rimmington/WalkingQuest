package walkingquest.kinematicworld.library.database.sql;


import walkingquest.kinematicworld.library.database.contracts.EventContract;
import walkingquest.kinematicworld.library.database.contracts.MiniQuestContract;
import walkingquest.kinematicworld.library.database.contracts.StepLogContract;

/**
 * Created by Devon on 6/17/2017.
 */

public class SQLTables {


    // this is the create and destroy commands for the steplog table
    public static class StepLog {
        public static final String CREATE_TABLE_STEP_LOG =
                "CREATE TABLE " + StepLogContract.TABLE_NAME + " (" +
                        StepLogContract._ID + " INTEGER PRIMARY KEY, " +
                        StepLogContract.COLUMN_NAME_DATE + " DATE)";

        public static final String DELETE_TABLE_STEP_LOG =
                "DROP TABLE IF EXISTS " + StepLogContract.TABLE_NAME;
    }

    // this is the create and destroy commands for the miniquest table
    public static class MiniQuest{
        public static final String CREATE_TABLE_MINIQUESTS =
                "CREATE TABLE " + MiniQuestContract.TABLE_NAME + " (" +
                        MiniQuestContract._ID + " INTEGER PRIMARY KEY, " +
                        MiniQuestContract.COLUMN_MINIQUEST_NAME + " VARCHAR(32), " +
                        MiniQuestContract.COLUMN_MINIQUEST_DESCRIPTION + " VARCHAR(128), " +
                        MiniQuestContract.COLUMN_MINIQUEST_STORY + " VARCHAR(512), " +
                        MiniQuestContract.COLUMN_MINIQUEST_DIFFICULTY + " INT," +
                        MiniQuestContract.COLUMN_MINIQUEST_REQUIRED_STEPS + " INT, " +
                        MiniQuestContract.COLUMN_MINIQUEST_COMPLETED_STEPS + " INT, " +
                        MiniQuestContract.COLUMN_MINIQUEST_ACTIVE + " BIT)";

        public static final String DELETE_TABLE_MINIQUESTS =
                "DROP TABLE IF EXISTS " + MiniQuestContract.TABLE_NAME;
    }


    // this is the create and destroy commands for the events table
    public static class Event{
        public static final String CREATE_TABLE_EVENTS =
                "CREATE TABLE " + EventContract.TABLE_NAME + " (" +
                        EventContract._ID + " INTEGER PRIMARY KEY ," +
                        EventContract.COLUMN_EVENT_NAME + " VARCHAR(32), " +
                        EventContract.COLUMN_EVENT_DESCRIPTION + " VARCHAR(128), " +
                        EventContract.COLUMN_EVENT_STORY + " VARCHAR(512), " +
                        EventContract.COLUMN_EVENT_DIFFICULTY + " INT," +
                        EventContract.COLUMN_EVENT_HOW_TO + " VARCHAR(256), " +
                        EventContract.COLUMN_EVENT_TYPE + " INT)";


        public static final String DELETE_TABLE_EVENTS =
                "DROP TABLE IF EXISTS " + EventContract.TABLE_NAME;
    }

}
