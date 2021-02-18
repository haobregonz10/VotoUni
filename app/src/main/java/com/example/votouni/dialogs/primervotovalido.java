package com.example.votouni.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.votouni.R;
import com.example.votouni.Votacion;
import com.example.votouni.Votacion2;

public class primervotovalido {
    public primervotovalido(final Context contexto){
        final Dialog dialogo= new Dialog(contexto);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialog_primervotovalido);
        Button okey= (Button) dialogo.findViewById(R.id.ImgBtnOkT1);

        okey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
                Intent i = new Intent(contexto, Votacion2.class);
                contexto.startActivity(i);
            }
        });
        dialogo.show();
    }
}
