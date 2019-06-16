package com.example.alshimaa.smartguide.presenter;

import android.content.Context;


import com.example.alshimaa.smartguide.api.Client;
import com.example.alshimaa.smartguide.api.Service;
import com.example.alshimaa.smartguide.view.TokensView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokensPresenter {


   /* TokensView getService;

    public TokensPresenter(Context context,TokensView getService) {
        this.getService = getService;
    }

    public void UpdateToken(String token, String user) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("token_app", token);
        Service service = Client.getClient().create(Service.class);

        Call<TokenResponse> call = service.UpDateToken(queryMap,"Bearer "+user);
        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {

                if (response.code()==200) {

                    getService.success();
//                        getService.ShowTotalprice(String.valueOf(response.body().getData().getTotalCarts()));
                }   else if(response.code()==404){
                    getService.Error();

                }
                else {
                    getService.Error();
                }
            }
            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                getService.Error();
            }
        });
    }
*/
}
