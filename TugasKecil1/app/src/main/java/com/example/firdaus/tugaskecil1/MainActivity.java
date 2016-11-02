package com.example.firdaus.tugaskecil1;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    DBadapter myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        openDB();
        populateListView ();

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.HOUR_OF_DAY,12);
                calendar.set(Calendar.MINUTE,0);
                calendar.set(Calendar.SECOND,0);

                Intent intent = new Intent(getApplicationContext(), Receiver.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

            }
        });

    }

   private void openDB () {
        myDB = new DBadapter(this);
        myDB.open();
    }

    private void populateListView () {
        Cursor cursor = myDB.getAllRows();//mengambil data dari database
        String[] fromFieldNames = new String[] {DBadapter.KEY_ROWID, DBadapter.KEY_TASK,DBadapter.KEY_DESKRIPSI,DBadapter.KEY_YEAR,DBadapter.KEY_MONTH,DBadapter.KEY_DAY};
        int[] toViewIDs = new int[] {R.id.item_number, R.id.item_text,R.id.item_desk,R.id.item_thn,R.id.item_bln,R.id.item_tgl};

        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.items, cursor, fromFieldNames, toViewIDs, 0);
        ListView mylist = (ListView) findViewById(R.id.listView);
        mylist.setAdapter(myCursorAdapter);
    }


    public void add_OnClick(View v) {
        //EditText add = (EditText) findViewById(R.id.tambahTempatTF);
        //String place_name = add.getText().toString();
        Intent intent2 = new Intent(this, Main2Activity.class);
        startActivity(intent2);
       // myDB.insertRow(nama,desk, tahunz,bulanz,tanggalz);
        populateListView ();

    }




    public void update_OnClick(View v) {
       // EditText id_tempat = (EditText) findViewById(R.id.updateIDTF);
       // EditText tempat = (EditText) findViewById(R.id.updateTempatTF);
      //  String place_id = id_tempat.getText().toString();
      //  Long id = Long.valueOf(place_id).longValue();
       // String place_name = tempat.getText().toString();
        Intent intent2 = new Intent(this, Main3Activity.class);
        startActivity(intent2);

        //myDB.updateRow(id, place_name,"aw",1,2,3);
        populateListView ();
    }

    public void delete_OnClick(View v) {
        EditText delete = (EditText) findViewById(R.id.hapusTempatTF);
        String place_id = delete.getText().toString();
        Long id = Long.valueOf(place_id).longValue();

        delete.setText("");
        myDB.deleteRow(id);
        populateListView ();
    }


}
