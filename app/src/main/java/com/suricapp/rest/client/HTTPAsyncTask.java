package com.suricapp.rest.client;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Franck on 17/03/15.
 */
public class HTTPAsyncTask extends AsyncTask<Object,Void,String> {
    /**
     *
     */
    RestClient restClient = new RestClient();
    public HTTPAsyncTask(Context context){}

    public interface OnTaskComplete {
        public void setMyTaskComplete(String message);
    }
    private OnTaskComplete onTaskComplete;

    public void setMyTaskCompleteListener(OnTaskComplete onTaskComplete) {
        this.onTaskComplete = onTaskComplete;
    }

    /**
     * Run the specified query according to its name in
     * @param params a set of Object
     *  params[0] = an array composed of {login /password}
        params[1] = URL to reach
        params[2]= method type {GET,PUT,POST,DELETE}
        params[3] = parameters used only in PUT,POST
     * @return the result of the query into a String object
     */
    @Override
    protected String doInBackground(Object... params) {
        String []credentials = (String[])params[0];
        String URL = (String)params[1];
        String methodName = (String)params[2];
        Object parameters =params[3];
        return switchMethodName(methodName,URL,parameters);
    }

    /**
     * Receive the result of the doInBackground() method and handle the callback
     * to the activity
     * @param result the result of the doInBackGround() method
     */
    @Override
    protected void onPostExecute(String result)
    {
        try {
            onTaskComplete.setMyTaskComplete(result);
        } catch (Exception e) {
        }
    }

    /**
     *
     * @param methodName Name of the method to use
     * @param URL Url to reach
     * @param param Parameters to add in the corresponding method (POST,PUT)
     * @return The result of the executed method into a single String object
     */
    protected String switchMethodName (String methodName,String URL,Object param){
        switch(methodName) {
            case "GET" :
                return restClient.GET(URL);
            case "PUT" :
                return restClient.PUT(URL, param);
            case "POST" :
                return restClient.POST(URL, param);
            case "DELETE" :
                return restClient.DELETE(URL);
            default:
                return "No such method";
        }

    }
}
