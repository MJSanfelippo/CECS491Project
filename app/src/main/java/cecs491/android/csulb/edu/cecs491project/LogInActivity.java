package cecs491.android.csulb.edu.cecs491project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LogInActivity extends AppCompatActivity{

    /**
     * sign up button
     */
    private Button buttonSignUp;
    /**
     * sign in button
     */
    private Button buttonSignIn;
    /**
     * edit text for the user's email
     */
    private EditText editTextEmail;
    /**
     * edit text for the user's password
     */
    private EditText editTextPassword;

    /**
     * progress dialog to show while user is being authenticated
     */
    private ProgressDialog progressDialog;

    /**
     * instance of the firebase authenticator
     */
    private FirebaseAuth firebaseAuth;

    /**
     * firebase database
     */
    private FirebaseDatabase db;

    /**
     * firebase user
     */
    private FirebaseUser firebaseUser;

    /**
     * the user's id
     */
    private String userId;

    /**
     * the reference to the database
     */
    private DatabaseReference ref;

    /**
     * the type of user: employee or employer
     */
    private String userType;

    /**
     * value event listener to grab data from the database when an event happens
     */
    private ValueEventListener userListener;

    /**
     * Takes the user to their home page depending on whether they're an employee or employer
     */
    private void goToHomePage(){
        Intent intent;
        if (userType.equalsIgnoreCase("Employer")){
            intent = new Intent(LogInActivity.this, EmployerHomePageActivity.class);
            startActivity(intent);
        } else {
            intent = new Intent(LogInActivity.this, EmployeeHomePageActivity.class);
            startActivity(intent);
        }
    }

    /**
     * instantiates all the necessary variables
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        instantiateValueEventListener();
        instantiateFirebase();
        instantiateLayout();
    }

    /**
     * instantiate the value event listener to get the user type, first name, and last name
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
     * instantiate the firebase authentication and database
     */
    private void instantiateFirebase(){
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
    }

    /**
     * instantiate all the layout components
     */
    private void instantiateLayout(){
        progressDialog = new ProgressDialog(this);
        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInUser();
            }
        });
        buttonSignUp = (Button) findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignUpPage();
            }
        });
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
    }

    /**
     * Takes the user to the sign up page
     */
    private void goToSignUpPage(){
        Intent i = new Intent(LogInActivity.this, NewUserActivity.class);
        startActivity(i);
    }

    /**
     * Retrieves the email and password from the text fields and performs some validation on them
     * If they pass the validation, a sign in is attempted
     * If the server is able to authenticate the username and password, it displays a message "signed in successful"
     * Otherwise it will display "Not successful"
     */
    private void signInUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Attempting login...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    firebaseUser = firebaseAuth.getCurrentUser();
                    userId = firebaseUser.getUid();
                    ref = db.getReference("Users");
                    ref.child(userId).addListenerForSingleValueEvent(userListener);
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(LogInActivity.this, "Sign in unsuccessful", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }
        });
    }
}