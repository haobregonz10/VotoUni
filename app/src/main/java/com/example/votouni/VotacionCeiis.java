package com.example.votouni;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.votouni.dialogs.primervotovalido;
import com.example.votouni.dialogs.tercervotovalido;
import com.example.votouni.interfaces.AdditivelyHomomorphicCryptosystem;
import com.example.votouni.paillier.PaillierCryptosystem;
import com.example.votouni.paillier.PaillierPrivateKey;
import com.example.votouni.paillier.PaillierPublicKey;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
public class VotacionCeiis extends AppCompatActivity {
    CheckBox CBLista1Ceiis;
    CheckBox CBLista2Ceiis;
    Button BtnVotarCeiis1;
    FirebaseFirestore mFirestore;
    private ProgressBar PBT1;
    Context contexto;
    public  static AdditivelyHomomorphicCryptosystem paillier2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votacion_ceiis);
        CBLista1Ceiis= findViewById(R.id.CBL1Ceiis);
        CBLista2Ceiis= findViewById(R.id.CBL2Ceiis);
        BtnVotarCeiis1= findViewById(R.id.btn_Ceiis1);
        mFirestore= FirebaseFirestore.getInstance();
        PBT1= findViewById(R.id.progressBarT1);
        PBT1.setVisibility(View.INVISIBLE);
        BigInteger n = new BigInteger("19165724925437947720793318169031746538947523761277");
        BigInteger lambda = new BigInteger("4791431231359486930198327351997281007546378935864");
        BigInteger mu = new BigInteger("16389292834899549512112951548712844318589924393296");

        paillier2= new PaillierCryptosystem(n, lambda, mu,164);
        contexto=this;
        BtnVotarCeiis1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PBT1.setVisibility(View.VISIBLE);

                if(ValidarCheck()){
                    try {
                        InsertarVotos();
                    } catch (BadPaddingException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (IllegalBlockSizeException e) {
                        e.printStackTrace();
                    } catch (NoSuchProviderException e) {
                        e.printStackTrace();
                    } catch (NoSuchPaddingException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    } catch (InvalidKeySpecException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(VotacionCeiis.this,"Elija solo a una lista!",Toast.LENGTH_SHORT).show();
                    PBT1.setVisibility(View.INVISIBLE);
                }

            }
        });
    }


    private void InsertarVotos() throws BadPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        String votoL1, votoL2, VotoBlanco;
        if(CBLista1Ceiis.isChecked()){
            votoL1= "1";
            votoL2= "0";
            VotoBlanco= "0";
        }else{
            if(CBLista2Ceiis.isChecked()){
                votoL1= "0";
                votoL2= "1";
                VotoBlanco= "0";
            }else{
                votoL1= "0";
                votoL2= "0";
                VotoBlanco= "1";
            }
        }
        votoL1 = PallierEncriptar(votoL1);
        votoL2 = PallierEncriptar(votoL2);
        VotoBlanco = PallierEncriptar(VotoBlanco);
        Map<String,Object> map = new HashMap<>();
        map.put("VotoL1",votoL1);
        map.put("VotoL2",votoL2);
        map.put("VotoBlanco",VotoBlanco);
        //mFirestore.collection("Votos").document().set(map);

        mFirestore.collection("VotosCeiis").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(VotacionCeiis.this,"Su voto se registró correctamente!",Toast.LENGTH_SHORT).show();
                PBT1.setVisibility(View.INVISIBLE);
                new tercervotovalido(contexto);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(VotacionCeiis.this,"No se pudo registrar su voto!",Toast.LENGTH_SHORT).show();
                PBT1.setVisibility(View.INVISIBLE);
            }
        });

    }

    private boolean ValidarCheck(){
        if(CBLista1Ceiis.isChecked() && CBLista2Ceiis.isChecked()){
            return false;
        }else{
            return true;
        }
    }


    public static String PallierEncriptar(String textoaCifrar)
    {

       BigInteger votoaCifrar= new BigInteger(textoaCifrar);
        BigInteger VotoCifradoPaillier = paillier2.encrypt(votoaCifrar);
        String VotoCifradoString= ""+VotoCifradoPaillier;
        return VotoCifradoString;

    }

}