package com.suricapp.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.suricapp.models.Message;
import com.suricapp.models.User;
import com.suricapp.rest.client.HTTPAsyncTask;
import com.suricapp.tools.DateManipulation;
import com.suricapp.tools.DialogCreation;
import com.suricapp.tools.ImageManipulation;
import com.suricapp.tools.LocationUsage;
import com.suricapp.tools.Variables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicolas on 21/03/15.
 */
public class MessageListAdapter extends ArrayAdapter<Message> {

    Context context;
    int layoutResourceId;
    private List<Message> messageData = new ArrayList<>();
    private View row;

    private MessageInformation mMessageInformation;



    public MessageListAdapter(Context context, int layoutResourceId){
        super(context,layoutResourceId);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        row = convertView;
        mMessageInformation = null;
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            mMessageInformation = new MessageInformation();
            mMessageInformation.photo = (ImageView)row.findViewById(R.id.activity_timeline_row_photo);
            mMessageInformation.pseudo = (TextView)row.findViewById(R.id.activity_timeline_row_pseudo);
            mMessageInformation.heure = (TextView)row.findViewById(R.id.activity_timeline_row_heure);
            mMessageInformation.distance = (TextView)row.findViewById(R.id.activity_timeline_row_distance);
            mMessageInformation.contenu = (TextView)row.findViewById(R.id.activity_timeline_row_contenu);
            mMessageInformation.nbjaime = (TextView)row.findViewById(R.id.activity_timeline_row_nbjaime);
            mMessageInformation.nbjaimepas = (TextView)row.findViewById(R.id.activity_timeline_row_nbjaimepas);
            mMessageInformation.titre = (TextView)row.findViewById(R.id.activity_timeline_row_titre);

            row.setTag(mMessageInformation);
        }
        else
        {
            mMessageInformation = (MessageInformation)row.getTag();
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
        mMessageInformation.distance.setText("À : "+dist + " " + context.getString(R.string.kilometre));
        }
        else
            mMessageInformation.distance.setText("À : "+dist + " " + context.getString(R.string.metre));

        mMessageInformation.pseudo.setText(messageData.get(position).getmUser().getUser_pseudo());
        loadImage(messageData.get(position).getmUser().getUser_picture(),position);


        // Action listenr to start profil or message detail view
        mMessageInformation.pseudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchProfilView(message);
            }
        });
        mMessageInformation.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchProfilView(message);
            }
        });

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

        return row;
    }

    private void launchDetailMessageView(Message mess)
    {
        Intent detailIntent = new Intent(context, DetailMessageActivity.class);
        detailIntent.putExtra("message",mess);
        context.startActivity(detailIntent);

    }

    private void launchProfilView(Message mess)
    {
        Intent intent = new Intent(context,ProfilActivity.class);
        intent.putExtra("user",mess.getmUser());
        context.startActivity(intent);
    }

    public void swapItems(List<Message> items) {
        messageData.clear();
        messageData.addAll(items);
        clear();
        addAll(items);
        notifyDataSetChanged();
    }


    private void loadImage(String photo,int position)
    {
        if (!photo.trim().matches("")) {
            byte imageByte[] = ImageManipulation.decodeImage(photo);
            mMessageInformation.photo.setImageBitmap(BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length));
        } else {
            mMessageInformation.photo.setImageResource(context.getResources().getIdentifier
                    ("@drawable/logo", null, context.getPackageName()));
        }
    }

    static class MessageInformation
    {
        ImageView photo;
        TextView pseudo;
        TextView heure;
        TextView distance;
        TextView contenu;
        TextView nbjaime;
        TextView nbjaimepas;
        TextView nbvue;
        TextView titre;
    }
}
