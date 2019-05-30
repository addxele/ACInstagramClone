package com.headfirst.android.ac_twitterclone;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserTab extends Fragment {
    private ListView listView;
    private ArrayList arrayList;
    private ArrayAdapter arrayAdapter;

    public UserTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_tab, container, false);

        listView = view.findViewById(R.id.listView);
        arrayList = new ArrayList();
        arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, arrayList);
        TextView txtLoading = view.findViewById(R.id.txtLoading);

        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();

        parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        parseQuery.findInBackground((users, e) -> {
            if (e == null) {
                if (users.size() > 0) {
                    for (ParseUser parseUser : users) {
                        arrayList.add(parseUser.getUsername());
                    }
                    listView.setAdapter(arrayAdapter);
                    txtLoading.animate().alpha(0).setDuration(1000);
                    listView.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }

}
