package com.track.brachio.donationtracker.model.database;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.track.brachio.donationtracker.LoginActivity;
import com.track.brachio.donationtracker.RegistrationActivity;
import com.track.brachio.donationtracker.controller.PersistanceManager;
import com.track.brachio.donationtracker.model.User;
import com.track.brachio.donationtracker.model.UserType;
import com.track.brachio.donationtracker.model.singleton.AllUsers;
import com.track.brachio.donationtracker.model.singleton.CurrentUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Handler for User
 */
public class FirebaseUserHandler implements GoogleApiClient.OnConnectionFailedListener {
    private final String TAG = "FirebaseUserHandler";
    private User userCallback;
    private static GoogleApiClient mGoogleApiClient;

    /**
     * returns current user
     * @return current user
     */
    public FirebaseUser getCurrentUser(){
        FirebaseAuth currentInstance = FirebaseAuth.getInstance();
        return currentInstance.getCurrentUser();
    }

    /**
     * returns name of current user
     * @return name of current user
     */
    public String getCurrentUserName(){
        FirebaseAuth currentInstance = FirebaseAuth.getInstance();
        FirebaseUser user = currentInstance.getCurrentUser();
        user = Objects.requireNonNull(user);
        return user.getDisplayName();
    }

    /**
     * returns email of current user
     * @return email of current user
     */
    public String getCurrentUserEmail(){
        FirebaseAuth currentInstance = FirebaseAuth.getInstance();
        FirebaseUser user = currentInstance.getCurrentUser();
        assert user != null;
        return user.getEmail();
    }

    /**
     * returns whether or not they are verified
     * @return true or false
     */
    public boolean isCurrentUserEmailVerified(){
        FirebaseAuth currentInstance = FirebaseAuth.getInstance();
        FirebaseUser user = currentInstance.getCurrentUser();
        user = Objects.requireNonNull(user);
        return user.isEmailVerified();
    }

    /**
     * returns id of current user
     * @return id
     */
    public String getCurrentUserID(){
        FirebaseAuth currentInstance = FirebaseAuth.getInstance();
        FirebaseUser user = currentInstance.getCurrentUser();
        user = Objects.requireNonNull(user);
        return user.getUid();
    }


    //PROBABLY DELETE THIS
    /**
     * updates the users name
     * @param newUserName new name
     */
    /*
    public void updateUserName(String newUserName){
        FirebaseAuth currentInstance = FirebaseAuth.getInstance();
        FirebaseUser user = currentInstance.getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(newUserName)
                .build();

        Objects.requireNonNull(user).updateProfile(profileUpdates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "User profile updated.");
                    }
                });
    }
    */

