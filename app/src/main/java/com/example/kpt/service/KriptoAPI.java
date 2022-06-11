package com.example.kpt.service;

import com.example.kpt.model.KriptoModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface KriptoAPI {

    @GET("currencies/ticker?key=3345c8d650f83149e36aff22d2a737107d5df647&ids=BTC,ETH,XRP,USDT,ADA,SOL,LTC,TRX,DOT,LINK,ALGO,MANA,AAVE,HT&interval=1d,30d&convert=EUR&per-page=100&attributes=id,name,logo_url,price&page=1%22")
    Observable<List<KriptoModel>> getData();
}
