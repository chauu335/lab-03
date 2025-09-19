package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {
    interface EditCityDialogListener {
        public void editCity();
    }

    private EditCityDialogListener listener;
    public static EditCityFragment newInstance(City city) {
        Bundle args = new Bundle();
        args.putSerializable("city", city);
        EditCityFragment fragment = new EditCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle args = requireArguments();
        City city = (City) args.getSerializable("city");
        View view  = LayoutInflater.from(getContext()).inflate(R.layout.fragment_edit_city, null);
        EditText editCityText = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceText = view.findViewById(R.id.edit_text_province_text);
        if (city != null) {
            editCityText.setText(city.getName());
            editProvinceText.setText(city.getProvince());
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("Edit City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", (dialog, which) -> {
                    String cityName = editCityText.getText().toString();
                    String provinceName = editProvinceText.getText().toString();
                    if (city != null) {
                        city.setName(cityName);
                        city.setProvince(provinceName);
                    }
                    listener.editCity();
                })
                .create();
    }
}
