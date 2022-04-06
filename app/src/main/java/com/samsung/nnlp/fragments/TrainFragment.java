package com.samsung.nnlp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samsung.nnlp.R;
import com.samsung.nnlp.models.neuronet.NeuralNetwork;


public class TrainFragment extends Fragment {
    private NeuralNetwork network;

    public TrainFragment() {}


    public static TrainFragment newInstance() {
        TrainFragment fragment = new TrainFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        network = (NeuralNetwork) getArguments().getSerializable("network");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_train, container, false);
    }
}