package com.example.tripmei;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class ViewNotes extends AppCompatActivity {

    ListView listViewData;
    ArrayAdapter<String> adapter;
    String [] items ;
    int id;
    String itemSelected = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);
        listViewData = findViewById(R.id.list_view_data);
        AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
        id = getIntent().getIntExtra("notesId" , -1);
        Log.i("sdfsdfas",id+"");
        List<Trip> listHistory =  db.tripDao().getRow(id);

        String notes = listHistory.get(0).getNotes();
        items = notes.split("♥");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,items);
        listViewData.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.item_done);
        {
            for(int i = 0; i <listViewData.getCount();i++){
                if(listViewData.isItemChecked(i)){
                    itemSelected+=listViewData.getItemAtPosition(i)+"♥";
                }
            }
            Toast.makeText(this, "Done : \n"+itemSelected, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}