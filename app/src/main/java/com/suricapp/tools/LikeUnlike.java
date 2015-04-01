package com.suricapp.tools;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.suricapp.models.Message;
import com.suricapp.rest.client.HTTPAsyncTask;
import com.suricapp.views.MessageListAdapter;
import com.suricapp.views.MessageProfilListAdapter;
import com.suricapp.views.R;

/**
 * Created by maxence on 31/03/15.
 */
public class LikeUnlike {

    /**
     * Like a message and change text colo and view
     * @param context
     * @param message
     */
    public static void likeMessageTimeline(final MessageListAdapter adapter,final Context context,final Message message)
    {
        message.setMessage_nb_like(message.getMessage_nb_like()+1);
        HTTPAsyncTask taskPseudo= new HTTPAsyncTask(context);
        taskPseudo.execute(null,Variables.PUTMESSAGE+message.getMessage_id(),"PUT",message.objectToNameValuePair());
        Log.w("URL", Variables.PUTMESSAGE + message.getMessage_id());
        taskPseudo.setMyTaskCompleteListener(new HTTPAsyncTask.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(String result) {
                Log.w("Result", result);
                adapter.refreshView();
            }
        });
    }

    /**
     ** UnLike a message and change text colo and view
     * @param context
     * @param message
     */
    public static void unlikeMessageTimeline(final MessageListAdapter adapter,final Context context,final Message message){
        message.setMessage_nb_unlike(message.getMessage_nb_unlike() + 1);
        HTTPAsyncTask taskPseudo = new HTTPAsyncTask(context);
        taskPseudo.execute(null, Variables.PUTMESSAGE + message.getMessage_id(), "PUT", message.objectToNameValuePair());
        taskPseudo.setMyTaskCompleteListener(new HTTPAsyncTask.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(String result) {
                adapter.refreshView();
            }
        });
    }

    /**
     * Like a message and change text colo and view
     * @param context
     * @param message
     */
    public static void likeMessageProfil(final MessageProfilListAdapter adapter,final Context context,final Message message)
    {
        message.setMessage_nb_like(message.getMessage_nb_like()+1);
        HTTPAsyncTask taskPseudo= new HTTPAsyncTask(context);
        taskPseudo.execute(null,Variables.PUTMESSAGE+message.getMessage_id(),"PUT",message.objectToNameValuePair());
        Log.w("URL",Variables.PUTMESSAGE+message.getMessage_id());
        taskPseudo.setMyTaskCompleteListener(new HTTPAsyncTask.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(String result) {
                Log.w("Result", result);
                adapter.refreshView();
            }
        });
    }

    /**
     ** UnLike a message and change text colo and view
     * @param context
     * @param message
     */
    public static void unlikeMessageProfil(final MessageProfilListAdapter adapter,final Context context,final Message message){
        message.setMessage_nb_unlike(message.getMessage_nb_unlike() + 1);
        HTTPAsyncTask taskPseudo = new HTTPAsyncTask(context);
        taskPseudo.execute(null, Variables.PUTMESSAGE + message.getMessage_id(), "PUT", message.objectToNameValuePair());
        taskPseudo.setMyTaskCompleteListener(new HTTPAsyncTask.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(String result) {
                adapter.refreshView();
            }
        });
    }


    /**
     * Like a message and change text colo and view
     * @param context
     * @param message
     */
    public static void likeMessageWithTextView(final TextView textView,final Context context,final Message message)
    {
        message.setMessage_nb_like(message.getMessage_nb_like()+1);
        HTTPAsyncTask taskPseudo= new HTTPAsyncTask(context);
        taskPseudo.execute(null,Variables.PUTMESSAGE+message.getMessage_id(),"PUT",message.objectToNameValuePair());
        Log.w("URL",Variables.PUTMESSAGE+message.getMessage_id());
        taskPseudo.setMyTaskCompleteListener(new HTTPAsyncTask.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(String result) {
                Log.w("Result", result);
                textView.setText(message.getMessage_nb_like()+" "+context.getString(R.string.jaime));
            }
        });
    }

    /**
     ** UnLike a message and change text colo and view
     * @param context
     * @param message
     */
    public static void unlikeMessageWithTextView(final TextView textView,final Context context,final Message message)
    {
        message.setMessage_nb_unlike(message.getMessage_nb_unlike()+1);
        HTTPAsyncTask taskPseudo= new HTTPAsyncTask(context);
        taskPseudo.execute(null,Variables.PUTMESSAGE+message.getMessage_id(),"PUT",message.objectToNameValuePair());
        Log.w("URL",Variables.PUTMESSAGE+message.getMessage_id());
        taskPseudo.setMyTaskCompleteListener(new HTTPAsyncTask.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(String result) {
                Log.w("Result", result);
                textView.setText(message.getMessage_nb_unlike()+" "+context.getString(R.string.jaimepas));
            }
        });
    }


}
