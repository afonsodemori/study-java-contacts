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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import io.afonso.contacts.R;
import io.afonso.contacts.dao.ContactDAO;
import io.afonso.contacts.model.Contact;
import io.afonso.contacts.ui.adapter.ContactsListAdapter;

import static io.afonso.contacts.ui.activity.Constants.KEY_CONTACT;
import static io.afonso.contacts.ui.activity.Constants.KEY_READ_ONLY;

public class TrashActivity extends AppCompatActivity {

    private ContactsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash);
        setTitle(R.string.activity_trash);

        setUpContactsList();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.update(ContactDAO.findByStatus(Contact.STATUS_TRASHED));
    }

    private void setUpContactsList() {
        ListView contactsList = findViewById(R.id.activity_trash_listView);
        setAdapter(contactsList);
        registerForContextMenu(contactsList);
        setOnItemClickListener(contactsList);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_trash_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Contact contact = adapter.getItem(menuInfo.position);

        switch (item.getItemId()) {
            case R.id.activity_trash_menu_restore:
                ContactDAO.restore(contact);
                adapter.update(ContactDAO.findByStatus(Contact.STATUS_TRASHED));
                // TODO: It works with this view, but is it correct?
                View view = findViewById(R.id.activity_trash_listView);
                Snackbar.make(view, R.string.message_contact_restored, Snackbar.LENGTH_LONG)
                        .setAction(R.string.action_undo, v -> {
                            // TODO: Is there a better way to undo actions?
                            ContactDAO.remove(contact);
                            adapter.update(ContactDAO.findByStatus(Contact.STATUS_TRASHED));
                        }).show();
                break;
            case R.id.activity_trash_menu_delete_forever:
                buildDeleteDialog(contact);
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_trash_options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.activity_trash_options_menu_restore_all:
                List<Contact> undo = new ArrayList<>(ContactDAO.findByStatus(Contact.STATUS_TRASHED));
                ContactDAO.restore(ContactDAO.findByStatus(Contact.STATUS_TRASHED));
                adapter.update(ContactDAO.findByStatus(Contact.STATUS_TRASHED));
                View view = findViewById(R.id.activity_trash_listView);
                Snackbar.make(view, getResources().getQuantityString(R.plurals.message_contacts_restores, undo.size(), undo.size()), Snackbar.LENGTH_LONG)
                        .setAction(R.string.action_undo, v -> {
                            // TODO: Is there a better way to undo actions?
                            ContactDAO.remove(undo);
                            adapter.update(ContactDAO.findByStatus(Contact.STATUS_TRASHED));
                        }).show();
                break;
            case R.id.activity_trash_options_menu_empty:
                buildDeleteDialog(ContactDAO.findByStatus(Contact.STATUS_TRASHED));
                break;
        }
        return super.onOptionsItemSelected(item);
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
                openContactForm(contact);
            } catch (NoSuchElementException e) {
                Toast.makeText(TrashActivity.this, R.string.message_contact_not_found, Toast.LENGTH_SHORT).show();
                Log.e(getLocalClassName(), "Contact " + clicked.getId() + " not found.");
            }
        });
    }

    private void openContactForm(Contact contact) {
        startActivity(
                (new Intent(this, ContactFormActivity.class))
                        .putExtra(KEY_CONTACT, contact)
                        .putExtra(KEY_READ_ONLY, true)
        );
    }

    private void buildDeleteDialog(List<Contact> contacts) {
        new AlertDialog
                .Builder(this)
                .setTitle(R.string.dialog_trash_empty_title)
                .setMessage(getResources().getQuantityString(R.plurals.dialog_trash_empty_message, contacts.size(), contacts.size()))
                .setPositiveButton(R.string.dialog_trash_empty_button_positive, (dialog, which) -> {
                    for (Contact contact : contacts) {
                        ContactDAO.realRemove(contact); // TODO: Sad sad sad.
                        adapter.remove(contact);
                    }
                })
                .setNegativeButton(R.string.dialog_button_negative, null)
                .show()
        ;
    }

    private void buildDeleteDialog(Contact contact) {
        new AlertDialog
                .Builder(this)
                .setTitle(R.string.dialog_trash_delete_permanent_title)
                .setMessage(getString(R.string.dialog_trash_delete_permanent_message, contact.getName()))
                .setPositiveButton(R.string.action_delete, (dialog, which) -> {
                    ContactDAO.realRemove(contact); // TODO: Sad sad sad.
                    adapter.remove(contact);
                })
                .setNegativeButton(R.string.dialog_button_negative, null)
                .show()
        ;
    }
}
