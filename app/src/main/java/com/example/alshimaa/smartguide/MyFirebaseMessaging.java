package com.example.alshimaa.smartguide;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.alshimaa.smartguide.activity.NavigationActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class MyFirebaseMessaging extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    String title,message,address,time,Day;
    MyNotification mNotificationManager;
    Intent intent;
    Context context;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        context=getApplicationContext();
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                Map<String, String> params = remoteMessage.getData();

                JSONObject json = new JSONObject(params);
                sendPushNotification(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
            String status = NetworkUtil.getConnectivityStatusString(context);
            Log.e("Receiver ", "" + status);
            if (status.equals("Not connected to Internet")) {
                Log.e("Receiver ", "not connction");// your code when internet lost
            } else {
                Log.e("Receiver ", "connected to internet");//your code when internet connection come back
            }
        }
    }
    private void sendPushNotification(JSONObject json) {
        mNotificationManager = new MyNotification(getApplicationContext());
        intent = new Intent(getApplicationContext(), NavigationActivity.class);

        Log.e(TAG, "Notification JSON " + json.toString());
        try {
//            JSONObject data = json.getJSONObject("data");

            title = json.getString("title");
            message = json.getString("message");
//            if(address.equals("null")) {
            mNotificationManager.showSmallNotification(title, message, intent);

//            }

        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }
}
