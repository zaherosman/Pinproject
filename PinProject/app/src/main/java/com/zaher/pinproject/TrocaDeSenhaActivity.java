package com.zaher.pinproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zaher.pinproject.data.remote.User.UserRequest;

public class TrocaDeSenhaActivity extends AppCompatActivity {

    TextView textEmail, textPassword, textCPassword, textRetornar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_troca_de_senha);

        initializeComponentes();

        textEmail.setText(getIntent().getStringExtra("Email"));

        Button btnTrocarSenha = findViewById(R.id.btnTrocaSenha);

        btnTrocarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = textEmail.getText().toString().trim();
                String password = textPassword.getText().toString().trim();
                String cPassword = textCPassword.getText().toString().trim();

                if(email.isEmpty() | password.isEmpty() | cPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            R.string.fill_in_all_fields,
                            Toast.LENGTH_LONG).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(getApplicationContext(),
                            R.string.invalid_email,
                            Toast.LENGTH_LONG).show();
                } else if (password.length() < 8){
                    Toast.makeText(getApplicationContext(),
                            R.string.minimunPassSize,
                            Toast.LENGTH_LONG).show();
                } else if(password.equals(cPassword)){
                    saveNewPassword(createdRequest());
                } else {
                    Toast.makeText(getApplicationContext(),
                            R.string.differentPassword,
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        textRetornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrocaDeSenhaActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }


    public void initializeComponentes(){
        textEmail = findViewById(R.id.textEmail);
        textPassword = findViewById(R.id.textPassword);
        textCPassword = findViewById(R.id.textCPassword);
        textRetornar = findViewById(R.id.textRetornar);
    }

    public UserRequest createdRequest(){
        UserRequest request = new UserRequest();
        request.setEmail(textEmail.getText().toString());
        request.setPass(textPassword.getText().toString());
        return request;
    }

    public void saveNewPassword(UserRequest userRequest){

    }
}