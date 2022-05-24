package com.samsung.nnlp.fragments.wb;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.samsung.nnlp.R;
import com.samsung.nnlp.fragments.wb.datatype.InputDigitFragment;
import com.samsung.nnlp.fragments.wb.datatype.InputImageFragment;
import com.samsung.nnlp.models.neuronet.NeuralNetwork;
import com.samsung.nnlp.models.threads.TrainThread;

import java.util.ArrayList;
import java.util.List;

public class DatasetFragment extends Fragment {
    private NeuralNetwork network;
    private List<double[]> in;
    private List<double[]> out;

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
            network = (NeuralNetwork) getArguments().getSerializable("nn");
            switch (getArguments().getString("inputType")) {
                case "Digits": input = InputDigitFragment.newInstance(); break;
                case "Image": input = new InputImageFragment(getContext(),false); break;
                case "ImageRGB": input = new InputImageFragment(getContext(),true); break;
            }
            switch (getArguments().getString("outputType")) {
                case "Digits": output = InputDigitFragment.newInstance(); break;
                case "Image": output = new InputImageFragment(getContext(),false); break;
                case "ImageRGB": output = new InputImageFragment(getContext(),true); break;
            }

            Bundle bundle = new Bundle();
            bundle.putInt("size", network.getLayers().get(0).getInput().length);
            input.setArguments(bundle);
            ((FrameLayout) view.findViewById(R.id.input_dataset)).addView(input.onCreateView(inflater, view.findViewById(R.id.input_dataset), savedInstanceState));

            bundle = new Bundle();
            bundle.putInt("size", network.getLayers().get(network.getLayers().size() - 1).getNeurons().length);
            output.setArguments(bundle);
            ((FrameLayout) view.findViewById(R.id.output_dataset)).addView(output.onCreateView(inflater, view.findViewById(R.id.output_dataset), savedInstanceState));

        }
        Button button = view.findViewById(R.id.start_train);
        button.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();

            in = new ArrayList<>();
            ArrayList<String> unparsed = input.getArguments().getStringArrayList("data");
            for (String s : unparsed) {
                String[] split = s.split(" ");
                double[] data = new double[network.getLayers().get(0).getInput().length];
                for (int i = 0; i < data.length; i++) data[i] = Double.parseDouble(split[i]);
                in.add(data);
            }

            out = new ArrayList<>();
            unparsed = output.getArguments().getStringArrayList("data");
            for (String s : unparsed) {
                String[] split = s.split(" ");
                double[] data = new double[network.getLayers().get(network.getLayers().size() - 1).getNeurons().length];
                for (int i = 0; i < data.length; i++) data[i] = Double.parseDouble(split[i]);
                out.add(data);
            }

            bundle.putSerializable("thread", new TrainThread(network, in, out));
            bundle.putSerializable("nn", network);
            bundle.putString("inputType", getArguments().getString("inputType"));

            Navigation.findNavController(view).navigate(R.id.train_to_wb, bundle);
        });

        return view;
    }
}