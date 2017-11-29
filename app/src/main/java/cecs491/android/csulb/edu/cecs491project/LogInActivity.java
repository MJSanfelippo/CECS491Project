package cecs491.android.csulb.edu.cecs491project;

import android.app.ProgressDialog;
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


public class LogInActivity extends AppCompatActivity implements View.OnClickListener{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonSignIn.setOnClickListener(this);

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
            Toast.makeText(this, "please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Attempting login...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(LogInActivity.this, "Signed in successful", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(LogInActivity.this, "Not successful", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }
        });

    }
    @Override
    public void onClick(View view){
        if (view == buttonSignIn){
            signInUser();
        }
    }
}
