package com.example.votouni;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.votouni.interfaces.AdditivelyHomomorphicCryptosystem;
import com.example.votouni.interfaces.PrivateKey;
import com.example.votouni.paillier.PaillierCryptosystem;
import com.example.votouni.paillier.PaillierPrivateKey;
import com.example.votouni.paillier.PaillierPublicKey;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class MainActivity extends AppCompatActivity {
    Button BTNVotar;
    EditText ETDNI, ETClave2;
    ProgressBar PBVotar;
    FirebaseFirestore db;
    String Valides;
    public  static AdditivelyHomomorphicCryptosystem paillier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PBVotar = findViewById(R.id.progressBarInicio);
        ETDNI= findViewById(R.id.ETDNI);
        ETClave2= findViewById(R.id.ETClave);
        BTNVotar= findViewById(R.id.btn_iniciar);
        db=FirebaseFirestore.getInstance();
        PBVotar.setVisibility(View.INVISIBLE);
        //paillier = new PaillierCryptosystem(164);
        BigInteger n = new BigInteger("19165724925437947720793318169031746538947523761277");
        BigInteger lambda = new BigInteger("4791431231359486930198327351997281007546378935864");
        BigInteger mu = new BigInteger("16389292834899549512112951548712844318589924393296");

        paillier= new PaillierCryptosystem(n, lambda, mu,164);
        BTNVotar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    System.out.println("TEXTO 1234: "+Desncriptar("1234"));
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (NoSuchProviderException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                PBVotar.setVisibility(View.VISIBLE);
                if(ValidarIngresoDNI()){
                    ValidarDNI();
                }else{
                    Toast.makeText(MainActivity.this,"Ingrese un DNI válido!",Toast.LENGTH_SHORT).show();
                    PBVotar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void ValidarDNI(){
        String DNI= ETDNI.getText().toString().trim();
        String Clave3= ETClave2.getText().toString().trim();
        final String[] ClaveDes = new String[1];
        db.collection("Votantes").document(DNI).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String Apellidos = documentSnapshot.getString("Apellidos");
                    String Codigo = documentSnapshot.getString("Codigo");
                    String Facultad = documentSnapshot.getString("Facultad");
                    String Nombre = documentSnapshot.getString("Nombre");
                    String Clave = documentSnapshot.getString("Clave");
                    System.out.println("DATA::::"+Apellidos+" "+Codigo+" "+Facultad+" "+Nombre+""+Clave);
                    PBVotar.setVisibility(View.INVISIBLE);
                    try {
                         ClaveDes[0] = Desncriptar(Clave);
                    } catch (IllegalBlockSizeException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    } catch (BadPaddingException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (NoSuchPaddingException e) {
                        e.printStackTrace();
                    } catch (InvalidKeySpecException e) {
                        e.printStackTrace();
                    } catch (NoSuchProviderException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    if(Clave3.equals(ClaveDes[0])){

                        Intent intent = new Intent(getApplicationContext(), InformacionVotante.class);
                        intent.putExtra("IApellidos", Apellidos);
                        intent.putExtra("ICodigo", Codigo);
                        intent.putExtra("INombre", Nombre);
                        intent.putExtra("IFacultad", Facultad);
                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this,"Usuario o Contraseña inválidos!",Toast.LENGTH_LONG).show();
                    }


                }else{
                    System.out.println("Usted no esta falcultado para votar!");
                    Toast.makeText(MainActivity.this,"Usted no esta falcultado para votar!",Toast.LENGTH_LONG).show();
                    PBVotar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }



    private boolean ValidarIngresoDNI(){
        String DNI= ETDNI.getText().toString().trim();
        if (DNI.equals("")){
            return false;
        }else{
            if(DNI.length()!=8){
                return false;
            }else{
                return true;
            }
        }
    }

    private String Desncriptar(String textoaDescifrar) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, NoSuchProviderException, UnsupportedEncodingException {



        RSA rsa = new RSA();

        //Admite claves de 512, 1024, 2048 y 4096 bits
        rsa.genKeyPair(512);
        rsa.setPublicKeyString("63uxcxggq6kqjjsgwagub2k877w562v0qnqgkzrl2yo92a9g9cv4pbxhdz3s3fghz8mdtiidbbok38p322z39cd36857ivpjujnhb2pp5z3vg70njky9nte565wv6kkqp482dlxaor9l2219fl");
        rsa.setPrivateKeyString("3lsuc5xvukco4aqywk6eify6yeozykr4tlhyd0r3snlo6qw624fret5hlgua67i812qxq375iofefqyzh92vxir4xytnmeqfvvxhe8yojysqpvnblb0b2lbtvx79xab79h2jii82z2otodr35a405w4ooen8p4cwg5hsjfcfzpvdsxsxgymwa6ftc36tsz1mnb8ehcnmokv7elkceh8kd5ssreuzx00wsrdzztrlvzy3smtvss2a8ztg86ma5mnvcxksl00vkwqw71tljf49ckil2fw4kw0ysxcahdch0vcs710fy7nlyogz952kvy5skwadrcqni5ec33jjgnqztxcaha7wwywrc7my7q1ugx3qf3f8sca1pzuwx8zh138iv762649sotn8scyyh2yytvdtic3w7188y9mk2htr70oq3tkazqcm508sc1lvwhu3ft2s3d94zjlwbxtr47fdpfp8sz3cge6ya5d62dr64bzt84zn7i9cffzdyjs9weufhed2qb17nzp803uitukis");

        String PrivK= rsa.getPrivateKeyString();//saveToDiskPrivateKey("/tmp/rsa.pri");
        String PublicK= rsa.getPublicKeyString();//rsa.saveToDiskPublicKey("/tmp/rsa.pub");
        System.out.println("PRIVATE K: "+PrivK);
        System.out.println("PUBLIC K: "+PublicK);


        System.out.println("\nCifrado:");
        System.out.println(textoaDescifrar);



        String unsecure = rsa.Decrypt(textoaDescifrar);
        //String unsecure = rsa.Encrypt(textoaDescifrar);


        System.out.println("\nDescifrado:");
        System.out.println(unsecure);
        return unsecure;
    }


}