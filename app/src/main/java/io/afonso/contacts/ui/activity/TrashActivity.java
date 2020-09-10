package io.afonso.contacts.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

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
        setTitle("Trash");

        setUpContactsList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.update(ContactDAO.allInactive());
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
                adapter.update(ContactDAO.allInactive());
                // TODO: It works with this view, but is it correct?
                View view = findViewById(R.id.activity_trash_listView);
                Snackbar.make(view, "Contact restored", Snackbar.LENGTH_LONG)
                        .setAction("Undo", v -> {
                            // TODO: Is there a better way to undo actions?
                            ContactDAO.remove(contact);
                            adapter.update(ContactDAO.allInactive());
                        }).show();
                break;
            case R.id.activity_trash_menu_delete_forever:
                buildDeleteDialog(contact);
                break;
        }

        return super.onContextItemSelected(item);
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
                Toast.makeText(TrashActivity.this, "Contact not found.", Toast.LENGTH_SHORT).show();
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

    private void buildDeleteDialog(Contact contact) {
        new AlertDialog
                .Builder(this)
                .setTitle("Permanently delete contact")
                .setMessage("Remove \"" + contact.getName() + "\" from your contacts? This action can't be undone.")
                .setPositiveButton("Delete forever", (dialog, which) -> {
                    ContactDAO.realRemove(contact); // TODO: Sad sad sad.
                    adapter.remove(contact);
                })
                .setNegativeButton("Cancel", null)
                .show()
        ;
    }
}
