package com.alryada.etqan.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.alryada.etqan.R;
import com.alryada.etqan.SignUpActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_profile_discover extends Fragment {


    public fragment_profile_discover() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_discover, container, false);
        Button signup = view.findViewById(R.id.btnSignUpDis);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SignUpActivity.class));
                getActivity().finish();
            }
        });
        return view;
    }

}
