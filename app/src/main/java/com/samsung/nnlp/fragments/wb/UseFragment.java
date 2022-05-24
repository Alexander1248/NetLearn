package com.samsung.nnlp.fragments.wb;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.samsung.nnlp.R;
import com.samsung.nnlp.fragments.wb.datatype.InputDigitFragment;
import com.samsung.nnlp.fragments.wb.datatype.InputImageFragment;
import com.samsung.nnlp.models.neuronet.NeuralNetwork;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UseFragment extends Fragment {
    private NeuralNetwork network;
    private EditText[] inputs;
    private TextView[] outputs;

   public UseFragment() {}

   public static UseFragment newInstance(String param1, String param2) {
        UseFragment fragment = new UseFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_use, container, false);


        if (getArguments() != null) {
            network = (NeuralNetwork) getArguments().getSerializable("nn");
            LinearLayout layout = view.findViewById(R.id.input);
            switch (getArguments().getString("inputType")) {
                case "Digits": {
                    inputs = new EditText[network.getLayers().get(0).getInput().length];
                    for (int i = 0; i < inputs.length; i++) {
                        inputs[i] = new EditText(getActivity());
                        inputs[i].setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
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
                    outputs = new TextView[network.getLayers().get(network.getLayers().size() - 1).getNeurons().length];
                    for (int i = 0; i < outputs.length; i++) {
                        outputs[i] = new EditText(getActivity());
                        outputs[i].setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
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
            double[] in = new double[network.getLayers().get(0).getInput().length];
            for (int i = 0; i < inputs.length; i++) in[i] = Double.parseDouble(inputs[i].getText().toString());

            network.setInput(in);

            int ol = network.getLayers().get(network.getLayers().size() - 1).getNeurons().length;
            for (int i = 0; i < ol; i++) outputs[i].setText(Double.toString(network.getOutput(i)));
        });

        return view;
    }
}