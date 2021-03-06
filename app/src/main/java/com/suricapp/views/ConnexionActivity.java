package com.suricapp.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.suricapp.models.Category;
import com.suricapp.rest.client.HTTPAsyncTask;
import com.suricapp.rest.client.RestClient;
import com.suricapp.tools.CheckConnection;
import com.suricapp.tools.DialogCreation;
import com.suricapp.tools.StringValidator;
import com.suricapp.tools.Variables;
import com.suricapp.tools.ViewModification;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;


public class ConnexionActivity extends ActionBarActivity implements View.OnClickListener {

    // Connexion Info
    private  TextView idTextView ;
    private Button mConnexionButton;
    private RestClient restClient;
    private  HTTPAsyncTask task ;
    private TextView mdpTextView;

    private TextView mSubscribeTextView;
    private View mLoginFormView;
    private View mLoginStatusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        mSubscribeTextView = (TextView)findViewById(R.id.activity_connexion_inscription);
        mSubscribeTextView.setOnClickListener(this);
        idTextView = (TextView)findViewById(R.id.activity_connexion_identifiant);
        mdpTextView = (TextView)findViewById(R.id.activity_connexion_mot_de_passe);
        mConnexionButton= (Button)findViewById(R.id.activity_connexion_connexion);
        mConnexionButton.setOnClickListener(this);
        mLoginFormView = findViewById(R.id.activity_connexion_form);
        mLoginStatusView = findViewById(R.id.activity_connexion_status);

        // check categories if newer add it.
        checkCategories();
        checkSharedPreferences();
    }

    /**
     * Check if someone is already connected
     */
    private void checkSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(Variables.SURICAPPREFERENCES, Context.MODE_PRIVATE);
        if(sharedPreferences.contains("userLog"))
        {
            Intent intent = new Intent(ConnexionActivity.this, TimelineActivity.class);
            startActivity(intent);
            this.finish();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.activity_connexion_inscription :
                //Check connection available
                if(CheckConnection.isNetworkAvailable(this)) {
                    Intent intent = new Intent(ConnexionActivity.this, InscriptionActivity.class);
                    startActivity(intent);
                }else
                {
                    DialogCreation.createDialog(this,getString(R.string.no_network_available),getString(R.string.no_network_available_desc));
                }
                break;
            case R.id.activity_connexion_connexion:
                if(CheckConnection.isNetworkAvailable(this)) {
                    if (idTextView.getText().toString().length() == 0 || mdpTextView.getText().toString().length() == 0) {
                        DialogCreation.createDialog(this, getString(R.string.erreur), getString(R.string.mdp_login_missing));
                    } else {
                        ViewModification.showProgress(true,mLoginStatusView,mLoginFormView,this);
                        identifyUser();
                    }

                }
                else
                    DialogCreation.createDialog(this,getString(R.string.no_network_available),getString(R.string.no_network_available_desc));
                break;
        }
    }

    /**
     * Check user identification
     */
    private void identifyUser() {
        String login = idTextView.getText().toString();
        HTTPAsyncTask taskPseudo= new HTTPAsyncTask(getLocalContext());
        taskPseudo.execute(null,Variables.GETPSEUDOFORUSER+login,"GET",null);
        taskPseudo.setMyTaskCompleteListener(new HTTPAsyncTask.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(String message){
                JSONArray jarray = null;
                try {
                    jarray = new JSONArray(message);
                    if(jarray.length() == 0 )
                    {
                        throw  new JSONException("Array to short");
                    }
                    else
                    {
                        JSONObject tmp = jarray.getJSONObject(0);
                        if(tmp.getString("user_password").equals(StringValidator.SHA1(mdpTextView.getText().toString())))
                        {
                            SharedPreferences preferences = getSharedPreferences(Variables.SURICAPPREFERENCES,Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor  = preferences.edit();
                            editor.putString("userLog", idTextView.getText().toString());
                            editor.putString("userLogId",tmp.getString("user_id"));
                            editor.apply();
                            Intent intent = new Intent(ConnexionActivity.this, CategoriesActivity.class);
                            intent.putExtra("fromConnexion",true);
                            startActivity(intent);
                            ((Activity)getLocalContext()).finish();
                        }
                        else {
                            ViewModification.showProgress(false,mLoginStatusView,mLoginFormView,getLocalContext());
                            DialogCreation.createDialog(getLocalContext(), getString(R.string.erreur), getString(R.string.bad_mdp_login));
                        }
                    }
                } catch (JSONException e) {
                    Log.w("Exception1",e.toString());
                    DialogCreation.createDialog(getLocalContext(),getString(R.string.erreur),getString(R.string.bad_mdp_login));
                    ViewModification.showProgress(false,mLoginStatusView,mLoginFormView,getLocalContext());
                } catch (NoSuchAlgorithmException e) {
                    Log.w("Exception2",e.toString());
                } catch (UnsupportedEncodingException e) {
                    Log.w("Exception3",e.toString());
                }catch (Exception e)
                {
                    Log.w("Exceptioné4",e.toString());
                }
            }
        });
    }

    private Context getLocalContext() {
        return this;
    }
    /**
     * Check if the category are the same in local than in server
     */
    private void checkCategories()
    {
//        http://suricapp.esy.es/ws.php/d_category

        HTTPAsyncTask taskPseudo= new HTTPAsyncTask(getApplicationContext());
        taskPseudo.execute(null,"http://suricapp.esy.es/ws.php/d_category","GET",null);
        taskPseudo.setMyTaskCompleteListener(new HTTPAsyncTask.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(String message) {
                JSONArray jarray = null;
                try {
                    jarray = new JSONArray(message);
                    int tmp =(int) Category.count(Category.class,null,null);

                    if(jarray.length() != tmp )
                    {
                        Category.deleteAll(Category.class);
                        for (int i=0;i<jarray.length();i++)
                        {
                            JSONObject jsonObject = jarray.getJSONObject(i);
                            Category cat = new Category();
                            cat.setCategory_id(Integer.parseInt(jsonObject.getString("category_id")));
                            cat.setCategory_description("category_description_fr_fr");
                            cat.setCategory_label("category_label");
                            cat.save();
                        }
                    }
                } catch (Exception e) {
                    Log.w("EXCEPTION",e.toString());
                }
            }
        });

    }
}
