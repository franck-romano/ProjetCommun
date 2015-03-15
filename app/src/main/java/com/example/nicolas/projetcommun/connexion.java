package com.example.nicolas.projetcommun;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class connexion extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        TextView inscription = (TextView)findViewById(R.id.inscription);
        TextView oubli = (TextView)findViewById(R.id.oubli);
        final Button connexion = (Button)findViewById(R.id.connexion);

            connexion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView id = (TextView)findViewById(R.id.identifiant);
                    TextView mdp = (TextView)findViewById(R.id.mdp);
                    Intent intent = new Intent(connexion.this,profil.class);
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
                Intent intent = new Intent(connexion.this, Inscription.class);
                startActivity(intent);
            }
        });
    }
}
