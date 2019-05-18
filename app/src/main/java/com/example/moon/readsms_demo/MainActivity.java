package com.example.moon.readsms_demo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyTag" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_SMS))
           != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_SMS},101);
        }else {
            getReceivedSMS();
            getSendSMS();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==101 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            getReceivedSMS();
            getSendSMS();
        }
    }

    public void  getReceivedSMS(){
        String INBOX = "content://sms/inbox";
        // public static final String SENT = "content://sms/sent";
        // public static final String DRAFT = "content://sms/draft";
        Cursor inboxed_sms = getContentResolver().query(Uri.parse(INBOX), null, null, null, null);
        if(inboxed_sms.getCount()>0){
            inboxed_sms.moveToFirst();
            do{
                if(inboxed_sms.getString(2).length()>=11 &&
                        (inboxed_sms.getString(2).regionMatches(0,"+8801",0,5)
                        ||(inboxed_sms.getString(2).regionMatches(0,"01",0,2)))) {
                    String sms = "Received ->" +"ID " + inboxed_sms.getString(0)+"Thrad ID -> " + inboxed_sms.getString(1) + " ->\n NUMBER " + inboxed_sms.getString(2) + " - >\n " + inboxed_sms.getString(12);
//                for(int i=0;i<inboxed_sms.getColumnCount();i++){
//                    sms+= "\n " +i + " -> " + inboxed_sms.getString(i);
//                }
                    Log.i(TAG, "onStart: " + sms);
                }

            }while (inboxed_sms.moveToNext());
        }else{
            Toast.makeText(getApplicationContext(),"SMS NOT FOUND",Toast.LENGTH_SHORT).show();
        }
    }


    public void  getSendSMS(){
        //String INBOX = "content://sms/inbox";
         String SENT = "content://sms/sent";
        // public static final String DRAFT = "content://sms/draft";
        Cursor inboxed_sms = getContentResolver().query(Uri.parse(SENT), null, null, null, null);
        if(inboxed_sms.getCount()>0){
            inboxed_sms.moveToFirst();
            do{
                if(inboxed_sms.getString(2).length()>=11 &&
                        (inboxed_sms.getString(2).regionMatches(0,"+8801",0,5)
                                ||(inboxed_sms.getString(2).regionMatches(0,"01",0,2)))) {
                    String sms = "Sent ->" +"ID " + inboxed_sms.getString(0)+"Thread ID -> " + inboxed_sms.getString(1) + " ->\n NUMBER " + inboxed_sms.getString(2) + " - >\n " + inboxed_sms.getString(12);
//                for(int i=0;i<inboxed_sms.getColumnCount();i++){
//                    sms+= "\n " +i + " -> " + inboxed_sms.getString(i);
//                }
                    Log.i(TAG, "onStart: " + sms);
                }

            }while (inboxed_sms.moveToNext());
        }else{
            Toast.makeText(getApplicationContext(),"SMS NOT FOUND",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
