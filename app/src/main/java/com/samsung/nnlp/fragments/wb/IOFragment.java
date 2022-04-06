package com.samsung.nnlp.fragments.wb;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samsung.nnlp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IOFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IOFragment extends Fragment {

   public IOFragment() {}

   public static IOFragment newInstance(String param1, String param2) {
        IOFragment fragment = new IOFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_io, container, false);
    }
}