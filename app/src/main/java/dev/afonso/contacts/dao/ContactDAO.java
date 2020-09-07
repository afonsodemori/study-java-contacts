package dev.afonso.contacts.dao;

import java.util.ArrayList;
import java.util.List;

import dev.afonso.contacts.model.Contact;

public class ContactDAO {
    private static List<Contact> contacts = new ArrayList<>();

    public static void save(Contact contact) {
        contacts.add(contact);
    }

    public static List<Contact> all() {
        return new ArrayList<>(contacts);
    }
}
