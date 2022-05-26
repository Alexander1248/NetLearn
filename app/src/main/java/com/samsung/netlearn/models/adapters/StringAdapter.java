package com.samsung.netlearn.models.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samsung.netlearn.R;

import java.util.ArrayList;

public class StringAdapter extends RecyclerView.Adapter<StringAdapter.StringHolder> {
    public final ArrayList<String> strings = new ArrayList<>();

    public StringAdapter() {
    }

    @NonNull
    @Override
    public StringHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.string_item, parent, false);
        return new StringHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StringHolder holder, int position) {
        holder.filepath.setText(strings.get(position));
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    public static class StringHolder extends RecyclerView.ViewHolder {
        public final TextView filepath;

        public StringHolder(@NonNull View itemView) {
            super(itemView);

            filepath = itemView.findViewById(R.id.string_view);
        }
    }
}
