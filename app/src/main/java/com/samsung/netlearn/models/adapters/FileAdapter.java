package com.samsung.netlearn.models.adapters;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samsung.netlearn.R;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileHolder> implements Serializable {
    private final ArrayList<String> files = new ArrayList<>();
    private final ArrayList<String> data = new ArrayList<>();
    private final boolean rgb;
    private boolean parsed;

    public FileAdapter(boolean rgb) {
        this.rgb = rgb;
    }

    public void addFile(File file) {
        files.add(file.getName());
        notifyDataSetChanged();
        new Thread(() -> {
            try {
                StringBuilder encoded = new StringBuilder();
                Log.i("fa_add", "Parsing...");
                ImageDecoder.Source source = ImageDecoder.createSource(file);
                Bitmap bitmap = ImageDecoder.decodeBitmap(source).copy(Bitmap.Config.RGBA_F16, true);
                if (rgb) {
                    for (int y = 0; y < bitmap.getHeight(); y++)
                        for (int x = 0; x < bitmap.getWidth(); x++) {
                            encoded.append(bitmap.getColor(x, y).red()).append(" ");
                            encoded.append(bitmap.getColor(x, y).green()).append(" ");
                            encoded.append(bitmap.getColor(x, y).blue()).append(" ");
                        }
                } else {
                    for (int y = 0; y < bitmap.getHeight(); y++)
                        for (int x = 0; x < bitmap.getWidth(); x++) {
                            Color color = bitmap.getColor(x, y);
                            encoded.append((color.red() + color.green() + color.blue()) / 3).append(" ");
                        }
                }
                data.add(encoded.toString());
                Log.i("fa_add", "Parsing completed!");
                parsed = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        Log.i("fa_add", file.getName());
    }
    public ArrayList<String> getData() {
        return data;
    }

    public boolean isParsed() {
        boolean state = parsed;
        parsed = false;
        return state;
    }

    @NonNull
    @Override
    public FileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.string_item, parent, false);
        return new FileHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileHolder holder, int position) {
        holder.filename.setText(files.get(position));
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public static class FileHolder extends RecyclerView.ViewHolder {
        public final TextView filename;

        public FileHolder(@NonNull View itemView) {
            super(itemView);
            filename = itemView.findViewById(R.id.string_view);
        }
    }
}
