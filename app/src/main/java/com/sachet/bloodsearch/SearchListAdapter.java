package com.sachet.bloodsearch;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sachet.bloodsearch.helper.UserInfo;

import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by AJ on 3/3/2018.
 */

public class SearchListAdapter extends ArrayAdapter<UserInfo> {


    Context context;
    public SearchListAdapter(@NonNull Context context, ArrayList<UserInfo> list) {
        super(context, 0, list);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_search_list_view,null);
        TextView username = view.findViewById(R.id.bloodlist_username);
        TextView address = view.findViewById(R.id.bloolist_city);
        TextView bloodgroup = view.findViewById(R.id.bloodlist_bloodgroup);
        TextView contact = view.findViewById(R.id.bloodlist_contact_no);
        TextView email = view.findViewById(R.id.bloodlist_email);
        CircleImageView userimage = view.findViewById(R.id.bloodlist_userimage);

        UserInfo info = getItem(position);

        username.setText(info.fullname);
        address.setText("City:" +"\t" +info.location);
        bloodgroup.setText("Blood Group:" +"\t" +info.bloodgroup);
        contact.setText("Contact:" +"\t" +info.phone);
        contact.setLinkTextColor(Color.RED);
        email.setText("Email:" +"\t" +info.email);
        email.setLinkTextColor(Color.RED);



        if(info.image != null)
            userimage.setImageBitmap(RegisterActivity.getBitmap(info.image));


        return view;
    }
}
