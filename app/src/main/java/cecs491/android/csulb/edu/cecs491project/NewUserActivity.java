package cecs491.android.csulb.edu.cecs491project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewUserActivity extends AppCompatActivity{

    /**
     * the first name edit text reference
     */
    private EditText editTextFirstName;

    /**
     * the last name edit text reference
     */
    private EditText editTextLastName;

    /**
     * the email edit text reference
     */
    private EditText editTextEmail;

    /**
     * the phone edit text reference
     */
    private EditText editTextPhone;

    /**
     * the password edit text reference
     */
    private EditText editTextPassword;

    /**
     * the confirm password edit text reference
     */
    private EditText editTextConfirmPassword;

    /**
     * the checkbox for employer or not reference
     */
    private CheckBox employerCheckBox;

    /**
     * the user's first name
     */
    private String firstName;

    /**
     * the user's last name
     */
    private String lastName;

    /**
     * the user's email
     */
    private String email;

    /**
     * the user's phone
     */
    private String phone;

    /**
     * the user's password
     */
    private String password;

    /**
     * the user's password again
     */
    private String confirmPassword;

    /**
     * the user's type
     */
    private String userType;

    /**
     * the user's id
     */
    private String uid;

    /**
     * the submit button reference
     */
    private Button submitButton;

    /**
     * the firebase authentication reference
     */
    private FirebaseAuth auth;

    /**
     * the database reference
     */
    private DatabaseReference ref;

    /**
     * instantiate the layout
     */
    private void instantiateLayout(){
        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextPassword = (EditText) findViewById(R.id.signUpEnterPassword);
        editTextConfirmPassword = (EditText) findViewById(R.id.signUpConfirmPassword);
        employerCheckBox = (CheckBox) findViewById(R.id.employerCheckBox);
        submitButton = (Button) findViewById(R.id.newUserSubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });
    }

    /**
     * instantiate the firebase components
     */
    private void instantiateFirebase(){
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("Users");
    }

    /**
     * create the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        instantiateFirebase();
        instantiateLayout();
    }

    /**
     * set the user values based on what's in the edit texts
     */
    private void getUserValues(){
        firstName = editTextFirstName.getText().toString().trim();
        lastName = editTextLastName.getText().toString().trim();
        email = editTextEmail.getText().toString().trim();
        phone = editTextPhone.getText().toString().trim(); //Make sure to double check formatting
        password = editTextPassword.getText().toString();
        confirmPassword = editTextConfirmPassword.getText().toString();
    }

    /**
     * sign the user up using firebase authentication and send them to the correct home page after sign up is complete
     */
    private void signUpUser(){
        userType = "Employee";
        if (employerCheckBox.isChecked()) {
            userType = "Employer";
        }
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser fbUser = auth.getCurrentUser();
                    uid = fbUser.getUid();
                    User user = new User(email, firstName, lastName, phone, userType);
                    addUserToDatabase(user);
                    if (userType.equals("Employee")){
                        Intent intent = new Intent(NewUserActivity.this, EmployeeHomePageActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(NewUserActivity.this, EmployerHomePageActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(NewUserActivity.this, "Sign up failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * check if there are any empty edit texts
     * @return true if any of the edit texts are empty
     */
    private boolean editTextsAreEmpty(){
        return (TextUtils.isEmpty(firstName)) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone);
    }

    /**
     * perform the necessary checks, then sign the use up
     */
    private void createUser() {
        getUserValues();
        if (editTextsAreEmpty()) {
            Toast.makeText(this, "Please enter all required information", Toast.LENGTH_LONG).show();
        } else if (!password.equals(confirmPassword)){
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show();
        } else {
            signUpUser();
        }
    }

    /**
     * add the user to the database with the information
     * @param user the user to be added
     */
    private void addUserToDatabase(User user){
        ref.child(uid).child("Email").setValue(user.getEmail());
        ref.child(uid).child("First Name").setValue(user.getFirstName());
        ref.child(uid).child("Last Name").setValue(user.getLastName());
        ref.child(uid).child("Phone").setValue(user.getPhoneNumber());
        ref.child(uid).child("User Type").setValue(user.getUserType());
        ref.child(uid).child("Disabled").setValue("false");
    }
}