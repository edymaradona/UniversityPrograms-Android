package ua.edu.universityprograms.app.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;

import java.util.prefs.Preferences;

import ua.edu.universityprograms.app.activities.MainActivity;

/**
 * Created by vcaciuc on 6/24/2014.
 */
public class RestartAppDialog extends DialogFragment {

    public interface restartAppDialogListener{
        public void onDialogNegativeClick(DialogFragment dialog);

    }
    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (restartAppDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    restartAppDialogListener mListener;
    SharedPreferences pref;


    public static RestartAppDialog fragmentInstance() {
        RestartAppDialog fragment = new RestartAppDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final int theme = pref.getInt("theme", android.R.style.Theme_Holo);
        final SharedPreferences.Editor editor = pref.edit();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("In order to change the theme, the app needs to be restarted. Would you like to restart the app?")
                .setTitle("Warning");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                if(theme == android.R.style.Theme_Holo){
                    editor.putInt("theme", android.R.style.Theme_Holo_Light);
                }else{
                    editor.putInt("theme", android.R.style.Theme_Holo);
                }
                editor.commit();
                Intent i = new Intent(getActivity(),MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog

            }
        });
        AlertDialog dialog = builder.create();
        return dialog;
    }
}
