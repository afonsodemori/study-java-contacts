package dev.afonso.contacts.model;

import android.view.View;

public class Contact {
    private final String name;
    private final String phone;
    private final String email;

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public String toString() {
        return name;
    }
}
