package io.afonso.contacts;

import android.app.Application;

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
            ContactDAO.save(new Contact("Gunther Central Perk", "555-000007", "gunther@afonso.dev"));
            ContactDAO.save(new Contact("Janice Litman", "555-000008", "just.janice@afonso.dev"));
            ContactDAO.save(new Contact("Mike Hannigan", "555-000009", "mr.no-balls@afonso.dev"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
