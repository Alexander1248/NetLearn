package com.samsung.nnlp.models;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samsung.nnlp.R;
import com.samsung.nnlp.models.neuronet.AFunction;
import com.samsung.nnlp.models.neuronet.NeuralNetwork;

import java.util.ArrayList;
import java.util.List;

public class LayerView extends RecyclerView.Adapter<LayerView.LayerHolder> {
    private final List<LayerShell> layers = new ArrayList<>();

    public LayerView(NeuralNetwork network) {
        for (int i = 0; i < network.getLayers().size(); i++)
            layers.add(new LayerShell(
                    network.getLayers().get(i).getNeurons()[0].getFunction(),
                    network.getLayers().get(i).getNeurons().length
            ));
    }

    @NonNull
    @Override
    public LayerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layer_item, parent, false);
        return new LayerHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LayerHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.func.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                layers.get(position).function = AFunction.valueOf((String) adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        holder.size.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0)
                    layers.get(position).size = Integer.parseInt(String.valueOf(charSequence));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        holder.size.setText(Integer.toString(layers.get(position).size));
        holder.func.setSelection(layers.get(position).function.ordinal());
    }

    @Override
    public int getItemCount() {
        return layers.size();
    }

    public static class LayerHolder extends RecyclerView.ViewHolder {
        public View view;
        public Spinner func;
        public EditText size;

        public LayerHolder(@NonNull android.view.View itemView) {
            super(itemView);
            view = itemView;

            func = view.findViewById(R.id.layer_func);
            size = view.findViewById(R.id.layer_size);

            String[] values = new String[AFunction.values().length];
            for (int i = 0; i < values.length; i++) values[i] = AFunction.values()[i].name();
            func.setAdapter(new ArrayAdapter<>(view.getContext(), R.layout.support_simple_spinner_dropdown_item, values));
        }
    }

    public static class LayerShell {
        public AFunction function;
        public int size;

        public LayerShell() {
            function = AFunction.Sigmoid;
            size = 1;
        }

        public LayerShell(AFunction function, int size) {
            this.function = function;
            this.size = size;
        }
    }

    public List<LayerShell> getLayers() {
        return layers;
    }
}
