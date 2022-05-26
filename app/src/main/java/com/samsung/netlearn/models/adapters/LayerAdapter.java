package com.samsung.netlearn.models.adapters;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samsung.netlearn.R;

import org.neuroph.nnet.MultiLayerPerceptron;

import java.util.ArrayList;
import java.util.List;

public class LayerAdapter extends RecyclerView.Adapter<LayerAdapter.LayerHolder> {
    private final List<Integer> layers = new ArrayList<>();

    public LayerAdapter(MultiLayerPerceptron network) {
        for (int i = 0; i < network.getLayers().length - 1; i++)
            layers.add(network.getLayerAt(i).getNeuronsCount() - 1);
        layers.add(network.getLayerAt(network.getLayers().length - 1).getNeuronsCount());
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
        holder.size.setText(layers.get(position).toString());

        holder.size.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0)
                    layers.set(position, Integer.parseInt(String.valueOf(charSequence)));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return layers.size();
    }

    public static class LayerHolder extends RecyclerView.ViewHolder {
        public View view;
        public EditText size;

        public LayerHolder(@NonNull android.view.View itemView) {
            super(itemView);
            view = itemView;
            size = view.findViewById(R.id.layer_size);
        }
    }

    public List<Integer> getLayers() {
        return layers;
    }
}
