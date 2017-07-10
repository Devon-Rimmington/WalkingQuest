using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class NativeController : MonoBehaviour {

	AndroidJavaClass nativeAccessor;
	long steps;

	// Use this for initialization
	void Start () {
		// This is the test for starting an android service from a unity game

		DontDestroyOnLoad (gameObject);

		// Debug.Log ("Starting service");

		AndroidJavaClass unityClass = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
		AndroidJavaObject unityActivity = unityClass.GetStatic<AndroidJavaObject>("currentActivity");
		nativeAccessor = new AndroidJavaClass("walkingquest.kinematicworld.library.services.UnityActivityPlayerHolder");
		nativeAccessor.CallStatic("StartServices", unityActivity);

		Debug.Log(nativeAccessor.CallStatic<long> ("getTotalSteps"));
		steps = nativeAccessor.CallStatic<long> ("getTotalSteps");
	}

	// Update is called once per frame
	void Update () {

		if (steps != nativeAccessor.CallStatic<long> ("getTotalSteps")) {
			steps = nativeAccessor.CallStatic<long> ("getTotalSteps");
			// Debug.Log(nativeAccessor.CallStatic<long> ("getSteps"));
		}
		//Debug.Log(customClass.CallStatic<long> ("getSteps"));
	}

	void OnDestroy(){
		nativeAccessor.CallStatic ("Unbind");
	}

	/*####Getting the total and trip step count#####*/
	public long getTotalSteps(){
		return nativeAccessor.CallStatic<long> ("getTotalSteps");
	}

	public long getTripSteps(){
		return nativeAccessor.CallStatic<long> ("getTripSteps");
	}
	/*###############################################*/

	// get if a miniquest is available
	public bool isMiniQuestAvailable(){
		return nativeAccessor.CallStatic<bool> ("getMiniQuestAvailable");
	}

	// get the current miniquestId
	public long getMiniQuestId(){
		if (isMiniQuestAvailable())
			return nativeAccessor.CallStatic<long> ("getMiniQuestId");
		return -1;
	}

	// get the number of steps completed towards the current miniquest
	public long getCompletedSteps(){
		if (isMiniQuestAvailable())
			return nativeAccessor.CallStatic<long> ("getStepsCompleted");
		return -1;
	}

	// get the number of steps that is required to complete the miniquest
	public long getRequiredSteps(){
		if (isMiniQuestAvailable())
			return nativeAccessor.CallStatic<long> ("getStepsRequired");
		return -1;
	}
		
	// get the last completed miniquest
	public long getLastMiniQuestCompleted(){
		return nativeAccessor.CallStatic<long> ("getLastMiniQuestCompleted");
	}

	// set the players current miniquest
	public bool setCurrentMiniQuest(long miniquestID, long stepsCompleted, long StepsRequired){
		nativeAccessor.CallStatic<bool> ("setMiniQuest", new object[] {
			miniquestID, stepsCompleted, StepsRequired
		});
		return true;
	}

	// get the number of activeEvents that a player can do
	public long getNumberOfActiveEvents(){
		return nativeAccessor.CallStatic<long> ("getNumberOfEvents");
	}

	// reseting the trip step count
	public void resetTripSteps(){
		nativeAccessor.CallStatic<bool> ("resetTripSteps");
	}
}
