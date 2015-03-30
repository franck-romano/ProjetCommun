package com.suricapp.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.suricapp.models.Comment;
import com.suricapp.models.Message;
import com.suricapp.tools.DateManipulation;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicolas on 21/03/15.
 */
public class CommentListAdapter extends ArrayAdapter<Comment> {

    Context context;
    int layoutResourceId;
    private List<Comment> commentData = new ArrayList<>();
    private View row;

    private CommentInformation mCommentInformation;



    public CommentListAdapter(Context context, int layoutResourceId){
        super(context,layoutResourceId);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        row = convertView;
        mCommentInformation = null;
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            mCommentInformation = new CommentInformation();
            mCommentInformation.pseudo = (TextView)row.findViewById(R.id.activity_detail_message_row_pseudo);
            mCommentInformation.heure = (TextView)row.findViewById(R.id.activity_detail_message_row_heure);
            mCommentInformation.comment = (TextView)row.findViewById(R.id.activity_detail_message_row_comment);

            row.setTag(mCommentInformation);
        }
        else
        {
            mCommentInformation = (CommentInformation)row.getTag();
        }

        final Comment comment = commentData.get(position);

        try {
            mCommentInformation.comment.setText(URLDecoder.decode(comment.getComment_content_fr_fr(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            Log.w("EXCEPTION",e.toString());
        }

        mCommentInformation.heure.setText(DateManipulation.timespanToString(comment.getComment_date()));

        mCommentInformation.pseudo.setText(commentData.get(position).getmUser().getUser_pseudo());

        mCommentInformation.pseudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchProfilView(comment.getComment_user_id_fk());
            }
        });

        return row;
    }

    private void launchProfilView(int id)
    {
        Intent intent = new Intent(context,ProfilActivity.class);
        intent.putExtra("userId",id);
        context.startActivity(intent);
    }

    public void swapItems(List<Comment> items) {
        commentData.clear();
        commentData.addAll(items);
        clear();
        addAll(items);
        notifyDataSetChanged();
    }


    static class CommentInformation
    {
        TextView pseudo;
        TextView heure;
        TextView comment;
    }
}
