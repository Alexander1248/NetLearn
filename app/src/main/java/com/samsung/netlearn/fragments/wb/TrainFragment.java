package com.samsung.netlearn.fragments.wb;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.samsung.netlearn.R;

import org.neuroph.nnet.MultiLayerPerceptron;


public class TrainFragment extends Fragment {
    private MultiLayerPerceptron network;
    private String inType = "Digits";
    private String outType = "Digits";

    public TrainFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() == null) network = new MultiLayerPerceptron();
        else {
            network = (MultiLayerPerceptron) getArguments().getSerializable("nn");
            inType = getArguments().getString("inputType");
            outType = getArguments().getString("outputType");
        }

        View view = inflater.inflate(R.layout.fragment_train, container, false);

        ((EditText) view.findViewById(R.id.learning_rate)).setText(Double.toString(network.getLearningRule().getLearningRate()));
        ((EditText) view.findViewById(R.id.learning_rate)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0)
                    network.getLearningRule().setLearningRate(Double.parseDouble(charSequence.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Bundle bundle = new Bundle();
        bundle.putString("inputType", inType);
        bundle.putString("outputType", outType);
        bundle.putSerializable("nn", network);
        view.findViewById(R.id.set_dataset).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.train_to_dataset, bundle));
        return view;
    }
}