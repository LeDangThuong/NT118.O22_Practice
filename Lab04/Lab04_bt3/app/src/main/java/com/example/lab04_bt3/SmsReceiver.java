package com.example.lab04_bt3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.ArrayList;

public class SmsReceiver extends BroadcastReceiver {

    public static final String SMS_FORWARD_BROADCAST_RECEIVER = "sms_forward_broadcast_receiver";
    public static final String SMS_MESSAGE_ADDRESS_KEY = "sms_messages_key";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Hiển thị một thông báo ngắn cho biết SmsReceiver đã được kích hoạt
        Toast.makeText(context.getApplicationContext(), "SmsReceiver", Toast.LENGTH_SHORT).show();

        // Định nghĩa chuỗi truy vấn để tìm kiếm trong nội dung tin nhắn SMS
        String queryString = "Are you OK?".toLowerCase();
        System.out.println("________" + queryString);

        // Lấy dữ liệu SMS từ các extras của intent
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            SmsMessage[] messages = new SmsMessage[pdus.length];
            for (int i = 0; i < pdus.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            }

            // Tạo một danh sách để lưu trữ địa chỉ gửi của các tin nhắn SMS liên quan
            ArrayList<String> addresses = new ArrayList<>();
            for (SmsMessage message : messages) {
                // Kiểm tra xem nội dung tin nhắn SMS có chứa chuỗi truy vấn hay không
                if (message.getMessageBody().toLowerCase().contains(queryString)) {
                    addresses.add(message.getOriginatingAddress());
                }
            }

            // Nếu có các địa chỉ liên quan:
            if (!addresses.isEmpty()) {
                // Nếu MainActivity không đang chạy, khởi động nó với danh sách các địa chỉ
                if (!MainActivity.isRunning) {
                    Intent iMain = new Intent(context, MainActivity.class);
                    iMain.putStringArrayListExtra(SMS_MESSAGE_ADDRESS_KEY, addresses);
                    iMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(iMain);
                } else {
                    // Nếu không, chuyển tiếp các địa chỉ bằng cách sử dụng broadcast
                    Intent iForwardBroadcastReceiver = new Intent(SMS_FORWARD_BROADCAST_RECEIVER);
                    iForwardBroadcastReceiver.putStringArrayListExtra(SMS_MESSAGE_ADDRESS_KEY, addresses);
                    context.sendBroadcast(iForwardBroadcastReceiver);
                }
            }
        }
    }
}
