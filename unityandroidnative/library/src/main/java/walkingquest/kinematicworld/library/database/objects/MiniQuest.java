package walkingquest.kinematicworld.library.database.objects;

/**
 * Created by Devon on 6/18/2017.
 */

public class MiniQuest extends BaseObject {

    private String name,
            description,
            story;
    private short difficulty;
    private long requiredSteps, completedSteps;
    private boolean active;

    public MiniQuest(){

        this._ID = null;
        this.name = null;
        this.description = null;
        this.story = null;
        this.difficulty = -1;
        this.requiredSteps = -1;
        this.completedSteps = -1;
        this.active = false;
    }

    public MiniQuest(String name,
                     String description,
                     String story,
                     short difficulty,
                     long requiredSteps,
                     long completedSteps,
                     boolean active){

        this._ID = null;
        this.name = name;
        this.description = description;
        this.story = story;
        this.difficulty = difficulty;
        this.requiredSteps = requiredSteps;
        this.completedSteps = completedSteps;
        this.active = active;
    }

    public MiniQuest(String _ID,
                     String name,
                     String description,
                     String story,
                     short difficulty,
                     long requiredSteps,
                     long completedSteps,
                     boolean active){

        this._ID = _ID;
        this.name = name;
        this.description = description;
        this.story = story;
        this.difficulty = difficulty;
        this.requiredSteps = requiredSteps;
        this.completedSteps = completedSteps;
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

    public short getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(short difficulty) {
        this.difficulty = difficulty;
    }

    public long getRequiredSteps() {
        return requiredSteps;
    }

    public void setRequiredSteps(long requiredSteps) {
        this.requiredSteps = requiredSteps;
    }

    public long getCompletedSteps() {
        return completedSteps;
    }

    public void setCompletedSteps(long completedSteps) {
        this.completedSteps = completedSteps;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
