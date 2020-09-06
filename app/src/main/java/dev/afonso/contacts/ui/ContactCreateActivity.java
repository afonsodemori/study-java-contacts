package dev.afonso.contacts.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import dev.afonso.contacts.R;

public class ContactCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_create);
        setTitle("New contact");
    }
}
