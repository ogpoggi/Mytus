package com.example.poggipc.applr;

import android.app.Activity;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.poggipc.applr.R;
import com.squareup.picasso.Picasso;

/**
 * Created by INNAX on 05/02/2018.
 */

public class CustomListAnnonce extends ArrayAdapter<String>{
    private String[] idAnnonce;
    private String[] title;
    private String[] duration;
    private String[] nbPlace;
    private String[] location;
    private String[] description;
    private String[] avatar;
    //private String[] nomcateg;

    private Activity context;

    public CustomListAnnonce(Activity context, String[] idAnnonce, String[] title, String[] duration, String[] nbPlace, String[] location, String[] description, String[] avatar/*, String[] nomcateg*/){
        super(context, R.layout.list_item_annonce,idAnnonce);
        this.context = context;
        this.idAnnonce = idAnnonce;
        this.title = title;
        this.duration = duration;
        this.nbPlace = nbPlace;
        this.location = location;
        this.description = description;
        this.avatar = avatar;
        //this.nomcateg = nomcateg;
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
        //TextView list_item_annonce_tv_Categ = (TextView) listViewItemAnnonce.findViewById(R.id.list_item_annonce_tv_Categ);
        ImageView list_item_annonce_iv_Avatar = (ImageView) listViewItemAnnonce.findViewById(R.id.list_item_annonce_iv_Avatar);
        Button list_tem_annonce_btn_participe = (Button) listViewItemAnnonce.findViewById(R.id.list_tem_annonce_btn_participe);

        list_item_annonce_tv_Id.setText(idAnnonce[position]);
        list_item_annonce_tv_Title.setText(title[position]);
        list_tem_annonce_tv_Duree.setText(duration[position]);
        list_item_annonce_tv_NbPlace.setText("Nb Place\n"+nbPlace[position]);
        list_item_annonce_tv_Location.setText(location[position]);
        list_item_annonce_tv_Description.setText(description[position]);
        //list_item_annonce_tv_Categ.setText(nomcateg[position]);
        Picasso.with(context).load(avatar[position]).placeholder(R.mipmap.ic_launcher_round).into(list_item_annonce_iv_Avatar);
        list_tem_annonce_btn_participe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("YEAHHHHHHHHHHHH","MESSSSSSSSS");
            }
        });


        return listViewItemAnnonce;
    }

}
