package com.headfirst.android.ac_twitterclone;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;

import libs.mjn.prettydialog.PrettyDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserTab extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private ListView listView;
    private ArrayList<String> arrayList;
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
        listView.setOnItemClickListener(UserTab.this);
        listView.setOnItemLongClickListener(UserTab.this);

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(getContext(), UserPosts.class);
        intent.putExtra("username", arrayList.get(position));
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereEqualTo("username", arrayList.get(position));
        parseQuery.getFirstInBackground(((object, e) -> {
            if (object != null && e == null) {
//                FancyToast.makeText(getContext(), object.get("profession") + "", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                final PrettyDialog prettyDialog = new PrettyDialog(getContext());
                prettyDialog.setTitle(object.getUsername() + " Info")
                        .setMessage(object.get("bio") + "\n"
                                + object.get("profession") + "\n"
                                + object.get("hobbies") + "\n"
                                + object.get("favoriteSport"))
                        .setIcon(R.drawable.person).addButton(
                        "OK", // button text
                        R.color.pdlg_color_white,// button text color
                        R.color.pdlg_color_green,// button background color
                        () -> { // button onClick listener
                            prettyDialog.dismiss();
                        }
                ).show();
            }
        }));
        return true;
    }
}
