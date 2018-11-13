package com.track.brachio.donationtracker.model.database;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.track.brachio.donationtracker.LoginActivity;
import com.track.brachio.donationtracker.RegistrationActivity;
import com.track.brachio.donationtracker.model.User;
import com.track.brachio.donationtracker.model.UserType;
import com.track.brachio.donationtracker.model.singleton.CurrentUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Handler for User
 */
@SuppressWarnings({"SpellCheckingInspection", "FeatureEnvy"})
public class FirebaseUserHandler {
    private final String TAG = "FirebaseUserHandler";
    private User userCallback;

    /**
     * returns current user
     * @return current user
     */
    public FirebaseUser getCurrentUser(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    /**
     * returns name of current user
     * @return name of current user
     */
    public String getCurrentUserName(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return Objects.requireNonNull(user).getDisplayName();
    }

    /**
     * returns email of current user
     * @return email of current user
     */
    public String getCurrentUserEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        return user.getEmail();
    }

    /**
     * reutrns whether or not they are verified
     * @return true or false
     */
    public boolean isCurrentUserEmailVerified(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return Objects.requireNonNull(user).isEmailVerified();
    }

    /**
     * returns id of current user
     * @return id
     */
    public String getCurrentUserID(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return Objects.requireNonNull(user).getUid();
    }

    /**
     * updates the users name
     * @param newUserName new name
     */
    public void updateUserName(String newUserName){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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

    /**
     * updates email of current user
     * @param newEmail new email
     */
    public void updateEmail(String newEmail){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Objects.requireNonNull(user).updatePassword(newPassword)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "User password updated.");
                    }
                });
    }

    //use for registration page
    //will add user to Firebase, make this user CurrentUser for singleton

    /**
     * creates new user
     * @param appUser new user
     * @param password passowrd of new user
     * @param registration type of user
     * @param activity current activity
     */
    //TODO create progress bar to be displayed
    @SuppressWarnings("TodoComment")
    public void createNewUser(User appUser, String password,
                              RegistrationActivity registration, Activity activity){
        // Write a message to the database
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        Map<String, Object> userMap = new HashMap<>();
        if(!appUser.isNull() && !password.isEmpty()) {
            userMap.put("firstname", appUser.getFirstName());
            userMap.put("lastname", appUser.getLastName());
            userMap.put("email", appUser.getEmail());
            userMap.put("usertype", appUser.getUserType().name());
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
                                                .document(Objects.requireNonNull(auth.getCurrentUser()).getUid())
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
           Task task = auth.signInWithEmailAndPassword(email, password)
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
        FirebaseAuth.getInstance().signOut();
    }

    /**
     * says whether or not user is signed in
     * @return true or false
     */
    public boolean isUserSignedIn(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d( TAG, "email " + Objects.requireNonNull(user).getEmail() );
        db.collection( "users" ).whereEqualTo( "email", user.getEmail() )
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
                                //noinspection unchecked
                                locationHash= (HashMap<String, Boolean>) doc.get("locationIDs");
                            } catch(ClassCastException ex){
                                Log.e(TAG, ex.getMessage());
                            }

                        }
                        assert userType != null;
                        UserType type = stringToUserType( userType );
                        if (type == UserType.Volunteer) {
                            Set<String> keySet = locationHash.keySet();
                            ArrayList<String> listOfKeys = new ArrayList<>(keySet);
                            userCallback = new User(firstName, lastName,
                                    email, userType, listOfKeys);
                        } else {
                            userCallback = new User( firstName, lastName, email, userType );
                        }
                        CurrentUser.getInstance().setUser( userCallback );
                        login.goToCorrectActivity();
                    }
                });
    }

    /**
     * returns locations
     * @param user user being passed in to find locations
     */
    @SuppressWarnings("StatementWithEmptyBody")
    private void getLocations(User user){
        if (user.getUserType() == UserType.Volunteer){

        } else if (user.getUserType() == UserType.Manager){

        }

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


}
