package com.samsung.nnlp.fragments.wb.datatype;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.samsung.nnlp.R;
import com.samsung.nnlp.activities.FileActivity;
import com.samsung.nnlp.models.adapters.FileAdapter;

import java.io.File;


public class InputImageFragment extends Fragment {
    private RecyclerView recycler;
    private final Context context;
    private final boolean rgb;

   public InputImageFragment(Context context, boolean rgb) {
       this.context = context;
       this.rgb = rgb;
   }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_input, container, false);

        recycler = view.findViewById(R.id.file_recycler);
        FileAdapter fileAdapter = new FileAdapter();
        recycler.setAdapter(fileAdapter);

        Button manager_button = view.findViewById(R.id.open_manager);
        manager_button.setOnClickListener(view1 -> {
            Intent fileManager = new Intent(context, FileActivity.class);
            context.startActivity(fileManager);
            File file = (File) fileManager.getSerializableExtra("path");
            if (file != null) fileAdapter.files.add(file);

        });


        return view;
    }
}