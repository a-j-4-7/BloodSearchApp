package com.sachet.bloodsearch;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DonorListFragment extends Fragment {


    public DonorListFragment() {


    }

  ListView listView;
    DatabaseHelper databasehelper;
    BloodListAdapter bloodListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donor_list, null);
        databasehelper = new DatabaseHelper(getContext());
        listView = view.findViewById(R.id.recyclerview_bloodlist);

        bloodListAdapter = new BloodListAdapter(getActivity(),databasehelper.getUserList());

        listView.setAdapter(bloodListAdapter);


        return view;



    }

}


