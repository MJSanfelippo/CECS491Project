package cecs491.android.csulb.edu.cecs491project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class EditEmployee extends AppCompatActivity {

    /**
     * the first name edit text reference
     */
    private EditText editTextFirstName;

    /**
     * the last name edit text reference
     */
    private EditText editTextLastName;

    /**
     * the email address edit text reference
     */
    private EditText editTextEmailAddress;

    /**
     * the phone number edit text reference
     */
    private EditText editTextPhoneNumber;

    /**
     * button to submit edits reference
     */
    private Button editButton;

    private void instantiateLayout() {
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextEmailAddress = findViewById(R.id.editTextEmailAddress);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editButton = findViewById(R.id.editButton);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);

        instantiateLayout();
    }
}
