package com.samsung.nnlp.fragments.wb;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samsung.nnlp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UseFragment extends Fragment {

   public UseFragment() {}

   public static UseFragment newInstance(String param1, String param2) {
        UseFragment fragment = new UseFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_use, container, false);

        return view;
    }
}