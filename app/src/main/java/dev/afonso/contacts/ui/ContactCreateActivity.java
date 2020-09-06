package dev.afonso.contacts.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dev.afonso.contacts.R;
import dev.afonso.contacts.model.Contact;

public class ContactCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_create);
        setTitle("New contact");

        Button createButton = findViewById(R.id.activity_contact_create_button_submit);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact contact = new Contact(
                        ((EditText) findViewById(R.id.activity_contact_create_name)).getText().toString(),
                        ((EditText) findViewById(R.id.activity_contact_create_phone)).getText().toString(),
                        ((EditText) findViewById(R.id.activity_contact_create_email)).getText().toString()
                );

                Toast.makeText(
                        ContactCreateActivity.this,
                        new StringBuilder(contact.getName())
                                .append(", ")
                                .append(contact.getPhone())
                                .append(", ")
                                .append(contact.getEmail()),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }
}
