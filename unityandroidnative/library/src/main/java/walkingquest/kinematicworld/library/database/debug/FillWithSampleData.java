package walkingquest.kinematicworld.library.database.debug;

import android.database.sqlite.SQLiteDatabase;

import walkingquest.kinematicworld.library.database.databaseHandlers.EventHandler;
import walkingquest.kinematicworld.library.database.databaseHandlers.MiniQuestHandler;
import walkingquest.kinematicworld.library.database.objects.Event;
import walkingquest.kinematicworld.library.database.objects.MiniQuest;

/**
 * Created by Devon on 6/19/2017.
 */

public class FillWithSampleData {


    public static MiniQuest[] miniQuests = {
            new MiniQuest("miniquest1", "miniquest #1", "this is the first miniquest", (short)1, 500, 0, false),
            new MiniQuest("miniquest2", "miniquest #2", "this is the second miniquest", (short)1, 500, 0, false),
            new MiniQuest("miniquest3", "miniquest #3", "this is the third miniquest", (short)1, 500, 0, false)
    };

    public static Event[] events = {
            new Event("event1", "event #1", "this is the first event", (short)1, "explanation of how to do the event", (short)0),
            new Event("event2", "event #2", "this is the second event", (short)1, "explanation of how to do the event", (short)0),
            new Event("event3", "event #3", "this is the third event", (short)1, "explanation of how to do the event", (short)0)
    };

    public static void SampleData(SQLiteDatabase db){

        MiniQuestHandler miniQuestHandler = new MiniQuestHandler();

        miniQuestHandler.insertObject(miniQuests[0], db);
        miniQuestHandler.insertObject(miniQuests[1], db);
        miniQuestHandler.insertObject(miniQuests[2], db);

        EventHandler eventHandler = new EventHandler();

        eventHandler.insertObject(events[0], db);
        eventHandler.insertObject(events[1], db);
        eventHandler.insertObject(events[2], db);
    }


}
