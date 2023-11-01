package com.example.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.View;
import android.widget.EditText;

public class NoteActivity extends AppCompatActivity {
    Zametka zametka;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        zametka = (Zametka) getIntent().getSerializableExtra("item");
        position = getIntent().getIntExtra("position", 0);

        EditText titleEditor = findViewById(R.id.titleEditor);
        EditText textEditor = findViewById(R.id.textEditor);

        titleEditor.setText(zametka.getTitle());
        textEditor.setText(zametka.getTxt());

        textEditor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                zametka.setTxt(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        titleEditor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                zametka.setTitle(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void onBackPressed(){
        Intent intent = new Intent();
        intent.putExtra("newItem", zametka);
        intent.putExtra("position1", position);
        setResult(RESULT_OK, intent);
        finish();
    }
}