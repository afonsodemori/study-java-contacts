package io.afonso.contacts.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import io.afonso.contacts.model.Contact;

@Database(entities = {Contact.class}, version = 1, exportSchema = false)
public abstract class ContactsDatabase extends RoomDatabase {
}
