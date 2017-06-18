package walkingquest.kinematicworld.library.database.sql;


import walkingquest.kinematicworld.library.database.contracts.StepLogContract;

/**
 * Created by Devon on 6/17/2017.
 */

public class SQLTables {

    public static final String CREATE_TABLE_STEP_LOG =
            "CREATE TABLE " + StepLogContract.StepEntry.TABLE_NAME + " ("+
                    StepLogContract.StepEntry._ID + " INTEGER PRIMARY KEY, " +
                    StepLogContract.StepEntry.COLUMN_NAME_DATE + " DATE)";

    public static final String DELETE_TABLE_STEP_LOG =
            "DROP TABLE IF EXISTS " + StepLogContract.StepEntry.TABLE_NAME;

}
