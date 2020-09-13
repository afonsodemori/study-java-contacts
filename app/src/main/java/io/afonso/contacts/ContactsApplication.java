package io.afonso.contacts;

import android.app.Application;

import java.util.Date;

import io.afonso.contacts.dao.ContactDAO;
import io.afonso.contacts.model.Contact;

public class ContactsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        loadFakeContacts();
    }

    private void loadFakeContacts() {
        try {
            ContactDAO.save(new Contact("Rachel Karen Green", "555-000001", "ray-ray@afonso.dev"));
            ContactDAO.save(new Contact("Ross Geller", "555-000002", "dr.monkey@afonso.dev"));
            ContactDAO.save(new Contact("Chandler Muriel Bing", "555-000003", "miss.chanandler.bong@afonso.dev"));
            ContactDAO.save(new Contact("Monica Geller-Bing", "555-000004", "monana@afonso.dev"));
            ContactDAO.save(new Contact("Joseph Francis Tribbiani", "555-000005", "ken.adams@afonso.dev"));
            ContactDAO.save(new Contact("Phoebe Buffay-Hannigan", "555-000006", "r_phalange@afonso.dev"));
            ContactDAO.save(new Contact("Gunther Central Perk", "555-000007", "iloverachel@afonso.dev"));
            ContactDAO.save(new Contact("Janice Litman", "555-000008", "just.janice@afonso.dev"));
            ContactDAO.save(new Contact("Mike Hannigan", "555-000009", "mr.no-balls@afonso.dev"));
            // in trash
            ContactDAO.save(
                    (new Contact("Heckles, Mr.", "555-000010", "weird_man@afonso.dev"))
                            .setTrashedAt(new Date())
                            .setStatus(Contact.STATUS_TRASHED)
            );
            ContactDAO.save(
                    (new Contact("Julie, from NY", "555-000011", "talker@afonso.dev"))
                            .setTrashedAt(new Date())
                            .setStatus(Contact.STATUS_TRASHED)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
