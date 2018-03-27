package com.sachet.bloodsearch;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.sachet.bloodsearch.helper.UserDataSharedPreference;
import com.tapadoo.alerter.Alerter;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by AJ on 2/20/2018.
 */

public class EditDetailsActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();


    String userName;

    UserDataSharedPreference userDataSharedPreference;


    Toolbar toolbar;
    ArrayAdapter<CharSequence> adapter1, adapter2;
    AppCompatEditText ed_fullname, ed_age, ed_email, ed_phone, ed_username, ed_password, ed_reenterpassword;
    String ed_genderValue, ed_fullnameValue, ed_ageValue, ed_emailValue, ed_phoneValue, ed_usernameValue, ed_passwordValue, ed_reenterpasswordValue, ed_bloodgroupValue, ed_locationValue;
    RadioGroup ed_gender;
    DatabaseHelper databaseHelper;
    TextInputLayout ed_passwordLayout, ed_reenterpasswordLayout;
    AppCompatSpinner spinner1, spinner2;
    Button uploadimg, confirm, cancel;
    CircleImageView ed_imageView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_details_activityy);

        toolbar = findViewById(R.id.toolbar_design);
        spinner1 = findViewById(R.id.edit_details_location_spinner);
        spinner2 = findViewById(R.id.edit_details_bloodgroup_spinner);
        uploadimg = findViewById(R.id.edit_details_uploadimg_btn);
        confirm = findViewById(R.id.edit_details_confirm_btn);
        cancel = findViewById(R.id.edit_details_cancel_btn);

        ed_imageView = findViewById(R.id.edit_details_userimg);
        ed_username = findViewById(R.id.edit_details_username);
        ed_password = findViewById(R.id.edit_details_password);
        ed_fullname = findViewById(R.id.edit_details_fullname);
        ed_age = findViewById(R.id.edit_details_age);
        ed_phone = findViewById(R.id.edit_details_phone);
        ed_email = findViewById(R.id.edit_details_email);
        ed_reenterpassword = findViewById(R.id.edit_details_reenter_password);
        ed_passwordLayout = findViewById(R.id.edit_details_password_layout);
        ed_reenterpasswordLayout = findViewById(R.id.edit_details_reenter_password_layout);
        ed_gender = findViewById(R.id.edit_details_gender);

        userDataSharedPreference = new UserDataSharedPreference();
        databaseHelper = new DatabaseHelper(this);

        setSupportActionBar(toolbar);
        getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ed_passwordLayout.setCounterEnabled(true);
        ed_passwordLayout.setCounterMaxLength(16);
        ed_reenterpasswordLayout.setCounterEnabled(true);
        ed_reenterpasswordLayout.setCounterMaxLength(16);

        ed_reenterpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String pass = ed_password.getText().toString();
                if (ed_reenterpassword.getText().toString().equals(pass) && pass.length() >= 8) {
                    ed_reenterpasswordLayout.setErrorEnabled(false);
                } else {
                    ed_reenterpasswordLayout.setErrorEnabled(true);
                    ed_reenterpasswordLayout.setError("Please enter a valid password not less than 8 digits!!!");

                }

            }
        });


        adapter1 = ArrayAdapter.createFromResource(this, R.array.spinner_location, R.layout.custom_spinner_item);
        adapter1.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinner1.setAdapter(adapter1);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapter2 = ArrayAdapter.createFromResource(this, R.array.spinner_bloodgroup, R.layout.custom_spinner_item);
        adapter2.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinner2.setAdapter(adapter2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateDetails();
            }
        });


        uploadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 101);
            }
        });


    }

    private void UpdateDetails() {

        edInitialize();
        if (!validate()) {
            showAlertDialog();


        } else {
            updateSuccess();
        }
    }

    private void showAlertDialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.dialog_style);
        dialog.setTitle("ERROR");
        dialog.setMessage("UPDATE FAILED !!!");
        dialog.setIcon(R.drawable.ic_error_black_24dp);
        dialog.show();
    }

    private void updateSuccess() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("fullname", ed_fullnameValue);
        contentValues.put("age", ed_ageValue);
        contentValues.put("phone", ed_phoneValue);
        contentValues.put("email", ed_emailValue);
        contentValues.put("location", ed_locationValue);
        contentValues.put("bloodgroup", ed_bloodgroupValue);
        contentValues.put("username", ed_usernameValue);
        contentValues.put("password", ed_passwordValue);
        contentValues.put("gender", ed_genderValue);
        contentValues.put("image", getBlob(bitmap));

        getSharedPreferenceValue();
        databaseHelper.updateUser(userName, contentValues);

        Toast.makeText(this, "UPDATE SUCCESSFULL :)", Toast.LENGTH_SHORT).show();
        finish();


    }


    private boolean validate() {
        boolean valid = true;
        if (ed_ageValue.isEmpty() || ed_ageValue.length() > 2) {
            ed_age.setError("Please enter valid age!!!");
            valid = false;
        }
        if (ed_phoneValue.isEmpty() || ed_phoneValue.length() > 10 || ed_phoneValue.length() < 10) {
            ed_phone.setError(" Please enter 10 digit phone number !!!");
            valid = false;
        }
        if (ed_emailValue.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(ed_emailValue).matches()) {
            ed_email.setError("{Please enter a valid E-mail ID");
            valid = false;
        }
        if (ed_fullnameValue.isEmpty() || ed_fullnameValue.length() > 25) {
            ed_fullname.setError("Please make a valid input");

        }
        if (spinner1.getSelectedItemPosition() == 0 | spinner2.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select both your bloodgroup and location before proceeding !!!", Toast.LENGTH_SHORT).show();

            valid = false;
        }

        if (ed_usernameValue.isEmpty() || ed_usernameValue.length() > 16) {
            ed_username.setError("Please enter a unique username not more than 16 digits !!!");
            valid = false;
        }


        return valid;
    }

    private void edInitialize() {
        ed_fullnameValue = ed_fullname.getText().toString().trim();
        ed_ageValue = ed_age.getText().toString().trim();
        ed_phoneValue = ed_phone.getText().toString().trim();
        ed_emailValue = ed_email.getText().toString().trim();
        ed_bloodgroupValue = spinner2.getSelectedItem().toString().trim();
        ed_locationValue = spinner1.getSelectedItem().toString().trim();
        ed_usernameValue = ed_username.getText().toString().trim();
        ed_passwordValue = ed_password.getText().toString().trim();
        ed_reenterpasswordValue = ed_reenterpassword.getText().toString().trim();
        RadioButton CheckedBtn = findViewById(ed_gender.getCheckedRadioButtonId());
        ed_genderValue = CheckedBtn.getText().toString().trim();


    }


    Bitmap bitmap;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {

            bitmap = (Bitmap) data.getExtras().get("data");

            ed_imageView.setImageBitmap(bitmap);
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

    private void getSharedPreferenceValue() {
        userName = userDataSharedPreference.getUserName(this);
        Log.i(TAG,"userDataSharedPreferenceValue = " +userName);

    }

}
