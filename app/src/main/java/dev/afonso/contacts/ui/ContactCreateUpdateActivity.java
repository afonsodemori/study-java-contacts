package dev.afonso.contacts.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import dev.afonso.contacts.R;
import dev.afonso.contacts.dao.ContactDAO;
import dev.afonso.contacts.model.Contact;

import static dev.afonso.contacts.ui.Constants.KEY_CONTACT;

public class ContactCreateUpdateActivity extends AppCompatActivity {

    public static final String TITLE_BAR_NEW = "New contact";
    public static final String TITLE_BAR_UPDATE = "Edit contact";

    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_create);
        setTitle(TITLE_BAR_NEW);

        EditText fieldName = findViewById(R.id.activity_contact_create_name);
        EditText fieldPhone = findViewById(R.id.activity_contact_create_phone);
        EditText fieldEmail = findViewById(R.id.activity_contact_create_email);
        Button buttonSubmit = findViewById(R.id.activity_contact_create_button_submit);

        Intent intent = getIntent();
        contact = (Contact) intent.getSerializableExtra(KEY_CONTACT);

        if (contact != null) {
            setTitle(TITLE_BAR_UPDATE);
            fieldName.setText(contact.getName());
            fieldPhone.setText(contact.getPhone());
            fieldEmail.setText(contact.getEmail());
            buttonSubmit.setText("Update");
        }

        buttonSubmit.setOnClickListener(v -> {
            if (contact == null) {
                contact = new Contact();
            }

            contact
                    .setName(fieldName.getText().toString())
                    .setPhone(fieldPhone.getText().toString())
                    .setEmail(fieldEmail.getText().toString())
            ;

            if (contact.hasValidId()) {
                ContactDAO.update(contact);
            } else {
                try {
                    ContactDAO.save(contact);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            finish();
        });
    }
}
