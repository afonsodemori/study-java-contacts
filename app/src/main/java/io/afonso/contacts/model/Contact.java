package io.afonso.contacts.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

// TODO: Read more about Parcelable instead of Serializable
//       @see https://developer.android.com/reference/android/os/Parcelable.html
//       @see https://medium.com/@lucas_marciano/por-que-usar-o-parcelable-ao-inv%C3%A9s-do-serializable-5f7543a9c7f3
public class Contact implements Comparable, Serializable {

    private static long lastId = 0;

    private long id;
    private String name;
    private String phone;
    private String email;

    public Contact() {
    }

    public Contact(String name, String phone, String email) {
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

    public boolean hasValidId() {
        return this.id > 0;
    }

    public Contact generateId() throws Exception {
        if (this.hasValidId()) {
            // TODO: Read more about java exception classes
            throw new Exception("Trying to generate ID for a Contact with a valid ID.");
        }

        this.id = ++lastId;
        return this;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Contact setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Contact setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Contact setEmail(String email) {
        this.email = email;
        return this;
    }
}
