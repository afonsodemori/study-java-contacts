package dev.afonso.contacts.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import dev.afonso.contacts.R;
import dev.afonso.contacts.dao.ContactDAO;
import dev.afonso.contacts.model.Contact;

import static dev.afonso.contacts.ui.Constants.KEY_CONTACT;

public class ContactCreateUpdateActivity extends AppCompatActivity {

    public static final String TITLE_BAR_NEW = "New contact";
    public static final String TITLE_BAR_UPDATE = "Edit contact";

    private Contact contact;

    private EditText fieldName;
    private EditText fieldPhone;
    private EditText fieldEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_create);
        initializeFields();
        loadContact();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_contacts_list_options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.activity_contacts_list_menu_save) {
            saveForm();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeFields() {
        fieldName = findViewById(R.id.activity_contact_create_name);
        fieldPhone = findViewById(R.id.activity_contact_create_phone);
        fieldEmail = findViewById(R.id.activity_contact_create_email);
    }

    private void loadContact() {
        Intent intent = getIntent();
        contact = (Contact) intent.getSerializableExtra(KEY_CONTACT);

        if (contact == null) {
            setTitle(TITLE_BAR_NEW);
            contact = new Contact();
        } else {
            setTitle(TITLE_BAR_UPDATE);
            fillForm();
        }
    }

    private void fillForm() {
        fieldName.setText(contact.getName());
        fieldPhone.setText(contact.getPhone());
        fieldEmail.setText(contact.getEmail());
    }

    private void updateContact() {
        contact
                .setName(fieldName.getText().toString())
                .setPhone(fieldPhone.getText().toString())
                .setEmail(fieldEmail.getText().toString())
        ;
    }

    private void saveForm() {
        updateContact();

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
    }
}
