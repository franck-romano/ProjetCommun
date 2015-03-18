package com.suricapp.rest.client;

import com.suricapp.rest.client.RestClientInterface;
import com.suricapp.suricapp.utils.StreamUtils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Franck on 16/03/15.
 */
public class RestClient implements RestClientInterface {

    @Override
    public String PUT(String url,Object param) {
        InputStream inputStream =null;
        String ack="PUT Statement has failed";
        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpPut httpPut = new HttpPut(url);
            ArrayList<NameValuePair> listNameValuePair=(ArrayList)param;
            try{
                httpPut.setEntity(new UrlEncodedFormEntity(listNameValuePair));
            }catch(UnsupportedEncodingException ex){
            }
            try {
                HttpResponse response = httpClient.execute(httpPut);
                inputStream = response.getEntity().getContent();
                if(inputStream != null){
                    ack= StreamUtils.convertInputStreamToString(inputStream);
                }
                else {
                    ack= "PUT Statement has failed";
                }
                return ack;
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
        }
        return ack;
    }

    @Override
    public String GET(String url) {
        InputStream inputStream =null;
        String result="";
        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(new HttpGet(url));
            inputStream = httpResponse.getEntity().getContent();
            if(inputStream != null){
                result= StreamUtils.convertInputStreamToString(inputStream);
            }
            else {
                result= "Did not work";
            }
        } catch (Exception e) {
        }
        return result;
    }

    @Override
    public String POST(String url,Object param) {
        InputStream inputStream =null;
        String ack="";
        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            ArrayList<NameValuePair> listNameValuePair=(ArrayList)param;
            try{
                httpPost.setEntity(new UrlEncodedFormEntity(listNameValuePair));
            }catch(UnsupportedEncodingException ex){
            }
            try {
                HttpResponse response = httpClient.execute(httpPost);
                inputStream = response.getEntity().getContent();
                if(inputStream != null){
                    ack= StreamUtils.convertInputStreamToString(inputStream);
                }
                else {
                    ack= "POST Statement has failed";
                }
                return ack;
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
        }
        return ack;
    }

    @Override
    public String DELETE(String url) {
        InputStream inputStream =null;
        String result="";
        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpDelete httpDelete = new HttpDelete(url);
            HttpResponse httpResponse = httpClient.execute(httpDelete);
            inputStream = httpResponse.getEntity().getContent();
            if(inputStream != null){
                result= StreamUtils.convertInputStreamToString(inputStream);
            }
            else {
                result= "Did not work";
            }
        } catch (Exception e) {
        }
        return result;
    }

   /* public void runAsyncThread(String ... params){
        getInternClass().execute(params);
    }

    public HTTPAsyncTask getInternClass(){
        return new HTTPAsyncTask();
    }
*/
    /*private class HTTPAsyncTask extends AsyncTask<Object,Void,String>
    {

        *//*CallBackListener mListener;

        public void setListener(CallBackListener listener){
            mListener = listener;
        }
        *//**//*
        t[0] = pseudo/pwd
        t[1] = URL
        t[2]= Type méthode
        t[3] = les parametres
         *//**//*
        @Override
        protected String doInBackground(Object... params) {
            String credentials = (String)params[0];
            String URL = (String)params[1];
            String methodName = (String)params[2];
            Object parameters =params[3];
            return switchMethodName(methodName,URL,parameters);
        }

        @Override
        protected void onPostExecute(String result)
        {
            try {
                JsonUtils.StringToJSON(result);
            } catch (JSONException e) {
            }
        }

        protected String switchMethodName (String methodName,String URL,Object param){
            switch(methodName) {
                case "GET" :
                    return GET(URL);
                case "PUT" :
                    return PUT(URL,param);
                case "POST" :
                    return POST(URL,param);
                case "DELETE" :
                    return GET(URL);
                default:
                    return "No such method";
            }

        }
    }*/
}
