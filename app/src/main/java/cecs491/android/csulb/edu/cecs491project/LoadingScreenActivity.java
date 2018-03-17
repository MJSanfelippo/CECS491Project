package cecs491.android.csulb.edu.cecs491project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.security.auth.login.LoginException;

/**
 * Created by Michael on 3/15/2018.
 */

public class LoadingScreenActivity extends AppCompatActivity {

    /**
     * the value event listener
     */
    private ValueEventListener userListener;

    /**
     * the user type
     */
    private String userType;

    /**
     * the firebase authentication reference
     */
    private FirebaseAuth firebaseAuth;

    /**
     * the firebase database
     */
    private FirebaseDatabase db;

    /**
     * the user id
     */
    private String userId;

    /**
     * the database reference
     */
    private DatabaseReference ref;

    /**
     * instantiates the value event listener to get user type and name
     */
    private void instantiateValueEventListener(){
        userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userType = dataSnapshot.child("User Type").getValue().toString();
                goToHomePage();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
    }

    /**
     * instantiate the firebase components
     */
    private void instantiateFirebase(){
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
    }

    /**
     * handle the authentication
     * if the user is not null then they are still logged in
     * otherwise send them to the login screen
     */
    private void handleAuthentication(){
        if (firebaseAuth.getCurrentUser() != null){
            userId = firebaseAuth.getCurrentUser().getUid();
            ref = db.getReference("Users");
            ref.child(userId).addListenerForSingleValueEvent(userListener);
        } else {
            Intent i = new Intent(LoadingScreenActivity.this, LogInActivity.class);
            startActivity(i);
        }
    }
    /**
     * creates the activity
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        instantiateFirebase();
        instantiateValueEventListener();
        handleAuthentication();
    }

    /**
     * send the user to the correct homepage
     */
    private void goToHomePage(){
        Intent intent;
        if (userType.equalsIgnoreCase("Employer")){
            intent = new Intent(LoadingScreenActivity.this, EmployerHomePageActivity.class);
            startActivity(intent);
        } else {
            intent = new Intent(LoadingScreenActivity.this, EmployeeHomePageActivity.class);
            startActivity(intent);
        }
    }
}
