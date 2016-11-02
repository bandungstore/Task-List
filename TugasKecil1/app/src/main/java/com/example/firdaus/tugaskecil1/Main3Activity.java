package com.example.firdaus.tugaskecil1;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class Main3Activity extends AppCompatActivity {
    Button btn;
    int year_x,month_x,day_x;
    static final int DIALOG_ID = 0;
    DBadapter myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        openDB();
        //populateListView ();
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);


        showDialogOnButtonClick();


    }

    public void showDialogOnButtonClick(){
        btn = (Button)findViewById(R.id.button2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id){
        if(id== DIALOG_ID)
            return new DatePickerDialog(this, dpickerListner ,year_x,month_x,day_x);
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListner = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x = year;
            month_x = month;
            day_x = dayOfMonth;
            Toast t = Toast.makeText(getApplicationContext(), day_x + "-" + month_x + "-" + year_x,     Toast.LENGTH_LONG);
            //EditText tahun_x = (EditText) findViewById(R.id.tahun);
            //tahun_x.setText();
            t.show();
        }
    };

    private void openDB () {
        myDB = new DBadapter(this);
        myDB.open();
    }

    public void klikButton(View v) {
        EditText id = (EditText) findViewById(R.id.id);
        EditText nama = (EditText) findViewById(R.id.nama);
        EditText desk = (EditText) findViewById(R.id.desk);
        String idx = id.getText().toString();
        String namax = nama.getText().toString();
        String deskx = desk.getText().toString();
        int tahunx = year_x;
        int bulanx = month_x;
        int harix = day_x;

        myDB.updateRow(Integer.parseInt(idx) , namax,deskx,tahunx,bulanx,harix);
        finish();
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);//Start the same Activity

        // populateListView ();
    }

    public void Kembali(View v){
        finish();
    }
}
