package com.sachet.bloodsearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sachet.bloodsearch.helper.UserDataSharedPreference;
import com.tapadoo.alerter.Alerter;

public class LoginActivity extends AppCompatActivity {

    Button login_btn, register_btn, free_user_btn;
    AppCompatEditText username, password;
    DatabaseHelper databaseHelper;
    RelativeLayout relativeLayout;
    TextInputLayout usernameLayout, passwordLayout;
    CheckBox remember;
    UserDataSharedPreference userDataSharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userDataSharedPreference = new UserDataSharedPreference();

        login_btn = findViewById(R.id.login_btn);
        register_btn = findViewById(R.id.register_btn);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        databaseHelper = new DatabaseHelper(this);
        relativeLayout = findViewById(R.id.main_activity);
        usernameLayout = findViewById(R.id.username_layout);
        passwordLayout = findViewById(R.id.password_layout);

        relativeLayout.setOnClickListener(null);
        remember = findViewById(R.id.remember);


        usernameLayout.setCounterEnabled(true);
        usernameLayout.setCounterMaxLength(16);


        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (username.getText().toString().isEmpty()) {
                    usernameLayout.setErrorEnabled(true);
                    usernameLayout.setError("Please enter your Username!!!");
                } else {
                    usernameLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (username.getText().toString().isEmpty()) {
                    usernameLayout.setErrorEnabled(true);
                    usernameLayout.setError("Please enter your Username!!!");
                } else {
                    usernameLayout.setErrorEnabled(false);
                }

            }
        });

        passwordLayout.setCounterEnabled(true);
        passwordLayout.setCounterMaxLength(16);

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (password.getText().toString().isEmpty()) {
                    passwordLayout.setErrorEnabled(true);
                    passwordLayout.setError("Please enter your Password");
                } else {
                    passwordLayout.setErrorEnabled(false);
                }

            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (password.getText().toString().isEmpty()) {
                    passwordLayout.setErrorEnabled(true);
                    passwordLayout.setError("Please enter your Password");
                } else {
                    passwordLayout.setErrorEnabled(false);
                }


            }
        });

//


        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameValue = username.getText().toString().trim();
                String passwordValue = password.getText().toString();

                if (databaseHelper.isValidLogin(usernameValue, passwordValue)) {
                    showLoginAlerter();
                    UserDataSharedPreference.setUserName(getApplicationContext(), usernameValue);
                    Toast.makeText(LoginActivity.this, "Login succesfull!!! Welcome" + "\t" + usernameValue, Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent = new Intent(LoginActivity.this, NavDrawerActivity.class);
                    intent.putExtra("username", usernameValue);
                    startActivity(intent);


                } else {
                    Toast.makeText(LoginActivity.this, "Login failure!!!", Toast.LENGTH_SHORT).show();
                    showLoginFailAlerter();
                }
//                ArrayList<UserInfo>  userInfos=databaseHelper.isValidLogin(usernameValue,passwordValue);
//                if (userInfos!=null) {
//                    UserDataSharedPreference.setUserName(getApplicationContext(),usernameValue);
//                    Intent intent = new Intent(LoginActivity.this, NavDrawerActivity.class);
//                    intent.putExtra("username",usernameValue);
//                    startActivity(intent);
//                    username.setText(null);
//                    password.setText(null);
//                    username.clearFocus();
//                    password.clearFocus();
//
//                    Toast.makeText(LoginActivity.this, "Login successful!!! Welcome"+"\t" + usernameValue, Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(LoginActivity.this, "Login failure!!!", Toast.LENGTH_SHORT).show();
//
//                }


            }
        });


    }

    public void showLoginAlerter() {
        String alertUNV = username.getText().toString();

        Alerter.create(LoginActivity.this)
                .setBackgroundColorRes(R.color.colorPrimary)
                .setTitle("WELCOME")
                .setText("You have successfully logged in !!! " +alertUNV)
                .setDuration(3000)
                .enableIconPulse(true)
                .enableSwipeToDismiss()
                .showIcon(true)
                .setIcon(R.drawable.ic_sentiment_very_satisfied_black_24dp)
                .show();


    }

    public void showLoginFailAlerter() {

        Alerter.create(LoginActivity.this)
                .setBackgroundColorRes(R.color.colorPrimary)
                .setTitle("CRITICAL ERROR !!!")
                .setText("PLEASE ENTER VALID USERNAME AND PASSWORD TO ENTER !!!")
                .setDuration(3000)
                .enableIconPulse(true)
                .enableSwipeToDismiss()
                .showIcon(true)
                .setIcon(R.drawable.ic_sentiment_dissatisfied_black_24dp)
                .show();


    }


}
