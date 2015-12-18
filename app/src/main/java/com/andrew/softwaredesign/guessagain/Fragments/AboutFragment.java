package com.andrew.softwaredesign.guessagain.Fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andrew.softwaredesign.guessagain.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends DialogFragment {

    public AboutFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.popup_about_layout, null))
                .setNegativeButton(R.string.acknowledge, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AboutFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

}
