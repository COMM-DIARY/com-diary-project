package com.example.cd;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cd.adapter.RecyclerViewAdapter;
import com.example.cd.contact.Contact;
import com.example.cd.data.MyDbHandler;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Contact> contactArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyDbHandler db = new MyDbHandler(Search.this);

        contactArrayList = new ArrayList<>();


        List<Contact> contactList = db.getAllContacts();
        for (Contact contact : contactList) {

            Log.d("dbinfo", "\nId: " + contact.getSr_no() + "\n" +
                    "Name: " + contact.getName() + "\n" +
                    "Address: " + contact.getAdd() + "\n" +
                    "City: " + contact.getCity() + "\n" +
                    "Mobile Number: " + contact.getMobile_no() + "\n" +
                    "Blood Group: " + contact.getBlood() + "\n");

            contactArrayList.add(contact);
        }
        recyclerViewAdapter = new RecyclerViewAdapter(Search.this,contactArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);

    }

}