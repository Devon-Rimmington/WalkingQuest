package walkingquest.kinematicworld.library.database.objects;

/**
 * Created by Devon on 6/28/2017.
 */

public class NativeData {

    private String userId;

    private long characterId,
        availableEventCount,
        miniquestId,
        miniquestStepRequired,
        miniquestStepCompleted,
        miniquestStartTime,
        userTotalStepCount,
        tripCounterSteps;

    // todo implement this value across the entire database section
    private long lastMiniQuestCompleted = 0;

    private boolean availableMiniQuest;

    public NativeData(String userId,
                      long characterId,
                      long availableEventCount,
                      long miniquestId,
                      long miniquestStepRequired,
                      long miniquestStepCompleted,
                      long miniquestStartTime,
                      long userTotalStepCount,
                      long tripCounterSteps,
                      boolean availableMiniQuest){

        this.userId = userId;
        this.characterId = characterId;
        this.availableEventCount = availableEventCount;
        this.miniquestId = miniquestId;
        this.miniquestStepRequired = miniquestStepRequired;
        this.miniquestStepCompleted = miniquestStepCompleted;
        this.miniquestStartTime = miniquestStartTime;
        this.userTotalStepCount = userTotalStepCount;
        this.tripCounterSteps = tripCounterSteps;
        this.availableMiniQuest = availableMiniQuest;

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getCharacterId() {
        return characterId;
    }

    public void setCharacterId(long characterId) {
        this.characterId = characterId;
    }

    public long getAvailableEventCount() {
        return availableEventCount;
    }

    public void setAvailableEventCount(long availableEventCount) {
        this.availableEventCount = availableEventCount;
    }

    public long getMiniquestId() {
        return miniquestId;
    }

    public void setMiniquestId(long miniquestId) {
        this.miniquestId = miniquestId;
    }

    public long getMiniquestStepRequired() {
        return miniquestStepRequired;
    }

    public void setMiniquestStepRequired(long miniquestStepRequired) {
        this.miniquestStepRequired = miniquestStepRequired;
    }

    public long getMiniquestStepCompleted() {
        return miniquestStepCompleted;
    }

    public void setMiniquestStepCompleted(long miniquestStepCompleted) {
        this.miniquestStepCompleted = miniquestStepCompleted;
    }

    public long getMiniquestStartTime() {
        return miniquestStartTime;
    }

    public void setMiniquestStartTime(long miniquestStartTime) {
        this.miniquestStartTime = miniquestStartTime;
    }

    public long getUserTotalStepCount() {
        return userTotalStepCount;
    }

    public void setUserTotalStepCount(long userTotalStepCount) {
        this.userTotalStepCount = userTotalStepCount;
    }

    public long getTripCounterSteps() {
        return tripCounterSteps;
    }

    public void setTripCounterSteps(long tripCounterSteps) {
        this.tripCounterSteps = tripCounterSteps;
    }

    public boolean isAvailableMiniQuest() {
        return availableMiniQuest;
    }

    public void setAvailableMiniQuest(boolean availableMiniQuest) {
        this.availableMiniQuest = availableMiniQuest;
    }

    public long getLastMiniQuestCompleted() {
        return lastMiniQuestCompleted;
    }

    public void setLastMiniQuestCompleted(long lastMiniQuestCompleted) {
        this.lastMiniQuestCompleted = lastMiniQuestCompleted;
    }

    public String toString(){
        return "UserID: " + this.userId + "\nCharcterID: " + this.characterId + "\nTrip Counter " + this.getTripCounterSteps() + "\nTotal Step Count" + this.getUserTotalStepCount()
                + "\nMiniQuestID: " + this.miniquestId + "\nMiniQuest Start Time " + this.miniquestStartTime
                + "\nNumber Of Active Events " + this.availableEventCount + "\nMiniQuest Available " + this.availableMiniQuest;
    }
}
