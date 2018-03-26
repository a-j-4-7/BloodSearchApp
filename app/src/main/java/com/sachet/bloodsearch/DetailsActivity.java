package com.sachet.bloodsearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.sachet.bloodsearch.helper.UserInfo;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailsActivity extends AppCompatActivity {

    CircleImageView imageView;
    TextView fn, un, age, gender, bloodgroup, city, contact, email;
    DatabaseHelper databaseHelper;
    Button button;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        username = getIntent().getStringExtra("username");

        databaseHelper = new DatabaseHelper(this);
        button = findViewById(R.id.edit_details_btn);
        fn = findViewById(R.id.details_fullname);
        un = findViewById(R.id.details_username);
        age = findViewById(R.id.detail_age);
        gender = findViewById(R.id.detail_gender);
        bloodgroup = findViewById(R.id.detail_bloodgroup);
        city = findViewById(R.id.detail_city);
        contact = findViewById(R.id.detail_contact);
        email = findViewById(R.id.detail_email);
        fn = findViewById(R.id.details_fullname);
        imageView = findViewById(R.id.detail_userimg);
//        populateData();
    }

//    public void populateData(){
//        UserInfo info = databaseHelper.getUserInfo(username);
//        un.setText(info.username);
//    }
}
