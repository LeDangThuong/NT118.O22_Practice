package com.example.lab_3.Phan2;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.lab_3.R;

public class StudentAdapter extends CursorAdapter {

    public StudentAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_student, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find views
        TextView mssvTextView = view.findViewById(R.id.text_mssv);
        TextView nameTextView = view.findViewById(R.id.text_name);
        TextView classTextView = view.findViewById(R.id.text_class);

        // Extract properties from cursor
        String mssv = cursor.getString(cursor.getColumnIndexOrThrow("MSSV"));
        String name = cursor.getString(cursor.getColumnIndexOrThrow("Hoten"));
        String className = cursor.getString(cursor.getColumnIndexOrThrow("Lop"));

        // Populate views with extracted properties
        mssvTextView.setText(mssv);
        nameTextView.setText(name);
        classTextView.setText(className);
    }
}
