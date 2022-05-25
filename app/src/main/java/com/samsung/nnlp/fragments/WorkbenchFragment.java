package com.samsung.nnlp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.samsung.nnlp.R;
import com.samsung.nnlp.models.NetView;
import com.samsung.nnlp.models.threads.TrainThread;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.nnet.MultiLayerPerceptron;


public class WorkbenchFragment extends Fragment {
    private MultiLayerPerceptron network;
    private String inputType;
    private String outputType;
    private TrainThread thread;

    public WorkbenchFragment() {}

    public static WorkbenchFragment newInstance() {
        WorkbenchFragment fragment = new WorkbenchFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workbench, container, false);

        if (getArguments() == null) {
            network = new MultiLayerPerceptron(2, 3, 1);
            inputType = getResources().getStringArray(R.array.types)[0];
            outputType = getResources().getStringArray(R.array.types)[0];
        }
        else {
            network = (MultiLayerPerceptron) getArguments().getSerializable("nn");
            inputType = getArguments().getString("inputType");
            outputType = getArguments().getString("outputType");
            thread = (TrainThread) getArguments().getSerializable("thread");
            if (thread != null)
                thread.setProgressBar(view.findViewById(R.id.learning_progress), view.findViewById(R.id.error_rate));

        }


        Button edit = view.findViewById(R.id.edit);
        Button train = view.findViewById(R.id.train);
        Button use = view.findViewById(R.id.use);
        Button save = view.findViewById(R.id.save);
        Button load = view.findViewById(R.id.load);
        LinearLayout netView = view.findViewById(R.id.net_view);
        NetView nw = new NetView(getContext());
        nw.setNeuralNetwork(network);
        netView.addView(nw);
        if (thread != null) {
            Button stop = view.findViewById(R.id.stop_train);
            stop.setVisibility(View.VISIBLE);
            stop.setOnClickListener(view1 -> thread.stopTraining());
            thread.start();
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable("nn", network);
        bundle.putSerializable("inputType", inputType);
        bundle.putSerializable("outputType", outputType);
        edit.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.to_edit, bundle));
        train.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.to_train, bundle));
        use.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.to_use, bundle));
        bundle.putBoolean("state", false);
        save.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.to_io, bundle));
        bundle.putBoolean("state", true);
        load.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.to_io, bundle));
        return view;
    }
}