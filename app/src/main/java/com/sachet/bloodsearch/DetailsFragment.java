package com.sachet.bloodsearch;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sachet.bloodsearch.helper.UserDataSharedPreference;
import com.sachet.bloodsearch.helper.UserInfo;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class DetailsFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();
    CircleImageView imageView;
    TextView fn, un, age, gender, bloodgroup, city, contact, email;
    DatabaseHelper databaseHelper;
    Button ed_button;
    String userName;
    UserDataSharedPreference userDataSharedPreference;
   private byte[] userPhoto;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, null);
        userDataSharedPreference = new UserDataSharedPreference();
        databaseHelper = new DatabaseHelper(getActivity());
        ed_button = view.findViewById(R.id.edit_details_btn);
        fn = view.findViewById(R.id.details_fullname);
        un = view.findViewById(R.id.details_username);
        age = view.findViewById(R.id.detail_age);
        gender = view.findViewById(R.id.detail_gender);
        bloodgroup = view.findViewById(R.id.detail_bloodgroup);
        city = view.findViewById(R.id.detail_city);
        contact = view.findViewById(R.id.detail_contact);
        email = view.findViewById(R.id.detail_email);
        fn = view.findViewById(R.id.details_fullname);
        imageView = view.findViewById(R.id.detail_userimg);
     getSharedPreferenceValue();
     populateData();

     setClickButton();


//        retrieveDataFromDatabase();


//        Log.i(TAG, "onCreateView: username " + String.valueOf(userModel.getUserName()));
        return view;
    }


    public void populateData(){

        UserInfo info = databaseHelper.getUserDetails(userName);
        fn.setText(info.fullname);
        un.setText("Username:"+"\t"+info.username);
        age.setText(info.age);
        gender.setText(info.gender);
        bloodgroup.setText(info.bloodgroup);
        city.setText(info.location);
        contact.setText(info.phone);
        email.setText(info.email);

        if (info.image != null)
       imageView.setImageBitmap(getBitmap(info.image));
    }


    //    private void setDatabaseValue() {
//        DataBaseUserModel model = dataModel.get(0);
//        fn.setText(model.getFullName());
//        un.setText("Username : " +"\t"+model.getUserName());
//        age.setText(model.getAge());
//        gender.setText(model.getGender());
//        bloodgroup.setText(model.getBloodGroup());
//        city.setText(model.getCity());
//        contact.setText(model.getContact());
//        email.setText(model.getEmail());
//        userPhoto = model.getImage();
//        Bitmap bitmap = BitmapFactory.decodeByteArray(userPhoto,0,userPhoto.length);
//        imageView.setImageBitmap(bitmap);
//    }
//
//    private void retrieveDataFromDatabase() {
//        dataModel = databaseHelper.getUserInfo(userName);
//    }

    private void setClickButton() {
        ed_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getSharedPreferenceValue() {
        userName = userDataSharedPreference.getUserName(getContext());
        Log.i(TAG, "getSharedPreferenceValue: userName " + userName);
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



}
