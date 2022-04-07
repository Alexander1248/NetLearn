package com.samsung.nnlp.models.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samsung.nnlp.R;
import com.samsung.nnlp.models.neuronet.NeuralNetwork;

import java.util.ArrayList;
import java.util.List;

public class DigitAdapter extends RecyclerView.Adapter<DigitAdapter.DigitHolder> {
    private final List<String> data = new ArrayList<>();
    private final int size;

    public DigitAdapter(int size) {
        this.size = size;
    }

    @NonNull
    @Override
    public DigitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.digit_item, parent, false);

        return new DigitAdapter.DigitHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DigitHolder holder, int position) {
        data.set(position, holder.digitItem.getText().toString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class DigitHolder extends RecyclerView.ViewHolder {
        public EditText digitItem;
        public DigitHolder(@NonNull View itemView) {
            super(itemView);
            digitItem = itemView.findViewById(R.id.digit_data_item);
        }
    }
}
