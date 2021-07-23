package com.example.cd;

import android.app.SearchManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cd.adapter.RecyclerViewAdapter;
import com.example.cd.contact.Contact;
import com.example.cd.data.MyDbHandler;

import java.util.ArrayList;
import java.util.List;


public class SearchCity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Contact> contactArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_city);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadData();

    }

    private void loadData() {
        MyDbHandler db = new MyDbHandler(SearchCity.this);

        contactArrayList = new ArrayList<>();

        List<Contact> contactList = db.getAllContacts();
        for (Contact contact : contactList) {
            contactArrayList.add(contact);
        }
        recyclerViewAdapter = new RecyclerViewAdapter(SearchCity.this,contactArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchContactC(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchContactC(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void searchContactC(String keyword) {
        MyDbHandler db = new MyDbHandler(SearchCity.this);

        contactArrayList = new ArrayList<>();

        List<Contact> contactList = db.searchCity(keyword);
        if(contactList != null){
            for (Contact contact : contactList) {
                contactArrayList.add(contact);
            }
        }else{
            Toast toast = Toast.makeText(SearchCity.this,"Data Not Found",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        recyclerViewAdapter = new RecyclerViewAdapter(SearchCity.this,contactArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

}
