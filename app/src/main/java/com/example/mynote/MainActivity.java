package com.example.mynote;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageButton newnotebtn;
    ListView itemlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        newnotebtn = findViewById(R.id.newnote_btn);
        itemlist = findViewById(R.id.notelist);

        //intent jump to new note activity from mainactivity
        Intent next = new Intent(MainActivity.this, new_note_activity.class);

        //add new note button, used to jump to new note activity
        newnotebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next.putExtra("source",1);
                startActivity(next);
            }
        });

        itemlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item_class sel_item = (item_class) parent.getItemAtPosition(position);
                Log.d("FLAG", "item - "+sel_item.getId());
                next.putExtra("source",0);
                next.putExtra("id", sel_item.getId());
                startActivity(next);
            }
        });

        itemlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item_class sel_item = (item_class) parent.getItemAtPosition(position);

                MyConnection con = new MyConnection(MainActivity.this);
                Log.d("Long", sel_item.getTime());
                // Create and show the popup menu
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.dele_option, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.menu_delete) {
                        // Handle delete operation
                        boolean res = con.Delete_Operation(sel_item.getId());
                        if(res){
                            refresh_list();
                            Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Delete Denied", Toast.LENGTH_SHORT).show();
                        }
                        refresh_list();
                        return true;
                    }
                    return false;
                });
                popupMenu.show();
                return true;
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh_list();

    }

    private void refresh_list(){
        itemlist = findViewById(R.id.notelist);
        MyConnection con = new MyConnection(this);
        List<item_class> items = con.Display_Operation();

        ItemAdapter adapter = new ItemAdapter(this, items);
        itemlist.setAdapter(adapter);
    }

}