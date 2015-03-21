package com.suricapp.views;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.suricapp.models.Message;

import java.util.List;

/**
 * Created by Nicolas on 21/03/15.
 */
public class MessageListAdapter extends ArrayAdapter<Message> {

    Context context;
    int layoutResourceId;
    private List<Message> messageData;
    private MessageInformation mMessageInformation;


    public MessageListAdapter(Context context, int layoutResourceId){
        super(context,layoutResourceId);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
    }

    public List<Message> getMessageData() {
        return messageData;
    }

    public void setMessageData(List<Message> messageData) {
        this.messageData = messageData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        mMessageInformation = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            mMessageInformation = new MessageInformation();
            mMessageInformation.photo = (ImageView)row.findViewById(R.id.activity_timeline_photo);
            mMessageInformation.pseudo = (TextView)row.findViewById(R.id.activity_timeline_pseudo);
            mMessageInformation.heure = (TextView)row.findViewById(R.id.activity_timeline_heure);
            mMessageInformation.distance = (TextView)row.findViewById(R.id.activity_timeline_distance);
            mMessageInformation.contenu = (TextView)row.findViewById(R.id.activity_timeline_contenu);
            mMessageInformation.nbjaime = (TextView)row.findViewById(R.id.activity_timeline_nbjaime);
            mMessageInformation.nbjaimepas = (TextView)row.findViewById(R.id.activity_timeline_nbjaimepas);
            mMessageInformation.nbvue = (TextView)row.findViewById(R.id.activity_timeline_nbvue);

            row.setTag(mMessageInformation);
        }
        else
        {
            mMessageInformation = (MessageInformation)row.getTag();
        }

        Message message = messageData.get(position);
        //mMessageInformation.photo.setImage(message.getMessage_id_user_fk().getUser_picture());
        mMessageInformation.pseudo.setText(message.getMessage_id_user_fk().getUser_pseudo());
        mMessageInformation.heure.setText(message.getMessage_date().toString());
        //mMessageInformation.distance.setText(message.getMessage_id_point_fk().getPoint_longitude());
        mMessageInformation.contenu.setText(message.getMessage_content_fr_fr());
        mMessageInformation.nbjaime.setText(message.getMessage_nb_like());
        mMessageInformation.nbjaimepas.setText(message.getMessage_nb_unlike());
        mMessageInformation.nbvue.setText(message.getMessage_nb_view());

        return row;
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
    }
}
