package io.afonso.contacts.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import io.afonso.contacts.R;

public class TrashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash);
        setTitle("Trash");
    }
}
