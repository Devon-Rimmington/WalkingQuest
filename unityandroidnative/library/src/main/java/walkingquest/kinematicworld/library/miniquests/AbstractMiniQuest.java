package walkingquest.kinematicworld.library.miniquests;

/**
 * Created by Devon on 6/16/2017.
 */

public abstract class AbstractMiniQuest {

    public abstract String getTitle();

    public abstract String getDescription();

    public abstract void addSteps(long step);

    public abstract long getStepRequirement();

}
