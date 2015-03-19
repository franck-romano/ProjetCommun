package com.suricapp.views;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.suricapp.rest.client.HTTPAsyncTask;
import com.suricapp.rest.client.RestClient;


public class ConnexionActivity extends ActionBarActivity implements View.OnClickListener {

    private  TextView id ;
    private Button connexion;
    private RestClient restClient;
    private  HTTPAsyncTask task ;

    private TextView 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        id= (TextView)findViewById(R.id.identifiant);
        TextView inscription = (TextView)findViewById(R.id.inscription);
        TextView oubli = (TextView)findViewById(R.id.oubli);
        connexion= (Button)findViewById(R.id.connexion);

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
                    TextView id = (TextView)findViewById(R.id.identifiant);
                    TextView mdp = (TextView)findViewById(R.id.mdp);
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

        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConnexionActivity.this, InscriptionActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
