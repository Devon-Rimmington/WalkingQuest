package walkingquest.kinematicworld.library.database.contracts;

import android.provider.BaseColumns;

/**
 * Created by Devon on 6/28/2017.
 */

public class NativeDataContract implements BaseColumns {

    public static final String TABLE_NAME = "native_data";
    public static final String COLUMN_USER_ID = "user_id";                                              // the users id when they sign in
    public static final String COLUMN_CHARACTER_ID = "active_character";                                // the id of the users active character

    public static final String COLUMN_ACTIVE_EVENT_AVAILABLE_COUNT = "available_active_events";         // the number of active events that are available to the user

    public static final String COLUMN_MINI_QUEST_AVAILABLE = "available_mini_quest";                    // indicates whether or not a miniquest is available
    public static final String COLUMN_MINI_QUEST_ID = "active_mini_quest_id";                           // the id of the miniquest that has been given to the user
    public static final String COLUMN_MINI_QUEST_STEPS_REQUIRED = "active_mini_quest_steps_required";   // the number of steps req to complete the quest
    public static final String COLUMN_MINI_QUEST_STEPS_COMPLETED = "active_mini_quest_steps_completed"; // the number of steps completed towards the miniquest
    public static final String COLUMN_MINI_QUEST_START_TIME = "active_mini_quest_start_time";           // the time that the player accepts the miniquest

    public static final String COLUMN_TOTAL_USER_STEPS = "user_total_steps";                            // the total number of steps that the user has taken over the lifetime of the app
    public static final String COLUMN_TRIP_COUNTER_STEPS = "user_trip_counter_steps";                   // the number of steps taken over a trip (like trip counter in car)

}
