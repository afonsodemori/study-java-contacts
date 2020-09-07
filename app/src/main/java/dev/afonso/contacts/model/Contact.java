package dev.afonso.contacts.model;

import androidx.annotation.NonNull;

public class Contact implements Comparable {

    private static long lastId = 0;

    private final long id;
    private final String name;
    private final String phone;
    private final String email;

    public Contact(String name, String phone, String email) {
        this.id = ++lastId;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Object o) {
        return this.name.compareTo(((Contact) o).getName());
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
