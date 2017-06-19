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
                "CREATE TABLE " + StepLogContract.StepEntry.TABLE_NAME + " (" +
                        StepLogContract.StepEntry._ID + " INTEGER PRIMARY KEY, " +
                        StepLogContract.StepEntry.COLUMN_NAME_DATE + " DATE)";

        public static final String DELETE_TABLE_STEP_LOG =
                "DROP TABLE IF EXISTS " + StepLogContract.StepEntry.TABLE_NAME;
    }

    // this is the create and destroy commands for the miniquest table
    public static class MiniQuest{
        public static final String CREATE_TABLE_MINIQUESTS =
                "CREATE TABLE " + MiniQuestContract.MiniQuest.TABLE_NAME + " (" +
                        MiniQuestContract.MiniQuest._ID + " INTEGER PRIMARY KEY, " +
                        MiniQuestContract.MiniQuest.COLUMN_MINIQUEST_NAME + " VARCHAR(32), " +
                        MiniQuestContract.MiniQuest.COLUMN_MINIQUEST_DESCRIPTION + " VARCHAR(128), " +
                        MiniQuestContract.MiniQuest.COLUMN_MINIQUEST_STORY + " VARCHAR(512), " +
                        MiniQuestContract.MiniQuest.COLUMN_MINIQUEST_DIFFICULTY + " INT," +
                        MiniQuestContract.MiniQuest.COLUMN_MINIQUEST_REQUIRED_STEPS + " INT, " +
                        MiniQuestContract.MiniQuest.COLUMN_MINIQUEST_COMPLETED_STEPS + " INT, " +
                        MiniQuestContract.MiniQuest.COLUMN_MINIQUEST_ACTIVE + " BIT)";

        public static final String DELETE_TABLE_MINIQUESTS =
                "DROP TABLE IF EXITS " + MiniQuestContract.MiniQuest.TABLE_NAME;
    }


    // this is the create and destroy commands for the events table
    public static class Event{
        public static final String CREATE_TABLE_EVENTS =
                "CREATE TABLE " + EventContract.Event.TABLE_NAME + " (" +
                        EventContract.Event._ID + " INTEGER PRIMARY KEY ," +
                        EventContract.Event.COLUMN_EVENT_NAME + " VARCHAR(32), " +
                        EventContract.Event.COLUMN_EVENT_DESCRIPTION + " VARCHAR(128), " +
                        EventContract.Event.COLUMN_EVENT_STORY + " VARCHAR(512), " +
                        EventContract.Event.COLUMN_EVENT_DIFFICULTY + " INT," +
                        EventContract.Event.COLUMN_EVENT_HOW_TO + " VARCHAR(256), " +
                        EventContract.Event.COLUMN_EVENT_TYPE + " INT)";


        public static final String DELETE_TABLE_EVENTS =
                "DROP TABLE IF EVENTS " + EventContract.Event.TABLE_NAME;
    }

}
