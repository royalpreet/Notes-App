package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

import static com.example.notes.MainActivity.notes;

public class Edit extends AppCompatActivity implements TextWatcher {
    EditText text;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        text = (EditText) findViewById(R.id.editText);

        Intent i = getIntent();
        id = i.getIntExtra("id", -1);

        if(id != -1){
            text.setText(notes.get(id));
        }

        text.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        MainActivity.notes.set(id, String.valueOf(s));
        MainActivity.arrayAdapter.notifyDataSetChanged();

        if(MainActivity.set == null){
            MainActivity.set = new HashSet<String>();
        }
        else{
            MainActivity.set.clear();
        }
        MainActivity.set.addAll(MainActivity.notes);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        sharedPreferences.edit().remove("notes").apply();
        sharedPreferences.edit().putStringSet("notes",MainActivity.set).apply();

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
