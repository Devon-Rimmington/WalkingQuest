using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class NativeController : MonoBehaviour {

	AndroidJavaClass nativeAccessor;
	long steps;

	// Use this for initialization
	void Start () {
		// This is the test for starting an android service from a unity game

		Debug.Log ("Starting service");

		AndroidJavaClass unityClass = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
		AndroidJavaObject unityActivity = unityClass.GetStatic<AndroidJavaObject>("currentActivity");
		nativeAccessor = new AndroidJavaClass("walkingquest.kinematicworld.library.services.UnityActivityPlayerHolder");
		nativeAccessor.CallStatic("StartServices", unityActivity);

		Debug.Log(nativeAccessor.CallStatic<long> ("getSteps"));
		steps = nativeAccessor.CallStatic<long> ("getSteps");
	}

	// Update is called once per frame
	void Update () {

		if (steps != nativeAccessor.CallStatic<long> ("getSteps")) {
			steps = nativeAccessor.CallStatic<long> ("getSteps");
			Debug.Log(nativeAccessor.CallStatic<long> ("getSteps"));
		}
		//Debug.Log(customClass.CallStatic<long> ("getSteps"));
	}

	void OnDestroy(){
		nativeAccessor.CallStatic ("Unbind");
	}
}
