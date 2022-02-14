package com.example.tripmei;

import static android.content.Context.ALARM_SERVICE;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;


public class HelperMethods {
    public static String ACTION_PENDING = "action";


    public static void startScheduling(Context context) {
        int timeInSec = 2;

        Intent intent = new Intent(context, AlarmService.class);
        PendingIntent pendingIntent = PendingIntent.getService(
                context.getApplicationContext(), 234, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (timeInSec * 1000), pendingIntent);
        Toast.makeText(context, "Alarm set to after " + timeInSec + " seconds", Toast.LENGTH_LONG).show();

    }

    public interface OnButton{
        void onClicked();
    }
    public static void showAlertDialog(Context context, HelperMethods.OnButton onButton) {
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle("Trip Alarm")
                .setCancelable(false)
                .setMessage("Reminder For your Trip !")
                .setPositiveButton("Start", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                /*       try {
                            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + addTripActivity.txt_StartPoint.getText().toString()
                                    + "/" + addTripActivity.txt_EndPoint.getText().toString());
                            startActivity(context , new Intent(Intent.ACTION_VIEW , uri) , null);
                        }catch (ActivityNotFoundException e){
                            Toast.makeText(context , "Please Install Google Maps " , Toast.LENGTH_SHORT).show();
                        }*/
                        onButton.onClicked();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.dismiss();
                        onButton.onClicked();
                    }
                }).setNeutralButton("Snooze", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        dialog.dismiss();
                        onButton.onClicked();
                    }
                }).create();
        alertDialog.getWindow().setType(LAYOUT_FLAG);
        alertDialog.show();
    }

}
