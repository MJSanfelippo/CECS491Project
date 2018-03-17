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

    private ValueEventListener userListener;
    private String userType;
    private String firstName;
    private String lastName;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase db;
    private String userId;
    private DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userType = dataSnapshot.child("User Type").getValue().toString();
                firstName = dataSnapshot.child("First Name").getValue().toString();
                lastName = dataSnapshot.child("Last Name").getValue().toString();
                signIn();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();


        if (firebaseAuth.getCurrentUser() != null){
            userId = firebaseAuth.getCurrentUser().getUid();
            ref = db.getReference("Users");
            ref.child(userId).addListenerForSingleValueEvent(userListener);
        } else {
            Intent i = new Intent(LoadingScreenActivity.this, LogInActivity.class);
            startActivity(i);
        }
    }

    private void signIn(){
        Intent intent;
        if (userType.equalsIgnoreCase("Employer")){
            intent = new Intent(LoadingScreenActivity.this, EmployerHomePageActivity.class);
            Bundle b = new Bundle();
            b.putString("firstName", firstName);
            b.putString("lastName", lastName);
            intent.putExtras(b);
            startActivity(intent);
        } else {
            intent = new Intent(LoadingScreenActivity.this, EmployeeHomePageActivity.class);
            Bundle b = new Bundle();
            b.putString("firstName", firstName);
            b.putString("lastName", lastName);
            intent.putExtras(b);
            startActivity(intent);
        }
    }
}
