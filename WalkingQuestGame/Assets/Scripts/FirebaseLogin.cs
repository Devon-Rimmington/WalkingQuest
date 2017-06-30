using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Firebase;
using Firebase.Auth;
using Firebase.Database;
using Firebase.Unity.Editor;
using UnityEngine.UI;

public class FirebaseLogin : MonoBehaviour {

	private string databaseURL = "https://walkingquest-fa207.firebaseio.com/";
	// P12FileName = "WalkingQuest-9a8822988291.p12",
	// servieAccountEmail = "walkingquestuser@walkingquest-fa207.iam.gserviceaccount.com",
	// secret = "notasecret";

	DatabaseReference reference;

	// UI elements
	public InputField email, password, passwordConfirm;
	public Toggle createAccount;
	public Button contine;
	public Text warnings;

	// group of buttons that will animate when the player toggles create new account
	public GameObject buttonGroup;

	// public string email = "devonrimmington@gmail.com", password = "password";

	public bool createAccountToggle = false,
		validAccountCreation = true;

	// Use this for initialization
	void Start () {

		// starting the animations
		((Animator)passwordConfirm.GetComponentsInParent<Animator> ()[0]).SetBool ("slideIn", createAccountToggle);
		((Animator)buttonGroup.GetComponents<Animator> () [0]).SetBool ("slideIn", createAccountToggle);

		FirebaseApp.DefaultInstance.SetEditorDatabaseUrl (databaseURL);
		reference = FirebaseDatabase.DefaultInstance.RootReference;

		// signup/login with basic account
		// BasicAuthentication ();
	}

	// Update is called once per frame
	void Update () {


	}

	public void BasicAuthentication(){

		// temp user information
		// string _email = "devonrimmington@gmail.com", _password = "password";

		Firebase.Auth.FirebaseAuth auth = Firebase.Auth.FirebaseAuth.DefaultInstance;

		if (auth.CurrentUser == null) {

			if (createAccountToggle) { 							// if the player wants to create an account
				CreateBasicAccount (email.text, password.text);
			} else { 											// if the player already has an account
				LoginBasicAccount (email.text, password.text);
			}
		} else {

			// this is a test
			Test test = new Test ("201");
			string jsonTest = JsonUtility.ToJson (test);
			Debug.Log(jsonTest);
			reference.Child ("users").Child(auth.CurrentUser.UserId).SetRawJsonValueAsync (jsonTest);
		}

	}

	// if the player would like to make a new account to signin with
	private void CreateBasicAccount(string email, string password){

		Firebase.Auth.FirebaseAuth auth = Firebase.Auth.FirebaseAuth.DefaultInstance;

		warnings.text = "";

		// validate all the fields for the new account
		if ((validateEmail() && validatePassword() && validatePasswordConfirm())) {

			// if the user has entered in valid information attempt to create an account for them

			auth.CreateUserWithEmailAndPasswordAsync (email, password).ContinueWith ((System.Threading.Tasks.Task<FirebaseUser> task) => {
				if (task.IsCanceled) {
					Debug.LogError ("create user password async canceled");
					return;
				} else if (task.IsFaulted) {
					Debug.LogError ("error on creating account " + task.Exception);
					return;
				}

				// if the account creation has gone through
				Firebase.Auth.FirebaseUser newUser = task.Result;
				Debug.LogFormat ("Firebase user created successfully: {0} ({1})",
					newUser.DisplayName, newUser.UserId);

				// save the players profile and other information

				// this is a test
				Test test = new Test ("203");
				string jsonTest = JsonUtility.ToJson (test);
				Debug.Log (jsonTest);
				reference.Child ("users").Child (newUser.UserId).SetRawJsonValueAsync (jsonTest);
			});
		}
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
	public void SignOutBasic(){
		Firebase.Auth.FirebaseAuth auth = Firebase.Auth.FirebaseAuth.DefaultInstance;
		auth.SignOut ();
	}

	// changes the account creation boolean when the toggle switch is 'flicked' and onToggleChanged is invoked
	public void toggleAccountCreation(){
		createAccountToggle = !createAccountToggle;
		passwordConfirm.enabled = createAccountToggle;
		((Animator)passwordConfirm.GetComponentsInParent<Animator> ()[0]).SetBool ("slideIn", createAccountToggle);
		((Animator)buttonGroup.GetComponents<Animator> () [0]).SetBool ("slideIn", createAccountToggle);
	}

	// validate that the email meets all the rules for a proper email
	public bool validateEmail(){

		// need check for if this is left blank

		string _email = email.text;

		if (!(_email.Length > 3 && _email.Contains ("@") && (_email.Contains (".com") || _email.Contains (".ca")))) {
			warnings.text += "Emails must be atleast 4 characters long and must contain @ and be a .com or .ca\n";
			return false;
		}		
		// if there isn't a problem
		return true;
	}

	// validate that the password meets the rules for being a good password
	public bool validatePassword() {

		// need check for if this is left blank

		string _password = password.text;

		if (_password.Length >= 8) {
			warnings.text += "passwords must be 8 characters or longer\n";
			return false;
		}		
		// if there isn't a problem
		return true;
	}
		
	// makes sure that the user is aware of what their password is by checking it againts a second request for the password
	public bool validatePasswordConfirm() {

		// need check for if this is left blank

		if (password.text.Equals(passwordConfirm.text)) {
			warnings.text += "passwords do not match\n";
			return false;
		}		
		// if there isn't a problem
		return true;
	}

}
