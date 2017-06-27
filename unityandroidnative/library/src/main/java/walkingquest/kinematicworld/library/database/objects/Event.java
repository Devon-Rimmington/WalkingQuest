package walkingquest.kinematicworld.library.database.objects;

/**
 * Created by Devon on 6/23/2017.
 */

public class Event extends BaseObject{

    private String name, description, story, successStory, failStory;
    private short eventType;
    private boolean active;

    public Event(){}

    public Event(String name,
                 String description,
                 String story,
                 String successStory,
                 String failStory,
                 short eventType,
                 boolean active){

        this.name = name;
        this.description = description;
        this.story = story;
        this.successStory = successStory;
        this.failStory = failStory;
        this.eventType = eventType;
        this.active = active;
    }

    public Event(long _id,
                 String name,
                 String description,
                 String story,
                 String successStory,
                 String failStory,
                 short eventType,
                 boolean active){

        this._id = _id;
        this.name = name;
        this.description = description;
        this.story = story;
        this.successStory = successStory;
        this.failStory = failStory;
        this.eventType = eventType;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getSuccessStory() {
        return successStory;
    }

    public void setSuccessStory(String successStory) {
        this.successStory = successStory;
    }

    public String getFailStory() {
        return failStory;
    }

    public void setFailStory(String failStory) {
        this.failStory = failStory;
    }

    public short getEventType() {
        return eventType;
    }

    public void setEventType(short eventType) {
        this.eventType = eventType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
