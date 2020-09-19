package io.afonso.contacts.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

// TODO: Read more about Parcelable instead of Serializable
//       @see https://developer.android.com/reference/android/os/Parcelable.html
//       @see https://medium.com/@lucas_marciano/por-que-usar-o-parcelable-ao-inv%C3%A9s-do-serializable-5f7543a9c7f3
@Entity
public class Contact implements Comparable, Serializable {

    private static long lastId = 0;

    public static int STATUS_ACTIVE = 1;
    public static int STATUS_TRASHED = 2;
    public static int STATUS_DELETED = 3;

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String phone;
    private String email;
    private int status = STATUS_ACTIVE;
    private Long trashedAt;

    public Contact() {
    }

    @Ignore
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

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getTrashedAt() {
        return trashedAt;
    }

    public void setTrashedAt(Long trashedAt) {
        this.trashedAt = trashedAt;
    }
}
