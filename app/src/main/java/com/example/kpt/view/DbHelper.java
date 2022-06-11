package com.example.kpt.view;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME ="Customer";
    static final String TABLE_NAME ="CustomerList";
    static final int DATABASE_VERSION = 1;
    static final String ID ="Id";
    static final String NAME ="Name";
    static final String SURNAME ="Surname";
    static final String EMAIL ="Email";
    static final String PASSWORD ="Passoword";
    static final String ISACTIVE ="IsActive";
    static final String ACTIVEAFTER ="ActiveAfter";

    Context context;
    public DbHelper(Context contex)
    {
        super(contex,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE = "CREATE TABLE "+TABLE_NAME+" ("
                +ID+" INTEGER PRIMARY KEY,"
                +NAME+ " TEXT,"
                +SURNAME+ " TEXT,"
                +EMAIL+ " TEXT,"
                +PASSWORD+ " TEXT,"
                +ISACTIVE+" TEXT,"
                +ACTIVEAFTER+ " TEXT"+")";
        sqLiteDatabase.execSQL(CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addContact(Customer contact)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME,contact.getName());
        values.put(SURNAME,contact.getSurname());
        values.put(EMAIL,contact.getEmail());
        values.put(PASSWORD,contact.getPassword());
        values.put(ISACTIVE,contact.isActive());
        values.put(ACTIVEAFTER,contact.getActiveAfter());
        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public Customer getContactById (String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,new String[]{ID,NAME,TABLE_NAME},ID +" =?",new String[]{id},null,null,null);
        if(cursor!=null)
            cursor.moveToFirst();
        Customer contact =new Customer(cursor.getString(0).toString(), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4), cursor.getInt(5), cursor.getInt(6));
        return  contact;
    }

    public List<Customer> getAllContacts(){
        List<Customer> contactList = new ArrayList<Customer>();
        String selectQuerry = "select * from "+TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuerry,null);

        if(cursor.moveToFirst()){
            do{
                Customer tmpContact = new Customer();
                tmpContact.setId(cursor.getString(0));
                tmpContact.setName(cursor.getString(1));
                tmpContact.setSurname(cursor.getString(2));
                tmpContact.setEmail(cursor.getString(3));
                tmpContact.setPassword(cursor.getString(4));
                tmpContact.setActive(Integer.parseInt(cursor.getString(5)));
                tmpContact.setActiveAfter(Integer.parseInt(cursor.getString(6)));
                contactList.add(tmpContact);
            }while (cursor.moveToNext());
        }
        return contactList;
    }
    public void updateContact(Customer contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME,contact.getName());
        values.put(SURNAME,contact.getSurname());
        values.put(EMAIL,contact.getEmail());
        values.put(PASSWORD,contact.getPassword());
        values.put(ISACTIVE,contact.isActive());
        values.put(ACTIVEAFTER,contact.getActiveAfter());

        db.update(TABLE_NAME,values,ID+"=?",new String[]{String.valueOf(contact.getId())});
        db.close();
    }
    public void deleteContact (Customer contact){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,ID+"=?",new String[]{String.valueOf(contact.getId())});
        db.close();
    }

}
