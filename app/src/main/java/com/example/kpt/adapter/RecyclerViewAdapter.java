package com.example.kpt.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kpt.R;
import com.example.kpt.model.KriptoModel;
import com.example.kpt.view.Customer;
import com.example.kpt.view.DbHelper;
import com.example.kpt.view.LoginPage;
import com.example.kpt.view.SignUpPage;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RowHolder> {

    private ArrayList<KriptoModel> kriptoList;

    public RecyclerViewAdapter(ArrayList<KriptoModel> kriptoList) {
        this.kriptoList = kriptoList;
    }

    private String[] colors = {"#51938d","#a64d79","#638cff","#ff9663","#967ef9","#f5f09e","#99a9ff"};

    public String kur;

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_layout,parent,false);
        return new RowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {
        holder.bind(kriptoList.get(position),colors,position);
    }

    @Override
    public int getItemCount() {
        return kriptoList.size();
    }

    public void setKur(String kurFonksiyon){
        kur = kurFonksiyon;
    }

    public class RowHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textPrice;
        TextView textViewKur;

        public RowHolder(@NonNull View itemView) {
            super(itemView);

        }


        public void bind(KriptoModel model,String[] colors, Integer position){
            itemView.setBackgroundColor(Color.parseColor(colors[position % 7]));
            textName = itemView.findViewById(R.id.textName);
            textPrice = itemView.findViewById(R.id.textPrice);


            if(kur.equals("USD")){
                textName.setText(model.currency);
                textPrice.setText(model.price + " USD");
            }else{
                textName.setText(model.currency);
                textPrice.setText(Double.parseDouble(model.price) * 17.50 + " TL");
            }
           // textName.setText(model.currency);
           // textPrice.setText(model.price + " $");    // Burası sonradan değişebilir



        }

    }
}


