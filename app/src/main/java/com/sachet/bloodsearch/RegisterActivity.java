package com.sachet.bloodsearch;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.tapadoo.alerter.Alerter;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {


    Button uploadimg, register, cancel;
    AppCompatEditText fullname, age, email, phone, username, password, reenterpassword;
    String genderValue,fullnameValue,ageValue,emailValue,phoneValue,usernameValue,passwordValue,reenterpasswordValue,bloodgroupValue,locationValue;
    RadioGroup gender;
    AppCompatSpinner spinner1, spinner2;
    CircleImageView imageView;
    DatabaseHelper databaseHelper;
    TextInputLayout passwordLayout, reenterpasswordLayout;
    ArrayAdapter<CharSequence> adapter, adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        databaseHelper = new DatabaseHelper(this);
        imageView = findViewById(R.id.user_image);

        register = findViewById(R.id.register);
        cancel = findViewById(R.id.cancel);
        fullname = findViewById(R.id.fullname);
        age = findViewById(R.id.age);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        gender = findViewById(R.id.gender);
        uploadimg = findViewById(R.id.uploadimg);
        spinner1 = findViewById(R.id.register_bloodgroup_spinner);
        spinner2 = findViewById(R.id.register_location_spinner);
        reenterpassword = findViewById(R.id.reenter_password);
        passwordLayout = findViewById(R.id.register_password_layout);

        reenterpasswordLayout = findViewById(R.id.register_reenter_password_layout);

        passwordLayout.setCounterEnabled(true);
        passwordLayout.setCounterMaxLength(16);
        reenterpasswordLayout.setCounterEnabled(true);
        reenterpasswordLayout.setCounterMaxLength(16);

        reenterpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String pass = password.getText().toString();
                if (reenterpassword.getText().toString().equals(pass) && pass.length()>=8 ) {
                    reenterpasswordLayout.setErrorEnabled(false);
                } else {
                    reenterpasswordLayout.setErrorEnabled(true);
                    reenterpasswordLayout.setError("Please enter a valid password not less than 8 digits!!!");

                }

            }
        });


//


        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                register();







            }
        });


        adapter = ArrayAdapter.createFromResource(this, R.array.spinner_bloodgroup, R.layout.custom_spinner_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinner1.setAdapter(adapter);


        adapter1 = ArrayAdapter.createFromResource(this, R.array.spinner_location, R.layout.custom_spinner_item);
        adapter1.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinner2.setAdapter(adapter1);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

        uploadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent1, 101);
            }
        });


    }

    private void register() {
        initialize();
        if(!validate()){
            showAlertDialog();


        }else{
            signupSuccess();
        }
    }

    private void showAlertDialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.dialog_style);
        dialog.setTitle("ERROR");
        dialog.setMessage("REGISTRATION FAILED !!!");
        dialog.setIcon(R.drawable.ic_error_black_24dp);
        dialog.show();
    }

    private void signupSuccess() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("fullname", fullnameValue);
        contentValues.put("age", ageValue);
        contentValues.put("phone", phoneValue);
        contentValues.put("email", emailValue);
        contentValues.put("location", locationValue);
        contentValues.put("bloodgroup", bloodgroupValue);
        contentValues.put("username", usernameValue);
        contentValues.put("password", passwordValue);
        contentValues.put("gender", genderValue);
        contentValues.put("image", getBlob(bitmap));


        databaseHelper.insertUser(contentValues);

        Toast.makeText(this, "User registered successfully :)", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);




    }


    private boolean validate() {
        boolean valid = true;
        if(ageValue.isEmpty()||ageValue.length()>2) {
            age.setError("Please enter valid age!!!");
            valid = false;
        }
        if(phoneValue.isEmpty() || phoneValue.length()>10 || phoneValue.length()<10) {
            phone.setError(" Please enter 10 digit phone number !!!");
            valid = false;
        }
        if (emailValue.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()){
            email.setError("{Please enter a valid E-mail ID");
            valid  = false;
        }
        if(fullnameValue.isEmpty() || fullnameValue.length()>25){
            fullname.setError("Please make a valid input");

        }
        if(spinner1.getSelectedItemPosition()==0 | spinner2.getSelectedItemPosition()==0){
            Toast.makeText(this, "Please select both your bloodgroup and location before proceeding !!!", Toast.LENGTH_SHORT).show();

            valid = false;
        }

        if(usernameValue.isEmpty() || usernameValue.length()>16){
            username.setError("Please enter a unique username not more 16 digits !!!");
            valid = false;
        }



        return valid;
    }

    private void initialize() {
        fullnameValue = fullname.getText().toString().trim();
        ageValue = age.getText().toString().trim();
        phoneValue = phone.getText().toString().trim();
        emailValue = email.getText().toString().trim();
        bloodgroupValue = spinner1.getSelectedItem().toString().trim();
        locationValue = spinner2.getSelectedItem().toString().trim();
        usernameValue = username.getText().toString().trim();
        passwordValue = password.getText().toString().trim();
        reenterpasswordValue = reenterpassword.getText().toString().trim();
        RadioButton CheckedBtn = findViewById(gender.getCheckedRadioButtonId());
        genderValue = CheckedBtn.getText().toString().trim();



    }


    Bitmap bitmap;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {

            bitmap = (Bitmap) data.getExtras().get("data");

            imageView.setImageBitmap(bitmap);
        }
        else{
            Toast.makeText(this, "Please select an image", Toast.LENGTH_LONG).show();
        }



    }


    public static byte[] getBlob(Bitmap bitmap) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bArray = bos.toByteArray();
        return bArray;
    }

    public static Bitmap getBitmap(byte[] byteArray) {

        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
    }
}
