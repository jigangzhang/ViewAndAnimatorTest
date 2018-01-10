package com.example.administrator.learntask.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.administrator.learntask.service.TestService;

public class TestReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if (intent.getAction().equals("com.example.service.test")) {
            Log.d("tag", "service restart");
            context.startService(new Intent(context, TestService.class));
        }

    }
}
