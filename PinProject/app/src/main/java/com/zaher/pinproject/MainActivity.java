package com.zaher.pinproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zaher.pinproject.data.remote.User.LoginObject;
import com.zaher.pinproject.data.remote.User.UserResponse;
import com.zaher.pinproject.data.repository.UserRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Mapeamento de variaveis e componentes
        TextView textEmail, textPassword, textForgotPass;

        textEmail = findViewById(R.id.textEmail);
        textPassword = findViewById(R.id.textPassword);
        textForgotPass = findViewById(R.id.textForgotPass);

        Button buttonLogin = findViewById(R.id.btnLogin);

        //Metodo responsavel para ir para executar login
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = textEmail.getText().toString().trim();
                password = textPassword.getText().toString().trim();

                if(email.isEmpty() | password.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            R.string.fill_in_all_fields,
                            Toast.LENGTH_LONG).show();
                } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(getApplicationContext(),
                            R.string.invalid_email,
                            Toast.LENGTH_LONG).show();
                } else {
                    getUser(email, password);
                }
            }
        });

        textForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });
    }

    public void getUser(String email, String password){
        Call<UserResponse> registerResponseCall = UserRepository.getUserService().getUser(new LoginObject(email, password));

        registerResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.isSuccessful()){
                    Intent intent = new Intent(MainActivity.this, Menu.class);

                    String id = response.body().getId_user().toString();
                    String mail = response.body().getEmail();

                    intent.putExtra("UserId", id);
                    intent.putExtra("mailUser", mail);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),
                            R.string.userNotFound,
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),
                        R.string.loginFailed,
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void dialog(){
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.updatePass)
                .setPositiveButton(R.string.setNewPass, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, TrocaDeSenhaActivity.class);
                        intent.putExtra("Email", email);
                        startActivity(intent);
                    }
                })

                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}