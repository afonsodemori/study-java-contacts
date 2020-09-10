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

    // TODO: Saddest method of the code. Replace it with states.
    public static void realRemove(Contact contact) {
        contacts.remove(contact);
    }

    public static void realRemove(List<Contact> contacts) {
        for (Contact contact : contacts) {
            realRemove(contact);
        }
    }

    public static void remove(Contact contact) {
        contact.setActive(false);
    }

    public static void remove(List<Contact> contacts) {
        for (Contact contact : contacts) {
            remove(contact);
        }
    }

    public static void restore(Contact contact) {
        contact.setActive(true);
    }

    public static void restore(List<Contact> contacts) {
        for (Contact contact : contacts) {
            restore(contact);
        }
    }

    public static List<Contact> all() {
        List<Contact> result = new ArrayList<>(contacts);
        Collections.sort(result, Contact::compareTo);

        return result;
    }

    public static List<Contact> allActive() {
        List<Contact> result = new ArrayList<>(contacts);
        List<Contact> filteredResult = new ArrayList<>();

        for (Contact contact : result) {
            if (contact.isActive()) {
                filteredResult.add(contact);
            }
        }

        Collections.sort(filteredResult, Contact::compareTo);

        return filteredResult;
    }

    // TODO: Duplicated code. Bad bad code...
    public static List<Contact> allInactive() {
        List<Contact> result = new ArrayList<>(contacts);
        List<Contact> filteredResult = new ArrayList<>();

        for (Contact contact : result) {
            if (!contact.isActive()) {
                filteredResult.add(contact);
            }
        }

        Collections.sort(filteredResult, Contact::compareTo);

        return filteredResult;
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
