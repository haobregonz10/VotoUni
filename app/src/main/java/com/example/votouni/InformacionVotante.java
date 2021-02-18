package com.example.votouni;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InformacionVotante extends AppCompatActivity {
    TextView TVApell;
    TextView TVNomb;
    TextView TVFac;
    TextView TVCod;
    Button BTNSgte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_votante);
        String valorNombre = getIntent().getExtras().getString("INombre");
        String valorApellidos = getIntent().getExtras().getString("IApellidos");
        String valorCodigo = getIntent().getExtras().getString("ICodigo");
        String valorFacultad = getIntent().getExtras().getString("IFacultad");
        TVApell= findViewById(R.id.TVApell);
        TVNomb= findViewById(R.id.TVNom);
        TVFac= findViewById(R.id.TVFac);
        TVCod= findViewById(R.id.TVCod);
        TVApell.setText(valorApellidos);
        TVNomb.setText(valorNombre);
        TVFac.setText(valorFacultad);
        TVCod.setText(valorCodigo);
        BTNSgte= findViewById(R.id.btn_sgte);
        BTNSgte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InformacionVotante.this, TipoVotacion.class);
                startActivity(i);
            }
        });



    }
}