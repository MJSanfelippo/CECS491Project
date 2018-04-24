package cecs491.android.csulb.edu.cecs491project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by phannachrin on 4/23/18.
 */

public class PostAnnouncementActivity extends AppCompatActivity {

    private EditText announcementEditText;

    private Button postButton;
    private Button cancelButton;

    private BottomNavigationView navigation;

    private FirebaseDatabase db;
    private FirebaseAuth auth;
    private DatabaseReference ref;

    private void instantiateFirebase() {
        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        ref = db.getReference("Announcements");
    }

    private void instantiateLayout(){
        announcementEditText = findViewById(R.id.announcementEditText);
        postButton = findViewById(R.id.postButton);
        cancelButton = findViewById(R.id.cancelButton);
        navigation = findViewById(R.id.navigation);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newAnnouncement = announcementEditText.getText().toString();
                //String date = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(new Date());
                String timeStamp = DateFormat.getDateTimeInstance().format(new Date());
                String employerId = auth.getUid();
                String announcementID = timeStamp;
                ref.child(announcementID).child("Made by").setValue(employerId);
                ref.child(announcementID).child("Message").setValue(newAnnouncement);
                ref.child(announcementID).child("Timestamp").setValue(timeStamp);
                postSuccessful();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent i = new Intent(PostAnnouncementActivity.this, EmployerAdminActivity.class);
                startActivity(i);
            }
        });
    }

    private void handleNavMenu(){
        Menu menu = navigation.getMenu();
        MenuItem item = menu.getItem(4);
        item.setChecked(true);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        intent = new Intent(PostAnnouncementActivity.this, EmployerHomePageActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_profile:
                        intent = new Intent(PostAnnouncementActivity.this, EmployerProfileActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_schedule:
                        intent = new Intent(PostAnnouncementActivity.this, EmployerScheduleActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_announcements:
                        return true;
                    case R.id.navigation_admin:
                        intent = new Intent(PostAnnouncementActivity.this, EmployerAdminActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }}
        );
    }

    private void postSuccessful(){
        AlertDialog alertDialog = new AlertDialog.Builder(PostAnnouncementActivity.this).create();
        alertDialog.setMessage("Post Successful.");
        alertDialog.show();
        Intent i = new Intent(PostAnnouncementActivity.this, PostAnnouncementActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_announcement);
        instantiateFirebase();
        instantiateLayout();
        handleNavMenu();
    }

}
