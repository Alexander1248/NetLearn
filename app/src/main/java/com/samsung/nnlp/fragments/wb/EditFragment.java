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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.samsung.nnlp.R;
import com.samsung.nnlp.models.adapters.LayerAdapter;
import com.samsung.nnlp.models.neuronet.NeuralNetwork;


public class EditFragment extends Fragment {
    private NeuralNetwork network;
    private int inputSize = 1;
    private String inputType;
    private String outputType;

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
        Spinner inType = view.findViewById(R.id.input_type);
        Spinner outType = view.findViewById(R.id.output_type);

        if (getArguments() == null) network = new NeuralNetwork();
        else {
            network = (NeuralNetwork) getArguments().getSerializable("nn");
            if (network.getLayers().size() > 0) {
                inputSize = network.getLayers().get(0).getInput().length;
                fl.setText(Integer.toString(inputSize));
            }
            inputType = getArguments().getString("inputType");
            outputType = getArguments().getString("outputType");
            String[] array = getResources().getStringArray(R.array.types);
            for (int i = 0; i < array.length; i++) {
                if (array[i].equals(inputType)) inType.setSelection(i);
                if (array[i].equals(outputType)) outType.setSelection(i);
            }
        }





        RecyclerView layers = view.findViewById(R.id.layer_view);
        layers.setHasFixedSize(true);
        layers.setAdapter(new LayerAdapter(network));

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
        inType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                inputType = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        outType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                outputType = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        add.setOnClickListener(view1 -> {
            LayerAdapter adapter = (LayerAdapter) layers.getAdapter();
            assert adapter != null;
            adapter.getLayers().add(new LayerAdapter.LayerShell());
            adapter.notifyDataSetChanged();
        });
        remove.setOnClickListener(view1 -> {
            LayerAdapter adapter = (LayerAdapter) layers.getAdapter();
            assert adapter != null;
            if (adapter.getLayers().size() > 0) {
                adapter.getLayers().remove(adapter.getLayers().size() - 1);
                adapter.notifyDataSetChanged();
            }
        });

        edit.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            LayerAdapter adapter = (LayerAdapter) layers.getAdapter();
            network = new NeuralNetwork();
            assert adapter != null;
            network.initInLayer(adapter.getLayers().get(0).function,adapter.getLayers().get(0).size , inputSize);
            for (int i = 1; i < adapter.getLayers().size(); i++)
                network.initHiddenOrOutLayer(adapter.getLayers().get(i).function,adapter.getLayers().get(i).size);
            bundle.putSerializable("nn", network);
            bundle.putString("inputType", inputType);
            bundle.putString("outputType", outputType);
            Navigation.findNavController(view).navigate(R.id.edit_to_wb, bundle);

        });
        return view;
    }
}