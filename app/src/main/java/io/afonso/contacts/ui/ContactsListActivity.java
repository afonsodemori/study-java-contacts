package io.afonso.contacts.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.NoSuchElementException;

import io.afonso.contacts.R;
import io.afonso.contacts.dao.ContactDAO;
import io.afonso.contacts.model.Contact;
import io.afonso.contacts.ui.adapter.ContactsListAdapter;

import static io.afonso.contacts.ui.Constants.KEY_CONTACT;

public class ContactsListActivity extends AppCompatActivity {

    private ContactsListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        if (Contact.getLastId() == 0) {
            // Avoid creating duplicate entries when rotating the phone or rerunning onCreate for
            // another reason. We only load the initial set of contacts if none were created before.
            loadFakeContacts();
        }

        findViewById(R.id.activity_main_fab_add)
                .setOnClickListener(v -> startActivity(
                        new Intent(ContactsListActivity.this, ContactCreateUpdateActivity.class)
                ));

        setUpContactsList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.clear();
        adapter.addAll(ContactDAO.all());
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
            case R.id.activity_contacts_list_menu_edit:
                openEditionForm(contact);
                break;
            case R.id.activity_contacts_list_menu_delete:
                ContactDAO.remove(contact);
                adapter.remove(contact);
                break;
        }

        return super.onContextItemSelected(item);
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
                Toast.makeText(ContactsListActivity.this, "Contact not found.", Toast.LENGTH_SHORT).show();
                Log.e(getLocalClassName(), "Contact " + clicked.getId() + " not found.");
            }
        });
    }

    private void openEditionForm(Contact contact) {
        startActivity(
                (new Intent(this, ContactCreateUpdateActivity.class))
                        .putExtra(KEY_CONTACT, contact)
        );
    }

    private void loadFakeContacts() {
        try {
            ContactDAO.save(new Contact("Rachel Karen Green", "555-000001", "ray-ray@afonso.dev"));
            ContactDAO.save(new Contact("Ross Geller", "555-000002", "dr.monkey@afonso.dev"));
            ContactDAO.save(new Contact("Chandler Muriel Bing", "555-000003", "miss.chanandler.bong@afonso.dev"));
            ContactDAO.save(new Contact("Monica Geller-Bing", "555-000004", "monana@afonso.dev"));
            ContactDAO.save(new Contact("Joseph Francis Tribbiani", "555-000005", "ken.adams@afonso.dev"));
            ContactDAO.save(new Contact("Phoebe Buffay-Hannigan", "555-000006", "r_phalange@afonso.dev"));
            ContactDAO.save(new Contact("Gunther Central Perk", "555-000007", "gunther@afonso.dev"));
            ContactDAO.save(new Contact("Janice Litman", "555-000008", "just.janice@afonso.dev"));
            ContactDAO.save(new Contact("Mike Hannigan", "555-000009", "mr.no-balls@afonso.dev"));
            ContactDAO.save(new Contact("Heckles, Mr.", "555-000010", "weird_man@afonso.dev"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
