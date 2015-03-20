package com.suricapp.views;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.suricapp.models.Category;
import com.suricapp.rest.client.HTTPAsyncTask;
import com.suricapp.rest.client.RestClient;
import com.suricapp.tools.CheckConnection;
import com.suricapp.tools.DialogCreation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ConnexionActivity extends ActionBarActivity implements View.OnClickListener {

    private  TextView id ;
    private Button connexion;
    private RestClient restClient;
    private  HTTPAsyncTask task ;

    private TextView mSubscribeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        id= (TextView)findViewById(R.id.activity_connexion_identifiant);

        mSubscribeTextView = (TextView)findViewById(R.id.activity_connexion_inscription);
        mSubscribeTextView.setOnClickListener(this);
        TextView oubli = (TextView)findViewById(R.id.activity_connexion_oubli);
        connexion= (Button)findViewById(R.id.activity_connexion_connexion);


        // check categories if newer add it.
        checkCategories();
        //task= new HTTPAsyncTask(this);
       /* ArrayList<NameValuePair> listNameValuePair = new ArrayList<>();
        listNameValuePair.add(new BasicNameValuePair("nom","Mimy"));
        listNameValuePair.add(new BasicNameValuePair("prenom","Zeubi"));*/
        //task.execute(null,"http://suricapp.esy.es/ws.php/d_user/user_mail/mailsdugars","GET",null);
        /**
        task.setMyTaskCompleteListener(new HTTPAsyncTask.OnTaskComplete() {
            @Override
            public void setMyTaskComplete(String message) {
                id.setText(message);
            }
        });
         */

            connexion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView id = (TextView)findViewById(R.id.activity_connexion_identifiant);
                    TextView mdp = (TextView)findViewById(R.id.activity_connexion_mot_de_passe);
                    Intent intent = new Intent(ConnexionActivity.this,TimelineActivity.class);
                    String ident = id.getText().toString();
                    String mot = mdp.getText().toString();

                    if(ident.trim().length()==0)
                        Toast.makeText(getApplicationContext(),"Veuillez remplir les champs identifiant et mot de passe svp", Toast.LENGTH_SHORT).show();
                    else {
                        if ((ident.equals("toto")&&(mot.equals("toto"))))
                                startActivity(intent);
                        else
                            Toast.makeText(getApplicationContext(), "Identifiant ou MDP Incorrect", Toast.LENGTH_SHORT).show();
                    }
                }
            });
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
        }
    }

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
                    Log.w("LG","iozubvizbvizbzivubzviuzbvi");
                    long tmp = Category.count(Category.class,null,null);
                    Log.w("LG",""+tmp);
                } catch (JSONException e) {
                    Log.w("EXCEPTION",e.toString());
                }
            }
        });

    }
}
