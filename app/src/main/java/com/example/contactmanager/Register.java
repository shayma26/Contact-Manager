package com.example.contactmanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {

    private EditText ed_fname, ed_lname, ed_phone, ed_password;
    private Button btn_register, btn_cancel;
    private TextView login_link;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ed_fname = findViewById(R.id.ed_fname_register);
        ed_lname = findViewById(R.id.ed_lname_register);
        ed_phone = findViewById(R.id.ed_phone_register);
        ed_password = findViewById(R.id.ed_password_register);
        login_link = findViewById(R.id.tv_login_link);
        btn_cancel = findViewById(R.id.btn_cancel_register);
        btn_register = findViewById(R.id.btn_register);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish()
                Register.this.finish();
            }
        });

        login_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Register.this, MainActivity.class);
                Register.this.finish();
                startActivity(i);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactDAO manager = new ContactDAO(Register.this);
                manager.open("ContactDB.db");

                String fname = ed_fname.getText().toString();
                String lname = ed_lname.getText().toString();
                String pwd = ed_password.getText().toString();
                String phone = ed_phone.getText().toString();


                if(!fname.isEmpty() && !lname.isEmpty() && !pwd.isEmpty() && pwd.length()>3 && phone.matches("^[24579][0-9]{7}$") && !manager.checkUser(phone)){
                    manager.insert(fname, lname, phone, pwd);
                    Toast.makeText(Register.this, "Member added successfully ! Please login", Toast.LENGTH_SHORT).show();
                   System.out.println("****************Show All Contacts****************");
                    System.out.println(manager.showAll());
                    System.out.println("****************End All Contacts****************");
                    /* Intent i = new Intent(Register.this, MainActivity.class);
                    Register.this.finish();
                    startActivity(i);*/
                }else{
                    Toast.makeText(Register.this, "Invalid credentials or user already exists", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    }
