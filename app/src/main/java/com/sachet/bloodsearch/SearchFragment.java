package com.sachet.bloodsearch;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.sachet.bloodsearch.helper.UserInfo;
import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    public SearchFragment() {

    }

    DatabaseHelper databaseHelper;
    AppCompatSpinner bloodgroup_spinner, location_spinner;
    Button search;
    ArrayAdapter<CharSequence> adapter, adapter1;
    SearchListAdapter searchListAdapter;
    ListView listView;


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, null);

        location_spinner = view.findViewById(R.id.location_spinner);
        bloodgroup_spinner = view.findViewById(R.id.bloodgroup_spinner);
        listView = view.findViewById(R.id.search_list_view);
        search = view.findViewById(R.id.search_btn);


        adapter = ArrayAdapter.createFromResource(getContext(), R.array.spinner_bloodgroup, R.layout.search_custom_spinner);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        bloodgroup_spinner.setAdapter(adapter);


        adapter1 = ArrayAdapter.createFromResource(getContext(), R.array.spinner_location, R.layout.search_custom_spinner);
        adapter1.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        location_spinner.setAdapter(adapter1);


        bloodgroup_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        location_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchDonor();


            }
        });


        return view;


    }

    private void searchDonor() {

        if (!validate()) {

        } else {
            onSuccess();
        }
    }

    private boolean validate() {
        boolean valid = true;

        if (bloodgroup_spinner.getSelectedItemPosition() == 0 && location_spinner.getSelectedItemPosition() == 0) {
            Alerter.create(getActivity())
                    .setDuration(3000)
                    .setBackgroundColorRes(R.color.colorPrimary)
                    .enableSwipeToDismiss()
                    .setTitle("ERROR !!!!")
                    .setText("PLEASE SELECT YOUR BLOODGROUP AND LOCATION !!!")
                    .setIcon(R.drawable.ic_sentiment_dissatisfied_black_24dp)
                    .showIcon(true)
                    .enableIconPulse(true)
                    .show();
            valid = false;
        } else if (bloodgroup_spinner.getSelectedItemPosition() == 0) {
            Toast.makeText(getActivity(), "Please select your bloodgroup !!!", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (location_spinner.getSelectedItemPosition() == 0) {
            Toast.makeText(getActivity(), "Please select your location !!!", Toast.LENGTH_SHORT).show();
            valid = false;
        }


        return valid;
    }

    private void onSuccess() {


        databaseHelper = new DatabaseHelper(getContext());

        String bloodgroupValue = bloodgroup_spinner.getSelectedItem().toString();
        String locationValue = location_spinner.getSelectedItem().toString();

        searchListAdapter = new SearchListAdapter(getContext(), databaseHelper.getSearchUserList(bloodgroupValue, locationValue));

        ArrayList<UserInfo> userInfos = databaseHelper.getSearchUserList(bloodgroupValue, locationValue);
        if (userInfos != null) {
            listView.setAdapter(searchListAdapter);
//                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
//                            View view1 = inflater.inflate(R.layout.search_list_item_custom_dialog,null);
//                            dialog.setView(view1);
//                            AlertDialog alertDialog = dialog.create();
//                            alertDialog.show();
//                            RelativeLayout callbtn = view1.findViewById(R.id.call);
//                            callbtn.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    Toast.makeText(getActivity(), "Calling User", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                            RelativeLayout messagebtn = view1.findViewById(R.id.message);
//                            RelativeLayout emailbtn = view1.findViewById(R.id.email);
//                        }
//                    });


        } else {
            listView.setAdapter(null);
            Toast.makeText(getContext(), "No Results found!!!", Toast.LENGTH_SHORT).show();

        }
    }

    private void selectSpinner() {

        if (bloodgroup_spinner.getSelectedItem().equals("Select Your Blood Group")) {

            Toast.makeText(getActivity(), "Please select your bloodgroup !!!", Toast.LENGTH_SHORT).show();
        } else if (location_spinner.getSelectedItemPosition() == 0) {
            Toast.makeText(getActivity(), "Please select your location !!!", Toast.LENGTH_SHORT).show();
        } else if (bloodgroup_spinner.getSelectedItem().equals("Select Your Blood Group") && location_spinner.getSelectedItem().equals("Select Your Location")) {
            Toast.makeText(getActivity(), "Please select your bloodgroup and location !!!", Toast.LENGTH_SHORT).show();

        }
    }


}
