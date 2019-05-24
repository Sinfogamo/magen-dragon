package com.example.reviv.security.help;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.example.reviv.security.Admin;
import com.example.reviv.security.R;
import com.example.reviv.security.database.myDbAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 14/12/2017.
 */

public class AdapterCategory extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<Category> items;
    myDbAdapter helper;

    public AdapterCategory(Activity activity, ArrayList<Category> items) {
        this.activity = activity;
        this.items = items;
        this.helper=new myDbAdapter(activity.getApplicationContext());
    }

    public int getCount() {
        return this.items.size();
    }

    public void clear() {
        this.items.clear();
    }

    public void addAll(ArrayList<Category> category) {
        for (int i = 0; i < category.size(); i++) {
            this.items.add(category.get(i));
        }
    }
    public Category getItemCategory(int arg0) {
        return this.items.get(arg0);
    }
    public Object getItem(int arg0) {
        return this.items.get(arg0);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_list, null);
        }
        Category dir = (Category) this.items.get(position);
        ((TextView) v.findViewById(R.id.gmailitem_title)).setText(dir.getTitle());
        ((ImageView) v.findViewById(R.id.gmailitem_letter)).setImageDrawable(dir.getImage());
        ((TextView) v.findViewById(R.id.list_desc)).setText(dir.getDesc());
        ImageButton imgBtn=(ImageButton) v.findViewById(R.id.imgBtnDelete);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View parent=(View)v.getParent();
                ListView lista=(ListView) parent.getParent();
                final int position=lista.getPositionForView(parent);
                AdapterCategory adapadorc=(AdapterCategory) lista.getAdapter();
                Category objCategory=new Category();
                objCategory=adapadorc.getItemCategory(position);

                //Log.d("Test",String.valueOf(objCategory.getCategoryId()));
                createDialog(objCategory.getCategoryId(),objCategory.getTitle(),v);

            }
        });
        return v;
    }

    public void createDialog(final String idU, String title, final View v)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());

        builder.setMessage("¿Desea eliminar al usuario "+title+"?").setTitle(R.string.eliminar);

        builder.setPositiveButton(R.string.aceptar,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                helper.deleteUser(idU);
                Activity actividad=((Activity)v.getContext());
                actividad.finish();
                actividad.overridePendingTransition(0, 0);
                actividad.startActivity(actividad.getIntent());
                actividad.overridePendingTransition(0, 0);
            }
        });
        builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog=builder.create();

        dialog.show();
    }
}
