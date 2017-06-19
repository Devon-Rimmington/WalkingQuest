package walkingquest.kinematicworld.library.database.contracts;

import android.provider.BaseColumns;

/**
 * Created by Devon on 6/18/2017.
 */

public class EventContract {

    public EventContract(){};

    public static class Event implements BaseColumns{

        public static final String TABLE_NAME = "events";
        public static final String COLUMN_EVENT_NAME = "name"; // the name of the event
        public static final String COLUMN_EVENT_DESCRIPTION = "description"; // a summary of what the event is
        public static final String COLUMN_EVENT_STORY = "story"; // the story (if any) related to the event
        public static final String COLUMN_EVENT_DIFFICULTY = "difficulty"; // how difficult the event is to the user
        public static final String COLUMN_EVENT_HOW_TO = "how_to"; // an explanation of how the player plays the event
        public static final String COLUMN_EVENT_TYPE = "type"; // the type of event that the game will load up for the player to play

    }

    // todo remove these and add a new class that will serve as the interface for the Events
    public static class EventCommands{


    }


}
