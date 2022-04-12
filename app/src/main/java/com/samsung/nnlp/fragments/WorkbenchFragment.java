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
import com.samsung.nnlp.models.netview.NetView;
import com.samsung.nnlp.models.neuronet.AFunction;
import com.samsung.nnlp.models.neuronet.NeuralNetwork;


public class WorkbenchFragment extends Fragment {
    private NeuralNetwork network;
    private String inputType;

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
            network = new NeuralNetwork();
            network.initInLayer(AFunction.Sigmoid, 1, 2);
            inputType = getResources().getStringArray(R.array.types)[0];
        }
        else {
            network = (NeuralNetwork) getArguments().getSerializable("nn");
            inputType = getArguments().getString("inputType");
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


        Bundle bundle = new Bundle();
        bundle.putSerializable("nn", network);
        bundle.putSerializable("inputType", inputType);
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