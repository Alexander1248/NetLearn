package com.samsung.nnlp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samsung.nnlp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DatasetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatasetFragment extends Fragment {

    public DatasetFragment() {}

    public static DatasetFragment newInstance(String param1, String param2) {
        DatasetFragment fragment = new DatasetFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dataset, container, false);
    }
}