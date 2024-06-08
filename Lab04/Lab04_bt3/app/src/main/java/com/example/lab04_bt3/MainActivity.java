package com.example.lab04_bt3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends AppCompatActivity {

    private ReentrantLock reentrantLock;
    private Switch swAutoResponse;
    private LinearLayout llButtons;
    private Button btnSafe, btnMayday;
    private ArrayList<String> requesters;
    private ArrayAdapter<String> adapter;
    private ListView lvMessages;
    private BroadcastReceiver broadcastReceiver;
    public static boolean isRunning;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private final String AUTO_RESPONSE = "auto_response";
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int PERMISSION_REQUEST_SEND_SMS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Kiểm tra và yêu cầu quyền
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS},
                    PERMISSION_REQUEST_CODE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    PERMISSION_REQUEST_SEND_SMS);
        }
        findViewsByIds();
        initVariables();
        handleOnClickListeners();
    }

    private void findViewsByIds() {
        swAutoResponse = (Switch) findViewById(R.id.sw_auto_response);
        llButtons = (LinearLayout) findViewById(R.id.ll_buttons);
        lvMessages = (ListView) findViewById(R.id.lv_messages);
        btnSafe = (Button) findViewById(R.id.btn_safe);
        btnMayday = (Button) findViewById(R.id.btn_mayday);
    }

    public void respond(boolean ok) {
        String okString = getString(R.string.i_am_safe_and_well);
        String notOkString = getString(R.string.tell_my_mother_i_love_her);
        String response = ok ? okString : notOkString;

        ArrayList<String> requestersCopy;
        reentrantLock.lock();
        try {
            requestersCopy = new ArrayList<>(requesters);
        } finally {
            reentrantLock.unlock();
        }

        for (String to : requestersCopy) {
            respond(to, response);
        }
    }

    private void respond(String to, String response) {
        reentrantLock.lock();
        try {
            requesters.remove(to);
            adapter.notifyDataSetChanged();

            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(to, null, response, null, null);
        } finally {
            reentrantLock.unlock();
        }
    }

    public void processReceiveAddresses(ArrayList<String> addresses) {
        reentrantLock.lock();
        try {
            for (String address : addresses) {
                if (!requesters.contains(address)) {
                    requesters.add(address);
                    adapter.notifyDataSetChanged();
                }
            }
        } finally {
            reentrantLock.unlock();
        }

        if (swAutoResponse.isChecked()) {
            respond(true);
        }
    }

    private void handleOnClickListeners() {
        btnSafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                respond(true);
            }
        });

        btnMayday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                respond(false);
            }
        });

        swAutoResponse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                llButtons.setVisibility(isChecked ? View.GONE : View.VISIBLE);
                editor.putBoolean(AUTO_RESPONSE, isChecked);
                editor.apply();
            }
        });
    }

    private void initBroadcastReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ArrayList<String> addresses = intent.getStringArrayListExtra(SmsReceiver.SMS_MESSAGE_ADDRESS_KEY);
                if (addresses != null) {
                    processReceiveAddresses(addresses);
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
        if (broadcastReceiver == null) initBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(SmsReceiver.SMS_FORWARD_BROADCAST_RECEIVER);
        registerReceiver(broadcastReceiver, intentFilter);
    }
    @Override
    protected void onStop() {
        super.onStop();
        isRunning = false;
        unregisterReceiver(broadcastReceiver);
    }
    private void initVariables() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        editor = sharedPreferences.edit();
        reentrantLock = new ReentrantLock();
        requesters = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, requesters);
        lvMessages.setAdapter(adapter);

        boolean autoResponse = sharedPreferences.getBoolean(AUTO_RESPONSE, false);
        swAutoResponse.setChecked(autoResponse);
        llButtons.setVisibility(autoResponse ? View.GONE : View.VISIBLE);

        initBroadcastReceiver();

        Intent intent = getIntent();
        if (intent != null) {
            ArrayList<String> addresses = intent.getStringArrayListExtra(SmsReceiver.SMS_MESSAGE_ADDRESS_KEY);
            if (addresses != null) {
                processReceiveAddresses(addresses);
            }
        }
    }
}