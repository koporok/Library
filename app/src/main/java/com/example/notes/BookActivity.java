package com.example.notes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;

public class BookActivity extends AppCompatActivity {
    Book book;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        book = (Book) getIntent().getSerializableExtra("item");
        position = getIntent().getIntExtra("position", 0);

        EditText titleEditor = findViewById(R.id.titleEditor);
        EditText textEditor = findViewById(R.id.textEditor);
        EditText Author = findViewById(R.id.AuthorEditor);
        EditText Feedback = findViewById(R.id.FeedBackEditor);


        titleEditor.setText(book.getTitle());
        textEditor.setText(book.getTxt());
        Author.setText(book.getAuthor());
        Feedback.setText(book.getFeedBack());

        Author.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                book.setAuthor(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        textEditor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                book.setTxt(s.toString());
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
                book.setTitle(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        Feedback.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                book.setFeedBack(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void onBackPressed(){
        Intent intent = new Intent();
        intent.putExtra("newItem", book);
        intent.putExtra("position1", position);
        setResult(RESULT_OK, intent);
        finish();
    }
}