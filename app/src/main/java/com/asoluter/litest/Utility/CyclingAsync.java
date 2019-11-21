package com.asoluter.litest.Utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.asoluter.litest.R;

public class CyclingAsync extends AsyncTask<Void,Void,Void> {

    Context context;
    private ProgressDialog dialog;
    private boolean cycling;


    public CyclingAsync(Context context){
        this.context=context;
        dialog=new ProgressDialog(context);
    }

    @Override
    protected Void doInBackground(Void... params) {

        while (cycling){
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void stopCycling(){
        cycling=false;
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage(context.getString(R.string.loading));
        dialog.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(dialog.isShowing()){
            dialog.dismiss();
        }
    }
}
