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
    public void onBindViewHolder(@NonNull DigitHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.digitItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                data.set(position, charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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
