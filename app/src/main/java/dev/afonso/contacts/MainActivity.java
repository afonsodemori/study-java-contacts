package dev.afonso.contacts;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();

        setContentView(R.layout.activity_main);

        List<String> contacts = new ArrayList<>(Arrays.asList(
                "Paco", "Quique", "Charo", "Beth", "John", "Adam", "Claire", "Joe", "Paul", "Nicky",
                "Gina", "Bob", "Peter", "Jack", "Janine", "Martha", "George", "Lisa", "Steve"
        ));

        ListView contactsList = findViewById(R.id.activity_main_list_contacts);
        contactsList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts));
    }

    // Other tests

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
    }
}
