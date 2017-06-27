package walkingquest.kinematicworld.library.database.objects;

/**
 * Created by Devon on 6/23/2017.
 */

public class MiniQuest extends BaseObject{

    private String name, description, story, successStory, failStory;
    private long requiredSteps, completedSteps;
    private boolean active;

    public MiniQuest(){}

    public MiniQuest(String name,
                     String description,
                     String story,
                     String successStory,
                     String failStory,
                     long requiredSteps,
                     long completedSteps,
                     boolean active){
        this.name = name;
        this.description = description;
        this.story = story;
        this.successStory = successStory;
        this.failStory = failStory;
        this.requiredSteps = requiredSteps;
        this.completedSteps = completedSteps;
        this.active = active;
    }

    public MiniQuest(long _id,
                     String name,
                     String description,
                     String story,
                     String successStory,
                     String failStory,
                     long requiredSteps,
                     long completedSteps,
                     boolean active){
        this._id = _id;
        this.name = name;
        this.description = description;
        this.story = story;
        this.successStory = successStory;
        this.failStory = failStory;
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

    // Other code

    public boolean isComplete(){
        return requiredSteps <= completedSteps;
    }
}
