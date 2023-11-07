package com.example.notes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static final int NOTE_ACTIVITY_REQUEST_CODE = 1;
    public ArrayList<Book> BooksList;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataBase.createInstance(this);
        if(DataBase.info()){
            BooksList = DataBase.getInstance().getItem("listOfItem");
        } else {
            BooksList = new ArrayList<>();
        }

        ListView listView = findViewById(R.id.listItem);
        adapter = new Adapter(this, android.R.layout.simple_expandable_list_item_1, BooksList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, v, position, id) -> {
            Intent intent = new Intent(MainActivity.this, BookActivity.class);
            intent.putExtra("item", BooksList.get(position));
            intent.putExtra("position", position);
            startActivityForResult(intent, NOTE_ACTIVITY_REQUEST_CODE);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            BooksList.remove(position);
            adapter.notifyDataSetChanged();
            return false;
        });
    }

    public void onClickButtonAdd(View view){
        Book a = new Book("Название книги", "", "", "");
        BooksList.add(a);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NOTE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                Book newItem = (Book) data.getSerializableExtra("newItem");
                int position1 = data.getIntExtra("position1", -1);
                BooksList.set(position1, newItem);
                DataBase.getInstance().addItem(BooksList);
                adapter.notifyDataSetChanged();
            }
        }
    }


}