package com.example.contactmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {


    private EditText ed_phone, ed_password;
    private Button btn_login,btn_cancel;
    private TextView register_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed_phone = findViewById(R.id.ed_phone_auth);
        ed_password = findViewById(R.id.ed_password_auth);
        btn_login = findViewById(R.id.btn_login_auth);
        btn_cancel = findViewById(R.id.btn_cancel_auth);
        register_link = findViewById(R.id.tv_register_link);

        //action listener
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish()
                MainActivity.this.finish();
            }
        });

        register_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Register.class);
                MainActivity.this.finish();
                startActivity(i);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactDAO manager = new ContactDAO(MainActivity.this);
                manager.open("ContactDB.db");
                String phone = ed_phone.getText().toString();
                String pwd = ed_password.getText().toString();
                if(manager.checkUserAndPassword(phone, pwd)){
                    Intent i = new Intent(MainActivity.this, Home.class);
                    i.putExtra("USER", manager.getName(phone));
                    MainActivity.this.finish();
                    startActivity(i);

                }else{
                    Toast.makeText(MainActivity.this, "Please verify your credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
//lazem tzid implements View.OnClickListener
//    @Override
//    public void onClick(View v) {
//        if(v==btn_cancel){
//
//        }
//        if(v==btn_login){
//
//        }
//    }
}