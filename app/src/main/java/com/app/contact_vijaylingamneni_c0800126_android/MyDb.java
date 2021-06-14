package com.app.contact_vijaylingamneni_c0800126_android;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class}, version = 1)
public abstract class MyDb extends RoomDatabase {

    public abstract ContactDao contactDao();

    private static MyDb instance;

    public static MyDb getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, MyDb.class, "contact_db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration().build();
        }
        return instance;

    }
}
