package com.sachet.bloodsearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TestRegisterActivity extends AppCompatActivity {
EditText username,password,address,email,phone;
Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_register);

        username = findViewById(R.id.test_username);
        password = findViewById(R.id.test_password);
        address = findViewById(R.id.test_address);
        email = findViewById(R.id.test_email);
        phone = findViewById(R.id.test_phone);
        register = findViewById(R.id.test_register_button);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

if(isFieldEmpty(username) | isFieldEmpty(password) | isFieldEmpty(address) | isFieldEmpty(phone) | isEmailValid(email)){

                String usernameValue = username.getText().toString();
                String passwordValue = password.getText().toString();
                String addressValue = address.getText().toString();
                String emailValue = email.getText().toString();
                String phoneValue = phone.getText().toString();

                Toast.makeText(TestRegisterActivity.this, "SUCCESFULLY REGISTERED", Toast.LENGTH_SHORT).show();
            }
            }
        });




    }

    public boolean isFieldEmpty(EditText view){

        String value = view.getText().toString();

        if(value.length() > 0)
            return true;
        else{
            view.setError("Please give valid input");
            return false;
        }
    }

    public boolean isEmailValid(EditText view){
        String chkEmailValue = view.getText().toString();

        if(Patterns.EMAIL_ADDRESS.matcher(chkEmailValue).matches()){
            return true;

        }else{
            view.setError("Please enter valid email");
            return false;
        }
    }
}
