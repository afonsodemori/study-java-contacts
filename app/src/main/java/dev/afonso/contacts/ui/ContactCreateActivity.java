package dev.afonso.contacts.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import dev.afonso.contacts.R;
import dev.afonso.contacts.dao.ContactDAO;
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

                ContactDAO.save(contact);

                startActivity(new Intent(
                        ContactCreateActivity.this,
                        ContactsListActivity.class
                ));
            }
        });
    }
}
