package com.suricapp.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.suricapp.models.Comment;
import com.suricapp.models.Message;
import com.suricapp.models.User;
import com.suricapp.models.UserComment;
import com.suricapp.models.UserMessageTimeline;
import com.suricapp.rest.client.HTTPAsyncTask;
import com.suricapp.tools.DateManipulation;
import com.suricapp.tools.DialogCreation;
import com.suricapp.tools.ImageManipulation;
import com.suricapp.tools.LocationUsage;
import com.suricapp.tools.Variables;
import com.suricapp.tools.ViewModification;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class DetailMessageActivity extends SuricappActionBar implements View.OnClickListener{


    // Usefull item for list view
    private ArrayList<Comment> allComments;

    //Variables pour la listView
    private CommentListAdapter commentAdapter;
    private ListView commentList;
    private EditText mCommentEditText;
    private Button mEnvoyerButton;

    /**
     * View to hide or show spinner
     */
    private View mView;
    private View mSpinnerView;

    // View items
    private ImageView mPhotoImageView;
    private TextView mLoginTextView;
    private TextView mDateTextView;
    private TextView mDistanceTextView;
    private TextView mTitreTextView;
    private TextView mContentTextView;
    private TextView mJaimeTextView;
    private TextView mJaimePasTextView;

    // Message shows
    private Message mMessage;

    // List for userPosition
    ArrayList<UserComment> userAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_detail_message);
        super.onCreate(savedInstanceState);

        // Get message to show
        mMessage = (Message)getIntent().getSerializableExtra("message");

        // View initialisation
        mPhotoImageView = (ImageView) findViewById(R.id.activity_detail_message_photo);
        mLoginTextView =(TextView) findViewById(R.id.activity_detail_message_pseudo);
        mDateTextView = (TextView) findViewById(R.id.activity_detail_message_heure);
        mDistanceTextView = (TextView) findViewById(R.id.activity_detail_message_distance);
        mTitreTextView = (TextView) findViewById(R.id.activity_detail_message_titre);
        mContentTextView = (TextView) findViewById(R.id.activity_detail_message_contenu);
        mJaimePasTextView = (TextView) findViewById(R.id.activity_detail_message_nbjaimepas);
        mJaimeTextView = (TextView) findViewById(R.id.activity_detail_message_nbjaime);
        mCommentEditText=(EditText) findViewById(R.id.activity_detail_message_commentaire);
        mEnvoyerButton = (Button) findViewById(R.id.activity_detail_message_envoyer);
        mEnvoyerButton.setOnClickListener(this);

        // List View
        commentList = (ListView)findViewById(R.id.activity_detail_message_listView);
        commentList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        commentAdapter = new CommentListAdapter(this,R.layout.detail_message_list_row);
        commentList.setAdapter(commentAdapter);

        //View for show or not the spinner
        mView = findViewById(R.id.detail_message_comment_view);
        mSpinnerView = findViewById(R.id.activity_detail_message_status);

        // Load Message informations
        loadMessageInfos();
        loadCommentAndUsers();

    }

    //
    private void loadCommentAndUsers() {
        ViewModification.showProgress(true, mSpinnerView, mView, getLocalContext());
        HTTPAsyncTask taskMessage= new HTTPAsyncTask(getLocalContext());
        taskMessage.execute(null, Variables.GETCOMMENTFROMMESSAGEID+mMessage.getMessage_id()+Variables.ORDERBYCOMMENTDATEASC,"GET",null);
        taskMessage.setMyTaskCompleteListener(new HTTPAsyncTask.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(String result) {
                try {
                    JSONArray jarray = new JSONArray(result);
                    userAdd = new ArrayList<UserComment>();
                    allComments = new ArrayList<>();
                    StringBuilder sb = new StringBuilder(Integer.parseInt(jarray.getJSONObject(0).getString("comment_user_id_fk")));
                    for (int i = 0; i < jarray.length(); i++) {
                        UserComment uc = new UserComment();

                        JSONObject jsonObject = jarray.getJSONObject(i);
                        Comment com = new Comment();

                        com.setComment_user_id_fk(Integer.parseInt(jsonObject.getString("comment_user_id_fk")));

                        uc.setmUserId(Integer.parseInt(jsonObject.getString("comment_user_id_fk")));
                        uc.setmCommentPosition(i);

                        userAdd.add(uc);
                        sb.append("," + Integer.parseInt(jsonObject.getString("comment_user_id_fk")));

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        Date parsedDate = dateFormat.parse(jsonObject.getString("comment_date"));
                        com.setComment_date(new java.sql.Timestamp(parsedDate.getTime()));
                        com.setComment_content_fr_fr(jsonObject.getString("comment_content_fr_fr"));
                        allComments.add(com);
                    }
                    getUserInfo(sb.toString());
                } catch (JSONException e) {
                    Log.w("BAD1", e.toString());
                    ViewModification.showProgress(false, mSpinnerView, mView, getLocalContext());
                } catch (ParseException e) {
                    Log.w("BAD2", e.toString());
                } catch (Exception e) {
                    Log.w("BAD3", e.toString());
                }
            }
        });
    }

    private void getUserInfo(String req) {
        HTTPAsyncTask taskPseudo = new HTTPAsyncTask(getLocalContext());
        taskPseudo.execute(null, Variables.GETMULTIPLEUSERWITHID +req, "GET", null);
        taskPseudo.setMyTaskCompleteListener(new HTTPAsyncTask.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(String result) {
                JSONArray jarray = null;
                try {

                    jarray = new JSONArray(result);
                    for (int i=0;i<jarray.length();i++)
                    {
                        setUserToMessage(jarray.getJSONObject(i));
                    }
                    commentAdapter.swapItems(allComments);
                    ViewModification.showProgress(false, mSpinnerView, mView, getLocalContext());
                    mEnvoyerButton.setEnabled(true);
                } catch (Exception e) {
                    Log.w("EXCEPTION", e.toString());
                }
            }
        });
    }

    private void setUserToMessage(JSONObject jsonObject) throws JSONException {
        User user = new User();
        user.setUser_pseudo(jsonObject.getString("user_pseudo"));
        user.setUser_id(Integer.parseInt(jsonObject.getString("user_id")));
        user.setUser_picture(jsonObject.getString("user_picture"));
        for(int i=0; i < userAdd.size();i++)
        {
            if(userAdd.get(i).getmUserId() == user.getUser_id()) {
                allComments.get(userAdd.get(i).getmCommentPosition()).setmUser(user);
            }
        }
    }


    private void loadMessageInfos()
    {
        if(!mMessage.getmUser().getUser_picture().trim().matches("")) {
            byte image[] = ImageManipulation.decodeImage(mMessage.getmUser().getUser_picture());
            mPhotoImageView.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
        }
        mLoginTextView.setText(mMessage.getmUser().getUser_pseudo());
        mDateTextView.setText(DateManipulation.timespanToString(mMessage.getMessage_date()));
        int dist = LocationUsage.disctanceBetween(mMessage.getMessage_latitude()
                , mMessage.getMessage_longitude(), this);
        if(dist>1000) {
            dist = dist/1000;
            mDistanceTextView.setText("À : " + dist + " " + getString(R.string.kilometre));
        }
        else
            mDistanceTextView.setText("À : " + dist + " " + getString(R.string.metre));
        try {
            mTitreTextView.setText(URLDecoder.decode(mMessage.getMessage_title_fr_fr(), "UTF-8"));
            mContentTextView.setText(URLDecoder.decode(mMessage.getMessage_content_fr_fr(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            Log.w("EXCEPTION",e.toString());
        }

        mJaimeTextView.setText(mMessage.getMessage_nb_like()+getString(R.string.jaime));
        mJaimePasTextView.setText(mMessage.getMessage_nb_unlike()+getString(R.string.jaimepas));

        mPhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchProfilView();
            }
        });
        mLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchProfilView();
            }
        });
    }

    private void launchProfilView()
    {
        Intent intent = new Intent(this,ProfilActivity.class);
        intent.putExtra("user",mMessage.getmUser());
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_message, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.activity_detail_message_envoyer:
                if(mCommentEditText.getText().length() == 0)
                {
                    DialogCreation.createDialog(this,getString(R.string.erreur),getString(R.string.commentaire_empty));
                }else
                {
                    postComment();
                }
                break;
        }
    }

    private void postComment()
    {
        // Date of post message
        mEnvoyerButton.setEnabled(false);
        Comment comment = new Comment();
        comment.setComment_date(new Timestamp(System.currentTimeMillis()));
        comment.setComment_message_id_fk(mMessage.getMessage_id());
        comment.setComment_user_id_fk(Integer.parseInt(getSharedPreferences(Variables.SURICAPPREFERENCES, Context.MODE_PRIVATE).getString("userLogId", "0")));
        try {
            comment.setComment_content_fr_fr(URLEncoder.encode(mCommentEditText.getText().toString(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            Log.w("EXEPTION",e.toString());
        }
        // Sent the message to server
        try {
            HTTPAsyncTask task = new HTTPAsyncTask(this);
            task.execute(null, Variables.POSTCOMMENT, "POST", comment.objectToNameValuePair());
            task.setMyTaskCompleteListener(new HTTPAsyncTask.OnTaskComplete() {
                @Override
                public void setMyTaskComplete(String message) {
                    Toast.makeText(getLocalContext(), getString(R.string.comment_saved), Toast.LENGTH_LONG).show();
                    setResult(Variables.REQUESTLOADMESSAGE);
                    removeFocusAndClear();
                    loadCommentAndUsers();
                }
            });
        }catch (Exception e)
        {
            Log.w("Exception Push",e.toString());
        }
    }

    private void removeFocusAndClear()
    {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mCommentEditText.getWindowToken(), 0);
        mCommentEditText.setText("");
    }
}
