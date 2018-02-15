package com.example.poggipc.applr;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.poggipc.applr.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by POGGIPC on 08/06/2017.
 */

public class CustomList extends ArrayAdapter<String> {

    private String[] ids;
    private String[] usernames;
    private String[] emails;
    //private String[] images;

    private Activity context;

    public CustomList(Activity context, String[] ids, String[] usernames, String[] emails/*, String[] images*/){
        super(context, R.layout.list_item,ids);
        this.context = context;
        this.ids = ids;
        this.usernames = usernames;
        this.emails = emails;
        //this.images = images;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_item,null,true);
        TextView list_item_tevId = (TextView) listViewItem.findViewById(R.id.list_item_tevId);
        TextView list_item_tevUsername = (TextView) listViewItem.findViewById(R.id.list_item_tevUsername);
        TextView list_item_tevEmail = (TextView) listViewItem.findViewById(R.id.list_item_tevEmail);
        //ImageView list_item_ivAvatar = (ImageView) listViewItem.findViewById(R.id.list_item_ivAvatar);


        //    byte[] decodedString = Base64.decode(images[position], Base64.DEFAULT);
         //   Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);



        list_item_tevId.setText(ids[position]);
        list_item_tevUsername.setText(usernames[position]);
        list_item_tevEmail.setText(emails[position]);
        //list_item_ivAvatar.setImageBitmap(bitmap);
        //Picasso.with(context).load(String.valueOf(images)).placeholder(R.mipmap.ic_launcher_round).into(list_item_ivAvatar);

        return listViewItem;
    }
}
