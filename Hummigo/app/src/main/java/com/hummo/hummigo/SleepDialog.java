package com.hummo.hummigo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class SleepDialog extends AppCompatDialogFragment {
    private EditText sleephours,waterdrank;

    private SleepDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.sleep_dialog, null);
        builder.setView(view)
                .setTitle("Daily Details")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String hours = sleephours.getText().toString();
                        String water= waterdrank.getText().toString();
                        listener.applyTexts(hours,water);


                    }
                });
        sleephours = view.findViewById(R.id.edit_sleep);
         waterdrank= view.findViewById(R.id.edit_water);
        return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (SleepDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }
    public interface SleepDialogListener {
        void applyTexts(String username, String password);
    }
}