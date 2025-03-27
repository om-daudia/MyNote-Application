package com.example.mynote;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class new_note_activity extends AppCompatActivity {
    Button save_btn;
    EditText title_txt, msg_txt;
    TextView time_txt;

    ImageButton back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_note);

        back_btn = (ImageButton) findViewById(R.id.back_btn);
        save_btn = (Button) findViewById(R.id.save_btn);


        title_txt = (EditText) findViewById(R.id.txt_title);
        msg_txt = (EditText) findViewById(R.id.txt_msg);
        time_txt = (TextView) findViewById(R.id.txt_currenttime);

        MyConnection con = new MyConnection(new_note_activity.this);

        Bundle source = getIntent().getExtras();
        int save_or_edit_flag = source.getInt("source");
        int edit_note_id = source.getInt("id");


        if(save_or_edit_flag == 0){
            try {
                List<item_class> edit_note = con.Get_Note(edit_note_id);
                title_txt.setText(edit_note.get(0).title);
                msg_txt.setText(edit_note.get(0).msg);
                time_txt.setText(edit_note.get(0).time);

//                Log.d("edited note Error", edit_note.get(0).id+"");
//                Log.d("edited note Error", "Error - "+edit_note.get(1).title);
//                Log.d("edited note Error", "Error - "+edit_note.get(2).msg);
//                Log.d("edited note Error", "Error - "+edit_note.get(3).time);
            } catch (Exception e) {
                Log.d("edited note Error", "Error - "+e.toString());
            }
        }

        Calendar cal = Calendar.getInstance();

        Intent previospage = new Intent(this, MainActivity.class);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(previospage);
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title_txt.getText().toString().equals("") && msg_txt.getText().toString().equals("")){
                    Toast.makeText(new_note_activity.this, "Access Denied", Toast.LENGTH_SHORT).show();
                    startActivity(previospage);
                }
                else {
                    if(save_or_edit_flag == 1){
                        boolean res = con.Insert_Operation(title_txt.getText().toString(), msg_txt.getText().toString(), cal.getTime().toString());
                        if (res) {
                            Toast.makeText(new_note_activity.this, "Saved", Toast.LENGTH_SHORT).show();
                            startActivity(previospage);
                        } else {
                            Toast.makeText(new_note_activity.this, "Not Saved", Toast.LENGTH_SHORT).show();
                            startActivity(previospage);
                        }
                    }
                    else{
                        boolean res = con.Update_Operation(edit_note_id, title_txt.getText().toString(), msg_txt.getText().toString(), cal.getTime().toString().replaceFirst("GMT+$", ""));
                        if (res) {
                            Toast.makeText(new_note_activity.this, "Updated", Toast.LENGTH_SHORT).show();
                            startActivity(previospage);
                        } else {
                            Toast.makeText(new_note_activity.this, "Not Updated", Toast.LENGTH_SHORT).show();
                            startActivity(previospage);
                        }
                    }


                }

            }
        });
    }
}