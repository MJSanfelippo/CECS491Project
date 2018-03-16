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

public class NewUserActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextEmail;
    private EditText editTextPhone;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;

    private CheckBox employerCheckBox;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private String userType;

    private String uid;
    private Button submitButton;

    private FirebaseAuth auth;
    private DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextPassword = (EditText) findViewById(R.id.signUpEnterPassword);
        editTextConfirmPassword = (EditText) findViewById(R.id.signUpConfirmPassword);
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("Users");

        employerCheckBox = (CheckBox) findViewById(R.id.employerCheckBox);

        submitButton = (Button) findViewById(R.id.newUserSubmitButton);
        submitButton.setOnClickListener(this);
    }

    private void createUser() {
        firstName = editTextFirstName.getText().toString().trim();
        lastName = editTextLastName.getText().toString().trim();
        email = editTextEmail.getText().toString().trim();
        phone = editTextPhone.getText().toString().trim(); //Make sure to double check formatting
        password = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();

        if ((TextUtils.isEmpty(firstName)) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please enter required information", Toast.LENGTH_LONG).show();
        } else if (!password.equals(confirmPassword)){
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show();
        } else {
            userType = "Employee";
            if (employerCheckBox.isChecked()) {
                userType = "Employer";
            }
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(NewUserActivity.this, "Sign up complete", Toast.LENGTH_LONG).show();
                        FirebaseUser fbUser = auth.getCurrentUser();
                        uid = fbUser.getUid();
                        User user = new User(email, firstName, lastName, phone, userType);
                        addUserToDatabase(user);
                        if (userType.equals("Employee")){
                            Intent intent = new Intent(NewUserActivity.this, EmployeeHomePageActivity.class);
                            Bundle b = new Bundle();
                            b.putString("firstName", firstName);
                            b.putString("lastName", lastName);
                            intent.putExtras(b);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(NewUserActivity.this, EmployerHomePageActivity.class);
                            Bundle b = new Bundle();
                            b.putString("firstName", firstName);
                            b.putString("lastName", lastName);
                            intent.putExtras(b);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(NewUserActivity.this, "Sign up failed", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }

    private void addUserToDatabase(User user){
        ref.child(uid).child("Email").setValue(user.getEmail());
        ref.child(uid).child("First Name").setValue(user.getFirstName());
        ref.child(uid).child("Last Name").setValue(user.getLastName());
        ref.child(uid).child("Phone").setValue(user.getPhoneNumber());
        ref.child(uid).child("User Type").setValue(user.getUserType());
    }
    public void onClick(View view) {
        if (view == submitButton) {
            createUser();
        }
    }
}
