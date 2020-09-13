package io.afonso.contacts.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import io.afonso.contacts.R;
import io.afonso.contacts.dao.ContactDAO;
import io.afonso.contacts.model.Contact;

import static io.afonso.contacts.ui.activity.Constants.KEY_CONTACT;
import static io.afonso.contacts.ui.activity.Constants.KEY_READ_ONLY;

public class ContactFormActivity extends AppCompatActivity {

    private Contact contact;
    private boolean isReadOnly;

    private EditText fieldName;
    private EditText fieldPhone;
    private EditText fieldEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getResources().getText(R.string.activity_trash);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_form);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initializeFields();
        loadContact();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!isReadOnly) {
            getMenuInflater().inflate(R.menu.activity_contact_form_options_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.activity_contact_form_options_menu_save:
                saveForm();
                break;
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
        isReadOnly = intent.getBooleanExtra(KEY_READ_ONLY, false);

        if (contact == null) {
            setTitle(R.string.activity_contact_form_create);
            contact = new Contact();
        } else {
            setTitle(R.string.activity_contact_form_update);
            fillForm();
        }

        if (isReadOnly) {
            setTitle(R.string.activity_contact_form_readonly);
            fieldName.setEnabled(false);
            fieldPhone.setEnabled(false);
            fieldEmail.setEnabled(false);
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
