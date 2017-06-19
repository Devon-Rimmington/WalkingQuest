package walkingquest.kinematicworld.library.database.contracts;

import android.provider.BaseColumns;

/**
 * Created by Devon on 6/18/2017.
 */

public final class MiniQuestContract implements BaseColumns{

    public static final String TABLE_NAME = "miniquests";
    public static final String COLUMN_MINIQUEST_NAME = "name"; // the name of the miniquest
    public static final String COLUMN_MINIQUEST_DESCRIPTION = "description"; // a tldr; of the story
    public static final String COLUMN_MINIQUEST_STORY = "story"; // the story that goes along with the miniquest
    public static final String COLUMN_MINIQUEST_DIFFICULTY = "difficulty"; // the difficulty for the quest (this could determine the reward)
    public static final String COLUMN_MINIQUEST_REQUIRED_STEPS = "required_steps"; // the number of steps the player must complete before the quest has been completed
    public static final String COLUMN_MINIQUEST_COMPLETED_STEPS = "completed_steps"; // the number of steps the player has completed since starting the quest
    public static final String COLUMN_MINIQUEST_ACTIVE = "active"; // indicates if this is the quest that is currently having steps accumulated towards completion

}
