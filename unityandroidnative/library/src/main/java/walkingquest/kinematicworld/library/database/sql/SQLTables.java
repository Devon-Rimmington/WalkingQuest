package walkingquest.kinematicworld.library.database.sql;

import walkingquest.kinematicworld.library.database.contracts.NativeDataContract;

/**
 * Created by Devon on 6/17/2017.
 */

public class SQLTables {

    public static class NativeDataTable{
        public static final String CREATE_TABLE_NATIVE_DATA =
                "CREATE TABLE " + NativeDataContract.TABLE_NAME + " (" +
                        NativeDataContract._ID + " INTEGER PRIMARY KEY, " +
                        NativeDataContract.COLUMN_USER_ID + " VARCHAR(64), " +
                        NativeDataContract.COLUMN_CHARACTER_ID + " INT8, " +
                        NativeDataContract.COLUMN_ACTIVE_EVENT_AVAILABLE_COUNT + " INT, " +
                        NativeDataContract.COLUMN_MINI_QUEST_AVAILABLE + " INT, " +
                        NativeDataContract.COLUMN_MINI_QUEST_ID + " INT, " +
                        NativeDataContract.COLUMN_MINI_QUEST_STEPS_REQUIRED + " INT8, " +
                        NativeDataContract.COLUMN_MINI_QUEST_STEPS_COMPLETED + " INT8, " +
                        NativeDataContract.COLUMN_MINI_QUEST_START_TIME + " INT8, " +
                        NativeDataContract.COLUMN_TOTAL_USER_STEPS + " INT8, " +
                        NativeDataContract.COLUMN_TRIP_COUNTER_STEPS + " INT8 );";

        public static final String DELETE_TABLE_NATIVE_DATA =
                "DROP TABLE IF EXISTS " + NativeDataContract.TABLE_NAME;
    }
}
