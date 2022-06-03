package com.samsung.netlearn.fragments.wb;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.samsung.netlearn.R;
import com.samsung.netlearn.models.adapters.FilenameAdapter;

import org.neuroph.nnet.MultiLayerPerceptron;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IOFragment extends Fragment {
    private MultiLayerPerceptron network;

   public IOFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_io, container, false);
        network = (MultiLayerPerceptron) getArguments().getSerializable("nn");

        RecyclerView recycler = view.findViewById(R.id.recycler);
        EditText text = view.findViewById(R.id.filename);
        Button button = view.findViewById(R.id.io_button);

        FilenameAdapter adapter = new FilenameAdapter(text, getContext());
        recycler.setAdapter(adapter);

        String[] files = getContext().fileList();
        if (files != null)
            for (String file : files)
                adapter.addString(file.split("\\.")[0]);

        boolean state = getArguments().getBoolean("state");

        if (state) { //Load
            button.setText("Load");
            button.setOnClickListener(view1 -> {
                if (text.getText().length() > 0) {
                    try {
                        FileInputStream fis = getContext().openFileInput(text.getText().toString() + ".nn");
                        ObjectInputStream ois = new ObjectInputStream(fis);

                        network = (MultiLayerPerceptron) ois.readObject();

                        ois.close();
                        fis.close();
                        Toast.makeText(getContext(), "File loaded!", Toast.LENGTH_SHORT).show();

                        Bundle bundle = getArguments();
                        bundle.putSerializable("nn", network);
                        NavHostFragment.findNavController(this).navigate(R.id.io_to_wb, bundle);
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else { //Save
            button.setText("Save");
            button.setOnClickListener(view1 -> {
                if (text.getText().length() > 0) {
                    try {
                        FileOutputStream fos = getContext().openFileOutput(text.getText().toString() + ".nn", Context.MODE_PRIVATE);
                        ObjectOutputStream oos = new ObjectOutputStream(fos);

                        oos.writeObject(network);
                        oos.flush();

                        oos.close();
                        fos.close();
                        Toast.makeText(getContext(), "File saved!", Toast.LENGTH_SHORT).show();

                        NavHostFragment.findNavController(this).navigate(R.id.io_to_wb, getArguments());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        return view;
    }
}