package cecs491.android.csulb.edu.cecs491project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by pbour on 3/16/2018.
 */

public class EmployerProfileActivity extends AppCompatActivity {

    /**
     * the first name text view reference
     */
    private TextView firstNameTextView;

    /**
     * the last name text view reference
     */
    private TextView lastNameTextView;

    /**
     * the email text view reference
     */
    private TextView emailTextView;

    /**
     * the phone text view reference
     */
    private TextView phoneTextView;

    /**
     * the sign out button reference
     */
    private Button signOutButton;

    /**
     * the user reference
     */
    private User user;

    /**
     * the database reference
     */
    private DatabaseReference ref;

    /**
     * the firebase authentication reference
     */
    private FirebaseAuth auth;

    /**
     * the user id
     */
    private String uid;

    /**
     * the firebase user
     */
    private FirebaseUser fbUser;

    /**
     * the value event listener
     */
    private ValueEventListener valueEventListener;

    /**
     * the bottom navigation view
     */
    private BottomNavigationView navigation;

    /**
     * instantiate all layout components
     */
    private void instantiateLayout(){
        firstNameTextView = findViewById(R.id.employerProfileFirstName);
        lastNameTextView = findViewById(R.id.employerProfileLastName);
        emailTextView =findViewById(R.id.employerProfileEmail);
        phoneTextView = findViewById(R.id.employerProfilePhone);
        signOutButton = findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent i = new Intent(EmployerProfileActivity.this, LogInActivity.class);
                startActivity(i);
            }
        });
        navigation = findViewById(R.id.navigationEmployer);
    }

    /**
     * instantiate all firebase components
     */
    private void instantiateFirebase(){
        auth = FirebaseAuth.getInstance();
        fbUser = auth.getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference("Users");
        uid = fbUser.getUid();
    }

    /**
     * instantiate the value event listener to get all info about the current user and attach it
     */
    private void instantiateValueEventListener(){
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userType = dataSnapshot.child("User Type").getValue().toString();
                String firstName = dataSnapshot.child("First Name").getValue().toString();
                String lastName = dataSnapshot.child("Last Name").getValue().toString();
                String email = dataSnapshot.child("Email").getValue().toString();
                String phone = dataSnapshot.child("Phone").getValue().toString();
                user = new User(email, firstName, lastName, phone, userType);
                setUserInformation(user);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        ref.child(uid).addListenerForSingleValueEvent(valueEventListener);
    }

    /**
     * handle what happens when the user clicks on different things in the navigation bar
     */
    private void handleNavMenu(){
        Menu menu = navigation.getMenu();
        MenuItem item = menu.getItem(1);
        item.setChecked(true);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        intent = new Intent(EmployerProfileActivity.this, EmployerHomePageActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_profile:
                        return true;
                    case R.id.navigation_schedule:
                        intent = new Intent(EmployerProfileActivity.this, EmployerScheduleActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_announcements:
                        return true;
                    case R.id.navigation_admin:
                        intent = new Intent(EmployerProfileActivity.this, EmployerAdminActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }}
        );
    }

    /**
     * create the activity
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_profile);

        instantiateLayout();
        instantiateFirebase();
        instantiateValueEventListener();
        handleNavMenu();
    }

    /**
     * set the user info to the text views
     * @param user the user
     */
    private void setUserInformation(User user){
        firstNameTextView.setText("First Name: " + user.getFirstName());
        lastNameTextView.setText("Last Name: " + user.getLastName());
        emailTextView.setText("Email: " + user.getEmail());
        phoneTextView.setText("Phone: " + user.getPhoneNumber());
    }
}