    /**
     * updates email of current user
     * @param newEmail new email
     */
    public void updateEmail(String newEmail){
        FirebaseAuth currentInstance = FirebaseAuth.getInstance();
        FirebaseUser user = currentInstance.getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(newEmail)
                .build();

        Objects.requireNonNull(user).updateProfile(profileUpdates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "User email address updated.");
                    }
                });
    }

    /**
     * updates password of current user
     * @param newPassword new password
     */
    public void updatePassword(String newPassword){
        FirebaseAuth currentInstance = FirebaseAuth.getInstance();
        FirebaseUser user = currentInstance.getCurrentUser();
        user = Objects.requireNonNull(user);

        user.updatePassword(newPassword)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "User password updated.");
                    }
                });
    }

    /**
     * update user
     * @param appUser user to update
     */
    public Task updateUser(User appUser){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference doc = db.collection("users").document(appUser.getKey());
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("firstname", appUser.getFirstName());
        userMap.put("lastname", appUser.getLastName());
        userMap.put("email", appUser.getEmail());
        UserType currentUserType = appUser.getUserType();
        String stringCurrentUserType = currentUserType.name();
        userMap.put("usertype", stringCurrentUserType);
        return doc.set(userMap);


    }



    //use for registration page
    //will add user to Firebase, make this user CurrentUser for singleton

    /**
     * creates new user
     * @param appUser new user
     * @param password password of new user
     * @param registration type of user
     * @param activity current activity
     */
    //TODO create progress bar to be displayed
    public void createNewUser(User appUser, String password,
                              RegistrationActivity registration, Activity activity){
        // Write a message to the database
        FirebaseAuth auth = FirebaseAuth.getInstance();

        Map<String, Object> userMap = new HashMap<>();
        if(!appUser.isNull() && !password.isEmpty()) {
            userMap.put("firstname", appUser.getFirstName());
            userMap.put("lastname", appUser.getLastName());
            userMap.put("email", appUser.getEmail());
            UserType currentUserType = appUser.getUserType();
            String stringCurrentUserType = currentUserType.name();
            userMap.put("usertype", stringCurrentUserType);
            userMap.put("date created", appUser.getTimestamp());
                auth.createUserWithEmailAndPassword(appUser.getEmail(), password)
                        .addOnCompleteListener(task -> {
                            try {
                                //check if successful
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Registered");
                                    FirebaseFirestore db1 = FirebaseFirestore.getInstance();
                                    if (auth.getUid() != null) {
                                        db1.collection("users")
                                                .document(Objects.requireNonNull(
                                                        auth.getCurrentUser()).getUid())
                                                .set(userMap)
                                                .addOnSuccessListener(
                                                        aVoid -> Log.d(TAG, "Added extra data"));
                                        Intent intent =
                                                new Intent(activity, LoginActivity.class);
                                        registration.startActivity(intent);
                                    } else {
                                        Log.e(TAG, "Cannot access current user");
                                    }
                                }else{
                                    Log.e(TAG, "Not registered");
                                    Toast.makeText(activity,
                                            "User with email entered already exists.",
                                            Toast.LENGTH_LONG).show();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        });

        } else {
            Log.e(TAG, "Entered null value");
        }
    }

    //use for sign in
    //make sign in user the CurrentUser

    /**
     * signs in user
     * @param email email entered
     * @param password password entered
     * @param login login activity
     * @param activity current activity
     */
    public void signInUser(String email, String password, LoginActivity login, Activity activity){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (!email.isEmpty() && !password.isEmpty()){
           auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            getSignedInUser(login);
                        } else {
                            // If sign in fails, display a message to the user.
                            try {
                                throw Objects.requireNonNull(task1.getException());
                            } catch(FirebaseAuthWeakPasswordException e) {
                                Toast.makeText(activity, e.getReason(),
                                        Toast.LENGTH_LONG).show();
                            } catch (FirebaseAuthInvalidUserException e) {
                                Toast.makeText(activity,
                                        "User does not exist.",
                                        Toast.LENGTH_LONG).show();
                            }
                            catch(Exception e) {
                                Toast.makeText(activity,
                                        e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    //use for logout
    //signOut of Firebase and set CurrentUser to null contents

    /**
     * signs out the user
     */
    public void signOutUser(){
        FirebaseAuth currentInstance = FirebaseAuth.getInstance();
        currentInstance.signOut();
    }

    /**
     * says whether or not user is signed in
     * @return true or false
     */
    public boolean isUserSignedIn(){
        FirebaseAuth currentInstance = FirebaseAuth.getInstance();
        FirebaseUser user = currentInstance.getCurrentUser();
        return user != null;
    }

    //sets CurrentUser information to the logged in user
    //causes login screen to go to correct screen when complete

    /**
     * returns signed in user
     * @param login login activity
     */
    private void getSignedInUser(LoginActivity login) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth currentInstance = FirebaseAuth.getInstance();
        FirebaseUser user = currentInstance.getCurrentUser();
        user = Objects.requireNonNull(user);
        String theEmail = user.getEmail();
        Log.d( TAG, "email " + theEmail );
        db.collection( "users" ).whereEqualTo( "email", theEmail )
                .get().addOnSuccessListener(documentSnapshots -> {
                    if (documentSnapshots.isEmpty()) {
                        Log.d( TAG, "onSuccess: LIST EMPTY" );
                    } else {
                        // Convert the whole Query Snapshot to a list
                        // of objects directly! No need to fetch each
                        // document.
                        List<DocumentSnapshot> retDocs = documentSnapshots.getDocuments();
                        Log.d( TAG, "onSuccess: " + retDocs.get( 0 ).getId() );

                        String firstName = "";
                        String lastName = "";
                        String email = "";
                        String userType = "";
                        Map<String, Boolean> locationHash = new HashMap<>(  );
                        for (DocumentSnapshot doc : retDocs) {
                            firstName = (String) doc.get( "firstname" );
                            lastName = (String) doc.get( "lastname" );
                            email = (String) doc.get( "email" );
                            userType = (String) doc.get( "usertype" );
                            try{
                                locationHash= (HashMap<String, Boolean>) doc.get("locationIDs");
                            } catch(ClassCastException ex){
                                Log.e(TAG, ex.getMessage());
                            }

                        }
                        assert userType != null;
                        UserType type = stringToUserType( userType );
                        if (type == UserType.Volunteer) {
                            //Set<String> keySet = Objects.requireNonNull(locationHash).keySet();
                            //ArrayList<String> listOfKeys = new ArrayList<>(keySet);
                            userCallback = new User(firstName, lastName,
                                    email, userType);
                        } else {
                            userCallback = new User( firstName, lastName, email, userType );
                        }
                        CurrentUser currentInstanceUser = CurrentUser.getInstance();
                        currentInstanceUser.setUser( userCallback );
                        login.goToCorrectActivity();
                    }
                });
    }

    /**
     * get all user in database
     */
    public Task getAllUsers() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Task task = db.collection( "users" )
            .get()
            .addOnSuccessListener(documentSnapshots -> {
                if (documentSnapshots.isEmpty()) {
                    Log.d( TAG, "onSuccess: LIST EMPTY" );
                } else {
                    List<DocumentSnapshot> retDocs = documentSnapshots.getDocuments();
                    String key;
                    String firstName;
                    String lastName;
                    String email;
                    String type;
                    String status;
                    Log.d(TAG, "onSuccess: Got Users");

                    List<User> users = new ArrayList<>( );
                    for (DocumentSnapshot doc : retDocs) {
                        key = doc.getId();
                        firstName = (String) doc.get("firstname");
                        lastName  = (String) doc.get( "lastname" );
                        email = (String) doc.get( "email" );
                        type = (String) doc.get("usertype");
                        //status = (String) doc.get("status");

                        User user = new User(firstName, lastName, email, type);
                        user.setKey(key);
                        users.add(user);
                    }
                    AllUsers allInstance = AllUsers.getInstance();
                    allInstance.setUsers( users );
                }
            });
        return task;
    }

    /**
     * converts string user to usertype user
     * @param str string user
     * @return UserType user
     */
    private UserType stringToUserType(String str){
        switch(str){
            case "Donator" : return UserType.Donator;
            case "Volunteer" : return UserType.Volunteer;
            case "Manager" : return UserType.Manager;
            case "Admin" : return UserType.Admin;
        }
        return null;
    }


    // This method configures Google SignIn
    public void configureGoogleSignIn(Context context){
        // Configure sign-in to request the user’s basic profile like name and email
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleApiClient with access to GoogleSignIn.API and the options above.
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .build();
        mGoogleApiClient.connect();
    }

    public void googleSignIn(Activity activity, int request) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        activity.startActivityForResult(signInIntent, request);
    }

    public void googleSignOut() {
        if (mGoogleApiClient.isConnected()) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
