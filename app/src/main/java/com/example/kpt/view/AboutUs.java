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

public class AboutUs extends AppCompatActivity {

    TextView aboutUs, AboutUsDetail;
    String language;
    SharedPreferences sharedPreferences;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        aboutUs = findViewById(R.id.AboutUs);
        AboutUsDetail = findViewById(R.id.aboutUsDetails);
        button = findViewById(R.id.button);

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#03DAC6"));
        actionBar.setBackgroundDrawable(colorDrawable);
        button.setBackgroundColor(Color.parseColor("#03DAC6"));

        sharedPreferences = this.getSharedPreferences("com.example.kpt.view", Context.MODE_PRIVATE);
        language = sharedPreferences.getString("LanguageValue","English");

        if(language.equals("English")){
            aboutUs.setText("About Us");
            AboutUsDetail.setText("The general aim of our project is to easily track the price of cryptocurrencies by users.\n" +
                    "to ensure that it is done. Apart from the basic price tracking feature in our application, the application\n" +
                    "Different currency types, language preference, price renewal period with the settings tab in the\n" +
                    "and notification preference options will be presented to the user. However, the application\n" +
                    "In order to use it, the user will need to register with his e-mail address. to user's preference\n" +
                    "When the crypto money chosen by the user reaches the price determined by the user from the e-mail address entered according to\n" +
                    "e-mail will be sent. You can also enter your username, surname and e-mail from the profile tab.\n" +
                    "will be able to view and log out of their account.");
            button.setText("Back");

        }else{
            aboutUs.setText("Hakkımızda");
            AboutUsDetail.setText("Projemizin genel amacı kripto paraların fiyat takibinin kullanıcılar tararfından kolay bir şekilde \n" +
                    "yapılmasını sağlamaktır. Uygulamamızda temel fiyat takibi özelliği dışında, uygulama \n" +
                    "içerisinde bulunacak ayarlar sekmesi ile farklı döviz cinsleri, dil tercihi, fiyat yenileme süresi\n" +
                    "ve bildirim tercihi seçenekleri kullanıcıya sunulacaktır. Bununla beraber uygulamayı \n" +
                    "kullanabilmek için kullanıcın mail adresi ile kayıt olması gerekecektir. Kullanıcının tercihine \n" +
                    "göre girdiği mail adresinden kullancının belirlemiş olduğu fıyata seçtiği kripto para ulaşınca \n" +
                    "mail gönderilecektir. Ayrıca profil sekmesinden kullanıcı adını, soyadını ve mailini \n" +
                    "görüntüleyebilecek ve hesabından çıkış yapabilecektir.");
            button.setText("Geri Dön");
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
