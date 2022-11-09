package com.example.contactmanager;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyContactHelper extends SQLiteOpenHelper {

    //declaration de nom de la table, titre des champs
    public static final String table_contact = "Contact";
    public static final String col_fname = "Fname";
    public static final String col_lname = "Lname";
    public static final String col_phone = "Phone_number";
    public static final String col_id = "Identifiant";
    public static final String col_pwd = "Password";

    String req = "create table "+table_contact
            +"("+col_id+" Integer primary Key autoincrement,"
            +col_fname+" Text not null,"
            +col_lname+" Text,"
            +col_pwd+" Text,"
            +col_phone+" Text not null)";

    public MyContactHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(req);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" drop table "+table_contact);
        onCreate(db);

    }
}
