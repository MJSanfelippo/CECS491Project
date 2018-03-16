package cecs491.android.csulb.edu.cecs491project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NewUserActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextEmail;
    private EditText editTextPhone;

    private CheckBox employerCheckBox;

    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);

        employerCheckBox = (CheckBox) findViewById(R.id.employerCheckBox);

        submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);
    }

    private void createUser() {
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String phone = editTextEmail.getText().toString().trim(); //Make sure to double check formatting

        if ((TextUtils.isEmpty(firstName)) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please enter required information", Toast.LENGTH_LONG);
        }

        boolean employer = false;
        if (employerCheckBox.isChecked()) {
            employer = true;
        }
    }

    public void onClick(View view) {
        if (view == submitButton) {
            //firebase shit
        }
    }
}
