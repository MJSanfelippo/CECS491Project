package cecs491.android.csulb.edu.cecs491project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Michael on 3/15/2018.
 */

public class EmployeeProfileActivity extends AppCompatActivity {

    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView emailTextView;
    private TextView phoneTextView;

    private User user;
    private DatabaseReference ref;
    private FirebaseUser fbUser;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);


        firstNameTextView = (TextView) findViewById(R.id.profileFirstName);
        lastNameTextView = (TextView) findViewById(R.id.profileLastName);
        emailTextView = (TextView) findViewById(R.id.profileEmail);
        phoneTextView = (TextView) findViewById(R.id.profilePhone);

        ref = FirebaseDatabase.getInstance().getReference("Users");
        fbUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = fbUser.getUid();
        ValueEventListener valueEventListener = new ValueEventListener() {
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
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ref.child(uid).addListenerForSingleValueEvent(valueEventListener);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        Menu menu = navigation.getMenu();
        MenuItem item = menu.getItem(1);
        item.setChecked(true);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        intent = new Intent(EmployeeProfileActivity.this, EmployeeHomePageActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_profile:
                        return true;
                    case R.id.navigation_schedule:
                        intent = new Intent(EmployeeProfileActivity.this, EmployeeScheduleActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_announcements:

                        return true;
                }
                return false;
            }}
        );
    }

    private void setUserInformation(User user){
        firstNameTextView.setText("First Name: " + user.getFirstName());
        lastNameTextView.setText("Last Name: " + user.getLastName());
        emailTextView.setText("Email: " + user.getEmail());
        phoneTextView.setText("Phone: " + user.getPhoneNumber());
    }
}
