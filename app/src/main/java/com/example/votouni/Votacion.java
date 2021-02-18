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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Votacion extends AppCompatActivity {
    CheckBox CBT1L1;
    CheckBox CBT1L2;
    Button BtnVotarT1;
    FirebaseFirestore mFirestore;
    private ProgressBar PBT1;
    Context contexto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votacion);
        CBT1L1= findViewById(R.id.CBT1L1);
        CBT1L2= findViewById(R.id.CBT1L2);
        BtnVotarT1= findViewById(R.id.btn_T1);
        mFirestore= FirebaseFirestore.getInstance();
        PBT1= findViewById(R.id.progressBarT1);
        PBT1.setVisibility(View.INVISIBLE);
        contexto=this;
        BtnVotarT1.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(Votacion.this,"Elija solo a una lista!",Toast.LENGTH_SHORT).show();
                    PBT1.setVisibility(View.INVISIBLE);
                }

            }
        });
    }


    private void InsertarVotos() throws BadPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        String voto;
        if(CBT1L1.isChecked()){
            voto= "T-S1-1";
            //voto= "T-S2-1";
        }else{
            if(CBT1L2.isChecked()){
                voto= "T-S1-2";
                //voto= "T-S2-2";
                //voto= "T-S3-2";
            }else{
                voto="blanco1";
            }
        }
        voto = Encriptar(voto);
        Map<String,Object> map = new HashMap<>();
        map.put("TIPO","UNI");
        map.put("VotoEncriptado",voto);
        //mFirestore.collection("Votos").document().set(map);

        mFirestore.collection("Votos").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(Votacion.this,"Su voto se registró correctamente!",Toast.LENGTH_SHORT).show();
                PBT1.setVisibility(View.INVISIBLE);
                new primervotovalido(contexto);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Votacion.this,"No se pudo registrar su voto!",Toast.LENGTH_SHORT).show();
                PBT1.setVisibility(View.INVISIBLE);
            }
        });

    }

    private boolean ValidarCheck(){
        if(CBT1L1.isChecked() && CBT1L2.isChecked()){
            return false;
        }else{
            return true;
        }
    }

    private String Encriptar(String textoaCifrar) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, NoSuchProviderException, UnsupportedEncodingException {
        //Definimos un texto a cifrar
        String str = "Este es el texto a cifrar";

        System.out.println("\nTexto a cifrar:");
        System.out.println(textoaCifrar);

        //Instanciamos la clase
        RSA rsa = new RSA();

        //Generamos un par de claves
        //Admite claves de 512, 1024, 2048 y 4096 bits
        rsa.genKeyPair(512);
        rsa.setPublicKeyString("63uxcxggq6kqjjsgwagub2k877w562v0qnqgkzrl2yo92a9g9cv4pbxhdz3s3fghz8mdtiidbbok38p322z39cd36857ivpjujnhb2pp5z3vg70njky9nte565wv6kkqp482dlxaor9l2219fl");
        //rsa.setPrivateKeyString("3lsuc5xvukco4aqywk6eify6yeozykr4tlhyd0r3snlo6qw624fret5hlgua67i812qxq375iofefqyzh92vxir4xytnmeqfvvxhe8yojysqpvnblb0b2lbtvx79xab79h2jii82z2otodr35a405w4ooen8p4cwg5hsjfcfzpvdsxsxgymwa6ftc36tsz1mnb8ehcnmokv7elkceh8kd5ssreuzx00wsrdzztrlvzy3smtvss2a8ztg86ma5mnvcxksl00vkwqw71tljf49ckil2fw4kw0ysxcahdch0vcs710fy7nlyogz952kvy5skwadrcqni5ec33jjgnqztxcaha7wwywrc7my7q1ugx3qf3f8sca1pzuwx8zh138iv762649sotn8scyyh2yytvdtic3w7188y9mk2htr70oq3tkazqcm508sc1lvwhu3ft2s3d94zjlwbxtr47fdpfp8sz3cge6ya5d62dr64bzt84zn7i9cffzdyjs9weufhed2qb17nzp803uitukis");


        String file_private = "/tmp/rsa.pri";
        String file_public = "/tmp/rsa.pub";

        //Las guardamos asi podemos usarlas despues
        //a lo largo del tiempo
        String PrivK= rsa.getPrivateKeyString();//saveToDiskPrivateKey("/tmp/rsa.pri");
        String PublicK= rsa.getPublicKeyString();//rsa.saveToDiskPublicKey("/tmp/rsa.pub");
        System.out.println("PRIVATE K: "+PrivK);
        System.out.println("PUBLIC K: "+PublicK);
        //Ciframos y e imprimimos, el texto cifrado
        //es devuelto en la variable secure
        String secure = rsa.Encrypt(textoaCifrar);

        System.out.println("\nCifrado:");
        System.out.println(secure);



        //A modo de ejemplo creamos otra clase rsa
        RSA rsa2 = new RSA();

        //A diferencia de la anterior aca no creamos
        //un nuevo par de claves, sino que cargamos
        //el juego de claves que habiamos guadado
        rsa2.setPrivateKeyString(PrivK);//openFromDiskPrivateKey("/tmp/rsa.pri");
        rsa2.setPublicKeyString(PublicK);//openFromDiskPublicKey("/tmp/rsa.pub");

        //Le pasamos el texto cifrado (secure) y nos
        //es devuelto el texto ya descifrado (unsecure)
        String unsecure = rsa2.Decrypt(secure);

        //Imprimimos
        System.out.println("\nDescifrado:");
        System.out.println(unsecure);
        return secure;
    }

}