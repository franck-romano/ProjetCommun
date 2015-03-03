package com.example.nicolas.projetcommun;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class connexion extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        TextView inscription = (TextView)findViewById(R.id.inscription);
        TextView oubli = (TextView)findViewById(R.id.oubli);

        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(connexion.this, Inscription.class);
                startActivity(intent);
            }
        });
    }
}
