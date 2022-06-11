package com.example.kpt.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kpt.R;

import java.util.ArrayList;

public class UserPage extends AppCompatActivity {

    TextView textViewUserPageName;
    TextView textViewUserPageSurname;
    TextView textViewUserPageEmail;
    Button buttonLogOut;
    Button buttonBack;


    String language;
    int id;
    SharedPreferences sharedPreferences;

    ArrayList<Customer> list = new ArrayList<Customer>();
    DbHelper Db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        textViewUserPageName = findViewById(R.id.textViewUserPageName);
        textViewUserPageSurname = findViewById(R.id.textViewUserPageSurname);
        textViewUserPageEmail = findViewById(R.id.textViewUserPageEmail);
        buttonLogOut = findViewById(R.id.buttonLogOut);
        buttonBack = findViewById(R.id.buttonBack);

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#03DAC6"));
        actionBar.setBackgroundDrawable(colorDrawable);
        buttonLogOut.setBackgroundColor(Color.parseColor("#03DAC6"));
        buttonBack.setBackgroundColor(Color.parseColor("#03DAC6"));

        sharedPreferences = this.getSharedPreferences("com.example.kpt.view", Context.MODE_PRIVATE);
        language = sharedPreferences.getString("LanguageValue","English");
        id = sharedPreferences.getInt("UserPageValue",-1);

        Db = new DbHelper(this);
        list = (ArrayList<Customer>) Db.getAllContacts();
       // int value = getIntent().getExtras().getInt("UserPageValue");
        int value = id;

        if(language.equals("English")){
            textViewUserPageName.setText("Name: " + list.get(value).getName().toString());
            textViewUserPageSurname.setText("Surname: " + list.get(value).getSurname().toString());
            textViewUserPageEmail.setText("Email: " + list.get(value).getEmail().toLowerCase().toString());
            buttonLogOut.setText("Log out");
            buttonBack.setText("Back");
        }else{
            textViewUserPageName.setText("Ad: " + list.get(value).getName().toString());
            textViewUserPageSurname.setText("Soyad: " + list.get(value).getSurname().toString());
            textViewUserPageEmail.setText("Email: " + list.get(value).getEmail().toLowerCase().toString());
            buttonLogOut.setText("Çıkış");
            buttonBack.setText("Geri dön");
        }


        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().putInt("UserPageValue",-1).apply();
                sharedPreferences.edit().putString("LanguageValue","English").apply();
                sharedPreferences.edit().putString("Kur","USD").apply();
                Intent intent = new Intent(getApplicationContext(),LoginPage.class);
                startActivity(intent);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

    }
}