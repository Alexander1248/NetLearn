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
import com.samsung.nnlp.fragments.wb.datatype.DigitDatasetFragment;
import com.samsung.nnlp.fragments.wb.datatype.ImageDatasetFragment;
import com.samsung.nnlp.models.neuronet.NeuralNetwork;
import com.samsung.nnlp.models.threads.TrainThread;

import java.util.List;
import java.util.zip.Inflater;

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
                case "Digits": input = DigitDatasetFragment.newInstance(); break;
                case "Image": input = ImageDatasetFragment.newInstance(false); break;
                case "ImageRGB": input = ImageDatasetFragment.newInstance(true); break;
            }
            switch (getArguments().getString("outputType")) {
                case "Digits": output = DigitDatasetFragment.newInstance(); break;
                case "Image": output = ImageDatasetFragment.newInstance(false); break;
                case "ImageRGB": output = ImageDatasetFragment.newInstance(true); break;
            }
            ((FrameLayout) view.findViewById(R.id.input_dataset)).addView(input.onCreateView(inflater, view.findViewById(R.id.input_dataset), savedInstanceState));

            ((FrameLayout) view.findViewById(R.id.output_dataset)).addView(output.onCreateView(inflater, view.findViewById(R.id.input_dataset), savedInstanceState));
        }
        Button button = view.findViewById(R.id.start_train);
        button.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("thread", new TrainThread(network, in, out));
            Navigation.findNavController(view).navigate(R.id.train_to_wb, bundle);
        });

        return view;
    }
}