package dev.afonso.contacts.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import dev.afonso.contacts.R;
import dev.afonso.contacts.dao.ContactDAO;

public class ContactsListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        ListView contactsList = findViewById(R.id.activity_contacts_list_listView);
        contactsList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ContactDAO.all()));
    }
}
