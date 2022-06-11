package com.example.kpt.view;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kpt.R;
import com.example.kpt.adapter.RecyclerViewAdapter;
import com.example.kpt.model.KriptoModel;
import com.example.kpt.service.KriptoAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ArrayList<KriptoModel> kriptoModels;
    private String Base_URL = "https://api.nomics.com/v1/";
    Retrofit retrofit;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    Customer mainCustomer = new Customer();
    DbHelper db ;
    CompositeDisposable compositeDisposable;
    public int id;
    EditText editTextNumberFiyat;

    TextView textViewKurMain;
    String language;
    SharedPreferences sharedPreferences;
    int refreshTime;

    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#03DAC6"));
        actionBar.setBackgroundDrawable(colorDrawable);

        sharedPreferences = this.getSharedPreferences("com.example.kpt.view", Context.MODE_PRIVATE);
        refreshTime = sharedPreferences.getInt("RefreshValue",20);
        language = sharedPreferences.getString("LanguageValue","English");

        // three dot menu1
        String kur= sharedPreferences.getString("Kur","USD");
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        recyclerView = findViewById(R.id.recyclerView);



        // Retrofit ve JSON
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Base_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        reloadDatas();
    }

    public void reloadDatas() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                loadData();
                handler.postDelayed(runnable,refreshTime*1000);
            }
        };

        handler.post(runnable);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if(language.equals("English")){
            menu.add("User Page");
            menu.add("Settings");
            menu.add("About Us");
        }else{
            menu.add("Kullanıcı Sayfası");
            menu.add("Ayarlar");
            menu.add("Hakkımızda");
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getTitle().equals("User Page") || item.getTitle().equals("Kullanıcı Sayfası") ){
            handler.removeCallbacks(runnable);
            Intent intent = new Intent(getApplicationContext(), UserPage.class);
            startActivity(intent);
        } else if(item.getTitle().equals("Settings") || item.getTitle().equals("Ayarlar")){
            handler.removeCallbacks(runnable);
            Intent intent = new Intent(getApplicationContext(), Settings.class);
            startActivity(intent);
        }else if(item.getTitle().equals("About Us") || item.getTitle().equals("Hakkımızda")){
            handler.removeCallbacks(runnable);
            Intent intent = new Intent(getApplicationContext(), AboutUs.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 443){
            String gelen_mesaj = data.getStringExtra("user");
            db = new DbHelper(this);
            mainCustomer = db.getContactById(gelen_mesaj);
            id = Integer.parseInt(gelen_mesaj);
            Toast.makeText(this, String.valueOf(id), Toast.LENGTH_SHORT).show();
            Customer contact = new Customer(mainCustomer.getId(), mainCustomer.getName(), mainCustomer.getSurname(),mainCustomer.getEmail(),mainCustomer.getPassword(),1, Integer.parseInt(editTextNumberFiyat.getText().toString()));
            //db.updateContact(contact);
        }
    }


    private void loadData(){
        final KriptoAPI kriptoAPI = retrofit.create(KriptoAPI.class);

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(kriptoAPI.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse));
    }

    private void handleResponse(List<KriptoModel> kriptoModelList){
        kriptoModels = new ArrayList<>(kriptoModelList);

        //RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerViewAdapter = new RecyclerViewAdapter(kriptoModels);

        recyclerViewAdapter.setKur(sharedPreferences.getString("Kur","USD"));
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        compositeDisposable.clear();
    }
}


// apikey link
// https://api.nomics.com/v1/currencies/ticker?key=3345c8d650f83149e36aff22d2a737107d5df647&ids=BTC,ETH,XRP&interval=1d,30d&convert=EUR&per-page=100&attributes=id,name,logo_url,price&page=1%22