package com.samsung.nnlp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.samsung.nnlp.R;
import com.samsung.nnlp.models.neuronet.AFunction;
import com.samsung.nnlp.models.neuronet.NeuralNetwork;


public class TrainFragment extends Fragment {
    private NeuralNetwork network;
    private String inType;
    private String outType;

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
        if (getArguments() == null) network = new NeuralNetwork();
        else network = (NeuralNetwork) getArguments().getSerializable("nn");

        View view = inflater.inflate(R.layout.fragment_train, container, false);
        ((EditText) view.findViewById(R.id.learning_rate)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() > 0) network.setLearningRate(Double.parseDouble(charSequence.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ((EditText) view.findViewById(R.id.momentum)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() > 0) network.setMomentum(Double.parseDouble(charSequence.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ((Spinner) view.findViewById(R.id.input_type)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               inType = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ((Spinner) view.findViewById(R.id.output_type)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                outType = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Bundle bundle = new Bundle();
        bundle.putSerializable("nn", network);
        bundle.putSerializable("inputType", inType);
        bundle.putSerializable("outputType", outType);
        ((Button) view.findViewById(R.id.set_dataset)).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.train_to_dataset, bundle));
        return view;
    }
}