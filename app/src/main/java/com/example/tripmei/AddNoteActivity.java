package com.example.tripmei;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.List;


public class AddNoteActivity extends AppCompatActivity {
    //var UI
    LinearLayout linearLayout;
    Button increase ,noteSave;
    EditText editText;

    //notes save in Room
    String notesDetils = "";

    //notos update
    int id;
    String notesUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // var find UI
        setContentView(R.layout.activity_add_note);
        increase = findViewById(R.id.btn_increase);
        linearLayout= findViewById(R.id.linearList);
        noteSave = findViewById(R.id.btn_save_notes);
        editText = findViewById(R.id.note_txt);

        //find Note old to Update
        id = getIntent().getIntExtra("ID",-1);
        //define db to  get old note
        AppDatabase db = AppDatabase.getDbInstance(this);
        List<Trip> listHistory =  db.tripDao().getRow(id);
        notesUpdate = listHistory.get(0).getNotes();
        if(!(notesUpdate==null)){
            String [] allNotes = notesUpdate.split("♥");
            for(int i=0 ; i<allNotes.length ;i++){
                addview();
                editText.setText(allNotes[i]);
            }
        }

        // add new view note
        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check view note
                if (linearLayout.getChildCount() <=0 ) { addview(); }
                else if(!editText.getText().toString().equals("")){ addview(); }
                else { Toast.makeText(getApplicationContext(), "Enter last Notes added", Toast.LENGTH_SHORT).show(); }
            }
        });

        // save note in Room
        noteSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkStateTxt()){
                    // insert note in room
                    db.tripDao().addNotes(notesDetils, id);
                    finish();
                }
            }
        });
    }

    //check Edit Text
    private boolean checkStateTxt() {
        boolean result = true;
        //find all Edit text find it view
        for (int i =0 ;i<linearLayout.getChildCount();i++){

            View checkview = linearLayout.getChildAt(i);
            EditText noteTxt = checkview.findViewById(R.id.note_txt);

            NotesDetils detils = new NotesDetils();
            if(!noteTxt.getText().toString().equals("")){
                detils.setNote(noteTxt.getText().toString());
            }else {result=false;}

            notesDetils+=detils.getNote()+"♥";
            //check do all edit text != null
            if(notesDetils.length()==0){
                result=false;
                Toast.makeText(getApplicationContext(), "Add Note First", Toast.LENGTH_SHORT).show();
            }
            else if(!result){
                Toast.makeText(getApplicationContext(), "Enter All Notes", Toast.LENGTH_SHORT).show();
            }
        }
        return result;
    }

    //method add view note
    private void addview() {
        //find view add note
        final View view1 = getLayoutInflater().inflate(R.layout.add_note, null, false);
        EditText editTxt = view1.findViewById(R.id.note_txt);

        //delete note
        ImageView imageView = view1.findViewById(R.id.delete_note);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeview(view1);
            }
        });
        //add note to linearLayout
        linearLayout.addView(view1);
        editText = editTxt;
    }
    //method remove any note
    private void removeview(View x) {
        linearLayout.removeView(x);
        x = linearLayout.getChildAt(linearLayout.getChildCount()-1);
    }

}