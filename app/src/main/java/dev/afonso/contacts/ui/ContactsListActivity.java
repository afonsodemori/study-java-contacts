package dev.afonso.contacts.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.afonso.contacts.R;

public class ContactsListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        List<String> contacts = new ArrayList<>(Arrays.asList(
                "Paco", "Quique", "Charo", "Beth", "John", "Adam", "Claire", "Joe", "Paul", "Nicky",
                "Gina", "Bob", "Peter", "Jack", "Janine", "Martha", "George", "Lisa", "Steve"
        ));

        ListView contactsList = findViewById(R.id.activity_contacts_list_listView);
        contactsList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts));
    }
}
