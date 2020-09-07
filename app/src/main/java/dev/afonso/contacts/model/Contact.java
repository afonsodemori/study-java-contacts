package dev.afonso.contacts.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

// TODO: Read more about Parcelable instead of Serializable
//       @see https://developer.android.com/reference/android/os/Parcelable.html
//       @see https://medium.com/@lucas_marciano/por-que-usar-o-parcelable-ao-inv%C3%A9s-do-serializable-5f7543a9c7f3
public class Contact implements Comparable, Serializable {

    private static long lastId = 0;

    private final long id;
    private String name;
    private String phone;
    private String email;

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
