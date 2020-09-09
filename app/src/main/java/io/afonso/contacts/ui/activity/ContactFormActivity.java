package io.afonso.contacts.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import io.afonso.contacts.R;
import io.afonso.contacts.dao.ContactDAO;
import io.afonso.contacts.model.Contact;

import static io.afonso.contacts.ui.activity.Constants.KEY_CONTACT;

public class ContactFormActivity extends AppCompatActivity {

    public static final String TITLE_BAR_NEW = "New contact";
    public static final String TITLE_BAR_UPDATE = "Edit contact";

    private Contact contact;

    private EditText fieldName;
    private EditText fieldPhone;
    private EditText fieldEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_form);
        initializeFields();
        loadContact();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_contact_form_options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.activity_contact_form_options_menu_save) {
            saveForm();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeFields() {
        fieldName = findViewById(R.id.activity_contact_form_name);
        fieldPhone = findViewById(R.id.activity_contact_form_phone);
        fieldEmail = findViewById(R.id.activity_contact_form_email);
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
