package com.example.notes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    public static final int NOTE_ACTIVITY_REQUEST_CODE = 1;
    DataBase dataBase;
    ListView booklist;
    SimpleCursorAdapter cursorAdapter;
    SQLiteDatabase db;
    public ArrayList<Book> BooksList;
    Adapter adapter;
    Cursor userCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBase = new DataBase(getApplicationContext());
        dataBase.create_db();
        BooksList = new ArrayList<>();

        booklist = findViewById(R.id.listItem);
        adapter = new Adapter(this, android.R.layout.simple_expandable_list_item_1, BooksList);
/*
        listView.setOnItemClickListener((parent, v, position, id) -> {
            Intent intent = new Intent(MainActivity.this, BookActivity.class);
            intent.putExtra("item", BooksList.get(position));
            intent.putExtra("position", position);
            intent.putExtra("title", position);
            intent.putExtra("author", position);
            intent.putExtra("genre", position);
            intent.putExtra("review", position);
            startActivityForResult(intent, NOTE_ACTIVITY_REQUEST_CODE);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            BooksList.remove(position);
            adapter.notifyDataSetChanged();
            return false;
        });*/
    }

    public void onClickButtonAdd(View view){
        Book a = new Book("Название книги", "", "", "");
        BooksList.add(a);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onResume(){
        super.onResume();
        db = dataBase.getReadableDatabase();
        userCursor = db.rawQuery("select * from " + DataBase.TABLE_BOOK, null);
        String bookList = Arrays.toString(new String[]{DataBase.COLUMN_TITLE});
        cursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_expandable_list_item_1,
                userCursor, new String[]{bookList}, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        booklist.setAdapter(cursorAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Book newItem = (Book) data.getSerializableExtra("newItem");
            int id = data.getIntExtra("id", 0);
            String title = data.getStringExtra("title");
            String author = data.getStringExtra("author");
            String genre = data.getStringExtra("genre");
            String review = data.getStringExtra("review");
            BooksList.set(id, newItem);
            if (id != -1){
                dataBase.updateBook(id, title, author, genre, review);
            }else{
                dataBase.insertBook(title, author, genre, review);
            }
            adapter.notifyDataSetChanged();
        }
    }
    public void onDestroy(){
        super.onDestroy();
        db.close();
        userCursor.close();
    }
}