package com.example.contactmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ContactDAO {
    Context con;
    SQLiteDatabase mybase;

    //Constructer
    public ContactDAO(Context con){
        this.con = con;
    }

    public void open(String file){
        MyContactHelper helper = new MyContactHelper(con, file, null, 1);
        // declaration d'une base
        // si on modifie la version == appel implicite à onUpgrade
        mybase = helper.getWritableDatabase();
        // permet d'ouvrir la base si elle existe
        // si n'existe pas, elle cree le fichier
        // et appel oncreate ==> creation des tables
    }

    public void close(){
        mybase.close();
    }

    public void insert(String fname, String lname,String phone, String pwd){
        //insertion dans la base
        ContentValues v = new ContentValues();
        //v est un hashmap
        v.put(MyContactHelper.col_fname,fname);
        v.put(MyContactHelper.col_lname,lname);
        v.put(MyContactHelper.col_phone,phone);
        v.put(MyContactHelper.col_pwd,pwd);
        mybase.insert(MyContactHelper.table_contact,null,v);
    }

    public Boolean checkUser(String phone){
        Cursor cursor = mybase.rawQuery("select * from "+MyContactHelper.table_contact+" where "+MyContactHelper.col_phone+" =?",new String[]{phone});
        if(cursor.getCount() > 0) {
            System.out.println("User already exists !");
            return true;
        }
        else
            return false;
    }

    public Boolean checkUserAndPassword(String phone, String pwd){

        Cursor cursor = mybase.rawQuery("Select * from "+MyContactHelper.table_contact+" where "+MyContactHelper.col_phone+" = ? and "+MyContactHelper.col_pwd+" = ?", new String[] {phone,pwd});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    String getName(String phone){
        Cursor cr = mybase.query(MyContactHelper.table_contact, new String[]{MyContactHelper.col_fname}, MyContactHelper.col_phone +"="+ phone, null, null, null, null);
        cr.moveToFirst();
            return cr.getString(0);

        }

        int getID(String phone){
            Cursor cr = mybase.query(MyContactHelper.table_contact, new String[]{MyContactHelper.col_id}, MyContactHelper.col_phone +"="+ phone, null, null, null, null);
            cr.moveToFirst();
            return cr.getInt(0);

        }

    ArrayList<Contact> showAll(){
        // initialisation de la valeur de retour
        ArrayList<Contact> data=new ArrayList<Contact>();
        // selection depuis la base
        Cursor cr=mybase.query(MyContactHelper.table_contact
                ,new String[]{MyContactHelper.col_id,MyContactHelper.col_fname,
                        MyContactHelper.col_lname,MyContactHelper.col_phone,MyContactHelper.col_pwd}
                ,null,null,null,null,null
        );
        // conversion d'un cursor à une arraylist data
        cr.moveToFirst();
        while (!cr.isAfterLast()) {
            int id = cr.getInt(0);//nrmlment l'ID
            String fname = cr.getString(1);
            String lname = cr.getString(2);
            String phone = cr.getString(3);
            String pwd = cr.getString(4);
            data.add(new Contact(fname, lname, phone, pwd));
            cr.moveToNext();
        }
        return data;
    }

    long edit(int id, String fname, String lname, String phone){
        int a=-1;
        // initialisation nouvelle valeur
        ContentValues v=new ContentValues();
        v.put(MyContactHelper.col_id,id);
        v.put(MyContactHelper.col_fname,fname);
        v.put(MyContactHelper.col_lname,lname);
        v.put(MyContactHelper.col_phone,phone);
        a=mybase.update(MyContactHelper.table_contact,
                v,
                MyContactHelper.col_id+"="+id,null);
        System.out.println("User modified");
        return a;
    }

    long delete(int id)
    {
        int a=-1;
        a=mybase.delete(MyContactHelper.table_contact,
                MyContactHelper.col_id+"="+id,null);
        return a;
    }
}

