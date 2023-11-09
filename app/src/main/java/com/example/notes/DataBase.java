package com.example.notes;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBase extends SQLiteOpenHelper {
    private static String DB_PATH; // полный путь к базе данных
    private static final String DATABASE_NAME = "books_db"; // Имя базы данных
    private static final int DB_VERSION = 4; // Версия базы данных

    static final String TABLE_BOOK = "book"; // Название таблицы
    public static final String COLUMN_ID = "_id"; // Константа для названия колонки id
    public static final String COLUMN_TITLE = "title"; // Константа для названия колонки названия книги
    public static final String COLUMN_AUTHOR = "author"; // Константа для названия колонки автора
    public static final String COLUMN_GENRE = "genre"; // Константа для названия колонки жанра
    public static final String COLUMN_REVIEW = "review"; // Константа для названия колонки отзыва
    private final Context MyContext;


    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
        this.MyContext = context;
        DB_PATH = context.getFilesDir().getPath() + DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_BOOK + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," // Создание колонки для id с автоинкрементом
                + COLUMN_TITLE + " TEXT," // Создание колонки для названия книги
                + COLUMN_AUTHOR + " TEXT," // Создание колонки для автора книги
                + COLUMN_GENRE + " TEXT," // Создание колонки для жанра
                + COLUMN_REVIEW + " TEXT);"); // Создание колонки для отзыва
    }
    void create_db(){
        File file = new File(DB_PATH);
        if (!file.exists()) {
            //получаем локальную бд как поток
            try(InputStream myInput = MyContext.getAssets().open(DATABASE_NAME);
                // Открываем пустую бд
                OutputStream myOutput = new FileOutputStream(DB_PATH)) {

                // побайтово копируем данные
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myOutput.flush();
            }
            catch(IOException ex){
                Log.d("DatabaseHelper", ex.getMessage());
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 4) { // Проверка старой версии базы данных
            // Дополнительные обновления схемы базы данных для новой версии, если требуется
            db.execSQL("ALTER TABLE " + TABLE_BOOK + " ADD COLUMN new_column INTEGER DEFAULT 0;");
        }
    }

    // Метод для удаления книги из базы данных по её id
    public void deleteBookById(int bookId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_BOOK, COLUMN_ID + "=?", new String[]{String.valueOf(bookId)});
    }

    // Метод для получения адаптера для курсора, который используется для связывания данных с ListView
    public SimpleCursorAdapter getCursorAdapter(Context context, int layout, int[] viewIds) {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_BOOK, null);

        String[] columns = new String[]{COLUMN_TITLE, COLUMN_AUTHOR, COLUMN_GENRE, COLUMN_REVIEW};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(context, layout, cursor, columns, viewIds, 0);
        return adapter;
    }

    // Метод для обновления книги по её id
    public void updateBook(int id, String title, String author, String genre, String review) {
        getWritableDatabase().execSQL("UPDATE " + TABLE_BOOK
                + " SET "
                + COLUMN_TITLE + "='" + title + "', "
                + COLUMN_AUTHOR + "='" + author + "', "
                + COLUMN_GENRE + "='" + genre + "', "
                + COLUMN_REVIEW + "='" + review + "'"
                + " WHERE "
                + COLUMN_ID + "=" + id);
    }

    SQLiteDatabase database = getWritableDatabase(); // Получение экземпляра базы данных
    // Метод для вставки новой книги в базу данных
    public long insertBook(String title, String author, String genre, String review) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title); // Вставка названия книги
        values.put(COLUMN_AUTHOR, author); // Вставка автора книги
        values.put(COLUMN_GENRE, genre); // Вставка жанра книги
        values.put(COLUMN_REVIEW, review); // Вставка отзыва о книге
        return database.insert(TABLE_BOOK, null, values);
    }
    // Метод для получения всех заметок из базы данных в виде курсора
    public Cursor getAllNotes() {
        SQLiteDatabase database = getReadableDatabase();
        return database.query(TABLE_BOOK, null, null, null, null, null, null);

    }
}