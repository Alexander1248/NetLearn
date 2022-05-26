package com.samsung.netlearn.fragments.wb;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.samsung.netlearn.R;
import com.samsung.netlearn.fragments.wb.datatype.InputDigitFragment;
import com.samsung.netlearn.fragments.wb.datatype.InputImageFragment;
import com.samsung.netlearn.models.threads.TrainThread;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;

import java.util.ArrayList;

public class DatasetFragment extends Fragment {
    private MultiLayerPerceptron network;

    private Fragment input;
    private Fragment output;

    public DatasetFragment() {}

    public static DatasetFragment newInstance() {
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
        View view = inflater.inflate(R.layout.fragment_dataset, container, false);
        if (getArguments() != null) {
            network = (MultiLayerPerceptron) getArguments().getSerializable("nn");
            switch (getArguments().getString("inputType")) {
                case "Digits": input = new InputDigitFragment(); break;
                case "Image": input = new InputImageFragment(getContext(),false); break;
                case "ImageRGB": input = new InputImageFragment(getContext(),true); break;
            }
            switch (getArguments().getString("outputType")) {
                case "Digits": output = new InputDigitFragment(); break;
                case "Image": output = new InputImageFragment(getContext(),false); break;
                case "ImageRGB": output = new InputImageFragment(getContext(),true); break;
            }

            Bundle bundle = new Bundle();
            bundle.putInt("size", network.getLayerAt(0).getNeuronsCount() - 1);
            input.setArguments(bundle);
            ((FrameLayout) view.findViewById(R.id.input_dataset)).addView(input.onCreateView(inflater, view.findViewById(R.id.input_dataset), savedInstanceState));

            bundle = new Bundle();
            bundle.putInt("size", network.getLayerAt(network.getLayersCount() - 1).getNeuronsCount());
            output.setArguments(bundle);
            ((FrameLayout) view.findViewById(R.id.output_dataset)).addView(output.onCreateView(inflater, view.findViewById(R.id.output_dataset), savedInstanceState));

        }
        Button button = view.findViewById(R.id.start_train);
        button.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("nn", network);
            bundle.putString("inputType", getArguments().getString("inputType"));
            bundle.putString("outputType", getArguments().getString("outputType"));
            boolean state = true;

            ArrayList<String> unparsedIn = input.getArguments().getStringArrayList("data");
            ArrayList<String> unparsedOut = output.getArguments().getStringArrayList("data");

            if (unparsedIn != null && unparsedOut != null && unparsedIn.size() > 0 && unparsedIn.size() == unparsedOut.size()) {
                DataSet dataset = new DataSet(network.getLayerAt(0).getNeuronsCount() - 1, network.getLayerAt(network.getLayersCount() - 1).getNeuronsCount());

                for (int j = 0; j < unparsedIn.size(); j++) {
                    double[] in = new double[network.getLayerAt(0).getNeuronsCount() - 1];
                    double[] out = new double[network.getLayerAt(network.getLayersCount() - 1).getNeuronsCount()];
                    String[] inSplit = unparsedIn.get(j).split(" ");

                    if (inSplit.length != in.length) {
                        Toast.makeText(getContext(), "Input size no match", Toast.LENGTH_SHORT).show();
                        state = false;
                        break;
                    }

                    String[] outSplit = unparsedOut.get(j).split(" ");
                    if (outSplit.length != out.length) {
                        Toast.makeText(getContext(), "Output size no match", Toast.LENGTH_SHORT).show();
                        state = false;
                        break;
                    }

                    for (int i = 0; i < in.length; i++) in[i] = Double.parseDouble(inSplit[i]);
                    for (int i = 0; i < out.length; i++) out[i] = Double.parseDouble(outSplit[i]);
                    dataset.addRow(new DataSetRow(in, out));
                }

                if (state) bundle.putSerializable("thread", new TrainThread(network, dataset));
                Navigation.findNavController(view).navigate(R.id.train_to_wb, bundle);
            }
        });

        return view;
    }
}