package com.samsung.netlearn.fragments.wb;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.samsung.netlearn.models.adapters.LayerAdapter;
import com.samsung.netlearn.R;

import org.neuroph.nnet.MultiLayerPerceptron;


public class EditFragment extends Fragment {
    private MultiLayerPerceptron network;
    private String inputType;
    private String outputType;

    public EditFragment() {}

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
        Spinner inType = view.findViewById(R.id.input_type);
        Spinner outType = view.findViewById(R.id.output_type);

        if (getArguments() == null) network = new MultiLayerPerceptron();
        else {
            network = (MultiLayerPerceptron) getArguments().getSerializable("nn");
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
            adapter.getLayers().add(1);
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
            network = new MultiLayerPerceptron(adapter.getLayers());
            bundle.putSerializable("nn", network);
            bundle.putString("inputType", inputType);
            bundle.putString("outputType", outputType);
            Navigation.findNavController(view).navigate(R.id.edit_to_wb, bundle);

        });
        return view;
    }
}