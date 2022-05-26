package com.samsung.netlearn.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.samsung.netlearn.R;


public class MenuFragment extends Fragment {

    public MenuFragment() {}

    public static MenuFragment newInstance() {
        MenuFragment fragment = new MenuFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        Button wb = view.findViewById(R.id.wb);
        Button lib = view.findViewById(R.id.lib);
        Button stgs = view.findViewById(R.id.stgs);

        wb.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.to_workbench, null));
        lib.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.to_library, null));
        stgs.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.to_settings, null));
        return view;
    }
}