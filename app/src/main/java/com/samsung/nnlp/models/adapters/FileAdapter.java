package com.samsung.nnlp.models.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samsung.nnlp.R;

import java.io.File;
import java.util.ArrayList;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileHolder> {
    public final ArrayList<File> files = new ArrayList<>();

    public FileAdapter() {
    }

    @NonNull
    @Override
    public FileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.string_item, parent, false);
        return new FileHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileHolder holder, int position) {
        holder.filepath.setText(files.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public static class FileHolder extends RecyclerView.ViewHolder {
        public final TextView filepath;

        public FileHolder(@NonNull View itemView) {
            super(itemView);

            filepath = itemView.findViewById(R.id.string_view);
        }
    }
}
