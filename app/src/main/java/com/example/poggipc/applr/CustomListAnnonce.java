package com.example.poggipc.applr;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.poggipc.applr.R;

/**
 * Created by INNAX on 05/02/2018.
 */

public class CustomListAnnonce extends ArrayAdapter<String> {
    private String[] idAnnonce;
    private String[] title;
    private String[] duration;
    private String[] nbPlace;
    private String[] location;
    private String[] description;

    private Activity context;

    public CustomListAnnonce(Activity context, String[] idAnnonce, String[] title, String[] duration, String[] nbPlace, String[] location, String[] description){
        super(context, R.layout.list_item_annonce,idAnnonce);
        this.context = context;
        this.idAnnonce = idAnnonce;
        this.title = title;
        this.duration = duration;
        this.nbPlace = nbPlace;
        this.location = location;
        this.description = description;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItemAnnonce = inflater.inflate(R.layout.list_item_annonce,null,true);
        TextView list_item_annonce_tv_Id = (TextView) listViewItemAnnonce.findViewById(R.id.list_item_annonce_tv_Id);
        TextView list_item_annonce_tv_Title = (TextView) listViewItemAnnonce.findViewById(R.id.list_item_annonce_tv_Title);
        TextView list_tem_annonce_tv_Duree = (TextView) listViewItemAnnonce.findViewById(R.id.list_tem_annonce_tv_Duree);
        TextView list_item_annonce_tv_NbPlace = (TextView) listViewItemAnnonce.findViewById(R.id.list_item_annonce_tv_NbPlace);
        TextView list_item_annonce_tv_Location = (TextView) listViewItemAnnonce.findViewById(R.id.list_item_annonce_tv_Location);
        TextView list_item_annonce_tv_Description = (TextView) listViewItemAnnonce.findViewById(R.id.list_item_annonce_tv_Description);

        list_item_annonce_tv_Id.setText(idAnnonce[position]);
        list_item_annonce_tv_Title.setText(title[position]);
        list_tem_annonce_tv_Duree.setText(duration[position]);
        list_item_annonce_tv_NbPlace.setText(nbPlace[position]);
        list_item_annonce_tv_Location.setText(location[position]);
        list_item_annonce_tv_Description.setText(description[position]);
        return listViewItemAnnonce;
    }

}
