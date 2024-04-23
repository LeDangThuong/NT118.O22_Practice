package com.example.lab_3.Bai2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        // Create tables again
        onCreate(db);
    }

    // Adding new contact
    public void addContact(Contact contact) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, contact.getName());
            values.put(KEY_PH_NO, contact.getPhoneNumber());
            db.insert(TABLE_CONTACTS, null, values);
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    // Getting single contact
    public Contact getContact(int id) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            cursor = db.query(TABLE_CONTACTS, new String[]{KEY_ID,
                            KEY_NAME, KEY_PH_NO}, KEY_ID + "=?",
                    new String[]{String.valueOf(id)}, null, null, null, null);
            if (cursor.moveToFirst()) {
                return new Contact(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2)
                );
            }
            return null; // Contact not found
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }

    // Getting All Contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getWritableDatabase();
            cursor = db.rawQuery("SELECT  * FROM " + TABLE_CONTACTS, null);
            if (cursor.moveToFirst()) {
                do {
                    contactList.add(new Contact(
                            Integer.parseInt(cursor.getString(0)),
                            cursor.getString(1),
                            cursor.getString(2)
                    ));
                } while (cursor.moveToNext());
            }
            return contactList;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }

    // Updating single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, contact.getName());
            values.put(KEY_PH_NO, contact.getPhoneNumber());
            return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                    new String[]{String.valueOf(contact.getId())});
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                    new String[]{String.valueOf(contact.getId())});
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }
}

