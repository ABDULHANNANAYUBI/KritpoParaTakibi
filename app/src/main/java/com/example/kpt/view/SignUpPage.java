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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kpt.R;

import java.util.ArrayList;
import java.util.Random;

public class SignUpPage extends AppCompatActivity {

    EditText editTextName;
    EditText editTextSurname;
    EditText editTextEmailSignUp;
    EditText editTextPasswordSignUp;
    TextView textViewInformation;

    Button buttonNewUser, back;
    ImageView img;
    Random random;
    DbHelper Db;
    public static boolean isActive = false;
    public static int ActiveAfter = 5;
    ArrayList<Customer> list = new ArrayList<Customer>();
    public boolean isEntered = false;
    String language;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        editTextName = findViewById(R.id.editTextTextPersonName);
        editTextSurname = findViewById(R.id.editTextTextPersonSurname);
        editTextEmailSignUp = findViewById(R.id.editTextTextEmailSignUp);
        editTextPasswordSignUp = findViewById(R.id.editTextTextPasswordSignUp);
        textViewInformation = findViewById(R.id.textViewInformation);
        buttonNewUser = findViewById(R.id.buttonNewUser);
        back = findViewById(R.id.back);


        buttonNewUser.setBackgroundColor(Color.parseColor("#03DAC6"));
        back.setBackgroundColor(Color.parseColor("#03DAC6"));

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#03DAC6"));
        actionBar.setBackgroundDrawable(colorDrawable);

        random = new Random();
        Db = new DbHelper(this);
        list = (ArrayList<Customer>) Db.getAllContacts();

        sharedPreferences = this.getSharedPreferences("com.example.kpt.view", Context.MODE_PRIVATE);
        language = sharedPreferences.getString("LanguageValue","English");

        if(language.equals("English")){
            textViewInformation.setText("Please fill all fields to register");
            editTextName.setHint("Enter Your Name");
            editTextSurname.setHint("Enter Your Surname");
            editTextEmailSignUp.setHint("Enter Your Email");
            editTextPasswordSignUp.setHint("Enter Your Password");
            buttonNewUser.setText("Sign up");
            back.setText("Back");
        }else{
            textViewInformation.setText("Kayıt olmak için tüm alanları doldurun");
            editTextName.setHint("Adınızı Girin");
            editTextSurname.setHint("Soyadınızı Girim");
            editTextEmailSignUp.setHint("E-mailiniz Girin");
            editTextPasswordSignUp.setHint("Şifrenizi Girin");
            buttonNewUser.setText("Kayıt ol");
            back.setText("Geri dön");
        }

        buttonNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i < list.size(); i++){
                    if(list.get(i).getEmail().toLowerCase().equals(editTextEmailSignUp.getText().toString().toLowerCase())){
                        Toast.makeText(SignUpPage.this, "The user already exist", Toast.LENGTH_SHORT).show();
                        isEntered = true;
                    }
                }
                if(isEntered){
                    Toast.makeText(SignUpPage.this, "Already Registed ...", Toast.LENGTH_SHORT).show();
                    isEntered = false;
                    return;
                }
                else{
                    if(editTextName.getText().toString().matches("") || editTextSurname.getText().toString().matches("") ||
                            editTextEmailSignUp.getText().toString().matches("") || editTextPasswordSignUp.getText().toString().matches("")
                    ){
                        Toast.makeText(SignUpPage.this, "The length text cannot be null or empty", Toast.LENGTH_SHORT).show();
                        isEntered = true;
                    }
                    else{
                        Toast.makeText(SignUpPage.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                        Db.addContact(new Customer(random.toString(), editTextName.getText().toString(), editTextSurname.getText().toString(), editTextEmailSignUp.getText().toString(), editTextPasswordSignUp.getText().toString(), 1, 10));
                    }
                }
                if(!isEntered){
                    Toast.makeText(SignUpPage.this, "The length text cannot be null or empty", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                    startActivity(intent);
                }
                isEntered = false;
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                startActivity(intent);
            }
        });
    }

    public void AddUser(View view) {

    }
}