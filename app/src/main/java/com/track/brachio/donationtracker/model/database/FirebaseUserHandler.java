package com.track.brachio.donationtracker.model.database;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.track.brachio.donationtracker.AdminMainActivity;
import com.track.brachio.donationtracker.DonatorMainActivity;
import com.track.brachio.donationtracker.LoginActivity;
import com.track.brachio.donationtracker.ManagerMainActivity;
import com.track.brachio.donationtracker.RegistrationActivity;
import com.track.brachio.donationtracker.VolunteerMainActivity;
import com.track.brachio.donationtracker.model.User;
import com.track.brachio.donationtracker.model.UserType;
import com.track.brachio.donationtracker.model.singleton.CurrentUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

public class FirebaseUserHandler {
    private String TAG = "FirebaseUserHandler";
    private User userCallback;

    public FirebaseUser getCurrentUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user;
    }

    public String getCurrentUserName(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user.getDisplayName();
    }

    public String getCurrentUserEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user.getEmail();
    }

    public boolean isCurrentUserEmailVerified(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user.isEmailVerified();
    }

    public String getCurrentUserID(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user.getUid();
    }

    public void updateUserName(String newUserName){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(newUserName)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });
    }

    public void updateEmail(String newEmail){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(newEmail)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User email address updated.");
                        }
                    }
                });
    }

    public void updatePassword(String newPassword){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User password updated.");
                        }
                    }
                });
    }

    //use for registration page
    //will add user to Firebase, make this user CurrentUser for singleton
    //TODO create progress bar to be displayed
    public void createNewUser(User appUser, String password, RegistrationActivity registration, Activity activity){
        // Write a message to the database
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        Map<String, Object> userMap = new HashMap<>();
        if(!appUser.isNull() && !password.isEmpty()) {
            userMap.put("firstname", appUser.getFirstname());
            userMap.put("lastname", appUser.getLastname());
            userMap.put("email", appUser.getEmail());
            userMap.put("usertype", appUser.getUserType().name());
            userMap.put("date created", appUser.getTimestamp());
                auth.createUserWithEmailAndPassword(appUser.getEmail(), password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                try {
                                    //check if successful
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "Registered");
                                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                                        if (auth.getUid() != null) {
                                            db.collection("users").document(auth.getCurrentUser().getUid()).set(userMap)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Log.d(TAG, "Added extra data");
                                                        }
                                                    });
                                            Intent intent = new Intent(activity, LoginActivity.class);
                                            registration.startActivity(intent);
                                        } else {
                                            Log.e(TAG, "Cannot access current user");
                                        }
                                    }else{
                                        Log.e(TAG, "Not registered");
                                        Toast.makeText(activity, "User with email entered already exists.", Toast.LENGTH_LONG).show();
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });

        } else {
            Log.e(TAG, "Entered null value");
        }
    }

    //use for sign in
    //make sign in user the CurrentUser
    public void signInUser(String email, String password, LoginActivity login, Activity activity){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (!email.isEmpty() && !password.isEmpty()){
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                getSignedInUser(login);
                            } else {
                                // If sign in fails, display a message to the user.
                                //Log.w(TAG, "signInWithEmail:failure", task.getException());
                                //Toast.makeText(activity, "Username or password is incorrect.", Toast.LENGTH_LONG).show();
                                try {
                                    throw task.getException();
                                } catch(FirebaseAuthWeakPasswordException e) {
                                    Toast.makeText(activity, e.getReason(), Toast.LENGTH_LONG).show();
                                } catch (FirebaseAuthInvalidUserException e) {
                                    Toast.makeText(activity, "User does not exist.", Toast.LENGTH_LONG).show();
                                }
                                catch(Exception e) {
                                    Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
        }
    }

    //use for logout
    //signOut of Firebase and set CurrentUser to null contents
    public void signOutUser(){
        FirebaseAuth.getInstance().signOut();
        CurrentUser.getInstance().setUser(null);
    }

    public boolean isUserSignedIn(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return true;
        } else {
            return false;
        }
    }

    //sets CurrentUser information to the logged in user
    //causes login screen to go to correct screen when complete
    private void getSignedInUser(LoginActivity login) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d( TAG, "email " + user.getEmail() );
        if (user == null) {
            Log.d( TAG, "onFailure: Not signed it" );
        } else {
            db.collection( "users" ).whereEqualTo( "email", user.getEmail() )
                    .get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot documentSnapshots) {
                    if (documentSnapshots.isEmpty()) {
                        Log.d( TAG, "onSuccess: LIST EMPTY" );
                        return;
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
                        for (DocumentSnapshot doc : retDocs) {
                            firstName = (String) doc.get( "firstname" );
                            lastName = (String) doc.get( "lastname" );
                            email = (String) doc.get( "email" );
                            userType = (String) doc.get( "usertype" );
                        }
                        userCallback = new User( firstName, lastName, email, userType );
                        CurrentUser.getInstance().setUser( userCallback );
                        login.goToCorrectActivity();
                    }
                }
            } );
        }


    }


}
