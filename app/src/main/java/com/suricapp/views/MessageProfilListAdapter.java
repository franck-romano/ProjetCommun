package com.suricapp.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.suricapp.models.Message;
import com.suricapp.tools.DateManipulation;
import com.suricapp.tools.ImageManipulation;
import com.suricapp.tools.LikeUnlike;
import com.suricapp.tools.LocationUsage;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicolas on 21/03/15.
 */
public class MessageProfilListAdapter extends ArrayAdapter<Message> {

    Context context;
    int layoutResourceId;
    private List<Message> messageData = new ArrayList<>();
    private View row;

    private MessageInformationProfil mMessageInformation;



    public MessageProfilListAdapter(Context context, int layoutResourceId){
        super(context,layoutResourceId);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
    }

    public void refreshView()
    {
        clear();
        addAll(messageData);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        row = convertView;
        mMessageInformation = null;
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            mMessageInformation = new MessageInformationProfil();
            mMessageInformation.heure = (TextView)row.findViewById(R.id.activity_profil_row_heure);
            mMessageInformation.distance = (TextView)row.findViewById(R.id.activity_profil_row_distance);
            mMessageInformation.contenu = (TextView)row.findViewById(R.id.activity_profil_row_contenu);
            mMessageInformation.nbjaime = (TextView)row.findViewById(R.id.activity_profil_row_nbjaime);
            mMessageInformation.nbjaimepas = (TextView)row.findViewById(R.id.activity_profil_row_nbjaimepas);
            mMessageInformation.titre = (TextView)row.findViewById(R.id.activity_profil_row_titre);
            mMessageInformation.jaimepasView = row.findViewById(R.id.activity_profil_row_nbjaimepas_view);
            mMessageInformation.jaimeView = row.findViewById(R.id.activity_profil_row_nbjaime_view);
            mMessageInformation.photoCateg = (ImageView) row.findViewById(R.id.activity_profil_row_photo_categ);

            row.setTag(mMessageInformation);
        }
        else
        {
            mMessageInformation = (MessageInformationProfil)row.getTag();
        }

        final Message message = messageData.get(position);

        try {
            mMessageInformation.titre.setText(URLDecoder.decode(message.getMessage_title_fr_fr(),"UTF-8"));
            mMessageInformation.contenu.setText(URLDecoder.decode(message.getMessage_content_fr_fr(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            Log.w("EXCEPTION",e.toString());
        }

        mMessageInformation.heure.setText(DateManipulation.timespanToString(message.getMessage_date()));
        mMessageInformation.nbjaime.setText(message.getMessage_nb_like()+" "+context.getString(R.string.jaime));
        mMessageInformation.nbjaimepas.setText(+message.getMessage_nb_unlike()+" "+context.getString(R.string.jaimepas));


        int dist = LocationUsage.disctanceBetween(message.getMessage_latitude()
                ,message.getMessage_longitude(),context);
        if(dist>1000) {
            dist = dist/1000;
            mMessageInformation.distance.setText("À :"+dist + " " + context.getString(R.string.kilometre));
        }
        else
            mMessageInformation.distance.setText("À :"+dist + " " + context.getString(R.string.metre));

        mMessageInformation.titre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchDetailMessageView(message);
            }
        });

        mMessageInformation.contenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchDetailMessageView(message);
            }
        });

        mMessageInformation.jaimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LikeUnlike.likeMessageProfil(getCurrentAdapter(), context, message);
            }
        });
        mMessageInformation.jaimepasView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LikeUnlike.unlikeMessageProfil(getCurrentAdapter(),context,message);
            }
        });

        setCorrectPhotoCateg(mMessageInformation,message);
        return row;
    }

    private void setCorrectPhotoCateg(MessageInformationProfil mMessageInformation,Message message)
    {
        switch (message.getMessage_id_category_fk())
        {
            case 2:
                mMessageInformation.photoCateg.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.categ_transport));
                break;
            case 3:
                mMessageInformation.photoCateg.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.categ_promotion));
                break;
            case 4 :
                mMessageInformation.photoCateg.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.categ_rencontre));
                break;
            case 5 :
                mMessageInformation.photoCateg.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.categ_evenement));
                break;
            case 6 :
                mMessageInformation.photoCateg.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.categ_autorites));
                break;
            case 7 :
                mMessageInformation.photoCateg.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.categ_covoiturage));
                break;
        }

    }

    private MessageProfilListAdapter getCurrentAdapter()
    {
        return this;
    }

    public void swapItems(List<Message> items) {
        messageData.clear();
        messageData.addAll(items);
        clear();
        addAll(items);
        notifyDataSetChanged();
    }

    private void launchDetailMessageView(Message mess)
    {
        Intent detailIntent = new Intent(context, DetailMessageActivity.class);
        detailIntent.putExtra("message",mess);
        context.startActivity(detailIntent);
    }

    static class MessageInformationProfil
    {
        View jaimeView;
        View jaimepasView;
        TextView heure;
        TextView distance;
        TextView contenu;
        TextView nbjaime;
        TextView nbjaimepas;
        TextView titre;
        ImageView photoCateg;
    }
}
