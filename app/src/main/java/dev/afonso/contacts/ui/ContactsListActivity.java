package dev.afonso.contacts.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.NoSuchElementException;

import dev.afonso.contacts.R;
import dev.afonso.contacts.dao.ContactDAO;
import dev.afonso.contacts.model.Contact;

public class ContactsListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        loadFakeContacts();

        ((FloatingActionButton) findViewById(R.id.activity_main_fab_add)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContactsListActivity.this, ContactCreateActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ListView contactsList = findViewById(R.id.activity_contacts_list_listView);
        contactsList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ContactDAO.all()));

        ((ListView) findViewById(R.id.activity_contacts_list_listView)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact ref = ContactDAO.all().get(position);

                try {
                    Contact contact = ContactDAO.find(ref.getId());
                    Log.i(getLocalClassName(), "Loading contact " + contact.getId() + " for edition.");
                } catch (NoSuchElementException e) {
                    Toast.makeText(ContactsListActivity.this, "Contact not found.", Toast.LENGTH_SHORT).show();
                    Log.e(getLocalClassName(), "Contact " + ref.getId() + " not found.");
                }
            }
        });
    }

    private void loadFakeContacts() {
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
    }
}
