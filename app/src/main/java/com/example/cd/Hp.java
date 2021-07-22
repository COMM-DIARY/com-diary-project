package com.example.cd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class Hp extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hp);

        Spinner aSpinner = findViewById(R.id.aSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Spinner_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aSpinner.setAdapter(adapter);
        aSpinner.setOnItemSelectedListener(this);

        b=findViewById(R.id.DA);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Hp.this,DisplayAll.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String item = parent.getItemAtPosition(position).toString();

        if(parent.getItemAtPosition(position).equals("Choose Below")){
            //do nothing...
        }
        else{
            if (parent.getItemAtPosition(position).equals("Name")) {
                Intent i = new Intent(this, Search.class);
                startActivity(i);
            } else if (parent.getItemAtPosition(position).equals("Address")) {
                Intent i = new Intent(this, Search.class);
                startActivity(i);
            } else if (parent.getItemAtPosition(position).equals("Blood Group")) {
                Intent i = new Intent(this, Search.class);
                startActivity(i);
            } else if (parent.getItemAtPosition(position).equals("Contact")) {
                Intent i = new Intent(this, Search.class);
                startActivity(i);
            } else if (parent.getItemAtPosition(position).equals("City")) {
                Intent i = new Intent(this, Search.class);
                startActivity(i);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}