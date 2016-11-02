package com.example.firdaus.tugaskecil1;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by Fikry Al Farisi M on 02/11/2016.
 */

public class Repeating_activity extends AppCompatActivity {
    DBadapter myDB;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repeating_activity_layout);

        openDB();
        populateListView ();
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
        ListView mylist = (ListView) findViewById(R.id.ListView);
        mylist.setAdapter(myCursorAdapter);
    }
}


