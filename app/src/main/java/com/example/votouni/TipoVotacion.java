package com.example.votouni;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TipoVotacion extends AppCompatActivity {
    Button BTEleccionTeuni;
    Button BTEleccionCefiis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_votacion);
        BTEleccionCefiis= findViewById(R.id.btn_eleccion_cefiis);
        BTEleccionTeuni= findViewById(R.id.btn_eleccion_Teuni);
        BTEleccionTeuni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TipoVotacion.this, Votacion.class);
                startActivity(i);
            }
        });

        BTEleccionCefiis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TipoVotacion.this, VotacionCeiis.class);
                startActivity(i);
            }
        });
    }

}