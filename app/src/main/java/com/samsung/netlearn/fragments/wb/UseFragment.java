package com.samsung.netlearn.fragments.wb;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.samsung.netlearn.R;

import org.neuroph.nnet.MultiLayerPerceptron;

public class UseFragment extends Fragment {
    private MultiLayerPerceptron network;
    private EditText[] inputs;
    private TextView[] outputs;

   public UseFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_use, container, false);


        if (getArguments() != null) {
            network = (MultiLayerPerceptron) getArguments().getSerializable("nn");

            LinearLayout layout = view.findViewById(R.id.input);
            switch (getArguments().getString("inputType")) {
                case "Digits": {
                    inputs = new EditText[network.getLayerAt(0).getNeuronsCount() - 1];
                    for (int i = 0; i < inputs.length; i++) {
                        inputs[i] = new EditText(getActivity());
                        inputs[i].setGravity(Gravity.CENTER);
                        inputs[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1.0f));
                        layout.addView(inputs[i]);
                    }
                    break;
                }
                case "Image": {
                    break;
                }
                case "ImageRGB": {
                    break;
                }
            }
            layout = view.findViewById(R.id.output);
            switch (getArguments().getString("outputType")) {
                case "Digits": {
                    outputs = new TextView[network.getLayerAt(network.getLayersCount() - 1).getNeurons().length];
                    for (int i = 0; i < outputs.length; i++) {
                        outputs[i] = new TextView(getActivity());
                        outputs[i].setGravity(Gravity.CENTER);
                        outputs[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1.0f));
                        layout.addView(outputs[i]);
                    }
                    break;
                }
                case "Image": {
                    break;
                }
                case "ImageRGB": {
                    break;
                }
            }
        }
        Button button = view.findViewById(R.id.calculate);
        button.setOnClickListener(view1 -> {
            double[] in = new double[inputs.length];
            for (int i = 0; i < inputs.length; i++) in[i] = Double.parseDouble(inputs[i].getText().toString());

            network.setInput(in);
            network.calculate();

            for (int i = 0; i < network.getOutputsCount(); i++) outputs[i].setText(String.format("%.2f", network.getOutput()[i]));
        });

        return view;
    }
}