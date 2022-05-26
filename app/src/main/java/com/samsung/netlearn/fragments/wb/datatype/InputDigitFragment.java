package com.samsung.netlearn.fragments.wb.datatype;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.samsung.netlearn.models.adapters.StringAdapter;
import com.samsung.netlearn.R;


public class InputDigitFragment extends Fragment {
    RecyclerView recycler;
   public InputDigitFragment() {
   }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_digit_input, container, false);
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
        ImageButton button = view.findViewById(R.id.add_button);
        button.setOnClickListener(view1 -> {
            StringBuilder str = new StringBuilder();
            boolean notNull = true;
            for (EditText text : texts) {
                if (text.getText().toString().equals("")) notNull = false;
                str.append(text.getText().toString()).append(" ");
            }
            if (notNull) {
                stringAdapter.strings.add(str.toString());
                stringAdapter.notifyDataSetChanged();
                getArguments().putStringArrayList("data", stringAdapter.strings);
            }
        });
        return view;
    }
}