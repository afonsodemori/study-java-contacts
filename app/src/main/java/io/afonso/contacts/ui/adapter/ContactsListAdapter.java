package io.afonso.contacts.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.afonso.contacts.R;
import io.afonso.contacts.model.Contact;

public class ContactsListAdapter extends BaseAdapter {
    private final List<Contact> contacts = new ArrayList<>();
    private final Context context;

    public ContactsListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Contact getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return contacts.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View view = LayoutInflater
                .from(context)
                .inflate(R.layout.list_item_contact, parent, false);

        Contact contact = contacts.get(position);

        TextView viewTitle = (TextView) view.findViewById(R.id.list_item_contact_title);
        TextView viewDetail = (TextView) view.findViewById(R.id.list_item_contact_detail);
        String contactDetail = !contact.getEmail().isEmpty() ? contact.getEmail() : contact.getPhone();

        viewTitle.setText(contact.getName());
        viewDetail.setText(contactDetail);

        return view;
    }

    public void update(List<Contact> contacts) {
        this.contacts.clear();
        this.contacts.addAll(contacts);
        notifyDataSetChanged();
    }

    public void remove(Contact contact) {
        contacts.remove(contact);
        notifyDataSetChanged();
    }
}
