package com.samsung.nnlp.fragments.wb;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.samsung.nnlp.R;
import com.samsung.nnlp.models.LayerView;
import com.samsung.nnlp.models.neuronet.NeuralNetwork;


public class EditFragment extends Fragment {
    private NeuralNetwork network;
    private int inputSize = 1;

    public EditFragment() {
    }

    public static EditFragment newInstance() {
        EditFragment fragment = new EditFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        Button edit = view.findViewById(R.id.edit_button);
        Button add = view.findViewById(R.id.add_button);
        Button remove = view.findViewById(R.id.remove_button);
        EditText fl = view.findViewById(R.id.first_layer);

        if (getArguments() == null) network = new NeuralNetwork();
        else {
            network = (NeuralNetwork) getArguments().getSerializable("nn");
            if (network.getLayers().size() > 0) {
                inputSize = network.getLayers().get(0).getInput().length;
                fl.setText(Integer.toString(inputSize));
            }
        }




        RecyclerView layers = view.findViewById(R.id.layer_view);
        layers.setHasFixedSize(true);
        layers.setAdapter(new LayerView(network));

        fl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0)
                    inputSize = Integer.parseInt(String.valueOf(charSequence));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        add.setOnClickListener(view1 -> {
            LayerView adapter = (LayerView) layers.getAdapter();
            assert adapter != null;
            adapter.getLayers().add(new LayerView.LayerShell());
            adapter.notifyDataSetChanged();
        });
        remove.setOnClickListener(view1 -> {
            LayerView adapter = (LayerView) layers.getAdapter();
            assert adapter != null;
            if (adapter.getLayers().size() > 0) {
                adapter.getLayers().remove(adapter.getLayers().size() - 1);
                adapter.notifyDataSetChanged();
            }
        });

        edit.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            LayerView adapter = (LayerView) layers.getAdapter();
            network = new NeuralNetwork();
            assert adapter != null;
            network.initInLayer(adapter.getLayers().get(0).function,adapter.getLayers().get(0).size , inputSize);
            for (int i = 1; i < adapter.getLayers().size(); i++)
                network.initHiddenOrOutLayer(adapter.getLayers().get(i).function,adapter.getLayers().get(i).size);
            bundle.putSerializable("nn", network);
            Navigation.findNavController(view).navigate(R.id.edit_to_wb, bundle);

        });
        return view;
    }
}