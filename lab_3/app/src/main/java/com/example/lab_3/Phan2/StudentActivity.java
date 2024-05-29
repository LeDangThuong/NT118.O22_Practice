package com.example.lab_3.Phan2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab_3.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {
    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database = null;
    String DATABASE_NAME = "qlsv.db";
    //Khai báo ListView
    ListView lv;
    ArrayList<String> mylist = new ArrayList<>();
    ArrayAdapter<String> myadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_listview);
//Ham Copy CSDL từ assets vào thư mục Databases
        processCopy();
//Mo CSDL trong ung dung len
        database = openOrCreateDatabase("qlsv.db",MODE_PRIVATE, null);
// Tạo ListView
        lv = findViewById(R.id.listview);
        myadapter = new ArrayAdapter<>(StudentActivity.this,
                android.R.layout.simple_list_item_1, mylist);
        lv.setAdapter(myadapter);
        // Truy vấn CSDL và cập nhật hiển thị lên Listview
        Cursor c =
                database.query("qlsv",null,null,null,null,null,null);
        c.moveToFirst();
        String data = "";
        while (c.isAfterLast() == false)
        {
            data = c.getString(0)+"-"+c.getString(1)+" "+c.getString(2);
            mylist.add(data);
            c.moveToNext();
        }
        c.close();
        myadapter.notifyDataSetChanged();
    }
    private void processCopy() {
        //private app
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (dbFile.exists())
            dbFile.delete(); // Xóa file cũ nếu đã tồn tại
        try {
            CopyDataBaseFromAsset();
            Toast.makeText(this, "Copying success from Assets folder",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, e.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }
    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX+
                DATABASE_NAME;
    }
    // Ham copy file DB tu thu muc Asset vao file DB moi tao ra trong
    //ung dung
    public void CopyDataBaseFromAsset() {
        try {
            InputStream myInput;
            myInput = getAssets().open(DATABASE_NAME);
            String outFileName = getDatabasePath();
            // Kiem tra neu duong dan khong co, thi tao moi file
            File f = new File(getApplicationInfo().dataDir +
                    DB_PATH_SUFFIX);
            if (!f.exists())
                f.mkdir();
            // Mo empty db su dung output stream
            OutputStream myOutput = new FileOutputStream(outFileName);
            // Sao chep du lieu bytes tu input toi ouput
            int size = myInput.available();
            byte[] buffer = new byte[size];
            myInput.read(buffer);
            myOutput.write(buffer);
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}