package com.example.firdaus.tugaskecil1;

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

public class MainActivity extends AppCompatActivity {

    DBadapter myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openDB();
        populateListView ();
    }

   private void openDB () {
        myDB = new DBadapter(this);
        myDB.open();
    }

    private void populateListView () {
        Cursor cursor = myDB.getAllRows();//mengambil data dari database
        String[] fromFieldNames = new String[] {DBadapter.KEY_ROWID, DBadapter.KEY_TASK};
        int[] toViewIDs = new int[] {R.id.item_number, R.id.item_text};

        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.items, cursor, fromFieldNames, toViewIDs, 0);
        ListView mylist = (ListView) findViewById(R.id.listView);
        mylist.setAdapter(myCursorAdapter);
    }


    public void add_OnClick(View v) {
        EditText add = (EditText) findViewById(R.id.tambahTempatTF);
        String place_name = add.getText().toString();

        myDB.insertRow(place_name);
        populateListView ();
    }

    public void update_OnClick(View v) {
        EditText id_tempat = (EditText) findViewById(R.id.updateIDTF);
        EditText tempat = (EditText) findViewById(R.id.updateTempatTF);
        String place_id = id_tempat.getText().toString();
        Long id = Long.valueOf(place_id).longValue();
        String place_name = tempat.getText().toString();

        myDB.updateRow(id, place_name);
        populateListView ();
    }

    public void delete_OnClick(View v) {
        EditText delete = (EditText) findViewById(R.id.hapusTempatTF);
        String place_id = delete.getText().toString();
        Long id = Long.valueOf(place_id).longValue();

        myDB.deleteRow(id);
        populateListView ();
    }
}
