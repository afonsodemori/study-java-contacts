package io.afonso.contacts.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.NoSuchElementException;

import io.afonso.contacts.R;
import io.afonso.contacts.dao.ContactDAO;
import io.afonso.contacts.model.Contact;
import io.afonso.contacts.ui.adapter.ContactsListAdapter;

import static io.afonso.contacts.ui.activity.Constants.KEY_CONTACT;

public class ContactsListActivity extends AppCompatActivity {

    private ContactsListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        findViewById(R.id.activity_contacts_list_fab_add)
                .setOnClickListener(v -> startActivity(
                        new Intent(ContactsListActivity.this, ContactFormActivity.class)
                ));

        setUpContactsList();

        new AlertDialog.Builder(this)
                .setTitle(R.string.temp_initial_warning_title)
                .setMessage(R.string.temp_initial_warning_message)
                .setPositiveButton(R.string.temp_initial_warning_button_positive, null)
                .setNegativeButton(null, null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.update(ContactDAO.findByStatus(Contact.STATUS_ACTIVE));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_contacts_list_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Contact contact = adapter.getItem(menuInfo.position);

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.activity_contacts_list_menu_edit:
                openEditionForm(contact);
                break;
            case R.id.activity_contacts_list_menu_delete:
                ContactDAO.remove(contact);
                adapter.remove(contact);
                // TODO: It works with this view, but is it correct?
                View view = findViewById(R.id.activity_contacts_list_listView);
                Snackbar.make(view, R.string.message_sent_to_trash, Snackbar.LENGTH_LONG)
                        .setAction(R.string.action_undo, v -> {
                            // TODO: Is there a better way to undo actions?
                            ContactDAO.restore(contact);
                            adapter.update(ContactDAO.findByStatus(Contact.STATUS_ACTIVE));
                        }).show();
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_contacts_list_options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = null;

        switch (item.getItemId()) {
            case R.id.activity_contacts_list_options_menu_trash:
                intent = new Intent(this, TrashActivity.class);
                break;
            case R.id.activity_contacts_list_options_menu_about:
                intent = new Intent(this, AboutActivity.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUpContactsList() {
        ListView contactsList = findViewById(R.id.activity_contacts_list_listView);
        setAdapter(contactsList);
        registerForContextMenu(contactsList);
        setOnItemClickListener(contactsList);
    }

    private void setAdapter(ListView contactsList) {
        adapter = new ContactsListAdapter(this);
        contactsList.setAdapter(adapter);
    }

    private void setOnItemClickListener(ListView contactsList) {
        contactsList.setOnItemClickListener((parent, view, position, id) -> {
            Contact clicked = (Contact) parent.getItemAtPosition(position);

            try {
                Contact contact = ContactDAO.find(clicked.getId());
                Log.i(getLocalClassName(), "Loading contact " + contact.getId() + " for edition.");
                openEditionForm(contact);
            } catch (NoSuchElementException e) {
                Toast.makeText(ContactsListActivity.this, R.string.message_contact_not_found, Toast.LENGTH_SHORT).show();
                Log.e(getLocalClassName(), "Contact " + clicked.getId() + " not found.");
            }
        });
    }

    private void openEditionForm(Contact contact) {
        startActivity(
                (new Intent(this, ContactFormActivity.class))
                        .putExtra(KEY_CONTACT, contact)
        );
    }
}
