package ua.edu.universityprograms.app.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import ua.edu.universityprograms.app.activities.Settings;

/**
 * Created by vcaciuc on 6/17/2014.
 */
public class NoCWIDdialog extends DialogFragment {

    // Warning for not entering the CWID
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("No information");
        builder.setMessage("Would you like to set your information?")
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        Intent intent = new Intent(getActivity(), Settings.class);
                        startActivity(intent);
                        dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dismiss();
                    }
                });
        builder.setIcon(android.R.drawable.ic_delete);
        // Create the AlertDialog object and return it
        return builder.create();
    }
}