package com.example.contactmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyListViewAdapter extends BaseAdapter {

    Context context;
    ArrayList<Contact> data;

    public MyListViewAdapter(Context context, ArrayList<Contact> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {//traja3 9adech mn view à créer
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //creation d'un view
        LayoutInflater lf = LayoutInflater.from(context);
        View v = lf.inflate(R.layout.view_contact, null);

        //récuperer les holders
        TextView tv_fname = v.findViewById(R.id.tv_fname_vc);
        TextView tv_lname = v.findViewById(R.id.tv_lname_vc);
        TextView tv_phone = v.findViewById(R.id.tv_phone_vc);
        ImageView img_call = v.findViewById(R.id.iv_call);
        ImageView img_edit = v.findViewById(R.id.iv_edit);
        ImageView img_delete = v.findViewById(R.id.iv_delete);

        Contact c = data.get(i);
        tv_fname.setText(c.fname);
        tv_lname.setText(c.lname);
        tv_phone.setText(c.phone_number);

        ContactDAO manager = new ContactDAO(context);
        manager.open("ContactDB.db");

        int id = manager.getID(c.phone_number);

        img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:"+c.phone_number));
                context.startActivity(i);
            }
        });

        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //creation d'un view
                LayoutInflater lf2 = LayoutInflater.from(context);
                View alert_v = lf2.inflate(R.layout.edit_alert, null);

                EditText et_fname = alert_v.findViewById(R.id.et_fname_edit);
                EditText et_lname = alert_v.findViewById(R.id.et_lname_edit);
                EditText et_phone = alert_v.findViewById(R.id.et_phone_edit);

                et_fname.setText(c.fname);
                et_lname.setText(c.lname);
                et_phone.setText(c.phone_number);

                AlertDialog.Builder alert=new AlertDialog.Builder(context );
                alert.setTitle("Edit Contact"); // titre
                alert.setView(alert_v);

                /* Ajout d'un bouton avec action utilisant une classe anonyme */

                alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String fname = et_fname.getText().toString();
                        String lname = et_lname.getText().toString();
                        String phone = et_phone.getText().toString();

                        if(!fname.isEmpty() && !lname.isEmpty() && phone.matches("^[24579][0-9]{7}$")) {
                            manager.edit(id, fname, lname, phone);
                            data.set(i,new Contact(fname,lname,phone, null));


                            Toast.makeText(context, "User updated successfully", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        }
                        else
                            Toast.makeText(context, "Invalid input !", Toast.LENGTH_SHORT).show();

                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


//                /* Ajout d'un autre bouton sans action */
//                alert.setNeutralButton("Cancel",null); // bouton sans action

                // creation d'une instance de l'alert dialogue
                AlertDialog dialog=alert.create();
                // affichage du boite de dialog
                dialog.show();
                // pour fermer la boite de dialog explicitement, on peut utiliser dialog.dismiss();

            }
        });

        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.remove(i);
                ContactDAO manager = new ContactDAO(context);
                manager.open("ContactDB.db");
                manager.delete(id);

                notifyDataSetChanged();
            }
        });

        return v;
    }
}
