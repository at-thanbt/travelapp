package com.example.asiantech.travelapp.activities.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.asiantech.travelapp.R;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by HungTQB on 17/05/2017.
 */
public class CustomMessageDialog extends DialogFragment {

    @Setter
    @Accessors(prefix = "m")
    private String mMessage;

    private boolean mIsAdded;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = View.inflate(getActivity(), R.layout.layout_message_dialog, null);
        TextView tvMessage = (TextView) view.findViewById(R.id.tvContent);
        TextView btnClose = (TextView) view.findViewById(R.id.btnClose);
        tvMessage.setText(mMessage);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        builder.setView(view);
        return builder.show();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (mIsAdded) {
            return;
        }
        mIsAdded = true;
        super.show(manager, tag);
    }

    @Override
    public void dismiss() {
        if (!mIsAdded) {
            return;
        }
        super.dismiss();
    }
}
