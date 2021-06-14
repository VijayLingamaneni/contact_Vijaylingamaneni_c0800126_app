package com.app.contact_vijaylingamneni_c0800126_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {

    RecyclerView rvContacts;
    ContactListAdapter contactListAdapter;
    AppCompatEditText etSearch;
    List<Contact> contactList = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        rvContacts =  findViewById(R.id.rvContacts);
        etSearch = findViewById(R.id.etSearch);


        contactListAdapter = new ContactListAdapter(contactList);
        rvContacts.setAdapter(contactListAdapter);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                // filter your list from your input
                if(s.length() ==0){
                    contactListAdapter.updateList(contactList);
                }else {
                    filter(s.toString());
                }

                //you can use runnable postDelayed like 500 ms to delay search text
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        contactList.clear();
        contactList.addAll(MyDb.getInstance(getApplicationContext()).contactDao().getContacts());
        contactListAdapter.notifyDataSetChanged();
    }

    void filter(String text){
        List<Contact> temp = new ArrayList();
        for(Contact d: contactList){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getPhone().contains(text)){
                temp.add(d);
            }
        }
        //update recyclerview
        contactListAdapter.updateList(temp);
    }
}