package dev.afonso.contacts.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import dev.afonso.contacts.R;
import dev.afonso.contacts.dao.ContactDAO;

public class ContactsListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

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
    }
}
