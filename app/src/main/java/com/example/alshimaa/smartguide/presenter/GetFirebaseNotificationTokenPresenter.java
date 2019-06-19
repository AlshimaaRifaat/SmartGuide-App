package com.example.alshimaa.smartguide.presenter;

import android.content.Context;

import com.example.alshimaa.smartguide.api.Client;
import com.example.alshimaa.smartguide.api.Service;
import com.example.alshimaa.smartguide.model.GetFirebaseNotificationTokenResponse;
import com.example.alshimaa.smartguide.view.GetFirebaseNotificationTokenView;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetFirebaseNotificationTokenPresenter {

    GetFirebaseNotificationTokenView getFirebaseNotificationTokenView;

    public GetFirebaseNotificationTokenPresenter(Context context,GetFirebaseNotificationTokenView getFirebaseNotificationTokenView) {
        this.getFirebaseNotificationTokenView = getFirebaseNotificationTokenView;
    }

    public void UpdateToken( String user_token,String firebaseToken,String deviceType,String role) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("user_token", user_token);
        queryMap.put("firebaseToken", firebaseToken);
        queryMap.put("deviceType", deviceType);
        queryMap.put("role", role);
        Service service = Client.getClient().create(Service.class);

        Call<GetFirebaseNotificationTokenResponse> call = service.getFirebaseToken(queryMap);
        call.enqueue(new Callback<GetFirebaseNotificationTokenResponse>() {
            @Override
            public void onResponse(Call<GetFirebaseNotificationTokenResponse> call, Response<GetFirebaseNotificationTokenResponse> response) {

                if (response.code()==200) {

                    getFirebaseNotificationTokenView.success();
//                        getService.ShowTotalprice(String.valueOf(response.body().getData().getTotalCarts()));
                }   else if(response.code()==404){
                    getFirebaseNotificationTokenView.Error();

                }
                else {
                    getFirebaseNotificationTokenView.Error();
                }
            }
            @Override
            public void onFailure(Call<GetFirebaseNotificationTokenResponse> call, Throwable t) {
                getFirebaseNotificationTokenView.Error();
            }
        });
    }

}
