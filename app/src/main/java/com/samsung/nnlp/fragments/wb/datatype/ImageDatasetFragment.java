package com.samsung.nnlp.fragments.wb.datatype;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samsung.nnlp.R;
import com.samsung.nnlp.models.neuronet.NeuralNetwork;


public class ImageDatasetFragment extends Fragment {

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

        return view;
    }
}