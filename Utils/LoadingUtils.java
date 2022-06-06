package com.example.bigfamilyv20.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.bigfamilyv20.R;

public class LoadingUtils {
    Activity activity;
    AlertDialog dialog;


    public LoadingUtils(Activity activity) {
        this.activity = activity;
    }

    public void startLoadingDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);

        LayoutInflater inflater=activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_layout,null));
        builder.setCancelable(false);
        dialog=builder.create();
        dialog.show();

    }

    public void dismissDialog(){
        dialog.dismiss();
    }
}
