package dev.afonso.contacts.ui;

import android.content.Intent;
import android.os.Bundle;
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

        Intent intent = getIntent();
        Contact contact = (Contact) intent.getSerializableExtra("contact");

        setTitle("New contact");

        EditText fieldName = findViewById(R.id.activity_contact_create_name);
        EditText fieldPhone = findViewById(R.id.activity_contact_create_phone);
        EditText fieldEmail = findViewById(R.id.activity_contact_create_email);
        Button buttonSubmit = findViewById(R.id.activity_contact_create_button_submit);

        if (contact != null) {
            setTitle("Edit contact");
            fieldName.setText(contact.getName());
            fieldPhone.setText(contact.getPhone());
            fieldEmail.setText(contact.getEmail());
            buttonSubmit.setText("Update");
        }

        buttonSubmit.setOnClickListener(v -> {
            if (contact == null) {
                ContactDAO.save(new Contact(
                        fieldName.getText().toString(),
                        fieldPhone.getText().toString(),
                        fieldEmail.getText().toString()
                ));
            } else {
                contact
                        .setName(fieldName.getText().toString())
                        .setPhone(fieldPhone.getText().toString())
                        .setEmail(fieldEmail.getText().toString())
                ;
                ContactDAO.update(contact);
            }

            finish();
        });
    }
}
