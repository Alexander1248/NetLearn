package com.samsung.netlearn.models.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samsung.netlearn.R;

import org.neuroph.nnet.MultiLayerPerceptron;

import java.util.ArrayList;

public class FilenameAdapter extends RecyclerView.Adapter<FilenameAdapter.StringHolder> {
    private final ArrayList<String> filenames = new ArrayList<>();
    private final EditText text;
    private final Context context;

    public FilenameAdapter(EditText text, Context context) {
        this.text = text;
        this.context = context;
    }

    public void addString(String str) {
        filenames.add(str);
        notifyDataSetChanged();
    }

    public ArrayList<String> getData() {
        return filenames;
    }

    @NonNull
    @Override
    public StringHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.string_item, parent, false);
        return new StringHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StringHolder holder, int position) {
        holder.filepath.setText(filenames.get(position));
        holder.filepath.setOnClickListener(view -> text.setText(((TextView) view).getText()));
        holder.filepath.setOnLongClickListener(view -> {
            boolean del = context.deleteFile(filenames.get(position) + ".nn");
            if (del) {
                Toast.makeText(context, "File deleted!", Toast.LENGTH_SHORT).show();
                filenames.remove(position);
                notifyDataSetChanged();
            }
            return del;
        });
    }

    @Override
    public int getItemCount() {
        return filenames.size();
    }

    public static class StringHolder extends RecyclerView.ViewHolder {
        public final TextView filepath;

        public StringHolder(@NonNull View itemView) {
            super(itemView);

            filepath = itemView.findViewById(R.id.string_view);
        }
    }
}
