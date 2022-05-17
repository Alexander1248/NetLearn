package com.samsung.nnlp.fragments.wb.datatype;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.samsung.nnlp.R;
import com.samsung.nnlp.models.FileManager;
import com.samsung.nnlp.models.adapters.StringAdapter;


public class ImageDatasetFragment extends Fragment {
    private RecyclerView recycler;

   public ImageDatasetFragment() {}


    public static ImageDatasetFragment newInstance(boolean rgb) {
        ImageDatasetFragment fragment = new ImageDatasetFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("rgb", rgb);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_dataset, container, false);

        recycler = view.findViewById(R.id.file_recycler);
        StringAdapter stringAdapter = new StringAdapter();
        recycler.setAdapter(stringAdapter);

        Button manager_button = view.findViewById(R.id.open_manager);
        manager_button.setOnClickListener(view1 -> {
            Intent manager = new Intent(this.getContext(), FileManager.class);
            stringAdapter.strings.add(manager.getStringExtra("path"));

        });


        return view;
    }
}