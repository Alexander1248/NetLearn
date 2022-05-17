package com.samsung.nnlp.fragments.wb.datatype;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.samsung.nnlp.R;
import com.samsung.nnlp.models.adapters.StringAdapter;


public class DigitDatasetFragment extends Fragment {
    RecyclerView recycler;
   public DigitDatasetFragment() {
   }


    public static DigitDatasetFragment newInstance() {
        return new DigitDatasetFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_digit_dataset, container, false);
        recycler = view.findViewById(R.id.string_recycler);
        StringAdapter stringAdapter = new StringAdapter();

        recycler.setHasFixedSize(true);
        recycler.setAdapter(stringAdapter);

        int size = getArguments().getInt("size");

        LinearLayout layout = view.findViewById(R.id.data_field);

        EditText et = view.findViewById(R.id.data_text);
        ViewGroup.LayoutParams layoutParams = et.getLayoutParams();
        layout.removeView(et);
        layoutParams.width /= size;

        EditText[] texts = new EditText[size];
        for (int i = 0; i < size; i++) {
            texts[i] = new EditText(layout.getContext());
            layout.addView(texts[i], i, layoutParams);
        }
        Button button = view.findViewById(R.id.add_button);
        button.setOnClickListener(view1 -> {
            StringBuilder str = new StringBuilder();
            for (EditText text : texts) str.append(text.getText().toString()).append(" ");
            stringAdapter.strings.add(str.toString());
            stringAdapter.notifyDataSetChanged();
            getArguments().putStringArrayList("data", stringAdapter.strings);
        });
        return view;
    }
}