using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Firebase;
using Firebase.Auth;
using Firebase.Database;
using Firebase.Unity.Editor;

public class FireBaseController : MonoBehaviour {

	private string databaseURL = "https://walkingquest-fa207.firebaseio.com/",
	P12FileName = "WalkingQuest-9a8822988291.p12",
	servieAccountEmail = "walkingquestuser@walkingquest-fa207.iam.gserviceaccount.com",
	secret = "notasecret";

	DatabaseReference reference;

	// public string userId = "0";

	// todo remove this

	public string email = "devonrimmington@gmail.com", password = "password";

	public bool createAccount = true;

	// Use this for initialization
	void Start () {

		FirebaseApp.DefaultInstance.SetEditorDatabaseUrl ("https://walkingquest-fa207.firebaseio.com/");
		reference = FirebaseDatabase.DefaultInstance.RootReference;

		// signup/login with basic account
		BasicAuthentication (createAccount, email, password);
	}
	
	// Update is called once per frame
	void Update () {
		

	}
		
	private void BasicAuthentication(bool _createAccount, string email, string password){

		// temp user information
		// string _email = "devonrimmington@gmail.com", _password = "password";

		Firebase.Auth.FirebaseAuth auth = Firebase.Auth.FirebaseAuth.DefaultInstance;

		if (auth.CurrentUser == null) {

			if (_createAccount) { 							// if the player wants to create an account
				CreateBasicAccount (email, password);
			} else { 											// if the player already has an account
				LoginBasicAccount (email, password);
			}
		} else {
			// Debug.Log ("currently signed in");


			// this is a test
			Test test = new Test ("201");
			string jsonTest = JsonUtility.ToJson (test);
			Debug.Log(jsonTest);
			reference.Child ("users").Child(auth.CurrentUser.UserId).SetRawJsonValueAsync (jsonTest);

			// SignOutBasic ();
		}

	}

	// if the player would like to make a new account to signin with
	private void CreateBasicAccount(string email, string password){

		Firebase.Auth.FirebaseAuth auth = Firebase.Auth.FirebaseAuth.DefaultInstance;

		auth.CreateUserWithEmailAndPasswordAsync (email, password).ContinueWith ((System.Threading.Tasks.Task<FirebaseUser> task) => {
			if(task.IsCanceled){
				Debug.LogError("create user password async canceled");
				return;
			}else if(task.IsFaulted){
				Debug.LogError("error on creating account "+ task.Exception);
				return;
			}

			// if the account creation has gone through
			Firebase.Auth.FirebaseUser newUser = task.Result;
			Debug.LogFormat("Firebase user created successfully: {0} ({1})",
				newUser.DisplayName, newUser.UserId);

			// save the players profile and other information

			// this is a test
			Test test = new Test ("203");
			string jsonTest = JsonUtility.ToJson (test);
			Debug.Log(jsonTest);
			reference.Child ("users").Child(newUser.UserId).SetRawJsonValueAsync (jsonTest);
		});
	}

	// if the player has an account already allow them to signin with it
	private void LoginBasicAccount(string email, string password){
	
		Firebase.Auth.FirebaseAuth auth = Firebase.Auth.FirebaseAuth.DefaultInstance;

		auth.SignInWithEmailAndPasswordAsync(email, password).ContinueWith(task => {
			if (task.IsCanceled) {
				Debug.LogError("SignInWithEmailAndPasswordAsync was canceled.");
				return;
			}
			if (task.IsFaulted) {
				Debug.LogError("SignInWithEmailAndPasswordAsync encountered an error: " + task.Exception);
				return;
			}

			Firebase.Auth.FirebaseUser newUser = task.Result;
			Debug.LogFormat("User signed in successfully: {0} ({1})",
				newUser.DisplayName, newUser.UserId);

			// this is a test
			Test test = new Test ("200");
			string jsonTest = JsonUtility.ToJson (test);

			Debug.Log(jsonTest);

			reference.Child ("users").Child(newUser.UserId).SetRawJsonValueAsync (jsonTest);
		});	
	}

	// sign a user out of their account
	private void SignOutBasic(){
		Firebase.Auth.FirebaseAuth auth = Firebase.Auth.FirebaseAuth.DefaultInstance;
		auth.SignOut ();
	}
}


// this is a demo 
public class Test{

	public string value = "1", name = "nan";
	public int age = 0;

	public Test(){
	}

	public Test(string i){
		value = i;
		name = "definatly not nan";
		age = 23;
	}
}
