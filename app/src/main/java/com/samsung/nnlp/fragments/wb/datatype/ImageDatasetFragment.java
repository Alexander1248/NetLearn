package com.samsung.nnlp.fragments.wb.datatype;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samsung.nnlp.R;
import com.samsung.nnlp.models.adapters.ImageAdapter;
import com.samsung.nnlp.models.neuronet.NeuralNetwork;


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
        recycler = view.findViewById(R.id.digit_data_recycler);
        recycler.setAdapter(new ImageAdapter());

        return view;
    }
}