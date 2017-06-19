package walkingquest.kinematicworld.library.database.objects;

/**
 * Created by Devon on 6/18/2017.
 */

public class Event extends BaseObject {

    private String name, description, story, howTo;
    private short difficulty, type;

    public Event(){
        this._ID = null;
        this.name = null;
        this.description = null;
        this.story = null;
        this.difficulty = -1;
        this.howTo = null;
        this.type = -1;
    }

    public Event(String name, String description, String story, short difficulty, String howTo, short type){
        this._ID = null;
        this.name = name;
        this.description = description;
        this.story = story;
        this.difficulty = difficulty;
        this.howTo = howTo;
        this.type = type;
    }

    public Event(String _ID, String name, String description, String story, short difficulty, String howTo, short type){
        this._ID = _ID;
        this.name = name;
        this.description = description;
        this.story = story;
        this.difficulty = difficulty;
        this.howTo = howTo;
        this.type = type;
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

    public String getHowTo() {
        return howTo;
    }

    public void setHowTo(String howTo) {
        this.howTo = howTo;
    }

    public short getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(short difficulty) {
        this.difficulty = difficulty;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }
}
