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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kpt.R;


public class Settings extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    Spinner spinner;
    RadioButton lira, dolar;
    Button setRefresh;
    EditText setRefreshText;
    String text;


    TextView language,kur,RefreshTimeText;
    String languageSettings;
    Boolean aBoolean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        spinner = findViewById(R.id.spinner);
        lira = findViewById(R.id.lira);
        dolar = findViewById(R.id.dolar);
        setRefresh = findViewById(R.id.setRefreshTime);
        setRefreshText = findViewById(R.id.setRefreshTimeText);
        setRefreshText.setText("20");

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#03DAC6"));
        actionBar.setBackgroundDrawable(colorDrawable);
        setRefresh.setBackgroundColor(Color.parseColor("#03DAC6"));

        sharedPreferences = this.getSharedPreferences("com.example.kpt.view", Context.MODE_PRIVATE);
        languageSettings = sharedPreferences.getString("LanguageValue","English");

        language = findViewById(R.id.language);
        kur = findViewById(R.id.kur);
        RefreshTimeText = findViewById(R.id.RefreshTimeText);


        if(languageSettings.equals("Turkish")){
            language.setText("Dilinizi Seçin");
            kur.setText("Lütfen Kurunuzu Seçin");
            RefreshTimeText.setText("Veri Yenileme Zamanı");
            setRefresh.setText("Ayarları Uygula");
            lira.setText("Türk Lirası");
            dolar.setText("Amerikan Doları");
            setRefreshText.setHint("Yenileme zamanı girin");
        }else{
            language.setText("Please Select Your Language");
            kur.setText("Please Select Your Currency");
            RefreshTimeText.setText("Refresh Time");
            setRefresh.setText("Apply Settings");
            lira.setText("Turkish Lira");
            dolar.setText("American dollar");
            setRefreshText.setHint("Enter Refresh Time");

        }

        dolar.setChecked(true);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.language, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                text = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        lira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lira.setChecked(true);
                dolar.setChecked(false);
                sharedPreferences.edit().putString("Kur","TL").apply();
                // here we should send the lira paramenter
            }
        });
        dolar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lira.setChecked(false);
                dolar.setChecked(true);
                sharedPreferences.edit().putString("Kur","USD").apply();
                //here we should send the dolar parameter
            }
        });
        setRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // here we should send the the refresh time

                if(setRefreshText.getText().toString().equals("")){
                    setRefreshText.setText("20");
                }

                if(Integer.parseInt(setRefreshText.getText().toString()) < 20 ){
                    if(text.equals("English")){
                        Toast.makeText(Settings.this, "Value can not be lower than 20, value set 20.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Settings.this, "Değer 20'den küçük olamaz, değer 20'ye ayarlandı.", Toast.LENGTH_SHORT).show();
                    }
                    setRefreshText.setText("20");
                }

                Intent main = new Intent(getApplicationContext(),MainActivity.class);
                Intent login = new Intent(getApplicationContext(),LoginPage.class);
                aBoolean = sharedPreferences.getBoolean("Value",false);
                sharedPreferences.edit().putString("LanguageValue",text).apply();
                sharedPreferences.edit().putInt("RefreshValue",Integer.parseInt(setRefreshText.getText().toString())).apply();

                if(aBoolean == true){
                    text = " ";
                    startActivity(login);
                    aBoolean = false;
                }else {
                    text = " ";
                    startActivity(main);
                }
                //return text;
            }
        });
    }

}