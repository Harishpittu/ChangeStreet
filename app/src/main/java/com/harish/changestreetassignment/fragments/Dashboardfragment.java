package com.harish.changestreetassignment.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by harish on 14/09/16.
 */
public class Dashboardfragment extends Fragment {

    public Dashboardfragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= new View(getContext());
        // = inflater.inflate(R.layout.custom_view_page, container, false);
        return view;
    }
}

