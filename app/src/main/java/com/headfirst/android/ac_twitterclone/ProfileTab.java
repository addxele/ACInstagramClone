package com.headfirst.android.ac_twitterclone;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTab extends Fragment {
    private EditText edtProfileName, edtBio, edtProfession, edtHobbies, edtFavoriteSport;
    private Button btnUpdateInfo;

    public ProfileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);
        edtProfileName = view.findViewById(R.id.edtProfileName);
        edtBio = view.findViewById(R.id.edtBio);
        edtProfession = view.findViewById(R.id.edtProfession);
        edtHobbies = view.findViewById(R.id.edtHobbies);
        edtFavoriteSport = view.findViewById(R.id.edtFavoriteSport);
        btnUpdateInfo = view.findViewById(R.id.btnUpdateInfo);

        ParseUser appUser = ParseUser.getCurrentUser();

        if (appUser.get("profileName") == null) {
            edtProfileName.setText("");
        }else{
            edtProfileName.setText(appUser.get("profileName") +"");
        }
        if (appUser.get("bio") == null) {
            edtBio.setText("");
        }else {
            edtBio.setText(appUser.get("bio") +"");
        }
        if (appUser.get("profession") == null) {
            edtProfession.setText("");
        }else {
            edtProfession.setText(appUser.get("profession") + "");
        }
        if (appUser.get("hobbies") == null) {
            edtHobbies.setText("");
        }else {
            edtHobbies.setText(appUser.get("hobbies") + "");
        }
        if (appUser.get("favoriteSport") == null) {
            edtFavoriteSport.setText("");
        }else {
            edtFavoriteSport.setText(appUser.get("favoriteSport") + "");
        }

        btnUpdateInfo.setOnClickListener(v -> {
            appUser.put("profileName", edtProfileName.getText().toString());
            appUser.put("bio", edtBio.getText().toString());
            appUser.put("profession", edtProfession.getText().toString());
            appUser.put("hobbies", edtHobbies.getText().toString());
            appUser.put("favoriteSport", edtFavoriteSport.getText().toString());
            appUser.saveInBackground((e) ->{
                if (e == null) {
                    FancyToast.makeText(getContext(), "info updated successfully", FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();
                }else {
                    FancyToast.makeText(getContext(), "info update failed", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                }
            });
        });
        return view;
    }

}
