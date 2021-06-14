package com.app.contact_vijaylingamneni_c0800126_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class EnterInfoActivity extends AppCompatActivity {

    AppCompatEditText etFName, etLName, etAddress, etPhone,etEmail;
    MaterialButton btnSave, btnShowList;
    int existingContactId = -1;
    boolean isUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_info);
        etFName = findViewById(R.id.etFirstName);
        etLName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        btnSave = findViewById(R.id.btnSave);
        btnShowList = findViewById(R.id.btnShowList);

        setUiForUpdate();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnterInfoActivity.this,ContactListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUiForUpdate() {
        if(getIntent().hasExtra("CONTACT_DATA")){
            Contact contact = (Contact) getIntent().getSerializableExtra("CONTACT_DATA");
            etFName.setText(contact.getFirstName());
            etLName.setText(contact.getLastName());
            etAddress.setText(contact.getAddress());
            etPhone.setText(contact.getPhone());
            etEmail.setText(contact.getEmail());

            btnSave.setText("Update Contact");
            btnShowList.setVisibility(View.GONE);
            existingContactId = contact.getId();
            isUpdate = true;
        }
    }

    private void saveData(){
        if(!TextUtils.isEmpty(etFName.getText()) && !TextUtils.isEmpty(etLName.getText()) && !TextUtils.isEmpty(etPhone.getText())
        && !TextUtils.isEmpty(etEmail.getText()) && !TextUtils.isEmpty(etAddress.getText())){

            Contact contact = new Contact(etFName.getText().toString(), etLName.getText().toString(),
                    etEmail.getText().toString(), etPhone.getText().toString(), etAddress.getText().toString());

            if(isUpdate){
                contact.setId(existingContactId);
                MyDb.getInstance(getApplicationContext()).contactDao().update(contact);
                Toast.makeText(this, "Contact Updated", Toast.LENGTH_LONG).show();
                finish();
            }else {
                MyDb.getInstance(getApplicationContext()).contactDao().insert(contact);
                clearData();
            }
        }else{
            Toast.makeText(this, "Please enter all data", Toast.LENGTH_LONG).show();
        }
    }

    private void clearData(){
        etEmail.setText("");
        etPhone.setText("");
        etFName.setText("");
        etLName.setText("");
        etAddress.setText("");

        Toast.makeText(this, "Data saved", Toast.LENGTH_LONG).show();
    }
}