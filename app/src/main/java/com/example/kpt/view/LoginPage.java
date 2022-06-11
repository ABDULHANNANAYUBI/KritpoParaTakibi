package com.example.kpt.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.kpt.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LoginPage extends AppCompatActivity {

    EditText editTextEMail;
    EditText editTextPassword;
    Button buttonLogin;
    Button buttonSignUp;
    ArrayList<Customer> list = new ArrayList<Customer>();
    DbHelper Db;
    boolean isEntered = false;
    FloatingActionButton floatingActionButton;

    SharedPreferences sharedPreferences;
    String language;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        sharedPreferences = this.getSharedPreferences("com.example.kpt.view",Context.MODE_PRIVATE);

        editTextEMail = findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#03DAC6"));
        actionBar.setBackgroundDrawable(colorDrawable);
        buttonLogin.setBackgroundColor(Color.parseColor("#03DAC6"));
        buttonSignUp.setBackgroundColor(Color.parseColor("#03DAC6"));

        Db = new DbHelper(this);

        language = sharedPreferences.getString("LanguageValue","English");
        id = sharedPreferences.getInt("UserPageValue",-1);

        if(id != -1){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }

        if(language.equals("English")){
            editTextEMail.setHint("Please Enter Your Email");
            editTextPassword.setHint("Please Enter Your Password");
            buttonLogin.setText("Log in");
            buttonSignUp.setText("Sign up");
        }else{
            editTextEMail.setHint("Lütfen E-postanızı girin");
            editTextPassword.setHint("Lütfen Şifrenizi Girin");
            buttonLogin.setText("Giriş");
            buttonSignUp.setText("Kayıt");
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Settings.class);
                sharedPreferences.edit().putBoolean("Value",true).apply();
                startActivity(intent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextEMail.getText().toString().matches("") || editTextPassword.getText().toString().matches("")){
                    Toast.makeText(LoginPage.this, "Cannot be null or empty", Toast.LENGTH_SHORT).show();
                    isEntered = true;
                } else{
                    list = (ArrayList<Customer>) Db.getAllContacts();
                    for(int i = 0; i < list.size(); i++){
                        if(list.get(i).getEmail().toLowerCase().equals(editTextEMail.getText().toString().toLowerCase()) && list.get(i).getPassword().toLowerCase().equals(editTextPassword.getText().toString().toLowerCase())){
                            Toast.makeText(LoginPage.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            String temp =list.get(i).getId().toString();
                            intent.putExtra("user", temp);
                            sharedPreferences.edit().putInt("UserPageValue",i).apply();
                            setResult(443, intent);
                            startActivity(intent);
                            isEntered = true;
                            editTextEMail.setText("");
                            editTextPassword.setText("");
                            break;
                        }
                        if(i == list.size()-1){
                            Toast.makeText(LoginPage.this, "Please check your informations", Toast.LENGTH_SHORT).show();
                            isEntered = true;
                        }
                    }
                }

            }
        });
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpPage.class);
                startActivity(intent);
            }
        });

    }
}