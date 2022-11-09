package com.example.contactmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;


public class Show extends AppCompatActivity {

    private ListView lv;
    SearchView search_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        lv = findViewById(R.id.lv_show);

        search_view = findViewById(R.id.sv_search);
        ContactDAO manager = new ContactDAO(Show.this);
        manager.open("ContactDB.db");
        MyListViewAdapter ad = new MyListViewAdapter(Show.this, manager.showAll());
        lv.setAdapter(ad);

        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                ArrayList<Contact> filter_contacts = new ArrayList<Contact>();
                for(Contact c : manager.showAll()){
                    if(c.fname.toLowerCase().contains(s.toLowerCase()) ||
                            c.lname.toLowerCase().contains(s.toLowerCase()) || c.phone_number.toLowerCase().contains(s.toLowerCase()))
                        filter_contacts.add(c);
                }

                MyListViewAdapter search_ad = new MyListViewAdapter(Show.this, filter_contacts);
                lv.setAdapter(search_ad);

                return false;
            }
        });

    }
}

/*
recycle view ==== list view
 */