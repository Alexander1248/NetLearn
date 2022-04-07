package com.samsung.nnlp.fragments.wb.datatype;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samsung.nnlp.R;
import com.samsung.nnlp.models.adapters.DigitAdapter;
import com.samsung.nnlp.models.neuronet.NeuralNetwork;


public class DigitDatasetFragment extends Fragment {
    private RecyclerView recycler;

   public DigitDatasetFragment() {}


    public static DigitDatasetFragment newInstance() {
        DigitDatasetFragment fragment = new DigitDatasetFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_digit_dataset, container, false);
        recycler = view.findViewById(R.id.digit_data_recycler);
        recycler.setAdapter(new DigitAdapter(getArguments().getInt("size")));


        return view;
    }
}