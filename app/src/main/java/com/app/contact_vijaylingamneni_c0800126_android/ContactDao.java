package com.app.contact_vijaylingamneni_c0800126_android;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert
    void insert(Contact contact);

    @Query("select * from Contact")
    List<Contact> getContacts();

    @Delete
    void delete(Contact contact);

    @Update
    void update(Contact contact);

}
