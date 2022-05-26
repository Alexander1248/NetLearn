package com.samsung.netlearn.fragments.wb.datatype;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.samsung.nnlp.R;
import com.samsung.netlearn.activities.FileActivity;
import com.samsung.netlearn.models.adapters.FileAdapter;

import java.io.File;


public class InputImageFragment extends Fragment {
    public static File bundleFile;

    private final Context context;
    private final boolean rgb;
    private FileAdapter fileAdapter;

   public InputImageFragment(Context context, boolean rgb) {
       this.context = context;
       this.rgb = rgb;
   }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_input, container, false);

        RecyclerView recycler = view.findViewById(R.id.file_recycler);
        fileAdapter = new FileAdapter(rgb);
        recycler.setAdapter(fileAdapter);

        Button manager_button = view.findViewById(R.id.open_manager);
        manager_button.setOnClickListener(view1 -> {
            bundleFile = null;
            Intent fileManager = new Intent(context, FileActivity.class);
            context.startActivity(fileManager);
            new Thread(() -> {
                while (bundleFile == null) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                fileAdapter.addFile(bundleFile);

                while (!fileAdapter.isParsed()) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                getArguments().putStringArrayList("data", fileAdapter.getData());
                Log.i("iif_add", "Thread completed!");
            }).start();
        });


        return view;
    }
}