package com.example.cd.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.cd.contact.Contact;
import com.example.cd.params.Params;

import java.util.ArrayList;
import java.util.List;

import static com.example.cd.params.Params.KEY_ADD;
import static com.example.cd.params.Params.KEY_BLOOD;
import static com.example.cd.params.Params.KEY_CITY;
import static com.example.cd.params.Params.KEY_MOBILE;
import static com.example.cd.params.Params.KEY_NAME;
import static com.example.cd.params.Params.TABLE_NAME;

public class MyDbHandler extends SQLiteOpenHelper {

    public MyDbHandler(Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + TABLE_NAME + " ("
                + Params.KEY_MOBILE + " TEXT PRIMARY KEY CHECK(length(" + Params.KEY_MOBILE + ") >= 10), " + Params.KEY_SR_NO + " INTEGER, " + Params.KEY_NAME
                + " TEXT NOT NULL, " + Params.KEY_ADD + " TEXT, " + Params.KEY_CITY + " TEXT, " +
                Params.KEY_BLOOD + " TEXT);";
        Log.d("dbinfo", "Query being run is : " + create);
        db.execSQL(create);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists PARAMS.TABLE_NAME");
    }

    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Params.KEY_SR_NO, contact.getSr_no());
        values.put(Params.KEY_NAME, contact.getName());
        values.put(Params.KEY_ADD, contact.getAdd());
        values.put(Params.KEY_CITY, contact.getCity());
        values.put(Params.KEY_MOBILE, contact.getMobile_no());
        values.put(Params.KEY_BLOOD, contact.getBlood());

        db.insert(TABLE_NAME, null, values);
        Log.d("dbinfo", "Successfully inserted");
        db.close();
    }
    public List<Contact> getAllContacts(){
        List<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Generate the query to read from the database
        String select = "SELECT * FROM " + TABLE_NAME;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(select, null);

        //Loop through now
        if(cursor.moveToFirst()){
            do{
                Contact contact = new Contact();
                contact.setMobile_no((cursor.getString(0)));
                contact.setSr_no(Integer.parseInt(cursor.getString(1)));
                contact.setName(cursor.getString(2));
                contact.setAdd(cursor.getString(3));
                contact.setCity(cursor.getString(4));
                contact.setBlood(cursor.getString(5));
                contactList.add(contact);
            }while(cursor.moveToNext());
        }
        return contactList;
    }
    public void updateContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Params.KEY_MOBILE, contact.getMobile_no());
        values.put(Params.KEY_SR_NO, contact.getSr_no());
        values.put(Params.KEY_NAME, contact.getName());
        values.put(Params.KEY_ADD, contact.getAdd());
        values.put(Params.KEY_CITY, contact.getCity());
        values.put(Params.KEY_BLOOD, contact.getBlood());

        //Lets update now
        db.update(TABLE_NAME, values, Params.KEY_SR_NO + "=?",
                new String[]{String.valueOf(contact.getSr_no())});

    }
    public void deleteContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, Params.KEY_SR_NO +"=?", new String[]{String.valueOf(contact.getSr_no())});
        db.close();
    }
    public void deleteContactById(int id){SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Params.TABLE_NAME, Params.KEY_SR_NO +"=?", new String[]{String.valueOf(id)});
        db.close();}

    public List<Contact> searchName (String keyword) {
        List<Contact> contacts = null;
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME
                    + " where " + KEY_NAME + " like ?", new String[] { "%" + keyword + "%" });
            if (cursor.moveToFirst()) {
                contacts = new ArrayList<Contact>();
                do {
                    Contact contact = new Contact();
                    contact.setMobile_no((cursor.getString(0)));
                    contact.setSr_no(Integer.parseInt(cursor.getString(1)));
                    contact.setName(cursor.getString(2));
                    contact.setAdd(cursor.getString(3));
                    contact.setCity(cursor.getString(4));
                    contact.setBlood(cursor.getString(5));
                    contacts.add(contact);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            contacts = null;
        }
        return contacts;
    }
    public List<Contact> searchPhone (String keyword) {
        List<Contact> contacts = null;
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME
                    + " where " + KEY_MOBILE + " like ?", new String[] { "%" + keyword + "%" });
            if (cursor.moveToFirst()) {
                contacts = new ArrayList<Contact>();
                do {
                    Contact contact = new Contact();
                    contact.setMobile_no((cursor.getString(0)));
                    contact.setSr_no(Integer.parseInt(cursor.getString(1)));
                    contact.setName(cursor.getString(2));
                    contact.setAdd(cursor.getString(3));
                    contact.setCity(cursor.getString(4));
                    contact.setBlood(cursor.getString(5));
                    contacts.add(contact);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            contacts = null;
        }
        return contacts;
    }

    public List<Contact> searchCity (String keyword) {
        List<Contact> contacts = null;
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME
                    + " where " + KEY_CITY + " like ?", new String[] { "%" + keyword + "%" });
            if (cursor.moveToFirst()) {
                contacts = new ArrayList<Contact>();
                do {
                    Contact contact = new Contact();
                    contact.setMobile_no((cursor.getString(0)));
                    contact.setSr_no(Integer.parseInt(cursor.getString(1)));
                    contact.setName(cursor.getString(2));
                    contact.setAdd(cursor.getString(3));
                    contact.setCity(cursor.getString(4));
                    contact.setBlood(cursor.getString(5));
                    contacts.add(contact);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            contacts = null;
        }
        return contacts;
    }
    public List<Contact> searchAddress (String keyword) {
        List<Contact> contacts = null;
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME
                    + " where " + KEY_ADD + " like ?", new String[] { "%" + keyword + "%" });
            if (cursor.moveToFirst()) {
                contacts = new ArrayList<Contact>();
                do {
                    Contact contact = new Contact();
                    contact.setMobile_no((cursor.getString(0)));
                    contact.setSr_no(Integer.parseInt(cursor.getString(1)));
                    contact.setName(cursor.getString(2));
                    contact.setAdd(cursor.getString(3));
                    contact.setCity(cursor.getString(4));
                    contact.setBlood(cursor.getString(5));
                    contacts.add(contact);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            contacts = null;
        }
        return contacts;
    }
    public List<Contact> searchBloodGroup (String keyword) {
        List<Contact> contacts = null;
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME
                    + " where " + KEY_BLOOD + " like ?", new String[] { "%" + keyword + "%" });
            if (cursor.moveToFirst()) {
                contacts = new ArrayList<>();
                do {
                    Contact contact = new Contact();
                    contact.setMobile_no((cursor.getString(0)));
                    contact.setSr_no(Integer.parseInt(cursor.getString(1)));
                    contact.setName(cursor.getString(2));
                    contact.setAdd(cursor.getString(3));
                    contact.setCity(cursor.getString(4));
                    contact.setBlood(cursor.getString(5));
                    contacts.add(contact);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            contacts = null;
        }
        return contacts;
    }
}