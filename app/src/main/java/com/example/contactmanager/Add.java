package com.example.contactmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Add extends AppCompatActivity {

    private EditText ed_lname, ed_fname, ed_phone;
    private Button btn_add, btn_reset, btn_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ed_lname = findViewById(R.id.ed_lname_add);
        ed_fname = findViewById(R.id.ed_fname_add);
        ed_phone = findViewById(R.id.ed_phone_add);

        btn_add = findViewById(R.id.btn_add);
        btn_reset = findViewById(R.id.btn_reset_add);
        btn_show = findViewById(R.id.btn_show_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = ed_fname.getText().toString();
                String lname = ed_lname.getText().toString();
                String phone = ed_phone.getText().toString();
                if(fname.isEmpty() || phone.isEmpty()){
                    Toast.makeText(Add.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
                else{
                    ContactDAO manager = new ContactDAO(Add.this);
                    manager.open("ContactDB.db");
                    manager.insert(fname, lname, phone, null);
                    ed_fname.setText("");
                    ed_lname.setText("");
                    ed_phone.setText("");
                    Toast.makeText(Add.this, fname+" "+lname+" added successfully to your contacts", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed_fname.setText("");
                ed_lname.setText("");
                ed_phone.setText("");
            }
        });

        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Add.this, Show.class);
                startActivity(i);
            }
        });

    }
}