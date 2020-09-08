package io.afonso.contacts.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import io.afonso.contacts.model.Contact;

public class ContactDAO {
    private static List<Contact> contacts = new ArrayList<>();

    public static void save(Contact contact) throws Exception {
        contact.generateId();
        contacts.add(contact);
    }

    public static void update(Contact contact) {
        Contact originalContact = find(contact.getId());
        contacts.set(contacts.indexOf(originalContact), contact);
    }

    public static void remove(Contact contact) {
        contacts.remove(contact);
    }

    public static List<Contact> all() {
        List<Contact> result = new ArrayList<>(contacts);
        Collections.sort(result, Contact::compareTo);

        return result;
    }

    public static Contact find(long id) {
        for (Contact contact : contacts) {
            if (contact.getId() == id) {
                return contact;
            }
        }

        throw new NoSuchElementException("Contact not found.");
    }
}
