package com.victorherrera.myapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends Activity {

    private static final int MY_REQUEST_CODE = 1000; //Cualquier numero

    List<AuthUI.IdpConfig> providers;

    Button btnSalida;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(), //constructor del email
                new AuthUI.IdpConfig.PhoneBuilder().build(), //constructor del telefono
                //new AuthUI.IdpConfig.FacebookBuilder().build(),//constructor del facebook
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        showSignInOptions();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IdpResponse response = IdpResponse.fromResultIntent(data);
        if (requestCode == MY_REQUEST_CODE){


            if (resultCode == RESULT_OK) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                //Cargar el email en un mensaje toast
               //textView.setText(user.getEmail());

               //btnSalida.setEnabled(true);
                Toast.makeText(this,"Cargado usuario: " + user.getEmail(),Toast.LENGTH_LONG).show();

            }
        } else {
            Toast.makeText(this,response.getError().getMessage(),Toast.LENGTH_SHORT).show();
        }


    }

    private void showSignInOptions() {
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
          .setAvailableProviders(providers)
          .setTheme(R.style.Theme_AppCompat)
          .build(), MY_REQUEST_CODE
        );
    }




}