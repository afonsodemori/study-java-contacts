package io.afonso.contacts.dao;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
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
        contact.setStatus(Contact.STATUS_DELETED);
    }

    public static void realRemove(List<Contact> contacts) {
        for (Contact contact : contacts) {
            realRemove(contact);
        }
    }

    public static void remove(Contact contact) {
        contact.setStatus(Contact.STATUS_TRASHED);
        contact.setTrashedAt(new Date().getTime());
    }

    public static void remove(List<Contact> contacts) {
        for (Contact contact : contacts) {
            remove(contact);
        }
    }

    public static void restore(Contact contact) {
        contact.setStatus(Contact.STATUS_ACTIVE);
        // TODO: when a contact is restored and the user clicks "undo", the trashed date is reset
        contact.setTrashedAt(null);
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

    public static List<Contact> findByStatus(int status) {
        List<Contact> result = new ArrayList<>(contacts);
        List<Contact> filteredResult = new ArrayList<>();

        // TODO: Improve this mess...
        Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND, -10);

        for (Contact contact : result) {
            // TODO: Ugly... but works... but ugly
            if (contact.getStatus() == status) {
                if (status == Contact.STATUS_TRASHED && contact.getTrashedAt() < c.getTime().getTime()) {
                    contact.setStatus(Contact.STATUS_DELETED); // deletes permanently contacts in trash for more than 30 days
                    continue;
                }
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
